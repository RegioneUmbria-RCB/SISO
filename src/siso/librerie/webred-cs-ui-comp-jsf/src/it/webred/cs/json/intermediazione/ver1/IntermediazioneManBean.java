package it.webred.cs.json.intermediazione.ver1;

import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.intermediazione.IntermediazioneManBaseBean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;

public class IntermediazioneManBean extends IntermediazioneManBaseBean{

	private static final long serialVersionUID = 1L;

	private IntermediazioneController controller;

	public IntermediazioneManBean() {
		super();
		controller = new IntermediazioneController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());
		this.getJsonCurrent().setTipoIntermediazione("Ricerca Abitazione");
		this.getJsonCurrent().setRicercaAbitazione(new RicercaAbitazione());
	}
	
	@Override
	public  boolean validaData ( ) {
		
		boolean validazioneOk = true;
		List<String> messagges;
		messagges = controller.validaData();
		if( messagges.size() > 0 ) {
			this.addWarning(controller.getDescrizioneScheda(), messagges);
			validazioneOk &= false;
		}

		return validazioneOk;
	}
	
	@Override
	public boolean save(Long visSecondoLivello){
		this.controller.setVisSecondoLivello(visSecondoLivello);
		return this.save();
	}
	
	@Override
	public boolean save(){
        boolean ok = false;
        try{   
			if(!validaData())
				return ok;
			
			if(this.getJsonCurrent().getTipoIntermediazione().equals("Ricerca Abitazione"))
				getJsonCurrent().getRicercaAbitazione().setComunePreferito(this.getComuneMan().getComune());

			controller.save(this.getClass().getName());
			
			//ora salva
			//addInfoFromProperties( "salva.ok" );
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			ok=true;
			
	    }catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}			
		return ok;
	}

	@Override
	public void restore() {
		controller.restore();
	}


	public IntermediazioneBean getJsonCurrent() {
		return controller.getJsonCurrent();
	}
	
	@Override
	public void init(CsDValutazione schedaPadre, CsDValutazione scheda) {
		
		try {
			controller.loadData( schedaPadre, scheda );
			Long idCaso = scheda.getCsDDiario().getCsACaso()!=null ? scheda.getCsDDiario().getCsACaso().getId() : null;
			setIdCaso(idCaso);
			setIdSchedaUdc(scheda.getCsDDiario().getSchedaId());
			if("Ricerca Abitazione".equals(this.getJsonCurrent().getTipoIntermediazione())){
				this.comuneMan.setComune(this.getJsonCurrent().getRicercaAbitazione().getComunePreferito());
				this.disableMotivoAltro = !Arrays.asList(this.getJsonCurrent().getRicercaAbitazione().getMotivi()).contains("Altro");
			}
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void init(ISchedaValutazione bean){
		try{
			controller.load((IntermediazioneBean)bean.getSelected());
			setIdCaso(bean.getIdCaso());
			setIdSchedaUdc(bean.getIdSchedaUdc());
			if(this.getJsonCurrent().getTipoIntermediazione().equals("Ricerca Abitazione")){
				this.comuneMan.setComune(this.getJsonCurrent().getRicercaAbitazione().getComunePreferito());
				this.disableMotivoAltro = !Arrays.asList(this.getJsonCurrent().getRicercaAbitazione().getMotivi()).contains("Altro");
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public IntermediazioneBean getSelected() {
		return controller.getJsonCurrent();
	}

	@Override
	public boolean elimina(){
        boolean ok = false;
       try{ 
    	   controller.elimina();
    	   addInfoFromProperties( "elimina.ok" );
    	   ok= true;
       }catch(CarSocialeServiceException cse){
    	   addMessage("Errore di eliminazione",cse.getMessage(),FacesMessage.SEVERITY_ERROR);
    	   logger.error(cse.getMessage(),cse);
       }catch(Exception e){
    	   addErrorFromProperties("elimina.error");
    	   logger.error(e.getMessage(),e);
       }
		return ok;
	}
	
	@Override
	public void setIdCasoController(Long idCaso){
		controller.setCasoId(idCaso);
	}
	
	@Override
	public void changeMotivoRicerca(){
		String stato[] = this.getJsonCurrent().getRicercaAbitazione().getMotivi();
		
		this.disableMotivoAltro=!Arrays.asList(stato).contains("Altro");
		if(disableMotivoAltro)
			this.getJsonCurrent().getRicercaAbitazione().setMotivoAltro(null);
	}
	

	@Override
	public void setIdSchedaUdc(Long idUdc) {
		controller.setUdcId(idUdc);
	}

	@Override
	public boolean isNew() {
		return !(controller.getDiarioId()!=null && controller.getDiarioId().longValue()>0);
	}

	@Override
	public void changeTipoIntermediazione() {
		String tipo = this.getJsonCurrent().getTipoIntermediazione();
		if("altro".equalsIgnoreCase(tipo))
			this.getJsonCurrent().setRicercaAbitazione(null);
		else{
			this.getJsonCurrent().setRicercaAbitazione(new RicercaAbitazione());
			this.getJsonCurrent().setQuestione(null);
			this.getJsonCurrent().setQuestioneDettaglio(null);
		}
		
	}


	@Override
	public CsDValutazione getCurrentModel() {
		return controller.getDataModel();
	}

	@Override
	public Long getIdSchedaUdc() {
		return controller.getUdcId();
	}
	
}
