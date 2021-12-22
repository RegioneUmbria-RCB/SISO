package it.webred.cs.csa.ejb.dto.configurazione;

import it.webred.cs.data.model.CsOOperatoreSettore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CsOOperatoreSettoreEstesa extends CsOOperatoreSettore implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String lblOrganizzazione;
	private String lblSettore;
	private String lblTipoOperatore;
	private List<String> listaRuoli;
	
	private boolean selezionato;

	public String getLblOrganizzazione() {
		return lblOrganizzazione;
	}

	public void setLblOrganizzazione(String lblOrganizzazione) {
		this.lblOrganizzazione = lblOrganizzazione;
	}

	public String getLblSettore() {
		return lblSettore;
	}

	public void setLblSettore(String lblSettore) {
		this.lblSettore = lblSettore;
	}

	public boolean isSelezionato() {
		return selezionato;
	}

	public String getLblTipoOperatore() {
		return lblTipoOperatore;
	}

	public void setLblTipoOperatore(String lblTipoOperatore) {
		this.lblTipoOperatore = lblTipoOperatore;
	}

	public void setSelezionato(boolean selezionato) {
		this.selezionato = selezionato;
	}
	
	public List<String> getListaRuoli() {
		return listaRuoli;
	}

	public void setListaRuoli(List<String> listaRuoli) {
		this.listaRuoli = listaRuoli;
	}

	public String getLblRuoli() {
		String lblRuoli = null;
		if(this.listaRuoli==null || this.listaRuoli.isEmpty())
			lblRuoli = "-";
		else{
			lblRuoli = "";
			for(String r : this.listaRuoli){
				lblRuoli += r;
				if (!lblRuoli.equals("")) lblRuoli += "&#10;";
			}
		}
		return lblRuoli;
	}
	
	public String getLblAppartiene() {
		return getAppartiene() == 1 ? "SI" : "NO";
	}
	
	public boolean isAttivo() {
		Date now = new Date();
		boolean okInizio = getDataInizioApp() != null &&
		(getDataInizioApp().getTime() <= now.getTime());
		boolean okFine = getDataFineApp() == null ||
		(getDataFineApp().getTime() > now.getTime());
		return okInizio && okFine ? true : false;
	}
	

	
}
