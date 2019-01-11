package it.webred.ss.web.bean.report;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalante;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.lista.Scheda;

import java.io.InputStream;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class ReportPrivacyBean extends ReportBaseBean{
	
	private static final String JASPER_PRIVACY_PATH = "/reports/SSPrivacy.jasper";;
	private Long selSchedaId;
	private Long ufficioId;
	
	public ReportPrivacyBean() {}
	
	public ReportPrivacyBean(Long schedaId, Long ufficioId){
		this.selSchedaId=schedaId;
		this.ufficioId = ufficioId;
	}
		
	 public StreamedContent getFile(){
	    	StreamedContent filePrivacy = null;
	    	if(selSchedaId == null){
	    		printSelectError();
	    	} else {
	    		boolean canAccessUfficio = this.canAccessUfficio(ufficioId);
	    		if(canAccessUfficio){
		    		if(initPDFPrivacy()){
			    	    try { 
							String  tempPath =  FacesContext.getCurrentInstance().getExternalContext().getRealPath(PDF_PATH);
					    	JasperExportManager.exportReportToPdfFile(jasperprint, tempPath);
							InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(PDF_PATH);
							filePrivacy = new DefaultStreamedContent(stream, "application/pdf", "privacy.pdf");
						} catch (JRException e) {
							logger.error(e);
						}
		    		}
	    		} else printPolicyUfficiError();
	    	}
	    	
	    	return filePrivacy;
	}
	    
	
	 private boolean initPDFPrivacy(){
	 	
	 	try {
				SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
	     			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
				
				AccessTableConfigurazioneSessionBeanRemote configService = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface(
						"CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
				
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(selSchedaId);
				SsScheda s = schedaService.readScheda(dto);
				
				HashMap<String, Object> parameters = new HashMap<String, Object>();
				
				this.initLoghiReport(parameters);
				
				// accesso
				SsSchedaAccesso accesso = s.getAccesso();
				parameters.put("data", accesso.getData()!=null ? ddMMyyyy.format(accesso.getData()) : "");
				parameters.put("operatore", this.getCognomeNomeUtente(accesso.getOperatore()));
				parameters.put("interlocutore", this.getInterlocutoreDesc(accesso));
				parameters.put("motivo", this.getMotivoDesc(accesso));
				
				// segnalante
				SsSchedaSegnalante segnalante = s.getSegnalante();
				if(segnalante != null){
					
					it.webred.cs.csa.ejb.dto.BaseDTO dtoCS = new it.webred.cs.csa.ejb.dto.BaseDTO();
					fillUserData(dtoCS);
					
					dtoCS.setObj(segnalante.getCodStatoCivile());
					CsTbStatoCivile csTbStatoCivile = configService.getStatoCivileByCodice(dtoCS);
					
					dtoCS.setObj(segnalante.getRelazioneId());
					CsTbTipoRapportoCon csTbTipoRelazine = configService.getTipoRapportoConByCodice(dtoCS);
					
					parameters.put("cognome_segnalante", format(segnalante.getCognome()).toUpperCase());
					parameters.put("nome_segnalante", format(segnalante.getNome()).toUpperCase());
					if(segnalante.getVia()!=null || segnalante.getComune()!=null)
						parameters.put("indirizzo_segnalante", format(segnalante.getVia())+", "+format(getDescrizioneComune(segnalante.getComune())));
					
					parameters.put("tel_segnalante", format(segnalante.getTelefono()));
					parameters.put("cel_segnalante", format(segnalante.getCel()));
					
					parameters.put("sesso_segnalante", format(segnalante.getSesso()));
					parameters.put("luogo_nascita_segnalante", format(segnalante.getLuogoDiNascita()));
					parameters.put("data_nascita_segnalante", segnalante.getDataNascita()!=null ? ddMMyyyy.format(segnalante.getDataNascita()) : "");
					parameters.put("relazione_segnalante", csTbTipoRelazine!=null ? format(csTbTipoRelazine.getDescrizione()) : "");
					parameters.put("sc_segnalante", csTbStatoCivile!=null ? format(csTbStatoCivile.getDescrizione()) : "");
					parameters.put("email_segnalante", format(segnalante.getEmail()));					
				}
				else {
					parameters.put("cognome_segnalante", "");
					parameters.put("nome_segnalante", "");
					parameters.put("tel_segnalante", "");
					parameters.put("cel_segnalante", "");
					parameters.put("sesso_segnalante", "");
					parameters.put("luogo_nascita_segnalante", "");
					parameters.put("data_nascita_segnalante", "");
					parameters.put("sc_segnalante", "");
					parameters.put("email_segnalante", "");
				}
				
				// segnalato
				dto.setObj(s.getSegnalato());
				SsSchedaSegnalato segnalato = schedaService.readSegnalatoById(dto);
				SsAnagrafica anagrafica = segnalato.getAnagrafica();
				
				
				parameters.put("cognome", format(anagrafica.getCognome()).toUpperCase());
				parameters.put("nome", format(anagrafica.getNome()).toUpperCase());
				
				String residenza = segnalato.getSenzaFissaDimora()!=null && segnalato.getSenzaFissaDimora() ? DataModelCostanti.SENZA_FISSA_DIMORA +" ": "";
				residenza += this.getDescrizioneIndirizzo(segnalato.getResidenza());
				
				parameters.put("residenza", residenza);
				
				parameters.put("sesso", anagrafica.getSesso());
				parameters.put("data_nascita", anagrafica.getData_nascita()!=null ? ddMMyyyy.format(anagrafica.getData_nascita()) : "");
				parameters.put("luogo_nascita", format(anagrafica.getLuogoDiNascita()));
				parameters.put("tel", format(segnalato.getTelefono()));
				parameters.put("cel", format(segnalato.getCel()));
				parameters.put("medico", format(segnalato.getMedico()));
				
				// riferimento
				SsSchedaRiferimento rif = s.getRiferimento();
				if(rif != null){
					it.webred.cs.csa.ejb.dto.BaseDTO dtoCS = new it.webred.cs.csa.ejb.dto.BaseDTO();
					fillUserData(dtoCS );
					
					dtoCS.setObj(rif.getCodStatoCivile());
					CsTbStatoCivile csTbStatoCivile = configService.getStatoCivileByCodice(dtoCS);
					
					dtoCS.setObj(rif.getRelazioneId());
					CsTbTipoRapportoCon csTbTipoRelazine = configService.getTipoRapportoConByCodice(dtoCS);
					
					parameters.put("cognome_riferimento", format(rif.getCognome()).toUpperCase());
					parameters.put("nome_riferimento", format(rif.getNome()).toUpperCase());
					
					parameters.put("indirizzo_riferimento", format(rif.getRecapito()));
					parameters.put("tel_riferimento", format(rif.getTelefono()));
					parameters.put("cel_riferimento", format(rif.getCel()));
					parameters.put("sesso_riferimento", format(rif.getSesso()));
					parameters.put("luogo_nascita_riferimento", format(rif.getLuogoDiNascita()));
					parameters.put("data_nascita_riferimento", rif.getDataNascita()!=null ? ddMMyyyy.format(rif.getDataNascita()) : "");
					parameters.put("sc_riferimento", csTbStatoCivile!=null ? format(csTbStatoCivile.getDescrizione()) : "");
					parameters.put("parentela_riferimento", csTbTipoRelazine!=null ? format(csTbTipoRelazine.getDescrizione()) : "");
					parameters.put("email_riferimento", format(rif.getEmail()));
				}
				else{
					parameters.put("cognome_riferimento", "");
					parameters.put("nome_riferimento", "");
					parameters.put("parentela_riferimento", "");
					parameters.put("indirizzo_riferimento", "");
					parameters.put("tel_riferimento", "");
					parameters.put("cel_riferimento", "");
					parameters.put("sesso_riferimento", "");
					parameters.put("luogo_nascita_riferimento", "");
					parameters.put("data_nascita_riferimento", "");
					parameters.put("sc_riferimento", "");
					parameters.put("email_riferimento", "");
				}
				
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
	
	public Long getUfficioId() {
		return ufficioId;
	}
	
	public void setSelSchedaId(Long selSchedaId) {
		this.selSchedaId = selSchedaId;
	}
	
	public void setUfficioId(Long ufficioId) {
		this.ufficioId = ufficioId;
	}
	

}
