package it.umbriadigitale.rest.base;




import it.umbriadigitale.rest.dto.BaseRequest;
import it.umbriadigitale.rest.dto.BaseResponse;
import it.umbriadigitale.rest.service.BaseService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;



@Path("/main/execute")
public class MainWs  extends BaseWs {


	
    public MainWs(String pack) {
		super(pack);
		// TODO Auto-generated constructor stub
	}

    
   

    

    
}