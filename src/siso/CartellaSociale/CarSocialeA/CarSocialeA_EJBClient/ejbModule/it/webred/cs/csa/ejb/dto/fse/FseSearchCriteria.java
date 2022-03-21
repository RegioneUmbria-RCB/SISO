package it.webred.cs.csa.ejb.dto.fse;

import java.util.Date;
import java.util.List;

import it.webred.cs.csa.ejb.dto.PaginationDTO;

public class FseSearchCriteria extends PaginationDTO{

	private static final long serialVersionUID = 1L;
	
	private Long organizzazioneId;
	private Date dataInizio;
	private Date dataFine;
	private String residenzaIstat;
	
	private List<Long> tipoFse;
	private String denominazione;
	private String codiceFiscale;
	private String progetto;
	private String codAttivita;
	
	private boolean onlyFirstByCFProgetto;
	
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
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getResidenzaIstat() {
		return residenzaIstat;
	}
	public void setResidenzaIstat(String residenzaIstat) {
		this.residenzaIstat = residenzaIstat;
	}
	public List<Long> getTipoFse() {
		return tipoFse;
	}
	public void setTipoFse(List<Long> tipoFse) {
		this.tipoFse = tipoFse;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getProgetto() {
		return progetto;
	}
	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}
	public String getCodAttivita() {
		return codAttivita;
	}
	public void setCodAttivita(String codAttivita) {
		this.codAttivita = codAttivita;
	}
	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}
	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}
	public boolean isOnlyFirstByCFProgetto() {
		return onlyFirstByCFProgetto;
	}
	public void setOnlyFirstByCFProgetto(boolean onlyFirstByCFProgetto) {
		this.onlyFirstByCFProgetto = onlyFirstByCFProgetto;
	}
}
