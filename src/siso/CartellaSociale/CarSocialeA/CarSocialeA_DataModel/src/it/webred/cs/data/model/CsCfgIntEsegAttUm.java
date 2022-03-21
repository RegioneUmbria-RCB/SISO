package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the CS_CFG_IT_STATO_ATTR database table.
 * 
 */
@Entity
@Table(name="CS_CFG_INT_ESEG_ATT_UM")
@NamedQuery(name="CsCfgIntEsegAttUm.findAll", query="SELECT c FROM CsCfgIntEsegAttUm c")
public class CsCfgIntEsegAttUm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_CFG_INT_ESEG_ATT_UM_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_CFG_INT_ESEG_ATT_UM_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to CsCfgAttr
	@ManyToOne
	@JoinColumn(name="CFG_INT_ESEG_STATO_INT_ID")
	private CsCfgIntEsegStatoInt csCfgIntEsegStatoInt;

	@ManyToOne
	@JoinColumn(name="ATTR_UM_ID")
	private CsCfgAttrUnitaMisura csCfgAttrUm;

	@Column(name="CALC_TOTALE", columnDefinition="CHAR(1)")
	private Boolean calcTotale;
	
	private Boolean abilitato;
	
	public CsCfgIntEsegAttUm() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CsCfgIntEsegStatoInt getCsCfgIntEsegStatoInt() {
		return csCfgIntEsegStatoInt;
	}

	public void setCsCfgIntEsegStatoInt(CsCfgIntEsegStatoInt csCfgIntEsegStatoInt) {
		this.csCfgIntEsegStatoInt = csCfgIntEsegStatoInt;
	}

	public CsCfgAttrUnitaMisura getCsCfgAttrUm() {
		return csCfgAttrUm;
	}

	public void setCsCfgAttrUm(CsCfgAttrUnitaMisura csCfgAttrUm) {
		this.csCfgAttrUm = csCfgAttrUm;
	}

	public Boolean getCalcTotale() {
		return calcTotale;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setCalcTotale(Boolean calcTotale) {
		this.calcTotale = calcTotale;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
	
}