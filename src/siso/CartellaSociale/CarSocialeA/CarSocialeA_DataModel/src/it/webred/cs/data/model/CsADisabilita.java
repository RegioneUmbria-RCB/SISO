package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CS_A_DISABILITA database table.
 * 
 */
@Entity
@Table(name="CS_A_DISABILITA")
@NamedQuery(name="CsADisabilita.findAll", query="SELECT c FROM CsADisabilita c")
public class CsADisabilita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_A_DISABILITA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_DISABILITA_ID_GENERATOR")
	private Long id;

	private String anno;

	private String certificatore;

	private String consulenza;

	@Column(name="CONSULENZA_COP")
	private String consulenzaCop;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_APP")
	private Date dataFineApp;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_SYS")
	private Date dataFineSys;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_APP")
	private Date dataInizioApp;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_SYS")
	private Date dataInizioSys;

	private String df;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="ENTE_ID")
	private BigDecimal enteId;

	@Column(name="GRAVITA_ID")
	private BigDecimal gravitaId;

	private String orientamento;

	private String pdf;

	private String terapie;

	@Column(name="TIPOLOGIA_ID")
	private BigDecimal tipologiaId;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	private String valutazione;

	//bi-directional many-to-one association to CsASoggetto
	@ManyToOne
	@JoinColumn(name="SOGGETTO_ANAGRAFICA_ID")
	private CsASoggettoLAZY csASoggetto;

	public CsADisabilita() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnno() {
		return this.anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getCertificatore() {
		return this.certificatore;
	}

	public void setCertificatore(String certificatore) {
		this.certificatore = certificatore;
	}

	public String getConsulenza() {
		return this.consulenza;
	}

	public void setConsulenza(String consulenza) {
		this.consulenza = consulenza;
	}

	public String getConsulenzaCop() {
		return this.consulenzaCop;
	}

	public void setConsulenzaCop(String consulenzaCop) {
		this.consulenzaCop = consulenzaCop;
	}

	public Date getDataFineApp() {
		return this.dataFineApp;
	}

	public void setDataFineApp(Date dataFineApp) {
		this.dataFineApp = dataFineApp;
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

	public String getDf() {
		return this.df;
	}

	public void setDf(String df) {
		this.df = df;
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

	public BigDecimal getEnteId() {
		return this.enteId;
	}

	public void setEnteId(BigDecimal enteId) {
		this.enteId = enteId;
	}

	public BigDecimal getGravitaId() {
		return this.gravitaId;
	}

	public void setGravitaId(BigDecimal gravitaId) {
		this.gravitaId = gravitaId;
	}

	public String getOrientamento() {
		return this.orientamento;
	}

	public void setOrientamento(String orientamento) {
		this.orientamento = orientamento;
	}

	public String getPdf() {
		return this.pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getTerapie() {
		return this.terapie;
	}

	public void setTerapie(String terapie) {
		this.terapie = terapie;
	}

	public BigDecimal getTipologiaId() {
		return this.tipologiaId;
	}

	public void setTipologiaId(BigDecimal tipologiaId) {
		this.tipologiaId = tipologiaId;
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

	public String getValutazione() {
		return this.valutazione;
	}

	public void setValutazione(String valutazione) {
		this.valutazione = valutazione;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return this.csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

}