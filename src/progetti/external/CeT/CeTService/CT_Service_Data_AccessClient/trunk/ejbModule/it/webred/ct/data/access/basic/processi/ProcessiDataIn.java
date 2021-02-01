package it.webred.ct.data.access.basic.processi;

import java.io.Serializable;
import java.util.Date;

import it.webred.ct.data.model.ici.VTIciCiviciAll;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class ProcessiDataIn extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private String processId;
	
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	} 
}
