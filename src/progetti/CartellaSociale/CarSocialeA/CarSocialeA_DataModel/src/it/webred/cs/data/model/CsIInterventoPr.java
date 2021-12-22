package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CS_A_DATI_SOCIALI database table.
 * 
 */
@Entity
@Table(name="CS_I_INTERVENTO_PR")
public class CsIInterventoPr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_I_INTERVENTO_PR_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_INTERVENTO_PR_ID_GENERATOR")
	private Long id;
	
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

	@Column(name="CODICE_FIN1")
	private String codiceFin1;
	
	//Settore EROGANTE
	@Column(name="FF_ORIGINE_ID")
	private Long ffOrigineId; 
	
	@ManyToOne
	@JoinColumn(name="SETTORE_TITOLARE_ID")
	private CsOSettore settoreTitolare;

	// SISO-663 SM
	@ManyToOne
	@JoinColumn(name="SETTORE_GESTORE_ID")
	private CsOSettore settoreGestore;
	// -=-
	
	@ManyToOne
	@JoinColumn(name="TITOLO_STUDIO_ID")
	private CsTbTitoloStudio csTbTitoloStudio;
	
	@ManyToOne
	@JoinColumn(name="COND_LAVORO_ID")
	private CsTbCondLavoro csTbCondLavoro;
	
	@ManyToOne
	@JoinColumn(name="ING_MERCATO_FK")
	private CsTbIngMercato csTbIngMercato;
	
	@ManyToOne
	@JoinColumn(name="SETT_IMPIEGO_ID")
	private CsTbSettoreImpiego csTbSettoreImpiego;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="PROGETTO_ID")
	private ArFfProgetto progetto;
		
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="PROGETTO_ATTIVITA_ID")
	private ArFfProgettoAttivita progettoAttivita;
	
	//bi-directional one-to-one association to CsIAffidoFam
	@OneToOne(mappedBy="csIInterventoPr",cascade = CascadeType.ALL , fetch=FetchType.EAGER, orphanRemoval=true )
	@PrimaryKeyJoinColumn
	private CsIInterventoPrFse csIInterventoPrFse;
	
	@OneToOne(mappedBy="csIInterventoPr",cascade = CascadeType.ALL, orphanRemoval=true)
	@PrimaryKeyJoinColumn
	private CsIInterventoPrFseSiru csIInterventoPrFseSiru;
	
	//SISO-1131
	@ManyToOne
	@JoinColumn(name="ID_PROGETTO_ALTRO")
	private CsTbProgettoAltro csTbProgettoAltro;
	
	@Column(name="FLAG_SERVIZIO_AMBITO")
	private Boolean servizioAmbito;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public CsTbTitoloStudio getCsTbTitoloStudio() {
		return csTbTitoloStudio;
	}

	public void setCsTbTitoloStudio(CsTbTitoloStudio csTbTitoloStudio) {
		this.csTbTitoloStudio = csTbTitoloStudio;
	}

	public CsTbCondLavoro getCsTbCondLavoro() {
		return csTbCondLavoro;
	}

	public void setCsTbCondLavoro(CsTbCondLavoro csTbCondLavoro) {
		this.csTbCondLavoro = csTbCondLavoro;
	}

	public CsTbIngMercato getCsTbIngMercato() {
		return csTbIngMercato;
	}

	public void setCsTbIngMercato(CsTbIngMercato csTbIngMercato) {
		this.csTbIngMercato = csTbIngMercato;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CsIInterventoPrFse getCsIInterventoPrFse() {
		return csIInterventoPrFse;
	}

	public void setCsIInterventoPrFse(CsIInterventoPrFse csIInterventoPrFse) {
		this.csIInterventoPrFse = csIInterventoPrFse;
	}

	public Long getFfOrigineId() {
		return ffOrigineId;
	}

	public void setFfOrigineId(Long ffOrigineId) {
		this.ffOrigineId = ffOrigineId;
	}

	public CsOSettore getSettoreTitolare() {
		return settoreTitolare;
	}

	public void setSettoreTitolare(CsOSettore settoreTitolare) {
		this.settoreTitolare = settoreTitolare;
	}

	public String getCodiceFin1() {
		return codiceFin1;
	}

	public void setCodiceFin1(String codiceFin1) {
		this.codiceFin1 = codiceFin1;
	}

	// SISO-663 SM
	public CsOSettore getSettoreGestore() {
		return settoreGestore;
	}

	public void setSettoreGestore(CsOSettore settoreGestore) {
		this.settoreGestore = settoreGestore;
	}
	// -=-

	public CsIInterventoPrFseSiru getCsIInterventoPrFseSiru() {
		return csIInterventoPrFseSiru;
	}

	public void setCsIInterventoPrFseSiru(
			CsIInterventoPrFseSiru csIInterventoPrFseSiru) {
		this.csIInterventoPrFseSiru = csIInterventoPrFseSiru;
	}

	public CsTbSettoreImpiego getCsTbSettoreImpiego() {
		return csTbSettoreImpiego;
	}

	public void setCsTbSettoreImpiego(CsTbSettoreImpiego csTbSettoreImpiego) {
		this.csTbSettoreImpiego = csTbSettoreImpiego;
	}
	//SISO-1131

	/**
	 * @return the csTbProgettoAltro
	 */
	public CsTbProgettoAltro getCsTbProgettoAltro() {
		return csTbProgettoAltro;
	}

	/**
	 * @param csTbProgettoAltro the csTbProgettoAltro to set
	 */
	public void setCsTbProgettoAltro(CsTbProgettoAltro csTbProgettoAltro) {
		this.csTbProgettoAltro = csTbProgettoAltro;
	}

	public Boolean getServizioAmbito() {
		return servizioAmbito;
	}

	public void setServizioAmbito(Boolean servizioAmbito) {
		this.servizioAmbito = servizioAmbito;
	}

	public ArFfProgetto getProgetto() {
		return progetto;
	}

	public void setProgetto(ArFfProgetto progetto) {
		this.progetto = progetto;
	}

	public ArFfProgettoAttivita getProgettoAttivita() {
		return progettoAttivita;
	}

	public void setProgettoAttivita(ArFfProgettoAttivita progettoAttivita) {
		this.progettoAttivita = progettoAttivita;
	}
}