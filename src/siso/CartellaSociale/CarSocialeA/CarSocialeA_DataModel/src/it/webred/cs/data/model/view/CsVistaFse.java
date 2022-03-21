package it.webred.cs.data.model.view;

import it.webred.cs.data.model.CsTbTipoExtraFse;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_VISTA_FSE")
public class CsVistaFse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Column(name = "ORG_TITOLARE_ID")
	private Long organizzazioneTitolareId;

	@Column(name = "ORG_TITOLARE_DESC")
	private String organizzazioneTitolareDesc;
	
	@Column(name="PROGETTO_ATTIVITA_ID")
	private String progettoAttivitaId;
	
	private String progetto;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_SOTTOSCRIZIONE")
	private Date dataSottoscrizione;
	
	@Column(name = "SOGGETTO_ATTUATORE")
	private String soggettoAttuatore;
	
	private String cognome;
	private String nome;
	private String cf;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_NASCITA")
	private Date dataNascita;

	@Column(name = "NASCITA_COMUNE_ID")
	private String comuneNascitaId;
	
	@Column(name = "NASCITA_COMUNE_DESC")
	private String comuneNascitaDes;
	
	@Column(name = "NASCITA_NAZIONE_ID")
	private String nazioneNascitaId;
	
	@Column(name = "CITTADINANZA_COD")
	private String cittadinanzaCod;

	@Column(name="RES_NAZIONE_ID")
	private String residenzaNazioneId;
	
	@Column(name="RES_COMUNE_ID")
	private String residenzaComuneId;
	
	@Column(name= "RES_COMUNE_DESC")
	private String residenzaComuneDesc;
	
	@Column(name="RES_INDIRIZZO")
	private String residenzaIndirizzo;
	
	@Column(name="RES_CAP")
	private String residenzaCap;
	
	@Column(name="DOM_COMUNE_ID")
	private String domicilioComuneId;
	
	@Column(name="DOM_COMUNE_DESC")
	private String domicilioComuneDesc;
	
	@Column(name="DOM_INDIRIZZO")
	private String domicilioIndirizzo;
	
	@Column(name="DOM_CAP")
	private String domicilioCap;
	
	private String telefono;
	private String cellulare;
	private String email;
	
	@Column(name = "TITOLO_STUDIO")
	private String titoloStudio;

	@Column(name = "COND_MERCATO_INGRESSO")
	private String condMercatoIngresso;
	
	@Column(name="DURATA_RICERCA")
	private String durataRicerca;
	
	@Column(name = "COD_VULNERABILE_PA")
	private String codVulnerabile;
	
	@ManyToOne
	@JoinColumn(name="TIPO")
	private CsTbTipoExtraFse tipoFse;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProgettoAttivitaId() {
		return progettoAttivitaId;
	}

	public void setProgettoAttivitaId(String progettoAttivitaId) {
		this.progettoAttivitaId = progettoAttivitaId;
	}

	public String getProgetto() {
		return progetto;
	}

	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}

	public Date getDataSottoscrizione() {
		return dataSottoscrizione;
	}

	public void setDataSottoscrizione(Date dataSottoscrizione) {
		this.dataSottoscrizione = dataSottoscrizione;
	}

	public String getSoggettoAttuatore() {
		return soggettoAttuatore;
	}

	public void setSoggettoAttuatore(String soggettoAttuatore) {
		this.soggettoAttuatore = soggettoAttuatore;
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

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getComuneNascitaId() {
		return comuneNascitaId;
	}

	public void setComuneNascitaId(String comuneNascitaId) {
		this.comuneNascitaId = comuneNascitaId;
	}

	public String getComuneNascitaDes() {
		return comuneNascitaDes;
	}

	public void setComuneNascitaDes(String comuneNascitaDes) {
		this.comuneNascitaDes = comuneNascitaDes;
	}

	public String getNazioneNascitaId() {
		return nazioneNascitaId;
	}

	public void setNazioneNascitaId(String nazioneNascitaId) {
		this.nazioneNascitaId = nazioneNascitaId;
	}

	public String getCittadinanzaCod() {
		return cittadinanzaCod;
	}

	public void setCittadinanzaCod(String cittadinanzaCod) {
		this.cittadinanzaCod = cittadinanzaCod;
	}
	public String getResidenzaComuneId() {
		return residenzaComuneId;
	}

	public void setResidenzaComuneId(String residenzaComuneId) {
		this.residenzaComuneId = residenzaComuneId;
	}

	public String getResidenzaComuneDesc() {
		return residenzaComuneDesc;
	}

	public void setResidenzaComuneDesc(String residenzaComuneDesc) {
		this.residenzaComuneDesc = residenzaComuneDesc;
	}

	public String getResidenzaIndirizzo() {
		return residenzaIndirizzo;
	}

	public void setResidenzaIndirizzo(String residenzaIndirizzo) {
		this.residenzaIndirizzo = residenzaIndirizzo;
	}

	public String getResidenzaCap() {
		return residenzaCap;
	}

	public void setResidenzaCap(String residenzaCap) {
		this.residenzaCap = residenzaCap;
	}

	public String getDomicilioComuneId() {
		return domicilioComuneId;
	}

	public void setDomicilioComuneId(String domicilioComuneId) {
		this.domicilioComuneId = domicilioComuneId;
	}

	public String getDomicilioComuneDesc() {
		return domicilioComuneDesc;
	}

	public void setDomicilioComuneDesc(String domicilioComuneDesc) {
		this.domicilioComuneDesc = domicilioComuneDesc;
	}

	public String getDomicilioIndirizzo() {
		return domicilioIndirizzo;
	}

	public void setDomicilioIndirizzo(String domicilioIndirizzo) {
		this.domicilioIndirizzo = domicilioIndirizzo;
	}

	public String getDomicilioCap() {
		return domicilioCap;
	}

	public void setDomicilioCap(String domicilioCap) {
		this.domicilioCap = domicilioCap;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitoloStudio() {
		return titoloStudio;
	}

	public void setTitoloStudio(String titoloStudio) {
		this.titoloStudio = titoloStudio;
	}

	public String getCondMercatoIngresso() {
		return condMercatoIngresso;
	}

	public void setCondMercatoIngresso(String condMercatoIngresso) {
		this.condMercatoIngresso = condMercatoIngresso;
	}
	
	public String getDurataRicerca() {
		return durataRicerca;
	}

	public void setDurataRicerca(String durataRicerca) {
		this.durataRicerca = durataRicerca;
	}

	public String getCodVulnerabile() {
		return codVulnerabile;
	}

	public void setCodVulnerabile(String codVulnerabile) {
		this.codVulnerabile = codVulnerabile;
	}

	public CsTbTipoExtraFse getTipoFse() {
		return tipoFse;
	}

	public void setTipoFse(CsTbTipoExtraFse tipoFse) {
		this.tipoFse = tipoFse;
	}

	public Long getOrganizzazioneTitolareId() {
		return organizzazioneTitolareId;
	}

	public void setOrganizzazioneTitolareId(Long organizzazioneTitolareId) {
		this.organizzazioneTitolareId = organizzazioneTitolareId;
	}

	public String getResidenzaNazioneId() {
		return residenzaNazioneId;
	}

	public void setResidenzaNazioneId(String residenzaNazioneId) {
		this.residenzaNazioneId = residenzaNazioneId;
	}

	public String getOrganizzazioneTitolareDesc() {
		return organizzazioneTitolareDesc;
	}

	public void setOrganizzazioneTitolareDesc(String organizzazioneTitolareDesc) {
		this.organizzazioneTitolareDesc = organizzazioneTitolareDesc;
	}


}