package it.webred.cs.csa.web.manbean.schedaSegr;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.csa.ejb.dto.udc.DatiSchedaAccessoDTO;
import it.webred.cs.csa.ejb.dto.udc.ListaDatiSchedaUdC;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.LazyDataModel;

public abstract class LazyListaSchedeSegrBaseModel extends LazyDataModel<SchedaSegr> {
     
	private static final long serialVersionUID = 1L;
	private String[] selectedProvenienza;
	
	private AccessTableSchedaSegrSessionBeanRemote schedaSegrService = (AccessTableSchedaSegrSessionBeanRemote)CsUiCompBaseBean.getCarSocialeEjb("AccessTableSchedaSegrSessionBean");
	
    protected SchedaSegrDTO getFilterCondition(Map filters,  SchedaSegrDTO dto){
		//**Get filters*//
		 String dataAccesso=(String) filters.get("dataAccesso");
		 String operatore=(String) filters.get("operatore");
		 String ufficio=(String) filters.get("ufficio");
		 String soggSegnalante=(String) filters.get("soggSegnalato");
		 String cfSegnalato=(String) filters.get("cfSegnalato");
		 String tipoIntervento=(String) filters.get("tipoIntervento");
		 String categoriaSociale = (String) filters.get("categoriaSociale");
		 
		 dto.setDataAccesso(dataAccesso);
		 dto.setOperatore(operatore);
		 dto.setSoggettoSegnalato(soggSegnalante);
		 dto.setTipoIntervento(tipoIntervento);
		 dto.setUfficio(ufficio);
		 dto.setCategoriaSociale(categoriaSociale);
		 dto.setCf(cfSegnalato);
		 
		// SISO-938 nuovo filtro in view - NB: tipoScheda è CS_SS_SCHEDA_SEGR.PROVENIENZA
		 if(this.selectedProvenienza!=null && this.selectedProvenienza.length>0)
			 dto.setLstProvenienza(this.selectedProvenienza);
		
		 return dto;			
    }
		  

	@Override
    public SchedaSegr getRowData(String rowKey) { 
		//TODO
        return null;
    }
 
    @Override
    public Object getRowKey(SchedaSegr schedaSegr) {
        return schedaSegr.getId();
    }
    
	private void verificaAnagraficaUpdateCsScheda(AccessTableSoggettoSessionBeanRemote soggettoService,
			AccessTableSchedaSegrSessionBeanRemote schedaSegrService, it.webred.cs.csa.ejb.dto.BaseDTO csDto,
			CsSsSchedaSegr csScheda) {

		CsASoggettoLAZY csSogg = soggettoService.getSoggettoByCF(csDto);

		if (csSogg != null) {
			if (csScheda.getFlgEsistente() == null || !csScheda.getFlgEsistente()) {
				csScheda.setFlgEsistente(true);
				csDto.setObj(csScheda);
				schedaSegrService.updateSchedaSegr(csDto);
			}
			if (csScheda.getCsASoggetto() == null) {
				csScheda.setCsASoggetto(csSogg);
			}
		}
	}
	
	protected List<SchedaSegr> loadListaSchedeUDC(SchedaSegrDTO dto, boolean showStatoCartella){
		List<SchedaSegr> data = new ArrayList<SchedaSegr>();
		try{ 
			dto.setLoadStatoIterCartella(showStatoCartella);
			List<ListaDatiSchedaUdC> list = schedaSegrService.getListaSchedeUdC(dto);
			
			for(ListaDatiSchedaUdC res: list){
				SchedaSegr schedaSegr = new SchedaSegr();
				DatiSchedaAccessoDTO udc = res.getUdc();
				try {
					BeanUtils.copyProperties(schedaSegr, udc);
					
					// info stato caso
					schedaSegr.setShowStatoCartella(showStatoCartella);
					if(showStatoCartella && res.getIterCaso()!=null){ 
						IterInfoStatoMan casoInfo = new IterInfoStatoMan();
						casoInfo.initialize(res.getIterCaso());
						schedaSegr.setLastIterStepInfo(casoInfo);
					}
					
					data.add(schedaSegr);
					
					
				} catch (Exception e) {
					String msgSchedaId = String.format("CS_SS_SCHEDA_SEGR [ID:%s - SCHEDA_ID:%s - PROVENIENZA:%s]: ",
							udc.getCsSsId(),
							udc.getId(),
							udc.getProvenienza());
					
					CsUiCompBaseBean.logger.error(msgSchedaId + e.getMessage(), e);
				}
	        }
			
	        //rowCount
			this.setRowCount(schedaSegrService.getListaSchedeUdcCount(dto));
		} catch (Throwable e) {
			
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		return data;
	}
	
	public String[] getSelectedProvenienza() {
		return selectedProvenienza;
	}

	public void setSelectedProvenienza(String[] selectedProvenienza) {
		this.selectedProvenienza = selectedProvenienza;
	}
}
