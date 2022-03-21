package it.webred.cs.sociosan.web.rest.services;

import it.umbriadigitale.rest.service.BaseService;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.webred.cs.sociosan.web.rest.dto.EchoRequest;
import it.webred.cs.sociosan.web.rest.dto.EchoResponse;




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






