package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Locazione implements Serializable {
    
	
	//Attributo obbligatorio, data indicata negli estremi di registrazione del contratto
	private String data;
	//Attributo obbligatorio, serie indicata negli estremi di registrazione del contratto
	private String serie;
	
	//Attributo obbligatorio, numero indicato negli estremi di registrazione del contratto
	private String numero;
	//Attributo obbligatorio, codice ufficio indicato negli estremi di registrazione del contratto
	private String codUfficio;
	//Attributo obbligatorio, canone indicato negli estremi di registrazione del contratto
	private Integer canone;
 
	/***
	 * L’elemento Intestatario contiene l’attributo:
		Attributo CodiceFiscale
		Attributo obbligatorio. Codice Fiscale dell’Intestatario.
	 */
	private List<String> intestatario;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCodUfficio() {
		return codUfficio;
	}

	public void setCodUfficio(String codUfficio) {
		this.codUfficio = codUfficio;
	}

	public Integer getCanone() {
		return canone;
	}

	public void setCanone(Integer canone) {
		this.canone = canone;
	}

	public List<String> getIntestatario() {
		return intestatario;
	}

	public void setIntestatario(List<String> intestatario) {
		this.intestatario = intestatario;
	}
	
	
	public void addIntestario(String cf) {
		if(this.intestatario == null)
			intestatario = new ArrayList<String>();
		intestatario.add(cf);
	}
	
	
}
