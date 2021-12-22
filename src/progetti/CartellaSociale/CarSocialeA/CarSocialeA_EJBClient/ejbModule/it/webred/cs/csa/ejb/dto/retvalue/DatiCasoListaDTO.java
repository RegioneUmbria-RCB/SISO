package it.webred.cs.csa.ejb.dto.retvalue;

import java.io.Serializable;
import java.util.Date;


public class DatiCasoListaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Date dataApertura;
	private String residenza;
	
	private Long anagraficaId;
	private Long casoId;
	private Long identificativo;
	private String denominazione;
	private Date dataNascita;
	private String cf;
	
	
	public Date getDataApertura() {
		return dataApertura;
	}
	public void setDataApertura(Date dataApertura) {
		this.dataApertura = dataApertura;
	}
	public String getResidenza() {
		return residenza;
	}
	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}
	public Long getAnagraficaId() {
		return anagraficaId;
	}
	public void setAnagraficaId(Long anagraficaId) {
		this.anagraficaId = anagraficaId;
	}
	public Long getCasoId() {
		return casoId;
	}
	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}
	public Long getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(Long identificativo) {
		this.identificativo = identificativo;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
}
