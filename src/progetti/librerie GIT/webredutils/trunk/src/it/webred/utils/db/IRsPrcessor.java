package it.webred.utils.db;

import java.sql.ResultSet;

public abstract class IRsPrcessor {

	protected Class pojoClass = null;

	public IRsPrcessor(Class pojoClass) {
		this.pojoClass = pojoClass;
	}
	public abstract Object toBean(ResultSet rs) throws Exception;
	
}
