package it.umbriadigitale.soclav.managedbeans.configurazione;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import it.umbriadigitale.soclav.model.anpal.RdCTbCpi;
import it.umbriadigitale.soclav.service.interfaccia.IConfigurazioneService;

@Component
public class CpiTableDataModel extends LazyDataModel<RdCTbCpi> implements SelectableDataModel<RdCTbCpi> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IConfigurazioneService confService;
	
	private String selRegione;
	private String selProvincia;
	
	public void refresh() {
		this.selRegione = null;
		this.selProvincia = null;
	}
	
	  @Override
	  public List<RdCTbCpi> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
		  if(!StringUtils.isEmpty(selRegione)) {
	    	 filters.put("regione", selRegione);
	    	 filters.put("provincia", selProvincia);
	    	 List<RdCTbCpi> lstCentriImpiego =  confService.getListaCPI(filters);
	     	this.setRowCount((int)lstCentriImpiego.size());
	     	int lastIndex = first+pageSize > lstCentriImpiego.size() ? lstCentriImpiego.size() : first+pageSize;
	     	return lstCentriImpiego.subList(first, lastIndex);
	     }else
	    	 this.setRowCount(0);
	    	 return new ArrayList<RdCTbCpi>();
	     
	  }
	

	@Override
	public RdCTbCpi getRowData(String arg) {
		return confService.findRdCTbCpi(arg);
	}

	@Override
	public Object getRowKey(RdCTbCpi arg){
		return arg.getCodice();
	}

	public List<SelectItem> getListaRegioni(){
		List<String> in = confService.getListaRegioni();
		List<SelectItem> out = new ArrayList<SelectItem>();
		this.riversa(in, out);
		return out;
	}
	
	
	public String getSelRegione() {
		return selRegione;
	}


	public void setSelRegione(String selRegione) {
		this.selRegione = selRegione;
	}


	public List<SelectItem> getListaProvince(){
		List<String> in = confService.getListaProv(selRegione);
		List<SelectItem> out = new ArrayList<SelectItem>();
		this.riversa(in, out);
		return out;
	}
	
	
	private void riversa(List<String> in, List<SelectItem> out) {
		for(String s : in)
			out.add(new SelectItem(s));
	}
	
	public void onChangeRegione() {
		this.selProvincia = null;
	}


	public IConfigurazioneService getConfService() {
		return confService;
	}


	public void setConfService(IConfigurazioneService confService) {
		this.confService = confService;
	}


	public String getSelProvincia() {
		return selProvincia;
	}


	public void setSelProvincia(String selProvincia) {
		this.selProvincia = selProvincia;
	}

}
