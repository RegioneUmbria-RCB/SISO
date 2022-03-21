package it.webred.rulengine.db.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;


/**
 * The persistent class for the R_RULE database table.
 * 
 */
@Entity
@Table(name="R_RULE")
public class RRule  implements java.io.Serializable {


	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_r_rule" )
	@SequenceGenerator(name="seq_r_rule", sequenceName="SEQ_R_RULE" )
	private Integer id;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DATE_START")
    private Date dateStart;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DATE_END")
    private Date dateEnd;
	
	@Column(name="CLASS_NAME")
    private String className;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="FK_COMMAND", nullable = false, insertable = true, updatable= true)
    private RCommand RCommand;
	
	/*
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_RULE", nullable = false)
    @OrderBy(value = "id")
    private Set<RRuleRpo> RRuleRpos = new HashSet(0);
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_RULE", nullable = false)
    @OrderBy(value = "id")
    private Set<RRuleRpi> RRuleRpis = new HashSet(0);
	*/


    /** default constructor */
    public RRule() {
    }

	/** minimal constructor */
    public RRule(Date dateStart, String className, RCommand RCommand) {
        this.dateStart = dateStart;
        this.className = className;
        this.RCommand = RCommand;
    }
    
    /** full constructor */
    public RRule(Date dateStart, Date dateEnd, String className, RCommand RCommand) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.className = className;
        this.RCommand = RCommand;
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

    public String getClassName() {
        return this.className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }

    public RCommand getRCommand() {
        return this.RCommand;
    }
    
    public void setRCommand(RCommand RCommand) {
        this.RCommand = RCommand;
    }

}
