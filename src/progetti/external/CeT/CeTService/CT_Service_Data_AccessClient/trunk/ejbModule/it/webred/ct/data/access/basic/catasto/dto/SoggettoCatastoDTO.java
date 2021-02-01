package it.webred.ct.data.access.basic.catasto.dto;

import it.webred.ct.data.model.catasto.ConsSoggTab;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.Date;

public class SoggettoCatastoDTO extends CeTBaseObject{

	private static final long serialVersionUID = 1L;
	private String descTipo;
	private String titolo;
	private ConsSoggTab consSoggTab;
	private String tipoTitolo;
	private BigDecimal percPoss;
	private Date dataInizioPossesso;
	private Date dataFinePossesso;
	private String tipImm;

	public String getDescTipo() {
		return descTipo;
	}
	public void setDescTipo(String descTipo) {
		this.descTipo = descTipo;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public ConsSoggTab getConsSoggTab() {
		return consSoggTab;
	}
	public void setConsSoggTab(ConsSoggTab consSoggTab) {
		this.consSoggTab = consSoggTab;
	}
	public void setTipoTitolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}
	public String getTipoTitolo() {
		return tipoTitolo;
	}
	
	public Date getDataInizioPossesso() {
		return dataInizioPossesso;
	}
	public void setDataInizioPossesso(Date dataInizioPossesso) {
		this.dataInizioPossesso = dataInizioPossesso;
	}
	public Date getDataFinePossesso() {
		return dataFinePossesso;
	}
	public void setDataFinePossesso(Date dataFinePossesso) {
		this.dataFinePossesso = dataFinePossesso;
	}
	public void setPercPoss(BigDecimal percPoss) {
		this.percPoss = percPoss;
	}
	public BigDecimal getPercPoss() {
		return percPoss;
	}
	public String getTipImm() {
		return tipImm;
	}
	public void setTipImm(String tipImm) {
		this.tipImm = tipImm;
	}
	
	
}
