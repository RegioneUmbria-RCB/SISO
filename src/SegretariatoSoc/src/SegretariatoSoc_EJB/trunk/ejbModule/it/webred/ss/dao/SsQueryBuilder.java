package it.webred.ss.dao;

import it.webred.ss.ejb.dto.SsSearchCriteria;

public class SsQueryBuilder {

	private SsSearchCriteria criteria;
	
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
				"nvl(S.DT_MOD,s.dt_ins) data_modifica, P.DT_INS data_privacy, s.identificativo ";

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
						"left join ss_scheda_privacy p on (upper(p.cf)=upper(ana.cf) and a.REL_UPO_ORGANIZZAZIONE_ID=p.organizzazione_id) "+
						"where 1=1 ";

		sql += sqlSchede + getSQLCriteria();
	
		//ORDINAMENTO SOLO ALFABETICO
		if(!count)
			sql += " ORDER BY data_modifica DESC, data_accesso DESC ";
		
		return sql;

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
		sqlCriteria += criteria.getpContattoId()!=null ?       " AND a.REL_UPO_PUNTO_CONTATTO_ID = "+ criteria.getpContattoId() : "";
		
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
