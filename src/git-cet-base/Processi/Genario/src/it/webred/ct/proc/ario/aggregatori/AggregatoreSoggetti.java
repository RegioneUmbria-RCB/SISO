package it.webred.ct.proc.ario.aggregatori;



import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.data.model.indice.SitSoggettoUnico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;
//import it.webred.ct.proc.ario.bean.SitSoggettoUnico;



public class AggregatoreSoggetti extends TipoAggregatore {


	private static final Logger	log	= Logger.getLogger(AggregatoreSoggetti.class.getName());
	private static Properties propSoggetti = null;
	private static Properties propCrit = null;
	
	private static ArrayList<Object[]> paramFonteRif;
	


	private long totNumRecAggiornati = 0;
	private long numRecAggiornati = 0;

	private long totNumRecNonAgganciati = 0;
	private long numRecNonAgganciati = 0;

	
	private long totNumRecLetti = 0;


	// questa hashmap viene svuotata ad ogni fonte nel metodo elabora
	HashMap<String,Object> elaborati=  new HashMap<String, Object>(); 
	HashMap<String,Object> elaboratiFonteRif=  new HashMap<String, Object>(); 
	private Connection conn = null;
	QueryRunner run = new QueryRunner();
	
	
	// sono dichiarati qui in quanto vengono condivisi fra i metodi
	private PreparedStatement prsRiesegui = null;
	private ResultSet rsRiesegui = null;

	
	//Costruttore
	public AggregatoreSoggetti(){
		//Caricamento del file di properties aggregatoreSoggetti	
		this.propSoggetti = new Properties();
		this.propCrit = new Properties();
		try {
			InputStream is = this.getClass().getResourceAsStream("/it/webred/ct/proc/ario/aggregatori/aggregatoreSoggetti.properties");
		    this.propSoggetti.load(is);   
		    
		    InputStream isCrit = this.getClass().getResourceAsStream("/it/webred/ct/proc/ario/aggregatori/criteriValutabiliSoggetti.properties");
            this.propCrit.load(isCrit); 
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage(), e);			   
		}
				
	}
	
	public void aggrega(String codEnte, HashParametriConfBean paramConfBean) throws AggregatoreException
	{
		
		try
		{				
				
			log.info("################# INIZIO Elaborazione controllo associabilità Soggetti in UNICO ##############################");
			
			paramFonteRif = (ArrayList<Object[]>) run.query(conn,getProperty("SQL_FONTE_PROGRESSIVO_RIF_SOGG"), new ArrayListHandler());												
			
			/**
			 * CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO				
			 */
			if (paramFonteRif == null)
				log.info("!!!!! ERRORE: FONTE DI RIFERIMENTO SOGGETTI NON TROVATA !!!!! continuo...");
			else{
				log.info("INIZIO CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO SOGGETTI" + paramFonteRif.get(0) );
				this.aggregazioneFonteRiferimento(codEnte, paramConfBean);
				log.info("FINE CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO SOGGETTI");
			}
			/**
			 * CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI
			 */
			log.info("INIZIO CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI SOGGETTI");
			this.aggregazioneAltreFonti(codEnte, paramConfBean);
			log.info("FINE CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI SOGGETTI");
					
	
			//Pulizia tabella UNICO (cancello tutti i record non collegati a totale e Validato <> 1)
			log.info("INIZIO cancellazione elementi in SOGGETTO UNICO e pulizia FLAG");
			this.pulisci();
			log.info("FINE cancellazione elementi in SOGGETTO UNICO e pulizia FLAG");			
	
			log.info("################# FINE Elaborazione controllo associabilità Soggetti in UNICO ##############################");
				
		}
		catch (Exception e){
			
			log.error("Errore:AggregatoreSoggetti=" + e.getMessage(),e);
			AggregatoreException ea = new AggregatoreException(e.getMessage());
			throw ea;
		}finally{}

	}
	

	/**
	 * Metodo che effettua l'inserimento/associazione dei soggetti di riferimento non agganciati
	 */
	private void inserisciNonAgganciatiRif(Object[] fonte) throws AggregatoreException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		SitSoggettoTotale sst = null;
		
		try {
			
			ps = conn.prepareStatement(getProperty("SQL_RICERCA_RIGHE_NON_AGGANCIATE_SOGG_RIF"));
			ps.setObject(1, fonte[0]);
			ps.setObject(2, fonte[1]);
			rs = ps.executeQuery();
			while (rs.next()) {			
				sst = this.mappingSoggetto(rs);
				salvaNonAgganciati(sst, 0, 100);
			}
		} catch (Exception e) {
				
				log.error("Errore:AggregatoreSoggetti inserisciNonAgganciati "+e.getMessage() ,e);
				AggregatoreException ea = new AggregatoreException(e.getMessage());
				throw ea;
		}
		finally {
		try {
			DbUtils.close(rs);
			DbUtils.close(ps);
			
		} catch (Exception e) {
		}	
		}
	}
	
	
	
	/**
	 * Metodo che effettua l'inserimento/associazione degli altri soggetti non agganciati
	 */
	private void inserisciNonAgganciati() throws AggregatoreException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		SitSoggettoTotale sst = null;
		
		try {
			
			ps = conn.prepareStatement(getProperty("SQL_RICERCA_RIGHE_NON_AGGANCIATE_SOGG"));
			ps.setObject(1, paramFonteRif.get(0)[0]);			
			ps.setObject(2, paramFonteRif.get(0)[1]);
			rs = ps.executeQuery();
			while (rs.next()) {				
				sst = this.mappingSoggetto(rs);
				salvaNonAgganciati(sst, 0, 100);
			}
		} catch (Exception e) {
				
				log.error("Errore:AggregatoreSoggetti inserisciNonAgganciati "+e.getMessage() ,e);
				AggregatoreException ea = new AggregatoreException(e.getMessage());
				throw ea;
		}
		finally {
		try {
			DbUtils.close(rs);
			DbUtils.close(ps);
			
		} catch (Exception e) {
		}	
		}
	}
		
	
	/**
	 * @param conn
	 * @param fonte
	 * @param ricerca 1=cognomcfdt , 2=cognomcf, 3=cognomdt,  4=cf (nel caso trattasi di fonterif allora e' null)
	 * @throws Exception
	 */
	private boolean isFonteRif(Object[] fonte) {
		Object es = fonte[0];
		Object pes = fonte[1];
		Object esRif = paramFonteRif.get(0)[0];
		Object pesRif = paramFonteRif.get(0)[1];
		return (es.equals(esRif) && pes.equals(pesRif));		
	}
	
	
	private boolean  elabora(ResultSet resSogg, int critMaxFonte, String codEnte, HashParametriConfBean paramConfBean) throws Exception
	{
				
		try {
			
			//Lettura parametro attivazione Criterio Lasco
			boolean critLascoDisattivato = this.recuperoCriterioLasco(codEnte, paramConfBean.getCriterioLascoSogg());						
						
			int i = 0;
			boolean hoAggregato = false;
			boolean rieseguiQuery = false;


			
			while(resSogg.next() && !rieseguiQuery){
				
				Object fk = null;
									
				SitSoggettoTotale soggCorr = this.mappingSoggetto(resSogg);
									
				//Controlla Criterio CAS-A100 (Codice Soggetto Fornito dall'ente)
				if(soggCorr.getCodiceSoggOrig() != null){
					
					String codSogg = this.cercaSoggettoUnico(soggCorr.getCodiceSoggOrig());
					
					if(codSogg!=null){
						//Associo il soggetto			
						fk = codSogg;
						
						String desc = propSoggetti.getProperty("descr.cas.a100");
						int ratCrit = Integer.parseInt(propSoggetti.getProperty("rating.cas.a100"));
						
						this.aggiornaTotaleCorrente(soggCorr, 100, ratCrit, desc, fk);																								
					}										
				}
				
				
					//Controlla Criterio A80 (Cf, Cognome, Nome, Data Nascita)															
					fk = cognomcfdtRicerca(soggCorr,fk,critMaxFonte,elaboratiFonteRif);
					//Controllo Criterio A60 (Cf, Cognome, Nome)																												
					fk = cognomcfRicerca(soggCorr, fk, critMaxFonte,elaboratiFonteRif);											
					//Controllo Criterio A50 (Cf, Denominazione)																												
					fk = denominazioneCfRicerca(soggCorr, fk, critMaxFonte,elaboratiFonteRif);											
					//Controllo Criterio A40 (Cf)													
					fk = cfRicerca(soggCorr, fk, critMaxFonte,elaboratiFonteRif);												
					//Controllo Criterio A30 (Pi)													
					fk = piRicerca(soggCorr, fk, critMaxFonte,elaboratiFonteRif);												
					//Controllo Criterio A20 (Nome, Cognome, Data Nascita)								
					fk = cognomdtRicerca(soggCorr, fk, critMaxFonte,elaboratiFonteRif);				
					if(!critLascoDisattivato){
						//Controllo Criterio LASCO (Nome, Cognome)								
						fk = soloCogNomRicerca(soggCorr, fk, critMaxFonte,elaboratiFonteRif);				
					}
				

					
				if(fk==null)
					spuntaTotaleCorrenteComeNONASSOCIABILE(soggCorr);
					
				//Effettuo la commit dei dati su DB
				i++;
				totNumRecLetti++;
				hoAggregato = hoAggregato || fk!=null;

				if(i > 100000){
					log.debug("COMMIT -  NUM. RECORD LETTI:" + totNumRecLetti);
					i=0;
					rieseguiQuery = hoAggregato ? true : false;
					conn.commit();
				}
			}
			
			
			conn.commit();
			
 			return rieseguiQuery;
			
		}catch(Exception e){
			log.error("Errore in Aggregazione SOGGETTI Riferimento: ",e);
			conn.rollback();
			throw new Exception("Errore in Aggregazione SOGGETTI Riferimento: " + e.getMessage());
		}finally {			
		}

	}
	
	
	/**
	 * Metodo che effettua l'elaborazione/aggregazione delle altre fonti
	 */
	private boolean elaboraAltreFonti(ResultSet resSogg,String codEnte, HashParametriConfBean paramConfBean) throws Exception
	{
				
		try {
			
			//Lettura parametro attivazione Criterio Lasco
			boolean critLascoDisattivato = this.recuperoCriterioLasco(codEnte, paramConfBean.getCriterioLascoSogg());		
			
			elaborati = null;
			elaborati = new HashMap<String, Object>();

			// serve per rieffetture una query ogni 100.000 record per selezionare quelli con l'fk ancora non valorizzato (che dopo 50.000 soggetti elaborati saranno pochi)
			boolean rieseguiQuery = false;
			boolean hoAggregato = false;
			
			int i = 0;
			while(resSogg.next() && !rieseguiQuery){
				// TODO : PER UN PROBLEMA DI MEMORIA ram DEVO PER FORZA SVUOTARE QUESTA HASHMAP, CHE ALTRIMENTI ARRIVEREBBE A DARE JAVA HEAP SPACE
				elaborati = new HashMap<String, Object>();
				Object fk = null;
							

				//Trovo il valore del criterio Max da valutare per la fonte
				Object[] paramFonte = new Object[2];				
				paramFonte[0] = resSogg.getBigDecimal("ENTESORGENTE");
				paramFonte[1] = resSogg.getBigDecimal("PROGES");
				int critMaxFonte = this.getCriterioMax(paramFonte);

				
				SitSoggettoTotale soggCorr = this.mappingSoggetto(resSogg);
									
				//Controlla Criterio CAS-A100 (Codice Soggetto Fornito dall'ente)
				if(soggCorr.getCodiceSoggOrig() != null){
					
					String codSogg = this.cercaSoggettoUnico(soggCorr.getCodiceSoggOrig());
					
					if(codSogg!=null){
						//Associo il soggetto			
						fk = codSogg;
						
						String desc = propSoggetti.getProperty("descr.cas.a100");
						int ratCrit = Integer.parseInt(propSoggetti.getProperty("rating.cas.a100"));
						
						this.aggiornaTotaleCorrente(soggCorr, 100, ratCrit, desc, fk);																								
					}										
				}
					
				
					//Controlla Criterio A80 (Cf, Cognome, Nome, Data Nascita)															
					fk = cognomcfdtRicerca(soggCorr,fk,critMaxFonte, elaborati);
					//Controllo Criterio A60 (Cf, Cognome, Nome)																												
					fk = cognomcfRicerca(soggCorr, fk, critMaxFonte, elaborati);											
					//Controllo Criterio A50 (Cf, Denominazione)																												
					fk = denominazioneCfRicerca(soggCorr, fk, critMaxFonte, elaborati);											
					//Controllo Criterio A40 (Cf)													
					fk = cfRicerca(soggCorr, fk, critMaxFonte, elaborati);												
					//Controllo Criterio A30 (Pi)													
					fk = piRicerca(soggCorr, fk, critMaxFonte, elaborati);												
					//Controllo Criterio A20 (Nome, Cognome, Data Nascita)								
					fk = cognomdtRicerca(soggCorr, fk, critMaxFonte, elaborati);				
					if(!critLascoDisattivato){
						//Controllo Criterio LASCO (Nome, Cognome)								
						fk = soloCogNomRicerca(soggCorr, fk, critMaxFonte, elaborati);				
					}
				

				if(fk==null)
					spuntaTotaleCorrenteComeNONASSOCIABILE(soggCorr);
				
				//Effettuo la commit dei dati su DB
				i++;
				hoAggregato = hoAggregato || fk!=null;
				totNumRecLetti++;
				if(i > 5000){
					log.debug("COMMIT -  NUM. RECORD LETTI:" + totNumRecLetti);
					log.debug("COMMIT " + i);
					i=0;
					conn.commit();
					rieseguiQuery = hoAggregato ? true : false;
				}
				
			}
			conn.commit();
			
			return rieseguiQuery;
			
		}catch(Exception e){
			log.error("Errore in Aggregazione SOGGETTI Altre fonti: ", e);
			conn.rollback();
			throw new Exception("Errore in Aggregazione SOGGETTI Altre fonti: " + e.getMessage());
		}finally {	
		}

	}
	
	

	private void spuntaTotaleCorrenteComeNONASSOCIABILE(
			SitSoggettoTotale soggCorr) throws Exception {
		try{

			
			Object pp[] = new Object[5];									
			pp[0] = "1";
			pp[1] = soggCorr.getId().getFkEnteSorgente();
			pp[2] = soggCorr.getId().getProgEs();
			pp[3] = soggCorr.getId().getCtrHash();
			pp[4] = soggCorr.getId().getIdDwh();
			run.update(conn,getProperty("SQL_UPDATE_FLAG_RUNTIME_NON_AGGANCIATO"),pp);
			
			numRecNonAgganciati = numRecNonAgganciati +1;
			if (numRecNonAgganciati>= 1000) 
			{
				totNumRecNonAgganciati = totNumRecNonAgganciati + numRecNonAgganciati;
				log.debug("Record Non Agganciati " + numRecNonAgganciati) ;
				numRecNonAgganciati = 0;
			}
			
		}catch (Exception e) {
			log.error("Errore spuntaTotaleCorrenteComeNONASSOCIABILE ",e);
			throw new Exception("Errore spuntaTotaleCorrenteComeNONASSOCIABILE ", e);
		}				
	}

	private Object soloCogNomRicerca(SitSoggettoTotale sst, Object fk, int critMaxFonte, HashMap<String, Object> elaboratiLocal) throws Exception {

		// se il record corrente ha codfisc o dt_nascita valorizzato allora ha  tutte le caratteristiche+ù
		// per essere un soggetto a se stante e non agganciato per nome, cognome ad un altro
		
		if (sst.getCodfisc()!=null || sst.getDtNascita()!=null)
			return fk;
		
		//Imposto Rating e RelDescr in base alla tipologia di fonte		
		int ratingCriterio = Integer.parseInt(propSoggetti.getProperty("rating.cas.a10"));
		String relDescr = propSoggetti.getProperty("descr.cas.a10");;
		
				

		Object p[] = new Object[4];
		p[0]=sst.getCognome();
		p[1]=sst.getNome();
		
		
 		if (p[0]==null || p[1]==null) 
			return fk;
 		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		Object trovato = pescaSeGiaElaborato(k);
		if (trovato!=null){
			return aggiorna(null, sst, critMaxFonte, ratingCriterio, relDescr, trovato);
		}	

		
		
		Object[] fonte = {sst.getId().getFkEnteSorgente(),sst.getId().getProgEs()};
		p[2]=fonte[0];
		p[3]=fonte[1];
		
		//List<SitSoggettoTotale> lSoggTotale = (List<SitSoggettoTotale>) run.query(conn, getProperty("SQL_RICERCA_SOGGETTO_TOTALE_LASCA1"),p, h);
		String sqlRicSoggTotLasca1 = getProperty("SQL_RICERCA_SOGGETTO_TOTALE_LASCA1");		
		List<SitSoggettoTotale> lSoggTotale = this.getListaSoggettiAssociati(conn, sqlRicSoggTotLasca1, p);
	
		
		
		// ci deve essere solo un COGNOME / NOME / FK_SOGGETTO per essere abbastanza sicuri di poter legare il mio record a questo
		if (lSoggTotale.size()==1)  {
			fk =  aggiorna( lSoggTotale, sst, critMaxFonte, ratingCriterio, relDescr, fk);
		}
		//elaboratiLocal.put(k , fk);

		
		return fk;
		
	}

	
	private Object cfRicerca(SitSoggettoTotale sst, Object fk, int critMaxFonte, HashMap<String, Object> elaboratiLocal) throws Exception {
		

		
		//Imposto Rating e RelDescr in base alla tipologia di fonte		
		int ratingCriterio = Integer.parseInt(propSoggetti.getProperty("rating.cas.a40"));
		String relDescr = propSoggetti.getProperty("descr.cas.a40");;
		

		
		Object p[] = new Object[5];
		p[0]=sst.getCodfisc();				

		
		if (p[0]==null || ((String)p[0]).length()<16 || ((String)p[0]).equals("0000000000000000") || ((String)p[0]).equals("00 00 000"))  // MILANO F205
			return fk;
		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		Object trovato = pescaSeGiaElaborato(k);
		if (trovato!=null){
			return aggiorna(null, sst, critMaxFonte, ratingCriterio, relDescr, trovato);
		}	

		
		
		p[1]=sst.getId().getFkEnteSorgente();
		p[2]=sst.getId().getProgEs();
		p[3]=sst.getId().getCtrHash();
		p[4]=sst.getId().getIdDwh();
		
		//List<SitSoggettoTotale> lSoggTotale = (List<SitSoggettoTotale>) run.query(conn, getProperty("SQL_RICERCA_SOGGETTO_TOTALE_CF"),p, h);
		String sqlRicSoggTotCf = getProperty("SQL_RICERCA_SOGGETTO_TOTALE_CF");		
		List<SitSoggettoTotale> lSoggTotale = this.getListaSoggettiAssociati(conn, sqlRicSoggTotCf, p);
	
		
		
		if (lSoggTotale.size()>0)  {			
			fk =  aggiorna( lSoggTotale, sst, critMaxFonte, ratingCriterio, relDescr, fk);
		} 
		//elaboratiLocal.put(k , fk);

		
		return fk;
		
	}

	private Object piRicerca(SitSoggettoTotale sst, Object fk, int critMaxFonte, HashMap<String, Object> elaboratiLocal) throws Exception {
		

		//Imposto Rating e RelDescr in base alla tipologia di fonte		
		int ratingCriterio = Integer.parseInt(propSoggetti.getProperty("rating.cas.a30"));
		String relDescr = propSoggetti.getProperty("descr.cas.a30");;
		
		
		Object p[] = new Object[5];
		p[0]=sst.getPi();
		
		
		if (p[0]==null || p[0].toString().equals("00000000000")) 
			return fk;
		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		Object trovato = pescaSeGiaElaborato(k);
		if (trovato!=null){
			return aggiorna(null, sst, critMaxFonte, ratingCriterio, relDescr, trovato);
		}	
		
		p[1]=sst.getId().getFkEnteSorgente();
		p[2]=sst.getId().getProgEs();
		p[3]=sst.getId().getCtrHash();
		p[4]=sst.getId().getIdDwh();
		

		String sqlRicSoggTotPiva = getProperty("SQL_RICERCA_SOGGETTO_TOTALE_PIVA");		
		List<SitSoggettoTotale> lSoggTotale = this.getListaSoggettiAssociati(conn, sqlRicSoggTotPiva, p);
	
		
		if (lSoggTotale.size()>0)  {
			fk =  aggiorna( lSoggTotale, sst, critMaxFonte, ratingCriterio, relDescr, fk);
		} 
		//elaboratiLocal.put(k , fk);

		
		return fk;
		
	}

	private Object denominazioneCfRicerca(SitSoggettoTotale sst, Object fk, int critMaxFonte, HashMap<String, Object> elaboratiLocal) throws Exception {



		//Imposto Rating e RelDescr in base alla tipologia di fonte		
		int ratingCriterio = Integer.parseInt(propSoggetti.getProperty("rating.cas.a50"));
		String relDescr = propSoggetti.getProperty("descr.cas.a50");;
		
		
		Object p[] = new Object[6];
		p[0]=sst.getDenominazione();
		p[1]=sst.getCodfisc();
		
		
 		if (p[0]==null || p[1]==null) 
			return fk;
 		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		Object trovato = pescaSeGiaElaborato(k);
		if (trovato!=null){
			return aggiorna(null, sst, critMaxFonte, ratingCriterio, relDescr, trovato);
		}	
		
		p[2]=sst.getId().getFkEnteSorgente();
		p[3]=sst.getId().getProgEs();
		p[4]=sst.getId().getCtrHash();
		p[5]=sst.getId().getIdDwh();
		

		String sqlRicSoggTotDenomCognNom = getProperty("SQL_RICERCA_SOGGETTO_TOTALE_DENOMCOGNOM");		
		List<SitSoggettoTotale> lSoggTotale = this.getListaSoggettiAssociati(conn, sqlRicSoggTotDenomCognNom, p);

		
		if (lSoggTotale.size()>0)  {			
			fk =  aggiorna( lSoggTotale, sst, critMaxFonte, ratingCriterio, relDescr, fk);
		} 
		//elaboratiLocal.put(k , fk);

		
		return fk;
		
	}	
	
	private Object cognomcfRicerca(SitSoggettoTotale sst, Object fk, int critMaxFonte, HashMap<String, Object> elaboratiLocal) throws Exception {


		//Imposto Rating e RelDescr in base alla tipologia di fonte		
		int ratingCriterio = Integer.parseInt(propSoggetti.getProperty("rating.cas.a60"));
		String relDescr = propSoggetti.getProperty("descr.cas.a60");;
		
		
		
		Object p[] = new Object[7];
		p[0]=sst.getCognome();
		p[1]=sst.getNome();
		p[2]=sst.getCodfisc();
		
		
 		if (p[0]==null || p[1]==null || p[2]==null || ((String)p[2]).length()<16 || ((String)p[2]).equals("0000000000000000")
				|| ((String)p[2]).equals("00 00 000")) 
			return fk;
		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		Object trovato = pescaSeGiaElaborato(k);
		if (trovato!=null){
			return aggiorna(null, sst, critMaxFonte, ratingCriterio, relDescr, trovato);
		}		
		
		
		p[3]=sst.getId().getFkEnteSorgente();
		p[4]=sst.getId().getProgEs();
		p[5]=sst.getId().getCtrHash();
		p[6]=sst.getId().getIdDwh();
		

		String sqlRicSoggTotCogNomCf = getProperty("SQL_RICERCA_SOGGETTO_TOTALE_COGNOMCF");		
		List<SitSoggettoTotale> lSoggTotale = this.getListaSoggettiAssociati(conn, sqlRicSoggTotCogNomCf, p);
	
		
		
		if (lSoggTotale.size()>0)  {			
			fk =  aggiorna( lSoggTotale, sst, critMaxFonte, ratingCriterio, relDescr, fk);
		}
		//elaboratiLocal.put(k , fk);

		
		return fk;
	}
	
	private Object cognomdtRicerca(SitSoggettoTotale sst, Object fk, int critMaxFonte, HashMap<String, Object> elaboratiLocal) throws Exception {
		


		//Imposto Rating e RelDescr in base alla tipologia di fonte		
		int ratingCriterio = Integer.parseInt(propSoggetti.getProperty("rating.cas.a20"));
		String relDescr = propSoggetti.getProperty("descr.cas.a20");;
		

		
		Object p[] = new Object[7];
		p[0]=sst.getCognome();
		p[1]=sst.getNome();
		p[2]=sst.getDtNascita();
				
		
 		if (p[0]==null || p[1]==null || p[2]==null) 
			return fk;
 		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		Object trovato = pescaSeGiaElaborato(k);
		if (trovato!=null){
			return aggiorna(null, sst, critMaxFonte, ratingCriterio, relDescr, trovato);
		}	

		
		p[3]=sst.getId().getFkEnteSorgente();
		p[4]=sst.getId().getProgEs();
		p[5]=sst.getId().getCtrHash();
		p[6]=sst.getId().getIdDwh();
		

		String sqlRicSoggTotCognNomDt = getProperty("SQL_RICERCA_SOGGETTO_TOTALE_COGNOMDT");		
		List<SitSoggettoTotale> lSoggTotale = this.getListaSoggettiAssociati(conn, sqlRicSoggTotCognNomDt, p);

		
		if (lSoggTotale.size()>0) {
			fk =  aggiorna( lSoggTotale, sst, critMaxFonte, ratingCriterio, relDescr, fk);
		}
		//elaboratiLocal.put(k , fk);

		
		return fk;
		
	}
		
	private Object cognomcfdtRicerca(SitSoggettoTotale sst, Object fk, int critMaxFonte, HashMap<String, Object> elaboratiLocal) throws Exception {
		
		
		//Imposto Rating e RelDescr in base alla tipologia di fonte
		int ratingCriterio = Integer.parseInt(propSoggetti.getProperty("rating.cas.a80"));
		String relDescr = propSoggetti.getProperty("descr.cas.a80");;
				
		
		Object p[] = new Object[8];
		p[0]=sst.getCognome();
		p[1]=sst.getNome();
		p[2]=sst.getCodfisc();
		p[3]=sst.getDtNascita();
		
		
		if (p[0]==null || p[1]==null || p[2]==null || p[3]==null || ((String)p[2]).length()<16 || ((String)p[2]).equals("0000000000000000")
				|| ((String)p[2]).equals("00 00 000")) 
			return fk;
		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		Object trovato = pescaSeGiaElaborato(k);
		if (trovato!=null){
			return aggiorna(null, sst, critMaxFonte, ratingCriterio, relDescr, trovato);
		}

		p[4]=sst.getId().getFkEnteSorgente();
		p[5]=sst.getId().getProgEs();
		p[6]=sst.getId().getCtrHash();
		p[7]=sst.getId().getIdDwh();
		

		String sqlRicSoggTotCogNomCfDt = getProperty("SQL_RICERCA_SOGGETTO_TOTALE_COGNOMCFDT");		
		List<SitSoggettoTotale> lSoggTotale = this.getListaSoggettiAssociati(conn, sqlRicSoggTotCogNomCfDt, p);			
		
		if (lSoggTotale.size()>0) {						
			fk =  aggiorna( lSoggTotale, sst, critMaxFonte, ratingCriterio, relDescr, fk);
		}
		

		//elaboratiLocal.put(k , fk);

				
		return fk;
		
	}

	/**
	 * Questo metodo verifica se l'oggetto corrente, i cui attributi chiave sono contenuti in un array
	 * e' gia' stato elaborato , o sulla fonte di riferimento oppure sulla fonte corrente in elaborazione
	 * chiaveFk=null se l'elemento non e' stato trovato
	 * @param k
	 * @return
	 */
	private Object pescaSeGiaElaborato(String k) {
		/* TODO: REIMPLEMENTARE GESTIONE, OUTOFMEMORY A MILANO
			le hashmap elaboratiFonteRif e elaborati
			potrebbero essere trasformate in tabelle, ma solo se necessario, nel caso che 
			senza gestione di questo metodo ci fossero rallentamenti evidenti nell'aggregazione
		*/
		return null;
		/*		
		Object chiaveFk = elaboratiFonteRif.get(k);
		if (chiaveFk==null)  
			chiaveFk = elaborati.get(k);

		return chiaveFk;
*/
		
		
	}

	private Object inserisci(SitSoggettoTotale sst, int rating) throws Exception
	{
		Object fk = null;
		QueryRunner run = new QueryRunner();
			Object pp[] = new Object[22];
			
			pp[0] = sst.getId().getIdDwh();
			pp[1] = sst.getCognome();
			pp[2] = sst.getNome();
			pp[3] = sst.getDenominazione();
			pp[4] = sst.getSesso();
			pp[5] = sst.getCodfisc();
			pp[6] = sst.getPi();
			pp[7] = sst.getDtNascita();
			pp[8] = sst.getTipoPersona();
			pp[9] = sst.getCodProvinciaNascita();
			pp[10] = sst.getCodComuneNascita();
			pp[11] = sst.getDescProvinciaNascita();
			pp[12] = sst.getDescComuneNascita();
			pp[13] = sst.getCodProvinciaRes();
			pp[14] = sst.getCodComuneRes();
			pp[15] = sst.getDescProvinciaRes();
			pp[16] = sst.getDescComuneRes();			
			
			Object nuovoId = new Object(); 
			nuovoId = 	((Map) run.query(conn,getProperty("SQL_NEXT_UNICO_SOGG"), new MapHandler())).get("ID");								
			pp[17] = nuovoId;
			fk  = nuovoId;
			
			Object[] fonte = paramFonteRif.get(0);
	        BigDecimal enteSorgente = (BigDecimal)fonte[0];
	        BigDecimal progr = (BigDecimal)fonte[1];
	        
	        // questa if servira' per mettere il fatto che l'unico che creo appartiene alla fonte di riferimento

	        pp[18] = rating;   
			pp[19] = sst.getCodiceSoggOrig();
			pp[20] = 0; 		//validato
			if (enteSorgente.longValue()==sst.getId().getFkEnteSorgente() 
		        	&& progr.longValue() == sst.getId().getProgEs()) 
	        		pp[21] = 1; 		//FONTE_RIF
		        else
					pp[21] = 0; 		//FONTE_RIF

			
			run.update(conn,getProperty("SQL_INSERISCI_SOGGETTO_UNICO"),pp);			
			
			return fk;
	}
	
	/**
	 * 
	 * @param conn
	 * @param lSoggTotale
	 * @param rs
	 * @param rating
	 * @param relDescr
	 * @return String- fk in caso abbia inserito un record in unico
	 * @throws Exception
	 */
	private Object  aggiorna(List<SitSoggettoTotale> lSoggTotale , SitSoggettoTotale sst, int critMaxFonte, int ratingCritValutato, String relDescr, Object fk) throws Exception {
		
			//se è già stato elaborato
			if(lSoggTotale ==null && fk != null){
				this.aggiornaTotaleCorrente(sst, critMaxFonte, ratingCritValutato, relDescr, fk);
				return fk;
			}
			
		
			QueryRunner run = new QueryRunner();		
			int ratingEleTotale = 0;			
			Object fkRs = new Object(); 
			fkRs = null;																
			
			//Controllo se l'elemento è collegato ad una UNICO e quinfi possiede un fk
			try {
				// LETTURA DELL'fk_soggetto del record che sto scorrendo
				Object pFK[] = new Object[4];
				pFK[0] = sst.getId().getFkEnteSorgente();
				pFK[1] = sst.getId().getProgEs();
				pFK[2] = sst.getId().getCtrHash();
				pFK[3] = sst.getId().getIdDwh();
				fkRs  = ((Map) run.query(conn,getProperty("SQL_SELECT_SOGGETTO_TOTALE_FK"),pFK, new MapHandler())).get("FK_SOGGETTO");								
			} catch (Exception e) {
				log.warn("Problemi reperimento fk per record " + sst.getId().getIdDwh(), e);
			}
																									
			if (fkRs==null){
				
				//Cerco l'fk con rating più alto degli elementi collegati
				for (SitSoggettoTotale s : lSoggTotale){										
					
					if(s.getFkSoggetto()!=null){
						
						if(s.getRating() != null){
							ratingEleTotale = Integer.parseInt(s.getRating().toString());
						}else{
							ratingEleTotale = 0;
						}
						
						if(ratingEleTotale >= ratingCritValutato){
							fkRs = s.getFkSoggetto();
							break;
						}						
					}
				}
			
				if(fkRs == null)
					fk =  inserisci(sst, ratingCritValutato);
				else
					this.aggiornaTotaleCorrente(sst, critMaxFonte, ratingCritValutato, relDescr, fkRs);

			}else
				fk =fkRs;
			
				
			
			//Aggiorna Rec Tot Corrente nel caso di nuovo inserimento in Unico
			if (fk!=null && fkRs==null) {					
				this.aggiornaNuovoTotaleUnico(sst,fk,critMaxFonte,ratingCritValutato);			
			}
			//else{	
				//TODO
				//1)Leggo l'elemento di Unico avente chiave=fkRs
			//	SitSoggettoUnico sogUni = this.getSoggettoUnico(fkRs.toString());
																			
				//2)Controllo se il rating di questo unico è < di rating.								
				//  Se minore allora aggiorno il dato unico con i dati del record corrente, ma solo se il record in unico non e' proveniente dal riferimento
				//	Altrimenti non fa nulla							
			//	if(sogUni.getRating().intValue() < ratingCritValutato && sogUni.getFonteRif().compareTo(BigDecimal.ZERO)==0){					
			//		this.aggiornaUnico(sogUni, sst, ratingCritValutato);
			//	}
								
				/* !!!!!!!!! PARTE REMMATA IN QUANTO sst.getRating() DELL'ELEMENTO CHE STO SCORRENDO RISULTA SEMPRE NULL E DUNQUE SI HA SEMPRE UNA SOSTITUZIONE DEL RATING (ERRATO)
				 * 			//3)Controllo sul TOTALE: se il rating dell'elemento corrente < rating --> aggiorno il rating dell'elemento corrente in totale				
				if(sst.getRating() == null ){
					BigDecimal val = new BigDecimal(0);
					sst.setRating(val);
				}				
				if(sst.getRating().intValue() < ratingCritValutato){				
					this.aggiornaTotaleCorrente(sst, critMaxFonte, ratingCritValutato, relDescr, fkRs);	
				}
				 !!!!!!!!   */					
				
			//}
			
				
			if(fkRs != null){
				fk = fkRs;
			}
			
			// tanto che ci sono aggiorno anche quelli che ho trovato 
			//Aggiorno gli altri elementi evenhtualemente collegati
			for (SitSoggettoTotale s : lSoggTotale)
			{
				
				//Controllo sul rating
				if(s.getRating() != null){
					ratingEleTotale = Integer.parseInt(s.getRating().toString());
				}else{
					ratingEleTotale = 0;
				}
				
				if (( s.getFkSoggetto()==null || 
					  !s.getFkSoggetto().toString().equals(fk.toString()) ||
					  !relDescr.equals(s.getRelDescr())) &&					  
					  (ratingEleTotale< ratingCritValutato)) {
					
					
					this.aggiornaTotaleCorrente(s, critMaxFonte, ratingCritValutato, relDescr, fk);
					
					// cambio anche l'fk a tutti quelli che avevano lo stesso fk di quello che sto scorrendo
					// un po' azzardato ma mi piace collegarne il più possibile
					if (s.getFkSoggetto()!=null 
							&& !s.getFkSoggetto().toString().equals(fk.toString())) {
						
						this.cambioFkCorrelati(fk, s.getFkSoggetto());
					
					}
							
				}
			}

			return fk;
	}
		
				
	public void setConnection(Connection conn)
	{
		this.conn = conn;
	}
	
	
	
	/**
	 * Metodo che carica i soggetti dalla TOTALE da dover aggregare per la fonte indicata
	 */
	protected void caricaDatiDaAggregareFonteRif() throws Exception{
		

		
		try {
		
			// Leggo Soggetto TOTALE	
			String sqlSoggettoTotale = getProperty("SQL_LEGGI_SOGGETTO_TOTALE_FONTE_RIF");
			
						
			prsRiesegui = connectionForLongResultset.prepareStatement(sqlSoggettoTotale);
			
			BigDecimal enteRif = (BigDecimal)paramFonteRif.get(0)[0];
			BigDecimal progRif = (BigDecimal)paramFonteRif.get(0)[1];
						
			prsRiesegui.setBigDecimal(1, enteRif);
			prsRiesegui.setBigDecimal(2, progRif);				
			
			rsRiesegui = prsRiesegui.executeQuery();				
			
		} catch (Exception e) {
			log.error("Errore in caricamento dati da tabella SIT_SOGGETTO_UNICO ",e);
			throw new Exception ("Errore in caricamento dati da tabella SIT_SOGGETTO_UNICO "+e.getMessage());
		} finally {			
		}				
	
	}
	
	
	/**
	 * Metodo che carica i soggetti dalla TOTALE da dover aggregare per le varie fonti
	 */
	protected void caricaDatiDaAggregare(Object[] fonte) throws Exception{
		
				
		try {
		
			// Leggo Soggetto TOTALE	
			String sqlSoggettoTotale = getProperty("SQL_RICERCA_RIGHE_SOGG");
			
						
			prsRiesegui = connectionForLongResultset.prepareStatement(sqlSoggettoTotale);
			
			BigDecimal enteRif = (BigDecimal)fonte[0];
			BigDecimal progRif = (BigDecimal)fonte[1];
						
			prsRiesegui.setBigDecimal(1, enteRif);
			prsRiesegui.setBigDecimal(2, progRif);				
			
			rsRiesegui = prsRiesegui.executeQuery();				
			

		} catch (Exception e) {
			log.error("Errore in caricamento dati da tabella SIT_SOGGETTO_UNICO ",e);
			throw new Exception ("Errore in caricamento dati da tabella SIT_SOGGETTO_UNICO "+e.getMessage());
		} finally {			
		}				
	
	}
	
	
	
	/**
	 * Metodo che effettua il mapping di un soggetto Totale letto
	 */
	protected SitSoggettoTotale mappingSoggetto(ResultSet rs) throws Exception{
		
		SitSoggettoTotale sst = new SitSoggettoTotale();				
		IndicePK iPk = new IndicePK();
		
				
		iPk.setIdDwh(rs.getString("IDDWH"));
		iPk.setFkEnteSorgente(rs.getInt("ENTESORGENTE"));
		iPk.setProgEs(rs.getInt("PROGES"));
		iPk.setCtrHash(rs.getString("CTRHASH"));
		
		sst.setId(iPk);
			
		sst.setCognome(rs.getString("COGNOME"));
		sst.setNome(rs.getString("NOME"));
		sst.setDenominazione(rs.getString("DENOMINAZIONE"));
		sst.setSesso(rs.getString("SESSO"));
		sst.setCodfisc(rs.getString("CODFIS"));
		sst.setPi(rs.getString("PIVA"));
		sst.setDtNascita(rs.getDate("DATANASCITA"));
		sst.setTipoPersona(rs.getString("TIPOPERSONA"));
		sst.setCodProvinciaNascita(rs.getString("CODPROVNASC"));
		sst.setCodComuneNascita(rs.getString("CODCOMNASC"));
		sst.setDescProvinciaNascita(rs.getString("DESCPROVNASC"));
		sst.setDescComuneNascita(rs.getString("DESCCOMNASC"));
		sst.setCodProvinciaRes(rs.getString("CODPROVRES"));
		sst.setCodComuneRes(rs.getString("CODCOMRES"));
		sst.setDescProvinciaRes(rs.getString("DESCPROVRES"));
		sst.setDescComuneRes(rs.getString("DESCCOMRES"));
		sst.setRating(rs.getBigDecimal("RATING"));
		sst.setRelDescr(rs.getString("REL_DESCR"));
		sst.setFkSoggetto(rs.getBigDecimal("FK_SOGGETTO"));
		sst.setCodiceSoggOrig(rs.getString("CODICE_SOGGETTO"));
		sst.setValidato(rs.getBigDecimal("VALIDATO"));
	
			
		return sst;
		
	}
	
	
	/**
	 * Metodo che effettua il mapping di un soggetto Unico letto
	 */
	protected SitSoggettoUnico mappingSoggettoUnico(ResultSet rs, SitSoggettoUnico su) throws Exception{
							
		su.setIdSoggetto(rs.getInt("ID_SOGGETTO"));
		su.setCognome(rs.getString("COGNOME"));
		su.setNome(rs.getString("NOME"));
		su.setDenominazione(rs.getString("DENOMINAZIONE"));
		su.setSesso(rs.getString("SESSO"));
		su.setCodfisc(rs.getString("CODFISC"));
		su.setPi(rs.getString("PI"));
		su.setDtIns(rs.getDate("DT_NASCITA"));
		su.setTipoPersona(rs.getString("TIPO_PERSONA"));
		su.setCodProvinciaNascita(rs.getString("COD_PROVINCIA_NASCITA"));
		su.setCodComuneNascita(rs.getString("COD_COMUNE_NASCITA"));
		su.setDescProvinciaNascita(rs.getString("DESC_PROVINCIA_NASCITA"));
		su.setDescComuneNascita(rs.getString("DESC_COMUNE_NASCITA"));
		su.setCodProvinciaRes(rs.getString("COD_PROVINCIA_RES"));
		su.setCodComuneRes(rs.getString("COD_COMUNE_RES"));
		su.setDescProvinciaRes(rs.getString("DESC_PROVINCIA_RES"));
		su.setDescComuneRes(rs.getString("DESC_COMUNE_RES"));
		su.setDtIns(rs.getDate("DT_INS"));
		su.setValidato(rs.getBigDecimal("VALIDATO"));
		su.setCtrlUtil(rs.getString("CTRL_UTIL"));
		su.setCodiceSoggettoOrig(rs.getString("CODICE_SOGGETTO"));
		su.setRating(rs.getBigDecimal("RATING"));
		su.setRating(rs.getBigDecimal("RATING"));
		su.setFonteRif(rs.getBigDecimal("FONTE_RIF"));	
		
		return su;
		
	}
	
	
	
	/**
	 * Metodo che cerca il soggetto in UNICO
	 */
	protected SitSoggettoUnico getSoggettoUnico(String codSogg) throws Exception {
						
		SitSoggettoUnico su = new SitSoggettoUnico();
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_SOGGETTO_UNICO");
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, codSogg);
			
			rs = ps.executeQuery();

			if(rs.next()){
				this.mappingSoggettoUnico(rs, su);
			}	
									
			
		}catch (Exception e) {
			log.error("Errore nella ricerca della soggetto UNICO per codice :",e);
			throw new Exception("Errore nella ricerca della soggetto UNICO per codice :" + e.getMessage());
		}finally{
			if(rs!=null)
			rs.close();
			if(ps!=null)
			ps.close();
		}

		return su;
		
	}
	
	
	/**
	 * Metodo che aggiorna il Soggetto in Unico con uno più valido della Totale
	 */
	protected void aggiornaUnico(SitSoggettoUnico ssu, SitSoggettoTotale sst, int rating) throws Exception{
		
		PreparedStatement ps = null;
		java.sql.Date sqlDate = null;
		
		try{
			
			String sql = getProperty("SQL_AGGIORNA_SOGGETTO_UNICO");
			
			ps = conn.prepareStatement(sql);

			ps.setString(1, sst.getCognome());
			ps.setString(2, sst.getNome());
			ps.setString(3, sst.getDenominazione());			
			ps.setString(4, sst.getSesso());
			ps.setString(5, sst.getCodfisc());
			ps.setString(6, sst.getPi());
			
			if(sst.getDtNascita() != null){
				sqlDate = new java.sql.Date(sst.getDtNascita().getTime());
			}else{
				sqlDate = null;
			}			
			ps.setDate(7, sqlDate);
			
			ps.setString(8, sst.getTipoPersona());
			ps.setString(9, sst.getCodProvinciaNascita());
			ps.setString(10, sst.getCodComuneNascita());
			ps.setString(11, sst.getDescProvinciaNascita());
			ps.setString(12, sst.getDescComuneNascita());
			ps.setString(13, sst.getCodProvinciaRes());
			ps.setString(14, sst.getCodComuneRes());
			ps.setString(15, sst.getDescProvinciaRes());
			ps.setString(16, sst.getDescComuneRes());
			
			if(sst.getDtInizioVal() != null){
				sqlDate = new java.sql.Date(sst.getDtInizioVal().getTime());
			}else{
				sqlDate = null;
			}
			ps.setDate(17,sqlDate);
			
			ps.setString(18, sst.getId().getIdDwh());
			ps.setInt(19, rating);
			ps.setString(20, sst.getCodiceSoggOrig());
			ps.setBigDecimal(21, sst.getValidato());
			ps.setLong(22, ssu.getIdSoggetto());
			
			
			ps.executeUpdate();																
			
		}catch (Exception e) {
			log.error("Errore nell'aggiornamento del soggetto UNICO per codice :",e);
			throw new Exception("Errore nell'aggiornamento del soggetto UNICO per codice :" + e.getMessage());
		}finally{
			DbUtils.close(ps);
		}
		
	}
	
	
	/**
	 * Metodo che aggiorna l'elemento Corrente della SOGGETTO TOTALE
	 */
	protected void aggiornaTotaleCorrente(SitSoggettoTotale sst,int critMaxFonte, int ratingCritValutato, String relDescr, Object fk) throws Exception{
		
		try{

			float attendibilita = 0;			
			if(critMaxFonte-ratingCritValutato != 0){
				attendibilita = ((float)1/((float)critMaxFonte-(float)ratingCritValutato))*1000;
			}else{
				attendibilita = 100;
			}
			
			
			Object pp[] = new Object[8];									
			pp[0] = fk;
			pp[1] = ratingCritValutato;
			pp[2] = relDescr;
			pp[3] = attendibilita;
			pp[4] = sst.getId().getFkEnteSorgente();
			pp[5] = sst.getId().getProgEs();
			pp[6] = sst.getId().getCtrHash();
			pp[7] = sst.getId().getIdDwh();
			run.update(conn,getProperty("SQL_UPDATE_SOGGETTO_TOTALE_FK"),pp);
			
			numRecAggiornati = numRecAggiornati +1;
			if (numRecAggiornati>= 1000) 
			{
				totNumRecAggiornati = totNumRecAggiornati + numRecAggiornati;
				log.debug("Aggiornamento record " + totNumRecAggiornati) ;
				numRecAggiornati = 0;
			}
			
		}catch (Exception e) {
			log.error("Errore in salvataggio elemento Corrente Soggetto Toatale: ",e);
			throw new Exception("Errore in salvataggio elemento Corrente Soggetto Toatale: "+ e.getMessage());
		}					
	}
	
	/**
	 * Metodo che aggiorna l'elemento della SOGGETTO TOTALE appena inserito in UNICO
	 */
	protected void aggiornaNuovoTotaleUnico(SitSoggettoTotale sst, Object fk, int critMaxFonte, int ratingCritValutato) throws Exception{
		
		try{
			
			float attendibilita = 0;			
			if(critMaxFonte-ratingCritValutato != 0){
				attendibilita = ((float)1/((float)critMaxFonte-(float)ratingCritValutato))*1000;
			}else{
				attendibilita = 100;
			}
			
			Object pp[] = new Object[8];
			pp[0] = fk;
			pp[1] = 100;
			pp[2] = "NATIVA";
			pp[3] = attendibilita;
			pp[4] = sst.getId().getFkEnteSorgente();
			pp[5] = sst.getId().getProgEs();
			pp[6] = sst.getId().getCtrHash();
			pp[7] = sst.getId().getIdDwh();
			run.update(conn,getProperty("SQL_UPDATE_SOGGETTO_TOTALE_FK"),pp);
			
		}catch (Exception e) {
			log.error("Errore in salvataggio elemento Corrente Soggetto Toatale: ",e);
			throw new Exception("Errore in salvataggio elemento Corrente Soggetto Toatale: "+ e.getMessage());
		}					
	}
	
	
	/**
	 * Metodo che cambia l'FK a tuuti
	 */
	protected void cambioFkCorrelati(Object fk, Object fkSoggetto ) throws Exception{
		
		try{
			
			Object pp [] = new Object[3];
			pp[0] = fk;
			pp[1] = fkSoggetto;
			pp[2] = fkSoggetto;
//			pp[2] = enteSorgente;
//			pp[3] = progr;
			// VEDI SQL: CAMBIO SOLO SE L'FK NON E' QUELLO DI UN ID_SOGGETTO DI RIFERIMENTO O VALIDATO, SE LO E' NON CAMBIO!!
			run.update(conn,getProperty("SQL_UPDATE_SOGGETTO_TOTALE_FK_ALL"),pp);
			
/*			
 * TODO: HO PROVATO E RIPROVATO , MA MI DA SEMPRE ID_SOGGETTO DUPLICATI , DUNQUE HO DECISO DI NON FARE UPDATE IN ALCUNI CASI CHE HO PREVISTO NELLA UPDATE SQL_UPDATE_SOGGETTO_TOTALE_FK_ALL
 * // nel caso che fra gli fkSoggetto ci fosse stato un soggetto di riferimento io vado a ripristinare la situazioen dei collegamenti con l'fkSoggetto precedente a fk
			// NON E' DETTO CHE QUESTA UPDATE DIA RISULTATI
			// in questo modo il riferimento rimane sempre riferimento
			long time = (long)(Math.random()*1000000);
			
			Object ppp[]  = new Object[2];
			ppp[0] = time;
			ppp[1] = fkSoggetto;
			run.update(conn,getProperty("SQL_RIPRISTINA_FK_SOGGETTO_RIF1"),ppp);

			Object pppp[]  = new Object[2];
			pppp[0] = fkSoggetto;
			pppp[1] = fk;
			run.update(conn,getProperty("SQL_RIPRISTINA_FK_SOGGETTO_RIF2"),pppp);

			
			Object ppppp[]  = new Object[3];
			ppppp[0] = fk;
			ppppp[1] = time;
			ppppp[2] = fk;
			run.update(conn,getProperty("SQL_RIPRISTINA_FK_SOGGETTO_RIF3"),ppppp);
	*/
			
		}catch (Exception e) {
			log.error("Errore in cambio Fk a Soggetti collegati precedentemente: ",e);
			throw new Exception("Errore in cambio Fk a Soggetti collegati precedentemente: " + e.getMessage());
		}	
	}
	
	
	/**
	 * Metodo che effettua l'inserimento dei soggetti che non hanno trovato aggancio
	 */
	private void salvaNonAgganciati(SitSoggettoTotale sst, int rating, int attendibilita) throws Exception{
		
			Object fk = null;
			QueryRunner run = new QueryRunner();
			Object pp[] = new Object[22];
			
			pp[0] = sst.getId().getIdDwh();
			pp[1] = sst.getCognome();
			pp[2] = sst.getNome();
			pp[3] = sst.getDenominazione();
			pp[4] = sst.getSesso();
			pp[5] = sst.getCodfisc();
			pp[6] = sst.getPi();
			pp[7] = sst.getDtNascita();
			pp[8] = sst.getTipoPersona();
			pp[9] = sst.getCodProvinciaNascita();
			pp[10] = sst.getCodComuneNascita();
			pp[11] = sst.getDescProvinciaNascita();
			pp[12] = sst.getDescComuneNascita();
			pp[13] = sst.getCodProvinciaRes();
			pp[14] = sst.getCodComuneRes();
			pp[15] = sst.getDescProvinciaRes();
			pp[16] = sst.getDescComuneRes();			
			
			Object nuovoId = new Object(); 
			nuovoId = 	((Map) run.query(conn,getProperty("SQL_NEXT_UNICO_SOGG"), new MapHandler())).get("ID");								
			pp[17] = nuovoId;
			fk  = nuovoId;
			
			pp[18] = rating;
			pp[19] = sst.getCodiceSoggOrig();
			pp[20] = 0; 		//validato
			
			Object[] fonte = paramFonteRif.get(0);
	        BigDecimal enteSorgente = (BigDecimal)fonte[0];
	        BigDecimal progr = (BigDecimal)fonte[1];

			if (enteSorgente.longValue()==sst.getId().getFkEnteSorgente() 
		        	&& progr.longValue() == sst.getId().getProgEs()) 
	        		pp[21] = 1; 		//FONTE_RIF
		        else
					pp[21] = 0; 		//FONTE_RIF

			
			run.update(conn,getProperty("SQL_INSERISCI_SOGGETTO_UNICO"),pp);
			
			
			//  aggiorno il record di totale che ho appena inserito in unico
			pp = new Object[8];			
			pp[0] = fk;
			pp[1] = rating;
			pp[2] = "NATIVA";
			pp[3] = attendibilita;
			pp[4] = sst.getId().getFkEnteSorgente();
			pp[5] = sst.getId().getProgEs();
			pp[6] = sst.getId().getCtrHash();
			pp[7] = sst.getId().getIdDwh();
						
			run.update(conn,getProperty("SQL_UPDATE_SOGGETTO_TOTALE_FK"),pp);					
	}
	
	
	/**
	 * Metodo che effettua la ricerca in Soggetti Unico per codice Soggetto Originario
	 */
	protected String cercaSoggettoUnico(String soggOrig) throws Exception {
		
		String codiceSogg = null;
		PreparedStatement ps = null;
		ResultSet rs= null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_COD_SOGGETTO_UNICO");
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, soggOrig);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				codiceSogg = rs.getString("ID_SOGGETTO");
			}
			


			
		}catch (Exception e) {
			log.error("Errore nella ricerca del soggetto UNICO per codice Soggetto originario :",e);
			throw new Exception("Errore nella ricerca del soggetto UNICO per codice Soggetto originario :" + e.getMessage());
		} finally {
			DbUtils.close(rs);
			DbUtils.close(ps);
			
		}
		
		
		return codiceSogg;
		
	}
	
	
	/**
	 * Metodo che effettua l'aggregazione Soggetti per la Fonte di Riferimento
	 */
	protected void aggregazioneFonteRiferimento(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
		

		
		try{
			
			boolean rieseguiQuery = true;
			while (rieseguiQuery) {

				rsRiesegui = null;
				prsRiesegui = null;
				
				try {
					//Effettuo il caricamento della lista dei TOTALI da aggregare per la fonte di riferimento						
					this.caricaDatiDaAggregareFonteRif();
															
					log.debug("elaborazione/aggregazione soggetti Fonte Riferimento");
					//Trovo il valore del criterio Max da valutare per la fonte
					int criterioMaxFonte = this.getCriterioMax(paramFonteRif.get(0));
					
					//Effettuo il controllo di associazione dei soggetti di Riferimento			
					rieseguiQuery = this.elabora(rsRiesegui, criterioMaxFonte, codEnte, paramConfBean);
					
					log.debug(" aggregazioneAltreFonti ciclica, totNumRecAggiornati : " +  totNumRecAggiornati);

					log.debug(" aggregazioneAltreFonti ciclica,  totNumRecNonAgganciati : " +  totNumRecNonAgganciati);

				}finally{
					DbUtils.close(rsRiesegui);
					DbUtils.close(prsRiesegui);
				}
			}
			log.debug("inserisco tutti i soggetti della fonte di riferimento che non hanno trovato aggancio");
			inserisciNonAgganciatiRif(paramFonteRif.get(0));
						
			//Commit Fonte Riferimento			
			conn.commit();
			log.debug("##################### Fonte di Riferimento Aggregata ###########################");
	
		}catch (Exception e) {
			log.error("Errore in Aggregazione Soggetti Riferimento: " ,e);
			throw new Exception("Errore in Aggregazione Soggetti Riferimento: " + e.getMessage());
		}
	}
	
			
	/**
	 * Metodo che effettua l'aggregazione Soggetti delle altre fonti caricate
	 */
	protected void aggregazioneAltreFonti(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
		
		
		try{			
			
			boolean rieseguiQuery = true;
			while (rieseguiQuery) {
				rsRiesegui = null;
				prsRiesegui = null;
				
				try {
					//Effettuo il caricamento della lista dei TOTALI da aggregare per la fonte data						
					this.caricaDatiDaAggregare(paramFonteRif.get(0));
					log.debug("rieseguiQuery, tirati fuori record da elaborare");
					//Effettuo il controllo di associazione dei soggetti per la fonte data 		
					rieseguiQuery = this.elaboraAltreFonti(rsRiesegui, codEnte, paramConfBean);
					
					
					log.debug(" aggregazioneAltreFonti ciclica, totNumRecAggiornati : " +  totNumRecAggiornati);

					log.debug(" aggregazioneAltreFonti ciclica,  totNumRecNonAgganciati : " +  totNumRecNonAgganciati);

				}finally{
					DbUtils.close(rsRiesegui);
					DbUtils.close(prsRiesegui);
				}
			}
			
			log.debug("inserisco tutti i soggetti della fonte che non hanno trovato aggancio");
			inserisciNonAgganciati();
							
			conn.commit();
			log.debug("##################### Fonti Aggregate ###########################");
			
		}catch (Exception e) {
			log.error("Errore in Aggregazione Altri Soggetti : ",e);
			throw new Exception("Errore in Aggregazione Altri Soggetti : " + e.getMessage());
		}
	}
	
	/**
	 * Metodo per il recupero del file criteriValutabili.properties contenente i criteri valutabili per le fonti 
	 */	
	protected int getCriterioMax(Object[] fonte) throws Exception {
		       
		String propName = "";
        try{
	        
	        BigDecimal ente = (BigDecimal)fonte[0];
	        BigDecimal progr = (BigDecimal)fonte[1];
	        
	        propName = "criterio." + ente + "." + progr;
	        
	        String p = this.propCrit.getProperty(propName);
			
	        int ratingMax = 0;
	        if(p!=null){
	        	ratingMax = Integer.parseInt(p);
	        }
	        
	        return ratingMax;
	        
        }catch (Exception e) {
        	log.error("Eccezione in caricamento percentuale criterio massimo per " + propName + ": " + e.getMessage(), e);
        	throw new Exception("Eccezione in caricamento percentuale criterio massimo per " + propName + ": " + e.getMessage());
        }finally {
		}  				
	}
	
	
	/**
	 * Metodo che pulisce la tabella dell'unico dopo l'associazione. 
	 * Vengono eliminati i record di UNICO che non hanno un corrispondente in TOTALE e che hanno il
	 * campo VALIDATO diverso da 1 (validato = 1 sta a significare che il dato UNICO è stato inserito a mano da interfaccia)
	 */
	protected void pulisci() throws Exception{
		
		try{			
			
			String sqlDeleteUnico = getProperty("SQL_PULISCI_SOGGETTI_UNICO");
			
			PreparedStatement ps = conn.prepareStatement(sqlDeleteUnico);			
			ps.execute();
			ps.close();
			
			String sqlFlagRuntime = getProperty("SQL_RESET_FLAG_RUNTIME_SOGGETTO");
			
			ps = conn.prepareStatement(sqlFlagRuntime);			
			ps.execute();
			ps.close();
			
			
			
			
		}catch (Exception e) {
			log.error("Errore nella cancellazione elementi da tabella UNICO ",e);
			throw new Exception("Errore nella cancellazione elementi da tabella UNICO :" + e.getMessage());
		}
		
	}
	

	/**
	 * Metodo che carica la lista dei soggetti collegati al record corrente
	 */
	protected List<SitSoggettoTotale>getListaSoggettiAssociati(Connection connessione, String sql,Object p[]) throws Exception{
		
		PreparedStatement prepStat = null;
		ResultSet resSet = null;
		List<SitSoggettoTotale> lista = new ArrayList<SitSoggettoTotale>();
		SitSoggettoTotale soggettoTotale = new SitSoggettoTotale();
		
		try{
			
			prepStat = conn.prepareStatement(sql);

			for(int i=0; i<p.length; i++){
				prepStat.setObject(i+1, p[i]);	
			}
			
			
			// prevenzione dell'errore allocLargeObjectOrArray   
			prepStat.setFetchSize(200);
			
			resSet = prepStat.executeQuery();
			
			while (resSet.next()) {
				soggettoTotale = this.mappingSoggetto(resSet);
				lista.add(soggettoTotale);
			}
			
		}catch (Exception e) {
			log.error(sql);
			if (p!=null) {
				for(int i=0; i<p.length; i++){
					log.error("Parametro" + i +":" +  p[i]);	
				}
			}
			log.error("Errore in lettura lista Soggetti Associati all'elemento Corrente: ",e);
			
			throw new Exception("Errore in lettura lista Soggetti Associati all'elemento Corrente: " + e.getMessage());
		}finally{
			if(prepStat!=null){
				prepStat.close();
			}
			if(resSet!=null){
				resSet.close();
			}
		}
	
							
		return lista;
	}
	
	
	
	/**
	 * Metodo che recupera il criterio lasco
	 */
	protected boolean recuperoCriterioLasco(String codEnte, Hashtable<String, Object> hashCriterioLasco) throws Exception{
		
		boolean critLascoDis = true;
			
		try{
			
			if(hashCriterioLasco!=null){
								
				String parCritLasc = (String)hashCriterioLasco.get("criterio.lasco.soggetti");
											
				if(parCritLasc!=null){
					
					if(parCritLasc.equals("0")){
						critLascoDis = true;
					}else{
						critLascoDis = false;
					}					
				}	
			}												
			
		}catch (Exception e) {
			log.error("Errore in recupero criterio Lasco Soggetti: ",e);
			throw new Exception("Errore in recupero criterio Lasco Soggetti: " + e.getMessage());
		}
		
		return critLascoDis;
		
	}

	@Override
	public void setConnectionForLongResultset(
			Connection connectionForLongResultset) throws AggregatoreException {
		
		this.connectionForLongResultset = connectionForLongResultset;
		
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		Connection connection = null;
		try
		{
			// This is the JDBC driver class for Oracle database
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// We use an Oracle express database for this example
			String url = "jdbc:oracle:thin:@ROMA:1521:DBCAT";
			
			// Define the username and password for connection to
			// our database.
			String username = "AM_F704_GIT";
			String password = "AM_F704_GIT";
			
			// To delete a table from database we use the DROP TABLE
			// command and specify the table name to be dropped
			String sql = "SELECT * FROM AM_FONTE";		

			// Connect to database
			connection = DriverManager.getConnection(url, username, password);
			// Create a statement
			PreparedStatement statement = connection.prepareStatement(sql);
			// Execute the statement to delete the table
			ResultSet RS = statement.executeQuery();
			
			statement.close();
			
			if(RS.next())
				System.out.println(RS.getObject(1));
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}
		
}
