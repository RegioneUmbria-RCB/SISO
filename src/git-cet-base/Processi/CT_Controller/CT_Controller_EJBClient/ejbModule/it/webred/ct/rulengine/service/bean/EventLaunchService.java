package it.webred.ct.rulengine.service.bean;
import java.util.List;

import it.webred.ct.rulengine.controller.model.REventiLaunch;

import javax.ejb.Remote;

@Remote
public interface EventLaunchService {
	
	
	public void saveREventoLaunch(REventiLaunch rel);
	
	public void deleteREventoLaunch(REventiLaunch rel);
	
	public void deleteAllEventi();
	
	public List<REventiLaunch> getREventiLaunch();
}
