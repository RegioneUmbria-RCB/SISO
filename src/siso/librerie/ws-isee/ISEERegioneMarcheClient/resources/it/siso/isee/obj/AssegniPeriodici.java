package it.siso.isee.obj;

import java.io.Serializable;

/***
 * (Quadro FC5 – Modulo FC.1)
 * @author franc
 *
 */
public class AssegniPeriodici implements Serializable {
	
	
	//Valore assegni percepiti figli
	private Integer assegniFigli;
	//Valore degli assegni corrisposti al coniuge.
	private Integer assegniConiuge;
	//Valore degli assegni corrisposti per il mantenimento dei figli.
	private Integer assegniGenitore;
	public Integer getAssegniFigli() {
		return assegniFigli;
	}
	public void setAssegniFigli(Integer assegniFigli) {
		this.assegniFigli = assegniFigli;
	}
	public Integer getAssegniConiuge() {
		return assegniConiuge;
	}
	public void setAssegniConiuge(Integer assegniConiuge) {
		this.assegniConiuge = assegniConiuge;
	}
	public Integer getAssegniGenitore() {
		return assegniGenitore;
	}
	public void setAssegniGenitore(Integer assegniGenitore) {
		this.assegniGenitore = assegniGenitore;
	}
	
	
}
