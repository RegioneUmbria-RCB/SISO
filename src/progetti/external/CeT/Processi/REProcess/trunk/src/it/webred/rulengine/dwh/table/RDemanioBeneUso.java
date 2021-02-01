package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;

public class RDemanioBeneUso extends TabellaDwhMultiProv 
{
	
	private BigDecimal codTipoUso;
	private String desTipoUso;
	private String tipo;
	
	
	@Override
	public String getValueForCtrHash()
	{
		
		String hash =  this.getIdOrig().getValore()+this.desTipoUso+this.codTipoUso+this.tipo;
		return hash;
	}


	public BigDecimal getCodTipoUso() {
		return codTipoUso;
	}


	public void setCodTipoUso(BigDecimal codTipoUso) {
		this.codTipoUso = codTipoUso;
	}


	public String getDesTipoUso() {
		return desTipoUso;
	}


	public void setDesTipoUso(String desTipoUso) {
		this.desTipoUso = desTipoUso;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


}
