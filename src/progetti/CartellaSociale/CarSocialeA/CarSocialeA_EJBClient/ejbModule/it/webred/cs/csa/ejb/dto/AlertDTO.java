package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.persist.CsAlert;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class AlertDTO extends CeTBaseObject {

	private static final long serialVersionUID = 1L;
	
	private CsACaso caso;
	private CsOOperatore operatoreFrom;
	private CsOSettore settoreFrom;
	private CsOOrganizzazione organizzazioneFrom;
	
	private CsOOperatoreSettore opSettoreTo;
	private CsOSettore settoreTo;
	private CsOOrganizzazione organizzazioneTo;
	
	private String descrizione;
	private String titolo;
	private String url;
	
	private String tipo;
	private boolean notificaSettoreSegnalante;
	
	public CsACaso getCaso() {
		return caso;
	}
	public CsOOperatore getOperatoreFrom() {
		return operatoreFrom;
	}
	public CsOSettore getSettoreFrom() {
		return settoreFrom;
	}
	public CsOOrganizzazione getOrganizzazioneFrom() {
		return organizzazioneFrom;
	}
	public CsOOperatoreSettore getOpSettoreTo() {
		return opSettoreTo;
	}
	public CsOSettore getSettoreTo() {
		return settoreTo;
	}
	public CsOOrganizzazione getOrganizzazioneTo() {
		return organizzazioneTo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public String getTitolo() {
		return titolo;
	}
	public String getUrl() {
		return url;
	}
	public String getTipo() {
		return tipo;
	}
	public void setCaso(CsACaso caso) {
		this.caso = caso;
	}
	public void setOperatoreFrom(CsOOperatore operatoreFrom) {
		this.operatoreFrom = operatoreFrom;
	}
	public void setSettoreFrom(CsOSettore settoreFrom) {
		this.settoreFrom = settoreFrom;
	}
	public void setOrganizzazioneFrom(CsOOrganizzazione organizzazioneFrom) {
		this.organizzazioneFrom = organizzazioneFrom;
	}
	public void setOpSettoreTo(CsOOperatoreSettore opSettoreTo) {
		this.opSettoreTo = opSettoreTo;
	}
	public void setSettoreTo(CsOSettore settoreTo) {
		this.settoreTo = settoreTo;
	}
	public void setOrganizzazioneTo(CsOOrganizzazione organizzazioneTo) {
		this.organizzazioneTo = organizzazioneTo;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public boolean isNotificaSettoreSegnalante() {
		return notificaSettoreSegnalante;
	}
	public void setNotificaSettoreSegnalante(boolean notificaSettoreSegnalante) {
		this.notificaSettoreSegnalante = notificaSettoreSegnalante;
	}
	
	public void fillAlertJPA(CsAlert a){
		a.setCsACaso(caso);
		a.setCsOOperatore1(this.operatoreFrom);
		a.setCsOSettore1(this.settoreFrom);
		a.setCsOOrganizzazione1(this.organizzazioneFrom);
		
		a.setTipo(tipo);
		a.setUrl(url);
		a.setTitoloDescrizione(this.titolo);
		a.setDescrizione(this.descrizione);
		
		a.setCsOpSettore2(this.opSettoreTo);
		a.setCsOSettore2(this.settoreTo);
		a.setCsOOrganizzazione2(this.organizzazioneTo);
		a.setNotificaSegnalante(this.notificaSettoreSegnalante);
	}
	
}
