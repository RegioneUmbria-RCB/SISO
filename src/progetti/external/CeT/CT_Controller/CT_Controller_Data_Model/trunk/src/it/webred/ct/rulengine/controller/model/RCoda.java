package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the R_CODA database table.
 * 
 */
@Entity
@Table(name="R_CODA")
public class RCoda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long istante;

	private String belfiore;

	private String jobname;

	@Column(name="MAX_TENTATIVI")
	private Long maxTentativi;

	@Column(name="NUM_TENTATIVO")
	private Long numTentativo;

	//bi-directional many-to-one association to RCommand
    @ManyToOne
	@JoinColumn(name="FK_COMMAND")
	private RCommand RCommand;
    
    @Column(name="INIZIO_ESECUZIONE")
	private Long inizioEsecuzione;
    
    public RCoda() {
    }

	public Long getIstante() {
		return this.istante;
	}

	public void setIstante(Long istante) {
		this.istante = istante;
	}

	public String getBelfiore() {
		return this.belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public String getJobname() {
		return this.jobname;
	}

	public void setJobname(String jobname) {
		this.jobname = jobname;
	}

	public Long getMaxTentativi() {
		return this.maxTentativi;
	}

	public void setMaxTentativi(Long maxTentativi) {
		this.maxTentativi = maxTentativi;
	}

	public Long getNumTentativo() {
		return this.numTentativo;
	}

	public void setNumTentativo(Long numTentativo) {
		this.numTentativo = numTentativo;
	}

	public RCommand getRCommand() {
		return this.RCommand;
	}

	public void setRCommand(RCommand RCommand) {
		this.RCommand = RCommand;
	}

	public Long getInizioEsecuzione() {
		return inizioEsecuzione;
	}

	public void setInizioEsecuzione(Long inizioEsecuzione) {
		this.inizioEsecuzione = inizioEsecuzione;
	}
	
}