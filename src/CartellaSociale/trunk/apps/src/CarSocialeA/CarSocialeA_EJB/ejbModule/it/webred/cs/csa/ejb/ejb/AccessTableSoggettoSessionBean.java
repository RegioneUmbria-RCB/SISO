package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.CasoSearchCriteria;
import it.webred.cs.csa.ejb.dto.ContatoreCasiDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.PaginationDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoCategoriaSocLAZY;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsASoggettoMedico;
import it.webred.cs.data.model.CsASoggettoStatoCivile;
import it.webred.cs.data.model.CsASoggettoStatus;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.data.model.CsOSettore;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableSoggettoSessionBean extends CarSocialeBaseSessionBean implements AccessTableSoggettoSessionBeanRemote {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SoggettoDAO soggettoDAO;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDAO;
	
	@EJB 
	private AccessTableIterStepSessionBeanRemote iterSessionBean;

	@Override
	public CsASoggettoLAZY getSoggettoById(BaseDTO dto) {
		return soggettoDAO.getSoggettoById((Long) dto.getObj());
	}
    
	@Override
	public List<CsASoggettoLAZY> getSoggettiByCF(BaseDTO dto) {
		return soggettoDAO.getSoggettiByCF((String) dto.getObj());
	}
	
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public CsASoggettoLAZY getSoggettoByCF(BaseDTO dto) {
		return soggettoDAO.getSoggettoByCF((String) dto.getObj());
	}
	
	public Boolean esisteSchedaSoggettoByCF(BaseDTO dto) {
		Boolean x = null;
		
		CsASoggettoLAZY s = soggettoDAO.getSoggettoByCF((String) dto.getObj());
		if(s!=null) x = true;
		else x = false;

		return x;
	}
	
    @Override
	public CsOSettore getLastItStepSettoreSoggetto(BaseDTO dto) throws Exception{
		
    	CsASoggettoLAZY s = soggettoDAO.getSoggettoByCF((String) dto.getObj());
    	
    	if(s!=null){
			IterDTO idto = new IterDTO();
			idto.setSessionId(dto.getSessionId());
			idto.setEnteId(dto.getEnteId());
			idto.setUserId(dto.getUserId());
			idto.setIdCaso(s.getCsACaso().getId());
			
			CsIterStepByCasoDTO it = iterSessionBean.getLastIterStepByCasoDTO(idto);
			if(it!=null && it.getCsItStep()!=null){
				if(it.getCsItStep().getCsOSettore1()!=null && it.getCsItStep().getCsOSettore1().getId()>0)
				  return configurazioneDAO.getSettoreById(it.getCsItStep().getCsOSettore1().getId());
			}
    	}
    	return null;
	}
    
	@Override
	public List<CsASoggettoLAZY> getSoggettiByDenominazione(BaseDTO dto) {
		return soggettoDAO.getSoggettiByDenominazione((String) dto.getObj());
	}
	
	@Override
	public CsASoggettoLAZY saveSoggetto(BaseDTO dto) {
		CsASoggettoLAZY cs = (CsASoggettoLAZY) dto.getObj();
		CsAAnagrafica csAna = soggettoDAO.saveAnagrafica(cs.getCsAAnagrafica());
		cs.setAnagraficaId(csAna.getId());
		soggettoDAO.saveSoggetto(cs);
		return cs;
	}
	
	@Override
	public void updateSoggetto(BaseDTO dto) {
		CsASoggettoLAZY cs = (CsASoggettoLAZY) dto.getObj();
		soggettoDAO.saveAnagrafica(cs.getCsAAnagrafica());
		soggettoDAO.updateSoggetto(cs);
	}

	@Override
	public List<CsASoggettoCategoriaSoc> getSoggettoCategorieBySoggetto(BaseDTO dto) {
		return soggettoDAO.getSoggCategorieBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public List<CsASoggettoCategoriaSocLAZY> getSoggettoCategorieAttualiBySoggetto(BaseDTO dto) {
		return soggettoDAO.getSoggCategorieAttualiLAZYBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public void saveSoggettoCategoria(BaseDTO dto) {
		soggettoDAO.saveSoggettoCategoria((CsASoggettoCategoriaSoc) dto.getObj());
	}
	
	@Override
	public boolean eliminaSoggettoCategorieBySoggetto(BaseDTO dto) {
		return soggettoDAO.eliminaSoggettoCategorieBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public List<CsCCategoriaSocialeBASIC> getCatSocAttualiBySoggetto(BaseDTO dto) {
		List<CsASoggettoCategoriaSocLAZY> lst = soggettoDAO.getSoggCategorieAttualiLAZYBySoggetto((Long) dto.getObj());
		List<CsCCategoriaSocialeBASIC> attuale = new ArrayList<CsCCategoriaSocialeBASIC>();
		for(CsASoggettoCategoriaSocLAZY cs : lst)
			attuale.add(cs.getCsCCategoriaSociale());
		
		return attuale;
	}

	@Override
	public List<DatiCasoListaDTO> getCasiSoggettoLAZY(PaginationDTO dto) throws Throwable {
		CasoSearchCriteria criteria = (CasoSearchCriteria) dto.getObj();
		criteria.setUsername(dto.getUserId());
		return soggettoDAO.getCasiSoggetto(dto.getFirst(), dto.getPageSize(), criteria);
	}
	
	@Override
	public Integer getCasiSoggettoCount(PaginationDTO dto) {
		CasoSearchCriteria criteria = (CasoSearchCriteria) dto.getObj();
		criteria.setUsername(dto.getUserId());
		return soggettoDAO.getCasiSoggettoCount((CasoSearchCriteria) dto.getObj());
	}
	
	@Override
	public ContatoreCasiDTO getCasiPerCategoriaCount(PaginationDTO dto) {
		CasoSearchCriteria criteria = (CasoSearchCriteria) dto.getObj();
		criteria.setUsername(dto.getUserId());
		return soggettoDAO.getCasiPerCategoriaCount((CasoSearchCriteria) dto.getObj());
	}
	
	@Override
	public List<CsASoggettoMedico> getSoggettoMedicoBySoggetto(BaseDTO dto) {
		return soggettoDAO.getSoggettoMedicoBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public void saveSoggettoMedico(BaseDTO dto) {
		soggettoDAO.saveSoggettoMedico((CsASoggettoMedico) dto.getObj());
	}
	
	@Override
	public void updateSoggettoMedico(BaseDTO dto) {
		soggettoDAO.updateSoggettoMedico((CsASoggettoMedico) dto.getObj());
	}
	
	@Override
	public void eliminaSoggettoMedicoBySoggetto(BaseDTO dto) {
		soggettoDAO.eliminaSoggettoMedicoBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public List<CsASoggettoStatoCivile> getSoggettoStatoCivileBySoggetto(BaseDTO dto) {
		return soggettoDAO.getSoggettoStatoCivileBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public void saveSoggettoStatoCivile(BaseDTO dto) {
		soggettoDAO.saveSoggettoStatoCivile((CsASoggettoStatoCivile) dto.getObj());
	}
	
	@Override
	public void updateSoggettoStatoCivile(BaseDTO dto) {
		soggettoDAO.updateSoggettoStatoCivile((CsASoggettoStatoCivile) dto.getObj());
	}
	
	@Override
	public void eliminaSoggettoStatoCivileBySoggetto(BaseDTO dto) {
		soggettoDAO.eliminaSoggettoStatoCivileBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public List<CsASoggettoStatus> getSoggettoStatusBySoggetto(BaseDTO dto) {
		return soggettoDAO.getSoggettoStatusBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public void saveSoggettoStatus(BaseDTO dto) {
		soggettoDAO.saveSoggettoStatus((CsASoggettoStatus) dto.getObj());
	}
	
	@Override
	public void updateSoggettoStatus(BaseDTO dto) {
		soggettoDAO.updateSoggettoStatus((CsASoggettoStatus) dto.getObj());
	}
	
	@Override
	public void eliminaSoggettoStatusBySoggetto(BaseDTO dto) {
		soggettoDAO.eliminaSoggettoStatusBySoggetto((Long) dto.getObj());
	}
	
	public CsAAnagrafica getAnagraficaById(BaseDTO dto) {
		return soggettoDAO.getAnagraficaById((Long) dto.getObj());
	}

	@Override
	public CsAAnagrafica saveAnagrafica(BaseDTO dto) {
		CsAAnagrafica csa = (CsAAnagrafica) dto.getObj();
		return soggettoDAO.saveAnagrafica(csa);
	}

	
}
