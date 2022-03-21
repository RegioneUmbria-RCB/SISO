package it.webred.rulengine.brick.elab.cartellaSociale;

import org.apache.log4j.Logger;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;

public class GeolocalizationServiceFactory {

	private static final GeolocalizationServiceFactory INSTANCE = new GeolocalizationServiceFactory();
	
	private static final Logger log = Logger.getLogger(GoogleGeocodeClient.class.getName());
	
	private static final String SERVICE = "smartwelfare.geolocalization.service";
	private static final String GOOGLE_API_KEY = "smartwelfare.geolocalization.service.google.apiKey";
	
	private static final String ROMA_WS_USERNAME = "smartwelfare.geolocalization.service.roma.wsusername";
	private static final String ROMA_WS_PWD = "smartwelfare.geolocalization.service.roma.wspassword";
			
	private GeolocalizationServiceFactory() {
	}

	public static GeolocalizationServiceFactory getInstance(Context ctx) {
		return INSTANCE;
	}
	
	
	private String getParameter(String key){
		ParameterService cdm = (ParameterService) ServiceLocator.getInstance().getService("CT_Service",
				"CT_Config_Manager", "ParameterBaseService");

		ParameterSearchCriteria paramSearchCriteria = new ParameterSearchCriteria();

		paramSearchCriteria.setKey("smartwelfare.geolocalization.service");
		paramSearchCriteria.setSection("param.globali");

		AmKeyValueExt amKeyValueExt = cdm.getAmKeyValueExt(paramSearchCriteria);
		String val = amKeyValueExt!=null ? amKeyValueExt.getValueConf() : null;
		return val;
	}

	public GeolocalizationService createGeolocalizationService() {
		String service = this.getParameter(SERVICE);

		if ("Google".equalsIgnoreCase(service)) {
			
			String apiKey = this.getParameter(GOOGLE_API_KEY);
			
			log.info("Google api key --> "+apiKey);

			GoogleGeocodeClient ggcc = new GoogleGeocodeClient(apiKey);
			return ggcc;

		} else if ("Roma".equalsIgnoreCase(service)) {

			String wsusername = this.getParameter(ROMA_WS_USERNAME);
			String wspassword = this.getParameter(ROMA_WS_PWD);

			RomaGeocodeClient rgcc = new RomaGeocodeClient(wsusername, wspassword);
			return rgcc;

		} else {

			return null;
		}

	}

}
