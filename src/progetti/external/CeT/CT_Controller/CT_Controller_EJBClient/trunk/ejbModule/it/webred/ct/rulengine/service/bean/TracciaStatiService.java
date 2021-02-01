package it.webred.ct.rulengine.service.bean;
import java.util.Date;
import java.util.List;

import it.webred.ct.rulengine.controller.model.RTracciaDate;
import it.webred.ct.rulengine.controller.model.RTracciaForniture;
import it.webred.ct.rulengine.controller.model.RTracciaStati;

import javax.ejb.Remote;

@Remote
public interface TracciaStatiService {

	public void saveTracciaStato(RTracciaStati rTracciaStati);
	
	public void deleteTracciaStato(RTracciaStati rTracciaStati);
	
	public void saveOrUpdateTracciaStato(RTracciaStati rTracciaStati);
	
	public List<RTracciaStati> getTracciaStato(String belfiore);
	
	public List<RTracciaStati> getTracciaStato(String belfiore,Long idFonte);

	public RTracciaStati getTracciaStatoByProcessId(String processId);
	
	public Date getMinTracciaStato(String belfiore, Long idFonte);
	
	public Date getMinTracciaForniture(String belfiore, Long idFonte);
	
	public Date getMaxTracciaForniture(String belfiore, Long idFonte);
	
	public List<RTracciaForniture> getTracciaFornitureByProcessId(String processId);
	
	public RTracciaDate getTracciaDate(String belfiore, Long idFonte);
	
	public void updateTracciaDate(RTracciaDate rTracciaDate);
	
	public void saveTracciaDate(RTracciaDate rTracciaDate);
	
	public void deleteTracciaDate(RTracciaDate rTracciaDate);
	
}
