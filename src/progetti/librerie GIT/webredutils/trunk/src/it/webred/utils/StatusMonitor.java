package it.webred.utils;

import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class StatusMonitor
{

	private static HashMap	mappa	= new HashMap();

	private StatusMonitor()
	{
	}

	static
	{

	}

	/**
	 * Regista lo stato delle attività delle classi Il metodo deve essere
	 * chiamato ogni volta che si inizia una nuova attività che si vuole
	 * monitorare Quando il metodo chiamante finisce tutte le attività deve
	 * chiamare il metodo con il parametro messaggio a null
	 * 
	 * @param obj
	 *            Il reference dell'oggetto chiamante
	 * @param messaggio
	 *            Messaggio da registrare
	 */
	@SuppressWarnings("unchecked")
	public static void setStato(Object obj, String messaggio)
	{
	String firma = obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
	if (messaggio == null)
		mappa.remove(firma);
	else
	{		
		if(mappa.containsKey(firma))
		{
			((StatusMonitorBean)mappa.get(firma)).setDataAggiornamento(new Date());
			((StatusMonitorBean)mappa.get(firma)).setMsg(messaggio);
		}
		else
		{
			StatusMonitorBean smb = new StatusMonitorBean(messaggio,new Date(), new Date());
			mappa.put(firma, smb);
		}
	}
	}
/**
 * Stampa sul output lo stato delle classi monitorate. (uso debug)
 * @param out
 */
	public static void printStatoObjs(PrintStream out)
	{

	out.println("");
	out.println("--------------- DUMP ATTIVITA IN CORSO ------------");
	Iterator iterator = mappa.entrySet().iterator();
	Map.Entry item;
	while (iterator.hasNext())
	{
		item = (Map.Entry) iterator.next();
		out.println(item.getKey() + ":\t\t " + ((StatusMonitorBean) item.getValue()).getMsg() + "\t\t" + ((StatusMonitorBean) item.getValue()).getDataInizio() + "\t\t" + ((StatusMonitorBean) item.getValue()).getDataAggiornamento());
	}
	out.println("--------------- FINE DUMP  -------------------------"); 
	out.println("");
	}
	/**
	 * Restituisce la mappa delle attività in corso
	 * @return Una mappa di StatusMonitorBean
	 */
	public static HashMap getMap()
	{
		return mappa;
	}
}
