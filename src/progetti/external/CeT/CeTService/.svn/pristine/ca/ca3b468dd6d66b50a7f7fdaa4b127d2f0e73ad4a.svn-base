package it.webred.ct.data.access.basic.common.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.Date;

public class RicercaSoggettoDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private String provenienza;
	private String tipoRicerca; //"all" per "select * "; null per "select distinct"; (cfr.XXXQueryBuilder)
	private String tipoSogg; //F oppure G
	//personafisica
	private String codFis;
	private String cognome;
	private String nome;
	private Date dtNas;
	private String codComNas;
	private String desComNas;
	//persona giuridica
	private String parIva;
	private String denom;
	private String operDenom;
	
	private Integer maxResult;
	
	public RicercaSoggettoDTO() {	}
	
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	
	public void setTipoRicerca(String tipoRicerca) {
		this.tipoRicerca = tipoRicerca;
	}
	public String getTipoRicerca() {
		return tipoRicerca;
	}
	public String getTipoSogg() {
		return tipoSogg;
	}
		
	public void setTipoSogg(String tipoSogg) {
		this.tipoSogg = tipoSogg;
	}
	
	public String getCodFis() {
		return codFis;
	}
	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDtNas() {
		return dtNas;
	}
	public void setDtNas(Date dtNas) {
		this.dtNas = dtNas;
	}
	public String getCodComNas() {
		return codComNas;
	}
	public void setCodComNas(String codComNas) {
		this.codComNas = codComNas;
	}
	public String getDesComNas() {
		return desComNas;
	}
	public void setDesComNas(String desComNas) {
		this.desComNas = desComNas;
	}
	public String getParIva() {
		return parIva;
	}
	public void setParIva(String parIva) {
		this.parIva = parIva;
	}
	public String getDenom() {
		return denom;
	}
	public void setDenom(String denom) {
		this.denom = denom;
	}
	public String getOperDenom() {
		return operDenom;
	}
	public void setOperDenom(String operDenom) {
		this.operDenom = operDenom;
	}
		
	public Integer getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}

	public void setUpperCase() {
		codFis = codFis!= null ? codFis.toUpperCase(): codFis;
		nome = nome!= null ? nome.toUpperCase(): nome;
		cognome = cognome != null?  cognome.toUpperCase(): cognome;
		denom = denom != null?  denom.toUpperCase(): denom;
		tipoSogg = tipoSogg != null ?  tipoSogg.toUpperCase(): tipoSogg;
		provenienza  = provenienza != null ?  provenienza.toUpperCase(): provenienza;
	}
	
	public void forzaRicercaPerCFPI () {
		nome = null;
		cognome = null;
		denom = null;
		dtNas=null;
		codComNas=null;
		desComNas=null;
	}
	public void forzaRicercaPerDatiAna () {
		codFis = null;
		parIva=null;
	}
	public void forzaRicercaPerPF () {
		codFis = null;
		parIva=null;
	}
	
	public void forzaRicercaPerPG () {
		nome = null;
		cognome = null;
		codFis = null;
		dtNas=null;
		codComNas=null;
		desComNas=null;
	}
	
	public void val(RicercaSoggettoDTO sogg ) {
		setEnteId(sogg.getEnteId());
		setUserId(sogg.getUserId());
		codFis= sogg.getCodFis();
		nome=sogg.getNome();
		cognome= sogg.getCognome();
		dtNas= sogg.getDtNas();
		codComNas=sogg.getCodComNas();
		desComNas = sogg.getDesComNas();
		denom= sogg.getDenom();
		parIva=sogg.getParIva();
		provenienza=sogg.getProvenienza();
		tipoSogg= sogg.getTipoSogg(); 
		
	}
	
	
}
