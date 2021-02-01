package it.webred.ss.web.bean.wizard;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.ToggleEvent;

public class DiarioSociale {
	
	
	private List<Nota> note;
	
	public DiarioSociale(){
		note = new ArrayList<Nota>();
	}
	
	public DiarioSociale(List<Nota> note){
		populateNote(note);
	}
	
	public List<Nota> getNote(){
		return note;
	}

	public void populateNote(List<Nota> note){
		for(Nota n: note)
			this.note.add(n);
	}
	
	public void onRowToggle(ToggleEvent event) {  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row State " + event.getVisibility(), "");  
          
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
}
