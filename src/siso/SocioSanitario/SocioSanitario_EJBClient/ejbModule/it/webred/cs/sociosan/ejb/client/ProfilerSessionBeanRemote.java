package it.webred.cs.sociosan.ejb.client;

import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.Remote;

@Remote
public interface ProfilerSessionBeanRemote {

	public String getUsernameUtente(CeTBaseObject dto);

}
