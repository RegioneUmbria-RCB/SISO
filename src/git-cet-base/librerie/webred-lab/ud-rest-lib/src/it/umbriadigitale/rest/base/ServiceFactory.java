package it.umbriadigitale.rest.base;


import it.webred.utils.GenericTuples;
import it.webred.utils.GenericTuples.T2;
import it.webred.utils.GenericTuples.T3;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.ws.rs.POST;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.umbriadigitale.rest.dto.BaseBody;
import it.umbriadigitale.rest.dto.BaseRequest;
import it.umbriadigitale.rest.dto.BaseResponse;
import it.umbriadigitale.rest.service.BaseService;
import it.umbriadigitale.rest.service.BaseServicePost;


public class ServiceFactory {
	protected static Logger logger = Logger.getLogger("ud-rest-lib");

	public static BaseServicePost<BaseRequest, BaseResponse, String>  build(String service, String req, String body) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		
		Class serviceClass = null;
		T3<Class, Class,Class> classes = null;
		try {
			serviceClass = getServiceClass(service);
			classes = getParameterClassT3(serviceClass);
		} catch (Exception e) {
			logger.error("Impossibile reperire la classe di implementazione/parametri del servizio " +  service, e);
			throw e;
		}
		

		Class reqClass = classes.firstObj;
		Class respClass = classes.firstObj; //?? non è il secondo?
		Class bodyClass = classes.thirdObj;
		
		Constructor costruttore=null;
		try {
			costruttore = serviceClass.getConstructor(reqClass, bodyClass);
		} catch (Exception e) {
			logger.error("Impossibile reperire il costruttore del servizio " +  service, e);
			throw e;
		}
		
		Object reqObject = reqClass.newInstance();
		reqObject = objectMapper.readValue(req, reqClass);
		
		/*Object bodyObject = bodyClass.newInstance();
		bodyObject = objectMapper.readValue(body, bodyClass);
		*/
		
		BaseServicePost<BaseRequest, BaseResponse, String> servizio = null;
		try {
			servizio = (BaseServicePost<BaseRequest, BaseResponse, String>) costruttore.newInstance(reqObject, body);
		} catch (Exception e) {
			logger.error("Impossibile costruire una istanza del servizio " +  service, e);
			throw e;
		}

		return servizio;
		
	}
	
	public static BaseService<BaseRequest, BaseResponse>  build(String service, String req) throws Exception {
			ObjectMapper objectMapper = new ObjectMapper();
			
			Class serviceClass = null;
			T2<Class, Class> classes = null;
			try {
				serviceClass = getServiceClass(service);
				classes = getParameterClassT2(serviceClass);
			} catch (Exception e) {
				logger.error("Impossibile reperire la classe di implementazione/parametri del servizio " +  service, e);
				throw e;
			}
			

			
			Class reqClass = classes.firstObj;
			Class respClass = classes.firstObj; //?? non è il secondo?
			
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
	
	
	private static  GenericTuples.T3<Class, Class, Class> getParameterClassT3(Class serviceClass) throws Exception {
		
		

		//java.lang.reflect.Method m = c.getMethod("execute");
		Type genericSuperClass = serviceClass.getGenericSuperclass();
		ParameterizedType parametrizedType = null;
		 if ((genericSuperClass instanceof ParameterizedType)) {
	            parametrizedType = (ParameterizedType) genericSuperClass;
	        } else {
	            genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
	        }
		 	T3<Class, Class, Class> ret = null;
			try {
				ParameterizedType pt = (ParameterizedType) genericSuperClass;
				Type[] type = pt.getActualTypeArguments();
				Class req = ((Class<?>)type[0]).newInstance().getClass();
				Class resp = ((Class<?>) type[1]).newInstance().getClass();
				Class body = (Class<?>) type[2]!=null ? ((Class<?>) type[2]).newInstance().getClass() : null;
				ret = new GenericTuples.T3<Class, Class,Class>(req,resp,body);
				
			} catch (Exception e) {
					logger.error(e);
					throw new Exception("Si accettano solo request / response con costruttori senza parametri",e);
			}
		
		return ret ;
		
	}
	
	private static  GenericTuples.T2<Class, Class> getParameterClassT2(Class serviceClass) throws Exception {
		
		

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
				Type[] type = pt.getActualTypeArguments();
				Class req = ((Class<?>)type[0]).newInstance().getClass();
				Class resp = ((Class<?>) type[1]).newInstance().getClass();
				ret = new GenericTuples.T2<Class, Class>(req,resp);
				
			} catch (Exception e) {
					logger.error(e);
					throw new Exception("Si accettano solo request / response con costruttori senza parametri",e);
			}
		
		return ret ;
		
	}
	
}
