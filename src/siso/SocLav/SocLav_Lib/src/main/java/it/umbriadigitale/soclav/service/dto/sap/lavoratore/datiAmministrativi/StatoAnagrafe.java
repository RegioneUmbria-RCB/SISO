package it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAmministrativi;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class StatoAnagrafe implements Serializable {
/*
	<statoinanagrafe>
		<codstatooccupazionale>DI</codstatooccupazionale>
		<codstatus>CO</codstatus>
		<categoria297>3</categoria297>
		<anzianita>26</anzianita>
		<indiceprofiling>0.907135570</indiceprofiling>
		<dataevento>2019-12-11</dataevento>
		<disponibilita>2017-09-27</disponibilita>
	</statoinanagrafe>        
*/
	
	private String codstatooccupazionale;
	private String codstatus;
	private String categoria297;
	private String anzianita;
	private String indiceprofiling;
	private Date dataevento;
	private Date disponibilita;
	
	/*Valorizzati da decodifica*/
	private String desstatooccupazionale;
	private String desstatus;
	private String descategoria297;

	public String getCodstatooccupazionale() {
		return codstatooccupazionale;
	}
	public void setCodstatooccupazionale(String codstatooccupazionale) {
		this.codstatooccupazionale = codstatooccupazionale;
	}
	public String getCodstatus() {
		return codstatus;
	}
	public void setCodstatus(String codstatus) {
		this.codstatus = codstatus;
	}
	public String getCategoria297() {
		return categoria297;
	}
	public void setCategoria297(String categoria297) {
		this.categoria297 = categoria297;
	}
	public String getAnzianita() {
		return anzianita;
	}
	public void setAnzianita(String anzianita) {
		this.anzianita = anzianita;
	}
	public String getIndiceprofiling() {
		return indiceprofiling;
	}
	public void setIndiceprofiling(String indiceprofiling) {
		this.indiceprofiling = indiceprofiling;
	}
	public Date getDataevento() {
		return dataevento;
	}
	public void setDataevento(Date dataevento) {
		this.dataevento = dataevento;
	}
	public Date getDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(Date disponibilita) {
		this.disponibilita = disponibilita;
	}
	public String getDesstatus() {
		return desstatus;
	}
	public void setDesstatus(String desstatus) {
		this.desstatus = desstatus;
	}
	public String getDescategoria297() {
		return descategoria297;
	}
	public void setDescategoria297(String descategoria297) {
		this.descategoria297 = descategoria297;
	}
	public String getDesstatooccupazionale() {
		return desstatooccupazionale;
	}
	public void setDesstatooccupazionale(String desstatooccupazionale) {
		this.desstatooccupazionale = desstatooccupazionale;
	}
	
}
