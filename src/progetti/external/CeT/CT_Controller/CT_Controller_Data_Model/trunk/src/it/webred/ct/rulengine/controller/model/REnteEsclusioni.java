package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the R_ENTE_ESCLUSIONI database table.
 * 
 */
@Entity
@Table(name="R_ENTE_ESCLUSIONI")
public class REnteEsclusioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FK_COMMAND")
	private Long fkCommand;

	@Column(name="ENTE_OFF")
	private String enteOff;

	@Column(name="ENTE_ON")
	private String enteOn;

    public REnteEsclusioni() {
    }

	public Long getFkCommand() {
		return this.fkCommand;
	}

	public void setFkCommand(Long fkCommand) {
		this.fkCommand = fkCommand;
	}

	public String getEnteOff() {
		return this.enteOff;
	}

	public void setEnteOff(String enteOff) {
		this.enteOff = enteOff;
	}

	public String getEnteOn() {
		return this.enteOn;
	}

	public void setEnteOn(String enteOn) {
		this.enteOn = enteOn;
	}

}