package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Identificativo;

/**
 * Tabella con l'aggiunta del flag provenienza
 * @author MarcoPort
 *
 */
public class TabellaDwhMultiProv extends TabellaDwh {

	private String provenienza;

	@Override
	public String getValueForCtrHash() {
		return null;
		
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	
	public ChiaveEsterna getIdExt() {
		ChiaveEsterna idExt = new ChiaveEsterna();
		idExt.setValore(super.getIdExt().getValore());
		
		String valore = idExt.getValore();
		idExt.setValore(getProvenienza() + '@' + valore);

		return idExt;
	}
	
	
	
}
