package it.umbriadigitale.soclav.service.dto.sap;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import it.umbriadigitale.soclav.service.dto.sap.lavoratore.Allegato;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.DatiAmministrativi;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.DatiAnagrafici;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.DatiInvio;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.EsperienzeLavoro;
import it.umbriadigitale.soclav.service.dto.sap.lavoratore.PoliticheAttive;

@XmlRootElement(name="lavoratore")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Lavoratore implements Serializable {
	
	private DatiInvio datiinvio;

	private DatiAmministrativi datiamministrativi;
	
	private EsperienzeLavoro esperienzelavoro_lst;
	
	private PoliticheAttive politiche_attive_lst;
	
	private Allegato allegato;
	
	private DatiAnagrafici datianagrafici;
	 
	public DatiInvio getDatiinvio() {
		return datiinvio;
	}

	public void setDatiinvio(DatiInvio datiinvio) {
		this.datiinvio = datiinvio;
	}

	public DatiAmministrativi getDatiamministrativi() {
		return datiamministrativi;
	}

	public void setDatiamministrativi(DatiAmministrativi datiamministrativi) {
		this.datiamministrativi = datiamministrativi;
	}

	public EsperienzeLavoro getEsperienzelavoro_lst() {
		return esperienzelavoro_lst;
	}

	public void setEsperienzelavoro_lst(EsperienzeLavoro esperienzelavoro_lst) {
		this.esperienzelavoro_lst = esperienzelavoro_lst;
	}

	public PoliticheAttive getPolitiche_attive_lst() {
		return politiche_attive_lst;
	}

	public void setPolitiche_attive_lst(PoliticheAttive politiche_attive_lst) {
		this.politiche_attive_lst = politiche_attive_lst;
	}

	public Allegato getAllegato() {
		return allegato;
	}

	public void setAllegato(Allegato allegato) {
		this.allegato = allegato;
	}

	public DatiAnagrafici getDatianagrafici() {
		return datianagrafici;
	}

	public void setDatianagrafici(DatiAnagrafici datianagrafici) {
		this.datianagrafici = datianagrafici;
	}
}
