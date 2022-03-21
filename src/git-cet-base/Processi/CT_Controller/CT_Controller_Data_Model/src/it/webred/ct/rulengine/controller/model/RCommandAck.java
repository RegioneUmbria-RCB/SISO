package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the R_COMMAND_ACK database table.
 * 
 */
@Entity
@Table(name="R_COMMAND_ACK")
public class RCommandAck implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="ACK_NAME")
	private String ackName;

	@Column(name="FK_COMMAND_LAUNCH")
	private Long fkCommandLaunch;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="LOG_DATE")
	private Date logDate;

	private String message;

	//bi-directional many-to-one association to RCommand
    @ManyToOne
	@JoinColumn(name="FK_COMMAND")
	private RCommand RCommand;

    public RCommandAck() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAckName() {
		return this.ackName;
	}

	public void setAckName(String ackName) {
		this.ackName = ackName;
	}

	public Long getFkCommandLaunch() {
		return this.fkCommandLaunch;
	}

	public void setFkCommandLaunch(Long fkCommandLaunch) {
		this.fkCommandLaunch = fkCommandLaunch;
	}

	public Date getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RCommand getRCommand() {
		return this.RCommand;
	}

	public void setRCommand(RCommand RCommand) {
		this.RCommand = RCommand;
	}
	
}