package it.webred.ss.web.bean.lista.complete;

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
import it.webred.ss.web.bean.SegretariatoSocSchedeTblBaseBean;
import it.webred.ss.web.bean.lista.Scheda;
import it.webred.ss.web.bean.lista.soggetti.SearchBean;
import it.webred.ss.web.bean.report.ReportBaseBean;
import it.webred.ss.web.bean.report.ReportPrivacyBean;
import it.webred.ss.web.bean.report.ReportSchedaBean;
import it.webred.ss.web.bean.util.PuntoContatto;

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
public class SchedeTableBean extends SegretariatoSocSchedeTblBaseBean {
	
	private Scheda selectedScheda;
	private LazySchedeTableDataModel dataModel;
	
	private String title;
	public DataTable dataTable ;
	private static final String NUOVA_SCHEDA = "editScheda.faces";
	private static final String VEDI_SCHEDA = "viewScheda.faces";
	private static final String PAGINA_CORRENTE = "schede.faces";
	private static final String UFFICIO = "ufficio";
	private static final String UDC_SOGGETTO = "UdcSoggetto";
	//private static final String DIRBASE = "/reports/";
	
    public SchedeTableBean() {
    	
		dataModel = new LazySchedeTableDataModel();
	
    	String selectedUfficio = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(UFFICIO);
		if(selectedUfficio != null){
	    	setTitleFromUfficio(new Long(selectedUfficio));
	    	dataModel.setUfficioId(new Long(selectedUfficio));
	        //populateSchedeFromUfficio(new Long(selectedUfficio));  
		}
		
		String selectedSoggetto = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(UDC_SOGGETTO);
		if(selectedSoggetto != null){
			setTitleFromSoggetto(new Long(selectedSoggetto));
			dataModel.setSoggettoId(new Long(selectedSoggetto));
	    	//populateSchedeFromSoggetto(new Long(selectedSoggetto));  
		}
		
    } 
   
 	
	public LazySchedeTableDataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazySchedeTableDataModel dataModel) {
		this.dataModel = dataModel;
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
    		boolean canAccessUfficio = this.canAccessUfficio(selectedScheda.getUfficioId());
    		if(canAccessUfficio){
	    		logAction(read, selectedScheda.getId());
	    		
	    		String url = addParameter(VEDI_SCHEDA, "id", selectedScheda.getId()+"");
	    		url = buildCompleteUrl(url);
	    		try {
	    			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	    		} catch (IOException e) {
	    			logger.error(e);
	    		}
    		}else printPolicyUfficiError();	
    	}
    }
    
    public void onEditClick(){
    	if(selectedScheda == null){
    		printSelectError();
    	} else {
    		boolean canAccessUfficio = this.canAccessUfficio(selectedScheda.getUfficioId());
    		if(canAccessUfficio){ 
    			if(canEdit() || isOwner(selectedScheda.getOperatore())){
    			
	    			logAction(edit, selectedScheda.getId());
	    			
		    		String url = addParameter(NUOVA_SCHEDA, "id", selectedScheda.getId()+"");
		    		
		    		url = buildCompleteUrl(url);
		    		
		    		try {
		    			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		    		} catch (IOException e) {
		    			logger.error(e);
		    		}
	    		 }else printPolicyError();
    		}else printPolicyUfficiError();
    	}
    }
    
    
    private String buildCompleteUrl(String url) {
    	//Matteo Leandri 05/07/2016
		//recupero il parametro currentLocation. La pagina sulla quale viene fatto il redirect lo utilizza per creare il nome del pulsante "indietro"
    	String result = url;
		String clParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentLocation"); 
		result = addParameter(result, "currentLocation", clParam != null ? clParam : "");
		
		String previousUrl = PAGINA_CORRENTE;
		if (dataModel.getUfficioId() != null)
			previousUrl = addParameter(previousUrl, UFFICIO, dataModel.getUfficioId().toString());
		if (dataModel.getSoggettoId() != null)
			previousUrl = addParameter(previousUrl, UDC_SOGGETTO, dataModel.getSoggettoId().toString());
		
		result = addParameter(result, "previousPage", previousUrl);
		return result;
    }
    
    public void goBack() {
    	
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("home.faces");
		} catch (IOException e) {
			logger.error(e);
		}
		
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
    			url = addParameter(NUOVA_SCHEDA, "id", selectedScheda.getId()+"");
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
		 ReportSchedaBean rb = new ReportSchedaBean(this.selectedScheda.getId(),this.selectedScheda.getUfficioId());
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
 

}
