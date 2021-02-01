package it.webred.cs.sample.ejb;

import java.util.List;

import it.webred.cs.sample.dao.ComuneDAO;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
//@Remote(HelloWorldSessionBeanRemote.class)
//@LocalBean
public class AccessTableDataComuniSessionBean implements AccessTableDataComuniSessionBeanRemote {

	@Autowired
	private ComuneDAO comuneDAO;
	
    /**
     * Default constructor. 
     */
    public AccessTableDataComuniSessionBean() {
        // TODO Auto-generated constructor stub
    }

    public List<String> getComuni(CeTBaseObject dataIn){
    	return comuneDAO.getTableData();
    }

}

