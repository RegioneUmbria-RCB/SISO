package it.webred.ct.proc.ario.test;

import it.webred.ct.data.model.indice.SitOggettoTotale;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.proc.ario.CaricatoreArioFactory;
import it.webred.ct.proc.ario.GestioneStringheVie;
import it.webred.ct.proc.ario.aggregatori.AggregatoreCivici;
import it.webred.ct.proc.ario.aggregatori.AggregatoreOggetti;
import it.webred.ct.proc.ario.aggregatori.AggregatoreSoggetti;
import it.webred.ct.proc.ario.aggregatori.AggregatoreVIe;
import it.webred.ct.proc.ario.aggregatori.BeanViario;
import it.webred.ct.proc.ario.bean.Civico;
import it.webred.ct.proc.ario.bean.Indirizzo;
import it.webred.ct.proc.ario.bean.Oggetto;
//import it.webred.ct.proc.ario.bean.SitOggettoTotale;
//import it.webred.ct.proc.ario.bean.SitSoggettoTotale;
//import it.webred.ct.proc.ario.bean.SitViaTotale;
import it.webred.ct.proc.ario.fonti.soggetto.fornitureElettriche.SoggettoEnel;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;
//import it.webred.ct.proc.ario.rule.GeneraArio;
import it.webred.ct.proc.ario.utils.ArioUtil;
import it.webred.ct.proc.ario.utils.GestioneStringheUtil;
import it.webred.ct.proc.ario.utils.NormalizzaUtil;
import it.webred.ct.proc.ario.utils.SmithWatermanGotohNibbioWindowedAffine;

import it.webred.rulengine.db.RulesConnection;
import it.webred.utils.GenericTuples;
import it.webred.utils.StringUtils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import uk.ac.shef.wit.simmetrics.similaritymetrics.SmithWaterman;
import uk.ac.shef.wit.simmetrics.similaritymetrics.TagLink;

public class MainTest {
	
		public static Properties props = null;				
	
	    private static final org.apache.log4j.Logger log = Logger.getLogger(MainTest.class.getName());

		// Below is the format of jdbc url for MySql database.
	    public static final String URL = "jdbc:oracle:thin:@172.16.2.61:1521:DBCAT";
	  
	    // The username for connecting to the database
	    public static final String USERNAME = "DIOGENE_F704";
	  
	    // The password for connecting to the database
	    public static final String PASSWORD = "DIOGENE_F704";
	  
	    public static void main(String[] args) throws Exception
	    {
	        Connection connection = null;
	        try
	        {
	         
	        	Class.forName("oracle.jdbc.driver.OracleDriver");
	             	            
	            connection = DriverManager.getConnection(URL,USERNAME, PASSWORD);
	  
	            if (connection != null){
	            	System.out.println("Connesione OK");
	            	
	           /* //Upper & Accenti	
	            	String stringa = null;	            	
	            	
	            	SitSoggettoTotale sst = new SitSoggettoTotale();
	            	
	            	sst.setCognome("POMPILI");
	            	sst.setNome("ING");
	            	sst.setDenominazione(null);
	            	sst.setSesso("m");
	            	sst.setCodfis("PMPNGI34A08L746C");
	            	sst.setPiva(null);
	            	sst.setTipoPersona(null);		
	            	sst.setDescProvNasc(null);
	            	sst.setDescComNasc(null);			
	            	sst.setDescProvRes(null);
	            	sst.setDescComRes(null);			
	            	
	            	
	            	//NormalizzaUtil nu = new NormalizzaUtil();
	            	//sst.setNome(nu.separaTitoli(sst.getNome()));
	            	
	            	NormalizzaTotali nt = new NormalizzaTotali();
	            	nt.normalizzaSoggetto(sst);
	            	
	            	System.out.println("Cognome: " + sst.getCognome());
	            	System.out.println("Nome: " + sst.getNome());
	            	System.out.println("Denominazione: " + sst.getDenominazione());
	            	System.out.println("Sesso: " + sst.getSesso());
	            	System.out.println("CodFis: " + sst.getCodfis());
	            	System.out.println("pIva: " + sst.getPiva());
	            	System.out.println("Tipo Persona: " + sst.getTipoPersona());
	            	System.out.println("Desc Prov Nasc: " + sst.getDescProvNasc());
	            	System.out.println("Desc Com Nasc: " + sst.getDescComNasc());
	            	System.out.println("Desc Prov Res: " + sst.getDescProvRes());
	            	System.out.println("Desc Com Res: " + sst.getDescComRes());
	            */	
	            
	            	
	            /*	//	Separazione Titoli
	            		           	            	
	            	String stringa = null;	            	
	            	NormalizzaTotali nt = new NormalizzaTotali();
	            	
	            	
	            	SitViaTotale svt = new SitViaTotale();
	            	
	            	svt.setNote("Test di prova, prova separa;");
	            	svt.setSedime("PZA");
	            	svt.setIndirizzo("CARROBIOLO");
	            		            	
	            	nt.normalizzaVia(svt);
	            	
	            	System.out.println("Note: " + svt.getNote());
	            	System.out.println("Sedime: " + svt.getSedime());
	            	System.out.println("Via: " + svt.getIndirizzo());
	           */ 	
	            	
	            /*	//Normalizza VIA
	            	
	            	NormalizzaTotali nt = new NormalizzaTotali();
	            	
	            	SitViaTotale svt2 = new SitViaTotale();
	            	
	            	svt2.setNote("Test 2 di prova, prova separa;");
	            	svt2.setSedime("");
	            	//svt2.setIndirizzo("VIA D'ANNUNZIO N. 11 P-T-S1");
	            	//svt2.setIndirizzo("VIA DEI PRATI N. 6 N. C P-S1");
	            	svt2.setIndirizzo("VIA DANTE ALIGHIERI 39");
	            	svt2.setIndirizzo("XX SETTEMBRE 7");
	            	
	            		            		            	
	            	nt.normalizzaVia(svt2);
	            	
	            	Indirizzo hashIndirizzo = GestioneStringheVie.restituisciIndirizzo(svt2.getIndirizzo(),svt2.getSedime());			
	    			svt2.setIndirizzo(hashIndirizzo.getInd().toString());
	    			svt2.setSedime(hashIndirizzo.getSed().toString());
	            		  	            
	            		    			
	            	System.out.println("Sedime: " + svt2.getSedime());
	            	System.out.println("Via: " + svt2.getIndirizzo());	            
	            	System.out.println("Note: " + svt2.getNote());
	            */	
	            	
	            	
	           
	            	
	            	
	         /*  // ## Estrapolazione del solo numero civico da indirizzo o da civico composto ####
	            		            
	            	//String indirizzo = "PZA GARIBALDI GIUS. 7A";	            	
	            	//String indirizzo = "MONZA (MI) - VIA A. DA BRESCIA";	            		            		            	
	            	//String indirizzo = "VIA G.BOCCACCIO CAB.391";		            	
	            	//String indirizzo = "molino s.michele 5 C1 p1";	            	
	            	//String indirizzo = "via prova 2 c3 p2";	            		            	
	            	//String indirizzo = "LGO IV NOVEMBRE KM31";	            	            		            			            		            	
	            	//String indirizzo = "VIA V. EMANUELE 30 -GIA' 16 - P.1-5";	            		            	
	            	//String indirizzo = "VIA D'ANNUNZIO N. 11 P-T-S1";	            	
	            	//String indirizzo = "VIA DEI PRATI N. 6 N. C P-S1";	            	
	            	//String indirizzo = "VIA MONTE CERVINO N. 19 - P.1 - S.1";
	            	//String indirizzo = "VIA PORTA LODI, 2 P. S1 INT. 80";
	            	//String indirizzo = "VIA MOLISE N. 16 - P.1 - S.1";
	            	
	            		            	
	            	//String indirizzo = "VIA BEATO ANGELICO 29/31 29/31";
	            	//String indirizzo = "Via 15 settembre BIANCO 1/2/ 1/4";	            		            
	            	//String indirizzo = "V MARCO D'AGRATE 38/C";
	            	
	            	
	            	String indirizzo = "XX SETTEMBRE 7";
	            	
	            	Collection collCivici = GestioneStringheVie.restituisciCivico(indirizzo);
	            	
	            	
	            	
	            	//Set s = propsSedimario.keySet();											
	    			Iterator iter = collCivici.iterator();					
	    			
	    			while (iter.hasNext()) {			       			
	    				Civico civ = (Civico)iter.next();	    				
	    				
	    				String civ1 = civ.getCivLiv1();
	    				String civ2 = civ.getCivLiv2();
	    				String civ3 = civ.getCivLiv3();
	    				String note = civ.getNote();
	    				
	    				String ind = civ.getInd().toString(); 
	    				String sed = civ.getSed().toString();
	    				
	    				System.out.println("");
	    				System.out.println("Civico 1: " + civ1);	    							
	    				System.out.println("Sedime: " + sed);
	    				System.out.println("Indirizzo: " + ind);
	    				System.out.println("Note: " + note);
	    			 }
	           	
	            */	
	            	
	            	//PADDING CAMPI OGGETTO TOTALE
	            	SitOggettoTotale sot = new SitOggettoTotale();
	            	
	            	sot.setFoglio("1");
	            	sot.setParticella("22");
	            	sot.setSub("87");	            				            		            	
	            	
	            	NormalizzaUtil.paddingOggetto(sot);
	            	
	            	System.out.println("Foglio: " + sot.getFoglio());
	            	System.out.println("Particella: " + sot.getParticella());
	            	System.out.println("Sub: " + sot.getSub());
	            	
	            	
	            	/*String s = "PROF.SA Al brvsuih";
	            	
	            	String ris = NormalizzaUtil. separaTitoli(s);
	            	
	            	System.out.println("Prima: " + s);
	            	System.out.println("Dopo: " + ris);*/
	            	
	            	//Normalizza Romani
	            	/*String lettera = GestioneStringheVie.normalizzaGiorniMese("VIA XX SETTEMBRE");
	            	System.out.println(lettera);*/
	            	
	            	
	            	//CLASSIFICAZIONE PREFISSI TOPONOMASTICI
	            	/*String ind = "viale delle sbregole,";
	            	String sedimeRif = null;
	            	
	            	String indirizzo = normalizzaAccentiAndUpper(ind);
	           
	            	Indirizzo hashIndirizzo = GestioneStringheVie.restituisciIndirizzo(indirizzo,sedimeRif);
	            	
	            	System.out.println("Sedime: "+hashIndirizzo.getSed());
	            	System.out.println("Indirizzo: "+hashIndirizzo.getInd());*/
	            	
	            	
	            	//Caricamento del file di properties dei sedime
	    			/*Properties propsSedimario = new Properties();
	    			try {			
	    				InputStream is = GestioneStringheVie.class.getResourceAsStream("/it/webred/ct/proc/ario/sedimario.properties");
	    				propsSedimario.load(is);                     
	    			}catch(Exception e) {
	    			    log.error("Eccezione: "+e.getMessage());			   
	    			}		
	    			String lettera = propsSedimario.getProperty("C SO");
	            	System.out.println(lettera);
	    			*/
	            	
	            	
	            	//Prove
	            	//SoggettoEnel se = new SoggettoEnel();
	            	
	            	/*String sql = se.getSql("TXT-ENEL@1268236250833");	            	
	            	System.out.println(sql);*/
	            	

					//Carica tutti i record da tabella DHW
	            	/*ResultSet rs = null;
	            	ResultSet pID = null;
	            	Statement st = null;
	            	PreparedStatement ps  = null;
	            	Connection connI = null;
	            	int fonteDati = 10;
	            	
	            	
	            	connI = RulesConnection.getConnection("DWH");
	            	ArioUtil au = new ArioUtil();	
	            	String procID = ""; //TXT-ENEL@1268236250833
	            	
	            	
	            	*/
	            	
	            	
	            	/*
	            	//Scrittura dei processID su tabella
	            	String sql = au.getQuerySQLSaveProcessId();
	            	au.setProcessID(connection,sql,fonteDati,procID,null,null,ps, rs);
	            	*/
	            	/*
	            	//Recupero i tutti i processID della tabella di caricamento DWH			            	
	            	String tab = se.getTable();
					rs = au.getAllProcessIdFromTabellaDWH(connection, tab, st, rs);
					while(rs.next())
					{
						System.out.println("Codice: "+ rs.getString("prID"));						
					}	      	    			
	            	*/
	            	
	            	
	            	/*
	            	//Lettura dei ProcessID da tabella Indice
	    			pID = au.getProcessidFromIndice(connection, fonteDati);
	    			
	    			while(pID.next())
					{
						System.out.println("Codice: "+ pID.getString("PROCESSID"));						
					}	      
	    			*/
	            	
	            	
	            	
	            	/*
	            	//elimino record della demografia gia' inseriti.
					au.deleteSitSoggettoTotale(fonteDati);
	            	
	            	//Caricamento RS da dati query su DHW	            	  	            	
	            	String sql = se.getSql(procID);
	            		            	          
					rs = au.getCaricamenti(connI,sql,procID,fonteDati,ps,rs);
													
					
					//Salvataggio SoggettoTotale
	            	long ti = System.currentTimeMillis();
	    			int conta =0;
	    			long totRecods = 0;
	    			
	    			String saveSql = au.getSaveSoggettoTotaleSql(se.getFkEnteSorgente());
	    			
	    			if (rs != null){
	    							
	    				while (rs.next())
	    				{
	    					
	    					
	    					SitSoggettoTotale sst = (SitSoggettoTotale)se.mapping(rs);	    					
	    						
	    					au.saveSitSoggettoTotale(connection,saveSql,sst);
	    					
	    					conta = conta +1;
	    					if (conta==1000) {
	    						long interv = System.currentTimeMillis() - ti;
	    						totRecods = totRecods + conta;
	    						conta = 0;
	    						log.debug(totRecods + " records =" + interv/1000 + " sec");
	    					}
	    					
	    				}
	    				
	    				log.info("CARICATORE - DEMOGRAFIA : Caricamento dei nuovi record in tabella");
	    			
	    			}else{
	    				log.info("CARICATORE - DEMOGRAFIA : Non ci sono dei nuovi record in tabella");
	    			}
	            	

					System.out.println("Fine Caricamento");
					
	            	
	            	
	            	
	            	
					
					if(rs!=null)
						rs.close();
					if(ps!=null)
						ps.close();
					if(pID!=null)
						pID.close();
					if(st!=null)
						st.close();
					if(connI!=null)
						connI.close();
					
					
	            	//Fine Prove
	            	*/
	            	
	            	
	            	/*
	            	 * GESTIONE SPILT SUBALTERNI IN OGGETTI
	            	 */
	            	
	            	/*SitOggettoTotale sot = new SitOggettoTotale();
	            	
	            	sot.setCategoria("Prova");
	            	sot.setCtrHash("xxxx87x78xxx7xxx232");
	            	sot.setEnteSorgente(10);
	            	sot.setProgEs(1);
	            	sot.setFkOggetto("xxxxx");
	            	sot.setFoglio("12");	            	
	            	sot.setParticella("13");
	            	sot.setSub("A13 23B");	            		            		            		            	
	            	
	            	Collection<Oggetto> collOgg = GestioneStringheUtil.restituisciSub(sot.getFoglio(), sot.getParticella(), sot.getSub());
	            		            
	            	
	            	Iterator iter = collOgg.iterator();
	            	
	            	while(iter.hasNext()){
	            		
	            		Oggetto ogg = (Oggetto)iter.next();
	            		
	            		sot.setFoglio(ogg.getFoglio());
	            		sot.setParticella(ogg.getParticella());
	            		sot.setSub(ogg.getSub());
	            		sot.setAnomalia(ogg.getAnomalia());
	            		
	            		NormalizzaUtil.paddingOggetto(sot);
	            		
	            		System.out.println("");
	            		System.out.println("CATEGORIA: " + sot.getCategoria());
		            	System.out.println("CTR HASH: " + sot.getCtrHash());
		            	System.out.println("ENTE SORG: " + sot.getEnteSorgente());
		            	System.out.println("PROG.ES. : " + sot.getProgEs());
		            	System.out.println("FK OGGETTO: " + sot.getFkOggetto());
		            	System.out.println("FOGLIO: " + sot.getFoglio());
		            	System.out.println("PARTICELLA: " + sot.getParticella());
		            	System.out.println("SUB: " + sot.getSub());
		            	System.out.println("ANOMALIA: " + sot.getAnomalia());
	            		
	            	}
	            	*/
	            	
	            	
	            	
	            	
	            	
	            	
	            	/*CARICAMENTO*/
	            	//Lancio dei Caricatori
	            /*	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
	            	 
	            	Date resultdate = new Date( System.currentTimeMillis());
	            	
	            	System.out.println("Inizio Caricamento : " + sdf.format(resultdate));
	            		            	
	            	
	            	//ArrayList<String> listaFonti = new ArrayList<String>();	            	
	            	//listaFonti.add(Constants.ES_FORNITURE_ELETTRICHE);
	            	//listaFonti.add(Constants.ES_GAS);
	            	//listaFonti.add(Constants.ES_DEMOGRAFIA);
	            	//listaFonti.add(Constants.ES_CATASTO);
	            	//listaFonti.add(Constants.ES_LOCAZIONI);
	            	
	            	if (props==null) {	    				
	    				//Caricamento del file di properties dei caricatori		
	    		        props = new Properties();
	    		        try {
	    		            InputStream is = MainTest.class.getResourceAsStream("/entiSorgente.properties");
	    		            props.load(is);  
	    		            	    		          
	    		        }catch(Exception e) {
	    		            log.error("Eccezione: "+e.getMessage());	    		            
	    		        }
	    			}
	            	
	            	Set<String> s = props.stringPropertyNames();	            	
	            	Iterator it = s.iterator();	      
	            	
	            	
	            	
	            	ArrayList<String> listaFonti = new ArrayList<String>();
	            	//CARICO TUTTE LE FONTI DA PROPERTIES
		    		if(props != null){
		    			
		    			Set<String> s1 = props.stringPropertyNames();            	
		            	Iterator it1 = s1.iterator();	            		            	
		            	
		            	while(it1.hasNext()){	            		
		            		String chiave = it1.next().toString();	
		            		log.info("INFO: Chiave da properties enti Sorgente: "+chiave);
		            		
		            		listaFonti.add(props.getProperty(chiave));
		            	}		    		
		    		}	    	
	            	
	            	
	            	while(it.hasNext()){	            		
	            		String chiave = it.next().toString();
	            		System.out.println("Chiave : " + chiave);
	            	}
	            	
	            	CaricatoreArioFactory caf = new CaricatoreArioFactory(listaFonti);
	            	
	            	caf.Execute("F704", connection, listaFonti, null);
	            
	            	
	            	resultdate = new Date( System.currentTimeMillis());
	            	System.out.println("Fine esecuzione CARICATORI "+ sdf.format(resultdate));
	            	connection.commit();
				*/
	            	
	            	
	            /**
	             * Chiamata a genario	
	             */
	            	//GeneraArio ga = new Ge
	           
	            	
	            	
	            /***
	             * ############## TEST DI AGGREGAZIONE ##############################################################################
	             */
	            	
	            	/*
	            	 * AGGREGAZIONE VIE (OK)
	            	 */
	            	/*log.debug("Inizio aggregazione VIE");
	    			AggregatoreVIe via = new AggregatoreVIe();
	    			via.setConnection(connection);
	    			via.aggrega();
	    			connection.commit();
	    			log.debug("Fine aggregazione VIE");*/
	            	
	    			/*
	            	 * AGGREGAZIONE CIVICI
	            	 */
	            	/*log.debug("Inizio aggregazione Civici");
	    			AggregatoreCivici civ = new AggregatoreCivici();
	    			civ.setConnection(connection);
	    			civ.aggrega();
	    			connection.commit();
	    			log.debug("Fine aggregazione Civici");*/
	    			
	            	
	            	/*
	            	 * AGGREGAZIONE SOGGETTI
	            	 */
	            	/*log.debug("Inizio aggregazione Soggetti");
	    			AggregatoreSoggetti sog = new AggregatoreSoggetti();
	    			sog.setConnection(connection);
	    			sog.aggrega();
	    			connection.commit();
	    			log.debug("Fine aggregazione Soggetti");
	    			*/	    				    			
	            	/*
	            	 * AGGREGAZIONE OGGETTI
	            	 */
	            	/*log.debug("Inizio aggregazione Oggetti");
	    			AggregatoreOggetti og = new AggregatoreOggetti();
	    			og.setConnection(connection);
	    			og.aggrega();
	    			connection.commit();
	    			log.debug("Fine aggregazione Oggetti");
	    			*/
	            	
	            	
	    		/***
	             * ############## FINE TEST DI AGGREGAZIONE ############################################################################## 	
	    		 */
	            	
	        
	            	
	    /*        	float perc = 0;
	        		BeanViario trovato = null;
	        		BeanViario trovatoInViaUnico = null;
	        		BeanViario vu = new BeanViario();
	        		BeanViario vnr = new BeanViario();
	        		
	        		
	        		vnr.setSedime("PIAZZA");
	        		vnr.setIndirizzo("BRAILLE");
	        		
	        		vu.setSedime("VIA");
	        		vu.setIndirizzo("BRAILLE");
	        		
	            	
	            	float percTrovata = new SmithWaterman().getSimilarity(vnr.getIndirizzoNormalizzato(),vu.getIndirizzoNormalizzato());
					if(percTrovata < 0.3)
						System.out.println("continue");
					
					if(percTrovata > GestioneStringheVie.SMITHWATERMAN_PERC_LIMITE_SUP && percTrovata > perc){
						perc = percTrovata-(float)0.20;
						trovato = vnr;
						trovatoInViaUnico = vu;						
					}						
					percTrovata = new TagLink().getSimilarity(vnr.getIndirizzoNormalizzato(),vu.getIndirizzoNormalizzato());
					if(percTrovata > GestioneStringheVie.TAGLINK_PERC_LIMITE_SUP && percTrovata > perc){
						perc = percTrovata-(float)0.15;
						trovato = vnr;
						trovatoInViaUnico = vu;						
					}
					percTrovata = new SmithWaterman().getSimilarity(vnr.getSedime()+" "+vnr.getIndirizzoNormalizzato(),vu.getSedime()+" "+ vu.getIndirizzoNormalizzato());
					if(percTrovata > GestioneStringheVie.SMITHWATERMAN_PERC_LIMITE_INF && percTrovata > perc){
						perc = percTrovata;
						trovato = vnr;
						trovatoInViaUnico = vu;						
					}						
					percTrovata = new TagLink().getSimilarity(vnr.getSedime()+" "+vnr.getIndirizzoNormalizzato(),vu.getSedime()+" "+ vu.getIndirizzoNormalizzato());
					if(percTrovata > GestioneStringheVie.TAGLINK_PERC_LIMITE_INF && percTrovata > perc){
						perc = percTrovata;
						trovato = vnr;
						trovatoInViaUnico = vu;					
					}						
	            	
	            	
					if(trovato != null  && ((vnr.getIndirizzo().length() > 2 && trovatoInViaUnico.getIndirizzo().length() > 2 ) || (vnr.getIndirizzo().length() < 2 && trovatoInViaUnico.getIndirizzo().length() < 2 ))){
						System.out.println("Le vie sono state associate con Percentuale: "+perc);
					}

		*/			
	            	
	            	
	            	
	            	/*//Lancio aggregatore VIA
	            	log.debug("Inizio aggregazione vie");
	    			AggregatoreVIe vie = new AggregatoreVIe();
	    			vie.setConnection(connection);
	    			vie.aggrega();

	    			connection.commit();
	            	
	            	
	            	//Lancio aggregatore CIVICO
	            	log.debug("Inizio aggregazione civici");
	    			AggregatoreCivici c = new AggregatoreCivici();
	    			c.setConnection(connection);
	    			c.aggrega();
	    			connection.commit();*/
	            	
	            	//Lancio aggregatore SOGGETTI
	            	/*log.debug("Inizio aggregazione soggetti");
	    			AggregatoreSoggetti sog = new AggregatoreSoggetti();
	    			sog.setConnection(connection);
	    			sog.aggrega();
	    			connection.commit();*/
	            	
	            	//Lancio aggregatore OGGETTO
	            	/*log.debug("Inizio aggregazione oggetti");
	    			AggregatoreOggetti og = new AggregatoreOggetti();
	    			og.setConnection(connection);
	    			og.aggrega();
	    			connection.commit();*/

	    			//new ApplicationAck("GeneraArio eseguito");

	            		            		            		            	
	            	
	            	
	            	/***
		             * ############## CHIAMATA A GENARIO ############################################################################## 	
		    		 */
	            	
	            	
	            	/*String s = "VIA XX SETTEMBRE";	            	            	
	            	String descrInd = GestioneStringheVie.normalizzaGiorniMese(s);
	            	System.out.println("Risultato: "+descrInd);*/
	            	
		            	
	            	/*SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
	              	 
	            	Date resultdate = new Date( System.currentTimeMillis());
	            	
	            	System.out.println("Inizio Caricamento : " + sdf.format(resultdate));
	            		            	
	            	
	            	GeneraArio ga = new GeneraArio();
	            	
	            	
	            	resultdate = new Date( System.currentTimeMillis());
	            	System.out.println("Fine esecuzione CARICATORI "+ sdf.format(resultdate));
	            	connection.commit();*/
					
	            	/***
		             * ############## FINE CHIAMATA A GENARIO ############################################################################## 	
		    		 */
		            
	            	
	            	
	            	//String s1 = "LIBERTA`";
	            	//String s1 = "DORA'?A'";
	            	//String s1 = "UMBERTO IA'?A'�";
	            	//String s1 = "SEGRA'?A'�";
	            	//String s1 = "LIBERTA'?A'";
	            	//String s1 = "MACALLA'?A'�";	            	
	            	//String s1 = "IA'?A'� MAGGIO";
	            	//String s1 = "NICOLO` PAGANINI";
	            	String s1 = "LIBERTA'"+String.valueOf((char)160);
	            	
	            	String s2 = s1.replaceAll("`", "'");
	            	System.out.println("String formattata:" + s2);
	            	
	            	String s3 = normalizzaAccentiAndUpper(s1);
	            	System.out.println("String formattata con Upper:" + s3);
	            	
	            	
	            	
	            	//float resTag = new TagLink().getSimilarity(s1,s2);
	             	//GenericTuples.T2<Boolean,Float>  oggettoDaAssociare = SmithWatermanGotohNibbioWindowedAffine.getSimilarity("VIA", " ", s1, s2);	
						

	    			//System.out.println("Perc. Ass. :"+resTag);
	            	
	            	
	            }else{
	            	System.out.println("Connesione NO");
	            }
	            
	        } finally
	        {
	            if (connection != null)
	            {
	                connection.close();
	            }
	        }
	    }
	    
	    
	    private static String normalizzaAccentiAndUpper(String stringa) {				    
	    		
	    /*	stringa = stringa==null?"":stringa.toUpperCase().trim();							
			
	    	stringa = stringa.replaceAll("`", "'");
			stringa = stringa.replaceFirst("\\?A'", "");
			stringa = stringa.replaceFirst("IA'", "I");
			stringa = stringa.replaceAll("�", "");
			stringa = stringa.replaceAll("�", "");
			
			//stringa = stringa.replaceAll("' ", "'");
			//stringa = stringa.replaceAll(". ", ".");
			stringa = stringa.replaceAll(String.valueOf((char)160),"");		//non-breaking space &nbsp;
		 						
			String UPPERCASE_ASCII =
			    "AEIOU" // grave
			    + "AEIOUY" // acute
			    + "AEIOUY" // circumflex
			    + "AON" // tilde
			    + "AEIOUY" // umlaut
			    + "A" // ring
			    + "C" // cedilla
			    + "OU" // double acute
			    ;

			// stesso numero di caratteri di quella sopra
			  String UPPERCASE_UNICODE =
			    "\u00C0\u00C8\u00CC\u00D2\u00D9"
			    + "\u00C1\u00C9\u00CD\u00D3\u00DA\u00DD"
			    + "\u00C2\u00CA\u00CE\u00D4\u00DB\u0176"
			    + "\u00C3\u00D5\u00D1"
			    + "\u00C4\u00CB\u00CF\u00D6\u00DC\u0178"
			    + "\u00C5"
			    + "\u00C7"
			    + "\u0150\u0170"
			    ;

			  StringBuilder sb = new StringBuilder();
		       int n = stringa.length();
		       for (int i = 0; i < n; i++) {
		          char c = stringa.charAt(i);
		          int pos = UPPERCASE_UNICODE.indexOf(c);
		          if (pos > -1){
		            sb.append(UPPERCASE_ASCII.charAt(pos));
		            sb.append("'");
		          }
		          else {
		            sb.append(c);
		          }
		       }
		       return sb.toString();
*/	    	
	    		stringa = stringa==null?"":stringa.toUpperCase().trim();							
	    		
	    		//Converto gli accenti inversi e sequenze particolari
	    		stringa = stringa.replaceAll("`", "'");
	    		stringa = stringa.replaceFirst("\\?A'", "");
	    		stringa = stringa.replaceFirst("IA'", "I");
	    		//stringa = stringa.replaceAll("' ", "'");
	    		//stringa = stringa.replaceAll(". ", ".");
	    		stringa = stringa.replaceAll(String.valueOf((char)160),"");
	    		
	    		try {			   
	    		    String source = stringa;
	    		   
	    		    String UPPERCASE_UNICODE ="\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C6\u00C7\u00C8\u00C9\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF\u00D0" +
	    									  "\u00D1\u00D2\u00D3\u00D4\u00D5\u00D6\u00D7\u00D8\u00D9\u00DA\u00DB\u00DC\u00DD\u00DE\u00DF\u00E0\u00E1" +
	    									  "\u00E2\u00E3\u00E4\u00E5\u00E6\u00E7\u00E8\u00E9\u00EA\u00EB\u00EC\u00ED\u00EE\u00EF\u00F0\u00F1\u00F2" +
	    									  "\u00F3\u00F4\u00F5\u00F6\u00F7\u00F8\u00F9\u00FA\u00FB\u00FC\u00FD\u00FE\u00FF\u0021\u0023\u0024\u0025" +
	    									  "\u0026\u0028\u0029\u002A\u002B\u002C\u002D\u002E\u002F\u003A\u003B\u003C\u003D\u003E\u003F\u0040\u005D" +
	    									  "\u005B\u005E\u005F\u0060\u007B\u007C\u007D\u007E\u00A1\u00A2\u00A3\u00A4\u00A5\700A6\u00A7\u00A8\u00A9" +
	    									  "\u00AA\u00AB\u00AC\u00AE\u00AF\u00B0\u00B1\u00B2\u00B3\u00B5\u00B6\u00B7\u00B8\u00B9\u00BB\u00BC\u00BD" +
	    									  "\u00BE\u00BF\u02BA\u0150\u0170";
	    		    
	    		  
	    		    StringBuffer sb = new StringBuffer();
	    			int n = stringa.length();				      
	    			String result = stringa;
	    			for (int i = 0; i < n; i++) {
	    				char c = stringa.charAt(i);
	    				String g = String.valueOf(c);
	    				int pos = UPPERCASE_UNICODE.indexOf(c);
	    				if (pos > -1){
	    				       	  
	    					if (g.equals("\u00C0") || g.equals("\u00C1") || g.equals("/u00C2") || g.equals("/u00C3") || g.equals("/u00C4") || g.equals("/u00C5")) {							       				        		 
	    						result = result.replaceFirst(g, "A"); 
	    					}
	                	    else if (g.equals("\u00C8") || g.equals("\u00C9") || g.equals("\u00CA") || g.equals("\u00CB")) {							      
	                	    	result = result.replaceFirst(g, "E"); 
	    					}
	    					else if (g.equals("\u00CC") || g.equals("\u00CD") || g.equals("\u00CE") || g.equals("\u00CF")) {							       
	    						result = result.replaceFirst(g, "I"); 
	    					}
	    					else if (g.equals("\u00D0")) {
	    						result = result.replaceFirst(g, "D");   							       
	    					}
	    					else if (g.equals("\u00D1")) {							       
	    						result = result.replaceFirst(g, "N"); 
	    					}
	    					else if (g.equals("\u00D2") || g.equals("\u00D3") || g.equals("\u00D4") || g.equals("\u00D5") || g.equals("\u00D6")) {						     
	    						result = result.replaceFirst(g, "O"); 
	    					}
	    					else if (g.equals("\u00D9") || g.equals("\u00DA") || g.equals("\u00DB") || g.equals("\u00DC")) {					       
	    						result = result.replaceFirst(g, "U"); 
	    					}
	    					else if (g.equals("\u00DD")) {
	    						result = result.replaceFirst(g, "Y");  							       
	    					}
	    					else if (g.equals("\u00E0") || g.equals("\u00E1") || g.equals("\u00E2") || g.equals("\u00E3") || g.equals("\u00E4") || g.equals("\u00E5")) {							       
	    						result = result.replaceFirst(g, "a"); 
	    					}
	    					else if (g.equals("\u00E8") || g.equals("\u00E9") || g.equals("\u00EA") || g.equals("\u00EB")) {							       
	    				    	result = result.replaceFirst(g, "e"); 
	    					}
	    					else if (g.equals("\u00EC") || g.equals("\u00ED") || g.equals("\u00EE") || g.equals("\u00EF") ) {							      
	    						result = result.replaceFirst(g, "i"); 
	    					}
	    					else if (g.equals("\u00F1")) {							       
	    						result = result.replaceFirst(g, "n"); 
	    					}
	    					else if (g.equals("\u00F2") || g.equals("\u00F3") || g.equals("\u00F4") || g.equals("\u00F5") || g.equals("\u00F6")) {							     
	    						result = result.replaceFirst(g, "o"); 
	    					}
	    					else if (g.equals("\u00F9") || g.equals("\u00FA") || g.equals("\u00FB") || g.equals("\u00FC") ) {						        
	    						result = result.replaceFirst(g, "u"); 
	    					}
	    					else if (g.equals("\u00FD") || g.equals("\u00FF")) {						       
	    						result = result.replaceFirst(g, "y"); 
	    					}
	    					else if (g.equals("\u0060")) {						       
	    						result = result.replaceFirst(g, "'"); 
	    					}
	    					else if (g.equals("\u00FE") || g.equals("\u00C6") || g.equals("\u00C7") || g.equals("\u00D7") || g.equals("\u00D8") || g.equals("\u00DE") ||
	    							 g.equals("\u00DF") || g.equals("\u00E6") || g.equals("\u00E7") || g.equals("\u00F0") || g.equals("\u0021") || 
	    							 g.equals("\u0023") || g.equals("\u0024") || g.equals("\u0025") || g.equals("\u0026") || g.equals("\u0028") || g.equals("\u0029") ||
	    							 g.equals("\u002A") || g.equals("\u002B") || g.equals("\u002C") || g.equals("\u002D") || g.equals("\u002E") || g.equals("\u002F") ||
	    							 g.equals("\u003A") || g.equals("\u003B") || g.equals("\u003C") || g.equals("\u003D") || g.equals("\u003E") || g.equals("\u003F") ||
	    							 g.equals("\u0040") ||						 
	    							 g.equals("\u005D") ||
	    							 g.equals("\u005B") || g.equals("\u005E") || g.equals("\u005F") ||
	    							 g.equals("\u007B") || g.equals("\u007C") || g.equals("\u007D") || g.equals("\u007E") || g.equals("\u00A1") || g.equals("\u00A2") ||
	    							 g.equals("\u00A3") || g.equals("\u00A4") || g.equals("\u00A5") || g.equals("\700A6") || g.equals("\u00A7") || g.equals("\u00A8") ||
	    							 g.equals("\u00A9") || g.equals("\u00AA") || g.equals("\u00AB") || g.equals("\u00AC") || g.equals("\u00AE") || g.equals("\u00AF") ||
	    							 g.equals("\u00B0") || g.equals("\u00B1") || g.equals("\u00B2") || g.equals("\u00B3") || g.equals("\u00B4") || g.equals("\u00B5") ||
	    							 g.equals("\u00B6") || g.equals("\u00B7") || g.equals("\u00B8") || g.equals("\u00B9") || g.equals("\u00BA") || g.equals("\u00BB") ||
	    							 g.equals("\u00BC") || g.equals("\u00BD") || g.equals("\u00BE") || g.equals("\u00BF") || g.equals("\u02BA") || g.equals("\u0150") || g.equals("\u0170")) {
	    							       							
	    									 g="\\"+g; 
	    									 result = result.replaceFirst(g, ""); 
	    					}else {
	    						sb.append(c);
	    					}					        	  					     
	    				}else {
	    					sb.append(c);
	    				}
	    			}
	    			
	    			return sb.toString();  		        	  	
	    		    
	    		  } finally {		    
	    		  }	
	   	       
	    	}

	    
	    private static String getProperty(String propName) {
			
			if (props==null) {
				
				//Caricamento del file di properties dei caricatori		
		        props = new Properties();
		        try {
		            InputStream is = MainTest.class.getResourceAsStream("/entiSorgenti.properties");
		            props.load(is);                     
		        }catch(Exception e) {
		            log.error("Eccezione: "+e.getMessage(), e);
		            return null;
		        }
			}
			
			String p = props.getProperty(propName);
			
			if (p==null)
				p = props.getProperty(propName);
				
			return p;
		}
		
	
}




   
	  
