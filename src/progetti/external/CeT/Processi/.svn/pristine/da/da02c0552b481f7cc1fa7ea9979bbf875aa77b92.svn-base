package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;

import oracle.sql.CLOB;

public class RDemanioBeneFascicolo extends TabellaDwhMultiProv 
{
	
	private String codCartella;
	private String desCartella;
	private String tipo;
	
	
	@Override
	public String getValueForCtrHash()
	{
		
		String hash = this.getIdOrig().getValore()+codCartella+desCartella+tipo ;
		return hash;
	}


	public String getCodCartella() {
		return codCartella;
	}


	public void setCodCartella(String codCartella) {
		this.codCartella = codCartella;
	}


	public String getDesCartella() {
		return desCartella;
	}


	public void setDesCartella(String desCartella) {
		this.desCartella = desCartella;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
