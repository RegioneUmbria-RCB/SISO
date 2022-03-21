package it.umbriadigitale.soclav.managedbeans.sociale;

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
import it.umbriadigitale.soclav.service.dto.DomandaRdCDTO;
import it.umbriadigitale.soclav.service.dto.gepi.GePiDomandaDTO;
import it.umbriadigitale.soclav.service.interfaccia.IAmbitiSocialeService;
import it.umbriadigitale.soclav.service.interfaccia.IUserService;

@Component
public class LazyListaRdCSociale extends LazyDataModel<GePiDomandaDTO>  {

	private static final long serialVersionUID = 1L;

	public final Logger logger = Logger.getLogger("SocLav");
	
	@Autowired
	IAmbitiSocialeService socService; // = (ICentriImpiegoService) getEjb("SocLav", "SocLav_WEB", "CentriImpiegoServiceImpl");
	
	@Autowired
	IUserService userService;
	
    @Override
    public List<GePiDomandaDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		
		
    	 com.github.adminfaces.starter.infra.model.SortOrder order = null;
         if (sortOrder != null) {
             order = sortOrder.equals(SortOrder.ASCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.ASCENDING
                     : sortOrder.equals(SortOrder.DESCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.DESCENDING
                     : com.github.adminfaces.starter.infra.model.SortOrder.UNSORTED;
         }
        
         logger.debug("*** Load List<RdCGePIBeneficiario> "+ new Date()); 
        
        AutenticazioneBean bean = (AutenticazioneBean)BaseBean.getBeanReference("autenticazioneBean");
        bean.getCetUser().getCurrentEnte();
        List<String> entiAbilitati = new ArrayList<String>();
        if(BaseBean.isVisualizzaListaCompletaRdC())
        	entiAbilitati.add("ALL");
        else {
        	entiAbilitati.add(bean.getCetUser().getCurrentEnte());
        	List<String> enti = userService.findEntiCompetenzaByUsername(bean.getCetUser().getUsername());
        	entiAbilitati.addAll(enti);
        }	
     	 
        List<GePiDomandaDTO> domande = socService.search(entiAbilitati, first, pageSize);
 		this.setRowCount(socService.count(entiAbilitati, first, pageSize));
 		
        return domande;
    }

	
	  @Override 
	  public GePiDomandaDTO getRowData(String rowKey) {
		  if(!StringUtils.isBlank(rowKey)) { 
			  String[] key = rowKey.split("@"); 
		  //return cpiService.find(key[0] , key[1]); 
		  } 
		  return null; 
	  }
	  
	  @Override 
	  public Object getRowKey(GePiDomandaDTO datiCaso) { 
		  return datiCaso.getProtocolloINPS()+"@"+datiCaso.getCfRichiedente(); 
	   }
	 
	
    
	
}
