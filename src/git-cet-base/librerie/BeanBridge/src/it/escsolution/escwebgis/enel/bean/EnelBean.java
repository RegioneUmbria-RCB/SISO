/*
 * Created on 3-mag-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.escsolution.escwebgis.enel.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EnelBean extends EscObject implements Serializable
{
	private String id;
	private String idExt;

	// tabella sit_enel_utente
	private String codiceFiscale  ;
	private String denominazione	 ;
	private String sesso ;
	private Date dataNascita;
	private String comuneNascitaSede	 ;
	private String provinciaNascitaSede	 ;
	private String comuneDomFiscale	 ;
	private String provinciaDomFiscale	 ;
	private String qualificaTitolare;	
	
	
	public String getChiave() {
		return id;
	}
	
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
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

	public String getComuneNascitaSede() {
		return comuneNascitaSede;
	}

	public void setComuneNascitaSede(String comuneNascitaSede) {
		this.comuneNascitaSede = comuneNascitaSede;
	}

	public String getProvinciaNascitaSede() {
		return provinciaNascitaSede;
	}

	public void setProvinciaNascitaSede(String provinciaNascitaSede) {
		this.provinciaNascitaSede = provinciaNascitaSede;
	}

	public String getComuneDomFiscale() {
		return comuneDomFiscale;
	}

	public void setComuneDomFiscale(String comuneDomFiscale) {
		this.comuneDomFiscale = comuneDomFiscale;
	}

	public String getProvinciaDomFiscale() {
		return provinciaDomFiscale;
	}

	public void setProvinciaDomFiscale(String provinciaDomFiscale) {
		this.provinciaDomFiscale = provinciaDomFiscale;
	}

	public String getQualificaTitolare() {
		return qualificaTitolare;
	}

	public void setQualificaTitolare(String qualificaTitolare) {
		this.qualificaTitolare = qualificaTitolare;
	}

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


	
}
