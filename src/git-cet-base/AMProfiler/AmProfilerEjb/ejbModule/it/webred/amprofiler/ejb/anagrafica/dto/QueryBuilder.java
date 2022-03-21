package it.webred.amprofiler.ejb.anagrafica.dto;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

public class QueryBuilder {

	private AnagraficaSearchCriteria criteria;
	
	private static String ID = "id";
	private static String USER = "user";
	private static String CAP = "cap";
	private static String CITTADINANZA = "cittadinanza";
	private static String CF = "codiceFiscale";
	private static String COGNOME = "cognome";
	private static String NOME = "nome";
	private static String INDIRIZZO = "indirizzo";
	private static String SESSO = "sesso";
	private static String STATO = "stato";
	private static String COMUNE_NASC = "comuneNascita";
	private static String COMUNE_RES = "comuneResidenza";
	private static String PROV_NASC = "provinciaNascita";
	private static String PROV_RES = "provinciaResidenza";
	private static String DATA_NASCITA = "dataNascita";
	private static String OLD_USER = "oldUser";
	private static String DISABLE_CAUSE = "disableCause";
	
	
	public QueryBuilder(AnagraficaSearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	
	public String createQuery() {
					
		String sql = "SELECT anag FROM AmAnagrafica anag inner join fetch anag.amUser user";
		
		String whereCond = getSQLCriteria();
		
		if (!"".equals(whereCond)) {
			sql += " WHERE 1 = 1 " + whereCond;
		}
		
		sql += " ORDER BY anag.cognome, anag.nome";

		return sql;
	}
	
	public String replaceApici(String s){
		return s.replaceAll("'", "''");
	}
	
	public String getSQLCriteria() {
		String sqlCriteria = "";
		
		sqlCriteria = (criteria.getId() == null) ? sqlCriteria : addOperator(sqlCriteria) + " anag.id = :" + ID ;

		sqlCriteria = isBlank(criteria.getUserName()) ? sqlCriteria : addOperator(sqlCriteria) + 
				(criteria.isUserNameEquals() ? " UPPER(anag.amUser.name) = :" + USER : " UPPER(anag.amUser.name) "+ concatLikeParam(USER));
		
		sqlCriteria = isBlank(criteria.getCap()) ? sqlCriteria : addOperator(sqlCriteria) + " anag.cap " + concatLikeParam(CAP);
		
		sqlCriteria = isBlank(criteria.getCittadinanza()) ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.cittadinanza)  " + concatLikeParam(CITTADINANZA);
		
		sqlCriteria =  isBlank(criteria.getCognome()) ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.cognome) " + concatLikeParam(COGNOME);
		
		sqlCriteria = isBlank(criteria.getNome()) ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.nome) " + concatLikeParam(NOME);
		
		sqlCriteria = isBlank(criteria.getCodiceFiscale()) ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.codiceFiscale) " + concatLikeParam(CF);
				
		sqlCriteria = isBlank(criteria.getIndirizzo()) ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.indirizzo) " + concatLikeParam(INDIRIZZO);
		
		sqlCriteria = isBlank(criteria.getSesso()) ? sqlCriteria : addOperator(sqlCriteria) + " anag.sesso = :" + SESSO;
		
		sqlCriteria = isBlank(criteria.getStato()) || criteria.getStato().trim().contains("PWD") ? sqlCriteria : addOperator(sqlCriteria) + " anag.amUser.disableCause :" + STATO;

		sqlCriteria = isBlank(criteria.getComuneNascita()) ? sqlCriteria : addOperator(sqlCriteria) + " anag.comuneNascita " + concatLikeParam(COMUNE_NASC);

		sqlCriteria = isBlank(criteria.getComuneResidenza()) ? sqlCriteria : addOperator(sqlCriteria) + " anag.comuneResidenza " + concatLikeParam(COMUNE_RES);

		sqlCriteria = isBlank(criteria.getProvinciaNascita()) ? sqlCriteria : addOperator(sqlCriteria) + " anag.provinciaNascita " + concatLikeParam(PROV_NASC);

		sqlCriteria = isBlank(criteria.getProvinciaResidenza()) ? sqlCriteria : addOperator(sqlCriteria) + " anag.provinciaResidenza " + concatLikeParam(PROV_RES);

		sqlCriteria = isBlank(criteria.getDataNascita()) ? sqlCriteria : addOperator(sqlCriteria) + " anag.dataNascita = TO_DATE( :" + DATA_NASCITA + ", 'dd/MM/yyyy')";
		
		String oldUser = criteria.getOldUser();
		if(isBlank(criteria.getOldUser())){
			//NON FARE NULLA
		}else if(criteria.getOldUser().equalsIgnoreCase("@@null")){
			sqlCriteria = addOperator(sqlCriteria) + " anag.amUser.oldName is null ";
		}else if(criteria.getOldUser().equalsIgnoreCase("@@!null")){
			sqlCriteria = addOperator(sqlCriteria) + " anag.amUser.oldName is not null ";
		}else{
			sqlCriteria = addOperator(sqlCriteria) + " UPPER(anag.amUser.oldName) " + concatLikeParam(OLD_USER);
		}
		
		if(isBlank(criteria.getDisableCause())){
			//NON FARE NULLA
		}else if(criteria.getDisableCause().equalsIgnoreCase("@@null")){
			sqlCriteria = addOperator(sqlCriteria) + " anag.amUser.disableCause is null ";
		}else if(criteria.getDisableCause().equalsIgnoreCase("@@!null")){
			sqlCriteria = addOperator(sqlCriteria) + " anag.amUser.disableCause is not null ";
		}else{
			sqlCriteria = addOperator(sqlCriteria) + " UPPER(anag.amUser.disableCause) " + concatLikeParam(DISABLE_CAUSE);
		}
		return sqlCriteria;
	}
	
	public void setParameters(Query q){
		if(!StringUtils.isBlank(criteria.getUserName()))
			q.setParameter(USER, criteria.getUserName().toUpperCase());
		
		if(!StringUtils.isBlank(criteria.getCap()))
			q.setParameter(CAP, criteria.getCap());
		
		if(!StringUtils.isBlank(criteria.getCittadinanza()))
			q.setParameter(CITTADINANZA, criteria.getCittadinanza().toUpperCase());
		
		if(!StringUtils.isBlank(criteria.getCognome()))
			q.setParameter(COGNOME, criteria.getCognome().toUpperCase());
		
		if(!StringUtils.isBlank(criteria.getNome()))
			q.setParameter(NOME, criteria.getNome().toUpperCase());
		
		if(!StringUtils.isBlank(criteria.getCodiceFiscale()))
			q.setParameter(CF, criteria.getCodiceFiscale().toUpperCase());
		
		if(!StringUtils.isBlank(criteria.getIndirizzo()))
			q.setParameter(INDIRIZZO, criteria.getIndirizzo().toUpperCase());
		
		if(!StringUtils.isBlank(criteria.getSesso()))
			q.setParameter(SESSO, criteria.getSesso());
		
		if(!isBlank(criteria.getStato()) && !criteria.getStato().trim().contains("PWD"))
			q.setParameter(STATO, criteria.getStato());
		
		if(!StringUtils.isBlank(criteria.getComuneNascita()))
			q.setParameter(COMUNE_NASC, criteria.getComuneNascita());
		
		if(!StringUtils.isBlank(criteria.getComuneResidenza()))
			q.setParameter(COMUNE_RES, criteria.getComuneResidenza());
		
		if(!StringUtils.isBlank(criteria.getProvinciaNascita()))
			q.setParameter(PROV_NASC, criteria.getProvinciaNascita());
		
		if(!StringUtils.isBlank(criteria.getProvinciaResidenza()))
			q.setParameter(PROV_RES, criteria.getProvinciaResidenza());
		
		if(!StringUtils.isBlank(criteria.getDataNascita()))
			q.setParameter(DATA_NASCITA, criteria.getDataNascita());
		
		if(!isBlank(criteria.getOldUser()) && !criteria.getOldUser().equalsIgnoreCase("@@null") && !criteria.getOldUser().equalsIgnoreCase("@@!null"))
			q.setParameter(OLD_USER, criteria.getOldUser().toUpperCase());
		
		if(!isBlank(criteria.getDisableCause()) && !criteria.getDisableCause().equalsIgnoreCase("@@null") && !criteria.getDisableCause().equalsIgnoreCase("@@!null"))
			q.setParameter(DISABLE_CAUSE, criteria.getDisableCause().toUpperCase());
		
	}
	
	protected String addOperator(String criteria) {  	    
    	    return criteria += " AND ";
    }
	
	protected String concatLikeParam(String label){
		return "LIKE '%'|| :"+label+" ||'%'";
	}
	
	protected boolean isBlank(String val){
		return val == null || val.trim().equals("");
	}
	

}
