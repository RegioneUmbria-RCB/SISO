package it.webred.ct.proc.ario.gestoreVariazioni;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class GestoreVariazioniVie extends GestoreVariazioni
{
	
	private static final Logger	log	= Logger.getLogger(GestoreVariazioniVie.class.getName());

	//Costruttore
	public GestoreVariazioniVie(){
		
		super();
		
		this.tipoDatoVar="VIA";
		this.TAB_XXX_TOTALE="SIT_VIA_TOTALE";
		this.TAB_XXX_UNICO="SIT_VIA_UNICO";
		this.SQL_FONTE_RIFERIMENTO = "SQL_FONTE_PROGRESSIVO_RIF_VIE";	
		this.TIPO_DATO="VIA";
	}
	

	@Override
	protected void disaggregaDatoCollegato(String fkAggr) {
		
		PreparedStatement pst2=null;
		
		try{
			String sql2a = 
					"UPDATE SIT_CIVICO_TOTALE "+
					"SET FK_VIA=NULL, FK_CIVICO=NULL, RATING=null, REL_DESCR=null, ANOMALIA=null, NOTE=null " +
					"WHERE FK_VIA = ?  ";
			pst2 = conn.prepareStatement(sql2a);
			pst2.setString(1, fkAggr);
			
			pst2.executeUpdate();
			
		/*	sql2a = "DELETE FROM SIT_CIVICO_UNICO WHERE FK_VIA=? ";
			pst2 = conn.prepareStatement(sql2a);
			pst2.setString(1, fkAggr);
			
			pst2.executeUpdate();*/
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			try {
				DbUtils.close(pst2);
			} catch (SQLException e) {
				log.error(e.getMessage(),e);
			}
		}
		
	}
	
	@Override
	protected void deleteDatoCollegatoDaTotale(String idDwh, String ente, String progEs) {
		
		PreparedStatement ps=null;
		
		try{
			
			//Rimuovo i civici direttamente connessi alla via (nella stessa fonte) dalla tabella TOTALE
			String sql ="DELETE FROM SIT_CIVICO_TOTALE WHERE FK_ENTE_SORGENTE=? AND PROG_ES=? AND ID_ORIG_VIA_TOTALE=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, ente);
			ps.setString(2, progEs);
			ps.setString(3, idDwh);
			
			ps.executeUpdate();
			
					
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}finally{
			try {
				DbUtils.close(ps);
			} catch (SQLException e) {
				log.error(e.getMessage(),e);
			}
		}
		
	}
	
	protected void pulisciUnico() throws Exception{
		
		PreparedStatement ps = null;
		
		try{			
			//Cancellazione Elemento da SIT_CIVICO_UNICO
			String sqlDeleteCivicoUnico = getProperty("SQL_PULISCI_CIVICO_UNICO");
			
			ps = conn.prepareStatement(sqlDeleteCivicoUnico);			
			ps.execute();
			ps.close();
			
			//Cancellazione Elemento da SIT_VIA_UNICO
			String sqlDeleteUnico = getProperty("SQL_PULISCI_VIA_UNICO");
			
			ps = conn.prepareStatement(sqlDeleteUnico);			
			ps.execute();
			ps.close();
			
		}catch (Exception e) {
			throw new Exception("Errore nella cancellazione elementi da tabella UNICO :" + e.getMessage());
		}finally{
			try {
				DbUtils.close(ps);
			} catch (SQLException e) {
				log.error(e.getMessage(),e);
			}
		}
		
	}
	
}

