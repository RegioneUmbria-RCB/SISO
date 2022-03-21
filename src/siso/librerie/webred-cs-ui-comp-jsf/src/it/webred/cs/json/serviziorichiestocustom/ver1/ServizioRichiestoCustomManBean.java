package it.webred.cs.json.serviziorichiestocustom.ver1;

import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbMacroIntervento;
import it.webred.cs.data.model.CsTbMicroIntervento;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.serviziorichiestocustom.IServizioRichiestoCustom;
import it.webred.cs.json.serviziorichiestocustom.ItemConsuntivoServizioRichiestoCustom;
import it.webred.cs.json.serviziorichiestocustom.ServizioRichiestoCustomManBaseBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.primefaces.context.RequestContext;

/**
 * SISO-438-Possibilità di allegare documenti in UdC
 *
 */
public class ServizioRichiestoCustomManBean extends ServizioRichiestoCustomManBaseBean{
	private static final long serialVersionUID = -3860042823118542345L;


	private ServizioRichiestoCustomController controller;
	private Boolean residenzaFuoriAmbito;
	
	public ServizioRichiestoCustomManBean() {
		super();
		controller = new ServizioRichiestoCustomController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());
		
	}
	
	@Override
	public void init(CsDValutazione schedaPadre, CsDValutazione scheda) {

		try {
			controller.loadData(schedaPadre, scheda);
			logger.debug(controller.getDiarioId());
			Long idCaso = scheda.getCsDDiario().getCsACaso() != null ? scheda.getCsDDiario().getCsACaso().getId() : null;
			setIdCaso(idCaso);
			valUltimModifica(scheda.getCsDDiario());
			setIdSchedaUdc(scheda.getCsDDiario().getSchedaId());
			initDocumenti(scheda.getCsDDiario().getId());
			initConsuntivo(scheda.getCsDDiario().getId());
			loadPDSMacro();
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void init(ISchedaValutazione bean) {

		try {
			controller.load((ServizioRichiestoCustomBean) bean.getSelected());
			setIdCaso(bean.getIdCaso());
			setIdSchedaUdc(bean.getIdSchedaUdc());
			cloneDocumenti((IServizioRichiestoCustom) bean);
			loadPDSMacro();
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	
	}

	@Override
	public boolean save(Long visSecondoLivello){
		this.controller.setVisSecondoLivello(visSecondoLivello);
		return this.save();
	}
	
	@Override
	public boolean save() {
		boolean ok = false;
		try {
			if (!validaData())
				return ok;

			controller.save(this.getClass().getName()); 
			super.saveDocumenti(controller.getDiarioId(), controller.getCasoId());

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
	public void restore() {
		controller.restore();
		
		
	}

	@Override
	public ServizioRichiestoCustomBean getSelected() {
		return (ServizioRichiestoCustomBean) controller.getJsonCurrent();
	}

	@Override
	public void setIdSchedaUdc(Long idUdc) {
		controller.setUdcId(idUdc); 
	}

	@Override
	public Long getIdSchedaUdc() {
		return controller.getUdcId();
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
	public void setIdCasoController(Long idCaso) {
		controller.setCasoId(idCaso);
	}
	

	public ServizioRichiestoCustomBean getJsonCurrent() {
		return controller.getJsonCurrent();
	}

	@Override
	public Long getDiarioId() {
		if (controller!=null) {
			return controller.getDiarioId(); 
		} else {
			return null;
		}
	}

	@Override
	public ItemConsuntivoServizioRichiestoCustom addConsuntivoItem() {
		ItemConsuntivoServizioRichiestoCustom item = new ItemConsuntivoServizioRichiestoCustom();
		List<ItemConsuntivoServizioRichiestoCustom> l = getConsuntivoListItem();
		if(l!=null)
		{
			l.add( item );
			return item;
		}
		
		return null;
	}

	@Override
	public void removeConsuntivoItem(ItemConsuntivoServizioRichiestoCustom item) {
		List<ItemConsuntivoServizioRichiestoCustom> l = getConsuntivoListItem();
		if(l!=null && item!=null)
		{
			l.remove(item);
		}
	}

	@Override
	public List<ItemConsuntivoServizioRichiestoCustom> getConsuntivoListItem() {
		ServizioRichiestoCustomBean b = this.getSelected();
		if(b!=null)
		{
			return b.getConsuntivoList();
		}		
		return null;
	}

	@Override
	public boolean isTipoInvioScheda() {
		String tic = this.getTipoInterventoCustom().toLowerCase();
		return (tic.contains("->"));
		
	}
	
	@Override
	public void setResidenzaFuoriAmbito(Boolean residenzaFuoriAmbito) {
		this.residenzaFuoriAmbito = residenzaFuoriAmbito;
	}
	
	public boolean isRenderResidenzaFuoriAmbito(){
		if(this.residenzaFuoriAmbito==null)
			return (getJsonCurrent().getNuoviInterventiSelezionati()!=null  && getJsonCurrent().getNuoviInterventiSelezionati().length>0);
		else
			return this.residenzaFuoriAmbito;
	}
	
	@Override
	protected void loadPDSMacro() {
		SelectItemGroup group1;
		try {
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(new Long(this.getJsonCurrent().getTipoInterventoCustomId()));
			List<CsTbMacroIntervento> macro = confService.readPDSMacro(dto);
			for (CsTbMacroIntervento macro1 : macro) {
				dto.setObj(macro1.getId());
				group1 = new SelectItemGroup(macro1.getPdsmacro());
				List<CsTbMicroIntervento> micro = confService.readPDSMicro(dto);
				List<SelectItem> lstMicro = new ArrayList<SelectItem>();
				boolean disabilitaGruppo = !macro1.getAbilitato();
				boolean vociDisabilitate = true;
				/*Per il componente non funziona il disabilita item, quindi la voce va eliminata*/
				for (CsTbMicroIntervento micro1 : micro) {
					SelectItem si = new SelectItem(micro1.getId(), micro1.getPdsMicro());
					boolean disabilita = !micro1.getAbilitato() && !micro1.getId().toString().equals(this.getJsonCurrent().getSelezionePDS());
					if(!disabilita)
						lstMicro.add(si);
					vociDisabilitate = vociDisabilitate && disabilita;
				}
				
				disabilitaGruppo = !macro1.getAbilitato() || vociDisabilitate;
				if(!disabilitaGruppo){
					group1.setSelectItems(lstMicro.toArray(new SelectItem[lstMicro.size()]));
					listaPDS.add(group1);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.addError("Errore caricamento categorie PDS", e.getMessage());
		}
	}

	
}
