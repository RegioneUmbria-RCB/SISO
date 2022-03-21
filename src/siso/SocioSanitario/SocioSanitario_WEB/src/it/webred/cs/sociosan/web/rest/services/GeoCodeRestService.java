package it.webred.cs.sociosan.web.rest.services;

import it.umbriadigitale.geocode.GeoFactory;
import it.umbriadigitale.geocode.GeocodeDTO;
import it.umbriadigitale.rest.service.BaseAuthService;
import it.umbriadigitale.rest.service.BaseService;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.umbriadigitale.rest.service.IAuthManager;
import it.umbriadigitale.rest.service.BaseService.TipoServizioRest;
import it.webred.cs.sociosan.web.rest.dto.EchoRequest;
import it.webred.cs.sociosan.web.rest.dto.EchoResponse;
import it.webred.cs.sociosan.web.rest.dto.GeoCodeRequest;
import it.webred.cs.sociosan.web.rest.dto.GeoCodeResponse;
import it.webred.cs.sociosan.web.rest.dto.SocioSanAuthRequest;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;














import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class GeoCodeRestService extends BaseAuthService<GeoCodeRequest, GeoCodeResponse> {

	public GeoCodeRestService(GeoCodeRequest req) {
		super(req);
	}


	
	public GeoCodeResponse executeAuthService() {
		// TODO Auto-generated method stub
		GeoCodeResponse resp =  new GeoCodeResponse();
		
		GeoFactory f = new GeoFactory();
		
		GeocodeDTO coord = f.buildCoordinates(req.getInput());
		
		
		
		
		resp.setOutput(coord);
		
		return resp;
	}


	@Override
	public IAuthManager getAuthManager() {
		return new SocioSanServiceAuthManager();
	}




	@Override
	public it.umbriadigitale.rest.service.BaseService.TipoServizioRest getTipoServizioRest()
			throws BaseServiceException {
		return TipoServizioRest.GET;
	}

	

}






