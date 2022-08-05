package it.umbriadigitale.argo.ejb.client.cs.dto.configurazione;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArFonteDTO implements Serializable {

	private static final long serialVersionUID = 3538579262771050082L;
	
	private Long id;
	private String codiceMemo;
	private String descrizione;
	private Date dataUltimaModifica;
	private String userUltimaModifica;
	private boolean abilitato;
	private Long fondoId;
	private Long progettoDefaultId;
	private Date dtInizioVal;
	private Date dtFineVal;
	private BigDecimal importo;
	
	private List<ArOrganizzazioneDTO> lstOrganizzazioni;
	private Boolean altreOrganizzazioni;

	
	public ArFonteDTO(){
		this.lstOrganizzazioni = new ArrayList<ArOrganizzazioneDTO>();
	
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

	public Long getFondoId() {
		return fondoId;
	}

	public void setFondoId(Long fondoId) {
		this.fondoId = fondoId;
	}

	public Long getProgettoDefaultId() {
		return progettoDefaultId;
	}

	public void setProgettoDefaultId(Long progettoDefaultId) {
		this.progettoDefaultId = progettoDefaultId;
	}

	public Date getDtInizioVal() {
		return dtInizioVal;
	}

	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}

	public Date getDtFineVal() {
		return dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
}
