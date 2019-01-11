package it.webred.cs.json.valSinba.ver1;

import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.barthel.ISchedaBarthel;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.isee.IIseeJson;
import it.webred.cs.json.valSinba.ValSinbaManBaseBean;
import it.webred.cs.json.valSinba.ValSinbaRowBean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

public class ValSinbaManBean extends ValSinbaManBaseBean {

	private static final long serialVersionUID = 1L;

	private ValSinbaController controller;
	
	
	
	public ValSinbaManBean() {
		controller = new ValSinbaController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());
		
		
	    
	}
	

	protected void loadCommonList()
	{

	    				
		BaseDTO anaFamCurrDto = new BaseDTO();
		fillEnte(anaFamCurrDto);
		
		
	}
	
	

	public ValSinbaBean getJsonCurrent() {

		return controller.getJsonCurrent();
	}

	@Override
	public void init(ISchedaValutazione bean) {
		try{
			controller.load((ValSinbaBean)bean.getSelected());
			valorizzaDatiBase(bean.getIdCaso(), bean.getIdSchedaUdc());
			this.getJsonCurrent().setDataValutazione(new Date());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void init(CsDValutazione parent, CsDValutazione scheda) {
		try {
			controller.loadData(parent, scheda);
			CsDDiario diario = scheda.getCsDDiario();
			Long idCaso = diario.getCsACaso() != null ? diario.getCsACaso().getId() : null;
			valorizzaDatiBase(idCaso, diario.getSchedaId());
			this.getJsonCurrent().setDescrizioneScheda(scheda.getDescrizioneScheda());
			this.getJsonCurrent().setDataValutazione(scheda.getCsDDiario().getDtAmministrativa());
	
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void initRowList(CsDValutazione parent, CsDValutazione scheda) {
		try {
			controller.loadData(parent, scheda);
			CsDDiario diario = scheda.getCsDDiario();
			Long idCaso = diario.getCsACaso() != null ? diario.getCsACaso().getId() : null;
			valorizzaDatiBase(idCaso, diario.getSchedaId());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void valorizzaRowList(ValSinbaRowBean row) {
		//TODO: valorizzare eventuali campi aggiuntivi da mostrare in lista
			
	}
	
	private void valorizzaDatiBase(Long idCaso, Long idSchedaUdc){
		setIdCaso(idCaso);
		setIdSchedaUdc(idSchedaUdc);
	}


	@Override
	public JsonBaseBean getSelected() {
		return getJsonCurrent();
	}

	@Override
	public boolean elimina() {
		boolean ok = false;
		try {
			//prescSpec.eliminaDocumenti(controller.getDataModel().getCsDDiario());
			controller.elimina();
			addInfoFromProperties("elimina.ok");
			ok = true;
		} catch (CarSocialeServiceException cse) {
			addMessage("Errore di eliminazione", cse.getMessage(), FacesMessage.SEVERITY_ERROR);
			logger.error(cse.getMessage(), cse);
		} catch (Exception e) {
			addErrorFromProperties("elimina.error");
			logger.error(e.getMessage(), e);
		}
		return ok;
	}

	@Override
	public void setIdCasoController(Long idCaso) {
		controller.setCasoId(idCaso);
	}

	@Override
	public void setIdSchedaUdc(Long idUdc) {
		controller.setUdcId(idUdc);
	}

	@Override
	public CsDValutazione getCurrentModel() {
		return controller.getDataModel();
	}

	@Override
	public boolean isNew() {
		return !(controller.getDiarioId() != null && controller.getDiarioId().longValue() > 0);
	}

	@Override
	public Long getIdSchedaUdc() {
		return controller.getUdcId();
	}

	public ValSinbaController getController() {
		return this.controller;
	}

	@Override
	public boolean save() {

		/*this.datiProv.valorizzaJson(this.getJsonCurrent().getDatiProvvedimenti());
		DatiProvvedimentoBean db = this.getJsonCurrent().getDatiProvvedimenti();
		*/
		boolean ok = false;
		try {
			if (validaData()) {
				
				controller.save(this.getClass().getName());
				ok = true;
				
				//this.prescSpec.salvaDocumento(controller.getDataModel().getCsDDiario());
				
				// ora salva
				addInfoFromProperties("salva.ok");
			}
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
		return ok;
	}

	@Override
	public boolean validaData() {
		boolean validazioneOk = true;
		List<String> messagges;
		messagges = controller.validaData();
		if( messagges.size() > 0 ) {
			addWarning("Val.SInBa:", messagges);
			validazioneOk &= false;
		}

		RequestContext.getCurrentInstance().addCallbackParam("canClose", validazioneOk);
		
		return validazioneOk;
	}

	@Override
	public void restore() {
		controller.restore();
	}
	
	

	@Override
	public ReportPdfDTO fillReport(String reportPath, List<String> listaSubreport, HashMap<String, Object> map, List<IIseeJson> lstIsee, ISchedaBarthel barthelMan) {
			ValSinbaPdfDTO pdf = new ValSinbaPdfDTO();
	
			//TODO:Riempire il bean da esportare
			
			return pdf;
	}
	


}
