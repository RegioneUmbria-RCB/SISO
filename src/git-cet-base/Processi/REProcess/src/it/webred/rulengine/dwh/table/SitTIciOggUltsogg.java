package it.webred.rulengine.dwh.table;

import java.sql.Connection;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitTIciOggUltsogg extends TabellaDwhMultiProv implements IdExtFromSequence {
	
	
	private RelazioneToMasterTable idExtIciUltSogg = new RelazioneToMasterTable(SitTIciUltSogg.class, new ChiaveEsterna());
	private String titSogg;
	
	public Relazione getIdExtIciUltSogg() {
		return idExtIciUltSogg;
	}

	public void setIdExtIciUltSogg(ChiaveEsterna idExtIciUltSogg) {
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitTIciUltSogg.class, idExtIciUltSogg);
		this.idExtIciUltSogg = r;
	}
	
	public String getTitSogg() {
		return titSogg;
	}

	public void setTitSogg(String titSogg) {
		this.titSogg = titSogg;
	}

	public String getValueForCtrHash() {		
		return (String)this.idExtIciUltSogg.getRelazione().getValore() +
		this.titSogg;
	}
	
	public String getSequenceName() {
		return "SEQ_SIT_T_ICI_SOGGETTI";
	}

	
}
