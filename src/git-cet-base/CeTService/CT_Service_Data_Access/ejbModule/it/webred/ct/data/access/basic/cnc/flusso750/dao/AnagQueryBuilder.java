package it.webred.ct.data.access.basic.cnc.flusso750.dao;


import it.webred.ct.data.access.basic.cnc.dao.AbstractQueryBuilder;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.Flusso750SearchCriteria;
import it.webred.ct.data.model.cnc.flusso750.ChiaveULPartita;
import it.webred.ct.data.model.cnc.flusso750.ChiaveULRuolo;



public class AnagQueryBuilder extends AbstractQueryBuilder {

	private Flusso750SearchCriteria criteria;
	private ChiaveULRuolo ruolo;
	private ChiaveULPartita partita;
	
	
	public AnagQueryBuilder(Flusso750SearchCriteria criteria) {
		this.criteria = criteria;
		ruolo = criteria.getChiaveRuolo();
		partita = criteria.getChiavePartita();
	}
	
	public String createQuery(boolean isCount) {
		
		String sql = "";
		
		String tblName = "";
		
		if (criteria.getTipoInt() != null) {
			if (criteria.getTipoInt().equals("D"))
				tblName = "VAnagrafeDebitore";
			else
				tblName = "VAnagrafeCointestatario";
		}
		else return null;
		
		if (isCount)
			sql = "SELECT COUNT(anag)";
		else
			sql = "SELECT anag";
		
				
		sql += " FROM " + tblName + " anag";
		
		if (criteria.getCodiceTributo() != null && !criteria.getCodiceTributo().equals("")) {
			sql += " , RArticolo datiCont";
		}
		
		String whereCond = this.getSQLCriteria();
		
		if (whereCond.equals(""))
			return null;
		
		if (!"".equals(whereCond)) {
			sql += " WHERE " + whereCond;
		}
		
		// Add join conditions if necessary
		if (criteria.getCodiceTributo() != null && !criteria.getCodiceTributo().equals("")) {
			if (!"".equals(whereCond))
				sql += " AND";
			
			sql += " anag.chiaveULRuolo.annoRuolo = datiCont.chiaveULRuolo.annoRuolo " + 
			       " AND anag.chiaveULRuolo.codAmbito = datiCont.chiaveULRuolo.codAmbito" +
			       " AND anag.chiaveULRuolo.codEnteCreditore = datiCont.chiaveULRuolo.codEnteCreditore" +
			       " AND anag.chiaveULRuolo.progressivoRuolo = datiCont.chiaveULRuolo.progressivoRuolo" +
			       " AND anag.chiaveULRuolo.annoRiferimento = datiCont.chiaveULRuolo.annoRiferimento" +
			       " AND anag.chiaveULRuolo.codicePartita = datiCont.chiaveULRuolo.codicePartita" +
			       " AND anag.chiaveULRuolo.codTipoUfficio = datiCont.chiaveULRuolo.codTipoUfficio" +
			       " AND anag.chiaveULRuolo.codUfficio = datiCont.chiaveULRuolo.codUfficio" ;
		}
		
		sql += " ORDER BY anag.cognome, anag.denominazione, anag.nome";
			
		


		//logger.info("SQL ["+sql+"]");
		return sql;
	}
	
	private String getSQLCriteria() {
		String sqlCriteria = "";

		// Chiave UL Ruolo
		sqlCriteria = (ruolo.getAnnoRuolo() == null || ruolo.getAnnoRuolo().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " anag.chiavePartita.annoRuolo=" + ruolo.getAnnoRuolo();
		sqlCriteria = (ruolo.getCodAmbito() == null || ruolo.getCodAmbito().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " anag.chiavePartita.codAmbito=" + ruolo.getCodAmbito();
		sqlCriteria = (ruolo.getCodEnteCreditore() == null || ruolo.getCodEnteCreditore().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " anag.chiavePartita.codEnteCreditore=" + ruolo.getCodEnteCreditore();
		sqlCriteria = (ruolo.getProgressivoRuolo() == null || ruolo.getProgressivoRuolo().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " anag.chiavePartita.progressivoRuolo=" + ruolo.getProgressivoRuolo();
		
		// Chiave UL Partita
		sqlCriteria = (partita.getAnnoRiferimento() == null || partita.getAnnoRiferimento().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " anag.chiavePartita.annoRiferimento=" + partita.getAnnoRiferimento();
		sqlCriteria = (partita.getCodicePartita() == null || partita.getCodicePartita().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.chiavePartita.codicePartita) LIKE '" + partita.getCodicePartita().toUpperCase() + "'";
		sqlCriteria = (partita.getCodTipoUfficio() == null || partita.getCodTipoUfficio().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " anag.chiavePartita.codTipoUfficio=" + partita.getCodTipoUfficio();
		sqlCriteria = (partita.getCodUfficio() == null || partita.getCodUfficio().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " anag.chiavePartita.codUfficio=" + partita.getCodUfficio();
		
		sqlCriteria = (criteria.getCodiceTributo() == null || criteria.getCodiceTributo().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " datiCont.codEntrata=" + criteria.getCodiceTributo();
		sqlCriteria = (criteria.getCodiceFiscale() == null || criteria.getCodiceFiscale().equals("")) ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.codFiscale) LIKE '" + criteria.getCodiceFiscale().toUpperCase() + "'";
		
		
		return sqlCriteria;
	}
	

	
}