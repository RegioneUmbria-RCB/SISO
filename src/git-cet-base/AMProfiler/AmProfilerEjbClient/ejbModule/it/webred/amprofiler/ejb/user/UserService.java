package it.webred.amprofiler.ejb.user;

import it.webred.amprofiler.ejb.anagrafica.dto.AnagraficaSearchCriteria;
import it.webred.amprofiler.model.AmGroup;
import it.webred.amprofiler.model.AmUser;
import it.webred.amprofiler.model.AmUserUfficio;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface UserService {

	AmUser getUserByName(String username);
	
	List<AmUser> getUsersByEnte(String belfiore);

	List<AmUser> getUsersByEnteInizialiGruppo(String belfiore, String gruppo);

	
	boolean createUser(AmUser user);

	boolean deleteUser(String username);
	
	boolean deleteUserByIstance(String username, String istance, String comune);

	boolean updateUser(AmUser user);

	List<AmUser> findAmUser(String amApplicationName, List<String> gruppi,
			AnagraficaSearchCriteria criteria,
			int fromIndex, int maxResults);
	
	//Gestione dati relativi all'ufficio dell'utente connesso
	AmUserUfficio getDatiUfficio(String username);
	
	boolean saveDatiUfficio(AmUserUfficio dati);
	
	public boolean verificaAggiornaAvvocato(String username, String ente);
	
	public int verificaGruppoAvvocati(String ente);
	
	public AmGroup getGroupByName(String name);

	public List<AmGroup> getGruppiUtente(String uname);

	public List<Object[]> getUtentiByApplicazioneEnte(String applicazione, String ente);		
	
	
}
