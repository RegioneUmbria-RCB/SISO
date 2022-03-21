package it.umbriadigitale.soclav.service.dto.gepi;

import java.util.Date;
import java.util.List;

import it.umbriadigitale.soclav.service.dto.DomandaRdCDTO;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici.Indirizzo;

public class GePiDomandaDTO extends DomandaRdCDTO{

	private String nomeCompletoRichiedente;
	
	private Indirizzo residenza;
	
	private String ambito;
	
	private Date dataAssegnazione;
	
	private String assegnante; //CF utente (coordinatore) che ha assegnato la domanda per avviarne la lavorazione
	 
	private String assegnatario; //CF utente (case manager) che ha in carico la domanda

	private List<GePiBeneficiarioDTO> familiari;
	
	public String getNomeCompletoRichiedente() {
		return nomeCompletoRichiedente;
	}

	public void setNomeCompletoRichiedente(String nomeCompletoRichiedente) {
		this.nomeCompletoRichiedente = nomeCompletoRichiedente;
	}

	public List<GePiBeneficiarioDTO> getFamiliari() {
		return familiari;
	}

	public void setFamiliari(List<GePiBeneficiarioDTO> familiari) {
		this.familiari = familiari;
	}

	public Indirizzo getResidenza() {
		return residenza;
	}

	public void setResidenza(Indirizzo residenza) {
		this.residenza = residenza;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public Date getDataAssegnazione() {
		return dataAssegnazione;
	}

	public void setDataAssegnazione(Date dataAssegnazione) {
		this.dataAssegnazione = dataAssegnazione;
	}

	public String getAssegnante() {
		return assegnante;
	}

	public void setAssegnante(String assegnante) {
		this.assegnante = assegnante;
	}

	public String getAssegnatario() {
		return assegnatario;
	}

	public void setAssegnatario(String assegnatario) {
		this.assegnatario = assegnatario;
	}
}
