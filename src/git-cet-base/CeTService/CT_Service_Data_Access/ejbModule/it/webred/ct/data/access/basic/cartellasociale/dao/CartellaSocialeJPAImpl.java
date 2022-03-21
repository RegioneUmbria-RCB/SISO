package it.webred.ct.data.access.basic.cartellasociale.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.cartellasociale.CartellaSocialeServiceException;
import it.webred.ct.data.access.basic.cartellasociale.dto.InterventoDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;


public class CartellaSocialeJPAImpl extends CTServiceBaseDAO implements CartellaSocialeDAO {
	
	private static final long serialVersionUID = 1L;
	
	public List<InterventoDTO> getInterventiByCF(String cf) {
		
		ArrayList<InterventoDTO> lista = new ArrayList<InterventoDTO>();
		
		try {
			logger.debug("getInterventiByCF[codfisc: "+cf);
			Query q = manager_diogene.createNamedQuery("CartellaSociale.getInterventiByCF");
			
			q.setParameter("codFisc", cf);
			List<Object[]> result =  q.getResultList();
			logger.debug("Result size ["+result.size()+"]");
			
			for(Object[] obj : result){
				InterventoDTO i = new InterventoDTO();
				i.setDescrizione((String)obj[0]);
				i.setDtInizioVal((Date)obj[1]);
				i.setDtFineVal((Date)obj[2]);
				i.setImportoErogato((BigDecimal)obj[3]);
				i.setDescComuneErogante((String)obj[4]);
				lista.add(i);
			}
			
			
		} catch (Throwable t) {
			logger.error("",t);
			throw new CartellaSocialeServiceException(t);
		}
		
		return lista;
	}

	
}
