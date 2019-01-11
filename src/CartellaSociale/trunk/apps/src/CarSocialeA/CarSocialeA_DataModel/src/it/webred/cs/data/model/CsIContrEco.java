package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the CS_I_CONTR_ECO database table.
 * 
 */
@Entity
@Table(name="CS_I_CONTR_ECO")
@NamedQuery(name="CsIContrEco.findAll", query="SELECT c FROM CsIContrEco c")
public class CsIContrEco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long interventoId;

	@Column(name="ACCREDITO_A")
	private String accreditoA;

	@Column(name="COMP_CITTA")
	private String compCitta;

	@Column(name="COMP_DENOMINAZIONE")
	private String compDenominazione;

	@Column(name="COMP_INDIRIZZO")
	private String compIndirizzo;

	@Column(name="COMP_TEL")
	private String compTel;

	private String iban;

	@Column(name="RICH_ALTRO_DESC")
	private String richAltroDesc;

	@Column(name="RICH_SE_STESSO")
	private BigDecimal richSeStesso;

	@Column(name="TIPO_PERIODO_EROGAZIONE")
	private String tipoPeriodoErogazione;
	
	@Column(name="TIPO_RISCOSSIONE")
	private String tipoRiscossione;
	
	@Column(name="VAL_RICHIESTO")
	private BigDecimal valRichiesto;
	
	//bi-directional many-to-one association to CsTbTipoContributo
	@ManyToOne
	@JoinColumn(name="TIPO_CONTRIBUTO_ID")
	private CsTbTipoContributo csTbTipoContributo;

	//bi-directional many-to-one association to CsAComponente
	@ManyToOne
	@JoinColumn(name="COMPONENTE_ID")
	private CsAComponente csAComponente;

	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_ID")
	@MapsId
	private CsIIntervento csIIntervento;

	public CsIContrEco() {
	}

	public long getInterventoId() {
		return this.interventoId;
	}

	public void setInterventoId(long interventoId) {
		this.interventoId = interventoId;
	}

	public String getAccreditoA() {
		return this.accreditoA;
	}

	public void setAccreditoA(String accreditoA) {
		this.accreditoA = accreditoA;
	}

	public String getCompCitta() {
		return this.compCitta;
	}

	public void setCompCitta(String compCitta) {
		this.compCitta = compCitta;
	}

	public String getCompDenominazione() {
		return this.compDenominazione;
	}

	public void setCompDenominazione(String compDenominazione) {
		this.compDenominazione = compDenominazione;
	}

	public String getCompIndirizzo() {
		return this.compIndirizzo;
	}

	public void setCompIndirizzo(String compIndirizzo) {
		this.compIndirizzo = compIndirizzo;
	}

	public String getCompTel() {
		return this.compTel;
	}

	public void setCompTel(String compTel) {
		this.compTel = compTel;
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getRichAltroDesc() {
		return this.richAltroDesc;
	}

	public void setRichAltroDesc(String richAltroDesc) {
		this.richAltroDesc = richAltroDesc;
	}

	public BigDecimal getRichSeStesso() {
		return this.richSeStesso;
	}

	public void setRichSeStesso(BigDecimal richSeStesso) {
		this.richSeStesso = richSeStesso;
	}

	public String getTipoRiscossione() {
		return this.tipoRiscossione;
	}

	public void setTipoRiscossione(String tipoRiscossione) {
		this.tipoRiscossione = tipoRiscossione;
	}

	public BigDecimal getValRichiesto() {
		return this.valRichiesto;
	}

	public void setValRichiesto(BigDecimal valRichiesto) {
		this.valRichiesto = valRichiesto;
	}

	public CsAComponente getCsAComponente() {
		return this.csAComponente;
	}

	public void setCsAComponente(CsAComponente csAComponente) {
		this.csAComponente = csAComponente;
	}

	public CsIIntervento getCsIIntervento() {
		return this.csIIntervento;
	}

	public void setCsIIntervento(CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}

	public String getTipoPeriodoErogazione() {
		return tipoPeriodoErogazione;
	}

	public void setTipoPeriodoErogazione(String tipoPeriodoErogazione) {
		this.tipoPeriodoErogazione = tipoPeriodoErogazione;
	}

	public CsTbTipoContributo getCsTbTipoContributo() {
		return csTbTipoContributo;
	}

	public void setCsTbTipoContributo(CsTbTipoContributo csTbTipoContributo) {
		this.csTbTipoContributo = csTbTipoContributo;
	}

}