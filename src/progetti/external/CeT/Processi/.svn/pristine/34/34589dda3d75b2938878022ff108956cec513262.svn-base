package it.webred.ct.proc.ario.aggregatori;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitFabbricatoTotale;
import it.webred.ct.data.model.indice.SitFabbricatoUnico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.rulengine.exception.RulEngineException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
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
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;

public class AggregatoreFabbricati extends TipoAggregatore
{
	

	private static final Logger	log	= Logger.getLogger(AggregatoreFabbricati.class.getName());
	private Connection conn = null;
	QueryRunner run = new QueryRunner();
	
	// questa hashmap viene svuotata ad ogni fonte nel metodo elabora
	HashMap<String,Object> elaborati=  new HashMap<String, Object>(); 

	private static Properties propFabbricati = null;
	private static Properties propCrit = null;
	
	private static ArrayList<Object[]> paramFonteRif;
	
	private long totNumRecAggiornati = 0;
	private long numRecAggiornati = 0;

	
	//Costruttore
	public AggregatoreFabbricati(){
		//Caricamento del file di properties aggregatoreCivici	
		this.propFabbricati = new Properties();		
		this.propCrit = new Properties();
		try {
			InputStream is = this.getClass().getResourceAsStream("/it/webred/ct/proc/ario/aggregatori/aggregatoreFabbricati.properties");
		    this.propFabbricati.load(is); 
		    
		    InputStream isCrit = this.getClass().getResourceAsStream("/it/webred/ct/proc/ario/aggregatori/criteriValutabiliFabbricati.properties");
            this.propCrit.load(isCrit); 		
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage());			   
		}
		
	}
	
	
	public void aggrega(String codEnte, HashParametriConfBean paramConfBean) throws AggregatoreException
	{

		
		try
		{
			
			log.info("####################### INIZIO Elaborazione controllo associabilità Fabbricati in UNICO #################################");
			
			paramFonteRif = (ArrayList<Object[]>) run.query(conn,getProperty("SQL_FONTE_PROGRESSIVO_RIF_FABBRICATI"), new ArrayListHandler());
									
			/**
			 * CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO				
			 */
			if (paramFonteRif == null)
				log.info("!!!!! ERRORE: FONTE DI RIFERIMENTO FABBRICATI NON TROVATA !!!!! continuo...");
			else{
				log.info("INIZIO CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO FABBRICATI" + paramFonteRif.get(0));
				this.aggregazioneFonteRiferimento(paramFonteRif.get(0), codEnte, paramConfBean);
				log.info("FINE CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO FABBRICATI");
			}
			/**
			 * CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI
			 */
			log.info("INIZIO CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI FABBRICATI");
			this.aggregazioneAltreFonti(codEnte,paramConfBean);
			log.info("FINE CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI FABBRICATI");
					

			//Pulizia tabella UNICO (cancello tutti i record non collegati a totale e Validato <> 1)
			log.info("INIZIO cancellazione elementi in FABBRICATI UNICO");
			this.pulisciUnico();
			log.info("FINE cancellazione elementi in FABBRICATI UNICO");			
			
		
			log.info("####################### FINE Elaborazione controllo associabilità Fabbricati in UNICO #################################");			
						
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Errore:AggregatoreFABBRICATI", e);
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
	 * Metodo che effettua l'aggregazione Fabbricati per la Fonte di Riferimento
	 */
	protected void aggregazioneFonteRiferimento(Object[] fonte, String codEnte, HashParametriConfBean paramConfBean) throws Exception{
		
		ResultSet rsFabbRifTotDaAggr = null;
		
		try{
			
			//Effettuo il caricamento della lista dei TOTALI da aggregare per la fonte di riferimento						
			rsFabbRifTotDaAggr = this.caricaDatiDaAggregareFonteRif();
													
			log.debug("elaborazione/aggregazione Fabbricati Fonte Riferimento");
			//Trovo il valore del criterio Max da valutare per la fonte
			int criterioMaxFonte = this.getCriterioMax(paramFonteRif.get(0));
			
			//Effettuo il controllo di associazione dei fabbricati di Riferimento			
			this.elabora(rsFabbRifTotDaAggr, criterioMaxFonte, codEnte, paramConfBean);
			
			log.debug("inserisco tutti i Fabbricati della fonte di riferimento che non hanno trovato aggancio");
			inserisciNonAgganciatiRif(paramFonteRif.get(0));
						
			//Commit Fonte Riferimento			
			conn.commit();
			log.debug("##################### Fonte di Riferimento Aggregata ###########################");
			
		}catch (Exception e) {
			throw new Exception("Errore in Aggregazione Fabbricati Riferimento: " + e.getMessage());
		}finally{
			if(rsFabbRifTotDaAggr!=null){				
				rsFabbRifTotDaAggr.close();
			}	
		}
	}
	
	
	/**
	 * Metodo che effettua l'aggregazione Fabbricati delle altre fonti caricate
	 */
	protected void aggregazioneAltreFonti(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
		
		ResultSet rsFabbDaAggr = null;
		
		try{
					
			//Effettuo il caricamento della lista dei TOTALI da aggregare per la fonte data						
			rsFabbDaAggr = this.caricaDatiDaAggregare(paramFonteRif.get(0));
							
			//Effettuo il controllo di associazione dei fabbricati per la fonte data 		
			this.elaboraAltreFonti(rsFabbDaAggr, codEnte, paramConfBean);
			
			log.debug("inserisco tutti i fabbricati della fonte che non hanno trovato aggancio");
			inserisciNonAgganciati();
							
			conn.commit();
			log.debug("##################### Fonti Aggregate ###########################");
			
		}catch (Exception e) {
			throw new Exception("Errore in Aggregazione Altri Fabbricati : " + e.getMessage());
		}finally{
			if(rsFabbDaAggr!=null){				
				rsFabbDaAggr.close();
			}	
		}
	}
	
	
	/**
	 * Metodo che pulisce la tabella dell'unico dopo l'associazione. 
	 * Vengono eliminati i record di UNICO che non hanno un corrispondente in TOTALE e che hanno il
	 * campo VALIDATO diverso da 1 (validato = 1 sta a significare che il dato UNICO è stato inserito a mano da interfaccia)
	 */
	protected void pulisciUnico() throws Exception{
		
		try{			
			
			String sqlDeleteUnico = getProperty("SQL_PULISCI_FABBRICATI_UNICO");
			
			PreparedStatement ps = conn.prepareStatement(sqlDeleteUnico);			
			ps.execute();
			ps.close();
			
		}catch (Exception e) {
			throw new Exception("Errore nella cancellazione elementi da tabella UNICO :" + e.getMessage());
		}
		
	}
	
	
	/**
	 * Metodo che carica i fabbricati dalla TOTALE da dover aggregare per la fonte indicata
	 */
	protected ResultSet caricaDatiDaAggregareFonteRif() throws Exception{
		
		PreparedStatement prs = null;
		ResultSet rs = null;
				
		try {
		
			// Leggo Fabbricati TOTALE	
			String sqlFabbricatoTotale = getProperty("SQL_LEGGI_FABBRICATO_TOTALE_FONTE_RIF");
			
						
			prs = connectionForLongResultset.prepareStatement(sqlFabbricatoTotale);
			
			BigDecimal enteRif = (BigDecimal)paramFonteRif.get(0)[0];
			BigDecimal progRif = (BigDecimal)paramFonteRif.get(0)[1];
						
			prs.setBigDecimal(1, enteRif);
			prs.setBigDecimal(2, progRif);				
			
			rs = prs.executeQuery();				
			
			return rs;
			
		} catch (Exception e) {
			throw new Exception ("Errore in caricamento dati da tabella SIT_FABBRICATO_UNICO "+e.getMessage());
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
        	log.error("Eccezione in caricamento percentuale criterio massimo per " + propName + ": " + e.getMessage());
        	throw new Exception("Eccezione in caricamento percentuale criterio massimo per " + propName + ": " + e.getMessage());
        }finally {
		}  				
	}
	
	
	/**
	 * Metodo che aggrega i fabbricati
	 */
	private void  elabora(ResultSet resFabb, int critMaxFonte, String codEnte, HashParametriConfBean paramConfBean) throws Exception
	{
		
		try {
			
			int i = 0;
			while(resFabb.next()){
				
				Object fk = null;
									
				SitFabbricatoTotale fabbCorr = this.mappingFabbricato(resFabb);																								
				
				if(fk==null){
																									
					String fonte = String.valueOf(fabbCorr.getId().getFkEnteSorgente());										
					String progEs = String.valueOf(fabbCorr.getId().getProgEs());
										
					String sezOk = this.sezioneInAggregazione("sezione.in.aggregazione", codEnte, fonte, progEs, paramConfBean.getSezioneInAggrOgg());
										
					if(sezOk.equals("true")){
						//Controlla Criterio A100 (Sezione, Foglio, Particella)															
						fk = foglioSezPartRicerca(fabbCorr,fk,critMaxFonte);
					}else{
						//Controlla Criterio A100 (Foglio, Particella)															
						fk = foglioPartRicerca(fabbCorr,fk,critMaxFonte);
					}
				}	
				
				//Effettuo la commit dei dati su DB
				i++;
				if(i > 50000){
					i=0;
					conn.commit();
				}
			}
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			throw new Exception("Errore in Aggregazione FABBRICATI Riferimento: " + e.getMessage());
		}finally {			
		}

	}
	
	
	
	/**
	 * Metodo che effettua il mapping di un Fabbricato Totale letto
	 */
	protected SitFabbricatoTotale mappingFabbricato(ResultSet rs) throws Exception{
		
		SitFabbricatoTotale sft = new SitFabbricatoTotale();				
		IndicePK iPk = new IndicePK();
		
				
		iPk.setIdDwh(rs.getString("IDDWH"));
		iPk.setFkEnteSorgente(rs.getInt("ENTESORGENTE"));
		iPk.setProgEs(rs.getInt("PROGES"));
		iPk.setCtrHash(rs.getString("CTRHASH"));
		
		sft.setId(iPk);
			
		
		sft.setFoglio(rs.getString("FOGLIO"));
		sft.setParticella(rs.getString("PARTICELLA"));		
		sft.setCategoria(rs.getString("CATEGORIA"));
		sft.setClasse(rs.getString("CLASSE"));
		sft.setRendita(rs.getString("RENDITA"));
		sft.setZona(rs.getString("ZONA"));
		sft.setFoglioUrbano(rs.getString("FOGLIO_URBANO"));
		sft.setSuperficie(rs.getString("SUPERFICIE"));
		sft.setScala(rs.getString("SCALA"));
		sft.setInterno(rs.getString("INTERNO"));
		sft.setPiano(rs.getString("PIANO"));
		sft.setDtInizioVal(rs.getDate("DT_INIZIO_VAL"));
		sft.setDtFineVal(rs.getDate("DT_FINE_VAL"));
		sft.setIdStorico(rs.getString("ID_STORICO"));
		sft.setNote(rs.getString("NOTE"));
		sft.setRating(rs.getBigDecimal("RATING"));
		sft.setRelDescr(rs.getString("REL_DESCR"));
		sft.setFkFabbricato(rs.getBigDecimal("FK_FABBRICATO"));
		sft.setIdStorico(rs.getString("ID_STORICO"));
		sft.setCodiceFabbOrig(rs.getString("CODICE_FABBRICATO"));
		sft.setAnomalia(rs.getString("ANOMALIA"));
		sft.setValidato(rs.getBigDecimal("VALIDATO"));
		sft.setStato(rs.getString("STATO"));				
		sft.setSezione(rs.getString("SEZIONE"));	
		
			
		return sft;
		
	}
	
	
	
	/**
	 * Metodo che effettua l'inserimento/associazione dei fabbricati di riferimento non agganciati
	 */
	private void inserisciNonAgganciatiRif(Object[] fonte) throws AggregatoreException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		SitFabbricatoTotale sft = null;
		
		try {
			
			ps = conn.prepareStatement(getProperty("SQL_RICERCA_RIGHE_NON_AGGANCIATE_FABB_RIF"));
			ps.setObject(1, fonte[0]);
			ps.setObject(2, fonte[1]);
			rs = ps.executeQuery();
			while (rs.next()) {			
				sft = this.mappingFabbricato(rs);
				salvaNonAgganciati(sft, 0, 0);
			}
		} catch (Exception e) {
				e.printStackTrace();
				log.error("Errore:AggregatoreFabbricati inserisciNonAgganciati" ,e);
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
	 * Metodo che effettua l'inserimento dei fabbricati che non hanno trovato aggancio
	 */
	private void salvaNonAgganciati(SitFabbricatoTotale sft, int rating, int attendibilita) throws Exception{
		
			Object fk = null;
			QueryRunner run = new QueryRunner();
			Object pp[] = new Object[7];
			
			Object nuovoId = new Object(); 
			nuovoId = ((Map) run.query(conn,getProperty("SQL_NEXT_UNICO_FABB"), new MapHandler())).get("ID");								
			pp[0] = nuovoId;
			fk  = nuovoId;			
			
			pp[1] = sft.getFoglio();
			pp[2] = sft.getParticella();								
			pp[3] = 0; 		//validato
			pp[4] = sft.getCodiceFabbOrig();
			pp[5] = sft.getSezione();
			pp[6] = rating;
			
			run.update(conn,getProperty("SQL_INSERISCI_FABBRICATO_UNICO"),pp);
			
			
			//  aggiorno il record di totale che ho appena inserito in unico
			pp = new Object[8];			
			pp[0] = fk;
			pp[1] = rating;
			pp[2] = "NATIVA";
			pp[3] = attendibilita;
			pp[4] = sft.getId().getFkEnteSorgente();
			pp[5] = sft.getId().getProgEs();
			pp[6] = sft.getId().getCtrHash();
			pp[7] = sft.getId().getIdDwh();
						
			run.update(conn,getProperty("SQL_UPDATE_FABBRICATO_TOTALE_FK"),pp);					
	}
	
	
	
	/*Criterio A100*/
	private Object foglioPartRicerca(SitFabbricatoTotale sft, Object fk, int critMaxFonte) throws Exception {
		
			
		//Imposto Rating e RelDescr in base alla tipologia di fonte
		int ratingCriterio = Integer.parseInt(propFabbricati.getProperty("rating.caf.a100"));
		String relDescr = propFabbricati.getProperty("descr.caf.a100");;
				
		
		Object p[] = new Object[6];
		p[0]=sft.getFoglio();
		p[1]=sft.getParticella();							
		
		if ("0000".equals(sft.getFoglio()) || "00000".equals(sft.getParticella())) 
			return fk;
		
		if (p[0]==null || p[1]==null) 
			return fk;
		
		try {
			if (isZero((String)p[1]))
					return fk;
		} catch (RulEngineException e ) {
			log.warn("Non possibile valutare se particella Zero" + p[1] ,e );
		}
		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		Object trovato = elaborati.get(k);
		if (trovato!=null){
			return aggiorna(null, sft, critMaxFonte, ratingCriterio, relDescr, trovato);
		}

		p[2]=sft.getId().getFkEnteSorgente();
		p[3]=sft.getId().getProgEs();
		p[4]=sft.getId().getCtrHash();
		p[5]=sft.getId().getIdDwh();
				
		
		String sqlRicFabbTotFoglioPart = getProperty("SQL_RICERCA_FABBRICATO_TOTALE_FOGLIOPART");		
		List<SitFabbricatoTotale> lFabbTotale = this.getListaFabbricatiAssociati(conn, sqlRicFabbTotFoglioPart, p);			
		
		if (lFabbTotale.size()>0) {						
			fk =  aggiorna(lFabbTotale, sft, critMaxFonte, ratingCriterio, relDescr, fk);
		}
				
		return fk;
		
	}
	
	
	/*Criterio A100 con gestione SEZIONE*/
	private Object foglioSezPartRicerca(SitFabbricatoTotale sft, Object fk, int critMaxFonte) throws Exception {
		
		
		//Imposto Rating e RelDescr in base alla tipologia di fonte
		int ratingCriterio = Integer.parseInt(propFabbricati.getProperty("rating.caf.a100"));
		String relDescr = propFabbricati.getProperty("descr.caf.a100");;
				
		
		Object p[] = new Object[7];
		p[0]=sft.getFoglio();
		p[1]=sft.getParticella();									
		p[2]=sft.getSezione();
		
		
		if ("0000".equals(sft.getFoglio()) || "00000".equals(sft.getParticella())) 
			return fk;
		if (p[0]==null || p[1]==null || p[2]==null) 
			return fk;
		
		try {
			if (isZero((String)p[1]))
					return fk;
		} catch (RulEngineException e ) {
			log.warn("Non possibile valutare se particella Zero" + p[1] ,e );
		}
		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		Object trovato = elaborati.get(k);
		if (trovato!=null){
			return aggiorna(null, sft, critMaxFonte, ratingCriterio, relDescr, trovato);
		}

		p[3]=sft.getId().getFkEnteSorgente();
		p[4]=sft.getId().getProgEs();
		p[5]=sft.getId().getCtrHash();
		p[6]=sft.getId().getIdDwh();
				
		
		String sqlRicFabbTotSezFoglioPart = getProperty("SQL_RICERCA_FABBRICATO_TOTALE_SEZFOGLIOPART");		
		List<SitFabbricatoTotale> lFabbTotale = this.getListaFabbricatiAssociati(conn, sqlRicFabbTotSezFoglioPart, p);			
		
		if (lFabbTotale.size()>0) {						
			fk =  aggiorna(lFabbTotale, sft, critMaxFonte, ratingCriterio, relDescr, fk);
		}
				
		return fk;
		
	}
	
	
	/**
	 * Metodo che carica la lista dei fabbricati collegati al record corrente
	 */
	protected List<SitFabbricatoTotale>getListaFabbricatiAssociati(Connection connessione, String sql,Object p[]) throws Exception{
		
		PreparedStatement prepStat = null;
		ResultSet resSet = null;
		List<SitFabbricatoTotale> lista = new ArrayList<SitFabbricatoTotale>();
		SitFabbricatoTotale fabbricatoTotale = new SitFabbricatoTotale();
		
		try{
			
			prepStat = conn.prepareStatement(sql);

			for(int i=0; i<p.length; i++){
				prepStat.setObject(i+1, p[i]);	
			}
			
			resSet = prepStat.executeQuery();
			
			while (resSet.next()) {
				fabbricatoTotale = this.mappingFabbricato(resSet);
				lista.add(fabbricatoTotale);
			}
			
		}catch (Exception e) {
			throw new Exception("Errore in lettura lista Fabbricati Associati all'elemento Corrente: " + e.getMessage());
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
	 * Metodo che aggrega i fabbricati in unico
	 */
	
	/**
	 * Metodo che aggiorna i fabbricati in Totale e Unico	
	 */
	private Object aggiorna(List<SitFabbricatoTotale> lFabbTotale , SitFabbricatoTotale sft, int critMaxFonte, int ratingCritValutato, String relDescr, Object fk) throws Exception {
		
			//se è già stato elaborato
			if(lFabbTotale ==null && fk != null){
				this.aggiornaTotaleCorrente(sft, critMaxFonte, ratingCritValutato, relDescr, fk);
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
				pFK[0] = sft.getId().getFkEnteSorgente();
				pFK[1] = sft.getId().getProgEs();
				pFK[2] = sft.getId().getCtrHash();
				pFK[3] = sft.getId().getIdDwh();
				fkRs  = ((Map) run.query(conn,getProperty("SQL_SELECT_FABBRICATO_TOTALE_FK"),pFK, new MapHandler())).get("FK_FABBRICATO");								
			} catch (Exception e) {
				log.warn("Problemi reperimento fk per record " + sft.getId().getIdDwh(), e);
			}
																									
			if (fkRs==null){
				
				//Cerco l'fk con rating più alto degli elementi collegati
				for (SitFabbricatoTotale c : lFabbTotale){										
					
					if(c.getFkFabbricato()!=null){
						
						if(c.getRating() != null){
							ratingEleTotale = Integer.parseInt(c.getRating().toString());
						}else{
							ratingEleTotale = 0;
						}
						
						if(ratingEleTotale > ratingCritValutato){
							fkRs = c.getFkFabbricato();
							break;
						}						
					}
				}
			
				if(fkRs == null)
					fk =  inserisci(sft, ratingCritValutato);
			}else
				fk =fkRs;
			
				
			
			//Aggiorna Rec Tot Corrente nel caso di nuovo inserimento in Unico
			if (fk!=null && fkRs==null) {	
				//TODO errore
				this.aggiornaNuovoTotaleUnico(sft,fk,critMaxFonte,ratingCritValutato);			
			}else{	
				//TODO
				//1)Leggo l'elemento di Unico avente chiave=fkRs
				SitFabbricatoUnico fabbUni = this.getFabbricatoUnico(fkRs.toString());
																			
				//2)Controllo se il rating di questo unico è < di rating.								
				//  Se minore allora aggiorno il dato unico con i dati del record corrente
				//	Altrimenti non fa nulla							
				if(Integer.parseInt(fabbUni.getRating().toString()) < ratingCritValutato){					
					this.aggiornaUnico(fabbUni, sft, ratingCritValutato);
				}
								
				//3)Controllo sul TOTALE: se il rating dell'elemento corrente < rating --> aggiorno il rating dell'elemento corrente in totale				
				if(sft.getRating() == null ){
					
					BigDecimal val = new BigDecimal(0);
					sft.setRating(val);
				}				
				if(sft.getRating().intValue() < ratingCritValutato){				
					this.aggiornaTotaleCorrente(sft, critMaxFonte, ratingCritValutato, relDescr, fkRs);	
				}
							
				
			}
			
				
			if(fkRs != null){
				fk = fkRs;
			}
			
			// tanto che ci sono aggiorno anche quelli che ho trovato 
			//Aggiorno gli altri elementi evenhtualemente collegati
			for (SitFabbricatoTotale c : lFabbTotale)
			{
				
				//Controllo sul rating
				if(c.getRating() != null){
					ratingEleTotale = Integer.parseInt(c.getRating().toString());
				}else{
					ratingEleTotale = 0;
				}
				
				if (( c.getFkFabbricato()==null || 
					  !c.getFkFabbricato().toString().equals(fk.toString()) ||
					  !relDescr.equals(c.getRelDescr())) &&					  
					  (ratingEleTotale< ratingCritValutato)) {
					
					
					this.aggiornaTotaleCorrente(c, critMaxFonte, ratingCritValutato, relDescr, fk);
					
					// cambio anche l'fk a tutti quelli che avevano lo stesso fk di quello che sto scorrendo
					// un po' azzardato ma mi piace collegarne il piu' possibile
					if (c.getFkFabbricato()!=null && !c.getFkFabbricato().toString().equals(fk.toString())) {						
						this.cambioFkCorrelati(c.getFkFabbricato(), fk);					
					}
							
				}
			}

			return fk;
	}
	
	
	/**
	 * Inserimento in UNICO
	 */
	private Object inserisci(SitFabbricatoTotale sft, int rating) throws Exception
	{
		
		Object fk = null;
		QueryRunner run = new QueryRunner();
		Object pp[] = new Object[7];
		
		Object nuovoId = new Object(); 
		nuovoId = ((Map) run.query(conn,getProperty("SQL_NEXT_UNICO_FABB"), new MapHandler())).get("ID");								
		pp[0] = nuovoId;
		fk  = nuovoId;			
		
		pp[1] = sft.getFoglio();
		pp[2] = sft.getParticella();									
		pp[3] = 0; 		//validato
		pp[4] = sft.getCodiceFabbOrig();
		pp[5] = sft.getSezione();
		pp[6] = rating;
		
		run.update(conn,getProperty("SQL_INSERISCI_FABBRICATO_UNICO"),pp);
				

		return fk;
	}
	
	
	
	/**
	 * Metodo che aggiorna l'elemento della FABBRICATO TOTALE appena inserito in UNICO
	 */
	protected void aggiornaNuovoTotaleUnico(SitFabbricatoTotale sft, Object fk, int critMaxFonte, int ratingCritValutato) throws Exception{
		
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
			pp[4] = sft.getId().getFkEnteSorgente();
			pp[5] = sft.getId().getProgEs();
			pp[6] = sft.getId().getCtrHash();
			pp[7] = sft.getId().getIdDwh();
			
			run.update(conn,getProperty("SQL_UPDATE_FABBRICATO_TOTALE_FK"),pp);
			
		}catch (Exception e) {
			throw new Exception("Errore in salvataggio elemento Corrente Fabbricato Totale: "+ e.getMessage());
		}					
	}
	
	
	/**
	 * Metodo che cerca il fabbricato in UNICO
	 */
	protected SitFabbricatoUnico getFabbricatoUnico(String codFabb) throws Exception {
						
		SitFabbricatoUnico fu = new SitFabbricatoUnico();
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_FABBRICATO_UNICO");
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, codFabb);
			
			rs = ps.executeQuery();

			if(rs.next()){
				this.mappingFabbricatoUnico(rs, fu);
			}	
									
			
		}catch (Exception e) {
			throw new Exception("Errore nella ricerca della fabbricato UNICO per codice :" + e.getMessage());
		}finally{
			if(rs!=null)
			rs.close();
			if(ps!=null)
			ps.close();
		}

		return fu;
		
	}
	
	
	/**
	 * Metodo che effettua il mapping di un fabbricato Unico 
	 */
	protected SitFabbricatoUnico mappingFabbricatoUnico(ResultSet rs, SitFabbricatoUnico fu) throws Exception{
							
		fu.setIdFabbricato(rs.getInt("ID_FABBRICATO"));
		fu.setFoglio(rs.getString("FOGLIO"));
		fu.setParticella(rs.getString("PARTICELLA"));		
		fu.setDtIns(rs.getDate("DT_INS"));
		fu.setValidato(rs.getBigDecimal("VALIDATO"));
		fu.setCodiceFabbOrig("CODICE_FABBRICATO");				
		fu.setSezione(rs.getString("SEZIONE"));								
		fu.setRating(rs.getBigDecimal("RATING"));
		
		return fu;
		
	}
	
	
	/**
	 * Metodo che aggiorna gli fabbricato in Unico con uno più valido della Totale
	 */
	protected void aggiornaUnico(SitFabbricatoUnico sfu, SitFabbricatoTotale sft, int rating) throws Exception{
		
		PreparedStatement ps = null;
		
		try{
			
			String sql = getProperty("SQL_AGGIORNA_FABBRICATO_UNICO");
			
			ps = conn.prepareStatement(sql);

			ps.setString(1, sft.getFoglio());
			ps.setString(2, sft.getParticella());			
			ps.setString(3, sft.getSezione());						
			ps.setBigDecimal(4, sft.getValidato());			
			ps.setString(5, sft.getCodiceFabbOrig());
			ps.setBigDecimal(6, (BigDecimal)sft.getRating());
			ps.setLong(7, sfu.getIdFabbricato());
						
			
			ps.executeUpdate();															
			
		}catch (Exception e) {
			throw new Exception("Errore nell'aggiornamento del fabbricato UNICO per codice :" + e.getMessage());
		}finally{
			DbUtils.close(ps);
		
		}
		
	}
	
	
	/**
	 * Metodo che aggiorna l'elemento Corrente del FABBRICATO TOTALE
	 */
	protected void aggiornaTotaleCorrente(SitFabbricatoTotale sft,int critMaxFonte, int ratingCritValutato, String relDescr, Object fk) throws Exception{
		
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
			pp[4] = sft.getId().getFkEnteSorgente();
			pp[5] = sft.getId().getProgEs();
			pp[6] = sft.getId().getCtrHash();
			pp[7] = sft.getId().getIdDwh();
			run.update(conn,getProperty("SQL_UPDATE_FABBRICATO_TOTALE_FK"),pp);
			
			numRecAggiornati = numRecAggiornati +1;
			if (numRecAggiornati>= 1000) 
			{
				totNumRecAggiornati = totNumRecAggiornati + numRecAggiornati;
				log.debug("Aggiornamento record " + totNumRecAggiornati) ;
				numRecAggiornati = 0;
			}
			
		}catch (Exception e) {
			throw new Exception("Errore in salvataggio elemento Corrente Fabbricato Totale: "+ e.getMessage());
		}					
	}
	
	
	/**
	 * Metodo che cambia l'FK a tutti
	 */
	protected void cambioFkCorrelati(Object fkFabbricato, Object fk) throws Exception{
		
		try{
			Object pp [] = new Object[2];
			pp[0] = fk;
			pp[1] = fkFabbricato;
			run.update(conn,getProperty("SQL_UPDATE_FABBRICATO_TOTALE_FK_ALL"),pp);
		}catch (Exception e) {
			throw new Exception("Errore in cambio Fk a Fabbricati collegati precedentemente: " + e.getMessage());
		}	
	}
	
	
	/**
	 * Metodo che carica i fabbricati dalla TOTALE da dover aggregare per le varie fonti
	 */
	protected ResultSet caricaDatiDaAggregare(Object[] fonte) throws Exception{
		
		PreparedStatement prs = null;
		ResultSet rs = null;
				
		try {
		
			// Leggo Fabbricato TOTALE	
			String sqlFabbricatoTotale = getProperty("SQL_RICERCA_RIGHE_FABB");
			
						
			prs = connectionForLongResultset.prepareStatement(sqlFabbricatoTotale);
			
			BigDecimal enteRif = (BigDecimal)fonte[0];
			BigDecimal progRif = (BigDecimal)fonte[1];
						
			prs.setBigDecimal(1, enteRif);
			prs.setBigDecimal(2, progRif);				
			
			rs = prs.executeQuery();				
			
			return rs;
			
		} catch (Exception e) {
			throw new Exception ("Errore in caricamento dati da tabella SIT_FABBRICATO_UNICO "+e.getMessage());
		} finally {			
		}				
	
	}
	
	
	/**
	 * Metodo che effettua l'elaborazione/aggregazione delle altre fonti
	 */
	private void  elaboraAltreFonti(ResultSet resFabb, String codEnte, HashParametriConfBean paramConfBean) throws Exception
	{
			
		try {
						
			int i = 0;
			while(resFabb.next()){
				
				Object fk = null;
							

				//Trovo il valore del criterio Max da valutare per la fonte
				Object[] paramFonte = new Object[2];				
				paramFonte[0] = resFabb.getBigDecimal("ENTESORGENTE");
				paramFonte[1] = resFabb.getBigDecimal("PROGES");
				int critMaxFonte = this.getCriterioMax(paramFonte);

				
				SitFabbricatoTotale fabbCorr = this.mappingFabbricato(resFabb);
						
				if(fk==null){
										
					String fonte = String.valueOf(fabbCorr.getId().getFkEnteSorgente());										
					String progEs = String.valueOf(fabbCorr.getId().getProgEs());
										
					String sezOk = this.sezioneInAggregazione("sezione.in.aggregazione", codEnte, fonte, progEs, paramConfBean.getSezioneInAggrOgg());
				
					
					if(sezOk.equals("true")){
						//Controlla Criterio A100 (Sezione, Foglio, Particella)															
						fk = foglioSezPartRicerca(fabbCorr,fk,critMaxFonte);
					}else{
						//Controlla Criterio A100 (Foglio, Particella,)															
						fk = foglioPartRicerca(fabbCorr,fk,critMaxFonte);
					}
				}
				
				//Effettuo la commit dei dati su DB
				i++;
				if(i > 50000){
					i=0;
					conn.commit();
				}
			}
			conn.commit();
		}catch(Exception e){
			conn.rollback();
			throw new Exception("Errore in Aggregazione FABBRICATI altre fonti: " + e.getMessage());
		}finally {			
		}

	}
	
	
	/**
	 * Metodo che effettua l'inserimento/associazione degli altri fabbricati non agganciati
	 */
	private void inserisciNonAgganciati() throws AggregatoreException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		SitFabbricatoTotale sft = null;
		
		try {
			
			ps = conn.prepareStatement(getProperty("SQL_RICERCA_RIGHE_NON_AGGANCIATE_FABB"));
			ps.setObject(1, paramFonteRif.get(0)[0]);			
			ps.setObject(2, paramFonteRif.get(0)[1]);
			rs = ps.executeQuery();
			while (rs.next()) {				
				sft = this.mappingFabbricato(rs);
				salvaNonAgganciati(sft, 0, 0);
			}
		} catch (Exception e) {
				e.printStackTrace();
				log.error("Errore:AggregatoreFabbricati inserisciNonAgganciati" ,e);
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
	protected String sezioneInAggregazione(String key, String codEnte, String fonte, String progEs, Hashtable<String, Object> sezioneInAggrFabb) throws Exception{
		
		String sezione = "false";		
			
		try{										
			
			if(fonte!=null){
				
				String chiave = key+fonte;	
								
				String parSezione = (String)sezioneInAggrFabb.get(chiave);
				
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
