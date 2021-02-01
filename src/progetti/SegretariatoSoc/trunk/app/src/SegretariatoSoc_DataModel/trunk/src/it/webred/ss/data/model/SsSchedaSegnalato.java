package it.webred.ss.data.model;

import it.webred.ss.data.model.tb.CsTbCondLavoro;
import it.webred.ss.data.model.tb.CsTbIngMercato;
import it.webred.ss.data.model.tb.CsTbProfessione;
import it.webred.ss.data.model.tb.CsTbSettoreImpiego;
import it.webred.ss.data.model.tb.CsTbTitoloStudio;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SS_SCHEDA_SEGNALATO")
@NamedQuery(name="SsSchedaSegnalato.findAll", query="SELECT c FROM SsSchedaSegnalato c")
public class SsSchedaSegnalato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_SCHEDA_SEGNALATO_ID_GENERATOR", sequenceName="SQ_SS_SCHEDA_SEGNALATO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_SCHEDA_SEGNALATO_ID_GENERATOR")
	private Long id;
	
	//bi-directional many-to-one association to SsAnagrafica
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="ANAGRAFICA")
	private SsAnagrafica anagrafica;
	
	private String telefono;
	
	private String cel;
	
	private String email;
	
	private String tessera_sanitaria;
	
	private String medico;
	
	private Boolean stp=false;
	
	private String lavoro;
	
	private String professione;
	
	private Long invalidita;
	
	@Column(name="TITOLO_STUDIO_ID")
	private BigDecimal titoloStudioId;
	
	@Column(name="SETT_IMPIEGO_ID")
	private BigDecimal settImpiegoId;
	
	//bi-directional many-to-one association to SsIndirizzo
	@OneToOne(cascade = {CascadeType.ALL}, orphanRemoval=true)
	@JoinColumn(name="RESIDENZA")
	private SsIndirizzo residenza;
	
	//bi-directional many-to-one association to SsIndirizzo
	@OneToOne(cascade = {CascadeType.ALL}, orphanRemoval=true)
	@JoinColumn(name="DOMICILIO")
	private SsIndirizzo domicilio;
	
	@Column(name="SENZA_FISSA_DIMORA")
	private Boolean senzaFissaDimora;
	
	@Column(name="NOTE_DOMICILIO")
	private String noteDomicilio;
	
	@Column(name="TIT_TELEFONO")
	private String titolareTelefono;
	
	@Column(name="TIT_CELLULARE")
	private String titolareCellulare;
	
	@Column(name="TIT_EMAIL")
	private String titolareEmail;
	
	@ManyToOne 
	@JoinColumn(name="TITOLO_STUDIO_ID", insertable=false, updatable=false)
	private CsTbTitoloStudio tbTitoloStudio;
	
	@ManyToOne  
	@JoinColumn(name="LAVORO", insertable=false, updatable=false)
	private CsTbCondLavoro tbCondLavoro;
	
	@ManyToOne  
	@JoinColumn(name="PROFESSIONE", insertable=false, updatable=false)
	private CsTbProfessione tbProfessione;
	
	@ManyToOne  
	@JoinColumn(name="SETT_IMPIEGO_ID", insertable=false, updatable=false)
	private CsTbSettoreImpiego tbSettoreImpiego;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTessera_sanitaria() {
		return tessera_sanitaria;
	}

	public void setTessera_sanitaria(String tessera_sanitaria) {
		this.tessera_sanitaria = tessera_sanitaria;
	}

	public String getMedico() {
		return medico;
	}

	public void setMedico(String medico) {
		this.medico = medico;
	}

	public SsIndirizzo getResidenza() {
		return residenza;
	}

	public void setResidenza(SsIndirizzo residenza) {
		this.residenza = residenza;
	}

	public SsIndirizzo getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(SsIndirizzo domicilio) {
		this.domicilio = domicilio;
	}

	public SsAnagrafica getAnagrafica() {
		return anagrafica;
	}

	public void setAnagrafica(SsAnagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}

	public String getProfessione() {
		return professione;
	}

	public void setProfessione(String professione) {
		this.professione = professione;
	}
	
	public String getLavoro() {
		return lavoro;
	}
	public void setLavoro(String lavoro) {
		this.lavoro = lavoro;
	}

	public BigDecimal getTitoloStudioId() {
		return titoloStudioId;
	}

	public BigDecimal getSettImpiegoId() {
		return settImpiegoId;
	}

	public void setTitoloStudioId(BigDecimal titoloStudioId) {
		this.titoloStudioId = titoloStudioId;
	}

	public void setSettImpiegoId(BigDecimal settImpiegoId) {
		this.settImpiegoId = settImpiegoId;
	}

	public Boolean getStp() {
		return stp;
	}

	public void setStp(Boolean stp) {
		this.stp = stp;
	}

	public Long getInvalidita() {
		return invalidita;
	}

	public void setInvalidita(Long invalidita) {
		this.invalidita = invalidita;
	}

	public Boolean getSenzaFissaDimora() {
		return senzaFissaDimora;
	}

	public void setSenzaFissaDimora(Boolean senzaFissaDimora) {
		this.senzaFissaDimora = senzaFissaDimora;
	}

	public String getNoteDomicilio() {
		return noteDomicilio;
	}

	public void setNoteDomicilio(String noteDomicilio) {
		this.noteDomicilio = noteDomicilio;
	}

	public String getTitolareTelefono() {
		return titolareTelefono;
	}

	public String getTitolareCellulare() {
		return titolareCellulare;
	}

	public String getTitolareEmail() {
		return titolareEmail;
	}

	public void setTitolareTelefono(String titolareTelefono) {
		this.titolareTelefono = titolareTelefono;
	}

	public void setTitolareCellulare(String titolareCellulare) {
		this.titolareCellulare = titolareCellulare;
	}

	public void setTitolareEmail(String titolareEmail) {
		this.titolareEmail = titolareEmail;
	}

	public CsTbTitoloStudio getTbTitoloStudio() {
		return tbTitoloStudio;
	}

	public void setTbTitoloStudio(CsTbTitoloStudio tbTitoloStudio) {
		this.tbTitoloStudio = tbTitoloStudio;
	}

	public CsTbCondLavoro getTbCondLavoro() {
		return tbCondLavoro;
	}

	public void setTbCondLavoro(CsTbCondLavoro tbCondLavoro) {
		this.tbCondLavoro = tbCondLavoro;
	}

	public CsTbProfessione getTbProfessione() {
		return tbProfessione;
	}

	public void setTbProfessione(CsTbProfessione tbProfessione) {
		this.tbProfessione = tbProfessione;
	}

	public CsTbSettoreImpiego getTbSettoreImpiego() {
		return tbSettoreImpiego;
	}

	public void setTbSettoreImpiego(CsTbSettoreImpiego tbSettoreImpiego) {
		this.tbSettoreImpiego = tbSettoreImpiego;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}