package it.webred.cs.data.model.persist;

import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbTipoAlert;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the CS_ALERT database table.
 * 
 */
@Entity
@Table(name="CS_ALERT")
@NamedQuery(name="CsAlert.findAll", query="SELECT c FROM CsAlert c")
public class CsAlert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_ALERT_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_ALERT_ID_GENERATOR")
	private Long id;

	private String descrizione;

	private Boolean letto;
	
	@Column(name="LETTO_USER")
	private String lettoUser;

	private String tipo;
	
	@ManyToOne
	@JoinColumn(name="TIPO", insertable=false, updatable=false)
	private CsTbTipoAlert tipoAlert;

	@Column(name="TITOLO_DESCRIZIONE")
	private String titoloDescrizione;

	private String url;

	private Boolean visibile;

	//bi-directional many-to-one association to CsACaso
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CASO_ID")
	private CsACaso csACaso;
	
	//uni-directional many-to-one association to CsOOperatore
	@ManyToOne
	@JoinColumn(name="OPERATORE_ID")
	private CsOOperatore csOOperatore1;
	
	//uni-directional many-to-one association to CsOOperatore
	@ManyToOne
	@JoinColumn(name="OP_SETTORE_ID_TO")
	private CsOOperatoreSettore csOpSettore2;

	//bi-directional many-to-one association to CsOOrganizzazione
	@ManyToOne
	@JoinColumn(name = "ORGANIZZAZIONE_ID")
	private CsOOrganizzazione csOOrganizzazione1;
		
	//bi-directional many-to-one association to CsOOrganizzazione
	@ManyToOne
	@JoinColumn(name="ORGANIZZAZIONE_ID_TO")
	private CsOOrganizzazione csOOrganizzazione2;

	// bi-directional many-to-one association to CsOSettore
	@ManyToOne
	@JoinColumn(name = "SETTORE_ID")
	private CsOSettore csOSettore1;
		
	//bi-directional many-to-one association to CsOSettore
	@ManyToOne
	@JoinColumn(name="SETTORE_ID_TO")
	private CsOSettore csOSettore2;
	
	@Column(name="FLG_EMAIL_INVIATA")
	private Boolean emailInviata;
	
	@Column(name="FLG_NOTIFICA_SEGNALANTE")
	private Boolean notificaSegnalante;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INVIO_EMAIL")
	private Date dtInvioEmail;

	public CsAlert() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Boolean getLetto() {
		return this.letto;
	}

	public void setLetto(Boolean letto) {
		this.letto = letto;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTitoloDescrizione() {
		return this.titoloDescrizione;
	}

	public void setTitoloDescrizione(String titoloDescrizione) {
		this.titoloDescrizione = titoloDescrizione;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getVisibile() {
		return this.visibile;
	}

	public void setVisibile(Boolean visibile) {
		this.visibile = visibile;
	}

	public CsACaso getCsACaso() {
		return this.csACaso;
	}

	public void setCsACaso(CsACaso csACaso) {
		this.csACaso = csACaso;
	}

	public CsOOrganizzazione getCsOOrganizzazione1() {
		return this.csOOrganizzazione1;
	}

	public void setCsOOrganizzazione1(CsOOrganizzazione csOOrganizzazione1) {
		this.csOOrganizzazione1 = csOOrganizzazione1;
	}

	public CsOOrganizzazione getCsOOrganizzazione2() {
		return this.csOOrganizzazione2;
	}

	public void setCsOOrganizzazione2(CsOOrganizzazione csOOrganizzazione2) {
		this.csOOrganizzazione2 = csOOrganizzazione2;
	}

	public CsOSettore getCsOSettore1() {
		return this.csOSettore1;
	}

	public void setCsOSettore1(CsOSettore csOSettore1) {
		this.csOSettore1 = csOSettore1;
	}

	public CsOSettore getCsOSettore2() {
		return this.csOSettore2;
	}

	public void setCsOSettore2(CsOSettore csOSettore2) {
		this.csOSettore2 = csOSettore2;
	}

	public CsOOperatore getCsOOperatore1() {
		return csOOperatore1;
	}

	public void setCsOOperatore1(CsOOperatore csOOperatore) {
		this.csOOperatore1 = csOOperatore;
	}

	public Boolean getEmailInviata() {
		return emailInviata;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setEmailInviata(Boolean emailInviata) {
		this.emailInviata = emailInviata;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public Boolean getNotificaSegnalante() {
		return notificaSegnalante;
	}

	public void setNotificaSegnalante(Boolean notificaSegnalante) {
		this.notificaSegnalante = notificaSegnalante;
	}
	
	public CsTbTipoAlert getTipoAlert() {
		return tipoAlert;
	}

	public void setTipoAlert(CsTbTipoAlert tipoAlert) {
		this.tipoAlert = tipoAlert;
	}

	public Date getDtInvioEmail() {
		return dtInvioEmail;
	}

	public void setDtInvioEmail(Date dtInvioEmail) {
		this.dtInvioEmail = dtInvioEmail;
	}

	public CsOOperatoreSettore getCsOpSettore2() {
		return csOpSettore2;
	}

	public void setCsOpSettore2(CsOOperatoreSettore csOpSettore2) {
		this.csOpSettore2 = csOpSettore2;
	}

	public String getLettoUser() {
		return lettoUser;
	}

	public void setLettoUser(String lettoUser) {
		this.lettoUser = lettoUser;
	}
}