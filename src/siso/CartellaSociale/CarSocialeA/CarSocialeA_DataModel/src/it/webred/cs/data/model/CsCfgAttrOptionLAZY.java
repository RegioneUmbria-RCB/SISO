package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the CS_CFG_ATTR_OPTION database table.
 * 
 */
@Entity
@Table(name="CS_CFG_ATTR_OPTION")
public class CsCfgAttrOptionLAZY implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_CFG_ATTR_OPTION_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_CFG_ATTR_OPTION_ID_GENERATOR")
	private Long id;

	private String abilitato;

	private BigDecimal ordine;

	private String tooltip;

	private String valore;

	//bi-directional many-to-one association to CsCfgAttr
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CFG_ATTR_ID")
	private CsCfgAttrLAZY csCfgAttr;

	public CsCfgAttrOptionLAZY() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public BigDecimal getOrdine() {
		return this.ordine;
	}

	public void setOrdine(BigDecimal ordine) {
		this.ordine = ordine;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
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