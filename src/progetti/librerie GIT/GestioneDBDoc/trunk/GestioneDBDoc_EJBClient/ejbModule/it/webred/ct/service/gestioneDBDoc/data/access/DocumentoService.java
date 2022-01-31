package it.webred.ct.service.gestioneDBDoc.data.access;

import javax.ejb.Remote;

import it.webred.ct.service.gestioneDBDoc.data.access.dto.DocInDTO;
import it.webred.ct.service.gestioneDBDoc.data.model.SitLoadDocumento;

@Remote
public interface DocumentoService {
	
	public Long salvaDocumento(DocInDTO dataIn);

	public SitLoadDocumento getDocumentoById(DocInDTO dataIn);

}
