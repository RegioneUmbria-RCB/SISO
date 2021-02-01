package it.webred.ct.proc.ario.fonti.civico.successioni;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.proc.ario.GestioneStringheVie;
import it.webred.ct.proc.ario.bean.BeanParser;
import it.webred.ct.proc.ario.bean.Civico;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.fonti.civico.Civici;
import it.webred.ct.proc.ario.fonti.civico.locazioni.CivicoLocazioniA;
import it.webred.ct.proc.ario.normalizzazione.NormalizzaTotali;

public class CivicoSuccessioniOggetto extends DatoDwh implements Civici{

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(CivicoSuccessioniOggetto.class.getName());
	
	
	public CivicoSuccessioniOggetto(){
		
		//Caricamento del file di properties dei caricatori		
		this.props = new Properties();
		try {
			InputStream is = this.getClass().getResourceAsStream("/sql/caricatori.sql");
		    this.props.load(is);                     
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage());			   
		}																
	}
	
	

	@Override
	public boolean dwhIsDrop(Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	//Metodo che indica se la tabella è gestita o no tramite PROCESSID
	public boolean existProcessId(){
		return false;
	}


	//Metodo che restituisce la query di cancellazione tabella Totale
	public String getDeleteSQL(){
		
		String sqlDeleteTot = this.getProperty("SQL_DEL_CIVICO_TOTALE");		
		return sqlDeleteTot;
	}

	@Override
	public int getFkEnteSorgente() {
		
		return 6;
	}

	//Metodo che restituisce la query di inserimento in tabella Totale
	public String getInsertSQL(){
		
		String sqlInsertTot = this.getProperty("SQL_INS_CIVICO_TOTALE");		
		return sqlInsertTot;
	}

	@Override
	public int getProgEs() {
		
		return 3;
	}

	@Override
	public String getQuerySQLDeleteProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLgetProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLNewProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLSaveProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuerySQLUpdateProcessId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	//Metodo che restituisce la query di ricerca in tabella Totale
	public String getSearchSQL(){
		
		String sqlSearchTot = this.getProperty("SQL_CERCA_CIV_TOTALE");		
		return sqlSearchTot;
	}

	@Override
	//Metodo che restituisce le query per il caricamento da DHW
	public String getSql(String processID) {

		String sqlCivicoLocazioniA = this.getProperty("SQL_CIV_SUCCESSIONI_OGGETTO");
						
		return sqlCivicoLocazioniA;
				
	}

	@Override
	//Metodo che restituisce la tabella del DWH
	public String getTable() {
		
		//Tabella del DHW da cui leggere i dati
		String tabella = "SUCCESSIONI_C";		
		return tabella;
	}

	//Metodo che restituisce la query di update in tabella Totale
	public String getUpdateSQL(){
		
		String sqlUpdateTot = this.getProperty("SQL_UPDATE_CIV_TOTALE");		
		return sqlUpdateTot;
	}

	@Override
	//Metodo che mappa normalizza e salva i dati
	public void prepareSaveDato(DatoDwh classeFonte, Connection connForInsert, String insCivicoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitCivicoTotale sct = new SitCivicoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			
			
			String idDwh = rs.getString("ID_DWH");
			
			String strInd = "AAAA ";
			String civico =	(rs.getString("CIV_LIV1")!=null?rs.getString("CIV_LIV1"):"-");																							
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
			
			String indirCiv = "";
			if(civico==null || civico.equals("")){
				indirCiv = indirizzo;
			}else{
				indirCiv=strInd+civico;
			}
			
			
			String idStorico = rs.getString("ID_STORICO");
			Date dataFineVal = rs.getDate("DT_FINE_VAL");
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirCiv);
			Iterator< Civico> iter = colCiv.iterator();
			
			while(iter.hasNext()){

				Civico civ =iter.next();
				
				sct.setIdStorico(idStorico);
				sct.setDataFineVal(dataFineVal);
				sct.setIdOrigViaTotale(idDwh);
				sct.setCivLiv1(civ.getCivLiv1());
				sct.setCivicoComp( BeanParser.getCivicoComposto(civ));
				sct.setNote(civ.getNote());				
				sct.setAnomalia(civ.getAnomalia());				
				
				sct.setField1(rs.getString("FIELD1"));
				sct.setField2(rs.getString("FIELD2"));
				sct.setField3(rs.getString("FIELD3"));
				sct.setField4(rs.getString("FIELD4"));
				sct.setField5(rs.getString("FIELD5"));
				sct.setField6(rs.getString("FIELD6"));
				sct.setField7(rs.getString("FIELD7"));
				sct.setField8(rs.getString("FIELD8"));
				sct.setField9(rs.getString("FIELD9"));
				sct.setField10(rs.getString("FIELD10"));
				sct.setField11(rs.getString("FIELD11"));
				
				//Normalizzazione
				nt.normalizzaCivico(sct);
				
				//Setto indice Civico
				iPk.setIdDwh(rs.getString("ID_DWH"));
				iPk.setFkEnteSorgente(this.getFkEnteSorgente());
				iPk.setProgEs(this.getProgEs());				
				//Calcolo Hash di chiave
				iPk.setCtrHash(setCtrHashSitCivicoTotale(sct));
				
				sct.setId(iPk);

							
				//Salvataggio
				super.saveSitCivicoTotale(classeFonte, connForInsert, insCivicoTotale, sct);
	
			}		
					
		}catch (Exception e) {
			log.warn("Errore:Save Civico Successioni Oggetto="+e.getMessage());
			Exception ea = new Exception("Errore:Save Civico Successioni Oggetto:"+e.getMessage());
			throw ea;
		}
		
	}

	@Override 
	//Metodo che mappa normalizza e aggiorna i dati
	public void prepareUpdateDato(DatoDwh classeFonte, Connection connForInsert, String insCivicoTotale, String updateCivicoTotale, String searchCivicoTotale, ResultSet rs, String codEnte, HashParametriConfBean paramConfBean) throws Exception {

		SitCivicoTotale sct = new SitCivicoTotale();
		NormalizzaTotali nt = new NormalizzaTotali();
		IndicePK iPk = new IndicePK();
		
		try{			
			
			String idDwh = rs.getString("ID_DWH");
			
			String strInd = "AAAA ";
			String civico =	(rs.getString("CIV_LIV1")!=null?rs.getString("CIV_LIV1"):"-");																										
			String indirizzo = (rs.getString("INDIRIZZO")!=null?rs.getString("INDIRIZZO"):"-");
			
			String indirCiv = "";
			if(civico==null || civico.equals("")){
				indirCiv = indirizzo;
			}else{
				indirCiv=strInd+civico;
			}
			
			String idStorico = rs.getString("ID_STORICO");
			Date dataFineVal = rs.getDate("DT_FINE_VAL");
			Collection<Civico> colCiv = GestioneStringheVie.restituisciCivico(indirCiv);
			Iterator< Civico> iter = colCiv.iterator();
			
			
			//Mappo i campi chiave (usati anche per la ricerca dei dati da aggiornare)
			iPk.setIdDwh(rs.getString("ID_DWH"));
			iPk.setFkEnteSorgente(this.getFkEnteSorgente());
			iPk.setProgEs(this.getProgEs());

			//Setto la chiave
			sct.setId(iPk);

			
			//Cancello tutti i civici di chiave corrispondente che non sono in colCiv
			String sqlDeleteColCiv = this.getProperty("SQL_DELETE_COLL_CIV");			
			deleteCollezioneCivici(sct, sqlDeleteColCiv, connForInsert, colCiv);

			
			while(iter.hasNext()){

				Civico civ =iter.next();
			
				sct.setIdStorico(idStorico);
				sct.setDataFineVal(dataFineVal);
				sct.setCivLiv1(civ.getCivLiv1());
				sct.setCivicoComp( BeanParser.getCivicoComposto(civ));
				sct.setNote(civ.getNote());
				sct.setFkCivico(null);
				sct.setRelDescr(null);
				sct.setRating(null);							
				sct.setAnomalia(civ.getAnomalia());
				sct.setIdOrigViaTotale(idDwh);
						
				sct.setField1(rs.getString("FIELD1"));
				sct.setField2(rs.getString("FIELD2"));
				sct.setField3(rs.getString("FIELD3"));
				sct.setField4(rs.getString("FIELD4"));
				sct.setField5(rs.getString("FIELD5"));
				sct.setField6(rs.getString("FIELD6"));
				sct.setField7(rs.getString("FIELD7"));
				sct.setField8(rs.getString("FIELD8"));
				sct.setField9(rs.getString("FIELD9"));
				sct.setField10(rs.getString("FIELD10"));
				sct.setField11(rs.getString("FIELD11"));
				
				//Normalizzazione Dati
				nt.normalizzaCivico(sct);

				//Calcolo hash per la ricerca
				iPk.setCtrHash(setCtrHashSitCivicoTotale(sct));
							
				//Setto la chiave
				sct.setId(iPk);
				
				
				String azione = super.trovaDatoTotaleCtrHash(classeFonte, connForInsert,searchCivicoTotale,iPk);
				
				if(azione.equals("INSERISCI")){
					//Inserisco il nuovo civivo
					super.saveSitCivicoTotale(classeFonte, connForInsert, insCivicoTotale, sct);
				}		
							
			}		
					
		}catch (Exception e) {
			log.warn("Errore:Update Civico Successioni Oggetto="+e.getMessage());
			Exception ea = new Exception("Errore:Update Civico Successioni Oggetto:"+e.getMessage());
			throw ea;
		}
		
	}

	//Metodo che indica se la query di lettura da DWH ha come parametro il CodiceEnte
	public boolean queryWithParamCodEnte(){
		return true;
	}

	//Metodo che restituisce la query di caricamento letta da file di property
	private String getProperty(String propName) {

		String p = this.props.getProperty(propName+ "." + this.getFkEnteSorgente());
		
		if (p==null)
			p = this.props.getProperty(propName);
			
		return p;
	}
	
	@Override
	//Metodo che stabilisce se viene o no fornito il codice originario del civico
	public boolean codiceCivico(String codEnte, HashParametriConfBean paramConfBean) throws Exception{
			
		//La fonte non gestisce il civico di origine
		return false;
	}

}
