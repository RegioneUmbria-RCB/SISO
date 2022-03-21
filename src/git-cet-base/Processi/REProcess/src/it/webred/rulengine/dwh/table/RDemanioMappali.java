package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;

import java.math.BigDecimal;

public class RDemanioMappali extends TabellaDwhMultiProv
{
	private Relazione idExtBene = new Relazione(RDemanioBene.class, new ChiaveEsterna());
	private String chiaveBene;
	private String tipoMappale;
	private String codComune;
	private String sezione;
	private String foglio;
	private String mappale;
	private String tipo;
	
	 
	@Override
	public String getValueForCtrHash()
	{
		
		String hash =  this.getIdOrig().getValore() + this.tipoMappale + this.codComune +this.sezione
				+ this.foglio + this.mappale + this.tipoMappale;
		return hash;
	}


	public Relazione getIdExtBene() {
		return idExtBene;
	}


	public void setIdExtBene(Relazione idExtBene) {
		this.idExtBene = idExtBene;
	}


	public String getChiaveBene() {
		return chiaveBene;
	}


	public void setChiaveBene(String chiaveBene) {
		this.chiaveBene = chiaveBene;
	}

	public String getTipoMappale() {
		return tipoMappale;
	}


	public void setTipoMappale(String tipoMappale) {
		this.tipoMappale = tipoMappale;
	}


	public String getFoglio() {
		return foglio;
	}


	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}


	public String getMappale() {
		return mappale;
	}


	public void setMappale(String mappale) {
		this.mappale = mappale;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setIdExtBene(ChiaveEsterna idExt) {
		Relazione r = new Relazione(RDemanioBene.class, idExt);
		this.idExtBene = r;
	}


	public String getCodComune() {
		return codComune;
	}


	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}


	public String getSezione() {
		return sezione;
	}


	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	

}
