package it.webred.rulengine.brick.bean;

import it.webred.rulengine.db.model.RAbNormal;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe per la gestione delle anomalie di tipo applicativo
 * 
 * @author sisani
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:25 $
 */
public class ApplicationAck extends CommandAck
{

	private List<RAbNormal> abn = new ArrayList<RAbNormal>();

	/**
	 * Costruttore per la creazione di un ApplicationAck con il messaggio di errore
	 * 
	 * @param message
	 */
	public ApplicationAck(String message)
	{
		super(message);
	}


	/**
	 * Costruttore per la creazione di un ApplicationAck con la lista delle anomalie 
	 * e il messaggio di errore
	 * @param abnormal
	 * @param message
	 */
	public ApplicationAck(List<RAbNormal> abnormal, String message)
	{
		super(message);
		if (this.getMessage() == null)
			this.setMessage("Riscontrate Anomalie");
		abn = abnormal;
	}

	/**
	 * @param abnormal
	 */
	public void addRAbNormal(RAbNormal abnormal)
	{
		abn.add(abnormal);
	}

	/**
	 * @return
	 */
	public List<RAbNormal> getAbn()
	{
		return abn;
	}

	/**
	 * @param abn
	 */
	public void setAbn(List<RAbNormal> abn)
	{
		this.abn = abn;
	}

}
