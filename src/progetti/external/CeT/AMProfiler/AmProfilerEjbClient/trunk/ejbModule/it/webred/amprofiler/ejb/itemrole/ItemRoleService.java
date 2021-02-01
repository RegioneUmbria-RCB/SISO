package it.webred.amprofiler.ejb.itemrole;

import java.util.List;

import it.webred.amprofiler.model.AmUser;

import javax.ejb.Remote;

@Remote
public interface ItemRoleService {

	public List<String> findApplicationRoles(String ente, String amApplicationName, String username);

	public boolean isAmItemEnabled(String ente, String username, String applicationItem);

	public List<String> findPermissionByEnteItemUsername(String ente,
			String applicationItem, String username);

	public List<String> findPermissionByEnteItemUsername(String ente,
			String applicationItem, String username, List<String> gruppi);
	
}
