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
 * The persistent class for the R_AB_NORMAL database table.
 * 
 */
@Entity
@Table(name="R_AB_NORMAL")
public class RAbNormal  implements java.io.Serializable {

	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_r_ab_normal")
	@SequenceGenerator(name="seq_r_ab_normal", sequenceName="SEQ_R_AB_NORMAL")
    private Integer id;
	
	@Column(name="ENTITY")
    private String entity;
	
	@Column(name="KEY")
    private String key;
	
	@Column(name="MESSAGE")
    private String message;
	
	@Temporal( TemporalType.DATE)
	@Column(name="MESSAGE_DATE")
    private Date messageDate;
	
	@Column(name="LIVELLO_ANOMALIA")
    private Integer livelloAnomalia;
	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="FK_COMMAND_ACK", nullable = false, insertable = false, updatable= false)
    private RCommandAck RCommandAck;


    /** default constructor */
    public RAbNormal() {
    }

    
    /** full constructor */
    public RAbNormal(String entity, String key, String message, Date messageDate, Integer livelloAnomalia, RCommandAck RCommandAck) {
        this.entity = entity;
        this.key = key;
        this.message = message;
        this.messageDate = messageDate;
        this.livelloAnomalia = livelloAnomalia;
        this.RCommandAck = RCommandAck;
    }
    

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntity() {
        return this.entity;
    }
    
    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getKey() {
        return this.key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public Date getMessageDate() {
        return this.messageDate;
    }
    
    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public Integer getLivelloAnomalia() {
        return this.livelloAnomalia;
    }
    
    public void setLivelloAnomalia(Integer livelloAnomalia) {
        this.livelloAnomalia = livelloAnomalia;
    }

    public RCommandAck getRCommandAck() {
        return this.RCommandAck;
    }
    
    public void setRCommandAck(RCommandAck RCommandAck) {
        this.RCommandAck = RCommandAck;
    }
   








}
