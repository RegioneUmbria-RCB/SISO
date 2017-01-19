package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CS_A_COMPONENTE_GIT database table.
 * 
 */
@Entity
@Table(name="CS_A_COMPONENTE_GIT")
@NamedQuery(name="CsAComponenteGit.findAll", query="SELECT c FROM CsAComponenteGit c")
public class CsAComponenteGit implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@SequenceGenerator(name="CS_A_COMPONENTE_GIT_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_COMPONENTE_GIT_ID_GENERATOR")
	private BigDecimal id;

	
	private String cell;

	private String cf;

	private String cittadinanza;

	private String cognome;

	@Column(name="COM_ALTRO_COD")
	private String comAltroCod;

	@Column(name="COM_ALTRO_DES")
	private String comAltroDes;

	@Column(name="COM_RES_COD")
	private String comResCod;

	@Column(name="COM_RES_DES")
	private String comResDes;

	@Column(name="COMUNE_NASCITA_COD")
	private String comuneNascitaCod;

	@Column(name="COMUNE_NASCITA_DES")
	private String comuneNascitaDes;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_DECESSO")
	private Date dataDecesso;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;

	private String email;
	
	//bi-directional many-to-one association to CsAFamigliaGruppo
	@ManyToOne
	@JoinColumn(name="FAMIGLIA_GRUPPO_GIT_ID")
	private CsAFamigliaGruppoGit csAFamigliaGruppoGit;

	@Column(name="FLG_SEGNALAZIONE")
	private String flgSegnalazione;


	@Column(name="INDIRIZZO_ALTRO")
	private String indirizzoAltro;

	@Column(name="INDIRIZZO_RES")
	private String indirizzoRes;

	private String nome;

	private String note;

	@Column(name="NUM_CIV_ALTRO")
	private String numCivAltro;

	@Column(name="NUM_CIV_RES")
	private String numCivRes;

	@Column(name="PROV_ALTRO_COD")
	private String provAltroCod;

	@Column(name="PROV_NASCITA_COD")
	private String provNascitaCod;

	@Column(name="PROV_RES_COD")
	private String provResCod;

	private String sesso;

	@Column(name="STATO_NASCITA_COD")
	private String statoNascitaCod;

	@Column(name="STATO_NASCITA_DES")
	private String statoNascitaDes;

	private String tel;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;
	
	//bi-directional many-to-one association to CsTbTipoRapportoCon
	@ManyToOne
	@JoinColumn(name="PARENTELA_ID")
	private CsTbTipoRapportoCon csTbTipoRapportoCon;

	
	@Column(name="PARENTELA_VALIDA")
	private Boolean parentelaValida;

	
	public CsAComponenteGit() {
	}

	public String getCell() {
		return this.cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getCf() {
		return this.cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getCittadinanza() {
		return this.cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComAltroCod() {
		return this.comAltroCod;
	}

	public void setComAltroCod(String comAltroCod) {
		this.comAltroCod = comAltroCod;
	}

	public String getComAltroDes() {
		return this.comAltroDes;
	}

	public void setComAltroDes(String comAltroDes) {
		this.comAltroDes = comAltroDes;
	}

	public String getComResCod() {
		return this.comResCod;
	}

	public void setComResCod(String comResCod) {
		this.comResCod = comResCod;
	}

	public String getComResDes() {
		return this.comResDes;
	}

	public void setComResDes(String comResDes) {
		this.comResDes = comResDes;
	}

	public String getComuneNascitaCod() {
		return this.comuneNascitaCod;
	}

	public void setComuneNascitaCod(String comuneNascitaCod) {
		this.comuneNascitaCod = comuneNascitaCod;
	}

	public String getComuneNascitaDes() {
		return this.comuneNascitaDes;
	}

	public void setComuneNascitaDes(String comuneNascitaDes) {
		this.comuneNascitaDes = comuneNascitaDes;
	}

	public Date getDataDecesso() {
		return this.dataDecesso;
	}

	public void setDataDecesso(Date dataDecesso) {
		this.dataDecesso = dataDecesso;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CsAFamigliaGruppoGit getCsAFamigliaGruppoGit() {
		return this.csAFamigliaGruppoGit;
	}

	public void setCsAFamigliaGruppoGit(CsAFamigliaGruppoGit csAFamigliaGruppoGit) {
		this.csAFamigliaGruppoGit = csAFamigliaGruppoGit;
	}
	public String getFlgSegnalazione() {
		return this.flgSegnalazione;
	}

	public void setFlgSegnalazione(String flgSegnalazione) {
		this.flgSegnalazione = flgSegnalazione;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getIndirizzoAltro() {
		return this.indirizzoAltro;
	}

	public void setIndirizzoAltro(String indirizzoAltro) {
		this.indirizzoAltro = indirizzoAltro;
	}

	public String getIndirizzoRes() {
		return this.indirizzoRes;
	}

	public void setIndirizzoRes(String indirizzoRes) {
		this.indirizzoRes = indirizzoRes;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumCivAltro() {
		return this.numCivAltro;
	}

	public void setNumCivAltro(String numCivAltro) {
		this.numCivAltro = numCivAltro;
	}

	public String getNumCivRes() {
		return this.numCivRes;
	}

	public void setNumCivRes(String numCivRes) {
		this.numCivRes = numCivRes;
	}

	public String getProvAltroCod() {
		return this.provAltroCod;
	}

	public void setProvAltroCod(String provAltroCod) {
		this.provAltroCod = provAltroCod;
	}

	public String getProvNascitaCod() {
		return this.provNascitaCod;
	}

	public void setProvNascitaCod(String provNascitaCod) {
		this.provNascitaCod = provNascitaCod;
	}

	public String getProvResCod() {
		return this.provResCod;
	}

	public void setProvResCod(String provResCod) {
		this.provResCod = provResCod;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getStatoNascitaCod() {
		return this.statoNascitaCod;
	}

	public void setStatoNascitaCod(String statoNascitaCod) {
		this.statoNascitaCod = statoNascitaCod;
	}

	public String getStatoNascitaDes() {
		return this.statoNascitaDes;
	}

	public void setStatoNascitaDes(String statoNascitaDes) {
		this.statoNascitaDes = statoNascitaDes;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public Boolean getParentelaValida() {
		return parentelaValida;
	}

	public void setParentelaValida(Boolean parentelaValida) {
		this.parentelaValida = parentelaValida;
	}
	
	public CsTbTipoRapportoCon getCsTbTipoRapportoCon() {
		return csTbTipoRapportoCon;
	}

	public void setCsTbTipoRapportoCon(CsTbTipoRapportoCon csTbTipoRapportoCon) {
		this.csTbTipoRapportoCon = csTbTipoRapportoCon;
	}

}