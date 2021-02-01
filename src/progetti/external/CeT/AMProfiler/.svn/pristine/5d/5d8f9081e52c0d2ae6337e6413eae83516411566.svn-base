package it.webred.amprofiler.ejb.anagrafica.dto;

public class QueryBuilder {

	private AnagraficaSearchCriteria criteria;
	
	private Integer id;
	private String user;
	private boolean userEquals;
	private String cap;
	private String cittadinanza;
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private String indirizzo;
	private String sesso;
	private String stato;
	private String comuneNascita;
	private String comuneResidenza;
	private String provinciaNascita;
	private String provinciaResidenza;
	private String dataNascita;
	
	public QueryBuilder(AnagraficaSearchCriteria criteria) {
		this.criteria = criteria;
		id = criteria.getId();
		user = criteria.getUserName();
		userEquals = criteria.isUserNameEquals();
		cap = criteria.getCap();
		cittadinanza = criteria.getCittadinanza();
		codiceFiscale = criteria.getCodiceFiscale();
		cognome = criteria.getCognome();
		nome = criteria.getNome();
		indirizzo = criteria.getIndirizzo();
		sesso = criteria.getSesso();
		stato = criteria.getStato();
		comuneNascita = criteria.getComuneNascita();
		comuneResidenza = criteria.getComuneResidenza();
		provinciaNascita = criteria.getProvinciaNascita();
		provinciaResidenza = criteria.getProvinciaResidenza();
		dataNascita = criteria.getDataNascita();
	}
	
	
	public String createQuery() {
					
		String sql = "SELECT anag FROM AmAnagrafica anag";
		
		String whereCond = getSQLCriteria();
		
		if (!"".equals(whereCond)) {
			sql += " WHERE 1 = 1 " + whereCond;
		}
		
		sql += " ORDER BY anag.cognome, anag.nome";

		return sql;
	}
	
	public String getSQLCriteria() {
		String sqlCriteria = "";
		
		sqlCriteria = (id == null) ? sqlCriteria : addOperator(sqlCriteria) + " anag.id = " + id;

		sqlCriteria = (user == null  || user.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + 
				(userEquals ? " UPPER(anag.amUser.name) = '" + user.toUpperCase() + "'" : " UPPER(anag.amUser.name) like '" + user.toUpperCase() + "%'"));
		
		sqlCriteria = (cap == null  || cap.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " anag.cap LIKE '" + cap + "'");
		
		sqlCriteria = (cittadinanza == null || cittadinanza.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.cittadinanza) LIKE '" + cittadinanza.toUpperCase() +"'");
		
		sqlCriteria = (cognome == null || cognome.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.cognome) LIKE '%" + cognome.toUpperCase() + "%'");

		sqlCriteria = (nome == null || nome.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.nome) LIKE '%" + nome.toUpperCase() + "%'");
		
		sqlCriteria = (codiceFiscale == null || codiceFiscale.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.codiceFiscale) LIKE '%" + codiceFiscale.toUpperCase() + "%'");
				
		sqlCriteria = (indirizzo == null || indirizzo.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(anag.indirizzo) LIKE '%" + indirizzo.toUpperCase() + "%'");
		
		sqlCriteria = (sesso == null  || sesso.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " anag.sesso LIKE '" + sesso + "'");
		
		sqlCriteria = (stato == null  || stato.trim().equals("") || stato.trim().contains("PWD") ? sqlCriteria : addOperator(sqlCriteria) + " anag.amUser.disableCause LIKE '" + stato + "'");

		sqlCriteria = (comuneNascita == null  || comuneNascita.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " anag.comuneNascita LIKE '%" + comuneNascita + "%'");

		sqlCriteria = (comuneResidenza == null  || comuneResidenza.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " anag.comuneResidenza LIKE '%" + comuneResidenza + "%'");

		sqlCriteria = (provinciaNascita == null  || provinciaNascita.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " anag.provinciaNascita LIKE '%" + provinciaNascita + "%'");

		sqlCriteria = (provinciaResidenza == null  || provinciaResidenza.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " anag.provinciaResidenza LIKE '%" + provinciaResidenza + "%'");

		sqlCriteria = (dataNascita == null  || dataNascita.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " anag.dataNascita = TO_DATE('" + dataNascita + "', 'dd/MM/yyyy'");

		
		return sqlCriteria;
	}
	
	protected String addOperator(String criteria) {  	    
    	    return criteria += " AND ";
    }

}
