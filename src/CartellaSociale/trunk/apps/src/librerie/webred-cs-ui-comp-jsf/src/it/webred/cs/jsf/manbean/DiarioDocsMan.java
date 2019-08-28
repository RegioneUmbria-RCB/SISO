package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDocumentoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDDiarioDoc;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.jsf.interfaces.IDiarioDocs;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean
@ViewScoped
public class DiarioDocsMan extends CsUiCompBaseBean implements IDiarioDocs {

	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
	protected AccessTableDocumentoSessionBeanRemote documentoService = (AccessTableDocumentoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDocumentoSessionBean");
	
	private int idxSelected;

	private DiarioDocsUploadMan uFileMan;
	private DownloadFileMan dFileMan;
	
	public DiarioDocsMan() {
		uFileMan = new DiarioDocsUploadMan();
		dFileMan = new DownloadFileMan();
	}
	
	public void caricaDocumenti(Long idDiario) {
		try{
			
			uFileMan = new DiarioDocsUploadMan();
			uFileMan.setIdDiario(idDiario);
			dFileMan = new DownloadFileMan();
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idDiario);
			List<CsDDiarioDoc> lista = diarioService.findDiarioDocById(dto);
			List<CsLoadDocumento> listaDoc = new ArrayList<CsLoadDocumento>();
			for(CsDDiarioDoc dd: lista) {
				listaDoc.add(dd.getCsLoadDocumento());
			}
			uFileMan.setDocumentiUploaded(listaDoc);
			
		}catch(Exception e){
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void deleteDocumentoFromDB(Long idDocumento){
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(idDocumento);
		diarioService.deleteDiarioDoc(dto);
		documentoService.deleteLoadDocumento(dto);
	}
	
	public void eliminaDoc() {
		try{
			
			CsLoadDocumento doc = uFileMan.getDocumentiUploaded().get(idxSelected);
			
			deleteDocumentoFromDB(doc.getId());
			
			uFileMan.getDocumentiUploaded().remove(idxSelected);
			
			addInfoFromProperties("elimina.ok");
			
		}catch(Exception e){
			addErrorFromProperties("elimina.error");
			logger.error(e.getMessage(),e);
		}
	}

	public DownloadFileMan getdFileMan() {
		return dFileMan;
	}

	public void setdFileMan(DownloadFileMan dFileMan) {
		this.dFileMan = dFileMan;
	}

	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

	public DiarioDocsUploadMan getuFileMan() {
		return uFileMan;
	}

	public void setuFileMan(DiarioDocsUploadMan uFileMan) {
		this.uFileMan = uFileMan;
	}
	
}
