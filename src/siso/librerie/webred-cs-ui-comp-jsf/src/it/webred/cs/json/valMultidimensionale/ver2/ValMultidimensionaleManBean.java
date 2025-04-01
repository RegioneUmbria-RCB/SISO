package it.webred.cs.json.valMultidimensionale.ver2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import it.webred.cs.csa.ejb.client.AccessTableSinaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;
import it.webred.cs.csa.utils.bean.report.dto.TriagePdfDTO;
import it.webred.cs.csa.utils.bean.report.dto.ValoriPdfDTO;
import it.webred.cs.data.DataModelCostanti.TipoParamSchedaMultidime;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.data.model.CsTbSinaDomanda;
import it.webred.cs.data.model.CsTbSinaRisposta;
import it.webred.cs.jsf.manbean.SinaMan;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.abitazione.AbitazioneManBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.barthel.ISchedaBarthel;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.isee.IIseeJson;
import it.webred.cs.json.valMultidimensionale.ValMultidimensionaleManBaseBean;
import it.webred.cs.json.valMultidimensionale.ValMultidimensionaleRowBean;
import it.webred.ct.support.datarouter.CeTBaseObject;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ValMultidimensionaleManBean extends ValMultidimensionaleManBaseBean {

	private static final long serialVersionUID = 1L;

	private ValMultidimensionaleController controller;
	//private SinaEsegDTO sinaDTO = new SinaEsegDTO(); 
	
	protected AccessTableSinaSessionBeanRemote sinaService = 
			(AccessTableSinaSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSinaSessionBean");

	/* CAMPI DA VERIFICARE*/
	
	//private List<CsTbSinaDomanda> lstSinaParams; 
	//private List<CsTbSinaDomanda> lstSinaParamInvalidita;

	//private  List<ArTbPrestazioniInps> lstPrestazioniInps = new ArrayList<ArTbPrestazioniInps>();
	
	private List<String> prestazionis;
	private List<String> dachis;
	private List<String> prestDachis;

	private List<CsTbSchedaMultidim> infoValReteSocTooltip = new ArrayList<CsTbSchedaMultidim>();
	private List<CsTbSchedaMultidim> infoSalFisicTooltip= new ArrayList<CsTbSchedaMultidim>();
	private List<CsTbSchedaMultidim> infoSalPsichicaTooltip = new ArrayList<CsTbSchedaMultidim>();
	private List<CsTbSchedaMultidim> infoAutoPersTooltip = new ArrayList<CsTbSchedaMultidim>();
	private List<CsTbSchedaMultidim> infoAutoDomesticaTooltip = new ArrayList<CsTbSchedaMultidim>();
	private List<CsTbSchedaMultidim> infoAutoExDomesticaTooltip = new ArrayList<CsTbSchedaMultidim>();
	private List<CsTbSchedaMultidim> infoAdAbitTooltip = new ArrayList<CsTbSchedaMultidim>();
	private List<CsTbSchedaMultidim> infoUbAbitTooltip = new ArrayList<CsTbSchedaMultidim>();
	private List<CsTbSchedaMultidim> infoValFamTooltip = new ArrayList<CsTbSchedaMultidim>();
	
	private List<SelectItem> lstItemsTipoMomValutazione=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsSaluteFisica=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsSalutePsichica=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAutoPers=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAutoDomestica=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAutoExDomestica=new ArrayList<SelectItem>();
	
	private List<SelectItem> lstItemsAbTipo=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAbTitolo=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAbProprietario=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAbComposizione=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsUbAbitazione=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAbBagno=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAbFornito=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAbElettrodomestici=new ArrayList<SelectItem>();
	private List<SelectItem> lstItemsAbAltriProblemi=new ArrayList<SelectItem>();
	
	protected List<AnagraficaMultidimAnzBean> selectedfamAnaConvs;
	protected List<AnagraficaMultidimAnzBean> selectedfamAnaNonConvs;
	protected List<AnagraficaMultidimAnzBean> selectedfamAnaAltris;
	
	//SISO-783
	private SinaMan sinaMan;
	
	
	public ValMultidimensionaleManBean() {
		controller = new ValMultidimensionaleController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());
		
		/*Inizializzazione dei dati*/
		CeTBaseObject dto = new CeTBaseObject();
		fillEnte(dto);
		
		loadListaSI(TipoParamSchedaMultidime.MOM_VAL_TIPO, null, lstItemsTipoMomValutazione);
		loadListaSI(TipoParamSchedaMultidime.VAL_RETE_SOC, infoValReteSocTooltip, null);
		loadListaSI(TipoParamSchedaMultidime.VAL_SALUTE_PERS, infoSalFisicTooltip, lstItemsSaluteFisica);
		loadListaSI(TipoParamSchedaMultidime.VAL_SALUTE_PSI, infoSalPsichicaTooltip, lstItemsSalutePsichica);
		loadListaSI(TipoParamSchedaMultidime.VAL_AUTO_PERS, infoAutoPersTooltip, lstItemsAutoPers);
		loadListaSI(TipoParamSchedaMultidime.VAL_AUTO_DOM, infoAutoDomesticaTooltip, lstItemsAutoDomestica);
		loadListaSI(TipoParamSchedaMultidime.VAL_AUTO_EDOM, infoAutoExDomesticaTooltip, lstItemsAutoExDomestica);
		loadListaSI(TipoParamSchedaMultidime.VAL_RETE_FAM, infoValFamTooltip, null);
		
		//lstPrestazioniInps = configService.getPrestazioniInpsSina(dto);
		loadListeTabAbitazione(dto);
		
		//loadSinaSI();
		
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
	    dachis.add("Utente");
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
	}


	
	private void loadListeTabAbitazione(CeTBaseObject dto){
		
		//loadListaSI(TipoParamSchedaMultidime.ABIT_TITOLO, null, lstItemsAbTitolo);
		//loadListaSI(TipoParamSchedaMultidime.ABIT_COMPOSIZIONE, null, lstItemsAbComposizione);
		loadListaSI(TipoParamSchedaMultidime.ABIT_BAGNO, null, lstItemsAbBagno);
		loadListaSI(TipoParamSchedaMultidime.ABIT_FORNITO, null, lstItemsAbFornito);
		loadListaSI(TipoParamSchedaMultidime.ABIT_ELETTRODOMESTICI, null, lstItemsAbElettrodomestici);
		loadListaSI(TipoParamSchedaMultidime.ABIT_APROBLEMI, null, lstItemsAbAltriProblemi);
		loadListaSI(TipoParamSchedaMultidime.ABIT_ADEGUATEZZA, infoAdAbitTooltip, null);
		loadListaSI(TipoParamSchedaMultidime.ABIT_UBICAZIONE, infoUbAbitTooltip, lstItemsUbAbitazione);
		
		List<KeyValueDTO> l1 = confService.getListaTipoAbitazione(dto);
		this.lstItemsAbTipo = new ArrayList<SelectItem>();
		for(KeyValueDTO t: l1){
			SelectItem si = new SelectItem(t.getDescrizione());
			si.setDisabled(!t.isAbilitato());
			this.lstItemsAbTipo.add(si);
		}
		
		List<KeyValueDTO> l2 = confService.getListaTitoloGod(dto);
		this.lstItemsAbTitolo = new ArrayList<SelectItem>();
		for(KeyValueDTO t: l2){
			SelectItem si = new SelectItem(t.getDescrizione());
			si.setDisabled(!t.isAbilitato());
			this.lstItemsAbTitolo.add(si);
		}
		List<KeyValueDTO> l3 = confService.getListaGestProprietario(dto);
		this.lstItemsAbProprietario = new ArrayList<SelectItem>();
		for(KeyValueDTO t : l3){
			SelectItem si = new SelectItem(t.getDescrizione());
			si.setDisabled(!t.isAbilitato());
			this.lstItemsAbProprietario.add(si);
			
		}
		
	}
		
	public ValMultidimensionaleBean getJsonCurrent() {

		return controller.getJsonCurrent();
	}

	/*DEVE ESSERE CHIAMATO SOLO QUANDO CREO UNA NUOVA VALUTAZIONE*/
	private void loadDatiAbitazione(){ //Prendo i valori in Dati Sociali
		
		CsADatiSociali cs =  new CsADatiSociali();
		IAbitazione abiMan;
		BaseDTO dto = new BaseDTO();
		
		try {
		fillEnte(dto);
		dto.setObj(soggetto.getAnagraficaId());
		dto.setObj2(new CsADatiSociali());
		List<?> listaCs = schedaService.findCsBySoggettoId(dto);
		
		int i = 0;
		while (i < listaCs.size()) {
			cs = (CsADatiSociali) listaCs.get(i);
			if (cs.getDataFineApp().after(new Date())){
				abiMan = AbitazioneManBaseBean.initByModel(cs.getAbitazione());
			   
			     i++;
			     this.getJsonCurrent().setTipAbit(abiMan.getTipoAbitazione());
				 this.getJsonCurrent().setAbGod(abiMan.getTitoloGodimento());
				 this.getJsonCurrent().setNumVani(abiMan.getNumVani());
				 this.getJsonCurrent().setProprietarioGestore(abiMan.getProprietarioGestore());
			     }
		}
		
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

	@Override
	public void init(ISchedaValutazione bean) {
		try{
			controller.load((ValMultidimensionaleBean)bean.getSelected());
			valorizzaDatiBase(bean.getIdCaso(), bean.getIdSchedaUdc(), false);
			this.getJsonCurrent().setDataValutazione(new Date());
			this.loadDatiAbitazione();
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void init(CsDValutazione parent, CsDValutazione scheda) {
		try {
			controller.loadData(parent, scheda);
			CsDDiario diario = scheda.getCsDDiario();
			Long idCaso = diario.getCsACaso() != null ? diario.getCsACaso().getId() : null;
			valorizzaDatiBase(idCaso, diario.getSchedaId(), false);
			this.getJsonCurrent().setDescrizioneScheda(scheda.getDescrizioneScheda());
			this.getJsonCurrent().setDataValutazione(scheda.getCsDDiario().getDtAmministrativa());
	
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void initRowList(CsDValutazione parent, CsDValutazione scheda) {
		try {
			controller.loadData(parent, scheda);
			CsDDiario diario = scheda.getCsDDiario();
			Long idCaso = diario.getCsACaso() != null ? diario.getCsACaso().getId() : null;
			valorizzaDatiBase(idCaso, diario.getSchedaId(), true);
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	private void valorizzaDatiBase(Long idCaso, Long idSchedaUdc, boolean isRow){
		setIdCaso(idCaso);
		setIdSchedaUdc(idSchedaUdc);
		if(!isRow)
			sinaMan = new SinaMan(controller.getDiarioId(),getJsonCurrent());
	}


	@Override
	public JsonBaseBean getSelected() {
		return getJsonCurrent();
	}

	@Override
	public boolean elimina() {
		boolean ok = false;
		try {
			//prescSpec.eliminaDocumenti(controller.getDataModel().getCsDDiario());
			controller.elimina();
			addInfoFromProperties("elimina.ok");
			ok = true;
		} catch (CarSocialeServiceException cse) {
			addMessage("Errore di eliminazione", cse.getMessage(), FacesMessage.SEVERITY_ERROR);
			logger.error(cse.getMessage(), cse);
		} catch (Exception e) {
			addErrorFromProperties("elimina.error");
			logger.error(e.getMessage(), e);
		}
		return ok;
	}

	@Override
	public void setIdCasoController(Long idCaso) {
		controller.setCasoId(idCaso);
	}

	@Override
	public void setIdSchedaUdc(Long idUdc) {
		controller.setUdcId(idUdc);
	}

	@Override
	public CsDValutazione getCurrentModel() {
		return controller.getDataModel();
	}

	@Override
	public boolean isNew() {
		return !(controller.getDiarioId() != null && controller.getDiarioId().longValue() > 0);
	}

	@Override
	public Long getIdSchedaUdc() {
		return controller.getUdcId();
	}

	public ValMultidimensionaleController getController() {
		return this.controller;
	}

	@Override
	public boolean save(Long visSecondoLivello){
		this.controller.setVisSecondoLivello(visSecondoLivello);
		return this.save();
	}
	
	@Override
	public boolean save() {

		Date dataAmministrativa = controller.getDtAmministrativa();
		//Valorizzo la data sina con quella della valutazione multidimensionale
		sinaMan.getSinaDTO().setData(dataAmministrativa);
		
		boolean ok = false;
		try {
			if (validaData()) {
				
				controller.save(this.getClass().getName());
				
				sinaMan.salva(controller.getDiarioId(),dataAmministrativa, controller.getVisSecondoLivello());
				
				ok = true;
				
				//this.prescSpec.salvaDocumento(controller.getDataModel().getCsDDiario());
				
				// ora salva
				addInfoFromProperties("salva.ok");
			}
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
		return ok;
	}

	@Override
	public boolean validaData() {
		boolean validazioneOk = true;
		List<String> messagges;
		messagges = this.getJsonCurrent().checkObbligatorieta();
		
		//SISO-985
		List<String> messagesSina = this.sinaMan.valida();
		if(messagesSina.size()>0){
			Iterator<String> messageSinaIterator = messagesSina.iterator();
			while(messageSinaIterator.hasNext()){
				messagges.add("Tab Disabilità e NA: "+messageSinaIterator.next());
		     }
		}
			
		if( messagges.size() > 0 ) {
			addWarning("Scheda di Valutazione Multidimensionale", StringUtils.join(messagges, "<br/>"));
			validazioneOk &= false;
		}

		RequestContext.getCurrentInstance().addCallbackParam("canClose", validazioneOk);
		
		return validazioneOk;
	}

	@Override
	public void restore() {
		controller.restore();
	}
	
	

	public List<String> getPrestazionis() {
		return prestazionis;
	}

	public void setPrestazionis(List<String> prestazionis) {
		this.prestazionis = prestazionis;
	}

	public List<String> getDachis() {
		return dachis;
	}

	public void setDachis(List<String> dachis) {
		this.dachis = dachis;
	}

	public List<String> getPrestDachis() {
		return prestDachis;
	}

	public void setPrestDachis(List<String> prestDachis) {
		this.prestDachis = prestDachis;
	}

	public List<CsTbSchedaMultidim> getInfoValReteSocTooltip() {
		return infoValReteSocTooltip;
	}

	public void setInfoValReteSocTooltip(
			List<CsTbSchedaMultidim> infoValReteSocTooltip) {
		this.infoValReteSocTooltip = infoValReteSocTooltip;
	}

	public List<CsTbSchedaMultidim> getInfoSalFisicTooltip() {
		return infoSalFisicTooltip;
	}

	public void setInfoSalFisicTooltip(List<CsTbSchedaMultidim> infoSalFisicTooltip) {
		this.infoSalFisicTooltip = infoSalFisicTooltip;
	}

	public List<CsTbSchedaMultidim> getInfoSalPsichicaTooltip() {
		return infoSalPsichicaTooltip;
	}

	public void setInfoSalPsichicaTooltip(
			List<CsTbSchedaMultidim> infoSalPsichicaTooltip) {
		this.infoSalPsichicaTooltip = infoSalPsichicaTooltip;
	}

	public List<CsTbSchedaMultidim> getInfoAutoPersTooltip() {
		return infoAutoPersTooltip;
	}

	public void setInfoAutoPersTooltip(List<CsTbSchedaMultidim> infoAutoPersTooltip) {
		this.infoAutoPersTooltip = infoAutoPersTooltip;
	}

	public List<CsTbSchedaMultidim> getInfoAutoDomesticaTooltip() {
		return infoAutoDomesticaTooltip;
	}

	public void setInfoAutoDomesticaTooltip(
			List<CsTbSchedaMultidim> infoAutoDomesticaTooltip) {
		this.infoAutoDomesticaTooltip = infoAutoDomesticaTooltip;
	}

	public List<CsTbSchedaMultidim> getInfoAutoExDomesticaTooltip() {
		return infoAutoExDomesticaTooltip;
	}

	public void setInfoAutoExDomesticaTooltip(
			List<CsTbSchedaMultidim> infoAutoExDomesticaTooltip) {
		this.infoAutoExDomesticaTooltip = infoAutoExDomesticaTooltip;
	}

	public List<CsTbSchedaMultidim> getInfoAdAbitTooltip() {
		return infoAdAbitTooltip;
	}

	public void setInfoAdAbitTooltip(List<CsTbSchedaMultidim> infoAdAbitTooltip) {
		this.infoAdAbitTooltip = infoAdAbitTooltip;
	}

	public List<CsTbSchedaMultidim> getInfoUbAbitTooltip() {
		return infoUbAbitTooltip;
	}

	public void setInfoUbAbitTooltip(List<CsTbSchedaMultidim> infoUbAbitTooltip) {
		this.infoUbAbitTooltip = infoUbAbitTooltip;
	}

	public List<CsTbSchedaMultidim> getInfoValFamTooltip() {
		return infoValFamTooltip;
	}

	public void setInfoValFamTooltip(List<CsTbSchedaMultidim> infoValFamTooltip) {
		this.infoValFamTooltip = infoValFamTooltip;
	}

	public List<SelectItem> getLstItemsTipoMomValutazione() {
		return lstItemsTipoMomValutazione;
	}

	public void setLstItemsTipoMomValutazione(
			List<SelectItem> lstItemsTipoMomValutazione) {
		this.lstItemsTipoMomValutazione = lstItemsTipoMomValutazione;
	}

	public List<SelectItem> getLstItemsSaluteFisica() {
		return lstItemsSaluteFisica;
	}

	public void setLstItemsSaluteFisica(List<SelectItem> lstItemsSaluteFisica) {
		this.lstItemsSaluteFisica = lstItemsSaluteFisica;
	}

	public List<SelectItem> getLstItemsSalutePsichica() {
		return lstItemsSalutePsichica;
	}

	public void setLstItemsSalutePsichica(List<SelectItem> lstItemsSalutePsichica) {
		this.lstItemsSalutePsichica = lstItemsSalutePsichica;
	}

	public List<SelectItem> getLstItemsAutoPers() {
		return lstItemsAutoPers;
	}

	public void setLstItemsAutoPers(List<SelectItem> lstItemsAutoPers) {
		this.lstItemsAutoPers = lstItemsAutoPers;
	}

	public List<SelectItem> getLstItemsAutoDomestica() {
		return lstItemsAutoDomestica;
	}

	public void setLstItemsAutoDomestica(List<SelectItem> lstItemsAutoDomestica) {
		this.lstItemsAutoDomestica = lstItemsAutoDomestica;
	}

	public List<SelectItem> getLstItemsAutoExDomestica() {
		return lstItemsAutoExDomestica;
	}

	public void setLstItemsAutoExDomestica(List<SelectItem> lstItemsAutoExDomestica) {
		this.lstItemsAutoExDomestica = lstItemsAutoExDomestica;
	}

	

	public List<SelectItem> getLstItemsAbComposizione() {
		return lstItemsAbComposizione;
	}

	public void setLstItemsAbComposizione(List<SelectItem> lstItemsAbComposizione) {
		this.lstItemsAbComposizione = lstItemsAbComposizione;
	}

	public List<SelectItem> getLstItemsUbAbitazione() {
		return lstItemsUbAbitazione;
	}

	public void setLstItemsUbAbitazione(List<SelectItem> lstItemsUbAbitazione) {
		this.lstItemsUbAbitazione = lstItemsUbAbitazione;
	}

	public List<SelectItem> getLstItemsAbBagno() {
		return lstItemsAbBagno;
	}

	public void setLstItemsAbBagno(List<SelectItem> lstItemsAbBagno) {
		this.lstItemsAbBagno = lstItemsAbBagno;
	}

	public List<SelectItem> getLstItemsAbFornito() {
		return lstItemsAbFornito;
	}

	public void setLstItemsAbFornito(List<SelectItem> lstItemsAbFornito) {
		this.lstItemsAbFornito = lstItemsAbFornito;
	}

	public List<SelectItem> getLstItemsAbElettrodomestici() {
		return lstItemsAbElettrodomestici;
	}

	public void setLstItemsAbElettrodomestici(
			List<SelectItem> lstItemsAbElettrodomestici) {
		this.lstItemsAbElettrodomestici = lstItemsAbElettrodomestici;
	}

	public List<SelectItem> getLstItemsAbAltriProblemi() {
		return lstItemsAbAltriProblemi;
	}

	public void setLstItemsAbAltriProblemi(List<SelectItem> lstItemsAbAltriProblemi) {
		this.lstItemsAbAltriProblemi = lstItemsAbAltriProblemi;
	}

	@Override
	public void valorizzaRowList(ValMultidimensionaleRowBean row) {
		//TODO: valorizzare eventuali campi aggiuntivi da mostrare in lista
		//aggiungere il campo stato da visualizzare nella listaCs
		if(this.getJsonCurrent().getStatoCompScheda() != null ) row.setStatoCompilazione(this.getJsonCurrent().getStatoCompScheda());
		
		
	}

	// Start Methods for Famiglia Anagrafica
	private void removeCompSelected(List<CsAComponente> attuali, List<AnagraficaMultidimAnzBean> toRemove){
		for (AnagraficaMultidimAnzBean item : toRemove) {
			for (CsAComponente it : attuali){
				if(it.getCsAAnagrafica().getId().equals(item.getId())) {
					attuali.remove(it);
					break;
				}
			}
		}	
	}
	
	public void loadAnagConv() {
		
		try{
		
			// Initialize famComponentes with db data
			dialogFamComponentes = new ArrayList<CsAComponente>();
			dialogSelectedFamComponentes = new ArrayList<CsAComponente>();
			if(famConvComponentes != null) {
				dialogFamComponentes.addAll(famConvComponentes);
				isConvivente = true;
				isParente = true;
				
				if(getJsonCurrent().getFamAnaConvs().size() > 0)
					removeCompSelected(dialogFamComponentes, getJsonCurrent().getFamAnaConvs());
			}
		
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}

	
	public void loadAnagNonConv() {
		
		try{
		
			// Initialize famComponentes with db data
			dialogFamComponentes = new ArrayList<CsAComponente>();
			dialogSelectedFamComponentes = new ArrayList<CsAComponente>();
			if(famNonConvComponentes != null) {
				dialogFamComponentes.addAll(famNonConvComponentes);
				isConvivente = false;
				isParente = true;
				
				if(getJsonCurrent().getFamAnaNonConvs().size() > 0)
					removeCompSelected(dialogFamComponentes, getJsonCurrent().getFamAnaNonConvs());
			}
		
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	
	public void loadAnagAltri() {
		
		try {
		
			// Initialize famComponentes with db data
			dialogFamComponentes = new ArrayList<CsAComponente>();
			dialogSelectedFamComponentes = new ArrayList<CsAComponente>();
			if(famAltriComponentes != null) {
				dialogFamComponentes.addAll(famAltriComponentes);
				isParente = false;
				
				if(getJsonCurrent().famAnaAltris.size() > 0)
					removeCompSelected(dialogFamComponentes, getJsonCurrent().getFamAnaAltris());
			}
		
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	private void removeAnaSelected(List<AnagraficaMultidimAnzBean> attuali, List<AnagraficaMultidimAnzBean> toRemove){
		for (AnagraficaMultidimAnzBean item : toRemove) {
			for (AnagraficaMultidimAnzBean it : attuali){
				if(it.getId().equals(item.getId())) {
					attuali.remove(it);
					break;
				}
			}
		}	
	}
	
	public void removeAnaConv() {
		if(selectedfamAnaConvs.size() > 0){
			this.removeAnaSelected(getJsonCurrent().getFamAnaConvs(), selectedfamAnaConvs);
		}else{
			this.addWarning("Rimozione familiari conviventi", "Selezione non effettuata");
		}
	}
	
	public void removeAnaNonConv(){
		if(selectedfamAnaNonConvs.size() > 0){
			this.removeAnaSelected(getJsonCurrent().getFamAnaNonConvs(), selectedfamAnaNonConvs);
		}else{
			this.addWarning("Rimozione familiari non conviventi", "Selezione non effettuata");
		}
	}
	
	
	public void removeAnaAltri(){
		if(selectedfamAnaAltris.size() > 0){
			this.removeAnaSelected(getJsonCurrent().getFamAnaAltris(), selectedfamAnaAltris);
		}else{
			this.addWarning("Rimozione altri soggetti", "Selezione non effettuata");
		}
	}
	
	public void addAnaCorr(){
		if(dialogSelectedFamComponentes.size() > 0){
			ArrayList<AnagraficaMultidimAnzBean> listFamComps = new ArrayList<AnagraficaMultidimAnzBean>();
			
			for (CsAComponente item : dialogSelectedFamComponentes) {
				String disponibilita = null;
				if(item.getCsTbDisponibilita() != null)
					disponibilita = item.getCsTbDisponibilita().getDescrizione();
				listFamComps.add(new AnagraficaMultidimAnzBean(item.getCsAAnagrafica().getId(), item.getCsAAnagrafica().getCognome(), item.getCsAAnagrafica().getNome(), item.getIndirizzoRes(), item.getComResDes(), item.getCsTbTipoRapportoCon().getDescrizione(), item.getCsAAnagrafica().getTel(), disponibilita));
			}
			
			if (isParente) {
				if (isConvivente == true)
					getJsonCurrent().getFamAnaConvs().addAll(listFamComps);
				else
					getJsonCurrent().getFamAnaNonConvs().addAll(listFamComps);
			} else {
				getJsonCurrent().getFamAnaAltris().addAll(listFamComps);
			}
		}
	}
	
	// End Methods for Famiglia Anagrafica

	@Override
	public ReportPdfDTO fillReport(String reportPath, List<String> listaSubreport, HashMap<String, Object> map, List<IIseeJson> lstIsee, ISchedaBarthel barthelMan) {
			ValMultidimPdfDTO pdf = new ValMultidimPdfDTO();
	
			CsAAnagrafica ana = soggetto.getCsAAnagrafica();
			String denominazione = ana.getCognome()+" "+ana.getNome();
			pdf.setDenominazione(denominazione);
			
			//Dati Generali
			pdf.setMomValTipo(getLabelByCod(TipoParamSchedaMultidime.MOM_VAL_TIPO, this.getJsonCurrent().getMomValTipo()));
			pdf.setMomValLuogo(this.getJsonCurrent().getMomValLuogo());
			pdf.setMomValRef(this.getJsonCurrent().getMomValRef());
			pdf.setMomValTel(this.getJsonCurrent().getMomValTel());
			//Sina
			List<ValoriPdfDTO> lstSina = new ArrayList<ValoriPdfDTO>();
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			Set<Long> domsId = sinaMan.getSinaDTO().getDomandas().keySet();
			for(Long domId : domsId) {
				CsTbSinaDomanda domanda = sinaMan.getSinaDTO().getDomandas().get(domId);
				String rispostaID = sinaMan.getSinaDTO().getRispostas().get(domId);
				CsTbSinaRisposta risposta = null;
				if(!StringUtils.isBlank(rispostaID)) {
					dto.setObj(new Long(rispostaID));
					risposta = confService.findSinaRisposta(dto);
				}
				
				String ds = domanda!=null && !StringUtils.isBlank(domanda.getTesto()) ? domanda.getTesto() : "";
				String rs = risposta!=null && !StringUtils.isBlank(risposta.getTooltip()) ? risposta.getTooltip() : ""; 
				
				lstSina.add(new ValoriPdfDTO(ds,rs,null));
			}
			
			map.put("sina", new JRBeanCollectionDataSource(lstSina));
			//Prestazioni Inps
			List<ValoriPdfDTO> lstInps = new ArrayList<ValoriPdfDTO>();
			for(String s : sinaMan.getSinaDTO().getLstPrestazioniInpsScelte()){
				dto.setObj(s); 	
				ArTbPrestazioniInps ar = confService.findArTbPrestazioniInpsByCodice(dto);
				lstInps.add(new ValoriPdfDTO(ar.getCodice(),ar.getDescrizione(),null));
			}
			map.put("inps", new JRBeanCollectionDataSource(lstInps));
			List<ValoriPdfDTO> lstViveCon = new ArrayList<ValoriPdfDTO>();
			
			if(this.getJsonCurrent().reteFamAllargata)
				lstViveCon.add(new ValoriPdfDTO("Rete Familiare Allargata","[Ha una rete familiare allargata(ristretta + zii, cugini...)]",this.getJsonCurrent().getDaQuandoSituazione()));
			
			if(this.getJsonCurrent().reteFamRistretta)
				lstViveCon.add(new ValoriPdfDTO("Rete Familiare Ristretta","[Ha una rete familiare ristretta(genitori e/o fratelli, coniugi e/o figli...)]",this.getJsonCurrent().getDaQuandoSituazione()));
			
			if(this.getJsonCurrent().unFamiliare)
				lstViveCon.add(new ValoriPdfDTO("Un Familiare","[Ha un solo familiare o convivente]", this.getJsonCurrent().getDaQuandoSituazione()));
			
			if(this.getJsonCurrent().assenzaReteFam)
				lstViveCon.add(new ValoriPdfDTO("Assenza Rete Familiare","[Assenza di rete familiare]",this.getJsonCurrent().getDaQuandoSituazione()));
			
			if(this.getJsonCurrent().reteFamDisponibile)
				lstViveCon.add(new ValoriPdfDTO("Rete Familiare Disponibile","[Rete familiare molto disponibile, integrata e collaborativa]",this.getJsonCurrent().getDaQuandoSituazione()));
			
			if(this.getJsonCurrent().reteFamCollaborativa)
				lstViveCon.add(new ValoriPdfDTO("Rete Familiare Collaborativa","[Rete familiare collaborativa, ma che ha bisogno di qualche sostegno sociale]",this.getJsonCurrent().getDaQuandoSituazione()));
			
			if(this.getJsonCurrent().reteFamScarsamenteIntegr)
				lstViveCon.add(new ValoriPdfDTO("Rete Familiare Scarsamente Integrata","[Rete familiare scarsamente integrata e multiproblematica che richiede una presa in carico dei servizi]",this.getJsonCurrent().getDaQuandoSituazione()));
			
			if(this.getJsonCurrent().reteFamNonCollaborativa)
				lstViveCon.add(new ValoriPdfDTO("Rete Familiare non Collaborativa","[Rete familiare con elevata conflittualità e/o multi problematicità, non collaborativa che richiede un impegnativo coinvolgimento e la presa in carico di uno o più servizi]",this.getJsonCurrent().getDaQuandoSituazione()));
			
			
			
			//if(this.getJsonCurrent().viveAltri && this.getJsonCurrent().getSpecificare()!=null)
			//	lstViveCon.add(new ValoriPdfDTO("Con altri",this.getJsonCurrent().getSpecificare(),null));
		
			listaSubreport.add(reportPath + "/subreport/schedaMultidimensionale/listaValori.jrxml");
			map.put("conChiVive", new JRBeanCollectionDataSource(lstViveCon));

			pdf.setnFigli(this.getJsonCurrent().getnFigli());
			pdf.setnFiglie(this.getJsonCurrent().getnFiglie());
			pdf.setnSorelle(this.getJsonCurrent().getnSorelle());
			pdf.setnFratelli(this.getJsonCurrent().getnFratelli());
			
			listaSubreport.add(reportPath + "/subreport/schedaMultidimensionale/soggetti.jrxml");
			map.put("famAnaConvs", new JRBeanCollectionDataSource(this.getJsonCurrent().getFamAnaConvs()));
			map.put("famAnaNonConvs", new JRBeanCollectionDataSource(this.getJsonCurrent().getFamAnaNonConvs()));
				
			//Rete Sociale
			pdf.setRelAltriSogg(this.getJsonCurrent().getRelAltriSogg());
			pdf.setRelAltriSoggRetr(this.getJsonCurrent().getRelAltriSoggRetr());
			map.put("famAnaAltris", new JRBeanCollectionDataSource(this.getJsonCurrent().getFamAnaAltris()));
		
			//Stato di salute
			List<ValoriPdfDTO> sl = new ArrayList<ValoriPdfDTO>();
			if(this.getJsonCurrent().isPatCard())
				sl.add(getPatologia("Patologie cardiache",this.getJsonCurrent().getPatCardDesc()));
			if(this.getJsonCurrent().isPatRen())
				sl.add(getPatologia("Patologie renali",this.getJsonCurrent().getPatRenDesc()));
			if(this.getJsonCurrent().isPatAppResp())
				sl.add(getPatologia("Patologie polmonari",this.getJsonCurrent().getPatAppRespDesc()));
			if(this.getJsonCurrent().isPatAppDig())
				sl.add(getPatologia("Patologie app. digerente",this.getJsonCurrent().getPatAppDigDesc()));
			if(this.getJsonCurrent().isPatAppPsiComp())
				sl.add(getPatologia("Patologie psichiatriche",this.getJsonCurrent().getPatAppPsiCompDesc()));
			if(this.getJsonCurrent().isPatSistNer())
				sl.add(getPatologia("Patologie del sistema nervoso",this.getJsonCurrent().getPatSistNerDesc()));
			if(this.getJsonCurrent().isArtArtOst())
				sl.add(getPatologia("Patologie articolari",this.getJsonCurrent().getArtArtOstDesc()));
			if(this.getJsonCurrent().isDistVista())
				sl.add(getPatologia("Disturbi della vista",this.getJsonCurrent().getDistVistaDesc()));
			if(this.getJsonCurrent().isDistUdito())
				sl.add(getPatologia("Disturbi dell'udito",this.getJsonCurrent().getDistUditoDesc()));
			if(this.getJsonCurrent().isDistLoco())
				sl.add(getPatologia("Disturbi locomotori",this.getJsonCurrent().getDistLocoDesc()));
			if(this.getJsonCurrent().isIncontinenza())
				sl.add(getPatologia("Incontinenza",this.getJsonCurrent().getIncontinenzaDesc()));
			if(this.getJsonCurrent().isPiagheDecub())
				sl.add(getPatologia("Piaghe da decubito",this.getJsonCurrent().getPiagheDecubDesc()));	
			if(this.getJsonCurrent().isAltrePat())
				sl.add(getPatologia("Altre patologie",this.getJsonCurrent().getAltrePatDesc()));	
			if(this.getJsonCurrent().isAltreInfo())
				sl.add(getPatologia("Altre informazioni",this.getJsonCurrent().getAltreInfoDesc()));	
			map.put("patologie", new JRBeanCollectionDataSource(sl));
			
			pdf.setNoteSanitarie(this.getJsonCurrent().getNoteSanit());
		
			pdf.setValSalFisica(getLabelByCod(TipoParamSchedaMultidime.VAL_SALUTE_PERS, this.getJsonCurrent().getSalFisic()));
			pdf.setValSalPsichica(getLabelByCod(TipoParamSchedaMultidime.VAL_SALUTE_PSI, this.getJsonCurrent().getValSalutePsi()));
		
			pdf.setValAutonomiaDomestica(getLabelByCod(TipoParamSchedaMultidime.VAL_AUTO_DOM, this.getJsonCurrent().getValAutoDom()));
			pdf.setValAutonomiaExtraDom(getLabelByCod(TipoParamSchedaMultidime.VAL_AUTO_EDOM, this.getJsonCurrent().getValAutoExDom()));
			
			//Abitazione
			pdf.setTipAbit(this.getJsonCurrent().getTipAbit());
			pdf.setAscensore(this.getJsonCurrent().getAscensore());
			pdf.setPortineria(this.getJsonCurrent().getPortineria());
			pdf.setTitoloGodimento(this.getJsonCurrent().getAbGod());
			pdf.setNumVani(this.getJsonCurrent().getNumVani());
			pdf.setGestoreProprietario(this.getJsonCurrent().getProprietarioGestore());
			pdf.setRiscaldamento(this.getJsonCurrent().getRiscaldamento());
			pdf.setGabinetto(getLabelByCod(TipoParamSchedaMultidime.ABIT_BAGNO, this.getJsonCurrent().getGabinetto()));
			
			String fornitos = "";
			for(String s : this.getJsonCurrent().getFornitos()){
				String v = getLabelByCod(TipoParamSchedaMultidime.ABIT_FORNITO, s);
				fornitos+=!v.isEmpty() ? v+", " : "";
			}
			pdf.setFornitos(!fornitos.isEmpty() ? fornitos.substring(0,fornitos.lastIndexOf(',')) : "");	
			
			String eds = "";
			for(String s : this.getJsonCurrent().getElettrodoms()){
				String v = getLabelByCod(TipoParamSchedaMultidime.ABIT_ELETTRODOMESTICI, s);
				eds+=!v.isEmpty() ? v+", " : "";
			}
			pdf.setElettrodomestici(!eds.isEmpty() ? eds.substring(0,eds.lastIndexOf(',')) : "");
			
			String ape = "";
			for(String s : this.getJsonCurrent().getAltriProbEsits()){
				String v = getLabelByCod(TipoParamSchedaMultidime.ABIT_APROBLEMI, s);
				ape+=!v.isEmpty() ? v+", " : "";
			}
			pdf.setAltriProblemi(!ape.isEmpty() ? ape.substring(0,ape.lastIndexOf(',')) : "");
			
			pdf.setValAdAbitazione(getLabelByCod(TipoParamSchedaMultidime.ABIT_ADEGUATEZZA, this.getJsonCurrent().getAdAbitRating()));
			pdf.setValUbAbitazione(getLabelByCod(TipoParamSchedaMultidime.ABIT_UBICAZIONE, this.getJsonCurrent().getUbAbits()));
			
			//Situazione Economica
		
			String isees =""; 
			for(IIseeJson isee : lstIsee)
				isees+= isee.fillReportIsee()+"<br/>";
			pdf.setIsee(isees);
			
			pdf.setIseeRipa(this.getJsonCurrent().getIseeRipa());
			
			//Mappa risorse
			String[] mrs = this.getJsonCurrent().getPrestazioniDachis();
			String mr="";
			for(String s: mrs){
				String[] ss = s.split("_");
				mr+= ss[0]+":  "+ss[1]+"<br/>";
			}
			if(!mr.isEmpty())
				pdf.setMappaRisorse(mr);
			

			//Barthel
			pdf.setRenderBarthel(barthelMan!=null);
			if(pdf.isRenderBarthel())
				barthelMan.fillReport(reportPath, listaSubreport, map);
			
			
			return pdf;
	}
	
	public void fillTriage(String reportPath, TriagePdfDTO pdf, List<String> listaSubreport, HashMap<String, Object> map){
		
		//DATA SCHEDA MULTIDIM
		pdf.setDataValutazioneMultidimensionale(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(this.getJsonCurrent().getDataValutazione()));
		
		/*SINTESI PARENTI*/
        List<ValoriPdfDTO> lstViveCon = new ArrayList<ValoriPdfDTO>();
		
		if(this.getJsonCurrent().viveConiuge)
			lstViveCon.add(new ValoriPdfDTO("Con il coniuge","",null));
		if(this.getJsonCurrent().viveSolo)
			lstViveCon.add(new ValoriPdfDTO("Solo","[Vive con familiari nella propria abitazione]", this.getJsonCurrent().getDaQuandoViveSolo()));
		if(this.getJsonCurrent().viveFigli)
			lstViveCon.add(new ValoriPdfDTO("Con i figli","[Vive con i figli nella propria abitazione o presso l'abitazione dei figli]",this.getJsonCurrent().getDaQuandoViveFigli()));
		if(this.getJsonCurrent().viveFamiliari)
			lstViveCon.add(new ValoriPdfDTO("Con i familiari","[Vive con familiari o conoscenti, nella propria abitazione o presso l'abitazione di loro]",this.getJsonCurrent().getDaQuandoViveFamiliari()));
		if(this.getJsonCurrent().viveAltri && this.getJsonCurrent().getSpecificare()!=null)
			lstViveCon.add(new ValoriPdfDTO("Con altri",this.getJsonCurrent().getSpecificare(),null));
	
		listaSubreport.add(reportPath + "/listaValori.jrxml");
		map.put("conChiVive", new JRBeanCollectionDataSource(lstViveCon));

		pdf.setnFigli(this.getJsonCurrent().getnFigli());
		pdf.setnFiglie(this.getJsonCurrent().getnFiglie());
		pdf.setnSorelle(this.getJsonCurrent().getnSorelle());
		pdf.setnFratelli(this.getJsonCurrent().getnFratelli());
		
		listaSubreport.add(reportPath + "/soggetti.jrxml");
		map.put("famAnaConvs", new JRBeanCollectionDataSource(this.getJsonCurrent().getFamAnaConvs()));
		map.put("famAnaNonConvs", new JRBeanCollectionDataSource(this.getJsonCurrent().getFamAnaNonConvs()));
	
		pdf.setValFamRating(getLabelByCod(TipoParamSchedaMultidime.VAL_RETE_FAM, null));
		
		//Rete Sociale
		pdf.setRelAltriSogg(this.getJsonCurrent().getRelAltriSogg());
		pdf.setRelAltriSoggRetr(this.getJsonCurrent().getRelAltriSoggRetr());
		map.put("famAnaAltris", new JRBeanCollectionDataSource(this.getJsonCurrent().getFamAnaAltris()));
		pdf.setValReteSocRating(getLabelByCod(TipoParamSchedaMultidime.VAL_RETE_SOC, null));
		
		//Abitazione
		pdf.setTipAbit(this.getJsonCurrent().getTipAbit());
		pdf.setAscensore(this.getJsonCurrent().getAscensore());
		pdf.setPortineria(this.getJsonCurrent().getPortineria());
		pdf.setTitoloGodimento(this.getJsonCurrent().getAbGod());
		pdf.setRiscaldamento(this.getJsonCurrent().getRiscaldamento());
		pdf.setGabinetto(getLabelByCod(TipoParamSchedaMultidime.ABIT_BAGNO, this.getJsonCurrent().getGabinetto()));
		
		String fornitos = "";
		for(String s : this.getJsonCurrent().getFornitos()){
			String v = getLabelByCod(TipoParamSchedaMultidime.ABIT_FORNITO, s);
			fornitos+=!v.isEmpty() ? v+", " : "";
		}
		pdf.setFornitos(!fornitos.isEmpty() ? fornitos.substring(0,fornitos.lastIndexOf(',')) : "");	
		
		String eds = "";
		for(String s : this.getJsonCurrent().getElettrodoms()){
			String v = getLabelByCod(TipoParamSchedaMultidime.ABIT_ELETTRODOMESTICI, s);
			eds+=!v.isEmpty() ? v+", " : "";
		}
		pdf.setElettrodomestici(!eds.isEmpty() ? eds.substring(0,eds.lastIndexOf(',')) : "");
		
		String ape = "";
		for(String s : this.getJsonCurrent().getAltriProbEsits()){
			String v = getLabelByCod(TipoParamSchedaMultidime.ABIT_APROBLEMI, s);
			ape+=!v.isEmpty() ? v+", " : "";
		}
		pdf.setAltriProblemi(!ape.isEmpty() ? ape.substring(0,ape.lastIndexOf(',')) : "");
		
		pdf.setValAdAbitazione(getLabelByCod(TipoParamSchedaMultidime.ABIT_ADEGUATEZZA, this.getJsonCurrent().getAdAbitRating()));
		pdf.setValUbAbitazione(getLabelByCod(TipoParamSchedaMultidime.ABIT_UBICAZIONE, this.getJsonCurrent().getUbAbits()));
	}
	

	private ValoriPdfDTO getPatologia(String patologia, String descrizione){
		ValoriPdfDTO v = new ValoriPdfDTO(patologia,(descrizione!=null && !descrizione.isEmpty()) ? descrizione : "" ,null);
		return v;
	}

	public SinaMan getSinaMan() {
		return sinaMan;
	}

	public void setSinaMan(SinaMan sinaMan) {
		this.sinaMan = sinaMan;
	}

	public List<SelectItem> getLstItemsAbTipo() {
		return lstItemsAbTipo;
	}

	public void setLstItemsAbTipo(List<SelectItem> lstItemsAbTipo) {
		this.lstItemsAbTipo = lstItemsAbTipo;
	}

	public List<SelectItem> getLstItemsAbTitolo() {
		return lstItemsAbTitolo;
	}

	public void setLstItemsAbTitolo(List<SelectItem> lstItemsAbTitolo) {
		this.lstItemsAbTitolo = lstItemsAbTitolo;
	}

	public List<SelectItem> getLstItemsAbProprietario() {
		return lstItemsAbProprietario;
	}

	public void setLstItemsAbProprietario(List<SelectItem> lstItemsAbProprietario) {
		this.lstItemsAbProprietario = lstItemsAbProprietario;
	}

	@Override
	protected void loadCommonList() {}
	
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

}
