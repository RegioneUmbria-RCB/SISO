package it.webred.ct.data.access.basic.cartellasociale;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.cartellasociale.dao.CartellaSocialeDAO;
import it.webred.ct.data.access.basic.cartellasociale.dto.InterventoDTO;
import it.webred.ct.data.access.basic.cartellasociale.dto.RicercaCarSocDTO;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless 
public class CartellaSocialeServiceBean extends CTServiceBaseBean implements CartellaSocialeService {

	private static final long serialVersionUID = 1L;
	@Autowired 
	private CartellaSocialeDAO csDAO;

	@Override
	public List<InterventoDTO> getInterventiByCF(RicercaCarSocDTO ro) {
		
		String cf = ro.getCodFisc();
		
		List<InterventoDTO> list = csDAO.getInterventiByCF(cf);
		
		return list;
	} 
	

}
