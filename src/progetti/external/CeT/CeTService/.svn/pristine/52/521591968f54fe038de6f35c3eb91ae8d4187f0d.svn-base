package it.webred.ct.data.access.basic.catasto.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RicercaSoggettoCatDTO extends CatastoBaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codEnte;
	
	private Date dtVal;
	
	private Date dtRifDa;
	private Date dtRifA;
		
	private BigDecimal pkid;//--> corrisponde a ANAG_SOGGETTI.pkid (= CONS_SOGG_TAB.pkid)
	private BigDecimal idSogg;//--> corrisponde a ANAG_SOGGETTI.codSoggetto (= CONS_SOGG_TAB.PK_CUAA)
	
	private String codFis;
	private String cognome;
	private String nome;
	private Date dtNas;
	
	private String denom;
	private String partIva;
	
	private boolean ricercaRegime = false;
	private boolean ricercaSoggCollegato = false;
	
	private Integer limit = 0;
	
	public RicercaSoggettoCatDTO() {
		super();
	}
	public RicercaSoggettoCatDTO(String codEnte ,Date dtVal) {
		super();
		this.codEnte=codEnte;
		this.dtVal  = dtVal;
	}
	
	public RicercaSoggettoCatDTO(String codEnte ,String codFis,Date dtVal) {
		super();
		this.codEnte=codEnte;
		this.codFis = codFis;
		this.dtVal  = dtVal;
	}
	
	public String getCodFis() {
		return codFis;
	}
	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}
	public Date getDtVal() {
		return dtVal;
	}
	public void setDtVal(Date dtVal) {
		this.dtVal = dtVal;
	}
	public String getCodEnte() {
		return codEnte;
	}
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	public BigDecimal getIdSogg() {
		return idSogg;
	}
	public void setIdSogg(BigDecimal idSogg) {
		this.idSogg = idSogg;
	}
	public Date getDtRifDa() {
		return dtRifDa;
	}
	public void setDtRifDa(Date dtRifDa) {
		this.dtRifDa = dtRifDa;
	}
	public Date getDtRifA() {
		return dtRifA;
	}
	public void setDtRifA(Date dtRifA) {
		this.dtRifA = dtRifA;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getDenom() {
		return denom;
	}
	public void setDenom(String denom) {
		this.denom = denom;
	}
	public String getPartIva() {
		return partIva;
	}
	public void setPartIva(String partIva) {
		this.partIva = partIva;
	}
	public Date getDtNas() {
		return dtNas;
	}
	public void setDtNas(Date dtNas) {
		this.dtNas = dtNas;
	}
	public BigDecimal getPkid() {
		return pkid;
	}
	public void setPkid(BigDecimal pkid) {
		this.pkid = pkid;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getsDtRifDa(String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return dtRifDa!=null ? sdf.format(dtRifDa) : null;
	}
	public String getsDtRifA(String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return dtRifA!=null ? sdf.format(dtRifA) : null;
	}
	public String getsDtNas(String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return dtNas!=null ? sdf.format(dtNas) : null;
	}
	public boolean isRicercaRegime() {
		return ricercaRegime;
	}
	public void setRicercaRegime(boolean ricercaRegime) {
		this.ricercaRegime = ricercaRegime;
	}
	public boolean isRicercaSoggCollegato() {
		return ricercaSoggCollegato;
	}
	public void setRicercaSoggCollegato(boolean ricercaSoggCollegato) {
		this.ricercaSoggCollegato = ricercaSoggCollegato;
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
