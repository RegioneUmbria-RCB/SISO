package it.webred.ct.support.datarouter;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

import static it.webred.ct.support.datarouter.ContextKey.*;
import static it.webred.ct.support.datarouter.context.DataContextHolder.*;

/**
 * Questa classe rappresenta l'inteceptor per permettere
 * la scelta del datasource
 * 
 *  @author Francesco Azzola
 * */

public class DataRouterInterceptor {
	
	protected Logger logger = Logger.getLogger("ctservice.log");
	
	/**
	 * EJB 3.0 Interceptor. This is a default interceptor 
	 * configured in ejb-jar.xml
	 **/
	
	@AroundInvoke
	public Object selectDataSource(InvocationContext ctx) throws Exception {
		logger.debug("---- DataRouterInterceptor ----");
		boolean foundRoutingData = false;
		int i = -1;
		
		Object[] args = ctx.getParameters();
		
		if (args == null || args.length < 1)
			return ctx.proceed();

		for (i=0; i < args.length; i++)
			if (args[i] instanceof CeTBaseObject) {
				foundRoutingData = true;
				break;
			}
		
		if (!foundRoutingData)
			return ctx.proceed();
		
		CeTBaseObject cetObj = (CeTBaseObject) args[i];
		
		String enteId = cetObj.getEnteId();
		String userId = cetObj.getUserId();
		String sessionId = cetObj.getSessionId();
		
		logger.debug("Ente ["+enteId+"] - UserId ["+userId+"] - SessionId ["+sessionId+"]");
		
		UserContext userCtx = new UserContextImpl();
		userCtx.setEnteId(enteId);
		userCtx.setUserId(userId);
		userCtx.setSessionId(sessionId);
		
		
		put(USER_CONTEXT.name(), userCtx);

		return ctx.proceed();
	}


}
