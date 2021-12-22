package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dichiarazione implements Serializable {

	private String codiceFiscale;
	
	private List<ComponenteNucleo> nucleoFamiliare;
	
	private List<ModelloBase> modelloBase;
	
	private Operazione operazione;
	
	//<!-- Ammesso solamente per l'operazione "C" -->
	private List<VariazioneDichiarazione> iseeCorrente;
	
	private List<Sottoscrizione> sottoscrizioni;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	 

	public List<ComponenteNucleo> getNucleoFamiliare() {
		return nucleoFamiliare;
	}

	public void setNucleoFamiliare(List<ComponenteNucleo> nucleoFamiliare) {
		this.nucleoFamiliare = nucleoFamiliare;
	}

	public List<ModelloBase> getModelloBase() {
		return modelloBase;
	}

	public void setModelloBase(List<ModelloBase> modelloBase) {
		this.modelloBase = modelloBase;
	}

	public Operazione getOperazione() {
		return operazione;
	}

	public void setOperazione(Operazione operazione) {
		this.operazione = operazione;
	}

	public List<VariazioneDichiarazione> getIseeCorrente() {
		return iseeCorrente;
	}

	public void setIseeCorrente(List<VariazioneDichiarazione> iseeCorrente) {
		this.iseeCorrente = iseeCorrente;
	}

	
	public void addIseeCorrente(VariazioneDichiarazione variazione) {
		if(this.iseeCorrente == null)
			this.iseeCorrente = new ArrayList<VariazioneDichiarazione>();
		this.iseeCorrente.add(variazione);
	}

	public void addModelloBase(ModelloBase modelloBase) {
		if(this.modelloBase == null)
			this.modelloBase = new ArrayList<ModelloBase>();
		this.modelloBase.add(modelloBase);
	}
	
	public void addSottoscrizione(Sottoscrizione sottoscrizione) {
		if(this.sottoscrizioni == null)
			this.sottoscrizioni = new ArrayList<Sottoscrizione>();
		this.sottoscrizioni.add(sottoscrizione);
	}
	public List<Sottoscrizione> getSottoscrizioni() {
		return sottoscrizioni;
	}

	public void setSottoscrizioni(List<Sottoscrizione> sottoscrizioni) {
		this.sottoscrizioni = sottoscrizioni;
	}
	
	
	
}
