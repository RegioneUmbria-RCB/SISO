package it.webred.cs.csa.web.manbean.schedaSegr;

import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.ejb.dto.udc.DatiSchedaAccessoDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;

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
		 
		 dto.setDataAccesso(validaInput(dataAccesso));
		 dto.setOperatore(validaInput(operatore));
		 dto.setSoggettoSegnalato(validaInput(soggSegnalante));
		 dto.setTipoIntervento(validaInput(tipoIntervento));
		 dto.setUfficio(validaInput(ufficio));
		 dto.setCategoriaSociale(validaInput(categoriaSociale));
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
    
  
    private String validaInput(String s){
    	
    	if(s!=null){
    		s = s.replaceAll("'", "''");
    		s = s.trim();
    	}
    	
    	return s;
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
			List<DatiSchedaAccessoDTO> list = schedaSegrService.getSchedeSegr(dto);
			
			for(DatiSchedaAccessoDTO res: list){
				IterInfoStatoMan infoCaso = null;
				SchedaSegr schedaSegr = new SchedaSegr();
				
				try {
					BeanUtils.copyProperties(schedaSegr, res);
					
					// info stato caso
					schedaSegr.setShowStatoCartella(showStatoCartella);
					if(showStatoCartella){ 
						infoCaso = verificaStatoCaso(schedaSegr.getCasoId());
						schedaSegr.setLastIterStepInfo(infoCaso);
					}
					
					data.add(schedaSegr);
					
					
				} catch (Exception e) {
					String msgSchedaId = String.format("CS_SS_SCHEDA_SEGR [ID:%s - SCHEDA_ID:%s - PROVENIENZA:%s]: ",
							res.getCsSsId(),
							res.getId(),
							res.getProvenienza());
					
					CsUiCompBaseBean.logger.error(msgSchedaId + e.getMessage(), e);
				}
	        }
			
	        //rowCount
			this.setRowCount(schedaSegrService.getSchedeSegrCount(dto));
		} catch (Throwable e) {
			
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		return data;
	}
	
	private IterInfoStatoMan verificaStatoCaso(Long casoId) throws Exception {

		if (casoId!= null && casoId.longValue()>0) {
			AccessTableIterStepSessionBeanRemote iterSessionBean = 
					(AccessTableIterStepSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableIterStepSessionBean");
			
			IterInfoStatoMan casoInfo = new IterInfoStatoMan();

			it.webred.cs.csa.ejb.dto.BaseDTO itDto = new it.webred.cs.csa.ejb.dto.BaseDTO();
			CsUiCompBaseBean.fillEnte(itDto);
			itDto.setObj(casoId);
			CsIterStepByCasoDTO itStep = iterSessionBean.getLastIterStepByCasoDTO(itDto);

			if (itStep != null)
				casoInfo.initialize(itStep);
			return casoInfo;
		}
		return null;
	}

	

	public String[] getSelectedProvenienza() {
		return selectedProvenienza;
	}

	public void setSelectedProvenienza(String[] selectedProvenienza) {
		this.selectedProvenienza = selectedProvenienza;
	}
}
