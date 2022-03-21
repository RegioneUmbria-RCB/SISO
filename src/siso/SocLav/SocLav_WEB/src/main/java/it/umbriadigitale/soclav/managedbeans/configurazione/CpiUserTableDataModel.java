package it.umbriadigitale.soclav.managedbeans.configurazione;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.umbriadigitale.soclav.model.RdCUser;
import it.umbriadigitale.soclav.model.RdCUserCpi;
import it.umbriadigitale.soclav.model.anpal.RdCTbCpi;
import it.umbriadigitale.soclav.service.interfaccia.IConfigurazioneService;

@Component
public class CpiUserTableDataModel extends LazyDataModel<RdCUserCpi> implements SelectableDataModel<RdCUserCpi> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IConfigurazioneService confService;

	private RdCUser selectedUser;
	
	  @Override
	  public List<RdCUserCpi> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
	   if(this.selectedUser!=null) {
		   this.setRowCount(this.selectedUser.getRdcCpi().size());
		  return this.selectedUser.getRdcCpi();
	   }
	   else {
		   this.setRowCount(0);
		   return null;
	   }
	  }
	
	@Override
	public RdCUserCpi getRowData(String arg) {
		for(RdCUserCpi uc : this.getWrappedData()) {
			if(uc.getId().getCodCpi().equalsIgnoreCase(arg))
				return uc;
		}
		return null;
	}

	@Override
	public Object getRowKey(RdCUserCpi arg){
		return arg.getId().getCodCpi();
	}

	public IConfigurazioneService getConfService() {
		return confService;
	}


	public void setConfService(IConfigurazioneService confService) {
		this.confService = confService;
	}

	public RdCUser getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(RdCUser selectedUser) {
		this.selectedUser = selectedUser;
	}

}
