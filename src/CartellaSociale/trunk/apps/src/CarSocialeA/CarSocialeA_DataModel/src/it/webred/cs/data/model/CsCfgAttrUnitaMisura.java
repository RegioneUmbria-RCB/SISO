package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the CS_CFG_IT_STATO_ATTR database table.
 * 
 */
@Entity
@Table(name="CS_CFG_ATTR_UNITA_MISURA")
@NamedQuery(name="CsCfgAttrUnitaMisura.findAll", query="SELECT c FROM CsCfgAttrUnitaMisura c")
public class CsCfgAttrUnitaMisura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_CFG_ATTR_UNITA_MISURA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_CFG_ATTR_UNITA_MISURA_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to CsCfgAttr
	@ManyToOne
	@JoinColumn(name="ATTRIBUTO_ID")
	private CsCfgAttr csCfgAttributo;

	//bi-directional many-to-one association to CsCfgAttr
	@ManyToOne
	@JoinColumn(name="UNITA_MISURA_ID")
	private CsTbUnitaMisura csTbUnitaMisura;

	public CsCfgAttrUnitaMisura() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CsCfgAttr getCsCfgAttributo() {
		return csCfgAttributo;
	}

	public void setCsCfgAttributo(CsCfgAttr csCfgAttributo) {
		this.csCfgAttributo = csCfgAttributo;
	}

	public CsTbUnitaMisura getCsTbUnitaMisura() {
		return csTbUnitaMisura;
	}

	public void setCsTbUnitaMisura(CsTbUnitaMisura csTbUnitaMisura) {
		this.csTbUnitaMisura = csTbUnitaMisura;
	}
}