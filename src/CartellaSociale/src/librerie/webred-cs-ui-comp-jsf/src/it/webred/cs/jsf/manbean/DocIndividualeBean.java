package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDocumentoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDDocIndividuale;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

public class DocIndividualeBean extends CsUiCompBaseBean {

	protected CsDDocIndividuale docIndividuale;
	protected CsLoadDocumento csLoadDocumento;
	
	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
	protected AccessTableDocumentoSessionBeanRemote documentoService = (AccessTableDocumentoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDocumentoSessionBean");

	public DocIndividualeBean( CsDDocIndividuale doc ) {
		docIndividuale = doc; 
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(doc.getDiarioId());
		csLoadDocumento = documentoService.findLoadDocumentoByDiarioId(dto);
//		Set<CsDDiarioDoc> docs = docIndividuale.getCsDDiario().getCsDDiarioDocs();
//		if( docs != null && !docs.isEmpty() )
//			csLoadDocumento = ((CsDDiarioDoc) (docs.toArray()[0])).getCsLoadDocumento();
	}
	public CsDDocIndividuale getCsDDocIndividuale(){
		return docIndividuale;
	}
	public CsLoadDocumento getCsLoadDocumento(){
		return csLoadDocumento;
	}
}
