package it.webred.ct.rulengine.service.bean;

import java.util.List;

import it.webred.ct.rulengine.controller.model.RCoda;

import javax.ejb.Remote;

@Remote
public interface QueueService {
	
	public void saveProcess(RCoda rCoda);
	
	public void saveOrUpdateProcess(RCoda rCoda);
	
	public void deleteProcess(RCoda rCoda);
	
	public RCoda getProcessFromCoda();
	
	public void deleteEntireQueue();
	
	public List<RCoda> getRCodaByEnte(String belfiore);
	
	public List<RCoda> getRCodaByEnti(List<String> enti);
	
	public List<RCoda> getFullRCoda();
	
	public List<String> getListaEntiInQueue();
}
