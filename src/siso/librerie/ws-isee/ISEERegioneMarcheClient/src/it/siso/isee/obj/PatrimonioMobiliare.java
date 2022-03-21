package it.siso.isee.obj;

import java.io.Serializable;

/***
 * (Quadro FC2 – Modulo FC.1)
L’Elemento
 * @author franc
 *
 */
public class PatrimonioMobiliare implements Serializable {
    
	//Flag che indica se nell’anno precedente la DSU è stato posseduto almeno un rapporto finanziario.
	private boolean flagPossessoRapportoFinanziario;
	/***
	 *  Flag che indica che nell’anno precedente la DSU, l’incremento di altre componenti il patrimonio mobiliare e immobiliare è stato superiore alla differenza della consistenza media e il saldo al 31 dicembre. Tipo Dati: booleano
		FlagIncremento =”0” (NON c’è Incremento)
		FlagIncremento =”1” (C’è Incremento)
	 */
	private boolean flagIncremento;
	private Rapporto rapporto;
	
	
}
