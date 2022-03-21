package it.webred.cs.csa.ejb.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTablePersonaCiviciSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;


/**
 * Session Bean implementation class AccessTableDataPersonaCiviciSessionBean
 */
@Stateless
public class AccessTablePersonaCiviciSessionBean extends CarSocialeBaseSessionBean implements AccessTablePersonaCiviciSessionBeanRemote {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/AnagrafeServiceBean")
	protected AnagrafeService anagrafeService;
	

    /**
     * Default constructor. 
     */
    public AccessTablePersonaCiviciSessionBean() {
        // TODO Auto-generated constructor stub
    }
 
    
    public CsAIndirizzo getIndirizzoResidenzaByNomeCiv(BaseDTO dto) {
    	String codFisc = (String)dto.getObj();
    	if (codFisc == null) {
			return null;
		}
		
    	CsAIndirizzo indirizzoRes = null;
    	RicercaCivicoDTO rc = new RicercaCivicoDTO();
    	rc.setEnteId(dto.getEnteId());
		rc.setUserId(dto.getUserId());
		rc.setSessionId(dto.getSessionId());
    	rc.setDescrizioneVia((String) dto.getObj());
    	rc.setCivico((String) dto.getObj2());
    	List<IndirizzoAnagDTO> result = anagrafeService.getIndirizzoResidenzaByNomeCiv(rc);
    	if (result != null && result.size() > 0) 	
    		indirizzoRes = fill(result.get(0));
		return indirizzoRes;
    }

    private CsAIndirizzo fill(IndirizzoAnagDTO obj){
    	CsAIndirizzo indirizzoRes = null;
		if(obj!=null){
	    	indirizzoRes = new CsAIndirizzo();
			CsAAnaIndirizzo indirizzoAna = new CsAAnaIndirizzo();
			indirizzoAna.setCodiceVia(obj.getCodiceVia());
			indirizzoAna.setIndirizzo(obj.getIndirizzo());
			indirizzoAna.setCivicoNumero(obj.getCivicoNumero());
			indirizzoAna.setCivicoAltro(obj.getCivicoAltro());
			indirizzoAna.setProv(obj.getProv());
			indirizzoAna.setComCod(obj.getComCod());
			indirizzoAna.setComDes(obj.getComDes());
			indirizzoAna.setStatoCod(obj.getStatoCod());
			indirizzoAna.setStatoDes(obj.getStatoDes());
			indirizzoRes.setDataInizioApp(obj.getDataInizioApp());
			indirizzoRes.setCsAAnaIndirizzo(indirizzoAna);
		}
		return indirizzoRes;
    	
    }
}
