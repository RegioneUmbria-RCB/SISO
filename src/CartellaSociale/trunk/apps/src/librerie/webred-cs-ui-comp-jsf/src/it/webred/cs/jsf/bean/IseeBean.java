package it.webred.cs.jsf.bean;


import it.webred.cs.json.dto.JsonBaseBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IseeBean extends JsonBaseBean {

	private static final long serialVersionUID = 1L;

    private Date dataIsee;
	private Date dataScadenzaIsee;
	private String annoRif;
	private String note;
	private String tipologia;
	private BigDecimal ise;
	private BigDecimal isee;
	private BigDecimal scalaEquivalenza;
	private String protocolloDsu;
	
	public Date getDataScadenzaIsee() {
		return dataScadenzaIsee;
	}

	public String getAnnoRif() {
		return annoRif;
	}

	public String getNote() {
		return note;
	}

	public String getTipologia() {
		return tipologia;
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

	public void setAnnoRif(String annoRif) {
		this.annoRif = annoRif;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
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
		
		if(StringUtils.isEmpty(this.getAnnoRif())) messages.add("Anno è un campo obbligatorio");
		if(StringUtils.isEmpty(this.getTipologia())) messages.add("Tipologia è un campo obbligatorio");
		if(this.getDataIsee()==null) messages.add("Data dichiarazione ISEE è un campo obbligatorio");
		if(this.getDataScadenzaIsee()==null) messages.add("Data scadenza ISEE è un campo obbligatorio");
		if(this.getIse() == null || this.getIse().intValue() < 0) messages.add("ISE è un campo obbligatorio");
		if(this.getIsee() == null || this.getIsee().intValue() < 0) messages.add("ISEE è un campo obbligatorio");
		if(this.getScalaEquivalenza() == null || this.getScalaEquivalenza().intValue() < 0) messages.add("Scala Equivalenza è un campo obbligatorio");
		
		return messages;
		
	}

	public String getProtocolloDsu() {
		return protocolloDsu;
	}

	public void setProtocolloDsu(String protocolloDsu) {
		this.protocolloDsu = protocolloDsu;
	}
	
	
	
}
