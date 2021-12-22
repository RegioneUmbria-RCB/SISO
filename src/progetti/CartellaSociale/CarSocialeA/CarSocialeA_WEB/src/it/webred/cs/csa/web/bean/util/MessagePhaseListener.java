package it.webred.cs.csa.web.bean.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.lang.StringUtils;

public class MessagePhaseListener implements PhaseListener {
	
	private static final long serialVersionUID = 1L;

	@Override
    public void afterPhase(PhaseEvent phaseEvent) {
      
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
    	List<FacesMessage> lst = phaseEvent.getFacesContext().getMessageList();
        for (int i=0; i< lst.size(); i++) {
        	FacesMessage message = lst.get(i);
        	if(StringUtils.isBlank(message.getDetail()) ||
                    (StringUtils.isNotBlank(message.getSummary()) && message.getSummary().equals(message.getDetail()))){
                phaseEvent.getFacesContext().getMessageList().get(i).setDetail("");
            }
        }
        List<FacesMessage> lste = phaseEvent.getFacesContext().getMessageList();
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}