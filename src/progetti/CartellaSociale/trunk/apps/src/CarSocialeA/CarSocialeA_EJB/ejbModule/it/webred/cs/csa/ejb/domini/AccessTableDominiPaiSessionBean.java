package it.webred.cs.csa.ejb.domini;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiPaiSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.PaiAffidoDAO;
import it.webred.cs.csa.ejb.dao.PaiSalDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDominioDTO;
import it.webred.cs.csa.ejb.dto.pai.sal.CsPaiSalDominioDTO;
import it.webred.cs.data.model.affido.CsTbPaiAffido;
import it.webred.cs.data.model.sal.CsTbPaiSal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.springframework.beans.factory.annotation.Autowired;

@Singleton
@Startup
public class AccessTableDominiPaiSessionBean extends CarSocialeBaseSessionBean implements AccessTableDominiPaiSessionBeanRemote {
	
	@Autowired
	private PaiAffidoDAO affidoDAO;
	@Autowired
	private PaiSalDAO salDAO;
	
	private HashMap<String, List<CsPaiAffidoDominioDTO>> cache;
	private HashMap<String, List<CsPaiSalDominioDTO>> cacheSal;
	
	@PostConstruct
	@Schedule(hour="0", persistent=false) //ricarica i domini ogni notte a mezzanotte
	public void initDominiSingleton(){
		this.cache = new HashMap<String, List<CsPaiAffidoDominioDTO>>();
		logger.debug("INIZIO caricamento AccessTableDominiPaiSessionBean");
		try {
			List<CsTbPaiAffido> domini = affidoDAO.findAll();
			
			for(CsTbPaiAffido d : domini){
				//SISO-981 Inizio
				CsPaiAffidoDominioDTO pad = new CsPaiAffidoDominioDTO(d.getDominio(), d.getCodice(), d.getDescrizione(), d.getCodiceSinba());
				//SISO-981 Fine
				List<CsPaiAffidoDominioDTO> listaDom;
				
				if(!cache.containsKey(d.getDominio())){
					listaDom = new ArrayList<CsPaiAffidoDominioDTO>();
				}else{
					listaDom = cache.get(d.getDominio());
				}
				
				listaDom.add(pad);
				cache.put(d.getDominio(), listaDom);
			}
			logger.debug("FINE caricamento AccessTableDominiPaiSessionBean");
		} catch (Exception e) {
			logger.error(e);
		}
		
		//SISO-1257
		this.cacheSal = new HashMap<String, List<CsPaiSalDominioDTO>>();
		try {
			List<CsTbPaiSal> domini = salDAO.findAll();
			
			for(CsTbPaiSal d : domini){
				//SISO-981 Inizio
				CsPaiSalDominioDTO pad = new CsPaiSalDominioDTO(d.getId().getDominio(), d.getId().getCodice(), d.getDescrizione());
				//SISO-981 Fine
				List<CsPaiSalDominioDTO> listaDom;
				
				if(!cacheSal.containsKey(d.getId().getDominio())){
					listaDom = new ArrayList<CsPaiSalDominioDTO>();
				}else{
					listaDom = cacheSal.get(d.getId().getDominio());
				}
				
				listaDom.add(pad);
				cacheSal.put(d.getId().getDominio(), listaDom);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	@Lock(LockType.READ)
	public List<CsPaiAffidoDominioDTO> findByDominio(BaseDTO base) {
		return cache.get((String) base.getObj());
	}

	//SISO-981 Inizio
	@Override
	@Lock(LockType.READ)
	public Integer findSINBADominioByCodice(String codice) {
		String SINBAValue = "";
//		List<CsPaiAffidoDominioDTO> listaDom = new ArrayList(cache.values());
//		for(CsPaiAffidoDominioDTO paiDominioDTO : listaDom){
//			
//			if(paiDominioDTO.getCodice().equals(codice)){
//				SINBAValue = paiDominioDTO.getCodiceSinba();
//				return Integer.parseInt(SINBAValue);
//				
//			}
//	 	}
		for(List<CsPaiAffidoDominioDTO> arrListPaiDominio : cache.values()){
			for(CsPaiAffidoDominioDTO paiDominioDTO : arrListPaiDominio){
				if(paiDominioDTO.getCodice().equals(codice)){
				
					SINBAValue = paiDominioDTO.getCodiceSinba();
					return Integer.parseInt(SINBAValue);
				}
			}
		}
		return -1; 
	}
	//SISO-981 Fine

	//SISO-1257
	@Override
	@Lock(LockType.READ)
	public List<CsPaiSalDominioDTO> findSalByDominio(BaseDTO base) {
		return cacheSal.get((String) base.getObj());
	}
	
	
}
