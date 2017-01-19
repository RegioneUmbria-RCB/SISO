package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the CS_CFG_ATTR database table.
 * 
 */
@Entity
@Table(name="CS_CFG_ATTR")
public class CsCfgAttrLAZY implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="\"LABEL\"")
	private String label;

	@Column(name="MESSAGGIO_OBBLIGATORIO")
	private String messaggioObbligatorio;

	@Column(name="TIPO_ATTR")
	private String tipoAttr;

	private String tooltip;

	@Column(name="VALORE_DEFAULT")
	private String valoreDefault;

	//bi-directional many-to-one association to CsCfgAttrOption
	@OneToMany(mappedBy="csCfgAttr",fetch = FetchType.LAZY)
	private List<CsCfgAttrOptionLAZY> csCfgAttrOptions;

	//bi-directional many-to-one association to CsCfgItStatoAttr
	@OneToMany(mappedBy="csCfgAttr",fetch = FetchType.LAZY)
	private List<CsCfgItStatoAttrLAZY> csCfgItStatoAttrs;

	//bi-directional many-to-one association to CsItStepAttrValue
	@OneToMany(mappedBy="csCfgAttr", fetch = FetchType.LAZY)
	private List<CsItStepAttrValueBASIC> csItStepAttrValues;

	public CsCfgAttrLAZY() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMessaggioObbligatorio() {
		return this.messaggioObbligatorio;
	}

	public void setMessaggioObbligatorio(String messaggioObbligatorio) {
		this.messaggioObbligatorio = messaggioObbligatorio;
	}

	public String getTipoAttr() {
		return this.tipoAttr;
	}

	public void setTipoAttr(String tipoAttr) {
		this.tipoAttr = tipoAttr;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getValoreDefault() {
		return this.valoreDefault;
	}

	public void setValoreDefault(String valoreDefault) {
		this.valoreDefault = valoreDefault;
	}

	public List<CsCfgAttrOptionLAZY> getCsCfgAttrOptions() {
		return this.csCfgAttrOptions;
	}

	public void setCsCfgAttrOptions(List<CsCfgAttrOptionLAZY> csCfgAttrOptions) {
		this.csCfgAttrOptions = csCfgAttrOptions;
	}

	public CsCfgAttrOptionLAZY addCsCfgAttrOption(CsCfgAttrOptionLAZY csCfgAttrOption) {
		getCsCfgAttrOptions().add(csCfgAttrOption);
		csCfgAttrOption.setCsCfgAttr(this);

		return csCfgAttrOption;
	}

	public CsCfgAttrOptionLAZY removeCsCfgAttrOption(CsCfgAttrOptionLAZY csCfgAttrOption) {
		getCsCfgAttrOptions().remove(csCfgAttrOption);
		csCfgAttrOption.setCsCfgAttr(null);

		return csCfgAttrOption;
	}

	public List<CsCfgItStatoAttrLAZY> getCsCfgItStatoAttrs() {
		return this.csCfgItStatoAttrs;
	}

	public void setCsCfgItStatoAttrs(List<CsCfgItStatoAttrLAZY> csCfgItStatoAttrs) {
		this.csCfgItStatoAttrs = csCfgItStatoAttrs;
	}

	public CsCfgItStatoAttrLAZY addCsCfgItStatoAttr(CsCfgItStatoAttrLAZY csCfgItStatoAttr) {
		getCsCfgItStatoAttrs().add(csCfgItStatoAttr);
		csCfgItStatoAttr.setCsCfgAttr(this);

		return csCfgItStatoAttr;
	}

	public CsCfgItStatoAttr removeCsCfgItStatoAttr(CsCfgItStatoAttr csCfgItStatoAttr) {
		getCsCfgItStatoAttrs().remove(csCfgItStatoAttr);
		csCfgItStatoAttr.setCsCfgAttr(null);

		return csCfgItStatoAttr;
	}

	public List<CsItStepAttrValueBASIC> getCsItStepAttrValues() {
		return this.csItStepAttrValues;
	}

	public void setCsItStepAttrValues(List<CsItStepAttrValueBASIC> csItStepAttrValues) {
		this.csItStepAttrValues = csItStepAttrValues;
	}

	public CsItStepAttrValueBASIC addCsItStepAttrValue(CsItStepAttrValueBASIC csItStepAttrValue) {
		getCsItStepAttrValues().add(csItStepAttrValue);
		csItStepAttrValue.setCsCfgAttr(this);

		return csItStepAttrValue;
	}

	public CsItStepAttrValue removeCsItStepAttrValue(CsItStepAttrValue csItStepAttrValue) {
		getCsItStepAttrValues().remove(csItStepAttrValue);
		csItStepAttrValue.setCsCfgAttr(null);

		return csItStepAttrValue;
	}

}