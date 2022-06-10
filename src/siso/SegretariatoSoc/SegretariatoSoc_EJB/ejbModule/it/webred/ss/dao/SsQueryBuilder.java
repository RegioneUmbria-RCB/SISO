package it.webred.ss.dao;

import java.text.SimpleDateFormat;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.webred.ss.ejb.dto.SsSearchCriteria;

public class SsQueryBuilder {

	private static final String ZONA_SOCIALE = "zonaSocialeId";
	private static final String ORGANIZZAZIONE_ID = "organizzazioneId";
	private static final String UFFICIO_ID = "ufficioId";
	private static final String PUNTO_CONTATTO_ID = "puntoContattoId";
	private static final String PUNTO_CONTATTO_DESC = "puntoContattoDesc";
	private static final String TIPO_SCHEDA_ID = "tipoSchedaId";
	private static final String TIPO_SCHEDA_DESC = "tipoSchedaDesc";
	private static final String OPERATORE = "operatore";
	private static final String DENOMINAZIONE = "denominazione";
	private static final String COD_FISCALE = "cf";
	private static final String ALIAS = "alias";
	private static final String RESIDENZA = "residenza";
	private static final String DATA_ACCESSO = "dataAccesso";
	private static final String LISTA_ANAGRAFICA_ID = "listaAnagraficaId";
	
	//Ricerca soggetti
	private static final String COGNOME = "cognome";
	private static final String NOME = "nome";	
	private static final String SESSO = "sesso";
	private static final String DATA_NASCITA_DA = "datNascitaDa";
	private static final String DATA_NASCITA_A = "datNascitaA";
	
	private SsSearchCriteria criteria;
	private SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	protected static Logger logger = Logger.getLogger("segretariatosoc.log");
	
	
	public SsQueryBuilder() {
	}

	public SsQueryBuilder(SsSearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	protected String concatLikeParam(String label){
		return "LIKE '%'|| :"+label+" ||'%'";
	}
	
	protected String setParameter(Query q, String key, Object val, String msg){
		try{
			q.setParameter(key, val);
			msg = msg + "["+key+":"+val+"]";
		}catch(Exception qe){
			logger.error("Tentativo di impostazione parametro query: "+qe.getMessage());
		}
		return msg;
	}

	public String createQueryLista(boolean count) {

		//I casi segnalati verso l'utente loggato sono in prima posizione
		//seguiti dai casi per cui ha i permessi in ordine di iter decrescente
		String sql = "";
		
		if(count)
			sql += "SELECT count(distinct s.id) ";
		else sql += "select DISTINCT "+
				"s.id, a.data data_accesso, "+
				"u.id ufficio_id, U.NOME ufficio, "+
				"pc.id p_contatto_id, PC.NOME punto_contatto, "+
				"nvl(ANA.COGNOME,'') cognome, nvl(ANA.NOME,''), ANA.DATA_NASCITA,  "+
				"nvl(trim(oANA.COGNOME||' '||oANA.NOME),a.operatore) operatore, I.TIPO tipo_intervento, "+
				"nvl(S.DT_MOD,s.dt_ins) data_modifica, P.DT_INS data_privacy, s.identificativo, cs.stato, cs_o.nome, "+
				"ana.cf, ana.alias, DECODE(comune_cod, NULL, stato_des, (comune_des||' ('||prov_cod||')'))  residenza ";

		String sqlSchede="from ss_scheda s "+
						"left join ss_tipo_scheda i on S.TIPO=i.id "+
						"left join ss_scheda_accesso a on s.accesso=a.id "+
						"left join ss_ufficio u on u.id=A.REL_UPO_UFFICIO_ID "+
						"left join ss_punto_contatto pc on pc.id=A.REL_UPO_PUNTO_CONTATTO_ID "+
						"left join ss_scheda_segnalato segn on s.segnalato=segn.id "+
						"left join ss_indirizzo res on segn.residenza = res.id "+
						"left join ss_anagrafica ana on segn.anagrafica=ana.id "+
						"left join ss_operatore_anagrafica oana on OAna.operatore = A.OPERATORE "+
						"left join cs_ss_scheda_segr cs on (s.id=cs.SCHEDA_ID AND cs.PROVENIENZA = 'SS') "+
						"left join cs_o_organizzazione cs_o on cs.cod_ente = cs_o.cod_routing "+
						"left join ss_scheda_privacy p on (upper(p.cf)=upper(ana.cf) and a.REL_UPO_ORGANIZZAZIONE_ID=p.organizzazione_id) "+
						"where 1=1 ";

		sql += sqlSchede + getSQLCriteria();

		/*
		 * 1.Le connessioni agli schemi non devono essere cablate nel codice (installazioni multi-ente) --> utilizzare sinonimi
		 * */
		String filtroPunto3 = " AND" + 
 				" NOT EXISTS ( SELECT * FROM AM_USER us " + 
                  " JOIN AM_USER_GROUP ug ON (ug.FK_AM_USER = us.NAME) " + 
                  " WHERE us.NAME = '"   +  criteria.getUserId() + "'" +
                  " AND ( ug.FK_AM_GROUP IN (" + 
                  " SELECT DISTINCT regexp_substr(u.ESCLUDI_AM_GROUP,'[^|]+', 1, level) FROM SS_UFFICIO " + 
                  " connect by regexp_substr(u.ESCLUDI_AM_GROUP, '[^|]+', 1, level) is not null ))) ";
				 //2.Il campo ESCLUDI_AM_GROUP è una stringa di valori concatenati tramite ';' --> CORREGGERE LA QUERY 
				sql += filtroPunto3;
		
		if(!count)
			sql += " ORDER BY data_accesso DESC ";
						
		return sql;

	}
	
	public String createQueryListaInviate(boolean count) {

		//I casi segnalati verso l'utente loggato sono in prima posizione
		//seguiti dai casi per cui ha i permessi in ordine di iter decrescente
		String sql = "";
		
		if(count)
			sql += "SELECT count(bi.id) ";
		else sql += "select bi.orig_id, bi.orig_data_accesso data_accesso, "+
				"bi.orig_nome_ufficio ufficio, "+
				"bi.orig_punto_contatto punto_contatto, "+
				"nvl(bi.orig_cognome,'') cognome, nvl(bi.orig_nome,''),bi.orig_data_nascita,  "+
				"trim(bi.orig_cognome_operatore||' '||bi.orig_nome_operatore) operatore, bi.orig_tipo_intervento tipo_intervento, "+
				"bi.orig_data_invio data_invio,bi.ricez_data data_ricezione, bi.orig_zona_soc zona_inviante, bi.orig_desc_organizzazione desc_organizzazione ";

		String sqlSchede="from ar_buffer_ss_invio bi where 1=1 ";

		sql += sqlSchede + getSQLCriteriaInviate();
	
		//ORDINAMENTO SOLO ALFABETICO
		if(!count)
			sql += " ORDER BY data_ricezione DESC NULLS FIRST, data_accesso DESC ";
		
		return sql;

	}
	
	
	private String getSQLCriteriaInviate(){
		//campi usati per il filter 
		String sqlCriteria = "";
		
		sqlCriteria += criteria.getZonaSociale()!=null       ? " AND bi.dest_zona_soc= :"+ZONA_SOCIALE : "";
		sqlCriteria += criteria.getOrganizzazioneId()!=null  ?  " AND bi.dest_organizzazione_id= :"+ ORGANIZZAZIONE_ID : "";
		sqlCriteria += criteria.isSoloOrganizzazione()       ? " AND bi.dest_ufficio_id is null " : "";
		
		sqlCriteria += criteria.getUfficioId()!=null         ? " AND bi.dest_ufficio_id = :"+ UFFICIO_ID : "";
		
		sqlCriteria += criteria.getTipoSchedaDescr()!=null   ? " AND bi.ORIG_TIPO_INTERVENTO = :" + TIPO_SCHEDA_DESC :"";
		sqlCriteria += criteria.getSegnalato()!=null         ? " AND UPPER(bi.ORIG_COGNOME||' '||bi.ORIG_NOME) "+concatLikeParam(DENOMINAZIONE) : "";
		
		sqlCriteria += criteria.getOperatoreUserName()!=null ? " AND UPPER(bi.ORIG_COGNOME_OPERATORE||' '||bi.ORIG_NOME_OPERATORE) "+concatLikeParam(OPERATORE) : "" ;
		sqlCriteria += criteria.getPuntoContattoDescr()!=null ? " AND UPPER(bi.ORIG_PUNTO_CONTATTO) "+ concatLikeParam(PUNTO_CONTATTO_DESC) : "";
		
		return sqlCriteria;
		
	}
	private String getSQLCriteria() {
		//campi usati per il filter 
		String sqlCriteria = "";
		
		sqlCriteria += criteria.getOrganizzazioneId()!=null ?  " AND A.REL_UPO_ORGANIZZAZIONE_ID = :"+ ORGANIZZAZIONE_ID : "";
		sqlCriteria += criteria.getUfficioId()!=null ?         " AND A.REL_UPO_UFFICIO_ID = :"+ UFFICIO_ID : "";
		
		if(criteria.getCompleta()!=null)
			sqlCriteria += criteria.getCompleta() ? " AND s.completa = 1 " : " AND s.completa = 0 ";
		
		if(criteria.getEliminata()!=null)
			sqlCriteria += criteria.getEliminata() ? " AND s.eliminata = 1 " : " AND s.eliminata = 0 ";
		
		sqlCriteria += criteria.getTipoScheda()!=null ?        " AND s.tipo = :" + TIPO_SCHEDA_ID :"";
		sqlCriteria += criteria.getOperatoreUserName()!=null ? " AND a.operatore = :"+ OPERATORE : "" ;
		sqlCriteria += criteria.getSegnalato()!=null ?         " AND UPPER(ana.cognome||' '||ana.nome)" +concatLikeParam(DENOMINAZIONE)  : "";
	    sqlCriteria += criteria.getCf()!=null ? 			   " AND UPPER(ana.cf)" +concatLikeParam(COD_FISCALE)  : "";
	    sqlCriteria += criteria.getAlias()!=null ? 			   " AND UPPER(ana.alias) "+concatLikeParam(ALIAS) : "";
		sqlCriteria += criteria.getpContattoId()!=null ?       " AND a.REL_UPO_PUNTO_CONTATTO_ID = :" + PUNTO_CONTATTO_ID  : "";
		sqlCriteria += criteria.getDataAccesso()!=null ?       " AND a.data= :" + DATA_ACCESSO  : "";
		sqlCriteria += criteria.getResidenza()!=null ? 		   " AND (UPPER(comune_des||' ('||prov_cod||')')" +concatLikeParam(RESIDENZA) + " OR UPPER(stato_des) " + concatLikeParam(RESIDENZA) +")" : "";
	
		sqlCriteria += criteria.getSoggettoId()!=null && !criteria.getSoggettoId().isEmpty() ? " AND segn.anagrafica IN (:"+LISTA_ANAGRAFICA_ID+")" : "";
		
		return sqlCriteria;
	}

	public String createQueryListaSoggetti() {
		String sql = "select segnalato "+
				"from SsScheda scheda, SsSchedaSegnalato segnalato "+
				"where segnalato.id = scheda.segnalato and scheda.eliminata = false ";
		
		sql+= criteria.getCognome()!=null && criteria.getCognome().length()>=2 ? 	"and upper(segnalato.anagrafica.cognome) "+concatLikeParam(COGNOME) : ""; 
		sql+= !StringUtils.isBlank(criteria.getNome()) ?				 			"and upper(segnalato.anagrafica.nome) "+concatLikeParam(NOME) : ""; 
		sql+= !StringUtils.isBlank(criteria.getCf()) ?								"and upper(segnalato.anagrafica.cf) = :"+COD_FISCALE :"";
		sql+= !StringUtils.isBlank(criteria.getSesso()) ?					 		"and upper(segnalato.anagrafica.sesso) = :"+SESSO : "";
		sql+= criteria.getDataNascitaDa()!=null ? 									"and segnalato.anagrafica.data_nascita >= TO_DATE(:"+DATA_NASCITA_DA+",'dd/MM/yyyy')":"";		
		sql+= criteria.getDataNascitaA()!=null ? 									"and segnalato.anagrafica.data_nascita <= TO_DATE(:"+DATA_NASCITA_A+",'dd/MM/yyyy')":"";	
		sql+= !StringUtils.isBlank(criteria.getAlias()) ?			 				"and upper(segnalato.anagrafica.alias) "+ concatLikeParam(ALIAS) : ""; 
		
		return sql;
	}
	
	
	public String setFilterParameters(Query q){
		
		String params = "";
		
		if(criteria.getZonaSociale()!=null)
			  params = setParameter(q, ZONA_SOCIALE, criteria.getZonaSociale(), params);
		
		  if(criteria.getOrganizzazioneId()!=null)
			  params = setParameter(q, ORGANIZZAZIONE_ID, criteria.getOrganizzazioneId(), params);
		  
		  if(criteria.getUfficioId() != null)
			  params = setParameter(q, UFFICIO_ID, criteria.getUfficioId(), params);
		  
		  if(criteria.getpContattoId() != null)
			  params = setParameter(q, PUNTO_CONTATTO_ID, criteria.getpContattoId(), params);
		  
		  if(!StringUtils.isBlank(criteria.getPuntoContattoDescr()))
			  params = setParameter(q, PUNTO_CONTATTO_DESC, criteria.getPuntoContattoDescr(), params);
		  
		  if(criteria.getTipoScheda() != null)
			  params = setParameter(q, TIPO_SCHEDA_ID, criteria.getTipoScheda(), params);
		  
		  if(!StringUtils.isBlank(criteria.getTipoSchedaDescr()))
			  params = setParameter(q, TIPO_SCHEDA_DESC, criteria.getTipoSchedaDescr(), params);
		  
		  if(criteria.getOperatoreUserName() != null)
			  params = setParameter(q, OPERATORE, criteria.getOperatoreUserName(), params);
		  
		  if(!StringUtils.isBlank(criteria.getSegnalato()))
				params = setParameter(q, DENOMINAZIONE, criteria.getSegnalato().trim().toUpperCase(), params);
		  
		  if(!StringUtils.isBlank(criteria.getCf()))
				params = setParameter(q, COD_FISCALE, criteria.getCf().trim().toUpperCase(), params);
		  
		  if(!StringUtils.isBlank(criteria.getAlias()))
				params = setParameter(q, ALIAS, criteria.getAlias().trim().toUpperCase(), params);
		  
		  if(!StringUtils.isBlank(criteria.getResidenza()))
				params = setParameter(q, RESIDENZA, criteria.getResidenza().trim().toUpperCase(), params);
		  
		  if(criteria.getDataAccesso()!=null)
				params = setParameter(q, DATA_ACCESSO, criteria.getDataAccesso(), params);
		  
		  if(criteria.getSoggettoId()!=null && !criteria.getSoggettoId().isEmpty())
			  params = setParameter(q, LISTA_ANAGRAFICA_ID, criteria.getSoggettoId(), params);
		  
		  //Lista Soggetti
		  if(!StringUtils.isBlank(criteria.getCognome()) && criteria.getCognome().length()>=2)
				params = setParameter(q, COGNOME, criteria.getCognome().trim().toUpperCase(), params);
		  
		  if(!StringUtils.isBlank(criteria.getNome()))
				params = setParameter(q, NOME, criteria.getNome().trim().toUpperCase(), params);
		  
		  if(!StringUtils.isBlank(criteria.getSesso()))
				params = setParameter(q, SESSO, criteria.getSesso().trim().toUpperCase(), params);
		  
		  if(criteria.getDataNascitaDa()!=null)
			  params = setParameter(q, DATA_NASCITA_DA, ddMMyyyy.format(criteria.getDataNascitaDa()), params);
		  
		  if(criteria.getDataNascitaA()!=null)
			  params = setParameter(q, DATA_NASCITA_A, ddMMyyyy.format(criteria.getDataNascitaA()), params);
		  
		  return params;
	}
}
