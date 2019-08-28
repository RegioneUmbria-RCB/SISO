package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the CS_O_OPERATORE database table.
 * 
 */
@Entity
@Table(name="CS_O_OPERATORE")
@NamedQuery(name="CsOOperatore.findAll", query="SELECT c FROM CsOOperatore c")
public class CsOOperatore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_O_OPERATORE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_O_OPERATORE_ID_GENERATOR")
	private Long id;

	private String abilitato;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private Date dataFineVal;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

	private String tooltip;

	private String username;
	
	@Transient
	private String denominazione;

	//bi-directional many-to-one association to CsOOperatoreSettore
	@OneToMany(mappedBy="csOOperatore", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OrderBy("id")
	private Set<CsOOperatoreSettore> csOOperatoreSettores;
	
	@ManyToOne
	@JoinColumn(name="ID", insertable=false, updatable=false)
	private CsOOperatoreAnagrafica csOOperatoreAnagrafica;
	

	public CsOOperatore() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public Date getDataFineVal() {
		return this.dataFineVal;
	}

	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public Date getDataInizioVal() {
		return this.dataInizioVal;
	}

	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<CsOOperatoreSettore> getCsOOperatoreSettores() {
		return this.csOOperatoreSettores;
	}

	public void setCsOOperatoreSettores(Set<CsOOperatoreSettore> csOOperatoreSettores) {
		this.csOOperatoreSettores = csOOperatoreSettores;
	}

	public CsOOperatoreSettore addCsOOperatoreSettore(CsOOperatoreSettore csOOperatoreSettore) {
		getCsOOperatoreSettores().add(csOOperatoreSettore);
		csOOperatoreSettore.setCsOOperatore(this);

		return csOOperatoreSettore;
	}

	public CsOOperatoreSettore removeCsOOperatoreSettore(CsOOperatoreSettore csOOperatoreSettore) {
		getCsOOperatoreSettores().remove(csOOperatoreSettore);
		csOOperatoreSettore.setCsOOperatore(null);

		return csOOperatoreSettore;
	}

	public CsOOperatoreAnagrafica getCsOOperatoreAnagrafica() {
		return csOOperatoreAnagrafica;
	}

	public void setCsOOperatoreAnagrafica(
			CsOOperatoreAnagrafica csOOperatoreAnagrafica) {
		this.csOOperatoreAnagrafica = csOOperatoreAnagrafica;
	}
	
	public String getDenominazione(){
		denominazione = "";
		if (this.csOOperatoreAnagrafica != null && (csOOperatoreAnagrafica.getCognome()!=null || csOOperatoreAnagrafica.getNome()!=null))
			denominazione = csOOperatoreAnagrafica.getCognome() + " " + csOOperatoreAnagrafica.getNome();
		if(denominazione.trim().isEmpty())
			denominazione = this.username;
		return denominazione;
	}

}