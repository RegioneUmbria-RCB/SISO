package it.siso.isee.obj;

import java.io.Serializable;

/***
 * (Quadro FC1)
 * @author Francesco Pellegrini
 *
 */
public class DatiComponente implements Serializable {

	//(Quadro FC1– Modulo FC.1)
  	private Residenza residenza;
	//(Quadro FC1– Modulo FC.1)
	private Anagrafica anagrafica;
	private boolean flagConvivenzaAnagrafica;
	
	/***
	 * Attributo obbligatorio che può assumere uno dei seguenti valori:
		LAVIND – Lavoratore dipendente a tempo indeterminato
		LAVDET – Lavoratore dipendente a tempo determinato o con contratto di apprendistato
		LAVINT – Lavoratore con contratto di somministrazione (“interinale” )
		LAVCIG – Lavoratore o disoccupato con sostegno al reddito (cassa integrazione ordinaria, straordinaria o in deroga, contratti di solidarietà; lavori socialmente utili; mobilità, ASPI, etc.)
		LAVPAR – Lavoratore parasubordinato (coll. a progetto o Co.Co.Co)
		LAVOCC – Lavoro accessorio (voucher), occasionale, tirocini/stages, etc.
		LAVAUT – Lavoratore autonomo, libero professionista, imprenditore
		DISOCC – Non occupato
		PENSIO – Pensionato
		CASLNG – Casalinga
		STUDNT – Studente
		ALTRO – Altro
	 */
	private String attivitaSoggetto;
	
	public Residenza getResidenza() {
		return residenza;
	}
	public void setResidenza(Residenza residenza) {
		this.residenza = residenza;
	}
	public Anagrafica getAnagrafica() {
		return anagrafica;
	}
	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}
	public boolean isFlagConvivenzaAnagrafica() {
		return flagConvivenzaAnagrafica;
	}
	public void setFlagConvivenzaAnagrafica(boolean flagConvivenzaAnagrafica) {
		this.flagConvivenzaAnagrafica = flagConvivenzaAnagrafica;
	}
	public String getAttivitaSoggetto() {
		return attivitaSoggetto;
	}
	public void setAttivitaSoggetto(String attivitaSoggetto) {
		this.attivitaSoggetto = attivitaSoggetto;
	}
	
	
}
