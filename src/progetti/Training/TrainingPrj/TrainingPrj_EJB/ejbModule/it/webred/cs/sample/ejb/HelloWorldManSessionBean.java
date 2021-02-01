package it.webred.cs.sample.ejb;

import it.webred.cs.sample.managedBean.AnotherBean;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Session Bean implementation class HelloWorldManSessionBean
 */
@Stateless
//@LocalBean
@Named
public class HelloWorldManSessionBean implements HelloWorldManSessionBeanRemote {
       
    /**
     * @see AcceTableDataSessionBean#HelloWorldSessionBean()
     */
    public HelloWorldManSessionBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    private AnotherBean anotherBean;
	
    @Inject
    private void setAnotherBean(AnotherBean anotherBean) {
    	this.anotherBean = anotherBean;
    }
    
	public String salutareSpringAOP(String qualcosAltro) {
		
		if ("arrivederci".equalsIgnoreCase(qualcosAltro) )
			return anotherBean.sayAPresto();
		else {
			System.out.println("ripeto : " + qualcosAltro);
			return qualcosAltro;
		}
	}
	
	
	
	
	
	private @Inject it.webred.cs.sample.aop.aspect.bean.SimpleBean simpleBean;

    public void salutareAspectJ(){
    	System.out.println("------------- ATTIVARE I SALUTI DEL BEAN CON PROXY -----------------");
    	simpleBean.salutareProxyBean();       
    	System.out.println("------------- ATTIVARE I SALUTI DEL BEAN CON SENZA PROXY -----------------");
    	simpleBean.salutareBean();       
    }


    
    

}
