package it.webred.AMProfiler.parameters.comune;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.config.model.AmFonteComunePK;
import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.model.AmInstanceComune;
import it.webred.ct.config.model.AmInstanceComunePK;
import it.webred.ct.config.parameters.dto.SelectObjDTO;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.Query;

public class NuovoComuneBean extends ComuneBaseBean {

	private AmComune nuovoComune = new AmComune();
	private String labelComuneSelected;
	private List<SelectObjDTO> listaInstance = new ArrayList<SelectObjDTO>();
	private List<SelectObjDTO> listaFonte = new ArrayList<SelectObjDTO>();

	public void doInit(){
		
		doCaricaInstance(null);
		doCaricaFonte(null);
		labelComuneSelected = "";
		nuovoComune = new AmComune();
		
	}
	
	public void doCaricaInstance(String comune){
		
		String msg = "listainstance";

		listaInstance = new ArrayList<SelectObjDTO>();
		
		try {
			
			List<AmInstance> lista = comuneService.getListaInstanceByUsername(getRequest().getUserPrincipal().getName());
			
			for(AmInstance instance: lista){
				SelectObjDTO dto =  new SelectObjDTO();
				dto.setOggetto(instance);
				
				if(comune != null && !comune.equals("")){
					AmInstanceComune ic = comuneService.getInstanceComuneByComuneInstance(comune, instance.getName());
					dto.setCheck(ic != null? true: false);
					dto.setInDb(ic != null? ic: null);
				}
				
				listaInstance.add(dto);
			}

		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}
		
	}
	
	public void doCaricaFonte(String comune){
		
		String msg = "listafonte";

		listaFonte = new ArrayList<SelectObjDTO>();
		
		try {
			
			List<AmFonte> lista = comuneService.getListaFonte();
			
			for(AmFonte fonte: lista){
				SelectObjDTO dto =  new SelectObjDTO();
				dto.setOggetto(fonte);
				
				if(comune != null){
					AmFonteComune fc = comuneService.getFonteComuneByComuneFonte(comune, fonte.getId());
					dto.setCheck(fc != null? true: false);
					dto.setInDb(fc != null? fc: null);
				}
				
				listaFonte.add(dto);
			}
			
		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}
		
	}
	
	public void doCaricaDatiComune(){
		
		doCaricaInstance(labelComuneSelected);
		doCaricaFonte(labelComuneSelected);
		
	}
	
	public void doSaveNewComune(){
		
		String msg = "salva";
		
		try {
			
			String belfiore = labelComuneSelected;
			if("".equals(labelComuneSelected)){
				comuneService.saveComune(nuovoComune);
				belfiore = nuovoComune.getBelfiore();
			}
			
			for(SelectObjDTO dto: listaInstance){
				if(dto.isCheck() && dto.getInDb() == null){
					AmInstanceComune ic = new AmInstanceComune();
					AmInstanceComunePK icPK = new AmInstanceComunePK();
					icPK.setFkAmComune(belfiore);
					icPK.setFkAmInstance(((AmInstance) dto.getOggetto()).getName());
					ic.setId(icPK);
					comuneService.saveInstanceComune(ic);
				}else if(!dto.isCheck() && dto.getInDb() != null){
					comuneService.deleteInstanceComune((AmInstanceComune) dto.getInDb());
				}
			}
			
			for(SelectObjDTO dto: listaFonte){
				if(dto.isCheck() && dto.getInDb() == null){
					AmFonteComune fc = new AmFonteComune();
					AmFonteComunePK fcPK = new AmFonteComunePK();
					fcPK.setFkAmComune(belfiore);
					fcPK.setFkAmFonte(((AmFonte) dto.getOggetto()).getId());
					fc.setId(fcPK);
					comuneService.saveFonteComune(fc);
				}else if(!dto.isCheck() && dto.getInDb() != null){
					comuneService.deleteFonteComune((AmFonteComune) dto.getInDb());
				}
			}

			nuovoComune = new AmComune();
			ComuneBean bean = (ComuneBean) getBeanReference("comuneBean");
			bean.fillItemsComune();
			
			super.addInfoMessage(msg);
		} catch (Throwable t) {
			super.addErrorMessage(msg + ".error", t.getMessage());
			logger.error(t.getMessage(),t);
		}
		
	}
	
	public void doCleanGestione(){
		
		labelComuneSelected = "";
		for(SelectObjDTO dto: listaInstance){
			dto.setInDb(null);
		}
		for(SelectObjDTO dto: listaFonte){
			dto.setInDb(null);
		}
		
	}
	
	public AmComune getNuovoComune() {
		return nuovoComune;
	}

	public void setNuovoComune(AmComune nuovoComune) {
		this.nuovoComune = nuovoComune;
	}

	public List<SelectObjDTO> getListaInstance() {
		return listaInstance;
	}

	public void setListaInstance(List<SelectObjDTO> listaInstance) {
		this.listaInstance = listaInstance;
	}

	public List<SelectObjDTO> getListaFonte() {
		return listaFonte;
	}

	public void setListaFonte(List<SelectObjDTO> listaFonte) {
		this.listaFonte = listaFonte;
	}

	public String getLabelComuneSelected() {
		return labelComuneSelected;
	}

	public void setLabelComuneSelected(String labelComuneSelected) {
		this.labelComuneSelected = labelComuneSelected;
	}

}
