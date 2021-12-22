package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableMediciSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.MedicoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.data.model.CsVMedico;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.siso.esb.Medico;
import it.webred.siso.esb.client.MedicoClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class AccessTableDataMediciSessionBean
 */
@Stateless
public class AccessTableMediciSessionBean extends CarSocialeBaseSessionBean implements AccessTableMediciSessionBeanRemote {


	private static final long serialVersionUID = 1L;
	@Autowired
	private MedicoDAO medicoDAO;
	
	
	private URL getMediciWebServiceWSDLLocation() {
		String urlString = getGlobalParameter(DataModelCostanti.AmParameterKey.WS_MEDICI_URL);
		return stringToUrl(urlString);
	}
	
    @Override
    public List<KeyValueDTO> getMedici(CeTBaseObject cet) {
    	List<KeyValueDTO> lstItems = new ArrayList<KeyValueDTO>();
    	List<CsCMedico> beanLstMedici = medicoDAO.getMedici();
    	if (beanLstMedici != null) {
			for (CsCMedico medico : beanLstMedici)
				lstItems.add(new KeyValueDTO(medico.getId(), medico.getDenominazione()));
		}
    	return lstItems;
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

	@Override
	public CsCMedico addNewMedicoUmbria(BaseDTO dto) {
		
		String codRegMedico = (String)dto.getObj();
		CsCMedico medico = null;
		
		if(codRegMedico!=null && !codRegMedico.isEmpty()){
			URL url = getMediciWebServiceWSDLLocation();
			MedicoClient mc = null;
			Medico medicoRegione = null;
			if (url != null) {
				mc = new MedicoClient(url);
				medicoRegione = mc.getMedicoByCodiceRegionale(codRegMedico);
			
				if (medicoRegione != null) {
					dto.setObj2(medicoRegione.getCognome());
					dto.setObj3(medicoRegione.getNome());
					medico = this.addNewMedico(dto);
				}
			}
		}
		return medico;
	}

	@Override
	public CsCMedico addNewMedico(BaseDTO dto) {
		
		CsCMedico medico = null;
		
		String codice = (String)dto.getObj();
		String cognome = (String)dto.getObj2();
		String nome = (String) dto.getObj3();
		
		if (!StringUtils.isBlank(codice) && !StringUtils.isBlank(cognome) && !StringUtils.isBlank(nome)) {
			CsCMedico newMedico = new CsCMedico();
			newMedico.setAbilitato("1");
			newMedico.setCognome(cognome);
			newMedico.setNome(nome);
			newMedico.setCodiceRegionale(codice);
			medico = medicoDAO.addNewMedicoFromAnagReg(newMedico);
		}
		return medico;
	}
}
