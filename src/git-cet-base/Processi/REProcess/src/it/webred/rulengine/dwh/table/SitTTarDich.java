package it.webred.rulengine.dwh.table;

import java.sql.Connection;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitTTarDich extends TabellaDwhMultiProv implements IdExtFromSequence {
		
	
	private RelazioneToMasterTable idExtOggRsu = new RelazioneToMasterTable(SitTTarOggetto.class, new ChiaveEsterna());
	private RelazioneToMasterTable idExtSogg = new RelazioneToMasterTable(SitTTarSogg.class, new ChiaveEsterna());

	public Relazione getIdExtOggRsu() {
		return idExtOggRsu;
	}

	public void setIdExtOggRsu(ChiaveEsterna idExtOggRsu)
	{
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitTTarOggetto.class, idExtOggRsu);
		this.idExtOggRsu = r;
	}
	
	public Relazione getIdExtSogg() {
		return idExtSogg;
	}

	public void setIdExtSogg(ChiaveEsterna idExtSogg)
	{
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitTTarSogg.class, idExtSogg);
		this.idExtSogg = r;
	}
	
	public String getValueForCtrHash() {	
		return (String)this.idExtOggRsu.getRelazione().getValore() +
		(String)this.idExtSogg.getRelazione().getValore();
	}
	
	public String getSequenceName() {
		return "SEQ_SIT_T_TAR_SOGGETTI";
	}

	
}
