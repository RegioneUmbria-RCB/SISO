package it.webred.cs.csa.ejb.dto.cartella;

import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.siso.ws.ricerca.dto.FamiliareDettaglio;

import java.io.Serializable;
import java.util.List;

public class DatiAnagraficaCasoDTO extends CeTBaseObject implements Serializable  {

	private CsASoggettoLAZY soggetto;
	private String usernameOperatore;
	private boolean nuovoInserimento;
	private CsOOperatoreSettore currentOpSettore;
	private List<CsAIndirizzo> listaIndirizzi;
	private List<ValiditaDTO> listaStatus;
	private List<ValiditaDTO> listaMedici;
	private List<ValiditaDTO> listaStatoCivile;
	private List<FamiliareDettaglio> listaFamiliari;
	private Long ssSchedaId;
	
	
	public CsASoggettoLAZY getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(CsASoggettoLAZY soggetto) {
		this.soggetto = soggetto;
	}
	public String getUsernameOperatore() {
		return usernameOperatore;
	}
	public void setUsernameOperatore(String usernameOperatore) {
		this.usernameOperatore = usernameOperatore;
	}
	public boolean isNuovoInserimento() {
		return nuovoInserimento;
	}
	public void setNuovoInserimento(boolean nuovoInserimento) {
		this.nuovoInserimento = nuovoInserimento;
	}
	public CsOOperatoreSettore getCurrentOpSettore() {
		return currentOpSettore;
	}
	public void setCurrentOpSettore(CsOOperatoreSettore currentOpSettore) {
		this.currentOpSettore = currentOpSettore;
	}
	public List<CsAIndirizzo> getListaIndirizzi() {
		return listaIndirizzi;
	}
	public void setListaIndirizzi(List<CsAIndirizzo> listaIndirizzi) {
		this.listaIndirizzi = listaIndirizzi;
	}
	public List<ValiditaDTO> getListaStatus() {
		return listaStatus;
	}
	public void setListaStatus(List<ValiditaDTO> listaStatus) {
		this.listaStatus = listaStatus;
	}
	public List<ValiditaDTO> getListaMedici() {
		return listaMedici;
	}
	public void setListaMedici(List<ValiditaDTO> listaMedici) {
		this.listaMedici = listaMedici;
	}
	public List<ValiditaDTO> getListaStatoCivile() {
		return listaStatoCivile;
	}
	public void setListaStatoCivile(List<ValiditaDTO> listaStatoCivile) {
		this.listaStatoCivile = listaStatoCivile;
	}
	public List<FamiliareDettaglio> getListaFamiliari() {
		return listaFamiliari;
	}
	public void setListaFamiliari(List<FamiliareDettaglio> listaFamiliari) {
		this.listaFamiliari = listaFamiliari;
	}
	public Long getSsSchedaId() {
		return ssSchedaId;
	}
	public void setSsSchedaId(Long ssSchedaId) {
		this.ssSchedaId = ssSchedaId;
	}
	
}
