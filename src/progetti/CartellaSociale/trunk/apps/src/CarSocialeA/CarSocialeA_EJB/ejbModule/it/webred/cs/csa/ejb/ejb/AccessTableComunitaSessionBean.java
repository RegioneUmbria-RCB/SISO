package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableComunitaSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.ComunitaDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCComunita;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Andrea
 *
 */

@Stateless
public class AccessTableComunitaSessionBean extends CarSocialeBaseSessionBean implements AccessTableComunitaSessionBeanRemote  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ComunitaDAO comunitaDao;
	

	@Override
	public List<CsCComunita> findComunitaByDescTipo(BaseDTO dto) throws Exception {		
		return comunitaDao.findComunitaByDescTipo((String)dto.getObj());
	
	}

}