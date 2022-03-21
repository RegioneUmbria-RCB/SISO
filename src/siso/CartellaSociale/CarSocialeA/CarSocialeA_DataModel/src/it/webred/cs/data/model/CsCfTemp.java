package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the CF_TEMP database table.
 * 
 */
@Entity
@Table(name="CF_TEMP")
@NamedQuery(name="CsCfTemp.findAll", query="SELECT c FROM CsCfTemp c")
public class CsCfTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TEST_CF_TEMP_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TEST_CF_TEMP_ID_GENERATOR")
	private Long id;

	@Column(name="CF_TEMP")
	private String cfTemp;
	
	@Column(name="CF")
	private String cf;
	
	@Column(name="ATTRIBUTI")
	private String attributi;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_CREAZIONE")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_MODIFICA")
	private Date dtMod;

	
	
	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getCf() {
		return this.cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getCfTemp() {
		return this.cfTemp;
	}

	public void setCfTemp(String cfTemp) {
		this.cfTemp = cfTemp;
	}

	

	public CsCfTemp() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getAttributi() {
		return attributi;
	}

	public void setAttributi(String attributi) {
		this.attributi = attributi;
	}


}