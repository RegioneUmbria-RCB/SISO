package it.webred.cs.jsf.interfaces;

import org.primefaces.event.FileUploadEvent;


public interface IUploadFile {

	public void gestisciFileUpload(FileUploadEvent event);
	public void elimina();
}
