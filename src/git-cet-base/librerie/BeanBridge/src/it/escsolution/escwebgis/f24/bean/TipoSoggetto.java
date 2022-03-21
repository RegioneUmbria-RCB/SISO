package it.escsolution.escwebgis.f24.bean;

import java.io.Serializable;

public class TipoSoggetto implements Serializable {
	private String codTipoSoggetto;
	private String desTipoSoggetto;
	
	/**
	 * 
	 */
	
	public String getCode(){
		return codTipoSoggetto;
	}
	public String getDescrizione(){
		return desTipoSoggetto;
	}
	public TipoSoggetto() {
	}
	public TipoSoggetto(String cod,String  des) {
		codTipoSoggetto = cod;
		desTipoSoggetto = des;
	}
	public String toString() {
		return desTipoSoggetto;
	}
	/**
	 * @return
	 */
	public String getCodTipoSoggetto() {
		return codTipoSoggetto;
	}

	/**
	 * @return
	 */
	public String getDesTipoSoggetto() {
		return desTipoSoggetto;
	}

	/**
	 * @param string
	 */
	public void setCodTipoSoggetto(String string) {
		codTipoSoggetto = string;
	}

	/**
	 * @param string
	 */
	public void setDesTipoSoggetto(String string) {
		desTipoSoggetto = string;
	}

}
