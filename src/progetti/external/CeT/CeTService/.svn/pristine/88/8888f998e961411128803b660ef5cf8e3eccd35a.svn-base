package it.webred.ct.config.parameters.comune;

import it.webred.ct.config.parameters.AbstractQueryBuilder;

public class ComuneQueryBuilder extends AbstractQueryBuilder{
	
	private final String SQL_LISTA_COMUNI_BY_USER = 
			"SELECT distinct c.belfiore, c.descrizione, " +
	        "CASE WHEN (SELECT COUNT (kv.key_conf) " +
	          "FROM am_key_value kv, am_key_value_ext kve " +
	         "WHERE kv.overw_type = 2 " +
	         "AND kve.key_conf(+) = kv.key_conf " +
	         "AND kve.fk_am_comune(+) = c.belfiore " +
	         "AND kv.MUST_BE_SET = 'Y' " +
	         "AND kve.VALUE_CONF is null) = 0 " +
	         "THEN 'N'  " +
	         "ELSE 'S' " +
	         "END as required_found " +
	         "FROM am_comune c " +
	         	"where (c.belfiore in ( " +
	 	            "select distinct c1.belfiore from " +
	 	            "am_comune c1, am_user_group ug, am_group g " +
	 	            "where g.name = ug.fk_am_group " +
	 	            "and c1.belfiore = g.fk_am_comune " + 
	 	            "and ug.fk_am_user = '@USER' " +
	 	         ") " +
	 	         "OR c.belfiore in ( " +
	 	            "select distinct c2.belfiore from " +
	 	            "am_comune c2, am_user_air ua " +
	 	            "where c2.belfiore = ua.fk_am_comune " +
	 	            "and ua.fk_am_user = '@USER' " +
	 	         ")" +
	 	         ")" +
	         "ORDER BY c.descrizione";
	
	private final String SQL_LISTA_INSTANCE_COMUNI = 
		"SELECT ic.fk_am_instance i1, ic.fk_am_instance i2, " +
		 "CASE WHEN (SELECT COUNT (kv.key_conf) " +
		  "FROM am_key_value kv, am_key_value_ext kve " +
		 "WHERE kv.overw_type = 3 " +
		 "AND kve.key_conf(+) = kv.key_conf " +
		 "AND kve.fk_am_comune(+) = ic.fk_am_comune " +
		 "AND kve.fk_am_instance(+) = ic.fk_am_instance " +
		 "AND kv.MUST_BE_SET = 'Y' " +
		 "AND kve.VALUE_CONF is null) = 0 " +
		 "THEN 'N' " +
		 "ELSE 'S' " +
		 "END as required_found " +
		 "FROM am_instance_comune ic " +
		 "WHERE ic.FK_AM_COMUNE = '@COMUNE' " +
		 "ORDER BY ic.fk_am_instance";
	
	private final String SQL_LISTA_FONTE_COMUNI = 
		"SELECT fc.FK_AM_FONTE, f.DESCRIZIONE, " +
		 "CASE WHEN (SELECT COUNT (kv.key_conf) " +
		  "FROM am_key_value kv, am_key_value_ext kve " +
		 "WHERE kv.overw_type = 4 " +
		 "AND kve.key_conf(+) = kv.key_conf " +
		 "AND kve.fk_am_comune(+) = fc.fk_am_comune " +
		 "AND kve.fk_am_fonte(+) = fc.fk_am_fonte " +
		 "AND kv.MUST_BE_SET = 'Y' " +
		 "AND kve.VALUE_CONF is null) = 0 " +
		 "THEN 'N' " +
		 "ELSE 'S' " +
		 "END as required_found " +
		 "FROM am_fonte_comune fc, am_fonte f " +
		 "WHERE fc.FK_AM_FONTE = f.ID " +
		 "AND fc.FK_AM_COMUNE = '@COMUNE' " +
		 "ORDER BY f.DESCRIZIONE";
	
	public ComuneQueryBuilder() {
	}
	
	public String createQueryListaComuneByUsername(String username) {
		
		String sql = SQL_LISTA_COMUNI_BY_USER.replaceAll("@USER", username);	
		return sql;
		
	}
	
	public String createQueryListaInstanceComune(String comune) {
		
		String sql = SQL_LISTA_INSTANCE_COMUNI;	
		sql = sql.replace("@COMUNE", comune);
		return sql;
		
	}
	
	public String createQueryListaFonteComune(String comune) {
		
		String sql = SQL_LISTA_FONTE_COMUNI;	
		sql = sql.replace("@COMUNE", comune);
		return sql;
		
	}
	
}
