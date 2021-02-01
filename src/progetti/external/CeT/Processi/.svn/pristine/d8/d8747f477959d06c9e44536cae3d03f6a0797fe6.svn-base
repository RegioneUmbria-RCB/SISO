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

	private GeolocalizationServiceFactory() {
	}

	public static GeolocalizationServiceFactory getInstance(Context ctx) {
		return INSTANCE;
	}

	public GeolocalizationService createGeolocalizationService() {

		ParameterService cdm = (ParameterService) ServiceLocator.getInstance().getService("CT_Service",
				"CT_Config_Manager", "ParameterBaseService");

		ParameterSearchCriteria paramSearchCriteria = new ParameterSearchCriteria();

		paramSearchCriteria.setKey("smartwelfare.geolocalization.service");
		paramSearchCriteria.setSection("param.globali");

		AmKeyValueExt amKeyValueExt = cdm.getAmKeyValueExt(paramSearchCriteria);

		String service = amKeyValueExt.getValueConf();

		if (service.equalsIgnoreCase("Google")) {

			paramSearchCriteria = new ParameterSearchCriteria();

			paramSearchCriteria.setKey("smartwelfare.geolocalization.service.google.apiKey");
			paramSearchCriteria.setSection("param.globali");

			amKeyValueExt = cdm.getAmKeyValueExt(paramSearchCriteria);
			
			String apiKey = amKeyValueExt.getValueConf();
			
			log.info("Google api key --> "+apiKey);

			GoogleGeocodeClient ggcc = new GoogleGeocodeClient(apiKey);
			return ggcc;

		} else if (service.equalsIgnoreCase("Roma")) {

			paramSearchCriteria = new ParameterSearchCriteria();

			paramSearchCriteria.setKey("smartwelfare.geolocalization.service.roma.wsusername");
			paramSearchCriteria.setSection("param.globali");

			amKeyValueExt = cdm.getAmKeyValueExt(paramSearchCriteria);

			String wsusername = amKeyValueExt.getValueConf();

			paramSearchCriteria = new ParameterSearchCriteria();

			paramSearchCriteria.setKey("smartwelfare.geolocalization.service.roma.wspassword");
			paramSearchCriteria.setSection("param.globali");

			amKeyValueExt = cdm.getAmKeyValueExt(paramSearchCriteria);
			String wspassword = amKeyValueExt.getValueConf();

			RomaGeocodeClient rgcc = new RomaGeocodeClient(wsusername, wspassword);
			return rgcc;

		} else {

			return null;
		}

	}

}
