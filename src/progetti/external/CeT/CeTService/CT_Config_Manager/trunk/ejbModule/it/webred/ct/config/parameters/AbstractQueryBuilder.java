package it.webred.ct.config.parameters;

public abstract class AbstractQueryBuilder {

	protected String addOperator(String criteria) {
		return criteria += " AND ";
	}
}
