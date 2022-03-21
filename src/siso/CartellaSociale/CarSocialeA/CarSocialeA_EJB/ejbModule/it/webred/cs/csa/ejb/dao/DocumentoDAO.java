package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsDDiarioDoc;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.data.model.CsTbSottocartellaDoc;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class DocumentoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CsLoadDocumento salvaDocumento(CsLoadDocumento obj) {
		
		try{
			em.persist(obj);
			return obj;
		
		}catch(Throwable e){
			throw new CarSocialeServiceException(e);
		}
	}

	public CsLoadDocumento getDocumentoById(Long id) {
		try{
			return em.find(CsLoadDocumento.class, id);
			
		}catch(Throwable e){
			throw new CarSocialeServiceException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CsLoadDocumento> getDocumenti(){
		try{
			Query q = em.createNamedQuery("CsLoadDocumento.findAll");
			return q.getResultList();
			
		}catch(Throwable e){
			throw new CarSocialeServiceException(e);
		}
	}
	
	public void deleteLoadDocumento(Long id) {
		try{
			Query q = em.createNamedQuery("CsLoadDocumento.deleteLoadDocumentoById");
			q.setParameter("id", id);
			q.executeUpdate();
		}catch(Throwable e){
			throw new CarSocialeServiceException(e);
		}
	}

	public CsLoadDocumento findLoadDocumentoByDiarioId(long id) {
		try{
			Query q = em.createNamedQuery("CsLoadDocumento.findLoadDocumentoByDiarioId");
			q.setParameter("idDiario", id);
		 	List<CsLoadDocumento> lst = (List<CsLoadDocumento>)q.getResultList();
		 	if( lst != null && lst.size() > 0 )
		 		return lst.get(0);
		}catch(Throwable e){
			logger.error("findLoadDocumentoByDiarioId "+e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
	 	return null;
	}


//INIZIO SISO-438
	public CsLoadDocumento merge(CsLoadDocumento csLoadDocumento) {
		try{
			return em.merge(csLoadDocumento);
		}catch(Throwable e){
			logger.error("", e);
			throw new CarSocialeServiceException(e);
		}
	
	}
//FINE SISO-438

}
