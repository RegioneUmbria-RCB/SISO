package it.webred.cs.json.isee.ver1;

import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.data.model.CsTbTipoIsee;
import it.webred.cs.jsf.bean.IseeBean;
import it.webred.cs.jsf.manbean.ProtocolloDsuMan;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.isee.IseeManBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.Date;
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
	      isee.setProtocolloBean(this.protDsuMan.getDto());
	      
	   try{  
		  if(!validaData())
	    	  return ok;
	      
			CsDIsee jpa = new CsDIsee();
		    BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			
			if(getIdDiario() != null) {
				
				dto.setObj(getIdDiario());
				jpa = diarioService.findIseeById(dto);
				
				//modifica
				jpa.getCsDDiario().setUsrMod(dto.getUserId());
				jpa.getCsDDiario().setDtMod(new Date());
				riversaIseeToJpa(jpa);
				
				dto.setObj(jpa);
				diarioService.updateIsee(dto);
			}
			else {
				
				CsOOperatoreSettore opSettore = getCurrentOpSettore();
				
				BaseDTO bdto = new BaseDTO();
				fillEnte(bdto);
				bdto.setObj(this.getIdCaso());
				CsACaso csa = casoService.findCasoById(bdto);
				
		        //CsACaso csa = new CsACaso();
		        //csa.setId(getIdCaso());
		        
		        CsTbTipoDiario cstd = new CsTbTipoDiario(); 
		        cstd.setId(new Long(DataModelCostanti.TipoDiario.ISEE_ID)); 
		        
		        jpa.getCsDDiario().setCsACaso(csa);
		        jpa.getCsDDiario().setDtIns(new Date());
		        jpa.getCsDDiario().setUserIns(dto.getUserId());
		        jpa.getCsDDiario().setResponsabileCaso(getOperResponsabileCaso(getIdCaso()).getId());
		        jpa.getCsDDiario().setCsTbTipoDiario(cstd);
		        jpa.getCsDDiario().setCsOOperatoreSettore(opSettore);
		        riversaIseeToJpa(jpa);
		        dto.setObj(jpa);
				CsDDiario csd = diarioService.saveIsee(dto);
				setIdDiario(csd.getId());
				
				//Invio alert nuovo inserimento
				bdto.setObj(csa.getCsASoggetto());
				bdto.setObj2(opSettore);
				bdto.setObj3(DataModelCostanti.TipiAlertCod.ISEE);
				bdto.setObj4("un nuovo ISEE");
				
				alertService.addAlertNuovoInserimentoToResponsabileCaso(bdto);
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
		this.protDsuMan = new ProtocolloDsuMan(jpa);
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
	public boolean isNew() {
		return !(idDiario!=null && idDiario.longValue()>0);
	}

	@Override
	public Long getIdSchedaUdc() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String fillReportIsee() {
		String s =  this.protDsuMan.getStampa();
		s+= " Data: "                       + (isee.getDataIsee()!=null ? ddMMyyyy.format(isee.getDataIsee()) : "          ");
		s+= " € " 						   + (isee.getIsee()!=null ? isee.getIsee() : "");
		return s;
	}
}
