package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

import java.math.BigDecimal;


public class RDemanioBeneEventi extends TabellaDwhMultiProv 
{
	private BigDecimal flVendita;
	private DataDwh dataVendita;
	private String annoAcq;
	private String annoCostr;
	private String annoRistr;
	private String tipo;


	@Override
	public String getValueForCtrHash()
	{
		
		String hash =  this.getIdOrig().getValore() + this.annoAcq + this.annoCostr + this.annoRistr + this.tipo + this.flVendita;
		return hash;
	}
	
	public BigDecimal getFlVendita() {
		return flVendita;
	}
	
	public void setFlVendita(BigDecimal flVendita) {
		this.flVendita = flVendita;
	}
	
	public DataDwh getDataVendita() {
		return dataVendita;
	}
	
	public void setDataVendita(DataDwh dataVendita) {
		this.dataVendita = dataVendita;
	}
	
	public String getAnnoAcq() {
		return annoAcq;
	}
	
	public void setAnnoAcq(String annoAcq) {
		this.annoAcq = annoAcq;
	}
	
	public String getAnnoCostr() {
		return annoCostr;
	}
	
	public void setAnnoCostr(String annoCostr) {
		this.annoCostr = annoCostr;
	}
	
	public String getAnnoRistr() {
		return annoRistr;
	}
	
	public void setAnnoRistr(String annoRistr) {
		this.annoRistr = annoRistr;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}