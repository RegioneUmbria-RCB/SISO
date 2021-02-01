package it.webred.ct.config.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the AM_AUDIT_DECODE database table.
 * 
 */
@Entity
@Table(name="AM_AUDIT_DECODE")
public class AmAuditDecode implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AmAuditDecodePK id;

	@Column(name="CAMPO_CHIAVE")
	private String campoChiave;

	@Column(name="CLASS_NAME")
	private String className;

	private String descrizione;

	@Column(name="METHOD_NAME")
	private String methodName;

	@Column(name="FK_AM_FONTE_TIPOINFO")
	private String fkAmFonteTipoinfo;

	//uni-directional one-to-one association to AmFonte
	@OneToOne
	@JoinColumn(name="FK_AM_FONTE")
	private AmFonte amFonte;

    public AmAuditDecode() {
    }

	public AmAuditDecodePK getId() {
		return this.id;
	}

	public void setId(AmAuditDecodePK id) {
		this.id = id;
	}
	
	public String getCampoChiave() {
		return this.campoChiave;
	}

	public void setCampoChiave(String campoChiave) {
		this.campoChiave = campoChiave;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getFkAmFonteTipoinfo() {
		return fkAmFonteTipoinfo;
	}

	public void setFkAmFonteTipoinfo(String fkAmFonteTipoinfo) {
		this.fkAmFonteTipoinfo = fkAmFonteTipoinfo;
	}

	public AmFonte getAmFonte() {
		return this.amFonte;
	}

	public void setAmFonte(AmFonte amFonte) {
		this.amFonte = amFonte;
	}
	
}