package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the CS_ALERT database table.
 * 
 */
@Entity
@Table(name="CS_ALERT")
public class CsAlertBASIC implements Serializable {
	private static final long serialVersionUID = 1L; 

	@Id
	private Long id;

	private String descrizione;

	private Boolean letto;

	private String tipo;

	@ManyToOne
	@JoinColumn(name="TIPO", insertable=false, updatable=false)
	private CsTbTipoAlert tipoAlert;

	@Column(name="TITOLO_DESCRIZIONE")
	private String titoloDescrizione;

	private String url;

	private Boolean visibile;


	@Basic
	@Column(name= "CASO_ID", insertable=false, updatable=false)
	protected Long csACasoId;
	
	
	@Basic
	@Column(name= "OPERATORE_ID", insertable=false, updatable=false)
	protected Long csOOperatore1Id;
	
	@Basic
	@Column(name= "OP_SETTORE_ID_TO")
	protected Long csOOpSettore2Id;
	
	@Basic
	@Column(name= "ORGANIZZAZIONE_ID", insertable=false, updatable=false)
	protected Long csOOrganizzazione1Id;
	
	@Basic
	@Column(name= "ORGANIZZAZIONE_ID_TO", insertable=false, updatable=false)
	protected Long csOOrganizzazione2Id;

	@Basic
	@Column(name= "SETTORE_ID", insertable=false, updatable=false)
	protected Long csOSettore1Id;
	

	@Basic
	@Column(name= "SETTORE_ID_TO", insertable=false, updatable=false)
	protected Long csOSettore2Id;
	
	
	//uni-directional many-to-one association to CsOOperatore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OPERATORE_ID")
	private CsOOperatoreBASIC csOOperatore1;
	
	/*	@ManyToOne
	@JoinColumn(name="OPERATORE_ID_TO")
	private CsOOperatore csOOperatore2;*/
	
	@ManyToOne
	@JoinColumn(name="OP_SETTORE_ID_TO",insertable=false, updatable=false)
	private CsOOperatoreSettore csOpSettore2;
	
	//bi-directional many-to-one association to CsOOrganizzazione
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZZAZIONE_ID")
	private CsOOrganizzazione csOOrganizzazione1;
		
	//bi-directional many-to-one association to CsOOrganizzazione
	@ManyToOne
	@JoinColumn(name="ORGANIZZAZIONE_ID_TO")
	private CsOOrganizzazione csOOrganizzazione2;

	// bi-directional many-to-one association to CsOSettore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SETTORE_ID")
	private CsOSettoreBASIC csOSettore1;
		
	//bi-directional many-to-one association to CsOSettore
	@ManyToOne
	@JoinColumn(name="SETTORE_ID_TO")
	private CsOSettoreBASIC csOSettore2;


	public CsOOperatoreBASIC getCsOOperatore1() {
		return csOOperatore1;
	}

	public void setCsOOperatore1(CsOOperatoreBASIC csOOperatore1) {
		this.csOOperatore1 = csOOperatore1;
	}

	public CsOOrganizzazione getCsOOrganizzazione1() {
		return csOOrganizzazione1;
	}

	public void setCsOOrganizzazione1(CsOOrganizzazione csOOrganizzazione1) {
		this.csOOrganizzazione1 = csOOrganizzazione1;
	}

	public CsOOrganizzazione getCsOOrganizzazione2() {
		return csOOrganizzazione2;
	}

	public void setCsOOrganizzazione2(CsOOrganizzazione csOOrganizzazione2) {
		this.csOOrganizzazione2 = csOOrganizzazione2;
	}

	public CsOSettoreBASIC getCsOSettore1() {
		return csOSettore1;
	}

	public void setCsOSettore1(CsOSettoreBASIC csOSettore1) {
		this.csOSettore1 = csOSettore1;
	}

	public CsOSettoreBASIC getCsOSettore2() {
		return csOSettore2;
	}

	public void setCsOSettore2(CsOSettoreBASIC csOSettore2) {
		this.csOSettore2 = csOSettore2;
	}

	public CsAlertBASIC() {
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

	public Long getCsACasoId() {
		return csACasoId;
	}

	public void setCsACasoId(Long csACasoId) {
		this.csACasoId = csACasoId;
	}

	public Long getCsOOperatore1Id() {
		return csOOperatore1Id;
	}

	public void setCsOOperatore1Id(Long csOOperatore1Id) {
		this.csOOperatore1Id = csOOperatore1Id;
	}

	public Long getCsOOrganizzazione1Id() {
		return csOOrganizzazione1Id;
	}

	public void setCsOOrganizzazione1Id(Long csOOrganizzazione1Id) {
		this.csOOrganizzazione1Id = csOOrganizzazione1Id;
	}

	public Long getCsOOrganizzazione2Id() {
		return csOOrganizzazione2Id;
	}

	public void setCsOOrganizzazione2Id(Long csOOrganizzazione2Id) {
		this.csOOrganizzazione2Id = csOOrganizzazione2Id;
	}

	public Long getCsOSettore1Id() {
		return csOSettore1Id;
	}

	public void setCsOSettore1Id(Long csOSettore1Id) {
		this.csOSettore1Id = csOSettore1Id;
	}

	public Long getCsOSettore2Id() {
		return csOSettore2Id;
	}

	public void setCsOSettore2Id(Long csOSettore2Id) {
		this.csOSettore2Id = csOSettore2Id;
	}

	public CsTbTipoAlert getTipoAlert() {
		return tipoAlert;
	}

	public void setTipoAlert(CsTbTipoAlert tipoAlert) {
		this.tipoAlert = tipoAlert;
	}

	public String getLabelTipo(){
		return tipoAlert.getDescrizione();
	}


	public CsOOperatoreSettore getCsOpSettore2() {
		return csOpSettore2;
	}


	public void setCsOpSettore2(CsOOperatoreSettore csOpSettore2) {
		this.csOpSettore2 = csOpSettore2;
	}
}