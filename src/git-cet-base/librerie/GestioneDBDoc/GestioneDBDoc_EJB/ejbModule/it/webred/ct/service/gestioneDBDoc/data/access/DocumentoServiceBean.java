package it.webred.ct.service.gestioneDBDoc.data.access;

import java.util.Date;

import it.webred.ct.service.gestioneDBDoc.data.access.dao.DocumentoDAO;
import it.webred.ct.service.gestioneDBDoc.data.access.dto.DocInDTO;
import it.webred.ct.service.gestioneDBDoc.data.model.SitLoadDocumento;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class DocumentoServiceBean extends GestioneDBDocBaseBean implements DocumentoService {
	
	@Autowired
	private DocumentoDAO documentoDAO;

	public Long salvaDocumento(DocInDTO dataIn) {
		((SitLoadDocumento)dataIn.getObj()).setUsrIns(dataIn.getUserId());
		((SitLoadDocumento)dataIn.getObj()).setDtIns(new Date());
		return documentoDAO.salvaDocumento((SitLoadDocumento)dataIn.getObj());
	}

	public SitLoadDocumento getDocumentoById(DocInDTO dataIn) {
		return documentoDAO.getDocumentoById(dataIn.getId());
	}

	
}
