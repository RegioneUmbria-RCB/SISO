package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CS_ALERT_CONFIG database table.
 * 
 */
@Entity
@Table(name="CS_ALERT_CONFIG")
@NamedQuery(name="CsAlertConfig.findAll", query="SELECT c FROM CsAlertConfig c")
public class CsAlertConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String tipo;
	
	//uni-directional many-to-one association to CsOOperatore
	@ManyToOne
	@JoinColumn(name="OPERATORE_ID_DEFAULT")
	private CsOOperatore csOOperatoreDefault;

	//bi-directional many-to-one association to CsOOrganizzazione
	@ManyToOne
	@JoinColumn(name = "ORGANIZZAZIONE_ID_DEFAULT")
	private CsOOrganizzazione csOOrganizzazioneDefault;

	// bi-directional many-to-one association to CsOSettore
	@ManyToOne
	@JoinColumn(name = "SETTORE_ID_DEFAULT")
	private CsOSettore csOSettoreDefault;

	public CsAlertConfig() {
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public CsOOperatore getCsOOperatoreDefault() {
		return csOOperatoreDefault;
	}

	public void setCsOOperatoreDefault(CsOOperatore csOOperatoreDefault) {
		this.csOOperatoreDefault = csOOperatoreDefault;
	}

	public CsOOrganizzazione getCsOOrganizzazioneDefault() {
		return csOOrganizzazioneDefault;
	}

	public void setCsOOrganizzazioneDefault(
			CsOOrganizzazione csOOrganizzazioneDefault) {
		this.csOOrganizzazioneDefault = csOOrganizzazioneDefault;
	}

	public CsOSettore getCsOSettoreDefault() {
		return csOSettoreDefault;
	}

	public void setCsOSettoreDefault(CsOSettore csOSettoreDefault) {
		this.csOSettoreDefault = csOSettoreDefault;
	}

}