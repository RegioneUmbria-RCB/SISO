package it.webred.cs.json.stranieri.ver1;

import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.dto.KeyValueDTO;
import it.webred.cs.json.stranieri.StranieriManBaseBean;
import it.webred.jsf.bean.ComuneBean;

import java.util.List;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;

public class StranieriManBean extends StranieriManBaseBean{

	private static final long serialVersionUID = 1L;

	private StranieriController controller;

	public StranieriManBean() {
		super();
		controller = new StranieriController();
		controller.setUser(getUser());
		controller.setOperatoreSettore((CsOOperatoreSettore) getSession().getAttribute("operatoresettore"));
		
	}
	
	@Override
	public boolean validaData ( ) {
		
		boolean validazioneOk = true;
		List<String> messagges;
		((StranieriBean)controller.getJsonCurrent()).setValidaConoscenzaLingua(this.validaConoscenzaLingua);
		((StranieriBean)controller.getJsonCurrent()).setValidaDatiImmigrazione(this.validaDatiImmigrazione);
		messagges = controller.validaData();
		if( messagges.size() > 0 ) {
			for(String msg : messagges)
				addWarning(msg,"");
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
			
			getJsonCurrent().setComuneValicoFrontiera(this.getComuneManITA().getComune());
			getJsonCurrent().setComuneUltimoArrivoREG(this.getComuneManREG().getComune());
			getJsonCurrent().setComuneRilascio(this.getComuneManRilascio().getComune());
			

			controller.save();
			
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


	public StranieriBean getJsonCurrent() {
		return controller.getJsonCurrent();
	}
	
	@Override
	public void init(CsDValutazione schedaPadre, CsDValutazione scheda) {
		
		try {
			controller.loadData( schedaPadre, scheda );
			Long idCaso = scheda.getCsDDiario().getCsACaso()!=null ? scheda.getCsDDiario().getCsACaso().getId() : null;
			valorizzaDatiBase(idCaso, scheda.getCsDDiario().getSchedaId());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void init(ISchedaValutazione bean){
		try{
			controller.load((StranieriBean)bean.getSelected());
			valorizzaDatiBase(bean.getIdCaso(), bean.getIdSchedaUdc());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	private void valorizzaDatiBase(Long idCaso, Long idSchedaUdc){
		setIdCaso(idCaso);
		setIdSchedaUdc(idSchedaUdc);
		this.comuneManITA.setComune(this.getJsonCurrent().getComuneValicoFrontiera());
		this.comuneManREG.setComune(this.getJsonCurrent().getComuneUltimoArrivoREG());
		this.comuneManRilascio.setComune(this.getJsonCurrent().getComuneRilascio());
		String stato = this.getJsonCurrent().getStatoPermessoSogg();
		KeyValueDTO status = this.getJsonCurrent().getStatus();
		this.disableTipoPermesso = 
				!ListaStatoPermesso.IN_POSSESSO_PERMESSO.equals(stato) && !ListaStatoPermesso.IN_ATTESA_RINNOVO.equals(stato);
		
		this.disableDatiPermesso = 
				!ListaStatoPermesso.IN_POSSESSO_PERMESSO.equals(stato) && !ListaStatoPermesso.IN_ATTESA_RINNOVO.equals(stato) && !ListaStatoPermesso.IN_ATTESA_PERMESSO.equals(stato);
		
		this.disablePnlStatoPermesso= ListaStatus.CITTADINO_COMUNITARIO_RESIDENTE.equals(status.getCodice()) || 
				                      ListaStatus.CITTADINO_COMUNITARIO_NON_RESIDENTE.equals(status.getCodice());
				
	}
	
	@Override
	public StranieriBean getSelected() {
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
	public void changeProtezioneInternazionale(){
		if(!this.getJsonCurrent().isProtezioneInternazionale()){
			this.comuneManREG.setComune(null);
		}else
			this.comuneManREG.setComune(new ComuneBean());
	}
	
	@Override
	public void changeStatoPermesso(){
		String stato = this.getJsonCurrent().getStatoPermessoSogg();
		if("in possesso di permesso".equals(stato) || "in attesa di rinnovo".equals(stato) ){
			this.disableDatiPermesso=false;
			this.disableTipoPermesso=false;
		}else if("in attesa di permesso".equals(stato)){
			this.getJsonCurrent().setScadPermessoSogg(null);
			this.getJsonCurrent().setIdPraticaPermesso(null);
			this.disableDatiPermesso=true;
			this.disableTipoPermesso=false;
		}else{
			this.getJsonCurrent().setPermesso(new KeyValueDTO());
			this.getJsonCurrent().setScadPermessoSogg(null);
			this.getJsonCurrent().setIdPraticaPermesso(null);
			this.disableDatiPermesso=true;
			this.disableTipoPermesso=true;
		} 
			
	}
	
	@Override
	public void changeStatus(){
		//Valorizza descrizione
		String codice = this.getJsonCurrent().getStatus().getCodice();
		String descrizione=null;
		if(codice!=null && !codice.isEmpty())
			descrizione = getStatus(codice);
		this.getJsonCurrent().getStatus().setDescrizione(descrizione);
		
		if(!ListaStatus.CITTADINO_COMUNITARIO_NON_RESIDENTE.equals(codice))
			this.getJsonCurrent().setPresenteDaOltre3Mesi(false);
		
		this.disablePnlStatoPermesso= ListaStatus.CITTADINO_COMUNITARIO_RESIDENTE.equals(codice) || 
                                      ListaStatus.CITTADINO_COMUNITARIO_NON_RESIDENTE.equals(codice);
		if(this.disablePnlStatoPermesso)
			this.getJsonCurrent().setStatoPermessoSogg(null);
		
		this.changeStatoPermesso();
	}
	
	@Override
	public void changePermesso(){
		//Valorizza descrizione
		String codice = this.getJsonCurrent().getPermesso().getCodice();
		String descrizione=null;
		if(codice!=null && !codice.isEmpty())
		   descrizione = getPermesso(codice);
		this.getJsonCurrent().getPermesso().setDescrizione(descrizione);
		
	}
	
	@Override
	public void changeLinguaItaAttestato(){
		if(this.getJsonCurrent().isLinguaItaAttestato()){
			this.getJsonCurrent().setLinguaItaComprensione(0);
			this.getJsonCurrent().setLinguaItaLettura(0);
			this.getJsonCurrent().setLinguaItaParlato(0);
			this.getJsonCurrent().setLinguaItaScrittura(0);
			this.comuneManRilascio.setComune(null);
		}else{
			this.getJsonCurrent().setLiguaItaLivello(null);
			this.comuneManRilascio.setComune(new ComuneBean());
		}
	}
	
	@Override
	public void changeMinoreNonAccompagnato(){
		if(!this.getJsonCurrent().getMinoreNonAccompagnato()){
			this.getJsonCurrent().setNomeStruttAccoglienza(null);
			this.getJsonCurrent().setIndirizzoStruttAccoglienza(null);
		}
	}

	@Override
	public void changePresente(){
		if(this.getJsonCurrent().isPresenteDaOltre3Mesi())
		   this.getJsonCurrent().setStatoPermessoSogg("nessun titolo di soggiorno");
	}
	
	@Override
	public void setIdSchedaUdc(Long idUdc) {
		controller.setUdcId(idUdc);
	}

	@Override
	public void changeNazioneOrigine() {
		//Valorizza descrizione
		String codice = this.getJsonCurrent().getNazioneOrigine().getCodice();
		String descrizione=null;
		if(codice!=null && !codice.isEmpty())
		   descrizione = getNazione(codice);
		this.getJsonCurrent().getNazioneOrigine().setDescrizione(descrizione);
	}

	@Override
	public void changeNazioneProvenienza() {
		//Valorizza descrizione
		String codice = this.getJsonCurrent().getUltimaNazioneProvenienza().getCodice();
		String descrizione=null;
		if(codice!=null && !codice.isEmpty())
		   descrizione = getNazione(codice);
		this.getJsonCurrent().getUltimaNazioneProvenienza().setDescrizione(descrizione);
	}

	 public void onChangePresente(){
	    	if(this.getJsonCurrent().isPresenteDaOltre3Mesi())
	    		this.getJsonCurrent().setStatoPermessoSogg("nessun titolo di soggiorno");
	    	else
	    		this.getJsonCurrent().setStatoPermessoSogg(null);
	    	
	    	this.changeStatoPermesso();
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
