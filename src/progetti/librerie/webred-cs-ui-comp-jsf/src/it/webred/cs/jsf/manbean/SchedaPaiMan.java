package it.webred.cs.jsf.manbean;

/*OBSOLETA*/

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.PaiDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.Pai.PERIODO_TEMPORALE;
import it.webred.cs.data.model.*;
import it.webred.cs.jsf.interfaces.ISchedaPAI;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class SchedaPaiMan extends CsUiCompBaseBean implements ISchedaPAI {

	private CsDPai pai;
	private CsOOperatoreBASIC responsabileCaso;
	private Long idCaso;
	private String widgetVar;
	private List<SelectItem> lstTipoPai;
	private List<SelectItem> lstMotivoChiusura;

	private List<CsDRelazione> lstRelazioni;
	private List<CsIIntervento> lstInterventi;
	private List<SelectItem> lstRadioOptions;
	private List<SelectItem> lstArFfProgetti;

	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getCarSocialeEjb("AccessTableConfigurazioneSessionBean");
	private AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getCarSocialeEjb( "AccessTableInterventoSessionBean");

	
	@Override
	public void inizializzaDialog(CsDPai pai, List<CsDRelazione> relazioni, List<CsIIntervento> interventi) {
		this.pai = pai;
		this.lstRelazioni = relazioni;
		this.lstInterventi = interventi;
	}
	
	@Override
	public List<SelectItem> getLstTipoPai() {

		if (lstTipoPai == null) {
			lstTipoPai = new ArrayList<SelectItem>();
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<KeyValueDTO> lst = confService.getTipoPai(bo);
			lstTipoPai = this.convertiLista(lst);
		}

		return lstTipoPai;
	}

	@Override
	public List<SelectItem> getLstMotivoChiusura() {

		if (lstMotivoChiusura == null) {
			lstMotivoChiusura = new ArrayList<SelectItem>();
			lstMotivoChiusura.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbMotivoChiusuraPai> lst = confService.getMotivoChiusuraPai(bo);
			if (lst != null) {
				for (CsTbMotivoChiusuraPai obj : lst) {
					lstMotivoChiusura.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}
		}

		return lstMotivoChiusura;
	}

	/*
	@Override
	public void salva() {
		List<String> messages = valida();
		messages.addAll(validaCreazione());
		if (messages != null && messages.size() > 0) {
			for (String msg : messages)
				addError(msg, "");
		} else {
			save();
		}
	}

	@Override
	public void aggiorna() {
		List<String> messages = valida();
		messages.addAll(validaAggiornamento());
		if (messages != null && messages.size() > 0) {
			for (String msg : messages)
				addError(msg, "");
		} else {
			save();
		}
	}

	@Override
	public void chiudi() {
		// TODO
		List<String> messages = valida();
		messages.addAll(validaChiusura());
		if (messages != null && messages.size() > 0) {
			for (String msg : messages)
				addError(msg, "");
		} else {
			save();
		}
	}*/

	private void save() {

		PaiDTO dto = new PaiDTO(this.getPai());
		fillEnte(dto);
		dto.setCasoId(idCaso);
		dto.setResponsabileId(responsabileCaso != null ? responsabileCaso.getId() : null);

		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		dto.setOpSettore(opSettore);

		try {
			diarioService.saveSchedaPai(dto);

			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			addInfoFromProperties("salva.ok");

		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void reset() {
		this.pai = new CsDPai();
		lstRelazioni = new ArrayList<CsDRelazione>();
		lstInterventi = new ArrayList<CsIIntervento>();
	}

	@Override
	public void elimina(CsDPai pai) {

		PaiDTO dto = new PaiDTO(pai);
		fillEnte(dto);
		diarioService.deleteSchedaPai(dto);
	}

	@Override
	public void carica() {
		// TODO Auto-generated method stub

	}

	public CsDPai getPai() {
		if (pai == null)
			pai = new CsDPai();
		return pai;
	}

	public void setPai(CsDPai pai) {
		this.pai = pai;
	}

	public Long getIdCaso() {
		return idCaso;
	}

	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}

	public String getWidgetVar() {
		widgetVar = "schedaPaiDialog";
		return widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}

	public CsOOperatoreBASIC getResponsabileCaso() {
		return responsabileCaso;
	}

	public void setResponsabileCaso(CsOOperatoreBASIC responsabileCaso) {
		this.responsabileCaso = responsabileCaso;
	}

	public List<CsIIntervento> getLstInterventi() {
		return lstInterventi;
	}

	public void setLstInterventi(List<CsIIntervento> lstInterventi) {
		this.lstInterventi = lstInterventi;
	}

	public List<CsDRelazione> getLstRelazioni() {
		return lstRelazioni;
	}

	public void setLstRelazioni(List<CsDRelazione> lstRelazioni) {
		this.lstRelazioni = lstRelazioni;
	}
	
	public List<SelectItem> getLstRadioOptions() {
		if(lstRadioOptions==null){
			lstRadioOptions = new ArrayList<SelectItem>();
			lstRadioOptions.add(new SelectItem(1, "No"));
			lstRadioOptions.add(new SelectItem(2, "Parzialmente"));
			lstRadioOptions.add(new SelectItem(3, "Si"));
			
		}
		
		return lstRadioOptions;
	}
	
	public List<SelectItem> getLstArFfProgetti() {
		if(this.lstArFfProgetti==null)
			lstArFfProgetti = this.loadLstArFfProgetti();
		return lstArFfProgetti;
	}

	public void setLstArFfProgetti(List<SelectItem> lstArFfProgetti) {
		this.lstArFfProgetti = lstArFfProgetti;
	}
	
	@Override
	public PERIODO_TEMPORALE[] getListaPeriodi(){
		return DataModelCostanti.Pai.PERIODO_TEMPORALE.values();
	}
	

}
