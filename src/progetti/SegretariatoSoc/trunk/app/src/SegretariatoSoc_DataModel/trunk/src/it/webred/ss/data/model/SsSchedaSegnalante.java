package it.webred.ss.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import it.webred.ss.data.model.tb.AmTabComuni;
import it.webred.ss.data.model.tb.CsOSettoreLIGHT;
import it.webred.ss.data.model.tb.CsTbStatoCivile;
import it.webred.ss.data.model.tb.CsTbTipoRapportoCon;


/**
 * The persistent class for the SS_SCHEDA_SEGNALANTE database table.
 * 
 */
@Entity
@Table(name="SS_SCHEDA_SEGNALANTE")
@NamedQuery(name="SsSchedaSegnalante.findAll", query="SELECT c FROM SsSchedaSegnalante c")
public class SsSchedaSegnalante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_SCHEDA_SEGNALANTE_ID_GENERATOR", sequenceName="SQ_SS_SCHEDA_SEGNALANTE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_SCHEDA_SEGNALANTE_ID_GENERATOR")
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	private String cf;
	
	private String ente_servizio;
	
	private String ruolo;
	
	private String telefono;
	
	private String cel;
	
	private String email;
	
	private String via;
	
	private String comune;
	
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
	private String sesso;
	
	@Column(name="COD_STATO_CIVILE")
	private String codStatoCivile;
	
	@Column(name="COMUNE_NASCITA_COD")
	private String comuneNascitaCod;
	
	@Column(name="COMUNE_NASCITA_DES")
	private String comuneNascitaDes;
	
	@Column(name="PROV_NASCITA_COD")
	private String provNascitaCod;
	
	@Column(name="STATO_NASCITA_COD")
	private String statoNascitaCod;
	
	@Column(name="STATO_NASCITA_DES")
	private String statoNascitaDes;
	
	@Column(name="RELAZIONE_ID")
	private Long relazioneId;

	@Column(name="CS_O_SETTORE_ID")
	private Long csOSettoreId;		//SISO-346
	
	private Boolean affidatario; //SISO-906

	@ManyToOne 
	@JoinColumn(name="COD_STATO_CIVILE", insertable=false, updatable=false)
	private CsTbStatoCivile tbStatoCivile;

	@ManyToOne 
	@JoinColumn(name="RELAZIONE_ID", insertable=false, updatable=false)
	private CsTbTipoRapportoCon tbRelazione;
	
	@ManyToOne
	@JoinColumn(name="COMUNE", insertable=false, updatable=false)
	private AmTabComuni tbComuneResidenza;
	
	@ManyToOne
	@JoinColumn(name="CS_O_SETTORE_ID", insertable=false, updatable=false)
	private CsOSettoreLIGHT settore;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEnte_servizio() {
		return ente_servizio;
	}

	public void setEnte_servizio(String ente_servizio) {
		this.ente_servizio = ente_servizio;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getComuneNascitaCod() {
		return comuneNascitaCod;
	}

	public void setComuneNascitaCod(String comuneNascitaCod) {
		this.comuneNascitaCod = comuneNascitaCod;
	}

	public String getComuneNascitaDes() {
		return comuneNascitaDes;
	}

	public void setComuneNascitaDes(String comuneNascitaDes) {
		this.comuneNascitaDes = comuneNascitaDes;
	}

	public String getProvNascitaCod() {
		return provNascitaCod;
	}

	public void setProvNascitaCod(String provNascitaCod) {
		this.provNascitaCod = provNascitaCod;
	}

	public String getStatoNascitaCod() {
		return statoNascitaCod;
	}

	public void setStatoNascitaCod(String statoNascitaCod) {
		this.statoNascitaCod = statoNascitaCod;
	}

	public String getStatoNascitaDes() {
		return statoNascitaDes;
	}

	public void setStatoNascitaDes(String statoNascitaDes) {
		this.statoNascitaDes = statoNascitaDes;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getCodStatoCivile() {
		return codStatoCivile;
	}

	public void setCodStatoCivile(String codStatoCivile) {
		this.codStatoCivile = codStatoCivile;
	}
	
	public Long getRelazioneId() {
		return relazioneId;
	}

	public void setRelazioneId(Long relazioneId) {
		this.relazioneId = relazioneId;
	}

	public String getLuogoDiNascita(){
		String s="";
		if(this.comuneNascitaDes!=null)
			s = this.comuneNascitaDes+" ("+this.provNascitaCod+")";
		else if(this.statoNascitaDes!=null)
			s = statoNascitaDes;
		return s;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Long getCsOSettoreId() {
		return csOSettoreId;
	}

	public void setCsOSettoreId(Long csOSettoreId) {
		this.csOSettoreId = csOSettoreId;
	}
    //SISO-906
	public Boolean getAffidatario() {
		return affidatario;
	}

	public void setAffidatario(Boolean affidatario) {
		this.affidatario = affidatario;
	}
	
	public CsTbStatoCivile getTbStatoCivile() {
		return tbStatoCivile;
	}

	public void setTbStatoCivile(CsTbStatoCivile tbStatoCivile) {
		this.tbStatoCivile = tbStatoCivile;
	}

	public CsTbTipoRapportoCon getTbRelazione() {
		return tbRelazione;
	}

	public void setTbRelazione(CsTbTipoRapportoCon tbRelazione) {
		this.tbRelazione = tbRelazione;
	}
	
	public AmTabComuni getTbComuneResidenza() {
		return tbComuneResidenza;
	}

	public void setTbComuneResidenza(AmTabComuni tbComuneResidenza) {
		this.tbComuneResidenza = tbComuneResidenza;
	}

	public CsOSettoreLIGHT getSettore() {
		return settore;
	}

	public void setSettore(CsOSettoreLIGHT settore) {
		this.settore = settore;
	}

	public String getStampaDesComuneResidenza (){
		return this.tbComuneResidenza != null ? 
				tbComuneResidenza.getDenominazione() + " (" + tbComuneResidenza.getSiglaProv() + ")" : "";
	}
}