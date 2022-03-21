package it.webred.cs.json.serviziorichiestocustom.ver1;

import it.webred.cs.json.serviziorichiestocustom.ServizioRichiestoCustomBaseBean;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServizioRichiestoCustomBean extends ServizioRichiestoCustomBaseBean { 

	private Date dataRichiesta = new Date();
	private String note;
	
	private String[] nuoviInterventiSelezionati;
	private String altro;
	
	@JsonIgnore
	private List<SelectItem> categories;    
    private String selezionePDS;
    
    @JsonIgnore
    public List<SelectItem> getCategories() {	
        return categories;
    }    
 
    public String getSelezionePDS() {
        return selezionePDS;
    }
    

    public void setSelezionePDS(String newSelection) {
        this.selezionePDS = newSelection;
    }
    
 
    public String[] getNuoviInterventiSelezionati() {
		return nuoviInterventiSelezionati;
	}

	public void setNuoviInterventiSelezionati(String[] nuoviInterventiSelezionati) {
		this.nuoviInterventiSelezionati = nuoviInterventiSelezionati;
	}
	
	public String getAltro() {
		return altro;
	}
	public void setAltro(String altro) {
		this.altro = altro;
	}
	
	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}


	@Override
	public List<String> checkObbligatorieta() {
		List<String> messages = new LinkedList<String>();
		
	 	if (dataRichiesta ==null)
			messages.add("Inserire una data richiesta.");
		else if (dataRichiesta.after(new Date()))
			messages.add("La data richiesta ("+ddMMyyyy.format(dataRichiesta)+") non può essere successiva alla data odierna.");
		return messages;
	
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
}
