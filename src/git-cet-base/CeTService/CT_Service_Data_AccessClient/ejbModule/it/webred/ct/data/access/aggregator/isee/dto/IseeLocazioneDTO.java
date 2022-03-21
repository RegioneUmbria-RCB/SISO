package it.webred.ct.data.access.aggregator.isee.dto;

import it.webred.ct.data.model.locazioni.LocazioniA;
import it.webred.ct.data.model.locazioni.LocazioniB;
import it.webred.ct.data.model.locazioni.LocazioniI;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class IseeLocazioneDTO extends CeTBaseObject implements Serializable {
		
	private LocazioniA locazioneA;
	private LocazioniB locazioneB;
	private String oggettoLocazione;
	private String tipoSoggetto;
	private BigDecimal importoCanone;
	private List<LocazioniI> listaLocImmobili;
	
	public IseeLocazioneDTO() {
		super();
	}

	public LocazioniA getLocazioneA() {
		return locazioneA;
	}

	public void setLocazioneA(LocazioniA locazioneA) {
		this.locazioneA = locazioneA;
	}

	public List<LocazioniI> getListaLocImmobili() {
		return listaLocImmobili;
	}

	public void setListaLocImmobili(List<LocazioniI> listaLocImmobili) {
		this.listaLocImmobili = listaLocImmobili;
	}

	public String getOggettoLocazione() {
		return oggettoLocazione;
	}

	public void setOggettoLocazione(String oggettoLocazione) {
		this.oggettoLocazione = oggettoLocazione;
	}

	public BigDecimal getImportoCanone() {
		return importoCanone;
	}

	public void setImportoCanone(BigDecimal importoCanone) {
		this.importoCanone = importoCanone;
	}

	public LocazioniB getLocazioneB() {
		return locazioneB;
	}

	public void setLocazioneB(LocazioniB locazioneB) {
		this.locazioneB = locazioneB;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

}
