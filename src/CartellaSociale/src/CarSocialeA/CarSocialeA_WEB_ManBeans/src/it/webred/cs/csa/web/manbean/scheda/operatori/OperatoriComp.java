package it.webred.cs.csa.web.manbean.scheda.operatori;

import it.webred.cs.jsf.bean.ValiditaCompBaseBean;

public class OperatoriComp extends ValiditaCompBaseBean {
	
	private String settore;
	private Boolean responsabile=false;
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


	
}
