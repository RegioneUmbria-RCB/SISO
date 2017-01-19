package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_I_PS_EXPORT_MAST")
@NamedQuery(name="CsIPsExportMast.findAll", query="SELECT c FROM CsIPsExportMast c")
public class CsIPsExportMast implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CS_I_PS_EXPORT_MAST_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_PS_EXPORT_MAST_ID_GENERATOR")
	private Long id;
	
	@OneToMany(mappedBy="csIPsExportMast", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<CsIPsExport> csIPsExportList;	
	
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
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	public CsIPsExportMast(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}
	public Set<CsIPsExport> getCsIPsExportList() {
		return csIPsExportList;
	}

	public void setCsIPsExportList(Set<CsIPsExport> csIPsExportList) {
		this.csIPsExportList = csIPsExportList;
	}

}
