package it.webred.cs.csa.web.bean.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

@SuppressWarnings("serial")
public class MyLogPhaseListener implements PhaseListener {
	private static Logger phaseLogger = Logger.getLogger("carsociale-phaselogger.log");	// logger dedicato
	
    public long startTime;
    
    
    private void log(Object... phaseParameters) {
    	String msg = "";
    	
    	for (Object object : phaseParameters) {
			msg += object.toString() + ";";
		}
    	
    	phaseLogger.debug(msg);
    }
    
    
    public void afterPhase(PhaseEvent event) {
    	
    	long endTime = System.nanoTime();
        long diffMs = (long) ((endTime - startTime) * 0.000001);
        
        String sessionId = "UNKNOWN";	// fallback
        try {
        	HttpSession session = (HttpSession) event.getFacesContext().getExternalContext().getSession(false);
        	sessionId = session.getId();
        }
        catch (Exception e) {
        	
        }
        
        PhaseId currentPhase = event.getPhaseId();
        
		String viewId = "no view";
		try {
			viewId = event.getFacesContext().getViewRoot().getViewId();
		}
		catch (Exception e) {
			
		}
		
		String reqParMap = "no reqParMap";
		try {
			reqParMap = event.getFacesContext().getExternalContext().getRequestParameterMap().toString();
		}
		catch (Exception e) {
			
		}
		
		log(sessionId, currentPhase, viewId, diffMs, reqParMap);
		
        
//        if (currentPhase == PhaseId.RENDER_RESPONSE) {
////            long endTime = System.nanoTime();
////            long diffMs = (long) ((endTime - startTime) * 0.000001);
//            
//            UIViewRoot viewRoot = event.getFacesContext().getViewRoot();
//			String viewId = viewRoot.getViewId();
//            
//            // proviamo a vedere che c'è
////            String sessionMap = event.getFacesContext().getExternalContext().getSessionMap().toString();
//            String reqParMap = event.getFacesContext().getExternalContext().getRequestParameterMap().toString();
//            
//            log(sessionId, endTime, currentPhase, viewId, diffMs, reqParMap);
//        }
//        else {
//        	log(sessionId, endTime, currentPhase, "no view", diffMs, "no ParMap");
//        }
        
//        log("Executed Phase " + event.getPhaseId());
        
    }
 
    public void beforePhase(PhaseEvent event) {
        if (event.getPhaseId() == PhaseId.RESTORE_VIEW) {
            startTime = System.nanoTime();
        }
    }
 
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}