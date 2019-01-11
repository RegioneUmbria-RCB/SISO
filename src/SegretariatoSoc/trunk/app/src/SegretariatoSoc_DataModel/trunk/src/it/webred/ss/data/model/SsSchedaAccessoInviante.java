package it.webred.ss.data.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the SS_SCHEDA_ACCESSO_INVIANTE database table.
 * 
 */
@Entity
@Table(name="SS_SCHEDA_ACCESSO_INVIANTE")
@NamedQuery(name="SsSchedaAccessoInviante.findAll", query="SELECT c FROM SsSchedaAccessoInviante c")
public class SsSchedaAccessoInviante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SQ_SS_SCHEDA_ACCESSO_ID_GENERATOR", sequenceName="SQ_SS_SCHEDA_ACCESSO_INVIANTE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SQ_SS_SCHEDA_ACCESSO_ID_GENERATOR")
	private Long id;
	
	@Column(name="ID_NUOVA_SCHEDA")
	private Long idNuovaScheda;
	
	
	@Column(name="ORIG_BELFIORE")
	private String origBelfiore;
	
	@Column(name="ORIG_DESC_ORGANIZZAZIONE")
	private String origDescOrganizzazione;
	
	@Column(name="ORIG_URI_SERVICE")
	private String origUriService;
	
	@Column(name="ORIG_ID")
	private Long origId;
	
	@Column(name="ORIG_DATA_ACCESSO")
	private Date origDataAccesso;
	
	@Column(name="ORIG_COGNOME")
	private String origCognome;
	
	@Column(name="ORIG_ACCESSO_MODALITA")
	private String origAccessoModalita;
	
	@Column(name="ORIG_ACCESSO_INTERLOCUTORE")
	private String origAccessoIneterlocutore;
	
	@Column(name="ORIG_ACCESSO_MOTIVO")
	private String origAccessoMotivo;
	
	@Column(name="ORIG_NOME")
	private String origNome;
	
	@Column(name="ORIG_DATA_NASCITA")
	private Date origDataNascita;
	
	@Column(name="ORIG_NOME_UFFICIO")
	private String origNomeUfficio;
	
	@Column(name="ORIG_PUNTO_CONTATTO")
	private String origPuntoContatto;
	
	@Column(name="ORIG_COGNOME_OPERATORE")
	private String origCognomeOperatore;
	
	@Column(name="ORIG_NOME_OPERATORE")
	private String origNomeOperatore;
	
	@Column(name="ORIG_EMAIL")
	private String origEmail;
	
	@Column(name="ORIG_TELEFONO")
	private String origTelefono;
	
	@Column(name="ORIG_ZONA_SOC")
	private String origZonaSoc;
	
	@Column(name="ORIG_TIPO_INTERVENTO")
	private String origTipoIntervento;
	
	@Column(name="ORIG_DATA_INVIO")
	private Date origDataInvio;
	
	@Column(name="ORIG_ID_UFFICIO")
	private Long origIdUfficio;
	
	@Column(name="ORIG_ID_ORGANIZZAZIONE")
	private Long origIdOrganizzazione;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdNuovaScheda() {
		return idNuovaScheda;
	}

	public void setIdNuovaScheda(Long idNuovaScheda) {
		this.idNuovaScheda = idNuovaScheda;
	}

	public String getOrigBelfiore() {
		return origBelfiore;
	}

	public void setOrigBelfiore(String origBelfiore) {
		this.origBelfiore = origBelfiore;
	}

	public String getOrigDescOrganizzazione() {
		return origDescOrganizzazione;
	}

	public void setOrigDescOrganizzazione(String origDescOrganizzazione) {
		this.origDescOrganizzazione = origDescOrganizzazione;
	}

	public String getOrigUriService() {
		return origUriService;
	}

	public void setOrigUriService(String origUriService) {
		this.origUriService = origUriService;
	}

	public Long getOrigId() {
		return origId;
	}

	public void setOrigId(Long origId) {
		this.origId = origId;
	}

	public Date getOrigDataAccesso() {
		return origDataAccesso;
	}

	public void setOrigDataAccesso(Date origDataAccesso) {
		this.origDataAccesso = origDataAccesso;
	}

	public String getOrigCognome() {
		return origCognome;
	}

	public void setOrigCognome(String origCognome) {
		this.origCognome = origCognome;
	}

	public String getOrigAccessoModalita() {
		return origAccessoModalita;
	}

	public void setOrigAccessoModalita(String origAccessoModalita) {
		this.origAccessoModalita = origAccessoModalita;
	}

	public String getOrigAccessoIneterlocutore() {
		return origAccessoIneterlocutore;
	}

	public void setOrigAccessoIneterlocutore(String origAccessoIneterlocutore) {
		this.origAccessoIneterlocutore = origAccessoIneterlocutore;
	}

	public String getOrigAccessoMotivo() {
		return origAccessoMotivo;
	}

	public void setOrigAccessoMotivo(String origAccessoMotivo) {
		this.origAccessoMotivo = origAccessoMotivo;
	}

	public String getOrigNome() {
		return origNome;
	}

	public void setOrigNome(String origNome) {
		this.origNome = origNome;
	}

	public Date getOrigDataNascita() {
		return origDataNascita;
	}

	public void setOrigDataNascita(Date origDataNascita) {
		this.origDataNascita = origDataNascita;
	}

	public String getOrigNomeUfficio() {
		return origNomeUfficio;
	}

	public void setOrigNomeUfficio(String origNomeUfficio) {
		this.origNomeUfficio = origNomeUfficio;
	}

	public String getOrigPuntoContatto() {
		return origPuntoContatto;
	}

	public void setOrigPuntoContatto(String origPuntoContatto) {
		this.origPuntoContatto = origPuntoContatto;
	}

	public String getOrigCognomeOperatore() {
		return origCognomeOperatore;
	}

	public void setOrigCognomeOperatore(String origCognomeOperatore) {
		this.origCognomeOperatore = origCognomeOperatore;
	}

	public String getOrigNomeOperatore() {
		return origNomeOperatore;
	}

	public void setOrigNomeOperatore(String origNomeOperatore) {
		this.origNomeOperatore = origNomeOperatore;
	}

	public String getOrigEmail() {
		return origEmail;
	}

	public void setOrigEmail(String origEmail) {
		this.origEmail = origEmail;
	}

	public String getOrigTelefono() {
		return origTelefono;
	}

	public void setOrigTelefono(String origTelefono) {
		this.origTelefono = origTelefono;
	}

	public String getOrigZonaSoc() {
		return origZonaSoc;
	}

	public void setOrigZonaSoc(String origZonaSoc) {
		this.origZonaSoc = origZonaSoc;
	}

	public String getOrigTipoIntervento() {
		return origTipoIntervento;
	}

	public void setOrigTipoIntervento(String origTipoIntervento) {
		this.origTipoIntervento = origTipoIntervento;
	}

	public Date getOrigDataInvio() {
		return origDataInvio;
	}

	public void setOrigDataInvio(Date origDataInvio) {
		this.origDataInvio = origDataInvio;
	}

	public Long getOrigIdUfficio() {
		return origIdUfficio;
	}

	public void setOrigIdUfficio(Long origIdUfficio) {
		this.origIdUfficio = origIdUfficio;
	}

	public Long getOrigIdOrganizzazione() {
		return origIdOrganizzazione;
	}

	public void setOrigIdOrganizzazione(Long origIdOrganizzazione) {
		this.origIdOrganizzazione = origIdOrganizzazione;
	}
	
	

	
}