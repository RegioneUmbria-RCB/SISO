package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsOOperatoreSettore;

import java.util.Date;

public class JsonBaseDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1L;
	
	private Long diarioId;
	private Long diarioPadreId; //Facoltativo, se c'è gerarchia
	private String jsonString;
	private String versione;
	private String descrizione;
	
	//Campi per CsDDiario
	private int tipoDiarioId;
	private Long casoId;
	private Long schedaUdcId;
	private CsOOperatoreSettore opSettore;
	private Long responsabileId;
	private Date dtAttivazioneDa;
	private Date dtAttivazioneA;
	private Date dtSospensioneDa;
	private Date dtSospensioneA;
	private Date dtChiusuraDa;
	private Date dtChiusuraA;
	private Date dtAmministrativa;
	
	public Long getResponsabileId() {
		return responsabileId;
	}
	public Date getDtAttivazioneDa() {
		return dtAttivazioneDa;
	}
	public Date getDtAttivazioneA() {
		return dtAttivazioneA;
	}
	public Date getDtSospensioneDa() {
		return dtSospensioneDa;
	}
	public Date getDtSospensioneA() {
		return dtSospensioneA;
	}
	public Date getDtChiusuraDa() {
		return dtChiusuraDa;
	}
	public Date getDtChiusuraA() {
		return dtChiusuraA;
	}
	public Date getDtAmministrativa() {
		return dtAmministrativa;
	}
	public void setResponsabileId(Long responsabileId) {
		this.responsabileId = responsabileId;
	}
	public void setDtAttivazioneDa(Date dtAttivazioneDa) {
		this.dtAttivazioneDa = dtAttivazioneDa;
	}
	public void setDtAttivazioneA(Date dtAttivazioneA) {
		this.dtAttivazioneA = dtAttivazioneA;
	}
	public void setDtSospensioneDa(Date dtSospensioneDa) {
		this.dtSospensioneDa = dtSospensioneDa;
	}
	public void setDtSospensioneA(Date dtSospensioneA) {
		this.dtSospensioneA = dtSospensioneA;
	}
	public void setDtChiusuraDa(Date dtChiusuraDa) {
		this.dtChiusuraDa = dtChiusuraDa;
	}
	public void setDtChiusuraA(Date dtChiusuraA) {
		this.dtChiusuraA = dtChiusuraA;
	}
	public void setDtAmministrativa(Date dtAmministrativa) {
		this.dtAmministrativa = dtAmministrativa;
	}

	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public CsOOperatoreSettore getOpSettore() {
		return opSettore;
	}
	public void setOpSettore(CsOOperatoreSettore opSettore) {
		this.opSettore = opSettore;
	}
	public String getVersione() {
		return versione;
	}
	public void setVersione(String versione) {
		this.versione = versione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getTipoDiarioId() {
		return tipoDiarioId;
	}
	public void setTipoDiarioId(int tipoDiarioId) {
		this.tipoDiarioId = tipoDiarioId;
	}
	public Long getCasoId() {
		return casoId;
	}
	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}
	public Long getDiarioId() {
		return diarioId;
	}
	public Long getDiarioPadreId() {
		return diarioPadreId;
	}
	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}
	public void setDiarioPadreId(Long diarioPadreId) {
		this.diarioPadreId = diarioPadreId;
	}
	public Long getSchedaUdcId() {
		return schedaUdcId;
	}
	public void setSchedaUdcId(Long schedaUdcId) {
		this.schedaUdcId = schedaUdcId;
	}

}
