package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the CS_TB_TIPO_RIENTRI_FAMI database table.
 * 
 */
@Entity
@Table(name="CS_TB_TIPO_RIENTRI_FAMI")
@NamedQuery(name="CsTbTipoRientriFami.findAll", query="SELECT c FROM CsTbTipoRientriFami c")
public class CsTbTipoRientriFami implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_TIPO_RIENTRI_FAMI_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_TIPO_RIENTRI_FAMI_ID_GENERATOR")
	private long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;

	//bi-directional many-to-one association to CsIResiMinore
	@OneToMany(mappedBy="csTbTipoRientriFami")
	private List<CsIResiMinore> csIResiMinores;

	public CsTbTipoRientriFami() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public List<CsIResiMinore> getCsIResiMinores() {
		return this.csIResiMinores;
	}

	public void setCsIResiMinores(List<CsIResiMinore> csIResiMinores) {
		this.csIResiMinores = csIResiMinores;
	}

	public CsIResiMinore addCsIResiMinore(CsIResiMinore csIResiMinore) {
		getCsIResiMinores().add(csIResiMinore);
		csIResiMinore.setCsTbTipoRientriFami(this);

		return csIResiMinore;
	}

	public CsIResiMinore removeCsIResiMinore(CsIResiMinore csIResiMinore) {
		getCsIResiMinores().remove(csIResiMinore);
		csIResiMinore.setCsTbTipoRientriFami(null);

		return csIResiMinore;
	}

}