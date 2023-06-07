package it.webred.ss.web.bean.wizard;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.ToggleEvent;

import it.webred.ss.ejb.dto.NotaDTO;

public class DiarioSociale {
	
	
	private List<NotaDTO> note;
	
	public DiarioSociale(){
		note = new ArrayList<NotaDTO>();
	}
	
	public DiarioSociale(List<NotaDTO> note){
		populateNote(note);
	}
	
	public List<NotaDTO> getNote(){
		return note;
	}

	public void populateNote(List<NotaDTO> note){
		for(NotaDTO n: note)
			this.note.add(n);
	}
	
	public void onRowToggle(ToggleEvent event) {  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row State " + event.getVisibility(), "");  
          
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
}
