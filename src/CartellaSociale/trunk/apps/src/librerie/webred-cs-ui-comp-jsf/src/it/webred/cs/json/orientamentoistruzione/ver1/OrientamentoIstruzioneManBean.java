package it.webred.cs.json.orientamentoistruzione.ver1;

import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.orientamentoistruzione.OrientamentoIstruzioneManBaseBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

public class OrientamentoIstruzioneManBean extends OrientamentoIstruzioneManBaseBean {

	private static final long serialVersionUID = 7622574854007012971L;

	private OrientamentoIstruzioneController controller;

	public OrientamentoIstruzioneManBean() {
		super();
		controller = new OrientamentoIstruzioneController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());
	}

	@Override
	public boolean validaData() {

		boolean validazioneOk = true;
		List<String> messages;
		messages = controller.validaData();
		if (messages.size() > 0) {
			addWarning(controller.getDescrizioneScheda(),  messages);
			validazioneOk &= false;
		}

		return validazioneOk;
	}

	@Override
	public boolean save() {
		boolean ok = false;
		try {
			if (!validaData())
				return ok;

			controller.save(this.getClass().getName());

			// ora salva
			// addInfoFromProperties( "salva.ok" );
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			ok = true;

		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
		return ok;
	}

	@Override
	public void restore() {
		controller.restore();
	}

	public OrientamentoIstruzioneBean getJsonCurrent() {
		return controller.getJsonCurrent();
	}

	@Override
	public void init(CsDValutazione schedaPadre, CsDValutazione scheda) {

		try {
			controller.loadData(schedaPadre, scheda);
			Long idCaso = scheda.getCsDDiario().getCsACaso() != null ? scheda.getCsDDiario().getCsACaso().getId() : null;
			setIdCaso(idCaso);
			valUltimModifica(scheda.getCsDDiario());
			setIdSchedaUdc(scheda.getCsDDiario().getSchedaId());

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void init(ISchedaValutazione bean) {
		try {
			controller.load((OrientamentoIstruzioneBean) bean.getSelected());
			setIdCaso(bean.getIdCaso());
			setIdSchedaUdc(bean.getIdSchedaUdc());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public OrientamentoIstruzioneBean getSelected() {
		return controller.getJsonCurrent();
	}

	@Override
	public boolean elimina() {
		boolean ok = false;
		try {
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
	public boolean isNew() {
		return !(controller.getDiarioId() != null && controller.getDiarioId().longValue() > 0);
	}

	@Override
	public CsDValutazione getCurrentModel() {
		return controller.getDataModel();
	}

	@Override
	public Long getIdSchedaUdc() {
		return controller.getUdcId();
	}

	@Override
	public void preValorizzaDati(BigDecimal idTitoloStudio, List<SelectItem> lstTitoliStudio, String linguaItaLivello) {
		if (idTitoloStudio != null)
			this.getJsonCurrent().setIdTitoloStudio(idTitoloStudio);
		if (lstTitoliStudio != null)
			this.getJsonCurrent().setLstTitoliStudio(lstTitoliStudio);
	}
}
