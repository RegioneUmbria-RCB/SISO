package it.webred.cs.data.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CS_A_CASO_ACCESSO_FASCICOLO database table.
 * 
 */
//tabella per gestione Organizzazione  e secondo livello
@Entity
@Table(name="CS_A_CASO_ACCESSO_FASCICOLO")
@NamedQuery(name="CsACasoAccessoFascicolo.findAll", query="SELECT c FROM CsACasoAccessoFascicolo c")
public class CsACasoAccessoFascicolo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CS_A_CASO_ACCESSO_FASCICOLO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_CASO_ACCESSO_FASCICOLO_ID_GENERATOR")
	private Long id;
	
	@Column(name="CASO_ID")
	private Long casoId;
	
	@Column(name="TIPOLOGIA")
	private String tipologia;
	
	@Column(name="ID_SETTORE")  //IdSettore se di secondo livello altrimenti IdOrganizzazione
	private Long idSettore;
	
	@Column(name="ID_ORGANIZZAZIONE")  //IdSettore se di secondo livello altrimenti IdOrganizzazione
	private Long idOrganizzazione;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_APP")
	private Date dataFineApp;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_APP")
	private Date dataInizioApp;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_SYS")
	private Date dataFineSys;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_SYS")
	private Date dataInizioSys;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;
	

	@Column(name="MOTIVO_FINE")
	private String motivoFine;

	@Column(name="MOTIVO_INIZIO")
	private String motivoInizio;
	
	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USER_MOD")
	private String usrMod;
	
	@Column(name="FLAG_NASCONDI_INFORMAZIONI")
	private Boolean flagNascondiInformazioni;

	@OneToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="ID_SETTORE", insertable=false, updatable=false)
	private CsOSettore csOSettore;
	
	@OneToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="ID_ORGANIZZAZIONE", insertable=false, updatable=false)
	private CsOOrganizzazione csOOrganizzazione;
	
	public Long getId() {
		return id;
	}

	public Long getCasoId() {
		return casoId;
	}

	public String getTipologia() {
		return tipologia;
	}

	public Date getDataFineApp() {
		return dataFineApp;
	}

	public Date getDataFineSys() {
		return dataFineSys;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public String getMotivoFine() {
		return motivoFine;
	}

	public String getMotivoInizio() {
		return motivoInizio;
	}

	public String getUserIns() {
		return userIns;
	}

	public String getUsrMod() {
		return usrMod;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public void setDataFineApp(Date dataFineApp) {
		this.dataFineApp = dataFineApp;
	}

	public void setDataFineSys(Date dataFineSys) {
		this.dataFineSys = dataFineSys;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public void setMotivoFine(String motivoFine) {
		this.motivoFine = motivoFine;
	}

	public void setMotivoInizio(String motivoInizio) {
		this.motivoInizio = motivoInizio;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public Date getDataInizioApp() {
		return dataInizioApp;
	}

	public void setDataInizioApp(Date dataInizioApp) {
		this.dataInizioApp = dataInizioApp;
	}

	public Date getDataInizioSys() {
		return dataInizioSys;
	}

	public void setDataInizioSys(Date dataInizioSys) {
		this.dataInizioSys = dataInizioSys;
	}

	public Long getIdSettore() {
		return idSettore;
	}

	public Long getIdOrganizzazione() {
		return idOrganizzazione;
	}

	public void setIdSettore(Long idSettore) {
		this.idSettore = idSettore;
	}

	public void setIdOrganizzazione(Long idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}

	public CsOSettore getCsOSettore() {
		return csOSettore;
	}

	public CsOOrganizzazione getCsOOrganizzazione() {
		return csOOrganizzazione;
	}

	public void setCsOSettore(CsOSettore csOSettore) {
		this.csOSettore = csOSettore;
	}

	public void setCsOOrganizzazione(CsOOrganizzazione csOOrganizzazione) {
		this.csOOrganizzazione = csOOrganizzazione;
	}

	public Boolean getFlagNascondiInformazioni() {
		return flagNascondiInformazioni;
	}

	public void setFlagNascondiInformazioni(Boolean flagNascondiInformazioni) {
		this.flagNascondiInformazioni = flagNascondiInformazioni;
	}

}
