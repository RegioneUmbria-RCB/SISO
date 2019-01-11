package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CS_O_OPERATORE_SETTORE database table.
 * 
 */
@Entity
@Table(name="CS_O_OPERATORE_SETTORE")
@NamedQuery(name="CsOOperatoreSettore.findAll", query="SELECT c FROM CsOOperatoreSettore c")
public class CsOOperatoreSettore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_O_OPERATORE_SETTORE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_O_OPERATORE_SETTORE_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_APP")
	private Date dataFineApp;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_APP")
	private Date dataInizioApp;

	//bi-directional many-to-one association to CsOOperatore
	@ManyToOne
	@JoinColumn(name="OPERATORE_ID")
	private CsOOperatore csOOperatore;

	//bi-directional many-to-one association to CsOSettore
	@ManyToOne
	@JoinColumn(name="SETTORE_ID")
	private CsOSettore csOSettore;
	
	@Column(name="AM_GROUP")
	private String amGroup;
	
	@Column(name="APPARTIENE")
	private long appartiene;
	
	private Boolean firma;
	
	@OneToMany(mappedBy="opSettore", fetch=FetchType.EAGER)
	private Set<CsOOpsettoreAlertConfig> alertConfig;

	public CsOOperatoreSettore() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataFineApp() {
		return this.dataFineApp;
	}

	public void setDataFineApp(Date dataFineApp) {
		this.dataFineApp = dataFineApp;
	}

	public Date getDataInizioApp() {
		return this.dataInizioApp;
	}

	public void setDataInizioApp(Date dataInizioApp) {
		this.dataInizioApp = dataInizioApp;
	}

	public CsOOperatore getCsOOperatore() {
		return this.csOOperatore;
	}

	public void setCsOOperatore(CsOOperatore csOOperatore) {
		this.csOOperatore = csOOperatore;
	}

	public CsOSettore getCsOSettore() {
		return this.csOSettore;
	}

	public void setCsOSettore(CsOSettore csOSettore) {
		this.csOSettore = csOSettore;
	}

	public String getAmGroup() {
		return amGroup;
	}

	public void setAmGroup(String amGroup) {
		this.amGroup = amGroup;
	}

	public long getAppartiene() {
		return appartiene;
	}

	public void setAppartiene(long appartiene) {
		this.appartiene = appartiene;
	}

	public Boolean getFirma() {
		return firma;
	}

	public void setFirma(Boolean firma) {
		this.firma = firma;
	}

	public Set<CsOOpsettoreAlertConfig> getAlertConfig() {
		return alertConfig;
	}

	public void setAlertConfig(Set<CsOOpsettoreAlertConfig> alertConfig) {
		this.alertConfig = alertConfig;
	}
	
	public List<CsOOpsettoreAlertConfig> getLstAlertConfig(){
		List<CsOOpsettoreAlertConfig> lst = new ArrayList<CsOOpsettoreAlertConfig>();
		Iterator<CsOOpsettoreAlertConfig> iter = this.alertConfig.iterator();
		while(iter.hasNext())
			lst.add(iter.next());
			
		return lst;
	}

}