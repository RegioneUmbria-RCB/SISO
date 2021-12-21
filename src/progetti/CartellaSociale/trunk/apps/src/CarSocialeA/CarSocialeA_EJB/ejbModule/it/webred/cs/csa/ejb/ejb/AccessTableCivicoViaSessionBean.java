package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableCivicoViaSessionBeanRemote;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class AccessTableDataCivicoViaSessionBean
 */
@Stateless
public class AccessTableCivicoViaSessionBean extends CarSocialeBaseSessionBean implements AccessTableCivicoViaSessionBeanRemote {

    public AccessTableCivicoViaSessionBean() {
        // TODO Auto-generated constructor stub
    }

}
