package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="CS_A_CASO_OPE_TIPO_OPE2")
@NamedQuery(name="CsACasoOpeTipoOpe2.findAll", query="SELECT c FROM CsACasoOpeTipoOpe2 c")
public class CsACasoOpeTipoOpe2  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CS_A_CASO_OPE_TIPO_OPE2_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_CASO_OPE_TIPO_OPE2_ID_GENERATOR")
	private Long id;
	
	@Column(name="CONTATTI")
	private String contatti;
	
	@Column(name="NOME_OPERATORE")
	private String nomeOperatore;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_SYS")
	private Date dataFineSys;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_APP")
	private Date dataFineApp;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_APP")
	private Date dataInizioApp;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_SYS")
	private Date dataInizioSys;

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
	
	//bi-directional many-to-one association to CsACaso
	@ManyToOne
	@JoinColumn(name="CASO_ID")
	private CsACaso csACaso;

	//bi-directional many-to-one association to CsOOperatoreTipoOperatore
	@ManyToOne
	@JoinColumn(name="TIPO_OPERATORE_ID")
	private CsTbTipoOperatore csTbTipoOperatore;

	public CsACasoOpeTipoOpe2() {
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

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return this.dtMod;
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

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}


	public String getContatti() {
		return contatti;
	}

	public void setContatti(String contatti) {
		this.contatti = contatti;
	}

	public String getNomeOperatore() {
		return nomeOperatore;
	}

	public void setNomeOperatore(String nomeOperatore) {
		this.nomeOperatore = nomeOperatore;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Date getDataFineApp() {
		return dataFineApp;
	}


	public void setDataFineApp(Date dataFineApp) {
		this.dataFineApp = dataFineApp;
	}


	public CsACaso getCsACaso() {
		return csACaso;
	}


	public void setCsACaso(CsACaso csACaso) {
		this.csACaso = csACaso;
	}


	public CsTbTipoOperatore getCsTbTipoOperatore() {
		return csTbTipoOperatore;
	}


	public void setCsTbTipoOperatore(CsTbTipoOperatore csTbTipoOperatore) {
		this.csTbTipoOperatore = csTbTipoOperatore;
	}

}
