package it.webred.ss.web.bean.wizard;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;


@ViewScoped
public class InterventiView implements Serializable {
     
	private static final long serialVersionUID = -5912076662560082485L;
	private List<InterventoEconomico> interventi;
    
    public List<InterventoEconomico> getInterventi() {
        return interventi;
    }
 
    public void setInterventi(List<InterventoEconomico> list) {
        this.interventi = list;
    }
}