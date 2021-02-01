package it.webred.ct.proc.ario.aggregatori;

import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
//import it.webred.ct.proc.ario.bean.SitViaTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.utils.SmithWatermanGotohNibbioWindowedAffine;
import it.webred.utils.GenericTuples;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;


public class AggregatoreVIe extends TipoAggregatore
{
	
	String SEPARATOR = "####";
	private boolean associazione = false;
	private boolean sostituzioneEleCorr = false;
	private static final Logger	log	= Logger.getLogger(AggregatoreVIe.class.getName());
	private Connection conn = null;
	private Object hackLastIdVia = null;		//hack per memorizzare l'id via quando elabora trova valorizzato lvieAltre con una sola via
	QueryRunner run = new QueryRunner();
	
	private long totNumRecAggiornati = 0;
	
	public void aggrega(String codEnte, HashParametriConfBean paramConfBean) throws AggregatoreException {
		
		try{			
			
			log.info("INIZIO Aggregazione dati di rifererimento");
			
			ArrayList<Object[]> paramFonteProgr = (ArrayList<Object[]>) run.query(conn,getProperty("SQL_FONTE_PROGRESSIVO_RIF_VIE"), new ArrayListHandler());
			if (paramFonteProgr == null)
				log.info("!!!!! ERRORE: FONTE DI RIFERIMENTO VIE NON TROVATA !!!!! continuo...");
			else{
				run.update(conn,getProperty("SQL_INSERISCI_VIA_RIFERIMENTO"),paramFonteProgr.get(0));
				run.update(conn,getProperty("SQL_AGGIORNA_DATI_DEFAULT_VIA_TOT"),paramFonteProgr.get(0));							
			}
			conn.commit();
			
			log.info("FINE aggregazione dati di riferimento");
			
			//creo o aggiorno l'indice testuale di unico
			this.createTextIndex();
			
			//Effettuo caricamento viario Unico
			//HashMap<String, BeanViario> viarioUnico = this.caricaViarioUnico();		
						
			//Effettuo il caricamento della lista dei TOTALI da aggregare			
			ResultSet lVieTotDaAggr = this.caricaDatiDaAggregare();
			
			
			log.info("Inizio Elaborazione controllo associabilità Vie in UNICO");						
			//this.elabora(conn,viarioUnico,lVieTotDaAggr);
			this.elabora(null,lVieTotDaAggr);
			log.info("Fine Elaborazione controllo associabilità Vie in UNICO");
						
			//Pulizia tabella UNICO (cancello tutti i record non collegati a totale e Validato <> 1)
			log.info("Inizio cancellazione elementi in UNICO");
			this.pulisciUnico();
			log.info("Fine cancellazione elementi in UNICO");									
			
		}catch (Exception e){
			
			try{
				conn.rollback();
			}catch(Exception er){
				log.error("ERRORE IN ROLLBACK AGGREGAZIONE VIE");
			}
			
			e.printStackTrace();
			log.error("Errore:Aggregatore=" + e.getMessage());
			AggregatoreException ea = new AggregatoreException(e.getMessage());
			throw ea;
		}
		finally{
		}

	}

	
	/**
	 * Controlla che due indirizzi siano simili
	 * Veridicando che uno sia il puntato dell'altro
	 * Per esempio Giuseppe Garibaldi con G. Garibaldi
	 * @param vu
	 * @param vnr
	 * @return
	 */
	private boolean associaSedimeDescrizionePuntata(BeanViario vu, BeanViario vnr) throws Exception {	
		try {
				/*if(vu.getSedime()==null || (vu.getSedime()!=null && vnr.getSedime()!=null && !vu.getSedime().equals(vnr.getSedime()))){
					return false;
				}*/		
				
				return GestioneStringheVie.ugualiSenzaPuntato(vu.getIndirizzo(),vnr.getIndirizzo());
		} catch (Exception e) {    		
			throw new Exception("Errore in confronto Descrizione Puntata "+e.getMessage());
    	}
	}
		

	public void setConnection(Connection conn)
	{
		this.conn = conn;
	}
		
			
	//private void elabora(Connection conn, HashMap<String, BeanViario> viarioUnico, List<BeanViario> lVieTotDaAggr) throws Exception {
	private void elabora(HashMap<String, BeanViario> viarioUnico, ResultSet lVieTotDaAggr) throws Exception {			
		try{
			
			int i = 0;
			while (lVieTotDaAggr.next()) {				
								
				BeanViario vnr = new BeanViario();
				vnr.setIddwh(lVieTotDaAggr.getString("IDDWH"));
				vnr.setFkentesorgente(lVieTotDaAggr.getString("FKENTESORGENTE"));
				vnr.setIndirizzo(lVieTotDaAggr.getString("INDIRIZZO"));
				vnr.setSedime(lVieTotDaAggr.getString("SEDIME"));
				vnr.setProges(lVieTotDaAggr.getString("PROGES"));
				vnr.setCodiceViaOrig(lVieTotDaAggr.getString("CODICEVIAORIG"));
				
				associazione = false;
				
				//Controlla Criterio A0 (Codice Via Fornito dall'ente)
				if(vnr.getCodiceViaOrig() != null){
					
					String codVia = this.cercaViaUnico(vnr.getCodiceViaOrig());
					if(codVia!=null){
						//Associo la via
						Object params[] = new Object[7];
						params[0]=codVia;
						params[1]=null;						//TODO note
						params[2]=100;
						params[3]="CAV-A00";				
						params[4]=vnr.getIddwh();
						params[5]=vnr.getFkentesorgente();
						params[6]=vnr.getProges();
						run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
		
						associazione = true;
					}else{
						//Controlla Criterio A1 (Associazione tra due vie di fonti diverse)
						String codViaUnico = this.cercaViaInFontiDiverse(vnr);
						
						//Controllo la presenza del codice in unico
						codVia = this.cercaViaUnico(codViaUnico);
						
						if(codVia!=null){
							//Associo la via
							Object params[] = new Object[7];
							params[0]=codVia;
							params[1]=null;						//TODO note
							params[2]=100;
							params[3]="CAV-A10";				
							params[4]=vnr.getIddwh();
							params[5]=vnr.getFkentesorgente();
							params[6]=vnr.getProges();
							run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
							
							associazione = true;
						}	
					}
					
				}else{
					associazione = false;
				}
				
				if(!associazione){
					
					String vnrIndirizzoNormalizzato = vnr.getIndirizzoNormalizzato();
					//Controlla Criterio A2 (Ugualianza descrizione)
					BeanViario viaUgualeNelUnico = cercaIndirizzoUnico(vnrIndirizzoNormalizzato);						
					if (viaUgualeNelUnico != null)
					{
						
						// associo la via 
						Object params[] = new Object[7];
						params[0]=viaUgualeNelUnico.getIdvia();					
						params[1]=null;//TODO note???
						params[2]=100;
						if(viaUgualeNelUnico.getIndirizzoNormalizzato().equals(vnrIndirizzoNormalizzato))
							params[3]="UGUALI";
						else params[3]=viaUgualeNelUnico.getReldescr();
						params[4]=vnr.getIddwh();
						params[5]=vnr.getFkentesorgente();
						params[6]=vnr.getProges();
						run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
							
						associazione = true;
					}
						
					if(!associazione){
						//Controllo Criterio A3/A5 (Corrispondenza Dati Catastali con ugual prefisso)
							
						if(!associazione){
							//Controllo Criterio A4/A6 (Soggetti relazionati ad una via con ugual prefisso)
							
							if(!associazione){
								//aggiorno il viaro unico con quello personalizzato per l'indirizzo che sto cercando
								viarioUnico = this.caricaViarioUnicoFuzzy(vnr.getIndirizzo());	
								
								//Cotrollo Criterio A6/A7 (Similitudine descrizione con uguale o differnte prefisso)
								this.associaVie(viarioUnico,vnr);
											
								if(!associazione){
									//Effettuo l'inserimento in UNICO del nuovo elemento
									this.inserisciInUnico(viarioUnico,vnr);
										
									//devo sincronizzare l'indice testuale di unico
									this.syncTextIndex();
									
									//Devo rileggere gli elementi presenti in unico				
									//viarioUnico = this.caricaViarioUnico();												
								}
							}													
						}
					}
				}
				
				//Effettuo la commit dei dati su DB
				i++;
				totNumRecAggiornati = totNumRecAggiornati + 1;
				if(i > 50000){
					i=0;
					log.debug("Aggiornamento record " + totNumRecAggiornati) ;
					conn.commit();
				}
				
			}
			
			conn.commit();
			
		}catch(Exception e){					
			conn.rollback();
			throw new Exception("Errore in operazione aggregazione fonte riferimento VIE: "+e.getMessage());
		}
		finally{
			if(lVieTotDaAggr!=null)
				lVieTotDaAggr.close();
		}
	
	}
	
	
	
	/**
	 * Metodo che legge i dati dalla tabella SIT_VIA_UNICO
	 * @return
	 * @throws Exception
	 */
	protected HashMap<String, BeanViario> caricaViarioUnico() throws Exception{
		
		// Leggo vie da viario principale		
		String sqlVieUnico = getProperty("SQL_LEGGI_VIE_UNICO");
		
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sqlVieUnico);
		
		HashMap<String, BeanViario> viarioUnico = new HashMap<String, BeanViario>();		
		
		try {
			while (rs.next()) {
				BeanViario bv = new BeanViario();
				bv.setIdvia(rs.getString("IDVIA"));
				bv.setIndirizzo(rs.getString("INDIRIZZO"));
				bv.setSedime(rs.getString("SEDIME"));
				viarioUnico.put(rs.getString("INDIRIZZO") + SEPARATOR + rs.getString("SEDIME"), bv);				
			}
			
		return viarioUnico;	
			
		} catch (Exception e) {
			throw new Exception ("Errore in caricamento dati da tabella SIT_VIA_UNICO "+e.getMessage());
		} finally {
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
		}				
	}
	
	/**
	 * Metodo che legge i dati dalla tabella SIT_VIA_UNICO in modo FUZZY
	 * @return
	 * @throws Exception
	 */
	protected HashMap<String, BeanViario> caricaViarioUnicoFuzzy(String indirizzo) throws Exception{
				
		//normalizzo l'indirizzo
		indirizzo = indirizzo.replaceAll("[\\.]", " ");
		indirizzo = indirizzo.replaceAll("[\\']", " ");
		String u[] = indirizzo.split("[\\s]+");
		//sembra che il metodo fuzzy non permette parole minori di 3 caratteri(ORA-29902) quindi formatto
		String paramBase = "";
		for(int i = 0; i < u.length; i++ ){
			if(u[i].length() > 3)
				paramBase += (u[i] + " ");
		}
		paramBase = paramBase.trim();
		String param1 = paramBase;
		String param2 = paramBase;
		String param3 = paramBase;
		String param4 = paramBase;
		
		if(!paramBase.equals("")){
			String v[] = paramBase.split("[\\s]+");
			param1 = v[0];
			param4 = v[0];
			if(v.length > 1){
				param2 = v[1];
				if(v.length > 2)
					param3 = v[2];
			}
		}
		
		//se è vuoto vuol dire che l'indirizzo ha solo parole da 2 o meno caratteri 
		if(param1.equals("")){
			param1 = indirizzo;
			param2 = indirizzo;
			param3 = indirizzo;
			param4 = indirizzo;
		}
		
		// Leggo vie da viario principale		
		String sqlVieUnico = getProperty("SQL_LEGGI_VIE_UNICO_FUZZY");
		sqlVieUnico = sqlVieUnico.replace("@1", param1);
		sqlVieUnico = sqlVieUnico.replace("@2", param2);
		sqlVieUnico = sqlVieUnico.replace("@3", param3);
		sqlVieUnico = sqlVieUnico.replace("@4", param4);
		
		ResultSet rs = null;
		Statement st = conn.createStatement();
		
		HashMap<String, BeanViario> viarioUnico = new HashMap<String, BeanViario>();		
		
		try {
			rs = st.executeQuery(sqlVieUnico);
			
			while (rs.next()) {
				BeanViario bv = new BeanViario();
				bv.setIdvia(rs.getString("IDVIA"));
				bv.setIndirizzo(rs.getString("INDIRIZZO"));
				bv.setSedime(rs.getString("SEDIME"));
				viarioUnico.put(rs.getString("INDIRIZZO") + SEPARATOR + rs.getString("SEDIME"), bv);				
			}
			
		return viarioUnico;	
			
		} catch (Exception e) {
			log.info("_____ERRORE FUZZY____: " + sqlVieUnico);
			throw new Exception ("Errore in caricamento dati da tabella SIT_VIA_UNICO in modo FUZZY "+e.getMessage());
		} finally {
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
		}				
	}
	
	/**
	 * Metodo che crea l'indice testuale per SIT_VIA_UNICO, necessario per la ricerca FUZZY
	 * @return
	 * @throws Exception
	 */
	protected void createTextIndex() throws Exception{
				
		String sqlDropIndex = getProperty("SQL_DROP_VIA_UNICO_TEXT_INDEX");
		String sqlCreateIndex = getProperty("SQL_CREA_VIA_UNICO_TEXT_INDEX");
		
		Statement st = conn.createStatement();
		try {
			st.executeUpdate(sqlDropIndex);
		} catch (Exception e) {
			//non esiste
		}
		
		try {
			st.executeUpdate(sqlCreateIndex);	
		} catch (Exception e) {
			throw new Exception ("Errore in CREAZIONE INDICE TEXT "+e.getMessage());
		} finally {
			if(st!=null)
				st.close();
		}				
	}
	
	/**
	 * Metodo che esegue la sincronizzazione dell'indice testuale per SIT_VIA_UNICO, necessario per la ricerca FUZZY
	 * @return
	 * @throws Exception
	 */
	protected void syncTextIndex() throws Exception{
				
		String sqlSyncIndex = getProperty("SQL_SYNC_VIA_UNICO_TEXT_INDEX");
		CallableStatement proc = conn.prepareCall(sqlSyncIndex);
		
		try {
		    proc.execute();
		} catch (Exception e) {
			throw new Exception ("Errore in SYNC INDICE TEXT "+e.getMessage());
		} finally {
			if(proc!=null)
				proc.close();
		}				
	}
	
	/**
	 * Metodo che legge i dati da aggregare dalla tabella SIT_VIA_TOTALE
	 */
	protected ResultSet caricaDatiDaAggregare() throws Exception{					
		
		//List<BeanViario> lvieAltre = null;	
		ResultSet rs = null;
		
		try {
			
			//BeanListHandler h = new BeanListHandler(BeanViario.class);			
			
			String sql = getProperty("SQL_LEGGI_VIA_TOTALE");
				
			Statement st = connectionForLongResultset.createStatement();
			rs = st.executeQuery(sql);
			//lvieAltre = (List<BeanViario>) run.query(conn, sql, h);			
				
			log.info("Lista Dati da caricare in Totale caricata");
			
		}catch (Exception e) {
			throw new Exception("Errore in lettura dati da Aggregare in SIT_VIA_TOTALE "+e.getMessage());
		}
		
		return rs;
		
	}
	
	
	/**
	 * Metodo che associa le vie puntate
	 */
	protected void associaViePuntate(HashMap<String, BeanViario> viarioUnico, BeanViario vnr) throws Exception{
	
		
		float perc = 0;
		BeanViario trovato = null;		
	
		try{
					
			BeanViario trovatoInViaUnico = null;			
			for (String key : viarioUnico.keySet()){
				
				BeanViario vu = viarioUnico.get(key);
				

				float percTrovata =  associaSedimeDescrizionePuntata(vu, vnr)==true?90:0;
						
				if(percTrovata == 0){
					continue;
				}else{
					perc = percTrovata;
					trovato = vnr;
					trovatoInViaUnico = vu;						
				}						
			}
			
			if(trovato != null  && ((vnr.getIndirizzo().length() > 2 && trovatoInViaUnico.getIndirizzo().length() > 2 ) || (vnr.getIndirizzo().length() < 2 && trovatoInViaUnico.getIndirizzo().length() < 2 ))){			
				
				// associo la via
				Object params[] = new Object[7];
				 
				params[0]=vnr.getIdvia();				
				params[1]=null;							//TODO note
				params[2]=perc*(float)100.00;			//TODO rating
				if(vnr.getSedime() == null)
					params[3]="SIMILESENZASEDIMEEPUNTATO";	//TODO rel_descr
				if(vnr.getSedime() != null)
					params[3]="SIMILECONSEDIMEEPUNTATO";	//TODO rel_descr
				params[4]=vnr.getIddwh();
				params[5]=vnr.getFkentesorgente();
				params[6]=vnr.getProges();
				run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
				trovatoInViaUnico.setRating(String.valueOf(perc));
				viarioUnico.put(vnr.getIndirizzo() + SEPARATOR + vnr.getSedime(), trovatoInViaUnico);	//chiave diversa stesso dato				
				
				associazione = true;
			}
				
		}catch (Exception e) {

			throw new Exception("Errore in Associa Vie Puntate " + e.getMessage());
			
		}	
			
	}
	
	
	
	
	/**
	 * Metodo che associa le vie per stesso sedime
	 */
	protected void associaVie(HashMap<String, BeanViario> viarioUnico, BeanViario vnr) throws Exception{
			
		float perc = 0;
		BeanViario trovato = null;		
		
				
		boolean associaConSedimeConPuntato = false;	
		boolean associaSenzaSedimeConPuntato = false;	
		
		//Inizializzo il vecchio oggetto da confrontare prima del salvataggio
		GenericTuples.T2<Boolean, Float>oggettoVecchio = new GenericTuples.T2<Boolean, Float>(true, (float)0.0);;
		
		try{
			
			BeanViario trovatoInViaUnico = null;			
			for (String key : viarioUnico.keySet()){
					
				BeanViario vu = viarioUnico.get(key);				
			
				associaConSedimeConPuntato = false;
				associaSenzaSedimeConPuntato = false;
				sostituzioneEleCorr = false;
					
				// controllo soltanto se le lunghezze degli indirizzi sono entrambe > 2 o entrambe <2 ... questo perche' alcuni algoritmi scavolano confrontando PO con POMEZIA
				if (!((vnr.getIndirizzo().length() > 2 && vu.getIndirizzo().length() > 2 ) || (vnr.getIndirizzo().length() < 2 && vu.getIndirizzo().length() < 2 )))
					continue;

				// ASSOCIO VIA CON STESSO SEDIME O NULLO E INDIRIZZO CON IL PUNTATO , PER ESEMPIO g.garibaldi con giuseppe garibaldi								
				if((vu.getIndirizzo().contains(".") || vnr.getIndirizzo().contains(".")) && associaSedimeDescrizionePuntata(vu, vnr)){
					trovato = vnr;
					trovatoInViaUnico = vu;
					if(vnr.getSedime() == null){
						associaSenzaSedimeConPuntato = true;
						perc = new Float(30);
					}
					if(vnr.getSedime() != null){
						associaConSedimeConPuntato = true;
						perc = new Float(90);
					}
					break;
				}else{
					String s1 = vnr.getIndirizzoNormalizzato().trim();
					String s2 = vu.getIndirizzoNormalizzato().trim();	
					
					String sedime1 = vnr.getSedime();
					String sedime2 = vu.getSedime();
					
					if(sedime1 == null)
						sedime1 = "-";
					if(sedime2 == null)
						sedime2 = "-";
																			
					
					//TODO l'algoritmo restituisce cmq 0 in caso di non associabilità, o una percentuale in caso di associabilità																			
					GenericTuples.T2<Boolean,Float>  oggettoDaAssociare = SmithWatermanGotohNibbioWindowedAffine.getSimilarity(sedime1, sedime2, s1, s2);					
					
					if(oggettoDaAssociare.secondObj > 0.3){	
						
						confrontaNuovoConVecchio(oggettoDaAssociare,oggettoVecchio);
						
						if(sostituzioneEleCorr){
							trovato = vnr;
							trovatoInViaUnico = vu;
							associaSenzaSedimeConPuntato = false;
							associaConSedimeConPuntato = false;
							perc = perc > (oggettoDaAssociare.secondObj * 100)? perc: oggettoDaAssociare.secondObj * 100;
						}	
					}													
				}												
			}
		
			if(trovato != null  && ((vnr.getIndirizzo().length() > 2 && trovatoInViaUnico.getIndirizzo().length() > 2 ) || (vnr.getIndirizzo().length() < 2 && trovatoInViaUnico.getIndirizzo().length() < 2 )))
			{
						
				// associo la via
				Object params[] = new Object[7];
				params[0]=trovatoInViaUnico.getIdvia();				
				params[1]=null;					//TODO note	
				params[2]=perc; //TODO rating
				String relDescr = "";
				if(associaConSedimeConPuntato){
					relDescr="SIMILECONSEDIMEEPUNTATO";			//TODO rel_descr
				}else if(associaSenzaSedimeConPuntato){
					relDescr="SIMILESENZASEDIMEEPUNTATO";	
				}else if(vnr.getSedime() != null){
					relDescr="SIMILECONSEDIME";					//TODO rel_descr
				}else if(vnr.getSedime() == null){
					relDescr="SIMILESENZASEDIME";					//TODO rel_descr
				}
				params[3]=relDescr;
				params[4]=vnr.getIddwh();
				params[5]=vnr.getFkentesorgente();
				params[6]=vnr.getProges();
				run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
				trovatoInViaUnico.setRating(String.valueOf(perc));
				trovatoInViaUnico.setReldescr(relDescr);
				viarioUnico.put(vnr.getIndirizzo() + SEPARATOR + vnr.getSedime(), trovatoInViaUnico);//chiave diversa stesso dato			
					
				associazione = true;
			}

		}catch (Exception e) {
			throw new Exception("Errore in Associa Vie Stesso Sedime "+e.getMessage());
		}
		
	}
	


	
	/**
	 * Metodo che effetua il salvataggio del nuovo dato in SIT_VIA_UNICO e associa il relativo elemento di SIT_VIA_TOTALE 
	 */
	protected void inserisciInUnico(HashMap<String, BeanViario> viarioUnico, BeanViario vnr) throws Exception{	
		
		try{
			
			//prendo un id 
			Object nuovoId = ((Map) run.query(conn,getProperty("SQL_NEXT_UNICO"), new MapHandler())).get("ID");								
			
			// creo la via
			Object paramsUnico[] = new Object[3];
			paramsUnico[0]=nuovoId;
			
			if(vnr.getSedime()==null){
				paramsUnico[1]="-";
			}else{
				paramsUnico[1]=vnr.getSedime();
			}
			
			paramsUnico[2]=vnr.getIndirizzo();
			run.update(conn,getProperty("SQL_NUOVO_VIA_UNICO"), paramsUnico);			
			//AGGIORNO LA TAB SIT_VIA_TOTALE
			// associo la via ok
			Object params[] = new Object[7];
			params[0]=nuovoId;
			params[1]=null;				//TODO note
			params[2]=100;				//TODO rating
			params[3]="NATIVA";			//TODO rel_descr
			params[4]=vnr.getIddwh();
			params[5]=vnr.getFkentesorgente();
			params[6]=vnr.getProges();
			
			run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
			

		}catch (Exception e) {
			throw new Exception("Errore in salvataggio dato in SIT_VIA_UNICO "+e.getMessage());
		}
	}
		

	/**
	 * Metodo che confronta il vecchio oggetto Via con il nuovo
	 */
	protected void confrontaNuovoConVecchio(GenericTuples.T2<Boolean,Float> oggettoDaAssociare,GenericTuples.T2<Boolean,Float> oggettoVecchio) throws Exception{
		
		try{
			
			if(oggettoDaAssociare.firstObj.equals(oggettoVecchio.firstObj)){
				if(oggettoDaAssociare.secondObj>oggettoVecchio.secondObj){
					//SEDIME UGUALE TRA TOTALE e UNICO, LA NUOVA STRINGA E' PRESA COME MIGLIORE							
					sostituzioneEleCorr = true;
				}
			}else{
				float diffPerc = oggettoDaAssociare.secondObj-oggettoVecchio.secondObj;
				
				if(oggettoDaAssociare.firstObj.equals(true)){			
					if(diffPerc>-0.10){
						//SEDIME DIVERSO TRA TOTALE e UNICOSEDIME, ma uguale tra oggVecchio e oggNuovo, VIENE PRESO IL NUOVO ELEMENTO 
						sostituzioneEleCorr = true;
					}	
				}else{		
					//TODO da commentare??? 
					if(diffPerc>0.10){
						//SEDIME DIVERSO MIGLIORE, VIENE PRESO IL NUOVO ELEMENTO
						sostituzioneEleCorr = true;
					}
				}				
			}
			
		}catch (Exception e) {
			throw new Exception("Errore in confronto vecchia via con nuova "+e.getMessage());
		}	
	}
	
	
	/**
	 * Metodo che pulisce la tabella dell'unico dopo l'associazione. 
	 * Vengono eliminati i record di UNICO che non hanno un corrispondente in TOTALE e che hanno il
	 * campo VALIDATO diverso da 1 (validato = 1 sta a significare che il dato UNICO è stato inserito a mano da interfaccia)
	 */
	
	protected void pulisciUnico() throws Exception{
		
		try{			
			//Cancellazione Elemento da SIT_CIVICO_UNICO
			String sqlDeleteCivicoUnico = getProperty("SQL_PULISCI_CIVICO_UNICO");
			
			PreparedStatement ps = conn.prepareStatement(sqlDeleteCivicoUnico);			
			ps.execute();
			ps.close();
			
			conn.commit();	
			
			//Cancellazione Elemento da SIT_VIA_UNICO
			String sqlDeleteUnico = getProperty("SQL_PULISCI_VIA_UNICO");
			
			ps = conn.prepareStatement(sqlDeleteUnico);			
			ps.execute();
			ps.close();
			
		}catch (Exception e) {
			throw new Exception("Errore nella cancellazione elementi da tabella UNICO :" + e.getMessage());
		}
		
	}
	
	
	
	/**
	 * Metodo che effettua la ricerca di una dato in unico dato il valore della via originaria del viario 
	 */	
	protected String cercaViaUnico(String viaOrig) throws Exception {
	
		String codiceVia = null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_VIA_UNICO");
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, viaOrig);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				codiceVia = rs.getString("ID_VIA");
			}
			
			ps.close();

			
		}catch (Exception e) {
			throw new Exception("Errore nella ricerca della via UNICO per codice VIA originario :" + e.getMessage());
		}
		
		
		return codiceVia;
		
	}
	
	
	/**
	 * Metodo che effettua la ricerca di una via nella tabella SIT_FONTI_ASS_DIVERSE per cercare vie associate di fonti diverse
	 */
	protected String cercaViaInFontiDiverse(BeanViario vnr) throws Exception{	
		
		String codiceVia = null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_VIA_FONTI_DIVERSE");
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, vnr.getCodiceViaOrig());
			ps.setString(2, vnr.getFkentesorgente());
			ps.setString(3, vnr.getProges());			
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				codiceVia = rs.getString("ID_VIA");
			}

			ps.close();
			
		}catch (Exception e) {
			throw new Exception("Errore nella ricerca della via UNICO per codice VIA originario :" + e.getMessage());
		}
		
		
		return codiceVia;
	}
	
	/**
	 * Metodo che effettua la ricerca di una dato in unico dato l'indirizzo 
	 */	
	protected BeanViario cercaIndirizzoUnico(String indirizzo) throws Exception {
	
		BeanViario bv = null;
		
		try{
			
			String sql = getProperty("SQL_CERCA_INDIRIZZO_UNICO");
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, indirizzo);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				bv = new BeanViario();
				bv.setIdvia(rs.getString("IDVIA"));
				bv.setIndirizzo(rs.getString("INDIRIZZO"));
				bv.setSedime(rs.getString("SEDIME"));
			}
			
			ps.close();

			
		}catch (Exception e) {
			throw new Exception("Errore nella ricerca della via UNICO per INDIRIZZO :" + e.getMessage());
		}
		
		
		return bv;
		
	}
	
	@Override
	public void setConnectionForLongResultset(
			Connection connectionForLongResultset) throws AggregatoreException {
		
		this.connectionForLongResultset = connectionForLongResultset;
		
	}
	
}
