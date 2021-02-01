package it.webred.cs.sample.managedBean;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
	 
	@Named
	public class MyBean  implements ISampleManBean {
	    private String message;
	    public MyBean() {
	        //empty.
	    }
	    
	    @PostConstruct
	    public void init() {
	        message = "A presto!";
	    }
	    @PreDestroy
	    public void destroy() {
	        message = "";
	    }
	    public String sayAPresto() {
	        return message;
	    }
	}
	
