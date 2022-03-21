package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.interfaces.INews;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class NewsMan extends CsUiCompBaseBean implements INews {

 
	@Override
	public String getMessaggioNews(){
		return super.getMessaggioNews();
	}
	
	@Override
	public String getGoogleDoc(){
		return super.getGoogleDoc();
	}
	
	
}
	


