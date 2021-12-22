package it.webred.cs.csa.web.manbean.redditoCittadinanza;

import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.SearchRdCDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.view.CsRdcAnagraficaGepi;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.ejb.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyListaReddCittadinanza extends LazyDataModel<DatiRdC> {
     
	private static final long serialVersionUID = 1L;
	
	@Override
    public DatiRdC getRowData(String rowKey) { 
		//TODO
        return null;
    }
 
    @Override
    public Object getRowKey(DatiRdC scheda) {
        return scheda.getId();
    }
 
    @Override
    public List<DatiRdC> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
        List<DatiRdC> data = new ArrayList<DatiRdC>();
 
		SearchRdCDTO dto = new SearchRdCDTO();
	
		CsUiCompBaseBean.fillEnte(dto);
		dto.setFirst(first);
		dto.setPageSize(pageSize);
			
		try {
			
			AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
			AccessTableIterStepSessionBeanRemote iterSessionBean = (AccessTableIterStepSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
		
			//**Get filters*//
			 String cf=(String) filters.get("cf");
			 String cognome = (String) filters.get("cognome");
			 String nome = (String) filters.get("nome");
			 
			 dto.setCf(cf);
			 dto.setCognome(cognome);
			 dto.setNome(nome);
			 
			CsOOperatoreSettore opSettore = (CsOOperatoreSettore) CsUiCompBaseBean.getSession().getAttribute("operatoresettore");
			dto.setEnte(opSettore.getCsOSettore().getCsOOrganizzazione().getCodCatastale());
			
			List<CsRdcAnagraficaGepi> list = soggettoService.getAnagraficheRdCGepi(dto);
			 
		
			BaseDTO bDto = new BaseDTO();
			CsUiCompBaseBean.fillEnte(bDto);
			
			for(CsRdcAnagraficaGepi s: list){
				try {
					
	        		DatiRdC scheda = new DatiRdC(s);
	        		
	        		CsASoggettoLAZY soggetto = s.getCsASoggetto();

					// info stato caso
	        		if(soggetto != null) {
	        			CsACaso caso = soggetto.getCsACaso();
						IterInfoStatoMan casoInfo = new IterInfoStatoMan();
						
						it.webred.cs.csa.ejb.dto.BaseDTO itDto = new it.webred.cs.csa.ejb.dto.BaseDTO();
						CsUiCompBaseBean.fillEnte(itDto);
						itDto.setObj(caso.getId());
						CsIterStepByCasoDTO itStep = iterSessionBean.getLastIterStepByCasoDTO(itDto);
						
						if( itStep != null )
							casoInfo.initialize( itStep);
						scheda.setLastIterStepInfo(casoInfo);
	        		}
	        		
	        		data.add(scheda);
	        		
				} catch (Exception e) {
					CsUiCompBaseBean.logger.error("BENEFICIARIO RDC: " + s.getCf() + e.getMessage(), e);
				}
	        }
			
	        //rowCount
			Integer dataSize;
			dataSize = soggettoService.getAnagraficheRdCGepiCount(dto);
			this.setRowCount(dataSize);
		
		} catch (Exception e) {
			CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		} catch (Throwable e1) {
			CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
			CsUiCompBaseBean.logger.error(e1.getMessage(), e1);
		}
		
		return data;
    }
	  
}
