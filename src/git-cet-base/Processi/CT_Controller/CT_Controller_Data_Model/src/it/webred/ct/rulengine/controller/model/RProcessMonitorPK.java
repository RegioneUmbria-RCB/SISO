package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the R_PROCESS_MONITOR database table.
 * 
 */
@Embeddable
public class RProcessMonitorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String belfiore;

	@Column(name="FK_COMMAND")
	private Long fkCommand;

	
    public RProcessMonitorPK() {
    }
    
    
	public RProcessMonitorPK(String belfiore, Long fkCommand) {
		super();
		this.belfiore = belfiore;
		this.fkCommand = fkCommand;
	}


	public String getBelfiore() {
		return this.belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public Long getFkCommand() {
		return this.fkCommand;
	}
	public void setFkCommand(Long fkCommand) {
		this.fkCommand = fkCommand;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RProcessMonitorPK)) {
			return false;
		}
		RProcessMonitorPK castOther = (RProcessMonitorPK)other;
		return 
			this.belfiore.equals(castOther.belfiore)
			&& (this.fkCommand == castOther.fkCommand);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.belfiore.hashCode();
		hash = hash * prime + ((int) (this.fkCommand ^ (this.fkCommand >>> 32)));
		
		return hash;
    }
}