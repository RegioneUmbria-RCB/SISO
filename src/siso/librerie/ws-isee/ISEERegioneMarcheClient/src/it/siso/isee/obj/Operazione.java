package it.siso.isee.obj;

import java.io.Serializable;

public class Operazione implements Serializable {
	/***
	 *  N - Nuova dichiarazione  
		A - Integrazione componente aggiuntiva 
		I - Integrazione redditi 
		S - Integrazione socio-sanitario residenziale 
		C - ISEE Corrente 
		R - Rettifica 
		X - Annullamento 
		Il numero protocollo di riferimento non deve essere valorizzato in caso di nuova dichiarazione, 
		mentre è obbligatorio negli altri casi 
	 */
	private String tipo;
	private String codiceFiscaleRiferimento;
	private String numeroProtocolloRiferimento;
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCodiceFiscaleRiferimento() {
		return codiceFiscaleRiferimento;
	}
	public void setCodiceFiscaleRiferimento(String codiceFiscaleRiferimento) {
		this.codiceFiscaleRiferimento = codiceFiscaleRiferimento;
	}
	public String getNumeroProtocolloRiferimento() {
		return numeroProtocolloRiferimento;
	}
	public void setNumeroProtocolloRiferimento(String numeroProtocolloRiferimento) {
		this.numeroProtocolloRiferimento = numeroProtocolloRiferimento;
	}
	
	
	
}
