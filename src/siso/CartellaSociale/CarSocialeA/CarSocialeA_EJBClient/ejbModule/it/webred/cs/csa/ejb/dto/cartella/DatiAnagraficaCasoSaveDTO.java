package it.webred.cs.csa.ejb.dto.cartella;

import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.siso.ws.ricerca.dto.FamiliareDettaglio;

import java.io.Serializable;
import java.util.List;

public class DatiAnagraficaCasoSaveDTO extends DatiAnagraficaCasoDTO implements Serializable  {

	private CsASoggettoLAZY soggetto;
	private String usernameOperatore;
	private boolean nuovoInserimento;
	private CsOOperatoreSettore currentOpSettore;

	private List<FamiliareDettaglio> listaFamiliari;
	private Long ssSchedaId;
	private String tipoScheda;
	
	
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
	public Long getSsSchedaId() {
		return ssSchedaId;
	}
	public void setSsSchedaId(Long ssSchedaId) {
		this.ssSchedaId = ssSchedaId;
	}
	public String getTipoScheda() {
		return tipoScheda;
	}
	public void setTipoScheda(String tipoScheda) {
		this.tipoScheda = tipoScheda;
	}
	public List<FamiliareDettaglio> getListaFamiliari() {
		return listaFamiliari;
	}
	public void setListaFamiliari(List<FamiliareDettaglio> listaFamiliari) {
		this.listaFamiliari = listaFamiliari;
	}
}
