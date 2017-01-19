package it.webred.ss.ejb.dto;

import java.util.List;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class SsSearchCriteria extends CeTBaseObject{

	private static final long serialVersionUID = 1L;
	
	private Integer first;
	private Integer pageSize;
	
	private Boolean eliminata;
	private Boolean completa;
	private Integer tipoScheda;
	
	private String operatoreUserName;
	private String segnalato;
	
	private List<Long> soggettoId;
	
	private Long organizzazioneId;
	private Long ufficioId;
	private Long pContattoId;
	
	public Integer getFirst() {
		return first;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	
	public Long getUfficioId() {
		return ufficioId;
	}
	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}
	public void setFirst(Integer first) {
		this.first = first;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setUfficioId(Long ufficioId) {
		this.ufficioId = ufficioId;
	}
	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}
	public Boolean getEliminata() {
		return eliminata;
	}
	public Boolean getCompleta() {
		return completa;
	}
	public void setEliminata(Boolean eliminata) {
		this.eliminata = eliminata;
	}
	public void setCompleta(Boolean completa) {
		this.completa = completa;
	}
	public Integer getTipoScheda() {
		return tipoScheda;
	}
	public void setTipoScheda(Integer tipoScheda) {
		this.tipoScheda = tipoScheda;
	}
	public Long getpContattoId() {
		return pContattoId;
	}
	public void setpContattoId(Long pContattoId) {
		this.pContattoId = pContattoId;
	}
	public String getOperatoreUserName() {
		return operatoreUserName;
	}
	public void setOperatoreUserName(String operatoreUserName) {
		this.operatoreUserName = operatoreUserName;
	}
	public String getSegnalato() {
		return segnalato;
	}
	public void setSegnalato(String segnalato) {
		this.segnalato = segnalato;
	}
	public List<Long> getSoggettoId() {
		return soggettoId;
	}
	public void setSoggettoId(List<Long> soggettoId) {
		this.soggettoId = soggettoId;
	}	


}
