package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the CS_A_CASO_OPE_TIPO_OPE database table.
 * 
 */
@Entity
@Table(name="CS_A_CASO_OPE_TIPO_OPE")
@NamedQuery(name="CsACasoOpeTipoOpe.findAll", query="SELECT c FROM CsACasoOpeTipoOpe c")
public class CsACasoOpeTipoOpe implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CS_A_CASO_OPE_TIPO_OPE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_CASO_OPE_TIPO_OPE_ID_GENERATOR")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_APP")
	private Date dataFineApp;

	@Column(name="CASO_ID")
	private long casoId;

	@Column(name="OPERATORE_TIPO_OPERATORE_ID")
	private long operatoreTipoOperatoreId;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_SYS")
	private Date dataInizioSys;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_SYS")
	private Date dataFineSys;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_APP")
	private Date dataInizioApp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="MOTIVO_FINE")
	private String motivoFine;

	@Column(name="MOTIVO_INIZIO")
	private String motivoInizio;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	@Column(name="FLAG_RESPONSABILE")
	private Boolean flagResponsabile;
	
	@Column(name="FLAG_GESTIONE_FASCICOLO")
	private Boolean flagGestioneFascicolo;
	
	//bi-directional many-to-one association to CsOOperatoreTipoOperatore
	@ManyToOne
	@JoinColumn(name="OPERATORE_TIPO_OPERATORE_ID", insertable=false, updatable=false)
	private CsOOperatoreTipoOperatore csOOperatoreTipoOperatore;

	public CsACasoOpeTipoOpe() {
	}

	public Date getDataFineSys() {
		return this.dataFineSys;
	}

	public void setDataFineSys(Date dataFineSys) {
		this.dataFineSys = dataFineSys;
	}

	public Date getDataInizioApp() {
		return this.dataInizioApp;
	}

	public void setDataInizioApp(Date dataInizioApp) {
		this.dataInizioApp = dataInizioApp;
	}

	public Date getDataInizioSys() {
		return this.dataInizioSys;
	}

	public void setDataInizioSys(Date dataInizioSys) {
		this.dataInizioSys = dataInizioSys;
	}

	public Date getDtIns() {
		return this.dtIns;
	}
	
	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getMotivoFine() {
		return this.motivoFine;
	}

	public void setMotivoFine(String motivoFine) {
		this.motivoFine = motivoFine;
	}

	public String getMotivoInizio() {
		return this.motivoInizio;
	}

	public void setMotivoInizio(String motivoInizio) {
		this.motivoInizio = motivoInizio;
	}

	public String getUserIns() {
		return this.userIns;
	}
	
	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public CsOOperatoreTipoOperatore getCsOOperatoreTipoOperatore() {
		return this.csOOperatoreTipoOperatore;
	}

	public void setCsOOperatoreTipoOperatore(CsOOperatoreTipoOperatore csOOperatoreTipoOperatore) {
		this.csOOperatoreTipoOperatore = csOOperatoreTipoOperatore;
	}

	public Boolean getFlagResponsabile() {
		return flagResponsabile;
	}

	public void setFlagResponsabile(Boolean flagResponsabile) {
		this.flagResponsabile = flagResponsabile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getDataFineApp() {
		return dataFineApp;
	}

	public void setDataFineApp(java.util.Date dataFineApp) {
		this.dataFineApp = dataFineApp;
	}

	public long getCasoId() {
		return casoId;
	}

	public void setCasoId(long casoId) {
		this.casoId = casoId;
	}

	public long getOperatoreTipoOperatoreId() {
		return operatoreTipoOperatoreId;
	}

	public void setOperatoreTipoOperatoreId(long operatoreTipoOperatoreId) {
		this.operatoreTipoOperatoreId = operatoreTipoOperatoreId;
	}

	public Boolean getFlagGestioneFascicolo() {
		return flagGestioneFascicolo;
	}

	public void setFlagGestioneFascicolo(Boolean flagGestioneFascicolo) {
		this.flagGestioneFascicolo = flagGestioneFascicolo;
	}
	
	public String getValString(){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String val = "";
		val+= this.dataInizioApp!=null ?  "dal " + df.format(this.dataInizioApp) : "" ;
		val+= this.dataFineApp!=null && !this.dataFineApp.after(new Date()) ? " al "+ df.format(this.dataFineApp) : "";
		return val;
		
	}
	
}