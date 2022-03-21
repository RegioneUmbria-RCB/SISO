package it.webred.cs.csa.ejb.dto.mobi.upload;

import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.data.model.CsIValQuota;
import it.webred.cs.data.model.view.VMobiIntErog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UploadMobileErogazioniDTO implements Serializable {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;
	private Long erOrganizzazioneErId;
	private Long erSettoreErogId;
	private String erSoggCf;
	private Long idMobile;
	private String testo;
	private VMobiIntErog programmazione;
	private ErogStatoCfgDTO stato;
	private String unimis;
	private BigDecimal valore;
	private UploadMobileValoreDTO[] valori;
	
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Long getErOrganizzazioneErId() {
		return erOrganizzazioneErId;
	}
	public void setErOrganizzazioneErId(Long erOrganizzazioneErId) {
		this.erOrganizzazioneErId = erOrganizzazioneErId;
	}
	public String getErSoggCf() {
		return erSoggCf;
	}
	public void setErSoggCf(String erSoggCf) {
		this.erSoggCf = erSoggCf;
	}
	public Long getIdMobile() {
		return idMobile;
	}
	public void setIdMobile(Long idMobile) {
		this.idMobile = idMobile;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public VMobiIntErog getProgrammazione() {
		return programmazione;
	}
	public void setProgrammazione(VMobiIntErog programmazione) {
		this.programmazione = programmazione;
	}
	public ErogStatoCfgDTO getStato() {
		return stato;
	}
	public void setStato(ErogStatoCfgDTO stato) {
		this.stato = stato;
	}
	public UploadMobileValoreDTO[] getValori() {
		return valori;
	}
	public void setValori(UploadMobileValoreDTO[] valori) {
		this.valori = valori;
	}
	public Long getErSettoreErogId() {
		return erSettoreErogId;
	}
	public void setErSettoreErogId(Long erSettoreErogId) {
		this.erSettoreErogId = erSettoreErogId;
	}
	public String getUnimis() {
		return unimis;
	}
	public void setUnimis(String unimis) {
		this.unimis = unimis;
	}
	public BigDecimal getValore() {
		return valore;
	}
	public void setValore(BigDecimal valore) {
		this.valore = valore;
	}
	
}
