package it.webred.cs.sociosan.ejb.client;

import javax.ejb.Remote;

import it.webred.cs.sociosan.ejb.dto.isee.RicercaIseeParams;
import it.webred.cs.sociosan.ejb.dto.isee.RicercaIseeResult;

@Remote
public interface RicercaIseeSessionBeanRemote {

	public RicercaIseeResult ricercaIsee(RicercaIseeParams params);

}
