package it.webred.ss.web.bean.lista.complete;

import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.DatiSchedaListDTO;
import it.webred.ss.ejb.dto.SsSearchCriteria;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.lista.LazySchedeUdcDataModel;
import it.webred.ss.web.bean.lista.Scheda;
import it.webred.ss.web.bean.util.PreselPuntoContatto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

public class LazySchedeTableDataModel extends LazySchedeUdcDataModel {
	
	private static final long serialVersionUID = 1L;
	
	private Long ufficioId;
	private Long soggettoId;
	
	  @Override
	    public List<Scheda> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
		  SegretariatoSocBaseBean.logger.debug("*** Load List<Scheda> " + new Date());
			PreselPuntoContatto pContMan = (PreselPuntoContatto)SegretariatoSocBaseBean.getBeanReference("preselPuntoContatto");
	        List<Scheda> schede = new ArrayList<Scheda>();
	    	
		    SsSearchCriteria criteria = new SsSearchCriteria();
		    SegretariatoSocBaseBean.fillEnte(criteria);
		    criteria.setOrganizzazioneId(pContMan.getPuntoContatto().getOrganizzazione().getId());
		    criteria = this.getFilterCondition(filters, criteria);
	        
	        if(this.ufficioId!=null){
	        	criteria.setUfficioId(ufficioId);
	        	schede = this.populateSchedeFromUfficio(first, pageSize, sortField, sortOrder, criteria);
	        }
	        
	        if(this.soggettoId != null)
	        	schede = this.populateSchedeFromSoggetto(first, pageSize, sortField, sortOrder, criteria);
	        
	       
	        	
	        return schede;
	  }
	  
	  private List<Scheda> populateSchedeFromUfficio(int first, int pageSize, String sortField, SortOrder sortOrder, SsSearchCriteria criteria) {  
		List<Scheda> schede = new ArrayList<Scheda>();
    	try {
    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
    			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			

			criteria.setFirst(first);
			criteria.setPageSize(pageSize);
			
        	Long size = schedaService.countSchedeInUfficio(criteria);
			this.setRowCount(size.intValue());
			
			List<DatiSchedaListDTO> results = schedaService.searchSchedeInUfficio(criteria);
        	
        	BaseDTO dto = new BaseDTO();
        	SegretariatoSocBaseBean.fillEnte(dto);
        	for(DatiSchedaListDTO row: results)
        		schede.add(new Scheda(row));
    
    	} catch(Exception e) {
    		SegretariatoSocBaseBean.logger.error(e.getMessage(), e);
    		SegretariatoSocBaseBean.addError("lettura.error");
    		
		}
		 		
    	return schede;
	}
		 	
	private List<Scheda> populateSchedeFromSoggetto(int first, int pageSize, String sortField, SortOrder sortOrder,  SsSearchCriteria criteria) {  
		  List<Scheda> schede = new ArrayList<Scheda>();
	    	try {
	    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
	    			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
				
	    		BaseDTO dto = new BaseDTO();
	    		SegretariatoSocBaseBean.fillEnte(dto);
	    		dto.setObj(this.soggettoId);
	    		SsAnagrafica ana = schedaService.readAnagraficaById(dto);
	    		
	    		dto.setObj(ana.getCf());
				List<SsAnagrafica> list = schedaService.readAnagraficheByCf(dto);
	    		
				criteria.setFirst(first);
				criteria.setPageSize(pageSize);
				
				List<Long> listaAnagrafiche = new ArrayList<Long>();
				for(SsAnagrafica a: list)
					listaAnagrafiche.add(a.getId());
				
				if(!listaAnagrafiche.isEmpty()){
					criteria.setSoggettoId(listaAnagrafiche);
					
			      	Long size = schedaService.countSchedeCompleteSoggetto(criteria);
					this.setRowCount(size.intValue());
					
					List<DatiSchedaListDTO> results = schedaService.searchSchedeCompleteSoggetto(criteria);
					for(DatiSchedaListDTO row: results)
		        		schede.add(new Scheda(row));
		        		
				}else
					this.setRowCount(0);
					
				
				/*	dto.setObj(a.getId());
		        	List<SsScheda> results = schedaService.readSchedeSoggetto(dto);
		        	
		        	for(SsScheda row: results){
		        		Long ufficio = row.getAccesso().getSsRelUffPcontOrg().getSsUfficio().getId();
		        		if(this.ufficiAbilitati.contains("*") || this.ufficiAbilitati.contains(ufficio)){
		        			dto.setObj(row.getSegnalato());
		        			SsSchedaSegnalato segnalato = schedaService.readSegnalatoById(dto);
		        			schede.add(new Scheda(row, segnalato));
		        		}
		        	}
				}*/
	        	
		        
	    	} catch(Exception e) {
	    		SegretariatoSocBaseBean.logger.error(e.getMessage(), e);
	    		SegretariatoSocBaseBean.addError("lettura.error");
	    		
			}
	    	return schede;
		}

	public Long getUfficioId() {
		return ufficioId;
	}

	public Long getSoggettoId() {
		return soggettoId;
	}

	public void setUfficioId(Long ufficioId) {
		this.ufficioId = ufficioId;
	}

	public void setSoggettoId(Long soggettoId) {
		this.soggettoId = soggettoId;
	}
	    
}
