package it.webred.cs.csa.web.manbean.report;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.ArrayUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.utils.JasperUtils;
import it.webred.cs.csa.utils.bean.report.dto.BasePdfDTO;
import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;
import it.webred.cs.csa.utils.bean.report.dto.TriagePdfDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.DatiProgettoBean;
import it.webred.cs.csa.web.manbean.fascicolo.relazioni.TriageBean;
import it.webred.cs.csa.web.manbean.report.dto.cartella.DiarioPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.fascicolo.ModelloPorPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.RelazionePdfDTO;
import it.webred.cs.csa.web.manbean.report.filler.RelazioniStampaFiller;
import it.webred.cs.csa.web.manbean.report.filler.ReportCartellaFiller;
import it.webred.cs.csa.web.manbean.report.filler.ReportFascicoloFiller;
import it.webred.cs.csa.web.manbean.report.filler.ReportFoglioAmmFiller;
import it.webred.cs.csa.web.manbean.scheda.SchedaBean;
import it.webred.cs.csa.web.manbean.scheda.anagrafica.AnagraficaBean;
import it.webred.cs.csa.web.manbean.scheda.invalidita.DatiInvaliditaBean;
import it.webred.cs.csa.web.manbean.scheda.parenti.ParentiBean;
import it.webred.cs.csa.web.manbean.scheda.sociali.DatiSocialiBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.jsf.bean.colloquio.DatiColloquioBean;
import it.webred.cs.json.barthel.ISchedaBarthel;
import it.webred.cs.json.isee.IIseeJson;
import it.webred.cs.json.valMultidimensionale.IValMultidimensionale;
import it.webred.cs.json.valSinba.IValSinba;
import it.webred.ss.ejb.dto.report.DatiPrivacyPdfDTO;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean
@SessionScoped
public class ReportBean extends ReportBaseBean {
	
	//tipi report
	private static final String REPORT_CARTELLA = "ReportCartella";
	private String tipoReport;
	
	//tipi subreport cartella
	private static final String ANAGRAFICA = "Anagrafica";
	private static final String PARENTI = "Parenti";
	private static final String DATISOCIALI = "Dati sociali";
	private static final String INVALIDITA = "Invalidita";
	private static final String DISABILITA = "Disabilita";
	private static final String TRIBUNALE = "Tribunale";
	private static final String OPERATORI = "Operatori";
	private static final String ATTIVITAPROF = "Attività Professionali";
	private static final String PAI = "PAI";
	private static final String DIARIO = "Diario";
	private static final String NOTE = "Note";
	
	private static final String VOUCHER = "voucher";
	private static final String CONTRIBUTOECONOMICO = "contibeconomico";
	private static final String BUONOSOC = "buonosoc";
	private static final String PASTIDOM = "pastidom";
	private static final String CENTRODIURNO = "centrodiurno";
	
	private static final String ESPORTA_STORICO = "Includi dati storici";
	
	
	private String modalWidgetVar = "wdgOpzioniStampaModal";
	private String modalId = "idOpzioniStampa";
	

	@ManagedProperty(value="#{schedaBean}")
	private SchedaBean schedaBean;
	
	@ManagedProperty(value="#{fascicoloBean}")
	private FascicoloBean fascicoloBean;
	
/*	@ManagedProperty(value="#{paiAffidoBean}")
	private PaiAffidoBean paiAffidoBean;
*/	
	private List<String> lstSubreportObbl;
	private List<String> lstSubreport;
	private List<String> lstReportOpz;
	private String[] selectedSubreport;
	private String[] selectedReportOpz;
	
	
	private List<RelazioneDTO> listaRelDTO;
	private List<RelazioneDTO> listaRelSel;
	private List<CsDPai> listaPAI = new ArrayList<CsDPai>();
	private List<CsDPai> listaPAISel = new ArrayList<CsDPai>();
	private boolean vediAttivita = false, vediPAI = false;
	private JasperPrint jasperprint;
	
	public void initializeStampaCartella(CsASoggettoLAZY cssoggetto) {

		if(cssoggetto != null) {
			vediAttivita = false;
			vediPAI= false;
			tipoReport = REPORT_CARTELLA;
			this.setSoggetto(cssoggetto);
			
			
//			schedaBean.initialize(soggetto);
//			schedaBean.disabilitaModifiche();
			/**gli inizialize sono stati spostati nel controllo della stampa */
			
			List<String> filtroSchede = new ArrayList<String>();
			filtroSchede.add(DataModelCostanti.TabFascicolo.COLLOQUIO);
			filtroSchede.add(DataModelCostanti.TabFascicolo.RELAZIONI);
			filtroSchede.add(DataModelCostanti.TabFascicolo.PAI);
			fascicoloBean.initializeFascicoloCartellaUtente(soggetto, true, filtroSchede);
			
			listaRelDTO = new ArrayList<RelazioneDTO>();
			listaRelSel = new ArrayList<RelazioneDTO>();
			lstSubreportObbl = new ArrayList<String>();
			lstSubreportObbl.add(ANAGRAFICA);
			
			lstSubreport = new ArrayList<String>();
//			if(schedaBean.isRenderTabParenti())
				lstSubreport.add(PARENTI);
//			if(schedaBean.isRenderTabDatiSociali())
				lstSubreport.add(DATISOCIALI);
//			if(schedaBean.isRenderTabInvalidita())
				lstSubreport.add(INVALIDITA);
//			if(schedaBean.isRenderTabDisabilita())
				lstSubreport.add(DISABILITA);
//			if(schedaBean.isRenderTabTribunale())
				lstSubreport.add(TRIBUNALE);
//			if(schedaBean.isRenderTabOperatori())
				lstSubreport.add(OPERATORI);
//				if(schedaBean.isRenderTabNote())
				lstSubreport.add(NOTE);			
				
			//DATI FASCICOLO - Controllare permessi prima della selezione: 
			//non si possono visualizzare i dati di riepilogo se l'operatore non è autorizzato ad entrare nel fascicolo
			if(fascicoloBean.isColloquio())
				lstSubreport.add(DIARIO);
			if(fascicoloBean.isRelaz())
				lstSubreport.add(ATTIVITAPROF);
			if(fascicoloBean.isPai())
				lstSubreport.add(PAI);
				
			selectedSubreport = lstSubreport.toArray(new String[lstSubreport.size()]);
			lstReportOpz = new ArrayList<String>();
			lstReportOpz.add(ESPORTA_STORICO);		
			
			RequestContext.getCurrentInstance().execute("wiz.loadStep (wiz.cfg.steps [0], true)");//riapre la wizard della dialog al primo step
			RequestContext.getCurrentInstance().execute(modalWidgetVar + ".show()");	
			RequestContext.getCurrentInstance().update(modalId);
		    
			
		} else addWarningFromProperties("seleziona.warning");
	}
	
	public String flowStampa(FlowEvent event){
		
		try{
			if(event.getNewStep().equals("step2")){
				
				List<String> selSubReport = Arrays.asList(selectedSubreport);
				vediAttivita = selSubReport.contains(ATTIVITAPROF);
				vediPAI = selSubReport.contains(PAI);
				
				if(vediAttivita){
					listaRelDTO.clear(); 
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(soggetto.getCsACaso().getId());
					listaRelDTO.addAll(diarioService.findRelazioniByCaso(dto));
				}
				if(vediPAI){
					listaPAI.clear(); 
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(soggetto.getCsACaso().getId());
					listaPAI.addAll(diarioService.findPaiByCaso(dto));
				}
				
			}
			
		} catch (Exception e) {
			addErrorFromProperties("report.error");
			logger.error(e.getMessage(), e);
		}
		return event.getNewStep();
	}
	
	public void controllaStampa(){
		
		schedaBean.initialize(soggetto);
		schedaBean.disabilitaModifiche();
		
		for(String str : selectedSubreport){
			
			if(str.equals(PARENTI) && !schedaBean.isRenderTabParenti())
				ArrayUtils.removeElement(selectedSubreport, str);
			if(str.equals(DATISOCIALI) && !schedaBean.isRenderTabDatiSociali())
				ArrayUtils.removeElement(selectedSubreport, str);
			if(str.equals(INVALIDITA) && !schedaBean.isRenderTabInvalidita())
				ArrayUtils.removeElement(selectedSubreport, str);
			if(str.equals(DISABILITA) && !schedaBean.isRenderTabDisabilita())
				ArrayUtils.removeElement(selectedSubreport, str);
			if(str.equals(TRIBUNALE) && !schedaBean.isRenderTabTribunale())
				ArrayUtils.removeElement(selectedSubreport, str);
			if(str.equals(OPERATORI) && !schedaBean.isRenderTabOperatori())
				ArrayUtils.removeElement(selectedSubreport, str);
			if(str.equals(NOTE) && !schedaBean.isRenderTabNote())
				ArrayUtils.removeElement(selectedSubreport, str);
			/*if(str.equals(DIARIO))
				filtroSchede.add(DataModelCostanti.TabFascicolo.COLLOQUIO);
			if(str.equals(ATTIVITAPROF))
				filtroSchede.add(DataModelCostanti.TabFascicolo.RELAZIONI);
			if(str.equals(PAI)){
				filtroSchede.add(DataModelCostanti.TabFascicolo.PAI);
				paiAffidoBean = new PaiAffidoBean();
			}*/
			
			//Se l'operatore non è autorizzato a vederle, non dovrebbe neanche caricare le liste!
			/*if(!filtroSchede.isEmpty()){
				
				if(!fascicoloBean.isColloquio())
					ArrayUtils.removeElement(selectedSubreport, DIARIO);
				if(!fascicoloBean.isRelaz()){
					ArrayUtils.removeElement(selectedSubreport, ATTIVITAPROF);
					this.listaRelSel.clear();
				}
				if(!fascicoloBean.isPai()){
					ArrayUtils.removeElement(selectedSubreport, PAI);
					this.listaPAISel.clear();
				}	
			}*/
			
		}
       
        
		if(REPORT_CARTELLA.equals(tipoReport))
				esportaCartella();		
		
		
	}
	
	public void creaStampa() {
		
		//if(REPORT_CARTELLA.equals(tipoReport))
		//	esportaCartella();
	}
	
	private boolean esportaStorico(){
		boolean esportaStorico = false;
		if(this.selectedReportOpz.length>0){
			for(String s : selectedReportOpz){
				if(s.equals(ESPORTA_STORICO)){
					esportaStorico = true;
					break;
				}
			}
		}
		return esportaStorico;
	}
	


	private void esportaCartella() {

		try {
			ReportCartellaFiller filler = new ReportCartellaFiller();
			filler.setSchedaBean(schedaBean);
			filler.setFascicoloBean(fascicoloBean);
			//filler.setPaiAffidoBean(paiAffidoBean);
			filler.setSoggetto(soggetto);
			filler.setEsportaStorico(esportaStorico());

			Map<String, String> mapSubreport = new HashMap<String, String>();
			for (int i = 0; i < selectedSubreport.length; i++)
				mapSubreport.put(selectedSubreport[i], selectedSubreport[i]);

			// path report e subreport
			String reportPath = getSession().getServletContext().getRealPath("/report");
			String subReportPath = reportPath + "/subreport/cartella";
			List<String> listaSubreport = new ArrayList<String>();
			listaSubreport.add(subReportPath + "/anagrafica.jrxml");

			// parametri subreport
			HashMap<String, Object> map = new HashMap<String, Object>();
			logger.info(String.format("Crea  Mappa di parametri per i Jasper Reports"));
			map.put("anagrafica", new JRBeanCollectionDataSource(Arrays.asList(filler.fillAnagrafica())));

			if (mapSubreport.containsKey(PARENTI)) {
				listaSubreport.add(reportPath + "/subreport/cartella/risorse.jrxml");
				listaSubreport.add(reportPath + "/subreport/cartella/parenti.jrxml");

				JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(filler.fillRisorse(subReportPath + "/"));
				map.put("risorse", ds);
			}

			if (mapSubreport.containsKey(DATISOCIALI)) {
				listaSubreport.add(reportPath + "/subreport/cartella/datisociali.jrxml");
				listaSubreport.add(reportPath + "/subreport/cartella/stranieri.jrxml");
				
				map.put("datisociali", new JRBeanCollectionDataSource(filler.fillDatiSociali(subReportPath + "/")));
			}

			if (mapSubreport.containsKey(INVALIDITA)) {
				listaSubreport.add(reportPath + "/subreport/cartella/invalidita.jrxml");
				map.put("invalidita", new JRBeanCollectionDataSource(filler.fillInvalidita()));
			}

			if (mapSubreport.containsKey(DISABILITA)) {
				listaSubreport.add(reportPath + "/subreport/cartella/disabilita.jrxml");
				map.put("disabilita", new JRBeanCollectionDataSource(filler.fillDisabilita()));
			}

			if (mapSubreport.containsKey(TRIBUNALE)) {
				listaSubreport.add(reportPath + "/subreport/cartella/tribunale.jrxml");
				map.put("tribunale", new JRBeanCollectionDataSource(filler.fillTribunale()));
			}

			if (mapSubreport.containsKey(OPERATORI)) {
				listaSubreport.add(reportPath + "/subreport/cartella/operatori.jrxml");
				map.put("operatori", new JRBeanCollectionDataSource(filler.fillOperatori()));
			}

			if (mapSubreport.containsKey(DIARIO)) {
				listaSubreport.add(reportPath + "/subreport/cartella/Diario.jrxml");
				map.put("Diario", new JRBeanCollectionDataSource(filler.fillDiario()));
			}
			if (mapSubreport.containsKey(NOTE)) {
				listaSubreport.add(reportPath + "/subreport/cartella/note.jrxml");
				map.put("note", new JRBeanCollectionDataSource(Arrays.asList(filler.fillNote())));
			}
			if(mapSubreport.containsKey(ATTIVITAPROF)){
				listaSubreport.add(reportPath + "/subreport/cartella/attivitaPro.jrxml");
				listaSubreport.add(reportPath + "/subreport/cartella/tipoAttivita_1.jrxml");
				listaSubreport.add(reportPath + "/subreport/cartella/tipoAttivita_2.jrxml");
				listaSubreport.add(reportPath + "/subreport/cartella/tipoAttivita_3.jrxml");
				listaSubreport.add(reportPath + "/subreport/cartella/problematiche.jrxml");//SISO-1223
				listaSubreport.add(reportPath + "/subreport/cartella/tipiInterventoProposti.jrxml");//SISO-1223
				
				map.put("attivita",new JRBeanCollectionDataSource(filler.fillAttivita(listaRelSel,subReportPath + "/")));
				
			}
			if(mapSubreport.containsKey(PAI)){
				listaSubreport.add(reportPath + "/subreport/cartella/PAI.jrxml");
				listaSubreport.add(reportPath + "/subreport/cartella/affido.jrxml");
				listaSubreport.add(reportPath + "/subreport/cartella/erogazioniPai.jrxml");
				listaSubreport.add(reportPath + "/subreport/cartella/tabelle.jrxml");
				listaSubreport.add(reportPath + "/subreport/cartella/attivitaProPAI.jrxml");
				
				if(this.listaRelDTO.isEmpty()){
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(soggetto.getCsACaso().getId());
					listaRelDTO.addAll(diarioService.findRelazioniByCaso(dto));
				}
				map.put("PAI", new JRBeanCollectionDataSource(filler.fillPAI(listaPAISel,listaRelDTO,subReportPath + "/"),false));
			}
						
			fillReportHeader(reportPath, listaSubreport, map);

			// parametri report
			ReportPdfDTO repPdf = new ReportPdfDTO();
			fillReportPieDiPagina(repPdf);

			JasperUtils jUtils = new JasperUtils();
			jUtils.esportaReport("Cartella_" + this.getDenominazione(), reportPath + "/cartella.jrxml", listaSubreport,
					map, new JRBeanCollectionDataSource(Arrays.asList(repPdf)));

		} catch (Exception e) {
			addErrorFromProperties("report.error");
			logger.error(e.getMessage(), e);
		}

	}


	
	private void addAnagraficaSintetica(String reportPath, HashMap<String, Object> map, List<String> listaSubreport){
		ReportFascicoloFiller fillerFasc = new ReportFascicoloFiller();
		fillerFasc.setSoggetto(soggetto);
		listaSubreport.add(reportPath + "/subreport/fascicolo/anagrafica.jrxml");
		map.put("anagrafica", new JRBeanCollectionDataSource(Arrays.asList(fillerFasc.fillDatiAnagrafica())));
	}
	
	public void esportaDiario(DatiColloquioBean q){
		try {
			
			CsASoggettoLAZY s = (CsASoggettoLAZY) getSession().getAttribute("soggetto");
			setSoggetto(s);
			
			//path report e subreport
			String reportPath =	getSession().getServletContext().getRealPath("/report");
			List<String> listaSubreport = new ArrayList<String>();
			
			//parametri subreport
			HashMap<String, Object> map = new HashMap<String, Object>();
			logger.info(String.format("Crea  Mappa di parametri per i Jasper Reports"));
			
			addAnagraficaSintetica(reportPath,map,listaSubreport);
			
			ReportCartellaFiller filler = new ReportCartellaFiller();
			listaSubreport.add(reportPath + "/subreport/cartella/Diario.jrxml");
			List<DiarioPdfDTO> lista = new ArrayList<DiarioPdfDTO>();
			filler.fillDiario(q,lista);
			map.put("Diario", new JRBeanCollectionDataSource(lista));
			
			fillReportHeader(reportPath, listaSubreport, map);
			
			//parametri report
			ReportPdfDTO repPdf = new ReportPdfDTO();
			fillReportPieDiPagina(repPdf);
			
			JasperUtils jUtils = new JasperUtils();
			jUtils.esportaReport("diario_" + this.getDenominazione(), reportPath + "/diario.jrxml", listaSubreport, map, new JRBeanCollectionDataSource(Arrays.asList(repPdf)));
			
		} catch (Exception e) {
			addErrorFromProperties("report.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void esportaValSinba(IValSinba json){
		
		//TODO
		
	}
	
	public void esportaValMultidimensionale(IValMultidimensionale json, ISchedaBarthel manSchedaBarthelBean){
		
		CsASoggettoLAZY s = (CsASoggettoLAZY) getSession().getAttribute("soggetto");
		setSoggetto(s);
		
		try {
			
			//path report e subreport
			String reportPath =	getSession().getServletContext().getRealPath("/report");
			List<String> listaSubreport = new ArrayList<String>();
			
			//parametri subreport
			HashMap<String, Object> map = new HashMap<String, Object>();
			logger.info(String.format("Crea  Mappa di parametri per i Jasper Reports"));
			
			addAnagraficaSintetica(reportPath,map,listaSubreport);
			
			JasperUtils jUtils = new JasperUtils();
			
			FascicoloBean rfasb = (FascicoloBean) getReferencedBean("fascicoloBean");
			if(rfasb.getIseeBean()==null) rfasb.initializeIseeTab(s);
			List<IIseeJson> lstIsee = rfasb.getIseeBean().getListaIsee();
			
			ReportPdfDTO pdfDTO = json.fillReport(reportPath, listaSubreport, map, lstIsee, manSchedaBarthelBean);
			fillReportCommonData(reportPath, listaSubreport, map, pdfDTO);
			
			jUtils.esportaReport("MultiDim_"+getDenominazione(), reportPath + "/schMultidimensionale"+json.getVersionLowerCase()+".jrxml", listaSubreport, map, new JRBeanCollectionDataSource(Arrays.asList(pdfDTO)));
			
		} catch (Exception e) {
			addErrorFromProperties("report.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	
	public void esportaRelazione(CsDRelazione diarioR, List<CsCTipoIntervento> listaTipiInterventoAttivi,List<Long> lstIdCatSoc) {
		try {
			
			this.setSoggetto((CsASoggettoLAZY) getSession().getAttribute("soggetto"));
			
			//denominazione = soggetto.getCsAAnagrafica().getCognome() + "_" + soggetto.getCsAAnagrafica().getNome();
			RelazioniStampaFiller filler = new RelazioniStampaFiller();
			filler.setDiarioRelaz(diarioR);
			filler.setSoggetto(soggetto);
			filler.setLstIdCatSoc(lstIdCatSoc);
			
			
			String reportPath =	getSession().getServletContext().getRealPath("/report");
			List<String> listaSubreport = new ArrayList<String>();
			Map<String, String> mapSubreport = new HashMap<String, String>();
			
			lstSubreport = new ArrayList<String>();
			//controllo qualiinterventi sono selezionati
			for(CsCTipoIntervento li: listaTipiInterventoAttivi){
			if(li.getId()==1){
				lstSubreport.add(VOUCHER);
				filler.setInterventoVoucher(li);
			}if(li.getId()==2){
				lstSubreport.add(CONTRIBUTOECONOMICO);
				filler.setInterventocontrib(li);
			}if(li.getId()==5){
				lstSubreport.add(BUONOSOC);
				filler.setInterventobuoni(li);
			}if(li.getId()==4){
				lstSubreport.add(PASTIDOM);
				filler.setInterventoPasti(li);
			}if(li.getId()==3){
				lstSubreport.add(CENTRODIURNO);
				filler.setInterventoCentriDiurni(li);
				}
			}
			selectedSubreport = lstSubreport.toArray(new String[lstSubreport.size()]);
			//integrazione box 
			
			for(int i = 0; i < selectedSubreport.length; i++) 
				mapSubreport.put(selectedSubreport[i], selectedSubreport[i]);
			
			
			
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			logger.info(String.format("Crea  Mappa di parametri per i Jasper Reports"));
			//map.put("relazioni", new JRBeanCollectionDataSource(Arrays.asList(filler.fillRelazioni())));
			
			if(mapSubreport.containsKey(VOUCHER)){
				listaSubreport.add(reportPath + "/subreport/cartellaRelaz/voucher.jrxml");
				map.put("voucher", new JRBeanCollectionDataSource(Arrays.asList(filler.fillVoucher())));
			}
			
			if(mapSubreport.containsKey(CONTRIBUTOECONOMICO)) {
				listaSubreport.add(reportPath + "/subreport/cartellaRelaz/contributo.jrxml");
				map.put("contribeconomico", new JRBeanCollectionDataSource(Arrays.asList(filler.fillContribEconomico())));
			}
			
			if(mapSubreport.containsKey(BUONOSOC)) {
				listaSubreport.add(reportPath + "/subreport/cartellaRelaz/buonosoc.jrxml");
				map.put("buonosoc", new JRBeanCollectionDataSource(Arrays.asList(filler.fillBuoniSoc())));
			}
			
			if(mapSubreport.containsKey(PASTIDOM)) {
				listaSubreport.add(reportPath + "/subreport/cartellaRelaz/pastidom.jrxml");
				map.put("pastidom", new JRBeanCollectionDataSource(Arrays.asList(filler.fillPastiDom())));
			}
			
			if(mapSubreport.containsKey(CENTRODIURNO)) {
				listaSubreport.add(reportPath + "/subreport/cartellaRelaz/centrodiurno.jrxml");
				map.put("centrodiurno", new JRBeanCollectionDataSource(Arrays.asList(filler.fillCentroDiurno())));
			}
			
			
			fillReportHeader(reportPath, listaSubreport, map);
			
			//parametri report
			RelazionePdfDTO relPdfDTO = new RelazionePdfDTO(); 
			relPdfDTO  = filler.fillRelazioni();
			JasperUtils jUtils = new JasperUtils();
			jUtils.esportaReport("Relazione_ "+ this.getDenominazione(), reportPath +"/relazione.jrxml",listaSubreport,map, new JRBeanCollectionDataSource(Arrays.asList(relPdfDTO)));
			
		} catch (Exception e) {
			addErrorFromProperties("report.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	
	
	
	public void esportaFoglioAmm(Long idDiario, Long idIntervento) {
	
		try {
		
			this.setSoggetto((CsASoggettoLAZY) getSession().getAttribute("soggetto"));
			//denominazione = soggetto.getCsAAnagrafica().getCognome() + "_" + soggetto.getCsAAnagrafica().getNome();
			ReportFoglioAmmFiller filler = new ReportFoglioAmmFiller();
			filler.setDiarioId(idDiario);
			filler.setSoggetto(soggetto);

			//path report e subreport
			String reportPath =	getSession().getServletContext().getRealPath("/report");
			List<String> listaSubreport = new ArrayList<String>();
			
			//parametri subreport
			HashMap<String, Object> map = new HashMap<String, Object>();
			logger.info(String.format("Crea  Mappa di parametri per i Jasper Reports"));
			
			BasePdfDTO tipoIntPdfDTO = filler.fillTipoIntervento();
			boolean stampa = false;
			if(filler.getIdTipoInterventoCustom()>0 && filler.getIdTipoInterventoCustom()<12){
				stampa = true;  
				listaSubreport.add(reportPath + "/subreport/foglioamm/"+"tipoIntervento_" + filler.getIdTipoInterventoCustom() + ".jrxml");
				map.put("tipoIntervento", new JRBeanCollectionDataSource(Arrays.asList(tipoIntPdfDTO)));
		    }
			
			addAnagraficaSintetica(reportPath,map,listaSubreport);
			
			fillReportHeader(reportPath, listaSubreport, map);
			
			JasperUtils jUtils = new JasperUtils();
			BasePdfDTO intPdfDTO = filler.fillIntervento(stampa);
			intPdfDTO.setShowCasellaContributo(tipoIntPdfDTO.getShowCasellaContributo());
			String tipoIntervento = filler.getIn().getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento().getDescrizione();
			jUtils.esportaReport(tipoIntervento + "_" + this.getDenominazione(), reportPath + "/foglioAmm.jrxml", listaSubreport, map, new JRBeanCollectionDataSource(Arrays.asList(intPdfDTO)));
			
		} catch (Exception e) {
			addErrorFromProperties("report.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	/***
	 * Avvia l'esportazione della scheda 
	 * @param diarioR
	 * @param triage
	 */
	public void esportaTriage(RelazioneDTO relazioneDTO, IValMultidimensionale msmab, TriageBean tbean){
		try{
			//Il soggetto del fascicolo 
			this.setSoggetto((CsASoggettoLAZY) getSession().getAttribute("soggetto"));
			
			ReportCartellaFiller rsf = new ReportCartellaFiller();
			
			schedaBean = new SchedaBean();
			AnagraficaBean ana = new AnagraficaBean();
			ana.initialize(getSoggetto().getAnagraficaId());
			schedaBean.setAnagraficaBean(ana);
			
			ParentiBean pb = new ParentiBean();
			pb.initialize(getSoggetto().getAnagraficaId());
			schedaBean.setParentiBean(pb);
			
			DatiSocialiBean dsb = new DatiSocialiBean();
			dsb.initialize(getSoggetto().getAnagraficaId());
			schedaBean.setDatiSocialiBean(dsb);
			
			DatiInvaliditaBean dib = new DatiInvaliditaBean();
			dib.initialize(getSoggetto().getAnagraficaId());
			schedaBean.setDatiInvaliditaBean(dib);
			
			rsf.setSoggetto(getSoggetto());
			rsf.setSchedaBean(schedaBean);
			
			TriagePdfDTO pdf = rsf.fillTriage(relazioneDTO, tbean);
			pdf.setDataStampa(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
			//USER
			if(getCurrentOpSettore().getCsOSettore().getEmail() != null)
			   pdf.setUser(getCurrentOpSettore().getCsOSettore().getEmail());
			
			//path report e subreport
			String reportPath =	getSession().getServletContext().getRealPath("/report");
			String subReportPath = reportPath + "/subreport/triage";
			List<String> listaSubreport = new ArrayList<String>();
			
			//parametri subreport
			HashMap<String, Object> map = new HashMap<String, Object>();
			logger.info(String.format("Crea Mappa di parametri per i Jasper Reports"));
			
			//parenti			
			listaSubreport.add(subReportPath + "/risorse.jrxml");
			listaSubreport.add(subReportPath + "/parenti.jrxml");
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(rsf.fillRisorse(subReportPath + "/"));
			map.put("risorse", ds);
			
			//dati sociali
			listaSubreport.add(subReportPath + "/datisociali.jrxml");
			listaSubreport.add(subReportPath + "/stranieri.jrxml");
			map.put("datisociali", new JRBeanCollectionDataSource(rsf.fillDatiSociali(subReportPath + "/")));
						
			//subreport scheda multidim sintesi dati sociali
			
			if(msmab != null){
				msmab.fillTriage(subReportPath, pdf, listaSubreport, map);
				pdf.setExistsMultidim(true);
			}
			
			//subreport scheda multidim abitazione
			
			JasperUtils jUtils = new JasperUtils();
			jUtils.esportaReport(this.getDenominazione(), reportPath + "/triage.jrxml", listaSubreport, map, new JRBeanCollectionDataSource(Arrays.asList(pdf)));
		}catch(Exception e){
			addErrorFromProperties("report.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void esportaModelloPor(SoggettoErogazioneBean beneficRif ,DatiProgettoBean datiprogetto,String soggettoAttuat){
		try{
		String reportPath =	getSession().getServletContext().getRealPath("/report");
		String logo =	getSession().getServletContext().getRealPath("/images");
		String nomeModulo = super.getModuloPorRegionale();
		ReportCartellaFiller filler = new ReportCartellaFiller();
	    boolean marche = super.isModuloPorMarche();
		
		ModelloPorPdfDTO pdf = filler.fillModello(beneficRif,datiprogetto,soggettoAttuat,marche);
		pdf.setImagePath(logo+"/"); //+"logo_FSE_2014_2020_completo.jpg"); nome immagine presente nel modulo
		JasperUtils jUtils = new JasperUtils();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		jUtils.esportaReport("Modulo POR", reportPath +"/"+ nomeModulo + ".jrxml", null, map, new JRBeanCollectionDataSource(Arrays.asList(pdf)));
		
		}catch(Exception e){
			addErrorFromProperties("report.error");
			logger.error(e.getMessage(),e);
		}		
	
		
	}
	
	public StreamedContent getStampaPrivacy(DatiPrivacyPdfDTO dati, String lblSegnalante, String lblUtente, String lblRiferimento){
		
		if(dati!=null && dati.isAnonimo()){
			this.addError("Utente Anonimo", "Impossibile raccogliere consenso al trattamento");
			return null;
		}
	
		 StreamedContent filePrivacy = null;
		 HashMap<String, Object> parameters = new HashMap<String, Object>();
		 final String PDF_PATH = "/report/temp.pdf";
		 final String JASPER_PRIVACY_PATH = "/report/SSPrivacy.jasper";
		 String DIRIMAGES = "/images/";
		 String dirAppImages  =  FacesContext.getCurrentInstance().getExternalContext().getRealPath(DIRIMAGES)+File.separatorChar; 
		 String dirLoghi = this.getPathLoghi();
		 File logo = new File(dirLoghi + "logo_bw.png");
		 
		 if(logo.exists())
				parameters.put("pathLogoComune", dirLoghi + "logo_bw.png");
			else {
				logger.error("Attenzione: Il logo 'logo_bw.png' non è presente per il comune ");
				parameters.put("pathLogoComune", dirAppImages + "logo_bw.png");
			}
		 parameters.put("pathTitolo", dirAppImages + "titolo_" + this.getTipoApplicazione() + ".png");
		 
		 try {
				// accesso
				String comuneAccesso = dati!=null ? dati.getAccessoComune() : getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getNome();
				
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
					parameters.put("labelSegnalante", lblSegnalante);
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
				parameters.put("labelSegnalato", lblUtente);
				
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
					parameters.put("labelRiferimento", lblRiferimento);
				}
				parameters.put("SUBREPORT_DIR", "/subreport/ss/");
			
				String  reportPath = getSession().getServletContext().getRealPath(JASPER_PRIVACY_PATH);    
				jasperprint = JasperFillManager.fillReport(reportPath, parameters, new JREmptyDataSource());
			
		 	} catch (Exception e) {
		 		//addError("pdf.error");
		 		logger.error(e);
			}
			try{
				String  tempPath =getSession().getServletContext().getRealPath("/report/temp.pdf");
				JasperExportManager.exportReportToPdfFile(jasperprint, tempPath);
			
				InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(PDF_PATH);
				String titolo = dati!=null ? dati.getCf() : "VUOTO";
				filePrivacy = new DefaultStreamedContent(stream, "application/pdf", titolo+"_privacy.pdf");
				
		 	} catch (JRException e) {
		 			logger.error(e);
		 		}
		
		 return filePrivacy;
	 }
	
	public JasperPrint getJasperprint() {
		return jasperprint;
	}

	public void setJasperprint(JasperPrint jasperprint) {
		this.jasperprint = jasperprint;
	}

	public SchedaBean getSchedaBean() {
		return schedaBean;
	}

	public void setSchedaBean(SchedaBean schedaBean) {
		this.schedaBean = schedaBean;
	}

	public List<String> getLstSubreportObbl() {
		return lstSubreportObbl;
	}

	public void setLstSubreportObbl(List<String> lstSubreportObbl) {
		this.lstSubreportObbl = lstSubreportObbl;
	}

	public List<String> getLstSubreport() {
		return lstSubreport;
	}

	public void setLstSubreport(List<String> lstSubreport) {
		this.lstSubreport = lstSubreport;
	}

	public List<String> getLstReportOpz() {
		return lstReportOpz;
	}

	public void setLstReportOpz(List<String> lstReportOpz) {
		this.lstReportOpz = lstReportOpz;
	}

	public String[] getSelectedSubreport() {
		return selectedSubreport;
	}

	public void setSelectedSubreport(String[] selectedSubreport) {
		this.selectedSubreport = selectedSubreport;
	}

	public String[] getSelectedReportOpz() {
		return selectedReportOpz;
	}

	public void setSelectedReportOpz(String[] selectedReportOpz) {
		this.selectedReportOpz = selectedReportOpz;
	}

	public String getModalWidgetVar() {
		return modalWidgetVar;
	}

	public String getModalId() {
		return modalId;
	}

	public FascicoloBean getFascicoloBean() {
		return fascicoloBean;
	}

	public void setFascicoloBean(FascicoloBean fascicoloBean) {
		this.fascicoloBean = fascicoloBean;
	}

	public List<RelazioneDTO> getListaRelDTO() {
		return listaRelDTO;
	}

	public boolean isVediAttivita() {
		return vediAttivita;
	}

	public void setVediAttivita(boolean vediAttivita) {
		this.vediAttivita = vediAttivita;
	}

	public boolean isVediPAI() {
		return vediPAI;
	}

	public void setVediPAI(boolean vediPAI) {
		this.vediPAI = vediPAI;
	}

	public List<RelazioneDTO> getListaRelSel() {
		return listaRelSel;
	}

	public void setListaRelSel(List<RelazioneDTO> listaRelSel) {
		this.listaRelSel = listaRelSel;
	}

	public List<CsDPai> getListaPAI() {
		return listaPAI;
	}

	public void setListaPAI(List<CsDPai> listaPAI) {
		this.listaPAI = listaPAI;
	}

	public List<CsDPai> getListaPAISel() {
		return listaPAISel;
	}

	public void setListaPAISel(List<CsDPai> listaPAISel) {
		this.listaPAISel = listaPAISel;
	}

}
