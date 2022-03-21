package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CS_TB_SINA_DOMANDA")
@NamedQuery(name = "CsTbSinaDomanda.findAll", query = "SELECT c FROM CsTbSinaDomanda c")
public class CsTbSinaDomanda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CS_TB_SINA_DOMANDA_ID_GENERATOR", sequenceName = "SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_TB_SINA_DOMANDA_ID_GENERATOR")
	private Long id;

	@Column(name = "TESTO")
	private String testo;

	@Column(name = "TOOLTIP")
	private String tooltip;

	//bi-directional one-to-many association to CsDDiarioDoc
	@OneToMany(mappedBy="csTbSinaDomanda", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CsTbSinaRisposta> csTbSinaRispostas;

	public CsTbSinaDomanda() {
		csTbSinaRispostas = new ArrayList<CsTbSinaRisposta>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public List<CsTbSinaRisposta> getCsTbSinaRispostas() {
		return csTbSinaRispostas;
	}

	public void setCsTbSinaRispostas(List<CsTbSinaRisposta> csTbSinaRispostas) {
		this.csTbSinaRispostas = csTbSinaRispostas;
	}
	
}
