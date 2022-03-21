package it.webred.rulengine.db.model;


import javax.persistence.*;



/**
 * The persistent class for the R_COMMAND database table.
 * 
 */
@Entity
@Table(name="R_COMMAND")
public class RCommand  implements java.io.Serializable {


	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_r_command")
	@SequenceGenerator(name="seq_r_command", sequenceName="SEQ_R_COMMAND")
    private Integer id;
	
	@Column(name="DESCR")
    private String descr;
	
	@Column(name="SYSTEM_COMMAND")
    private Integer systemCommand;
	
	@Column(name="LONG_DESCR")
    private String longDescr;
	
	@Column(name="COD_COMMAND")
    private String codCommand;
	
	

    /** default constructor */
    public RCommand() {
    }

	/** minimal constructor */
    public RCommand(Integer systemCommand, String codCommand) {
        this.systemCommand = systemCommand;
        this.codCommand = codCommand;
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

    public Integer getSystemCommand() {
        return this.systemCommand;
    }
    
    public void setSystemCommand(Integer systemCommand) {
        this.systemCommand = systemCommand;
    }

    public String getLongDescr() {
        return this.longDescr;
    }
    
    public void setLongDescr(String longDescr) {
        this.longDescr = longDescr;
    }

    public String getCodCommand() {
        return this.codCommand;
    }
    
    public void setCodCommand(String codCommand) {
        this.codCommand = codCommand;
    }
}
