package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.utils.bean.report.dto.BasePdfDTO;

public class DatiAnagraficiPdfDTO extends BasePdfDTO {

	private String cognome = EMPTY_VALUE;
	private String nome = EMPTY_VALUE;
	private String dataNascita = EMPTY_VALUE;
	private String dataMorte = EMPTY_VALUE;
	private	String luogoNascita = EMPTY_VALUE;
	private String sesso = EMPTY_VALUE;
	private String codFiscale = EMPTY_VALUE;
	private String cittadinanza = EMPTY_VALUE;
	private String residenza = EMPTY_VALUE;

	private String telefono = EMPTY_VALUE;
	private String cellulare = EMPTY_VALUE;
	private String email = EMPTY_VALUE;
	
	public String getCognome() {
		return cognome;
	}
	public String getNome() {
		return nome;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public String getSesso() {
		return sesso;
	}
	public String getCodFiscale() {
		return codFiscale;
	}
	public String getCittadinanza() {
		return cittadinanza;
	}
	public String getResidenza() {
		return residenza;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getCellulare() {
		return cellulare;
	}
	public String getEmail() {
		return email;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDataMorte() {
		return dataMorte;
	}
	public void setDataMorte(String dataMorte) {
		this.dataMorte = dataMorte;
	}
	
}
