package it.webred.cs.csa.web.manbean.fascicolo.schedaMultidimAnz;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.JsonBaseDTO;
import it.webred.cs.csa.ejb.dto.SchedaBarthelDTO;
import it.webred.cs.csa.utils.JasperUtils;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.fascicolo.schedaMultidimAnz.barthel.ISchedaBarthel;
import it.webred.cs.csa.web.manbean.report.ReportBaseBean;
import it.webred.cs.csa.web.manbean.report.dto.ReportPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.ValoriPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.schedaMultidimensionale.SchedaMultidimPdfDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoParamSchedaMultidime;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.jsf.interfaces.ISchedaMultidimAnz;
import it.webred.cs.json.isee.IIseeJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManSchedaMultidimAnzBean extends FascicoloCompBaseBean implements ISchedaMultidimAnz {
	
	private List<CsDValutazione> listaSchedeMultidims;
	private CsDValutazione selectedSchedaMultidimAnz;
	private CsDValutazione newSchedaMultidimAnz = new CsDValutazione();
	
	private List<String> prestazionis;
	private List<String> dachis;
	private List<String> prestDachis;
	
	private SchedaMultidimAnzBean schedaMultidimBean;
	
	private List<CsTbSchedaMultidim> infoValReteSocTooltip;
	private List<CsTbSchedaMultidim> infoSalFisicTooltip;
	private List<CsTbSchedaMultidim> infoSalPsichicaTooltip;
	private List<CsTbSchedaMultidim> infoAutoPersTooltip;
	private List<CsTbSchedaMultidim> infoAutoDomesticaTooltip;
	private List<CsTbSchedaMultidim> infoAutoExDomesticaTooltip;
	private List<CsTbSchedaMultidim> infoAdAbitTooltip;
	private List<CsTbSchedaMultidim> infoUbAbitTooltip;
	private List<CsTbSchedaMultidim> infoValFamTooltip;
	
	private List<SelectItem> lstItemsTipoMomValutazione;
	private List<SelectItem> lstItemsSaluteFisica;
	private List<SelectItem> lstItemsSalutePsichica;
	private List<SelectItem> lstItemsAutoPers;
	private List<SelectItem> lstItemsAutoDomestica;
	private List<SelectItem> lstItemsAutoExDomestica;
	
	private List<SelectItem> lstItemsAbTipo;
	private List<SelectItem> lstItemsAbTitolo;
	private List<SelectItem> lstItemsAbComposizione;
	private List<SelectItem> lstItemsUbAbitazione;
	private List<SelectItem> lstItemsAbBagno;
	private List<SelectItem> lstItemsAbFornito;
	private List<SelectItem> lstItemsAbElettrodomestici;
	private List<SelectItem> lstItemsAbAltriProblemi;
	
	private Date dataValutazione;
	private String descrizioneScheda;
	
	private List<CsAComponente> famConvComponentes;
	private List<CsAComponente> famNonConvComponentes;
	private List<CsAComponente> famAltriComponentes;
	private List<CsAComponente> famComponentes;
	private List<CsAComponente> selectedFamComponentes;
	private List<AnagraficaMultidimAnzBean> selectedfamAnaConvs;
	private List<AnagraficaMultidimAnzBean> selectedfamAnaNonConvs;
	private List<AnagraficaMultidimAnzBean> selectedfamAnaAltris;
	
	private boolean removeAnaConvButton;
	private boolean addAnaCorrButton;
	private boolean removeAnaNonConvButton;
	private boolean addAnaNonCorrButton;
	private boolean removeAnaAltriButton;
	private boolean addAnaAltriButton;
	private boolean apriAnaConvButton;
	private boolean apriAnaNonConvButton;
	private boolean apriAltrifamButton;
	private boolean isConvivente;
	private boolean isParente;
	
	private boolean newSchedaMultidimAnzRendered;
	
	@Override
	public void initializeData() {
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj2(DataModelCostanti.TipoDiario.VALUTAZIONE_MDS_ID);
		
		if(csASoggetto != null){
			dto.setObj(csASoggetto.getAnagraficaId());
			newSchedaMultidimAnzRendered = true;
		}
		else{
			dto.setObj(null);
			newSchedaMultidimAnzRendered = false;
		}
		
		listaSchedeMultidims = diarioService.getSchedeValutazioneSoggetto(dto);
		
		for(CsDValutazione val: listaSchedeMultidims) {
			String denom = this.getDenominazioneOperatore(val.getCsDDiario().getCsOOperatoreSettore().getCsOOperatore());
			val.getCsDDiario().getCsOOperatoreSettore().getCsOOperatore().setDenominazione(denom);
		}
	}
	
	private void initializeSchedaMultidimAnzDialog(Date dataVal, String descScheda, SchedaMultidimAnzBean schedaMultidim) throws Exception{

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		
		dto.setObj(TipoParamSchedaMultidime.MOM_VAL_TIPO);
		lstItemsTipoMomValutazione=this.loadListaSI(configService.getParamsSchedaMultidim(dto));
		
		dto.setObj(TipoParamSchedaMultidime.VAL_RETE_SOC);
		infoValReteSocTooltip = configService.getParamsSchedaMultidim(dto);
		
		dto.setObj(TipoParamSchedaMultidime.VAL_SALUTE_PERS);
		infoSalFisicTooltip =configService.getParamsSchedaMultidim(dto);
		lstItemsSaluteFisica=this.loadListaSI(infoSalFisicTooltip);
				
		dto.setObj(TipoParamSchedaMultidime.VAL_SALUTE_PSI);
		infoSalPsichicaTooltip =configService.getParamsSchedaMultidim(dto);
		lstItemsSalutePsichica=this.loadListaSI(infoSalPsichicaTooltip);
		
		dto.setObj(TipoParamSchedaMultidime.VAL_AUTO_PERS);
		infoAutoPersTooltip =configService.getParamsSchedaMultidim(dto);
		lstItemsAutoPers=this.loadListaSI(infoAutoPersTooltip);
		
		dto.setObj(TipoParamSchedaMultidime.VAL_AUTO_DOM);
		infoAutoDomesticaTooltip =configService.getParamsSchedaMultidim(dto);
		lstItemsAutoDomestica=this.loadListaSI(infoAutoDomesticaTooltip);
		
		dto.setObj(TipoParamSchedaMultidime.VAL_AUTO_EDOM);
		infoAutoExDomesticaTooltip =configService.getParamsSchedaMultidim(dto);
		lstItemsAutoExDomestica=this.loadListaSI(infoAutoExDomesticaTooltip);
		
		dto.setObj(TipoParamSchedaMultidime.VAL_RETE_FAM);
		infoValFamTooltip = configService.getParamsSchedaMultidim(dto);
		
		loadListeTabAbitazione(dto);
			
		// Initialize Matrix fileds in Mappe Risorse
		prestazionis = new ArrayList<String>();
		prestazionis.add("Pasti");
		prestazionis.add("Igiene personale");
		prestazionis.add("Lavanderia");
		prestazionis.add("Igiene ambientale");
		prestazionis.add("Gestione economica");
		prestazionis.add("Assunzione farmaci");
		prestazionis.add("Commissioni/spesa");
		prestazionis.add("Accompagnamento/Visite mediche/esami");
		prestazionis.add("Compagnia");
	    
		dachis = new ArrayList<String>();
	    dachis.add("Anziano");
	    dachis.add("Familiari conviventi");
	    dachis.add("Familiari non conviventi");
	    dachis.add("Personale retribuito");
	    dachis.add("Vicini amici volontari");
	    dachis.add("Servizi");
	    dachis.add("Nessuno");
	    
	    prestDachis = new ArrayList<String>();
	    
	    for (String item : prestazionis) {
			for (String it : dachis) {
				prestDachis.add(item + '_' + it);
			}
		}
	    				
		BaseDTO anaFamCurrDto = new BaseDTO();
		fillEnte(anaFamCurrDto);
		
		if (csASoggetto != null) {
			anaFamCurrDto.setObj(csASoggetto.getAnagraficaId());
			anaFamCurrDto.setObj3(true);
			CsAFamigliaGruppo famGr = schedaService.findFamigliaAllaDataBySoggettoId(anaFamCurrDto);
			if(famGr!=null){
				List<CsAComponente> famAllComponentes = famGr.getCsAComponentes();
	
				apriAnaConvButton = false;
				apriAnaNonConvButton = false;
				apriAltrifamButton = false;
				if(famAllComponentes != null) {
					for (CsAComponente it : famAllComponentes) {
						if (it.getCsTbTipoRapportoCon().getParente()) {
							if (it.getConvivente()) {
								famConvComponentes = new ArrayList<CsAComponente>();
								famConvComponentes.add(it);
								apriAnaConvButton = apriAnaConvButton || true;
								apriAnaNonConvButton = apriAnaNonConvButton || false;
								apriAltrifamButton = apriAltrifamButton || false;
							} else {
								famNonConvComponentes = new ArrayList<CsAComponente>();
								famNonConvComponentes.add(it);
								apriAnaConvButton = apriAnaConvButton || false;
								apriAnaNonConvButton = apriAnaNonConvButton || true;
								apriAltrifamButton = apriAltrifamButton || false;
							}
						} else {
							famAltriComponentes = new ArrayList<CsAComponente>();
							famAltriComponentes.add(it);
							apriAnaConvButton = apriAnaConvButton || false;
							apriAnaNonConvButton = apriAnaNonConvButton || false;
							apriAltrifamButton = apriAltrifamButton || true;
						}
					}
				}
			}
		}
		
		dataValutazione = dataVal;
		descrizioneScheda = descScheda;
		schedaMultidimBean = schedaMultidim;
		removeAnaConvButton = true;
		addAnaCorrButton = true;
		removeAnaNonConvButton = true;
		addAnaNonCorrButton = true;
		removeAnaAltriButton = true;
		addAnaAltriButton = true;
	}
	
	private void loadListeTabAbitazione(BaseDTO dto){
		
		dto.setObj(TipoParamSchedaMultidime.ABIT_TIPO);
		lstItemsAbTipo=this.loadListaSI(configService.getParamsSchedaMultidim(dto));
		
		dto.setObj(TipoParamSchedaMultidime.ABIT_TITOLO);
		lstItemsAbTitolo=this.loadListaSI(configService.getParamsSchedaMultidim(dto));
		
		dto.setObj(TipoParamSchedaMultidime.ABIT_COMPOSIZIONE);
		lstItemsAbComposizione=this.loadListaSI(configService.getParamsSchedaMultidim(dto));
		
		dto.setObj(TipoParamSchedaMultidime.ABIT_BAGNO);
		lstItemsAbBagno=this.loadListaSI(configService.getParamsSchedaMultidim(dto));
		
		dto.setObj(TipoParamSchedaMultidime.ABIT_FORNITO);
		lstItemsAbFornito=this.loadListaSI(configService.getParamsSchedaMultidim(dto));
		
		dto.setObj(TipoParamSchedaMultidime.ABIT_ELETTRODOMESTICI);
		lstItemsAbElettrodomestici=this.loadListaSI(configService.getParamsSchedaMultidim(dto));
		
		dto.setObj(TipoParamSchedaMultidime.ABIT_APROBLEMI);
		lstItemsAbAltriProblemi=this.loadListaSI(configService.getParamsSchedaMultidim(dto));
		
		dto.setObj(TipoParamSchedaMultidime.ABIT_ADEGUATEZZA);
		infoAdAbitTooltip = configService.getParamsSchedaMultidim(dto);
		
		dto.setObj(TipoParamSchedaMultidime.ABIT_UBICAZIONE);
		infoUbAbitTooltip = configService.getParamsSchedaMultidim(dto);
		lstItemsUbAbitazione = this.loadListaSI(infoUbAbitTooltip);
		
	}
	
	private List<SelectItem> loadListaSI(List<CsTbSchedaMultidim> lst){
		List<SelectItem> siList = new ArrayList<SelectItem>();
		for(CsTbSchedaMultidim tb : lst){
			SelectItem si = new SelectItem(tb.getCodice(), tb.getDescrizione());
			si.setDisabled(!tb.getAbilitato());
			siList.add(si);
		}
		return siList;
	}
	
	public void loadSchedaMultidimAnzData() {
		String jsonObj  = selectedSchedaMultidimAnz.getCsDDiario().getCsDClob().getDatiClob();
		ObjectMapper objectMapper = new ObjectMapper();
		
		try{
		
			initializeSchedaMultidimAnzDialog(selectedSchedaMultidimAnz.getCsDDiario().getDtAmministrativa(), selectedSchedaMultidimAnz.getDescrizioneScheda(), objectMapper.readValue(jsonObj, SchedaMultidimAnzBean.class));
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void stampaReportSchedaMultidimAnzData(CsDValutazione scheda){
		this.selectedSchedaMultidimAnz=scheda;
		this.loadSchedaMultidimAnzData();
		ReportBaseBean rb = (ReportBaseBean)this.getReferencedBean("reportBaseBean");
		CsASoggettoLAZY soggetto = (CsASoggettoLAZY) getSession().getAttribute("soggetto");
		rb.setSoggetto(soggetto);
		
		try {
			
			//path report e subreport
			String reportPath =	getSession().getServletContext().getRealPath("/report");
			List<String> listaSubreport = new ArrayList<String>();
			
			//parametri subreport
			HashMap<String, Object> map = new HashMap<String, Object>();
			logger.info(String.format("Crea  Mappa di parametri per i Jasper Reports"));
					
			//Barthel
			CsDValutazione valBarthel = this.loadSchedaBarthel(scheda);
			this.setOnViewBarthel(scheda);
			this.manSchedaBarthelBean.fillReport(reportPath, listaSubreport, map);
			
			JasperUtils jUtils = new JasperUtils();
			ReportPdfDTO pdfDTO = fillSchedaMultidimensionale(reportPath, listaSubreport, map);
			rb.fillReportCommonData(reportPath, listaSubreport, map, pdfDTO);
			
			SchedaMultidimPdfDTO pdf = (SchedaMultidimPdfDTO)pdfDTO;
			pdf.setRenderBarthel(valBarthel!=null);
			jUtils.esportaReport("MultiDim_"+rb.getDenominazione(), reportPath + "/schMultidimensionale.jrxml", listaSubreport, map, new JRBeanCollectionDataSource(Arrays.asList(pdf)));
			
		} catch (Exception e) {
			addErrorFromProperties("report.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	private String getLabelByCod(String tipo, Object codice){
		
		String s = "";
		String sCodice = codice!=null ? codice.toString() : null;
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(tipo);
		dto.setObj2(sCodice);
		CsTbSchedaMultidim sm = configService.getParamSchedaMultidim(dto);
		if(sm!=null){
			s = sm.getDescrizione();
			s+= (sm.getTooltip()!=null && !sm.getTooltip().isEmpty()) ? " ["+sm.getTooltip()+"]" : "";
		}
		return s;
	}
		
	private SchedaMultidimPdfDTO fillSchedaMultidimensionale(String reportPath, List<String> listaSubreport, HashMap<String, Object> map){
		SchedaMultidimPdfDTO pdf = new SchedaMultidimPdfDTO();
		
		//dati soggetto 
		CsASoggettoLAZY soggetto = (CsASoggettoLAZY) getSession().getAttribute("soggetto");
		CsAAnagrafica ana = soggetto.getCsAAnagrafica();
		String denominazione = ana.getCognome()+" "+ana.getNome();
		pdf.setDenominazione(denominazione);
		pdf.setCf(ana.getCf());
		pdf.setSesso(ana.getSesso());
		pdf.setComuneNascita(ana.getComuneNascitaDes()+" ("+ana.getProvNascitaCod()+")");
		pdf.setDataNascita(ddMMyyyy.format(ana.getDataNascita()));
		pdf.setCittadinanza(ana.getCittadinanza());
		String telefono = ana.getTel()!=null ? "fisso: "+ana.getTel() : "";
		telefono += ana.getCell()!=null? " cell.: "+ana.getCell() : "";
		pdf.setTelefono(telefono);
		
		BaseDTO dto1 = new BaseDTO();
		fillEnte(dto1);
		dto1.setObj(soggetto.getAnagraficaId());	
		List <CsAIndirizzo> indirizzo = indirizzoService.getIndirizziBySoggetto(dto1);
		for(CsAIndirizzo i: indirizzo){
			if(i.getDataFineApp()==null && i.getCsTbTipoIndirizzo().getId()==1){
				CsAAnaIndirizzo ind = i.getCsAAnaIndirizzo();
				String via = ind.getIndirizzo();
				String civico = ind.getCivicoNumero();
				String citta = ind.getComDes();
				String pv = ind.getProv();
				String d = "";
				d+= (via!=null && !via.isEmpty()) ? via : "";
				d+= (civico!=null && !civico.isEmpty()) ? " "+civico : "";
				d+= (citta!=null && !citta.isEmpty()) ? ", "+citta : "";
				d+= (pv!=null && !pv.isEmpty()) ? " ("+pv+")" : "";
				if(!d.isEmpty()) pdf.setIndirizzo(d.trim());
			}		
		}
		
		//Dati Generali
		pdf.setMomValTipo(getLabelByCod(TipoParamSchedaMultidime.MOM_VAL_TIPO, schedaMultidimBean.getMomValTipo()));
		pdf.setMomValLuogo(schedaMultidimBean.getMomValLuogo());
		pdf.setMomValRef(schedaMultidimBean.getMomValRef());
		pdf.setMomValTel(schedaMultidimBean.getMomValTel());
		
		List<ValoriPdfDTO> lstViveCon = new ArrayList<ValoriPdfDTO>();
		
		if(schedaMultidimBean.viveConiuge)
			lstViveCon.add(new ValoriPdfDTO("Con il coniuge","",null));
		if(schedaMultidimBean.viveSolo)
			lstViveCon.add(new ValoriPdfDTO("Solo","[Vive con familiari nella propria abitazione]", schedaMultidimBean.getDaQuandoViveSolo()));
		if(schedaMultidimBean.viveFigli)
			lstViveCon.add(new ValoriPdfDTO("Con i figli","[Vive con i figli nella propria abitazione o presso l'abitazione dei figli]",schedaMultidimBean.getDaQuandoViveFigli()));
		if(schedaMultidimBean.viveFamiliari)
			lstViveCon.add(new ValoriPdfDTO("Con i familiari","[Vive con familiari o conoscenti, nella propria abitazione o presso l'abitazione di loro]",schedaMultidimBean.getDaQuandoViveFamiliari()));
		if(schedaMultidimBean.viveAltri && schedaMultidimBean.getSpecificare()!=null)
			lstViveCon.add(new ValoriPdfDTO("Con altri",schedaMultidimBean.getSpecificare(),null));
	
		listaSubreport.add(reportPath + "/subreport/schedaMultidimensionale/listaValori.jrxml");
		map.put("conChiVive", new JRBeanCollectionDataSource(lstViveCon));

		pdf.setnFigli(schedaMultidimBean.getnFigli());
		pdf.setnFiglie(schedaMultidimBean.getnFiglie());
		pdf.setnSorelle(schedaMultidimBean.getnSorelle());
		pdf.setnFratelli(schedaMultidimBean.getnFratelli());
		
		listaSubreport.add(reportPath + "/subreport/schedaMultidimensionale/soggetti.jrxml");
		map.put("famAnaConvs", new JRBeanCollectionDataSource(schedaMultidimBean.getFamAnaConvs()));
		map.put("famAnaNonConvs", new JRBeanCollectionDataSource(schedaMultidimBean.getFamAnaNonConvs()));
	
		pdf.setValFamRating(getLabelByCod(TipoParamSchedaMultidime.VAL_RETE_FAM, schedaMultidimBean.getValFamRating()));
		
		//Rete Sociale
		pdf.setRelAltriSogg(schedaMultidimBean.getRelAltriSogg());
		pdf.setRelAltriSoggRetr(schedaMultidimBean.getRelAltriSoggRetr());
		map.put("famAnaAltris", new JRBeanCollectionDataSource(schedaMultidimBean.getFamAnaAltris()));
		pdf.setValReteSocRating(getLabelByCod(TipoParamSchedaMultidime.VAL_RETE_SOC, schedaMultidimBean.getValReteSocRating()));
	
		//Stato di salute
		List<ValoriPdfDTO> sl = new ArrayList<ValoriPdfDTO>();
		if(schedaMultidimBean.isPatCard())
			sl.add(getPatologia("Patologie cardiache",schedaMultidimBean.getPatCardDesc()));
		if(schedaMultidimBean.isPatRen())
			sl.add(getPatologia("Patologie renali",schedaMultidimBean.getPatRenDesc()));
		if(schedaMultidimBean.isPatAppResp())
			sl.add(getPatologia("Patologie polmonari",schedaMultidimBean.getPatAppRespDesc()));
		if(schedaMultidimBean.isPatAppDig())
			sl.add(getPatologia("Patologie app. digerente",schedaMultidimBean.getPatAppDigDesc()));
		if(schedaMultidimBean.isPatAppPsiComp())
			sl.add(getPatologia("Patologie psichiatriche",schedaMultidimBean.getPatAppPsiCompDesc()));
		if(schedaMultidimBean.isPatSistNer())
			sl.add(getPatologia("Patologie del sistema nervoso",schedaMultidimBean.getPatSistNerDesc()));
		if(schedaMultidimBean.isArtArtOst())
			sl.add(getPatologia("Patologie articolari",schedaMultidimBean.getArtArtOstDesc()));
		if(schedaMultidimBean.isDistVista())
			sl.add(getPatologia("Disturbi della vista",schedaMultidimBean.getDistVistaDesc()));
		if(schedaMultidimBean.isDistUdito())
			sl.add(getPatologia("Disturbi dell'udito",schedaMultidimBean.getDistUditoDesc()));
		if(schedaMultidimBean.isDistLoco())
			sl.add(getPatologia("Disturbi locomotori",schedaMultidimBean.getDistLocoDesc()));
		if(schedaMultidimBean.isIncontinenza())
			sl.add(getPatologia("Incontinenza",schedaMultidimBean.getIncontinenzaDesc()));
		if(schedaMultidimBean.isPiagheDecub())
			sl.add(getPatologia("Piaghe da decubito",schedaMultidimBean.getPiagheDecubDesc()));	
		if(schedaMultidimBean.isAltrePat())
			sl.add(getPatologia("Altre patologie",schedaMultidimBean.getAltrePatDesc()));	
		if(schedaMultidimBean.isAltreInfo())
			sl.add(getPatologia("Altre informazioni",schedaMultidimBean.getAltreInfoDesc()));	
		map.put("patologie", new JRBeanCollectionDataSource(sl));
		
		pdf.setNoteSanitarie(schedaMultidimBean.getNoteSanit());
	
		pdf.setValSalFisica(getLabelByCod(TipoParamSchedaMultidime.VAL_SALUTE_PERS, schedaMultidimBean.getSalFisic()));
		pdf.setValSalPsichica(getLabelByCod(TipoParamSchedaMultidime.VAL_SALUTE_PSI, schedaMultidimBean.getValSalutePsi()));
		pdf.setValAutonomiaPers(getLabelByCod(TipoParamSchedaMultidime.VAL_AUTO_PERS, schedaMultidimBean.getValAutoPers()));
		pdf.setValAutonomiaDomestica(getLabelByCod(TipoParamSchedaMultidime.VAL_AUTO_DOM, schedaMultidimBean.getValAutoDom()));
		pdf.setValAutonomiaExtraDom(getLabelByCod(TipoParamSchedaMultidime.VAL_AUTO_EDOM, schedaMultidimBean.getValAutoExDom()));
		
		//Abitazione
		pdf.setTipAbit(getLabelByCod(TipoParamSchedaMultidime.ABIT_TIPO, schedaMultidimBean.getTipAbit()));
		pdf.setAscensore(schedaMultidimBean.getAscensore());
		pdf.setPortineria(schedaMultidimBean.getPortineria());
		pdf.setTitoloGodimento(getLabelByCod(TipoParamSchedaMultidime.ABIT_TITOLO, schedaMultidimBean.getAbGod()));
		pdf.setComposizione(getLabelByCod(TipoParamSchedaMultidime.ABIT_COMPOSIZIONE, schedaMultidimBean.getComp()));
		pdf.setRiscaldamento(schedaMultidimBean.getRiscaldamento());
		pdf.setGabinetto(getLabelByCod(TipoParamSchedaMultidime.ABIT_BAGNO, schedaMultidimBean.getGabinetto()));
		
		String fornitos = "";
		for(String s : schedaMultidimBean.getFornitos()){
			String v = getLabelByCod(TipoParamSchedaMultidime.ABIT_FORNITO, s);
			fornitos+=!v.isEmpty() ? v+", " : "";
		}
		pdf.setFornitos(!fornitos.isEmpty() ? fornitos.substring(0,fornitos.lastIndexOf(',')) : "");	
		
		String eds = "";
		for(String s : schedaMultidimBean.getElettrodoms()){
			String v = getLabelByCod(TipoParamSchedaMultidime.ABIT_ELETTRODOMESTICI, s);
			eds+=!v.isEmpty() ? v+", " : "";
		}
		pdf.setElettrodomestici(!eds.isEmpty() ? eds.substring(0,eds.lastIndexOf(',')) : "");
		
		String ape = "";
		for(String s : schedaMultidimBean.getAltriProbEsits()){
			String v = getLabelByCod(TipoParamSchedaMultidime.ABIT_APROBLEMI, s);
			ape+=!v.isEmpty() ? v+", " : "";
		}
		pdf.setAltriProblemi(!ape.isEmpty() ? ape.substring(0,ape.lastIndexOf(',')) : "");
		
		pdf.setValAdAbitazione(getLabelByCod(TipoParamSchedaMultidime.ABIT_ADEGUATEZZA, schedaMultidimBean.getAdAbitRating().toString()));
		pdf.setValUbAbitazione(getLabelByCod(TipoParamSchedaMultidime.ABIT_UBICAZIONE, schedaMultidimBean.getUbAbits().toString()));
		
		//Situazione Economica
		FascicoloBean rb = (FascicoloBean)this.getReferencedBean("fascicoloBean");
		List<IIseeJson> lstIsee = rb.getIseeBean().getListaIsee();
		String isees =""; 
		for(IIseeJson isee : lstIsee)
			isees+= isee.fillReportIsee()+"<br/>";
		pdf.setIsee(isees);
		
		pdf.setIseeRipa(schedaMultidimBean.getIseeRipa());
		pdf.setValEconomica(getLabelByCod(TipoParamSchedaMultidime.VAL_ECONOMICA, schedaMultidimBean.getValEconRating().toString()));
		
		//Mappa risorse
		String[] mrs = schedaMultidimBean.getPrestazioniDachis();
		String mr="";
		for(String s: mrs){
			String[] ss = s.split("_");
			mr+= ss[0]+":  "+ss[1]+"<br/>";
		}
		if(!mr.isEmpty())
			pdf.setMappaRisorse(mr);
		
		return pdf;
	}
	
	
	
	private ValoriPdfDTO getPatologia(String patologia, String descrizione){
		ValoriPdfDTO v = new ValoriPdfDTO(patologia,
				(descrizione!=null && !descrizione.isEmpty()) ? descrizione : "" ,null);
		return v;
	}
	
	// Start Methods for Famiglia Anagrafica
	@Override
	public void loadAnagConv() {
		
		try{
		
			// Initialize famComponentes with db data
			famComponentes = new ArrayList<CsAComponente>();
			if(famConvComponentes != null) {
				famComponentes.addAll(famConvComponentes);
				removeAnaConvButton = true;
				addAnaCorrButton = true; 
				isConvivente = true;
				isParente = true;
				
				if(schedaMultidimBean.famAnaConvs.size() > 0){
					for (CsAComponente it : famComponentes) {
						for (AnagraficaMultidimAnzBean item : schedaMultidimBean.famAnaConvs) {
							if(it.getCsAAnagrafica().getId().equals(item.getId()))
								famComponentes.remove(it);
						}
						break;
					}
				}
			}
		
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void loadAnagNonConv() {
		
		try{
		
			// Initialize famComponentes with db data
			famComponentes = new ArrayList<CsAComponente>();
			if(famNonConvComponentes != null) {
				famComponentes.addAll(famNonConvComponentes);
				removeAnaNonConvButton = true;
				addAnaCorrButton = true;
				isConvivente = false;
				isParente = true;
				
				if(schedaMultidimBean.famAnaNonConvs.size() > 0){
					for (CsAComponente it : famComponentes) {
						for (AnagraficaMultidimAnzBean item : schedaMultidimBean.famAnaNonConvs) {
							if(it.getCsAAnagrafica().getId().equals(item.getId()))
								famComponentes.remove(it);
						}
						break;
					}
				}
			}
		
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void loadAnagAltri() {
		
		try {
		
			// Initialize famComponentes with db data
			famComponentes = new ArrayList<CsAComponente>();
			if(famAltriComponentes != null) {
				famComponentes.addAll(famAltriComponentes);
				addAnaCorrButton = true;
				removeAnaAltriButton = true;
				isParente = false;
				
				if(schedaMultidimBean.famAnaAltris.size() > 0){
					for (CsAComponente it : famComponentes) {
						for (AnagraficaMultidimAnzBean item : schedaMultidimBean.famAnaAltris) {
							if(it.getCsAAnagrafica().getId().equals(item.getId()))
								famComponentes.remove(it);
						}
						break;
					}
				}
			}
		
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void removeAnaConv() {
		if(selectedfamAnaConvs.size() > 0){
			for (AnagraficaMultidimAnzBean it : schedaMultidimBean.famAnaConvs){
				for (AnagraficaMultidimAnzBean item : selectedfamAnaConvs) {
					if(it.getId().equals(item.getId()))
						schedaMultidimBean.famAnaConvs.remove(it);
				}
				
				if(schedaMultidimBean.famAnaConvs.size() == 0)
					removeAnaConvButton = true;
				break;
			}	
		}
		else{
			removeAnaConvButton = true;
		}
	}

	@Override
	public void removeAnaNonConv(){
		if(selectedfamAnaNonConvs.size() > 0){
			for (AnagraficaMultidimAnzBean it : schedaMultidimBean.famAnaNonConvs){
				for (AnagraficaMultidimAnzBean item : selectedfamAnaNonConvs) {
					if(it.getId().equals(item.getId()))
						schedaMultidimBean.famAnaNonConvs.remove(it);
				}
				if(schedaMultidimBean.famAnaNonConvs.size() == 0)
					removeAnaConvButton = true;
				break;
			}	
		}
		else{
			removeAnaConvButton = true;
		}
	}
	
	@Override
	public void removeAnaAltri(){
		if(selectedfamAnaAltris.size() > 0){
			for (AnagraficaMultidimAnzBean it : schedaMultidimBean.famAnaAltris){
				for (AnagraficaMultidimAnzBean item : selectedfamAnaAltris) {
					if(it.getId().equals(item.getId()))
						schedaMultidimBean.famAnaAltris.remove(it);
				}
				if(schedaMultidimBean.famAnaAltris.size() == 0)
					removeAnaAltriButton = true;
				break;
			}	
		}
		else{
			removeAnaAltriButton = true;
		}
	}
	
	@Override
	public void addAnaCorr(){
		if(selectedFamComponentes.size() > 0){
			ArrayList<AnagraficaMultidimAnzBean> listFamComps = new ArrayList<AnagraficaMultidimAnzBean>();
			
			for (CsAComponente item : selectedFamComponentes) {
				String disponibilita = null;
				if(item.getCsTbDisponibilita() != null)
					disponibilita = item.getCsTbDisponibilita().getDescrizione();
				listFamComps.add(new AnagraficaMultidimAnzBean(item.getCsAAnagrafica().getId(), item.getCsAAnagrafica().getCognome(), item.getCsAAnagrafica().getNome(), item.getIndirizzoRes(), item.getComResDes(), item.getCsTbTipoRapportoCon().getDescrizione(), item.getCsAAnagrafica().getTel(), disponibilita));
			}
			
			if (isParente) {
				if (isConvivente == true)
					schedaMultidimBean.famAnaConvs = listFamComps;
				else
					schedaMultidimBean.famAnaNonConvs = listFamComps;
			} else {
				schedaMultidimBean.famAnaAltris = listFamComps;
			}
		}
	}
	
	@Override
	public void onRowSelectFamAnaConv(SelectEvent event) {
		removeAnaConvButton = false;
    }
 
    @Override
	public void onRowUnselectFamAnaConv(UnselectEvent event) {
    	removeAnaConvButton = true;
    }
	
    @Override
	public void onRowSelectFamAnaNonConv(SelectEvent event) {
		removeAnaNonConvButton = false;
    }
 
    @Override
	public void onRowUnselectFamAnaNonConv(UnselectEvent event) {
    	removeAnaNonConvButton = true;
    }
    
    @Override
	public void onRowSelectFamAnaCorr(SelectEvent event) {
		addAnaCorrButton = false;
    }
 
    @Override
	public void onRowUnselectFamAnaCorr(UnselectEvent event) {
        addAnaCorrButton = true;
    }
    
    @Override
	public void onRowSelectFamAnaNonCorr(SelectEvent event) { 
		addAnaNonCorrButton = false;
    }
 
    @Override
	public void onRowUnselectFamAnaNonCorr(UnselectEvent event) {
        addAnaNonCorrButton = true;
    }
    
    @Override
	public void onRowSelectFamAnaAltri(SelectEvent event) { 
    	removeAnaAltriButton = false;
    }
 
    @Override
	public void onRowUnselectFamAnaAltri(UnselectEvent event) {
    	removeAnaAltriButton = true;
    }
	// End Methods for Famiglia Anagrafica
    
    
	@Override
	public void saveSchedaMultidimAnzDialog() {
					
		try {
		
			if(!validaSchedaMultidimanz()) {
				RequestContext.getCurrentInstance().addCallbackParam("saved", false);
				return;
			}
			
			CsDValutazione schedaMultidimAnz = selectedSchedaMultidimAnz;
			
			BaseDTO schedaMultidimAnzDto = new BaseDTO();
			fillEnte(schedaMultidimAnzDto);
			
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(schedaMultidimBean);
			
			JsonBaseDTO dto = new JsonBaseDTO(); 
			fillEnte( dto );
			
			dto.setDiarioId(schedaMultidimAnz!=null ? schedaMultidimAnz.getCsDDiario().getId() : null);
			dto.setVersione("1"); //TODO
			dto.setDescrizione(descrizioneScheda);
			dto.setJsonString( jsonString );
			
			//Valorizzo i dati del diario
			dto.setTipoDiarioId(DataModelCostanti.TipoDiario.VALUTAZIONE_MDS_ID);
			dto.setCasoId(csASoggetto.getCsACaso().getId());
			dto.setOpSettore( (CsOOperatoreSettore) getSession().getAttribute("operatoresettore") );
			dto.setDtAmministrativa(dataValutazione);
			
			diarioService.saveSchedaJson(dto);
		
			initializeData();
		
			addInfoFromProperties("salva.ok");
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	private boolean validaSchedaMultidimanz() {
		
		boolean ok = true;

		if(!schedaMultidimBean.isViveAltri()
				&& !schedaMultidimBean.isViveConiuge()
				&& !schedaMultidimBean.isViveFamiliari()
				&& !schedaMultidimBean.isViveFigli()
				&& !schedaMultidimBean.isViveSolo()) {
			addError("Rete familiare è un campo obbligatorio", "Tab Rete familiare");
			ok = false;
		}
		
		if(schedaMultidimBean.getValFamRating().intValue() == 0) {
			addError("Sintesi valutazione rete familiare è un campo obbligatorio", "Tab Rete familiare");
			ok = false;
		}
		
		if(schedaMultidimBean.getRelAltriSogg() == null || "".equals(schedaMultidimBean.getRelAltriSogg())) {
			addError("Relazioni con altri soggetti è un campo obbligatorio", "Tab Rete sociale");
			ok = false;
		}
		
		if(schedaMultidimBean.getRelAltriSoggRetr() == null || "".equals(schedaMultidimBean.getRelAltriSoggRetr())) {
			addError("Relazioni con altri soggetti retribuiti è un campo obbligatorio", "Tab Rete sociale");
			ok = false;
		}
		
		return ok;
	}
	
	@Override
	public List<CsDValutazione> getListaSchedeMultidims() {
		return listaSchedeMultidims;
	}

	public void setListaSchedeMultidims(List<CsDValutazione> listaSchedeMultidims) {
		this.listaSchedeMultidims = listaSchedeMultidims;
	}

	public SchedaMultidimAnzBean getSchedaMultidimBean() {
		return schedaMultidimBean;
	}

	public void setSchedaMultidimBean(SchedaMultidimAnzBean schedaMultidimBean) {
		this.schedaMultidimBean = schedaMultidimBean;
	}
	
	@Override
	public CsDValutazione getSelectedSchedaMultidimAnz() {
		return selectedSchedaMultidimAnz;
	}

	public void setSelectedSchedaMultidimAnz(CsDValutazione selectedSchedaMultidimAnz) throws Exception {
		this.selectedSchedaMultidimAnz = selectedSchedaMultidimAnz;
		loadSchedaMultidimAnzData();
	}

	@Override
	public CsDValutazione getNewSchedaMultidimAnz() {
		return newSchedaMultidimAnz;
	}

	public void setNewSchedaMultidimAnz() throws Exception {
		this.newSchedaMultidimAnz = new CsDValutazione();
		selectedSchedaMultidimAnz = null;
		initializeSchedaMultidimAnzDialog(new Date(), "", new SchedaMultidimAnzBean());
	}

	@Override
	public List<String> getPrestazionis() {
		return prestazionis;
	}

	@Override
	public List<String> getDachis() {
		return dachis;
	}

	@Override
	public List<String> getPrestDachis() {
		return prestDachis;
	}

	@Override
	public List<CsAComponente> getFamComponentes() {
		return famComponentes;
	}

	@Override
	public List<CsAComponente> getSelectedFamComponentes() {
		return selectedFamComponentes;
	}

	public void setSelectedFamComponentes(List<CsAComponente> selectedFamComponentes) {
		this.selectedFamComponentes = selectedFamComponentes;
	}

	public List<AnagraficaMultidimAnzBean> getSelectedfamAnaConvs() {
		return selectedfamAnaConvs;
	}

	public void setSelectedfamAnaConvs(List<AnagraficaMultidimAnzBean> selectedfamAnaConvs) {
		this.selectedfamAnaConvs = selectedfamAnaConvs;
	}

	public List<AnagraficaMultidimAnzBean> getSelectedfamAnaNonConvs() {
		return selectedfamAnaNonConvs;
	}

	public void setSelectedfamAnaNonConvs(List<AnagraficaMultidimAnzBean> selectedfamAnaNonConvs) {
		this.selectedfamAnaNonConvs = selectedfamAnaNonConvs;
	}

	public List<AnagraficaMultidimAnzBean> getSelectedfamAnaAltris() {
		return selectedfamAnaAltris;
	}

	public void setSelectedfamAnaAltris(List<AnagraficaMultidimAnzBean> selectedfamAnaAltris) {
		this.selectedfamAnaAltris = selectedfamAnaAltris;
	}

	@Override
	public Date getDataValutazione() {
		return dataValutazione;
	}

	public void setDataValutazione(Date dataValutazione) {
		this.dataValutazione = dataValutazione;
	}
	
	@Override
	public String getDescrizioneScheda() {
		return descrizioneScheda;
	}

	public void setDescrizioneScheda(String descrizioneScheda) {
		this.descrizioneScheda = descrizioneScheda;
	}

	@Override
	public boolean isRemoveAnaConvButton() {
		return removeAnaConvButton;
	}

	@Override
	public boolean isAddAnaCorrButton() {
		return addAnaCorrButton;
	}

	@Override
	public boolean isRemoveAnaNonConvButton() {
		return removeAnaNonConvButton;
	}

	@Override
	public boolean isAddAnaNonCorrButton() {
		return addAnaNonCorrButton;
	}

	@Override
	public boolean isApriAnaConvButton() {
		return apriAnaConvButton;
	}

	@Override
	public boolean isApriAnaNonConvButton() {
		return apriAnaNonConvButton;
	}
	
	@Override
	public boolean isRemoveAnaAltriButton() {
		return removeAnaAltriButton;
	}

	@Override
	public boolean isAddAnaAltriButton() {
		return addAnaAltriButton;
	}
	
	@Override
	public boolean isApriAltrifamButton() {
		return apriAltrifamButton;
	}

	@Override
	public List<CsAComponente> getFamAltriComponentes() {
		return famAltriComponentes;
	}

	@Override
	public boolean isConvivente() {
		return isConvivente;
	}

	@Override
	public boolean isParente() {
		return isParente;
	}

	@Override
	public boolean isNewSchedaMultidimAnzRendered() {
		return newSchedaMultidimAnzRendered;
	}
	

	@Override
	public List<CsTbSchedaMultidim> getInfoValReteSocTooltip() {
		return infoValReteSocTooltip;
	}
	
	@Override
	public List<CsTbSchedaMultidim> getInfoSalFisicTooltip() {
		return infoSalFisicTooltip;
	}
	
	@Override
	public List<CsTbSchedaMultidim> getInfoAdAbitTooltip() {
		return infoAdAbitTooltip;
	}

	@Override
	public List<CsTbSchedaMultidim> getInfoUbAbitTooltip() {
		return infoUbAbitTooltip;
	}

	@Override
	public List<CsTbSchedaMultidim> getInfoValFamTooltip() {
		return infoValFamTooltip;
	}

	public void setInfoValReteSocTooltip(List<CsTbSchedaMultidim> infoValReteSocTooltip) {
		this.infoValReteSocTooltip = infoValReteSocTooltip;
	}

	public void setInfoSalFisicTooltip(List<CsTbSchedaMultidim> infoSalFisicTooltip) {
		this.infoSalFisicTooltip = infoSalFisicTooltip;
	}

	public void setInfoAdAbitTooltip(List<CsTbSchedaMultidim> infoAdAbitTooltip) {
		this.infoAdAbitTooltip = infoAdAbitTooltip;
	}

	public void setInfoUbAbitTooltip(List<CsTbSchedaMultidim> infoUbAbitTooltip) {
		this.infoUbAbitTooltip = infoUbAbitTooltip;
	}

	public void setInfoValFamTooltip(List<CsTbSchedaMultidim> infoValFamTooltip) {
		this.infoValFamTooltip = infoValFamTooltip;
	}

	@Override
	public List<SelectItem> getLstItemsSaluteFisica() {
		return lstItemsSaluteFisica;
	}

	public void setLstItemsSaluteFisica(List<SelectItem> lstItemsSaluteFisica) {
		this.lstItemsSaluteFisica = lstItemsSaluteFisica;
	}

	@Override
	public List<SelectItem> getLstItemsUbAbitazione() {
		return lstItemsUbAbitazione;
	}

	public void setLstItemsUbAbitazione(List<SelectItem> lstItemsUbAbitazione) {
		this.lstItemsUbAbitazione = lstItemsUbAbitazione;
	}

	@Override
	public List<SelectItem> getLstItemsAbFornito() {
		return lstItemsAbFornito;
	}
	
	@Override
	public List<SelectItem> getLstItemsAbElettrodomestici() {
		return lstItemsAbElettrodomestici;
	}
	
	@Override
	public List<SelectItem> getLstItemsAbAltriProblemi() {
		return lstItemsAbAltriProblemi;
	}

	public void setLstItemsAbFornito(List<SelectItem> lstItemsAbFornito) {
		this.lstItemsAbFornito = lstItemsAbFornito;
	}

	public void setLstItemsAbElettrodomestici(List<SelectItem> lstItemsAbElettrodomestici) {
		this.lstItemsAbElettrodomestici = lstItemsAbElettrodomestici;
	}

	public void setLstItemsAbAltriProblemi(List<SelectItem> lstItemsAbAltriProblemi) {
		this.lstItemsAbAltriProblemi = lstItemsAbAltriProblemi;
	}
	
	@Override
	public List<SelectItem> getLstItemsTipoMomValutazione() {
		return lstItemsTipoMomValutazione;
	}

	@Override
	public List<SelectItem> getLstItemsAbBagno() {
		return lstItemsAbBagno;
	}

	@Override
	public List<SelectItem> getLstItemsAbTipo() {
		return lstItemsAbTipo;
	}

	public void setLstItemsAbTipo(List<SelectItem> lstItemsAbTipo) {
		this.lstItemsAbTipo = lstItemsAbTipo;
	}
	
	@Override
	public List<SelectItem> getLstItemsAbTitolo() {
		return lstItemsAbTitolo;
	}

	public void setLstItemsAbTitolo(List<SelectItem> lstItemsAbTitolo) {
		this.lstItemsAbTitolo = lstItemsAbTitolo;
	}
	
	@Override
	public List<SelectItem> getLstItemsAbComposizione() {
		return lstItemsAbComposizione;
	}

	public void setLstItemsAbComposizione(List<SelectItem> lstItemsAbComposizione) {
		this.lstItemsAbComposizione = lstItemsAbComposizione;
	}

	public List<CsTbSchedaMultidim> getInfoSalPsichicaTooltip() {
		return infoSalPsichicaTooltip;
	}

	public List<CsTbSchedaMultidim> getInfoAutoPersTooltip() {
		return infoAutoPersTooltip;
	}

	public List<CsTbSchedaMultidim> getInfoAutoDomesticaTooltip() {
		return infoAutoDomesticaTooltip;
	}

	public List<SelectItem> getLstItemsSalutePsichica() {
		return lstItemsSalutePsichica;
	}

	public List<SelectItem> getLstItemsAutoPers() {
		return lstItemsAutoPers;
	}

	public List<SelectItem> getLstItemsAutoDomestica() {
		return lstItemsAutoDomestica;
	}

	public List<SelectItem> getLstItemsAutoExDomestica() {
		return lstItemsAutoExDomestica;
	}

	public void setInfoSalPsichicaTooltip(List<CsTbSchedaMultidim> infoSalPsichicaTooltip) {
		this.infoSalPsichicaTooltip = infoSalPsichicaTooltip;
	}

	public void setInfoAutoPersTooltip(List<CsTbSchedaMultidim> infoAutoPersTooltip) {
		this.infoAutoPersTooltip = infoAutoPersTooltip;
	}

	public void setInfoAutoDomesticaTooltip(List<CsTbSchedaMultidim> infoAutoDomesticaTooltip) {
		this.infoAutoDomesticaTooltip = infoAutoDomesticaTooltip;
	}

	public void setLstItemsSalutePsichica(List<SelectItem> lstItemsSalutePsichica) {
		this.lstItemsSalutePsichica = lstItemsSalutePsichica;
	}

	public void setLstItemsAutoPers(List<SelectItem> lstItemsAutoPers) {
		this.lstItemsAutoPers = lstItemsAutoPers;
	}

	public void setLstItemsAutoDomestica(List<SelectItem> lstItemsAutoDomestica) {
		this.lstItemsAutoDomestica = lstItemsAutoDomestica;
	}

	public void setLstItemsAutoExDomestica(List<SelectItem> lstItemsAutoExDomestica) {
		this.lstItemsAutoExDomestica = lstItemsAutoExDomestica;
	}

	public List<CsTbSchedaMultidim> getInfoAutoExDomesticaTooltip() {
		return infoAutoExDomesticaTooltip;
	}

	public void setInfoAutoExDomesticaTooltip(List<CsTbSchedaMultidim> infoAutoExDomesticaTooltip) {
		this.infoAutoExDomesticaTooltip = infoAutoExDomesticaTooltip;
	}



	//**************************************************************************************
	// SCHEDA BARTHEL
	//**************************************************************************************
	private ISchedaBarthel manSchedaBarthelBean;

	public ISchedaBarthel getManSchedaBarthelBean() {
		return manSchedaBarthelBean;
	}
	
	private CsDValutazione loadSchedaBarthel(CsDValutazione scheda) throws Exception{
		
		CsDValutazione valutazioneSchedaMultidim = scheda;

		SchedaBarthelDTO barthelDto = new SchedaBarthelDTO();
		fillEnte( barthelDto );
		barthelDto.setDiarioPadreId( valutazioneSchedaMultidim.getDiarioId() );
		barthelDto.setTipoDiarioId(DataModelCostanti.TipoDiario.BARTHEL_ID);
		CsDValutazione valutazioneSchedaBarthel = diarioService.findValutazioneChildByPadreId(barthelDto);	
		return valutazioneSchedaBarthel;
	}
	
	@Override
	public void setOnViewBarthel(CsDValutazione scheda) throws Exception {

		CsDValutazione valutazioneSchedaBarthel = this.loadSchedaBarthel(scheda);

		String jsonString = ""; String className = ""; 
		if( valutazioneSchedaBarthel != null ) {
			jsonString = valutazioneSchedaBarthel.getCsDDiario().getCsDClob().getDatiClob();
			className = valutazioneSchedaBarthel.getVersioneScheda();
		}

		//la versione di default è utile se si vuole comunque instanziare una versione intermedia tra la prima e la max
		String defaultVersion = "";
		manSchedaBarthelBean = (ISchedaBarthel) WebredClassFactory.newInstance( className, ISchedaBarthel.class, defaultVersion );

		//Initialize scheda barthel
		manSchedaBarthelBean.init( scheda, valutazioneSchedaBarthel );
	}


}
