package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableMediciSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.MedicoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.data.model.CsVMedico;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class AccessTableDataMediciSessionBean
 */
@Stateless
public class AccessTableMediciSessionBean extends CarSocialeBaseSessionBean implements AccessTableMediciSessionBeanRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private MedicoDAO medicoDAO;
	
	/**
     * Default constructor. 
     */
    public AccessTableMediciSessionBean() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public List<CsCMedico> getMedici(CeTBaseObject cet) {
    	return medicoDAO.getMedici();
    }
    
    @Override
    public List<CsCMedico> searchMedici(BaseDTO cet) {
    	return medicoDAO.searchMediciByDescrizione((String)cet.getObj(), (Integer)cet.getObj2());
    }
    
    @Override
    public CsCMedico getMedicoByCodReg(BaseDTO dto) {
    	return medicoDAO.getMedicoByCodReg(dto);
    }
    
    @Override
    public CsCMedico findMedicoById(BaseDTO dto) throws Exception {
    	return medicoDAO.findMedicoById((Long) dto.getObj());
    }

	@Override
	public CsCMedico addNewMedicoFromAnagReg(BaseDTO dto) {
		return medicoDAO.addNewMedicoFromAnagReg((CsCMedico)dto.getObj());
	}
	
	@Override
	public CsVMedico getMedicoRegioneByCodiceRegionale(BaseDTO dto) {
		return medicoDAO.getMedicoRegioneByCodiceRegionale(dto);
	}
	
	 public CsCMedico findMedicoByDescrizione(BaseDTO dto){
		 String desc = (String)dto.getObj();
		 List<CsCMedico> lstMedici =  medicoDAO.findMediciByDescrizione(desc);
		 if(lstMedici.size()==1)
			 return lstMedici.get(0);
		 else
			 return null;
	 }

}
