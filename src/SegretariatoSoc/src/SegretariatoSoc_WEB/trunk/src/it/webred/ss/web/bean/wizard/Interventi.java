package it.webred.ss.web.bean.wizard;

import it.webred.ss.data.model.SsSchedaInterventi;
import it.webred.ss.data.model.SsSchedaPrivacy;

import java.util.ArrayList;
import java.util.List;

public class Interventi {
	
	private Long id;
	private List<String> interventi;
	private String altro;
	
	public List<String> getInterventi() {
		return interventi;
	}
	public void setInterventi(List<String> interventi) {
		this.interventi = interventi;
	}
	public String getAltro() {
		return altro;
	}
	public void setAltro(String altro) {
		this.altro = altro;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	public void fillModel(SsSchedaInterventi model) {
		model.setId(id);
		model.setAltro(altro);
	}
	public void initFromModel(SsSchedaInterventi intervento) {
		if(intervento != null){
			id = intervento.getId();
			altro = intervento.getAltro();
			interventi = new ArrayList<String>();
		}
		
	}
	
	
}
