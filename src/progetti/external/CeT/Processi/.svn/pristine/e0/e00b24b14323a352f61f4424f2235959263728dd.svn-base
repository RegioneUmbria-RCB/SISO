package it.webred.ct.proc.ario.aggregatori;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.data.model.indice.SitCivicoUnico;
import it.webred.ct.data.model.indice.SitOggettoTotale;
import it.webred.ct.data.model.indice.SitOggettoUnico;
import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.exception.RulEngineException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;

public class AggregatoreOggetti extends TipoAggregatore
{
	

	private static final Logger	log	= Logger.getLogger(AggregatoreOggetti.class.getName());
	private Connection conn = null;
	QueryRunner run = new QueryRunner();
	
	// questa hashmap viene svuotata ad ogni fonte nel metodo elabora
	HashMap<String,Object> elaborati=  new HashMap<String, Object>(); 

	private static Properties propOggetti = null;
	private static Properties propCrit = null;
	
	private static ArrayList<Object[]> paramFonteRif;
	
	private long totNumRecAggiornati = 0;
	private long numRecAggiornati = 0;
	

	
	//Costruttore
	public AggregatoreOggetti(){
		//Caricamento del file di properties aggregatoreCivici	
		this.propOggetti = new Properties();		
		this.propCrit = new Properties();
		try {
			InputStream is = this.getClass().getResourceAsStream("/it/webred/ct/proc/ario/aggregatori/aggregatoreOggetti.properties");
		    this.propOggetti.load(is); 
		    
		    InputStream isCrit = this.getClass().getResourceAsStream("/it/webred/ct/proc/ario/aggregatori/criteriValutabiliOggetti.properties");
            this.propCrit.load(isCrit); 		
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage(), e);			   
		}
		
	}
	
	
	public void aggrega(String codEnte, HashParametriConfBean paramConfBean) throws AggregatoreException
	{

		
		try
		{
			
			log.info("####################### INIZIO Elaborazione controllo associabilità Oggetti in UNICO #################################");
			
			paramFonteRif = (ArrayList<Object[]>) run.query(conn,getProperty("SQL_FONTE_PROGRESSIVO_RIF_OGGETTI"), new ArrayListHandler());
									
			/**
			 * CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO				
			 */
			if (paramFonteRif == null)
				log.info("!!!!! ERRORE: FONTE DI RIFERIMENTO OGGETTI NON TROVATA !!!!! continuo...");
			else{
				log.info("INIZIO CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO OGGETTI");
				this.aggregazioneFonteRiferimento(paramFonteRif.get(0), codEnte, paramConfBean);
				log.info("FINE CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO OGGETTI");
			}
			/**
			 * CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI
			 */
			log.info("INIZIO CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI OGGETTI");
			this.aggregazioneAltreFonti(codEnte,paramConfBean);
			log.info("FINE CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI OGGETTI");
					

			//Pulizia tabella UNICO (cancello tutti i record non collegati a totale e Validato <> 1)
			log.info("INIZIO cancellazione elementi in OGGETTO UNICO");
			this.pulisciUnico();
			log.info("FINE cancellazione elementi in OGGETTO UNICO");			
			
		
			log.info("####################### FINE Elaborazione controllo associabilità Oggetti in UNICO #################################");			
						
		}
		catch (Exception e)
		{
			
			log.error("Errore:AggregatoreOGGETTO "+e.getMessage(), e);
			AggregatoreException ea = new AggregatoreException(e.getMessage());
			throw ea;
		}
		finally
		{


		}

	}

	
	public void setConnection(Connection conn){
		this.conn = conn;
	}
	
	
	/**
	 * Metodo che effettua l'aggregazione Soggetti per la Fonte di Riferimento
	 */
	protected void aggregazioneFonteRiferimento(Object[] fonte, String codEnte, HashParametriConfBean paramConfBean) throws Exception{
		
		ResultSet rsOggRifTotDaAggr = null;
		
		try{
			
			//Effettuo il caricamento della lista dei TOTALI da aggregare per la fonte di riferimento						
			rsOggRifTotDaAggr = this.caricaDatiDaAggregareFonteRif();
													
			log.debug("elaborazione/aggregazione Oggetti Fonte Riferimento");
			//Trovo il valore del criterio Max da valutare per la fonte
			int criterioMaxFonte = this.getCriterioMax(paramFonteRif.get(0));
			
			//Effettuo il controllo di associazione dei soggetti di Riferimento			
			this.elabora(rsOggRifTotDaAggr, criterioMaxFonte, codEnte, paramConfBean);
			
			log.debug("inserisco tutti gli Oggetti della fonte di riferimento che non hanno trovato aggancio");
			inserisciNonAgganciatiRif(paramFonteRif.get(0));
						
			//Commit Fonte Riferimento			
			conn.commit();
			log.debug("##################### Fonte di Riferimento Aggregata ###########################");
			
		}catch (Exception e) {
			throw new Exception("Errore in Aggregazione Oggetti Riferimento: " + e.getMessage());
		}finally{
			DbUtils.close(rsOggRifTotDaAggr);
		}
	}
	
	
	/**
	 * Metodo che effettua l'aggregazione Soggetti delle altre fonti caricate
	 */
	protected void aggregazioneAltreFonti(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
		

		
		try{
					
			boolean rieseguiQuery = true;
			

			
			int ripetizioni = 0;
			
			while (rieseguiQuery) {

				PreparedStatement prs = null;
				
				// Leggo Oggetto TOTALE	
				String sqlOggettoTotale = getProperty("SQL_RICERCA_RIGHE_OGG");
				
				BigDecimal enteRif = (BigDecimal)paramFonteRif.get(0)[0];
				BigDecimal progRif = (BigDecimal)paramFonteRif.get(0)[1];
				
				prs = connectionForLongResultset.prepareStatement(sqlOggettoTotale);
				prs.setBigDecimal(1, enteRif);
				prs.setBigDecimal(2, progRif);		
				
				
				ResultSet rsOggDaAggr = null;
				try {
					
					
					log.info("Ripetizione query " + ripetizioni);
					
					//Effettuo il caricamento della lista dei TOTALI da aggregare per la fonte data	
					rsOggDaAggr = prs.executeQuery();	
						
					//rsOggDaAggr = this.caricaDatiDaAggregare(paramFonteRif.get(0), prs, rsOggDaAggr);
					
					//Effettuo il controllo di associazione degli oggetti per la fonte data 		
					rieseguiQuery = this.elaboraAltreFonti(rsOggDaAggr, codEnte, paramConfBean);
					
					ripetizioni ++;
					
				}catch (Exception e) {
					throw new Exception("Errore in Caricamento Dati da Aggregare da Altre Fonti : " + e.getMessage());
				}finally{
					DbUtils.close(rsOggDaAggr);
					DbUtils.close(prs);
				}
			}
			
			log.debug("inserisco tutti gli oggetti della fonte che non hanno trovato aggancio");
			inserisciNonAgganciati();
							
			conn.commit();
			log.debug("##################### Fonti Aggregate ###########################");
			
		}catch (Exception e) {
			throw new Exception("Errore in Aggregazione Altri Oggetti : " + e.getMessage());
		}
	}
	
	
	/**
	 * Metodo che pulisce la tabella dell'unico dopo l'associazione. 
	 * Vengono eliminati i record di UNICO che non hanno un corrispondente in TOTALE e che hanno il
	 * campo VALIDATO diverso da 1 (validato = 1 sta a significare che il dato UNICO è stato inserito a mano da interfaccia)
	 */
	protected void pulisciUnico() throws Exception{
		
		try{			
			
			String sqlDeleteUnico = getProperty("SQL_PULISCI_OGGETTI_UNICO");
			
			PreparedStatement ps = conn.prepareStatement(sqlDeleteUnico);			
			ps.execute();
			ps.close();
			
		}catch (Exception e) {
			throw new Exception("Errore nella cancellazione elementi da tabella UNICO :" + e.getMessage());
		}
		
	}
	
	
	/**
	 * Metodo che carica i soggetti dalla TOTALE da dover aggregare per la fonte indicata
	 */
	protected ResultSet caricaDatiDaAggregareFonteRif() throws Exception{
		
		PreparedStatement prs = null;
		ResultSet rs = null;
				
		try {
		
			// Leggo Oggetto TOTALE	
			String sqlSoggettoTotale = getProperty("SQL_LEGGI_OGGETTO_TOTALE_FONTE_RIF");
			
						
			prs = connectionForLongResultset.prepareStatement(sqlSoggettoTotale);
			
			BigDecimal enteRif = (BigDecimal)paramFonteRif.get(0)[0];
			BigDecimal progRif = (BigDecimal)paramFonteRif.get(0)[1];
						
			prs.setBigDecimal(1, enteRif);
			prs.setBigDecimal(2, progRif);				
			
			rs = prs.executeQuery();				
			
			return rs;
			
		} catch (Exception e) {
			throw new Exception ("Errore in caricamento dati da tabella SIT_OGGETTO_UNICO "+e.getMessage());
		} finally {			
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
	 * Metodo che aggrega gli oggetti
	 */
	private void  elabora(ResultSet resOgg, int critMaxFonte, String codEnte, HashParametriConfBean paramConfBean) throws Exception
	{
		
		try {
			
			int i = 0;
			while(resOgg.next()){
				
				Object fk = null;
									
				SitOggettoTotale oggCorr = this.mappingOggetto(resOgg);																								
				
				if(fk==null){
																									
					String fonte = String.valueOf(oggCorr.getId().getFkEnteSorgente());										
					String progEs = String.valueOf(oggCorr.getId().getProgEs());
										
					String sezOk = this.sezioneInAggregazione("sezione.in.aggregazione", codEnte, fonte, progEs, paramConfBean.getSezioneInAggrOgg());
					
					/*String chiave = "criterio.sezione."+ oggCorr.getId().getFkEnteSorgente()+"."+oggCorr.getId().getProgEs();
					String sezOk = propOggetti.getProperty(chiave);*/
					
					if(sezOk.equals("true")){
						//Controlla Criterio A100 (Sezione, Foglio, Particella, Subalterno)															
						fk = foglioSezPartSubRicerca(oggCorr,fk,critMaxFonte);
					}else{
						//Controlla Criterio A100 (Foglio, Particella, Subalterno)															
						fk = foglioPartSubRicerca(oggCorr,fk,critMaxFonte);
					}
				}	
				
				//Effettuo la commit dei dati su DB
				i++;
				if(i > 100000){
					i=0;
					conn.commit();
				}
			}
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			throw new Exception("Errore in Aggregazione OGGETTI Riferimento: " + e.getMessage());
		}finally {			
		}

	}
	
	
	
	/**
	 * Metodo che effettua il mapping di un Oggetto Totale letto
	 */
	protected SitOggettoTotale mappingOggetto(ResultSet rs) throws Exception{
		
		SitOggettoTotale sot = new SitOggettoTotale();				
		IndicePK iPk = new IndicePK();
		
				
		iPk.setIdDwh(rs.getString("IDDWH"));
		iPk.setFkEnteSorgente(rs.getInt("ENTESORGENTE"));
		iPk.setProgEs(rs.getInt("PROGES"));
		iPk.setCtrHash(rs.getString("CTRHASH"));
		
		sot.setId(iPk);
			
		
		sot.setFoglio(rs.getString("FOGLIO"));
		sot.setParticella(rs.getString("PARTICELLA"));
		sot.setSub(rs.getString("SUB"));
		sot.setCategoria(rs.getString("CATEGORIA"));
		sot.setClasse(rs.getString("CLASSE"));
		sot.setRendita(rs.getString("RENDITA"));
		sot.setZona(rs.getString("ZONA"));
		sot.setFoglioUrbano(rs.getString("FOGLIO_URBANO"));
		sot.setSuperficie(rs.getString("SUPERFICIE"));
		sot.setScala(rs.getString("SCALA"));
		sot.setInterno(rs.getString("INTERNO"));
		sot.setPiano(rs.getString("PIANO"));
		sot.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
		sot.setDtFineVal(rs.getDate("DT_FINE_VAL"));
		sot.setIdStorico(rs.getString("ID_STORICO"));
		sot.setNote(rs.getString("NOTE"));
		sot.setRating(rs.getBigDecimal("RATING"));
		sot.setRelDescr(rs.getString("REL_DESCR"));
		sot.setFkOggetto(rs.getBigDecimal("FK_OGGETTO"));
		sot.setIdStorico(rs.getString("ID_STORICO"));
		sot.setCodiceOggOrig(rs.getString("CODICE_OGGETTO"));
		sot.setAnomalia(rs.getString("ANOMALIA"));
		sot.setValidato(rs.getBigDecimal("VALIDATO"));
		sot.setStato(rs.getString("STATO"));				
		sot.setSezione(rs.getString("SEZIONE"));	
		
			
		return sot;
		
	}
	
	
	
	/**
	 * Metodo che effettua l'inserimento/associazione dei soggetti di riferimento non agganciati
	 */
	private void inserisciNonAgganciatiRif(Object[] fonte) throws AggregatoreException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		SitOggettoTotale sot = null;
		
		try {
			
			ps = conn.prepareStatement(getProperty("SQL_RICERCA_RIGHE_NON_AGGANCIATE_OGG_RIF"));
			ps.setObject(1, fonte[0]);
			ps.setObject(2, fonte[1]);
			rs = ps.executeQuery();
			while (rs.next()) {			
				sot = this.mappingOggetto(rs);
				salvaNonAgganciati(sot, 0, 0);
			}
		} catch (Exception e) {
				
				log.error("Errore:AggregatoreOggetti inserisciNonAgganciati "+e.getMessage() ,e);
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
	 * Metodo che effettua l'inserimento dei soggetti che non hanno trovato aggancio
	 */
	private void salvaNonAgganciati(SitOggettoTotale sot, int rating, int attendibilita) throws Exception{
		
			Object fk = null;
			QueryRunner run = new QueryRunner();
			Object pp[] = new Object[8];
			
			Object nuovoId = new Object(); 
			nuovoId = ((Map) run.query(conn,getProperty("SQL_NEXT_UNICO_OGG"), new MapHandler())).get("ID");								
			pp[0] = nuovoId;
			fk  = nuovoId;			
			
			pp[1] = sot.getFoglio();
			pp[2] = sot.getParticella();
			pp[3] = sot.getSub();								
			pp[4] = 0; 		//validato
			pp[5] = sot.getCodiceOggOrig();
			pp[6] = sot.getSezione();
			pp[7] = rating;
			
			run.update(conn,getProperty("SQL_INSERISCI_OGGETTO_UNICO"),pp);
			
			
			//  aggiorno il record di totale che ho appena inserito in unico
			pp = new Object[8];			
			pp[0] = fk;
			pp[1] = rating;
			pp[2] = "NATIVA";
			pp[3] = attendibilita;
			pp[4] = sot.getId().getFkEnteSorgente();
			pp[5] = sot.getId().getProgEs();
			pp[6] = sot.getId().getCtrHash();
			pp[7] = sot.getId().getIdDwh();
						
			run.update(conn,getProperty("SQL_UPDATE_OGGETTO_TOTALE_FK"),pp);					
	}
	
	
	
	/*Criterio A100*/
	private Object foglioPartSubRicerca(SitOggettoTotale sot, Object fk, int critMaxFonte) throws Exception {
		
		//QueryRunner run = new QueryRunner();
		//BeanListHandler h = new BeanListHandler(SitOggettoTotale.class);
		
		//Imposto Rating e RelDescr in base alla tipologia di fonte
		int ratingCriterio = Integer.parseInt(propOggetti.getProperty("rating.cao.a100"));
		String relDescr = propOggetti.getProperty("descr.cao.a100");
				
		
		Object p[] = new Object[7];
		p[0]=sot.getFoglio();
		p[1]=sot.getParticella();
		p[2]=sot.getSub();								
		
		if ("0000".equals(sot.getFoglio()) || "00000".equals(sot.getParticella()) || "0000".equals(sot.getSub()) ) 
			return fk;
		if (p[0]==null || p[1]==null || p[2]==null) 
			return fk;		
		
		if (isZero((String)p[2])){
			log.warn("Impossibile valutare se sub Zero " + p[0]+" "+p[1]+" "+p[2]);
			return fk;
		} 
		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		Object trovato = elaborati.get(k);
		if (trovato!=null){
			return aggiorna(null, sot, critMaxFonte, ratingCriterio, relDescr, trovato);
		}

		p[3]=sot.getId().getFkEnteSorgente();
		p[4]=sot.getId().getProgEs();
		p[5]=sot.getId().getCtrHash();
		p[6]=sot.getId().getIdDwh();
				
		
		String sqlRicOggTotFoglioPartSub = getProperty("SQL_RICERCA_OGGETTO_TOTALE_FOGLIOPARTSUB");		
		List<SitOggettoTotale> lOggTotale = this.getListaOggettiAssociati(conn, sqlRicOggTotFoglioPartSub, p);			
		
		if (lOggTotale.size()>0) {						
			fk =  aggiorna(lOggTotale, sot, critMaxFonte, ratingCriterio, relDescr, fk);
		}
				
		return fk;
		
	}
	
	
	/*Criterio A100 con gestione SEZIONE*/
	private Object foglioSezPartSubRicerca(SitOggettoTotale sot, Object fk, int critMaxFonte) throws Exception {
		
		//QueryRunner run = new QueryRunner();
		//BeanListHandler h = new BeanListHandler(SitOggettoTotale.class);
		
		//Imposto Rating e RelDescr in base alla tipologia di fonte
		int ratingCriterio = Integer.parseInt(propOggetti.getProperty("rating.cao.a100"));
		String relDescr = propOggetti.getProperty("descr.cao.a100");;
				
		
		Object p[] = new Object[8];
		p[0]=sot.getFoglio();
		p[1]=sot.getParticella();
		p[2]=sot.getSub();								
		p[3]=sot.getSezione();
		
		if ("0000".equals(sot.getFoglio()) || "00000".equals(sot.getParticella()) || "0000".equals(sot.getSub()) ) 
			return fk;
		
		if (p[0]==null || p[1]==null || p[2]==null || p[3]==null) 
			return fk;
		
		if (isZero((String)p[2])){
			log.warn("Impossibile valutare se sub Zero " + p[0]+" "+p[1]+" "+p[2]);
			return fk;
		}
		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		Object trovato = elaborati.get(k);
		if (trovato!=null){
			return aggiorna(null, sot, critMaxFonte, ratingCriterio, relDescr, trovato);
		}

		p[4]=sot.getId().getFkEnteSorgente();
		p[5]=sot.getId().getProgEs();
		p[6]=sot.getId().getCtrHash();
		p[7]=sot.getId().getIdDwh();
				
		
		String sqlRicOggTotSezFoglioPartSub = getProperty("SQL_RICERCA_OGGETTO_TOTALE_SEZFOGLIOPARTSUB");		
		List<SitOggettoTotale> lOggTotale = this.getListaOggettiAssociati(conn, sqlRicOggTotSezFoglioPartSub, p);			
		
		if (lOggTotale.size()>0) {						
			fk =  aggiorna(lOggTotale, sot, critMaxFonte, ratingCriterio, relDescr, fk);
		}
				
		return fk;
		
	}
	
	
	/**
	 * Metodo che carica la lista dei soggetti collegati al record corrente
	 */
	protected List<SitOggettoTotale>getListaOggettiAssociati(Connection connessione, String sql,Object p[]) throws Exception{
		
		PreparedStatement prepStat = null;
		ResultSet resSet = null;
		List<SitOggettoTotale> lista = new ArrayList<SitOggettoTotale>();
		SitOggettoTotale oggettoTotale = new SitOggettoTotale();
		
		try{
			
			prepStat = conn.prepareStatement(sql);

			for(int i=0; i<p.length; i++){
				prepStat.setObject(i+1, p[i]);	
			}
			
			resSet = prepStat.executeQuery();
			
			while (resSet.next()) {
				oggettoTotale = this.mappingOggetto(resSet);
				lista.add(oggettoTotale);
			}
			
		}catch (Exception e) {
			throw new Exception("Errore in lettura lista Ooggetti Associati all'elemento Corrente: " + e.getMessage());
		}finally{
				DbUtils.close(prepStat);
				DbUtils.close(resSet);
		}				
		return lista;
	}
	
	
	
	/**
	 * Metodo che aggrega gli oggetti in unico
	 */
	
	/**
	 * Metodo che aggiorna i civici in Totale e Unico	
	 */
	private Object aggiorna(List<SitOggettoTotale> lOggTotale , SitOggettoTotale sot, int critMaxFonte, int ratingCritValutato, String relDescr, Object fk) throws Exception {
		
			//se è già stato elaborato
			if(lOggTotale ==null && fk != null){
				this.aggiornaTotaleCorrente(sot, critMaxFonte, ratingCritValutato, relDescr, fk);
				return fk;
			}

			QueryRunner run = new QueryRunner();		
			int ratingEleTotale = 0;			
			Object fkRs = new Object(); 
			fkRs = null;																
			
			//Controllo se l'elemento è collegato ad una UNICO e quinfi possiede un fk
			try {
				// LETTURA DELL'fk_civico del record che sto scorrendo
				Object pFK[] = new Object[4];
				pFK[0] = sot.getId().getFkEnteSorgente();
				pFK[1] = sot.getId().getProgEs();
				pFK[2] = sot.getId().getCtrHash();
				pFK[3] = sot.getId().getIdDwh();
				fkRs  = ((Map) run.query(conn,getProperty("SQL_SELECT_OGGETTO_TOTALE_FK"),pFK, new MapHandler())).get("FK_OGGETTO");								
			} catch (Exception e) {
				log.warn("Problemi reperimento fk per record " + sot.getId().getIdDwh(), e);
			}
																									
			if (fkRs==null){
				
				//Cerco l'fk con rating più alto degli elementi collegati
				for (SitOggettoTotale c : lOggTotale){										
					
					if(c.getFkOggetto()!=null){
						
						if(c.getRating() != null){
							ratingEleTotale = Integer.parseInt(c.getRating().toString());
						}else{
							ratingEleTotale = 0;
						}
						//il rating per gli oggetti è sempre 100, non c'è bisogno di valutarlo maggiore
						if(ratingEleTotale >= ratingCritValutato){
							fkRs = c.getFkOggetto();
							break;
						}						
					}
				}
			
				if(fkRs == null)
					fk =  inserisci(sot, ratingCritValutato);
			}else
				fk =fkRs;
			
				
			
			//Aggiorna Rec Tot Corrente nel caso di nuovo inserimento in Unico
			if (fk!=null && fkRs==null) {	
				//TODO errore
				this.aggiornaNuovoTotaleUnico(sot,fk,critMaxFonte,ratingCritValutato);			
			}else{	
				//TODO
				//1)Leggo l'elemento di Unico avente chiave=fkRs
				SitOggettoUnico oggUni = this.getOggettoUnico(fkRs.toString());
																			
				//2)Controllo se il rating di questo unico è < di rating.								
				//  Se minore allora aggiorno il dato unico con i dati del record corrente
				//	Altrimenti non fa nulla							
				if(Integer.parseInt(oggUni.getRating().toString()) < ratingCritValutato){					
					this.aggiornaUnico(oggUni, sot, ratingCritValutato);
				}
								
				//3)Controllo sul TOTALE: se il rating dell'elemento corrente < rating --> aggiorno il rating dell'elemento corrente in totale				
				if(sot.getRating() == null ){
					
					BigDecimal val = new BigDecimal(0);
					sot.setRating(val);
				}				
				if(sot.getRating().intValue() < ratingCritValutato){				
					this.aggiornaTotaleCorrente(sot, critMaxFonte, ratingCritValutato, relDescr, fkRs);	
				}
							
				
			}
			
				
			if(fkRs != null){
				fk = fkRs;
			}
			
			// tanto che ci sono aggiorno anche quelli che ho trovato 
			//Aggiorno gli altri elementi evenhtualemente collegati
			for (SitOggettoTotale c : lOggTotale)
			{
				
				//Controllo sul rating
				if(c.getRating() != null){
					ratingEleTotale = Integer.parseInt(c.getRating().toString());
				}else{
					ratingEleTotale = 0;
				}
				
				if (( c.getFkOggetto()==null || 
					  !c.getFkOggetto().toString().equals(fk.toString()) ||
					  !relDescr.equals(c.getRelDescr())) &&					  
					  (ratingEleTotale< ratingCritValutato)) {
					
					
					this.aggiornaTotaleCorrente(c, critMaxFonte, ratingCritValutato, relDescr, fk);
					
					// cambio anche l'fk a tutti quelli che avevano lo stesso fk di quello che sto scorrendo
					// un po' azzardato ma mi piace collegarne il piu' possibile
					if (c.getFkOggetto()!=null && !c.getFkOggetto().toString().equals(fk.toString())) {						
						this.cambioFkCorrelati(c.getFkOggetto(), fk);					
					}
							
				}
			}

			return fk;
	}
	
	
	/**
	 * Inserimento in UNICO
	 */
	private Object inserisci(SitOggettoTotale sot, int rating) throws Exception
	{
		
		Object fk = null;
		QueryRunner run = new QueryRunner();
		Object pp[] = new Object[8];
		
		Object nuovoId = new Object(); 
		nuovoId = ((Map) run.query(conn,getProperty("SQL_NEXT_UNICO_OGG"), new MapHandler())).get("ID");								
		pp[0] = nuovoId;
		fk  = nuovoId;			
		
		pp[1] = sot.getFoglio();
		pp[2] = sot.getParticella();
		pp[3] = sot.getSub();								
		pp[4] = 0; 		//validato
		pp[5] = sot.getCodiceOggOrig();
		pp[6] = sot.getSezione();
		pp[7] = rating;
		
		run.update(conn,getProperty("SQL_INSERISCI_OGGETTO_UNICO"),pp);
				

		return fk;
	}
	
	
	
	/**
	 * Metodo che aggiorna l'elemento della OGGETTO TOTALE appena inserito in UNICO
	 */
	protected void aggiornaNuovoTotaleUnico(SitOggettoTotale sot, Object fk, int critMaxFonte, int ratingCritValutato) throws Exception{
		
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
			pp[4] = sot.getId().getFkEnteSorgente();
			pp[5] = sot.getId().getProgEs();
			pp[6] = sot.getId().getCtrHash();
			pp[7] = sot.getId().getIdDwh();
			
			run.update(conn,getProperty("SQL_UPDATE_OGGETTO_TOTALE_FK"),pp);
			
		}catch (Exception e) {
			throw new Exception("Errore in salvataggio elemento Corrente Oggetto Totale: "+ e.getMessage());
		}					
	}
	
	
	/**
	 * Metodo che cerca l'oggetto in UNICO
	 */
	protected SitOggettoUnico getOggettoUnico(String codOgg) throws Exception {
						
		SitOggettoUnico ou = new SitOggettoUnico();
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_OGGETTO_UNICO");
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, codOgg);
			
			rs = ps.executeQuery();

			if(rs.next()){
				this.mappingOggettoUnico(rs, ou);
			}	
									
			
		}catch (Exception e) {
			throw new Exception("Errore nella ricerca della civico UNICO per codice :" + e.getMessage());
		}finally{
			DbUtils.close(rs);
			DbUtils.close(ps);
		}

		return ou;
		
	}
	
	
	/**
	 * Metodo che effettua il mapping di un oggetto Unico 
	 */
	protected SitOggettoUnico mappingOggettoUnico(ResultSet rs, SitOggettoUnico ou) throws Exception{
							
		ou.setIdOggetto(rs.getInt("ID_OGGETTO"));
		ou.setFoglio(rs.getString("FOGLIO"));
		ou.setParticella(rs.getString("PARTICELLA"));
		ou.setSub(rs.getString("SUB"));
		ou.setDtIns(rs.getDate("DT_INS"));
		ou.setValidato(rs.getBigDecimal("VALIDATO"));
		ou.setCodiceOggOrig("CODICE_OGGETTO");				
		ou.setSezione(rs.getString("SEZIONE"));								
		ou.setRating(rs.getBigDecimal("RATING"));
		
		return ou;
		
	}
	
	
	/**
	 * Metodo che aggiorna gli oggetto in Unico con uno più valido della Totale
	 */
	protected void aggiornaUnico(SitOggettoUnico sou, SitOggettoTotale sot, int rating) throws Exception{
		
		PreparedStatement ps = null;
		
		try{
			
			String sql = getProperty("SQL_AGGIORNA_OGGETTO_UNICO");
			
			ps = conn.prepareStatement(sql);

			ps.setString(1, sot.getFoglio());
			ps.setString(2, sot.getParticella());
			ps.setString(3, sot.getSub());
			ps.setString(4, sot.getSezione());						
			ps.setBigDecimal(5, sot.getValidato());			
			ps.setString(6, sot.getCodiceOggOrig());
			ps.setBigDecimal(7, (BigDecimal)sot.getRating());
			ps.setLong(8, sou.getIdOggetto());
						
			
			ps.executeUpdate();																
			
		}catch (Exception e) {
			throw new Exception("Errore nell'aggiornamento dell'oggetto UNICO per codice :" + e.getMessage());
		}finally{
			DbUtils.close(ps);
		}
		
	}
	
	
	/**
	 * Metodo che aggiorna l'elemento Corrente della CIVICO TOTALE
	 */
	protected void aggiornaTotaleCorrente(SitOggettoTotale sot,int critMaxFonte, int ratingCritValutato, String relDescr, Object fk) throws Exception{
		
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
			pp[4] = sot.getId().getFkEnteSorgente();
			pp[5] = sot.getId().getProgEs();
			pp[6] = sot.getId().getCtrHash();
			pp[7] = sot.getId().getIdDwh();
			run.update(conn,getProperty("SQL_UPDATE_OGGETTO_TOTALE_FK"),pp);
			
			numRecAggiornati = numRecAggiornati +1;
			if (numRecAggiornati>= 1000) 
			{
				totNumRecAggiornati = totNumRecAggiornati + numRecAggiornati;
				log.debug("Aggiornamento record " + totNumRecAggiornati) ;
				numRecAggiornati = 0;
			}
			
		}catch (Exception e) {
			throw new Exception("Errore in salvataggio elemento Corrente Oggetto Totale: "+ e.getMessage());
		}					
	}
	
	
	/**
	 * Metodo che cambia l'FK a tutti
	 */
	protected void cambioFkCorrelati(Object fkOggetto, Object fk) throws Exception{
		
		try{
			Object pp [] = new Object[2];
			pp[0] = fk;
			pp[1] = fkOggetto;
			run.update(conn,getProperty("SQL_UPDATE_OGGETTO_TOTALE_FK_ALL"),pp);
		}catch (Exception e) {
			throw new Exception("Errore in cambio Fk a Oggetti collegati precedentemente: " + e.getMessage());
		}	
	}
	
	
	/**
	 * Metodo che carica gli oggetti dalla TOTALE da dover aggregare per le varie fonti
	 */
	protected ResultSet caricaDatiDaAggregare(Object[] fonte, PreparedStatement prs, ResultSet rs) throws Exception{
		
		BigDecimal enteRif = (BigDecimal)fonte[0];
		BigDecimal progRif = (BigDecimal)fonte[1];
		
		try {
		
			// Leggo Oggetto TOTALE	
			String sqlOggettoTotale = getProperty("SQL_RICERCA_RIGHE_OGG");
			
		
		
			prs.setBigDecimal(1, enteRif);
			prs.setBigDecimal(2, progRif);				
			
			rs = prs.executeQuery();				
			
			return rs;
			
		} catch (Exception e) {
			throw new Exception ("Errore in caricamento dati da tabella SIT_OGGETTO_UNICO "+e.getMessage());
		} finally {	
		}				
	
	}
	
	
	/**
	 * Metodo che effettua l'elaborazione/aggregazione delle altre fonti
	 */
	private boolean elaboraAltreFonti(ResultSet resOgg, String codEnte, HashParametriConfBean paramConfBean) throws Exception
	{
			
		try {
			
			// serve per rieffetture una query ogni 100.000 record per selezionare quelli con l'fk ancora non valorizzato (che dopo 50.000 soggetti elaborati saranno pochi)
			boolean rieseguiQuery = false;
			boolean hoAggregato = false;
			
			int i = 0;
			while(resOgg.next() && !rieseguiQuery){
				
				Object fk = null;
							
				//Trovo il valore del criterio Max da valutare per la fonte
				Object[] paramFonte = new Object[2];				
				paramFonte[0] = resOgg.getBigDecimal("ENTESORGENTE");
				paramFonte[1] = resOgg.getBigDecimal("PROGES");
				int critMaxFonte = this.getCriterioMax(paramFonte);

				
				SitOggettoTotale oggCorr = this.mappingOggetto(resOgg);
						
				if(fk==null){
										
					String fonte = String.valueOf(oggCorr.getId().getFkEnteSorgente());										
					String progEs = String.valueOf(oggCorr.getId().getProgEs());
										
					String sezOk = this.sezioneInAggregazione("sezione.in.aggregazione", codEnte, fonte, progEs, paramConfBean.getSezioneInAggrOgg());
				
					
					if(sezOk.equals("true")){
						//Controlla Criterio A100 (Sezione, Foglio, Particella, Subalterno)															
						fk = foglioSezPartSubRicerca(oggCorr,fk,critMaxFonte);
					}else{
						//Controlla Criterio A100 (Foglio, Particella, Subalterno)															
						fk = foglioPartSubRicerca(oggCorr,fk,critMaxFonte);
					}
				}
				
				i++;
				
				hoAggregato = hoAggregato || fk!=null;
				
				//Effettuo la commit dei dati su DB
				if(i > 100000){ 
					i=0;
					conn.commit();
					rieseguiQuery = hoAggregato ? true : false;
				}
			}
			conn.commit();
			
			return rieseguiQuery;
		}catch(Exception e){
			conn.rollback();
			throw new Exception("Errore in Aggregazione OGGETTI altre fonti: " + e.getMessage());
		}finally {			
		}

	}
	
	
	/**
	 * Metodo che effettua l'inserimento/associazione degli altri oggetti non agganciati
	 */
	private void inserisciNonAgganciati() throws AggregatoreException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		SitOggettoTotale sot = null;
		
		try {
			
			ps = conn.prepareStatement(getProperty("SQL_RICERCA_RIGHE_NON_AGGANCIATE_OGG"));
			ps.setObject(1, paramFonteRif.get(0)[0]);			
			ps.setObject(2, paramFonteRif.get(0)[1]);
			rs = ps.executeQuery();
			while (rs.next()) {				
				sot = this.mappingOggetto(rs);
				salvaNonAgganciati(sot, 0, 0);
			}
		} catch (Exception e) {
				
				log.error("Errore:AggregatoreOggetti inserisciNonAgganciati "+e.getMessage() ,e);
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
	 * Metodo che legge il flag sezione in aggregazione
	 */
	protected String sezioneInAggregazione(String key, String codEnte, String fonte, String progEs, Hashtable<String, Object> sezioneInAggrOgg) throws Exception{
		
		String sezione = "false";		
			
		try{										
			
			if(fonte!=null){
				
				String chiave = key+fonte;	
								
				String parSezione = (String)sezioneInAggrOgg.get(chiave);
				
				String[] temp = null;	
				
				if(parSezione!= null){
					
					temp = parSezione.split(",");
					
					for(int i =0; i < temp.length ; i++){
						if(temp[i].equals(progEs)){
							sezione = "true";
							break;
						}				
					}	
				}
			}
																
		}catch (Exception e) {
			throw new RulEngineException("Errore in reperimento sezione in aggregazione per ente "+codEnte+" fonte "+fonte+" :"+e.getMessage());
		}
		
		return sezione; 
	}
	
	@Override
	public void setConnectionForLongResultset(
			Connection connectionForLongResultset) throws AggregatoreException {
		
		this.connectionForLongResultset = connectionForLongResultset;
		
	}
	
	

	
	
	
	
}
