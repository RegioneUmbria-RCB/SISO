package it.siso.isee.obj;

import java.io.Serializable;

public class DettaglioGenitoreNonConvivente implements Serializable{

	//OBBLIGATORIO
	private String codiceFiscale;
	
	/**OBBLIGATORIO
	 * Flag che indica se i dati della componente aggiuntiva sono contestuali alla presentazione della DSU o verranno presentati in seguito 
	 */
	private boolean flagCompletezzaDati;
	
	//(Modulo FC.4 - sez. III)
	private Abitazione abitazioneComponenteAggiuntiva;
	
	//(Modulo FC.4 - sez. II)
	private NucleoFamiliareComponenti nucleoFamiliareComponenteAggiuntiva;
	
	private DatiComponente componenteGenitore;
	private ComponenteAggiuntiva componenteAggiuntivaGenitore;
	
	
	

	public DatiComponente getComponenteGenitore() {
		return componenteGenitore;
	}

	public void setComponenteGenitore(DatiComponente componenteGenitore) {
		this.componenteGenitore = componenteGenitore;
	}

	public ComponenteAggiuntiva getComponenteAggiuntivaGenitore() {
		return componenteAggiuntivaGenitore;
	}

	public void setComponenteAggiuntivaGenitore(ComponenteAggiuntiva componenteAggiuntivaGenitore) {
		this.componenteAggiuntivaGenitore = componenteAggiuntivaGenitore;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public boolean isFlagCompletezzaDati() {
		return flagCompletezzaDati;
	}

	public void setFlagCompletezzaDati(boolean flagCompletezzaDati) {
		this.flagCompletezzaDati = flagCompletezzaDati;
	}

	public Abitazione getAbitazioneComponenteAggiuntiva() {
		return abitazioneComponenteAggiuntiva;
	}

	public void setAbitazioneComponenteAggiuntiva(Abitazione abitazioneComponenteAggiuntiva) {
		this.abitazioneComponenteAggiuntiva = abitazioneComponenteAggiuntiva;
	}

	public NucleoFamiliareComponenti getNucleoFamiliareComponenteAggiuntiva() {
		return nucleoFamiliareComponenteAggiuntiva;
	}

	public void setNucleoFamiliareComponenteAggiuntiva(NucleoFamiliareComponenti nucleoFamiliareComponenteAggiuntiva) {
		this.nucleoFamiliareComponenteAggiuntiva = nucleoFamiliareComponenteAggiuntiva;
	}
	
	
}
