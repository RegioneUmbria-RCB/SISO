package it.webred.cs.csa.web.bean;

import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ComuneTestBean extends CsUiCompBaseBean {

	ComuneTestMan comune1 = new ComuneTestMan();
	ComuneTestMan comune2 = new ComuneTestMan();
	
	@PostConstruct
	public void init() {
		comune1.setWidgetVar("comune1");
		comune2.setWidgetVar("comune2");
		
		comune1.setTipoComune("tipo 1");
		comune2.setTipoComune("tipo 2");
	}

	public ComuneTestMan getComune1() {
		return comune1;
	}

	public void setComune1(ComuneTestMan comune1) {
		this.comune1 = comune1;
	}

	public ComuneTestMan getComune2() {
		return comune2;
	}

	public void setComune2(ComuneTestMan comune2) {
		this.comune2 = comune2;
	}
	
	
}
