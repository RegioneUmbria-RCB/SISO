package it.webred.cs.csa.web.manbean.fascicolo.schedeSegr;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.schedaSegr.SchedaSegr;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.jsf.interfaces.IListaSchedeSegr;
import it.webred.cs.jsf.manbean.IterDialogMan;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

public class SchedeSegrBean extends FascicoloCompBaseBean implements IListaSchedeSegr {

	private List<SchedaSegr> lazyListaSchedeSegrModel;
	private SchedaSegr schedaSelected;
	
	@ManagedProperty( value="#{iterDialogMan}")
	private IterDialogMan iterDialogMan;
	
	private AccessTableSchedaSegrSessionBeanRemote csSchedaSegrService = (AccessTableSchedaSegrSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
	
	@Override
	public void initializeData(Object data) {
		
		try{
			//Schede Segretariato nel fascicolo
			lazyListaSchedeSegrModel = new ArrayList<SchedaSegr>();
			SsSchedaSessionBeanRemote ssSchedaSegrService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			AccessTableIterStepSessionBeanRemote iterSessionBean = (AccessTableIterStepSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
			AccessTableSoggettoSessionBeanRemote soggSessionBean = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
			
			BaseDTO csb = new BaseDTO();
			CsUiCompBaseBean.fillEnte(csb);
			csb.setObj(idSoggetto);
		
			CsAAnagrafica anag = soggSessionBean.getAnagraficaById(csb);
			if(anag!=null && anag.getCf()!=null){
				String cf = anag!=null ? anag.getCf() : null;
				
				it.webred.ss.ejb.dto.BaseDTO ssb = new it.webred.ss.ejb.dto.BaseDTO();
				CsUiCompBaseBean.fillEnte(ssb);
				
				ssb.setObj(cf);
				List<SsScheda> list = ssSchedaSegrService.readSchedeByCF(ssb);
				
				for(SsScheda ssScheda: list){
					if(ssScheda.getCompleta() && !ssScheda.getEliminata()){
					//bDto.setObj(csScheda.getId());
					//SsScheda ssScheda = ssSchedaSegrService.readScheda(bDto);
					ssb.setObj(ssScheda.getSegnalato());
	        		SsSchedaSegnalato segnalato = ssSchedaSegrService.readSegnalatoById(ssb);
	        		ssb.setObj(ssScheda.getTipo());
	        		SsTipoScheda tipoScheda = ssSchedaSegrService.readTipoSchedaById(ssb);
	        		SchedaSegr schedaSegr = new SchedaSegr(ssScheda, segnalato, tipoScheda);
	        		valorizzaCondLavoro(schedaSegr);
	        		
	        		schedaSegr.setShowStatoCartella(false);
	        		AmAnagrafica amAna = getAnagraficaByUsername(ssScheda.getAccesso().getOperatore());
	        		if(amAna != null) {
	        			schedaSegr.setOperatore(amAna.getCognome() + " " + amAna.getNome());
	        		}
	        		
	        		//Allora la ritrovo anche in CS_SS_SCHEDA_SEGR
	        		if(tipoScheda!=null && tipoScheda.getPresa_in_carico()){
	        			
		        		csb.setObj(ssScheda.getId());
		        		CsSsSchedaSegr csScheda = csSchedaSegrService.findSchedaSegrById(csb);
		        		schedaSegr.setCsSsSchedaSegr(csScheda);
		
						// info stato caso
		        		if(csScheda!=null && csScheda.getCsASoggetto() != null) {
		        			
		        			//Dal momento che il valore in questa fase è impostato a FALSE, 
		        			//non vale la pena caricare il dato, che non viene comunque mostrato
		        			if(schedaSegr.isShowStatoCartella()){
			        			CsACaso caso = csScheda.getCsASoggetto().getCsACaso();
								IterInfoStatoMan casoInfo = new IterInfoStatoMan();
								
								BaseDTO itDto = new BaseDTO();
								fillEnte(itDto);
								itDto.setObj(caso.getId());
								CsIterStepByCasoDTO itStep = iterSessionBean.getLastIterStepByCasoDTO(itDto);
								
								if( itStep != null )
									casoInfo.initialize( itStep);
								schedaSegr.setLastIterStepInfo(casoInfo);
		        			}
		        		}
	        		}
	        		if(DataModelCostanti.SchedaSegr.STATO_CREATA.equals(schedaSegr.getStato()))
	        			lazyListaSchedeSegrModel.add(0,schedaSegr);
	        		else 
	        			lazyListaSchedeSegrModel.add(schedaSegr);
				}
			  }
			}
		
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	
	
	public ActionListener getCloseDialog() {
	    return new ActionListener() {
	        @Override
	        public void processAction(ActionEvent event) throws AbortProcessingException {
	        	//loadListaCasi();
	        }
	    };
	}
	
	public IterDialogMan getIterDialogMan() {
		return iterDialogMan;
	}

	public void setIterDialogMan(IterDialogMan iterDialogMan) {
		this.iterDialogMan = iterDialogMan;
	}

	public List<SchedaSegr> getLazyListaSchedeSegrModel() {
		return lazyListaSchedeSegrModel;
	}

	public void setLazyListaSchedeSegrModel(
			List<SchedaSegr> lazyListaSchedeSegrModel) {
		this.lazyListaSchedeSegrModel = lazyListaSchedeSegrModel;
	}
		
	public boolean isRenderNuovaCartella() {
		return false;
	}
	
	public boolean isRenderCaricaCartella() {
		return false;
	}

	public AccessTableSchedaSegrSessionBeanRemote getCsSchedaSegrService() {
		return csSchedaSegrService;
	}

	public void setCsSchedaSegrService(AccessTableSchedaSegrSessionBeanRemote csSchedaSegrService) {
		this.csSchedaSegrService = csSchedaSegrService;
	}

	public SchedaSegr getSchedaSelected() {
		return schedaSelected;
	}

	public void setSchedaSelected(SchedaSegr schedaSelected) {
		this.schedaSelected = schedaSelected;
	}
	
	  private void valorizzaCondLavoro(SchedaSegr scheda){
	    	try{
	    		SsSchedaSegnalato segnalato = scheda.getSsSchedaSegnalato();
	    	if(segnalato.getLavoro()!=null){
	    		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
	    		this.fillEnte(d);
	    		d.setObj(segnalato.getLavoro());
	    		CsTbCondLavoro cl = configService.getCondLavoroById(d);
	    		scheda.setLavoro(cl);
	    	}
			
	    	} catch(Exception e) {
	    		logger.error(e.getMessage(), e);
	    		addError("lettura.error","Impossibile recuperare la condizione di lavoro");
			}
	    }
	    
}
