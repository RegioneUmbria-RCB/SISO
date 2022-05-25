package it.webred.cs.csa.ejb.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.webred.cs.data.model.CsACaso;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class IterDTO extends CeTBaseObject {

	private static final long serialVersionUID = 1L;
	
	private Long idAlert;
	private List<Long> idAlertList;
	private Long idStato;
	private String opRuolo;
	private Long idOperatore;
	private Long idSettore;
	private Long idOrganizzazione;
	private Long idOpSettoreTo;
	private Long idSettTo;
	private Long idOrgTo;
	private String descrizione;
	private String titoloDescrizione;
	private String url;
	private String tipo;
	private String alertUrl;
	private boolean notificaSettoreSegnalante;
	private String nota;
	private HashMap<Long, Object> attrNewStato;
	private Date dataInserimento = new Date();
	
	private CsACaso csACaso;
	
	public Long getIdAlert() {
		return idAlert;
	}
	public void setIdAlert(Long idAlert) {
		this.idAlert = idAlert;
	}
	public List<Long> getIdAlertList() {
		return idAlertList;
	}
	public void setIdAlertList(List<Long> idAlertList) {
		this.idAlertList = idAlertList;
	}
	public Long getIdSettore() {
		return idSettore;
	}
	public void setIdSettore(Long idSettore) {
		this.idSettore = idSettore;
	}
	public Long getIdSettTo() {
		return idSettTo;
	}
	public void setIdSettTo(Long idSettTo) {
		this.idSettTo = idSettTo;
	}
	public Long getIdOrgTo() {
		return idOrgTo;
	}
	public void setIdOrgTo(Long idOrgTo) {
		this.idOrgTo = idOrgTo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getTitoloDescrizione() {
		return titoloDescrizione;
	}
	public void setTitoloDescrizione(String titoloDescrizione) {
		this.titoloDescrizione = titoloDescrizione;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Long getIdOperatore() {
		return idOperatore;
	}
	public void setIdOperatore(Long idOperatore) {
		this.idOperatore = idOperatore;
	}
	public Long getIdStato() {
		return idStato;
	}
	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}
	public String getOpRuolo() {
		return opRuolo;
	}
	public void setOpRuolo(String opRuolo) {
		this.opRuolo = opRuolo;
	}
	public String getAlertUrl() {
		return alertUrl;
	}
	public void setAlertUrl(String alertUrl) {
		this.alertUrl = alertUrl;
	}
	public boolean isNotificaSettoreSegnalante() {
		return notificaSettoreSegnalante;
	}
	public void setNotificaSettoreSegnalante(boolean notificaSettoreSegnalante) {
		this.notificaSettoreSegnalante = notificaSettoreSegnalante;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public HashMap<Long, Object> getAttrNewStato() {
		return attrNewStato;
	}
	public void setAttrNewStato(HashMap<Long, Object> attrNewStato) {
		this.attrNewStato = attrNewStato;
	}
	public Long getIdOrganizzazione() {
		return idOrganizzazione;
	}
	public void setIdOrganizzazione(Long idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Long getIdOpSettoreTo() {
		return idOpSettoreTo;
	}
	public void setIdOpSettoreTo(Long idOpSettoreTo) {
		this.idOpSettoreTo = idOpSettoreTo;
	}
	public CsACaso getCsACaso() {
		return csACaso;
	}
	public void setCsACaso(CsACaso csACaso) {
		this.csACaso = csACaso;
	}
	
}
