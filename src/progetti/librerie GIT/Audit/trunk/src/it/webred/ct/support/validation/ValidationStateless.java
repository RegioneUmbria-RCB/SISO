package it.webred.ct.support.validation;




import it.webred.ct.support.audit.AuditDBWriter;
import it.webred.ct.support.audit.AuditStateless;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.CryptoPrimitive;
import java.util.UUID;

import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ejb.SessionContext;

public class ValidationStateless {

	/* 
	https://github.com/PiotrNowicki/ejb-interceptors-spike/blob/master/src/main/java/com/piotrnowicki/interceptors/control/SpikeInterceptor.java
		*/
	
    @Resource
    SessionContext sctx;
    
	@AroundInvoke
	public Object validateMethodAccess(InvocationContext ctx) throws Throwable {

//		SecurityContextHol.getContext().getAuthentication().getPrincipal()

		
		Object returnVal = null;
		Object arguments[] = null; 
		arguments = ctx.getParameters();
		Method method = ctx.getMethod();
		String methodName = method.getName();
		String userName = sctx.getCallerPrincipal().getName();
		String fakeSessionID =null;
		String userNameLogin =null;
		String className = ctx.getTarget().getClass().getName();
        String originName = Thread.currentThread().getName();
		
		Class declaringClass = ctx.getMethod().getDeclaringClass();
		boolean esegui = true;
		boolean classeLogin = false;
		if ("it.webred.amprofiler.ejb.perm.LoginBean".equals(declaringClass.getName()))
			classeLogin = true;
			
		String sessionId = null;
		String ente = null;
		boolean isTokenSessione = false;
		
		for (int i = 0; i < arguments.length; i++) {
			Object arg = arguments[i];
			
			// simulo l'autenticazione in modo che possa essere creata una riga in am_audit con lid della sessione
			if (classeLogin && methodName.equals("getToken") && i==0 ) {
				userNameLogin = arg.toString();
				fakeSessionID =  UUID.randomUUID().toString();
			}

			if (arg instanceof CeTBaseObject) {
				CeTBaseObject bo = (CeTBaseObject) arg;
				sessionId = bo.getSessionId();
				ente = bo.getEnteId();
				isTokenSessione = true;
			}
			if (arg instanceof CeTToken) {
				CeTToken cetT = (CeTToken) arg;
				sessionId = cetT.getSessionId();
				ente = cetT.getEnte();
				isTokenSessione = true;
			}
		}

		AuditConsentiAccessoAnonimo consentiAccessoAnonimo = method.getAnnotation(AuditConsentiAccessoAnonimo.class);
		if (consentiAccessoAnonimo == null || !consentiAccessoAnonimo.enabled()) {
			if ("anonymous".equals(userName) && sessionId==null) {
				AuditDBWriter dbWriter = new AuditDBWriter();
				dbWriter.write(methodName, className, userName, null, ente, null, null, sessionId, "ERROR:ACCESSO ANONIMO NON CONSENTITO", null, null, null, null);
				throw new Exception("Accesso anonimo non consentito al metodo " +declaringClass +"."+ methodName); 
				
			}
		}	

		if (fakeSessionID!=null)  {
			userName = userNameLogin;
			sessionId = fakeSessionID;
			
		}
		
		AuditSaltaValidazioneSessionID saltaValidazione = method.getAnnotation(AuditSaltaValidazioneSessionID.class);
		if (saltaValidazione == null || !saltaValidazione.enabled()) {
			ValidationDBReader dbReader = new ValidationDBReader();
			esegui = dbReader.validateMethod(declaringClass.getName(),
					methodName, arguments, userName, sessionId, ente , isTokenSessione);
		}
		

        
        
		String e = null;
		

        if (esegui) {
			try {
	                //String tracingName = userName + " " + originName;
	                //Thread.currentThread().setName(tracingName);
	                returnVal = ctx.proceed();
	                if (fakeSessionID!=null) {
	                	CeTToken t = (CeTToken) returnVal;
	                	t.setSessionId(fakeSessionID);
	                	returnVal = t;
	                }
			} catch (Exception ex) {
				throw ex;
			}
			finally{
				AuditDBWriter dbWriter = new AuditDBWriter();
				dbWriter.auditMethod(ente, userName, sessionId , declaringClass.getName(), methodName, arguments, returnVal, e);
			}

		} else {
			AuditDBWriter dbWriter = new AuditDBWriter();
			dbWriter.write(methodName, className, userName, null, ente, null, null, sessionId, "WARNING:CHIAMATA NON PERMESSA", null, null, null, null);
			throw new Exception("Chiamata non consentita all'utente"); 
		}
        
		

		return returnVal;
	}
	
	 

	
}
