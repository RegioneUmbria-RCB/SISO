package it.webred.cs.json.isee.ver2;

import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.bean.IseeBean;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.isee.IseeManBaseBean;
import it.webred.cs.sociosan.ejb.dto.isee.DatiIsee;

import java.util.List;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;

public class ManIseeBean extends IseeManBaseBean {

	private static final long serialVersionUID = 1L;

	private IseeController controller;
	
	public ManIseeBean() {
		super();
		controller = new IseeController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());
		
	}
	
	@Override
	public  boolean validaData ( ) {
		
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
			
			getSelected().setProtocolloBean(protDsuMan.getDto());
			controller.save(this.getClass().getName());

			//ora salva
			addInfoFromProperties( "salva.ok" );
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


	public IseeBean getJsonCurrent() {
		return controller.getJsonCurrent();
	}
	
	@Override
	public void init(CsDValutazione schedaMultidim, CsDValutazione schedaBarthel) {
		
		try {
			controller.loadData( schedaMultidim, schedaBarthel );
			setIdCaso(schedaBarthel.getCsDDiario().getCsACaso().getId());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void init(ISchedaValutazione bean){}

	@Override
	public IseeBean getSelected() {
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
	public CsDValutazione getCurrentModel() {
		return controller.getDataModel();
	}
	
	@Override
	public boolean isNew() {
		return !(controller.getDiarioId()!=null && controller.getDiarioId().longValue()>0);
	}
	
	@Override
	public Long getIdSchedaUdc() {
		return controller.getUdcId();
	}
	
	@Override
	public String fillReportIsee() {
		IseeBean isee = getSelected();
		String s =  this.protDsuMan.getStampa();
		s+= " Data: "                       + (isee.getDataIsee()!=null ? ddMMyyyy.format(isee.getDataIsee()) : "          ");
		s+= " € " 						    + (isee.getIsee()!=null ? isee.getIsee() : "");
		return s;
	}

	@Override
	public void init(CsDIsee jpa) {//Non implementare: valido solo per versione 1 che salva su DB
	}

	@Override
	public void onChangeAnnoRiferimento(){
		DatiIsee in = protDsuMan.cbxAnnoDsuListener();
		if(in!=null){
			this.getJsonCurrent().setIse(in.getIse());
			this.getJsonCurrent().setIsee(in.getIsee());
			this.getJsonCurrent().setScalaEquivalenza(in.getScalaEquivalenza());
			this.getJsonCurrent().setTipo(in.getTipo());
			this.getJsonCurrent().setDataScadenzaIsee(in.getDataScadenzaIsee());
			this.getJsonCurrent().setDataIsee(in.getDataPresentazioneIsee());
		}
	}
	
	@Override 
	public void onChangeTipologia(){
		protDsuMan.setTipologia(getJsonCurrent().getTipo()!=null ? this.getJsonCurrent().getTipo().getId() : null);
		DatiIsee in = protDsuMan.trovaAttestazione();
		if(in!=null){
			this.getJsonCurrent().setIse(in.getIse());
			this.getJsonCurrent().setIsee(in.getIsee());
			this.getJsonCurrent().setScalaEquivalenza(in.getScalaEquivalenza());
			this.getJsonCurrent().setTipo(in.getTipo());
			this.getJsonCurrent().setDataScadenzaIsee(in.getDataScadenzaIsee());
			this.getJsonCurrent().setDataIsee(in.getDataPresentazioneIsee());
		}else{
			this.getJsonCurrent().setIse(null);
			this.getJsonCurrent().setIsee(null);
			this.getJsonCurrent().setScalaEquivalenza(null);
			//this.getJsonCurrent().setTipo(in.getTipo());
			this.getJsonCurrent().setDataScadenzaIsee(null);
			this.getJsonCurrent().setDataIsee(null);
		}
	}
	
}
