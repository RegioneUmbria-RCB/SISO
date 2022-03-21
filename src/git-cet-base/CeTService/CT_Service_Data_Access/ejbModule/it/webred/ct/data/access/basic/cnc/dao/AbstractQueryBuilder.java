package it.webred.ct.data.access.basic.cnc.dao;

public abstract class AbstractQueryBuilder {

	public abstract String createQuery(boolean isCount);
		
	protected String addOperator(String criteria) {
    	if (criteria == null || criteria.equals(""))
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }
}
