package it.webred.ct.data.access.basic.cnc.statoriscossione.dao;

import java.io.Serializable;

import it.webred.ct.data.access.basic.cnc.dao.AbstractQueryBuilder;
import it.webred.ct.data.access.basic.cnc.statoriscossione.dto.StatoRiscossioneSearchCriteria;
import it.webred.ct.data.model.cnc.statoriscossione.ChiaveULStatoRiscossione;

public class StatoQueryBuilder extends AbstractQueryBuilder implements Serializable {

	private StatoRiscossioneSearchCriteria criteria;
	private ChiaveULStatoRiscossione riscossione;
	
	
	public StatoQueryBuilder(StatoRiscossioneSearchCriteria criteria) {
		this.criteria = criteria;
		this.riscossione = criteria.getChiaveRiscossione();
	}
	
	public String createQuery(boolean isCount) {
		
		String sql = "";
		
		if (isCount)
			sql = "SELECT COUNT(risc)";
		else
			sql = "SELECT risc";
		
				
		sql += " FROM SRiscossioni risc";
		
		String whereCond = this.getSQLCriteria();
		
		if (whereCond.equals(""))
			return null;
		
		if (!"".equals(whereCond)) {
			sql += " WHERE " + whereCond;
		}
		
		sql += " ORDER BY risc.chiaveRiscossione.progressivoRuolo, risc.chiaveRiscossione.annoRuolo, risc.chiaveRiscossione.progressivoArticoloRuolo";


		//logger.info("SQL ["+sql+"]");
		return sql;
	}
	
	private String getSQLCriteria() {
		String sqlCriteria = "";

		// Chiave UL Ruolo
		sqlCriteria = (riscossione.getAnnoRuolo() == null || riscossione.getAnnoRuolo().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " risc.chiaveRiscossione.annoRuolo=" + riscossione.getAnnoRuolo();
		sqlCriteria = (riscossione.getCodAmbito() == null || riscossione.getCodAmbito().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " risc.chiaveRiscossione.codAmbito=" + riscossione.getCodAmbito();
		sqlCriteria = (riscossione.getCodEnteCreditore() == null || riscossione.getCodEnteCreditore().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " risc.chiaveRiscossione.codEnteCreditore=" + riscossione.getCodEnteCreditore();
		//sqlCriteria = (riscossione.getCodEnteCreditore() == null || riscossione.getCodEnteCreditore().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " risc.chiaveRiscossione.codEnteCreditore=" + riscossione.getCodAmbito();
		sqlCriteria = (riscossione.getProgressivoRuolo() == null || riscossione.getProgressivoRuolo().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " risc.chiaveRiscossione.progressivoRuolo=" + riscossione.getProgressivoRuolo();
		sqlCriteria = (criteria.getCodiceFiscale() == null || criteria.getCodiceFiscale().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " risc.codFiscale LIKE '" + criteria.getCodiceFiscale().toUpperCase() + "'";
		
		return sqlCriteria;
	}

}
