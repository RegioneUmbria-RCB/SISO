package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Transient;

@Entity
@Table(name="CS_A_COMPONENTE_ANAG_CASO_GIT")
@NamedQuery(name="CsAComponenteAnagCasoGit.findAll", query="SELECT c FROM CsAComponenteAnagCasoGit c")
public class CsAComponenteAnagCasoGit implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@SequenceGenerator(name="CS_A_COMPONENTE_ANAG_CASO_GIT_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_COMPONENTE_ANAG_CASO_GIT_ID_GENERATOR")
	private BigDecimal id;

	private String cognome;
	
	private String nome;
	
	private String cf;
	@Transient
	private String sesso; 
	 
	@ManyToOne
	@JoinColumn(name="SOGGETTO_ANAGRAFICA_ID")
	private CsASoggettoLAZY csASoggetto;
	
	@Column(name="FLG_SEGNALAZIONE")
	private Boolean flgSegnalazione;
	
	private String segnalazione;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	private String cittadinanza;
	
	@Column(name="INDIRIZZO_RES")
	private String indirizzoRes;
	
	@Column(name="NUM_CIV_RES")
	private String numCivRes;
	
	@Column(name="PROV_RES_COD")
	private String provResCod;
	
	@Column(name="COM_RES_COD")
	private String comResCod;
	
	@Column(name="COM_RES_DES")
	private String comResDes;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_DECESSO")
	private Date dataDecesso;
	
	
	
	@Column(name="INDIRIZZO_DOM")
	private String indirizzoDom;
	
	@Column(name="NUM_CIV_DOM")
	private String numCivDom;
	
	@Column(name="PROV_DOM_COD")
	private String provDomCod;
	
	@Column(name="COM_DOM_COD")
	private String comDomCod;
	
	@Column(name="COM_DOM_DES")
	private String comDomDes;
	
	@Column(name="COGNOME_MEDICO")
	private String cognomeMedico;

	@Column(name="NOME_MEDICO")
	private String nomeMedico;
	
	@Column(name="CODICE_REGIONALE_MEDICO")
	private String codiceRegionaleMedico;
	
	@Column(name="ID_ORIG_WS")
	private String idOrigWs;
	
	@Transient
	private Date dataSceltaMedico;
	@Transient
	private Date dataRevocaMedico;
	
	@Transient
	private String codiceFiscaleMedico;
	
	@Transient
	private String statoCivile;
	
	@Transient
	private String telefono;
	
	
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getIdOrigWs() {
		return idOrigWs;
	}

	public void setIdOrigWs(String idOrigWs) {
		this.idOrigWs = idOrigWs;
	}

	public String getStatoCivile() {
		return statoCivile;
	}

	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

	public Date getDataSceltaMedico() {
		return dataSceltaMedico;
	}

	public void setDataSceltaMedico(Date dataSceltaMedico) {
		this.dataSceltaMedico = dataSceltaMedico;
	}

	public Date getDataRevocaMedico() {
		return dataRevocaMedico;
	}

	public void setDataRevocaMedico(Date dataRevocaMedico) {
		this.dataRevocaMedico = dataRevocaMedico;
	}

	public String getCodiceFiscaleMedico() {
		return codiceFiscaleMedico ;
	}

	public void setCodiceFiscaleMedico(String codiceFiscaleMedico) {
		this.codiceFiscaleMedico = codiceFiscaleMedico;
	}

	public String getCognomeMedico() {
		return cognomeMedico;
	}

	public void setCognomeMedico(String cognomeMedico) {
		this.cognomeMedico = cognomeMedico;
	}

	public String getNomeMedico() {
		return nomeMedico;
	}

	public void setNomeMedico(String nomeMedico) {
		this.nomeMedico = nomeMedico;
	}

	public String getCodiceRegionaleMedico() {
		return codiceRegionaleMedico;
	}

	public void setCodiceRegionaleMedico(String codiceRegionaleMedico) {
		this.codiceRegionaleMedico = codiceRegionaleMedico;
	}

	public String getIndirizzoDom() {
		return indirizzoDom;
	}

	public void setIndirizzoDom(String indirizzoDom) {
		this.indirizzoDom = indirizzoDom;
	}

	public String getNumCivDom() {
		return numCivDom;
	}

	public void setNumCivDom(String numCivDom) {
		this.numCivDom = numCivDom;
	}

	public String getProvDomCod() {
		return provDomCod;
	}

	public void setProvDomCod(String provDomCod) {
		this.provDomCod = provDomCod;
	}

	public String getComDomCod() {
		return comDomCod;
	}

	public void setComDomCod(String comDomCod) {
		this.comDomCod = comDomCod;
	}

	public String getComDomDes() {
		return comDomDes;
	}

	public void setComDomDes(String comDomDes) {
		this.comDomDes = comDomDes;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getIndirizzoRes() {
		return indirizzoRes;
	}

	public void setIndirizzoRes(String indirizzoRes) {
		this.indirizzoRes = indirizzoRes;
	}

	public String getNumCivRes() {
		return numCivRes;
	}

	public void setNumCivRes(String numCivRes) {
		this.numCivRes = numCivRes;
	}

	public String getProvResCod() {
		return provResCod;
	}

	public void setProvResCod(String provResCod) {
		this.provResCod = provResCod;
	}

	public String getComResCod() {
		return comResCod;
	}

	public void setComResCod(String comResCod) {
		this.comResCod = comResCod;
	}

	public String getComResDes() {
		return comResDes;
	}

	public void setComResDes(String comResDes) {
		this.comResDes = comResDes;
	}

	public Date getDataDecesso() {
		return dataDecesso;
	}

	public void setDataDecesso(Date dataDecesso) {
		this.dataDecesso = dataDecesso;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public Boolean getFlgSegnalazione() {
		return flgSegnalazione;
	}

	public void setFlgSegnalazione(Boolean flgSegnalazione) {
		this.flgSegnalazione = flgSegnalazione;
	}

	public String getSegnalazione() {
		return segnalazione;
	}

	public void setSegnalazione(String segnalazione) {
		this.segnalazione = segnalazione;
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

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

 
	 
}
 
 
	
	

