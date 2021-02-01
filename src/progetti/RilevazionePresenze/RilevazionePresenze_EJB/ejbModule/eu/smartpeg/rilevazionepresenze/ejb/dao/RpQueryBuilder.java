package eu.smartpeg.rilevazionepresenze.ejb.dao;

import java.text.SimpleDateFormat;

import eu.smartpeg.rilevazionepresenze.data.dto.RpSearchCriteria;


public class RpQueryBuilder {
	
	private RpSearchCriteria criteria;
	private SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	
	public RpQueryBuilder() {
	}
	
	public RpQueryBuilder(RpSearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	public String createQueryListaSoggetti() {
		String sql = "SELECT a FROM Anagrafica a where 1=1 " ;
				
		
		sql+= criteria.getCognome()!=null && criteria.getCognome().length()>=2 ? 	"and upper(a.cognome) like '%"+criteria.getCognome().trim().replace("'","''").toUpperCase()+"%' " : ""; 
		sql+= criteria.getNome()!=null && !criteria.getNome().isEmpty() ? 			"and upper(a.nome) like '%"+criteria.getNome().trim().replace("'","''").toUpperCase()+"%' " : ""; 
		sql+= criteria.getCf()!=null && !criteria.getCf().isEmpty() ?				"and upper(a.cf) ='"+criteria.getCf().toUpperCase()+"'":"";
		sql+= criteria.getSesso()!=null && !criteria.getSesso().isEmpty() ? 		"and upper(a.genere) ='"+criteria.getSesso().toUpperCase()+"'":"";
		sql+= criteria.getDataNascitaDa()!=null ? 		"and a.dataNascita >= TO_DATE('"+ddMMyyyy.format(criteria.getDataNascitaDa())+"','dd/MM/yyyy')":"";		
		sql+= criteria.getDataNascitaA()!=null ? 		"and a.dataNascita <= TO_DATE('"+ddMMyyyy.format(criteria.getDataNascitaA())+"','dd/MM/yyyy')":"";	
		
		return sql;
	}
}
