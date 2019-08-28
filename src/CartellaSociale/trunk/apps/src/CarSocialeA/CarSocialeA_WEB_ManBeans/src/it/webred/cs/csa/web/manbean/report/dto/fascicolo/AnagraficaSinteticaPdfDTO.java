package it.webred.cs.csa.web.manbean.report.dto.fascicolo;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;

public class AnagraficaSinteticaPdfDTO extends ReportPdfDTO {

	//soggetto
	private String denominazione = EMPTY_VALUE;
	private String dataNascita = EMPTY_VALUE;
	private String cf = EMPTY_VALUE;
	private String comuneNascita = EMPTY_VALUE;
	private String cittadinanza = EMPTY_VALUE;
	private String telefono = EMPTY_VALUE;
	private String indirizzo = EMPTY_VALUE;
	
	public String getDenominazione() {
		return denominazione;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public String getCf() {
		return cf;
	}
	public String getComuneNascita() {
		return comuneNascita;
	}
	public String getCittadinanza() {
		return cittadinanza;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
}
