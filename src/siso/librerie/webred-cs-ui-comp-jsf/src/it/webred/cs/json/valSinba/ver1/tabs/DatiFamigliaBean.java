package it.webred.cs.json.valSinba.ver1.tabs;

import it.webred.cs.json.valSinba.ver1.tabs.DatiFamigliaMan.TIPO_COMPONENTE;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

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
	
	@JsonIgnore private ComponenteFamigliaBean componenteFamiglia;
	
	// TODO: Aggiungere lista componenti famiglia
	List<ComponenteFamigliaBean> lstComponentiFamiglia = new ArrayList<ComponenteFamigliaBean>();
	

	
	public DatiFamigliaBean() {
		
	}
	
	public boolean isGenitoreSelected(){
		boolean genitore = TIPO_COMPONENTE.MADRE.codice == composizioneFamiglia || TIPO_COMPONENTE.PADRE.codice== composizioneFamiglia;
		return genitore;
	}
	
	
	public void aggiungiComponente()
	{
		ComponenteFamigliaBean cf = new ComponenteFamigliaBean();
		if (composizioneFamiglia != 0 && !componenteExist(composizioneFamiglia)){
			
			cf.setTipoID(composizioneFamiglia);
			cf.setTipo(DatiFamigliaMan.TIPO_COMPONENTE.getDescrizioneByCodice(composizioneFamiglia));
			if (!StringUtils.isBlank(cittadinanza)){
				if (cittadinanza.split("-").length == 2){
					cf.setCittadinanza(cittadinanza.split("-")[1]);
					cf.setCittadinanzaID(String.format("%03d",Integer.valueOf(cittadinanza.split("-")[0])));
				}
			}
			if (regioneFam !=0){
				cf.setRegioneID(String.format("%02d",regioneFam));
				cf.setRegione(DatiFamigliaMan.REGIONI.getDescrizioneByCodice(regioneFam));
			}
			if (titoloStudio != 0){
				cf.setTitoloStudioID(titoloStudio);
				cf.setTitoloStudio(DatiFamigliaMan.TITOLO_STUDIO.getDescrizioneByCodice(titoloStudio));
			}
			if (occupazione != 0){
				cf.setOccupazioneID(occupazione);
				cf.setOccupazione(DatiFamigliaMan.OCCUPAZIONE.getDescrizioneByCodice(occupazione));
			}
			
			if (cf.isNotNull()){
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
	
	private boolean componenteExist(int c){
		for (ComponenteFamigliaBean comp : lstComponentiFamiglia){
			if (comp.getTipoID() == c){
				return true;
			}
		}
		return false;
	}
	
	public List<ComponenteFamigliaBean> getLstComponentiFamiglia() {
		return lstComponentiFamiglia;
	}

	public void setLstComponentiFamiglia(
			List<ComponenteFamigliaBean> lstComponentiFamiglia) {
		this.lstComponentiFamiglia = lstComponentiFamiglia;
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

	public SelectItem getNazioneResidenzaItem() {
		return nazioneResidenzaItem;
	}

	public void setNazioneResidenzaItem(SelectItem nazioneResidenzaItem) {
		this.nazioneResidenzaItem = nazioneResidenzaItem;
	}

}
