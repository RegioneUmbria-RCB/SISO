package it.webred.cs.csa.web.manbean.report;

import it.webred.cs.csa.utils.JasperUtils;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.report.dto.BasePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.ReportPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.RelazionePdfDTO;
import it.webred.cs.csa.web.manbean.report.filler.RelazioniStampaFiller;
import it.webred.cs.csa.web.manbean.report.filler.ReportCartellaFiller;
import it.webred.cs.csa.web.manbean.report.filler.ReportFoglioAmmFiller;
import it.webred.cs.csa.web.manbean.scheda.SchedaBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsDRelazione;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.context.RequestContext;

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
	private static final String DIARIO = "Diario";
	private static final String NOTE = "Note";
	
	private static final String VOUCHER = "voucher";
	private static final String CONTRIBUTOECONOMICO = "contibeconomico";
	private static final String BUONOSOC = "buonosoc";
	private static final String PASTIDOM = "pastidom";
	private static final String CENTRODIURNO = "centrodiurno";
	
	
	private String modalWidgetVar = "wdgOpzioniStampaModal";
	private String modalId = "idOpzioniStampa";
	
	@ManagedProperty(value="#{schedaBean}")
	private SchedaBean schedaBean;
	
	@ManagedProperty(value="#{fascicoloBean}")
	private FascicoloBean fascicoloBean;
	
	private List<String> lstSubreportObbl;
	private List<String> lstSubreport;
	private List<String> lstReportOpz;
	private String[] selectedSubreport;
	private String[] selectedReportOpz;
	
	public void initializeStampaCartella(CsASoggettoLAZY cssoggetto) {
		if(cssoggetto != null) {
			
			tipoReport = REPORT_CARTELLA;
			this.setSoggetto(cssoggetto);
			//denominazione = soggetto.getCsAAnagrafica().getCognome() + "_" + soggetto.getCsAAnagrafica().getNome();
			schedaBean.initialize(soggetto);
			schedaBean.disabilitaModifiche();
			
			List<String> filtroSchede = new ArrayList<String>();
			filtroSchede.add(DataModelCostanti.TabFascicolo.COLLOQUIO);
			fascicoloBean.initializeFascicoloCartellaUtente(soggetto, true, filtroSchede);
		
			
			lstSubreportObbl = new ArrayList<String>();
			lstSubreportObbl.add(ANAGRAFICA);
			
			lstSubreport = new ArrayList<String>();
			if(schedaBean.isRenderTabParenti())
				lstSubreport.add(PARENTI);
			if(schedaBean.isRenderTabDatiSociali())
				lstSubreport.add(DATISOCIALI);
			if(schedaBean.isRenderTabInvalidita())
				lstSubreport.add(INVALIDITA);
			if(schedaBean.isRenderTabDisabilita())
				lstSubreport.add(DISABILITA);
			if(schedaBean.isRenderTabTribunale())
				lstSubreport.add(TRIBUNALE);
			if(schedaBean.isRenderTabOperatori())
				lstSubreport.add(OPERATORI);
			//vedere diario
			if(fascicoloBean.isColloquio())
				lstSubreport.add(DIARIO);
			if(schedaBean.isRenderTabNote())
				lstSubreport.add(NOTE);
			
			selectedSubreport = lstSubreport.toArray(new String[lstSubreport.size()]);
			lstReportOpz = new ArrayList<String>();
			lstReportOpz.add("Includi dati storici");		
			
			RequestContext.getCurrentInstance().execute(modalWidgetVar + ".show()");
			RequestContext.getCurrentInstance().update(modalId);
		} else addWarningFromProperties("seleziona.warning");
	}
	
	public void creaStampa() {
		
		if(REPORT_CARTELLA.equals(tipoReport))
			esportaCartella();
	}
	
	private void esportaCartella() {
		
		try { 
			
			ReportCartellaFiller filler = new ReportCartellaFiller();
			filler.setSchedaBean(schedaBean);
			filler.setFascicoloBean(fascicoloBean);
			filler.setSoggetto(soggetto);
			
			Map<String, String> mapSubreport = new HashMap<String, String>();
			for(int i = 0; i < selectedSubreport.length; i++) 
				mapSubreport.put(selectedSubreport[i], selectedSubreport[i]);
			
			//path report e subreport
			String reportPath =	getSession().getServletContext().getRealPath("/report");
			List<String> listaSubreport = new ArrayList<String>();
			listaSubreport.add(reportPath + "/subreport/cartella/anagrafica.jrxml");
			
			//parametri subreport
			HashMap<String, Object> map = new HashMap<String, Object>();
			logger.info(String.format("Crea  Mappa di parametri per i Jasper Reports"));
			map.put("anagrafica", new JRBeanCollectionDataSource(Arrays.asList(filler.fillAnagrafica())));
						
			if(mapSubreport.containsKey(PARENTI)) {
				listaSubreport.add(reportPath + "/subreport/cartella/parenti.jrxml");
				map.put("parenti", new JRBeanCollectionDataSource(filler.fillParenti()));
			}
			
			if(mapSubreport.containsKey(DATISOCIALI)) {
				listaSubreport.add(reportPath + "/subreport/cartella/datisociali.jrxml");
				map.put("datisociali", new JRBeanCollectionDataSource(Arrays.asList(filler.fillDatiSociali())));
			}
			
			if(mapSubreport.containsKey(INVALIDITA)) {
				listaSubreport.add(reportPath + "/subreport/cartella/invalidita.jrxml");
				map.put("invalidita", new JRBeanCollectionDataSource(Arrays.asList(filler.fillInvalidita())));
			}
			
			if(mapSubreport.containsKey(DISABILITA)) {
				listaSubreport.add(reportPath + "/subreport/cartella/disabilita.jrxml");
				map.put("disabilita", new JRBeanCollectionDataSource(Arrays.asList(filler.fillDisabilita())));
			}
			
			if(mapSubreport.containsKey(TRIBUNALE)) {
				listaSubreport.add(reportPath + "/subreport/cartella/tribunale.jrxml");
				map.put("tribunale", new JRBeanCollectionDataSource(Arrays.asList(filler.fillTribunale())));
			}
			
			if(mapSubreport.containsKey(OPERATORI)) {
				listaSubreport.add(reportPath + "/subreport/cartella/operatori.jrxml");
				map.put("operatori", new JRBeanCollectionDataSource(filler.fillOperatori()));
			}
			
			if(mapSubreport.containsKey(DIARIO)) {
				listaSubreport.add(reportPath + "/subreport/cartella/Diario.jrxml");
				map.put("Diario", new JRBeanCollectionDataSource(filler.fillDiario()));
			}
			if(mapSubreport.containsKey(NOTE)) {
				listaSubreport.add(reportPath + "/subreport/cartella/note.jrxml");
				map.put("note", new JRBeanCollectionDataSource(Arrays.asList(filler.fillNote())));
			}
			
			fillReportHeader(reportPath, listaSubreport, map);
			
			//parametri report
			ReportPdfDTO repPdf = new ReportPdfDTO();
			fillReportPieDiPagina(repPdf);
			
			JasperUtils jUtils = new JasperUtils();
			jUtils.esportaReport("Cartella_" + this.getDenominazione(), reportPath + "/cartella.jrxml", listaSubreport, map, new JRBeanCollectionDataSource(Arrays.asList(repPdf)));
			
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
			if(filler.getIdTipoInterventoCustom()>0){
				listaSubreport.add(reportPath + "/subreport/foglioamm/tipoIntervento_" + filler.getIdTipoInterventoCustom() + ".jrxml");
				map.put("tipoIntervento", new JRBeanCollectionDataSource(Arrays.asList(tipoIntPdfDTO)));
		    }
			
			fillReportHeader(reportPath, listaSubreport, map);
			
			JasperUtils jUtils = new JasperUtils();
			BasePdfDTO intPdfDTO = filler.fillIntervento();
			intPdfDTO.setShowCasellaContributo(tipoIntPdfDTO.getShowCasellaContributo());
			String tipoIntervento = filler.getIn().getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento().getDescrizione();
			jUtils.esportaReport(tipoIntervento + "_" + this.getDenominazione(), reportPath + "/foglioAmm.jrxml", listaSubreport, map, new JRBeanCollectionDataSource(Arrays.asList(intPdfDTO)));
			
		} catch (Exception e) {
			addErrorFromProperties("report.error");
			logger.error(e.getMessage(),e);
		}
		
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


}
