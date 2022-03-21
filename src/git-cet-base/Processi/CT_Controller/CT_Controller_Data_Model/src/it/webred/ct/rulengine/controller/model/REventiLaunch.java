package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the R_EVENTI_LAUNCH database table.
 * 
 */
@Entity
@Table(name="R_EVENTI_LAUNCH")
public class REventiLaunch implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ISTANTE")
	private Long istante;
	
	@Column(name="BELFIORE")
	private String belfiore;

	@Column(name="COMMAND_ID")
	private Long commandId;

	@Column(name="COMMAND_STATO")
	private Long commandStato;

	@Column(name="COMMAND_TYPE")
	private Long commandType;

	@Column(name="ID_FONTE")
	private Long idFonte;

    public REventiLaunch() {
    }

	public String getBelfiore() {
		return this.belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public Long getCommandId() {
		return this.commandId;
	}

	public void setCommandId(Long commandId) {
		this.commandId = commandId;
	}

	public Long getCommandStato() {
		return this.commandStato;
	}

	public void setCommandStato(Long commandStato) {
		this.commandStato = commandStato;
	}

	public Long getCommandType() {
		return this.commandType;
	}

	public void setCommandType(Long commandType) {
		this.commandType = commandType;
	}

	public Long getIdFonte() {
		return this.idFonte;
	}

	public void setIdFonte(Long idFonte) {
		this.idFonte = idFonte;
	}

	public Long getIstante() {
		return istante;
	}

	public void setIstante(Long istante) {
		this.istante = istante;
	}

}