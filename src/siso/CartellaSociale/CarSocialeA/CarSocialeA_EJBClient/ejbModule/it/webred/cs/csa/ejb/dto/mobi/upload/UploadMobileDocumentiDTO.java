package it.webred.cs.csa.ejb.dto.mobi.upload;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UploadMobileDocumentiDTO implements Serializable {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;
	private byte[] data;
	private Long erOrganizzazioneErId;
	private Long erSettoreErogId;
	private String erSoggCf;
	private Long idMobile;
	private String testo;
	private Long tipo;
	private String titolo;
	
	private Long dove;
	private Long tipoColloquio;
	private Long conChi;
	
	public Long getDove() {
		return dove;
	}
	public void setDove(Long dove) {
		this.dove = dove;
	}
	public Long getTipoColloquio() {
		return tipoColloquio;
	}
	public void setTipoColloquio(Long tipoColloquio) {
		this.tipoColloquio = tipoColloquio;
	}
	public Long getConChi() {
		return conChi;
	}
	public void setConChi(Long conChi) {
		this.conChi = conChi;
	}	
	
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
	public Long getTipo() {
		return tipo;
	}
	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public Long getErSettoreErogId() {
		return erSettoreErogId;
	}
	public void setErSettoreErogId(Long erSettoreErogId) {
		this.erSettoreErogId = erSettoreErogId;
	}
	
}
