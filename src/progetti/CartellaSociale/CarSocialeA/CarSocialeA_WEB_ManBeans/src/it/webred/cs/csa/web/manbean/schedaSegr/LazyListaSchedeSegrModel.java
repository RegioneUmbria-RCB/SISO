package it.webred.cs.csa.web.manbean.schedaSegr;

import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

public class LazyListaSchedeSegrModel extends LazyListaSchedeSegrBaseModel {
    
	private static final long serialVersionUID = 1L;

	@Override
    public List<SchedaSegr> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
		List<SchedaSegr> data = new ArrayList<SchedaSegr>();
		 
		SchedaSegrDTO dto = new SchedaSegrDTO();
	
		CsUiCompBaseBean.fillEnte(dto);
		dto.setFirst(first);
		dto.setPageSize(pageSize);
			
		try {
			
			dto = this.getFilterCondition(filters, dto);
			CsOOperatoreSettore opSettore = (CsOOperatoreSettore) CsUiCompBaseBean.getSession().getAttribute("operatoresettore");
			dto.setIdSettore(opSettore.getCsOSettore().getId());
			
			data = this.loadListaSchedeUDC(dto, true);
		
		} catch (Exception e) {
			CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		
		return data;
    }
	
}
