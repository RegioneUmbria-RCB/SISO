package it.webred.ct.data.access.basic.tarsu.dto;

import java.io.Serializable;
import it.webred.ct.data.model.tarsu.*;

public class SoggettoTarsuDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String titolo;
	private SitTTarSogg soggetto;
	private String descViaResidenza;
	private Boolean locatarioIndTarsu;		  //Locatario presso l'indirizzo tarsu alla data della dichiarazione
	private String titoloCatastoUiu;          //Titolo a catasto del soggetto tarsu (sulla UIU), alla data della dichiarazione
	private Boolean familiareSoggCat;
	
	public String getTitoloCatastoUiu() {
		return titoloCatastoUiu;
	}
	public void setTitoloCatastoUiu(String titoloCatastoUiu) {
		this.titoloCatastoUiu = titoloCatastoUiu;
	}
	public void setLocatarioIndTarsu(Boolean locatarioIndTarsu) {
		this.locatarioIndTarsu = locatarioIndTarsu;
	}
	public Boolean getLocatarioIndTarsu() {
		return locatarioIndTarsu;
	}
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public SitTTarSogg getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(SitTTarSogg soggetto) {
		this.soggetto = soggetto;
	}
	public void setDescViaResidenza(String descViaResidenza) {
		this.descViaResidenza = descViaResidenza;
	}
	public String getDescViaResidenza() {
		return descViaResidenza;
	}
	public void setFamiliareSoggCat(Boolean familiareSoggCat) {
		this.familiareSoggCat = familiareSoggCat;
	}
	public Boolean getFamiliareSoggCat() {
		return familiareSoggCat;
	}

}
