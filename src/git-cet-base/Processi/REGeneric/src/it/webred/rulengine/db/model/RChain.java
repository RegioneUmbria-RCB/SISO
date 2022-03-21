package it.webred.rulengine.db.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the R_CHAIN database table.
 * 
 */
@Entity
@Table(name="R_CHAIN")
public class RChain  implements java.io.Serializable {


    
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Id
    private Integer id;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DATE_START")
    private Date dateStart;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DATE_END")
    private Date dateEnd;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="COMMAND_ON_EXIT",nullable = false, insertable = false, updatable= false)
    private RCommand RCommandByCommandOnExit;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="FK_COMMAND",nullable = false, insertable = false, updatable= false)
    private RCommand RCommandByFkCommand;
	
    //private Set<RChainCommand> RChainCommands = new HashSet(0);



    /** default constructor */
    public RChain() {
    }

	/** minimal constructor */
    public RChain(Date dateStart, RCommand RCommandByFkCommand) {
        this.dateStart = dateStart;
        this.RCommandByFkCommand = RCommandByFkCommand;
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

    public RCommand getRCommandByCommandOnExit() {
        return this.RCommandByCommandOnExit;
    }
    
    public void setRCommandByCommandOnExit(RCommand RCommandByCommandOnExit) {
        this.RCommandByCommandOnExit = RCommandByCommandOnExit;
    }

    public RCommand getRCommandByFkCommand() {
        return this.RCommandByFkCommand;
    }
    
    public void setRCommandByFkCommand(RCommand RCommandByFkCommand) {
        this.RCommandByFkCommand = RCommandByFkCommand;
    }



}
