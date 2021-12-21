package it.webred.cs.csa.web.manbean.schedaSegr;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti.PermessiCartella;
import it.webred.cs.jsf.interfaces.IListaSchedeSegr;
import it.webred.cs.jsf.manbean.IterDialogMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.sociosan.ejb.client.ArgoBufferManagerSessionBeanRemote;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;

@ManagedBean
@ViewScoped
public class ListaSchedeSegrBean extends CsUiCompBaseBean implements IListaSchedeSegr {

	private LazyDataModel<SchedaSegr> lazyListaSchedeSegrModel;
	private SchedaSegr schedaSelected;
	private String note;
	private List<SelectItem> lstProvenienza;
	
	@ManagedProperty( value="#{iterDialogMan}")
	private IterDialogMan iterDialogMan;
	
	private AccessTableSchedaSegrSessionBeanRemote schedaSegrService = (AccessTableSchedaSegrSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
	private SsSchedaSessionBeanRemote udcService = (SsSchedaSessionBeanRemote) getEjb("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
	private ArgoBufferManagerSessionBeanRemote argoBufferManager = (ArgoBufferManagerSessionBeanRemote) getEjb("SocioSanitario", "SocioSanitario_EJB", "ArgoBufferManagerSessionBean");

	
	public ListaSchedeSegrBean() {
		lazyListaSchedeSegrModel = new LazyListaSchedeSegrModel();
		
		CeTBaseObject cet = new CeTBaseObject();
		CsUiCompBaseBean.fillEnte(cet);
		this.lstProvenienza = confService.getSsProvenienza(cet);	
	}
	
	@Override
	public void respingiScheda(){
		try {
			
			if(schedaSelected.isPropostaPIC()){
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				
				//Aggiorno lo stato CS_SS_SCHEDA_SEGR
				dto.setObj(schedaSelected.getCsSsId());
				dto.setObj2(note);
				schedaSegrService.respingiScheda(dto);
				
				if(schedaSelected.isProvenienzaUDC()){
					//Recupero il reponsabile della scheda
					it.webred.ss.ejb.dto.BaseDTO sdto = new it.webred.ss.ejb.dto.BaseDTO();
					fillEnte(sdto);
					sdto.setObj(schedaSelected.getId());	// SISO-938 bisogna usare CS_SS_SCHEDA_SEGR.SCHEDA_ID
					SsScheda s = udcService.readScheda(sdto);
					String cf = s.getAccesso().getOperatore();
					
					sdto.setObj(s.getSegnalato());
					SsSchedaSegnalato segnalato = udcService.readSegnalatoById(sdto);
					String denominazione = segnalato.getAnagrafica().getCognome()+" "+segnalato.getAnagrafica().getNome();
					
					AmAnagrafica ana = getAnagraficaByUsername(cf);
					String emailTo = ana.getAmUser().getEmail();
					//Recuperare indirizzo e inviare email.
					try{
						// Segnalibri
						String subject = "Respingimento scheda "+this.getLabelSegrSociale();
						String messageBody = "L'operatore di Cartella Sociale "+getCognomeNomeUtente(getCurrentUsername())+" ha respinto la scheda "+this.getLabelSegrSociale()+" del soggetto "+denominazione+". \n";
						messageBody += note!=null ? "Note: "+note : "";
			
						argoBufferManager.sendSimpleMailFromSISO(emailTo, subject,messageBody);
						
					}catch(Exception e){
						logger.error("__ Errore in invio email per respingimento scheda UDC con idScheda:"+schedaSelected.getId()+" "+e.getMessage());
					}
				}
			}
		
		} catch(Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void vediScheda() {
		
		try {
			if(schedaSelected.isPropostaPIC()){
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(schedaSelected.getCsSsId());
				schedaSegrService.vediScheda(dto);
			}
		
		} catch(Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public List<SelectItem> getLstProvenienza() {
		return this.lstProvenienza;
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
	
	public LazyDataModel<SchedaSegr> getLazyListaSchedeSegrModel() {
		return lazyListaSchedeSegrModel;
	}

	public void setLazyListaSchedeSegrModel(
			LazyDataModel<SchedaSegr> lazyListaSchedeSegrModel) {
		this.lazyListaSchedeSegrModel = lazyListaSchedeSegrModel;
	}

	@Override
	public boolean isRenderNuovaCartella() {
		return checkPermesso(PermessiCartella.ITEM, PermessiCartella.CREAZIONE_CASO);
	}
	
	@Override
	public boolean isRenderCaricaCartella() {
		return checkPermesso(PermessiCartella.ITEM, PermessiCartella.CREAZIONE_CASO);
	}
	
	@Override
	public boolean isRenderVista(){
		return true;
	}
	
	@Override
	public boolean isRenderRespinta(){
		return true;
	}

	public AccessTableSchedaSegrSessionBeanRemote getSchedaSegrService() {
		return schedaSegrService;
	}

	public void setSchedaSegrService(
			AccessTableSchedaSegrSessionBeanRemote schedaSegrService) {
		this.schedaSegrService = schedaSegrService;
	}

	public SchedaSegr getSchedaSelected() {
		return schedaSelected;
	}

	public void setSchedaSelected(SchedaSegr schedaSelected) {
		this.schedaSelected = schedaSelected;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
