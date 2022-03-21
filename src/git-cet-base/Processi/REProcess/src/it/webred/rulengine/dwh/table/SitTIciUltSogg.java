package it.webred.rulengine.dwh.table;

import java.sql.Connection;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitTIciUltSogg extends TabellaDwhMultiProv implements IdExtFromSequence {
		
	
	private RelazioneToMasterTable idExtOggIci = new RelazioneToMasterTable(SitTIciOggetto.class, new ChiaveEsterna());
	private RelazioneToMasterTable idExtSogg = new RelazioneToMasterTable(SitTIciSogg.class, new ChiaveEsterna());
	
	public Relazione getIdExtOggIci() {
		return idExtOggIci;
	}

	public void setIdExtOggIci(ChiaveEsterna idExtOggIci)
	{
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitTIciOggetto.class, idExtOggIci);
		this.idExtOggIci = r;
	}
	
	public Relazione getIdExtSogg() {
		return idExtSogg;
	}

	public void setIdExtSogg(ChiaveEsterna idExtSogg)
	{
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitTIciSogg.class, idExtSogg);
		this.idExtSogg = r;
	}
	
	public String getValueForCtrHash() {		
		return (String)this.idExtOggIci.getRelazione().getValore() +
		(String)this.idExtSogg.getRelazione().getValore();
	}
	
	public String getSequenceName() {
		return "SEQ_SIT_T_ICI_SOGGETTI";
	}

	
}
