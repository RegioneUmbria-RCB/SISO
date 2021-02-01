package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the R_COMMAND_LAUNCH database table.
 * 
 */
@Entity
@Table(name="R_COMMAND_LAUNCH")
public class RCommandLaunch implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String belfiore;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="DATE_END")
	private Date dateEnd;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="DATE_START")
	private Date dateStart;

	@Column(name="FK_COMMAND")
	private Long fkCommand;

	private String processid;
	
	@Column(name="ID_SCHED")
	private Long idSched;
	
    public RCommandLaunch() {
    }


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBelfiore() {
		return this.belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public Date getDateEnd() {
		return this.dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateStart() {
		return this.dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Long getFkCommand() {
		return this.fkCommand;
	}

	public void setFkCommand(Long fkCommand) {
		this.fkCommand = fkCommand;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}


	public Long getIdSched() {
		return idSched;
	}


	public void setIdSched(Long idSched) {
		this.idSched = idSched;
	}
	
	
}