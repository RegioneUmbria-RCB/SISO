package it.webred.ct.data.access.basic.anagrafe.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.Date;


public class AnagrafeCoordCatDTO extends CeTBaseObject implements Serializable{

	private static final long serialVersionUID = -8184151741689170075L;

	private String id;
	
	private String idExt;

	private String idOrig;
	
	private String cognome;
	private String nome;
	private String sesso;

	private Date dataNascita;
	
	private String comuneNascita;
	private String provNascita;
	private String foglio;
	private String mappale;
	private String subalterno;
	
	private String tipoIndirizzoPers;
	private String indirizzoPers;
	private String civicoPers;
	
	private String indirizzoCat;
	private String civicoCat;
	private String barratoCat;
	
	private Date dtInizioValPers;
	
	private Date dtFineValPers;

	private Date dtInizioDatoPers;
	
	private Date dtFineDatoPers;
	
	private Date dtInizioDatoCat;
	
	private Date dtFineDatoCat;
	
	private Date dtInizioFileCat;
	
	private Date dtFineFileCat;
	



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getIdExt() {
		return idExt;
	}



	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}



	public String getIdOrig() {
		return idOrig;
	}



	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
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



	public String getSesso() {
		return sesso;
	}



	public void setSesso(String sesso) {
		this.sesso = sesso;
	}



	public Date getDataNascita() {
		return dataNascita;
	}



	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}



	public String getComuneNascita() {
		return comuneNascita;
	}



	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}



	public String getProvNascita() {
		return provNascita;
	}



	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}



	public String getFoglio() {
		return foglio;
	}



	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}



	public String getMappale() {
		return mappale;
	}



	public void setMappale(String mappale) {
		this.mappale = mappale;
	}



	public String getSubalterno() {
		return subalterno;
	}



	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}



	public String getTipoIndirizzoPers() {
		return tipoIndirizzoPers;
	}



	public void setTipoIndirizzoPers(String tipoIndirizzoPers) {
		this.tipoIndirizzoPers = tipoIndirizzoPers;
	}



	public String getIndirizzoPers() {
		return indirizzoPers;
	}



	public void setIndirizzoPers(String indirizzoPers) {
		this.indirizzoPers = indirizzoPers;
	}



	public String getCivicoPers() {
		return civicoPers;
	}



	public void setCivicoPers(String civicoPers) {
		this.civicoPers = civicoPers;
	}



	public String getIndirizzoCat() {
		return indirizzoCat;
	}



	public void setIndirizzoCat(String indirizzoCat) {
		this.indirizzoCat = indirizzoCat;
	}



	public String getCivicoCat() {
		return civicoCat;
	}



	public void setCivicoCat(String civicoCat) {
		this.civicoCat = civicoCat;
	}



	public String getBarratoCat() {
		return barratoCat;
	}



	public void setBarratoCat(String barratoCat) {
		this.barratoCat = barratoCat;
	}



	public Date getDtInizioValPers() {
		return dtInizioValPers;
	}



	public void setDtInizioValPers(Date dtInizioValPers) {
		this.dtInizioValPers = dtInizioValPers;
	}



	public Date getDtFineValPers() {
		return dtFineValPers;
	}



	public void setDtFineValPers(Date dtFineValPers) {
		this.dtFineValPers = dtFineValPers;
	}



	public Date getDtInizioDatoPers() {
		return dtInizioDatoPers;
	}



	public void setDtInizioDatoPers(Date dtInizioDatoPers) {
		this.dtInizioDatoPers = dtInizioDatoPers;
	}



	public Date getDtFineDatoPers() {
		return dtFineDatoPers;
	}



	public void setDtFineDatoPers(Date dtFineDatoPers) {
		this.dtFineDatoPers = dtFineDatoPers;
	}



	public Date getDtInizioDatoCat() {
		return dtInizioDatoCat;
	}



	public void setDtInizioDatoCat(Date dtInizioDatoCat) {
		this.dtInizioDatoCat = dtInizioDatoCat;
	}



	public Date getDtFineDatoCat() {
		return dtFineDatoCat;
	}



	public void setDtFineDatoCat(Date dtFineDatoCat) {
		this.dtFineDatoCat = dtFineDatoCat;
	}



	public Date getDtInizioFileCat() {
		return dtInizioFileCat;
	}



	public void setDtInizioFileCat(Date dtInizioFileCat) {
		this.dtInizioFileCat = dtInizioFileCat;
	}



	public Date getDtFineFileCat() {
		return dtFineFileCat;
	}



	public void setDtFineFileCat(Date dtFineFileCat) {
		this.dtFineFileCat = dtFineFileCat;
	}
	


	public String stampaRecord(){
		String r =  cognome+"|"+nome+"|"+dataNascita+"|"+comuneNascita+"|"+provNascita+"|";
		return r;
	}

	
}
