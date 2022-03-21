package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CS_TB_SINA_RISPOSTA")
@NamedQuery(name = "CsTbSinaRisposta.findAll", query = "SELECT c FROM CsTbSinaRisposta c")
public class CsTbSinaRisposta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CS_TB_SINA_RISPOSTA_ID_GENERATOR", sequenceName = "SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_TB_SINA_RISPOSTA_ID_GENERATOR")
	private long id;

	@ManyToOne
	@JoinColumn(name = "DOMANDA_ID")
	private CsTbSinaDomanda csTbSinaDomanda;

	@Column(name = "VALORE")
	private BigDecimal valore;

	@Column(name = "TOOLTIP")
	private String tooltip;

	public CsTbSinaRisposta() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CsTbSinaDomanda getCsTbSinaDomanda() {
		return csTbSinaDomanda;
	}

	public void setCsTbSinaDomanda(CsTbSinaDomanda csTbSinaDomanda) {
		this.csTbSinaDomanda = csTbSinaDomanda;
	}

	public BigDecimal getValore() {
		return valore;
	}

	public void setValore(BigDecimal valore) {
		this.valore = valore;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
}
