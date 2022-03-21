package it.webred.ss.web.bean.report;

import it.webred.cs.data.DataModelCostanti.TipoDiario;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.abitazione.ver1.AbitazioneBean;
import it.webred.cs.json.abitazione.ver1.AbitazioneManBean;
import it.webred.cs.json.dto.KeyValueDTO;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.serviziorichiestocustom.IServizioRichiestoCustom;
import it.webred.cs.json.serviziorichiestocustom.ServizioRichiestoCustomManBaseBean;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.cs.json.stranieri.StranieriManBaseBean;
import it.webred.cs.json.stranieri.ver1.StranieriBean;
import it.webred.cs.json.stranieri.ver1.StranieriManBean;
import it.webred.jsf.bean.ComuneBean;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.report.DatiSchedaPdfDTO;
import it.webred.ss.web.bean.lista.soggetti.SearchBean;
import it.webred.ss.web.bean.report.dto.ArrivoInItalia;
import it.webred.ss.web.bean.report.dto.ArrivoInRegione;
import it.webred.ss.web.bean.report.dto.CondizioneGiuridica;
import it.webred.ss.web.bean.report.dto.ConoscenzaLinguaItaliana;
import it.webred.ss.web.bean.report.dto.JRBeanDataSourceReportScheda;
import it.webred.ss.web.bean.report.dto.LinguaAttestato;
import it.webred.ss.web.bean.report.dto.LinguaAutovalutazione;
import it.webred.ss.web.bean.report.dto.PresenzaInItalia;
import it.webred.ss.web.bean.wizard.Nota;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class ReportSchedaBean extends ReportBaseBean {

	private static final String JASPER_PATH = "/reports/SS.jasper";
	
	private Long selSchedaId;
	private String identificativoFile;
	private DatiSchedaPdfDTO dati;
	private boolean canAccessUfficio;
	
	public ReportSchedaBean(){
		super();
	}

	public ReportSchedaBean(DatiSchedaPdfDTO pdf, boolean abilita) {
		this.dati = pdf;
		this.identificativoFile = dati.getCf();
		this.canAccessUfficio=abilita;
	}
	
	public ReportSchedaBean(Long schedaId, String identificativo, boolean abilita) {
		this.selSchedaId = schedaId;
		this.canAccessUfficio = abilita;
		this.identificativoFile = identificativo;
	}

	protected DatiSchedaPdfDTO initDatiSchedaBean(Long idScheda) {
		DatiSchedaPdfDTO dati = null; 	
		try {
				
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(idScheda);
				dati = schedaService.getDatiReportScheda(dto);
		
		} catch (Exception e) {
	 		addError("pdf.error");
	 		logger.error(e);
		}
		return dati;
	}

	private boolean initPDFScheda() {
		if(this.selSchedaId!=null)
			dati = this.initDatiSchedaBean(this.selSchedaId);
		
		try {

			HashMap<String, Object> parameters = new HashMap<String, Object>();

			this.initLoghiReport(parameters);

			// accesso
			parameters.put("labelSegrSociale", this.getLabelSegrSociale());
			
			
			String descComune = this.getLabelOrganizzazione() + " di " + (dati!=null ? dati.getAccessoComune() : "");
			parameters.put("comune_accesso", descComune);
			
			parameters.put("labelAccesso", this.getLabelUDCAccesso().toUpperCase());
			parameters.put("data_colloquio", dati!=null ? dati.getAccessoData() : "");
			parameters.put("operatore", dati!=null ? dati.getAccessoOperatore() : "");
			parameters.put("sede", dati!=null ? dati.getAccessoUfficio() : "");
	
			parameters.put("puntoContatto", dati!=null ? dati.getAccessoPuntoContatto() : "");
			parameters.put("organizzazione", descComune);
			parameters.put("modalita", dati!=null ? dati.getAccessoModalita() : "");

			parameters.put("motivo",dati!=null ? dati.getAccessoMotivo() : "");
			parameters.put("indirizzoPContatto", dati!=null ? dati.getAccessoPuntoContattoIndirizzo() : "");
			parameters.put("telefonoPContatto", dati!=null ? dati.getAccessoPuntoContattoTel(): "");
			parameters.put("emailPContatto", dati!=null ? dati.getAccessoPuntoContattoEmail(): "");

			parameters.put("interlocutore", dati!=null ? dati.getAccessoInterlocutore() : "");

			// segnalante
			parameters.put("labelSegnalante", this.getLabelUDCSegnalante().toUpperCase());
			parameters.put("showInterlocutore", dati!=null ? dati.isRenderSegnalante() : true);
			
			parameters.put("cognome_segnalante", dati!=null ? dati.getSegnalanteCognome() : "");
			parameters.put("nome_segnalante", dati!=null ? dati.getSegnalanteNome() : "");
			parameters.put("indirizzo_segnalante",dati!=null ? dati.getSegnalanteResidenza() : "");
			
			String stel = dati!=null ? dati.getSegnalanteTel() : "";
			String scel = dati!=null ? dati.getSegnalanteCel() : "";
			parameters.put("tel_segnalante", (!stel.isEmpty() && !scel.isEmpty()) ? stel+ "/" + scel : (stel+" "+scel).trim() );
			
			parameters.put("sesso_segnalante", dati!=null ? dati.getSegnalanteSesso() : "");
			parameters.put("luogo_nascita_segnalante", dati!=null ? dati.getSegnalanteLuogo_nascita() : "");
			parameters.put("data_nascita_segnalante",  dati!=null ? dati.getSegnalanteData_nascita() : "");
			parameters.put("sc_segnalante", dati!=null ? dati.getSegnalanteStatoCivile() : "");
			parameters.put("email_segnalante", dati!=null ? dati.getSegnalanteEmail() : "");
			//SISO-906 -Specifica del parente quando affidatario
			parameters.put("relazione_segnalante",  dati!=null ? dati.getSegnalanteRelazione() : "");
			parameters.put("ente_segnalante", dati!=null ? dati.getSegnalanteEnte() : "");
			parameters.put("ruolo_segnalante", dati!=null ? dati.getSegnalanteRuolo() : "");
				
			
			// segnalato
			parameters.put("labelSegnalato", this.getLabelUDCSegnalato().toUpperCase());
			parameters.put("cognome", dati!=null ? dati.getCognome() : "");
			parameters.put("nome", dati!=null ? dati.getNome() : "");
			parameters.put("alias", dati!=null ? dati.getAlias() : "");
			parameters.put("residenza", dati!=null ? dati.getResidenza() : "");
			parameters.put("sesso", dati!=null ? dati.getSesso() : "");
			parameters.put("luogo_nascita", dati!=null ? dati.getLuogo_nascita() : "");
			parameters.put("data_nascita",dati!=null ? dati.getData_nascita() : "");
			parameters.put("tel", dati!=null ? dati.getTel() : "");
			parameters.put("cel", dati!=null ? dati.getCel() : "");
			parameters.put("email", dati!=null ? dati.getEmail() : "");
			parameters.put("cf", dati!=null ? dati.getCf() : "");
			parameters.put("cittadinanza", dati!=null ? dati.getCittadinanza() : "");
			parameters.put("cittadinanza2", dati!=null ? dati.getCittadinanza2() : "");

			
			parameters.put("condLavoro", dati!=null ? dati.getCondLavoro() : "");
			parameters.put("professione", dati!=null ? dati.getProfessione() : "");
			parameters.put("titoloStudio", dati!=null ? dati.getTitoloStudio() : "");
			parameters.put("settoreImpiego", dati!=null ? dati.getSettoreImpiego() : "");
			
			parameters.put("medico", dati!=null ? dati.getMedico() : "");

			//SISO-947: riferimento		
			parameters.put("renderRiferimenti", dati.isRenderRiferimenti());
			if(dati.isRenderRiferimenti()) {
				parameters.put("Riferimenti", new JRBeanCollectionDataSource(dati.getLstRiferimenti()));
				parameters.put("labelRiferimento", this.getLabelUDCRiferimento());
			}
			parameters.put("SUBREPORT_DIR", "/subreport/");
			
			// motivo
			parameters.put("labelMotivazione", this.getLabelUDCMotivazione().toUpperCase());
			parameters.put("motivi", dati!=null ? dati.getMotivi() : "");
			parameters.put("motivo_altro", dati!=null ? dati.getMotivoAltro() : "");


			// servizi
			parameters.put("labelInterventi", this.getLabelUDCInterventi().toUpperCase());
			parameters.put("interventi", dati!=null ? dati.getInterventi() : "");
			parameters.put("intervento_altro", dati!=null ? dati.getInterventoAltro() : "");

			// tipo
			parameters.put("labelChiusura", this.getLabelUDCChiusura().toUpperCase());
			parameters.put("tipi", dati!=null ? dati.getTipoScheda() : "");

			JRBeanDataSourceReportScheda jrBeanDsReport = new JRBeanDataSourceReportScheda();
			if(dati!=null && !StringUtils.isEmpty(dati.getCf()) && this.selSchedaId!=null){
				SearchBean search = new SearchBean();
				parameters.put("conosciuto", format(search.isInEnteEsterno(dati.getCf())));
				parameters.put("in_carico", format(search.isInCs(dati.getCf())));
			
				// diario
				SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
				
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(selSchedaId);
				SsScheda s = schedaService.readScheda(dto);
				SsSchedaAccesso accesso = s.getAccesso();
				IFamConviventi famMan = super.getSchedaJsonFamConviventi(s.getId());
	
				dto.setObj(dati.getCf());
				List<SsAnagrafica> anagrafiche = schedaService.readAnagraficheByCf(dto);
	
				String diario = "";
				for (SsAnagrafica ana : anagrafiche) {
					dto.setObj(ana);
					dto.setObj2(accesso.getSsRelUffPcontOrg().getSsOOrganizzazione().getId());
					List<SsDiario> diari = schedaService.readDiarioSoggettoEnte(dto);
					List<Nota> note = this.loadNoteDiarioAccessibili(diari, accesso.getOperatore(), null);
					for (Nota nota : note)
						if (nota.isCanRead()) {
							String ente = nota.getEnte() != null ? nota.getEnte().getNome() : "";
	
							diario += "DATA: " + (nota.getData() != null ? ddMMyyyyhhmmss.format(nota.getData()) : " ") + "\n";
							diario += "AUTORE: " + nota.getOperatore() + "\n";
							diario += "ENTE: " + ente + "\n";
							diario += "PUBBLICA: " + format(nota.getPubblica()) + "\n";
							diario += "NOTA: " + nota.getNota() + "\n\n";
						}
				}
	
				if (!diario.isEmpty())
					parameters.put("diario", diario);
	
				// siso-437 [sm]
				IAbitazione iab = super.getSchedaJsonAbitazione(s.getId());
				AbitazioneManBean abitazioneManBean = (AbitazioneManBean) iab;
				AbitazioneBean abitazioneBean = abitazioneManBean.getJsonCurrent();
	
				parameters.put("abitazione_tipologia", abitazioneBean.getTipoAbitazione());
				parameters.put("abitazione_num_vani", abitazioneBean.getNumVani());
				parameters.put("abitazione_titolo_godimento", abitazioneBean.getTitoloGodimento());
				parameters.put("abitazione_prop_o_gestore", abitazioneBean.getProprietarioGestore());
				parameters.put("abitazione_note", abitazioneBean.getNote());
	
				
	
				famMan.fillReport(jrBeanDsReport.getFamiliariEConviventi());
		
				// opzionalità della sezione per i cittadini stranieri
	
				IStranieri iStranieri = getSchedaJsonStranieri(s.getId());
				if (iStranieri != null) {
					parameters.put("presenzaInItalia", true);
					StranieriManBaseBean stranBaseManBean = (StranieriManBaseBean) iStranieri;
					StranieriBean beanStranieri = ((StranieriManBean) stranBaseManBean).getJsonCurrent();
	
					PresenzaInItalia presenzaInItalia = jrBeanDsReport.getPresenzaInItalia();
					ConoscenzaLinguaItaliana conoscenzaLinguaItaliana = presenzaInItalia.getConoscenzaLinguaItaliana();
					conoscenzaLinguaItaliana.setBambinoInEtaNonScolastica(beanStranieri.isEtaNonScolastica());
					conoscenzaLinguaItaliana.setAttestatoOautovalutazione(beanStranieri.isLinguaItaAttestato());
	
					if (conoscenzaLinguaItaliana.isAttestatoOautovalutazione()) {
						LinguaAttestato linguaAttestato = conoscenzaLinguaItaliana.getLinguaAttestato();
						linguaAttestato.setComune(beanStranieri.getComuneRilascio().getDenominazione());
						linguaAttestato.setLivello(beanStranieri.getLiguaItaLivello());
						linguaAttestato.setRilasciatoDa(beanStranieri.getIstitutoRilascio());
					} else {
						LinguaAutovalutazione linguaAutovalutazione = conoscenzaLinguaItaliana.getLinguaAutovalutazione();
						linguaAutovalutazione.setComprensione(Integer.toString(beanStranieri.getLinguaItaComprensione()));
						linguaAutovalutazione.setLettura(Integer.toString(beanStranieri.getLinguaItaLettura()));
						linguaAutovalutazione.setParlato(Integer.toString(beanStranieri.getLinguaItaParlato()));
						linguaAutovalutazione.setScrittura(Integer.toString(beanStranieri.getLinguaItaScrittura()));
					}
					conoscenzaLinguaItaliana.setAltreLingueConosciute(beanStranieri.getAltreLingue());
	
					CondizioneGiuridica condizioneGiuridica = presenzaInItalia.getCondizioneGiuridica();
					if (beanStranieri.getStatus().getDescrizione() != null) {
						condizioneGiuridica.setStatus(beanStranieri.getStatus().getDescrizione());
					}
					condizioneGiuridica.setPresenteDaPiuDi3mesi(beanStranieri.isPresenteDaOltre3Mesi());
					condizioneGiuridica.setTitoloDiSoggiorno("cg titolo di soggiorno");
					if (beanStranieri.getPermesso().getDescrizione() != null) {
						condizioneGiuridica.setTipoDiPermesso(beanStranieri.getPermesso().getDescrizione());
					}
					if (beanStranieri.getIdPraticaPermesso() != null) {
						condizioneGiuridica.setPermessoNumId(beanStranieri.getIdPraticaPermesso());
					}
	
					Date scadPermessoSogg = beanStranieri.getScadPermessoSogg();
					if (scadPermessoSogg != null) {
						final java.text.DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
						condizioneGiuridica.setPermessoScadenza(DF.format(scadPermessoSogg));
					}
	
					presenzaInItalia.setMinoreStranieroNonAccompagnato(beanStranieri.getMinoreNonAccompagnato());
					if (beanStranieri.getNazioneOrigine().getDescrizione() != null) {
						presenzaInItalia.setPaeseOrigineNucleoFamiliare(beanStranieri.getNazioneOrigine().getDescrizione());
					}
					presenzaInItalia.setCondizioneGiuridica(condizioneGiuridica);
	
					ArrivoInItalia arrivoInItalia = presenzaInItalia.getArrivoInItalia();
					arrivoInItalia.setPresenza(beanStranieri.getArrivoItalia());
					arrivoInItalia.setAnnoPrimoArrivo(beanStranieri.getAnnoPrimoArrivoITA());
					arrivoInItalia.setDaSolo(beanStranieri.isDaSoloPrimoArrivoITA());
					arrivoInItalia.setAnnoPrimoPermessoDiSoggiorno(beanStranieri.getAnnoPrimoPermSogg());
	
					ComuneBean beanComuneValicoDiFrontiera = beanStranieri.getComuneValicoFrontiera();
					if (beanComuneValicoDiFrontiera != null) {
						arrivoInItalia.setValicoDiFrontiera(beanComuneValicoDiFrontiera.getDenominazione());
					}
	
					KeyValueDTO ultimoPaeseProvenienza = beanStranieri.getUltimaNazioneProvenienza();
					if (ultimoPaeseProvenienza != null && ultimoPaeseProvenienza.getDescrizione() != null) {
						arrivoInItalia.setUltimoPaeseDiProvenienza(ultimoPaeseProvenienza.getDescrizione());
					}
	
					ArrivoInRegione arrivoInRegione = presenzaInItalia.getArrivoInRegione();
					arrivoInRegione.setPresenza(beanStranieri.getArrivoRegione());
					arrivoInRegione.setRichiedenteOTitolareProtezioneInternazionale(beanStranieri.isProtezioneInternazionale());
					ComuneBean comuneTitolareSprar = beanStranieri.getComuneUltimoArrivoREG();
					if (comuneTitolareSprar != null) {
						arrivoInRegione.setComuneTitolareSprar(comuneTitolareSprar.getDenominazione());
					}
					arrivoInRegione.setStrutturaResidenzialeDiAccoglienza(beanStranieri.getNomeStruttAccoglienza());
					arrivoInRegione.setIndirizzo(beanStranieri.getIndirizzoStruttAccoglienza());
				}
	
				StringBuilder sbListaTipiIntervento = new StringBuilder();
				List<CsDValutazione> res = getSchedeJsonInterventiCustom(s);
				for(CsDValutazione val : res){
					
					if(TipoDiario.INTERMEDIAZIONE_AB_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
						sbListaTipiIntervento.append("Intermediazione Abitativa").append("\n");
					}
					if(TipoDiario.ORIENTAMENTO_LAVORO_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
						sbListaTipiIntervento.append("Inserimento Orientamento al Lavoro").append("\n");
					}
					if(TipoDiario.MEDIAZIONE_CULT_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
						sbListaTipiIntervento.append("Mediazione Culturale").append("\n");
					}
					if(TipoDiario.ORIENTAMENTO_ISTRUZIONE_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
						sbListaTipiIntervento.append("Orientamento all'istruzione / formazione").append("\n");
					}
					if(TipoDiario.RICHIESTA_SERVIZIO_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
						IServizioRichiestoCustom iServizioRichiestoCustom = 
								(IServizioRichiestoCustom) ServizioRichiestoCustomManBaseBean.initByModel(val, null);
						ServizioRichiestoCustomManBaseBean srcmb = (ServizioRichiestoCustomManBaseBean)iServizioRichiestoCustom;
						jrBeanDsReport.getServiziRichiestiCustomList().add(srcmb.getJsonCurrent());
						sbListaTipiIntervento.append(iServizioRichiestoCustom.getTipoInterventoCustom()).append("\n");
					}
				}
				
				//delete
				parameters.put("servizi_custom", sbListaTipiIntervento.toString()); //NON USATO NEL REPORT: attualmente vengono mostrati solo i CUSTOM EFFETTIVI
			}
			
			// Bean dataset DATASOURCE			
			Set<JRBeanDataSourceReportScheda> dataSet = new HashSet<JRBeanDataSourceReportScheda>();
			dataSet.add(jrBeanDsReport);
			JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(dataSet);			
					
			// FILL
			String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(JASPER_PATH);
			jasperprint = JasperFillManager.fillReport(reportPath, parameters, beanDataSource);

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("pdf.error");
		}
		return false;
	}

	public StreamedContent getFile() {
		StreamedContent fileScheda = null;
		/*if (selSchedaId == null) {
			printSelectError();
		} else {*/
			if (canAccessUfficio) {
				logAction(print, selSchedaId);

				/*
				 * dataTable = new DataTable();
				 * 
				 * Map<String, String> theFilterValues = dataTable.getFilters();
				 */
				if (initPDFScheda()) {
					try {
						String tempPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(PDF_PATH);
						JasperExportManager.exportReportToPdfFile(jasperprint, tempPath);
						InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(PDF_PATH);
						String titolo = (!StringUtils.isEmpty(identificativoFile) ? identificativoFile : "VUOTO") +"_"+"scheda.pdf";
						fileScheda = new DefaultStreamedContent(stream, "application/pdf", titolo);
					} catch (JRException e) {
						logger.error(e);
					}
				}
			} else
				printPolicyUfficiError();
		//}

		return fileScheda;
	}
	
	public Long getSelSchedaId() {
		return selSchedaId;
	}

	public boolean isCanAccessUfficio() {
		return canAccessUfficio;
	}

	public void setCanAccessUfficio(boolean canAccessUfficio) {
		this.canAccessUfficio = canAccessUfficio;
	}

}
