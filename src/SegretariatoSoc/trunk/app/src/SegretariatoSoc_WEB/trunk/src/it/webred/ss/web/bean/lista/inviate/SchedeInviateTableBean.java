package it.webred.ss.web.bean.lista.inviate;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsInterventiSchede;
import it.webred.ss.data.model.SsMotivazioniSchede;
import it.webred.ss.data.model.SsPuntoContatto;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalante;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.OperatoreDTO;
import it.webred.ss.web.bean.SegretariatoSocSchedeTblBaseBean;
import it.webred.ss.web.bean.lista.Scheda;
import it.webred.ss.web.bean.lista.soggetti.SearchBean;
import it.webred.ss.web.bean.report.ReportBaseBean;
import it.webred.ss.web.bean.report.ReportPrivacyBean;
import it.webred.ss.web.bean.report.ReportSchedaBean;
import it.webred.ss.web.bean.util.PuntoContatto;
import it.webred.ss.web.bean.util.Ufficio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;


@ManagedBean
@ViewScoped
public class SchedeInviateTableBean extends SegretariatoSocSchedeTblBaseBean {
	
	private Scheda selectedScheda;
	private LazySchedeInviateTableDataModel dataModel;
	
	private String title;
	public DataTable dataTable ;
	
	private static final String UFFICIO = "ufficio";
	private static final String ENTE = "ente";
	private static final String UDC_SOGGETTO = "UdcSoggetto";
	

	private List<SelectItem> ptoConttInviante; 
	//private static final String DIRBASE = "/reports/";
	
    public SchedeInviateTableBean() {
    	
		dataModel = new LazySchedeInviateTableDataModel();
	
    	String selectedUfficio = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(UFFICIO);
		if(selectedUfficio != null){
	    	setTitleFromUfficio(new Long(selectedUfficio));
	    	dataModel.setUfficioId(new Long(selectedUfficio));
	        //populateSchedeFromUfficio(new Long(selectedUfficio));  
		}
		
		String selectedOrganizzazione = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(ENTE);
		if(selectedOrganizzazione != null){
			Long selectedOrganizzazioneId=new Long(selectedOrganizzazione);
			setTitleFromOrganizzazione(selectedOrganizzazioneId);
	    	dataModel.setOrganizzazioneId(selectedOrganizzazioneId);
	        //populateSchedeFromUfficio(new Long(selectedUfficio));  
		}
		
		String selectedSoggetto = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(UDC_SOGGETTO);
		if(selectedSoggetto != null){
			setTitleFromSoggetto(new Long(selectedSoggetto));
			dataModel.setSoggettoId(new Long(selectedSoggetto));
	    	//populateSchedeFromSoggetto(new Long(selectedSoggetto));  
		}
		
		ptoConttInviante = new ArrayList<SelectItem>();
		ptoConttInviante.add(new SelectItem(""));
		Ufficio ufficio = this.getPreselectedPContatto().getUfficio();
		for (SelectItem selectItem : ufficio.getListaPContatto()) {
			ptoConttInviante.add(new SelectItem(selectItem.getLabel(), selectItem.getLabel()));
		}
		
    } 
   
 	
	public LazySchedeInviateTableDataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazySchedeInviateTableDataModel dataModel) {
		this.dataModel = dataModel;
	}
	
	@Override
	public List<SelectItem> getLstOperatori() {
		if (lstOperatori==null || lstOperatori.isEmpty()) {
			lstOperatori = new ArrayList<SelectItem>();
			lstOperatori.add(new SelectItem(""));
			try {
				
				SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
						"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
					
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setOrganizzazione(this.getPreselectedPContatto().getOrganizzazione().getId());
				dto.setObj(this.getPreselectedPContatto().getUfficio().getId());
				
				List<OperatoreDTO> lst = schedaService.getListaOperatori(dto);
				for(OperatoreDTO o : lst)
				  lstOperatori.add(new SelectItem(o.getDenominazione(), o.getDenominazione()));
				
			} catch (NamingException e) {
				logger.error(e);
			}
		}
		return lstOperatori;
	}

	
	public List<SelectItem> getPtoContt() {
		return ptoConttInviante;
	} 
	
	 @Override
	 public List<SelectItem> getTipiIntervento(){
		 if(tipiIntervento==null || tipiIntervento.isEmpty()){
			try{
				 tipiIntervento = new ArrayList<SelectItem>();
				 tipiIntervento.add(new SelectItem(""));
				 SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
							"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
						
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				List<SsTipoScheda> tipi = schedaService.readTipiScheda(dto);
				
				for(SsTipoScheda tipo: tipi)
				{
					if(tipo.getTipo().toLowerCase().contains("invio"))
						tipiIntervento.add(new SelectItem(tipo.getTipo()));
				}
						
			 } catch(Exception e) {
		 		logger.error(e.getMessage(), e);
		 		addError("lettura.error");	
			}
		 }
		return tipiIntervento;
     	
     }


	private boolean setTitleFromUfficio(Long ufficioID){
		try {
    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
    				"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
    		BaseDTO dto = new BaseDTO();
        	fillUserData(dto);
        	dto.setObj(ufficioID);
        	
        	title = schedaService.readUfficio(dto).getNome();
        	
        	return true;
    		
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("lettura.error");
    		
    		return false;
		}
	}
	
	private boolean setTitleFromOrganizzazione(Long organizzazioneId){
		try {
    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
    				"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
    		BaseDTO dto = new BaseDTO();
        	fillUserData(dto);
        	dto.setOrganizzazione(organizzazioneId);
        	 //TODO ricerca organizzazione tramite id in  CS o in AR
        	title = "Ente: "+schedaService.readSsArOOrganizzazioniById(dto).getNome();
        	
        	return true;
    		
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("lettura.error");
    		
    		return false;
		}
	}
	
	
	private boolean setTitleFromSoggetto(Long soggettoID) {
		try{
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
			BaseDTO dto = new BaseDTO();
	    	fillUserData(dto);
	    	dto.setObj(soggettoID);
	    	
	    	SsAnagrafica ana = schedaService.readAnagraficaById(dto);
	    	String denominazione = ana.getCognome() + " " + ana.getNome();
	    	title = "Soggetto: " + denominazione.toUpperCase();
	    	
	    	return true;
		
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
			
			return false;
		}
	}
    
    public void onViewClick(){
    	if(selectedScheda == null){
    		printSelectError();
    	} else {
    		
    		//il controllo sui diritti di visualizzare la scheda è inutile in questa fase per una scheda ricevuta
    		//lo metterei invece al momento di elaborare i dati della pagina viewScheda
    		//potrei infatti accedere alla pagina modificando i parametri in query string e visualizzare schede che non ho ricevuto
    		
	    		logAction(read, selectedScheda.getId());
	    		//TODO aggiungi parametro di scheda remota
	    		String url = addParameter(VEDI_SCHEDA_URL, SCHEDA_KEY, selectedScheda.getId()+"");
	    		url = addParameter(url, "vediSchedaRemota", "true");
        		
	    		url = buildCompleteUrl(url);
	    		try {
	    			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	    		} catch (IOException e) {
	    			logger.error(e);
	    		}
    		
    	}
    }
    
    public void onRiceviClick(){
    	if(selectedScheda == null) 				printSelectError();
    	else if(selectedScheda.isRicevuta()) 	addError("schedaGiaRicevuta.error");
    	else if(!selectedScheda.isRicevuta()){
    		
				logAction(receive, selectedScheda.getId());
				
	    		String url = addParameter(RICEVI_SCHEDA_URL, SCHEDA_KEY, selectedScheda.getId()+"");
	    		url = addParameter(url, RICEVI_SCHEDA, "true");
	    		url = addParameter(url, ZONA_SOCIALE_KEY, selectedScheda.getOrigZonaSociale());
	    		
	    		url = buildCompleteUrl(url);
	    		
	    		try {
	    			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	    		} catch (IOException e) {
	    			logger.error(e);
	    		} 
    	}
    }
    
    
    private String buildCompleteUrl(String url) {
    	//Matteo Leandri 05/07/2016
		//recupero il parametro currentLocation. La pagina sulla quale viene fatto il redirect lo utilizza per creare il nome del pulsante "indietro"
    	String result = url;
		String clParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentLocation"); 
		result = addParameter(result, "currentLocation", clParam != null ? clParam : "");
		
		String previousUrl = SCHEDE_INVIATE_URL;
		if (dataModel.getUfficioId() != null)
			previousUrl = addParameter(previousUrl, UFFICIO, dataModel.getUfficioId().toString());
		if (dataModel.getOrganizzazioneId() != null)
			previousUrl = addParameter(previousUrl, ENTE, dataModel.getOrganizzazioneId().toString());
		if (dataModel.getSoggettoId() != null)
			previousUrl = addParameter(previousUrl, UDC_SOGGETTO, dataModel.getSoggettoId().toString());
		
		result = addParameter(result, "previousPage", previousUrl);
		return result;
    }
    
    
    public boolean isPuntoDiContattoSelezionato() {
    	PuntoContatto pCont = this.getPreselectedPContatto();
		return (pCont != null && pCont.getIdPContatto()!=null);
    }

    public void onNewClick(){
    	if (!isPuntoDiContattoSelezionato()) {
			addError("no.puntoContatto.error");
		} else if (selectedScheda == null){
    		printSelectError();
    	} else if (selectedScheda.getCognome() == null || selectedScheda.getCognome().isEmpty() || selectedScheda.getNome() == null || selectedScheda.getNome().isEmpty()){
    		addError("seleziona.altro.soggetto.error");
    	} else {
    		String url="";    		
    		try {
    			String clParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentLocation");
    			url = addParameter(NUOVA_SCHEDA_URL, SCHEDA_KEY, selectedScheda.getId()+"");
        		url = addParameter(url, "currentLocation", clParam  != null ? clParam : "");
        		url = addParameter(url, "creaNuovaScheda", "true");
        		url = buildCompleteUrl(url);
	    		
    		} catch (Exception e) {
    			addError("lettura.error");
    			logger.error(e.getMessage(),e);
    		}
    		
    		try {
    			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    		} catch (IOException e) {
    			logger.error(e);
    		}
    	}
    }
    
    public void onPrivacyClick(){
    	if(selectedScheda == null){
    		printSelectError();
    	} else {
    		// TODO: apri dialog per salvare foglio privacy
    	}
    }
    
    public void onDeleteClick(){
    	if(selectedScheda == null){
    		printSelectError();
    	} else {
    		try {
    			AccessTableSchedaSegrSessionBeanRemote schedaSegrService =
						(AccessTableSchedaSegrSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
				
    			String statoScheda = null;
    			if (selectedScheda.getId()!=null) {
					// http://progetti.asc.webred.it/browse/SISO-419
					// se CS_SS_SCHEDA_SEGR.STATO!=NULL non posso fare modifiche
					it.webred.cs.csa.ejb.dto.BaseDTO baseDto = new it.webred.cs.csa.ejb.dto.BaseDTO();
					fillUserData(baseDto);
					baseDto.setObj(selectedScheda.getId());
					statoScheda = schedaSegrService.findStatoSchedaSegrById(baseDto);
				}
    			
    			if (statoScheda==null || statoScheda.isEmpty()) {
	        		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
	        			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
	    			
	    			BaseDTO dto = new BaseDTO();
	    			fillUserData(dto);
	    			dto.setObj(selectedScheda.getId());
	            	schedaService.eliminaScheda(dto);
	            	
	            	//schede.remove(selectedScheda);
	            	//dataModel = new SchedeTableDataModel(schede);
	            	
	            	logAction(delete, selectedScheda.getId());
    			} else {
					addError("policy.error.cartella.state");
				}
        	} catch(Exception e) {
        		logger.error(e.getMessage(), e);
        		addError("scrittura.error");
        		
    		}
    	}
    }
    
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
  
	public Scheda getSelectedScheda() {  
        return selectedScheda;  
    }  
    public void setSelectedScheda(Scheda selectedScheda) {  
        this.selectedScheda = selectedScheda;  
    }  
  
  
	public StreamedContent getFileScheda(){
		 ReportSchedaBean rb = new ReportSchedaBean(this.selectedScheda!=null ? this.selectedScheda.getId():null, this.selectedScheda!=null ?  this.selectedScheda.getUfficioId():null);
		 return rb.getFile();
	 }
	 
	public StreamedContent getFilePrivacy(){
		ReportPrivacyBean rb = new ReportPrivacyBean(this.selectedScheda.getId(),this.selectedScheda.getUfficioId());
		return rb.getFile();
	}
	
	public void preProcessListaPDF(Object document) {
		try {
			
		ReportBaseBean rb = new ReportBaseBean();
		rb.preProcessPDF(document);
		
		} catch (Exception e) {
			addError("pdf.error");
	 		logger.error(e);
		}
	}


	public List<SelectItem> getPtoConttInviante() {
		return ptoConttInviante;
	}
 

}
