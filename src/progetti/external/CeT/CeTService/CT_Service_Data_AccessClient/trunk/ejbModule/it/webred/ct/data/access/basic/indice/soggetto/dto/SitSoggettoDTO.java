package it.webred.ct.data.access.basic.indice.soggetto.dto;

import java.io.Serializable;
import java.util.Date;

import it.webred.ct.data.access.basic.indice.dto.SitOperationDTO;
import it.webred.ct.data.model.indice.SitSoggettoUnico;

public class SitSoggettoDTO extends SitOperationDTO implements Serializable{

	private String denominazione;
	private String codFis;
	private String pIva;
	private Date dataNascita;
	private String comuneNascita;
	private String fonteDati;
	private long fkEnteSorgente;
	private SitSoggettoUnico sitSoggettoUnico;
	
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodFis() {
		return codFis;
	}
	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}
	public String getComuneNascita() {
		return comuneNascita;
	}
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	public String getpIva() {
		return pIva;
	}
	public void setpIva(String pIva) {
		this.pIva = pIva;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public SitSoggettoUnico getSitSoggettoUnico() {
		return sitSoggettoUnico;
	}
	public void setSitSoggettoUnico(SitSoggettoUnico sitSoggettoUnico) {
		this.sitSoggettoUnico = sitSoggettoUnico;
	}
	public String getFonteDati() {
		return fonteDati;
	}
	public void setFonteDati(String fonteDati) {
		this.fonteDati = fonteDati;
	}
	public long getFkEnteSorgente() {
		return fkEnteSorgente;
	}
	public void setFkEnteSorgente(long fkEnteSorgente) {
		this.fkEnteSorgente = fkEnteSorgente;
	}
	
}
