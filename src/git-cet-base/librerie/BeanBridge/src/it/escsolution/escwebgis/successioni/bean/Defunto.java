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
public class Defunto extends EscObject implements Serializable{

	private String codFiscale;
	private String cognome;
	private String nome;
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
	private String dataApertura;
	
	private String id;
	
	
	public Defunto(){
		
		codFiscale = "";
		cognome = "";
		nome = "";
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
		id="";
	    }
	
	public String getChiave(){ 
		return ""+ufficio+"|"+anno+"|"+volume+"|"+numero+"|"+sottonumero+"|"+comune+"|"+progressivo;
	}
	

	/**
	 * @return
	 */
	public String getCodFiscale() {
		return codFiscale;
	}

	/**
	 * @return
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @return
	 */
	public String getComuneNascita() {
		return comuneNascita;
	}

	/**
	 * @return
	 */
	public String getDataNascita() {
		return dataNascita;
	}

	/**
	 * @return
	 */
	public String getDescrComuneNascita() {
		return descrComuneNascita;
	}


	/**
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return
	 */
	public String getSesso() {
		return sesso;
	}

	/**
	 * @param string
	 */
	public void setCodFiscale(String string) {
		codFiscale = string;
	}

	/**
	 * @param string
	 */
	public void setCognome(String string) {
		cognome = string;
	}

	/**
	 * @param string
	 */
	public void setComuneNascita(String string) {
		comuneNascita = string;
	}

	/**
	 * @param string
	 */
	public void setDataNascita(String string) {
		dataNascita = string;
	}

	/**
	 * @param string
	 */
	public void setDescrComuneNascita(String string) {
		descrComuneNascita = string;
	}

	/**
	 * @param string
	 */
	public void setNome(String string) {
		nome = string;
	}

	/**
	 * @param string
	 */
	public void setSesso(String string) {
		sesso = string;
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

	public String getDataApertura() {
		return dataApertura;
	}

	public void setDataApertura(String dataApertura) {
		this.dataApertura = dataApertura;
	}
	
}
