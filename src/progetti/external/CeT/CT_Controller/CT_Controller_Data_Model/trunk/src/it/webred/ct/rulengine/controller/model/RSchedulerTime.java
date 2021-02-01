package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the R_SCHEDULER_TIME database table.
 * 
 */
@Entity
@Table(name="R_SCHEDULER_TIME")
public class RSchedulerTime implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_r_scheduler_time" )
	@SequenceGenerator(name="seq_r_scheduler_time", sequenceName="SEQ_R_SCHEDULER_TIME", allocationSize=1)
	private Long id;

	private String belfiore;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="DT_START")
	private Date dtStart;
    
    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="DT_END")
	private Date dtEnd;

	@Column(name="OGNI_GIORNI")
	private Long ogniGiorni;

	@Column(name="OGNI_MESI")
	private Long ogniMesi;

	@Column(name="OGNI_MINUTI")
	private Long ogniMinuti;
	
	@Column(name="OGNI_ORE")
	private Long ogniOre;

	@Column(name="OGNI_SETTIMANE")
	private Long ogniSettimane;

	//bi-directional many-to-one association to RCommand
    @ManyToOne
	@JoinColumn(name="FK_COMMAND")
	private RCommand RCommand;
    
    @Column(name="JOBNAME_REF")
    private String jobnameRef;
    
    public RSchedulerTime() {
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

	public Long getOgniGiorni() {
		return this.ogniGiorni;
	}

	public void setOgniGiorni(Long ogniGiorni) {
		this.ogniGiorni = ogniGiorni;
	}

	public Long getOgniMesi() {
		return this.ogniMesi;
	}

	public void setOgniMesi(Long ogniMesi) {
		this.ogniMesi = ogniMesi;
	}

	public Long getOgniOre() {
		return this.ogniOre;
	}

	public void setOgniOre(Long ogniOre) {
		this.ogniOre = ogniOre;
	}

	public Long getOgniSettimane() {
		return this.ogniSettimane;
	}

	public void setOgniSettimane(Long ogniSettimane) {
		this.ogniSettimane = ogniSettimane;
	}

	public RCommand getRCommand() {
		return this.RCommand;
	}

	public void setRCommand(RCommand RCommand) {
		this.RCommand = RCommand;
	}

	public Date getDtStart() {
		return dtStart;
	}

	public void setDtStart(Date dtStart) {
		this.dtStart = dtStart;
	}

	public Date getDtEnd() {
		return dtEnd;
	}

	public void setDtEnd(Date dtEnd) {
		this.dtEnd = dtEnd;
	}

	public String getJobnameRef() {
		return jobnameRef;
	}

	public void setJobnameRef(String jobnameRef) {
		this.jobnameRef = jobnameRef;
	}

	public Long getOgniMinuti() {
		return ogniMinuti;
	}

	public void setOgniMinuti(Long ogniMinuti) {
		this.ogniMinuti = ogniMinuti;
	}
	
}