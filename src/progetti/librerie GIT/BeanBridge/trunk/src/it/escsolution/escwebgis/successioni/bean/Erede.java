/*
 * Created on 3-mag-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.escsolution.escwebgis.successioni.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Erede extends EscObject implements Serializable{

	private String codFiscale;
	private String denominazione;
	private String dataNascita;
	private String sesso;
	private String comuneNascita;
	private String descrComuneNascita;
	private String provNascita;
	private String descrProvNascita;
	private String ufficio;
	private String anno;
	private String volume;
	private String numero;
	private String sottonumero;
	private String comune;
	private String progressivo;
	private String progressivoErede;
	
	private String id;
	
	
	public Erede(){
		
		codFiscale = "";
		denominazione = "";
		dataNascita = "";
		sesso = "";
		comuneNascita = "";
		descrComuneNascita = "";
		provNascita = "";
		descrProvNascita = "";
		ufficio = "";
		anno = "";
		volume = "";
		numero = "";
		sottonumero = "";
		comune = "";
		progressivo = "";
		progressivoErede = "";
		id="";
	    }
	
	public String getChiave(){ 
		return ""+ufficio+"|"+anno+"|"+volume+"|"+numero+"|"+sottonumero+"|"+comune+"|"+progressivo+"|"+progressivoErede;
	}

	/**
	 * @return Returns the anno.
	 */
	public String getAnno() {
		return anno;
	}
	/**
	 * @param anno The anno to set.
	 */
	public void setAnno(String anno) {
		this.anno = anno;
	}
	/**
	 * @return Returns the codFiscale.
	 */
	public String getCodFiscale() {
		return codFiscale;
	}
	/**
	 * @param codFiscale The codFiscale to set.
	 */
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	/**
	 * @return Returns the comune.
	 */
	public String getComune() {
		return comune;
	}
	/**
	 * @param comune The comune to set.
	 */
	public void setComune(String comune) {
		this.comune = comune;
	}
	/**
	 * @return Returns the comuneNascita.
	 */
	public String getComuneNascita() {
		return comuneNascita;
	}
	/**
	 * @param comuneNascita The comuneNascita to set.
	 */
	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}
	/**
	 * @return Returns the dataNascita.
	 */
	public String getDataNascita() {
		return dataNascita;
	}
	/**
	 * @param dataNascita The dataNascita to set.
	 */
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	/**
	 * @return Returns the descrComuneNascita.
	 */
	public String getDescrComuneNascita() {
		return descrComuneNascita;
	}
	/**
	 * @param descrComuneNascita The descrComuneNascita to set.
	 */
	public void setDescrComuneNascita(String descrComuneNascita) {
		this.descrComuneNascita = descrComuneNascita;
	}
	/**
	 * @return Returns the descrProvNascita.
	 */
	public String getDescrProvNascita() {
		return descrProvNascita;
	}
	/**
	 * @param descrProvNascita The descrProvNascita to set.
	 */
	public void setDescrProvNascita(String descrProvNascita) {
		this.descrProvNascita = descrProvNascita;
	}
	/**
	 * @return Returns the denominazione.
	 */
	public String getDenominazione() {
		return denominazione;
	}
	/**
	 * @param denominazione The denominazione to set.
	 */
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	/**
	 * @return Returns the numero.
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * @param numero The numero to set.
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	/**
	 * @return Returns the progressivo.
	 */
	public String getProgressivo() {
		return progressivo;
	}
	/**
	 * @param progressivo The progressivo to set.
	 */
	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}
	/**
	 * @return Returns the progressivoErede.
	 */
	public String getProgressivoErede() {
		return progressivoErede;
	}
	/**
	 * @param progressivoErede The progressivoErede to set.
	 */
	public void setProgressivoErede(String progressivoErede) {
		this.progressivoErede = progressivoErede;
	}
	/**
	 * @return Returns the provNascita.
	 */
	public String getProvNascita() {
		return provNascita;
	}
	/**
	 * @param provNascita The provNascita to set.
	 */
	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}
	/**
	 * @return Returns the sesso.
	 */
	public String getSesso() {
		return sesso;
	}
	/**
	 * @param sesso The sesso to set.
	 */
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	/**
	 * @return Returns the sottonumero.
	 */
	public String getSottonumero() {
		return sottonumero;
	}
	/**
	 * @param sottonumero The sottonumero to set.
	 */
	public void setSottonumero(String sottonumero) {
		this.sottonumero = sottonumero;
	}
	/**
	 * @return Returns the ufficio.
	 */
	public String getUfficio() {
		return ufficio;
	}
	/**
	 * @param ufficio The ufficio to set.
	 */
	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
	/**
	 * @return Returns the volume.
	 */
	public String getVolume() {
		return volume;
	}
	/**
	 * @param volume The volume to set.
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
