package it.webred.rulengine.brick.loadDwh.load.concessioni.filters;

import it.webred.rulengine.exception.RulEngineException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;

public abstract class StandardConcessioniFilter {

	private static final org.apache.log4j.Logger log = Logger.getLogger(StandardConcessioniFilter.class.getName());

	public abstract void filterSpecEnte(Connection conn ) throws RulEngineException ;
		
	/**
	 * Mette dei progressivi nel caso che non ci siano le chiavi dei tipiRecord
	 * @param conn
	 * @throws RulEngineException
	 */
	public  void filter(Connection conn ) throws RulEngineException {
		
		filterSpecEnte(conn);
		Map result = null;
		BigDecimal maxA ;
		BigDecimal maxB ;
		BigDecimal maxC ;
		BigDecimal maxD ;
		BigDecimal maxE ;
		try {
			ResultSetHandler h = new MapHandler();
			QueryRunner qry = new QueryRunner();
			String sql = "SELECT COUNT(*)+1 MAX FROM RE_CONCESSIONI_A";
			result = (Map) qry.query(conn, sql, h);
			maxA = (BigDecimal) result.get("MAX");
		} catch (SQLException e) {
			throw new RulEngineException("Errore DURANTE FILTRO");
		}
		try {
			ResultSetHandler h = new MapHandler();
			QueryRunner qry = new QueryRunner();
			String sql = "SELECT COUNT(*)+1 MAX FROM RE_CONCESSIONI_B";
			result = (Map) qry.query(conn, sql, h);
			maxB = (BigDecimal) result.get("MAX");
		} catch (SQLException e) {
			throw new RulEngineException("Errore DURANTE FILTRO");
		}
		try {
			ResultSetHandler h = new MapHandler();
			QueryRunner qry = new QueryRunner();
			String sql = "SELECT COUNT(*)+1 MAX FROM RE_CONCESSIONI_C";
			result = (Map) qry.query(conn, sql, h);
			maxC = (BigDecimal) result.get("MAX");
		} catch (SQLException e) {
			throw new RulEngineException("Errore DURANTE FILTRO");
		}
		try {
			ResultSetHandler h = new MapHandler();
			QueryRunner qry = new QueryRunner();
			String sql = "SELECT COUNT(*)+1 MAX FROM RE_CONCESSIONI_D";
			result = (Map) qry.query(conn, sql, h);
			maxD = (BigDecimal) result.get("MAX");
		} catch (SQLException e) {
			throw new RulEngineException("Errore DURANTE FILTRO");
		}
		try {
			ResultSetHandler h = new MapHandler();
			QueryRunner qry = new QueryRunner();
			String sql = "SELECT COUNT(*)+1 MAX FROM RE_CONCESSIONI_E";
			result = (Map) qry.query(conn, sql, h);
			maxE = (BigDecimal) result.get("MAX");
		} catch (SQLException e) {
			throw new RulEngineException("Errore DURANTE FILTRO");
		}
		
		updateChiaveNull(maxA, "RE_CONCESSIONI_A", conn);
		updateChiaveNull(maxB, "RE_CONCESSIONI_B", conn);
		updateChiaveNull(maxC, "RE_CONCESSIONI_C", conn);
		updateChiaveNull(maxD, "RE_CONCESSIONI_D", conn);
		updateChiaveNull(maxD, "RE_CONCESSIONI_E", conn);
		
	}
	
	private void updateChiaveNull(BigDecimal count, String tabella,Connection conn) throws RulEngineException {
		PreparedStatement ps=null;

		try {
			double max = count.doubleValue();
			String sql = "SELECT CHIAVE, PROCESSID  FROM " + tabella + " WHERE CHIAVE IS NULL AND RE_FLAG_ELABORATO ='0' FOR UPDATE OF CHIAVE";
			ps = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String processId = rs.getString("PROCESSID");
				processId+="@" + max;
				rs.updateString(1, processId);
				rs.updateRow();
				max+=1;
			}
			DbUtils.close(rs);
			
		} catch (SQLException e) {
			throw new RulEngineException("Errore DURANTE FILTRO");
		} finally {
			try {
				DbUtils.close(ps);
			} catch (SQLException e) {
				log.error(e);
			}
		}
	}

	
}
