package it.webred.ct.data.access.basic.tarsu.dto;

import java.io.Serializable;

import it.webred.ct.data.model.common.*;
import it.webred.ct.data.model.tarsu.*;

import java.util.*;

public class InformativaTarsuDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private SitTTarOggetto oggettoTarsu;
	
	private SitEnte ente;
	
	private List<SoggettoTarsuDTO> dichiaranti;
	
	private List<SoggettoTarsuDTO> contribuenti;
	
	private List<SoggettoTarsuDTO> ulterioriSoggetti;
	
	
	public SitTTarOggetto getOggettoTarsu() {
		return oggettoTarsu;
	}
	public void setOggettoTarsu(SitTTarOggetto oggettoTarsu) {
		this.oggettoTarsu = oggettoTarsu;
	}
	public SitEnte getEnte() {
		return ente;
	}
	public void setEnte(SitEnte ente) {
		this.ente = ente;
	}
	public List<SoggettoTarsuDTO> getDichiaranti() {
		return dichiaranti;
	}
	public void setDichiaranti(List<SoggettoTarsuDTO> dichiaranti) {
		this.dichiaranti = dichiaranti;
	}
	public List<SoggettoTarsuDTO> getContribuenti() {
		return contribuenti;
	}
	public void setContribuenti(List<SoggettoTarsuDTO> contribuenti) {
		this.contribuenti = contribuenti;
	}
	public List<SoggettoTarsuDTO> getUlterioriSoggetti() {
		return ulterioriSoggetti;
	}
	public void setUlterioriSoggetti(List<SoggettoTarsuDTO> ulterioriSoggetti) {
		this.ulterioriSoggetti = ulterioriSoggetti;
	}
	

	
	

}
