package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsIPs;

import java.util.Date;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

public interface IProtocolloDsu {

	public List<String> getListaAnniDsu();

	public void cbxAnnoDsuListener(AjaxBehaviorEvent event);

	public void resetProtDSU();

	public void valorizza(CsIPs csIPs);

	public void valorizza(CsDIsee isee);

	public boolean valida(Date dataDSU);

	public String getStampa();
	
}
