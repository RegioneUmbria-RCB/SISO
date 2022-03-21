package it.webred.ct.support.audit;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;
 

public class AuditStateless{
 
    @Resource
    SessionContext sctx;
    
    
	protected Logger logger = Logger.getLogger("audit.log");

	
	public Object auditMethodAccess(InvocationContext ctx) throws Throwable {
	  
		Object returnVal = null;
		Object arguments[] = null; 
		arguments = ctx.getParameters();
		Method method = ctx.getMethod();
		String methodName = method.getName();
		String userName = sctx.getCallerPrincipal().getName();
		 logger.debug("AuditStateless:auditMethodAccess ["+methodName+"]["+userName+"] INIT");
		try {
			
			String e = null;
			
			try {
				returnVal = ctx.proceed();
			} catch (Exception ex) {
				e = ex.getMessage();
				logger.error("AuditStateless:auditMethodAccess errore chiamata proceed", ex);
				throw ex;
			} finally {
				
				arguments = ctx.getParameters();
				Class declaringClass = ctx.getMethod().getDeclaringClass();
	
				AuditDBWriter.auditMethod( null, userName, null, declaringClass.getName(), methodName, arguments, returnVal, e);

			}
		} catch (Exception e) {
			logger.error("AuditStateless:auditMethodAccess errore auditMethodAccess:", e);
			e.printStackTrace();
		}
		return returnVal;
	}
}
