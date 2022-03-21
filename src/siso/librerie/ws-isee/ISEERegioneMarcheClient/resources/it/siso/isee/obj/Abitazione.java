package it.siso.isee.obj;

import java.io.Serializable;
/***
 * 
 * (Quadro B – Modulo MB.1 o Modulo MB.1rid)
 * @author Francesco Pellegrini
 *
 */
public class Abitazione implements Serializable {

	/***
	 * PROP – Di Proprietà
		LOC – In Locazione
		ALTRO
	 */
	private String tipoAbitazione;
	/***
	 * Attributo da valorizzare se i coniugi hanno diversa residenza, quindi occorre indicare quale delle due residenze indicare:
		D – Residenza del Dichairante
		C – Residenza del Coniuge
	 */
	
	private String residenzaRiferimento;
	
	private Residenza indirizzoAbitazione;
	
	private Locazione locazione;

	public String getTipoAbitazione() {
		return tipoAbitazione;
	}

	public void setTipoAbitazione(String tipoAbitazione) {
		this.tipoAbitazione = tipoAbitazione;
	}

	public String getResidenzaRiferimento() {
		return residenzaRiferimento;
	}

	public void setResidenzaRiferimento(String residenzaRiferimento) {
		this.residenzaRiferimento = residenzaRiferimento;
	}

	public Residenza getIndirizzoAbitazione() {
		return indirizzoAbitazione;
	}

	public void setIndirizzoAbitazione(Residenza indirizzoAbitazione) {
		this.indirizzoAbitazione = indirizzoAbitazione;
	}

	public Locazione getLocazione() {
		return locazione;
	}

	public void setLocazione(Locazione locazione) {
		this.locazione = locazione;
	}
	
	
}
