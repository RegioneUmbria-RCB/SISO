package it.webred.cs.csa.web.manbean.scheda;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.scheda.parenti.ParentiBean;
import it.webred.cs.csa.web.manbean.scheda.parenti.ParentiComp;
import it.webred.cs.csa.web.manbean.scheda.sociali.DatiSocialiComp;
import it.webred.cs.data.model.CsADatiInvalidita;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsADisabilita;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.manbean.DatiValGestioneMan.compComparator;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.context.RequestContext;

public abstract class SchedaValiditaBaseBean extends CsUiCompBaseBean {
	
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSessionBean");
	
	protected Long soggettoId;
	protected Long casoId;
	public int currentIndex = -1;
	protected List<ValiditaCompBaseBean> listaComponenti;
	public String param;
	public String nuovoAttiva;
	public boolean flagMsg=false;
	
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

		
		soggettoId = sId;
		
		if(soggettoId != null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(soggettoId);
			dto.setObj2(getTypeClass());
			
			listaComponenti = new ArrayList();
			List<?> listaCs = schedaService.findCsBySoggettoId(dto);
			
			for(Object cs: listaCs){
				
				logger.debug("*** INIZIO chiamata getComponenteFromCs da SchedaValiditaBAseBean.initialize");
				listaComponenti.add(getComponenteFromCs(cs));
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
			
			for(ValiditaCompBaseBean comp: listaComponenti) {
				
				if(!validaCs(comp))
					return false;
				
				Object cs = getCsFromComponente(comp);
				cs = salvaJsonCollegati(comp, cs); 
				
				dto.setObj(cs);
				if(comp.getId() != null)
					schedaService.updateCsA(dto);
				else 
					schedaService.saveCsA(dto);
		}
				
			initialize(soggettoId);
	
			//addInfoFromProperties("salva.ok");
		
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
	
	public Object salvaJsonCollegati(ValiditaCompBaseBean obj, Object cs){return cs;}
	public ValiditaCompBaseBean duplicaJsonCollegati(ValiditaCompBaseBean objo, ValiditaCompBaseBean objd){return objd;}
	public void eliminaJsonCollegati(ValiditaCompBaseBean obj){}

	public abstract String getCodiceTab();
	
	public void elimina() {
		
		try{
			
			ValiditaCompBaseBean comp = listaComponenti.get(currentIndex);
			
			if(comp.getId() != null){
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(comp.getId());
				dto.setObj2(getTypeClass());
				
				schedaService.eliminaCsById(dto);
				eliminaJsonCollegati(comp);

				initialize(soggettoId);
			} else {
				listaComponenti.remove(currentIndex);
			}
			
			addInfoFromProperties("elimina.ok");
			
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
	
	protected abstract boolean validaNumeroSituazioniAperte();
	
}
