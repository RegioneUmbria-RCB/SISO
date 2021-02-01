package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableCivicoViaSessionBeanRemote;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class AccessTableDataCivicoViaSessionBean
 */
@Stateless
public class AccessTableCivicoViaSessionBean extends CarSocialeBaseSessionBean implements AccessTableCivicoViaSessionBeanRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/AnagrafeServiceBean")
	protected AnagrafeService anagrafeService;
	
	/**
     * Default constructor. 
     */
    public AccessTableCivicoViaSessionBean() {
        // TODO Auto-generated constructor stub
    }

}
