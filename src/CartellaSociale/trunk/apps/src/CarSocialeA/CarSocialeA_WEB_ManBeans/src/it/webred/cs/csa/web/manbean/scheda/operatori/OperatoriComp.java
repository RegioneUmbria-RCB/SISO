package it.webred.cs.csa.web.manbean.scheda.operatori;

import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaCompUtils;

public class OperatoriComp extends SchedaValiditaCompUtils {
	
	private String settore;
	private Boolean responsabile=false;
	private String gestisciFascicolo;
	private String contatti;
	private boolean operTipo2;
	
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}

	public boolean isOperTipo2() {
		return operTipo2;
	}
	public void setOperTipo2(boolean operTipo2) {
		this.operTipo2 = operTipo2;
	}
	public Boolean getResponsabile() {
		return responsabile;
	}
	public void setResponsabile(Boolean responsabile) {
		this.responsabile = responsabile;
	}
	public String getContatti() {
		return contatti;
	}
	public void setContatti(String contatti) {
		this.contatti = contatti;
	}
	public String getGestisciFascicolo() {
		return gestisciFascicolo;
	}
	public void setGestisciFascicolo(String gestisciFascicolo) {
		this.gestisciFascicolo = gestisciFascicolo;
	}
	
}
