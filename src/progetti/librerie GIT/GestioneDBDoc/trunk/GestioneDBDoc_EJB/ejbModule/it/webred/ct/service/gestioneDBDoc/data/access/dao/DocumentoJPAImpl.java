package it.webred.ct.service.gestioneDBDoc.data.access.dao;

import it.webred.ct.service.gestioneDBDoc.data.model.SitLoadDocumento;

public class DocumentoJPAImpl extends GestioneDBDocBaseDAO implements DocumentoDAO {

	public Long salvaDocumento(SitLoadDocumento obj){
		
		try{
		
			manager.persist(obj);
		
			return obj.getId();
		
		}catch(Exception e){
			
			
			
		}
		
		return null;
		
	}

	public SitLoadDocumento getDocumentoById(Long id){
		try{
			
			return manager.find(SitLoadDocumento.class, id);
			
		}catch(Exception e){
			
		
		}
		
		return null;
		
	}

}
