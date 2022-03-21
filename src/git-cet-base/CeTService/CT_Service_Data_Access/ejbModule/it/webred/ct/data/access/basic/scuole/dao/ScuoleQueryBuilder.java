package it.webred.ct.data.access.basic.scuole.dao;

public class ScuoleQueryBuilder{
	
	public ScuoleQueryBuilder() {
	}
	
	public String createQueryIstitutiByTipoIscrizione(String tipoiscrizione) {
		
		String tipoFlag = "";
		if(tipoiscrizione.equals("prePost0"))
			tipoFlag = "flagSnPre";
		if(tipoiscrizione.equals("prePost1"))
			tipoFlag = "flagSnPost";
		if(tipoiscrizione.equals("prePost2"))
			tipoFlag = "flagSnPrepost";
		
		String sql = "select i " +
				"from SitSclIstituti i, CfgSclPrepostIstituti pp " +
				"where i.codIstituto = pp.id.idSitSclIstituti " +
				"and pp."+ tipoFlag +" = 'S'";
			
		return sql;
		
	}
	
	public String createQueryTipiIstitutiByTipoIscrizione(String tipoiscrizione) {
		
		String tipoFlag = "";
		if(tipoiscrizione.equals("prePost0"))
			tipoFlag = "flagSnPre";
		if(tipoiscrizione.equals("prePost1"))
			tipoFlag = "flagSnPost";
		if(tipoiscrizione.equals("prePost2"))
			tipoFlag = "flagSnPrepost";
		
		String sql = "select distinct i.tipoIstituto " +
				"from SitSclIstituti i, CfgSclPrepostIstituti pp " +
				"where i.codIstituto = pp.id.idSitSclIstituti " +
				"and pp."+ tipoFlag +" = 'S'";
			
		return sql;
		
	}
	
	public String createQueryTipiIstituti() {
		
		String sql = "select distinct i.tipoIstituto " +
				"from SitSclIstituti i order by i.tipoIstituto";
			
		return sql;
		
	}
}
