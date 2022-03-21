package it.webred.ct.support.audit.test;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.Enumeration;
import java.util.LinkedList;

public class Objtest {

	public enum DataBeanState {
		 NEW, DELETE, VIEW, VIEW_LIST, VIEW_LIST_CUSTOM0, VIEW_LIST_CUSTOM1, VIEW_LIST_CUSTOM2, VIEW_LIST_CUSTOM3, INSERT, UPDATE, OK, ERROR;
		};
	
	private LinkedList ll;
	
	private DataBeanState en = DataBeanState.NEW;
	
	private CeTBaseObject cet;

	public LinkedList getLl() {
		return ll;
	}

	public void setLl(LinkedList ll) {
		this.ll = ll;
	}

	public DataBeanState getEn() {
		return en;
	}

	public void setEn(DataBeanState en) {
		this.en = en;
	}

	public CeTBaseObject getCet() {
		return cet;
	}

	public void setCet(CeTBaseObject cet) {
		this.cet = cet;
	}
	
}
