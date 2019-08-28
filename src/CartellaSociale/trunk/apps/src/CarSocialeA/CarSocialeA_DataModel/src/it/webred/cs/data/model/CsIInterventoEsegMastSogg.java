package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_I_INTERVENTO_ESEG_MAST_SOGG")
public class CsIInterventoEsegMastSogg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsIInterventoEsegMastSoggPK id;
	
	@ManyToOne
	@JoinColumn(name="CASO_ID")
	private CsACaso caso;

	@Column
	private String cognome;

	@Column
	private String nome;
	
	@Column 
	private String cittadinanza;
	
	@Column(name="ANNO_NASCITA")
	private Integer annoNascita;
	
	@Column(name="FLAG_RIFERIMENTO")
	private Boolean riferimento;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;
	
	@Column(name="USER_INS")
	private String userIns;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	@Column(name="USER_MOD")
	private String userMod;
	
    @MapsId("masterId")
    @ManyToOne (optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="INT_ESEG_MAST_ID", referencedColumnName="id")
	private CsIInterventoEsegMast master;
	

	public CsIInterventoEsegMastSogg() {
		id = new CsIInterventoEsegMastSoggPK();
	}

	public CsACaso getCaso() {
		return caso;
	}

	public void setCaso(CsACaso caso) {
		this.caso = caso;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getCognome() {
		return cognome;
	}

	public String getNome() {
		return nome;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getCf() {
		return id.getCf();
	}

	public CsIInterventoEsegMast getMaster() {
		return master;
	}

	public void setCf(String cf) {
		id.setCf(cf);;
	}

	public void setMaster(CsIInterventoEsegMast master) {
		this.master = master;
	}

	public Boolean getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(Boolean riferimento) {
		this.riferimento = riferimento;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public CsIInterventoEsegMastSoggPK getId() {
		return id;
	}

	public void setId(CsIInterventoEsegMastSoggPK id) {
		this.id = id;
	}

	public Integer getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(Integer annoNascita) {
		this.annoNascita = annoNascita;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public String getUserMod() {
		return userMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}

}