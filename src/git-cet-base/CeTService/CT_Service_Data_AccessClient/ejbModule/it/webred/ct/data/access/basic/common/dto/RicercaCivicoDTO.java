package it.webred.ct.data.access.basic.common.dto;

import java.io.Serializable;

import java.util.Date;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class RicercaCivicoDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String civico; 
	private String descrizioneVia;
    private String toponimoVia;
    private String idVia;
    private String idCivico;
    private Date dataRif;
    private String[] listaIdCivici;
    private String[] listaId;
    
	
    public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico.toUpperCase().trim();
	}
	public String getDescrizioneVia() {
		return descrizioneVia;
	}
	public void setDescrizioneVia(String descrizioneVia) {
		this.descrizioneVia = descrizioneVia.toUpperCase().trim();
	}
	public String getToponimoVia() {
		return toponimoVia;
	}
	public void setToponimoVia(String toponimoVia) {
		this.toponimoVia = toponimoVia.toUpperCase().trim();
	}
	public void setIdVia(String idVia) {
		this.idVia = idVia;
	}
	public String getIdVia() {
		return idVia;
	}
	public void setIdCivico(String idCivico) {
		this.idCivico = idCivico;
	}
	public String getIdCivico() {
		return idCivico;
	}
	public void setDataRif(Date dataRif) {
		this.dataRif = dataRif;
	}
	public Date getDataRif() {
		return dataRif;
	}
	public void setListaId(String[] listaId) {
		this.listaId = listaId;
	}
	public String[] getListaId() {
		return listaId;
	}
	public void setListaIdCivici(String[] listaIdCivici) {
		this.listaIdCivici = listaIdCivici;
	}
	public String[] getListaIdCivici() {
		return listaIdCivici;
	}
	
}
