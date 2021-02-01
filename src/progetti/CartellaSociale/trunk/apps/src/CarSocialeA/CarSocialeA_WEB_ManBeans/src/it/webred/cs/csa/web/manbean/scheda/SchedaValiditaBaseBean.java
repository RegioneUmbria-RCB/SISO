package it.webred.cs.csa.web.manbean.scheda;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.context.RequestContext;

public abstract class SchedaValiditaBaseBean extends SchedaUtils {
	
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSessionBean");
	
	protected Long soggettoId;
	protected Long casoId;
	public int currentIndex = -1;
	protected List<ValiditaCompBaseBean> listaComponenti;
	protected List<ValiditaCompBaseBean> listaComponentiToRemove;
	public String param;
	public String nuovoAttiva;
	public boolean flagMsg=false;
	protected boolean modificheNonSalvate;
	
	public boolean isFlagMsg() {
		return flagMsg;
	}

	public void setFlagMsg(boolean flagMsg) {
		this.flagMsg = flagMsg;
	}
	
	public void initialize(Long sId, Long casoId){
		this.casoId = casoId;
		this.initialize(sId);
	}

	public void initialize(Long sId) {
		
		
		logger.debug("*** INIZIO SchedaValiditaBAseBean.initialize ....sId=" + sId);

		caricaValoriCombo();
		
		soggettoId = sId;
		
		if(soggettoId != null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(soggettoId);
			dto.setObj2(getTypeClass());
			
			listaComponenti = new ArrayList();
			listaComponentiToRemove = new ArrayList();
			List<?> listaCs = schedaService.findCsBySoggettoId(dto);
			
			for(Object cs: listaCs){
				
				logger.debug("*** INIZIO chiamata getComponenteFromCs da SchedaValiditaBAseBean.initialize");
				ValiditaCompBaseBean comp = getComponenteFromCs(cs);
				valorizzaComboComp(comp);
				listaComponenti.add(comp);
				logger.debug("*** FINE chiamata getComponenteFromCs da SchedaValiditaBAseBean.initialize");
				
			}
			
			if(listaComponenti.size() > 0)
				currentIndex = 0;
		}
		
		logger.debug("*** FINE SchedaValiditaBAseBean.initialize ....");
	}
	
	public abstract Object getTypeClass();
			
	public void nuovo() {
		currentIndex = 0;
	}
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getNuovoAttiva() {
		return nuovoAttiva;
	}

	public void setNuovoAttiva(String nuovoAttiva) {
		this.nuovoAttiva = nuovoAttiva;
	}
	
	public boolean salva() {
		boolean salvato = true;
		try{
			
			if(!validaNumeroSituazioniAperte()) {
				return false;
			}	
			
			if(!validaComponenti())
				return false;
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);			
			 getTypeClass().toString().indexOf("CsADatiSociali");
			 getTypeClass().toString().contains("CsADatiSociali");
			//Rimuovo
			for(ValiditaCompBaseBean comp: listaComponentiToRemove){	
				dto.setObj(comp.getId());
				dto.setObj2(getTypeClass());
				
				schedaService.eliminaCsById(dto);
				eliminaJsonCollegati(comp);
			}
			
			//Salvo
			for(ValiditaCompBaseBean comp: listaComponenti) {
				
				//accede alla validazione solo quella attiva
				//ovvero quella con data finale = 31/12/9999
				//data fine == null quando si sta inserendo una NUOVA
				if((comp.getDataFine() == null || DataModelCostanti.END_DATE.equals(comp.getDataFine())) && !validaCs(comp))
					return false;
				
				Object cs = getCsFromComponente(comp);
				cs = salvaJsonCollegati(comp, cs); 
				
				dto.setObj(cs);
				if(comp.getId() != null)
					schedaService.updateCsA(dto);
				else 
					schedaService.saveCsA(dto);
		}
			modificheNonSalvate=false;	
			initialize(soggettoId);
	
		} catch(Exception e) {
			salvato = false;
			logger.error(e.getMessage(),e);
			addErrorFromProperties("salva.error"+this.getCodiceTab());
		}
		
		return salvato;
		
	}
		
	public void salvaCs(Long id) {
		
		try{

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);			
			
			ValiditaCompBaseBean comp = listaComponenti.get(currentIndex);
			
			Object cs = getCsFromComponente(comp);
			cs = salvaJsonCollegati(comp, cs); 
			
			dto.setObj(cs);
			if(comp.getId() != null)
				schedaService.updateCsA(dto);
			else schedaService.saveCsA(dto);
			
			initialize(soggettoId);
			
			addInfoFromProperties("chiudi.ok");
		
		} catch(Exception e) {
			addErrorFromProperties("chiudi.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public void chiudiCs(Long id) {
		
		try{

			
			ValiditaCompBaseBean comp = listaComponenti.get(currentIndex);
			
			//addInfoFromProperties("chiudi.ok");
		
		} catch(Exception e) {
			addErrorFromProperties("chiudi.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public Object salvaJsonCollegati(ValiditaCompBaseBean obj, Object cs){return cs;}
	public ValiditaCompBaseBean duplicaJsonCollegati(ValiditaCompBaseBean objo, ValiditaCompBaseBean objd){return objd;}
	public void eliminaJsonCollegati(ValiditaCompBaseBean obj){}

	public abstract String getCodiceTab();
	
	public void elimina() {
		
		try{
			
			ValiditaCompBaseBean comp = listaComponenti.get(currentIndex);
			
			if(comp.getId() != null){
				//SPOSTATO IN FASE DI SALVATAGGIO FINALE DEL TAB
			/*	BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(comp.getId());
				dto.setObj2(getTypeClass());
				
				schedaService.eliminaCsById(dto);
				eliminaJsonCollegati(comp);          

				initialize(soggettoId); */
				
				listaComponentiToRemove.add(comp);
			}
			listaComponenti.remove(currentIndex);
			
			//addInfoFromProperties("elimina.ok");
			
		} catch(Exception e) {
			addErrorFromProperties("elimina.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public void copia() {
		
		try{
			
			ValiditaCompBaseBean comp = listaComponenti.get(currentIndex);
			Object cs = getCsFromComponenteCopy(comp);
			ValiditaCompBaseBean newComp = getComponenteFromCs(cs);
			//Sovrascrivo i JSON valorizzati dal data model
			newComp = duplicaJsonCollegati(comp,newComp);
			
			newComp.setDataInizio(new Date());
			newComp.setDataFine(null);
			newComp.setId(null);
			listaComponenti.add(0, newComp);
			currentIndex = 0;
			
			addInfoFromProperties("operazione.ok");
			
		} catch(Exception e) {
			addErrorFromProperties("operazione.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public boolean validaCs(ValiditaCompBaseBean comp) {
		if (this.validaComponenti()) {
			RequestContext.getCurrentInstance().addCallbackParam("canClose", true);
			return true;
		} else {
			RequestContext.getCurrentInstance().addCallbackParam("canClose", false);
			return false;
		}
	}
	
	public boolean validaComponenti() {
		return true;
	}
	
	//Da sovrascrivere se richiesto un comportamento diverso (es.Tab Parenti per ogni componente ha delle liste di oggetti che vanno re-istanziati)
	public Object getCsFromComponenteCopy(Object obj) {
		return getCsFromComponente(obj);
	}	
	
	public abstract Object getCsFromComponente(Object obj);
	
	public abstract ValiditaCompBaseBean getComponenteFromCs(Object obj);
			
	public List<ValiditaCompBaseBean> getListaComponenti() {
		return listaComponenti;
	}

	public void setListaComponenti(List<ValiditaCompBaseBean> listaComponenti) {
		this.listaComponenti = listaComponenti;
	}

	public Long getSoggettoId() {
		return soggettoId;
	}

	public void setSoggettoId(Long soggettoId) {
		this.soggettoId = soggettoId;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	protected int getNumeroSituazioniAperte() {
		int count=0;
		
		for (ValiditaCompBaseBean comp : this.listaComponenti)
			if (comp.getDataFine()==null || !comp.isFinito())
				count++;
		
		return count;
	}
	
	public abstract String getNomeTab();

	protected abstract boolean validaNumeroSituazioniAperte();
	protected abstract void caricaValoriCombo();
	protected abstract void valorizzaComboComp(ValiditaCompBaseBean comp);

	public boolean isModificheNonSalvate() {
		return modificheNonSalvate;
	}

	public void setModificheNonSalvate(boolean modificheNonSalvate) {
		this.modificheNonSalvate = modificheNonSalvate;
	}
	
}
