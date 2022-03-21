package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CS_I_SEMI_RESI_MIN database table.
 * 
 */
@Entity
@Table(name="CS_I_SEMI_RESI_MIN")
@NamedQuery(name="CsISemiResiMin.findAll", query="SELECT c FROM CsISemiResiMin c")
public class CsISemiResiMin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long interventoId;

	private BigDecimal anno;

	@Column(name="COMUNITA_ALTRO")
	private String comunitaAltro;

	@Column(name="CONTRIBUTO_MENSILE_FAMI_ORI")
	private BigDecimal contributoMensileFamiOri;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_PROV_AG")
	private Date dtProvAg;

	@Column(name="FLAG_ALTRO")
	private BigDecimal flagAltro;

	@Column(name="FLAG_CENE")
	private BigDecimal flagCene;

	@Column(name="FLAG_INTERVENTO_EDU")
	private BigDecimal flagInterventoEdu;

	@Column(name="FLAG_PERNOTTAMENTO")
	private BigDecimal flagPernottamento;

	@Column(name="FLAG_PRANZO")
	private BigDecimal flagPranzo;

	@Column(name="FLAG_SENZA_PROVVEDIMENTO")
	private BigDecimal flagSenzaProvvedimento;

	@Column(name="FLAG_TRASPORTO_CC")
	private BigDecimal flagTrasportoCc;

	@Column(name="FLAG_TRASPORTO_SC")
	private BigDecimal flagTrasportoSc;

	private BigDecimal importo;

	@Column(name="N_PROVV_AG")
	private String nProvvAg;

	@Column(name="NUM_GIORNI_SETT")
	private BigDecimal numGiorniSett;

	@Column(name="VALORE_RETTA")
	private BigDecimal valoreRetta;

	//bi-directional many-to-one association to CsCComunita
	@ManyToOne
	@JoinColumn(name="COMUNITA_SETTORE_ID")
	private CsCComunita csCComunita;

	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_ID")
	@MapsId
	private CsIIntervento csIIntervento;

	//bi-directional many-to-one association to CsTbTipoRetta
	@ManyToOne
	@JoinColumn(name="TIPO_RETTA_ID")
	private CsTbTipoRetta csTbTipoRetta;

	public CsISemiResiMin() {
	}

	public long getInterventoId() {
		return this.interventoId;
	}

	public void setInterventoId(long interventoId) {
		this.interventoId = interventoId;
	}

	public BigDecimal getAnno() {
		return this.anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public String getComunitaAltro() {
		return this.comunitaAltro;
	}

	public void setComunitaAltro(String comunitaAltro) {
		this.comunitaAltro = comunitaAltro;
	}

	public BigDecimal getContributoMensileFamiOri() {
		return this.contributoMensileFamiOri;
	}

	public void setContributoMensileFamiOri(BigDecimal contributoMensileFamiOri) {
		this.contributoMensileFamiOri = contributoMensileFamiOri;
	}

	public Date getDtProvAg() {
		return this.dtProvAg;
	}

	public void setDtProvAg(Date dtProvAg) {
		this.dtProvAg = dtProvAg;
	}

	public BigDecimal getFlagAltro() {
		return this.flagAltro;
	}

	public void setFlagAltro(BigDecimal flagAltro) {
		this.flagAltro = flagAltro;
	}

	public BigDecimal getFlagCene() {
		return this.flagCene;
	}

	public void setFlagCene(BigDecimal flagCene) {
		this.flagCene = flagCene;
	}

	public BigDecimal getFlagInterventoEdu() {
		return this.flagInterventoEdu;
	}

	public void setFlagInterventoEdu(BigDecimal flagInterventoEdu) {
		this.flagInterventoEdu = flagInterventoEdu;
	}

	public BigDecimal getFlagPernottamento() {
		return this.flagPernottamento;
	}

	public void setFlagPernottamento(BigDecimal flagPernottamento) {
		this.flagPernottamento = flagPernottamento;
	}

	public BigDecimal getFlagPranzo() {
		return this.flagPranzo;
	}

	public void setFlagPranzo(BigDecimal flagPranzo) {
		this.flagPranzo = flagPranzo;
	}

	public BigDecimal getFlagSenzaProvvedimento() {
		return this.flagSenzaProvvedimento;
	}

	public void setFlagSenzaProvvedimento(BigDecimal flagSenzaProvvedimento) {
		this.flagSenzaProvvedimento = flagSenzaProvvedimento;
	}

	public BigDecimal getFlagTrasportoCc() {
		return flagTrasportoCc;
	}

	public void setFlagTrasportoCc(BigDecimal flagTrasportoCc) {
		this.flagTrasportoCc = flagTrasportoCc;
	}

	public String getnProvvAg() {
		return nProvvAg;
	}

	public void setnProvvAg(String nProvvAg) {
		this.nProvvAg = nProvvAg;
	}

	public BigDecimal getFlagTrasportoSc() {
		return this.flagTrasportoSc;
	}

	public void setFlagTrasportoSc(BigDecimal flagTrasportoSc) {
		this.flagTrasportoSc = flagTrasportoSc;
	}

	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getNProvvAg() {
		return this.nProvvAg;
	}

	public void setNProvvAg(String nProvvAg) {
		this.nProvvAg = nProvvAg;
	}

	public BigDecimal getNumGiorniSett() {
		return this.numGiorniSett;
	}

	public void setNumGiorniSett(BigDecimal numGiorniSett) {
		this.numGiorniSett = numGiorniSett;
	}

	public BigDecimal getValoreRetta() {
		return this.valoreRetta;
	}

	public void setValoreRetta(BigDecimal valoreRetta) {
		this.valoreRetta = valoreRetta;
	}

	public CsCComunita getCsCComunita() {
		return this.csCComunita;
	}

	public void setCsCComunita(CsCComunita csCComunita) {
		this.csCComunita = csCComunita;
	}

	public CsIIntervento getCsIIntervento() {
		return this.csIIntervento;
	}

	public void setCsIIntervento(CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}

	public CsTbTipoRetta getCsTbTipoRetta() {
		return this.csTbTipoRetta;
	}

	public void setCsTbTipoRetta(CsTbTipoRetta csTbTipoRetta) {
		this.csTbTipoRetta = csTbTipoRetta;
	}

}