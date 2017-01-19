package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTablePersonaCiviciSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;


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
    
    public CsAIndirizzo getIndirizzoResidenzaByCodFisc(BaseDTO dto) {
    	String codFisc = (String)dto.getObj();
    	if (codFisc == null) {
			return null;
		}
		CsAIndirizzo indirizzoRes = null;
		CsAAnaIndirizzo indirizzoAna = null;
    	RicercaSoggettoAnagrafeDTO rs = new RicercaSoggettoAnagrafeDTO();
    	rs.setEnteId(dto.getEnteId());
		rs.setUserId(dto.getUserId());
		rs.setSessionId(dto.getSessionId());
    	rs.setCodFis(codFisc);
    	List<Object[]> result = anagrafeService.getIndirizzoResidenzaByCodFisc(rs);
    	if (result != null && result.size() > 0) {
			Object[] obj = result.get(0);
			indirizzoRes = new CsAIndirizzo();
			indirizzoAna = new CsAAnaIndirizzo();
			int idx = -1;
			indirizzoAna.setCodiceVia((String)obj[++idx]);
			String indirizzo = (String)obj[++idx];
			indirizzoAna.setIndirizzo(indirizzo == null ? null : indirizzo.trim());
			indirizzoAna.setCivicoNumero((String)obj[++idx]);
			indirizzoAna.setCivicoAltro((String)obj[++idx]);
			indirizzoAna.setProv((String)obj[++idx]);
			indirizzoAna.setComCod((String)obj[++idx]);
			indirizzoAna.setComDes((String)obj[++idx]);
			indirizzoAna.setStatoCod((String)obj[++idx]);
			indirizzoAna.setStatoDes((String)obj[++idx]);
			indirizzoRes.setDataInizioApp((Date)obj[++idx]);
			indirizzoRes.setCsAAnaIndirizzo(indirizzoAna);
		}
		return indirizzoRes;
    }
    
    public CsAIndirizzo getIndirizzoResidenzaByNomeCiv(BaseDTO dto) {
    	String codFisc = (String)dto.getObj();
    	if (codFisc == null) {
			return null;
		}
		CsAIndirizzo indirizzoRes = null;
		CsAAnaIndirizzo indirizzoAna = null;
    	RicercaCivicoDTO rc = new RicercaCivicoDTO();
    	rc.setEnteId(dto.getEnteId());
		rc.setUserId(dto.getUserId());
		rc.setSessionId(dto.getSessionId());
    	rc.setDescrizioneVia((String) dto.getObj());
    	rc.setCivico((String) dto.getObj2());
    	List<Object[]> result = anagrafeService.getIndirizzoResidenzaByNomeCiv(rc);
    	if (result != null && result.size() > 0) {
			Object[] obj = result.get(0);
			indirizzoRes = new CsAIndirizzo();
			indirizzoAna = new CsAAnaIndirizzo();
			int idx = -1;
			indirizzoAna.setCodiceVia((String)obj[++idx]);
			String indirizzo = (String)obj[++idx];
			indirizzoAna.setIndirizzo(indirizzo == null ? null : indirizzo.trim());
			indirizzoAna.setCivicoNumero((String)obj[++idx]);
			indirizzoAna.setCivicoAltro((String)obj[++idx]);
			indirizzoAna.setProv((String)obj[++idx]);
			indirizzoAna.setComCod((String)obj[++idx]);
			indirizzoAna.setComDes((String)obj[++idx]);
			indirizzoAna.setStatoCod((String)obj[++idx]);
			indirizzoAna.setStatoDes((String)obj[++idx]);
			indirizzoRes.setDataInizioApp((Date)obj[++idx]);
			indirizzoRes.setCsAAnaIndirizzo(indirizzoAna);
		}
		return indirizzoRes;
    }

}
