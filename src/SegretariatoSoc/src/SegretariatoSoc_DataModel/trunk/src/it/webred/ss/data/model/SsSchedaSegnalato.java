package it.webred.ss.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

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
	
	@Column(name="STRUTTURA_ACCOGLIENZA")
	private String strutturaAccoglienza;
	
	//bi-directional many-to-one association to SsIndirizzo
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="RESIDENZA")
	private SsIndirizzo residenza;
	
	//bi-directional many-to-one association to SsIndirizzo
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="DOMICILIO")
	private SsIndirizzo domicilio;
	

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

	public String getStrutturaAccoglienza() {
		return strutturaAccoglienza;
	}

	public void setStrutturaAccoglienza(String strutturaAccoglienza) {
		this.strutturaAccoglienza = strutturaAccoglienza;
	}

	public Long getInvalidita() {
		return invalidita;
	}

	public void setInvalidita(Long invalidita) {
		this.invalidita = invalidita;
	}

}