package it.webred.cs.json.familiariConviventi.ver1;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.familiariConviventi.DatiSocialiFamiliariConviventiPdfDTO;
import it.webred.cs.json.familiariConviventi.FamiliariManBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

public class FamConviventiManBean extends FamiliariManBaseBean{

	private static final long serialVersionUID = 1L;
	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	private List<CsTbTipologiaFamiliare> lstCsTbTipologiaFam;
	private List<SelectItem> lstGruppiVulnerabili;
	private List<SelectItem> lstTipologiaFam;
	private FamConviventiController controller;

	public FamConviventiManBean() {
		super();
		controller = new FamConviventiController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());
	}
	
	@Override
	public boolean validaData ( ) {
		
		boolean validazioneOk = true;
		List<String> messagges;
		messagges = controller.validaData();
		if( messagges.size() > 0 ) {
			for(String msg : messagges)
				addWarning("Familiari e Conviventi", msg);
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


	public FamConviventiBean getJsonCurrent() {
		return controller.getJsonCurrent();
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
			if(this.getVersionLowerCase().equals(bean.getVersionLowerCase()))
				controller.load((FamConviventiBean)bean.getSelected());
			else
				copyDataBetweenVersions(bean);
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		setIdCaso(bean.getIdCaso());
		setIdSchedaUdc(bean.getIdSchedaUdc());
	}

	public void copyDataBetweenVersions(ISchedaValutazione bean){
		try{
			controller.copyDataBetweenVersions(bean.getSelected());
		} catch (Exception e) {
			//addErrorFromProperties("caricamento.error");
			logger.error("Errore trasferimento dati FamConviventiBean - versioni diverse"+e.getMessage(),e);
		}
	}

	@Override
	public FamConviventiBean getSelected() {
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

	private void loadTipologieNucleo(){
		
		CeTBaseObject cet = new CeTBaseObject();
		fillEnte(cet);
		
		lstTipologiaFam = new ArrayList<SelectItem>();
		lstCsTbTipologiaFam = new ArrayList<CsTbTipologiaFamiliare>();
		List<CsTbTipologiaFamiliare> lst1 = confService.getTipologieFamiliari(cet);
		for (CsTbTipologiaFamiliare p : lst1) {
			SelectItem fa = new SelectItem(p.getId(), p.getDescrizione());
			fa.setDisabled(!"1".equals(p.getAbilitato()));
			lstTipologiaFam.add(fa);
			lstCsTbTipologiaFam.add(p);
		}
		
	}
	
	@Override
	public List<SelectItem> getListaTipologiaNucleo() {
		if(lstTipologiaFam == null)
			this.loadTipologieNucleo();
		return lstTipologiaFam;

	}
	
	@Override
	public List<CsTbTipologiaFamiliare> getLstCsTbTipologiaFam() {
		
		if(lstCsTbTipologiaFam == null)
			this.loadTipologieNucleo();
		return lstCsTbTipologiaFam;
	}
	
	@Override
	public List<SelectItem> getListaGruppoVulnerabile() {
		if(lstGruppiVulnerabili == null){
			lstGruppiVulnerabili = new ArrayList<SelectItem>();
			lstGruppiVulnerabili.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbGVulnerabile> lst = confService.getGruppiVulnerab(bo);
			if (lst != null) {
				for (CsTbGVulnerabile p : lst) {
					SelectItem fa = new SelectItem(p.getId(), p.getDescrizione());
					fa.setDisabled(!"1".equals(p.getAbilitato()));
					lstGruppiVulnerabili.add(fa);
				}
			}		
		}
		return lstGruppiVulnerabili;
	}
	

	@Override
	public void changeTipoNucleo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeGruppoVulnerabile() {
		// TODO Auto-generated method stub
		
	}

	public void changeConFigli(){
		this.getJsonCurrent().initPnlFigli();
	}
	
/*	@Override
	public long getTipologiaFamiliareId(){
		return this.getJsonCurrent().getTipologiaFamiliare();
	}*/

	@Override
	public void fillReport(DatiSocialiFamiliariConviventiPdfDTO famConv) {
		
		famConv.setVersioneFamConv(this.getVersionLowerCase());
		
		CsTbTipologiaFamiliare fa = getTipoNucleo(this.getJsonCurrent().getTipologiaFamiliare());
		famConv.setTipologiaNucleo(fa!=null ? fa.getDescrizione() : null);
		
		famConv.setHaFigli(this.getJsonCurrent().isConFigli());
		famConv.setNumeroFigli(Integer.toString(this.getJsonCurrent().getConFigliNum()));
		famConv.setNumeroMinori(Integer.toString(this.getJsonCurrent().getConMinoriNum()));
		
		CsTbGVulnerabile gr = this.getGruppoVulnerabile(this.getJsonCurrent().getGruppoVulnerabile());
		if(gr!=null) famConv.setGruppoVulnerabile(gr.getDescrizione());
		
		CsTbGVulnerabile gr2 = this.getGruppoVulnerabile(this.getJsonCurrent().getGruppoVulnerabile2());
		if(gr2!=null) famConv.setGruppoVulnerabile2(gr2.getDescrizione());
		
		CsTbGVulnerabile gr3 = this.getGruppoVulnerabile(this.getJsonCurrent().getGruppoVulnerabile3());
		if(gr3!=null) famConv.setGruppoVulnerabile3(gr3.getDescrizione());
		
		famConv.setConiugeOCompagno(this.getJsonCurrent().isConCompagnoConiuge());
		famConv.setGenitoriOAffidatari(Integer.toString(this.getJsonCurrent().getConGenitoriAffidatariNum()));
		famConv.setAltriParenti(Integer.toString(this.getJsonCurrent().getConAltriParentiNum()));
		famConv.setAltriConviventiItaliani(Integer.toString(this.getJsonCurrent().getConItalianiNum()));
		famConv.setAltriConviventiStranieri(Integer.toString(this.getJsonCurrent().getConStranieriNum()));
		
		famConv.setMagExtF(this.getJsonCurrent().getMaggioriEst().getNumFemmine()); 
		famConv.setMagExtM(this.getJsonCurrent().getMaggioriEst().getNumMaschi());
		famConv.setMagItaF(this.getJsonCurrent().getMaggioriIta().getNumFemmine());
		famConv.setMagItaM(this.getJsonCurrent().getMaggioriIta().getNumMaschi());
		famConv.setMinExtF(this.getJsonCurrent().getMinoriEst().getNumFemmine());
		famConv.setMinExtM(this.getJsonCurrent().getMinoriEst().getNumMaschi());
		famConv.setMinItaF(this.getJsonCurrent().getMinoriIta().getNumFemmine());
		famConv.setMinItaM(this.getJsonCurrent().getMinoriIta().getNumMaschi());
		
	}

	@Override
	public CsTbGVulnerabile getGruppoVulnerabile() {
		String s = this.getJsonCurrent().getGruppoVulnerabile();
		return this.getGruppoVulnerabile(s);
	}

}
