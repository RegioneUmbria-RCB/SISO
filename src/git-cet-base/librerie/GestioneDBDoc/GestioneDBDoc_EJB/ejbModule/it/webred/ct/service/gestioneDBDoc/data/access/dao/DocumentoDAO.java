package it.webred.ct.service.gestioneDBDoc.data.access.dao;

import it.webred.ct.service.gestioneDBDoc.data.model.SitLoadDocumento;

public interface DocumentoDAO {

	public Long salvaDocumento(SitLoadDocumento obj);

	public SitLoadDocumento getDocumentoById(Long id);

}
