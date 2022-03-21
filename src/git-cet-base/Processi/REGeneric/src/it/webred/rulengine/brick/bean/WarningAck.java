package it.webred.rulengine.brick.bean;

import it.webred.rulengine.db.model.RAbNormal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per la gestione delle anomalie di tipo warning non bloccante.
 * 
 * @author sisani
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:25 $
 */
public class WarningAck extends CommandAck implements Serializable{

	private List<RAbNormal> abn = new ArrayList<RAbNormal>();
	
	
	public WarningAck(String message) {
		super(message);
	}
	

	public WarningAck(List<RAbNormal> abnormal, String message) {
		super(message);
		
		if (this.getMessage() == null) {
			this.setMessage("Presenti Anomalie");
		}
			
		abn = abnormal;
	}
	
	public void addRAbNormal(RAbNormal abnormal)
	{
		abn.add(abnormal);
	}
	
	
	public List<RAbNormal> getAbn()
	{
		return abn;
	}
	
	public void setAbn(List<RAbNormal> abn)
	{
		this.abn = abn;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public void setMessage(String message) {
		super.setMessage(message);
	}

}
