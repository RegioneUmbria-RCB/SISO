/*
 * Created on 10-mag-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.escsolution.escwebgis.toponomastica.bean;
import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Strada extends EscObject  implements Serializable {


	private String codStrada;
	private String toponimo;
	private String nomeVia;
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
	private String ordinamento;
	private String descrLocalita;
	private String comune;
	private String numero;
	
	public Strada(){
		
			codStrada = "";
			toponimo = "";
			nomeVia = "";
			ordinamento = "";
			descrLocalita = "";
			comune = "";
			
			}
	public String getChiave() {
		return codStrada;
	}	/**
	 * @return
	 */
	public String getCodStrada() {
		return codStrada;
	}

	/**
	 * @return
	 */
	public String getComune() {
		return comune;
	}

	/**
	 * @return
	 */
	public String getDescrLocalita() {
		return descrLocalita;
	}

	/**
	 * @return
	 */
	public String getNomeVia() {
		return nomeVia;
	}

	/**
	 * @return
	 */
	public String getOrdinamento() {
		return ordinamento;
	}

	/**
	 * @return
	 */
	public String getToponimo() {
		return toponimo;
	}

	/**
	 * @param string
	 */
	public void setCodStrada(String string) {
		codStrada = string;
	}

	/**
	 * @param string
	 */
	public void setComune(String string) {
		comune = string;
	}

	/**
	 * @param string
	 */
	public void setDescrLocalita(String string) {
		descrLocalita = string;
	}

	/**
	 * @param string
	 */
	public void setNomeVia(String string) {
		nomeVia = string;
	}

	/**
	 * @param string
	 */
	public void setOrdinamento(String string) {
		ordinamento = string;
	}

	/**
	 * @param string
	 */
	public void setToponimo(String string) {
		toponimo = string;
	}

	public String getIdFonte() {
		return "4";
	}

	public String getTipoFonte() {
		return "TOPONOMASTICA COMUNALE";
	}

	public String getDiaKey() {
		if (diaKey != null && !diaKey.equals("")) {
			return diaKey;
		}
		diaKey = "";
		if (codStrada != null && !codStrada.equals("")) {
			diaKey += codStrada;
		}
		return diaKey;
	}
	
}
