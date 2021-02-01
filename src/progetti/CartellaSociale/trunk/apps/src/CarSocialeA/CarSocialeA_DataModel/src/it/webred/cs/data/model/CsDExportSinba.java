package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CS_D_VALUTAZIONE database table.
 * 
 */
@Entity
@Table(name = "CS_D_EXPORT_SINBA")
@NamedQuery(name = "CsDExportSinba.findAll", query = "SELECT c FROM CsDExportSinba c")
public class CsDExportSinba implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "CS_D_EXPORT_SINBA_GENERATOR", sequenceName = "SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_D_EXPORT_SINBA_GENERATOR")
	private Long id;

	
	@Column(name = "NOME_FILE")
	private String nomeFile;

	@Column(name = "DT_INS")
	private Date dtIns;

	// bi-directional many-to-one association to CsFlgIntervento
	@OneToMany(mappedBy = "csDExportSinba", fetch = FetchType.EAGER)
	private Set<CsDSinba> csDSinbas;
	
	
	// Nuovi campi inseriti per l'export
	@Column(name="ENTE_EROGATORE")
	private String enteErogatore;
	
	@Column(name="DENOMINAZ_ENTE")
	private String denominazEnte;
	
	@Column(name="INDIRIZZO_ENTE")
	private String indirizzoEnte;

	@Column(name="CF_OPERATORE")
	private String cfOperatore;
	
	@Column
	private String flusso;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INVIO")
	private Date dataInvio;
	
	@Column(name="NUM_PROGRESSIVO")
	private Long numProgressivo;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	@Column(name="IDENTIFICAZIONE_FLUSSO")
	private String identificazioneFlusso;
	
	@Lob
	@Column(name="XML")
	private String xml;
	
	
	
	
	

	public CsDExportSinba() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	

	public Set<CsDSinba> getCsDSinbas() {
		return csDSinbas;
	}

	public void setCsDSinbas(Set<CsDSinba> csDSinbas) {
		this.csDSinbas = csDSinbas;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getEnteErogatore() {
		return enteErogatore;
	}

	public void setEnteErogatore(String enteErogatore) {
		this.enteErogatore = enteErogatore;
	}

	public String getDenominazEnte() {
		return denominazEnte;
	}

	public void setDenominazEnte(String denominazEnte) {
		this.denominazEnte = denominazEnte;
	}

	public String getIndirizzoEnte() {
		return indirizzoEnte;
	}

	public void setIndirizzoEnte(String indirizzoEnte) {
		this.indirizzoEnte = indirizzoEnte;
	}

	public String getCfOperatore() {
		return cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}

	public String getFlusso() {
		return flusso;
	}

	public void setFlusso(String flusso) {
		this.flusso = flusso;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public Long getNumProgressivo() {
		return numProgressivo;
	}

	public void setNumProgressivo(Long numProgressivo) {
		this.numProgressivo = numProgressivo;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getIdentificazioneFlusso() {
		return identificazioneFlusso;
	}

	public void setIdentificazioneFlusso(String identificazioneFlusso) {
		this.identificazioneFlusso = identificazioneFlusso;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
	
	

}