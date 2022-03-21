package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FSE_CHK_IT_COMUNE")
public class FseChkItComune implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name ="ID")
	private String id;
	private String provinciaId;
	private String codice;
	private String codiceCatastale;
	private String denominazione;
	private String prefisso;
	private String cap;
	private String grado_rurale;
	private String annnotazioni;
	private Boolean soppresso;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name ="PROVINCIA_ID")
	public String getProvinciaId() {
		return provinciaId;
	}
	public void setProvinciaId(String provinciaId) {
		this.provinciaId = provinciaId;
	}
	@Column(name ="CODICE")
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	@Column(name ="CODICE_CATASTALE")
	public String getCodiceCatastale() {
		return codiceCatastale;
	}
	public void setCodiceCatastale(String codiceCatastale) {
		this.codiceCatastale = codiceCatastale;
	}
	@Column(name ="DENOMINAZIONE")
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	@Column(name ="PREFISSO")
	public String getPrefisso() {
		return prefisso;
	}
	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}
	@Column(name ="CAP")
	public String getCap() {
		return cap;
	}
	
	public void setCap(String cap) {
		this.cap = cap;
	}
	@Column(name ="GRADO_RURALE")
	public String getGrado_rurale() {
		return grado_rurale;
	}
	public void setGrado_rurale(String grado_rurale) {
		this.grado_rurale = grado_rurale;
	}
	@Column(name ="ANNOTAZIONI")
	public String getAnnnotazioni() {
		return annnotazioni;
	}
	public void setAnnnotazioni(String annnotazioni) {
		this.annnotazioni = annnotazioni;
	}
	@Column(name ="SOPPRESSO")
	public Boolean getSoppresso() {
		return soppresso;
	}
	public void setSoppresso(Boolean soppresso) {
		this.soppresso = soppresso;
	}
	
	

}
