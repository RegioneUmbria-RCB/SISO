package it.webred.ct.data.model.docfa;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;
import it.webred.ct.data.model.catasto.SitiuiuPK;
import it.webred.ct.data.model.locazioni.LocazioneAPK;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOCFA_UIU database table.
 * 
 */
@Entity
@Table(name="DOCFA_UIU")
@IndiceEntity(sorgente="9")
@IdClass(DocfaUiuPK.class)
public class DocfaUiu implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@IndiceKey(pos="1")
	@Id
	@Temporal( TemporalType.DATE)
	private Date fornitura;
	
	@IndiceKey(pos="2")
	@Id
	@Column(name="PROTOCOLLO_REG")
	private String protocolloReg;
	
	@IndiceKey(pos="3")
	@Id
	@Column(name="NR_PROG")
	private BigDecimal nrProg;

	@Column(name="CO_DENOMINATORE")
	private BigDecimal coDenominatore;

	@Column(name="CO_FOGLIO")
	private String coFoglio;

	@Column(name="CO_NUMERO")
	private String coNumero;

	@Column(name="CO_SEZIONE")
	private String coSezione;

	@Column(name="CO_SUBALTERNO")
	private String coSubalterno;

	@Column(name="CO_TIPI_OPERAZIONE")
	private String coTipiOperazione;

	private BigDecimal denominatore;

	private String edificio;

	@Column(name="FLAG_UIU_RECUPERATA")
	private BigDecimal flagUiuRecuperata;

	private String foglio;

    @Column(name="ID_PLANIMETRIA")
	private BigDecimal idPlanimetria;

	@Column(name="INDIR_CODICE")
	private String indirCodice;

	@Column(name="INDIR_FLAG")
	private String indirFlag;

	@Column(name="INDIR_NCIV_DUE")
	private String indirNcivDue;

	@Column(name="INDIR_NCIV_TRE")
	private String indirNcivTre;

	@Column(name="INDIR_NCIV_UNO")
	private String indirNcivUno;

	@Column(name="INDIR_NRPIANO_DUE")
	private String indirNrpianoDue;

	@Column(name="INDIR_NRPIANO_TRE")
	private String indirNrpianoTre;

	@Column(name="INDIR_NRPIANO_UNO")
	private String indirNrpianoUno;

	@Column(name="INDIR_TOPONIMO")
	private String indirToponimo;

	private String interno;

	private String lotto;

	private String numero;

	@Column(name="PARTITA_SPECIALE")
	private String partitaSpeciale;

	@Column(name="PROP_CATEGORIA")
	private String propCategoria;

	@Column(name="PROP_CLASSE")
	private String propClasse;

	@Column(name="PROP_CONSISTENZA")
	private String propConsistenza;

	@Column(name="PROP_RENDITA_EURO_CATA")
	private String propRenditaEuroCata;

	@Column(name="PROP_SUPERFICIE_CATA")
	private BigDecimal propSuperficieCata;

	@Column(name="PROP_ZONA_CENSUARIA")
	private String propZonaCensuaria;

	@Column(name="RIF_MOD_1N")
	private BigDecimal rifMod1n;

	private String scala;

	private String sezione;

	private String subalterno;

	@Column(name="TIPO_OPERAZIONE")
	private String tipoOperazione;

	@Transient
	private String descTipoOperazione;
	
    public DocfaUiu() {
    }

	public BigDecimal getCoDenominatore() {
		return this.coDenominatore;
	}

	public void setCoDenominatore(BigDecimal coDenominatore) {
		this.coDenominatore = coDenominatore;
	}

	public String getCoFoglio() {
		return this.coFoglio;
	}

	public void setCoFoglio(String coFoglio) {
		this.coFoglio = coFoglio;
	}

	public String getCoNumero() {
		return this.coNumero;
	}

	public void setCoNumero(String coNumero) {
		this.coNumero = coNumero;
	}

	public String getCoSezione() {
		return this.coSezione;
	}

	public void setCoSezione(String coSezione) {
		this.coSezione = coSezione;
	}

	public String getCoSubalterno() {
		return this.coSubalterno;
	}

	public void setCoSubalterno(String coSubalterno) {
		this.coSubalterno = coSubalterno;
	}

	public String getCoTipiOperazione() {
		return this.coTipiOperazione;
	}

	public void setCoTipiOperazione(String coTipiOperazione) {
		this.coTipiOperazione = coTipiOperazione;
	}

	public BigDecimal getDenominatore() {
		return this.denominatore;
	}

	public void setDenominatore(BigDecimal denominatore) {
		this.denominatore = denominatore;
	}

	public String getEdificio() {
		return this.edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public BigDecimal getFlagUiuRecuperata() {
		return this.flagUiuRecuperata;
	}

	public void setFlagUiuRecuperata(BigDecimal flagUiuRecuperata) {
		this.flagUiuRecuperata = flagUiuRecuperata;
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

	public BigDecimal getIdPlanimetria() {
		return this.idPlanimetria;
	}

	public void setIdPlanimetria(BigDecimal idPlanimetria) {
		this.idPlanimetria = idPlanimetria;
	}

	public String getIndirCodice() {
		return this.indirCodice;
	}

	public void setIndirCodice(String indirCodice) {
		this.indirCodice = indirCodice;
	}

	public String getIndirFlag() {
		return this.indirFlag;
	}

	public void setIndirFlag(String indirFlag) {
		this.indirFlag = indirFlag;
	}

	public String getIndirNcivDue() {
		return this.indirNcivDue;
	}

	public void setIndirNcivDue(String indirNcivDue) {
		this.indirNcivDue = indirNcivDue;
	}

	public String getIndirNcivTre() {
		return this.indirNcivTre;
	}

	public void setIndirNcivTre(String indirNcivTre) {
		this.indirNcivTre = indirNcivTre;
	}

	public String getIndirNcivUno() {
		String s = this.indirNcivUno;
		if (s != null) {
			//s = s.trim();
			while (s.length() > 1 && s.charAt(0) == '0')
				s = s.substring(1);
				
		}
		return s;
	}

	public void setIndirNcivUno(String indirNcivUno) {
		this.indirNcivUno = indirNcivUno;
	}

	public String getIndirNrpianoDue() {
		return this.indirNrpianoDue;
	}

	public void setIndirNrpianoDue(String indirNrpianoDue) {
		this.indirNrpianoDue = indirNrpianoDue;
	}

	public String getIndirNrpianoTre() {
		return this.indirNrpianoTre;
	}

	public void setIndirNrpianoTre(String indirNrpianoTre) {
		this.indirNrpianoTre = indirNrpianoTre;
	}

	public String getIndirNrpianoUno() {
		return this.indirNrpianoUno;
	}

	public void setIndirNrpianoUno(String indirNrpianoUno) {
		this.indirNrpianoUno = indirNrpianoUno;
	}

	public String getIndirToponimo() {
		return this.indirToponimo;
	}

	public void setIndirToponimo(String indirToponimo) {
		this.indirToponimo = indirToponimo;
	}

	public String getInterno() {
		return this.interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getLotto() {
		return this.lotto;
	}

	public void setLotto(String lotto) {
		this.lotto = lotto;
	}

	public BigDecimal getNrProg() {
		return this.nrProg;
	}

	public void setNrProg(BigDecimal nrProg) {
		this.nrProg = nrProg;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPartitaSpeciale() {
		return this.partitaSpeciale;
	}

	public void setPartitaSpeciale(String partitaSpeciale) {
		this.partitaSpeciale = partitaSpeciale;
	}

	public String getPropCategoria() {
		return this.propCategoria;
	}

	public void setPropCategoria(String propCategoria) {
		this.propCategoria = propCategoria;
	}

	public String getPropClasse() {
		return this.propClasse;
	}

	public void setPropClasse(String propClasse) {
		this.propClasse = propClasse;
	}

	public String getPropConsistenza() {
		return this.propConsistenza;
	}

	public void setPropConsistenza(String propConsistenza) {
		this.propConsistenza = propConsistenza;
	}

	public String getPropRenditaEuroCata() {
		return this.propRenditaEuroCata;
	}

	public void setPropRenditaEuroCata(String propRenditaEuroCata) {
		this.propRenditaEuroCata = propRenditaEuroCata;
	}

	public BigDecimal getPropSuperficieCata() {
		return this.propSuperficieCata;
	}

	public void setPropSuperficieCata(BigDecimal propSuperficieCata) {
		this.propSuperficieCata = propSuperficieCata;
	}

	public String getPropZonaCensuaria() {
		return this.propZonaCensuaria;
	}

	public void setPropZonaCensuaria(String propZonaCensuaria) {
		this.propZonaCensuaria = propZonaCensuaria;
	}

	

	public BigDecimal getRifMod1n() {
		return this.rifMod1n;
	}

	public void setRifMod1n(BigDecimal rifMod1n) {
		this.rifMod1n = rifMod1n;
	}

	public String getScala() {
		return this.scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
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

	public String getTipoOperazione() {
		return this.tipoOperazione;
	}

	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}

	public String getDescTipoOperazione() {
		return descTipoOperazione;
	}

	public void setDescTipoOperazione(String descTipoOperazione) {
		this.descTipoOperazione = descTipoOperazione;
	}

	public String getProtocolloReg() {
		return protocolloReg;
	}

	public void setProtocolloReg(String protocolloReg) {
		this.protocolloReg = protocolloReg;
	}

}