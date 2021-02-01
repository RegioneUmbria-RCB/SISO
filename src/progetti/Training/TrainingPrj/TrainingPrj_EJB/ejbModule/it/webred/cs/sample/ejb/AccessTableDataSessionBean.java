package it.webred.cs.sample.ejb;


import java.util.List;

import it.webred.cs.sample.dao.HelloWorldDAO;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class HelloWorldSessionBean
 */
@Stateless
//@Remote(HelloWorldSessionBeanRemote.class)
//@LocalBean
public class AccessTableDataSessionBean implements AccessTableDataSessionBeanRemote {

	@Autowired
	private HelloWorldDAO helloWorldDAO;
	
    /**
     * Default constructor. 
     */
    public AccessTableDataSessionBean() {
        // TODO Auto-generated constructor stub
    }
    

    
    public List<String> getTableData(CeTBaseObject dataIn){
    	return helloWorldDAO.getTableData();
    }
    



	

}
