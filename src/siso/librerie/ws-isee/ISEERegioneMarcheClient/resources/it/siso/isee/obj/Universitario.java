package it.siso.isee.obj;

import java.io.Serializable;
/***
 * (Quadro C – Modulo MB.2)
 * @author franc
 *
 */
public class Universitario implements Serializable {

	//OBBLIGATORIO
	private String codiceFiscaleBeneficiario;
	
	/***
	 * OBBLIGATORIO
	 * Può avere i seguenti valori:
TuttiPresenti
SoloUno
ConiugatiNonPresenti
NonConiugatiNonPresenti
	 */
	private String presenzaGenitori;
	
	/***
	 * Flag che indica se lo studente è fuori dalla unità abitativa di famiglia da almeno due anni (prima casella del riquadro Autonomia dello Studente)
	 */
	private boolean flagResidenzaStudente;
	
	/***
	 * Flag che indica se lo studente possiede adeguata capacità di reddito (seconda casella del riquadro Autonomia dello Studente)
	 */
	private boolean flagCapacitaRedditoStudente;
	
	/***
	 * Codice Fiscale di uno dei genitori.
		Formato: come gli altri codici fiscali. Vedi sopra
		Da inserire solo se lo studente non è autonomo
	 */
	private String codiceFiscaleGenitore;
	
	/***
	 * 	Numero del protocollo della dichiarazione del genitore da agganciare.
		Questo attributo ha una struttura definita in un type esterno TypeProtocollo come definito in precedenza
	 */
	private String numeroProtocolloRifGenitore;

	public String getCodiceFiscaleBeneficiario() {
		return codiceFiscaleBeneficiario;
	}

	public void setCodiceFiscaleBeneficiario(String codiceFiscaleBeneficiario) {
		this.codiceFiscaleBeneficiario = codiceFiscaleBeneficiario;
	}

	public String getPresenzaGenitori() {
		return presenzaGenitori;
	}

	public void setPresenzaGenitori(String presenzaGenitori) {
		this.presenzaGenitori = presenzaGenitori;
	}

	public boolean isFlagResidenzaStudente() {
		return flagResidenzaStudente;
	}

	public void setFlagResidenzaStudente(boolean flagResidenzaStudente) {
		this.flagResidenzaStudente = flagResidenzaStudente;
	}

	public boolean isFlagCapacitaRedditoStudente() {
		return flagCapacitaRedditoStudente;
	}

	public void setFlagCapacitaRedditoStudente(boolean flagCapacitaRedditoStudente) {
		this.flagCapacitaRedditoStudente = flagCapacitaRedditoStudente;
	}

	public String getCodiceFiscaleGenitore() {
		return codiceFiscaleGenitore;
	}

	public void setCodiceFiscaleGenitore(String codiceFiscaleGenitore) {
		this.codiceFiscaleGenitore = codiceFiscaleGenitore;
	}

	public String getNumeroProtocolloRifGenitore() {
		return numeroProtocolloRifGenitore;
	}

	public void setNumeroProtocolloRifGenitore(String numeroProtocolloRifGenitore) {
		this.numeroProtocolloRifGenitore = numeroProtocolloRifGenitore;
	}
	
	
	
}
