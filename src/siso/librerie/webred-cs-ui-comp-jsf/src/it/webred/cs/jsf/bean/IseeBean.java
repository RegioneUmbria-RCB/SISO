package it.webred.cs.jsf.bean;


import it.webred.cs.data.model.CsTbTipoIsee;
import it.webred.cs.json.dto.JsonBaseBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IseeBean extends JsonBaseBean {

	private static final long serialVersionUID = 1L;

    private Date dataIsee;
	private Date dataScadenzaIsee;
	private String note;
	private CsTbTipoIsee tipo;
	private BigDecimal ise;
	private BigDecimal isee;
	private BigDecimal scalaEquivalenza;
	private ProtocolloDsuBean protocolloBean;
	
	public IseeBean(){
		protocolloBean = new ProtocolloDsuBean();
		tipo = new CsTbTipoIsee();
	}
	
	public Date getDataScadenzaIsee() {
		return dataScadenzaIsee;
	}

	public String getNote() {
		return note;
	}

	public BigDecimal getIse() {
		return ise;
	}

	public BigDecimal getIsee() {
		return isee;
	}

	public BigDecimal getScalaEquivalenza() {
		return scalaEquivalenza;
	}

	public void setDataScadenzaIsee(Date dataScadenzaIsee) {
		this.dataScadenzaIsee = dataScadenzaIsee;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setIse(BigDecimal ise) {
		this.ise = ise;
	}

	public void setIsee(BigDecimal isee) {
		this.isee = isee;
	}

	public void setScalaEquivalenza(BigDecimal scalaEquivalenza) {
		this.scalaEquivalenza = scalaEquivalenza;
	}

	public Date getDataIsee() {
		return dataIsee;
	}

	public void setDataIsee(Date dataIsee) {
		this.dataIsee = dataIsee;
	}
	
	public List<String> checkObbligatorieta(){
		List<String> messages = new LinkedList<String>();
		
		if(this.protocolloBean.getAnno()==null || this.protocolloBean.getAnno()<=0) messages.add("Anno è un campo obbligatorio");
		if(this.tipo==null || tipo.getId()==null) messages.add("Tipologia è un campo obbligatorio");
		if(this.getDataIsee()==null) messages.add("Data dichiarazione ISEE è un campo obbligatorio");
		if(this.getDataScadenzaIsee()==null) messages.add("Data scadenza ISEE è un campo obbligatorio");
		if(this.getIse() == null || this.getIse().intValue() < 0) messages.add("ISE è un campo obbligatorio");
		if(this.getIsee() == null || this.getIsee().intValue() < 0) messages.add("ISEE è un campo obbligatorio");
		if(this.getScalaEquivalenza() == null || this.getScalaEquivalenza().intValue() < 0) messages.add("Scala Equivalenza è un campo obbligatorio");
		
		return messages;
		
	}

	public ProtocolloDsuBean getProtocolloBean() {
		return protocolloBean;
	}

	public void setProtocolloBean(ProtocolloDsuBean protocolloBean) {
		this.protocolloBean = protocolloBean;
	}

	public CsTbTipoIsee getTipo() {
		return tipo;
	}

	public void setTipo(CsTbTipoIsee tipo) {
		this.tipo = tipo;
	}

	
}
