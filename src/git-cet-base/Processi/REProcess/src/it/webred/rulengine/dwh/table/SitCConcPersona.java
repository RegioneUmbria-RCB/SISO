package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitCConcPersona extends  TabellaDwhMultiProv {

	private RelazioneToMasterTable idExtCConcessioni = new RelazioneToMasterTable(SitCConcessioni.class,new ChiaveEsterna());
	private Relazione idExtCPersona = new Relazione(SitCPersona.class,new ChiaveEsterna());
	private String titolo;
	
	
	@Override
	public String getValueForCtrHash() {
		return ""+idExtCConcessioni.getRelazione().getValore()+idExtCPersona.getRelazione().getValore()
		+ titolo+getProvenienza();

	}
	
	public Relazione getIdExtCPersona() {
		return idExtCPersona;
	}

	public void setIdExtCPersona(ChiaveEsterna idExtCPersona) {
		Relazione r = new Relazione(SitCConcPersona.class,idExtCPersona);
		this.idExtCPersona = r;	
	}

	public Relazione getIdExtCConcessioni()
	{
		return idExtCConcessioni;
	}

	public void setIdExtCConcessioni(ChiaveEsterna idExtCConcessioni)
	{
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitCConcessioni.class,idExtCConcessioni);
		this.idExtCConcessioni = r;	
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
		
}
