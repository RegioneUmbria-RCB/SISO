package it.webred.cs.csa.ejb.dto;

import it.webred.cs.csa.ejb.dto.erogazioni.CompartecipazioneDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SpesaDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class DatiAggregatiErogazioneDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private SpesaDTO totaleSpesa;
	private CompartecipazioneDTO totaleCompartecipazione;
	
	private String totaleErogato;
	private SpesaDTO masterSpesa;
	private CompartecipazioneDTO masterCompartecipazione;
	
	private Long masterFFOrigineId; 
	private String masterCodFin1;
	private String tipoInterventoCustom;


	
	private List<ErogazioneDTO> erogazioni;



	public SpesaDTO getTotaleSpesa() {
		return totaleSpesa;
	}



	public CompartecipazioneDTO getTotaleCompartecipazione() {
		return totaleCompartecipazione;
	}



	public String getTotaleErogato() {
		return totaleErogato;
	}



	public SpesaDTO getMasterSpesa() {
		return masterSpesa;
	}



	public CompartecipazioneDTO getMasterCompartecipazione() {
		return masterCompartecipazione;
	}



	public Long getMasterFFOrigineId() {
		return masterFFOrigineId;
	}



	public String getMasterCodFin1() {
		return masterCodFin1;
	}



	public String getTipoInterventoCustom() {
		return tipoInterventoCustom;
	}



	public List<ErogazioneDTO> getErogazioni() {
		return erogazioni;
	}



	public void setTotaleSpesa(SpesaDTO totaleSpesa) {
		this.totaleSpesa = totaleSpesa;
	}



	public void setTotaleCompartecipazione(CompartecipazioneDTO totaleCompartecipazione) {
		this.totaleCompartecipazione = totaleCompartecipazione;
	}



	public void setTotaleErogato(String totaleErogato) {
		this.totaleErogato = totaleErogato;
	}



	public void setMasterSpesa(SpesaDTO masterSpesa) {
		this.masterSpesa = masterSpesa;
	}



	public void setMasterCompartecipazione(CompartecipazioneDTO masterCompartecipazione) {
		this.masterCompartecipazione = masterCompartecipazione;
	}



	public void setMasterFFOrigineId(Long masterFFOrigineId) {
		this.masterFFOrigineId = masterFFOrigineId;
	}



	public void setMasterCodFin1(String masterCodFin1) {
		this.masterCodFin1 = masterCodFin1;
	}



	public void setTipoInterventoCustom(String tipoInterventoCustom) {
		this.tipoInterventoCustom = tipoInterventoCustom;
	}



	public void setErogazioni(List<ErogazioneDTO> erogazioni) {
		this.erogazioni = erogazioni;
	}
	

	

}
