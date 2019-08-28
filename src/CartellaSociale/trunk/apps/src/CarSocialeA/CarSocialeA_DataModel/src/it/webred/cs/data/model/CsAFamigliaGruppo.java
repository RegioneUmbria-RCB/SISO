package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CS_A_FAMIGLIA_GRUPPO database table.
 * 
 */
@Entity
@Table(name="CS_A_FAMIGLIA_GRUPPO")
@NamedQuery(name="CsAFamigliaGruppo.findAll", query="SELECT c FROM CsAFamigliaGruppo c")
public class CsAFamigliaGruppo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_A_FAMIGLIA_GRUPPO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_FAMIGLIA_GRUPPO_ID_GENERATOR")
	private Long id;

	private String codice;

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

	private String descrizione;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;

	private String tipo;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;
	
	@Column(name="FLG_VIVE_SOLO")
	private Boolean viveSolo;
	
	@Column(name="FLG_HA_PARENTI")
	private Boolean haParenti;
	
	@Column(name="FLG_PARENTI_SCONOSCIUTI")
	private Boolean parentiSconosciuti;   

	@Column(name="FLG_INFO_SCONOSCIUTE")
	private Integer infoSconosciute;   
	
	//bi-directional many-to-one association to CsAComponente
	@OneToMany(mappedBy="csAFamigliaGruppo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CsAComponente> csAComponentes;

	//bi-directional many-to-one association to CsASoggetto
	@ManyToOne
	@JoinColumn(name="SOGGETTO_ANAGRAFICA_ID")
	private CsASoggettoLAZY csASoggetto;

	public CsAFamigliaGruppo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
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

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public List<CsAComponente> getCsAComponentes() {
		return this.csAComponentes;
	}

	public void setCsAComponentes(List<CsAComponente> csAComponentes) {
		this.csAComponentes = csAComponentes;
	}

	public CsAComponente addCsAComponente(CsAComponente csAComponente) {
		getCsAComponentes().add(csAComponente);
		csAComponente.setCsAFamigliaGruppo(this);

		return csAComponente;
	}

	public CsAComponente removeCsAComponente(CsAComponente csAComponente) {
		getCsAComponentes().remove(csAComponente);
		csAComponente.setCsAFamigliaGruppo(null);

		return csAComponente;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return this.csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public Boolean getViveSolo() {
		return viveSolo;
	}

	public void setViveSolo(Boolean viveSolo) {
		this.viveSolo = viveSolo;
	}

	public Boolean getHaParenti() {
		return haParenti;
	}

	public void setHaParenti(Boolean haParenti) {
		this.haParenti = haParenti;
	}

	public Boolean getParentiSconosciuti() {
		return parentiSconosciuti;
	}

	public void setParentiSconosciuti(Boolean parentiSconosciuti) {
		this.parentiSconosciuti = parentiSconosciuti;
	}

	public Integer getInfoSconosciute() {
		return infoSconosciute;
	}

	public void setInfoSconosciute(Integer infoSconosciute) {
		this.infoSconosciute = infoSconosciute;
	}
	
}