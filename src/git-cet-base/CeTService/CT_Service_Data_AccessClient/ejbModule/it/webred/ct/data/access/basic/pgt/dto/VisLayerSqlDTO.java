package it.webred.ct.data.access.basic.pgt.dto;

import java.io.Serializable;

import it.webred.ct.data.model.pgt.PgtSqlLayer;

public class VisLayerSqlDTO implements Serializable{
	
	private PgtSqlLayer layer;
	private long idTemplate;
	private String applicazione;
	private String sqlTemplate;
	private String sqlLayer;
	private String modalita;
	private Boolean visualizza; 
	
	public long getIdTemplate() {
		return idTemplate;
	}
	public void setIdTemplate(long idTemplate) {
		this.idTemplate = idTemplate;
	}
	public String getApplicazione() {
		return applicazione;
	}
	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}
	public String getSqlTemplate() {
		return sqlTemplate;
	}
	public void setSqlTemplate(String sqlTemplate) {
		this.sqlTemplate = sqlTemplate;
	}
	public String getSqlLayer() {
		return sqlLayer;
	}
	public void setSqlLayer(String sqlLayer) {
		this.sqlLayer = sqlLayer;
	}
	public String getModalita() {
		return modalita;
	}
	public void setModalita(String modalita) {
		this.modalita = modalita;
	}
	public Boolean getVisualizza() {
		return visualizza;
	}
	public void setVisualizza(Boolean visualizza) {
		this.visualizza = visualizza;
	}
	public PgtSqlLayer getLayer() {
		return layer;
	}
	public void setLayer(PgtSqlLayer layer) {
		this.layer = layer;
	}
}
