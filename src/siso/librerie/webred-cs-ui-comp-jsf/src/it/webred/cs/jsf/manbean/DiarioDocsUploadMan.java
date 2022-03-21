package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDDiarioDoc;
import it.webred.cs.data.model.CsDDiarioDocPK;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.jsf.manbean.UploadFileMan;

public class DiarioDocsUploadMan extends UploadFileMan {
	
	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
	
	private Long idDiario;
	
	@Override
	protected void gestisciUploadedDoc(CsLoadDocumento documento) {
		
		CsDDiarioDoc dd = new CsDDiarioDoc();
		CsDDiarioDocPK ddPK = new CsDDiarioDocPK();
		ddPK.setDiarioId(idDiario);
		ddPK.setDocumentoId(documento.getId());
		dd.setId(ddPK);
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(dd);
		diarioService.saveDiarioDoc(dto);
		
		if(!isExternalSave())
			addInfo("Upload avvenuto con successo", "");
	}

	public Long getIdDiario() {
		return idDiario;
	}

	public void setIdDiario(Long idDiario) {
		this.idDiario = idDiario;
	}
	
}
	


