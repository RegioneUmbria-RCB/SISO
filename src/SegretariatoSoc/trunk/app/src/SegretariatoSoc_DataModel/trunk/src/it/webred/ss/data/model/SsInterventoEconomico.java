package it.webred.ss.data.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the SS_INTERVENTO_ECONOMICO database table.
 * 
 */
@Entity
@Table(name="SS_INTERVENTO_ECONOMICO")
@NamedQuery(name="SsInterventoEconomico.findAll", query="SELECT c FROM SsInterventoEconomico c")
public class SsInterventoEconomico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_INTERVENTO_ECONOMICO_ID_GENERATOR", sequenceName="SQ_SS_INTERVENTO_ECONOMICO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_INTERVENTO_ECONOMICO_ID_GENERATOR")
	private Long id;
	
	private Long importo;
	
	@ManyToOne
	@JoinColumn(name="TIPO")
	private SsInterventoEconomicoTipo tipo;
	
	@ManyToOne
	@JoinColumn(name="SOGGETTO")
	private SsAnagrafica soggetto; 
	
	private String erogante;
	
	private Date data;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getImporto() {
		return importo;
	}

	public void setImporto(Long importo) {
		this.importo = importo;
	}

	public SsInterventoEconomicoTipo getTipo() {
		return tipo;
	}

	public void setTipo(SsInterventoEconomicoTipo tipo) {
		this.tipo = tipo;
	}

	public SsAnagrafica getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(SsAnagrafica soggetto) {
		this.soggetto = soggetto;
	}

	public String getErogante() {
		return erogante;
	}

	public void setErogante(String erogante) {
		this.erogante = erogante;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
}