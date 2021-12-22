package it.umbriadigitale.soclav.rest;

 

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.umbriadigitale.soclav.model.gepi.RdCAnagraficaGepi;
 
public class Messaggio implements Serializable{

    private String esito;
    private String messaggio;
    private List<RdCAnagraficaGepi> listRdcAnagraficheGepi; 

    public Messaggio( String esito, String messaggio ) {
         this.esito = esito;
         this.messaggio = messaggio;
         
    }

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public List<RdCAnagraficaGepi> getListRdcAnagraficheGepi() {
		return listRdcAnagraficheGepi;
	}

	public void setListRdcAnagraficheGepi(List<RdCAnagraficaGepi> listRdcAnagraficheGepi) {
		this.listRdcAnagraficheGepi = listRdcAnagraficheGepi;
		if(this.listRdcAnagraficheGepi== null || this.listRdcAnagraficheGepi.size() == 0)
			this.messaggio ="Nessun elemento trovato.";
	}

    

     
}
