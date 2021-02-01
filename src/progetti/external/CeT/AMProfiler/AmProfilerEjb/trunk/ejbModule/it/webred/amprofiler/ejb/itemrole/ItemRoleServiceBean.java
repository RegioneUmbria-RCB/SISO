package it.webred.amprofiler.ejb.itemrole;

import it.webred.amprofiler.ejb.AmProfilerBaseService;
import it.webred.amprofiler.ejb.itemrole.dto.QueryBuilder;
import it.webred.amprofiler.ejb.user.UserServiceException;
import it.webred.amprofiler.model.AmAiRole;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmPermission;
import it.webred.amprofiler.model.AmUser;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class ItemRoleServiceBean extends AmProfilerBaseService implements
		ItemRoleService {

	@Override
	public List<String> findApplicationRoles(String ente, String amApplicationName,
			String username) {

		List<String> listaRole = new ArrayList<String>();
		try {

			Query q = em.createNamedQuery("AmAiRole.getRoleByEnteAppUser");
			q.setParameter("username", username);
			q.setParameter("application", amApplicationName);
			q.setParameter("ente", ente);
			List<AmAiRole> lista = (List<AmAiRole>) q.getResultList();
			
			for(AmAiRole role: lista){
					listaRole.add(role.getFkAmRole());
			}

		} catch (Throwable t) {
			throw new ItemRoleServiceException(t);
		}

		return listaRole;
		
	}

	@Override
	public boolean isAmItemEnabled(String ente, String username, String applicationItem) {

		boolean exist = false;
		try {

			Query q = em.createNamedQuery("AmAiRole.getRoleByEnteItemUser");
			q.setParameter("username", username.toUpperCase());
			q.setParameter("item", applicationItem);
			q.setParameter("ente", ente);
			List<AmAiRole> lista = (List<AmAiRole>) q.getResultList();
			
			if(lista.size() > 0)
				exist = true;

		} catch (Throwable t) {
			throw new ItemRoleServiceException(t);
		}

		return exist;
	}
	
	@Override
	public List<String> findPermissionByEnteItemUsername(String ente,
			String applicationItem, String username) {
		
		List<String> listaPermessi = new ArrayList<String>();
		try {

			Query q = em.createNativeQuery(new QueryBuilder().createQueryPermessiByEnteItemUsername(ente, applicationItem, username, null));
			
			List<String> result = (List<String>) q.getResultList();
			
			for(String obj: result){
				listaPermessi.add( obj);
			}

		} catch (Throwable t) {
			throw new ItemRoleServiceException(t);
		}

		return listaPermessi;
	}
	
	@Override
	public List<String> findPermissionByEnteItemUsername(String ente,
			String applicationItem, String username, List<String> gruppi) {
		
		List<String> listaPermessi = new ArrayList<String>();
		try {

			Query q = em.createNativeQuery(new QueryBuilder().createQueryPermessiByEnteItemUsername(ente, applicationItem, username, gruppi));
			
			List<String> result = (List<String>) q.getResultList();
			
			for(String obj: result){
				listaPermessi.add( obj);
			}

		} catch (Throwable t) {
			throw new ItemRoleServiceException(t);
		}

		return listaPermessi;
	}
	
}
