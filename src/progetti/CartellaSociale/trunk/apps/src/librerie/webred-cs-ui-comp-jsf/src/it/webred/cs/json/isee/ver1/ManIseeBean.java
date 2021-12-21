package it.webred.cs.json.isee.ver1;

import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbTipoIsee;
import it.webred.cs.jsf.bean.IseeBean;
import it.webred.cs.jsf.manbean.ProtocolloDsuMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.isee.IseeManBaseBean;
import it.webred.cs.sociosan.ejb.dto.isee.DatiIsee;

import java.util.List;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;

public class ManIseeBean extends IseeManBaseBean {

	private static final long serialVersionUID = 1L;
	private IseeBean isee;
	private Long idDiario;
	private Long visSecondoLivello;
	
	public ManIseeBean() {
		super();
		isee = new IseeBean();
	}
	
	@Override
	public  boolean validaData ( ) {
		
		boolean validazioneOk = true;
		List<String> messagges;
		messagges = isee.checkObbligatorieta();
		
		if(isee.getTipo()!=null && isee.getTipo().getId()!=null){
			BaseDTO cet = new BaseDTO();
			fillEnte(cet);
			cet.setObj(isee.getTipo().getId());
			CsTbTipoIsee tipo = confService.getTipoIsee(cet);
			if(tipo!=null && !tipo.getAbilitato())
				messagges.add("La tipologia selezionata non è più valida");
		}
		
		if( messagges.size() > 0 ) {
			addWarning("ISEE:", messagges);
			validazioneOk &= false;
		}

		RequestContext.getCurrentInstance().addCallbackParam("canClose", validazioneOk);
		
		return validazioneOk;
	}
	
	@Override
	public boolean save(Long visSecondoLivello){
		this.visSecondoLivello = visSecondoLivello;
		return this.save();
	}
	
	@Override
	public boolean save(){
		  boolean ok = false;
	      isee.setProtocolloBean(protDsuMan.getDto());
	      
	   try{  
		  if(!validaData())
	    	  return ok;
	      
			CsDIsee jpa = new CsDIsee();
		    BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			
			if(getIdDiario() != null) {
				
				dto.setObj(getIdDiario());
				jpa = diarioService.findIseeById(dto);
	
				riversaIseeToJpa(jpa);
			
				dto.setObj(jpa);
				diarioService.updateIsee(dto);
			}
			else {
				
				CsOOperatoreSettore opSettore = getCurrentOpSettore();
		        riversaIseeToJpa(jpa);
		        
		        dto.setObj(jpa);
		        dto.setObj2(this.getIdCaso());
		        dto.setObj3(opSettore);
				CsDDiario csd = diarioService.saveIsee(dto);
				setIdDiario(csd.getId());
			}
			
			ok=true;
			addInfoFromProperties( "salva.ok" );
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			
	    }catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}		
		return ok;
	}
	
	private CsDIsee riversaIseeToJpa(CsDIsee jpa){
		jpa.setIse(isee.getIse());
		jpa.setIsee(isee.getIsee());
		jpa.setNote(isee.getNote());
		jpa.setScalaEquivalenza(isee.getScalaEquivalenza());
		jpa.setTipoIsee(isee.getTipo().getId()==null ? null : isee.getTipo());
		jpa.getCsDDiario().setDtAmministrativa(isee.getDataIsee());
		jpa.getCsDDiario().setDtChiusuraDa(isee.getDataScadenzaIsee());
		jpa.getCsDDiario().setVisSecondoLivello(this.visSecondoLivello);
		//jpa.setAnnoRif(isee.getAnnoRif());
		//jpa.setProtocolloDsu(isee.getProtocolloDsu());
		protDsuMan.valorizza(jpa);
		
		return jpa;
	}
	
	private IseeBean riversaJpaToIsee(CsDIsee jpa){
		setIdDiario(jpa.getCsDDiario().getId());
		setIdCaso(jpa.getCsDDiario().getCsACaso().getId());
		
		IseeBean isee = new IseeBean();
		
		isee.setIse(jpa.getIse());
		isee.setIsee(jpa.getIsee());
		isee.setNote(jpa.getNote());
		isee.setScalaEquivalenza(jpa.getScalaEquivalenza());
		isee.setTipo(jpa.getTipoIsee()!=null ? jpa.getTipoIsee() : new CsTbTipoIsee());
		isee.setDataIsee(jpa.getCsDDiario().getDtAmministrativa());
		isee.setDataScadenzaIsee(jpa.getCsDDiario().getDtChiusuraDa());
		//isee.setProtocolloDsu(jpa.getProtocolloDsu());
		//isee.setAnnoRif(jpa.getAnnoRif());
		protDsuMan = new ProtocolloDsuMan(jpa);
		isee.setProtocolloBean(protDsuMan.getDto());
		
		return isee;
	}
	
		@Override
	public void restore() {
		//controller.restore();
	}

	
	@Override
	public void init(CsDValutazione schedaMultidim, CsDValutazione schedaBarthel) {}
	
	@Override
	public void init(ISchedaValutazione bean) {}
	
	@Override
	public void init(CsDIsee jpa){
		isee = this.riversaJpaToIsee(jpa);
		setIdCaso(jpa.getCsDDiario().getCsACaso().getId());
		
		valUltimModifica(jpa.getCsDDiario());
	}

	@Override
	public IseeBean getSelected() {
		return isee;
	}

	@Override
	public boolean elimina(){
		boolean ok = false;
		try{
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(getIdDiario());
			diarioService.deleteIsee(dto);
			ok=true;
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
	public void setIdCasoController(Long idCaso) {
		// TODO Auto-generated method stub
	}

	public Long getIdDiario() {
		return idDiario;
	}

	public void setIdDiario(Long idDiario) {
		this.idDiario = idDiario;
	}

	
	@Override
	public void setIdSchedaUdc(Long idUdc) {}

	
	@Override
	public CsDValutazione getCurrentModel() {
		return null;
	}
	
	@Override
	public void setIdCaso(Long idCaso){
		super.setIdCaso(idCaso);
		if(this.getIdCaso()!=null){
			protDsuMan.setIdRichiesta("C" + this.getIdCaso());
			
			BaseDTO bdto = new BaseDTO();
			CsUiCompBaseBean.fillEnte(bdto);
			bdto.setObj(this.getIdCaso());
			CsACaso csa = casoService.findCasoById(bdto);
			protDsuMan.setCodfisc(csa.getCsASoggetto().getCsAAnagrafica().getCf());
		}
	}

	
	@Override
	public boolean isNew() {
		return !(idDiario!=null && idDiario.longValue()>0);
	}

	@Override
	public Long getIdSchedaUdc() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void onChangeAnnoRiferimento(){
		DatiIsee in = protDsuMan.cbxAnnoDsuListener();
		if(in!=null){
			this.isee.setIse(in.getIse());
			this.isee.setIsee(in.getIsee());
			this.isee.setScalaEquivalenza(in.getScalaEquivalenza());
			this.isee.setTipo(in.getTipo());
			this.isee.setDataScadenzaIsee(in.getDataScadenzaIsee());
			this.isee.setDataIsee(in.getDataPresentazioneIsee());
		}else{
			this.isee.setIse(null);
			this.isee.setIsee(null);
			this.isee.setScalaEquivalenza(null);
			this.isee.setTipo(new CsTbTipoIsee());
			this.isee.setDataScadenzaIsee(null);
			this.isee.setDataIsee(null);
		}
	}
	
	@Override
	public void onChangeTipologia() {
		protDsuMan.setTipologia(isee.getTipo().getId());
		DatiIsee in = protDsuMan.trovaAttestazione();
		if(in!=null){
			this.isee.setIse(in.getIse());
			this.isee.setIsee(in.getIsee());
			this.isee.setScalaEquivalenza(in.getScalaEquivalenza());
			this.isee.setTipo(in.getTipo());
			this.isee.setDataScadenzaIsee(in.getDataScadenzaIsee());
			this.isee.setDataIsee(in.getDataPresentazioneIsee());
		}else{
			this.isee.setIse(null);
			this.isee.setIsee(null);
			this.isee.setScalaEquivalenza(null);
			//this.isee.setTipo(in.getTipo());
			this.isee.setDataScadenzaIsee(null);
			this.isee.setDataIsee(null);
		}
	}
	
	@Override
	public String fillReportIsee() {
		String s = protDsuMan.getStampa();
		s+= " Data: "                       + (isee.getDataIsee()!=null ? ddMMyyyy.format(isee.getDataIsee()) : "          ");
		s+= " € " 						   + (isee.getIsee()!=null ? isee.getIsee() : "");
		return s;
	}
}
