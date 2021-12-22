package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsIPs;
import it.webred.cs.sociosan.ejb.dto.isee.DatiIsee;

import java.util.Date;
import java.util.List;

public interface IProtocolloDsu {

	public List<String> getListaAnniDsu();

	public DatiIsee cbxAnnoDsuListener();

	public void resetProtDSU();

	public void valorizza(CsIPs csIPs);

	public void valorizza(CsDIsee isee);

	public boolean valida(Date dataDSU);

	public String getStampa();

	public boolean isRenderMessage();

	public DatiIsee trovaAttestazione();
	
}
