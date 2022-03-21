package it.webred.cs.csa.web.manbean.scheda;

import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

public class SchedaUtils extends CsUiCompBaseBean {

	public boolean isDisabilitaForm(){
		SchedaBean bean = (SchedaBean)getBeanReference("schedaBean");
		return !bean.isAttivaSalvataggio();
	}
	
}
