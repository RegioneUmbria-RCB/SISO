package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_IT_STEP_ATTR_VALUE database table.
 * 
 */
@Entity
@Table(name = "CS_I_INTERVENTO_ESEG_VALORE")
@NamedQuery(name = "CsIInterventoEsegValore.findAll", query = "SELECT c FROM CsIInterventoEsegValore c")
public class CsIInterventoEsegValore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CS_I_INTERVENTO_ESEG_VALORE_ID_GENERATOR", sequenceName = "SQ_ID",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_I_INTERVENTO_ESEG_VALORE_ID_GENERATOR")
	private long id;

	private String valore;

	//bi-directional many-to-one association to CsIInterventoEseg
	@ManyToOne
	@JoinColumn(name = "INTERVENTO_ESEG_ID")
	private CsIInterventoEseg csIInterventoEseg;

	//bi-directional many-to-one association to CsTbUnitaMisura
	@ManyToOne
	@JoinColumn(name = "ATTR_UM_ID")
	private CsCfgAttrUnitaMisura csAttributoUnitaMisura;

	public CsIInterventoEsegValore() {
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

	public CsIInterventoEseg getCsIInterventoEseg() {
		return csIInterventoEseg;
	}

	public void setCsIInterventoEseg(CsIInterventoEseg csIInterventoEseg) {
		this.csIInterventoEseg = csIInterventoEseg;
	}

	public CsCfgAttrUnitaMisura getCsAttributoUnitaMisura() {
		return csAttributoUnitaMisura;
	}

	public void setCsAttributoUnitaMisura(CsCfgAttrUnitaMisura csAttributoUnitaMisura) {
		this.csAttributoUnitaMisura = csAttributoUnitaMisura;
	}
}