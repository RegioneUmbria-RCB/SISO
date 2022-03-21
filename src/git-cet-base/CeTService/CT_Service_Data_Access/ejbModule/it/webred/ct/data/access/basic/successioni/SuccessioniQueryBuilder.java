package it.webred.ct.data.access.basic.successioni;

import it.webred.ct.data.access.basic.successioni.dto.RicercaSuccessioniDTO;

public class SuccessioniQueryBuilder {
	
	private final String HQL_SUCA_BY_PK = "FROM SuccessioniA SUCA "
			+ " where 1=1 @HQL_SUCA_WHERE@ "
			+ " order by SUCA.dataApertura, SUCA.cognomeDefunto, SUCA.nomeDefunto ";
	
	private final String HQL_SUCB_BY_PK = "FROM SuccessioniB SUCB "
			+ " where 1=1 @HQL_SUCB_WHERE@ "
			+ " order by SUCB.denominazione ";
	
	private final String HQL_SUCC_BY_PK = "FROM SuccessioniC SUCC "
			+ " where 1=1 @HQL_SUCC_WHERE@ "
			+ " order by SUCC.sezione, SUCC.foglio, SUCC.particella1, SUCC.subalterno1 ";
	
	private final String HQL_SUCD_BY_PK = "FROM SuccessioniD SUCD "
			+ " where 1=1 @HQL_SUCD_WHERE@ "
			+ " order by SUCD.ufficio, SUCD.anno, SUCD.volume, SUCD.numero, SUCD.sottonumero, SUCD.comune, SUCD.progressivo ";
	
	private final String SQL_EREDITA_BY_PARAMS = "select distinct "
			+ " successioni_c.UFFICIO, successioni_c.ANNO, successioni_c.VOLUME, successioni_c.NUMERO, successioni_c.SOTTONUMERO, successioni_c.COMUNE, successioni_c.PROGRESSIVO, "
			+ " successioni_c.FOGLIO, successioni_c.PARTICELLA1, successioni_c.SUBALTERNO1, successioni_c.PROGRESSIVO_IMMOBILE, successioni_c.PROGRESSIVO_PARTICELLA, successioni_d.PROGRESSIVO_EREDE, "
			+ " successioni_c.NUMERATORE_QUOTA_DEF, successioni_c.DENOMINATORE_QUOTA_DEF, successioni_d.NUMERATORE_QUOTA, successioni_d.DENOMINATORE_QUOTA, "
			+ " successioni_b.CF_EREDE, successioni_b.DENOMINAZIONE "
			+ " from "
			+ " successioni_d, successioni_c, successioni_b "
			+ " where "
			+ " successioni_d.UFFICIO = successioni_c.UFFICIO "
			+ " and successioni_d.ANNO = successioni_c.ANNO and successioni_d.VOLUME = successioni_c.VOLUME "
			+ " and successioni_d.NUMERO =  successioni_c.NUMERO and successioni_d.SOTTONUMERO = successioni_c.SOTTONUMERO "
			+ " and successioni_d.COMUNE = successioni_c.COMUNE and successioni_d.PROGRESSIVO = successioni_c.PROGRESSIVO "
			+ " and successioni_d.PROGRESSIVO_IMMOBILE = successioni_c.PROGRESSIVO_IMMOBILE and successioni_d.UFFICIO = successioni_b.UFFICIO "
			+ " and successioni_d.ANNO = successioni_b.ANNO and successioni_d.VOLUME = successioni_b.VOLUME "
			+ " and successioni_d.NUMERO =  successioni_b.NUMERO and successioni_d.SOTTONUMERO = successioni_b.SOTTONUMERO "
			+ " and successioni_d.COMUNE = successioni_b.COMUNE and successioni_d.PROGRESSIVO = successioni_b.PROGRESSIVO "
			+ " and successioni_d.PROGRESSIVO_EREDE = successioni_b.PROGRESSIVO_EREDE "
			+ " @SQL_SUC_WHERE@ ";
		
	public String createQuerySuccessioniAByCriteria(RicercaSuccessioniDTO criteria){
		
		String hql = this.HQL_SUCA_BY_PK;
		
		String condSogg = "";
		condSogg = ( ( criteria.getCodFis() == null || criteria.getCodFis().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCA.cfDefunto = '"+ criteria.getCodFis().trim() + "' " );
		
		condSogg = ( ( criteria.getUfficio() == null || criteria.getUfficio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCA.id.ufficio = '"+ criteria.getUfficio().trim() + "' " );
		condSogg = ( ( criteria.getAnno() == null || criteria.getAnno().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCA.id.anno = '"+ criteria.getAnno().trim() + "' " );
		condSogg = ( ( criteria.getVolume() == null ) ? condSogg : addOperator(condSogg) +  " SUCA.id.volume = '"+ criteria.getVolume() + "' " );
		condSogg = ( ( criteria.getNumero() == null ) ? condSogg : addOperator(condSogg) +  " SUCA.id.numero = '"+ criteria.getNumero() + "' " );
		condSogg = ( ( criteria.getSottonumero() == null ) ? condSogg : addOperator(condSogg) +  " SUCA.id.sottonumero = '"+ criteria.getSottonumero() + "' " );
		condSogg = ( ( criteria.getComune() == null || criteria.getComune().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCA.id.comune = '"+ criteria.getComune().trim() + "' " );
		condSogg = ( ( criteria.getProgressivo() == null ) ? condSogg : addOperator(condSogg) +  " SUCA.id.progressivo = '"+ criteria.getProgressivo() + "' " );
		condSogg = ( ( criteria.getFornitura() == null ) ? condSogg : addOperator(condSogg) +  " SUCA.id.fornitura = '"+ criteria.getFornitura() + "' " );
		
		hql = hql.replace("@HQL_SUCA_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQuerySuccessioniCByCriteria(RicercaSuccessioniDTO criteria){
		
		String hql = this.HQL_SUCC_BY_PK;
		
		String condSogg = "";
		condSogg = ( ( criteria.getSezione() == null || criteria.getSezione().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCC.sezione = '"+ criteria.getSezione().trim() + "' " );
		condSogg = ( ( criteria.getFoglio() == null || criteria.getFoglio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LPAD(SUCC.foglio, 4, '0') = LPAD('"+ criteria.getFoglio().trim() + "', 4, '0') " );
		condSogg = ( ( criteria.getParticella() == null || criteria.getParticella().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LPAD(SUCC.particella1, 5, '0') = LPAD('"+ criteria.getParticella().trim() + "', 5, '0') " );
		condSogg = ( ( criteria.getSubalterno() == null || criteria.getSubalterno().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LPAD(SUCC.subalterno1, 4, '0') = LPAD('"+ criteria.getSubalterno().trim() + "', 4, '0') " );
		
		hql = hql.replace("@HQL_SUCC_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQuerySuccessioniBByCriteria(RicercaSuccessioniDTO criteria){
		
		String hql = this.HQL_SUCB_BY_PK;
		
		String condSogg = "";
		condSogg = ( ( criteria.getCodFis() == null || criteria.getCodFis().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCB.cfErede = '"+ criteria.getCodFis().trim() + "' " );
		
		condSogg = ( ( criteria.getUfficio() == null || criteria.getUfficio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCB.id.ufficio = '"+ criteria.getUfficio().trim() + "' " );
		condSogg = ( ( criteria.getAnno() == null || criteria.getAnno().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCB.id.anno = '"+ criteria.getAnno().trim() + "' " );
		condSogg = ( ( criteria.getVolume() == null ) ? condSogg : addOperator(condSogg) +  " SUCB.id.volume = '"+ criteria.getVolume() + "' " );
		condSogg = ( ( criteria.getNumero() == null ) ? condSogg : addOperator(condSogg) +  " SUCB.id.numero = '"+ criteria.getNumero() + "' " );
		condSogg = ( ( criteria.getSottonumero() == null ) ? condSogg : addOperator(condSogg) +  " SUCB.id.sottonumero = '"+ criteria.getSottonumero() + "' " );
		condSogg = ( ( criteria.getComune() == null || criteria.getComune().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCB.id.comune = '"+ criteria.getComune().trim() + "' " );
		condSogg = ( ( criteria.getProgressivo() == null ) ? condSogg : addOperator(condSogg) +  " SUCB.id.progressivo = '"+ criteria.getProgressivo() + "' " );
		condSogg = ( ( criteria.getFornitura() == null ) ? condSogg : addOperator(condSogg) +  " SUCB.id.fornitura = '"+ criteria.getFornitura() + "' " );
		
		hql = hql.replace("@HQL_SUCB_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQuerySuccessioniDByCriteria(RicercaSuccessioniDTO criteria){
		
		String hql = this.HQL_SUCD_BY_PK;
		
		String condSogg = "";
		
		condSogg = ( ( criteria.getUfficio() == null || criteria.getUfficio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCD.id.ufficio = '"+ criteria.getUfficio().trim() + "' " );
		condSogg = ( ( criteria.getAnno() == null || criteria.getAnno().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCD.id.anno = '"+ criteria.getAnno().trim() + "' " );
		condSogg = ( ( criteria.getVolume() == null ) ? condSogg : addOperator(condSogg) +  " SUCD.id.volume = '"+ criteria.getVolume() + "' " );
		condSogg = ( ( criteria.getNumero() == null ) ? condSogg : addOperator(condSogg) +  " SUCD.id.numero = '"+ criteria.getNumero() + "' " );
		condSogg = ( ( criteria.getSottonumero() == null ) ? condSogg : addOperator(condSogg) +  " SUCD.id.sottonumero = '"+ criteria.getSottonumero() + "' " );
		condSogg = ( ( criteria.getComune() == null || criteria.getComune().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " SUCD.id.comune = '"+ criteria.getComune().trim() + "' " );
		condSogg = ( ( criteria.getProgressivo() == null ) ? condSogg : addOperator(condSogg) +  " SUCD.id.progressivo = '"+ criteria.getProgressivo() + "' " );
		condSogg = ( ( criteria.getFornitura() == null ) ? condSogg : addOperator(condSogg) +  " SUCD.id.fornitura = '"+ criteria.getFornitura() + "' " );
		
		hql = hql.replace("@HQL_SUCD_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQuerySuccessioniByParams(RicercaSuccessioniDTO criteria){
		
		String sql = this.SQL_EREDITA_BY_PARAMS;
		
		String condSogg = "";
		
		condSogg = ( ( criteria.getUfficio() == null || criteria.getUfficio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " successioni_d.ufficio = '"+ criteria.getUfficio().trim() + "' " );
		condSogg = ( ( criteria.getAnno() == null || criteria.getAnno().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " successioni_d.anno = '"+ criteria.getAnno().trim() + "' " );
		condSogg = ( ( criteria.getVolume() == null ) ? condSogg : addOperator(condSogg) +  " successioni_d.volume = '"+ criteria.getVolume() + "' " );
		condSogg = ( ( criteria.getNumero() == null ) ? condSogg : addOperator(condSogg) +  " successioni_d.numero = '"+ criteria.getNumero() + "' " );
		condSogg = ( ( criteria.getSottonumero() == null ) ? condSogg : addOperator(condSogg) +  " successioni_d.sottonumero = '"+ criteria.getSottonumero() + "' " );
		condSogg = ( ( criteria.getComune() == null || criteria.getComune().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " successioni_d.comune = '"+ criteria.getComune().trim() + "' " );
		condSogg = ( ( criteria.getProgressivo() == null ) ? condSogg : addOperator(condSogg) +  " successioni_d.progressivo = '"+ criteria.getProgressivo() + "' " );
		condSogg = ( ( criteria.getFornitura() == null ) ? condSogg : addOperator(condSogg) +  " successioni_d.fornitura = '"+ criteria.getFornitura() + "' " );
		
		sql = sql.replace("@SQL_SUC_WHERE@", condSogg);
		
		return sql;
	}//-------------------------------------------------------------------------
	
	protected String addOperator(String criteria) {
    	if (criteria == null)
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }//-------------------------------------------------------------------------
	
}



