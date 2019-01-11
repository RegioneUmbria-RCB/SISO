package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the CS_A_SERVIZI database table.
 * 
 */
@Entity
@Table(name="CS_A_SERVIZI")
@NamedQuery(name="CsAServizi.findAll", query="SELECT c FROM CsAServizi c")
public class CsAServizi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_A_SERVIZI_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_SERVIZI_ID_GENERATOR")
	private Long id;

	private String adh;
	
	private String adi;
	
	private String adip;
	
	private String pasti;
	
	private String sad;
	
	@Column(name="ASS_COMUNICAZIONE")
	private String assComunicazione;
	
	@Column(name="CSE_PICCOLI")
	private String csePiccoli;
	
	@Column(name="EDU_INDIVIDUALE")
	private String eduIndividuale;
	
	@Column(name="EDU_PROGETTO")
	private String eduProgetto;
	
	@Column(name="SOST_SCOLASTICO")
	private String sostScolastico;
	
	@Column(name="INTEGR_SCOLASTICA")
	private String integrScolastica;
	
	@Column(name="POLO_H")
	private String poloH;
	
	@Column(name="PROGETTO_ADO")
	private String progettoAdo;
	
	@Column(name="ALTRO")
	private String altro;
	
	@Column(name="INTEGR_SCOLASTICA_PROV")
	private String integrScolasticaProv;
	
	@Column(name="CENTRO_DIURNO_RIAB_SEMIRES")
	private String centroDiurnoRiabSemires;
	
	@Column(name="ALTRO_SEMIRES")
	private String altroSemires;
	
	@Column(name="DESCR_STRUTTURA_SEMIRES")
	private String descrStrutturaSemires;
	
	@Column(name="LUOGO_STRUTTURA_SEMIRES_ID")
	private BigDecimal luogoStrutturaSemiresId;
	
	@Column(name="RETTA_A_CARICO_SEMIRES_ID")
	private BigDecimal rettaACaricoSemiresId;
	
	@Column(name="COMUNITA_MINORI_RES")
	private String comunitaMinoriRes;
	
	@Column(name="TIPO_COMUNITA_RES_ID")
	private BigDecimal tipoComunitaResId;
	
	@Column(name="AFFIDO_FAMILIARE_RES")
	private String affidoFamiliareRes;
	
	@Column(name="COMUNITA_TERAPEUTICA_RES")
	private String comunitaTerapeuticaRes;
	
	@Column(name="RSA_RES")
	private String rsaRes;
	
	@Column(name="DESCR_STRUTTURA_RES")
	private String descrStrutturaRes;
	
	@Column(name="LUOGO_STRUTTURA_RES_ID")
	private BigDecimal luogoStrutturaResId;
	
	@Column(name="RETTA_A_CARICO_RES_ID")
	private BigDecimal rettaACaricoResId;
	
	@Column(name="SOST_GENITORIALITA")
	private String sostGenitorialita;
	
	@Column(name="TRASPORTO_UFF_ISTRUZIONE")
	private String trasportoUffIstruzione;
	
	@Column(name="TRASPORTO_SOCIALE")
	private String trasportoSociale;
	
	@Column(name="CHI_EFFETTUA_SERVIZIO")
	private String chiEffettuaServizio;
	
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

	public CsAServizi() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public CsASoggettoLAZY getCsASoggetto() {
		return this.csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public String getAdh() {
		return adh;
	}

	public void setAdh(String adh) {
		this.adh = adh;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public String getAdip() {
		return adip;
	}

	public void setAdip(String adip) {
		this.adip = adip;
	}

	public String getPasti() {
		return pasti;
	}

	public void setPasti(String pasti) {
		this.pasti = pasti;
	}

	public String getSad() {
		return sad;
	}

	public void setSad(String sad) {
		this.sad = sad;
	}

	public String getAssComunicazione() {
		return assComunicazione;
	}

	public void setAssComunicazione(String assComunicazione) {
		this.assComunicazione = assComunicazione;
	}

	public String getCsePiccoli() {
		return csePiccoli;
	}

	public void setCsePiccoli(String csePiccoli) {
		this.csePiccoli = csePiccoli;
	}

	public String getEduIndividuale() {
		return eduIndividuale;
	}

	public void setEduIndividuale(String eduIndividuale) {
		this.eduIndividuale = eduIndividuale;
	}

	public String getEduProgetto() {
		return eduProgetto;
	}

	public void setEduProgetto(String eduProgetto) {
		this.eduProgetto = eduProgetto;
	}

	public String getSostScolastico() {
		return sostScolastico;
	}

	public void setSostScolastico(String sostScolastico) {
		this.sostScolastico = sostScolastico;
	}

	public String getIntegrScolastica() {
		return integrScolastica;
	}

	public void setIntegrScolastica(String integrScolastica) {
		this.integrScolastica = integrScolastica;
	}

	public String getPoloH() {
		return poloH;
	}

	public void setPoloH(String poloH) {
		this.poloH = poloH;
	}

	public String getProgettoAdo() {
		return progettoAdo;
	}

	public void setProgettoAdo(String progettoAdo) {
		this.progettoAdo = progettoAdo;
	}

	public String getAltro() {
		return altro;
	}

	public void setAltro(String altro) {
		this.altro = altro;
	}

	public String getIntegrScolasticaProv() {
		return integrScolasticaProv;
	}

	public void setIntegrScolasticaProv(String integrScolasticaProv) {
		this.integrScolasticaProv = integrScolasticaProv;
	}

	public String getCentroDiurnoRiabSemires() {
		return centroDiurnoRiabSemires;
	}

	public void setCentroDiurnoRiabSemires(String centroDiurnoRiabSemires) {
		this.centroDiurnoRiabSemires = centroDiurnoRiabSemires;
	}

	public String getAltroSemires() {
		return altroSemires;
	}

	public void setAltroSemires(String altroSemires) {
		this.altroSemires = altroSemires;
	}

	public String getDescrStrutturaSemires() {
		return descrStrutturaSemires;
	}

	public void setDescrStrutturaSemires(String descrStrutturaSemires) {
		this.descrStrutturaSemires = descrStrutturaSemires;
	}

	public BigDecimal getLuogoStrutturaSemiresId() {
		return luogoStrutturaSemiresId;
	}

	public void setLuogoStrutturaSemiresId(BigDecimal luogoStrutturaSemiresId) {
		this.luogoStrutturaSemiresId = luogoStrutturaSemiresId;
	}

	public BigDecimal getRettaACaricoSemiresId() {
		return rettaACaricoSemiresId;
	}

	public void setRettaACaricoSemiresId(BigDecimal rettaACaricoSemiresId) {
		this.rettaACaricoSemiresId = rettaACaricoSemiresId;
	}

	public String getComunitaMinoriRes() {
		return comunitaMinoriRes;
	}

	public void setComunitaMinoriRes(String comunitaMinoriRes) {
		this.comunitaMinoriRes = comunitaMinoriRes;
	}

	public BigDecimal getTipoComunitaResId() {
		return tipoComunitaResId;
	}

	public void setTipoComunitaResId(BigDecimal tipoComunitaResId) {
		this.tipoComunitaResId = tipoComunitaResId;
	}

	public String getAffidoFamiliareRes() {
		return affidoFamiliareRes;
	}

	public void setAffidoFamiliareRes(String affidoFamiliareRes) {
		this.affidoFamiliareRes = affidoFamiliareRes;
	}

	public String getComunitaTerapeuticaRes() {
		return comunitaTerapeuticaRes;
	}

	public void setComunitaTerapeuticaRes(String comunitaTerapeuticaRes) {
		this.comunitaTerapeuticaRes = comunitaTerapeuticaRes;
	}

	public String getRsaRes() {
		return rsaRes;
	}

	public void setRsaRes(String rsaRes) {
		this.rsaRes = rsaRes;
	}

	public String getDescrStrutturaRes() {
		return descrStrutturaRes;
	}

	public void setDescrStrutturaRes(String descrStrutturaRes) {
		this.descrStrutturaRes = descrStrutturaRes;
	}

	public BigDecimal getLuogoStrutturaResId() {
		return luogoStrutturaResId;
	}

	public void setLuogoStrutturaResId(BigDecimal luogoStrutturaResId) {
		this.luogoStrutturaResId = luogoStrutturaResId;
	}

	public BigDecimal getRettaACaricoResId() {
		return rettaACaricoResId;
	}

	public void setRettaACaricoResId(BigDecimal rettaACaricoResId) {
		this.rettaACaricoResId = rettaACaricoResId;
	}

	public String getSostGenitorialita() {
		return sostGenitorialita;
	}

	public void setSostGenitorialita(String sostGenitorialita) {
		this.sostGenitorialita = sostGenitorialita;
	}

	public String getTrasportoUffIstruzione() {
		return trasportoUffIstruzione;
	}

	public void setTrasportoUffIstruzione(String trasportoUffIstruzione) {
		this.trasportoUffIstruzione = trasportoUffIstruzione;
	}

	public String getTrasportoSociale() {
		return trasportoSociale;
	}

	public void setTrasportoSociale(String trasportoSociale) {
		this.trasportoSociale = trasportoSociale;
	}

	public String getChiEffettuaServizio() {
		return chiEffettuaServizio;
	}

	public void setChiEffettuaServizio(String chiEffettuaServizio) {
		this.chiEffettuaServizio = chiEffettuaServizio;
	}

}