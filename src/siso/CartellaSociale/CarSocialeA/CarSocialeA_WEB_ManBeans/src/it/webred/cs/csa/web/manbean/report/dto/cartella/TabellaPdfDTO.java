package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.utils.bean.report.dto.StoricoPdfDTO;


public class TabellaPdfDTO extends StoricoPdfDTO {

	private String nome = EMPTY_VALUE;
	private String cognome = EMPTY_VALUE;
	private String sesso = EMPTY_VALUE;
	private String codFiscale = EMPTY_VALUE;
	private String relazione = EMPTY_VALUE;
	private String headerTab = EMPTY_VALUE;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public String getRelazione() {
		return relazione;
	}
	public void setRelazione(String relazione) {
		this.relazione = relazione;
	}
	public String getHeaderTab() {
		return headerTab;
	}
	public void setHeaderTab(String headerTab) {
		this.headerTab = headerTab;
	}
	
	
}
