package it.webred.cs.csa.ejb.ejb;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableSinaSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.SinaDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SinaEsegDTO;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsDSinaEseg;
import it.webred.cs.data.model.CsDSinaEsegPK;
import it.webred.cs.data.model.CsTbSinaDomanda;
import it.webred.cs.data.model.CsTbSinaRisposta;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableSinaSessionBean extends CarSocialeBaseSessionBean implements AccessTableSinaSessionBeanRemote {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SinaDAO sinaDao;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDao;

	/*@Override
	public CsDSina newSina(BaseDTO dto) {
		return sinaDao.newSina((CsDSina) dto.getObj());
	}*/

	@Override
	public SinaEsegDTO getSinaById(BaseDTO dto) {
		return this.getSinaById((Long)dto.getObj(), (List<CsTbSinaDomanda>)dto.getObj2());
	}
	
	@Override
	public SinaEsegDTO getSinaByDiarioId(BaseDTO dto) {
		return this.getSinaByDiarioId((Long)dto.getObj(), (List<CsTbSinaDomanda>)dto.getObj2());
	}
	
	private SinaEsegDTO getSinaById(Long id, List<CsTbSinaDomanda> lstParams){
	
		if(lstParams==null) lstParams = configurazioneDao.getListaDomandaSina();
		CsDSina csDSina = sinaDao.getSinaById(id);
		return csDSina == null ? null : SinaEsegDTO.create(csDSina, lstParams);
	}
	
	private SinaEsegDTO getSinaByDiarioId(Long id, List<CsTbSinaDomanda> lstParams){
		try{
			if(lstParams==null) lstParams = configurazioneDao.getListaDomandaSina();
			CsDSina csDSina = sinaDao.getSinaByDiarioId(id);
			return csDSina == null ? null : SinaEsegDTO.create(csDSina, lstParams);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	
	@Override
	public SinaEsegDTO saveSina(SinaEsegDTO e) {
		CsDSina s = e.getCsDSina();
	    
		if(e.getRispostas() != null && s.getCsDSinaEseg().size() > 0){
			s.getCsDSinaEseg().clear();
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
				    esegPK.setSinaId(s.getId());
				    eseg.setId(esegPK);
				    eseg.setCsDSina(s);
				    eseg.setUserIns(e.getUserId());//s.getCsDDiario().getUserIns()
				    Timestamp actual = new Timestamp(System.currentTimeMillis());
				    eseg.setDtIns(actual);
				    CsTbSinaDomanda d = new CsTbSinaDomanda();
				    d.setId(idDomanda);
				    eseg.setCsTbSinaDomanda(d);				    
				    CsTbSinaRisposta r = new CsTbSinaRisposta();
				    r.setId(idRisposta);			    
				    eseg.setCsTbSinaRisposta(r);
					s.getCsDSinaEseg().add(eseg);
					
				}else{
					//Delete?
				}
			}
			if(!e.getLstSinaParamInvalidita().isEmpty()){
				long idDomanda = Long.parseLong("10");
				for(Long l : e.getLstSinaParamInvalidita()){
					
					CsDSinaEseg eseg = new CsDSinaEseg();
					CsDSinaEsegPK esegPK = new CsDSinaEsegPK();
			    
					esegPK.setDomandaId(idDomanda);
					esegPK.setRispostaId(l);
					esegPK.setSinaId(s.getId());
			    
					eseg.setId(esegPK);
					eseg.setCsDSina(s);
					eseg.setUserIns(e.getUserId());//s.getCsDDiario().getUserIns()
					Timestamp actual = new Timestamp(System.currentTimeMillis());
					eseg.setDtIns(actual);
					CsTbSinaDomanda d = new CsTbSinaDomanda();
					d.setId(idDomanda);
					eseg.setCsTbSinaDomanda(d);				    
					CsTbSinaRisposta r = new CsTbSinaRisposta();
					r.setId(l);			    
					eseg.setCsTbSinaRisposta(r);
					s.getCsDSinaEseg().add(eseg);
				}
			}
		
		
	    sinaDao.updateSina(s);
		Long idSina = s.getId();
		//TODO: Implementare salvataggio parametri
		
		return this.getSinaById(idSina, e.getLstSinaParams());
		
	}

	@Override
	public SinaEsegDTO saveNewSina(SinaEsegDTO e) {
		CsDSina s = e.getCsDSina();
		s = sinaDao.updateSina(s);
		e.setCsDSina(s);
		return e;
		}
	
	
	
//	@Override
//	public SinaEsegDTO saveSina(SinaEsegDTO e) {
//		CsDSina s = e.getCsDSina();
//		Iterator<Long> i = e.getRispostas().keySet().iterator();
//		
//		
//		while(i.hasNext()){
//			Long idDomanda = i.next();
//			Long idRisposta = e.getRispostas().get(i);
//			if(idRisposta!=null){
//			    CsDSinaEseg eseg = new CsDSinaEseg();
//			    eseg.setCsDSina(s);
//			    CsTbSinaDomanda d = new CsTbSinaDomanda();
//			    d.setId(idDomanda);
//			    eseg.setCsTbSinaDomanda(d);
//			    
//			    CsTbSinaRisposta r = new CsTbSinaRisposta();
//			    r.setId(idRisposta);
//			    
//			    eseg.setCsTbSinaRisposta(r);
//				s.getCsDSinaEseg().add(eseg);
//			}else{
//				//Delete?
//			}
//		}
//		
//		s = sinaDao.updateSina(s);
//		Long idSina = s.getId();
//		//TODO: Implementare salvataggio parametri
//		
//		return this.getSinaById(idSina, e.getLstSinaParams());
//		
//	}

	@Override
	public void deleteSina(BaseDTO dto) {
		sinaDao.deleteSinaEsegById((Long) dto.getObj());
	}

//	@Override
//	public List<ArTbPrestazioniInps> getPrestazioniInpsSina() {
//		
//		return sinaDao.getPrestazioniInpsSina();
//	}
	

}
