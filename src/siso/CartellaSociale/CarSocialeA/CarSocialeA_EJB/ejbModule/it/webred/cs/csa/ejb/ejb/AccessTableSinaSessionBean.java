package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableSinaSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.ExportCasellarioDAO;
import it.webred.cs.csa.ejb.dao.SinaDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.sina.SinaEsegDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoSinaDomanda;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsDSinaEseg;
import it.webred.cs.data.model.CsDSinaLIGHT;
import it.webred.cs.data.model.CsIPsExport;
import it.webred.cs.data.model.CsTbSinaDomanda;
import it.webred.ct.support.validation.ValidationStateless;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableSinaSessionBean extends CarSocialeBaseSessionBean implements AccessTableSinaSessionBeanRemote {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SinaDAO sinaDao;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDao;

	@Autowired
	private ExportCasellarioDAO exportDAO;

	@Override
	public SinaEsegDTO getSinaById(BaseDTO dto) {
		return this.getSinaById((Long)dto.getObj(), (List<CsTbSinaDomanda>)dto.getObj2());
	}
	
	@Override
	public SinaEsegDTO clonaSinaById(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		List<CsTbSinaDomanda> lstParams = (List<CsTbSinaDomanda>)dto.getObj2();
		try{
			if(lstParams==null) lstParams = configurazioneDao.getListaDomandaSina();
			CsDSina csDSina = sinaDao.getSinaById(id);
			List<CsDSinaEseg> lstSinaEseg = sinaDao.getSinaEsegBySinaId(id);
			List<String> prestazioniInps = sinaDao.getSinaPrestazioniInpsBySinaId(id);
			return SinaEsegDTO.clone(csDSina, lstParams, lstSinaEseg, prestazioniInps);
			
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
		}
		return null; 
	}
	
	@Override
	public SinaEsegDTO getSinaByDiarioId(BaseDTO dto) {
		Long id = (Long)dto.getObj();
		List<CsTbSinaDomanda> lstParams = (List<CsTbSinaDomanda>)dto.getObj2();
		
		try{		
			CsDSina csDSina = null;
			if(id!=null){
				csDSina = sinaDao.getSinaByDiarioId(id);
				return this.loadSinaDTO(csDSina, lstParams);
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	private SinaEsegDTO getSinaById(Long id, List<CsTbSinaDomanda> lstParams){
		try{
			if(id!=null) {
				CsDSina csDSina = sinaDao.getSinaById(id);
				return this.loadSinaDTO(csDSina, lstParams);
			}
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
		}
		return null; 
	}
	
	private SinaEsegDTO loadSinaDTO(CsDSina csDSina, List<CsTbSinaDomanda> lstParams){
		try {
			if(lstParams==null) lstParams = configurazioneDao.getListaDomandaSina();
			if(csDSina!=null) {
				List<CsDSinaEseg> lstSinaEseg = sinaDao.getSinaEsegBySinaId(csDSina.getId());
				List<String> prestazioniInps = sinaDao.getSinaPrestazioniInpsBySinaId(csDSina.getId());
				if(csDSina!=null)
					return SinaEsegDTO.create(csDSina, lstParams, lstSinaEseg, prestazioniInps);
			}
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@Override
	public Long saveSina(SinaEsegDTO e) {
		CsDSina s = null;
		List<CsDSinaEseg> esegs = new ArrayList<CsDSinaEseg>();
		if(e.getSinaId()!=null) {
			s = sinaDao.getSinaById(e.getSinaId());
			esegs = sinaDao.getSinaEsegBySinaId(s.getId());
		}else
			s = new CsDSina();
		
		s.setData(e.getData());
		s.setFlagValutaDopo(e.getFlagValutaDopo());
		s.setIntEsegMastId(e.getInterventoEsegMastId());
		s.setDiarioId(e.getDiarioMultidimId());
		
		if(s.getId() ==null){
			s.setDtIns(new Date());
			s.setUserIns(e.getUserId());
		}else {
			s.setDtMod(new Date());
			s.setUserMod(e.getUserId());
		}
		//s.setArTbPrestazioniInps(e.getPrestazioniSina());
		
		s = sinaDao.updateSina(s);
	    
		Long idSina = s.getId();
		
		sinaDao.savePrestazioneInpsSina(idSina, e.getLstPrestazioniInpsScelte());
		
		if(e.getRispostas() != null && !esegs.isEmpty()){
			sinaDao.deleteSinaEsegBySinaId(s.getId());
		}
		
		for (Entry<Long, String> entry:  e.getRispostas().entrySet()) { //in rispostas non ci dovrebbe più essere la domanda 10 ! e salva le altre 
			   Long idDomanda = entry.getKey();
			   Long idRisposta = null;
			   if(entry.getValue() != null){
				   idRisposta = new Long(entry.getValue());
			   }
			   
			   if(idRisposta!=null){
					sinaDao.updateSinaEseg(idDomanda, idRisposta, idSina, e.getUserId());
				}else{
					//Delete?
				}
			}
			if(!e.getLstSinaParamInvalidita().isEmpty()){
				long idDomanda = TipoSinaDomanda.INVALIDITA_CIVILE;
				for(Long l : e.getLstSinaParamInvalidita())
					sinaDao.updateSinaEseg(idDomanda, l, idSina, e.getUserId());
			}
			
			return idSina;
		
	}

	@Override
	public boolean canDeleteSina(BaseDTO dto){
		SinaEsegDTO sina = (SinaEsegDTO) dto.getObj();
		
		boolean canDelete = true;
		if(!sina.getFlagValutaDopo()) {
			if(sina.getInterventoEsegMastId()!=null && sina.getData()!=null){
				List<CsIPsExport> exps =  exportDAO.findCsIPsExportByCsIInterventoMastIdExported(sina.getInterventoEsegMastId());
				//Verifico la data della valutazione SINA se è successiva all'esportazione la posso eliminare
				Iterator<CsIPsExport> iexps = exps.iterator();
				while(iexps.hasNext() && canDelete){
					CsIPsExport exp = iexps.next();
					canDelete = sina.getData().after(exp.getDtExport());
				}
			}
		}
		return canDelete;
	}
	
	@Override
	public void deleteSina(BaseDTO dto) {
		if(this.canDeleteSina(dto)) {
			SinaEsegDTO sina = (SinaEsegDTO) dto.getObj();
			Long id = sina.getSinaId();
		   sinaDao.deleteSinaEsegBySinaId(id);
		   sinaDao.deletePrestazioniSinaById(id);
		   sinaDao.deleteSinaById(id);
		}
	}
	
	//SISO-783
	@Override
	public List<SinaEsegDTO> findDiarioSinaByMastId(BaseDTO dto){
		Long mastId = (Long)dto.getObj();
		List<CsDSina> lista = sinaDao.findSinaByMastId(mastId);
		return this.loadListaSinaDTO(lista);
	}
	
	@Override
	public HashMap<Long,CsDSinaLIGHT> getSinaByMastIds(BaseDTO dto) {
		List<Long> idsTot = (List<Long>) dto.getObj();
		HashMap<Long,CsDSinaLIGHT> mappaSina = new HashMap<Long,CsDSinaLIGHT>();
		List<Long> ids = new ArrayList<Long>();
		int range = DataModelCostanti.MAX_PARAMS_QUERY_IN_CLAUSE;
		int size = idsTot.size();
		
		int numChunck = size / range;
		int residui = size % range;
		
		logger.debug("getSinaByMastIds size["+size+"] maxrange["+range+"] size / range["+numChunck+"] size % range["+residui+"]");
		
		int min = 0;
		int max = numChunck > 0 ? range : size;
		
		int i = 0;
		while(i < numChunck){
			ids = idsTot.subList(min, max);
			fillMappaSinaByMastIds(ids, mappaSina);
			min += range;
			max += range;
			i++;
		}
		
		if(residui > 0){
			max = min + residui;
			ids = idsTot.subList(min, max);
			fillMappaSinaByMastIds(ids, mappaSina);
		}
					
		return mappaSina;	
	}
	
	private void fillMappaSinaByMastIds(List<Long> ids, HashMap<Long,CsDSinaLIGHT> mappaSina) {
		List<CsDSinaLIGHT> listaSina = sinaDao.getSinaByMastId(ids);	
		for(CsDSinaLIGHT sina: listaSina)
			mappaSina.put(sina.getInterventoEsegMastId(), sina);
	}
	
	
	//SISO-783
	@Override
	public List<SinaEsegDTO> findSinaByCaso(BaseDTO dto){
		Long casoId = (Long) dto.getObj();
		List<CsDSina> lista =  sinaDao.findSinaByCaso(casoId);
		return this.loadListaSinaDTO(lista);
	}
	
	//SISO-783
	@Override
	public Date findMinDateSinaCollegatiByMastId(BaseDTO dto){
		Long mastId = (Long) dto.getObj();
		return sinaDao.findMinDateSinaCollegatiByMastId(mastId);
	}
	
	@Override
	public List<ArTbPrestazioniInps> getPrestazioniInpsSina(BaseDTO dto) {
		String[] codici = {"A1.04","A1.10","A1.11","A1.12","A1.13","A1.14","A1.15","A1.16","A1.23","A2.07","A2.08","A2.14","A2.20","A2.21","A3.02"};
		List<ArTbPrestazioniInps> lst =  configurazioneDao.findArTbPrestazioniInpsByCodice(codici);
		return lst;
	}
	
	//SISO-928
	@Override
	public List<SinaEsegDTO> findSinaCollegabiliByCf(BaseDTO dto){
		Long mastId = (Long) dto.getObj();
		String cf =  (String)dto.getObj2();		
		List<CsDSina> lista = sinaDao.findSinaCollegabiliByCf(cf,mastId);
		return this.loadListaSinaDTO(lista);
	}
	
	private List<SinaEsegDTO> loadListaSinaDTO(List<CsDSina> lista){
		List<SinaEsegDTO> lstOut = new ArrayList<SinaEsegDTO>();
		if(!lista.isEmpty()){
		   List<CsTbSinaDomanda> lstParams = configurazioneDao.getListaDomandaSina();
		   for(CsDSina sina : lista)
			   lstOut.add(this.loadSinaDTO(sina, lstParams));
		}
		return lstOut;
	}

}
