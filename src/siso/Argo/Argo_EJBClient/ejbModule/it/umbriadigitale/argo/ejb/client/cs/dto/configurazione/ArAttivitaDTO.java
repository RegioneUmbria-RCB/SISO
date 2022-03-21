package it.umbriadigitale.argo.ejb.client.cs.dto.configurazione;

import java.io.Serializable;
import java.util.Date;



public class ArAttivitaDTO implements Serializable {


	private long id;	
	private Long progettoId;
	private String progettoDesc;
	private String codice;
	private String descrizione;	
	private String tooltip;	
	private Boolean abilitato;
	private Date dataUltimaModifica;
	private String userUltimaModifica;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getProgettoId() {
		return progettoId;
	}
	public void setProgettoId(Long progettoId) {
		this.progettoId = progettoId;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public Boolean getAbilitato() {
		return abilitato;
	}
	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
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
	public String getProgettoDesc() {
		return progettoDesc;
	}
	public void setProgettoDesc(String progettoDesc) {
		this.progettoDesc = progettoDesc;
	}
}
