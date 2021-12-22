package it.webred.ss.web.bean;

import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.OperatoreDTO;
import it.webred.ss.web.bean.util.Ufficio;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

public class SegretariatoSocSchedeTblBaseBean extends SegretariatoSocBaseBean {
	
	protected List<SelectItem> tipiIntervento;
	protected List<SelectItem> lstOperatori;
	private List<SelectItem> ptoContt; 
	
	public List<SelectItem> getLstOperatori() {
		
		lstOperatori = new ArrayList<SelectItem>();
		lstOperatori.add(new SelectItem(""));
		try {
			
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
				
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setOrganizzazione(this.getPreselectedPContatto().getOrganizzazione().getId());
			dto.setObj(this.getPreselectedPContatto().getUfficio().getId());
			
			List<OperatoreDTO> lst = schedaService.getListaOperatori(dto);
			for(OperatoreDTO o : lst)
			  lstOperatori.add(new SelectItem(o.getUsername(), o.getDenominazione()));
			
		} catch (NamingException e) {
			logger.error(e);
		}
		
		return lstOperatori;
	}
	
	 public List<SelectItem> getTipiIntervento(){
		 if(tipiIntervento==null || tipiIntervento.isEmpty()){
			try{
				 tipiIntervento = new ArrayList<SelectItem>();
				 tipiIntervento.add(new SelectItem(""));
				 SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
							"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
						
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				List<SsTipoScheda> tipi = schedaService.readTipiScheda(dto);
				
				for(SsTipoScheda tipo: tipi)
						tipiIntervento.add(new SelectItem(tipo.getId(),tipo.getTipo()));
			 } catch(Exception e) {
		 		logger.error(e.getMessage(), e);
		 		addError("lettura.error");	
			}
		 }
		return tipiIntervento;
     	
     }
	 
	public void setTipiIntervento(List<SelectItem> tipiIntervento) {
		this.tipiIntervento = tipiIntervento;
	}
	
	public List<SelectItem> getPtoContt() {
		ptoContt = new ArrayList<SelectItem>();
		ptoContt.add(new SelectItem(""));
		Ufficio ufficio = this.getPreselectedPContatto().getUfficio();
		ptoContt.addAll(ufficio.getListaPContatto());
		
		return ptoContt;
	}
	
}
