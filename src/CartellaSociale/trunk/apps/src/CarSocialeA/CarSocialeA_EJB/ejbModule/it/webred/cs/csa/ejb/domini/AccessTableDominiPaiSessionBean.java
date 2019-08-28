package it.webred.cs.csa.ejb.domini;

import it.webred.cs.csa.ejb.client.domini.AccessTableDominiPaiSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.PaiAffidoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDominioDTO;
import it.webred.cs.data.model.affido.CsTbPaiAffido;

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
public class AccessTableDominiPaiSessionBean implements AccessTableDominiPaiSessionBeanRemote {
	
	@Autowired
	private PaiAffidoDAO affidoDAO;
	
	private HashMap<String, List<CsPaiAffidoDominioDTO>> cache;
	
	@PostConstruct
	@Schedule(hour="0", persistent=false) //ricarica i domini ogni notte a mezzanotte
	public void initDominiSingleton(){
		this.cache = new HashMap<String, List<CsPaiAffidoDominioDTO>>();
		try {
			List<CsTbPaiAffido> domini = affidoDAO.findAll();
			
			for(CsTbPaiAffido d : domini){
				
				CsPaiAffidoDominioDTO pad = new CsPaiAffidoDominioDTO(d.getDominio(), d.getCodice(), d.getDescrizione());
				
				List<CsPaiAffidoDominioDTO> listaDom;
				
				if(!cache.containsKey(d.getDominio())){
					listaDom = new ArrayList<CsPaiAffidoDominioDTO>();
				}else{
					listaDom = cache.get(d.getDominio());
				}
				
				listaDom.add(pad);
				cache.put(d.getDominio(), listaDom);
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

}
