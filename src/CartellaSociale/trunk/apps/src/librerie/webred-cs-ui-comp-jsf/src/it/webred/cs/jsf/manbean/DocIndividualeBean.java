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
	protected boolean disableDownload;
	
	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getCarSocialeEjb ("AccessTableDiarioSessionBean");
	protected AccessTableDocumentoSessionBeanRemote documentoService = (AccessTableDocumentoSessionBeanRemote) getCarSocialeEjb ("AccessTableDocumentoSessionBean");

	public DocIndividualeBean( CsDDocIndividuale doc , boolean permessoDownload) {
		docIndividuale = doc; 
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(doc.getDiarioId());
		csLoadDocumento = documentoService.findLoadDocumentoByDiarioId(dto);
		
		disableDownload = !permessoDownload;
		logger.debug("Inizializzo Doc Individuale Bean - "+ doc.getDiarioId());
		if(!permessoDownload) {
			String currUsername = getCurrentOpSettore().getCsOOperatore().getUsername();
			Long currIdOperatore = getCurrentOpSettore().getCsOOperatore().getId();
			
			//Verifico che l'autore coincida, per abilitarlo
			boolean isAutore = currUsername.equals(doc.getCsDDiario().getUserIns()) ||
					(doc.getCsDDiario().getUsrMod()!=null && currUsername.equals(doc.getCsDDiario().getUsrMod()))  ||
					(doc.getCsDDiario().getCsOOperatoreSettore()!=null &&  currIdOperatore ==  doc.getCsDDiario().getCsOOperatoreSettore().getCsOOperatore().getId());
			disableDownload = !isAutore;
			
			logger.debug("disablePermDownload["+permessoDownload+"], isAutore["+isAutore+"] --> disableDownload["+disableDownload+"]");
		}
		
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
	public boolean isDisableDownload() {
		return disableDownload;
	}
	public void setDisableDownload(boolean disableDownload) {
		this.disableDownload = disableDownload;
	}
	
}
