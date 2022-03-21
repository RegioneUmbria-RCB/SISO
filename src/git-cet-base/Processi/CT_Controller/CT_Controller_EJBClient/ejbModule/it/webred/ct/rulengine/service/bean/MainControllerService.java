package it.webred.ct.rulengine.service.bean;



import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommandType;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface MainControllerService {
	
	public List<RCommandType> getListRCommandType(); 
	
	public RCommandType getRCommandType(Long idCommandType); 
	
	public RAnagStati getRAnagStato(Long idStato);
	
	public RAnagStati getRAnagStato(Long idCommandType,String tipoStato);
	
	
	public List<RAnagStati> getAllStati();
	
	public List<RAnagStati> getStati(Long rCommandType);
}
