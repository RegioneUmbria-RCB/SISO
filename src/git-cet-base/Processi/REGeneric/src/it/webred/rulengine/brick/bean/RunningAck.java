package it.webred.rulengine.brick.bean;

import it.webred.rulengine.db.model.RAbNormal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RunningAck extends CommandAck implements Serializable {

	private List<RAbNormal> abn = new ArrayList<RAbNormal>();

	public RunningAck(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}


	public RunningAck(String message, List<RAbNormal> abn) {
		super(message);
		this.abn = abn;
	}

	
	public void addRAbNormal(RAbNormal abnormal)	{
		abn.add(abnormal);
	}
	
	public List<RAbNormal> getAbn() {
		return abn;
	}

	public void setAbn(List<RAbNormal> abn) {
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
