package it.webred.cs.csa.ejb.queryBuilder;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import it.webred.cs.csa.ejb.dto.pai.PaiSearchCriteria;
import it.webred.cs.data.DataModelCostanti;


public class PaiQueryBuilder extends QueryBuilderBase{
	
	
	private static String SETTORE_ID = "settoreId";
	private static String DENOMINAZIONE = "denominazione";
	private static String TIPO_BENEFICIARIO = "tipoBeneficiario";
	private static String DIARIO_ID  = "diarioId";
	private static String DA_CHIUDERE = "daChiudere";
	private static String DA_CONTROLLARE = "daControllare";
	private static String TIPO_PAI_ID = "tipoPaiId";
	
	public static String CASO_ID = "casoId";
	public static String COD_FISCALE = "cf";
	
	public static final String SQL_INTERVALLO_PAI = "select d.id, D.DT_ATTIVAZIONE_DA, D.DT_CHIUSURA_DA "
       +" from cs_d_diario d , cs_d_pai p left join CS_PAI_MAST_SOGG s on S.DIARIO_ID = p.diario_id  "
       +" where d.id = p.diario_id "
       +" and D.TIPO_DIARIO_ID = " + DataModelCostanti.TipoDiario.PAI_ID + " "
       +" and p.diario_id <> :diarioId "
       +" and S.CF = :codFiscale and P.TIPO_PAI_ID = :tipoPaiId";

/*	public static String SQL_PAI_CASO = "SELECT distinct p.* FROM cs_d_diario d"
			+ "	  INNER JOIN cs_d_pai p ON p.diario_id = d.id"
			+ "	  WHERE d.tipo_diario_id = " + DataModelCostanti.TipoDiario.PAI_ID + " AND d.caso_id = :casoId";
	
	public static String SQL_PAI_ESTERNI_FROM_CF = "SELECT distinct p.* FROM cs_d_diario d"
			+ "	  INNER JOIN cs_d_pai p ON p.diario_id = d.id"
			+ "	  INNER JOIN cs_pai_mast_sogg  ms ON ms.diario_id = p.diario_id AND ms.cf = :codiceFiscale "
			+ "	  WHERE d.tipo_diario_id = " + DataModelCostanti.TipoDiario.PAI_ID + " AND d.caso_id IS NULL";
	
	public static String SQL_PAI_FASCICOLO = SQL_PAI_CASO + " UNION ALL "+ SQL_PAI_ESTERNI_FROM_CF;
	
	public static String SQL_COUNT_PAI_FASCICOLO = "SELECT COUNT(*) FROM ("+SQL_PAI_FASCICOLO+")";
	
	public static String SQL_ALL_PAI = "select r from CsDPai r "+
        	"inner join fetch r.csDDiario d "+
        	"join fetch d.csOOperatoreSettore os "+ 
        	"where os.csOSettore.id = :" + SETTORE_ID +
        	" FILTER_CONDITION "+
        	"order by r.diarioId desc";
	
	public static String SQL_COUNT_ALL_PAI = "select count(distinct d.id) from CsDDiario d "+
        	" where d.csTbTipoDiario.id = " + DataModelCostanti.TipoDiario.PAI_ID + " AND d.csOOperatoreSettore.csOSettore.id = :" + SETTORE_ID +
        	" FILTER_CONDITION ";
        	
    */

	public static String SQL_RELAZIONI_PAI_ESTERNI_FROM_CF = "SELECT r.* FROM cs_d_diario d"
			+ "	  INNER JOIN cs_d_pai p ON p.diario_id = d.id"
			+ "	  INNER JOIN cs_pai_mast_sogg  ms ON ms.diario_id = p.diario_id  AND ms.cf = :codiceFiscale"
			+ "	  INNER JOIN cs_rel_diario_diariorif rif ON rif.diario_id = d.id"
			+ "	  INNER JOIN cs_d_diario d2 ON d2.id = rif.diario_id_rif AND d2.tipo_diario_id = 6"
			+ "   INNER JOIN cs_d_relazione r ON r.diario_id = d2.id"
			+ "	  WHERE d.tipo_diario_id = " + DataModelCostanti.TipoDiario.PAI_ID + " AND d.caso_id IS NULL";
	
	public static String SQL_INTERVENTI_PAI_ESTERNI_FROM_CF = "SELECT INTERVENTO.*	FROM CS_I_INTERVENTO INTERVENTO"
			+ " INNER JOIN CS_D_PAI pai	ON pai.DIARIO_ID = intervento.DIARIO_PAI_ID"
			+ "	INNER JOIN CS_PAI_MAST_SOGG SOGG ON sogg.DIARIO_ID = pai.DIARIO_ID "
			+ "	WHERE sogg.cf= :cf ";
	
	public static String SQL_ALL_PAI_SINTESI = "SELECT distinct p.* "
			+ "FROM CS_VISTA_PAI p, cs_pai_mast_sogg s "
			+ "WHERE p.diario_id = s.diario_id FILTER_CONDITION "
        	+"order by p.diario_Id desc";
	
	public static String SQL_COUNT_ALL_PAI_SINTESI = "SELECT COUNT(DISTINCT p.diario_id) "
			+ "FROM CS_VISTA_PAI p, cs_pai_mast_sogg s "
			+ "WHERE p.diario_id = s.diario_id FILTER_CONDITION ";
	
	public String getSqlExternalPai(PaiSearchCriteria psb, boolean count){
		String cond = !psb.isLoadListaExtCompleta() ? " and p.caso_Id is null " : "";
		cond+= " AND p.settore_Id = :"+SETTORE_ID;
		cond = this.getFilterCondition(psb, cond, count);
		String sql = count ? SQL_COUNT_ALL_PAI_SINTESI : SQL_ALL_PAI_SINTESI;
		sql  = sql.replace("FILTER_CONDITION",cond);
		return sql;
	}
	
	public String getSqFascicoloPai(PaiSearchCriteria psb, boolean count){
		String cond = "AND (p.caso_Id = :"+CASO_ID+" OR (p.caso_Id is null AND upper(s.cf) = :"+COD_FISCALE+"))";
		cond = this.getFilterCondition(psb, cond, count);
		String sql = count ? SQL_COUNT_ALL_PAI_SINTESI : SQL_ALL_PAI_SINTESI;
		sql  = sql.replace("FILTER_CONDITION",cond);
		return sql;
	}
	
	public String getFilterCondition(PaiSearchCriteria psb, String cond, boolean count){
		cond+= !StringUtils.isBlank(psb.getDenominazione()) ? " and upper(s.cognome||' '||s.nome) " + concatLikeParam(DENOMINAZIONE) : "";
		cond+= !StringUtils.isBlank(psb.getTipoBeneficiario()) ? " and p.tipo_Beneficiario = :"+ TIPO_BENEFICIARIO : "";
		cond+= psb.getDiarioId()!=null && psb.getDiarioId()>0 ? " and p.diario_Id = :" + DIARIO_ID : "";
		cond+= psb.getDaChiudere()!=null ? " and p.da_Chiudere = :" + DA_CHIUDERE : "";
		cond+= psb.getDaControllare()!=null ? " and p.da_Controllare = :" + DA_CONTROLLARE : "";
		cond+= psb.getTipoPaiId()!=null && psb.getTipoPaiId()>0 ? " and p.tipo_Pai_Id=:"+TIPO_PAI_ID : "";
		
		return cond;
	}
	
	
	public String setFilterParameters(Query q, PaiSearchCriteria criteria){
		String params = "";
		if(!StringUtils.isBlank(criteria.getDenominazione())) 
			params = setParameter(q, DENOMINAZIONE, criteria.getDenominazione().toUpperCase(), params);
		if(!StringUtils.isBlank(criteria.getTipoBeneficiario())) 
			params = setParameter(q, TIPO_BENEFICIARIO, criteria.getTipoBeneficiario(), params);
		if(criteria.getDiarioId()!=null && criteria.getDiarioId()>0)
			params = setParameter(q, DIARIO_ID, criteria.getDiarioId(), params);
		if(criteria.getDaChiudere()!=null)
			params = setParameter(q, DA_CHIUDERE, criteria.getDaChiudere(), params);
		if(criteria.getDaControllare()!=null)
			params = setParameter(q, DA_CONTROLLARE, criteria.getDaControllare(), params);
		if(criteria.getTipoPaiId()!=null && criteria.getTipoPaiId()>0)
			params = setParameter(q, TIPO_PAI_ID, criteria.getTipoPaiId(), params);
		return params;
	}
	
	
}
