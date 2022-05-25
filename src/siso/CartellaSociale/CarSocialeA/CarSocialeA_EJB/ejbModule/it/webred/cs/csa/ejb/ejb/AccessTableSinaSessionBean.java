package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableSinaSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.SinaDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SinaEsegDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoSinaDomanda;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsDSinaEseg;
import it.webred.cs.data.model.CsDSinaEsegPK;
import it.webred.cs.data.model.CsDSinaLIGHT;
import it.webred.cs.data.model.CsTbSinaDomanda;
import it.webred.ct.support.validation.ValidationStateless;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
					if(csDSina!=null){
						List<CsDSinaEseg> lstSinaEseg = sinaDao.getSinaEsegBySinaId(csDSina.getId());
						List<String> prestazioniInps = sinaDao.getSinaPrestazioniInpsBySinaId(csDSina.getId());
						if(lstParams==null) lstParams = configurazioneDao.getListaDomandaSina();
						return SinaEsegDTO.create(csDSina, lstParams, lstSinaEseg, prestazioniInps);
					}
				}
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
			return null;
	}
	
	private SinaEsegDTO getSinaById(Long id, List<CsTbSinaDomanda> lstParams){
		try{
			if(id!=null) {
				if(lstParams==null) lstParams = configurazioneDao.getListaDomandaSina();
				CsDSina csDSina = sinaDao.getSinaById(id);
				List<CsDSinaEseg> lstSinaEseg = sinaDao.getSinaEsegBySinaId(id);
				List<String> prestazioniInps = sinaDao.getSinaPrestazioniInpsBySinaId(id);
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
		//s.setArTbPrestazioniInps(e.getPrestazioniSina());
		
		s = sinaDao.updateSina(s);
	    
		Long idSina = s.getId();
		
		sinaDao.savePrestazioneInpsSina(idSina, e.getLstPrestazioniInpsScelte());
		
		if(e.getRispostas() != null && !esegs.isEmpty()){
			sinaDao.deleteSinaEsegById(s.getId());
		}
		
		for (Entry<Long, String> entry:  e.getRispostas().entrySet()) { //in rispostas non ci dovrebbe più essere la domanda 10 ! e salva le altre 
			   Long idDomanda = entry.getKey();
			   Long idRisposta = null;
			   if(entry.getValue() != null){
				   idRisposta = new Long(entry.getValue());
			   }
			   
			   if(idRisposta!=null){
				    CsDSinaEseg eseg = new CsDSinaEseg();
				    CsDSinaEsegPK esegPK = new CsDSinaEsegPK();
				    esegPK.setDomandaId(idDomanda);
				    esegPK.setRispostaId(idRisposta);
				    esegPK.setSinaId(idSina);
				    eseg.setId(esegPK);
				    eseg.setUserIns(e.getUserId());
				    Timestamp actual = new Timestamp(System.currentTimeMillis());
				    eseg.setDtIns(actual);
					
					sinaDao.updateSinaEseg(eseg);
					logger.debug(idDomanda+" "+idRisposta+" "+s.getId());
					
				}else{
					//Delete?
				}
			}
			if(!e.getLstSinaParamInvalidita().isEmpty()){
				long idDomanda = TipoSinaDomanda.INVALIDITA_CIVILE;
				for(Long l : e.getLstSinaParamInvalidita()){
					
					CsDSinaEseg eseg = new CsDSinaEseg();
					CsDSinaEsegPK esegPK = new CsDSinaEsegPK();
			    
					esegPK.setDomandaId(idDomanda);
					esegPK.setRispostaId(l);
					esegPK.setSinaId(idSina);
			    
					eseg.setId(esegPK);
					eseg.setUserIns(e.getUserId());
					Timestamp actual = new Timestamp(System.currentTimeMillis());
					eseg.setDtIns(actual);
					
					sinaDao.updateSinaEseg(eseg);
					
				}
			}
			
			return idSina;
		
	}

	@Override
	public void deleteSina(BaseDTO dto) {
		sinaDao.deleteSinaById((Long) dto.getObj());
	}
	
	//SISO-783
	@Override
	public List<CsDSina> findDiarioSinaByMastId(BaseDTO dto){
		Long mastId = (Long)dto.getObj();
		return sinaDao.findSinaByMastId(mastId);
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
	public List<CsDSina> findSinaByCaso(BaseDTO dto){
		Long casoId = (Long) dto.getObj();
		return sinaDao.findSinaByCaso(casoId);
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
	public List<CsDSina> findSinaCollegabiliByCf(BaseDTO dto){
		Long mastId = (Long) dto.getObj();
		String cf =  (String)dto.getObj2();		
		return sinaDao.findSinaCollegabiliByCf(cf,mastId);
	}

}
