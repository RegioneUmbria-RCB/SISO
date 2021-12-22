package it.webred.cs.json.valSinba.ver1.tabs;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatiFamigliaBean {
	
	private int condizioneMinore;
	private int luogoVita;
	private String nazioneResidenza;
	private SelectItem nazioneResidenzaItem;
	private int regione;
	private int minoreStranieroAccompagnato;
	
	@JsonIgnore private int composizioneFamiglia;
	@JsonIgnore private String cittadinanza;
	@JsonIgnore private int regioneFam;
	@JsonIgnore private int titoloStudio;
	@JsonIgnore private int occupazione;
	
	@JsonIgnore private List<SelectItem> lstCittadinanze;
	@JsonIgnore private List<SelectItem> lstNazioni;
	@JsonIgnore private List<SelectItem> lstRegione;
	@JsonIgnore	private List<SelectItem> lstRegioneFam;
	@JsonIgnore private List<SelectItem> lstTitoliStudio;
	@JsonIgnore private List<SelectItem> lstOccupazione;
	
	@JsonIgnore private ComponenteFamigliaBean componenteFamiglia;
	
	// TODO: Aggiungere lista componenti famiglia
	List<ComponenteFamigliaBean> lstComponentiFamiglia = new ArrayList<ComponenteFamigliaBean>();
	
	
	public DatiFamigliaBean() {
		
	}
	
	public void aggiungiComponente()
	{
		ComponenteFamigliaBean cf = new ComponenteFamigliaBean();
		if (composizioneFamiglia != 0 && !componenteExist(composizioneFamiglia))
		{
			cf.setTipoID(composizioneFamiglia);
			cf.setTipo(getTipoComponenteFromId(composizioneFamiglia));
			if (cittadinanza != null && !"".equals(cittadinanza))
			{
				if (cittadinanza.split("-").length == 2)
				{
					cf.setCittadinanza(cittadinanza.split("-")[1]);
					cf.setCittadinanzaID(String.format("%03d",Integer.valueOf(cittadinanza.split("-")[0])));
				}
			}
			if (regioneFam != 0)
			{
				cf.setRegioneID(String.format("%02d",regioneFam));
				cf.setRegione(getRegioneComponenteFromId(regioneFam));
			}
			if (titoloStudio != 0)
			{
				cf.setTitoloStudioID(titoloStudio);
				cf.setTitoloStudio(getTitoloStudioComponenteFromId(titoloStudio));
			}
			if (occupazione != 0)
			{
				cf.setOccupazioneID(occupazione);
				cf.setOccupazione(getOccupazioneComponenteFromId(occupazione));
			}
			
			if (cf.isNotNull())
			{
				lstComponentiFamiglia.add(cf);
				resetCombo();
			}
		}
	}
	
	private void resetCombo()
	{
		composizioneFamiglia = 0;
		cittadinanza = ""; // TODO: cambiare in Intero
		regioneFam = 0;
		titoloStudio = 0;
		occupazione = 0;
	}
	
	public void rimuoviComponente(int componente)
	{
		if(componente != 0 && componenteExist(componente) )
		{
			ComponenteFamigliaBean remove = new ComponenteFamigliaBean();
			
			for (ComponenteFamigliaBean c : lstComponentiFamiglia)
			{
				if (c.getTipoID() == componente)
				{
					remove = c;
				}
			}
			
			if (remove.isNotNull())
			{
				lstComponentiFamiglia.remove(remove);
			}
		}
	}
	
	private boolean componenteExist(int c)
	{
		for (ComponenteFamigliaBean comp : lstComponentiFamiglia)
		{
			if (comp.getTipoID() == c)
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean componenteExist(String c)
	{
		for (ComponenteFamigliaBean comp : lstComponentiFamiglia)
		{
			if (comp.getTipo().equalsIgnoreCase(c))
			{
				return true;
			}
		}
		return false;
	}
	
	
	private String getTipoComponenteFromId(int id)
	{
		switch (id)
		{
		case 0:
			return "";
		case 1:
			return "Madre";
		case 2:
			return "Padre";
		case 3:
			return "Fratelli/Sorelle";
		case 4:
			return "Nonno/a";
		case 5:
			return "Compagno/a della madre o del padre";
		case 6:
			return "Altri conviventi";
		}
		return "";
	}
	
	
	private String getRegioneComponenteFromId(int id)
	{
		switch (id)
		{
		case 0:
			return "";
		case 1:
			return "Abruzzo";
		case 2:
			return "Basilicata";
		case 3:
			return "Bolzano";
		case 4:
			return "Calabria";
		case 5:
			return "Campania";
		case 6:
			return "Emilia Romagna";
		case 7:
			return "Friuli Venezia Giulia";
		case 8:
			return "Lazio";
		case 9:
			return "Liguria";
		case 10:
			return "Lombardia";
		case 11:
			return "Marche";
		case 12:
			return "Molise";
		case 13:
			return "Piemonte";
		case 14:
			return "Puglia";
		case 15:
			return "Sardegna";
		case 16:
			return "Sicilia";
		case 17:
			return "Toscana";
		case 18:
			return "Trento";
		case 19:
			return "Umbria";
		case 20:
			return "Valle D'Aosta";
		case 21:
			return "Veneto";
		}
		return "";
	}
	
	private String getTitoloStudioComponenteFromId(int id)
	{
		switch (id)
		{
		case 0:
			return "";
		case 1:
			return "Nessun Titolo";
		case 2:
			return "Licenza Elementare";
		case 3:
			return "Licenza Media";
		case 4:
			return "Qualifica Professionale";
		case 5:
			return "Diploma Scuola Superiore";
		case 6:
			return "Laurea o Diploma di Laurea";
		}
		return "";
	}
	
	
	private String getOccupazioneComponenteFromId(int id)
	{
		switch (id)
		{
		case 0:
			return "";
		case 1:
			return "Occupato";
		case 2:
			return "Disoccupato alla ricerca di nuova occupazione";
		case 3:
			return "In cerca di prima occupazione";
		case 4:
			return "Casalinga/o";
		case 5:
			return "Studente";
		case 6:
			return "Ritirato/a dal lavoro";
		case 7:
			return "Inabile al lavoro";
		case 8:
			return "In altra condizione";
		}
		return "";
	}
	
	public List<ComponenteFamigliaBean> getLstComponentiFamiglia() {
		return lstComponentiFamiglia;
	}

	public void setLstComponentiFamiglia(
			List<ComponenteFamigliaBean> lstComponentiFamiglia) {
		this.lstComponentiFamiglia = lstComponentiFamiglia;
	}

	public List<SelectItem> getLstNazioni() {
		return lstNazioni;
	}



	public void setLstNazioni(List<SelectItem> lstNazioni) {
		this.lstNazioni = lstNazioni;
	}



	public List<SelectItem> getLstRegione() {
		return lstRegione;
	}


	public void setLstRegione(List<SelectItem> lstRegione) {
		this.lstRegione = lstRegione;
	}


	public List<SelectItem> getLstOccupazione() {
		return lstOccupazione;
	}


	public void setLstOccupazione(List<SelectItem> lstOccupazione) {
		this.lstOccupazione = lstOccupazione;
	}


	public List<SelectItem> getLstCittadinanze() {
		return lstCittadinanze;
	}



	public void setLstCittadinanze(List<SelectItem> lstCittadinanze) {
		this.lstCittadinanze = lstCittadinanze;
	}



	public List<SelectItem> getLstTitoliStudio() {
		return lstTitoliStudio;
	}



	public void setLstTitoliStudio(List<SelectItem> lstTitoliStudio) {
		this.lstTitoliStudio = lstTitoliStudio;
	}

	public int getCondizioneMinore() {
		return condizioneMinore;
	}

	public void setCondizioneMinore(int condizioneMinore) {
		this.condizioneMinore = condizioneMinore;
	}

	public int getLuogoVita() {
		return luogoVita;
	}

	public void setLuogoVita(int luogoVita) {
		this.luogoVita = luogoVita;
	}

	public String getNazioneResidenza() {
		return nazioneResidenza;
	}

	public void setNazioneResidenza(String nazioneResidenza) {
		this.nazioneResidenza = nazioneResidenza;
	}

	public int getRegione() {
		return regione;
	}

	public void setRegione(int regione) {
		this.regione = regione;
	}

	public int getMinoreStranieroAccompagnato() {
		return minoreStranieroAccompagnato;
	}

	public void setMinoreStranieroAccompagnato(int minoreStranieroAccompagnato) {
		this.minoreStranieroAccompagnato = minoreStranieroAccompagnato;
	}

	public int getComposizioneFamiglia() {
		return composizioneFamiglia;
	}

	public void setComposizioneFamiglia(int composizioneFamiglia) {
		this.composizioneFamiglia = composizioneFamiglia;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public int getRegioneFam() {
		return regioneFam;
	}

	public void setRegioneFam(int regioneFam) {
		this.regioneFam = regioneFam;
	}

	public int getTitoloStudio() {
		return titoloStudio;
	}

	public void setTitoloStudio(int titoloStudio) {
		this.titoloStudio = titoloStudio;
	}

	public int getOccupazione() {
		return occupazione;
	}

	public void setOccupazione(int occupazione) {
		this.occupazione = occupazione;
	}

	public List<SelectItem> getLstRegioneFam() {
		return lstRegioneFam;
	}

	public void setLstRegioneFam(List<SelectItem> lstRegioneFam) {
		this.lstRegioneFam = lstRegioneFam;
	}

	public SelectItem getNazioneResidenzaItem() {
		return nazioneResidenzaItem;
	}

	public void setNazioneResidenzaItem(SelectItem nazioneResidenzaItem) {
		this.nazioneResidenzaItem = nazioneResidenzaItem;
	}

}
