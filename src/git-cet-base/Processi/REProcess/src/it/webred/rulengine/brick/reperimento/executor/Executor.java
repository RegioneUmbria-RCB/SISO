package it.webred.rulengine.brick.reperimento.executor;

import java.net.URL;
import java.util.HashMap;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;

import it.webred.rulengine.brick.reperimento.executor.logic.BasicLogic;

public class Executor extends BasicLogic {
	private static final Logger log = Logger.getLogger(Executor.class.getName());
	
	
	
	/**
	/**
	 * Metodo che effettua la chiamata al servizio di lancio di un Data Source in Caronte Client
	 * @param id
	 * @param processId
	 * @throws Exception
	 */
	protected void lanciaDataSource(String nomeDataSource, String processId) throws Exception {		
		log.info("Applicativo Controller - avvio lancio data source con processId = " + processId);
		String WSPath = "/services/LanciaDataSource";
		//String appPath = (String)((HashMap)getConfigurazione().get(CONTROLLER_HM_APP)).get(CONTROLLER_HM_APP_KEY_CARONTE);
		String codEnteProcessId = processId.length() > 3 ? processId.substring(0, 4) : null;
		String appPath = (String)((HashMap)getConfigurazione(codEnteProcessId).get(CONTROLLER_HM_APP)).get(CONTROLLER_HM_APP_KEY_CARONTE);
		////////////////////////////////
		String nameWS = appPath + WSPath;
		log.debug("Sto invocando il web service: " + nameWS);
		URL endPointWS = new URL(nameWS);	
		
		// inizializzazione WS
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.removeAllParameters();
		call.setUsername("user1");
		call.setPassword("pippo");
		
		// configurazione parametri WS
		call.setTargetEndpointAddress(endPointWS);
		call.addParameter("nomeDataSource", XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter("processId", XMLType.XSD_STRING, ParameterMode.IN);
		call.setOperationName("lanciaDataSourceWithName");
		call.setReturnType(XMLType.XSD_ANY);
		
		// invocazione WS
		try {
			call.invoke(new Object[] {nomeDataSource, processId});
		}catch (AxisFault af) {
			log.error("Problemi con AXIS!!!!",af  );
			throw af;
		}
	}

/** 
	 * Metodo che effettua la chiamata al servizio di attivazione dell'esecuzione di comandi in RulEngine
	 * @param codProcesso
	 * @param processId
	 * @throws Exception
	 */
	protected void execRECommand(String codProcesso, String processId) throws Exception {
		log.info("Applicativo Controller - avvio esecuzione command con processId = " + processId);
		String WSPath = "/services/CommandLauncher";
		String appPath = (String)((HashMap)getConfigurazione().get(CONTROLLER_HM_APP)).get(CONTROLLER_HM_APP_KEY_RULENGINE);
		String nameWS = appPath + WSPath;
		URL endPointWS = new URL(nameWS);
		// inizializzazione WS
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.removeAllParameters();
		call.setUsername("user1");
		call.setPassword("pippo");	
		// configurazione parametri WS
		call.setTargetEndpointAddress(endPointWS);
		call.addParameter("codCommand", XMLType.XSD_STRING, ParameterMode.IN);
		call.addParameter("processId", XMLType.XSD_STRING, ParameterMode.IN);
		call.setOperationName("lanciaCommand");
		call.setReturnType(XMLType.XSD_ANY);
		// invocazione WS
		try {			
			call.invoke(new Object[] {codProcesso, processId});
		}catch (AxisFault af) {
			log.error("Problemi con AXIS!!!!",af  );
			throw af;
		}
	}
}
