package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CS_D_SINA_ESEG")
public class CsDSinaEseg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private CsDSinaEsegPK id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_INS")
	private Date dtIns;

	@Column(name = "USER_INS")
	private String userIns;
	
	// bi-directional many-to-one association to CsDSinaDomanda
	@ManyToOne
	@JoinColumn(name = "DOMANDA_ID", insertable = false, updatable = false)
	private CsTbSinaDomanda csTbSinaDomanda;

	// bi-directional many-to-one association to CsDSinaRisposta
	@ManyToOne
	@JoinColumn(name = "RISPOSTA_ID", insertable = false, updatable = false)
	private CsTbSinaRisposta csTbSinaRisposta;


	public CsDSinaEsegPK getId() {
		return this.id;
	}

	public void setId(CsDSinaEsegPK id) {
		this.id = id;
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

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

}
