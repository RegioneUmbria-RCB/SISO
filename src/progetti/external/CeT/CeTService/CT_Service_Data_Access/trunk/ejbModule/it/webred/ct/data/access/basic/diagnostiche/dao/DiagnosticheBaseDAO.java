package it.webred.ct.data.access.basic.diagnostiche.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.diagnostiche.DiagnosticheDataIn;
import it.webred.ct.data.access.basic.diagnostiche.DiagnosticheServiceException;
import it.webred.ct.data.access.basic.diagnostiche.ici.DiagnosticheIciServiceException;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.DiagnosticheTarServiceException;
import it.webred.ct.data.model.diagnostiche.DocfaAnomalie;
import it.webred.ct.data.model.locazioni.LocazioniA;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class DiagnosticheBaseDAO extends CTServiceBaseDAO {

	private static final long serialVersionUID = 1L;

	public List<DocfaAnomalie> getListaAnomalie(String tipo)
			throws DiagnosticheServiceException {
		try {

			Query q = manager_diogene.createNamedQuery("DocfaAnomalie.getAnomalie");
			q.setParameter("tipo", tipo);
			return q.getResultList();

		} catch (Throwable t) {
			throw new DiagnosticheTarServiceException(t);
		}
	}
	
	public String getDocfaDescAnomalieById(String sCodAnomalie)throws DiagnosticheIciServiceException {
		
		String sDescAnomalie = null;
		if(sCodAnomalie!=null){
			logger.info("nomalia: "+ sCodAnomalie);
			sDescAnomalie = "";
			String[] codAnomalie = sCodAnomalie.trim().split("\\|");
			logger.info("lenh: "+ codAnomalie.length);
			
			try {
					Query q = manager_diogene.createNamedQuery("DocfaAnomalie.getById");
					for(int i=0; i<codAnomalie.length; i++){
						String id = codAnomalie[i];
						logger.info("Id Anomalia: "+ id);
						if(id!=null && !"".equalsIgnoreCase(id)){
							q.setParameter("id", id);
							DocfaAnomalie d = (DocfaAnomalie) q.getSingleResult();
							
							sDescAnomalie += d.getDescrizione()+"@";
						}
					} 
							
			} catch (Throwable t) {
				throw new DiagnosticheIciServiceException(t);
			}
		}
		
		return sDescAnomalie;

	}
	
	
	
}
