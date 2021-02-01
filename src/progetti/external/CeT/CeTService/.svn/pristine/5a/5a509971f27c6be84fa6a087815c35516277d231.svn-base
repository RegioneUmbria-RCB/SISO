package it.webred.ct.data.access.basic.catasto.dto;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

public class RicercaOggettoCatDTO extends CatastoBaseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String codEnte;
	private String idUiu;
	private String idTerreno;
	private String sezione;
	private String foglio;
	private String particella;
	private String unimm;
	private String sub;
	private Date dtVal;
	
	private Date dtInizioRif;
	private Date dtFineRif;
	
	private BigDecimal pkCuaa;
	
	private CatastoSearchCriteria criteria;
	private Integer startm;
	private Integer numberRecord;
	private String via;
	private String codiceVia;
	private BigDecimal pkIdStra;
	private String civico;
	
	private String codCategoria;
	private String codTipoDocumento;
	
	private Boolean altriSoggetti;
	
	private BigDecimal ideMuta;
	
	private Integer limit = 0;
	
	public RicercaOggettoCatDTO() {
		super();
	}
	public RicercaOggettoCatDTO(String codEnte, String foglio, String particella, String unimm, Date dtVal, String idUiu) {
		super();
		this.codEnte=codEnte;
		this.foglio = foglio;
		this.particella = particella;
		this.unimm = unimm;
		this.dtVal  = dtVal;
		this.idUiu = idUiu;
	}
	public RicercaOggettoCatDTO(String codEnte, String foglio, String particella, String unimm, Date dtVal) {
		super();
		this.codEnte=codEnte;
		this.foglio = foglio;
		this.particella = particella;
		this.unimm = unimm;
		this.dtVal  = dtVal;
	}
	public RicercaOggettoCatDTO(String codEnte, String idUiu) {
		super();
		this.codEnte = codEnte;
		this.idUiu = idUiu;
	}
	public RicercaOggettoCatDTO(CatastoSearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}
	public String getCodEnte() {
		if(codEnte == null)
			codEnte = this.getEnteId();
		return codEnte;
	}
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	public String getIdUiu() {
		return idUiu;
	}
	public void setIdUiu(String idUiu) {
		this.idUiu = idUiu;
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
	public String getUnimm() {
		return unimm;
	}
	public void setUnimm(String unimm) {
		this.unimm = unimm;
	}
	public Date getDtVal() {
		return dtVal;
	}
	public void setDtVal(Date dtVal) {
		this.dtVal = dtVal;
	}
	public String getCodCategoria() {
		return codCategoria;
	}
	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}
	public BigDecimal getPkCuaa() {
		return pkCuaa;
	}
	public void setPkCuaa(BigDecimal pkCuaa) {
		this.pkCuaa = pkCuaa;
	}
	public CatastoSearchCriteria getCriteria() {
		return criteria;
	}
	public void setCriteria(CatastoSearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public BigDecimal getPkIdStra() {
		return pkIdStra;
	}
	public void setPkIdStra(BigDecimal pkIdStra) {
		this.pkIdStra = pkIdStra;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public Integer getStartm() {
		return startm;
	}
	public void setStartm(Integer startm) {
		this.startm = startm;
	}
	public Integer getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(Integer numberRecord) {
		this.numberRecord = numberRecord;
	}
	public void setIdTerreno(String idTerreno) {
		this.idTerreno = idTerreno;
	}
	public String getIdTerreno() {
		return idTerreno;
	}
	public void setAltriSoggetti(Boolean altriSoggetti) {
		this.altriSoggetti = altriSoggetti;
	}
	public Boolean getAltriSoggetti() {
		return altriSoggetti;
	}
	public Date getDtInizioRif() {
		return dtInizioRif;
	}
	public Date getDtFineRif() {
		return dtFineRif;
	}
	public void setDtInizioRif(Date dtInizioRif) {
		this.dtInizioRif = dtInizioRif;
	}
	public void setDtFineRif(Date dtFineRif) {
		this.dtFineRif = dtFineRif;
	}
	public BigDecimal getIdeMuta() {
		return ideMuta;
	}
	public void setIdeMuta(BigDecimal ideMuta) {
		this.ideMuta = ideMuta;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getCodTipoDocumento() {
		return codTipoDocumento;
	}
	public void setCodTipoDocumento(String codTipoDocumento) {
		this.codTipoDocumento = codTipoDocumento;
	}
	public String getCodiceVia() {
		return codiceVia;
	}
	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
