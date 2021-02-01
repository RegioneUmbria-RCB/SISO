package it.webred.ct.proc.ario.aggregatori;


import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.data.model.indice.SitCivicoUnico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;

public class AggregatoreCivici extends TipoAggregatore
{
	

	private static final Logger	log	= Logger.getLogger(AggregatoreCivici.class.getName());
	private Connection conn = null;
	private static ArrayList<Object[]> paramFonteRif;
	private static ArrayList<Object[]> paramFonti;
	QueryRunner run = new QueryRunner();
	
	// questa hashmap viene svuotata ad ogni fonte nel metodo elabora
	HashMap<String,Object> elaborati=  new HashMap<String, Object>(); 
	
	private static Properties propCivici = null;
	private static Properties propCrit = null;
	
	private long totNumRecAggiornati = 0;
	private long numRecAggiornati = 0;
	
	
	//Costruttore
	public AggregatoreCivici(){
		//Caricamento del file di properties aggregatoreCivici	
		this.propCivici = new Properties();		
		this.propCrit = new Properties();
		try {
			InputStream is = this.getClass().getResourceAsStream("/it/webred/ct/proc/ario/aggregatori/aggregatoreCivici.properties");
		    this.propCivici.load(is); 
		    
		    InputStream isCrit = this.getClass().getResourceAsStream("/it/webred/ct/proc/ario/aggregatori/criteriValutabiliCivici.properties");
            this.propCrit.load(isCrit); 		
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage());			   
		}
	}
	
	
	

	public void aggrega(String codEnte, HashParametriConfBean paramConfBean) throws AggregatoreException{						
		
		try{
			
			log.info("Caricamento in Civico TOTALE degli Fk_via associati");
			this.aggiornaFkViaCivicoTotale();
			
			
			log.info("################### INIZIO Elaborazione controllo associabilità Civici in UNICO #######################");								
			
			paramFonteRif = (ArrayList<Object[]>) run.query(conn,getProperty("SQL_FONTE_PROGRESSIVO_RIF_CIVICI"), new ArrayListHandler());						
			
			/**
			 * CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO				
			 */
			
			if (paramFonteRif == null)
				log.info("!!!!! ERRORE: FONTE DI RIFERIMENTO CIVICI NON TROVATA !!!!! continuo...");
			else{
				log.info("INIZIO CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO");
				this.aggregazioneFonteRiferimento(paramFonteRif.get(0));
				log.info("FINE CARICAMENTO ED AGGREGAZIONE DEI DATI PROVENIENTI DA FONTE DI RIFERIMENTO");						
			}
			/**
			 * CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI
			 */
			log.info("CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI");
			this.aggregazioneAltreFonti();				
			log.info("FINE CARICAMENTO ED AGGREGAZIONE DEI DATI DELLE ALTRE FONTI");
							
			//Pulizia tabella UNICO (cancello tutti i record non collegati a totale e Validato <> 1)
			log.info("INIZIO cancellazione elementi in CIVICO UNICO");
			this.pulisciUnico();
			log.info("FINE cancellazione elementi in CIVICO UNICO");
			
			log.info("############ FINE Elaborazione controllo associabilità Civici in UNICO ################################");
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Errore:Aggregatore CIVICO", e);
			AggregatoreException ea = new AggregatoreException(e.getMessage());
			throw ea;
		}
		finally
		{
		}

	}

	
	public void setConnection(Connection conn)
	{
		this.conn = conn;
	}

	
	/**
	 * Metodo che effettua l'aggregazione Civici per la Fonte di Riferimento
	 */
	protected void aggregazioneFonteRiferimento(Object[] fonte) throws Exception{
		
		ResultSet rsCivRifTotDaAggr = null;
		
		try{
			
			//Effettuo il caricamento della lista dei TOTALI da aggregare per la fonte di riferimento						
			rsCivRifTotDaAggr = this.caricaDatiDaAggregareFonteRif();
													
			log.debug("elaborazione/aggregazione civici Fonte Riferimento");
			//Trovo il valore del criterio Max da valutare per la fonte
			int criterioMaxFonte = this.getCriterioMax(paramFonteRif.get(0));
			
			//Effettuo il controllo di associazione dei civici di Riferimento			
			this.elabora(rsCivRifTotDaAggr, criterioMaxFonte);
			
			log.debug("inserisco tutti i civici della fonte DI RIFERIMENTO che non hanno trovato aggancio");
			inserisciNonAgganciatiRif(paramFonteRif.get(0));
						
			//Commit Fonte Riferimento			
			conn.commit();
			
			log.debug("##################### Fonte Civico di Riferimento Aggregata ###########################");
			
		}catch (Exception e) {
			throw new Exception("Errore in Aggregazione Civici Riferimento: " + e.getMessage());
		}finally{
			if(rsCivRifTotDaAggr!=null){				
				rsCivRifTotDaAggr.close();
			}	
		}
	}
	
	
	/**
	 * Metodo che carica i civici dalla TOTALE da dover aggregare per la fonte indicata
	 */
	protected ResultSet caricaDatiDaAggregareFonteRif() throws Exception{
		
		PreparedStatement prs = null;
		ResultSet rs = null;
				
		try {
		
			// Leggo Soggetto TOTALE	
			String sqlCivicoTotale = getProperty("SQL_LEGGI_CIVICO_TOTALE_FONTE_RIF");
			
						
			prs = connectionForLongResultset.prepareStatement(sqlCivicoTotale);
			
			BigDecimal enteRif = (BigDecimal)paramFonteRif.get(0)[0];
			BigDecimal progRif = (BigDecimal)paramFonteRif.get(0)[1];
						
			prs.setBigDecimal(1, enteRif);
			prs.setBigDecimal(2, progRif);				
			
			rs = prs.executeQuery();				
			
			return rs;
			
		} catch (Exception e) {
			throw new Exception ("Errore in caricamento dati da tabella SIT_CIVICO_TOTALE "+e.getMessage());
		} finally {			
		}				
	
	}
	
	
	/**
	 * Metodo per il recupero del file criteriValutabili.properties contenente i criteri valutabili per le fonti 
	 */	
	protected int getCriterioMax(Object[] fonte) throws Exception {
		       
        try{
	        
	        BigDecimal ente = (BigDecimal)fonte[0];
	        BigDecimal progr = (BigDecimal)fonte[1];
	        
	        String propName = "criterio." + ente + "." + progr;
	        
	        String p = this.propCrit.getProperty(propName);
			
	        int ratingMax = 0;
	        if(p!=null){
	        	ratingMax = Integer.parseInt(p);
	        }	
	        
	        return ratingMax;
	        
        }catch (Exception e) {
        	log.error("Eccezione in caricamento percentuale criterio massimo: " + e.getMessage());
        	throw new Exception("Eccezione in caricamento percentuale criterio massimo: " + e.getMessage());
        }finally {
		}  				
	}
	
	
	/**
	 * Metodo che esegue l'aggregazione dei Civici di riferimento
	 */
	private void  elabora(ResultSet resCiv, int critMaxFonte) throws Exception
	{
				
		try {
				
			int i = 0;
			while(resCiv.next()){
				
				Object fk = null;
									
				SitCivicoTotale civCorr = this.mappingCivico(resCiv);
									
				//Controlla Criterio CAC-A100 (Codice Civico Fornito dall'ente)
				if(civCorr.getCodiceCivOrig() != null){
					
					String codCiv = this.cercaCivicoUnico(civCorr.getCodiceCivOrig());
					
					if(codCiv!=null){
						//Associo il civico			
						fk = codCiv;
						
						String desc = propCivici.getProperty("descr.cac.a100");
						int ratCrit = Integer.parseInt(propCivici.getProperty("rating.cac.a100"));
						
						this.aggiornaTotaleCorrente(civCorr, 100, ratCrit, desc, fk);																								
					}										
				}
				
				
				if(fk==null){
					//Controlla Criterio A10 (Civico e Via)															
					fk = civicoRicerca(civCorr,fk,critMaxFonte);
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
			throw new Exception("Errore in processo aggregazione civici fonte riferimento: "+ e.getMessage());	
		}
		finally {				
		}

	}
	
	
	
	/**
	 * Metodo che effettua il mapping di un civico Totale 
	 */
	protected SitCivicoTotale mappingCivico(ResultSet rs) throws Exception{
		
		SitCivicoTotale sct = new SitCivicoTotale();	
		IndicePK iPk = new IndicePK();
		
		
		
		iPk.setIdDwh(rs.getString("IDDWH"));
		iPk.setFkEnteSorgente(rs.getInt("ENTESORGENTE"));
		iPk.setProgEs(rs.getInt("PROGES"));
		iPk.setCtrHash(rs.getString("CTRHASH"));
		
		sct.setId(iPk);
						
		sct.setFkCivico(rs.getBigDecimal("FK_CIVICO"));
		sct.setRelDescr(rs.getString("REL_DESCR"));
		sct.setRating(rs.getBigDecimal("RATING"));
		sct.setNote(rs.getString("NOTE"));		
		sct.setCivicoComp(rs.getObject("CIVICO_COMPOSTO"));
		sct.setCivLiv1(rs.getString("CIVICO"));
		sct.setIdOrigViaTotale(rs.getString("ID_ORIG_VIA_TOTALE"));
		sct.setFkVia(rs.getBigDecimal("FK_VIA"));
		sct.setIdStorico(rs.getString("ID_STORICO"));
		sct.setDataFineVal(rs.getDate("DT_FINE_VAL"));
		sct.setAnomalia(rs.getString("ANOMALIA"));
		sct.setValidato(rs.getBigDecimal("VALIDATO"));
		sct.setStato(rs.getString("STATO"));
		sct.setCodiceCivOrig(rs.getString("CODICE_CIVICO"));
	
			
		return sct;
		
	}
	
	
	/**
	 * Metodo che effettua la ricerca in Civico Unico per codice Civico Originario
	 */
	protected String cercaCivicoUnico(String civOrig) throws Exception {
		
		String codiceCiv = null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_COD_CIVICO_UNICO");
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, civOrig);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				codiceCiv = rs.getString("ID_CIVICO");
			}
			
			ps.close();

			
		}catch (Exception e) {
			log.error("Errore nella ricerca del civico UNICO per codice Civico originario ",e);
			throw new Exception("Errore nella ricerca del civico UNICO per codice Civico originario :" + e.getMessage());
		}
		
		
		return codiceCiv;
		
	}
	
	
	
	/**
	 * Metodo che aggiorna l'elemento Corrente della CIVICO TOTALE
	 */
	protected void aggiornaTotaleCorrente(SitCivicoTotale sct,int critMaxFonte, int ratingCritValutato, String relDescr, Object fk) throws Exception{
		
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
			pp[4] = sct.getId().getFkEnteSorgente();
			pp[5] = sct.getId().getProgEs();
			pp[6] = sct.getId().getCtrHash();
			pp[7] = sct.getId().getIdDwh();
			run.update(conn,getProperty("SQL_UPDATE_CIVICO_TOTALE_FK"),pp);

			numRecAggiornati = numRecAggiornati +1;
			if (numRecAggiornati>= 1000) 
			{
				totNumRecAggiornati = totNumRecAggiornati + numRecAggiornati;
				log.debug("Aggiornamento record " + totNumRecAggiornati) ;
				numRecAggiornati = 0;
			}
			
		}catch (Exception e) {
			log.error("Errore in salvataggio elemento Corrente Civico Totale: ",e);
			throw new Exception("Errore in salvataggio elemento Corrente Civico Totale: "+ e.getMessage());
		}					
	}
	
	
	
	/**
	 * Criterio di ricerca per Civico  A10
	 */
	private Object civicoRicerca(SitCivicoTotale sct, Object fk, int critMaxFonte) throws Exception {
		
		//QueryRunner run = new QueryRunner();
		//BeanListHandler h = new BeanListHandler(SitCivicoTotale.class);
		
		//Imposto Rating e RelDescr in base alla tipologia di fonte
		int ratingCriterio = Integer.parseInt(propCivici.getProperty("rating.cac.a10"));
		String relDescr = propCivici.getProperty("descr.cac.a10");;
				
		
		Object p[] = new Object[6];
		p[0]=sct.getFkVia();
		p[1]=sct.getCivLiv1();
		
		if ("00000000".equals(sct.getCivLiv1()))
			return fk;
		if (p[0]==null || p[1]==null) 
			return fk;
		
		List<Object> wordList = Arrays.asList(p);
		String k = wordList.toString();
		if (elaborati.get(k)!=null)
			return fk;

		p[2]=sct.getId().getFkEnteSorgente();
		p[3]=sct.getId().getProgEs();
		p[4]=sct.getId().getCtrHash();
		p[5]=sct.getId().getIdDwh();
		
		//List<SitCivicoTotale> lCivTotale = (List<SitCivicoTotale>) run.query(conn, getProperty("SQL_RICERCA_CIVICO_TOTALE_CIV_FKVIA"),p, h);
		
		//TODO
		String sqlRicCivTotFkVia = getProperty("SQL_RICERCA_CIVICO_TOTALE_CIV_FKVIA");		
		List<SitCivicoTotale> lCivTotale = this.getListaCiviciAssociati(conn, sqlRicCivTotFkVia, p);
		
		if (lCivTotale.size()>0) {						
			fk =  aggiorna( lCivTotale, sct, critMaxFonte, ratingCriterio, relDescr, fk);
		}
				
		return fk;
		
	}
	
	
	/**
	 * Metodo che aggiorna i civici in Totale e Unico	
	 */
	private Object aggiorna(List<SitCivicoTotale> lCivTotale , SitCivicoTotale sct, int critMaxFonte, int ratingCritValutato, String relDescr, Object fk) throws Exception {
		

			QueryRunner run = new QueryRunner();		
			int ratingEleTotale = 0;			
			Object fkRs = new Object(); 
			fkRs = null;																
			
			//Controllo se l'elemento è collegato ad una UNICO e quinfi possiede un fk
			try {
				// LETTURA DELL'fk_civico del record che sto scorrendo
				Object pFK[] = new Object[4];
				pFK[0] = sct.getId().getFkEnteSorgente();
				pFK[1] = sct.getId().getProgEs();
				pFK[2] = sct.getId().getCtrHash();
				pFK[3] = sct.getId().getIdDwh();
				fkRs  = ((Map) run.query(conn,getProperty("SQL_SELECT_CIVICO_TOTALE_FK"),pFK, new MapHandler())).get("FK_CIVICO");								
			} catch (Exception e) {
				log.warn("Problemi reperimento fk per record " + sct.getId().getIdDwh(), e);
			}
																									
			if (fkRs==null){
				
				//Cerco l'fk con rating più alto degli elementi collegati
				for (SitCivicoTotale c : lCivTotale){										
					
					if(c.getFkCivico()!=null){
						
						if(c.getRating() != null){
							ratingEleTotale = Integer.parseInt(c.getRating().toString());
						}else{
							ratingEleTotale = 0;
						}
						
						if(ratingEleTotale > ratingCritValutato){
							fkRs = c.getFkCivico();
							break;
						}						
					}
				}
			
				if(fkRs == null)
					fk =  inserisci(sct, ratingCritValutato);
			}else
				fk =fkRs;
			
				
			
			//Aggiorna Rec Tot Corrente nel caso di nuovo inserimento in Unico
			if (fk!=null && fkRs==null) {					
				this.aggiornaNuovoTotaleUnico(sct,fk,critMaxFonte,ratingCritValutato);			
			}else{	
				//TODO
				//1)Leggo l'elemento di Unico avente chiave=fkRs
				SitCivicoUnico civUni = this.getCivicoUnico(fkRs.toString());
																			
				//2)Controllo se il rating di questo unico è < di rating.								
				//  Se minore allora aggiorno il dato unico con i dati del record corrente
				//	Altrimenti non fa nulla							
				if(Integer.parseInt(civUni.getRating().toString()) < ratingCritValutato){					
					this.aggiornaUnico(civUni, sct, ratingCritValutato);
				}
								
				//3)Controllo sul TOTALE: se il rating dell'elemento corrente < rating --> aggiorno il rating dell'elemento corrente in totale				
				if(sct.getRating() == null ){
					
					BigDecimal val = new BigDecimal(0);
					sct.setRating(val);
				}				
				if(sct.getRating().intValue() < ratingCritValutato){				
					this.aggiornaTotaleCorrente(sct, critMaxFonte, ratingCritValutato, relDescr, fkRs);	
				}
							
				
			}
			
				
			if(fkRs != null){
				fk = fkRs;
			}
			
			// tanto che ci sono aggiorno anche quelli che ho trovato 
			//Aggiorno gli altri elementi evenhtualemente collegati
			for (SitCivicoTotale c : lCivTotale)
			{
				
				//Controllo sul rating
				if(c.getRating() != null){
					ratingEleTotale = Integer.parseInt(c.getRating().toString());
				}else{
					ratingEleTotale = 0;
				}
				
				if (( c.getFkCivico()==null || 
					  !c.getFkCivico().toString().equals(fk.toString()) ||
					  !relDescr.equals(c.getRelDescr())) &&					  
					  (ratingEleTotale< ratingCritValutato)) {
					
					
					this.aggiornaTotaleCorrente(c, critMaxFonte, ratingCritValutato, relDescr, fk);
					
					// cambio anche l'fk a tutti quelli che avevano lo stesso fk di quello che sto scorrendo
					// un po' azzardato ma mi piace collegarne il piu' possibile
					if (c.getFkCivico()!=null && !c.getFkCivico().toString().equals(fk.toString())) {						
						this.cambioFkCorrelati(c.getFkCivico(), fk);					
					}
							
				}
			}

			return fk;
	}
	
	
	/**
	 * Metodo che effettua il salvataggio di un civico Unico 
	 */
	private Object inserisci(SitCivicoTotale sct, int rating) throws Exception
	{
		Object fk = null;
		QueryRunner run = new QueryRunner();
			
			Object pp[] = new Object[6];
			
			
			Object nuovoId = new Object(); 
			nuovoId = 	((Map) run.query(conn,getProperty("SQL_NEXT_UNICO_CIV"), new MapHandler())).get("ID");								
			pp[0] = nuovoId;
			fk  = nuovoId;
			
			pp[1] = sct.getCivLiv1();
			pp[2] = sct.getFkVia();
			pp[3] = 0; 	//validato
			pp[4] = sct.getCodiceCivOrig();
			pp[5] = rating;
									
			run.update(conn,getProperty("SQL_INSERISCI_CIVICO_UNICO"),pp);			
			
			return fk;
	}
	
	
	/**
	 * Metodo che aggiorna l'elemento della CIVICO TOTALE appena inserito in UNICO
	 */
	protected void aggiornaNuovoTotaleUnico(SitCivicoTotale sct, Object fk, int critMaxFonte, int ratingCritValutato) throws Exception{
		
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
			pp[4] = sct.getId().getFkEnteSorgente();
			pp[5] = sct.getId().getProgEs();
			pp[6] = sct.getId().getCtrHash();
			pp[7] = sct.getId().getIdDwh();
			run.update(conn,getProperty("SQL_UPDATE_CIVICO_TOTALE_FK"),pp);
			
		}catch (Exception e) {
			throw new Exception("Errore in salvataggio elemento Corrente Civico Totale: "+ e.getMessage());
		}					
	}
	
	
	/**
	 * Metodo che cerca il civico in UNICO
	 */
	protected SitCivicoUnico getCivicoUnico(String codCiv) throws Exception {
						
		SitCivicoUnico cu = new SitCivicoUnico();
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_CIVICO_UNICO");
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, codCiv);
			
			rs = ps.executeQuery();

			if(rs.next()){
				this.mappingCivicoUnico(rs, cu);
			}	
									
			
		}catch (Exception e) {
			throw new Exception("Errore nella ricerca della civico UNICO per codice :" + e.getMessage());
		}finally{
			if(rs!=null)
			rs.close();
			if(ps!=null)
			ps.close();
		}

		return cu;
		
	}
	
	
	
	/**
	 * Metodo che effettua il mapping di un civico Unico 
	 */
	protected SitCivicoUnico mappingCivicoUnico(ResultSet rs, SitCivicoUnico cu) throws Exception{
							
		cu.setIdCivico(rs.getInt("ID_CIVICO"));
		cu.setCivico(rs.getString("CIVICO"));
		cu.setFkVia(rs.getBigDecimal("FK_VIA"));
		cu.setValidato(rs.getBigDecimal("VALIDATO"));	
		cu.setCodiceCivOrig("CODICE_CIVICO");			
		cu.setRating(rs.getBigDecimal("RATING"));
		
		return cu;
		
	}
	
	
	/**
	 * Metodo che aggiorna il Soggetto in Unico con uno più valido della Totale
	 */
	protected void aggiornaUnico(SitCivicoUnico scu, SitCivicoTotale sct, int rating) throws Exception{
		
		PreparedStatement ps = null;
		
		try{
			
			String sql = getProperty("SQL_AGGIORNA_CIVICO_UNICO");
			
			ps = conn.prepareStatement(sql);

			ps.setString(1, sct.getCivLiv1());
			ps.setBigDecimal(2, sct.getFkVia());
			ps.setBigDecimal(3, sct.getValidato());			
			ps.setString(4, sct.getCodiceCivOrig());
			ps.setBigDecimal(5, (BigDecimal)sct.getRating());
			ps.setLong(6, scu.getIdCivico());
						
			
			ps.executeUpdate();																
			
		}catch (Exception e) {
			throw new Exception("Errore nell'aggiornamento del civico UNICO per codice :" + e.getMessage());
		}finally{
			DbUtils.close(ps);

		}
		
	}
	
	
	/**
	 * Metodo che cambia l'FK a tutti
	 */
	protected void cambioFkCorrelati(Object fkCivico, Object fk) throws Exception{
		
		try{
			Object pp [] = new Object[2];
			pp[0] = fk;
			pp[1] = fkCivico;
			run.update(conn,getProperty("SQL_UPDATE_CIVICO_TOTALE_FK_ALL"),pp);
		}catch (Exception e) {
			throw new Exception("Errore in cambio Fk a Civici collegati precedentemente: " + e.getMessage());
		}	
	}
	
	
	
	/**
	 * Metodo che effettua l'inserimento/associazione dei civici di riferimento non agganciati
	 */
	private void inserisciNonAgganciatiRif(Object[] fonte) throws AggregatoreException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		SitCivicoTotale sct = null;
		
		try {
			
			ps = conn.prepareStatement(getProperty("SQL_RICERCA_RIGHE_NON_AGGANCIATE_CIV_RIF"));
			ps.setObject(1, fonte[0]);
			ps.setObject(2, fonte[1]);
			rs = ps.executeQuery();
			while (rs.next()) {				
				sct = this.mappingCivico(rs);
				salvaNonAgganciati(sct, 0, 0);
			}
		} catch (Exception e) {
				e.printStackTrace();
				log.error("Errore:AggregatoreCivici inserisciNonAgganciati" ,e);
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
	 * Metodo che effettua l'inserimento dei civici che non hanno trovato aggancio
	 */
	private void salvaNonAgganciati(SitCivicoTotale sct, int rating, int attendibilita) throws Exception{
		

			Object fk = null;
			QueryRunner run = new QueryRunner();
						
			Object pp[] = new Object[6];
			
			
			Object nuovoId = new Object(); 
			nuovoId = 	((Map) run.query(conn,getProperty("SQL_NEXT_UNICO_CIV"), new MapHandler())).get("ID");								
			pp[0] = nuovoId;
			fk  = nuovoId;
			
			pp[1] = sct.getCivLiv1();
			pp[2] = sct.getFkVia();
			pp[3] = 0; 	//validato
			pp[4] = sct.getCodiceCivOrig();
			pp[5] = rating;
			
									
			run.update(conn,getProperty("SQL_INSERISCI_CIVICO_UNICO"),pp);									
			
			
			//  aggiorno il record di totale che ho appena inserito in unico
			pp = new Object[8];			
			pp[0] = fk;
			pp[1] = rating;
			pp[2] = "NATIVA";
			pp[3] = attendibilita;
			pp[4] = sct.getId().getFkEnteSorgente();
			pp[5] = sct.getId().getProgEs();
			pp[6] = sct.getId().getCtrHash();
			pp[7] = sct.getId().getIdDwh();
						
			run.update(conn,getProperty("SQL_UPDATE_CIVICO_TOTALE_FK"),pp);					
	}
	
	
	/**
	 * Metodo che effettua un caricamento preventivo dei codici fk_via associati ai vari civici
	 */
	protected void aggiornaFkViaCivicoTotale() throws Exception{
				
		PreparedStatement ps = null;
		
		try{
			
			//Aggiorno gli i totali che non hanno fk
			String sql = getProperty("SQL_AGGIORNA_FK_VIA_CIVICI");			
			ps = conn.prepareStatement(sql);			
			ps.execute();							
						
			//Aggiorno i Totali che hanno un fk diverso, modificato (disassociato)
			sql = getProperty("SQL_AGGIORNA_CIVICI_MODIFICATI");			
			ps = conn.prepareStatement(sql);			
			ps.execute();							
			
			
		}catch (Exception e) {
			throw new Exception("Errore nell'aggiornamento dell'Fk_via dei civici TOTALI :" + e.getMessage());
		}finally{			
			if(ps!=null)
			ps.close();
		}		
	}
	
	
	/**
	 * Metodo che effettua l'aggregazione Civici delle altre fonti caricate
	 */
	protected void aggregazioneAltreFonti() throws Exception{
		
		ResultSet rsCivDaAggr = null;
		
		try{
					
			//Effettuo il caricamento della lista dei TOTALI da aggregare per la fonte data						
			rsCivDaAggr = this.caricaDatiDaAggregare(paramFonteRif.get(0));
							
			//Effettuo il controllo di associazione dei civici per la fonte data 		
			this.elaboraAltreFonti(rsCivDaAggr);
			
			log.debug("inserisco tutti i civici della fonte " + paramFonteRif.get(0) + " che non hanno trovato aggancio");
			inserisciNonAgganciati();
							
			conn.commit();
			log.debug("##################### Fonti Civici Aggregate ###########################");
			
		}catch (Exception e) {
			log.error("Errore in Aggregazione Altre Fonti Civici",e);
			throw new Exception("Errore in Aggregazione Altri Civici : " + e.getMessage());
		}finally{
			if(rsCivDaAggr!=null){				
				rsCivDaAggr.close();
			}	
		}
	}
	
	
	
	/**
	 * Metodo che carica i civici dalla TOTALE da dover aggregare per le varie fonti
	 */
	protected ResultSet caricaDatiDaAggregare(Object[] fonte) throws Exception{
		
		PreparedStatement prs = null;
		ResultSet rs = null;
				
		try {
		
			// Leggo Civico TOTALE	
			String sqlCivicoTotale = getProperty("SQL_RICERCA_RIGHE_CIV");
			
						
			prs = connectionForLongResultset.prepareStatement(sqlCivicoTotale);
			
			BigDecimal enteRif = (BigDecimal)fonte[0];
			BigDecimal progRif = (BigDecimal)fonte[1];
						
			prs.setBigDecimal(1, enteRif);
			prs.setBigDecimal(2, progRif);				
			
			rs = prs.executeQuery();				
			
			return rs;
			
		} catch (Exception e) {
			throw new Exception ("Errore in caricamento dati da tabella SIT_CIVICO_TOTALE "+e.getMessage());
		} finally {			
		}				
	
	}
	
	
	
	/**
	 * Metodo che esegue l'aggregazione dei Civici di delle altre fonti
	 */
	private void  elaboraAltreFonti(ResultSet resCiv) throws Exception
	{
		log.debug("ELABORA ALTRE FONTI");
		try {
							
			int i = 0;
			while(resCiv.next()){
				
				Object fk = null;				
				
				//Trovo il valore del criterio Max da valutare per la fonte
				Object[] paramFonte = new Object[2];				
				paramFonte[0] = resCiv.getBigDecimal("ENTESORGENTE");
				paramFonte[1] = resCiv.getBigDecimal("PROGES");
				int critMaxFonte = this.getCriterioMax(paramFonte);
				
				
				SitCivicoTotale civCorr = this.mappingCivico(resCiv);
									
				//Controlla Criterio CAC-A100 (Codice Civico Fornito dall'ente)
				if(civCorr.getCodiceCivOrig() != null){
					
					String codCiv = this.cercaCivicoUnico(civCorr.getCodiceCivOrig());
					
					if(codCiv!=null){
						//Associo il civico			
						fk = codCiv;
						
						String desc = propCivici.getProperty("descr.cac.a100");
						int ratCrit = Integer.parseInt(propCivici.getProperty("rating.cac.a100"));
						
						this.aggiornaTotaleCorrente(civCorr, 100, ratCrit, desc, fk);																								
					}										
				}
				
				
				if(fk==null){
					//Controlla Criterio A10 (Civico e Via)															
					fk = civicoRicerca(civCorr,fk,critMaxFonte);
				}	
			
				
				//Effettuo la commit dei dati su DB
				i++;
				if(i > 100000){
					i=0;
					log.debug("Commit di 100000 records!");
					conn.commit();
				}
			}
			conn.commit();
			
		}catch(Exception e){
			log.error("Errore nel processo di aggregazione civici altre fonti",e);
			conn.rollback();
			throw new Exception("Errore nel processo di aggregazione civici altre fonti :" + e.getMessage());
		}finally {			
		}

	}
	
	
	/**
	 * Metodo che effettua l'inserimento/associazione degli altri civici non agganciati
	 */
	private void inserisciNonAgganciati() throws AggregatoreException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		SitCivicoTotale sct = null;
		
		try {
			
			ps = conn.prepareStatement(getProperty("SQL_RICERCA_RIGHE_NON_AGGANCIATE_CIV"));
			ps.setObject(1, paramFonteRif.get(0)[0]);			
			ps.setObject(2, paramFonteRif.get(0)[1]);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				sct = this.mappingCivico(rs);
				salvaNonAgganciati(sct, 0, 0);
			}
		} catch (Exception e) {
				e.printStackTrace();
				log.error("Errore:AggregatoreCivici inserisciNonAgganciati" ,e);
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
	 * Metodo che pulisce la tabella dell'unico dopo l'associazione. 
	 * Vengono eliminati i record di UNICO che non hanno un corrispondente in TOTALE e che hanno il
	 * campo VALIDATO diverso da 1 (validato = 1 sta a significare che il dato UNICO è stato inserito a mano da interfaccia)
	 */
	protected void pulisciUnico() throws Exception{
		
		try{			
			
			String sqlDeleteUnico = getProperty("SQL_PULISCI_CIVICO_UNICO");
			
			PreparedStatement ps = conn.prepareStatement(sqlDeleteUnico);			
			ps.execute();
			ps.close();
			
		}catch (Exception e) {
			throw new Exception("Errore nella cancellazione elementi da tabella CIVICO UNICO :" + e.getMessage());
		}
		
	}

	
	/**
	 * Metodo che carica la lista dei civici collegati al record corrente
	 */
	protected List<SitCivicoTotale>getListaCiviciAssociati(Connection connessione, String sqlRicCivTotFkVia,Object p[]) throws Exception{
		
		PreparedStatement prepStat = null;
		ResultSet resSet = null;
		List<SitCivicoTotale> lista = new ArrayList<SitCivicoTotale>();
		SitCivicoTotale civicoTotale = new SitCivicoTotale();
		
		try{
			
			prepStat = conn.prepareStatement(sqlRicCivTotFkVia);

			for(int i=0; i<p.length; i++){
				prepStat.setObject(i+1, p[i]);	
			}
			
			resSet = prepStat.executeQuery();
			
			while (resSet.next()) {
				civicoTotale = this.mappingCivico(resSet);
				lista.add(civicoTotale);
			}
			
		}catch (Exception e) {
			throw new Exception("Errore in lettura lista Civici Associati all'elemento Corrente: " + e.getMessage());
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
	
	
	@Override
	public void setConnectionForLongResultset(
			Connection connectionForLongResultset) throws AggregatoreException {
		
		this.connectionForLongResultset = connectionForLongResultset;
		
	}
	
}

