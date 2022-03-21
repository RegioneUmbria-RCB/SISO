package it.webred.ct.data.access.aggregator.isee.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;
public class IseeCespiteDTO extends CeTBaseObject implements Serializable {
		
	private String idOggettoIci;
	private String tipoPatrimonio;
	private String comune;
	private String provincia;
	private BigDecimal quotaPosseduta;
	private boolean quotaPossedutaIci;
	private boolean quotaPossedutaCatasto;
	private BigDecimal renditaCatastale;
	private BigDecimal valoreICI;
	private BigDecimal quotaMutuoResidua;
	private boolean abitazione;
	private String foglio;
	private String particella;
	private String sub;
	private String indirizzo;
	private String civico;
	private String annoDenuncia;
	private String numeroDenuncia;
	private String flagAbiPri3112;
	
	public IseeCespiteDTO() {
		super();
	}

	public String getTipoPatrimonio() {
		return tipoPatrimonio;
	}

	public void setTipoPatrimonio(String tipoPatrimonio) {
		this.tipoPatrimonio = tipoPatrimonio;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public BigDecimal getQuotaPosseduta() {
		return quotaPosseduta;
	}

	public void setQuotaPosseduta(BigDecimal quotaPosseduta) {
		this.quotaPosseduta = quotaPosseduta;
	}

	public BigDecimal getValoreICI() {
		return valoreICI;
	}

	public void setValoreICI(BigDecimal valoreICI) {
		this.valoreICI = valoreICI;
	}

	public BigDecimal getQuotaMutuoResidua() {
		return quotaMutuoResidua;
	}

	public void setQuotaMutuoResidua(BigDecimal quotaMutuoResidua) {
		this.quotaMutuoResidua = quotaMutuoResidua;
	}

	public boolean isAbitazione() {
		return abitazione;
	}

	public void setAbitazione(boolean abitazione) {
		this.abitazione = abitazione;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getIdOggettoIci() {
		return idOggettoIci;
	}

	public void setIdOggettoIci(String idOggettoIci) {
		this.idOggettoIci = idOggettoIci;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getAnnoDenuncia() {
		return annoDenuncia;
	}

	public void setAnnoDenuncia(String annoDenuncia) {
		this.annoDenuncia = annoDenuncia;
	}

	public String getNumeroDenuncia() {
		return numeroDenuncia;
	}

	public void setNumeroDenuncia(String numeroDenuncia) {
		this.numeroDenuncia = numeroDenuncia;
	}

	public String getFlagAbiPri3112() {
		return flagAbiPri3112;
	}

	public void setFlagAbiPri3112(String flagAbiPri3112) {
		this.flagAbiPri3112 = flagAbiPri3112;
	}

	public boolean isQuotaPossedutaIci() {
		return quotaPossedutaIci;
	}

	public void setQuotaPossedutaIci(boolean quotaPossedutaIci) {
		this.quotaPossedutaIci = quotaPossedutaIci;
	}

	public boolean isQuotaPossedutaCatasto() {
		return quotaPossedutaCatasto;
	}

	public void setQuotaPossedutaCatasto(boolean quotaPossedutaCatasto) {
		this.quotaPossedutaCatasto = quotaPossedutaCatasto;
	}

	public BigDecimal getRenditaCatastale() {
		return renditaCatastale;
	}

	public void setRenditaCatastale(BigDecimal renditaCatastale) {
		this.renditaCatastale = renditaCatastale;
	}

}
