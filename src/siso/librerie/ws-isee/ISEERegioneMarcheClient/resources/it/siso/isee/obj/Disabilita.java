package it.siso.isee.obj;

import java.io.Serializable;

/***
 * (Quadro FC7 – Modulo FC.2)
 * @author Francesco Pellegrini
 *
 */
public class Disabilita implements Serializable {

	/***
	 *	Media
		Grave
		NonAutosuff
	 */
	private String gradoDisabilita;
	private String enteCerit;
	private String docCertif;
	private String dataCertif;
	/***
	 * Flag che indica se persona che beneficia di prestazioni socio-sanitarie in ambiente residenziale a ciclo continuativo.
	 */
	private boolean flagPrestResidenziale;
	private Integer rettaVersata;
	private Integer speseAssistenza;
	
	
}
