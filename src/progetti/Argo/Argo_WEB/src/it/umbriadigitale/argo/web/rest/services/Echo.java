package it.umbriadigitale.argo.web.rest.services;

import it.umbriadigitale.argo.web.rest.dto.EchoRequest;
import it.umbriadigitale.argo.web.rest.dto.EchoResponse;
import it.umbriadigitale.rest.service.BaseService;
import it.umbriadigitale.rest.service.BaseServiceException;





public class Echo extends BaseService<EchoRequest, EchoResponse> {




	public Echo(EchoRequest req) {
		super(req);
	}




	@Override
	public EchoResponse execute() {
		// TODO Auto-generated method stub
		EchoResponse resp =  new EchoResponse();
		resp.setOutput(req.getInput());
		return resp;
	}




	@Override
	public TipoServizioRest getTipoServizioRest() throws BaseServiceException {
		return TipoServizioRest.GET;
	}

}






