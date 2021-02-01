package it.webred.ct.proc.ario.aggregatori;

import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
//import it.webred.ct.proc.ario.bean.SitViaTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.utils.SmithWatermanGotohNibbioWindowedAffine;
import it.webred.utils.GenericTuples;

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
	
	public void aggrega(String codEnte, HashParametriConfBean paramConfBean) throws AggregatoreException {
				
		
		try{			
			
			
			//Inserisco i dati di default
			log.info("INIZIO Aggregazione dati di rifererimento");
			
			ArrayList<Object[]> paramFonteProgr = (ArrayList<Object[]>) run.query(conn,getProperty("SQL_FONTE_PROGRESSIVO_RIF_VIE"), new ArrayListHandler());
			run.update(conn,getProperty("SQL_INSERISCI_VIA_RIFERIMENTO"),paramFonteProgr.get(0));
			run.update(conn,getProperty("SQL_AGGIORNA_DATI_DEFAULT_VIA_TOT"),paramFonteProgr.get(0));							
			
			conn.commit();
			
			log.info("FINE aggregazione dati di riferimento");
			
			
			
			//Effettuo caricamento viario Unico
			HashMap<String, BeanViario> viarioUnico = this.caricaViarioUnico();			
						
			//Effettuo il caricamento della lista dei TOTALI da aggregare			
			List<BeanViario> lVieTotDaAggr = this.caricaDatiDaAggregare();
			
			
			log.info("Inizio Elaborazione controllo associabilità Vie in UNICO");						
			//this.elabora(conn,viarioUnico,lVieTotDaAggr);
			this.elabora(viarioUnico,lVieTotDaAggr);
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
				if(vu.getSedime()==null || (vu.getSedime()!=null && !vu.getSedime().equals(vnr.getSedime()))){
					return false;
				}		
				
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
	private void elabora(HashMap<String, BeanViario> viarioUnico, List<BeanViario> lVieTotDaAggr) throws Exception {			
		try{
			
			for (BeanViario vnr : lVieTotDaAggr){				
				
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
					//Controlla Criterio A2 (Ugualianza descrizione)					
					BeanViario viaUgualeNelUnico = viarioUnico.get(vnr.getIndirizzoNormalizzato() + SEPARATOR + vnr.getSedime());							
					if (viaUgualeNelUnico != null)
					{
						// associo la via 
						Object params[] = new Object[7];
						params[0]=viaUgualeNelUnico.getIdvia();					
						params[1]=null;//TODO note???
						params[2]=100;
						params[3]="UGUALI";
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
								//Cotrollo Criterio A6/A7 (Similitudine descrizione con uguale o differnte prefisso)
								this.associaVie(viarioUnico,vnr);
											
								if(!associazione){
									//Effettuo l'inserimento in UNICO del nuovo elemento
									this.inserisciInUnico(viarioUnico,vnr);
												
									//Devo rileggere gli elementi presenti in unico				
									viarioUnico = this.caricaViarioUnico();													
								}
							}													
						}
					}
				}
				
				//Effettuo il commit delle operazioni su DB
				conn.commit();
				
			}	
			
		}catch(Exception e){					
			conn.rollback();
			throw new Exception("Errore in operazione aggregazione fonte riferimento VIE: "+e.getMessage());
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
	 * Metodo che legge i dati da aggregare dalla tabella SIT_VIA_TOTALE
	 */
	protected List<BeanViario> caricaDatiDaAggregare() throws Exception{					
		
		List<BeanViario> lvieAltre = null;		
		
		try {
			
			BeanListHandler h = new BeanListHandler(BeanViario.class);			
			
			String sql = getProperty("SQL_LEGGI_VIA_TOTALE");
				
			lvieAltre = (List<BeanViario>) run.query(conn, sql, h);			
				
			log.info("Lista Dati da caricare in Totale caricata");
			
		}catch (Exception e) {
			throw new Exception("Errore in lettura dati da Aggregare in SIT_VIA_TOTALE "+e.getMessage());
		}
		
		return lvieAltre;
		
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
		
				
		boolean associaConPuntato = false;		
		
		//Inizializzo il vecchio oggetto da confrontare prima del salvataggio
		GenericTuples.T2<Boolean, Float>oggettoVecchio = new GenericTuples.T2<Boolean, Float>(true, (float)0.0);;
		
		try{
			
			BeanViario trovatoInViaUnico = null;			
			for (String key : viarioUnico.keySet()){
					
				BeanViario vu = viarioUnico.get(key);				
			
				associaConPuntato = false;
				sostituzioneEleCorr = false;
					
				// controllo soltanto se le lunghezze degli indirizzi sono entrambe > 2 o entrambe <2 ... questo perche' alcuni algoritmi scavolano confrontando PO con POMEZIA
				if (!((vnr.getIndirizzo().length() > 2 && vu.getIndirizzo().length() > 2 ) || (vnr.getIndirizzo().length() < 2 && vu.getIndirizzo().length() < 2 )))
					continue;

				// ASSOCIO VIA CON STESSO SEDIME E INDIRIZZO CON IL PUNTATO , PER ESEMPIO g.garibaldi con giuseppe garibaldi						
				float percTrovata =  associaSedimeDescrizionePuntata(vu, vnr)==true?(float)0.90:0;
							
				if(percTrovata > 0){
					perc = percTrovata;
					trovato = vnr;
					trovatoInViaUnico = vu;
					associaConPuntato = true;
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
							associaConPuntato = false;
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
				params[2]=perc;	//TODO rating
				if(associaConPuntato){
					params[3]="SIMILECONSEDIMEEPUNTATO";			//TODO rel_descr
				}else if(oggettoVecchio.firstObj.booleanValue()){
					params[3]="SIMILECONSEDIME";					//TODO rel_descr
				}else if(!oggettoVecchio.firstObj.booleanValue()){
					params[3]="SIMILESENZASEDIME";					//TODO rel_descr
				}
				params[4]=vnr.getIddwh();
				params[5]=vnr.getFkentesorgente();
				params[6]=vnr.getProges();
				run.update(conn,getProperty("SQL_AGGIORNA_VIA_TOTALE"), params);
				trovatoInViaUnico.setRating(String.valueOf(perc));
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
					oggettoVecchio = oggettoDaAssociare;
					sostituzioneEleCorr = true;
				}
			}else{
				float diffPerc = oggettoDaAssociare.secondObj-oggettoVecchio.secondObj;
				
				if(oggettoDaAssociare.firstObj.equals(true)){			
					if(diffPerc>-0.10){
						//SEDIME DIVERSO TRA TOTALE e UNICOSEDIME, ma uguale tra oggVecchio e oggNuovo, VIENE PRESO IL NUOVO ELEMENTO 
						oggettoVecchio = oggettoDaAssociare;
						sostituzioneEleCorr = true;
					}	
				}else{		
					//TODO da commentare??? 
					if(diffPerc>0.10){
						//SEDIME DIVERSO MIGLIORE, VIENE PRESO IL NUOVO ELEMENTO
						oggettoVecchio = oggettoDaAssociare;
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
			
			String sqlDeleteUnico = getProperty("SQL_PULISCI_VIA_UNICO");
			
			PreparedStatement ps = conn.prepareStatement(sqlDeleteUnico);			
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
	
}
