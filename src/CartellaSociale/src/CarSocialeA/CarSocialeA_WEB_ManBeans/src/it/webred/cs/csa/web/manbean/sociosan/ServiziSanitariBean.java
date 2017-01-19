package it.webred.cs.csa.web.manbean.sociosan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.webred.cs.jsf.interfaces.IServiziSanitari;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.sociosan.ejb.dto.ServiziDTO;
import it.webred.siso.ws.client.atlante.client.dto.GetServiziOspiteDTO;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ServiziSanitariBean extends CsUiCompBaseBean implements IServiziSanitari{
    
	private HashMap<String, ServiziDTO> mappa= new HashMap<String,ServiziDTO>();
	private ServiziDTO serviziSanitari = null;
	private String codFiscale;

	private void loadServiziSanitari(String cf){
		this.codFiscale=cf;
		serviziSanitari = null;
		try{
			if(cf != null && !cf.isEmpty()){
				serviziSanitari = mappa.get(codFiscale);
				if(serviziSanitari==null){
					serviziSanitari = getServiziSanitariBase(cf);
					mappa.put(codFiscale, serviziSanitari);
				}
			}else{
				serviziSanitari = null;
			}
		} catch (Exception e) {
			addError("general", "caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	
	@Override
	public boolean isRendered(String cf) {
		this.codFiscale = cf;
		boolean presenti = false;
		if (cf!=null && !cf.isEmpty())
			loadServiziSanitari(codFiscale);
		presenti = (serviziSanitari!=null && serviziSanitari.getServizi()!=null && !serviziSanitari.getServizi().isEmpty()) ? true : false;
		return presenti;
	}

	
	@Override
	public List<GetServiziOspiteDTO> getServiziSanitari() {
		return serviziSanitari!=null ? serviziSanitari.getServizi() : new ArrayList<GetServiziOspiteDTO>();
	}
	
}
