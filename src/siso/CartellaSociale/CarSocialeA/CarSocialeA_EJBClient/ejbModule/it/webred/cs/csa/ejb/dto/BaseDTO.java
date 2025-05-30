package it.webred.cs.csa.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;


public class BaseDTO extends CeTBaseObject  {
	private static final long serialVersionUID = 1L;
	
	private Object obj;
	private Object obj2;
	private Object obj3;
	private Object obj4;
	private Object obj5;
	private Object obj6;
	
	/*Parametri valorizzati automaticamente tramite fillEnte con i dati dell'operatoreSettore corrente - servono per AccessoFascicoloInterceptor*/
	private Boolean nascondiInfoPerSettore;
	private Long settoreId;

	
	
	public Object getObj6() {
		return obj6;
	}

	public void setObj6(Object obj6) {
		this.obj6 = obj6;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Object getObj2() {
		return obj2;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}

	public Object getObj3() {
		return obj3;
	}

	public void setObj3(Object obj3) {
		this.obj3 = obj3;
	}

	public Object getObj4() {
		return obj4;
	}

	public void setObj4(Object obj4) {
		this.obj4 = obj4;
	}

	public Boolean getNascondiInfoPerSettore() {
		return nascondiInfoPerSettore;
	}

	public void setNascondiInfoPerSettore(Boolean nascondiInfoPerSettore) {
		this.nascondiInfoPerSettore = nascondiInfoPerSettore;
	}

	public Long getSettoreId() {
		return settoreId;
	}

	public void setSettoreId(Long settoreId) {
		this.settoreId = settoreId;
	}

	public Object getObj5() {
		return obj5;
	}

	public void setObj5(Object obj5) {
		this.obj5 = obj5;
	}
	
}
