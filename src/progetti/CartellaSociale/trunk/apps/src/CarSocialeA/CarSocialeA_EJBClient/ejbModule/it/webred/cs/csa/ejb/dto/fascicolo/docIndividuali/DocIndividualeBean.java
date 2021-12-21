package it.webred.cs.csa.ejb.dto.fascicolo.docIndividuali;

import java.util.List;

import it.webred.cs.csa.ejb.dto.fascicolo.DatiInterceptorDTO;
import it.webred.cs.data.model.CsDDocIndividuale;
import it.webred.cs.data.model.CsLoadDocumento;

public class DocIndividualeBean extends DatiInterceptorDTO{

	protected CsDDocIndividuale docIndividuale;
	protected CsLoadDocumento csLoadDocumento;
	protected boolean disableDownload;
	protected boolean soloLettura;
	
	public DocIndividualeBean( CsDDocIndividuale doc , CsLoadDocumento loadDocumento,  boolean permessoDownload, String currUsername, List<Long> sottocartellaDown) {
		setIdSettoreDiario(doc.getDiarioId());
		setSettSecondoLivello(doc.getCsDDiario().getVisSecondoLivello());
		
		docIndividuale = doc; 
		csLoadDocumento = loadDocumento;
		
		/*
		 * Posso fare il download del documento:
		 * - se ho il permesso in AM (permessoDownload)
		 * - se sono l'autore del documento
		 * - se per una tipologia di documento ho il bypass (SISO-1760) --> TODO
		 * */
		boolean isAutore = doc.getCsDDiario().isAutore(currUsername);
		boolean downloadBypass = !doc.getPrivato() && sottocartellaDown.contains(doc.getCsTbSottocartellaDoc().getId());
		disableDownload = !permessoDownload && !isAutore && !downloadBypass;
		soloLettura = !permessoDownload && !isAutore;
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
	public boolean isSoloLettura() {
		return soloLettura;
	}
	public void setSoloLettura(boolean soloLettura) {
		this.soloLettura = soloLettura;
	}
}
