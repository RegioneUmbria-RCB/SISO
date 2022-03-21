package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the R_PROCESS_MONITOR database table.
 * 
 */
@Entity
@Table(name="R_PROCESS_MONITOR")
public class RProcessMonitor implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RProcessMonitorPK id;

	private Long istante;

	private String processid;

	//bi-directional many-to-one association to RCommand
    @ManyToOne
	@JoinColumn(name="FK_COMMAND", referencedColumnName="ID", updatable=false, insertable=false)
	private RCommand RCommand;
    
  //bi-directional many-to-one association to RAnagStati
    @ManyToOne
	@JoinColumn(name="FK_STATO", referencedColumnName="ID")
	private RAnagStati RAnagStato;
    
    public RProcessMonitor() {
    }

	public RProcessMonitorPK getId() {
		return this.id;
	}

	public void setId(RProcessMonitorPK id) {
		this.id = id;
	}
	
	public Long getIstante() {
		return this.istante;
	}

	public void setIstante(Long istante) {
		this.istante = istante;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public RCommand getRCommand() {
		return this.RCommand;
	}

	public void setRCommand(RCommand RCommand) {
		this.RCommand = RCommand;
	}

	public RAnagStati getRAnagStato() {
		return RAnagStato;
	}

	public void setRAnagStato(RAnagStati rAnagStato) {
		RAnagStato = rAnagStato;
	}
	
}