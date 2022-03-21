package it.webred.cs.csa.web.manbean.scheda;

import it.webred.cs.jsf.bean.ValiditaCompBaseBean;

public class SchedaValiditaCompUtils extends ValiditaCompBaseBean {

	public boolean isDisabilitaForm(){
		SchedaBean bean = (SchedaBean)getBeanReference("schedaBean");
		return !bean.isAttivaSalvataggio();
	}
	
}
