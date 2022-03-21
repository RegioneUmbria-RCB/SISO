package it.webred.rulengine.dwh.def;

import it.webred.utils.DateFormat;

import java.sql.Timestamp;

public class DataDwh implements TipoData, Campo
{

	private Timestamp valore;
	
	public DataDwh(){
	}
	
	public DataDwh(Object o){
		setValore(o);
	}
	
	public void setValore(Timestamp ts)
	{
		this.valore = ts;

	}

	public Timestamp getValore()
	{
		return valore;
	}
	
	/*
	 *  !!!!!!!!!!!!! importante !!!!!!!!!!!!!!!!!!!!!!!
	 *  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 *  NON MODIFICARE LA FORMATTAZIONE IN QUESTO METODO VISTO CHE VIENE USATO PER COMPORRE L'HASH
	 *  NEL MECCANISMO DI STORICIZZAZIONE DEL DWH , EVENTUALMENTE FARE UN ALTRO METODO SE SERVE 
	 */
	public String getDataFormattata() {
		return DateFormat.dateToString(valore,"yyyyMMdd HHmmss");
	}

	public void setValore(Object o)
	{
		if (o instanceof java.sql.Date)
			valore = new Timestamp((((java.sql.Date)o).getTime()));
		else	
			valore = (Timestamp)o;
		
	}



}
