package it.webred.rulengine.db.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;



/**
 * The persistent class for the R_COMMAND_TYPE database table.
 * 
 */
@Entity
@Table(name="R_COMMAND_TYPE")
public class RCommandType  implements java.io.Serializable {

	@Id
	@Column(name="ID")
    private Integer id;
	
	@Column(name="DESCR")
    private String descr;
    
	//@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@JoinColumn(name = "FK_COMMAND_TYPE", nullable = false)
    //private Set<RCommand> RCommands = new HashSet(0);


    /** default constructor */
    public RCommandType() {
    }

    


    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescr() {
        return this.descr;
    }
    
    public void setDescr(String descr) {
        this.descr = descr;
    }

   








}
