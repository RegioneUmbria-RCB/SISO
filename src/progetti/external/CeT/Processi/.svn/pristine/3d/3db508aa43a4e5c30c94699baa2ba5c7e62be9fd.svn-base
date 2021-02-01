package it.webred.ct.proc.ario.gestoreVariazioni;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class GestoreVariazioniOggetti extends GestoreVariazioni
{
	
	private static final Logger	log	= Logger.getLogger(GestoreVariazioniOggetti.class.getName());

	//Costruttore
	public GestoreVariazioniOggetti(){
		
		super();
		
		this.tipoDatoVar="OGG";
		this.TAB_XXX_TOTALE="SIT_OGGETTO_TOTALE";
		this.TAB_XXX_UNICO="SIT_OGGETTO_UNICO";
		this.SQL_FONTE_RIFERIMENTO = "SQL_FONTE_PROGRESSIVO_RIF_OGGETTI";	
		this.TIPO_DATO="OGGETTO";
	}
	

	@Override
	protected void disaggregaDatoCollegato(String fkAggr) {}
	
	@Override
	protected void deleteDatoCollegatoDaTotale(String idDwh, String ente,String progEs) {}

	/**
	 * Metodo che pulisce la tabella dell'unico dopo l'associazione. 
	 * Vengono eliminati i record di UNICO che non hanno un corrispondente in TOTALE e che hanno il
	 * campo VALIDATO diverso da 1 (validato = 1 sta a significare che il dato UNICO è stato inserito a mano da interfaccia)
	 */
	protected void pulisciUnico() throws Exception{
		PreparedStatement ps=null;
		try{			
			
			String sqlDeleteUnico = getProperty("SQL_PULISCI_OGGETTI_UNICO");
			
			ps = conn.prepareStatement(sqlDeleteUnico);			
			ps.execute();
			ps.close();
			
		}catch (Exception e) {
			throw new Exception("Errore nella cancellazione elementi da tabella OGGETTI UNICO :" + e.getMessage());
		}finally{
			try {
				DbUtils.close(ps);
			} catch (SQLException e) {
				log.error(e.getMessage(),e);
			}
		}
		
	}

	
	
}

