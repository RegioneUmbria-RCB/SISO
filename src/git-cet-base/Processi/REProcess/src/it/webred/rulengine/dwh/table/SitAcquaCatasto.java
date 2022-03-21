package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.TipoXml;


public class SitAcquaCatasto extends TabellaDwh
{

	private String codServizio;
	private String assenzaDatiCat;
	private String sezione;
	private String foglio;
	private String particella;
	private String subalterno;
	private String estensionePart;
	private String tipologiaPart;

	public String getCodServizio() {
		return codServizio;
	}

	public void setCodServizio(String codServizio) {
		this.codServizio = codServizio;
	}

	public String getAssenzaDatiCat() {
		return assenzaDatiCat;
	}

	public void setAssenzaDatiCat(String assenzaDatiCat) {
		this.assenzaDatiCat = assenzaDatiCat;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
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

	public String getEstensionePart() {
		return estensionePart;
	}

	public void setEstensionePart(String estensionePart) {
		this.estensionePart = estensionePart;
	}

	public String getTipologiaPart() {
		return tipologiaPart;
	}

	public void setTipologiaPart(String tipologiaPart) {
		this.tipologiaPart = tipologiaPart;
	}

	@Override
	public String getValueForCtrHash()
	{
		String a = (sezione!=null?sezione:"")+
				 (foglio!=null?foglio:"")+
				 (particella!=null?particella:"")+
				 (subalterno!=null?subalterno:"");
		return "".equals(a)?null:a;
	}
	
}
