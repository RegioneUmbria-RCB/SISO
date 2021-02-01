package it.webred.ct.config.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the AM_SECTION database table.
 * 
 */
@Entity
@Table(name="AM_SECTION")
public class AmSection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name="NAME")
	private String name;

	@Column(name="N_ORDINE")
	private Integer ordine;

	private Integer tipo;

	//bi-directional many-to-one association to AmKeyValue
	@OneToMany(mappedBy="amSection", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	private Set<AmKeyValue> amKeyValues;

	//bi-directional many-to-one association to AmKeyValueExt
	@OneToMany(mappedBy="amSection", cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	private Set<AmKeyValueExt> amKeyValueExts;

	//uni-directional many-to-one association to AmApplication
    @ManyToOne
	@JoinColumn(name="FK_AM_APPLICATION")
	private AmApplication amApplication;

	//uni-directional many-to-one association to AmFonte
    @ManyToOne
	@JoinColumn(name="FK_AM_FONTE")
	private AmFonte amFonte;

    public AmSection() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrdine() {
		return this.ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

	public Integer getTipo() {
		return this.tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
	public Set<AmKeyValue> getAmKeyValues() {
		return amKeyValues;
	}

	public void setAmKeyValues(Set<AmKeyValue> amKeyValues) {
		this.amKeyValues = amKeyValues;
	}

	public Set<AmKeyValueExt> getAmKeyValueExts() {
		return amKeyValueExts;
	}

	public void setAmKeyValueExts(Set<AmKeyValueExt> amKeyValueExts) {
		this.amKeyValueExts = amKeyValueExts;
	}

	public AmApplication getAmApplication() {
		return this.amApplication;
	}

	public void setAmApplication(AmApplication amApplication) {
		this.amApplication = amApplication;
	}
	
	public AmFonte getAmFonte() {
		return this.amFonte;
	}

	public void setAmFonte(AmFonte amFonte) {
		this.amFonte = amFonte;
	}
	
}