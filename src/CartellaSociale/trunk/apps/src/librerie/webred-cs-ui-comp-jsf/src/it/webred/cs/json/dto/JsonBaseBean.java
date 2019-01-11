package it.webred.cs.json.dto;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class JsonBaseBean {

	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger("carsociale.log");
	public abstract List<String> checkObbligatorieta();
	public JsonBaseBean autoClone() throws Exception{
		return (JsonBaseBean)BeanUtils.cloneBean(this);
	}
	
}
