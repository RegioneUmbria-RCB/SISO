package it.webred.cs.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class DatiChiBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String periodoChiusura;
	private String dataEffettivaChiusura;
	private String motiviChiusura;
	
	
	public String getPeriodoChiusura() {
		return periodoChiusura;
	}

	public void setPeriodoChiusura(String periodoChiusura) {
		this.periodoChiusura = periodoChiusura;
	}

	public String getDataEffettivaChiusura() {
		return dataEffettivaChiusura;
	}

	public void setDataEffettivaChiusura(String dataEffettivaChiusura) {
		this.dataEffettivaChiusura = dataEffettivaChiusura;
	}

	public String getMotiviChiusura() {
		return motiviChiusura;
	}

	public void setMotiviChiusura(String motiviChiusura) {
		this.motiviChiusura = motiviChiusura;
	}	

}
