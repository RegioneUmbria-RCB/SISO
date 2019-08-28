package it.webred.ss.dao;

import java.text.SimpleDateFormat;

import it.webred.ss.ejb.dto.SsSearchCriteria;

public class SsQueryBuilder {

	private SsSearchCriteria criteria;
	private SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	
	public SsQueryBuilder() {
	}

	public SsQueryBuilder(SsSearchCriteria criteria) {
		this.criteria = criteria;
	}

	public String createQueryLista(boolean count) {

		//I casi segnalati verso l'utente loggato sono in prima posizione
		//seguiti dai casi per cui ha i permessi in ordine di iter decrescente
		String sql = "";
		
		if(count)
			sql += "SELECT count(s.id) ";
		else sql += "select s.id, a.data data_accesso, "+
				"u.id ufficio_id, U.NOME ufficio, "+
				"pc.id p_contatto_id, PC.NOME punto_contatto, "+
				"nvl(ANA.COGNOME,'') cognome, nvl(ANA.NOME,''), ANA.DATA_NASCITA,  "+
				"nvl(trim(oANA.COGNOME||' '||oANA.NOME),a.operatore) operatore, I.TIPO tipo_intervento, "+
				"nvl(S.DT_MOD,s.dt_ins) data_modifica, P.DT_INS data_privacy, s.identificativo, cs.stato, ana.cf ";

	/*	String sqlSchede = 
				"from SsScheda s "+
				"left join s.accesso a "+
				"inner join a.ssRelUffPcontOrg rel " +
				"where 1=1 ";
		
		String sqlSchede = 
				"from SsScheda s "+
				"left join s.accesso a " +
				"inner join a.ssRelUffPcontOrg rel, "+
				"SsSchedaSegnalato segn  "+
				"inner join  segn.anagrafica ana "+
				"where s.segnalato = segn.id ";*/
		
		String sqlSchede="from ss_scheda s "+
						"left join ss_tipo_scheda i on S.TIPO=i.id "+
						"left join ss_scheda_accesso a on s.accesso=a.id "+
						"left join ss_ufficio u on u.id=A.REL_UPO_UFFICIO_ID "+
						"left join ss_punto_contatto pc on pc.id=A.REL_UPO_PUNTO_CONTATTO_ID "+
						"left join ss_scheda_segnalato segn on s.segnalato=segn.id "+
						"left join ss_anagrafica ana on segn.anagrafica=ana.id "+
						"left join cs_o_operatore_anagrafica oana on OAna.USERNAME= A.OPERATORE "+
						"left join cs_ss_scheda_segr cs on s.id=cs.id "+
						"left join ss_scheda_privacy p on (upper(p.cf)=upper(ana.cf) and a.REL_UPO_ORGANIZZAZIONE_ID=p.organizzazione_id) "+
						"where 1=1 ";

		sql += sqlSchede + getSQLCriteria();
	
		//ORDINAMENTO SOLO ALFABETICO
		if(!count)
			sql += " ORDER BY data_modifica DESC, data_accesso DESC ";
		
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

	/*	String sqlSchede = 
				"from SsScheda s "+
				"left join s.accesso a "+
				"inner join a.ssRelUffPcontOrg rel " +
				"where 1=1 ";
		
		String sqlSchede = 
				"from SsScheda s "+
				"left join s.accesso a " +
				"inner join a.ssRelUffPcontOrg rel, "+
				"SsSchedaSegnalato segn  "+
				"inner join  segn.anagrafica ana "+
				"where s.segnalato = segn.id ";*/
		
		String sqlSchede="from ar_buffer_ss_invio bi "+
						"where 1=1 ";

		sql += sqlSchede + getSQLCriteriaInviate();
	
		//ORDINAMENTO SOLO ALFABETICO
		if(!count)
			sql += " ORDER BY data_ricezione DESC NULLS FIRST, data_accesso DESC ";
		
		return sql;

	}
	
	
	private String getSQLCriteriaInviate(){
		//campi usati per il filter 
				String sqlCriteria = "";
				
				sqlCriteria += criteria.getZonaSociale()!=null       ? " AND bi.dest_zona_soc='"+criteria.getZonaSociale()+"'" : "";
				sqlCriteria += criteria.getOrganizzazioneId()!=null  ?  " AND bi.dest_organizzazione_id= "+ criteria.getOrganizzazioneId() : "";
				sqlCriteria += criteria.isSoloOrganizzazione()       ? " AND bi.dest_ufficio_id is null " : "";
				
				sqlCriteria += criteria.getUfficioId()!=null         ? " AND bi.dest_ufficio_id = "+ criteria.getUfficioId() : "";
				
				sqlCriteria += criteria.getTipoSchedaDescr()!=null   ? " AND bi.ORIG_TIPO_INTERVENTO = '" +criteria.getTipoSchedaDescr()+"'" :"";
				sqlCriteria += criteria.getSegnalato()!=null         ? " AND UPPER(bi.ORIG_COGNOME||' '||bi.ORIG_NOME) like '%" + criteria.getSegnalato().toUpperCase() + "%'" : "";
				
				sqlCriteria += criteria.getOperatoreUserName()!=null ? " AND UPPER(bi.ORIG_COGNOME_OPERATORE||' '||bi.ORIG_NOME_OPERATORE) like '%"+criteria.getOperatoreUserName()+"%'" : "" ;
				sqlCriteria += criteria.getPuntoContattoDescr()!=null ? " AND UPPER(bi.ORIG_PUNTO_CONTATTO) LIKE '%"+ criteria.getPuntoContattoDescr().toUpperCase()  + "%'" : "";
				
				return sqlCriteria;
		
	}
	private String getSQLCriteria() {
		//campi usati per il filter 
		String sqlCriteria = "";
		
		sqlCriteria += criteria.getOrganizzazioneId()!=null ?  " AND A.REL_UPO_ORGANIZZAZIONE_ID = "+ criteria.getOrganizzazioneId() : "";
		sqlCriteria += criteria.getUfficioId()!=null ?         " AND A.REL_UPO_UFFICIO_ID = "+ criteria.getUfficioId() : "";
		
		if(criteria.getCompleta()!=null)
			sqlCriteria += criteria.getCompleta() ? " AND s.completa = 1 " : " AND s.completa = 0 ";
		
		if(criteria.getEliminata()!=null)
			sqlCriteria += criteria.getEliminata() ? " AND s.eliminata = 1 " : " AND s.eliminata = 0 ";
		
		sqlCriteria += criteria.getTipoScheda()!=null ?        " AND s.tipo = " +criteria.getTipoScheda() :"";
		sqlCriteria += criteria.getOperatoreUserName()!=null ? " AND a.operatore = '"+criteria.getOperatoreUserName()+"'" : "" ;
		sqlCriteria += criteria.getSegnalato()!=null ?         " AND UPPER(ana.cognome||' '||ana.nome) like '%" + criteria.getSegnalato().toUpperCase() + "%'" : "";
	    sqlCriteria += criteria.getCf()!=null ? 			   " AND UPPER(ana.cf) like '%" + criteria.getCf().toUpperCase() + "%'" : "";
		sqlCriteria += criteria.getpContattoId()!=null ?       " AND a.REL_UPO_PUNTO_CONTATTO_ID = "+ criteria.getpContattoId() : "";
		sqlCriteria += criteria.getDataAccesso()!=null ?       " AND a.data=TO_DATE('"+ddMMyyyy.format(criteria.getDataAccesso())+"','dd/MM/yyyy')" : "";
		
		String cond = "";
		if(criteria.getSoggettoId()!=null && !criteria.getSoggettoId().isEmpty()){
			for(Long id : criteria.getSoggettoId())
				cond+= ","+id;
			cond = cond.substring(1);
			sqlCriteria +=  " AND segn.anagrafica in ("+cond +")";
		}
		
		
		
		return sqlCriteria;
	}
	
}
