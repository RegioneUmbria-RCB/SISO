package it.webred.ss.web.bean.lista.incomplete;

import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.DatiSchedaListDTO;
import it.webred.ss.ejb.dto.SsSearchCriteria;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.lista.LazySchedeUdcDataModel;
import it.webred.ss.web.bean.lista.Scheda;
import it.webred.ss.web.bean.util.PreselPuntoContatto;
import it.webred.ss.web.bean.util.UserBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

public class LazySchedeIncompleteDataModel extends LazySchedeUdcDataModel {
	
	private static final long serialVersionUID = 1L;
	private List<Object> ufficiAbilitati;
	
	
	 @Override
	 public List<Scheda> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
		  SegretariatoSocBaseBean.logger.debug("*** Load List<Scheda> " + new Date());
			PreselPuntoContatto pContMan = (PreselPuntoContatto)SegretariatoSocBaseBean.getBeanReference("preselPuntoContatto");
	        List<Scheda> schede = new ArrayList<Scheda>();
	    	
		    SsSearchCriteria criteria = new SsSearchCriteria();
		    SegretariatoSocBaseBean.fillEnte(criteria);
		    criteria.setOrganizzazioneId(pContMan.getPuntoContatto().getOrganizzazione().getId());
		    criteria.setUfficioId(pContMan.getPuntoContatto().getUfficio().getId());
		    criteria = this.getFilterCondition(filters, criteria);
	        
	        schede = this.getListaSchedeIncomplete(first, pageSize, sortField, sortOrder, criteria);
	        	
	        return schede;
	  }
	  
	  private List<Scheda> getListaSchedeIncomplete(int first, int pageSize, String sortField, SortOrder sortOrder, SsSearchCriteria criteria) {  
		List<Scheda> schede = new ArrayList<Scheda>();
    	try {
    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
    			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			

			criteria.setFirst(first);
			criteria.setPageSize(pageSize);
			
        	Long size = schedaService.countSchedeIncomplete(criteria);
			this.setRowCount(size.intValue());
			
			List<DatiSchedaListDTO> results = schedaService.searchSchedeIncomplete(criteria);
        	
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

	
	public List<Object> getUfficiAbilitati() {
		return ufficiAbilitati;
	}

	public void setUfficiAbilitati(List<Object> ufficiAbilitati) {
		this.ufficiAbilitati = ufficiAbilitati;
	}

}
