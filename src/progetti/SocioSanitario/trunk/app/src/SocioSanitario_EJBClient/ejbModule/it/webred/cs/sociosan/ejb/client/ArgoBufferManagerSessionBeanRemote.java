package it.webred.cs.sociosan.ejb.client;

import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import javax.ejb.Remote;

@Remote
public interface ArgoBufferManagerSessionBeanRemote {


	public void sendSimpleMailFromSISO(String emailDest, String oggetto, String corpo) throws SocioSanitarioException;


}
