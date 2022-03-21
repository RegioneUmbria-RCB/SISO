package it.webred.ct.data.model.docfa;

import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOCFA_DATI_GENERALI database table.
 * 
 */
@Entity
@Table(name="DOCFA_DATI_GENERALI")
@IdClass(DocfaDatiGeneraliPK.class)
public class DocfaDatiGenerali implements Serializable {
	private static final long serialVersionUID = 1L;

	@IndiceKey(pos="1")
	@Id
    @Temporal( TemporalType.DATE)
	private Date fornitura;
	
	@IndiceKey(pos="2")
	@Id
	@Column(name="PROTOCOLLO_REG")
	private String protocolloReg;
		
	@Column(name="ALTRE_CAUSALI")
	private BigDecimal altreCausali;

	private String annotazioni;

	@Column(name="CAUSALE_NOTA_VAX")
	private String causaleNotaVax;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_MAPPALE")
	private Date dataMappale;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_VARIAZIONE")
	private Date dataVariazione;

	@Column(name="DESC_VARIAZ_A")
	private String descVariazA;

	@Column(name="DESC_VARIAZ_ALTRE")
	private String descVariazAltre;

	@Column(name="DESC_VARIAZ_DA")
	private String descVariazDa;

	@Column(name="ELAB_PL_DENOMINATORE")
	private BigDecimal elabPlDenominatore;

	@Column(name="ELAB_PL_FOGLIO")
	private String elabPlFoglio;

	@Column(name="ELAB_PL_NUMERO")
	private BigDecimal elabPlNumero;

	@Column(name="ELAB_PL_SEZIONE")
	private String elabPlSezione;

	@Column(name="FLAG_AMP")
	private BigDecimal flagAmp;

	@Column(name="FLAG_COMMI_FIN2005")
	private BigDecimal flagCommiFin2005;

	@Column(name="FLAG_DEP")
	private BigDecimal flagDep;

	@Column(name="FLAG_DET")
	private BigDecimal flagDet;

	@Column(name="FLAG_DIV")
	private BigDecimal flagDiv;

	@Column(name="FLAG_EDI")
	private BigDecimal flagEdi;

	@Column(name="FLAG_EDS")
	private BigDecimal flagEds;

	@Column(name="FLAG_FRF")
	private BigDecimal flagFrf;

	@Column(name="FLAG_FRZ")
	private BigDecimal flagFrz;

	@Column(name="FLAG_FUS")
	private BigDecimal flagFus;

	@Column(name="FLAG_NC")
	private BigDecimal flagNc;

	@Column(name="FLAG_R57")
	private BigDecimal flagR57;

	@Column(name="FLAG_RIFERIMENTO")
	private BigDecimal flagRiferimento;

	@Column(name="FLAG_UFU")
	private BigDecimal flagUfu;

	@Column(name="FLAG_VAR")
	private BigDecimal flagVar;

	@Column(name="FLAG_VDE")
	private BigDecimal flagVde;

	@Column(name="FLAG_VMI")
	private BigDecimal flagVmi;

	@Column(name="FLAG_VRP")
	private BigDecimal flagVrp;

	@Column(name="FLAG_VSI")
	private BigDecimal flagVsi;

	@Column(name="FLAG_VTO")
	private BigDecimal flagVto;
	
	@Column(name="ID_FAB_COMUNE")
	private String idFabComune;

	@Column(name="ID_FAB_DENOMINATORE")
	private BigDecimal idFabDenominatore;

	@Column(name="ID_FAB_FOGLIO")
	private String idFabFoglio;

	@Column(name="ID_FAB_NUMERO")
	private BigDecimal idFabNumero;

	@Column(name="ID_FAB_SEZIONE")
	private String idFabSezione;

	@Column(name="ID_FAB_SUBALTERNO")
	private String idFabSubalterno;

	@Column(name="ID_TER_COMUNE")
	private String idTerComune;

	@Column(name="ID_TER_DENOMINATORE")
	private BigDecimal idTerDenominatore;

	@Column(name="ID_TER_FOGLIO")
	private String idTerFoglio;

	@Column(name="ID_TER_NUMERO")
	private BigDecimal idTerNumero;

	@Column(name="ID_TER_SUBALTERNO")
	private String idTerSubalterno;

	@Column(name="NR_DITTA")
	private BigDecimal nrDitta;

	@Column(name="NR_DOC_RIPRESENTATI")
	private String nrDocRipresentati;

	@Column(name="NR_ELAB_PLANIM")
	private BigDecimal nrElabPlanim;

	@Column(name="NR_INTESTATI_DITTA")
	private BigDecimal nrIntestatiDitta;

	@Column(name="NR_MOD_DUE_N_PARTE_DUE")
	private BigDecimal nrModDueNParteDue;

	@Column(name="NR_MOD_DUE_N_PARTE_UNO")
	private BigDecimal nrModDueNParteUno;

	@Column(name="NR_MOD_UNO_N_PARTE_DUE")
	private BigDecimal nrModUnoNParteDue;

	@Column(name="NR_MOD_UNO_N_PARTE_UNO")
	private BigDecimal nrModUnoNParteUno;

	@Column(name="NR_PLANIMETRIE")
	private BigDecimal nrPlanimetrie;

	@Column(name="NR_TOTALE_DITTE")
	private BigDecimal nrTotaleDitte;

	@Column(name="NUMERO_MAPPALE")
	private BigDecimal numeroMappale;

	@Column(name="U_DERIV_BENI_COM_NO_CENS")
	private BigDecimal uDerivBeniComNoCens;

	@Column(name="U_DERIV_DEST_ORDINARIA")
	private BigDecimal uDerivDestOrdinaria;

	@Column(name="U_DERIV_DEST_SPECIALE")
	private BigDecimal uDerivDestSpeciale;

	@Column(name="UIU_AFFE_DENOMINATORE")
	private BigDecimal uiuAffeDenominatore;

	@Column(name="UIU_AFFE_FOGLIO")
	private String uiuAffeFoglio;

	@Column(name="UIU_AFFE_NUMERO")
	private BigDecimal uiuAffeNumero;

	@Column(name="UIU_AFFE_SEZIONE")
	private String uiuAffeSezione;

	@Column(name="UIU_IN_COSTITUZIONE")
	private BigDecimal uiuInCostituzione;

	@Column(name="UIU_IN_SOPPRESSIONE")
	private BigDecimal uiuInSoppressione;

	@Column(name="UIU_IN_VARIAZIONE")
	private BigDecimal uiuInVariazione;

    public DocfaDatiGenerali() {
    }

	public BigDecimal getAltreCausali() {
		return this.altreCausali;
	}

	public void setAltreCausali(BigDecimal altreCausali) {
		this.altreCausali = altreCausali;
	}

	public String getAnnotazioni() {
		return this.annotazioni;
	}

	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public String getCausaleNotaVax() {
		return this.causaleNotaVax;
	}

	public void setCausaleNotaVax(String causaleNotaVax) {
		this.causaleNotaVax = causaleNotaVax;
	}

	public Date getDataMappale() {
		return this.dataMappale;
	}

	public void setDataMappale(Date dataMappale) {
		this.dataMappale = dataMappale;
	}

	public Date getDataVariazione() {
		return this.dataVariazione;
	}

	public void setDataVariazione(Date dataVariazione) {
		this.dataVariazione = dataVariazione;
	}

	public String getDescVariazA() {
		return this.descVariazA;
	}

	public void setDescVariazA(String descVariazA) {
		this.descVariazA = descVariazA;
	}

	public String getDescVariazAltre() {
		return this.descVariazAltre;
	}

	public void setDescVariazAltre(String descVariazAltre) {
		this.descVariazAltre = descVariazAltre;
	}

	public String getDescVariazDa() {
		return this.descVariazDa;
	}

	public void setDescVariazDa(String descVariazDa) {
		this.descVariazDa = descVariazDa;
	}

	public BigDecimal getElabPlDenominatore() {
		return this.elabPlDenominatore;
	}

	public void setElabPlDenominatore(BigDecimal elabPlDenominatore) {
		this.elabPlDenominatore = elabPlDenominatore;
	}

	public String getElabPlFoglio() {
		return this.elabPlFoglio;
	}

	public void setElabPlFoglio(String elabPlFoglio) {
		this.elabPlFoglio = elabPlFoglio;
	}

	public BigDecimal getElabPlNumero() {
		return this.elabPlNumero;
	}

	public void setElabPlNumero(BigDecimal elabPlNumero) {
		this.elabPlNumero = elabPlNumero;
	}

	public String getElabPlSezione() {
		return this.elabPlSezione;
	}

	public void setElabPlSezione(String elabPlSezione) {
		this.elabPlSezione = elabPlSezione;
	}

	public BigDecimal getFlagAmp() {
		return this.flagAmp;
	}

	public void setFlagAmp(BigDecimal flagAmp) {
		this.flagAmp = flagAmp;
	}

	public BigDecimal getFlagCommiFin2005() {
		return this.flagCommiFin2005;
	}

	public void setFlagCommiFin2005(BigDecimal flagCommiFin2005) {
		this.flagCommiFin2005 = flagCommiFin2005;
	}

	public BigDecimal getFlagDep() {
		return this.flagDep;
	}

	public void setFlagDep(BigDecimal flagDep) {
		this.flagDep = flagDep;
	}

	public BigDecimal getFlagDet() {
		return this.flagDet;
	}

	public void setFlagDet(BigDecimal flagDet) {
		this.flagDet = flagDet;
	}

	public BigDecimal getFlagDiv() {
		return this.flagDiv;
	}

	public void setFlagDiv(BigDecimal flagDiv) {
		this.flagDiv = flagDiv;
	}

	public BigDecimal getFlagEdi() {
		return this.flagEdi;
	}

	public void setFlagEdi(BigDecimal flagEdi) {
		this.flagEdi = flagEdi;
	}

	public BigDecimal getFlagEds() {
		return this.flagEds;
	}

	public void setFlagEds(BigDecimal flagEds) {
		this.flagEds = flagEds;
	}

	public BigDecimal getFlagFrf() {
		return this.flagFrf;
	}

	public void setFlagFrf(BigDecimal flagFrf) {
		this.flagFrf = flagFrf;
	}

	public BigDecimal getFlagFrz() {
		return this.flagFrz;
	}

	public void setFlagFrz(BigDecimal flagFrz) {
		this.flagFrz = flagFrz;
	}

	public BigDecimal getFlagFus() {
		return this.flagFus;
	}

	public void setFlagFus(BigDecimal flagFus) {
		this.flagFus = flagFus;
	}

	public BigDecimal getFlagNc() {
		return this.flagNc;
	}

	public void setFlagNc(BigDecimal flagNc) {
		this.flagNc = flagNc;
	}

	public BigDecimal getFlagR57() {
		return this.flagR57;
	}

	public void setFlagR57(BigDecimal flagR57) {
		this.flagR57 = flagR57;
	}

	public BigDecimal getFlagRiferimento() {
		return this.flagRiferimento;
	}

	public void setFlagRiferimento(BigDecimal flagRiferimento) {
		this.flagRiferimento = flagRiferimento;
	}

	public BigDecimal getFlagUfu() {
		return this.flagUfu;
	}

	public void setFlagUfu(BigDecimal flagUfu) {
		this.flagUfu = flagUfu;
	}

	public BigDecimal getFlagVar() {
		return this.flagVar;
	}

	public void setFlagVar(BigDecimal flagVar) {
		this.flagVar = flagVar;
	}

	public BigDecimal getFlagVde() {
		return this.flagVde;
	}

	public void setFlagVde(BigDecimal flagVde) {
		this.flagVde = flagVde;
	}

	public BigDecimal getFlagVmi() {
		return this.flagVmi;
	}

	public void setFlagVmi(BigDecimal flagVmi) {
		this.flagVmi = flagVmi;
	}

	public BigDecimal getFlagVrp() {
		return this.flagVrp;
	}

	public void setFlagVrp(BigDecimal flagVrp) {
		this.flagVrp = flagVrp;
	}

	public BigDecimal getFlagVsi() {
		return this.flagVsi;
	}

	public void setFlagVsi(BigDecimal flagVsi) {
		this.flagVsi = flagVsi;
	}

	public BigDecimal getFlagVto() {
		return this.flagVto;
	}

	public void setFlagVto(BigDecimal flagVto) {
		this.flagVto = flagVto;
	}

	public Date getFornitura() {
		return this.fornitura;
	}

	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}

	public String getIdFabComune() {
		return this.idFabComune;
	}

	public void setIdFabComune(String idFabComune) {
		this.idFabComune = idFabComune;
	}

	public BigDecimal getIdFabDenominatore() {
		return this.idFabDenominatore;
	}

	public void setIdFabDenominatore(BigDecimal idFabDenominatore) {
		this.idFabDenominatore = idFabDenominatore;
	}

	public String getIdFabFoglio() {
		return this.idFabFoglio;
	}

	public void setIdFabFoglio(String idFabFoglio) {
		this.idFabFoglio = idFabFoglio;
	}

	public BigDecimal getIdFabNumero() {
		return this.idFabNumero;
	}

	public void setIdFabNumero(BigDecimal idFabNumero) {
		this.idFabNumero = idFabNumero;
	}

	public String getIdFabSezione() {
		return this.idFabSezione;
	}

	public void setIdFabSezione(String idFabSezione) {
		this.idFabSezione = idFabSezione;
	}

	public String getIdFabSubalterno() {
		return this.idFabSubalterno;
	}

	public void setIdFabSubalterno(String idFabSubalterno) {
		this.idFabSubalterno = idFabSubalterno;
	}

	public String getIdTerComune() {
		return this.idTerComune;
	}

	public void setIdTerComune(String idTerComune) {
		this.idTerComune = idTerComune;
	}

	public BigDecimal getIdTerDenominatore() {
		return this.idTerDenominatore;
	}

	public void setIdTerDenominatore(BigDecimal idTerDenominatore) {
		this.idTerDenominatore = idTerDenominatore;
	}

	public String getIdTerFoglio() {
		return this.idTerFoglio;
	}

	public void setIdTerFoglio(String idTerFoglio) {
		this.idTerFoglio = idTerFoglio;
	}

	public BigDecimal getIdTerNumero() {
		return this.idTerNumero;
	}

	public void setIdTerNumero(BigDecimal idTerNumero) {
		this.idTerNumero = idTerNumero;
	}

	public String getIdTerSubalterno() {
		return this.idTerSubalterno;
	}

	public void setIdTerSubalterno(String idTerSubalterno) {
		this.idTerSubalterno = idTerSubalterno;
	}

	public BigDecimal getNrDitta() {
		return this.nrDitta;
	}

	public void setNrDitta(BigDecimal nrDitta) {
		this.nrDitta = nrDitta;
	}

	public String getNrDocRipresentati() {
		return this.nrDocRipresentati;
	}

	public void setNrDocRipresentati(String nrDocRipresentati) {
		this.nrDocRipresentati = nrDocRipresentati;
	}

	public BigDecimal getNrElabPlanim() {
		return this.nrElabPlanim;
	}

	public void setNrElabPlanim(BigDecimal nrElabPlanim) {
		this.nrElabPlanim = nrElabPlanim;
	}

	public BigDecimal getNrIntestatiDitta() {
		return this.nrIntestatiDitta;
	}

	public void setNrIntestatiDitta(BigDecimal nrIntestatiDitta) {
		this.nrIntestatiDitta = nrIntestatiDitta;
	}

	public BigDecimal getNrModDueNParteDue() {
		return this.nrModDueNParteDue;
	}

	public void setNrModDueNParteDue(BigDecimal nrModDueNParteDue) {
		this.nrModDueNParteDue = nrModDueNParteDue;
	}

	public BigDecimal getNrModDueNParteUno() {
		return this.nrModDueNParteUno;
	}

	public void setNrModDueNParteUno(BigDecimal nrModDueNParteUno) {
		this.nrModDueNParteUno = nrModDueNParteUno;
	}

	public BigDecimal getNrModUnoNParteDue() {
		return this.nrModUnoNParteDue;
	}

	public void setNrModUnoNParteDue(BigDecimal nrModUnoNParteDue) {
		this.nrModUnoNParteDue = nrModUnoNParteDue;
	}

	public BigDecimal getNrModUnoNParteUno() {
		return this.nrModUnoNParteUno;
	}

	public void setNrModUnoNParteUno(BigDecimal nrModUnoNParteUno) {
		this.nrModUnoNParteUno = nrModUnoNParteUno;
	}

	public BigDecimal getNrPlanimetrie() {
		return this.nrPlanimetrie;
	}

	public void setNrPlanimetrie(BigDecimal nrPlanimetrie) {
		this.nrPlanimetrie = nrPlanimetrie;
	}

	public BigDecimal getNrTotaleDitte() {
		return this.nrTotaleDitte;
	}

	public void setNrTotaleDitte(BigDecimal nrTotaleDitte) {
		this.nrTotaleDitte = nrTotaleDitte;
	}

	public BigDecimal getNumeroMappale() {
		return this.numeroMappale;
	}

	public void setNumeroMappale(BigDecimal numeroMappale) {
		this.numeroMappale = numeroMappale;
	}

	public String getProtocolloReg() {
		return this.protocolloReg;
	}

	public void setProtocolloReg(String protocolloReg) {
		this.protocolloReg = protocolloReg;
	}

	public BigDecimal getUDerivBeniComNoCens() {
		return this.uDerivBeniComNoCens;
	}

	public void setUDerivBeniComNoCens(BigDecimal uDerivBeniComNoCens) {
		this.uDerivBeniComNoCens = uDerivBeniComNoCens;
	}

	public BigDecimal getUDerivDestOrdinaria() {
		return this.uDerivDestOrdinaria;
	}

	public void setUDerivDestOrdinaria(BigDecimal uDerivDestOrdinaria) {
		this.uDerivDestOrdinaria = uDerivDestOrdinaria;
	}

	public BigDecimal getUDerivDestSpeciale() {
		return this.uDerivDestSpeciale;
	}

	public void setUDerivDestSpeciale(BigDecimal uDerivDestSpeciale) {
		this.uDerivDestSpeciale = uDerivDestSpeciale;
	}

	public BigDecimal getUiuAffeDenominatore() {
		return this.uiuAffeDenominatore;
	}

	public void setUiuAffeDenominatore(BigDecimal uiuAffeDenominatore) {
		this.uiuAffeDenominatore = uiuAffeDenominatore;
	}

	public String getUiuAffeFoglio() {
		return this.uiuAffeFoglio;
	}

	public void setUiuAffeFoglio(String uiuAffeFoglio) {
		this.uiuAffeFoglio = uiuAffeFoglio;
	}

	public BigDecimal getUiuAffeNumero() {
		return this.uiuAffeNumero;
	}

	public void setUiuAffeNumero(BigDecimal uiuAffeNumero) {
		this.uiuAffeNumero = uiuAffeNumero;
	}

	public String getUiuAffeSezione() {
		return this.uiuAffeSezione;
	}

	public void setUiuAffeSezione(String uiuAffeSezione) {
		this.uiuAffeSezione = uiuAffeSezione;
	}

	public BigDecimal getUiuInCostituzione() {
		return this.uiuInCostituzione;
	}

	public void setUiuInCostituzione(BigDecimal uiuInCostituzione) {
		this.uiuInCostituzione = uiuInCostituzione;
	}

	public BigDecimal getUiuInSoppressione() {
		return this.uiuInSoppressione;
	}

	public void setUiuInSoppressione(BigDecimal uiuInSoppressione) {
		this.uiuInSoppressione = uiuInSoppressione;
	}

	public BigDecimal getUiuInVariazione() {
		return this.uiuInVariazione;
	}

	public void setUiuInVariazione(BigDecimal uiuInVariazione) {
		this.uiuInVariazione = uiuInVariazione;
	}

}