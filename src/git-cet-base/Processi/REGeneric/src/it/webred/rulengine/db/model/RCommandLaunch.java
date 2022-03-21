package it.webred.rulengine.db.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * The persistent class for the R_COMMAND_LAUNCH database table.
 * 
 */
@Entity
@Table(name="R_COMMAND_LAUNCH")
public class RCommandLaunch  implements java.io.Serializable {


        
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_r_command_launch" )
	@SequenceGenerator(name="seq_r_command_launch", sequenceName="SEQ_R_COMMAND_LAUNCH")
    private Integer id;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="DATE_START")
    private Date dateStart;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="DATE_END")
    private Date dateEnd;
	
	@Column(name="PROCESSID")
    private String processid;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_COMMAND",nullable = false, insertable = true, updatable= true)
    private RCommand RCommand;
	
	@Column(name="BELFIORE")
	private String belfiore;
	
	@Column(name="ID_SCHED")
	private Long idSched;
	
    /** default constructor */
    public RCommandLaunch() {
    }

	/** minimal constructor */
    public RCommandLaunch(String processid) {
        this.processid = processid;
    }
    

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateStart() {
        return this.dateStart;
    }
    
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return this.dateEnd;
    }
    
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
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

	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public Long getIdSched() {
		return idSched;
	}

	public void setIdSched(Long idSched) {
		this.idSched = idSched;
	}

}
