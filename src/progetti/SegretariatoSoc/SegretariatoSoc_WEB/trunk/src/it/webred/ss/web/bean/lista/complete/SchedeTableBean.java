package it.webred.ss.web.bean.lista.complete;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.data.DataModelCostanti;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.report.DatiPrivacyPdfDTO;
import it.webred.ss.ejb.dto.report.DatiSchedaPdfDTO;
import it.webred.ss.ejb.dto.report.RiferimentoPdfDTO;
import it.webred.ss.web.bean.SegretariatoSocSchedeTblBaseBean;
import it.webred.ss.web.bean.lista.Scheda;
import it.webred.ss.web.bean.report.ReportBaseBean;
import it.webred.ss.web.bean.report.ReportPrivacyBean;
import it.webred.ss.web.bean.report.ReportSchedaBean;
import it.webred.ss.web.bean.util.PuntoContatto;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.StreamedContent;


@ManagedBean
@ViewScoped
public class SchedeTableBean extends SegretariatoSocSchedeTblBaseBean {
	
	private Scheda selectedScheda;
	private LazySchedeTableDataModel dataModel;
	
	private String title;
	private boolean renderUfficio;
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
	    	renderUfficio=false;
	        //populateSchedeFromUfficio(new Long(selectedUfficio));  
		}
		
		String selectedSoggetto = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(UDC_SOGGETTO);
		if(selectedSoggetto != null){
			setTitleFromSoggetto(new Long(selectedSoggetto));
			dataModel.setSoggettoId(new Long(selectedSoggetto));
			
			renderUfficio=true;
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
	    		
	    		String url = addParameter(VEDI_SCHEDA, SCHEDA_KEY, selectedScheda.getId()+"");
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
	    			
		    		String url = addParameter(NUOVA_SCHEDA, SCHEDA_KEY, selectedScheda.getId()+"");
		    		
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
    	}else {
    		if(canAccessUfficio(selectedScheda.getUfficioId())){
    		String url="";    		
    		try {
    			String clParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("currentLocation");
    			url = addParameter(NUOVA_SCHEDA, SCHEDA_KEY, selectedScheda.getId()+"");
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
    		}else
    			printPolicyUfficiError();	
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
					baseDto.setObj2(DataModelCostanti.SchedaSegr.PROVENIENZA_SS);	// SISO-938
					statoScheda = schedaSegrService.findStatoSchedaSegrBySchedaIdProvenienza(baseDto);
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
		if(this.selectedScheda==null || this.selectedScheda.getId() == null) {
    		printSelectError();
    		return null;
		}else {
			Long idScheda = this.selectedScheda!=null ? this.selectedScheda.getId():null;
			String idFittizio = this.selectedScheda!=null ? this.selectedScheda.getIdentificativo().toString() : null;
			Long idUfficio = this.selectedScheda!=null ?  this.selectedScheda.getUfficioId():null;
			boolean canAccessUfficio = this.canAccessUfficio(idUfficio);
			ReportSchedaBean rb = new ReportSchedaBean(idScheda, idFittizio, canAccessUfficio);
			return rb.getFile();
		}
	 }
	 
	public StreamedContent getFilePrivacy(){
		if(this.selectedScheda==null || this.selectedScheda.getId() == null) {
    		printSelectError();
    		return null;
		}else {
			boolean canAccessUfficio = this.canAccessUfficio(this.selectedScheda.getUfficioId());
			ReportPrivacyBean rb = new ReportPrivacyBean(this.selectedScheda.getId(), getSelectedScheda().getCf(), canAccessUfficio);
			return rb.getFile();
		}
	}
	
	public StreamedContent getFileSchedaVuoto(){
		DatiSchedaPdfDTO dati = new DatiSchedaPdfDTO();
		dati.setAccessoComune(this.getPreselectedPContatto().getOrganizzazione().getNome());
		PuntoContatto pc = this.getPreselectedPContatto()!=null ? this.getPreselectedPContatto(): null;
		dati.setAccessoUfficio((pc!=null && pc.getUfficio()!=null) ? pc.getUfficio().getNome() : "");
		dati.setAccessoPuntoContatto(pc!=null && !StringUtils.isEmpty(pc.getNomePContatto()) ? pc.getNomePContatto() : "");
		dati.getLstRiferimenti().add(new RiferimentoPdfDTO());
		ReportSchedaBean rb = new ReportSchedaBean(dati, true);
		return rb.getFile();
	}
	
	public StreamedContent getFilePrivacyVuoto(){
		DatiPrivacyPdfDTO dati = new DatiPrivacyPdfDTO();
		dati.setAccessoComune(this.getPreselectedPContatto().getOrganizzazione().getNome());
		dati.getLstRiferimenti().add(new RiferimentoPdfDTO());
		ReportPrivacyBean rb = new ReportPrivacyBean(dati, true);
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
	public void postProcessListaExcel(Object document) {
		try {
		
		ReportBaseBean rb = new ReportBaseBean();
			rb.postProcessXLS(document);
		
		} catch (Exception e) {
			addError("pdf.error");
	 		logger.error(e);
		}
	}

	public boolean isRenderUfficio() {
		return renderUfficio;
	}


	public void setRenderUfficio(boolean renderUfficio) {
		this.renderUfficio = renderUfficio;
	}
	
}
