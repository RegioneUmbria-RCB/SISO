package it.webred.cs.data.model;

import java.util.List;

import javax.persistence.Entity;
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

    @OneToMany(mappedBy="csCfgIntervEseg")
    private List<CsCfgIntEsegStatoInt> csCfgIntEsegStatoInt;

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

	public List<CsCfgIntEsegStatoInt> getCsCfgIntEsegStatoInt() {
		return csCfgIntEsegStatoInt;
	}

	public void setCsCfgIntEsegStatoInt(List<CsCfgIntEsegStatoInt> csCfgIntEsegStatoInt) {
		this.csCfgIntEsegStatoInt = csCfgIntEsegStatoInt;
	}
	
}
