package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.web.manbean.report.dto.BasePdfDTO;

public class AnagraficaPdfDTO extends BasePdfDTO {

	private String cognome = EMPTY_VALUE;
	private String nome = EMPTY_VALUE;
	private String dataNascita = EMPTY_VALUE;
	private	String luogoNascita = EMPTY_VALUE;
	private String sesso = EMPTY_VALUE;
	private String codFiscale = EMPTY_VALUE;
	private String cittadinanza = EMPTY_VALUE;
	private String cittadinanza2 = EMPTY_VALUE;
	private String residenza = EMPTY_VALUE;
	private String statoCivile = EMPTY_VALUE;
	private String status = EMPTY_VALUE;
	private String medico = EMPTY_VALUE;
	private String telefono = EMPTY_VALUE;
	private String cellulare = EMPTY_VALUE;
	private String email = EMPTY_VALUE;
	private String catSociale = EMPTY_VALUE;
	private String assSociale = EMPTY_VALUE;
	private String dataPIC = EMPTY_VALUE;
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = format(cognome);
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = format(nome);
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = format(dataNascita);
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = format(luogoNascita);
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = format(sesso);
	}
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = format(codFiscale);
	}
	public String getCittadinanza() {
		return cittadinanza;
	}
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = format(cittadinanza);
	}
	public String getResidenza() {
		return residenza;
	}
	public void setResidenza(String residenza) {
		this.residenza = format(residenza);
	}
	public String getStatoCivile() {
		return statoCivile;
	}
	public void setStatoCivile(String statoCivile) {
		this.statoCivile = format(statoCivile);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = format(status);
	}
	public String getMedico() {
		return medico;
	}
	public void setMedico(String medico) {
		this.medico = format(medico);
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = format(telefono);
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = format(cellulare);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = format(email);
	}
	public String getCatSociale() {
		return catSociale;
	}
	public void setCatSociale(String catSociale) {
		this.catSociale = format(catSociale);
	}
	public String getAssSociale() {
		return assSociale;
	}
	public void setAssSociale(String assSociale) {
		this.assSociale = format(assSociale);
	}
	public String getDataPIC() {
		return dataPIC;
	}
	public void setDataPIC(String dataPIC) {
		this.dataPIC = format(dataPIC);
	}
	public String getCittadinanza2() {
		return cittadinanza2;
	}
	public void setCittadinanza2(String cittadinanza2) {
		this.cittadinanza2 = cittadinanza2;
	}
	
}
