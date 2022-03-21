package it.webred.cs.jsf.manbean;

import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.jsf.interfaces.IDownloadFile;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class DownloadFileMan extends CsUiCompBaseBean implements IDownloadFile {

	private StreamedContent download;
	
	public void prepDownload(CsLoadDocumento documento){
		if(documento!=null){
			InputStream is = new ByteArrayInputStream(documento.getDocumento());
		    logger.debug(documento.getNome()+" - size file: "+documento.getDocumento().length);
		    setDownload(new DefaultStreamedContent(is,documento.getTipo(),documento.getNome()));
		}
	}

	public StreamedContent getDownload() {
		return download;
	}


	public void setDownload(StreamedContent download) {
		this.download = download;
	}
	
}
	


