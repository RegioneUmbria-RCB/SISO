package it.webred.ct.support.validation;

import it.webred.ct.support.audit.AmAuditDTO;
import it.webred.ct.support.audit.AuditDBWriter;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

public class ValidationStateless {
	protected Logger logger = Logger.getLogger("audit.log");
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
        
        //logger.debug("ValidationStateless:validateMethodAccess ["+methodName+"]["+className+"]["+userName+"] INIT");
		
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
			
			// simulo l'autenticazione in modo che possa essere creata una riga in am_audit con l'id della sessione
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
				
				AmAuditDTO dto = new AmAuditDTO();
				dto.setMethodName(methodName);
				dto.setClassName(className);
				dto.setUserId(userName);
				dto.setEnteId(ente);
				dto.setSessionId(sessionId);
				dto.setDescrizione("ERROR:ACCESSO ANONIMO NON CONSENTITO");
			
				AuditDBWriter.write(dto);
				throw new Exception("ValidationStateless:validateMethodAccess: Accesso anonimo non consentito al metodo " +declaringClass +"."+ methodName); 
				
			}
		}	

		if (fakeSessionID!=null)  {
			userName = userNameLogin;
			sessionId = fakeSessionID;
			
		}
		
		AuditSaltaValidazioneSessionID saltaValidazione = method.getAnnotation(AuditSaltaValidazioneSessionID.class);
		if (saltaValidazione == null || !saltaValidazione.enabled()) {
			logger.debug("ValidationStateless:validateMethodAccess VALIDAZIONE SESSIONID in corso...");
			esegui = ValidationDBReader.validateMethod(declaringClass.getName(),methodName, arguments, userName, sessionId, ente , isTokenSessione);
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
				AuditDBWriter.auditMethod(ente, userName, sessionId , declaringClass.getName(), methodName, arguments, returnVal, e);
			}
			//logger.debug("ValidationStateless:validateMethodAccess ["+methodName+"]["+className+"]["+userName+"] --> Chiamata effettuata"); 
		} else {
			
			AmAuditDTO dto = new AmAuditDTO();
			dto.setMethodName(methodName);
			dto.setClassName(className);
			dto.setUserId(userName);
			dto.setEnteId(ente);
			dto.setSessionId(sessionId);
			dto.setDescrizione("WARNING:CHIAMATA NON PERMESSA");
			
			AuditDBWriter.write(dto);
			throw new Exception("ValidationStateless:validateMethodAccess ["+methodName+"]["+className+"]["+userName+"] --> Chiamata non consentita"); 
		}

		return returnVal;
	}
}
