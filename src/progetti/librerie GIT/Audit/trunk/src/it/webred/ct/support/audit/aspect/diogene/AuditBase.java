package it.webred.ct.support.audit.aspect.diogene;

import it.webred.ct.support.audit.AuditDBWriter;
import java.lang.reflect.Field;
import org.aspectj.lang.JoinPoint;

public class AuditBase {

	static final void println(String s) {
		System.out.println("_____###__AUDIT LOG: " + s);
	}
	
	static protected void auditDiogeneMethod(JoinPoint jp, Object returnVal, String exception) {
		
		AuditDBWriter dbWriter = new AuditDBWriter();
		Object arguments[] = null;
			
		arguments = jp.getArgs();
		String methodName = jp.getSignature().getName();
		Class cl = jp.getSignature().getDeclaringType();
		String declaringClass = cl.getName();
		
		Field privateField;
		try {
			privateField = cl.getSuperclass().getDeclaredField("envUtente");
			privateField.setAccessible(true);
			Object envUtente = privateField.get(jp.getTarget());
		
			privateField = envUtente.getClass().getDeclaredField("utente");
			privateField.setAccessible(true);
			Object cetUser = privateField.get(envUtente);
			
			privateField = cetUser.getClass().getDeclaredField("username");
			privateField.setAccessible(true);
			String user = (String) privateField.get(cetUser);
			
			privateField = cetUser.getClass().getDeclaredField("currentEnte");
			privateField.setAccessible(true);
			String ente = (String) privateField.get(cetUser);
			
			privateField = cetUser.getClass().getDeclaredField("sessionId");
			privateField.setAccessible(true);
			String sessionId = (String) privateField.get(cetUser);
	
			dbWriter.auditMethod(ente ,user, sessionId, declaringClass, methodName, arguments, returnVal, null);
		} catch (NoSuchFieldException e1) {
			println("Errore NoSuchFieldException nella creazione del log nella Classe: " + declaringClass + " e Metodo: " + methodName 
					+ ". Field non trovato");
			e1.printStackTrace();
		} catch (Throwable e) {
			println("Errore Generico nella creazione del log nella Classe: " + declaringClass + " e Metodo: " + methodName);
			e.printStackTrace();
		}
		
	}
	
}
