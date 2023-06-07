package it.webred.cs.json.provvedimentiMinori.ver1.tabs;

import it.webred.cs.json.dto.JsonBaseBean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrescrizioniSocioEducativeBean extends JsonBaseBean{
	
	private String[] selSostegno;
	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String[] getSelSostegno() {
		return selSostegno;
	}

	public void setSelSostegno(String[] selSostegno) {
		this.selSostegno = selSostegno;
	}

	@Override
	public List<String> checkObbligatorieta() {
		// TODO Auto-generated method stub
		return null;
	}

}
