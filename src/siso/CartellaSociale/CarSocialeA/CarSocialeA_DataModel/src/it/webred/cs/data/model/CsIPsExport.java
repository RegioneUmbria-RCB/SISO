package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_I_PS_EXPORT")
@NamedQuery(name="CsIPsExport.findAll", query="SELECT c FROM CsIPsExport c")
public class CsIPsExport implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CS_I_PS_EXPORT_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_PS_EXPORT_ID_GENERATOR")
	private Long id;
		
	@ManyToOne
	@JoinColumn(name="INTERVENTO_ID")
	private CsIIntervento csIIntervento;
	
	@ManyToOne
	@JoinColumn(name="INTERVENTO_ESEG_ID")
	private CsIInterventoEseg csIInterventoEseg;
	
	@ManyToOne
	@JoinColumn(name="INTERVENTO_ESEG_MAST_ID")
	private CsIInterventoEsegMast csIInterventoEsegMast;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="EXPORT_MASTER_ID")
	private CsIPsExportMast csIPsExportMast;		
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	/* SISO-719 Nuove colonne */
	@Column(name = "FLAG_ESPORTATO")
	private Integer flagEsportato;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_EXPORT")
	private Date dtExport;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_REVOCA_EXPORT")
	private Date dtRevocaExport;
	
	@Column(name = "XML_ELEMENT_GROUPING")
	private Integer xmlElementGrouping;
	
	
	public CsIPsExport(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsIIntervento getCsIIntervento() {
		return csIIntervento;
	}

	public void setCsIIntervento(CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}
	
	public CsIInterventoEseg getCsIInterventoEseg() {
		return csIInterventoEseg;
	}

	public void setCsIInterventoEseg(CsIInterventoEseg csIInterventoEseg) {
		this.csIInterventoEseg = csIInterventoEseg;
	}

	public CsIInterventoEsegMast getCsIInterventoEsegMast() {
		return csIInterventoEsegMast;
	}

	public void setCsIInterventoEsegMast(CsIInterventoEsegMast csIInterventoEsegMast) {
		this.csIInterventoEsegMast = csIInterventoEsegMast;
	}

	public CsIPsExportMast getCsIPsExportMast() {
		return csIPsExportMast;
	}

	public void setCsIPsExportMast(CsIPsExportMast csIPsExportMast) {
		this.csIPsExportMast = csIPsExportMast;
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

	public Integer getFlagEsportato() {
		return flagEsportato;
	}

	public void setFlagEsportato(Integer flagEsportato) {
		this.flagEsportato = flagEsportato;
	}

	public Date getDtExport() {
		return dtExport;
	}

	public void setDtExport(Date dtExport) {
		this.dtExport = dtExport;
	}

	public Date getDtRevocaExport() {
		return dtRevocaExport;
	}

	public void setDtRevocaExport(Date dtRevocaExport) {
		this.dtRevocaExport = dtRevocaExport;
	}

	public Integer getXmlElementGrouping() {
		return xmlElementGrouping;
	}

	public void setXmlElementGrouping(Integer xmlElementGrouping) {
		this.xmlElementGrouping = xmlElementGrouping;
	}
}
