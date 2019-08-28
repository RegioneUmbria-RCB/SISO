package it.webred.cs.json.abitazione.ver1;

import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.abitazione.AbitazioneManBaseBean;

import java.util.List;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;

public class AbitazioneManBean extends AbitazioneManBaseBean{

	private static final long serialVersionUID = 1L;
	
	private AbitazioneController controller;

	public AbitazioneManBean() {
		super();
		controller = new AbitazioneController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());
		
		BaseDTO dto = new BaseDTO();
		CsUiCompBaseBean.fillEnte(dto);
		this.loadListeAbitazione(dto);
		
	}
	
	@Override
	public boolean validaData ( ) {
		
		boolean validazioneOk = true;
		List<String> messagges;
		messagges = controller.validaData();
		if( messagges.size() > 0 ) {
			addWarning(controller.getDescrizioneScheda(), messagges);
			validazioneOk &= false;
		}

		return validazioneOk;
	}
	
	@Override
	public boolean save(){
        boolean ok = false;
        try{   
			if(!validaData())
				return ok;
	
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


	public AbitazioneBean getJsonCurrent() {
		return controller.getJsonCurrent();
	}
	
	public String getTipoAbitazione(){
		return getJsonCurrent().getTipoAbitazione();
	}
	
	public String getNumVani(){
		return this.getJsonCurrent().getNumVani();
	}
	
	public String getTitoloGodimento(){
		return this.getJsonCurrent().getTitoloGodimento();
	}
	
	public String getNote(){
		return this.getJsonCurrent().getNote();
	}
	
	public String getProprietarioGestore(){
		return this.getJsonCurrent().getProprietarioGestore();
	}
	
	@Override
	public void init(CsDValutazione schedaPadre, CsDValutazione scheda) {
		
		try {
			controller.loadData( schedaPadre, scheda );
			Long idCaso = scheda.getCsDDiario().getCsACaso()!=null ? scheda.getCsDDiario().getCsACaso().getId() : null;
			setIdCaso(idCaso);
			setIdSchedaUdc(scheda.getCsDDiario().getSchedaId());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void init(ISchedaValutazione bean){
		try{
			controller.load((AbitazioneBean)bean.getSelected());
			setIdCaso(bean.getIdCaso());
			setIdSchedaUdc(bean.getIdSchedaUdc());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}


	@Override
	public AbitazioneBean getSelected() {
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
	public void setIdSchedaUdc(Long idUdc) {
		controller.setUdcId(idUdc);
	}

	@Override
	public boolean isNew() {
		return !(controller.getDiarioId()!=null && controller.getDiarioId().longValue()>0);
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
