package it.webred.cs.csa.ejb.dto;



public class SinbaSearchCriteria extends PaginationDTO{

	private static final long serialVersionUID = 1L;
	

	private Long casoId;
	
	private Long organizzazioneId;
		
	
	private String dataInizio;
	private String dataFine;
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
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
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
	
	
	
}
