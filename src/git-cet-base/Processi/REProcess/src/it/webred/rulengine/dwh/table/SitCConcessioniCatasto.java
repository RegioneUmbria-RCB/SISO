package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;



public class SitCConcessioniCatasto extends TabellaDwhMultiProv
{

	private RelazioneToMasterTable idExtCConcessioni = new RelazioneToMasterTable(SitCConcessioni.class,new ChiaveEsterna());
	private String foglio;
	private String particella;
	private String subalterno;
	private String tipo;
	private String sezione;
	private Relazione idExtCConcIndirizzi = new Relazione(SitCConcIndirizzi.class,new ChiaveEsterna());
	private String codiceFabbricato;

	public String getCodiceFabbricato() {
		return codiceFabbricato;
	}

	public void setCodiceFabbricato(String codiceFabbricato) {
		this.codiceFabbricato = codiceFabbricato;
	}

	public Relazione getIdExtCConcIndirizzi()
	{
		
		return idExtCConcIndirizzi;
	}

	public void setIdExtCConcIndirizzi(ChiaveEsterna idExtCConcIndirizzi)
	{
		Relazione r = new Relazione(SitCConcIndirizzi.class,idExtCConcIndirizzi);
		this.idExtCConcIndirizzi = r;	
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
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

	
	public String getValueForCtrHash() {
		
		return (String)idExtCConcIndirizzi.getRelazione().getValore()+(String)idExtCConcessioni.getRelazione().getValore()+foglio+particella+subalterno+tipo+sezione+getProvenienza();
	}


	public String getFoglio() {
		return foglio;
	}


	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}


	public String getParticella() {
		return particella;
	}


	public void setParticella(String particella) {
		this.particella = particella;
	}


	public String getSubalterno() {
		return subalterno;
	}


	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}



	


}
