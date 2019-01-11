package it.webred.cs.sociosan.ejb.ejb;



import it.webred.cs.base.CsBaseSessionBean;
import it.webred.cs.sociosan.ejb.client.AtlanteSessionBeanRemote;
import it.webred.cs.sociosan.ejb.dao.AtlanteDAO;
import it.webred.cs.sociosan.ejb.dto.ServiziDTO;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.siso.ws.client.atlante.client.AtlanteClient;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;


@Stateless
public class AtlanteSessionBean extends CsBaseSessionBean implements AtlanteSessionBeanRemote  {
	
	@Autowired
	private AtlanteDAO atlanteDao;

	@Override
	public ServiziDTO getServizi(String codiceFisclae) throws  SocioSanitarioException {
		AtlanteClient atlante;
		ServiziDTO servizi = new ServiziDTO();
		try {
			
			servizi = atlanteDao.getServizi(codiceFisclae);
			
		} catch (SocioSanitarioException e) {
			throw e;
		} catch (Exception e) {
			throw new SocioSanitarioException(e);
		}
		

		
		return servizi;
	}
	


	
}
