package it.webred.trainingprj;

import it.webred.cs.sample.ejb.HelloWorldManSessionBeanRemote;
import it.webred.ejb.utility.ClientUtility;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.NamingException;

@ManagedBean
@RequestScoped
public class HomeBean {

	private String messaggio;
	private String risposta = "(inserire messaggio di saluto)";
	
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public String getRisposta() {
		return risposta;
	}
	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}	
	public void inviaMessaggio() {
		try {

			HelloWorldManSessionBeanRemote bean = (HelloWorldManSessionBeanRemote) ClientUtility.getEjbInterface("TrainingPrj", "TrainingPrj_EJB", "HelloWorldManSessionBean");
			//HelloWorldManSessionBeanRemote bean =  ClientUtility.getEJBInterfaceForRemoteClient(HelloWorldManSessionBeanRemote.class, "TrainingPrj", "TrainingPrj_EJB", "HelloWorldManSessionBean", "");
			risposta = bean.salutareSpringAOP(messaggio);			
		} catch (NamingException e) {
			e.printStackTrace();
			risposta = "ERRORE NELL'INVIO DEL MESSAGGIO: " + messaggio;
		}		
	}
	

	
}
