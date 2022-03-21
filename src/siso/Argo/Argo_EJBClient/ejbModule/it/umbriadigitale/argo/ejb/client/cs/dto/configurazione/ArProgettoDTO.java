package it.umbriadigitale.argo.ejb.client.cs.dto.configurazione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArProgettoDTO implements Serializable {

	private static final long serialVersionUID = 3538579262771050082L;
	
	private Long id;
	private String codiceMemo;
	private String descrizione;
	private Date dataUltimaModifica;
	private String userUltimaModifica;
	private boolean abilitato;
	private boolean fse;

	//TODO:aggiungere le altre proprietà del model da gestire
	
	private List<ArAttivitaDTO> lstAttivita;
	private List<ArOrganizzazioneDTO> lstOrganizzazioni;
	private Boolean altreOrganizzazioni;

	
	public ArProgettoDTO(){
		this.lstAttivita = new ArrayList<ArAttivitaDTO>();
		this.lstOrganizzazioni = new ArrayList<ArOrganizzazioneDTO>();
	
	}
	
	public List<ArAttivitaDTO> getLstAttivita() {
		return lstAttivita;
	}

	public void setLstAttivita(List<ArAttivitaDTO> lstAttivita) {
		this.lstAttivita = lstAttivita;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodiceMemo() {
		return codiceMemo;
	}

	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}

	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getUserUltimaModifica() {
		return userUltimaModifica;
	}

	public void setUserUltimaModifica(String userUltimaModifica) {
		this.userUltimaModifica = userUltimaModifica;
	}

	public List<ArOrganizzazioneDTO> getLstOrganizzazioni() {
		return lstOrganizzazioni;
	}

	public void setLstOrganizzazioni(List<ArOrganizzazioneDTO> lstOrganizzazioni) {
		this.lstOrganizzazioni = lstOrganizzazioni;
	}

	public Boolean getAltreOrganizzazioni() {
		return altreOrganizzazioni;
	}

	public void setAltreOrganizzazioni(Boolean altreOrganizzazioni) {
		this.altreOrganizzazioni = altreOrganizzazioni;
	}

	public boolean isAbilitato() {
		return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}

	public boolean isFse() {
		return fse;
	}

	public void setFse(boolean fse) {
		this.fse = fse;
	}	
}
