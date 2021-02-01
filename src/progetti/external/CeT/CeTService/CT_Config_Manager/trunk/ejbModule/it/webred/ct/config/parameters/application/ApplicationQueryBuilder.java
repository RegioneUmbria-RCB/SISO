package it.webred.ct.config.parameters.application;

import it.webred.ct.config.parameters.AbstractQueryBuilder;

public class ApplicationQueryBuilder extends AbstractQueryBuilder{
	
	private final String SQL_LISTA_APPLICAZIONI_UTENTE=
		"SELECT DISTINCT * FROM( " +
				"SELECT DISTINCT a.*, c.FK_AM_COMUNE, i.URL " + 
				    "FROM am_application a, am_instance i, am_instance_comune c, am_user_air air " + 
				    "WHERE i.fk_am_application = a.NAME  " +
				    "AND c.FK_AM_INSTANCE  = i.NAME  " +
				   // "AND a.NAME != 'AMProfiler'  " +
				    "AND AIR.FK_AM_USER = '@user' "+
				    "AND (AIR.FK_AM_COMUNE = C.FK_AM_COMUNE OR AIR.FK_AM_COMUNE IS NULL)  " +
				"UNION " +
				"SELECT DISTINCT a.*, c.FK_AM_COMUNE, i.URL  " +
				    "FROM am_application a, am_instance i, am_instance_comune c, am_user_group ugroup, am_group  gruppo " +
				    "WHERE i.fk_am_application = a.NAME  " +
				    "AND c.FK_AM_INSTANCE  = i.NAME  " +
				   // "AND a.NAME != 'AMProfiler'  " +
				    "AND ugroup.FK_AM_USER = '@user' "+
				    "AND (GRUPPO.FK_AM_COMUNE = C.FK_AM_COMUNE OR GRUPPO.FK_AM_COMUNE IS NULL) " +
				    "AND UGROUP.FK_AM_GROUP = GRUPPO.NAME " +
		  ") order by FK_AM_COMUNE, APP_CATEGORY, NAME ";
	
	private final String SQL_LISTA_APPLICATION = 
		"SELECT a.name, a.name name2, " +
		 "CASE WHEN SUM (CASE WHEN (SELECT COUNT (kv.key_conf) " +
		  "FROM am_key_value kv, am_key_value_ext kve " +
		 "WHERE kv.overw_type = 1 " +
		 "AND kve.key_conf(+) = kv.key_conf " +
		 "AND kve.fk_am_instance(+) = i.name " +
		 "AND kv.MUST_BE_SET = 'Y' " +
		 "AND kve.VALUE_CONF is null) = 0 " +
		 "THEN 0 " +
		 "ELSE 1 " +
		 "END) = 0 " +
		 "THEN 'N' " +
		 "ELSE 'S' " +
		 "END as required_found " +
		 "FROM am_instance i, am_application a " +
		 "WHERE i.FK_AM_APPLICATION (+)= a.NAME " +
		 "GROUP BY a.name " +
		 "ORDER BY a.name";
	
	private final String SQL_LISTA_INSTANCE = 
			"SELECT i.name, i.name name2, " +
			"CASE WHEN (SELECT COUNT (kv.key_conf) " +
			  "FROM am_key_value kv, am_key_value_ext kve " +
			"WHERE kv.overw_type = 1 " +
			"AND kve.key_conf(+) = kv.key_conf " + 
			"AND kve.fk_am_instance(+) = i.name " + 
			"AND kv.MUST_BE_SET = 'Y' " +
			"AND kve.VALUE_CONF is null) = 0 " + 
			"THEN 'N' " +
			"ELSE 'S' " +
			"END as required_found " + 
			"FROM am_instance i, am_instance_comune ic " + 
			"WHERE i.FK_AM_APPLICATION = '@APPLICATION' " +
	        "AND ic.FK_AM_INSTANCE = i.NAME " +
	        "AND (ic.FK_AM_COMUNE in ( " +
	            "select distinct c.belfiore from " +
	            "am_comune c, am_user_group ug, am_group g " +
	            "where g.name = ug.fk_am_group " +
	            "and c.belfiore = g.fk_am_comune " + 
	            "and ug.fk_am_user = '@USER' " +
	         ") " +
	         "OR ic.FK_AM_COMUNE in ( " +
	            "select distinct c.belfiore from " +
	            "am_comune c, am_user_air ua " +
	            "where c.belfiore = ua.fk_am_comune " +
	            "and ua.fk_am_user = '@USER' " +
	         ")" +
	         ")" +
			 "ORDER BY i.name";
	
	public ApplicationQueryBuilder() {
	}
	
	public String createQueryListaApplication() {
		
		String sql = SQL_LISTA_APPLICATION;	
		return sql;
		
	}
	
	public String createQueryListaInstanceByAppUser(String application, String username) {
		
		String sql = SQL_LISTA_INSTANCE;	
		sql = sql.replace("@APPLICATION", application);
		sql = sql.replaceAll("@USER", username);
		return sql;
		
	}

	public String getSQL_LISTA_APPLICAZIONI_UTENTE() {
		return SQL_LISTA_APPLICAZIONI_UTENTE;
	}
	
}
