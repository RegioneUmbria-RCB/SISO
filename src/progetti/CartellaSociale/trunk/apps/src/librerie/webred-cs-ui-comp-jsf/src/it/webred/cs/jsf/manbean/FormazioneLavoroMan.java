package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbProfessione;
import it.webred.cs.data.model.CsTbSettoreImpiego;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.jsf.interfaces.IFormazioneLavoro;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.commons.lang3.StringUtils;

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
	private CsTbTitoloStudio tbTitoloStudio;
	
	private List<SelectItem> lstTitoliStudio;
	private List<SelectItem> lstProfessioni;
	private List<SelectItem> lstConLavorativa;
	private List<SelectItem> lstSettoreImpiego;
	
	private boolean renderTitoloStudio;
	private boolean renderProfessione;
	private boolean renderSettoreImpiego;
	private boolean renderCondLavorativa;
	
	private boolean requiredCondLavorativa;
	
	 public FormazioneLavoroMan() {
	 	this.renderTitoloStudio=true;
	 	this.renderCondLavorativa=true;
	 	this.renderSettoreImpiego=true;
	 	this.renderProfessione=true;
	 	this.requiredCondLavorativa=true;
	 }
	
	
	@Override
	public List<SelectItem> getLstTitoliStudio() {
		if(lstTitoliStudio == null){
			lstTitoliStudio = new ArrayList<SelectItem>();
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<KeyValueDTO> lst = confService.getTitoliStudio(bo);
			lstTitoliStudio = convertiLista(lst);
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
						boolean abilitato = obj.getAbilitato()!=null ? obj.getAbilitato().booleanValue() : Boolean.FALSE;
						if(!abilitato && !valorePresetted)
							si.setDisabled(true);
						if(labelGroup==null || labelGroup.trim().isEmpty())
							lstConLavorativa.add(si);
						else
							siList.add(si);
					}
					if(labelGroup!=null && !labelGroup.trim().isEmpty()){
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

	@Override
	public String getTitoloStudioIstat(){
		String istat = tbTitoloStudio!=null ? format(tbTitoloStudio.getCodIstat()) : "";
		String desc = tbTitoloStudio!=null ? format(tbTitoloStudio.getDescrizione()): "";
		String tooltip = tbTitoloStudio!=null ? format(tbTitoloStudio.getTooltip()) : "";
		return (istat+" "+desc+" "+tooltip).trim();
	}
	
	@Override
	public String getTitoloStudio() {
		return tbTitoloStudio!=null ? format(tbTitoloStudio.getDescrizione()) : "";
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
	    		if(lavoro!=null && cl.getCsTbIngMercato()!=null && cl.getCsTbIngMercato().getDescrizione()!=null)
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
		tbTitoloStudio = null;
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
		fillEnte(d);
    	if(idTitoloStudio!=null){
    		d.setObj(idTitoloStudio.longValue());
    		tbTitoloStudio = confService.getTitoloStudioById(d);
    	}
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
		
		if(this.renderCondLavorativa && requiredCondLavorativa 
				&& (this.idCondLavorativa==null || this.idCondLavorativa.longValue()<=0)) 
			 messages.add("Condizione Lavorativa è un campo obbligatorio");
		if(this.renderTitoloStudio && (this.idTitoloStudio==null || this.idTitoloStudio.longValue()<=0)) 
			 messages.add("Titolo di Studio è un campo obbligatorio");
		
		boolean validazioneOk = true;
		
		if( messages.size() > 0 ) {
			addWarning("Formazione e Lavoro", messages);
			validazioneOk &= false;
		}
		return validazioneOk;
	}


	public boolean isRenderTitoloStudio() {
		return renderTitoloStudio;
	}

	public void setRenderTitoloStudio(boolean renderTitoloStudio) {
		this.renderTitoloStudio = renderTitoloStudio;
	}

	public boolean isRenderProfessione() {
		return renderProfessione;
	}

	public void setRenderProfessione(boolean renderProfessione) {
		this.renderProfessione = renderProfessione;
	}

	public boolean isRenderSettoreImpiego() {
		return renderSettoreImpiego;
	}

	public void setRenderSettoreImpiego(boolean renderSettoreImpiego) {
		this.renderSettoreImpiego = renderSettoreImpiego;
	}

	public boolean isRenderCondLavorativa() {
		return renderCondLavorativa;
	}
	
	public void setRenderCondLavorativa(boolean renderCondLavorativa) {
		this.renderCondLavorativa = renderCondLavorativa;
	}


	public boolean isRequiredCondLavorativa() {
		return requiredCondLavorativa;
	}

	public void setRequiredCondLavorativa(boolean requiredCondLavorativa) {
		this.requiredCondLavorativa = requiredCondLavorativa;
	}
	
}
