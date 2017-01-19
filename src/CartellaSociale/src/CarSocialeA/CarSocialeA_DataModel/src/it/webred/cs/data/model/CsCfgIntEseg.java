package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the CS_CFG_IT_STATO database table.
 * 
 */
@Entity
@Table(name = "CS_CFG_INT_ESEG")
@NamedQuery(name="CsCfgIntEseg.findAll", query="SELECT c FROM CsCfgIntEseg c")
public class CsCfgIntEseg implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_CFG_INT_ESEG_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_CFG_INT_ESEG_ID_GENERATOR")
	private long id;

	@OneToOne
	@JoinColumn(name = "TIPO_INTERVENTO_ID")
	private CsCTipoIntervento tipoIntervento;

    @ManyToMany
	@JoinTable(
		name="CS_CFG_INT_ESEG_ATT_UM"
		, joinColumns={
			@JoinColumn(name="CFG_INT_ESEG_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ATTR_UM_ID")
			}
		)
	@OrderBy("id ASC")
    private List<CsCfgAttrUnitaMisura> csCfgAttrUnitaMisuras;

    @OneToMany(mappedBy="csCfgIntervEseg")
    private List<CsCfgIntEsegAttUm> csCfgIntEsegAttUms;

    @ManyToMany
	@JoinTable(
		name="CS_CFG_INT_ESEG_STATO_INT"
		, joinColumns={
			@JoinColumn(name="CFG_INT_ESEG_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="STATO_ID")
			}
		)
	private List<CsCfgIntEsegStato> csCfgIntEsegStatos;

	public CsCfgIntEseg() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CsCTipoIntervento getTipoIntervento() {
		return tipoIntervento;
	}

	public void setTipoIntervento(CsCTipoIntervento tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}

	public List<CsCfgAttrUnitaMisura> getCsCfgAttrUnitaMisuras() {
		return csCfgAttrUnitaMisuras;
	}

	public void setCsCfgAttrUnitaMisuras(
			List<CsCfgAttrUnitaMisura> csCfgAttrUnitaMisuras) {
		this.csCfgAttrUnitaMisuras = csCfgAttrUnitaMisuras;
	}

	public List<CsCfgIntEsegStato> getCsCfgIntEsegStatos() {
		return csCfgIntEsegStatos;
	}

	public void setCsCfgIntEsegStatos(List<CsCfgIntEsegStato> csCfgIntEsegStatos) {
		this.csCfgIntEsegStatos = csCfgIntEsegStatos;
	}

	public List<CsCfgIntEsegAttUm> getCsCfgIntEsegAttUms() {
		return csCfgIntEsegAttUms;
	}

	

}
