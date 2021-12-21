package eu.smartpeg.rilevazionepresenze.data.model.pai;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "AR_CS_PAI_PTI_CONSUNTI")
@NamedQuery(name = "ArCsPaiPtiConsunti.findAll", query = "SELECT c FROM ArCsPaiPtiConsunti c")
public class ArCsPaiPtiConsunti implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AR_CS_PAI_PTI_CONSUNTI_ID_GENERATOR", sequenceName = "SQ_PAI_PTI_CONSUNTI", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AR_CS_PAI_PTI_CONSUNTI_ID_GENERATOR")
	private Long id;

	@Column(name = "ID_PAI_PTI")
	private Long idPaiPTI;

	@Column(name = "COD_ROUTING")
	private String codRouting;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_DA")
	private Date dataDa;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_A")
	private Date dataA;
	
	@Column(name = "NUM_GIORNI")
	private Long numGiorni;

	@Column(name = "TARIFFA")
	private Long tariffa;
	
	@Column(name = "INVIATO_ENTE")
	private Boolean inviatoEnte;
	
	@Column(name = "FLAG_EROGATO")
	private Boolean flagErogato;
	
	@Column(name = "FLAG_NOTIFICA")
	private Boolean flagNotifica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPaiPTI() {
		return idPaiPTI;
	}

	public void setIdPaiPTI(Long idPaiPTI) {
		this.idPaiPTI = idPaiPTI;
	}

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public Date getDataDa() {
		return dataDa;
	}

	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}

	public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public Long getNumGiorni() {
		return numGiorni;
	}

	public void setNumGiorni(Long numGiorni) {
		this.numGiorni = numGiorni;
	}

	public Long getTariffa() {
		return tariffa;
	}

	public void setTariffa(Long tariffa) {
		this.tariffa = tariffa;
	}

	public Boolean getInviatoEnte() {
		return inviatoEnte;
	}

	public void setInviatoEnte(Boolean inviatoEnte) {
		this.inviatoEnte = inviatoEnte;
	}

	public Boolean getFlagErogato() {
		return flagErogato;
	}

	public void setFlagErogato(Boolean flagErogato) {
		this.flagErogato = flagErogato;
	}

	public Boolean getFlagNotifica() {
		return flagNotifica;
	}

	public void setFlagNotifica(Boolean flagNotifica) {
		this.flagNotifica = flagNotifica;
	}

}
