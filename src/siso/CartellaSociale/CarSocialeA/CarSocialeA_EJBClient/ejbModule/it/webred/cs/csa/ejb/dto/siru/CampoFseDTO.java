package it.webred.cs.csa.ejb.dto.siru;

import java.io.Serializable;

public class CampoFseDTO implements Serializable {

	private String label;
	private boolean abilitato;
	private boolean obbligatorio;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isAbilitato() {
		return abilitato;
	}
	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}
	public boolean isObbligatorio() {
		return obbligatorio;
	}
	public void setObbligatorio(boolean obbligatorio) {
		this.obbligatorio = obbligatorio;
	}
	
	public String getLabelReq(){
		String s = this.label!=null ? this.label : "";
		s+= this.obbligatorio ? " *" : "";
		return s;
	}
	
	public boolean isValida(){
		return this.isAbilitato() && this.isObbligatorio();
	}
}
