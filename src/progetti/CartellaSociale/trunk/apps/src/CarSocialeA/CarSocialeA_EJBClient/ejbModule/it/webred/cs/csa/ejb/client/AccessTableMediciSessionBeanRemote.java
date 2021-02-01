package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.data.model.CsVMedico;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableMediciSessionBeanRemote {
	
	public List<CsCMedico> getMedici(CeTBaseObject cet);
	
	public List<CsCMedico> searchMedici(BaseDTO cet);
	
    public CsCMedico findMedicoById(BaseDTO dto) throws Exception;
    
    public CsCMedico getMedicoByCodReg(BaseDTO dto); 
    
	public CsVMedico getMedicoRegioneByCodiceRegionale(BaseDTO dto);
	
	public CsCMedico findMedicoByDescrizione(BaseDTO dto);
	
	public CsCMedico addNewMedicoUmbria(BaseDTO dto);
	
	public CsCMedico addNewMedico(BaseDTO dto);
}
