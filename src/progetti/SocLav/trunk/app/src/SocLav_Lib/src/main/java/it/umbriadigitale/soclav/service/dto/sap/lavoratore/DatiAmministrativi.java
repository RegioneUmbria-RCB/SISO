package it.umbriadigitale.soclav.service.dto.sap.lavoratore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAmministrativi.AssolvimentoIstruzione;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAmministrativi.ListaSpeciale;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAmministrativi.ListeSpeciali;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAmministrativi.PeriodoDisoccupazione;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAmministrativi.StatoAnagrafe;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class DatiAmministrativi implements Serializable {

	private StatoAnagrafe statoinanagrafe;
	private ListeSpeciali listespeciali_lst;
	private PeriodoDisoccupazione periodidisoccupazione;
	private AssolvimentoIstruzione assolvimentoistruzione;
	private AltreNotizie altreinformazioni;
		
	public PeriodoDisoccupazione getPeriodidisoccupazione() {
		return periodidisoccupazione;
	}
	public void setPeriodidisoccupazione(PeriodoDisoccupazione periodidisoccupazione) {
		this.periodidisoccupazione = periodidisoccupazione;
	}
	public AssolvimentoIstruzione getAssolvimentoistruzione() {
		return assolvimentoistruzione;
	}
	public void setAssolvimentoistruzione(AssolvimentoIstruzione assolvimentoistruzione) {
		this.assolvimentoistruzione = assolvimentoistruzione;
	}
	public StatoAnagrafe getStatoinanagrafe() {
		return statoinanagrafe;
	}
	public void setStatoinanagrafe(StatoAnagrafe statoinanagrafe) {
		this.statoinanagrafe = statoinanagrafe;
	}
	public ListeSpeciali getListespeciali_lst() {
		return listespeciali_lst;
	}
	public void setListespeciali_lst(ListeSpeciali listespeciali_lst) {
		this.listespeciali_lst = listespeciali_lst;
	}
	public AltreNotizie getAltreinformazioni() {
		return altreinformazioni;
	}
	public void setAltreinformazioni(AltreNotizie altreinformazioni) {
		this.altreinformazioni = altreinformazioni;
	}
	
}
