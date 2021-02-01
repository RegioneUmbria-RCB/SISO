package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CatSocialeDAO;
import it.webred.cs.csa.ejb.dao.SchedaSegrDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsSsSchedaSegr;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableSchedaSegrSessionBean extends CarSocialeBaseSessionBean implements AccessTableSchedaSegrSessionBeanRemote  {

	private static final long serialVersionUID = 1L;

	public static Logger logger = Logger.getLogger("carsociale.log");
	
	@Autowired
	private SchedaSegrDAO schedaSegrDao;

	@Autowired
	private CatSocialeDAO catSocialeDao;

	
/*	@Override
	public CsCCategoriaSocialeBASIC findCategoriaSchedaSegrById(BaseDTO dto) {	
		return schedaSegrDao.findCatSocialeSchedaSegrById((Long) dto.getObj());
	}*/
	
	@Override
	public String findStatoSchedaSegrById(BaseDTO dto) {	
		
		CsSsSchedaSegr ss = schedaSegrDao.findSchedaSegrById((Long) dto.getObj());
		return ss!=null ? ss.getStato(): null;
	}
	
	@Override
	public CsSsSchedaSegr findSchedaSegrById(BaseDTO dto) {	
		
		return schedaSegrDao.findSchedaSegrById((Long) dto.getObj());
	}
	
	@Override
	public CsSsSchedaSegr findSchedaSegrCreataByIdAnagrafica(BaseDTO dto) {	
		List<CsSsSchedaSegr> lista = schedaSegrDao.findSchedaSegrByIdAnagrafica((Long) dto.getObj(), DataModelCostanti.SchedaSegr.STATO_CREATA);
		if(lista != null && lista.size() > 0)
			return lista.get(0);	
		else return null;
	}

	@Override
	public List<CsSsSchedaSegr> getSchedeSegr(SchedaSegrDTO dto) {	
		
		return schedaSegrDao.getSchedeSegr(dto);

	}
	
	@Override
	public Integer getSchedeSegrCount(SchedaSegrDTO dto) {
		return schedaSegrDao.getSchedeSegrCount(dto.isOnlyNew(), dto);
	}
	
	@Override
	public boolean saveSchedaSegr(SchedaSegrDTO dto) {
		try {
			
			CsSsSchedaSegr sc = schedaSegrDao.findSchedaSegrById(dto.getId());
			
			//Inserimento
			if(sc==null || DataModelCostanti.SchedaSegr.STATO_INS.equals(dto.getFlgStato())) {
				CsSsSchedaSegr scheda = new CsSsSchedaSegr();
				scheda.setId(dto.getId());
				scheda.setFlgStato(dto.getFlgStato());
				scheda.setUserIns(dto.getUserId());
				scheda.setDtIns(new Date());
				
				scheda.setCodEnte(dto.getEnteId());
/*				if(dto.getIdCatSociale() != null) {
					CsCCategoriaSociale catSoc = catSocialeDao.getCategoriaSocialeById(dto.getIdCatSociale());
					scheda.setCsCCategoriaSociale(catSoc);
				}*/
				
				schedaSegrDao.saveSchedaSegr(scheda);
			} else if(DataModelCostanti.SchedaSegr.STATO_MOD.equals(dto.getFlgStato())) {
					CsSsSchedaSegr scheda = schedaSegrDao.findSchedaSegrById(dto.getId());
					scheda.setFlgStato(dto.getFlgStato());
					scheda.setUsrMod(dto.getUserId());
					scheda.setDtMod(new Date());
				/*	if(dto.getIdCatSociale() != null) {
						CsCCategoriaSociale catSoc = catSocialeDao.getCategoriaSocialeById(dto.getIdCatSociale());
						scheda.setCsCCategoriaSociale(catSoc);
					}*/
					
					schedaSegrDao.updateSchedaSegr(scheda);
			}
			
			
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	@Override
	public void updateSchedaSegr(BaseDTO dto) {
		schedaSegrDao.updateSchedaSegr((CsSsSchedaSegr) dto.getObj());
	}
	
	@Override
	public void deleteSchedaSegr(SchedaSegrDTO dto) throws Exception {
			schedaSegrDao.deleteSchedaSegr(dto.getId());
	}
	
	@Override
	public void agganciaCartellaASchedaUDC(BaseDTO dto) throws Exception {
		try{
		    CsASoggettoLAZY soggetto = (CsASoggettoLAZY)dto.getObj2();
			CsSsSchedaSegr csSsSchedaSegr = schedaSegrDao.findSchedaSegrById((Long)dto.getObj());
			//csSsSchedaSegr.setCsASoggetto((CsASoggettoLAZY)dto.getObj2());
			csSsSchedaSegr.setSoggettoId(soggetto.getAnagraficaId());
			csSsSchedaSegr.setStato(DataModelCostanti.SchedaSegr.STATO_CREATA);
			csSsSchedaSegr.setDtMod(new Date());
			csSsSchedaSegr.setUsrMod(dto.getUserId());
			
			schedaSegrDao.updateSchedaSegr(csSsSchedaSegr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Long findSchedaAggiornataUDCSoggetto(BaseDTO dto) {
		return schedaSegrDao.findSchedaAggiornataUDCSoggetto((String)dto.getObj());
	}
}
