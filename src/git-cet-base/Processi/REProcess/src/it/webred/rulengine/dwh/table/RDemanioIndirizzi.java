package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;

import java.math.BigDecimal;

public class RDemanioIndirizzi extends TabellaDwhMultiProv
{
	private Relazione idExtBene = new Relazione(RDemanioBene.class, new ChiaveEsterna());
	private String chiaveBene;
	private BigDecimal flgPrincipale;
	private BigDecimal codVia;
	private String tipoVia;
	private String descrVia;
	private String civico;
	private String codComune;
	private String desComune;
	private String prov;
	private String tipo;
	
	 
	@Override
	public String getValueForCtrHash()
	{
		
		String hash =  this.getIdOrig().getValore() + this.tipoVia + this.descrVia + this.desComune+this.getProv();
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


	public BigDecimal getFlgPrincipale() {
		return flgPrincipale;
	}


	public void setFlgPrincipale(BigDecimal flgPrincipale) {
		this.flgPrincipale = flgPrincipale;
	}


	public BigDecimal getCodVia() {
		return codVia;
	}


	public void setCodVia(BigDecimal codVia) {
		this.codVia = codVia;
	}


	public String getTipoVia() {
		return tipoVia;
	}


	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}


	public String getDescrVia() {
		return descrVia;
	}


	public void setDescrVia(String descrVia) {
		this.descrVia = descrVia;
	}


	public String getCivico() {
		return civico;
	}


	public void setCivico(String civico) {
		this.civico = civico;
	}


	public String getCodComune() {
		return codComune;
	}


	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}


	public String getDesComune() {
		return desComune;
	}


	public void setDesComune(String desComune) {
		this.desComune = desComune;
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


	public String getProv() {
		return prov;
	}


	public void setProv(String prov) {
		this.prov = prov;
	}
	
}
