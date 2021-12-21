package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.cartella.ValiditaDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiValiditaGestione;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.dto.utility.KeyValuePairBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.primefaces.context.RequestContext;

public abstract class DatiValGestioneMan extends CsUiCompBaseBean implements IDatiValiditaGestione {

	protected Integer maxActiveComponents = new Integer(1);
	
	@SuppressWarnings("rawtypes")
	protected List<KeyValuePairBean> lstItems;
	
	protected String itemSelezionato;
	
	protected List<ValiditaCompBaseBean> lstComponentsActive = new ArrayList<ValiditaCompBaseBean>();
	
	protected List<ValiditaCompBaseBean> lstComponents = new ArrayList<ValiditaCompBaseBean>();
	
	protected List<ValiditaCompBaseBean> lstComponentsOld = new ArrayList<ValiditaCompBaseBean>();
		
	protected ValiditaCompBaseBean compSelezionato = new ValiditaCompBaseBean();
	
	protected Integer indexSelezionato;
	
	protected String warningMessage;
	
	public DatiValGestioneMan(){
		init();
	}
	
	private void init(){
	    lstItems = new ArrayList<KeyValuePairBean>();
		List<KeyValueDTO> lst = this.loadListItems();
		if(lst!=null){
			for(KeyValueDTO kv : lst){
				if(kv.isAbilitato()){	
					lstItems.add(new KeyValuePairBean(kv.getCodice(), kv.getDescrizione()));
				}
			}
		}
	}
	
	protected abstract List<KeyValueDTO> loadListItems();
	
	public void gestisci() {
		lstComponentsOld = copyCompList(lstComponents);
	}
	
	protected String getDescrizioneTipo(){return "";}
	
	protected abstract List<KeyValuePairBean> getDettaglioSelezione(Long id);
	
	public void addDettagliSelezione(ValiditaCompBaseBean comp){
		List<KeyValuePairBean> lst = getDettaglioSelezione(comp.getId());
		if(lst!=null && !lst.isEmpty())
			comp.setInfoAggiuntive(lst);
	}
	
	public void aggiungiSelezionato() {
		
		try{
			
			if(itemSelezionato != null && !"".equals(itemSelezionato)){

				String[] str = itemSelezionato.split("\\|");
				Date dataInizio = (str.length>2 && str[2]!=null && !str[2].trim().isEmpty()) ? ddMMyyyy.parse(str[2].trim()) : new Date();
				Date dataFine   = (str.length>3 && str[3]!=null && !str[3].trim().isEmpty()) ? ddMMyyyy.parse(str[3].trim()) : DataModelCostanti.END_DATE;
				ValiditaCompBaseBean comp = new ValiditaCompBaseBean();
				comp.setId(new Long(str[0]));
				comp.setTipo(this.getDescrizioneTipo());
				comp.setDescrizione(str[1]);
				comp.setDataInizio(dataInizio);
				comp.setDataFine(dataFine);
				comp.setPrevalente(false);
				
				addDettagliSelezione(comp);
				
				if(checkExists(comp))
					addWarning("L'elemento selezionato è già presente", "");
				else lstComponents.add(comp);
			} else
				addWarning("Seleziona un elemento", "");
			
		} catch (Exception e) {
			addError("Errore durante l'inserimento dell'elemento selezionato", null);
			logger.error(e.getMessage(),e);
		}
	}
	
	public void chiudiSelezionato() {

		ValiditaCompBaseBean comp = lstComponents.get(indexSelezionato);
		comp.setDataFine(compSelezionato.getDataTemp());
		comp.setPrevalente(false);

	}
	//SISO-1127  Inizio
	public void aggiornaDataInizioSelezionato() {

		ValiditaCompBaseBean comp = lstComponents.get(indexSelezionato);
		comp.setDataInizio(compSelezionato.getDataTemp());
		comp.setPrevalente(false);

	}
	//SISO-1127 DataFine
	public void eliminaSelezionato() {		
		
		lstComponents.remove(indexSelezionato.intValue());
	
	}
	
	public boolean salva() {
		itemSelezionato = null;
		List<ValiditaCompBaseBean> lstTempActive = getActiveList();
		
		boolean ok = true;
		if(lstTempActive.size() > maxActiveComponents) {
			addError("Superato il numero di elementi attivi, massimo: " + maxActiveComponents, null);
			ok = false;
		}
		
		if(ok)
			lstComponentsActive = lstTempActive;
		
		RequestContext.getCurrentInstance().addCallbackParam("saved", ok);
		return ok;
	}
	
	public List<ValiditaCompBaseBean> getActiveList() {
		
		List<ValiditaCompBaseBean> lstTempActive = new ArrayList<ValiditaCompBaseBean>();
		
		for(ValiditaCompBaseBean comp: lstComponents) {
			if(comp.isAttivo())
				lstTempActive.add(comp);
		}
		
		return lstTempActive;
	}
	
	public void reset() {
		itemSelezionato = null;
		lstComponents = copyCompList(lstComponentsOld);
	}
	
	private List<ValiditaCompBaseBean> copyCompList(List<ValiditaCompBaseBean> listaFrom) {
		
		List<ValiditaCompBaseBean> listaTo = new ArrayList<ValiditaCompBaseBean>();
		if(listaFrom != null){
			for(ValiditaCompBaseBean comp: listaFrom){
				
				ValiditaCompBaseBean newComp = new ValiditaCompBaseBean();
				newComp.setDataFine(comp.getDataFine());
				newComp.setDataInizio(comp.getDataInizio());
				newComp.setDescrizione(comp.getDescrizione());
				newComp.setId(comp.getId());
				newComp.setPrevalente(comp.isPrevalente());
				addDettagliSelezione(newComp);
				listaTo.add(newComp);
				
			}
		}
		
		return listaTo;
		
	}
	
	protected boolean checkExists(ValiditaCompBaseBean comp) {
		for(ValiditaCompBaseBean el: lstComponents) {
			if(el.getId()!=null && el.getId().equals(comp.getId()) && el.isAttivo())
				return true;
		}
		return false;
	}

	public List<ValiditaCompBaseBean> getLstComponents() {
		if (lstComponents != null) {
			Collections.sort(lstComponents, new compComparator());
		}
		return lstComponents;
	}

	public void setLstComponents(List<ValiditaCompBaseBean> lstComponents) {
		this.lstComponents = lstComponents;
	}

	public ValiditaCompBaseBean getCompSelezionato() {
		return compSelezionato;
	}

	public void setCompSelezionato(ValiditaCompBaseBean compSelezionato) {
		this.compSelezionato = compSelezionato;
	}

	@SuppressWarnings("rawtypes")
	public List<KeyValuePairBean> getLstItems(){
		return lstItems;
	};
	
	@SuppressWarnings("rawtypes")
	public void setLstItems(List<KeyValuePairBean> lstItems) {
		this.lstItems = lstItems;
	}

	public String getItemSelezionato() {
		return itemSelezionato;
	}

	public void setItemSelezionato(String itemSelezionato) {
		this.itemSelezionato = itemSelezionato;
	}

	public Integer getIndexSelezionato() {
		return indexSelezionato;
	}

	public void setIndexSelezionato(Integer indexSelezionato) {
		this.indexSelezionato = indexSelezionato;
	}

	public List<ValiditaCompBaseBean> getLstComponentsActive() {
		return lstComponentsActive;
	}

	public void setLstComponentsActive(
			List<ValiditaCompBaseBean> lstComponentsActive) {
		this.lstComponentsActive = lstComponentsActive;
	}
	
	public Integer getMaxActiveComponents() {
		return maxActiveComponents;
	}

	public void setMaxActiveComponents(Integer maxActiveComponents) {
		this.maxActiveComponents = maxActiveComponents;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}


	public class compComparator implements Comparator<ValiditaCompBaseBean> {
	    @Override
	    public int compare(ValiditaCompBaseBean o1, ValiditaCompBaseBean o2) {
	        Date dtIni1 = o1.getDataInizio();
	        if (dtIni1 == null) {
	        	try {
	        		dtIni1 = DataModelCostanti.END_DATE;
	        	} catch (Exception e) {}
	        }
	        Date dtIni2 = o2.getDataInizio();
	        if (dtIni2 == null) {
	        	try {
	        		dtIni2 = DataModelCostanti.END_DATE;
	        	} catch (Exception e) {}
	        }
	        Date dtFin1 = o1.getDataFine();
	        if (dtFin1 == null) {
	        	try {
	        		dtFin1 = DataModelCostanti.END_DATE;
	        	} catch (Exception e) {}
	        }
	        Date dtFin2 = o2.getDataFine();
	        if (dtFin2 == null) {
	        	try {
	        		dtFin2 = DataModelCostanti.END_DATE;
	        	} catch (Exception e) {}
	        }
	        if (dtFin2.compareTo(dtFin1) == 0) {
	        	return dtIni2.compareTo(dtIni1);
	        }
	        return dtFin2.compareTo(dtFin1);
	    }
	}
	
	public List<ValiditaDTO> riversaLista(){
		List<ValiditaDTO> lista = new ArrayList<ValiditaDTO>();
		for(ValiditaCompBaseBean comp: getLstComponents()) {
			ValiditaDTO cs = new ValiditaDTO();
			cs.setDataInizio(comp.getDataInizio());
			cs.setDataFine(comp.getDataFine());
			cs.setId(comp.getId());
			lista.add(cs);
		}
		return lista;
	}
	
}
