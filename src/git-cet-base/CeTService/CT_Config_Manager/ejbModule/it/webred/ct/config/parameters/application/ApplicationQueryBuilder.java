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
				    "AND AIR.FK_AM_USER = :username "+
				    "AND (AIR.FK_AM_COMUNE = C.FK_AM_COMUNE OR AIR.FK_AM_COMUNE IS NULL)  " +
				"UNION " +
				"SELECT DISTINCT a.*, c.FK_AM_COMUNE, i.URL  " +
				    "FROM am_application a, am_instance i, am_instance_comune c, am_user_group ugroup, am_group  gruppo " +
				    "WHERE i.fk_am_application = a.NAME  " +
				    "AND c.FK_AM_INSTANCE  = i.NAME  " +
				   // "AND a.NAME != 'AMProfiler'  " +
				    "AND ugroup.FK_AM_USER = :username "+
				    "AND (GRUPPO.FK_AM_COMUNE = C.FK_AM_COMUNE OR GRUPPO.FK_AM_COMUNE IS NULL) " +
				    "AND UGROUP.FK_AM_GROUP = GRUPPO.NAME " +
		  ") order by FK_AM_COMUNE, APP_CATEGORY, NAME ";
	
	private final String SQL_LISTA_APPLICAZIONI_UTENTE_MENU = "SELECT DISTINCT * FROM( " +
			"SELECT DISTINCT a.*, ac.descrizione, c.FK_AM_COMUNE, i.URL, i.URI, I.PORT_REWRITE, I.CONCAT_URI " + 
			    "FROM am_application a, am_instance i, am_instance_comune c, am_user_air air, am_comune ac " + 
			    "WHERE i.fk_am_application = a.NAME  " +
			    "AND c.FK_AM_INSTANCE  = i.NAME  " +
			    "AND a.NAME != 'AMProfiler'  " +
			    "AND AIR.FK_AM_USER = :username "+
			    "AND (AIR.FK_AM_COMUNE = C.FK_AM_COMUNE OR AIR.FK_AM_COMUNE IS NULL)  " +
			    "and ac.belfiore = C.FK_AM_COMUNE  " +
			"UNION " +
			"SELECT DISTINCT a.*, ac.descrizione, c.FK_AM_COMUNE, i.URL, i.URI, I.PORT_REWRITE, I.CONCAT_URI  " +
			    "FROM am_application a, am_instance i, am_instance_comune c, am_user_group ugroup, am_group  gruppo, am_comune ac " +
			    "WHERE i.fk_am_application = a.NAME  " +
			    "AND c.FK_AM_INSTANCE  = i.NAME  " +
			    "AND a.NAME != 'AMProfiler'  " +
			    "AND ugroup.FK_AM_USER = :username "+
			    "AND (GRUPPO.FK_AM_COMUNE = C.FK_AM_COMUNE OR GRUPPO.FK_AM_COMUNE IS NULL) " +
			    "AND UGROUP.FK_AM_GROUP = GRUPPO.NAME " +
			    "and ac.belfiore = C.FK_AM_COMUNE " +
			") order by descrizione, FK_AM_COMUNE, APP_CATEGORY, NAME ";
	
	
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
			"WHERE i.FK_AM_APPLICATION = :application " +
	        "AND ic.FK_AM_INSTANCE = i.NAME " +
	        "AND (ic.FK_AM_COMUNE in ( " +
	            "select distinct c.belfiore from " +
	            "am_comune c, am_user_group ug, am_group g " +
	            "where g.name = ug.fk_am_group " +
	            "and c.belfiore = g.fk_am_comune " + 
	            "and ug.fk_am_user = :username " +
	         ") " +
	         "OR ic.FK_AM_COMUNE in ( " +
	            "select distinct c.belfiore from " +
	            "am_comune c, am_user_air ua " +
	            "where c.belfiore = ua.fk_am_comune " +
	            "and ua.fk_am_user = :username " +
	         ")" +
	         ")" +
			 "ORDER BY i.name";
	
	public ApplicationQueryBuilder() {
	}
	
	public String createQueryListaApplication() {
		
		String sql = SQL_LISTA_APPLICATION;	
		return sql;
		
	}
	
	public String createQueryListaInstanceByAppUser() {
		
		return SQL_LISTA_INSTANCE;			
	}

	public String getSQL_LISTA_APPLICAZIONI_UTENTE() {
		return SQL_LISTA_APPLICAZIONI_UTENTE;
	}
	
	public String createQueryIstanzeApplicazioneMenu(){
		return this.SQL_LISTA_APPLICAZIONI_UTENTE_MENU;
		
	}
	
}
