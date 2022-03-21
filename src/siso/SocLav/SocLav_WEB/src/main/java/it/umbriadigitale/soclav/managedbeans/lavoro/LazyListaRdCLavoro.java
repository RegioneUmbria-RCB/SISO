package it.umbriadigitale.soclav.managedbeans.lavoro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.umbriadigitale.soclav.managedbeans.AutenticazioneBean;
import it.umbriadigitale.soclav.managedbeans.BaseBean;
import it.umbriadigitale.soclav.service.dto.anpal.AnpalDomandaDTO;
import it.umbriadigitale.soclav.service.interfaccia.ICentriImpiegoService;
import it.umbriadigitale.soclav.service.interfaccia.IUserService;

@Component
public class LazyListaRdCLavoro extends LazyDataModel<AnpalDomandaDTO>  {

	private static final long serialVersionUID = 1L;

	public final Logger logger = Logger.getLogger("SocLav");
	
	@Autowired
	ICentriImpiegoService cpiService; // = (ICentriImpiegoService) getEjb("SocLav", "SocLav_WEB", "CentriImpiegoServiceImpl");
	
	@Autowired
	IUserService userService;
	
    @Override
    public List<AnpalDomandaDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		
		
    	 com.github.adminfaces.starter.infra.model.SortOrder order = null;
         if (sortOrder != null) {
             order = sortOrder.equals(SortOrder.ASCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.ASCENDING
                     : sortOrder.equals(SortOrder.DESCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.DESCENDING
                     : com.github.adminfaces.starter.infra.model.SortOrder.UNSORTED;
         }
        
         logger.debug("*** Load List<RdCAnpalBeneficiario> "+ new Date()); 
        
        AutenticazioneBean bean = (AutenticazioneBean)BaseBean.getBeanReference("autenticazioneBean");
        List<String> entiAbilitati = new ArrayList<String>();
        if(BaseBean.isVisualizzaListaCompletaRdC())
        	entiAbilitati.add("ALL");
        else {
        	entiAbilitati.add(bean.getCetUser().getCurrentEnte());
        	List<String> enti = userService.findEntiCompetenzaByUsername(bean.getCetUser().getUsername());
        	entiAbilitati.addAll(enti);
        }	
     	 
        List<AnpalDomandaDTO> domande = cpiService.search(entiAbilitati, first, pageSize);
 		this.setRowCount(cpiService.count(entiAbilitati, first, pageSize));
 		
        return domande;
    }

	
	  @Override 
	  public AnpalDomandaDTO getRowData(String rowKey) {
		  if(!StringUtils.isBlank(rowKey)) { 
			  String[] key = rowKey.split("@"); 
		  //return cpiService.find(key[0] , key[1]); 
		  } 
		  return null; 
	  }
	  
	  @Override 
	  public Object getRowKey(AnpalDomandaDTO datiCaso) { 
		  return datiCaso.getProtocolloINPS()+"@"+datiCaso.getCfRichiedente(); 
	   }
	 
	
    
	
}
