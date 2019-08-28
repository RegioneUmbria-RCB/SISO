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
@NamedQuery(name="CsCfgAttr.findAll", query="SELECT c FROM CsCfgAttr c")
public class CsCfgAttr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_CFG_ATTR_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_CFG_ATTR_ID_GENERATOR")
	private long id;

	@Column(name="LABEL")
	private String label;

	@Column(name="MESSAGGIO_OBBLIGATORIO")
	private String messaggioObbligatorio;

	@Column(name="TIPO_ATTR")
	private String tipoAttr;

	private String tooltip;

	@Column(name="VALORE_DEFAULT")
	private String valoreDefault;

	//bi-directional many-to-one association to CsCfgAttrOption
	@OneToMany(mappedBy="csCfgAttr")
	private List<CsCfgAttrOption> csCfgAttrOptions;      //Opzioni per attributi di tipo LIST

/*	//bi-directional many-to-one association to CsCfgItStatoAttr
	@OneToMany(mappedBy="csCfgAttr")
	private List<CsCfgItStatoAttr> csCfgItStatoAttrs;
*/

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

	public List<CsCfgAttrOption> getCsCfgAttrOptions() {
		return this.csCfgAttrOptions;
	}

	public void setCsCfgAttrOptions(List<CsCfgAttrOption> csCfgAttrOptions) {
		this.csCfgAttrOptions = csCfgAttrOptions;
	}

	public CsCfgAttrOption addCsCfgAttrOption(CsCfgAttrOption csCfgAttrOption) {
		getCsCfgAttrOptions().add(csCfgAttrOption);
		csCfgAttrOption.setCsCfgAttr(this);

		return csCfgAttrOption;
	}

	public CsCfgAttrOption removeCsCfgAttrOption(CsCfgAttrOption csCfgAttrOption) {
		getCsCfgAttrOptions().remove(csCfgAttrOption);
		csCfgAttrOption.setCsCfgAttr(null);

		return csCfgAttrOption;
	}

/*	public List<CsCfgItStatoAttr> getCsCfgItStatoAttrs() {
		return this.csCfgItStatoAttrs;
	}

	public void setCsCfgItStatoAttrs(List<CsCfgItStatoAttr> csCfgItStatoAttrs) {
		this.csCfgItStatoAttrs = csCfgItStatoAttrs;
	}

	public CsCfgItStatoAttr addCsCfgItStatoAttr(CsCfgItStatoAttr csCfgItStatoAttr) {
		getCsCfgItStatoAttrs().add(csCfgItStatoAttr);
		csCfgItStatoAttr.setCsCfgAttr(this);

		return csCfgItStatoAttr;
	}

	public CsCfgItStatoAttr removeCsCfgItStatoAttr(CsCfgItStatoAttr csCfgItStatoAttr) {
		getCsCfgItStatoAttrs().remove(csCfgItStatoAttr);
		csCfgItStatoAttr.setCsCfgAttr(null);

		return csCfgItStatoAttr;
	}
*/


}