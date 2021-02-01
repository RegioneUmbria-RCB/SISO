package it.webred.rulengine.brick.loadDwh.load.docfa;


import it.webred.rulengine.brick.loadDwh.load.docfa.dto.DocfaPack;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

/**
 * Classe che estende Caricatore e si occupa del caricamento di dati Docfa
 * 
 * @author Dan
 * @version $Revision: 1.2 $ $Date: 2008/03/27 08:41:22 $
 */
public class CaricatoreDocfa extends Caricatore
{

	private static final Logger	log	= Logger.getLogger(CaricatoreDocfa.class.getName());
	private String	suffissoFile	= ".zip";

	public CaricatoreDocfa(String configuration)
	{
		super(configuration);
		log.debug("new CaricatoreDocfa");
	}

	private String[] cercaFileDaElaborare(String percorsoFiles)
		throws Exception
	{

		try
		{
			String cartellaDati = percorsoFiles.substring(0, percorsoFiles.lastIndexOf("/"));
			File filesDati = new File(cartellaDati);
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name)
				{
					return name.toLowerCase().endsWith(suffissoFile.toLowerCase());
				}
			};
			return filesDati.list(filter);
		}
		catch (Exception e)
		{
			log.error("Errore nel metodo cercaFileDaElaborare: ", e);
			throw e;

		}
	}

	/**
	 * Esegue il caricamento dei dati da un File di testo richiamando leggiFileLunghezzaFissaMultiplePrep
	 * 
	 * 
	 * 
	 * @param tracciatoxml
	 *            Il tracciato di configurazione
	 * @param nomeFileConf
	 *            Il nome del file di configurazione
	 * @param processID
	 *            ID del processo
	 * @throws Exception
	 */
	public synchronized int executeCaricamentoSpec(TracciatoXml tracciatoxml, String nomeFileConf, String processID, Long dataCaricamento, DocfaEnv env)
		throws Exception
	{

		Connection con = env.getConn();
		//Statement stat = null;
		
		try		{
			TracciatoXml docTracciatoXml = controllaConfigurazioneDoc(tracciatoxml, nomeFileConf);
			TracciatoXml dmTracciatoXml = controllaConfigurazioneDm(tracciatoxml, nomeFileConf);
			TracciatoXml plTracciatoXml = controllaConfigurazionePl(tracciatoxml, nomeFileConf);
			TracciatoXml dcTracciatoXml = controllaConfigurazioneDc(tracciatoxml, nomeFileConf);
			
			//con = apriConnessione();
			//stat = con.createStatement();

			creaTabelle(docTracciatoXml, con, controlloTabelle(docTracciatoXml, con));
			creaTabelle(dmTracciatoXml, con, controlloTabelle(dmTracciatoXml, con));
			creaTabelle(plTracciatoXml, con, controlloTabelle(plTracciatoXml, con));
			creaTabelle(dcTracciatoXml, con, controlloTabelle(dcTracciatoXml, con));
			
			String cartellafiledaCaricare = tracciatoxml.getSourceTab();
			
			String[] fileDaElaborare = cercaFileDaElaborare(tracciatoxml.getSourceTab());
			
			if (fileDaElaborare.length < 1) {
				return RETURN_FINE_NO_FILE;
			}
				
			boolean nessuno_nuovo_doc = true;
			boolean nessuno_nuovo_dc = true;
			boolean nessuno_nuovo_dm = true;
			boolean nessuno_nuovo_pl = true;
			
			new File(cartellafiledaCaricare + "LETTI_DA_CARONTE/").mkdir();
			
			for (int i = 0; i < fileDaElaborare.length; i++)
			{

				if (new File(cartellafiledaCaricare + "LETTI_DA_CARONTE/" + fileDaElaborare[i]).exists())
				{
					log.debug("Scartato file perche già elaborato " + fileDaElaborare[i]);
					new File(cartellafiledaCaricare + fileDaElaborare[i]).delete();
					continue;
				}
				deleteDir(new File(cartellafiledaCaricare + "tmpDocfa/"));
				
				new File(cartellafiledaCaricare + "tmpDocfa/").mkdir();
				log.debug("Caricamento " + fileDaElaborare[i]);
				if (fileDaElaborare[i].toLowerCase().endsWith("_doc.zip") || 
					(fileDaElaborare[i].toLowerCase().indexOf("_doc_") > -1 && fileDaElaborare[i].toLowerCase().endsWith(suffissoFile)))
				{
					UnZip(cartellafiledaCaricare + fileDaElaborare[i], cartellafiledaCaricare + "tmpDocfa/");
					File tmpFolder = new File(cartellafiledaCaricare + "tmpDocfa/");
					String[] fileInCartella = tmpFolder.list();
					String COD_ENTE = null;
					String DATA = null;
					for (int iFo = 0; iFo < fileInCartella.length; iFo++)
					{
						if (fileInCartella[iFo].toLowerCase().endsWith(".lis"))
						{
							// cartella\CODENTE_DATA_DOC.lis es.
							// F205_200601_DOC.LIS (19 caratteri)
							//ma per integrative: F205_INT_200601_DOC.LIS (23 caratteri)
							String esNome = "F205_200601_DOC.LIS";
							String nomeFile = getNomeFileIntegrative(fileInCartella[iFo], esNome.length());
							COD_ENTE = nomeFile.substring(0, 4);
							DATA = nomeFile.substring(5, 11) + "01";
							break;
						}
					}
					if (COD_ENTE == null || DATA == null)
						throw new Exception("Non è stato possibile leggere il file di configurazione .LIS");
					long start = new java.util.Date().getTime();
					long contarecord = 0;
					log.debug("Inizio scrittura su db dati da ds " + fileDaElaborare[i]);
					for (int iFo = 0; iFo < fileInCartella.length; iFo++)
					{
						if (fileInCartella[iFo].toLowerCase().endsWith(".dat"))
						{
							BufferedReader in = new BufferedReader(new FileReader(cartellafiledaCaricare + "tmpDocfa/" + fileInCartella[iFo]));
							contarecord += leggiFileDocfaDocMultiplePrep(in, docTracciatoXml, con, fileInCartella[iFo].substring(0, fileInCartella[iFo].length() - 4), COD_ENTE, DATA);
							in.close();
							nessuno_nuovo_doc = false;
						}
					}
					log.debug("Finito inserimento Docfa " + fileDaElaborare[i] + "; " + contarecord + " record in:" + (new java.util.Date().getTime() - start));
				}
				else if (fileDaElaborare[i].toLowerCase().endsWith("_dm_pl.zip")
						|| (fileDaElaborare[i].toLowerCase().indexOf("_dm_pl_") > -1 && fileDaElaborare[i].toLowerCase().endsWith(suffissoFile))
						|| fileDaElaborare[i].toLowerCase().endsWith("_dc_pl.zip")
						|| (fileDaElaborare[i].toLowerCase().indexOf("_dc_pl_") > -1 && fileDaElaborare[i].toLowerCase().endsWith(suffissoFile))
						|| fileDaElaborare[i].toLowerCase().endsWith("_dc.zip")
						|| (fileDaElaborare[i].toLowerCase().indexOf("_dc_") > -1 && fileDaElaborare[i].toLowerCase().endsWith(suffissoFile)))
				{
					UnZip(cartellafiledaCaricare + fileDaElaborare[i], cartellafiledaCaricare + "tmpDocfa/");
					File tmpFolder = new File(cartellafiledaCaricare + "tmpDocfa/");
					String[] fileInCartella = tmpFolder.list();
					for (int iFo = 0; iFo < fileInCartella.length; iFo++)
					{
						if (fileInCartella[iFo].toLowerCase().endsWith("_dm.dat") || fileInCartella[iFo].toLowerCase().endsWith("_dc.dat") || fileInCartella[iFo].toLowerCase().endsWith("_sc.dat"))
						{
							// cartella\CODENTE_DATA_DM.DAT es.
							// F205_200601_DM.DAT (18 caratteri)
							//ma per integrative: F205_INT_200601_DM.DAT (22 caratteri)
							String esNome = "F205_200601_DM.DAT";
							String nomeFile = getNomeFileIntegrative(fileInCartella[iFo], esNome.length());
							// String COD_ENTE = nomeFile.substring(0, 4);
							String DATA = nomeFile.substring(5, 11) + "01";
							BufferedReader in = new BufferedReader(new FileReader(cartellafiledaCaricare + "tmpDocfa/" + fileInCartella[iFo]));
							long start = new java.util.Date().getTime();
							long contarecord = 0;
							
							//gestione letture ed insert nelle tab T1 e T2
							if (fileInCartella[iFo].toLowerCase().endsWith("_dm.dat"))
							{
								contarecord = leggiFileDocfaDmDcMultiplePrep(in, dmTracciatoXml, con, DATA);
								nessuno_nuovo_dm = false;
							}
							else if (fileInCartella[iFo].toLowerCase().endsWith("_dc.dat"))
							{
								contarecord = leggiFileDocfaDmDcMultiplePrep(in, dcTracciatoXml, con, DATA);
								nessuno_nuovo_dc = false;
							}
							else if (fileInCartella[iFo].toLowerCase().endsWith("_sc.dat"))
							{
								contarecord = leggiFileDocfaDmDcMultiplePrep(in, plTracciatoXml, con, DATA);
								nessuno_nuovo_pl = false;
							}
							in.close();

							log.debug("Finito inserimento Docfa " + fileDaElaborare[i] + "; " + contarecord + " record in:" + (new java.util.Date().getTime() - start));
							//break;
						}
					}
				}

				new File(cartellafiledaCaricare + fileDaElaborare[i]).renameTo(new File(cartellafiledaCaricare + "LETTI_DA_CARONTE/" + fileDaElaborare[i]));
			}
			
			deleteDir(new File(cartellafiledaCaricare + "tmpDocfa/"));
			
			if (nessuno_nuovo_doc && nessuno_nuovo_dm && nessuno_nuovo_dc && nessuno_nuovo_pl)
				return RETURN_FINE_NO_FILE;
			
			log.debug("Finito scrittura file in db");
			
			/*TODO: differenze */
			
			if (!nessuno_nuovo_doc) {
				DocfaPack dp = new DocfaPack(con, "DOC",docTracciatoXml.getNomeTabella(),nuovoNomeFile(nomeFileConf, "DOC_", true),processID, dataCaricamento,env.pkListQuery);
				new ExportToTable().analizzaDifferenze(dp);
			}
			
			if (!nessuno_nuovo_dm){
				DocfaPack dp = new DocfaPack(con, "DM",dmTracciatoXml.getNomeTabella(), nuovoNomeFile(nomeFileConf, "DM_", true), processID, dataCaricamento,env.pkListQuery);
				new ExportToTable().analizzaDifferenze(dp);
			}
			
			if (!nessuno_nuovo_pl){
				DocfaPack dp = new DocfaPack(con, "PL",plTracciatoXml.getNomeTabella(), nuovoNomeFile(nomeFileConf, "PL_", true), processID, dataCaricamento,env.pkListQuery);
				new ExportToTable().analizzaDifferenze(dp);
			}
			
			if (!nessuno_nuovo_dc) {
				DocfaPack dp = new DocfaPack(con, "DC",dcTracciatoXml.getNomeTabella(), nuovoNomeFile(nomeFileConf, "DC_", true), processID, dataCaricamento,env.pkListQuery);
				new ExportToTable().analizzaDifferenze(dp);
			}
			
			log.debug("Finito analiz diff");
			log.debug("Caricamento File terminato senza errori");
			
			return RETURN_CONTINUE;

		}catch (Exception e)	{
			throw e;
		}finally		{
			con.commit();
		}

	}
	
	private String getNomeFileIntegrative(String nomeFile, int len) {		
		if (nomeFile != null) {
			String infissoInt = "INT_";
			if (nomeFile.lastIndexOf(infissoInt) == nomeFile.length() - (len + infissoInt.length() - 5)) {
				nomeFile = nomeFile.substring(0, nomeFile.lastIndexOf(infissoInt)) +
							nomeFile.substring(nomeFile.lastIndexOf(infissoInt) + infissoInt.length());
			}
			nomeFile = nomeFile.substring(nomeFile.length() - len);
		}
		return nomeFile;
	}

	/**
	 * Legge i dati Docfa
	 * 
	 * @param fileIn
	 *            BufferedReader che contiene il file da leggere
	 * @param tracciato
	 *            Il tracciato di configurazione
	 * @param con
	 *            Una connessione al DB MySql
	 * @throws Exception
	 * 
	 */
	private long leggiFileDocfaDocMultiplePrep(BufferedReader fileIn, TracciatoXml docTracciatoXml, Connection con, String PROTOCOLLO, String COD_ENTE, String DATA)
		throws Exception
	{

		String currentLine = "";
		long contarecord = 0;

		try
		{
			// Imposto il numero di insert in un PreparedStatement
			int mxInsertSize = 100;
			List l = new ArrayList();
			while ((currentLine = fileIn.readLine()) != null)
			{
				contarecord++;
				l.add(currentLine);
				if (mxInsertSize == l.size())	{
					String sql = this.getPrpDiIniziale(docTracciatoXml, l, con);
					this.getPrpDoc(con,docTracciatoXml, l, sql, COD_ENTE, DATA, PROTOCOLLO);
					l.clear();
				}
			}
			if (l.size() > 0)
				this.getPrpFinaleDoc(docTracciatoXml, l, con, COD_ENTE, DATA, PROTOCOLLO);
		}
		catch (Exception e)		{
			log.error("Errore nel metodo leggiFileDocfaDocMultiplePrep: ", e);
			throw e;
		}
		
		return contarecord;
	}

	private long leggiFileDocfaDmDcMultiplePrep(BufferedReader fileIn, TracciatoXml dmdcTracciatoXml, Connection con, String DATA)
		throws Exception
	{
		String currentLine = "";
		long contarecord = 0;
		
		try	{
			// Imposto il numero di insert in un PreparedStatement
			int mxInsertSize = 100;
			List l = new ArrayList();
			while ((currentLine = fileIn.readLine()) != null)	{
				contarecord++;
				l.add(currentLine);
				
				if (mxInsertSize == l.size())	{
					String sql = this.getPrpDiIniziale(dmdcTracciatoXml, l, con);
					this.getPrpDcDm(con,dmdcTracciatoXml, l, sql, DATA);
					l.clear();
				}
			}
			
			if (l.size() > 0)
				this.getPrpFinaleDcDm(dmdcTracciatoXml, l, con, DATA);
			
		}catch (Exception e){
			log.error("Errore nel metodo leggiFileDocfaDmMultiplePrep: ", e);
			throw e;
		}
		
		return contarecord;
	}
	

	/**
	 * Setta i parametri un PreparedStatement che viene passato
	 * 
	 * @param tracciato
	 *            Il tracciato di configurazione
	 * @param listalinee
	 *            Lista delle righe lette da un file di testo
	 * @param ps
	 *            Il PreparedStatement
	 * @param codice_fornitura
	 * @throws Exception
	 */
	private void getPrpDoc(Connection con,TracciatoXml tracciato, List listalinee, String sql, String COD_ENTE, String DATA, String PROTOCOLLO)
		throws Exception	{
		
		try {
			
			int idx = 0;
			for (int kk = 0; kk < listalinee.size(); kk++)
			{
				PreparedStatement ps = con.prepareStatement(sql);
				
				String riga = listalinee.get(kk).toString();
				String hash = COD_ENTE + DATA + PROTOCOLLO;
				
				ps.setString(++idx, COD_ENTE);
				ps.setString(++idx, DATA);
				ps.setString(++idx, PROTOCOLLO);
				
				String TIPO_RECORD = riga.substring(7, 8);
				ps.setString(++idx, TIPO_RECORD);	
				hash += TIPO_RECORD;
				
				ps.setString(++idx, riga);
				hash += riga;
				
				ps.setString(++idx, super.getCtrHash(hash));
				
				ps.execute();
				ps.close();
				idx = 0;
			}
			
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
			throw e;
		}finally {
			con.commit();
		}
	}

	private void getPrpDcDm(Connection con,TracciatoXml tracciato, List listalinee, String ps, String DATA)
		throws Exception	{
		
		try {
			int idx = 0;
			
			for (int kk = 0; kk < listalinee.size(); kk++)
			{
				PreparedStatement pstmt = con.prepareStatement(ps);
				
				String currentLine = listalinee.get(kk).toString();
				//log.debug(currentLine);
				String col[] = new String[tracciato.count()];
				System.arraycopy(currentLine.split(tracciato.getSeparatore()), 0, col, 0, currentLine.split(tracciato.getSeparatore()).length);
				String hash = "";
				for (int i = 0; i < tracciato.count(); i++)
				{
					//log.debug(i);
					String valore = null;
					if (((Colonna) tracciato.getListaColonne().get(i)).getPos() == -1) {
						valore = DATA;
					}
					else {
						valore = col[((Colonna) tracciato.getListaColonne().get(i)).getPos()];
					}
					
					if(valore != null && !valore.equals("")) {
						pstmt.setString(++idx, valore);	
						hash += valore;
					}
					else {
						pstmt.setNull(++idx, Types.VARCHAR);
					}
					
				}
				
				pstmt.setString(++idx, super.getCtrHash(hash));
				
				pstmt.execute();
				pstmt.close();
				idx = 0;
			}
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
			throw e;
		}finally {
			con.commit();
		}

	}

	/**
	 * Crea un PreparedStatement
	 * 
	 * @param tracciato
	 *            Il tracciato di configurazione
	 * @param listalinee
	 *            Lista delle righe lette da un file di testo
	 * @param con
	 *            Una connessione al DB MySql
	 * @return Il PreparedStatement creato
	 * @throws Exception
	 */
	private String getPrpDiIniziale(TracciatoXml tracciato, List listalinee, Connection con)
		throws Exception {
		
		StringBuffer s = new StringBuffer();
		s.append("INSERT INTO ");
		s.append(tracciato.getNomeTabella() + "_T1");
		s.append(" VALUES ");
		
		/*
		for (int k = 0; k < listalinee.size(); k++)
		{
			s.append(" (");
			for (int i = 0; i < tracciato.count(); i++)
			{
				s.append("?,");
			}
			s.append("MD5(?)),");
		}
		*/
		
		s.append(" (");
		for (int i = 0; i < tracciato.count(); i++)		{
			s.append("?,");
		}
		s.append("?)");
		
		String ss = s.toString();
		//ss = ss.substring(0, ss.length() - 1) + ";";
		log.debug(ss);

		return ss;
	}

	/**
	 * Setta i parametri un PreparedStatement che viene passato; viene chiamato quando il numero di righe da inserire è inferiore a mxInsertSize del metodo leggiFileLunghezzaFissaMultiplePrep
	 * 
	 * @param tracciato
	 *            Il tracciato di configurazione
	 * @param listalinee
	 *            Lista delle righe lette da un file di testo
	 * @param con
	 *            La connessione al DB
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void getPrpFinaleDoc(TracciatoXml tracciato, List listalinee, Connection con, String COD_ENTE, String DATA, String PROTOCOLLO)
		throws Exception
	{
		PreparedStatement ps = null;
		try
		{
			for (int k = 0; k < listalinee.size(); k++) {
				String riga = listalinee.get(k).toString();
				int idx = 0;
				
				StringBuffer s = new StringBuffer();
				s.append("INSERT INTO ");
				s.append(tracciato.getNomeTabella() + "_T1");
				s.append(" VALUES (");
				
				for (int i = 0; i < tracciato.count(); i++)
				{
					s.append("?,");
				}
				s.append("?)");
				
				
				String ss = s.toString();
				//log.debug(ss);

				ps = con.prepareStatement(ss);
				ps.setString(++idx, COD_ENTE);
				ps.setString(++idx, DATA);
				ps.setString(++idx, PROTOCOLLO);
				
				String hash = COD_ENTE + DATA + PROTOCOLLO;

				String TIPO_RECORD = riga.substring(7, 8);
				ps.setString(++idx, TIPO_RECORD);	
				hash += TIPO_RECORD;
				
				ps.setString(++idx, riga);
				hash += riga;

				ps.setString(++idx, super.getCtrHash(hash));
				
				ps.execute();
				ps.close();
			}
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
			throw e;
		}finally	{
			con.commit();
		}
	}

	private void getPrpFinaleDcDm(TracciatoXml tracciato, List listalinee, Connection con, String DATA)
		throws Exception	{
		
		PreparedStatement ps = null;
		
		try	{
			
			for (int k = 0; k < listalinee.size(); k++)
			{
				StringBuffer s = new StringBuffer();
				s.append("INSERT INTO ");
				s.append(tracciato.getNomeTabella() + "_T1");
				s.append(" VALUES (");
				
				for (int i = 0; i < tracciato.count(); i++)	{
					s.append("?,");
				}
				s.append("?)");
				
				String ss = s.toString();
				//log.debug(ss);
				ps = con.prepareStatement(ss);
				
				String currentLine = listalinee.get(k).toString();
				String col[] = new String[tracciato.count()];
				System.arraycopy(currentLine.split(tracciato.getSeparatore()), 0, col, 0, currentLine.split(tracciato.getSeparatore()).length);
				
				//calcolo hash
				String hash = "";
				int idx = 0;
				for (int i = 0; i < tracciato.count(); i++)
				{
					String valore;
					if (((Colonna) tracciato.getListaColonne().get(i)).getPos() == -1)
						valore = DATA;
					else
						valore = col[((Colonna) tracciato.getListaColonne().get(i)).getPos()];
					
					if(valore != null && !valore.equals("")) {
						ps.setString(++idx, valore);	
						hash += valore;
					}
					else {
						ps.setNull(++idx, Types.VARCHAR);
					}
				}
				
				ps.setString(++idx, super.getCtrHash(hash));		
				ps.execute();
				ps.close();
			}
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
			throw e;
		}finally	{
			con.commit();
		}
	}

	
	
	/**
	 * Esegue il test di lettura del File di testo richiamando uno dei due metodi leggiFileLunghezzaFissaTest leggiFileConSeparatoriTest , a seconda che si tratti di file a lunghezza fissa o con separatore
	 * 
	 * @throws Exception
	 */
	public void testDataSource(TracciatoXml tracciatoxml)
		throws Exception
	{

		String[] fileDaElaborare = cercaFileDaElaborare(tracciatoxml.getSourceTab());
		if (fileDaElaborare.length < 1)
		{
			throw new Exception("Non è stato trovato alcun file dati sul quale eseguire il test!");
		}

	}

	public String getDefaultDataEstrazione()
	{

		return null;
	}

	@Override
	public Object getRiferimentoDS(TracciatoXml tracciatoxml)
		throws Exception
	{

		return tracciatoxml.getSourceTab();
	}

	@SuppressWarnings("unchecked")
	private TracciatoXml controllaConfigurazioneDoc(TracciatoXml tracciatoXml, String nomeFileConf)
		throws Exception
	{
		TracciatoXml doc = new TracciatoXml(tracciatoXml.getNomeFile(), "DOC_" + tracciatoXml.getNome().toUpperCase(), tracciatoXml.getVersione(), tracciatoXml.getSeparatore(), tracciatoXml.getCaricatore(), tracciatoXml.getDriver(), tracciatoXml.getStringaConnessione(), tracciatoXml.getSourceTab(), new ArrayList(), tracciatoXml.getGestoreDataEstrazione());
		Colonna colD = new Colonna();
		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("COD_ENTE");
		colD.setDimensioneOrig(5);
		doc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("DATE");
		colD.setTipoDest("DATE");
		colD.setFormato("yyyymmdd");
		colD.setNome("DATA");
		colD.setDimensioneOrig(10);
		doc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("PROTOCOLLO");
		colD.setDimensioneOrig(20);
		doc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("TIPO_RECORD");
		colD.setDimensioneOrig(2);
		doc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("RIGA_DETTAGLIO");
		colD.setDimensioneOrig(1000);
		doc.getListaColonne().add(clonaColonna(colD));

		//ScriviTracciato.scriviTracciato(doc, nuovoNomeFile(nomeFileConf, "DOC_", false), false);
		return doc;
	}

	@SuppressWarnings("unchecked")
	private TracciatoXml controllaConfigurazioneDc(TracciatoXml tracciatoXml, String nomeFileConf)
		throws Exception
	{

		TracciatoXml dc = new TracciatoXml(tracciatoXml.getNomeFile(), "DC_" + tracciatoXml.getNome().toUpperCase(), tracciatoXml.getVersione(), tracciatoXml.getSeparatore(), tracciatoXml.getCaricatore(), tracciatoXml.getDriver(), tracciatoXml.getStringaConnessione(), tracciatoXml.getSourceTab(), new ArrayList(), tracciatoXml.getGestoreDataEstrazione());
		Colonna colD = new Colonna();

		colD.setChiave(false);
		colD.setTipo("DATE");
		colD.setTipoDest("DATE");
		colD.setFormato("yyyymmdd");
		colD.setNome("DATA");
		colD.setDimensioneOrig(10);
		colD.setPos(-1);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("CODICE_ENTE");
		colD.setDimensioneOrig(4);
		colD.setPos(0);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("SEZIONE");
		colD.setDimensioneOrig(1);
		colD.setPos(1);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("IDENTIFICATIVO_IMMO");
		colD.setDimensioneOrig(9);
		colD.setPos(2);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("PROTOCOLLO_REG");
		colD.setDimensioneOrig(9);
		colD.setPos(3);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("DATE");
		colD.setTipoDest("DATE");
		colD.setFormato("ddMMyyyy");
		colD.setNome("DATA");
		colD.setDimensioneOrig(10);
		colD.setNome("DATA_REG");
		colD.setPos(4);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("ZONA");
		colD.setDimensioneOrig(3);
		colD.setPos(5);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("CATEGORIA");
		colD.setDimensioneOrig(3);
		colD.setPos(6);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("CLASSE");
		colD.setDimensioneOrig(2);
		colD.setPos(7);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("CONSISTENZA");
		colD.setDimensioneOrig(7);
		colD.setPos(8);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("SUPERFICIE");
		colD.setDimensioneOrig(5);
		colD.setPos(9);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("RENDITA_EURO");
		colD.setDimensioneOrig(18);
		colD.setPos(10);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("PARTITA_SPECIALE");
		colD.setDimensioneOrig(1);
		colD.setPos(11);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("LOTTO");
		colD.setDimensioneOrig(2);
		colD.setPos(12);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("EDIFICIO");
		colD.setDimensioneOrig(2);
		colD.setPos(13);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("SCALA");
		colD.setDimensioneOrig(2);
		colD.setPos(14);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("INTERNO_UNO");
		colD.setDimensioneOrig(3);
		colD.setPos(15);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("INTERNO_DUE");
		colD.setDimensioneOrig(3);
		colD.setPos(16);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("PIANO_UNO");
		colD.setDimensioneOrig(4);
		colD.setPos(17);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("PIANO_DUE");
		colD.setDimensioneOrig(4);
		colD.setPos(18);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("PIANO_TRE");
		colD.setDimensioneOrig(4);
		colD.setPos(19);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("PIANO_QUATRO");
		colD.setDimensioneOrig(4);
		colD.setPos(20);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("SEZIONE_URBANA");
		colD.setDimensioneOrig(4);
		colD.setPos(21);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("FOGLIO");
		colD.setDimensioneOrig(4);
		colD.setPos(22);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("NUMERO");
		colD.setDimensioneOrig(5);
		colD.setPos(23);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("DENOMINATORE");
		colD.setDimensioneOrig(4);
		colD.setPos(24);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("SUBALTERNO");
		colD.setDimensioneOrig(4);
		colD.setPos(25);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("EDIFICABILITA");
		colD.setDimensioneOrig(1);
		colD.setPos(26);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("PRESENZA_GRAFFATI");
		colD.setDimensioneOrig(1);
		colD.setPos(27);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("TOPONIMO");
		colD.setDimensioneOrig(30);
		colD.setPos(28);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("INDIRIZZO");
		colD.setDimensioneOrig(50);
		colD.setPos(29);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("CIVICO_UNO");
		colD.setDimensioneOrig(6);
		colD.setPos(30);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("CIVICO_DUE");
		colD.setDimensioneOrig(6);
		colD.setPos(31);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("CIVICO_TRE");
		colD.setDimensioneOrig(6);
		colD.setPos(32);
		dc.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("ULTERIORI_INDIRIZZI");
		colD.setDimensioneOrig(1);
		colD.setPos(33);
		dc.getListaColonne().add(clonaColonna(colD));

		dc.setSeparatore("[|]");
		//ScriviTracciato.scriviTracciato(dc, nuovoNomeFile(nomeFileConf, "DC_", false), false);
		return dc;
	}

	@SuppressWarnings("unchecked")
	private TracciatoXml controllaConfigurazioneDm(TracciatoXml tracciatoXml, String nomeFileConf)
		throws Exception
	{

		TracciatoXml dm = new TracciatoXml(tracciatoXml.getNomeFile(), "DM_" + tracciatoXml.getNome().toUpperCase(), tracciatoXml.getVersione(), tracciatoXml.getSeparatore(), tracciatoXml.getCaricatore(), tracciatoXml.getDriver(), tracciatoXml.getStringaConnessione(), tracciatoXml.getSourceTab(), new ArrayList(), tracciatoXml.getGestoreDataEstrazione());
		Colonna colD = new Colonna();

		colD.setChiave(false);
		colD.setTipo("DATE");
		colD.setTipoDest("DATE");
		colD.setFormato("yyyymmdd");
		colD.setNome("DATA");
		colD.setDimensioneOrig(10);
		colD.setPos(-1);
		dm.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("COD_ENTE");
		colD.setDimensioneOrig(5);
		colD.setPos(0);
		dm.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("SEZIONE");
		colD.setDimensioneOrig(1);
		colD.setPos(1);
		dm.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("IDENTIFICATIVO_IMMO");
		colD.setDimensioneOrig(9);
		colD.setPos(2);
		dm.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("PROTOCOLLO_REG");
		colD.setDimensioneOrig(9);
		colD.setPos(3);
		dm.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("DATE");
		colD.setTipoDest("DATE");
		colD.setFormato("ddMMyyyy");
		colD.setNome("DATA_REG");
		colD.setDimensioneOrig(10);
		colD.setPos(4);
		dm.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("PROGRESSIVO_POL");
		colD.setDimensioneOrig(4);
		colD.setPos(5);
		dm.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("SUPERFICIE");
		colD.setDimensioneOrig(9);
		colD.setPos(6);
		dm.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("AMBIENTE");
		colD.setDimensioneOrig(1);
		colD.setPos(7);
		dm.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("ALTEZZA");
		colD.setDimensioneOrig(4);
		colD.setPos(8);
		dm.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("ALTEZZA_MAX");
		colD.setDimensioneOrig(4);
		colD.setPos(9);
		dm.getListaColonne().add(clonaColonna(colD));

		dm.setSeparatore("[|]");
		//ScriviTracciato.scriviTracciato(dm, nuovoNomeFile(nomeFileConf, "DM_", false), false);
		return dm;
	}

	@SuppressWarnings("unchecked")
	private TracciatoXml controllaConfigurazionePl(TracciatoXml tracciatoXml, String nomeFileConf)
		throws Exception
	{

		TracciatoXml pl = new TracciatoXml(tracciatoXml.getNomeFile(), "PL_" + tracciatoXml.getNome().toUpperCase(), tracciatoXml.getVersione(), tracciatoXml.getSeparatore(), tracciatoXml.getCaricatore(), tracciatoXml.getDriver(), tracciatoXml.getStringaConnessione(), tracciatoXml.getSourceTab(), new ArrayList(), tracciatoXml.getGestoreDataEstrazione());
		Colonna colD = new Colonna();

		colD.setChiave(false);
		colD.setTipo("DATE");
		colD.setTipoDest("DATE");
		colD.setFormato("yyyymmdd");
		colD.setNome("DATA");
		colD.setDimensioneOrig(10);
		colD.setPos(-1);
		pl.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("COD_ENTE");
		colD.setDimensioneOrig(5);
		colD.setPos(0);
		pl.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("SEZIONE");
		colD.setDimensioneOrig(1);
		colD.setPos(1);
		pl.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("IDENTIFICATIVO_IMMO");
		colD.setDimensioneOrig(9);
		colD.setPos(2);
		pl.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("PROTOCOLLO_REG");
		colD.setDimensioneOrig(9);
		colD.setPos(3);
		pl.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("DATE");
		colD.setTipoDest("DATE");
		colD.setFormato("ddMMyyyy");
		colD.setNome("DATA_REG");
		colD.setDimensioneOrig(10);
		colD.setPos(4);
		pl.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("VARCHAR2");
		colD.setTipoDest("VARCHAR2");
		colD.setFormato("");
		colD.setNome("NOME_PLAN");
		colD.setDimensioneOrig(50);
		colD.setPos(5);
		pl.getListaColonne().add(clonaColonna(colD));
		
		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("PROGRESSIVO");
		colD.setDimensioneOrig(4);
		colD.setPos(6);
		pl.getListaColonne().add(clonaColonna(colD));
		
		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("FORMATO");
		colD.setDimensioneOrig(4);
		colD.setPos(7);
		pl.getListaColonne().add(clonaColonna(colD));

		colD.setChiave(false);
		colD.setTipo("NUMBER");
		colD.setTipoDest("NUMBER");
		colD.setFormato("");
		colD.setNome("SCALA");
		colD.setDimensioneOrig(4);
		colD.setPos(8);
		pl.getListaColonne().add(clonaColonna(colD));

		pl.setSeparatore("[|]");
		//ScriviTracciato.scriviTracciato(pl, nuovoNomeFile(nomeFileConf, "PL_", false), false);
		return pl;
	}

	protected String controllaNomeFile(String nomeFile)
	{

		if (nomeFile.lastIndexOf(".xml") != nomeFile.length() - 4)
			nomeFile += ".xml";

		return nomeFile;
	}

	private String nuovoNomeFile(String nf, String prefisso, boolean completo)
	{
		nf = controllaNomeFile(nf);
		int indicePath = nf.lastIndexOf("/") > nf.lastIndexOf("\\") ? nf.lastIndexOf("/") : nf.lastIndexOf("\\");
		String nomeFile = prefisso + nf.substring(indicePath + 1, nf.length());
		if (completo)
			return nf.substring(0, indicePath + 1) + nomeFile;
		else
			return nomeFile;

	}

	private Colonna clonaColonna(Colonna c)
	{
		Colonna n = new Colonna();
		n.setNome(c.getNome());
		n.setTipo(c.getTipo());
		n.setTipoDest(c.getTipoDest());
		n.setFormato(c.getFormato());
		n.setDimensioneOrig(c.getDimensioneOrig());
		n.setDimensioneDest(c.getDimensioneOrig());
		n.setChiave(c.isChiave());
		n.setPos(c.getPos());
		return n;

	}

	private boolean deleteDir(File dir)
	{
		if (dir.isDirectory())
		{
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++)
			{
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success)
				{
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

	private void UnZip(String filename, String cartellaDest)
		throws Exception
	{
		final int BUFFER = 2048;
		BufferedOutputStream dest = null;
		FileInputStream fis = new FileInputStream(filename);
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null)
		{
			// log.debug("Extracting: " + entry);
			int count;
			byte data[] = new byte[BUFFER];
			// write the files to the disk
			FileOutputStream fos = new FileOutputStream(cartellaDest + entry.getName());
			dest = new BufferedOutputStream(fos, BUFFER);
			while ((count = zis.read(data, 0, BUFFER)) != -1)
			{
				dest.write(data, 0, count);
			}
			dest.flush();
			dest.close();
		}
		zis.close();
		fis.close();
	}

	@Override
	public boolean normalizza(String belfiore) throws RulEngineException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {
			return new Env<DocfaEnv>((DocfaEnv) ei);
	}

	@Override
	public GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni() {
		// TODO Auto-generated method stub
		return null;
	}
}
