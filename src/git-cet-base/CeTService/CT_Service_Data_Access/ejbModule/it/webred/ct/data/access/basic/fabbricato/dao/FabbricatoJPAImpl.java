package it.webred.ct.data.access.basic.fabbricato.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.fabbricato.FabbricatoServiceException;
import it.webred.ct.data.model.fabbricato.FabbricatoExRurale;
import it.webred.ct.data.model.fabbricato.FabbricatoMaiDichiarato;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class FabbricatoJPAImpl extends CTServiceBaseDAO implements FabbricatoDAO{

	private static final long serialVersionUID = 1L;
	
	@Override
	public List<FabbricatoExRurale> getListaFabbricatiExRurali(String sez, String foglio, String particella, String sub){
		List<FabbricatoExRurale> lista = new ArrayList<FabbricatoExRurale>();
		
		try{
			Query q;
			
			if(sez!=null){
				if(sub!=null){
					q = manager_diogene.createNamedQuery("FabbricatoExRurale.getListaBySezFPS");
					q.setParameter("subalterno", sub);
				}else
					q = manager_diogene.createNamedQuery("FabbricatoExRurale.getListaBySezFPSubNull");
				
				q.setParameter("sezione", sez);
				
			}else{
				if(sub!=null){
					q = manager_diogene.createNamedQuery("FabbricatoExRurale.getListaByFPS");
					q.setParameter("subalterno", sub);
				}else
					q = manager_diogene.createNamedQuery("FabbricatoExRurale.getListaByFPSubNull");
			}
			
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			
			
			lista = (List<FabbricatoExRurale>)q.getResultList();
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new FabbricatoServiceException(t);
		}
		
		return lista;
	}
	 
	 @Override	
	 public List<FabbricatoMaiDichiarato> getListaFabbricatiMaiDichiarati(String sez, String foglio, String particella){
		List<FabbricatoMaiDichiarato> lista = new ArrayList<FabbricatoMaiDichiarato>();
		
		try{
			
			Query q;
			
			if(sez!=null){
				
				q = manager_diogene.createNamedQuery("FabbricatoMaiDichiarato.getListaBySezFP");
				q.setParameter("sezione", sez);
				
			}else{
				q = manager_diogene.createNamedQuery("FabbricatoMaiDichiarato.getListaByFP");
			}
			
			q.setParameter("foglio", foglio);
			q.setParameter("particella", particella);
			
			lista = (List<FabbricatoMaiDichiarato>)q.getResultList();
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new FabbricatoServiceException(t);
		}
		
		return lista;
	}

}
