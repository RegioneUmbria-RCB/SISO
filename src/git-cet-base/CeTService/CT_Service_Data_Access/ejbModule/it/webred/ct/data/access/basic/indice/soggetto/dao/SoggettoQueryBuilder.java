package it.webred.ct.data.access.basic.indice.soggetto.dao;

import it.webred.ct.data.access.basic.indice.AbstractQueryBuilder;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;

public class SoggettoQueryBuilder extends AbstractQueryBuilder {

	private IndiceSearchCriteria criteria;

	private String denominazione;
	private String codiceFiscale;
	private String partitaIva;
	private String unicoId;
	private String enteSorgenteId;
	private String progressivoES;

	private final String SQL_LISTA_ENTE_SORGENTE_BY_UNICO = "SELECT DISTINCT o, j.id.progEs FROM SitEnteSorgente o, SitSoggettoTotale j "
			+ "WHERE j.fkSoggetto = "
			+ "@IDUNICO "
			+ "AND o.id = j.id.fkEnteSorgente "
			+ "ORDER BY o.descrizione, j.id.progEs";

	public SoggettoQueryBuilder() {
	}

	public SoggettoQueryBuilder(IndiceSearchCriteria criteria) {
		this.criteria = criteria;

		denominazione = criteria.getDenominazione();
		codiceFiscale = criteria.getCodiceFiscale();
		partitaIva = criteria.getPartitaIva();
		unicoId = criteria.getUnicoId();
		enteSorgenteId = criteria.getEnteSorgenteId();
		progressivoES = criteria.getProgressivoES();

	}

	public String createQueryUnico(boolean isCount) {

		String sql = "";
		enteSorgenteId = "";
		progressivoES = "";

		if (isCount)
			sql = "SELECT COUNT(o)";
		else
			sql = "SELECT o";

		sql += " FROM SitSoggettoUnico o";

		String whereCond = getSQLCriteria();

		if (whereCond.equals(""))
			return null;

		if (!"".equals(whereCond)) {
			sql += " WHERE " + whereCond;
		}

		sql += " ORDER BY o.denominazione, o.cognome, o.nome";

		return sql;

	}

	public String createQueryTotale(boolean isCount, boolean addUnico) {

		String sql = "";

		if (isCount)
			sql = "SELECT COUNT(DISTINCT o.id.ctrHash)";
		else
			sql = "SELECT DISTINCT o.id.ctrHash, o.denominazione, o.cognome, o.nome, o.codfisc, o.pi, o.dtNascita, o.descComuneNascita, o.validato, o.stato, o.id.fkEnteSorgente";

		if (addUnico && !isCount)
			sql += " , u";

		sql += " FROM SitSoggettoTotale o";

		if (addUnico)
			sql += ", SitSoggettoUnico u";

		String whereCond = getSQLCriteria();

		if (whereCond.equals(""))
			return null;

		if (!"".equals(whereCond)) {
			sql += " WHERE " + whereCond;

			if (addUnico)
				sql += " AND o.fkSoggetto = u.idSoggetto";
		}

		sql += " ORDER BY o.denominazione, o.cognome, o.nome";

		return sql;

	}

	public String createQueryEntiByUnico(String unicoId) {

		String sql = SQL_LISTA_ENTE_SORGENTE_BY_UNICO;
		sql = sql.replace("@IDUNICO", unicoId);

		return sql;

	}

	private String getSQLCriteria() {
		String sqlCriteria = "";
		
		if(denominazione != null  && !denominazione.trim().equals("")){
			int indexNome = denominazione.indexOf(" ");
			String nome = indexNome != -1? denominazione.substring(++indexNome): "%";
			String cognome = indexNome != -1? denominazione.substring(0, --indexNome): denominazione;
			sqlCriteria = addOperator(sqlCriteria) + " (UPPER(o.denominazione) LIKE '" + denominazione.toUpperCase() + "%' OR (UPPER(o.cognome) LIKE '" + cognome.toUpperCase() + "%' AND UPPER(o.nome) LIKE '" + nome.toUpperCase() + "%'))";
		}
		
		sqlCriteria = (codiceFiscale == null  || codiceFiscale.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(o.codfisc) LIKE '" + codiceFiscale.toUpperCase() + "%'");

		sqlCriteria = (partitaIva == null  || partitaIva.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(o.pi) LIKE '" + partitaIva.toUpperCase() + "%'");
		
		sqlCriteria = (unicoId == null  || unicoId.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " o.fkSoggetto = " + unicoId);

		sqlCriteria = (enteSorgenteId == null  || enteSorgenteId.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " o.id.fkEnteSorgente = " + enteSorgenteId);

		sqlCriteria = (progressivoES == null  || progressivoES.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " o.id.progEs = " + progressivoES);
		
		return sqlCriteria;
	}
}
