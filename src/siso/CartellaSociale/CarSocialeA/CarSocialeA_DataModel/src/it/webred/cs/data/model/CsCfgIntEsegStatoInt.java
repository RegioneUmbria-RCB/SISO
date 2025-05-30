package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the CS_CFG_IT_STATO_ATTR database table.
 * 
 */
@Entity
@Table(name="CS_CFG_INT_ESEG_STATO_INT")
@NamedQuery(name="CsCfgIntEsegStatoInt.findAll", query="SELECT c FROM CsCfgIntEsegStatoInt c")
public class CsCfgIntEsegStatoInt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_CFG_INTESEG_STATO_INT_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_CFG_INTESEG_STATO_INT_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to CsCfgAttr
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="CFG_INT_ESEG_ID")
	private CsCfgIntEseg csCfgIntervEseg;
	
	@OneToMany(mappedBy="csCfgIntEsegStatoInt")
	private List<CsCfgIntEsegAttUm> csCfgIntEsegAttUms;


	//bi-directional many-to-one association to CsCfgAttr
	@ManyToOne
	@JoinColumn(name="STATO_ID")
	private CsCfgIntEsegStato csCfgIntEsegStato;
	
	@Column(name="BYPASS_TIPO_STATO")
	private Boolean bypassTipoStato;

	public CsCfgIntEsegStatoInt() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CsCfgIntEseg getCsCfgIntervEseg() {
		return csCfgIntervEseg;
	}

	public void setCsCfgIntervEseg(CsCfgIntEseg csCfgIntervEseg) {
		this.csCfgIntervEseg = csCfgIntervEseg;
	}

	public CsCfgIntEsegStato getCsCfgIntEsegStato() {
		return csCfgIntEsegStato;
	}

	public void setCsCfgIntEsegStato(CsCfgIntEsegStato csCfgIntEsegStato) {
		this.csCfgIntEsegStato = csCfgIntEsegStato;
	}

	public List<CsCfgIntEsegAttUm> getCsCfgIntEsegAttUms() {
		return csCfgIntEsegAttUms;
	}

	public void setCsCfgIntEsegAttUms(List<CsCfgIntEsegAttUm> csCfgIntEsegAttUms) {
		this.csCfgIntEsegAttUms = csCfgIntEsegAttUms;
	}

	public Boolean getBypassTipoStato() {
		return bypassTipoStato;
	}

	public void setBypassTipoStato(Boolean bypassTipoStato) {
		this.bypassTipoStato = bypassTipoStato;
	}

}