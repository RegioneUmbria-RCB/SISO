package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableDocumentoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.DocInDTO;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.jsf.interfaces.IUploadFile;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

public class UploadFileMan extends CsUiCompBaseBean implements IUploadFile {

	private List<CsLoadDocumento> documentiUploaded;
	
	private boolean externalSave;
	private Integer selId;
	
	public UploadFileMan(){
		documentiUploaded = new ArrayList<CsLoadDocumento>();
	}
	
	@Override
	public void gestisciFileUpload(FileUploadEvent event)  {
        
		UploadedFile file = event.getFile();
		
		CsLoadDocumento documento = new CsLoadDocumento();
		documento.setNome(file.getFileName());
		documento.setDocumento(file.getContents());
		documento.setTipo(file.getContentType());

		if(!externalSave)
			salvaDocumento(documento);
		
		documentiUploaded.add(documento);
		
	}
	
	@Override
	public void elimina() {
		documentiUploaded.remove(this.selId.intValue());
	}
	
	public void salvaDocumento(CsLoadDocumento documento) {
		
		try{
			DocInDTO dataIn = new DocInDTO();
			fillEnte(dataIn);
			dataIn.setObj(documento);
	
			AccessTableDocumentoSessionBeanRemote docService = (AccessTableDocumentoSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
							"AccessTableDocumentoSessionBean");
	
			documento = docService.salvaDocumento(dataIn);
			gestisciUploadedDoc(documento);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Errore nel caricamento del file", "Upload non avvenuto.");
		}
		
	}
	
	protected void gestisciUploadedDoc(CsLoadDocumento documento) {
	}

	public List<CsLoadDocumento> getDocumentiUploaded() {
		return documentiUploaded;
	}

	public void setDocumentiUploaded(List<CsLoadDocumento> documentiUploaded) {
		this.documentiUploaded = documentiUploaded;
	}

	public Integer getSelId() {
		return selId;
	}

	public void setSelId(Integer selId) {
		this.selId = selId;
	}

	public boolean isExternalSave() {
		return externalSave;
	}

	public void setExternalSave(boolean externalSave) {
		this.externalSave = externalSave;
	}

}
	


