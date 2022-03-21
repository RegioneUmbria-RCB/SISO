package eu.smartpeg.rilevazionepresenze.web;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import eu.smartpeg.rilevazionepresenze.datautil.DataModelCostanti;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

@ManagedBean
@SessionScoped
public class HomeController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Logger logger = Logger.getLogger("rilevazionepresenze.log");
	
	protected static ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
	
	private int tabViewIndex = 0;
	
	private boolean isVisibleVillaggi = false;
	private boolean isVisibleRichieste = false;
	
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
	
	public boolean isRichiesteVisible() {

        String val = getGlobalParameter(DataModelCostanti.AmParameterKey.TIPO_FUNZIONE_STRUTTURA);
        if(val!= null && val.equals("2")) {
        	return isVisibleRichieste = true;
        } 
        return false;
    }
	
	public boolean isVillaggiVisible() {

        String val = getGlobalParameter(DataModelCostanti.AmParameterKey.TIPO_FUNZIONE_STRUTTURA);
        if(val!= null && val.equals("1")) {
        	return isVisibleVillaggi = true;
        } 
        return false;
    }
	
	public static String getGlobalParameter(String paramName) {
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey(paramName);
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		if (amKey != null && amKey.getValueConf() != null && !amKey.getValueConf().trim().isEmpty()) {
			return amKey.getValueConf();
		} else
			logger.warn("Parametro '" + paramName + "' non definito");

		return null;
	}
	
	public static Object getEjb(String ear, String module, String ejbName) {
		Context cont;
		try {
			cont = new InitialContext();
			return cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	public boolean isVisibleVillaggi() {
		return isVisibleVillaggi;
	}
	public void setVisibleVillaggi(boolean isVisibleVillaggi) {
		this.isVisibleVillaggi = isVisibleVillaggi;
	}
	public boolean isVisibleRichieste() {
		return isVisibleRichieste;
	}
	public void setVisibleRichieste(boolean isVisibleRichieste) {
		this.isVisibleRichieste = isVisibleRichieste;
	}
	
	

}
