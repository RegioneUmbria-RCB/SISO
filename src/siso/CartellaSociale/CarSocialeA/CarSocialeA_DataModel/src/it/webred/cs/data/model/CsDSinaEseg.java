package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CS_D_SINA_ESEG")
@NamedQuery(name = "CsDSinaEseg.findAll", query = "SELECT c FROM CsDSinaEseg c")
public class CsDSinaEseg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private CsDSinaEsegPK id;
	
	// bi-directional many-to-one association to CsDSina
	@ManyToOne
	@JoinColumn(name = "SINA_ID", insertable = false, updatable = false)
	private CsDSina csDSina;

	// bi-directional many-to-one association to CsDSinaDomanda
	@ManyToOne
	@JoinColumn(name = "DOMANDA_ID", insertable = false, updatable = false)
	private CsTbSinaDomanda csTbSinaDomanda;

	// bi-directional many-to-one association to CsDSinaRisposta
	@ManyToOne
	@JoinColumn(name = "RISPOSTA_ID", insertable = false, updatable = false)
	private CsTbSinaRisposta csTbSinaRisposta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_MOD")
	private Date dtMod;

	@Column(name = "USER_INS")
	private String userIns;

	@Column(name = "USER_MOD")
	private String userMod;


	public CsDSina getCsDSina() {
		return csDSina;
	}

	public CsDSinaEsegPK getId() {
		return this.id;
	}

	public void setId(CsDSinaEsegPK id) {
		this.id = id;
	}
	
	public void setCsDSina(CsDSina csDSina) {
		this.csDSina = csDSina;
	}

	public CsTbSinaDomanda getCsTbSinaDomanda() {
		return csTbSinaDomanda;
	}

	public void setCsTbSinaDomanda(CsTbSinaDomanda csTbSinaDomanda) {
		this.csTbSinaDomanda = csTbSinaDomanda;
	}

	public CsTbSinaRisposta getCsTbSinaRisposta() {
		return csTbSinaRisposta;
	}

	public void setCsTbSinaRisposta(CsTbSinaRisposta csTbSinaRisposta) {
		this.csTbSinaRisposta = csTbSinaRisposta;
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

	public String getUserMod() {
		return userMod;
	}

	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}

	
}
