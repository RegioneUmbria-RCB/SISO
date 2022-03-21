package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CS_O_OPSETTORE_ALERT_CONFIG")
public class CsOOpsettoreAlertConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsOOpsettoreAlertConfigPK id;
	
	@ManyToOne
	@JoinColumn(name="OPERATORE_SETTORE_ID", insertable=false, updatable=false)
	private CsOOperatoreSettore opSettore;
	
	@ManyToOne
	@JoinColumn(name="TIPO_COD", insertable=false, updatable=false)
	private CsTbTipoAlert csTbTipoAlert;
	
	@Column(name="FLG_NOTIFICA")
	private boolean flgNotifica;
	
	@Column(name="FLG_EMAIL")
	private boolean flgEmail;

	public boolean isFlgNotifica() {
		return flgNotifica;
	}

	public boolean isFlgEmail() {
		return flgEmail;
	}

	public void setFlgNotifica(boolean flgNotifica) {
		this.flgNotifica = flgNotifica;
	}

	public void setFlgEmail(boolean flgEmail) {
		this.flgEmail = flgEmail;
	}

	public CsOOpsettoreAlertConfigPK getId() {
		return id;
	}

	public CsOOperatoreSettore getOpSettore() {
		return opSettore;
	}

	public void setId(CsOOpsettoreAlertConfigPK id) {
		this.id = id;
	}

	public void setOpSettore(CsOOperatoreSettore opSettore) {
		this.opSettore = opSettore;
	}

	public CsTbTipoAlert getCsTbTipoAlert() {
		return csTbTipoAlert;
	}

	public void setCsTbTipoAlert(CsTbTipoAlert csTbTipoAlert) {
		this.csTbTipoAlert = csTbTipoAlert;
	}
	
}