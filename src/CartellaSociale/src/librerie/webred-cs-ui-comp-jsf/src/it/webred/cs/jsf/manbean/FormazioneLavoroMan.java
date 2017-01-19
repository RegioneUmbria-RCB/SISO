package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbProfessione;
import it.webred.cs.data.model.CsTbSettoreImpiego;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.jsf.interfaces.IFormazioneLavoro;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

@ManagedBean
@NoneScoped
public class FormazioneLavoroMan extends CsUiCompBaseBean implements IFormazioneLavoro {
	
	private static final long serialVersionUID = 1L;
	
	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	private BigDecimal idTitoloStudio;
	private BigDecimal idProfessione;
	private BigDecimal idSettoreImpiego;
	private BigDecimal idCondLavorativa;
	
	private String professione;
	private String lavoro;
	private String settImpiego;
	private String titoloStudio;
	
	private List<SelectItem> lstTitoliStudio;
	private List<SelectItem> lstProfessioni;
	private List<SelectItem> lstConLavorativa;
	private List<SelectItem> lstSettoreImpiego;
	
	 public FormazioneLavoroMan() {
		 	//
	 }
	
	
	@Override
	public List<SelectItem> getLstTitoliStudio() {
		
		if(lstTitoliStudio == null){
			lstTitoliStudio = new ArrayList<SelectItem>();
			lstTitoliStudio.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbTitoloStudio> lst = confService.getTitoliStudio(bo);
			if (lst != null) {
				for (CsTbTitoloStudio obj : lst) {
					lstTitoliStudio.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstTitoliStudio;
	}

	public void setLstTitoliStudio(List<SelectItem> lstTitoliStudio) {
		this.lstTitoliStudio = lstTitoliStudio;
	}

	@Override
	public List<SelectItem> getLstProfessioni() {
		
		if(lstProfessioni == null){
			lstProfessioni = new ArrayList<SelectItem>();
			lstProfessioni.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbProfessione> lst = confService.getProfessioni(bo);
			if (lst != null) {
				for (CsTbProfessione obj : lst) {
					String desc=obj.getDescrizione();
					lstProfessioni.add(new SelectItem(obj.getId(), desc)); 
					
				}
			}		
		}
		
		return lstProfessioni;
	}

	public void setLstProfessioni(List<SelectItem> lstProfessioni) {
		this.lstProfessioni = lstProfessioni;
	}

	@Override
	public List<SelectItem> getLstSettoreImpiego() {
		
		if(lstSettoreImpiego == null){
			lstSettoreImpiego = new ArrayList<SelectItem>();
			lstSettoreImpiego.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbSettoreImpiego> lst = confService.getSettoreImpiego(bo);
			if (lst != null) {
				for (CsTbSettoreImpiego obj : lst) {
					lstSettoreImpiego.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstSettoreImpiego;
	}

	public void setLstSettoreImpiego(List<SelectItem> lstSettoreImpiego) {
		this.lstSettoreImpiego = lstSettoreImpiego;
	} 
	
	@Override
	public List<SelectItem> getLstConLavorativa() {
		if(lstConLavorativa == null){
			lstConLavorativa = new ArrayList<SelectItem>();
			lstConLavorativa.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject  xo = new CeTBaseObject();
			fillEnte(xo);
			TreeMap<String, List<CsTbCondLavoro>> tree = confService.getMappaCondLavoro(xo);
			for(String str : tree.keySet()){
				List<CsTbCondLavoro> lst = tree.get(str);
				if (lst != null && !lst.isEmpty()) {
					String labelGroup = lst.get(0).getCsTbIngMercato().getDescrizione();
					SelectItemGroup gr = new SelectItemGroup(labelGroup);
					List<SelectItem> siList = new ArrayList<SelectItem>();
					for (CsTbCondLavoro obj : lst) {
						SelectItem si = new SelectItem(obj.getId(), obj.getDescrizione());
						boolean valorePresetted = idCondLavorativa!=null && obj.getId()==idCondLavorativa.longValue();
						if("0".equals(obj.getAbilitato()) && !valorePresetted)
							si.setDisabled(true);
						if(labelGroup==null || labelGroup.trim().isEmpty())
							siList.add(si);
						else
						    lstConLavorativa.add(si);
					}
					if(labelGroup==null || labelGroup.trim().isEmpty()){
						gr.setSelectItems(siList.toArray(new SelectItem[siList.size()]));
						lstConLavorativa.add(gr);
					}
				}		
			}
		}
		return lstConLavorativa;
	}

	public void setLstConLavorativa(List<SelectItem> lstConLavorativa) {
		this.lstConLavorativa = lstConLavorativa;
	}

	public BigDecimal getIdTitoloStudio() {
		return idTitoloStudio;
	}


	public BigDecimal getIdProfessione() {
		return idProfessione;
	}


	public BigDecimal getIdSettoreImpiego() {
		return idSettoreImpiego;
	}


	public BigDecimal getIdCondLavorativa() {
		return idCondLavorativa;
	}


	public void setIdTitoloStudio(BigDecimal idTitoloStudio) {
		this.idTitoloStudio = idTitoloStudio;
		this.valorizzaTitoloStudio();
	}


	public void setIdProfessione(BigDecimal idProfessione) {
		this.idProfessione = idProfessione;
		this.valorizzaProfessione();
	}


	public void setIdSettoreImpiego(BigDecimal idSettoreImpiego) {
		this.idSettoreImpiego = idSettoreImpiego;
		this.valorizzaSettoreImpiego();
	}


	public void setIdCondLavorativa(BigDecimal idCondLavorativa) {
		this.idCondLavorativa = idCondLavorativa;
		this.valorizzaCondLavoro();
	}


	@Override
	public void onChangeProfessione() {
		if(this.idProfessione==null || this.idProfessione.longValue()==0){
			this.idSettoreImpiego=null;
			this.settImpiego=null;
		}
	}


	public String getProfessione() {
		return professione;
	}


	public String getLavoro() {
		return lavoro;
	}


	public String getSettImpiego() {
		return settImpiego;
	}


	public String getTitoloStudio() {
		return titoloStudio;
	}


	private void valorizzaCondLavoro(){
		lavoro = null;
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillEnte(d);
    	if(idCondLavorativa!=null){
    		d.setObj(idCondLavorativa.toString());
    		CsTbCondLavoro cl = confService.getCondLavoroById(d);
    		if(cl!=null){
	    		lavoro = cl.getDescrizione();
	    		if(lavoro!=null && cl.getCsTbIngMercato()!=null)
	    			lavoro = cl.getCsTbIngMercato().getDescrizione()+": "+lavoro;
    		}
    	}
    	lavoro = format(lavoro);
    }
	
	private void valorizzaSettoreImpiego(){
		settImpiego = null;
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
		fillEnte(d);
    	if(idSettoreImpiego!=null){
    		d.setObj(idSettoreImpiego.longValue());
    		CsTbSettoreImpiego cl = confService.getSettoreImpiegoById(d);
    		settImpiego=cl!=null ? cl.getDescrizione(): null;
    	}
    	settImpiego = format(settImpiego);
    }
	
	private void valorizzaTitoloStudio(){
		titoloStudio = null;
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
		fillEnte(d);
    	if(idTitoloStudio!=null){
    		d.setObj(idTitoloStudio.longValue());
    		CsTbTitoloStudio cl = confService.getTitoloStudioById(d);
    		titoloStudio=cl!=null ? cl.getDescrizione(): null;
    	}
    	titoloStudio = format(titoloStudio);
    }
    
	private void valorizzaProfessione(){
		professione = null;
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
		fillEnte(d);
    	if(idProfessione!=null){
    		d.setObj(idProfessione.toString());
    		CsTbProfessione cl = confService.getProfessioneById(d);
    		professione=cl!=null ? cl.getDescrizione(): null;
    	}
    	professione = format(professione);
    }
	
	private String format(String arg){
    	if(arg!=null)
    		return arg;
    	else
    		return "";
    }
	
	/*public void valorizzaDescrizioni(){
		this.valorizzaCondLavoro();
		this.valorizzaProfessione();
		this.valorizzaSettoreImpiego();
		this.valorizzaTitoloStudio();
	}*/
	
	@Override
	public boolean validaData() {
		
		List<String> messages = new LinkedList<String>();
		
		if(this.idCondLavorativa==null || this.idCondLavorativa.longValue()<=0) 
			 messages.add("'Condizione Lavorativa' è un campo obbligatorio");
		if(this.idTitoloStudio==null || this.idTitoloStudio.longValue()<=0) 
			 messages.add("'Titolo di Studio' è un campo obbligatorio");
		
		boolean validazioneOk = true;
		if( messages.size() > 0 ) {
			for(String msg : messages)
				addWarning(msg,"");
			validazioneOk &= false;
		}
		return validazioneOk;
	}
	

}
