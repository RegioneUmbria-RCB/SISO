package it.webred.ct.data.model.cnc.statoriscossione;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_FR5_QUIETANZE database table.
 * 
 */
@Entity
@Table(name="RE_CNC_FR5_QUIETANZE")
public class SQuietanze implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ARTICOLO_BILANCIO_ORIGIN")
	private String articoloBilancioOrigin;

	@Column(name="CAPITOLO_BILANCIO")
	private String capitoloBilancio;

	@Column(name="CAPO_BILANCIO")
	private String capoBilancio;

	@Column(name="COD_AMBITO")
	private String codAmbito;

	@Column(name="COD_COMPETENZA_RESIDUI")
	private String codCompetenzaResidui;

	@Column(name="COD_DIVISA_OPERAZ")
	private String codDivisaOperaz;

	@Column(name="COD_ENTE_BENEF")
	private String codEnteBenef;

	@Column(name="COD_TIPO_UFFICIO")
	private String codTipoUfficio;

	@Column(name="COD_TIPO_VERSAMENTO")
	private String codTipoVersamento;

	@Column(name="COD_UFFICIO_BENEF")
	private String codUfficioBenef;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Column(name="DT_QUIETANZA_EFFETT")
	private String dtQuietanzaEffett;

	@Column(name="DT_QUIETANZA_PROVVIS")
	private String dtQuietanzaProvvis;

	@Column(name="DT_QUIETANZA_RIFERIM")
	private String dtQuietanzaRiferim;

	@Column(name="DT_REGISTR_INFORMAZ")
	private String dtRegistrInformaz;

	@Id
	private String id;

	@Column(name="IMPORTO_QUIETANZA")
	private BigDecimal importoQuietanza;

	@Column(name="IMPORTO_SOMME_RECUP")
	private BigDecimal importoSommeRecup;

	@Column(name="IMPORTO_SOMME_TRATT")
	private BigDecimal importoSommeTratt;

	@Column(name="IMPORTO_ULT_SOMME_VERS")
	private BigDecimal importoUltSommeVers;

	@Column(name="IMPORTO_VERS_RUO_ANT_RIF")
	private BigDecimal importoVersRuoAntRif;

	@Column(name="NUMERO_QUIETANZA_EFFETT")
	private BigDecimal numeroQuietanzaEffett;

	@Column(name="NUMERO_QUIETANZA_PROVVIS")
	private BigDecimal numeroQuietanzaProvvis;

	@Column(name="NUMERO_QUIETANZA_RIFERIM")
	private BigDecimal numeroQuietanzaRiferim;

	private String processid;

	@Column(name="PROGRESSIVO_RECORD")
	private BigDecimal progressivoRecord;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

    public SQuietanze() {
    }

	public String getArticoloBilancioOrigin() {
		return this.articoloBilancioOrigin;
	}

	public void setArticoloBilancioOrigin(String articoloBilancioOrigin) {
		this.articoloBilancioOrigin = articoloBilancioOrigin;
	}

	public String getCapitoloBilancio() {
		return this.capitoloBilancio;
	}

	public void setCapitoloBilancio(String capitoloBilancio) {
		this.capitoloBilancio = capitoloBilancio;
	}

	public String getCapoBilancio() {
		return this.capoBilancio;
	}

	public void setCapoBilancio(String capoBilancio) {
		this.capoBilancio = capoBilancio;
	}

	public String getCodAmbito() {
		return this.codAmbito;
	}

	public void setCodAmbito(String codAmbito) {
		this.codAmbito = codAmbito;
	}

	public String getCodCompetenzaResidui() {
		return this.codCompetenzaResidui;
	}

	public void setCodCompetenzaResidui(String codCompetenzaResidui) {
		this.codCompetenzaResidui = codCompetenzaResidui;
	}

	public String getCodDivisaOperaz() {
		return this.codDivisaOperaz;
	}

	public void setCodDivisaOperaz(String codDivisaOperaz) {
		this.codDivisaOperaz = codDivisaOperaz;
	}

	public String getCodEnteBenef() {
		return this.codEnteBenef;
	}

	public void setCodEnteBenef(String codEnteBenef) {
		this.codEnteBenef = codEnteBenef;
	}

	public String getCodTipoUfficio() {
		return this.codTipoUfficio;
	}

	public void setCodTipoUfficio(String codTipoUfficio) {
		this.codTipoUfficio = codTipoUfficio;
	}

	public String getCodTipoVersamento() {
		return this.codTipoVersamento;
	}

	public void setCodTipoVersamento(String codTipoVersamento) {
		this.codTipoVersamento = codTipoVersamento;
	}

	public String getCodUfficioBenef() {
		return this.codUfficioBenef;
	}

	public void setCodUfficioBenef(String codUfficioBenef) {
		this.codUfficioBenef = codUfficioBenef;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getDtQuietanzaEffett() {
		return this.dtQuietanzaEffett;
	}

	public void setDtQuietanzaEffett(String dtQuietanzaEffett) {
		this.dtQuietanzaEffett = dtQuietanzaEffett;
	}

	public String getDtQuietanzaProvvis() {
		return this.dtQuietanzaProvvis;
	}

	public void setDtQuietanzaProvvis(String dtQuietanzaProvvis) {
		this.dtQuietanzaProvvis = dtQuietanzaProvvis;
	}

	public String getDtQuietanzaRiferim() {
		return this.dtQuietanzaRiferim;
	}

	public void setDtQuietanzaRiferim(String dtQuietanzaRiferim) {
		this.dtQuietanzaRiferim = dtQuietanzaRiferim;
	}

	public String getDtRegistrInformaz() {
		return this.dtRegistrInformaz;
	}

	public void setDtRegistrInformaz(String dtRegistrInformaz) {
		this.dtRegistrInformaz = dtRegistrInformaz;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getImportoQuietanza() {
		return this.importoQuietanza;
	}

	public void setImportoQuietanza(BigDecimal importoQuietanza) {
		this.importoQuietanza = importoQuietanza;
	}

	public BigDecimal getImportoSommeRecup() {
		return this.importoSommeRecup;
	}

	public void setImportoSommeRecup(BigDecimal importoSommeRecup) {
		this.importoSommeRecup = importoSommeRecup;
	}

	public BigDecimal getImportoSommeTratt() {
		return this.importoSommeTratt;
	}

	public void setImportoSommeTratt(BigDecimal importoSommeTratt) {
		this.importoSommeTratt = importoSommeTratt;
	}

	public BigDecimal getImportoUltSommeVers() {
		return this.importoUltSommeVers;
	}

	public void setImportoUltSommeVers(BigDecimal importoUltSommeVers) {
		this.importoUltSommeVers = importoUltSommeVers;
	}

	public BigDecimal getImportoVersRuoAntRif() {
		return this.importoVersRuoAntRif;
	}

	public void setImportoVersRuoAntRif(BigDecimal importoVersRuoAntRif) {
		this.importoVersRuoAntRif = importoVersRuoAntRif;
	}

	public BigDecimal getNumeroQuietanzaEffett() {
		return this.numeroQuietanzaEffett;
	}

	public void setNumeroQuietanzaEffett(BigDecimal numeroQuietanzaEffett) {
		this.numeroQuietanzaEffett = numeroQuietanzaEffett;
	}

	public BigDecimal getNumeroQuietanzaProvvis() {
		return this.numeroQuietanzaProvvis;
	}

	public void setNumeroQuietanzaProvvis(BigDecimal numeroQuietanzaProvvis) {
		this.numeroQuietanzaProvvis = numeroQuietanzaProvvis;
	}

	public BigDecimal getNumeroQuietanzaRiferim() {
		return this.numeroQuietanzaRiferim;
	}

	public void setNumeroQuietanzaRiferim(BigDecimal numeroQuietanzaRiferim) {
		this.numeroQuietanzaRiferim = numeroQuietanzaRiferim;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public BigDecimal getProgressivoRecord() {
		return this.progressivoRecord;
	}

	public void setProgressivoRecord(BigDecimal progressivoRecord) {
		this.progressivoRecord = progressivoRecord;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

}