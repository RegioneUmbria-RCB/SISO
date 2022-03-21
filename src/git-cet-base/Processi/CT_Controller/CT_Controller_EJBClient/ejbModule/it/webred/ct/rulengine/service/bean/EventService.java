package it.webred.ct.rulengine.service.bean;
import it.webred.ct.rulengine.controller.model.RAnagEventi;
import it.webred.ct.rulengine.controller.model.REventiEnte;
import it.webred.ct.rulengine.controller.model.REventiEntePK;
import it.webred.ct.rulengine.controller.model.REventiLaunch;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface EventService {
	
	public List<RAnagEventi> getEventiClasseA(REventiLaunch rel);
	public List<RAnagEventi> getEventiClasseB(REventiLaunch rel);
	public List<RAnagEventi> getEventiClasseC(REventiLaunch rel);
	public List<RAnagEventi> getEventiClasseD(REventiLaunch rel);
	public List<RAnagEventi> getEventiClasseE(REventiLaunch rel);
	public List<RAnagEventi> getEventiClasseF(REventiLaunch rel);
	public List<RAnagEventi> getEventiClasseG(REventiLaunch rel);
	
	public List<RAnagEventi> getEventi();
	public List<RAnagEventi> getEventiStd();
	public List<RAnagEventi> getEventiEnte(String belfiore);
	
	
	public void salvaNuovoEvento(RAnagEventi rav);
	public void deleteEvento(Long ravId);
	
	
	public void abilitaEvento(REventiEnte ree);
	public void disabilitaEvento(REventiEntePK ree);
}
