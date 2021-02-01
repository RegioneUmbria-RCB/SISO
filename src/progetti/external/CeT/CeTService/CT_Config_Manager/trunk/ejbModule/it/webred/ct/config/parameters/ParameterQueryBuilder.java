package it.webred.ct.config.parameters;

import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

public class ParameterQueryBuilder extends AbstractQueryBuilder{
	
	private ParameterSearchCriteria criteria;

	private String type;
	private String comune;
	private String instance;
	private String fonte;
	private String section;
	private String application;
	private String applicationKv;
	private String key;

	private final String SQL_LISTA_KEY_VALUE = "select kv.KEY_CONF, kv.MUST_BE_SET, kv.SECTION_NAME, "
			+ "kve.VALUE_CONF, kve.EXT_TYPE, kve.FK_AM_COMUNE, kve.FK_AM_INSTANCE, "
			+ "kve.FK_AM_FONTE, kv.FK_AM_APPLICATION, kv.DESCRIZIONE_KEY, kve.ID, kv.VALUE_CONF AS default_value "
			+ "from am_key_value kv, am_key_value_ext kve "
			+ "where kve.KEY_CONF (+)= kv.KEY_CONF ";
	
	private final String SQL_LISTA_KEY_VALUE_EXT = "select kve from AmKeyValueExt kve where kve.id is not null ";

	private final String SQL_GET_SECTION = "select section "
			+ "from AmSection section " + "where section.name = '@NAME' ";

	public ParameterQueryBuilder() {
	}
	
	public ParameterQueryBuilder(ParameterSearchCriteria criteria) {
		this.criteria = criteria;

		type = criteria.getType();
		comune = criteria.getComune();
		instance = criteria.getInstance();
		fonte = criteria.getFonte();
		section = criteria.getSection();
		application = criteria.getApplication();
		applicationKv = criteria.getApplicationKv();
		key = criteria.getKey();

	}

	public String createQueryAmKeyValue() {

		String sql = SQL_LISTA_KEY_VALUE;
		String whereCond = getSQLCriteria();
		
		if (!"".equals(whereCond)) {
			sql += whereCond;
		}
		
		sql += " order by kv.SECTION_NAME, kv.KEY_CONF";

		return sql;

	}
	
	public String createQueryAmKeyValueExt() {

		String sql = SQL_LISTA_KEY_VALUE_EXT;
		String whereCond = getSQLCriteriaAlt();
		
		if (!"".equals(whereCond)) {
			sql += whereCond;
		}
		
		sql += " order by kve.keyConf";

		return sql;

	}

	public String createQueryAmSection(String name, String application) {

		String sql = SQL_GET_SECTION;
		sql = sql.replace("@NAME", name);
		if (application != null)
			sql += " and section.amApplication.name = '" + application + "'";
		else
			sql += " and section.amApplication is null";

		return sql;

	}

	private String getSQLCriteria() {
		String sqlCriteria = "";

		sqlCriteria = (type == null || type.trim().equals("") ? sqlCriteria
				: addOperator(sqlCriteria) + " kv.OVERW_TYPE = '" + type + "'");
		
		sqlCriteria = (key == null || key.trim().equals("") ? sqlCriteria
				: addOperator(sqlCriteria) + " kv.key_conf = '" + key + "'");
		
		sqlCriteria = (comune == null || comune.trim().equals("") ? sqlCriteria
				: addOperator(sqlCriteria) + " kve.fk_am_comune (+)= '" + comune + "'");
		
		sqlCriteria = (instance == null || instance.trim().equals("") ? sqlCriteria
				: addOperator(sqlCriteria) + " kve.fk_am_instance (+)= '" + instance + "'");
		
		sqlCriteria = (fonte == null || fonte.trim().equals("") ? sqlCriteria
				: addOperator(sqlCriteria) + " kve.fk_am_fonte (+)= '" + fonte + "'");
		
		sqlCriteria = (section == null || section.trim().equals("") ? sqlCriteria
				: addOperator(sqlCriteria) + " kve.section_name (+)= '" + section + "'");
		
		sqlCriteria = (application == null || application.trim().equals("") ? sqlCriteria
				: addOperator(sqlCriteria) + " kve.fk_am_application (+)= '" + application + "'");
		
		sqlCriteria = (applicationKv == null || applicationKv.trim().equals("") ? sqlCriteria
				: addOperator(sqlCriteria) + " kv.fk_am_application = '" + applicationKv + "'");
		
		sqlCriteria = ((applicationKv == null || applicationKv.trim().equals("")) && "0".equals(type) ?
				addOperator(sqlCriteria) + " kv.fk_am_application is null": sqlCriteria);

		return sqlCriteria;
	}
	
	private String getSQLCriteriaAlt() {
		String sqlCriteria = "";

		sqlCriteria = (key == null || key.trim().equals("") ? addOperator(sqlCriteria) + " kve.keyConf is null "
				: addOperator(sqlCriteria) + " kve.keyConf = '" + key + "'");
		
		sqlCriteria = (comune == null || comune.trim().equals("") ? addOperator(sqlCriteria) + " kve.amComune is null "
				: addOperator(sqlCriteria) + " kve.amComune.belfiore = '" + comune + "'");
		
		sqlCriteria = (instance == null || instance.trim().equals("") ? addOperator(sqlCriteria) + " kve.amInstance is null "
				: addOperator(sqlCriteria) + " kve.amInstance.name = '" + instance + "'");
		
		sqlCriteria = (fonte == null || fonte.trim().equals("") ? addOperator(sqlCriteria) + " kve.fkAmFonte is null "
				: addOperator(sqlCriteria) + " kve.fkAmFonte = " + fonte );

		return sqlCriteria;
	}
}
