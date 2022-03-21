package it.webred.cs.jsf.interfaces;

import it.webred.cs.jsf.manbean.DownloadFileMan;
import it.webred.cs.jsf.manbean.UploadFileMan;



public interface IDiarioDocs {

	public void caricaDocumenti(Long idDiario);
	public void eliminaDoc();
	public int getIdxSelected();
	public DownloadFileMan getdFileMan();
	public UploadFileMan getuFileMan();
	
}
