package it.webred.cs.csa.web.manbean.configurazione;

import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUserUfficio;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneEnteSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.OperatoriSearchCriteria;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.view.CsAmAnagraficaOperatore;
import it.webred.cs.jsf.bean.DatiOperatore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

public class OperatoriTableDataModel extends LazyDataModel<DatiOperatore> implements SelectableDataModel<DatiOperatore> {

	private List<SelectItem> listaEnti;
	private List<String> filtroEnti;
	private String filtroAbilitato;
	
	  @Override
	  public List<DatiOperatore> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
		  
	     List<DatiOperatore> lstOperatori;
		 UserService userService = (UserService) CsUiCompBaseBean.getEjb("AmProfiler", "AmProfilerEjb", "UserServiceBean");
		 AccessTableConfigurazioneEnteSessionBeanRemote operatoreService = (AccessTableConfigurazioneEnteSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableConfigurazioneEnteSessionBean");
		
			HashMap<String, CsOOrganizzazione> map = new HashMap<String, CsOOrganizzazione>();
			lstOperatori = new ArrayList<DatiOperatore>();
			
			OperatoriSearchCriteria criteria = getFilterCondition(filters);
			CsUiCompBaseBean.fillEnte(criteria);
			criteria.setFirst(first);
			criteria.setPageSize(pageSize);
			
			setRowCount(operatoreService.countUtentiAmPerCs(criteria));
			List<CsAmAnagraficaOperatore> listaOp = operatoreService.getUtentiAmPerCs(criteria);
			
			for(CsAmAnagraficaOperatore op: listaOp) {
				AmAnagrafica amAna = CsUiCompBaseBean.getAnagraficaByUsername(op.getUsername());
				if(amAna != null) {
					DatiOperatore datiOp = DatiOperatore.copyFromAmAnagrafica(amAna);
					datiOp.setIdOperatore(op.getOperatoreId());
					datiOp.setAbilitato(op.getAbilitato());
					
					//Informazioni Ufficio
					AmUserUfficio uff = userService.getDatiUfficio(datiOp.getAmUser().getName());
					datiOp.setUfficio(uff);
					
					
					String entiAbilitatiAm = "";
					String[] entib = op.getEnti()!=null ? op.getEnti().split("\\|") : null;
					for(String b : entib){
						CsOOrganizzazione o = null;
						if(map.containsKey(b))
							o = map.get(b);
						else{
						    BaseDTO dto = new BaseDTO();
						    CsUiCompBaseBean.fillEnte(dto);
						    dto.setObj(b);
						    o = operatoreService.getOrganizzazioneByCodFittizio(dto);
						    map.put(b, o);
						}
						
						entiAbilitatiAm+=", "+ o.getNome();
						
					}
					datiOp.setEnti(entiAbilitatiAm.replaceFirst(",", "").trim());
					
					lstOperatori.add(datiOp);
				}
			}
			
			Iterator<String> keys = map.keySet().iterator();
			listaEnti = new ArrayList<SelectItem>();
			while(keys.hasNext()){
				String s = keys.next();
				listaEnti.add(new SelectItem(s, map.get(s).getNome()));
			}
			
			return lstOperatori;
	  }
	
	protected OperatoriSearchCriteria getFilterCondition(Map filters){
		OperatoriSearchCriteria ss = new OperatoriSearchCriteria();  
		
		String cognome = (String) filters.get("cognome");
		String nome = (String)  filters.get("nome");
		String cf = (String)  filters.get("cf");
		String username = (String) filters.get("username");
		
		if(cognome!=null) ss.setCognome(cognome);
		if(nome!=null) ss.setNome(nome);
		if(cf!=null) ss.setCodiceFiscale(cf);
		if(username!=null) ss.setUsername(username);
		if(!StringUtils.isBlank(filtroAbilitato)) ss.setAbilitato("1".equalsIgnoreCase(this.filtroAbilitato));
		if(this.filtroEnti!=null && !filtroEnti.isEmpty())ss.setEnti(filtroEnti);
	  return ss;
  }
	
	@Override
	public DatiOperatore getRowData(String arg) {
		List<DatiOperatore> soggetti = (List<DatiOperatore>) getWrappedData();
		for(DatiOperatore s: soggetti)
			if(arg.equals(getRowKey(s)))
				return s;
		
		return null;
	}

	public List<SelectItem> getListaEnti() {
		return listaEnti;
	}

	public void setListaEnti(List<SelectItem> listaEnti) {
		this.listaEnti = listaEnti;
	}

	@Override
	public Object getRowKey(DatiOperatore arg){
		return arg.getAmUser().getName();
	}

	public List<String> getFiltroEnti() {
		return filtroEnti;
	}

	public void setFiltroEnti(List<String> filtroEnti) {
		this.filtroEnti = filtroEnti;
	}

	public String getFiltroAbilitato() {
		return filtroAbilitato;
	}

	public void setFiltroAbilitato(String filtroAbilitato) {
		this.filtroAbilitato = filtroAbilitato;
	}

}
