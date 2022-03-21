package it.webred.cs.csa.ejb.client.domini;

import javax.ejb.Remote;

@Remote
public interface AccessTableDominiAmKeySessionBeanRemote {

	public String findByKey(String chiave);

}
