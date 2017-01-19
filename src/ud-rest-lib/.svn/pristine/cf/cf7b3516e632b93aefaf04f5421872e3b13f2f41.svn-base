package it.umbriadigitale.rest.base;


import it.webred.utils.GenericTuples;
import it.webred.utils.GenericTuples.T2;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.umbriadigitale.rest.dto.BaseRequest;
import it.umbriadigitale.rest.dto.BaseResponse;
import it.umbriadigitale.rest.service.BaseService;


public class ServiceFactory {
	protected static Logger logger = Logger.getLogger("ud-rest-lib");

	

	public static BaseService<BaseRequest, BaseResponse>  build(String service, String req) throws Exception {
			ObjectMapper objectMapper = new ObjectMapper();
			
			Class serviceClass = null;
			T2<Class, Class> classes = null;
			try {
				serviceClass = getServiceClass(service);
				classes = getParameterClass(serviceClass);
			} catch (Exception e) {
				logger.error("Impossibile reperire la classe di implementazione/parametri del servizio " +  service, e);
				throw e;
			}
			

			
			Class reqClass = classes.firstObj;
			Class respClass = classes.firstObj;
			
			Constructor costruttore=null;
			try {
				costruttore = serviceClass.getConstructor(reqClass);
			} catch (Exception e) {
				logger.error("Impossibile reperire il costruttore del servizio " +  service, e);
				throw e;
			}
			
			Object reqObject = reqClass.newInstance();
			reqObject = objectMapper.readValue(req, reqClass);
			
		
			
			BaseService<BaseRequest, BaseResponse> servizio = null;
			try {
				servizio = (BaseService<BaseRequest, BaseResponse>) costruttore.newInstance(reqObject);
			} catch (Exception e) {
				logger.error("Impossibile costruire una istanza del servizio " +  service, e);
				throw e;
			}

			return servizio;
			

		
	}
	
	private static Class<?> getServiceClass(String service) throws ClassNotFoundException {
		try {
			return  Class.forName(service);
		} catch (ClassNotFoundException e) {
			throw e;
		}

		
	}
	
	
	private static  GenericTuples.T2<Class, Class> getParameterClass(Class serviceClass) throws AppException {
		
		

		//java.lang.reflect.Method m = c.getMethod("execute");
		Type genericSuperClass = serviceClass.getGenericSuperclass();
		ParameterizedType parametrizedType = null;
		 if ((genericSuperClass instanceof ParameterizedType)) {
	            parametrizedType = (ParameterizedType) genericSuperClass;
	        } else {
	            genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
	        }
		 	T2<Class, Class> ret = null;
			try {
				ParameterizedType pt = (ParameterizedType) genericSuperClass;
				Class req = ((Class<?>) pt.getActualTypeArguments()[0]).newInstance().getClass();
				Class resp = ((Class<?>) pt.getActualTypeArguments()[1]).newInstance().getClass();
				ret = new GenericTuples.T2<Class, Class>(req,resp);
				
			} catch (Exception e) {
					throw new AppException(e, "Si accettano solo request / response con costruttori senza parametri");
			}
		
		return ret ;
		
	}
	
}
