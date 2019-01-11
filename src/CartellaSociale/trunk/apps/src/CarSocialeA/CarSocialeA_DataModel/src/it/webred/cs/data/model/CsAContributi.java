package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the CS_A_CONTRIBUTI database table.
 * 
 */
@Entity
@Table(name="CS_A_CONTRIBUTI")
@NamedQuery(name="CsAContributi.findAll", query="SELECT c FROM CsAContributi c")
public class CsAContributi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_A_CONTRIBUTI_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_CONTRIBUTI_ID_GENERATOR")
	private Long id;
	
	@Column(name="BUONO_ID")
	private BigDecimal buonoId;
	
	@Column(name="CONTR_ORDINARIO")
	private String contrOrdinario;
	
	@Column(name="CONTR_STRAORDINARIO")
	private String contrStraordinario;
	
	@Column(name="CONTR_INT_SOSTEGNO")
	private String contrIntSostegno;
	
	@Column(name="CONTR_HOUSING")
	private String contrHousing;
	
	@Column(name="ANTICIPO_PROVV")
	private String anticipoProvv;
	
	private BigDecimal importo;
	
	@Column(name="PRIMA_EROGAZIONE")
	private String primaErogazione;
	
	@Column(name="INTERVENTO_PREGRESSO")
	private String interventoPregresso;
	
	private String rinnovo;
	
	@Column(name="RICHIESTA_RESPINTA")
	private String richiestaRespinta;
	
	private String sospensione;
	
	@Column(name="ESENZIONE_ID")
	private BigDecimal esenzioneId;
	
	@Column(name="RIDUZIONE_ID")
	private BigDecimal riduzioneId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	//bi-directional many-to-one association to CsASoggetto
	@ManyToOne
	@JoinColumn(name="SOGGETTO_ANAGRAFICA_ID")
	private CsASoggettoLAZY csASoggetto;

	public CsAContributi() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBuonoId() {
		return buonoId;
	}

	public void setBuonoId(BigDecimal buonoId) {
		this.buonoId = buonoId;
	}

	public String getContrOrdinario() {
		return contrOrdinario;
	}

	public void setContrOrdinario(String contrOrdinario) {
		this.contrOrdinario = contrOrdinario;
	}

	public String getContrStraordinario() {
		return contrStraordinario;
	}

	public void setContrStraordinario(String contrStraordinario) {
		this.contrStraordinario = contrStraordinario;
	}

	public String getContrIntSostegno() {
		return contrIntSostegno;
	}

	public void setContrIntSostegno(String contrIntSostegno) {
		this.contrIntSostegno = contrIntSostegno;
	}

	public String getContrHousing() {
		return contrHousing;
	}

	public void setContrHousing(String contrHousing) {
		this.contrHousing = contrHousing;
	}

	public String getAnticipoProvv() {
		return anticipoProvv;
	}

	public void setAnticipoProvv(String anticipoProvv) {
		this.anticipoProvv = anticipoProvv;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getPrimaErogazione() {
		return primaErogazione;
	}

	public void setPrimaErogazione(String primaErogazione) {
		this.primaErogazione = primaErogazione;
	}

	public String getInterventoPregresso() {
		return interventoPregresso;
	}

	public void setInterventoPregresso(String interventoPregresso) {
		this.interventoPregresso = interventoPregresso;
	}

	public String getRinnovo() {
		return rinnovo;
	}

	public void setRinnovo(String rinnovo) {
		this.rinnovo = rinnovo;
	}

	public String getRichiestaRespinta() {
		return richiestaRespinta;
	}

	public void setRichiestaRespinta(String richiestaRespinta) {
		this.richiestaRespinta = richiestaRespinta;
	}

	public String getSospensione() {
		return sospensione;
	}

	public void setSospensione(String sospensione) {
		this.sospensione = sospensione;
	}

	public BigDecimal getEsenzioneId() {
		return esenzioneId;
	}

	public void setEsenzioneId(BigDecimal esenzioneId) {
		this.esenzioneId = esenzioneId;
	}

	public BigDecimal getRiduzioneId() {
		return riduzioneId;
	}

	public void setRiduzioneId(BigDecimal riduzioneId) {
		this.riduzioneId = riduzioneId;
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

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUsrMod() {
		return usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

}