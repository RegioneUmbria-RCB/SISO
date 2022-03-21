package it.umbriadigitale.soclav.managedbeans.configurazione;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import it.umbriadigitale.soclav.model.RdCUser;
import it.umbriadigitale.soclav.service.interfaccia.IUserService;

@Component
public class LazyListaUtenti extends LazyDataModel<RdCUser>  {

	private static final long serialVersionUID = 1L;

	public final Logger logger = Logger.getLogger("SocLav");
	
	@Autowired
	IUserService userService;
	
    @Override
    public List<RdCUser> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		
		
    	 com.github.adminfaces.starter.infra.model.SortOrder order = null;
         if (sortOrder != null) {
             order = sortOrder.equals(SortOrder.ASCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.ASCENDING
                     : sortOrder.equals(SortOrder.DESCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.DESCENDING
                     : com.github.adminfaces.starter.infra.model.SortOrder.UNSORTED;
         }
        
         logger.debug("*** Load List<RdCUSer> "+ new Date()); 
         	 
        Page<RdCUser> utenti = userService.findAllUsers(first, pageSize);
 		this.setRowCount((int)userService.countAllUsers());
 		
        return utenti.getContent();
    }

	
	  @Override 
	  public RdCUser getRowData(String rowKey) {
		  if(!StringUtils.isBlank(rowKey)) { 
			  String[] key = rowKey.split("@"); 
			  //return cpiService.find(key[0] , key[1]); 
		  } 
		  return null; 
	  }
	  
	  @Override 
	  public Object getRowKey(RdCUser utente) { 
		  return utente.getUsername()+"@"+utente.getEnteDefault();
	   }
}
