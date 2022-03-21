package it.webred.ct.data.access.basic.catasto.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TerrenoDerivazioneDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal chiave;
    private String codNazionale;
	private String sezione;
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
	private BigDecimal rendita;
	
	private Date dataIniVal;
	private Date dataFinVal;
	
	private String descrFineVal;
	
	private BigDecimal ideMutaIni;
	private BigDecimal ideMutaFine;
	
	public String getCodNazionale() {
		return codNazionale;
	}
	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public long getFoglio() {
		return foglio;
	}
	public void setFoglio(long foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public String getPartita() {
		return partita;
	}
	public void setPartita(String partita) {
		this.partita = partita;
	}
	public BigDecimal getSuperficie() {
		return superficie;
	}
	public void setSuperficie(BigDecimal superficie) {
		this.superficie = superficie;
	}
	public BigDecimal getQualita() {
		return qualita;
	}
	public void setQualita(BigDecimal qualita) {
		this.qualita = qualita;
	}
	public String getDescQualita() {
		return descQualita;
	}
	public void setDescQualita(String descQualita) {
		this.descQualita = descQualita;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public BigDecimal getRedditoDominicale() {
		return redditoDominicale;
	}
	public void setRedditoDominicale(BigDecimal redditoDominicale) {
		this.redditoDominicale = redditoDominicale;
	}
	public BigDecimal getRedditoAgrario() {
		return redditoAgrario;
	}
	public void setRedditoAgrario(BigDecimal redditoAgrario) {
		this.redditoAgrario = redditoAgrario;
	}
	public BigDecimal getRendita() {
		return rendita;
	}
	public void setRendita(BigDecimal rendita) {
		this.rendita = rendita;
	}
	public Date getDataIniVal() {
		return dataIniVal;
	}
	public void setDataIniVal(Date dataIniVal) {
		this.dataIniVal = dataIniVal;
	}
	public Date getDataFinVal() {
		return dataFinVal;
	}
	public BigDecimal getIdeMutaIni() {
		return ideMutaIni;
	}
	public void setIdeMutaIni(BigDecimal ideMutaIni) {
		this.ideMutaIni = ideMutaIni;
	}
	public BigDecimal getIdeMutaFine() {
		return ideMutaFine;
	}
	public void setIdeMutaFine(BigDecimal ideMutaFine) {
		this.ideMutaFine = ideMutaFine;
	}
	public void setDataFinVal(Date dataFinVal) {
		this.dataFinVal = dataFinVal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDescrFineVal() {
		
		SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
		descrFineVal ="";
		
		if (this.dataFinVal == null)
			return descrFineVal = "-";
		else{
			String descData = SDF.format(this.dataFinVal);
			descrFineVal = descData.equals("31/12/9999") ? "ATTUALE" : "CESSATO IL " + descData ;
		}
		
		return descrFineVal;
	}
	public BigDecimal getChiave() {
		return chiave;
	}
	public void setChiave(BigDecimal chiave) {
		this.chiave = chiave;
	}
	
}
