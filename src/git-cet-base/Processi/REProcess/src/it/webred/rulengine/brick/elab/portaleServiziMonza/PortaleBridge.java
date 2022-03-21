package it.webred.rulengine.brick.elab.portaleServiziMonza;

import it.webred.ct.config.model.AmInstance;
import it.webred.rulengine.brick.bean.CommandAck;

public interface PortaleBridge {

	public static final boolean USE_PORTALE_BRIDGE = true;
	
	public String getBridgeUrlPattern();
	
	public CommandAck goToPortaleByBridge(AmInstance ist, String dir, String enteID) throws Exception;
	
}
