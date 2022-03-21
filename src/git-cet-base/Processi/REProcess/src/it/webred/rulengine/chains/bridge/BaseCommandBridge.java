package it.webred.rulengine.chains.bridge;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseCommandBridge {
	
	public static final String BCBPAR = "bcbpar_";
	
	/*
	 * Oggetto Map in cui vengono salvati i parametri di tipi complessi
	 * provenienti dal contesto jelly
	 */
	protected static Map<String,Object> _globalBridgeParams;
	
	static {
		_globalBridgeParams = new HashMap<String, Object>();
	}
	
}
