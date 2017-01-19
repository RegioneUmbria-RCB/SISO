/**
 * 
 */
package it.webred.cs.jsf.bean;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.io.Serializable;

import javax.naming.NamingException;

/**
 * @author alessandro.feriani
 *
 */
public abstract class IterBaseBean extends CsUiCompBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public IterBaseBean() {
	}

	protected AccessTableIterStepSessionBeanRemote getIterSessioBean() throws NamingException {
		AccessTableIterStepSessionBeanRemote sessionBean = (AccessTableIterStepSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
		return sessionBean;
	}
	
	protected AccessTableConfigurazioneSessionBeanRemote getConfigurazioneSessioBean() throws NamingException {
		AccessTableConfigurazioneSessionBeanRemote sessionBean = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		return sessionBean;
	}

	
	protected AccessTableOperatoreSessionBeanRemote getOperatoreSessioBean() throws NamingException {
		AccessTableOperatoreSessionBeanRemote sessionBean = (AccessTableOperatoreSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
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
	
	protected CsOOperatore getOperatore (String username) throws NamingException{
		try {
			AccessTableOperatoreSessionBeanRemote operatoreSessionBean = getOperatoreSessioBean();
			OperatoreDTO dto = new OperatoreDTO();
			fillEnte(dto);
			dto.setUsername(username);
			return operatoreSessionBean.findOperatoreByUsername(dto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Operatore non trovato!", "Errore nella ricerca dell'operatore!");
		}
		return null;
	}
}
