package it.webred.ss.web.bean.lista;

import it.webred.ss.ejb.dto.SsSearchCriteria;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;

public abstract class LazySchedeUdcDataModel extends LazyDataModel<Scheda> implements SelectableDataModel<Scheda> {
	
	private static final long serialVersionUID = 1L;
	private Date fDataAccesso;
	
	@Override
	public Scheda getRowData(String arg) {
		List<Scheda> schede = (List<Scheda>) getWrappedData();
		for(Scheda s: schede)
			if(arg.equals(getRowKey(s)))
				return s;
		
		return null;
	}

	@Override
	public Object getRowKey(Scheda arg) {
		return arg.getId();
	}
	
	protected SsSearchCriteria getFilterCondition(Map filters,  SsSearchCriteria ss){
	  
		String fIntervento = (String) filters.get("intervento");
		String fSegnalato = (String)  filters.get("segnalato");
		String fCF = (String)  filters.get("cf");
		String fOperatore = (String)  filters.get("operatore");
		String fPuntoContatto = (String)  filters.get("puntoContatto");
		
		if(fIntervento!=null)
			ss.setTipoScheda(new Integer(fIntervento));
		
		if(fSegnalato!=null)
			ss.setSegnalato(fSegnalato);
		
		if(fOperatore!=null)
			ss.setOperatoreUserName(fOperatore);
		
		if(fPuntoContatto!=null)
			ss.setpContattoId(new Long(fPuntoContatto));
		
		if(this.fDataAccesso!=null)
			ss.setDataAccesso(this.fDataAccesso);
		
		if(fCF!=null)
			ss.setCf(fCF);
		
	  return ss;
  }
	
	public Date getfDataAccesso() {
		return fDataAccesso;
	}
	
	public void setfDataAccesso(Date fDataAccesso) {
		this.fDataAccesso = fDataAccesso;
	}
	
}
