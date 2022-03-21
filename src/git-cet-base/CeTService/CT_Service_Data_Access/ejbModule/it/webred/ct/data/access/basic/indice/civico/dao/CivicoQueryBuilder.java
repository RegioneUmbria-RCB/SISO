package it.webred.ct.data.access.basic.indice.civico.dao;

public class CivicoQueryBuilder{
	
	
	private final String SQL_LISTA_CIVICI_BY_VIA_UNICO = 
		"SELECT DISTINCT s, t.id.ctrHash, t.civLiv1, t.id.progEs " +
		"FROM SitCivicoTotale t, SitEnteSorgente s "+
		"WHERE t.id.fkEnteSorgente = s.id " +
		"AND t.fkVia = @IDUNICO " +
		"ORDER BY t.civLiv1";
	
	private final String COUNT_LISTA_CIVICI_BY_VIA_UNICO = 
		"SELECT COUNT (DISTINCT t.id.ctrHash) " +
		"FROM SitCivicoTotale t "+
		"WHERE t.fkVia = @IDUNICO";
	
	public CivicoQueryBuilder() {
	}
	
	public String createQueryCiviciByViaUnico(String unicoId, boolean count) {
		
		String sql = "";
		
		if(count)
			sql = COUNT_LISTA_CIVICI_BY_VIA_UNICO;
		else sql = SQL_LISTA_CIVICI_BY_VIA_UNICO;
		
		sql = sql.replace("@IDUNICO", unicoId);
			
		return sql;
		
	}
}
