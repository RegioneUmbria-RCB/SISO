package it.webred.amprofiler.ejb.itemrole.dto;

import java.util.List;

public class QueryBuilder {
	
	public QueryBuilder() {
	}
	
	public String createQueryPermessiByEnteItemUsername(String ente,
			String applicationItem, String username, List<String> gruppi) {
		
		String filtraGruppi = "";
		if(gruppi != null && gruppi.size() > 0){
			String nomeGruppi = "";
			filtraGruppi += "and g.name in (";
			for (String gr: gruppi)
				nomeGruppi += ",'" + gr + "' ";
			filtraGruppi += nomeGruppi.substring(1) + ")";
		}
		
		String sql = "select distinct name from ( " +
				
					"select p.name from Am_User_Air ua, Am_Permission_Air pa, Am_Permission p " +
					"where ua.fk_Am_Ai_Role = pa.fk_Am_Ai_Role " +
					"and pa.fk_Am_Permission = p.name " +
					"and UPPER(ua.fk_Am_User) = '" + username.toUpperCase() + "' " +
					"and ua.fk_Am_Comune = '" + ente + "' " +
					"and p.fk_Am_Item = '" + applicationItem + "' " +
					
					" UNION " +
					
					"select p.name from Am_User_Group ug, Am_Group_Air ga, Am_Group g, Am_Permission_Air pa, Am_Permission p " +
					"where ga.fk_Am_Ai_Role = pa.fk_Am_Ai_Role " +
					"and ug.fk_Am_Group = ga.fk_Am_Group " +
					"and pa.fk_Am_Permission = p.name " +
					"and g.name = ug.fk_Am_Group " +
					"and UPPER(ug.fk_Am_User) = '" + username.toUpperCase() + "' " +
					"and g.fk_Am_Comune = '" + ente + "' " +
					"and p.fk_Am_Item = '" + applicationItem + "' " +filtraGruppi + ") " +
					
					"order by name";
		
		System.out.println("*********************************************    " + sql);
		return sql;
	}
	
}
