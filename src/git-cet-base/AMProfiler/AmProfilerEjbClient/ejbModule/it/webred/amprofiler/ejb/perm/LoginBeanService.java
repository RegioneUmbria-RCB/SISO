package it.webred.amprofiler.ejb.perm;
import it.webred.amprofiler.ejb.dto.AmBaseDTO;
import it.webred.amprofiler.ejb.dto.PermessiDTO;
import it.webred.amprofiler.model.AmGroup;
import it.webred.amprofiler.model.AmTracciaAccessi;
import it.webred.amprofiler.model.perm.PermAccesso;
import it.webred.ct.support.validation.CeTToken;
import it.webred.utils.GenericTuples;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface LoginBeanService {

	public HashMap<String, String> getPermissions(String username, String ente);
	
	public HashMap getPermissionsByGroup(String gruppo, String ente);

	public PermAccesso getPermissionAccesso(String username, String ente);
		
	public Boolean isPwdValida(String username, int numGiorni);
	
	public List<String> getEnte(String username);
	
	public List<GenericTuples.T2<String, String>> getEntiByUtente(String username);

	public List<AmGroup> getGruppi(String username, String ente);

	public CeTToken getToken(String username, String password, String ente) 
			throws LoginServiceException;
	
	public HashMap getPermissionsByAmInstanceComune(PermessiDTO dto);
	
	public void salvaTracciaAccessi(AmBaseDTO traccia);
	
	public List<AmTracciaAccessi> findTracciaAccessiByLastAccessUser(String userName);
	
	public String findUtenteBySessionId(String sessionId);
	
	public void aggiornaTracciaAccessiBySessionID(AmBaseDTO dto) throws LoginServiceException;
	
	//public void aggiornaTracciaAccessiByPubPk(AmBaseDTO dto) throws LoginServiceException;
	
	public void aggiornaTracciaAccessiByPriK(AmBaseDTO dto) throws LoginServiceException;
	
	public AmTracciaAccessi findTracciaAccessiByPriK(String priK);

	public boolean appartieneAGruppoLike(String uNameOperatoreCorrente,String codRouting, String string);
}
