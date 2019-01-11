package it.webred.cs.csa.ejb.client;

import java.util.List;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DocInDTO;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.Remote;

@Remote
public interface AccessTableDocumentoSessionBeanRemote {
	
	public CsLoadDocumento salvaDocumento(DocInDTO dataIn);
	
	public CsLoadDocumento getDocumentoById(DocInDTO dataIn);
	
	public List<CsLoadDocumento> getDocumenti(CeTBaseObject cet);
	
	public void deleteLoadDocumento(BaseDTO dto);

	public CsLoadDocumento findLoadDocumentoByDiarioId(BaseDTO dto);

}
