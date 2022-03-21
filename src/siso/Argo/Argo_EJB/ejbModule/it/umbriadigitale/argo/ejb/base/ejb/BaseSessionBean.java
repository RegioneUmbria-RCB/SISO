package it.umbriadigitale.argo.ejb.base.ejb;



import it.umbriadigitale.argo.data.ArSpazio;
import it.umbriadigitale.argo.ejb.ArgoBaseSessionBean;
import it.umbriadigitale.argo.ejb.base.dao.ArSpazioDAO;
import it.umbriadigitale.argo.ejb.client.base.dto.pass.SpazioDTO;
import it.umbriadigitale.argo.ejb.client.base.dto.retvalue.SpazioRetvalDTO;
import it.umbriadigitale.argo.ejb.client.base.ejbclient.BaseSessionBeanRemote;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Andrea
 *
 */
@Stateless
@Remote(BaseSessionBeanRemote.class)
public class BaseSessionBean extends ArgoBaseSessionBean implements BaseSessionBeanRemote {

	@Autowired
	private ArSpazioDAO arSpazioDao;

	@Override
	public void addSpazio(SpazioDTO dto) throws Exception {
		
		ArSpazio arSpazio = new ArSpazio();
		arSpazio.setLatitude(dto.getLatitude());
		arSpazio.setLatitude(dto.getLongitude());
		arSpazio.setBelfiore(dto.getBelfiore());
		
		arSpazioDao.createArSpazio(arSpazio);
		
	}

	
	public List<SpazioRetvalDTO> getSpazioByBelfiore(SpazioDTO spazioDTO)
			throws Exception {

		 List<SpazioRetvalDTO> retList = new ArrayList<SpazioRetvalDTO>();
		 
		List<ArSpazio> lista = arSpazioDao.findArSpazioByBELFIORE(spazioDTO.getBelfiore());
		SpazioRetvalDTO dto = new SpazioRetvalDTO();
		
		for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
			ArSpazio arSpazio = (ArSpazio) iterator.next();
			
			dto.setBelfiore(spazioDTO.getBelfiore());
			dto.setLatitude(arSpazio.getLatitude());
			dto.setLongitude(arSpazio.getLongitude());
			retList.add(dto);
			
		}
		
		
		return retList;
		
		
		
	}

}
