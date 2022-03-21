package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="IMPORT_SIRU_PROGETTI_ATTIVITA")
public class ImportSiruProgettiAttivita implements Serializable {
    
	
	@EmbeddedId
	private ImportSiruProgettiAttivitaPK id;

	@Column(name="PROGETTO_ID")
	private String progettoId;
		
	@Column(name="INT_ID")
	private String intId;
	
	@Column(name="INT_DESCRIZIONE")
	private String intDescrizione;
	
	@Column(name="TITOLO_CORSO")
	private String titoloCorso;
	
	@Column(name="COD_CORSO")
	private String codCorso;
	
	@Column(name="PARTECIPANTI_PREVISTI")
	private String partecipantiPrevisti;
	
	@Column(name="DENOMINAZIONE_SOG")
	private String denominazioneSog;
	private String piva;
	private String cf;
	private String belfiore;
	
	@Column(name="DT_INS")
	private Date dtIns;
	
	public String getProgettoId() {
		return progettoId;
	}
	public void setProgettoId(String progettoId) {
		this.progettoId = progettoId;
	}
	public String getIntId() {
		return intId;
	}
	public void setIntId(String intId) {
		this.intId = intId;
	}
	public String getIntDescrizione() {
		return intDescrizione;
	}
	public void setIntDescrizione(String intDescrizione) {
		this.intDescrizione = intDescrizione;
	}
	public String getTitoloCorso() {
		return titoloCorso;
	}
	public void setTitoloCorso(String titoloCorso) {
		this.titoloCorso = titoloCorso;
	}
	public String getCodCorso() {
		return codCorso;
	}
	public void setCodCorso(String codCorso) {
		this.codCorso = codCorso;
	}
	public String getPartecipantiPrevisti() {
		return partecipantiPrevisti;
	}
	public void setPartecipantiPrevisti(String partecipantiPrevisti) {
		this.partecipantiPrevisti = partecipantiPrevisti;
	}
	public String getDenominazioneSog() {
		return denominazioneSog;
	}
	public void setDenominazioneSog(String denominazioneSog) {
		this.denominazioneSog = denominazioneSog;
	}
	public String getPiva() {
		return piva;
	}
	public void setPiva(String piva) {
		this.piva = piva;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getBelfiore() {
		return belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public ImportSiruProgettiAttivitaPK getId() {
		return id;
	}
	public void setId(ImportSiruProgettiAttivitaPK id) {
		this.id = id;
	}
	public Date getDtIns() {
		return dtIns;
	}
	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}
}
