package it.webred.ct.service.gestioneDBDoc.data.access.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class DocInDTO extends CeTBaseObject{
	
	private Long id ;
	private Object obj;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	

}
