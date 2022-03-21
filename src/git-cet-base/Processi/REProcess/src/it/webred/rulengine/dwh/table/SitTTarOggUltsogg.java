package it.webred.rulengine.dwh.table;

import java.sql.Connection;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitTTarOggUltsogg extends TabellaDwhMultiProv implements IdExtFromSequence {
		
	
	private RelazioneToMasterTable idExtTarUltSogg = new RelazioneToMasterTable(SitTTarUltSogg.class, new ChiaveEsterna());
	private String titSogg;
	
	public Relazione getIdExtTarUltSogg() {
		return idExtTarUltSogg;
	}

	public void setIdExtTarUltSogg(ChiaveEsterna idExtTarUltSogg) {
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitTTarUltSogg.class, idExtTarUltSogg);
		this.idExtTarUltSogg = r;
	}
	
	public String getTitSogg() {
		return titSogg;
	}

	public void setTitSogg(String titSogg) {
		this.titSogg = titSogg;
	}

	public String getValueForCtrHash() {		
		return (String)this.idExtTarUltSogg.getRelazione().getValore() +
		this.titSogg;
	}
	
	public String getSequenceName() {
		return "SEQ_SIT_T_TAR_SOGGETTI";
	}

	
}
