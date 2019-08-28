package it.webred.cs.csa.web.manbean.schedaSegr;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiCaso;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.jsf.interfaces.IListaSchedeSegr;
import it.webred.cs.jsf.manbean.IterDialogMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.sociosan.ejb.client.CTConfigClientSessionBeanRemote;
import it.webred.mailing.MailUtils;
import it.webred.mailing.MailUtils.MailAddressList;
import it.webred.mailing.MailUtils.MailParamBean;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;

import javax.activation.FileDataSource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.primefaces.model.LazyDataModel;

@ManagedBean
@ViewScoped
public class ListaSchedeSegrBean extends CsUiCompBaseBean implements IListaSchedeSegr {

	private LazyDataModel<SchedaSegr> lazyListaSchedeSegrModel;
	private SchedaSegr schedaSelected;
	private String note;
	
	@ManagedProperty( value="#{iterDialogMan}")
	private IterDialogMan iterDialogMan;
	
	private AccessTableSchedaSegrSessionBeanRemote schedaSegrService = (AccessTableSchedaSegrSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
	private SsSchedaSessionBeanRemote udcService = (SsSchedaSessionBeanRemote) getEjb("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
	private CTConfigClientSessionBeanRemote mailConf = (CTConfigClientSessionBeanRemote) getEjb("SocioSanitario","SocioSanitario_EJB", "CTConfigClientSessionBean");
	
	
	public void respingiScheda() {
		
		try {
			
			CsSsSchedaSegr scheda = schedaSelected.getCsSsSchedaSegr();
			if(scheda!=null){
				scheda.setNotaStato(note);
				scheda.setStato(DataModelCostanti.SchedaSegr.STATO_RESPINTA);
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				
				//Aggiorno lo stato CS_SS_SCHEDA_SEGR
				dto.setObj(scheda);
				schedaSegrService.updateSchedaSegr(dto);
				
				//Recupero il reponsabile della scheda
				it.webred.ss.ejb.dto.BaseDTO sdto = new it.webred.ss.ejb.dto.BaseDTO();
				fillEnte(sdto);
				sdto.setObj(scheda.getId());
				SsScheda s = udcService.readScheda(sdto);
				String cf = s.getAccesso().getOperatore();
				
				sdto.setObj(s.getSegnalato());
				SsSchedaSegnalato segnalato = udcService.readSegnalatoById(sdto);
				String denominazione = segnalato.getAnagrafica().getCognome()+" "+segnalato.getAnagrafica().getNome();
				
				AmAnagrafica ana = getAnagraficaByUsername(cf);
				String emailTo = ana.getAmUser().getEmail();
				//Recuperare indirizzo e inviare email.
				
				if(emailTo!=null){
					try{
						// Now try to send email
						MailAddressList addressTO = new MailAddressList(emailTo);
						MailAddressList addressCC = new MailAddressList();
						MailAddressList addressBCC = new MailAddressList();
			
						// Segnalibri
						String subject = "Respingimento scheda "+this.getLabelSegrSociale();
						String messageBody = "L'operatore di Cartella Sociale "+this.getCognomeNomeUtente(getCurrentUsername())+" ha respinto la scheda "+this.getLabelSegrSociale()+" del soggetto "+denominazione+". \n";
						messageBody += note!=null ? "Note: "+note : "";
			
						MailParamBean params = mailConf.getSISOMailParametres();
						MailUtils.sendEmail(params, addressTO, addressCC, addressBCC, subject,messageBody, (FileDataSource[]) null);
						
					}catch(Exception e){
						logger.error("__ Errore in invio email per respingimento scheda UDC con id:"+scheda.getId()+" "+e.getMessage());
					}
				}
				
			}
		
		} catch(Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void vediScheda() {
		
		try {
			
			CsSsSchedaSegr scheda = schedaSelected.getCsSsSchedaSegr();
			if(scheda!=null){
				scheda.setStato(DataModelCostanti.SchedaSegr.STATO_VISTA);
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(scheda);
				schedaSegrService.updateSchedaSegr(dto);
			}
		
		} catch(Exception e) {
			addErrorFromProperties("salva.error");
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
	
	public ListaSchedeSegrBean() {
		lazyListaSchedeSegrModel = new LazyListaSchedeSegrModel();
	}

	public LazyDataModel<SchedaSegr> getLazyListaSchedeSegrModel() {
		return lazyListaSchedeSegrModel;
	}

	public void setLazyListaSchedeSegrModel(
			LazyDataModel<SchedaSegr> lazyListaSchedeSegrModel) {
		this.lazyListaSchedeSegrModel = lazyListaSchedeSegrModel;
	}

	public boolean isRenderNuovaCartella() {
		return checkPermesso(PermessiCaso.ITEM, PermessiCaso.CREAZIONE_CASO);
	}
	
	public boolean isRenderCaricaCartella() {
		return checkPermesso(PermessiCaso.ITEM, PermessiCaso.CREAZIONE_CASO);
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
