package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the R_ANAG_EVENTI database table.
 * 
 */
@Entity
@Table(name="R_ANAG_EVENTI")
public class RAnagEventi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="seq_r_eventi" )
	@SequenceGenerator(name="seq_r_eventi", sequenceName="SEQ_R_EVENTI")
	private Long id;
	
	private String standard;

	@Column(name="AFTER_COMMAND")
	private Long afterCommand;

	@Column(name="AFTER_ID_FONTE")
	private Long afterIdFonte;

	//bi-directional many-to-one association to RAnagStati
    @ManyToOne
	@JoinColumn(name="AFTER_COMMAND_STATO")
	private RAnagStati RAnagStati;

	//bi-directional many-to-one association to RCommand
    @ManyToOne
	@JoinColumn(name="FK_NEXT_COMMAND")
	private RCommand RCommand;

	//bi-directional many-to-one association to RCommandType
    @ManyToOne
	@JoinColumn(name="AFTER_COMMAND_TYPE")
	private RCommandType RCommandType;

        
    
    public RAnagEventi() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getAfterCommand() {
		return this.afterCommand;
	}

	public void setAfterCommand(Long afterCommand) {
		this.afterCommand = afterCommand;
	}

	public Long getAfterIdFonte() {
		return this.afterIdFonte;
	}

	public void setAfterIdFonte(Long afterIdFonte) {
		this.afterIdFonte = afterIdFonte;
	}

	public RAnagStati getRAnagStati() {
		return this.RAnagStati;
	}

	public void setRAnagStati(RAnagStati RAnagStati) {
		this.RAnagStati = RAnagStati;
	}
	
	public RCommand getRCommand() {
		return this.RCommand;
	}

	public void setRCommand(RCommand RCommand) {
		this.RCommand = RCommand;
	}
	
	public RCommandType getRCommandType() {
		return this.RCommandType;
	}

	public void setRCommandType(RCommandType RCommandType) {
		this.RCommandType = RCommandType;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	
}