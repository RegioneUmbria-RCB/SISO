package eu.smartpeg.rilevazionepresenze.web;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

@ManagedBean
@SessionScoped
public class HomeController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int tabViewIndex = 0;
	
	public HomeController() {
		
	}
	public int getTabViewIndex() {
		return tabViewIndex;
	}

	public void setTabViewIndex(int tabViewIndex) {
		this.tabViewIndex = tabViewIndex;
	}

	public void onChangeTabView(TabChangeEvent tce){
		   try{

			    FacesContext context = FacesContext.getCurrentInstance();
			    Map<String,String> params = context.getExternalContext().getRequestParameterMap();
			    TabView tabView = (TabView) tce.getComponent();
			    String activeIndexValue = params.get(tabView.getClientId(context) + "_tabindex");
			
			    this.tabViewIndex = Integer.parseInt(activeIndexValue);
			}catch(Exception ex){
				this.tabViewIndex = 0;
			}
		}

}
