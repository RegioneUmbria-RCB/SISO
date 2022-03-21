package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="CS_O_OPERATORE")
public class CsOOperatoreBASIC implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_O_OPERATORE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_O_OPERATORE_ID_GENERATOR")
	private Long id;

	private String abilitato;

	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private Date dataFineVal;

	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

	@Basic
	private String tooltip;

	@Basic
	private String username;
	
	
	@Basic
	@Transient
	private String denominazione;
	
	@ManyToOne
	@JoinColumn(name="ID", insertable=false, updatable=false)
	private CsOOperatoreAnagrafica csOOperatoreAnagrafica;

	public CsOOperatoreBASIC() {
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

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
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
		if (this.csOOperatoreAnagrafica != null) 
			denominazione = this.csOOperatoreAnagrafica.getDenominazione();
		if(denominazione.trim().isEmpty())
			denominazione = this.username;
		return denominazione;
	}
}