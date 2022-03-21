package it.webred.ct.service.indice.web.common;

import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ServiceLocator {

	private HashMap<String, Object> service = new HashMap();

	private static ServiceLocator me;
	private InitialContext ctx;

	private ServiceLocator() {
		// Set Context Reference
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	public static ServiceLocator getInstance() {
		if (me == null)
			me = new ServiceLocator();

		return me;
	}

	public Object getReference(String name) {
		/*
		 * Object o = service.get( name); if (o != null) return o;
		 */
		Object o = null;

		try {
			o = ctx.lookup(name);
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}

		// service.put(name, o);
		return o;
	}
}
