package it.webred.ct.support.audit;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;
 

public class AuditStateless{
 
    @Resource
    SessionContext sctx;
    
    
	protected Logger logger = Logger.getLogger("CTservice_log");

	
	public Object auditMethodAccess(InvocationContext ctx) throws Throwable {
	  
		Object returnVal = null;
		Object arguments[] = null; 
		arguments = ctx.getParameters();
		Method method = ctx.getMethod();
		String methodName = method.getName();
		String userName = sctx.getCallerPrincipal().getName();
		
		try {
			
			AuditDBWriter dbWriter = new AuditDBWriter();
			String e = null;
			
			try {
				returnVal = ctx.proceed();
			} catch (Exception ex) {
				e = ex.getMessage();
				logger.error("errore chiamata proceed", ex);
				throw ex;
			} finally {
				
				arguments = ctx.getParameters();
				Class declaringClass = ctx.getMethod().getDeclaringClass();
	
				dbWriter.auditMethod( null, userName, null, declaringClass.getName(), methodName, arguments, returnVal, e);

			}
		} catch (Exception e) {
			logger.error("errore auditMethodAccess:", e);
			e.printStackTrace();
		}
		return returnVal;
	}
}
