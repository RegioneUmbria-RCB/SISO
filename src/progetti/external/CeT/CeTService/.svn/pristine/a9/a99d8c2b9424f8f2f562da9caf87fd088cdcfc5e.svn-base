package it.webred.ct.data.access.basic.indice;

public abstract class AbstractQueryBuilder {

	public abstract String createQueryUnico(boolean isCount);
	
	public abstract String createQueryTotale(boolean isCount, boolean addUnico);
	
	public abstract String createQueryEntiByUnico(String unicoId);

		
	protected String addOperator(String criteria) {
    	if (criteria == null || criteria.equals(""))
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }
}
