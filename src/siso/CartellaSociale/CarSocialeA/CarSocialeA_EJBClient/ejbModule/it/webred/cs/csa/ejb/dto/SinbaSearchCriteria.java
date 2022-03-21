package it.webred.cs.csa.ejb.dto;

import java.util.Date;


public class SinbaSearchCriteria extends PaginationDTO{

	private static final long serialVersionUID = 1L;
	

	private Long casoId;
	
	private Long organizzazioneId;
		
	private Date dataInizio;
	private Date dataFine;
	private String organizzazioneBelfiore;
	private String statoEsportazione;
	private String cognome;
	private String nome;
	private String codiceFiscale;
	public Long getCasoId() {
		return casoId;
	}
	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}
	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}
	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}
	public String getOrganizzazioneBelfiore() {
		return organizzazioneBelfiore;
	}
	public void setOrganizzazioneBelfiore(String organizzazioneBelfiore) {
		this.organizzazioneBelfiore = organizzazioneBelfiore;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getStatoEsportazione() {
		return statoEsportazione;
	}
	public void setStatoEsportazione(String statoEsportazione) {
		this.statoEsportazione = statoEsportazione;
	}

	public Date getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Date getDataFine() {
		return dataFine;
	}
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
}
