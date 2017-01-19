package it.umbriadigitale.rest.base;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.beanutils.BeanUtils;


public class ErrorMessage {
	
	/** contains the same HTTP Status code returned by the server */
	int status;
	
	/** application specific error code */
	int appSpecificCode;
	
	/** message describing the error*/
	String message;
		
	/** link point to page where the error message is documented */
	String link;
	
	/** extra information that might useful for developers */
	String developerMessage;	
	
	Timestamp time;
		

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public ErrorMessage(AppException ex){
		try {
			BeanUtils.copyProperties(this, ex);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	
	

	public int getAppSpecificCode() {
		return appSpecificCode;
	}

	public void setAppSpecificCode(int appSpecificCode) {
		this.appSpecificCode = appSpecificCode;
	}

	public ErrorMessage() {}
}
