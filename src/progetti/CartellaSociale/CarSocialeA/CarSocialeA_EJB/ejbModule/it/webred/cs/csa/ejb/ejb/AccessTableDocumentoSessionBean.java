package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableDocumentoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.DocumentoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DocInDTO;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;


@Stateless
public class AccessTableDocumentoSessionBean extends CarSocialeBaseSessionBean implements AccessTableDocumentoSessionBeanRemote  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private DocumentoDAO documentoDao;

	@Override
	public CsLoadDocumento salvaDocumento(DocInDTO dataIn)  {
		((CsLoadDocumento)dataIn.getObj()).setUsrIns(dataIn.getUserId());
		((CsLoadDocumento)dataIn.getObj()).setDtIns(new Date());
		return documentoDao.salvaDocumento((CsLoadDocumento)dataIn.getObj());
	}

	@Override
	public CsLoadDocumento getDocumentoById(DocInDTO dataIn)  {
		return documentoDao.getDocumentoById(dataIn.getId());
	}
	
	@Override
	public List<CsLoadDocumento> getDocumenti(CeTBaseObject cet){
		return documentoDao.getDocumenti();
	}
	
	@Override
	public void deleteLoadDocumento(BaseDTO dto){
		documentoDao.deleteLoadDocumento((Long) dto.getObj());
	}

	@Override
	public CsLoadDocumento findLoadDocumentoByDiarioId(BaseDTO dto) {
		return documentoDao.findLoadDocumentoByDiarioId( (Long)dto.getObj() );
	}

}
