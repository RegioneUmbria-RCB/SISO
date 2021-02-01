package it.webred.ct.data.access.basic.locazioni;

import it.webred.ct.data.access.basic.locazioni.dto.RicercaLocazioniDTO;

public class LocazioniQueryBuilder {
	
	private final String HQL_LOCA_BY_FPS = "FROM LocazioniA LOCA, LocazioniI LOCI "
			+ " where LOCA.ufficio = LOCI.ufficio AND LOCA.anno = LOCI.anno AND LOCA.serie = LOCI.serie AND LOCA.numero = LOCI.numero @HQL_LOC_AI_WHERE@ "
			+ " order by LOCI.sezUrbana, LOCI.foglio, LOCI.particella, LOCI.subalterno ";
	
	private final String HQL_LOCA_BY_CFeCAUSA = "FROM LocazioniA LOCA, LocazioniB LOCB "
			+ " where LOCA.ufficio = LOCB.ufficio AND LOCA.anno = LOCB.anno AND LOCA.serie = LOCB.serie AND LOCA.numero = LOCB.numero @HQL_LOC_AB_WHERE@ "
			+ " order by LOCA.dataInizio, LOCA.dataFine, LOCA.dataStipula ";
	
		
	public String createQueryLocazioniByCriteria(RicercaLocazioniDTO criteria){
		
		String hql = this.HQL_LOCA_BY_FPS;
		
		String condSogg = "";
		condSogg = ( ( criteria.getSezione() == null || criteria.getSezione().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LOCI.sezUrbana = '"+ criteria.getSezione().trim() + "' " );
		condSogg = ( ( criteria.getFoglio() == null || criteria.getFoglio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LOCI.foglio = '"+ criteria.getFoglio().trim() + "' " );
		condSogg = ( ( criteria.getParticella() == null || criteria.getParticella().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LOCI.particella = '"+ criteria.getParticella().trim() + "' " );
		condSogg = ( ( criteria.getSubalterno() == null || criteria.getSubalterno().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LOCI.subalterno = '"+ criteria.getSubalterno().trim() + "' " );
		
		hql = hql.replace("@HQL_LOC_AI_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQueryLocazioniByParams(RicercaLocazioniDTO criteria){
		
		String hql = this.HQL_LOCA_BY_CFeCAUSA;
		
		String condSogg = "";
		condSogg = ( ( criteria.getCodFis() == null || criteria.getCodFis().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LOCB.codicefiscale = '"+ criteria.getCodFis().trim() + "' " );
		condSogg = ( ( criteria.getTipoCoinvolgimento() == null || criteria.getTipoCoinvolgimento().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LOCB.tipoSoggetto = '"+ criteria.getTipoCoinvolgimento().trim() + "' " );
		
		hql = hql.replace("@HQL_LOC_AB_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	protected String addOperator(String criteria) {
    	if (criteria == null)
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }//-------------------------------------------------------------------------
	
}

