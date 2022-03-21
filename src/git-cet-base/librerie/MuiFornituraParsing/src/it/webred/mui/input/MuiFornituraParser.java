package it.webred.mui.input;

import it.webred.mui.MuiException;
import it.webred.mui.hibernate.HibernateUtil;
import it.webred.mui.Constants;
import it.webred.mui.model.MiDupFabbricatiIdentifica;
import it.webred.mui.model.MiDupFabbricatiInfo;
import it.webred.mui.model.MiDupFornitura;
import it.webred.mui.model.MiDupFornituraInfo;
import it.webred.mui.model.MiDupIndirizziSog;
import it.webred.mui.model.MiDupNotaTras;
import it.webred.mui.model.MiDupSoggetti;
import it.webred.mui.model.MiDupTerreniInfo;
import it.webred.mui.model.MiDupTitolarita;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.skillbill.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MuiFornituraParser implements Constants
{
	private InputStream								_input;

	private boolean									_transactionalImport			= true;

	private String									_currentline					= null;

	private RowField								_rowField						= new RowField();

	private Map<Integer, MuiFornituraRecordParser>	_parsers						= new HashMap<Integer, MuiFornituraRecordParser>();

	private static Map<Long, MuiFornituraParser>	_runningParsers					= new HashMap<Long, MuiFornituraParser>();

	public static SimpleDateFormat					yearParser						= new SimpleDateFormat("yyyy");

	public static SimpleDateFormat					dateParser						= new SimpleDateFormat("ddMMyyyy");
	
	public static SimpleDateFormat					dateParser6						= new SimpleDateFormat("ddMMyy");

	public static final Integer						FORNITURA_RECORD_TYPE			= 1;

	public static final Integer						NOTA_RECORD_TYPE				= 2;

	public static final Integer						SOGGETTO_RECORD_TYPE			= 3;

	public static final Integer						TITOLARITA_RECORD_TYPE			= 4;

	public static final Integer						INDIRIZZO_RECORD_TYPE			= 5;

	public static final Integer						IMMOBILEINFO_RECORD_TYPE		= 6;

	public static final Integer						IMMOBILEIDENTIFICA_RECORD_TYPE	= 7;

	public static final Integer						TERRENO_RECORD_TYPE				= 8;

	public static final Integer						FORNITURA_INFO_RECORD_TYPE		= 9;

	private int										_rowCount						= 0;

	private int										nFileCount						= -1;

	private int										nFileTot						= -1;

	private static Map<Class, Map>					_classMethods					= new HashMap<Class, Map>();

	private LineNumberReader						lnr;

	private MiDupFornitura							miDupFornitura;
	
	private String 									fileAbsolutePath;					

	public void parse()
		throws IOException
	{
		parse(false);
	}

	public void parse(boolean xmlOriginated)
		throws IOException
	{
		List<String> errori = new ArrayList<String>();
		try
		{
			MuiFornituraRecordParser recordParser;
			lnr = new LineNumberReader(new InputStreamReader(_input));
			_currentline = lnr.readLine();
			if (_currentline == null)
			{
				throw new MuiInvalidInputDataException("Il File e' vuoto!");
			}
			int rt1 = evalRecordType();
			if (rt1 != 1)
			{
				throw new MuiInvalidInputDataException("Il Formato del File e' errato, il file non comincia con l'header della fornitura note!");
			}

			try
			{
				recordParser = getRecordParserForRecordType(1);
				recordParser.parse(_currentline);
			}
			catch (Throwable t)
			{
				Logger.log().error("Il Formato del File e' errato ", _currentline, t);
				throw new MuiInvalidInputDataException("Il Formato del File e' errato, errore durante la lettura dell'header della fornitura note!");
			}
			miDupFornitura = (MiDupFornitura) recordParser.getRecord();
			if (nFileTot != -1)
			{
				miDupFornitura.setFileTotNotParsed(String.valueOf(nFileTot));
			}
			if (nFileCount != -1)
			{
				miDupFornitura.setFileLoadedNotParsed(String.valueOf(nFileCount));
			}

			Logger.log().info(MiDupFornitura.class.getName(), miDupFornitura);
			// recordParser.store();
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();

			Query query = session.createQuery("select fornitura from MiDupFornitura as fornitura  where" + " (fornitura.dataInizialeDate <= :dataInizialeDate AND fornitura.dataFinaleDate > :dataInizialeDate) or " + " (fornitura.dataInizialeDate <= :dataFinaleDate AND fornitura.dataFinaleDate >= :dataFinaleDate) or "
					+ " (fornitura.dataInizialeDate >= :dataInizialeDate AND fornitura.dataFinaleDate <= :dataFinaleDate) ");
			query.setDate("dataInizialeDate", dateParser.parse(miDupFornitura.getDataIniziale()));
			query.setDate("dataFinaleDate", dateParser.parse(miDupFornitura.getDataFinale()));
			query.getQueryString();
			Logger.log().info("query ", query);
			MiDupFornitura overlapping = null;
			for (Iterator it = query.iterate(); it.hasNext();)
			{
				overlapping = (MiDupFornitura) it.next();
				Logger.log().info(this.getClass().getName(), "Il File ha intervalli temporali che si sovrappongono a caricamenti gia' effettuati! " + overlapping);
			}
			/*
			 * if(true){ return; }
			 */
			if (overlapping != null)
			{
				if (!xmlOriginated)
				{
					Logger.log().error(this.getClass().getName(), "Il File ha intervalli temporali che si sovrappongono a caricamenti gia' effettuati! - " + _currentline);
					throw new MuiInvalidInputDataException("Il File ha intervalli temporali che si sovrappongono a caricamenti gia' effettuati! errore durante la lettura dell'header della fornitura note!");
				}
				else
				{
					Logger.log().error(this.getClass().getName(), "Il File ha intervalli temporali che si sovrappongono a caricamenti gia' effettuati - condizione consentita perche' file originale in xml" + _currentline);
					// recordParser.store();
					handlePartialLoading(session);
				}
			}

			miDupFornitura.setDataCaricamento(new java.util.Date());
			session.saveOrUpdate(miDupFornitura);
			session.flush();
			tx.commit();
			HibernateUtil.closeSession();
			_runningParsers.put(miDupFornitura.getIid(), this);
		}
		catch (IOException iae)
		{
			Logger.log().error(this.getClass().getName(), "errore durante la valutazione del record - " + _currentline, iae);
			errori.add(_currentline);
			throw new MuiException("errore durante la valutazione del record", _currentline, iae);
		}
		catch (MuiInvalidInputDataException t)
		{
			Logger.log().error(this.getClass().getName(), "errore durante la valutazione del record - " + _currentline, t);
			errori.add(_currentline);
			throw t;
		}
		catch (Throwable t)
		{
			Logger.log().error(this.getClass().getName(), "Si è verificato un errore durante la lettura del file!(" + _currentline + ") - " + _currentline, t);
			errori.add(_currentline);
			throw new MuiException("Si è verificato un errore durante la lettura del file!(" + _currentline + ")", _currentline, t);
		}finally{
			if (errori.size()>0) {
				PrintWriter  pw = new PrintWriter (fileAbsolutePath + ".err");
				for (int ii = 0; ii < errori.size(); ii++)
				{
					pw.println(errori.get(ii));
				}
				pw.close();
			}
		}

	}

	private void handlePartialLoading(Session session)
		throws ParseException
	{
		Query query;
		query = session.createQuery("select fornitura from MiDupFornitura as fornitura  where " + " fornitura.dataInizialeDate = :dataInizialeDate AND fornitura.dataFinaleDate = :dataFinaleDate");
		query.setDate("dataInizialeDate", dateParser.parse(miDupFornitura.getDataIniziale()));
		query.setDate("dataFinaleDate", dateParser.parse(miDupFornitura.getDataFinale()));
		query.getQueryString();
		Logger.log().info("query ", query);
		MiDupFornitura partiallyLoaded = null;
		for (Iterator it = query.iterate(); it.hasNext();)
		{
			partiallyLoaded = (MiDupFornitura) it.next();
		}
		if (partiallyLoaded != null)
		{
			Logger.log().info(this.getClass().getName(), "Il File e' parte di un caricamento multiplo " + partiallyLoaded);
			String loadedFiles = partiallyLoaded.getFileLoadedNotParsed();
			String[] loaded = loadedFiles.split(",");
			for (int i = 0; i < loaded.length; i++)
			{
				if (miDupFornitura.getFileLoadedNotParsed().equals(loaded[i]))
				{
					Logger.log().error(this.getClass().getName(), "Il File n." + loaded[i] + "/" + partiallyLoaded.getFileTotNotParsed() + " per il periodo indicato è già stato caricato ! ");
					throw new MuiInvalidInputDataException("Il File n." + loaded[i] + "/" + partiallyLoaded.getFileTotNotParsed() + " per il periodo indicato è già stato caricato ! ");
				}
			}
			partiallyLoaded.setFileLoadedNotParsed(partiallyLoaded.getFileLoadedNotParsed() + "," + miDupFornitura.getFileLoadedNotParsed());
			miDupFornitura = partiallyLoaded;
		}
		else
		{
			Logger.log().error(this.getClass().getName(), "Il File ha intervalli temporali che si sovrappongono a caricamenti gia' effettuati! - " + _currentline);
			throw new MuiInvalidInputDataException("Il File ha intervalli temporali che si sovrappongono a caricamenti gia' effettuati! errore durante la lettura dell'header della fornitura note!");
		}

	}

	public void parseLastLine(String line)
		throws MuiInvalidInputDataException,
		IOException
	{
		MuiFornituraRecordParser recordParser;
		_currentline = line;
		if (_currentline == null || _currentline.trim().length() == 0)
		{
			throw new MuiInvalidInputDataException("Il Formato del File e' errato, il file non termina con il footer della fornitura note!");
		}
		int rt1 = evalRecordType();
		if (rt1 != 9)
		{
			throw new MuiInvalidInputDataException("Il Formato del File e' errato, il file non termina con il footer della fornitura note!");
		}

		try
		{
			recordParser = getRecordParserForRecordType(9);
			recordParser.parse(_currentline);
		}
		catch (Throwable t)
		{
			Logger.log().error("Il Formato del File e' errato ", _currentline, t);
			throw new MuiInvalidInputDataException("Il Formato del File e' errato, errore durante la lettura del footer della fornitura note!");
		}
		MiDupFornituraInfo miDupFornituraInfo = (MiDupFornituraInfo) recordParser.getRecord();
		if (miDupFornitura.getMiDupFornituraInfo() != null)
		{
			Logger.log().info(this.getClass().getName(), "caricamento su stesso periodo, aggiorno fornituraInfo ");
			MiDupFornituraInfo present = miDupFornitura.getMiDupFornituraInfo();
			present.setFabbricati(addAsInt(present.getFabbricati(), miDupFornituraInfo.getFabbricati()));
			present.setImmobiliTrattati(addAsInt(present.getImmobiliTrattati(), miDupFornituraInfo.getImmobiliTrattati()));
			present.setNote(addAsInt(present.getNote(), miDupFornituraInfo.getNote()));
			present.setNoteRegistrate(addAsInt(present.getNoteRegistrate(), miDupFornituraInfo.getNoteRegistrate()));
			present.setNoteScartate(addAsInt(present.getNoteScartate(), miDupFornituraInfo.getNoteScartate()));
			present.setParticelle(addAsInt(present.getParticelle(), miDupFornituraInfo.getParticelle()));
			present.setRecordScritti(addAsInt(present.getRecordScritti(), miDupFornituraInfo.getRecordScritti()));

			miDupFornituraInfo = present;
		}

		// recordParser.store();
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		miDupFornituraInfo.setMiDupFornitura(miDupFornitura);
		session.saveOrUpdate(miDupFornituraInfo);
		session.flush();
		if (isTransactionalImport())
		{
			tx.commit();
		}
		HibernateUtil.closeSession();

	}

	private String addAsInt(String val1, String val2)
	{
		int val1i = val1 != null ? Integer.valueOf(val1) : 0;
		int val2i = val2 != null ? Integer.valueOf(val2) : 0;
		int sum = val1i + val2i;
		return "" + sum;
	}

	public void parseNotes()
	{
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		MuiFornituraRecordParser recordParser;
		MiDupNotaTras miDupNotaTras = null;
		Map<Integer, MiDupSoggetti> soggettiNota = new HashMap<Integer, MiDupSoggetti>();
		List<MiDupFabbricatiIdentifica> fabbricatiIdentificaNota = new ArrayList<MiDupFabbricatiIdentifica>();
		Map<Integer, MiDupFabbricatiInfo> fabbricatiInfoNota = new HashMap<Integer, MiDupFabbricatiInfo>();
		List<MiDupTitolarita> titolaritaNota = new ArrayList<MiDupTitolarita>();
		Map<Integer, MiDupTerreniInfo> terreniNota = new HashMap<Integer, MiDupTerreniInfo>();
		List<MiDupIndirizziSog> indirizziNota = new ArrayList<MiDupIndirizziSog>();
		boolean skipNota = false;
		List<String> errori = new ArrayList<String>();
		try
		{
			while ((_currentline = lnr.readLine()) != null)
			{
				Logger.log().info(this.getClass().getName(), "parsing record \"" + _currentline + "\"");
				try
				{
					long id = getIdNota();
					int rt = evalRecordType();
					recordParser = getRecordParserForRecordType(rt);
					if (recordParser != null)
					{
						recordParser.setIdNota(id);
						recordParser.parse(_currentline);
						setPojoProperty(recordParser.getRecord(), "miDupFornitura", miDupFornitura);
						if (rt == NOTA_RECORD_TYPE)
						{
							if (skipNota)
							{
								Logger.log().info(this.getClass().getName(), "skip 1 record");
								skipNota = false;
							}
							else
							{
								Logger.log().info(this.getClass().getName(), "committo 1 nota");
							}
							_rowCount++;
							if (!isTransactionalImport())
							{
								tx.commit();
							}
							commitaFilgiNota(miDupNotaTras, indirizziNota, soggettiNota, fabbricatiIdentificaNota, fabbricatiInfoNota, titolaritaNota, terreniNota);
							if (!isTransactionalImport())
							{
								tx = session.beginTransaction();
							}							
							miDupNotaTras = (MiDupNotaTras) recordParser.getRecord();
							skipNota = !recordParser.check(miDupNotaTras);
							if (!skipNota)
							{
								session.save(miDupNotaTras);
							}
							soggettiNota.clear();
							fabbricatiIdentificaNota.clear();
							fabbricatiInfoNota.clear();
							titolaritaNota.clear();
							terreniNota.clear();
							indirizziNota.clear();
						}
						else
						{
							recordParser.setMiDupNotaTras(miDupNotaTras);
							if (rt == SOGGETTO_RECORD_TYPE)
							{
								parseSoggetto(recordParser, soggettiNota);
							}
							else if (rt == IMMOBILEINFO_RECORD_TYPE)
							{
								parseFabbricatoInfo(recordParser, fabbricatiInfoNota);
							}
							else if (rt == IMMOBILEIDENTIFICA_RECORD_TYPE)
							{
								parseFabbricatoIdentifica(recordParser, fabbricatiIdentificaNota);
							}
							else if (rt == TITOLARITA_RECORD_TYPE)
							{
								parseTitolarita(recordParser, titolaritaNota);
							}
							else if (rt == TERRENO_RECORD_TYPE)
							{
								parseTerreno(recordParser, terreniNota);
							}
							else if (rt == INDIRIZZO_RECORD_TYPE)
							{
								parseIndirizzo(recordParser, indirizziNota);
							}
							else if (rt == FORNITURA_INFO_RECORD_TYPE)
							{
								// parseFornituraInfo(recordParser);
							}

							setPojoProperty(recordParser.getRecord(), "miDupNotaTras", miDupNotaTras);
							if (!skipNota)
							{
								session.save(recordParser.getRecord());
							}
						}

						// Logger.log().info(
						// "inserito 1 record",
						// p.dumpPojo( p.getRecord()));
					}

				}
				catch (Throwable t)
				{
					Logger.log().error(this.getClass().getName(), "errore durante la valutazione del record - " + _currentline, t);
					errori.add(_currentline);
					continue;
				}finally{
					if (errori.size()>0) {
						PrintWriter  pw = new PrintWriter (fileAbsolutePath +".err");
						for (int ii = 0; ii < errori.size(); ii++)
						{
							pw.println(errori.get(ii));
						}
						pw.close();
					}
				}
			}
			//dan fattorizato commitaFilgiNota per potere agganciare qui l'ultimo record
			commitaFilgiNota(miDupNotaTras, indirizziNota, soggettiNota, fabbricatiIdentificaNota, fabbricatiInfoNota, titolaritaNota, terreniNota);
			tx.commit();
		}
		catch (HibernateException e)
		{
			try
			{
				Logger.log().error(this.getClass().getName(), "rollback caricamento per eccezione Hibernate - " + e, e);
				tx.rollback();
			}
			catch (HibernateException e1)
			{
			}
			try
			{
				tx = session.beginTransaction();
				miDupFornitura.deleteFully(session);
				tx.commit();
			}
			catch (Throwable ex)
			{
				Logger.log().error(this.getClass().getName(), "errore durante l'annullamento automatico del caricamento della fornitura", e);
			}
			try
			{
				HibernateUtil.closeSession();
			}
			catch (HibernateException eh)
			{
				// TODO Auto-generated catch block
			}
		}
		catch (IOException e)
		{
			Logger.log().error(this.getClass().getName(), "rollback caricamento per eccezione I/O - " + e, e);
			try
			{
				tx.rollback();
			}
			catch (HibernateException e1)
			{
			}
			try
			{
				tx = session.beginTransaction();
				miDupFornitura.deleteFully(session);
				tx.commit();
			}
			catch (Throwable ex)
			{
				Logger.log().error(this.getClass().getName(), "errore durante l'annullamento automatico del caricamento della fornitura", e);
			}
			try
			{
				HibernateUtil.closeSession();
			}
			catch (HibernateException eh)
			{
				// TODO Auto-generated catch block
			}
		}
		catch (Throwable e)
		{
			try
			{
				Logger.log().error(this.getClass().getName(), "rollback caricamento per eccezione - " + e, e);
				tx.rollback();
			}
			catch (HibernateException e1)
			{
			}
			try
			{
				tx = session.beginTransaction();
				miDupFornitura.deleteFully(session);
				tx.commit();
			}
			catch (Throwable ex)
			{
				Logger.log().error(this.getClass().getName(), "errore durante l'annullamento automatico del caricamento della fornitura", e);
			}
			try
			{
				HibernateUtil.closeSession();
			}
			catch (HibernateException eh)
			{
				// TODO Auto-generated catch block
			}
		}
		finally
		{
			_runningParsers.remove(miDupFornitura.getIid());
		}
	}

	private void commitaFilgiNota(MiDupNotaTras miDupNotaTras, 
			List<MiDupIndirizziSog> indirizziNota, 
			Map<Integer, MiDupSoggetti> soggettiNota, 
			List<MiDupFabbricatiIdentifica> fabbricatiIdentificaNota, 
			Map<Integer, MiDupFabbricatiInfo> fabbricatiInfoNota, 
			List<MiDupTitolarita> titolaritaNota, 
			Map<Integer, MiDupTerreniInfo> terreniNota)
	throws Exception
	{
		Thread.yield();
		// forzatura: deve essere il primo di un blocco
		if (miDupNotaTras != null)
		{
			for (Iterator iter = indirizziNota.iterator(); iter.hasNext();)
			{
				MiDupIndirizziSog element = (MiDupIndirizziSog) iter.next();
				try
				{
					element.setMiDupSoggetti(soggettiNota.get(Integer.valueOf(element.getIdSoggettoNota())));
				}
				catch (NumberFormatException nfe)
				{
				}
			}
			for (Iterator iter = fabbricatiIdentificaNota.iterator(); iter.hasNext();)
			{
				MiDupFabbricatiIdentifica element = (MiDupFabbricatiIdentifica) iter.next();
				try
				{
					element.setMiDupFabbricatiInfo(fabbricatiInfoNota.get(Integer.valueOf(element.getIdImmobile())));
				}
				catch (NumberFormatException nfe)
				{
				}
			}
			for (Iterator iter = titolaritaNota.iterator(); iter.hasNext();)
			{
				MiDupTitolarita element = (MiDupTitolarita) iter.next();
				try
				{
					element.setMiDupFabbricatiInfo(fabbricatiInfoNota.get(Integer.valueOf(element.getIdImmobile())));
				}
				catch (NumberFormatException nfe)
				{
				}
				try
				{
					element.setMiDupSoggetti(soggettiNota.get(Integer.valueOf(element.getIdSoggettoNota())));
				}
				catch (NumberFormatException nfe)
				{
				}
				try
				{
					element.setMiDupTerreniInfo(terreniNota.get(Integer.valueOf(element.getIdImmobile())));
				}
				catch (NumberFormatException nfe)
				{
				}
			}

		}
	}
	protected void parseIndirizzo(MuiFornituraRecordParser recordParser, List<MiDupIndirizziSog> indirizziNota)
	{
		MiDupIndirizziSog indirizzo = (MiDupIndirizziSog) recordParser.getRecord();
		if (recordParser.check(indirizzo))
		{
			indirizziNota.add(indirizzo);
		}
	}

	private void parseTerreno(MuiFornituraRecordParser recordParser, Map<Integer, MiDupTerreniInfo> terreniNota)
	{
		MiDupTerreniInfo terreniInfo = (MiDupTerreniInfo) recordParser.getRecord();
		if (recordParser.check(terreniInfo))
		{
			try
			{
				terreniNota.put(Integer.valueOf(terreniInfo.getIdImmobile()), terreniInfo);
			}
			catch (NumberFormatException nfe)
			{

			}
		}
	}

	protected void parseTitolarita(MuiFornituraRecordParser recordParser, List<MiDupTitolarita> titolaritaNota)
	{
		MiDupTitolarita titolarita = (MiDupTitolarita) recordParser.getRecord();
		if (recordParser.check(titolarita))
		{
			titolaritaNota.add(titolarita);
		}
	}

	protected void parseFabbricatoIdentifica(MuiFornituraRecordParser recordParser, List<MiDupFabbricatiIdentifica> fabbricatiIdentificaNota)
	{
		MiDupFabbricatiIdentifica fabbricatoIdentifica = (MiDupFabbricatiIdentifica) recordParser.getRecord();
		if (recordParser.check(fabbricatoIdentifica))
		{
			fabbricatiIdentificaNota.add(fabbricatoIdentifica);
		}
	}

	protected void parseFabbricatoInfo(MuiFornituraRecordParser recordParser, Map<Integer, MiDupFabbricatiInfo> fabbricatiInfoNota)
	{
		MiDupFabbricatiInfo fabbricatoInfo = (MiDupFabbricatiInfo) recordParser.getRecord();
		if (recordParser.check(fabbricatoInfo))
		{
			try
			{
				fabbricatiInfoNota.put(Integer.valueOf(fabbricatoInfo.getIdImmobile()), fabbricatoInfo);
			}
			catch (NumberFormatException nfe)
			{

			}
		}
	}

	protected void parseSoggetto(MuiFornituraRecordParser recordParser, Map<Integer, MiDupSoggetti> soggettiNota)
	{
		MiDupSoggetti soggetto = (MiDupSoggetti) recordParser.getRecord();
		if (recordParser.check(soggetto))
		{
			try
			{
				soggettiNota.put(Integer.valueOf(soggetto.getIdSoggettoNota()), soggetto);
			}
			catch (NumberFormatException nfe)
			{

			}
		}
	}

	private MuiFornituraRecordParser getRecordParserForRecordType(int rt)
	{
		MuiFornituraRecordParser recordParser = _parsers.get(rt);
		if (recordParser == null)
		{
			try
			{
				recordParser = (MuiFornituraRecordParser) Class.forName("it.webred.mui.input.parsers.MuiFornituraRecordTipo" + rt + "Parser").newInstance();
				_parsers.put(rt, recordParser);
				MuiFornituraRecordChecker recordChecker = (MuiFornituraRecordChecker) Class.forName("it.webred.mui.input.parsers.MuiFornituraRecordTipo" + rt + "Checker").newInstance();
				recordParser.setChecker(recordChecker);
			}
			catch (ClassNotFoundException cnfe)
			{

			}
			catch (IllegalAccessException iae)
			{

			}
			catch (java.lang.InstantiationException iae)
			{

			}
		}
		if (recordParser != null)
		{
			recordParser.reset();
		}
		return recordParser;
	}

	private Long getIdNota()
	{
		Long res;
		RowField rf = getNextField();
		try
		{
			res = Long.parseLong(rf.field);
		}
		catch (NumberFormatException e)
		{
			Logger.log().error(this.getClass().getName(), "errore durante la valutazione idNota del record = \"" + _currentline + "\" idNota=\"" + rf.field + "\"", e);
			throw e;
		}
		_currentline = rf.remaining;
		return res;
	}

	private int evalRecordType()
	{
		int rt;
		RowField rf = getNextField();
		rt = Integer.parseInt(rf.field);
		_currentline = rf.remaining;
		return rt;
	}

	private RowField getNextField()
	{
		return getNextField(_currentline, _rowField);
	}

	public static RowField getNextField(String sline)
	{
		RowField rf = new RowField();
		return getNextField(sline, rf);
	}

	public static RowField getNextField(String sline, char delimiter)
	{
		RowField rf = new RowField();
		return getNextField(sline, rf, delimiter);
	}

	public static RowField getNextField(String sline, RowField rf)
	{
		char delimiter = '|';
		return getNextField(sline, rf, delimiter);
	}

	public static RowField getNextField(String sline, RowField rf, char delimiter)
	{
		// If Added by MaX 17/12/2007 - perché in alcuni casi si arriva qui con
		// sline == NULL e andava in errore
		if (sline == null)
		{
			return null;
		}
		rf.pos = sline.indexOf(delimiter);
		if (rf.pos == -1)
		{
			return null;
		}
		else
		{
			rf.field = sline.substring(0, rf.pos);
			rf.remaining = sline.substring(rf.pos + 1);
			return rf;
		}
	}

	public InputStream getInput()
	{
		return _input;
	}

	public void setInput(InputStream input)
	{
		this._input = input;
	}

	public static void setPojoProperty(Object pojo, String fieldName, Object fieldValue)
	{
		Class pojoClass = pojo.getClass();
		setPojoProperty(pojo, fieldName, fieldValue, pojoClass);
	}

	public static void setPojoProperty(Object pojo, String fieldName, Object fieldValue, Class pojoClass)
	{
		String fieldSetterName = "set" + fieldName.toUpperCase().charAt(0) + (fieldName.length() > 1 ? fieldName.substring(1) : "");
		try
		{
			Method method = getPojoMethod(fieldValue, fieldSetterName, pojoClass);
			method.invoke(pojo, fieldValue);
		}
		catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Method getPojoMethod(Object fieldValue, String methodName, Class pojoClass)
		throws NoSuchMethodException
	{
		Map fieldMethodMap = _classMethods.get(pojoClass);
		if (fieldMethodMap == null)
		{
			fieldMethodMap = new HashMap();
			_classMethods.put(pojoClass, fieldMethodMap);
		}
		Method method = (Method) fieldMethodMap.get(methodName);
		if (method == null)
		{
			// TODO:chiavardata da togliere
			method = pojoClass.getMethod(methodName, (fieldValue != null ? fieldValue.getClass() : String.class));
			fieldMethodMap.put(methodName, method);
		}
		return method;
	}

	public boolean isTransactionalImport()
	{
		return _transactionalImport;
	}

	public void setTransactionalImport(boolean transactionalImport)
	{
		this._transactionalImport = transactionalImport;
	}

	public int getRowCount()
	{
		return _rowCount;
	}

	public static Map<Long, MuiFornituraParser> getRunningParsers()
	{
		return _runningParsers;
	}

	public String getLastLine(java.io.File file)
		throws IOException
	{
		StringBuffer res = new StringBuffer();
		int maxback = 5000;
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		long pos = raf.length() - 2;
		raf.seek(pos);
		byte ch = 0;
		;
		List buf = new ArrayList();
		while ((ch = raf.readByte()) != '\n' && ((raf.length() - pos) < maxback))
		{
			buf.add(0, (char) ch);
			pos--;
			raf.seek(pos);
		}
		Iterator iter = buf.iterator();
		while (iter.hasNext())
		{
			res.append((Character) iter.next());

		}
		
		raf.close();
		return res.toString();

	}

	public int getNFileCount()
	{
		return nFileCount;
	}

	public void setNFileCount(int fileCount)
	{
		nFileCount = fileCount;
	}

	public int getNFileTot()
	{
		return nFileTot;
	}

	public void setNFileTot(int fileTot)
	{
		nFileTot = fileTot;
	}

	public void setFileAbsolutePath(String fileAbsolutePath) {
		this.fileAbsolutePath = fileAbsolutePath;
	}
	
}
