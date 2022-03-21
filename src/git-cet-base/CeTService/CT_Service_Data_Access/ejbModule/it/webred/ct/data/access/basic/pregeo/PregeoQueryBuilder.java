package it.webred.ct.data.access.basic.pregeo;

import it.webred.ct.data.access.basic.pregeo.dto.RicercaPregeoDTO;

public class PregeoQueryBuilder {
	
	/*
	 * SELECT i 
				FROM PregeoInfo i
				WHERE LPAD(substr(i.foglio,0,3),4,'0') = LPAD(:foglio,4,'0')
				  AND LPAD(i.particella, 5, '0')  = LPAD(:particella, 5, '0') 
	 */
	
	private final String HQL_PREGEO_BY_COORD = "FROM PregeoInfo INFO "
			+ " where 1=1 @PREGEO_BY_COORD_WHERE@ "
			+ " order by INFO.foglio, INFO.particella ";
	
	public String createQueryPregeoByCriteria(RicercaPregeoDTO criteria){
		
		String hql = this.HQL_PREGEO_BY_COORD;
		
		String condSogg = "";
		condSogg = ( ( criteria.getFoglio() == null || criteria.getFoglio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LPAD(SUBSTR(INFO.foglio, 0, 3), 4, '0') = LPAD('"+ criteria.getFoglio().trim() + "', 4, '0') " );
		condSogg = ( ( criteria.getParticella() == null || criteria.getParticella().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LPAD(INFO.particella, 5, '0') = LPAD('"+ criteria.getParticella().trim() + "', 5, '0') " );
		
		hql = hql.replace("@PREGEO_BY_COORD_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	protected String addOperator(String criteria) {
    	if (criteria == null)
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }//-------------------------------------------------------------------------
	
}



