/**
 * 
 */
package it.webred.cs.jsf.bean;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.io.Serializable;

import javax.naming.NamingException;

public abstract class IterBaseBean extends CsUiCompBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public IterBaseBean() {
	}

	protected AccessTableIterStepSessionBeanRemote getIterSessioBean() throws NamingException {
		AccessTableIterStepSessionBeanRemote sessionBean = (AccessTableIterStepSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
		return sessionBean;
	}

	protected AccessTableCasoSessionBeanRemote getCasoSessioBean() throws NamingException {
		AccessTableCasoSessionBeanRemote sessionBean = (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
		return sessionBean;
	}

	protected AccessTableAlertSessionBeanRemote getAlertSessioBean() throws NamingException {
		AccessTableAlertSessionBeanRemote sessionBean = (AccessTableAlertSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");
		return sessionBean;
	}
	
	protected AccessTableCatSocialeSessionBeanRemote getCatSocSessioBean() throws NamingException {
		AccessTableCatSocialeSessionBeanRemote catSocBean = (AccessTableCatSocialeSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCatSocialeSessionBean");
		return catSocBean;
	}
}
