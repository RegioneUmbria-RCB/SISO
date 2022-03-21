package it.webred.WsSitClient.dto;

import java.io.Serializable;
import java.util.Date;

public class RequestDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String token;
	private Date dataIniRif;
	private Date dataFinRif;
	private String idcCivico;
	private String reference;
	
	private String toponomy;
	private String type;
	
	private String codiceVia;
	private String civico;
	private String state;
	
	public String getCodiceVia() {
		return codiceVia;
	}
	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getToponomy() {
		return toponomy;
	}
	public void setToponomy(String toponomy) {
		this.toponomy = toponomy;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getDataIniRif() {
		return dataIniRif;
	}
	public void setDataIniRif(Date dataIniRif) {
		this.dataIniRif = dataIniRif;
	}
	public Date getDataFinRif() {
		return dataFinRif;
	}
	public void setDataFinRif(Date dataFinRif) {
		this.dataFinRif = dataFinRif;
	}
	public String getIdcCivico() {
		return idcCivico;
	}
	public void setIdcCivico(String idcCivico) {
		this.idcCivico = idcCivico;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	
}
