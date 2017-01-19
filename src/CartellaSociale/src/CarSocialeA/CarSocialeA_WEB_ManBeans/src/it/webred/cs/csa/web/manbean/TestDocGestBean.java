package it.webred.cs.csa.web.manbean;

import it.webred.cs.jsf.manbean.DownloadFileMan;
import it.webred.cs.jsf.manbean.UploadFileMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class TestDocGestBean extends CsUiCompBaseBean  {

	private UploadFileMan fileMan;
	private DownloadFileMan dFileMan;
	

	@PostConstruct
	public void onPostConstruct() {
		fileMan = new UploadFileMan();
		dFileMan = new DownloadFileMan();
	}
	
	public UploadFileMan getFileMan() {
		return fileMan;
	}

	public void setFileMan(UploadFileMan fileMan) {
		this.fileMan = fileMan;
	}

	public DownloadFileMan getdFileMan() {
		return dFileMan;
	}

	public void setdFileMan(DownloadFileMan dFileMan) {
		this.dFileMan = dFileMan;
	}

	

}
