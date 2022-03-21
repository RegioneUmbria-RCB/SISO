package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the RE_CNC_R3Z_CODA database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R3Z_CODA")
public class VCodaFlusso implements Serializable {
	private static final long serialVersionUID = 1L;

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	private String anno;

	@Column(name="COD_AMBITO")
	private String codAmbito;

	@Column(name="COD_AMBITO_ID")
	private String codAmbitoId;

	@Column(name="COD_DIVISA_OPERAZ")
	private String codDivisaOperaz;

	@Column(name="COD_ENTE_CREDITORE")
	private String codEnteCreditore;

	@Column(name="COD_ENTE_CREDITORE_ID")
	private String codEnteCreditoreId;

	@Column(name="COD_SIGLA")
	private String codSigla;

	@Column(name="DT_CONSEGNA_RUOLI")
	private String dtConsegnaRuoli;

	@Column(name="DT_CREAZIONE_FILE")
	private String dtCreazioneFile;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	private String filler;

	private String processid;

	private String progressivo;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	@Column(name="SEGNO_TOT_ARR")
	private String segnoTotArr;

	@Column(name="TOT_AGGIO")
	private String totAggio;

	@Column(name="TOT_AGGIO_ANTICIP")
	private String totAggioAnticip;

	@Column(name="TOT_ALTRI_IMP")
	private String totAltriImp;

	@Column(name="TOT_ARROTOND")
	private String totArrotond;

	@Column(name="TOT_CAPITALE")
	private String totCapitale;

	@Column(name="TOT_CARICO")
	private String totCarico;

	@Column(name="TOT_INTERESSI")
	private String totInteressi;

	@Column(name="TOT_REC")
	private String totRec;

	@Column(name="TOT_REC_ANA_COIN_R7K")
	private String totRecAnaCoinR7k;

	@Column(name="TOT_REC_ANA_DEB_R7A")
	private String totRecAnaDebR7a;

	@Column(name="TOT_REC_ANA_ULT_DEST_R7B")
	private String totRecAnaUltDestR7b;

	@Column(name="TOT_REC_ART_R7E")
	private String totRecArtR7e;

	@Column(name="TOT_REC_CAR_R7D")
	private String totRecCarR7d;

	@Column(name="TOT_REC_CAR_SPE_R7C")
	private String totRecCarSpeR7c;

	@Column(name="TOT_REC_FINE_RUO_R5Z")
	private String totRecFineRuoR5z;

	@Column(name="TOT_REC_FRONT_RUO_R5B")
	private String totRecFrontRuoR5b;

	@Column(name="TOT_REC_INIZIO_RUO_R5A")
	private String totRecInizioRuoR5a;

	@Column(name="TOT_REC_RATE_R7G")
	private String totRecRateR7g;

	@Column(name="TOT_REC_ULT_ANA_R7H")
	private String totRecUltAnaR7h;

	@Column(name="TOT_REC_ULT_BENEF_R7F")
	private String totRecUltBenefR7f;

	@Column(name="TOT_SANZIONI")
	private String totSanzioni;

    public VCodaFlusso() {
    }

	public String getAnno() {
		return this.anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getCodAmbito() {
		return this.codAmbito;
	}

	public void setCodAmbito(String codAmbito) {
		this.codAmbito = codAmbito;
	}

	public String getCodAmbitoId() {
		return this.codAmbitoId;
	}

	public void setCodAmbitoId(String codAmbitoId) {
		this.codAmbitoId = codAmbitoId;
	}

	public String getCodDivisaOperaz() {
		return this.codDivisaOperaz;
	}

	public void setCodDivisaOperaz(String codDivisaOperaz) {
		this.codDivisaOperaz = codDivisaOperaz;
	}

	public String getCodEnteCreditore() {
		return this.codEnteCreditore;
	}

	public void setCodEnteCreditore(String codEnteCreditore) {
		this.codEnteCreditore = codEnteCreditore;
	}

	public String getCodEnteCreditoreId() {
		return this.codEnteCreditoreId;
	}

	public void setCodEnteCreditoreId(String codEnteCreditoreId) {
		this.codEnteCreditoreId = codEnteCreditoreId;
	}

	public String getCodSigla() {
		return this.codSigla;
	}

	public void setCodSigla(String codSigla) {
		this.codSigla = codSigla;
	}

	public String getDtConsegnaRuoli() {
		return this.dtConsegnaRuoli;
	}

	public void setDtConsegnaRuoli(String dtConsegnaRuoli) {
		this.dtConsegnaRuoli = dtConsegnaRuoli;
	}

	public String getDtCreazioneFile() {
		return this.dtCreazioneFile;
	}

	public void setDtCreazioneFile(String dtCreazioneFile) {
		this.dtCreazioneFile = dtCreazioneFile;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getFiller() {
		return this.filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProgressivo() {
		return this.progressivo;
	}

	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public String getSegnoTotArr() {
		return this.segnoTotArr;
	}

	public void setSegnoTotArr(String segnoTotArr) {
		this.segnoTotArr = segnoTotArr;
	}

	public String getTotAggio() {
		return this.totAggio;
	}

	public void setTotAggio(String totAggio) {
		this.totAggio = totAggio;
	}

	public String getTotAggioAnticip() {
		return this.totAggioAnticip;
	}

	public void setTotAggioAnticip(String totAggioAnticip) {
		this.totAggioAnticip = totAggioAnticip;
	}

	public String getTotAltriImp() {
		return this.totAltriImp;
	}

	public void setTotAltriImp(String totAltriImp) {
		this.totAltriImp = totAltriImp;
	}

	public String getTotArrotond() {
		return this.totArrotond;
	}

	public void setTotArrotond(String totArrotond) {
		this.totArrotond = totArrotond;
	}

	public String getTotCapitale() {
		return this.totCapitale;
	}

	public void setTotCapitale(String totCapitale) {
		this.totCapitale = totCapitale;
	}

	public String getTotCarico() {
		return this.totCarico;
	}

	public void setTotCarico(String totCarico) {
		this.totCarico = totCarico;
	}

	public String getTotInteressi() {
		return this.totInteressi;
	}

	public void setTotInteressi(String totInteressi) {
		this.totInteressi = totInteressi;
	}

	public String getTotRec() {
		return this.totRec;
	}

	public void setTotRec(String totRec) {
		this.totRec = totRec;
	}

	public String getTotRecAnaCoinR7k() {
		return this.totRecAnaCoinR7k;
	}

	public void setTotRecAnaCoinR7k(String totRecAnaCoinR7k) {
		this.totRecAnaCoinR7k = totRecAnaCoinR7k;
	}

	public String getTotRecAnaDebR7a() {
		return this.totRecAnaDebR7a;
	}

	public void setTotRecAnaDebR7a(String totRecAnaDebR7a) {
		this.totRecAnaDebR7a = totRecAnaDebR7a;
	}

	public String getTotRecAnaUltDestR7b() {
		return this.totRecAnaUltDestR7b;
	}

	public void setTotRecAnaUltDestR7b(String totRecAnaUltDestR7b) {
		this.totRecAnaUltDestR7b = totRecAnaUltDestR7b;
	}

	public String getTotRecArtR7e() {
		return this.totRecArtR7e;
	}

	public void setTotRecArtR7e(String totRecArtR7e) {
		this.totRecArtR7e = totRecArtR7e;
	}

	public String getTotRecCarR7d() {
		return this.totRecCarR7d;
	}

	public void setTotRecCarR7d(String totRecCarR7d) {
		this.totRecCarR7d = totRecCarR7d;
	}

	public String getTotRecCarSpeR7c() {
		return this.totRecCarSpeR7c;
	}

	public void setTotRecCarSpeR7c(String totRecCarSpeR7c) {
		this.totRecCarSpeR7c = totRecCarSpeR7c;
	}

	public String getTotRecFineRuoR5z() {
		return this.totRecFineRuoR5z;
	}

	public void setTotRecFineRuoR5z(String totRecFineRuoR5z) {
		this.totRecFineRuoR5z = totRecFineRuoR5z;
	}

	public String getTotRecFrontRuoR5b() {
		return this.totRecFrontRuoR5b;
	}

	public void setTotRecFrontRuoR5b(String totRecFrontRuoR5b) {
		this.totRecFrontRuoR5b = totRecFrontRuoR5b;
	}

	public String getTotRecInizioRuoR5a() {
		return this.totRecInizioRuoR5a;
	}

	public void setTotRecInizioRuoR5a(String totRecInizioRuoR5a) {
		this.totRecInizioRuoR5a = totRecInizioRuoR5a;
	}

	public String getTotRecRateR7g() {
		return this.totRecRateR7g;
	}

	public void setTotRecRateR7g(String totRecRateR7g) {
		this.totRecRateR7g = totRecRateR7g;
	}

	public String getTotRecUltAnaR7h() {
		return this.totRecUltAnaR7h;
	}

	public void setTotRecUltAnaR7h(String totRecUltAnaR7h) {
		this.totRecUltAnaR7h = totRecUltAnaR7h;
	}

	public String getTotRecUltBenefR7f() {
		return this.totRecUltBenefR7f;
	}

	public void setTotRecUltBenefR7f(String totRecUltBenefR7f) {
		this.totRecUltBenefR7f = totRecUltBenefR7f;
	}

	public String getTotSanzioni() {
		return this.totSanzioni;
	}

	public void setTotSanzioni(String totSanzioni) {
		this.totSanzioni = totSanzioni;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}