package it.webred.cs.csa.ejb.dto.cartella;

import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.siso.ws.ricerca.dto.FamiliareDettaglio;

import java.io.Serializable;
import java.util.List;

public class DatiAnagraficaCasoDTO extends CeTBaseObject implements Serializable  {

	private List<CsAIndirizzo> listaIndirizzi;
	private List<ValiditaDTO> listaStatus;
	private List<ValiditaDTO> listaMedici;
	private List<ValiditaDTO> listaStatoCivile;
	private boolean existsCatSociali = false;
	
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
	public boolean isExistsCatSociali() {
		return existsCatSociali;
	}
	public void setExistsCatSociali(boolean existsCatSociali) {
		this.existsCatSociali = existsCatSociali;
	}
	
}