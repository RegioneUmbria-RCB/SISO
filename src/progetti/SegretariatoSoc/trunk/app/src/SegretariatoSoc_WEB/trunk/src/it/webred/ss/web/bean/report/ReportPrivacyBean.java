package it.webred.ss.web.bean.report;

import java.io.InputStream;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.report.DatiPrivacyPdfDTO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean
@ViewScoped
public class ReportPrivacyBean extends ReportBaseBean{
	
	private static final String JASPER_PRIVACY_PATH = "/reports/SSPrivacy.jasper";;
	private Long selSchedaId;
	private String identificativoFile;
	private DatiPrivacyPdfDTO dati;
	private boolean canAccessUfficio;
	
	
	public ReportPrivacyBean() {}
	
	public ReportPrivacyBean(Long schedaId, String idFittizio, boolean abilita){
		this.selSchedaId=schedaId;
		this.canAccessUfficio=abilita;
		this.identificativoFile = idFittizio;
	}
	
	public ReportPrivacyBean(DatiPrivacyPdfDTO pdf, boolean abilita) {
		this.dati = pdf;
		this.identificativoFile = dati.getCf();
		this.canAccessUfficio=abilita;
	}
		
	 public StreamedContent getFile(){
	    	StreamedContent filePrivacy = null;
		/*
		 * if(selSchedaId == null){ printSelectError(); } else {
		 */
	    		if(canAccessUfficio){
		    		if(initPDFPrivacy()){
			    	    try { 
							String  tempPath =  FacesContext.getCurrentInstance().getExternalContext().getRealPath(PDF_PATH);
					    	JasperExportManager.exportReportToPdfFile(jasperprint, tempPath);
							InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(PDF_PATH);
							
							String titolo = (!StringUtils.isEmpty(identificativoFile) ? identificativoFile : "VUOTO") +"_"+"privacy.pdf";
							filePrivacy = new DefaultStreamedContent(stream, "application/pdf", titolo);
						} catch (JRException e) {
							logger.error(e);
						}
		    		}
	    		} else printPolicyUfficiError();
	    	//}
	    	
	    	return filePrivacy;
	}
	 
	 public StreamedContent getFileVuoto(){
		 return this.getFile();
	 }
	
	 private boolean initPDFPrivacy(){
	 	if(this.selSchedaId!=null) 
	 		dati = this.initDatiPrivacyBean(this.selSchedaId); 
		
		if(dati!=null && dati.isAnonimo()){
			addErrorMessage("Utente Anonimo", "Impossibile raccogliere consenso al trattamento");
			return false;
		}
		 			
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		
		try {
			this.initLoghiReport(parameters);
					
			// accesso
			String comuneAccesso = dati!=null ? dati.getAccessoComune() : this.getPreselectedPContatto().getOrganizzazione().getNome();
			
			parameters.put("renderAccesso", dati.isRenderAccesso());
			if(dati.isRenderAccesso()) {
				parameters.put("data", dati!=null ? dati.getAccessoData() : "");
				parameters.put("operatore", dati!=null ? dati.getAccessoOperatore() : "");
				parameters.put("interlocutore", dati!=null ? dati.getAccessoInterlocutore() : "");
				parameters.put("motivo", dati!=null ? dati.getAccessoMotivo() : "");
			}
			parameters.put("comune_accesso", this.getLabelOrganizzazione() + " di " + comuneAccesso);
			
			// segnalante
			parameters.put("renderSegnalante", dati.isRenderSegnalante());
			if(dati.isRenderSegnalante()) {
				parameters.put("labelSegnalante", this.getLabelUDCSegnalante());
				parameters.put("cognome_segnalante", dati!=null ? dati.getSegnalanteCognome() : "");
				parameters.put("nome_segnalante", dati!=null ? dati.getSegnalanteNome() : "");
				parameters.put("indirizzo_segnalante", dati!=null ? dati.getSegnalanteResidenza() : "");
				
				parameters.put("tel_segnalante", dati!=null ? dati.getSegnalanteTel() : "");
				parameters.put("cel_segnalante", dati!=null ? dati.getSegnalanteCel(): "");
				
				parameters.put("sesso_segnalante", dati!=null ? dati.getSegnalanteSesso() : "");
				parameters.put("luogo_nascita_segnalante", dati!=null ? dati.getSegnalanteLuogo_nascita() : "");
				parameters.put("data_nascita_segnalante", dati!=null ? dati.getSegnalanteData_nascita() : "");
				//SISO-906 -Specifica del parente quando affidatario
				parameters.put("relazione_segnalante", dati!=null ? dati.getSegnalanteRelazione() : "");
				parameters.put("sc_segnalante", dati!=null ? dati.getSegnalanteStatoCivile() : "");
				parameters.put("email_segnalante", dati!=null ? dati.getSegnalanteEmail() : "");	
			}
			
			// segnalato
			parameters.put("labelSegnalato", this.getLabelUDCSegnalato());
			
			parameters.put("cognome", dati!=null ? dati.getCognome() : "");
			parameters.put("nome", dati!=null ? dati.getNome(): "");
			
			parameters.put("sesso", dati!=null ? dati.getSesso() : "");
			parameters.put("data_nascita", dati!=null ? dati.getData_nascita() : "");
			parameters.put("luogo_nascita", dati!=null ? dati.getLuogo_nascita() : "");
			parameters.put("residenza", dati!=null ? dati.getResidenza() : "");
	
			parameters.put("tel", dati!=null ? dati.getTel() : "");
			parameters.put("cel", dati!=null ? dati.getCel() : "");
			parameters.put("medico", dati!=null ? dati.getMedico() : "");
		
			// riferimento
			//SISO-947: riferimento
			parameters.put("renderRiferimenti", dati.isRenderRiferimenti());
			if(dati.isRenderRiferimenti()) {
				parameters.put("Riferimenti", new JRBeanCollectionDataSource(dati.getLstRiferimenti()));
				parameters.put("labelRiferimento", this.getLabelUDCRiferimento());
			}
			parameters.put("SUBREPORT_DIR", "/subreport/");
			
			String  reportPath =  FacesContext.getCurrentInstance().getExternalContext().getRealPath(JASPER_PRIVACY_PATH);
			jasperprint = JasperFillManager.fillReport(reportPath, parameters, new JREmptyDataSource());
			
			return true;
	 	
	 	} catch (Exception e) {
	 		addError("pdf.error");
	 		logger.error(e);
			}
	 	return false;
	 }
	 
	public Long getSelSchedaId() {
		return selSchedaId;
	}
	
	public void setSelSchedaId(Long selSchedaId) {
		this.selSchedaId = selSchedaId;
	}

	protected DatiPrivacyPdfDTO initDatiPrivacyBean(Long idScheda) {
		DatiPrivacyPdfDTO dati = null; 	
		try {
				
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(idScheda);
				dati = schedaService.getDatiReportPrivacy(dto);
		
		} catch (Exception e) {
	 		addError("pdf.error");
	 		logger.error(e);
		}
		return dati;
	}
		
}
