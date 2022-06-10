package it.webred.ss.ejb.dto;

import java.util.Date;
import java.util.List;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class SsSearchCriteria extends CeTBaseObject{

	private static final long serialVersionUID = 1L;
	
	private Integer first;
	private Integer pageSize;
	
	private Boolean eliminata;
	private Boolean completa;
	
	private Integer tipoScheda;
	private String tipoSchedaDescr;
	
	private String operatoreUserName;
	private String segnalato;
	private String cf;
	
	private Date dataAccesso;
	
	private List<Long> soggettoId;
	
	private String zonaSociale;
	private Long organizzazioneId;
	private Long ufficioId;
	
	private Long pContattoId;
	private String puntoContattoDescr;

	public boolean soloOrganizzazione;
	
	//RICERCA SOGGETTO
	private String cognome;
	private String nome;
	private Date dataNascitaDa;
	private Date dataNascitaA;
	private String sesso;
	private String alias;
	private String residenza;
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascitaDa() {
		return dataNascitaDa;
	}
	public void setDataNascitaDa(Date dataNascitaDa) {
		this.dataNascitaDa = dataNascitaDa;
	}
	public Date getDataNascitaA() {
		return dataNascitaA;
	}
	public void setDataNascitaA(Date dataNascitaA) {
		this.dataNascitaA = dataNascitaA;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	public Integer getFirst() {
		return first;
	}
	public void setFirst(Integer first) {
		this.first = first;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Boolean getEliminata() {
		return eliminata;
	}
	public void setEliminata(Boolean eliminata) {
		this.eliminata = eliminata;
	}
	public Boolean getCompleta() {
		return completa;
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
	public String getTipoSchedaDescr() {
		return tipoSchedaDescr;
	}
	public void setTipoSchedaDescr(String tipoSchedaDescr) {
		this.tipoSchedaDescr = tipoSchedaDescr;
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
	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}
	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}
	public Long getUfficioId() {
		return ufficioId;
	}
	public void setUfficioId(Long ufficioId) {
		this.ufficioId = ufficioId;
	}
	public Long getpContattoId() {
		return pContattoId;
	}
	public void setpContattoId(Long pContattoId) {
		this.pContattoId = pContattoId;
	}
	public String getPuntoContattoDescr() {
		return puntoContattoDescr;
	}
	public void setPuntoContattoDescr(String puntoContattoDescr) {
		this.puntoContattoDescr = puntoContattoDescr;
	}
	public boolean isSoloOrganizzazione() {
		return soloOrganizzazione;
	}
	public void setSoloOrganizzazione(boolean soloOrganizzazione) {
		this.soloOrganizzazione = soloOrganizzazione;
	}
	public Date getDataAccesso() {
		return dataAccesso;
	}
	public void setDataAccesso(Date dataAccesso) {
		this.dataAccesso = dataAccesso;
	}
	public String getZonaSociale() {
		return zonaSociale;
	}
	public void setZonaSociale(String zonaSociale) {
		this.zonaSociale = zonaSociale;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	//SISO-948
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getResidenza() {
		return residenza;
	}
	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}
	
}
