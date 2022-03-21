package it.webred.ct.support.audit;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class AuditDBLoggerBean
 */
@Stateless
public class AuditDBLoggerBean implements AuditDBLogger, AuditLog {

 @PersistenceContext(unitName="auditPers")
 private EntityManager manager;

public void logEvent(AuditEvent event) {
	logger.debug("AuditDBLoggerBean--logEvent: ENTE [" + event.getEnteId()+ "];USER ["+event.getUserId() + "]; METODO [" + event.getMethodName()+"] ");
	try {
	  AuditDBEvent dbEvent = new AuditDBEvent();
	  dbEvent.setEnteId(event.getEnteId());
	  dbEvent.setDataIns(new Date());
	  dbEvent.setException(event.getException());
	  dbEvent.setMethodName(event.getMethodName());
	  dbEvent.setClassName(event.getClassName());
	  if(event.getResult().length() > 4000)
		  dbEvent.setResult(event.getResult().substring(0, 3999));
	  else dbEvent.setResult(event.getResult());
	  dbEvent.setUserId(event.getUserId());
	  if(event.getArgs().length() > 4000)
		  dbEvent.setArgs(event.getArgs().substring(0, 3999));
	  else dbEvent.setArgs(event.getArgs());
	  manager.persist(dbEvent);
	  
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
}
 
 

}
