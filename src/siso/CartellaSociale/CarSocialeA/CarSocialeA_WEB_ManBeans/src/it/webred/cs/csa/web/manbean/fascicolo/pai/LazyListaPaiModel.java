package it.webred.cs.csa.web.manbean.fascicolo.pai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.pai.ListaDatiPaiDTO;
import it.webred.cs.csa.ejb.dto.pai.PaiSearchCriteria;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

public class LazyListaPaiModel extends LazyDataModel<ListaDatiPaiDTO> {
    
	private Long idCaso;
	private String cf;
	private boolean fromFascicoloCartellaUtente = false;
	private boolean accessoEsternoDatiCartella = false;
	
    @Override
    public List<ListaDatiPaiDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
        List<ListaDatiPaiDTO> data = new ArrayList<ListaDatiPaiDTO>();
       
        PaiSearchCriteria dto = new PaiSearchCriteria();
		CsUiCompBaseBean.fillEnte(dto);
		dto.setFirst(first);
		dto.setPageSize(pageSize);
			
		try {
			
			AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb( "AccessTableDiarioSessionBean");
			
			dto = this.getFilterCondition(filters, dto);
			
			// SISO-1280 Inizio
			if (fromFascicoloCartellaUtente) {
				dto.setCasoId(idCaso);
				dto.setCodiceFiscale(cf);
				data = diarioService.findListaPaiFascicolo(dto);
				setRowCount(diarioService.countListaPaiFascicolo(dto));
			} else {
				dto.setLoadListaExtCompleta(accessoEsternoDatiCartella);
				data = diarioService.findListaPaiEsterni(dto); 
				setRowCount(diarioService.countListaPaiEsterni(dto));
			}
			 
		} catch (Exception e) {
			CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		
		return data;
    }

    protected PaiSearchCriteria getFilterCondition(Map filters, PaiSearchCriteria ss){
    	String id = (String)filters.get("diarioId");
    	String tipoBeneficiario = (String)filters.get("tipoBeneficiario");
		String denominazione = (String) filters.get("denominazione");
		String tipoPaiId = (String)  filters.get("tipoPaiId");
		String daChiudere = (String) filters.get("daChiudere");
		String daControllare = (String) filters.get("daControllare");
		
		if(!StringUtils.isBlank(id)) ss.setDiarioId(new Long(id));
		ss.setTipoBeneficiario(tipoBeneficiario);
		ss.setDenominazione(denominazione);
		if(!StringUtils.isBlank(tipoPaiId)) ss.setTipoPaiId(new Long(tipoPaiId));
		if(!StringUtils.isBlank(daChiudere))
			ss.setDaChiudere("true".equalsIgnoreCase(daChiudere));
		if(!StringUtils.isBlank(daControllare))
			ss.setDaControllare("true".equalsIgnoreCase(daControllare));
	  return ss;
  }
    
	public Long getIdCaso() {
		return idCaso;
	}

	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public boolean isFromFascicoloCartellaUtente() {
		return fromFascicoloCartellaUtente;
	}

	public void setFromFascicoloCartellaUtente(boolean fromFascicoloCartellaUtente) {
		this.fromFascicoloCartellaUtente = fromFascicoloCartellaUtente;
	}

	public boolean isAccessoEsternoDatiCartella() {
		return accessoEsternoDatiCartella;
	}

	public void setAccessoEsternoDatiCartella(boolean accessoEsternoDatiCartella) {
		this.accessoEsternoDatiCartella = accessoEsternoDatiCartella;
	}    
	
	@Override
    public ListaDatiPaiDTO getRowData(String rowKey) { 
		//TODO
        return null;
    }
  
    @Override
    public Object getRowKey(ListaDatiPaiDTO dati) {
        return dati.getDiarioId();
    }
}
