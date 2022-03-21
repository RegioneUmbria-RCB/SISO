package it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato;

import java.io.Serializable;
import java.util.List;

public class LstAltreInformazioni implements Serializable {

	List<AltreInformazioni> altreinformazioni;

	public List<AltreInformazioni> getAltreinformazioni() {
		return altreinformazioni;
	}

	public void setAltreinformazioni(List<AltreInformazioni> altreinformazioni) {
		this.altreinformazioni = altreinformazioni;
	}
}
