package it.webred.ct.proc.ario.gestoreVariazioni;

import it.webred.rulengine.exception.RulEngineException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.log4j.Logger;

public abstract class GestoreVariazioni 
{
	
	private static final Logger	log	= Logger.getLogger(GestoreVariazioni.class.getName());
	
	protected Connection conn = null;
	protected Connection connectionForLongResultset;
	protected static Properties prop = null;
	
	protected static ArrayList<Object[]> paramFonteRif;
	QueryRunner run = new QueryRunner();
	
	protected String tipoDatoVar;
	protected String TAB_XXX_TOTALE;
	protected String TAB_XXX_UNICO;
	protected String SQL_FONTE_RIFERIMENTO;
	protected String TIPO_DATO;

	
	
	
	private final String SQL_UPD_SIT_CORR_VAR=
			"UPDATE SIT_CORRELAZIONE_VARIAZIONI SET FLG_ELABORATO='1', DT_ELAB_CORRELAZIONE=SYSDATE " +
			"WHERE FK_ENTE_SORGENTE=? AND PROG_ES=? AND ID_DWH=? AND TIPO_DATO=?";
	
	//Costruttore
	public GestoreVariazioni(){
		
	}
	
	
	protected void deleteRecordDaUnico(String id) throws SQLException{
		
		PreparedStatement pst2 = null;
		
		try{
		
		String sql2a = "DELETE FROM "+this.TAB_XXX_UNICO+" WHERE ID_"+this.TIPO_DATO+" = ?  ";
		pst2 = conn.prepareStatement(sql2a);
		pst2.setString(1, id);
		
		pst2.executeUpdate();
		
		}catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DbUtils.close(pst2);
		}
		
		
	}
	
	protected void disaggregaTotale(String fkAggr) throws SQLException{
		PreparedStatement pst2 = null;
		
		String fkTab = "FK_"+this.TIPO_DATO;
		
		try{
		
		String sql2a = 
				"UPDATE "+this.TAB_XXX_TOTALE+" "+
				"SET "+fkTab+"=NULL, RATING=null, REL_DESCR=null, ANOMALIA=null, NOTE=null " +
				"WHERE "+fkTab+"= ?  ";
		pst2 = conn.prepareStatement(sql2a);
		pst2.setString(1, fkAggr);
		
		pst2.executeUpdate();
		
		}catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DbUtils.close(pst2);
		}
		
	}
	
	protected String[] getFkAggregazione(String idDwh, String ente, String progEs) throws SQLException{
		
		String fkTab = "FK_"+this.TIPO_DATO;
		
		PreparedStatement pst2 = null;
		ResultSet rs2 = null;
		String[] res = {null,null};
		
		try{
		
		String sql2a = "SELECT DISTINCT "+fkTab+" , REL_DESCR FROM "+this.TAB_XXX_TOTALE+" WHERE ID_DWH = ? AND FK_ENTE_SORGENTE=? AND PROG_ES=? ";
		pst2 = conn.prepareStatement(sql2a);
		pst2.setString(1, idDwh);
		pst2.setString(2, ente);
		pst2.setString(3, progEs);
		
		 rs2 = pst2.executeQuery();
		if(rs2.next()){
			res[0] = rs2.getString(fkTab);
			res[1] = rs2.getString("REL_DESCR");
			
		}
		
		}catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DbUtils.close(rs2);
			DbUtils.close(pst2);
		}
		
		return res;
		
	}
	
	protected abstract void pulisciUnico() throws Exception;
	
	protected BigDecimal[] getFonteRiferimento() throws SQLException, Exception{
		BigDecimal enteRif=null;
		BigDecimal progRif=null;
		
		paramFonteRif = (ArrayList<Object[]>) run.query(conn,getProperty(this.SQL_FONTE_RIFERIMENTO), new ArrayListHandler());			
		
		
		if (paramFonteRif == null)
			log.info("!!!!! ERRORE: FONTE DI RIFERIMENTO NON TROVATA !!!!! continuo...");
		else{
			 enteRif = (BigDecimal)paramFonteRif.get(0)[0];
			 progRif = (BigDecimal)paramFonteRif.get(0)[1];				
		}
		
		BigDecimal[] rif = {enteRif,progRif};
		
		return rif;
	}
	
	public void elabora(String codEnte) throws RulEngineException{						
		
		PreparedStatement pst = null;PreparedStatement pst2 = null;
		ResultSet rs = null;ResultSet rs2 = null;
		
		BigDecimal enteRif=null;
		BigDecimal progRif=null;
		
		String SQL_SIT_CORR_VAR = 
				"SELECT DISTINCT "+"FK_"+this.TIPO_DATO+" , REL_DESCR, var.FK_ENTE_SORGENTE, var.PROG_ES, var.ID_DWH  " +
				"FROM  SIT_CORRELAZIONE_VARIAZIONI var left join "+this.TAB_XXX_TOTALE+" tot " +
				"ON (var.ID_DWH = tot.id_dwh AND var.FK_ENTE_SORGENTE=tot.FK_ENTE_SORGENTE AND var.PROG_ES=tot.PROG_ES) " +
				"WHERE var.FLG_ELABORATO='0' AND var.TIPO_DATO=? order by REL_DESCR ";
		
		try{
		
			BigDecimal[] rif = this.getFonteRiferimento();
			
			enteRif=rif[0];
			progRif=rif[1];
		
			pst = conn.prepareStatement(SQL_SIT_CORR_VAR);
			System.out.println(SQL_SIT_CORR_VAR);
			pst.setString(1, this.tipoDatoVar);
			
			rs = pst.executeQuery();
			boolean elaborato = false;
			while(rs.next()){
				
				try{
				
					String ente = rs.getString("FK_ENTE_SORGENTE");
					String progEs = rs.getString("PROG_ES");
					String idDwh = rs.getString("ID_DWH");
					
					//Recupero il codice FK_XXX da SIT_XXX_TOTALE
				/*	String[] res = getFkAggregazione(idDwh,ente,progEs);
					String fkAggr = res[0];*/
					
					String fkAggr = rs.getString("FK_"+this.TIPO_DATO);
					
					
					if(fkAggr!=null){
						
						String relDescr = rs.getString("REL_DESCR"); 
						
					/*	boolean datoFdR = enteRif!=null && progRif!=null && enteRif.toString().equals(ente) && progRif.toString().equals(progEs);
						boolean enteSenzaFdR = enteRif==null || progRif==null;
						boolean disaggrega = datoFdR || enteSenzaFdR; 
						disaggrega = (!disaggrega) ? this.isDatoNonCollegatoAFonteRif(idDwh,  ente,  progEs, enteRif.toString(), progRif.toString()) : disaggrega;*/
						
						boolean disaggrega = relDescr!=null && relDescr.equals("NATIVA");
						
						if(disaggrega){
							
							this.disaggregaDatoCollegato(fkAggr); //ex. i civici, nel caso delle vie
							
							//Resetto i campi di correlazione di tutti i record con fk_XXX = a quello trovato: il record è infatti registrato in SIT_XX_UNICO
							this.disaggregaTotale(fkAggr);
						}
					}
						
					this.deleteDatoCollegatoDaTotale(idDwh, ente, progEs);
					
					//In ogni caso, elimino il record dalla tabella SIT_XXX_TOTALE
					deleteRecordDaTotale(this.TAB_XXX_TOTALE, idDwh,ente,progEs);
						
					this.updateFlagElaborato(idDwh, ente, progEs);
						
					elaborato = true;
					
					conn.commit();
				
				}catch(Exception e){
					conn.rollback();
				}
				
			}
			
			//Elimino il record dalla tabella SIT_XXX_UNICO, che non sono più presente in SIT_XXX_TOTALE
			if(elaborato)
				this.pulisciUnico();
			
		}
		catch (Exception e)
		{
			log.error("Decorrelazione ERRORE "+e.getMessage(), e);
			RulEngineException ea = new RulEngineException(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e);
			}
			throw ea;
		}
		finally
		{try {
				DbUtils.close(rs2);
				DbUtils.close(pst2);
				DbUtils.close(rs);
				DbUtils.close(pst);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void elaboraNonNativi(String codEnte) throws RulEngineException{		
		
		PreparedStatement pst = null;PreparedStatement pst2 = null;
		ResultSet rs = null;ResultSet rs2 = null;
		
		BigDecimal enteRif=null;
		BigDecimal progRif=null;
		
		
		String SQL_CREATE = 
				"CREATE TABLE TEMP_"+this.tipoDatoVar+"_TO_DELETE AS " +
				"SELECT DISTINCT "+"FK_"+this.TIPO_DATO+" , REL_DESCR, var.FK_ENTE_SORGENTE, var.PROG_ES, var.ID_DWH  " +
				"FROM "+this.TAB_XXX_TOTALE+" tot, SIT_CORRELAZIONE_VARIAZIONI var " +
				"WHERE var.ID_DWH = tot.id_dwh AND var.FK_ENTE_SORGENTE=tot.FK_ENTE_SORGENTE AND var.PROG_ES=tot.PROG_ES " +
				"AND var.FLG_ELABORATO='0' AND var.TIPO_DATO=? AND tot.REL_DESCR<>'NATIVA' ";
		
		String SQL_DELETE_TOTALE = 
				"delete from "+this.TAB_XXX_TOTALE+" where " +
				"(id_dwh, fk_ente_sorgente,prog_es) in (select id_dwh, fk_ente_sorgente, prog_es from TEMP_"+this.tipoDatoVar+"_TO_DELETE)";
		
		String SQL_UPDATE_VAR =
				"update SIT_CORRELAZIONE_VARIAZIONI set flg_elaborato='1' where " +
				"(id_dwh, fk_ente_sorgente,prog_es) in (select id_dwh, fk_ente_sorgente, prog_es from TEMP_"+this.tipoDatoVar+"_TO_DELETE)";

		String SQL_DROP_TMP = "drop table TEMP_"+this.tipoDatoVar+"_TO_DELETE";
	
		
		
	}
	
	protected boolean isDatoNonCollegatoAFonteRif(String idDwh, String ente, String prog, String enteRif, String progRif) throws SQLException{
		
		String sql = "select distinct 1 disaggrega_unico from "+this.TAB_XXX_TOTALE+" t1 where not exists " +
				"(select 1 from "+this.TAB_XXX_TOTALE+" t2 where t2.FK_"+this.TIPO_DATO+"=T1.FK_"+this.TIPO_DATO+" and fk_ente_sorgente=? and prog_es=?) " +
				"and t1.id_dwh = ? and fk_ente_sorgente=? and prog_es=? ";
		
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		
		boolean disaggrega = false;
		
		try{
		
			pst2 = conn.prepareStatement(sql);
			pst2.setString(1, enteRif);
			pst2.setString(2, progRif);
			pst2.setString(3, idDwh);
			pst2.setString(4, ente);
			pst2.setString(5, prog);
			
			rs = pst2.executeQuery();
			if(rs.next())
				disaggrega = true;
		
		}catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DbUtils.close(rs);
			DbUtils.close(pst2);
			
		}
				
		return disaggrega;
	}
	
	//Serve per disaggregare i civici collegati alla via 
	protected abstract void disaggregaDatoCollegato(String fkAggr);
	
	protected abstract void deleteDatoCollegatoDaTotale(String idDwh,String ente,String progEs);
	
	private void updateFlagElaborato(String idDwh, String ente, String progEs) throws SQLException{
		PreparedStatement pst2 = null;
		
		try{
		
			pst2 = conn.prepareStatement(this.SQL_UPD_SIT_CORR_VAR);
			pst2.setString(1, ente);
			pst2.setString(2, progEs);
			pst2.setString(3, idDwh);
			pst2.setString(4, this.tipoDatoVar);
			
			pst2.executeUpdate();
		
		}catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DbUtils.close(pst2);
		}
	}
	

	private void deleteRecordDaTotale(String tabella, String idDwh, String ente, String progEs) throws SQLException{
		
		PreparedStatement pst2 = null;
		
		try{
		
		String sql2a = "DELETE FROM "+tabella+" WHERE ID_DWH = ? AND FK_ENTE_SORGENTE=? AND PROG_ES=? ";
		pst2 = conn.prepareStatement(sql2a);
		pst2.setString(1, idDwh);
		pst2.setString(2, ente);
		pst2.setString(3, progEs);
		
		pst2.executeUpdate();
		
		}catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			DbUtils.close(pst2);
		}
			
	}
	
	
	public void setConnection(Connection conn)
	{
		this.conn = conn;
	}

	public Connection getConnectionForLongResultset() {
		return connectionForLongResultset;
	}
	
	public void setConnectionForLongResultset(Connection connectionForLongResultset) {
		
		this.connectionForLongResultset = connectionForLongResultset;
		
	}
	
	//Metodo per il recupero del file aggregatori.properties contenente le query
	public String getProperty(String propName) throws Exception {
		
		if (prop!=null)
			return prop.getProperty(propName);	
		else {
			//Caricamento del file di properties degli aggregatori		
			prop = new Properties();
	        InputStream is = null;
	        try{
		        try {
		            is = this.getClass().getResourceAsStream("/sql/aggregatori.sql");
		            prop.load(is);                     
		        }catch(Exception e) {
		            log.error("Eccezione caricamento file property ", e);
		            return null;
		        }
		        
		        String p = prop.getProperty(propName);
				return p;
		        
	        }finally {
				is.close();
			}  			
		}
	}
	
}

