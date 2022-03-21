package it.webred.ct.data.access.basic.cnc.flusso750.dao;

import java.io.Serializable;

import it.webred.ct.data.access.basic.cnc.dao.AbstractQueryBuilder;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.Flusso750SearchCriteria;
import it.webred.ct.data.model.cnc.flusso750.ChiaveULRuolo;

public class RuoloQueryBuilder extends AbstractQueryBuilder implements Serializable {

	private Flusso750SearchCriteria criteria;
	private ChiaveULRuolo ruolo;
	
	
	public RuoloQueryBuilder(Flusso750SearchCriteria criteria) {
		this.criteria = criteria;
		this.ruolo = criteria.getChiaveRuolo();
	}
	
	public String createQuery(boolean isCount) {
		
		String sql = "";
		
		if (isCount)
			sql = "SELECT COUNT(infoRuolo)";
		else
			sql = "SELECT NEW it.webred.ct.data.access.basic.cnc.flusso750.dto.InfoRuoloDTO(infoRuolo, null)";
		
				
		sql += " FROM VInizioRuolo infoRuolo";
		
		String whereCond = this.getSQLCriteria();
		
		if (!"".equals(whereCond)) {
			sql += " WHERE " + whereCond;
		}
		
		sql += " ORDER BY infoRuolo.chiaveRuolo.annoRuolo, infoRuolo.chiaveRuolo.progressivoRuolo";


		//logger.info("SQL ["+sql+"]");
		return sql;
	}
	
	private String getSQLCriteria() {
		String sqlCriteria = "";

		// Chiave UL Ruolo
		sqlCriteria = (ruolo.getAnnoRuolo() == null || ruolo.getAnnoRuolo().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " infoRuolo.chiaveRuolo.annoRuolo=" + ruolo.getAnnoRuolo();
		sqlCriteria = (ruolo.getCodAmbito() == null || ruolo.getCodAmbito().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " infoRuolo.chiaveRuolo.codAmbito=" + ruolo.getCodAmbito();
		sqlCriteria = (ruolo.getCodEnteCreditore() == null || ruolo.getCodEnteCreditore().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " infoRuolo.chiaveRuolo.codEnteCreditore=" + ruolo.getCodEnteCreditore();
		sqlCriteria = (ruolo.getProgressivoRuolo() == null || ruolo.getProgressivoRuolo().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " infoRuolo.chiaveRuolo.progressivoRuolo=" + ruolo.getProgressivoRuolo();
				
		
		return sqlCriteria;
	}

}
