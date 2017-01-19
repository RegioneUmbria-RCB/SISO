package it.webred.ss.web.bean.report;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
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
import it.webred.ss.web.bean.lista.Scheda;
import it.webred.ss.web.bean.lista.soggetti.SearchBean;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class ReportSchedaBean extends ReportBaseBean{
	
	private static final String JASPER_PATH = "/reports/SS.jasper";
	private Long selSchedaId;
	private Long ufficioId;
	
	public ReportSchedaBean(Long schedaId, Long ufficioId){
		this.selSchedaId=schedaId;
		this.ufficioId = ufficioId;
	}
		
	 private boolean initPDFScheda(){
	    	
    	try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
        			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
			AccessTableConfigurazioneSessionBeanRemote configService = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface(
					"CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
				
    		BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(selSchedaId);
			SsScheda s = schedaService.readScheda(dto);
			IFamConviventi famMan = super.getSchedaJsonFamConviventi(s.getId());
			
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			
			this.initLoghiReport(parameters);
			
			// accesso
			SsSchedaAccesso accesso = s.getAccesso();
			parameters.put("data_colloquio", accesso.getData()!=null ? ddMMyyyy.format(accesso.getData()) : "");
			parameters.put("operatore", getCognomeNomeUtente(accesso.getOperatore()));
			parameters.put("sede", accesso.getSsRelUffPcontOrg().getSsUfficio().getNome());
			SsPuntoContatto pc = accesso.getSsRelUffPcontOrg().getSsPuntoContatto();
			parameters.put("puntoContatto", pc.getNome());
			String orgRif = accesso.getSsRelUffPcontOrg().getSsOOrganizzazione().getNome();
			orgRif = accesso.getSsRelUffPcontOrg().getSsOOrganizzazione().getBelfiore()!=null ? "Comune di "+orgRif : orgRif;
			parameters.put("organizzazione", orgRif);
			parameters.put("modalita", accesso.getModalita());
			parameters.put("motivo", format(accesso.getMotivo())+" "+format(accesso.getMotivoDesc()));
			
			parameters.put("indirizzoPContatto", pc.getIndirLocalizzazione()!=null ? pc.getIndirLocalizzazione() : "");
			parameters.put("telefonoPContatto", pc.getTel()!=null ? "Tel. "+pc.getTel(): "");
			parameters.put("emailPContatto", pc.getMail()!=null ? "e-mail: "+pc.getMail() : "");
			
			// segnalante
			SsSchedaSegnalante segnalante = s.getSegnalante();
			if(segnalante != null){
				
				it.webred.cs.csa.ejb.dto.BaseDTO dtoStatoCivile = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(dtoStatoCivile );
				dtoStatoCivile.setObj(segnalante.getCodStatoCivile());
				CsTbStatoCivile csTbStatoCivile = configService.getStatoCivileByCodice(dtoStatoCivile);
				
				parameters.put("cognome_segnalante", format(segnalante.getCognome()).toUpperCase());
				parameters.put("nome_segnalante", format(segnalante.getNome()).toUpperCase());
				if(segnalante.getVia()!=null || segnalante.getComune()!=null)
					parameters.put("indirizzo_segnalante", format(segnalante.getVia())+", "+format(getDescrizioneComune(segnalante.getComune())));
				parameters.put("tel_segnalante", format(segnalante.getTelefono()));
				parameters.put("cel_segnalante", format(segnalante.getCel()));
				
				CsOSettoreBASIC settore = getSettore(segnalante.getInviato_da());
				parameters.put("inviante", settore!=null ? format(settore.getNome()) : "");
				
				parameters.put("sesso_segnalante", format(segnalante.getSesso()));
				parameters.put("luogo_nascita_segnalante", format(segnalante.getLuogoDiNascita()));
				parameters.put("data_nascita_segnalante", segnalante.getDataNascita()!=null ? ddMMyyyy.format(segnalante.getDataNascita()) : "");
				parameters.put("sc_segnalante", csTbStatoCivile!=null ? format(csTbStatoCivile.getDescrizione()) : "");
				parameters.put("email_segnalante", format(segnalante.getEmail()));
			}	
			else {
				parameters.put("cognome_segnalante", "");
				parameters.put("nome_segnalante", "");
				parameters.put("tel_segnalante", "");
				parameters.put("cel_segnalante", "");
				parameters.put("inviante", "");
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
			
			String residenza = this.getDescrizioneIndirizzo(segnalato.getResidenza());
			parameters.put("residenza", residenza);
			
			parameters.put("sesso", anagrafica.getSesso());
			
			parameters.put("luogo_nascita", format(anagrafica.getLuogoDiNascita()));
			parameters.put("data_nascita", anagrafica.getData_nascita()!=null ? ddMMyyyy.format(anagrafica.getData_nascita()) : "");
			parameters.put("tel", format(segnalato.getTelefono()));
			parameters.put("cel", format(segnalato.getCel()));
			parameters.put("cf", anagrafica.getCf());
			
			String cittadinanza = anagrafica.getCittadinanza();
			CsTbCittadinanzaAcq cittAcq = this.getCittadinanzaAcq(anagrafica.getCittadinanzaAcq());
			if(cittAcq!=null)
				cittadinanza += " ("+ cittAcq.getDescrizione()+")";
	
			parameters.put("cittadinanza", format(cittadinanza));
			parameters.put("cittadinanza2", format(anagrafica.getCittadinanza2()));
			
			
			CsTbTipologiaFamiliare fam = famMan!=null ? this.getFamiglia(famMan.getTipologiaFamiliareId()) : null;
			parameters.put("tipo_famiglia", fam !=null ? format(fam.getDescrizione()): "");
			
			
			SearchBean search = new SearchBean();
			parameters.put("conosciuto", format(search.isInEnteEsterno(anagrafica.getCf())));
			parameters.put("in_carico", format(search.isInCs(anagrafica.getCf())));
			
			FormazioneLavoroMan formLavoroMan = new FormazioneLavoroMan();
			formLavoroMan.setIdCondLavorativa(segnalato.getLavoro()!=null ? new BigDecimal(segnalato.getLavoro()) : null);
			formLavoroMan.setIdProfessione(segnalato.getProfessione()!=null ? new BigDecimal(segnalato.getProfessione()) : null);
			formLavoroMan.setIdTitoloStudio(segnalato.getTitoloStudioId());
			formLavoroMan.setIdSettoreImpiego(segnalato.getSettImpiegoId());
			//formLavoroMan.valorizzaDescrizioni();
			
		    parameters.put("condLavoro", format(formLavoroMan.getLavoro()));
	     	parameters.put("professione",  format(formLavoroMan.getProfessione()));
	     	parameters.put("titoloStudio", format(formLavoroMan.getTitoloStudio()));
	     	parameters.put("settoreImpiego", format(formLavoroMan.getSettImpiego()));
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
				
				
				parameters.put("cognome_rif", format(rif.getCognome()).toUpperCase());
				parameters.put("nome_rif", format(rif.getNome()).toUpperCase());
				parameters.put("problemi_rif", format(rif.getProblemi_desc()));
				parameters.put("indirizzo_rif", format(rif.getRecapito()));
				parameters.put("tel_rif", format(rif.getTelefono()));
				parameters.put("cel_rif", format(rif.getCel()));
				parameters.put("sesso_rif", format(rif.getSesso()));
				parameters.put("luogo_nascita_rif", format(rif.getLuogoDiNascita()));
				parameters.put("data_nascita_rif", rif.getDataNascita()!=null ? ddMMyyyy.format(rif.getDataNascita()) : "");
				parameters.put("sc_rif", csTbStatoCivile!=null ? format(csTbStatoCivile.getDescrizione()) : "");
				parameters.put("parentela_rif", csTbTipoRelazine!=null ? format(csTbTipoRelazine.getDescrizione()) : "");
				parameters.put("email_rif", format(rif.getEmail()));
			} else {
				parameters.put("cognome_rif", "");
				parameters.put("nome_rif", "");
				parameters.put("parentela_rif", "");
				parameters.put("problemi_rif", "");
				parameters.put("indirizzo_rif", "");
				parameters.put("tel_rif", "");
				parameters.put("cel_rif", "");
				parameters.put("sesso_rif", "");
				parameters.put("luogo_nascita_rif", "");
				parameters.put("data_nascita_rif", "");
				parameters.put("sc_rif", "");
				parameters.put("email_rif", "");
			}
			
			// motivo
			String temp = "";
			dto.setObj(s.getMotivazione());
			List<SsMotivazioniSchede> motivazioni = schedaService.readMotivazioniScheda(dto);
			for(SsMotivazioniSchede m: motivazioni)
				temp += m.getMotivazione().getMotivo() + "\n";
			parameters.put("motivi", temp);
			parameters.put("motivo_altro", format(s.getMotivazione().getAltro()));
			
			
			// servizi
			temp = "";
			if(s.getInterventi() != null){
				dto.setObj(s.getInterventi());
				List<SsInterventiSchede> interventi = schedaService.readInterventiScheda(dto);
				for(SsInterventiSchede i: interventi)
					temp += i.getIntervento().getIntervento() + "\n";
				
				parameters.put("interventi", temp);
				parameters.put("intervento_altro", format(s.getInterventi().getAltro()));
			}
			
			
			// tipo
			dto.setObj(s.getTipo());
			SsTipoScheda tipo = schedaService.readTipoSchedaById(dto);
			parameters.put("tipi", tipo.getTipo());
			
			// diario
			dto.setObj(anagrafica.getCf());
        	List<SsAnagrafica> anagrafiche = schedaService.readAnagraficheByCf(dto);
        	
        	String diario = "";
        	for(SsAnagrafica ana: anagrafiche){
        		dto.setObj(ana);
        		List<SsDiario> note = schedaService.readDiarioSociale(dto);
            	for(SsDiario nota: note)
            		if(canReadNotaDiario(nota)){
            			String ente = nota.getEnte()!=null ? nota.getEnte().getNome() : ""; 
            			
            			diario += "DATA: " + (nota.getData()!=null ? ddMMyyyyhhmmss.format(nota.getData()) : " ") + "\n";
    					diario += "AUTORE: " + this.getCognomeNomeUtente(nota.getAutore()) + "\n";
    					diario += "ENTE: " + ente + "\n";
    					diario += "PUBBLICA: " + format(nota.getPubblica()) + "\n";
    					diario += "NOTA: " + nota.getNota() + "\n\n";
            		}
        	}
			
			if(!diario.isEmpty())
				parameters.put("diario", diario);
			
			String  reportPath =  FacesContext.getCurrentInstance().getExternalContext().getRealPath(JASPER_PATH);
			jasperprint = JasperFillManager.fillReport(reportPath, parameters, new JREmptyDataSource());
			
			return true;
    	
    	} catch (Exception e) {
    		addError("pdf.error");
    		logger.error(e);
		}
    	return false;
    }
	 
    public StreamedContent getFile(){
    	StreamedContent fileScheda=null;
    	if(selSchedaId == null){
    		printSelectError();
    	} else {
    		boolean canAccessUfficio = this.canAccessUfficio(this.ufficioId);
    		if(canAccessUfficio){
	    		logAction(print, selSchedaId);
	    		
	    		/*dataTable = new DataTable();
	    		
	       		Map<String, String> theFilterValues = dataTable.getFilters();
	    		*/ 
	    		if(initPDFScheda()){
		    	    try { 
						String  tempPath =  FacesContext.getCurrentInstance().getExternalContext().getRealPath(PDF_PATH);
				    	JasperExportManager.exportReportToPdfFile(jasperprint, tempPath);
						InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(PDF_PATH);
						fileScheda = new DefaultStreamedContent(stream, "application/pdf", "scheda.pdf");
					} catch (JRException e) {
						logger.error(e);
					}
	    		}
    	    } else printPolicyUfficiError();
    	}
    	
    	return fileScheda;
    }
	    
    private boolean canReadNotaDiario(SsDiario nota){
 		if(nota.getPubblica())
 			return true;
 		if(nota.getAutore().equals(this.getUserNameOperatore()))
 			return true;
 		if(canReadDiario() && nota.getEnte().getId()==this.getPreselectedPContatto().getOrganizzazione().getId())
 			return true;
 		
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
