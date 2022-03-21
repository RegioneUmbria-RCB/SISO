package it.webred.ct.data.model.diagnostiche;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the DOCFA_TAR_REPORT database table.
 * 
 */
@Entity
@Table(name="DOCFA_TAR_REPORT")
public class DocfaTarReport implements Serializable {
	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger("ctservice.log");
	
	@Transient
	private String annotazioni;

	@Column(name="CATEGORIA_CATASTO")
	private String categoriaCatasto;

	@Column(name="CATEGORIA_DOCFA")
	private String categoriaDocfa;

	@Column(name="CAUSALE_DOCFA")
	private String causaleDocfa;

	@Column(name="CIVICI_DOCFA")
	private String civiciDocfa;

	@Column(name="CIVICO_CATASTO")
	private String civicoCatasto;

	@Column(name="COD_ANOMALIE")
	private String codAnomalie;

	@Column(name="COD_ENTE")
	private String codEnte;

	@Column(name="COD_VIA_CATASTO")
	private BigDecimal codViaCatasto;

	@Column(name="CONS_CALC")
	private String consCalc;

	@Column(name="CONSISTENZA_DOCFA")
	private String consistenzaDocfa;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_AGGIORNAMENTO")
	private Date dataAggiornamento;
    
    @Temporal( TemporalType.DATE)
	@Column(name="DATA_VARIAZIONE")
	private Date dataVariazione;

	@Column(name="DATA_DOCFA")
	private String dataDocfa;

	@Column(name="DATA_DOCFA_SUCCESSIVO")
	private String dataDocfaSuccessivo;

	@Column(name="DIC_COGNOME")
	private String dicCognome;

	@Column(name="DIC_NOME")
	private String dicNome;

	@Column(name="DIC_RES_CAP")
	private String dicResCap;

	@Column(name="DIC_RES_COM")
	private String dicResCom;

	@Column(name="DIC_RES_INDIR")
	private String dicResIndir;

	@Column(name="DIC_RES_NUMCIV")
	private String dicResNumciv;

	@Column(name="DIC_RES_PROV")
	private String dicResProv;

	@Column(name="DOCFA_CONTEMPORANEI")
	private String docfaContemporanei;

	@Column(name="DOCFA_IN_ANNO")
	private String docfaInAnno;

	@Column(name="FLG_ANOMALIE")
	private String flgAnomalie;

	@Column(name="FLG_DOCFA_S_NO_C")
	private String flgDocfaSNoC;

	@Column(name="FLG_ELABORATO")
	private String flgElaborato;

	@Column(name="FLG_INDIRIZZO_IN_TOPONOMASTICA")
	private String flgIndirizzoInToponomastica;

	@Column(name="FLG_TAR_ANTE_DOCFA")
	private String flgTarAnteDocfa;

	@Column(name="FLG_TAR_POST_DOCFA")
	private String flgTarPostDocfa;

	@Column(name="FLG_SUP_TAR_POST_MAG_PRE")
	private String flgSupTarPostMagPre;
	
	@Column(name="FLG_SUP_TAR_POST_MIN_PRE")
	private String flgSupTarPostMinPre;
	
	@Column(name="FLG_SUP_TAR_POST_MAG_C340")
	private String flgSupTarPostMagC340;
	
	@Column(name="FLG_SUP_TAR_POST_MIN_C340")
	private String flgSupTarPostMinC340;
	
	@Column(name="FLG_UIU_CATASTO")
	private String flgUiuCatasto;

	private String foglio;

    @Temporal( TemporalType.DATE)
	private Date fornitura;

    @Id
	@Column(name="ID_REP")
	private String idRep;

	private String particella;

	@Column(name="PREFISSO_VIA_CATASTO")
	private String prefissoViaCatasto;

	@Column(name="PREFISSO_VIA_DOCFA")
	private String prefissoViaDocfa;

	@Column(name="PROTOCOLLO_DOCFA")
	private String protocolloDocfa;

	private String sezione;

	private String subalterno;

	@Column(name="SUP_AVG_MAX")
	private String supAvgMax;

	@Column(name="SUP_AVG_MIN")
	private String supAvgMin;

	@Column(name="SUP_CATASTO")
	private BigDecimal supCatasto;

	@Column(name="SUP_DOCFA_CENS")
	private String supDocfaCens;

	@Column(name="SUP_DOCFA_TARSU")
	private BigDecimal supDocfaTarsu;
	
	@Column(name="SUP_C340_ABC")
	private BigDecimal supC340Abc;
	
	@Column(name="SUP_DIPENDENZE_ESCL")
	private BigDecimal supDipendenzeEscl;
	
	@Column(name="TIPO_OPER_DOCFA")
	private String tipoOperDocfa;

	@Column(name="UIU_COSTITUITE")
	private BigDecimal uiuCostituite;

	@Column(name="UIU_SOPPRESSE")
	private BigDecimal uiuSoppresse;

	@Column(name="UIU_VARIATE")
	private BigDecimal uiuVariate;

	@Column(name="VIA_CATASTO")
	private String viaCatasto;

	@Column(name="VIA_DOCFA")
	private String viaDocfa;

    public DocfaTarReport() {
    }

	public String getAnnotazioni() {
		return this.annotazioni;
	}

	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public String getCategoriaCatasto() {
		return this.categoriaCatasto;
	}

	public void setCategoriaCatasto(String categoriaCatasto) {
		this.categoriaCatasto = categoriaCatasto;
	}

	public String getCategoriaDocfa() {
		return this.categoriaDocfa;
	}

	public void setCategoriaDocfa(String categoriaDocfa) {
		this.categoriaDocfa = categoriaDocfa;
	}

	public String getCausaleDocfa() {
		return this.causaleDocfa;
	}

	public void setCausaleDocfa(String causaleDocfa) {
		this.causaleDocfa = causaleDocfa;
	}

	public String getCiviciDocfa() {
		return this.civiciDocfa;
	}

	public void setCiviciDocfa(String civiciDocfa) {
		this.civiciDocfa = civiciDocfa;
	}

	public String getCivicoCatasto() {
		return this.civicoCatasto;
	}

	public void setCivicoCatasto(String civicoCatasto) {
		this.civicoCatasto = civicoCatasto;
	}

	public String getCodAnomalie() {
		return this.codAnomalie;
	}

	public void setCodAnomalie(String codAnomalie) {
		this.codAnomalie = codAnomalie;
	}

	public String getCodEnte() {
		return this.codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	public BigDecimal getCodViaCatasto() {
		return this.codViaCatasto;
	}

	public void setCodViaCatasto(BigDecimal codViaCatasto) {
		this.codViaCatasto = codViaCatasto;
	}

	public String getConsCalc() {
		return this.consCalc;
	}

	public void setConsCalc(String consCalc) {
		this.consCalc = consCalc;
	}

	public String getConsistenzaDocfa() {
		return this.consistenzaDocfa;
	}

	public void setConsistenzaDocfa(String consistenzaDocfa) {
		this.consistenzaDocfa = consistenzaDocfa;
	}

	public Date getDataAggiornamento() {
		return this.dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public String getDataDocfa() {
		return this.dataDocfa;
	}

	public Date getDataDocfaToDate() {
		if (this.dataDocfa != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			try {
				return sdf.parse(this.dataDocfa);
			} catch (ParseException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return null;
	}
	
	public void setDataDocfa(String dataDocfa) {
		this.dataDocfa = dataDocfa;
	}

	public String getDataDocfaSuccessivo() {
		return this.dataDocfaSuccessivo;
	}

	public void setDataDocfaSuccessivo(String dataDocfaSuccessivo) {
		this.dataDocfaSuccessivo = dataDocfaSuccessivo;
	}

	public String getDicCognome() {
		return this.dicCognome;
	}

	public void setDicCognome(String dicCognome) {
		this.dicCognome = dicCognome;
	}

	public String getDicNome() {
		return this.dicNome;
	}

	public void setDicNome(String dicNome) {
		this.dicNome = dicNome;
	}

	public String getDicResCap() {
		return this.dicResCap;
	}

	public void setDicResCap(String dicResCap) {
		this.dicResCap = dicResCap;
	}

	public String getDicResCom() {
		return this.dicResCom;
	}

	public void setDicResCom(String dicResCom) {
		this.dicResCom = dicResCom;
	}

	public String getDicResIndir() {
		return this.dicResIndir;
	}

	public void setDicResIndir(String dicResIndir) {
		this.dicResIndir = dicResIndir;
	}

	public String getDicResNumciv() {
		return this.dicResNumciv;
	}

	public void setDicResNumciv(String dicResNumciv) {
		this.dicResNumciv = dicResNumciv;
	}

	public String getDicResProv() {
		return this.dicResProv;
	}

	public void setDicResProv(String dicResProv) {
		this.dicResProv = dicResProv;
	}

	public String getDocfaContemporanei() {
		return this.docfaContemporanei;
	}

	public void setDocfaContemporanei(String docfaContemporanei) {
		this.docfaContemporanei = docfaContemporanei;
	}

	public String getDocfaInAnno() {
		return this.docfaInAnno;
	}

	public void setDocfaInAnno(String docfaInAnno) {
		this.docfaInAnno = docfaInAnno;
	}

	public String getFlgAnomalie() {
		return this.flgAnomalie;
	}

	public void setFlgAnomalie(String flgAnomalie) {
		this.flgAnomalie = flgAnomalie;
	}

	public String getFlgDocfaSNoC() {
		return this.flgDocfaSNoC;
	}

	public void setFlgDocfaSNoC(String flgDocfaSNoC) {
		this.flgDocfaSNoC = flgDocfaSNoC;
	}

	public String getFlgElaborato() {
		return this.flgElaborato;
	}

	public void setFlgElaborato(String flgElaborato) {
		this.flgElaborato = flgElaborato;
	}

	public String getFlgIndirizzoInToponomastica() {
		return this.flgIndirizzoInToponomastica;
	}

	public void setFlgIndirizzoInToponomastica(String flgIndirizzoInToponomastica) {
		this.flgIndirizzoInToponomastica = flgIndirizzoInToponomastica;
	}

	public String getFlgTarAnteDocfa() {
		return this.flgTarAnteDocfa;
	}

	public void setFlgTarAnteDocfa(String flgTarAnteDocfa) {
		this.flgTarAnteDocfa = flgTarAnteDocfa;
	}

	public String getFlgTarPostDocfa() {
		return this.flgTarPostDocfa;
	}

	public void setFlgTarPostDocfa(String flgTarPostDocfa) {
		this.flgTarPostDocfa = flgTarPostDocfa;
	}

	public String getFlgSupTarPostMagPre() {
		return flgSupTarPostMagPre;
	}

	public void setFlgSupTarPostMagPre(String flgSupTarPostMagPre) {
		this.flgSupTarPostMagPre = flgSupTarPostMagPre;
	}

	public String getFlgSupTarPostMinPre() {
		return flgSupTarPostMinPre;
	}

	public void setFlgSupTarPostMinPre(String flgSupTarPostMinPre) {
		this.flgSupTarPostMinPre = flgSupTarPostMinPre;
	}

	public String getFlgSupTarPostMagC340() {
		return flgSupTarPostMagC340;
	}

	public void setFlgSupTarPostMagC340(String flgSupTarPostMagC340) {
		this.flgSupTarPostMagC340 = flgSupTarPostMagC340;
	}

	public String getFlgSupTarPostMinC340() {
		return flgSupTarPostMinC340;
	}

	public void setFlgSupTarPostMinC340(String flgSupTarPostMinC340) {
		this.flgSupTarPostMinC340 = flgSupTarPostMinC340;
	}

	public String getFlgUiuCatasto() {
		return this.flgUiuCatasto;
	}

	public void setFlgUiuCatasto(String flgUiuCatasto) {
		this.flgUiuCatasto = flgUiuCatasto;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public Date getFornitura() {
		return this.fornitura;
	}

	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}

	public String getIdRep() {
		return this.idRep;
	}

	public void setIdRep(String idRep) {
		this.idRep = idRep;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getPrefissoViaCatasto() {
		return this.prefissoViaCatasto;
	}

	public void setPrefissoViaCatasto(String prefissoViaCatasto) {
		this.prefissoViaCatasto = prefissoViaCatasto;
	}

	public String getPrefissoViaDocfa() {
		return this.prefissoViaDocfa;
	}

	public void setPrefissoViaDocfa(String prefissoViaDocfa) {
		this.prefissoViaDocfa = prefissoViaDocfa;
	}

	public String getProtocolloDocfa() {
		return this.protocolloDocfa;
	}

	public void setProtocolloDocfa(String protocolloDocfa) {
		this.protocolloDocfa = protocolloDocfa;
	}

	public String getSezione() {
		return this.sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getSubalterno() {
		return this.subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getSupAvgMax() {
		return this.supAvgMax;
	}

	public void setSupAvgMax(String supAvgMax) {
		this.supAvgMax = supAvgMax;
	}

	public String getSupAvgMin() {
		return this.supAvgMin;
	}

	public void setSupAvgMin(String supAvgMin) {
		this.supAvgMin = supAvgMin;
	}

	public BigDecimal getSupCatasto() {
		return this.supCatasto;
	}

	public void setSupCatasto(BigDecimal supCatasto) {
		this.supCatasto = supCatasto;
	}

	public String getSupDocfaCens() {
		return this.supDocfaCens;
	}

	public void setSupDocfaCens(String supDocfaCens) {
		this.supDocfaCens = supDocfaCens;
	}

	public BigDecimal getSupDocfaTarsu() {
		return this.supDocfaTarsu;
	}

	public void setSupDocfaTarsu(BigDecimal supDocfaTarsu) {
		this.supDocfaTarsu = supDocfaTarsu;
	}

	public String getTipoOperDocfa() {
		return this.tipoOperDocfa;
	}

	public String getTipoOperDocfaEx() {
		String tipoOp = "";
		if (this.tipoOperDocfa != null) {
			if (this.tipoOperDocfa.equals("S"))
				tipoOp = "Soppressione";
			if (this.tipoOperDocfa.equals("C"))
				tipoOp = "Costituzione";
			if (this.tipoOperDocfa.equals("V"))
				tipoOp = "Variazione";
		}
		return tipoOp;
	}
	
	public void setTipoOperDocfa(String tipoOperDocfa) {
		this.tipoOperDocfa = tipoOperDocfa;
	}

	public BigDecimal getUiuCostituite() {
		return this.uiuCostituite;
	}

	public void setUiuCostituite(BigDecimal uiuCostituite) {
		this.uiuCostituite = uiuCostituite;
	}

	public BigDecimal getUiuSoppresse() {
		return this.uiuSoppresse;
	}

	public void setUiuSoppresse(BigDecimal uiuSoppresse) {
		this.uiuSoppresse = uiuSoppresse;
	}

	public BigDecimal getUiuVariate() {
		return this.uiuVariate;
	}

	public void setUiuVariate(BigDecimal uiuVariate) {
		this.uiuVariate = uiuVariate;
	}

	public String getViaCatasto() {
		return this.viaCatasto;
	}

	public void setViaCatasto(String viaCatasto) {
		this.viaCatasto = viaCatasto;
	}

	public String getViaDocfa() {
		return this.viaDocfa;
	}

	public void setViaDocfa(String viaDocfa) {
		this.viaDocfa = viaDocfa;
	}

	public void setDataVariazione(Date dataVariazione) {
		this.dataVariazione = dataVariazione;
	}

	public Date getDataVariazione() {
		return dataVariazione;
	}

	public BigDecimal getSupC340Abc() {
		return supC340Abc;
	}

	public void setSupC340Abc(BigDecimal supC340Abc) {
		this.supC340Abc = supC340Abc;
	}

	public BigDecimal getSupDipendenzeEscl() {
		return supDipendenzeEscl;
	}

	public void setSupDipendenzeEscl(BigDecimal supDipendenzeEscl) {
		this.supDipendenzeEscl = supDipendenzeEscl;
	}

	
}