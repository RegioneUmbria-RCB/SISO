package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CS_IT_STEP_ATTR_VALUE database table.
 * 
 */
@Entity
@Table(name="CS_IT_STEP_ATTR_VALUE")
@NamedQuery(name="CsItStepAttrValue.findAll", query="SELECT c FROM CsItStepAttrValue c")
public class CsItStepAttrValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_IT_STEP_ATTR_VALUE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_IT_STEP_ATTR_VALUE_ID_GENERATOR")
	private long id;

	private String valore;

	//bi-directional many-to-one association to CsCfgAttr
	@ManyToOne
	@JoinColumn(name="CFG_ATTR_ID")
	private CsCfgAttr csCfgAttr;

	//bi-directional many-to-one association to CsItStep
	@ManyToOne
	@JoinColumn(name="IT_STEP_ID")
	private CsItStep csItStep;

	public CsItStepAttrValue() {
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

	public CsCfgAttr getCsCfgAttr() {
		return this.csCfgAttr;
	}

	public void setCsCfgAttr(CsCfgAttr csCfgAttr) {
		this.csCfgAttr = csCfgAttr;
	}

	public CsItStep getCsItStep() {
		return this.csItStep;
	}

	public void setCsItStep(CsItStep csItStep) {
		this.csItStep = csItStep;
	}

}