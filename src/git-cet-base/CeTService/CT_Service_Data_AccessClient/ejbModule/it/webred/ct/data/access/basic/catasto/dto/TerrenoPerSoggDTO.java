package it.webred.ct.data.access.basic.catasto.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TerrenoPerSoggDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal pkCuaa;
	private long foglio;
	private String particella;
	private String subalterno;
	private String partita;
	private BigDecimal superficie; //SITITRKC.AREA_PART
	private BigDecimal qualita; //SITITRKC.QUAL_CAT
	private String descQualita;
	private String classe; 	//SITITRKC.CLASSE_TERRENO
	private BigDecimal redditoDominicale;
	private BigDecimal redditoAgrario;	
	private String sezione;
	private BigDecimal rendita;
	private BigDecimal percPoss;
	private String tipoTitolo;
	private String desTipoTitolo;
	
	private Date dataIniVal;
	private Date dataFinVal;
	private Date dtIniPos;
	private Date dtFinPos;
	private String titolo;
	
	private String regime;
	private String descRegime;
	private String soggCollegato;
	private String titNoCod;
	
	public BigDecimal getPkCuaa() {
		return pkCuaa;
	}
	public long getFoglio() {
		return foglio;
	}
	public String getParticella() {
		return particella;
	}
	public String getSubalterno() {
		return subalterno;
	}
	public String getPartita() {
		return partita;
	}
	public BigDecimal getSuperficie() {
		return superficie;
	}
	public BigDecimal getQualita() {
		return qualita;
	}
	public String getClasse() {
		return classe;
	}
	public BigDecimal getRedditoDominicale() {
		return redditoDominicale;
	}
	public BigDecimal getRedditoAgrario() {
		return redditoAgrario;
	}
	public String getSezione() {
		return sezione;
	}
	public BigDecimal getRendita() {
		return rendita;
	}
	public BigDecimal getPercPoss() {
		return percPoss;
	}
	public String getTipoTitolo() {
		return tipoTitolo;
	}
	public String getDesTipoTitolo() {
		return desTipoTitolo;
	}
	public Date getDataIniVal() {
		return dataIniVal;
	}
	public Date getDataFinVal() {
		return dataFinVal;
	}
	public Date getDtIniPos() {
		return dtIniPos;
	}
	public Date getDtFinPos() {
		return dtFinPos;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setPkCuaa(BigDecimal pkCuaa) {
		this.pkCuaa = pkCuaa;
	}
	public void setFoglio(long foglio) {
		this.foglio = foglio;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public void setPartita(String partita) {
		this.partita = partita;
	}
	public void setSuperficie(BigDecimal superficie) {
		this.superficie = superficie;
	}
	public void setQualita(BigDecimal qualita) {
		this.qualita = qualita;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public void setRedditoDominicale(BigDecimal redditoDominicale) {
		this.redditoDominicale = redditoDominicale;
	}
	public void setRedditoAgrario(BigDecimal redditoAgrario) {
		this.redditoAgrario = redditoAgrario;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public void setRendita(BigDecimal rendita) {
		this.rendita = rendita;
	}
	public void setPercPoss(BigDecimal percPoss) {
		this.percPoss = percPoss;
	}
	public void setTipoTitolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}
	public void setDesTipoTitolo(String desTipoTitolo) {
		this.desTipoTitolo = desTipoTitolo;
	}
	public void setDataIniVal(Date dataIniVal) {
		this.dataIniVal = dataIniVal;
	}
	public void setDataFinVal(Date dataFinVal) {
		this.dataFinVal = dataFinVal;
	}
	public void setDtIniPos(Date dtIniPos) {
		this.dtIniPos = dtIniPos;
	}
	public void setDtFinPos(Date dtFinPos) {
		this.dtFinPos = dtFinPos;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescQualita() {
		return descQualita;
	}
	public void setDescQualita(String descQualita) {
		this.descQualita = descQualita;
	}
	public String getRegime() {
		return regime;
	}
	public void setRegime(String regime) {
		this.regime = regime;
	}
	public String getDescRegime() {
		return descRegime;
	}
	public void setDescRegime(String descRegime) {
		this.descRegime = descRegime;
	}
	public String getSoggCollegato() {
		return soggCollegato;
	}
	public void setSoggCollegato(String soggCollegato) {
		this.soggCollegato = soggCollegato;
	}
	public String getDtFinPosStr() {
		return dtFinPos == null ? null : new SimpleDateFormat("dd/MM/yyyy").format(dtFinPos);
	}
	public String getDataFinValStr() {
		return dataFinVal == null ? null : new SimpleDateFormat("dd/MM/yyyy").format(dataFinVal);
	}
	public String getPercPossStr() {
		//verificare se deve essere formattato (cfr. con ImmobiliAccatastatiOutDTO)
		return percPoss == null ? null : percPoss.toString().replace(".", ",");
	}
	public String getTitNoCod() {
		return titNoCod;
	}
	public void setTitNoCod(String titNoCod) {
		this.titNoCod = titNoCod;
	}
	

}
