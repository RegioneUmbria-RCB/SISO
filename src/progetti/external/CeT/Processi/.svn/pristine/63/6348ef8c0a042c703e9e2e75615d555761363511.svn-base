package it.webred.rulengine.brick.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

/**
 * @author MARCO
 * Evoluzione della regola AssociaUiu.
 * A differenza della prima regola fa uso di SIT_MATRICE_CIVICI_T per verificare 
 * la corrispondenza fra civici catastali e quelli del comune 
 * Inoltre non inserisce Civici nuovi rispetto a quelli già presenti in SITICIVI
 * e caricati con altri strumenti.
 *  
 *  */
public class ValSitiCiviUiu extends it.webred.rulengine.DbCommand implements Rule {

	public ValSitiCiviUiu(BeanCommand bc) {
		super(bc);
		// TODO Auto-generated constructor stub
	}
	private static final org.apache.log4j.Logger log = Logger.getLogger(ValSitiCiviUiu.class.getName());

	
	@Override
	public CommandAck runWithConnection(Context ctx, Connection conn)
			throws CommandException {

	
		List<RAbNormal> abnormals = new ArrayList<RAbNormal>();
		String codEnte = (String)ctx.getDeclarativeType("RULENGINE.CODENTE").getValue();

		try {
			ResultSet rsCat =null;
	        Connection connSiti = RulesConnection.getConnection("SITI_"+ ctx.getBelfiore());
			Connection connDwh = RulesConnection.getConnection("DWH_"+ ctx.getBelfiore());
			String sqlCatastoIndirizzi = getProperty("sql.CATASTO_INDIRIZZI");
			String sqlFIND_CIVICI_DA_CAT_A_SITICIVI = getProperty("sql.FIND_CIV_DA_CAT_A_SITICIVI");
			String sqlINSERT_SITICIVI_UIU = getProperty("sql.INSERT_SITICIVI_UIU");
			String sqlDELETE_SITICIVI_UIU = getProperty("sql.DELETE_SITICIVI_UIU");
		
		try {
			// cancello siticiVI_uiu  
			QueryRunner delSITICIVI_UIU = new QueryRunner();
			delSITICIVI_UIU.update(conn,sqlDELETE_SITICIVI_UIU);

		} catch (Exception e) {
			log.error("Problemi durante la cancellazione di SITICIVI_UIU / SITICIVI er utente RulEngine");
		}
		
        try {
        	
        	String codNAzionale = (String)ctx.getDeclarativeType("RULENGINE.CODENTE").getValue();
			PreparedStatement psCat = connSiti.prepareStatement(sqlCatastoIndirizzi);
			psCat.setString(1, codNAzionale );
			rsCat = psCat.executeQuery();
			while (rsCat.next()) {

				// reperisco codice DEL CIVICO SU SITICIVI
				String pkCivico = null;
				try {
					ResultSetHandler h = new org.apache.commons.dbutils.handlers.MapHandler();
					QueryRunner run = new QueryRunner();
					Object[] params = new Object[] {rsCat.getString("ID_DWH")};
					Map result = (Map) run.query(connDwh, sqlFIND_CIVICI_DA_CAT_A_SITICIVI,params, h);
					if (result!=null && !result.isEmpty()) {
						// pk civico in siticivi
						pkCivico = (String) result.get("ID_DWH");
					}
				} catch (Exception e) {
					log.error("Errore trascodifica civico",e);
					ErrorAck ack = new ErrorAck(e);
					return ack;
				} 
				
					if (pkCivico==null)
						continue;


				
				
				// inserimento siticivi_uiu
				try {
					QueryRunner ins = new QueryRunner();
					// SPLIT id_dwh per prendere ID_IMM
					String idDwh = (String)rsCat.getString("ID_DWH");
					String[] split = idDwh.split("\\|"); // id_imm
					Object[] paramsIns = new Object[] {pkCivico, split[0]};
					ins.update(conn,sqlINSERT_SITICIVI_UIU,paramsIns);
					
				} catch (Exception e) {
					log.error("Errore inserimento SITICIVI_UIU",e);
					ErrorAck ack = new ErrorAck(e);
					return ack;
				}
				
				
			}
        } catch (Exception e) {
			log.error("Errore Associazione UIU a SITICIVI ",e);
			ErrorAck ack = new ErrorAck(e);
			return ack;

		} finally {
			try {
	        	DbUtils.close(rsCat);
	        	DbUtils.close(connSiti);
	        	DbUtils.close(connDwh);
			} catch (SQLException e) {
			}
		}
		
		
		
		ApplicationAck ack = new ApplicationAck(abnormals, "Valorizzazione SITICIVI_UIU eseguita con successo !");
		return ack;
	}catch (Exception e)
	{
		log.error("Errore nell'esecuzione di ValSitiCiviUiu",e);
		ErrorAck ea = new ErrorAck(e);
		return ea;
	} finally {
		
	}
	}
	
	private String getProperty(String propName) {
		
		String p =  DwhUtils.getProperty(this.getClass(), propName);
		return p;
	}
}
