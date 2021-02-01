package it.webred.rulengine.db.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the R_COMMAND_ACK database table.
 * 
 */
@Entity
@Table(name="R_COMMAND_ACK")
public class RCommandAck  implements java.io.Serializable {

    
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_r_command_ack" )
	@SequenceGenerator(name="seq_r_command_ack", sequenceName="SEQ_R_COMMAND_ACK")
    private Integer id;
	
	@Column(name="MESSAGE")
    private String message;
	
	@Temporal( TemporalType.DATE)
	@Column(name="LOG_DATE")
    private Date logDate;
	
	@Column(name="ACK_NAME")
    private String ackName;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="FK_COMMAND",nullable = false, insertable = true, updatable = true)
    private RCommand RCommand;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="FK_COMMAND_LAUNCH",nullable = false, insertable = true, updatable = true)
    private RCommandLaunch RCommandLaunch;
	


    // Constructors

    /** default constructor */
    public RCommandAck() {
    }

    


    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public Date getLogDate() {
        return this.logDate;
    }
    
    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getAckName() {
        return this.ackName;
    }
    
    public void setAckName(String ackName) {
        this.ackName = ackName;
    }

    public RCommand getRCommand() {
        return this.RCommand;
    }
    
    public void setRCommand(RCommand RCommand) {
        this.RCommand = RCommand;
    }

    public RCommandLaunch getRCommandLaunch() {
        return this.RCommandLaunch;
    }
    
    public void setRCommandLaunch(RCommandLaunch RCommandLaunch) {
        this.RCommandLaunch = RCommandLaunch;
    }

}
