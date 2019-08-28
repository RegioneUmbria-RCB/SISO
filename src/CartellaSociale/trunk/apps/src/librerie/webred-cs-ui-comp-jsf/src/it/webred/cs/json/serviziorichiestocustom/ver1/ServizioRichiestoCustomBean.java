package it.webred.cs.json.serviziorichiestocustom.ver1;

import it.webred.cs.json.dto.JsonBaseBean;
 


import it.webred.cs.json.serviziorichiestocustom.ServizioRichiestoCustomBaseBean;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServizioRichiestoCustomBean extends ServizioRichiestoCustomBaseBean { 

	private Date dataRichiesta = new Date();

	public Date getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}


	@Override
	public List<String> checkObbligatorieta() {
		List<String> messages = new LinkedList<String>();
		
	 	if (dataRichiesta ==null) {
				messages.add("Inserire una data richiesta.");
		} 

		return messages;
	
	}

	
	
}
