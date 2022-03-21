package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitLicenzeCommercioTit extends TabellaDwhMultiProv {
	
	private RelazioneToMasterTable idExtAutorizzazione = new RelazioneToMasterTable(SitLicenzeCommercio.class, new ChiaveEsterna());
	private Relazione idExtAnagrafica = new Relazione(SitLicenzeCommercioAnag.class, new ChiaveEsterna());
	private String titolo;
	
	public Relazione getIdExtAutorizzazione() {
		return idExtAutorizzazione;
	}

	public void setIdExtAutorizzazione(ChiaveEsterna idExtAutorizzazione)
	{
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitLicenzeCommercio.class, idExtAutorizzazione);
		this.idExtAutorizzazione = r;
	}
	
	public Relazione getIdExtAnagrafica() {
		return idExtAnagrafica;
	}

	public void setIdExtAnagrafica(ChiaveEsterna idExtAnagrafica) {
		Relazione r = new Relazione(SitLicenzeCommercioAnag.class, idExtAnagrafica);
		this.idExtAnagrafica = r;	
	}
	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	@Override
	public String getValueForCtrHash()
	{
		return (String)this.idExtAutorizzazione.getRelazione().getValore() +
		(String)this.idExtAnagrafica.getRelazione().getValore() +
		this.titolo +
		this.getProvenienza();
	}
	
}
