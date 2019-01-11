package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the CS_IT_STEP_ATTR_VALUE database table.
 * 
 */
@Entity
@Table(name="CS_IT_STEP_ATTR_VALUE")
public class CsItStepAttrValueBASIC implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_IT_STEP_ATTR_VALUE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_IT_STEP_ATTR_VALUE_ID_GENERATOR")
	private long id;

	private String valore;

	//bi-directional many-to-one association to CsCfgAttr
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CFG_ATTR_ID")
	private CsCfgAttrLAZY csCfgAttr;

	@Basic
	@Column(name= "IT_STEP_ID", insertable=false, updatable=false)
	private Long csItStepId;

	
	/*
	//bi-directional many-to-one association to CsItStep
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="IT_STEP_ID")
	private CsItStepLAZY csItStep;
*/
	
	public CsItStepAttrValueBASIC() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValore() {
		return this.valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public CsCfgAttrLAZY getCsCfgAttr() {
		return this.csCfgAttr;
	}

	public void setCsCfgAttr(CsCfgAttrLAZY csCfgAttr) {
		this.csCfgAttr = csCfgAttr;
	}



}