package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the RE_CNC_R5Z_FINE_RUOLO database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R5Z_FINE_RUOLO")
public class VFineRuolo implements Serializable {
	private static final long serialVersionUID = 1L;

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	@Embedded
	private ChiaveULRuolo chiaveRuolo;
	
    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	private String filler;

	@Column(name="IMPORTO_IVA_RIVALSA")
	private String importoIvaRivalsa;

	private String processid;

	@Column(name="PROGRESSIVO_RECORD")
	private String progressivoRecord;


	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	@Column(name="SEGNO_TOTALE_ARR_RUOLO")
	private String segnoTotaleArrRuolo;

	@Column(name="TOT_AGGIO_ANTICIP_RUOLO")
	private String totAggioAnticipRuolo;

	@Column(name="TOT_AGGIO_RUOLO")
	private String totAggioRuolo;

	@Column(name="TOT_ALTRI_IMP_RUOLO")
	private String totAltriImpRuolo;

	@Column(name="TOT_ARROTOND_RUOLO")
	private String totArrotondRuolo;

	@Column(name="TOT_CAPITALE_RUOLO")
	private String totCapitaleRuolo;

	@Column(name="TOT_CARICO_RUOLO")
	private String totCaricoRuolo;

	@Column(name="TOT_INTERESSI_RUOLO")
	private String totInteressiRuolo;

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

	@Column(name="TOT_REC_FRONT_RUO_R5B")
	private String totRecFrontRuoR5b;

	@Column(name="TOT_REC_RATE_R7G")
	private String totRecRateR7g;

	@Column(name="TOT_REC_RUOLO")
	private String totRecRuolo;

	@Column(name="TOT_REC_ULT_ANA_R7H")
	private String totRecUltAnaR7h;

	@Column(name="TOT_REC_ULT_BENEF_R7F")
	private String totRecUltBenefR7f;

	@Column(name="TOT_SANZIONI_RUOLO")
	private String totSanzioniRuolo;

    public VFineRuolo() {
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

	public String getImportoIvaRivalsa() {
		return this.importoIvaRivalsa;
	}

	public void setImportoIvaRivalsa(String importoIvaRivalsa) {
		this.importoIvaRivalsa = importoIvaRivalsa;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProgressivoRecord() {
		return this.progressivoRecord;
	}

	public void setProgressivoRecord(String progressivoRecord) {
		this.progressivoRecord = progressivoRecord;
	}


	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public String getSegnoTotaleArrRuolo() {
		return this.segnoTotaleArrRuolo;
	}

	public void setSegnoTotaleArrRuolo(String segnoTotaleArrRuolo) {
		this.segnoTotaleArrRuolo = segnoTotaleArrRuolo;
	}

	public String getTotAggioAnticipRuolo() {
		return this.totAggioAnticipRuolo;
	}

	public void setTotAggioAnticipRuolo(String totAggioAnticipRuolo) {
		this.totAggioAnticipRuolo = totAggioAnticipRuolo;
	}

	public String getTotAggioRuolo() {
		return this.totAggioRuolo;
	}

	public void setTotAggioRuolo(String totAggioRuolo) {
		this.totAggioRuolo = totAggioRuolo;
	}

	public String getTotAltriImpRuolo() {
		return this.totAltriImpRuolo;
	}

	public void setTotAltriImpRuolo(String totAltriImpRuolo) {
		this.totAltriImpRuolo = totAltriImpRuolo;
	}

	public String getTotArrotondRuolo() {
		return this.totArrotondRuolo;
	}

	public void setTotArrotondRuolo(String totArrotondRuolo) {
		this.totArrotondRuolo = totArrotondRuolo;
	}

	public String getTotCapitaleRuolo() {
		return this.totCapitaleRuolo;
	}

	public void setTotCapitaleRuolo(String totCapitaleRuolo) {
		this.totCapitaleRuolo = totCapitaleRuolo;
	}

	public String getTotCaricoRuolo() {
		return this.totCaricoRuolo;
	}

	public void setTotCaricoRuolo(String totCaricoRuolo) {
		this.totCaricoRuolo = totCaricoRuolo;
	}

	public String getTotInteressiRuolo() {
		return this.totInteressiRuolo;
	}

	public void setTotInteressiRuolo(String totInteressiRuolo) {
		this.totInteressiRuolo = totInteressiRuolo;
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

	public String getTotRecFrontRuoR5b() {
		return this.totRecFrontRuoR5b;
	}

	public void setTotRecFrontRuoR5b(String totRecFrontRuoR5b) {
		this.totRecFrontRuoR5b = totRecFrontRuoR5b;
	}

	public String getTotRecRateR7g() {
		return this.totRecRateR7g;
	}

	public void setTotRecRateR7g(String totRecRateR7g) {
		this.totRecRateR7g = totRecRateR7g;
	}

	public String getTotRecRuolo() {
		return this.totRecRuolo;
	}

	public void setTotRecRuolo(String totRecRuolo) {
		this.totRecRuolo = totRecRuolo;
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

	public String getTotSanzioniRuolo() {
		return this.totSanzioniRuolo;
	}

	public void setTotSanzioniRuolo(String totSanzioniRuolo) {
		this.totSanzioniRuolo = totSanzioniRuolo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public ChiaveULRuolo getChiaveRuolo() {
		return chiaveRuolo;
	}


	public void setChiaveRuolo(ChiaveULRuolo chiaveRuolo) {
		this.chiaveRuolo = chiaveRuolo;
	}
	
	

}