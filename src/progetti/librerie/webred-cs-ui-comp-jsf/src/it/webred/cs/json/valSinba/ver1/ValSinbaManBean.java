package it.webred.cs.json.valSinba.ver1;

import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SinbaDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDTO;
import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDSinba;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbTipoPai;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.valSinba.ValSinbaManBaseBean;
import it.webred.cs.json.valSinba.ValSinbaRowBean;
import it.webred.cs.json.valSinba.ver1.tabs.ComponenteFamigliaBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiAffidamentoBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiAffidamentoMan;
import it.webred.cs.json.valSinba.ver1.tabs.DatiDisabilitaBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiDisabilitaMan;
import it.webred.cs.json.valSinba.ver1.tabs.DatiFamigliaBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiFamigliaMan;
import it.webred.cs.json.valSinba.ver1.tabs.DatiGeneraliBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiGeneraliMan;
import it.webred.cs.json.valSinba.ver1.tabs.DatiSegnalazioniBean;
import it.webred.cs.json.valSinba.ver1.tabs.DatiSegnalazioniMan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

public class ValSinbaManBean extends ValSinbaManBaseBean {

	private static final long serialVersionUID = 1L;

	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat yearformat = new SimpleDateFormat("yyyy");
	private ValSinbaController controller;
//	private boolean attivaSalvataggio = true;
	private boolean disabledSave = false;
    private boolean copia = false;
    
	private List<CsCCategoriaSociale> categorieSociali;
	private List<SelectItem> lstParenti = null;
	
	// Manager dei TABS
	private DatiGeneraliMan datiGen;
	private DatiFamigliaMan datiFam;
	private DatiDisabilitaMan datiDis;
	private DatiSegnalazioniMan datiSegn;
	private DatiAffidamentoMan datiAff;
	
	public ValSinbaManBean() {
		controller = new ValSinbaController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());
		
		// Inizializzazione dei manager dei tabs
		datiGen = new DatiGeneraliMan();
		datiFam = new DatiFamigliaMan();
		datiDis = new DatiDisabilitaMan();
		datiSegn = new DatiSegnalazioniMan();
		datiAff = new DatiAffidamentoMan();
	    
	}
	
	//SISO-777 l'inizializzazione viene eseguita rispetto alla data di riferimento
	private void initPrestazioniBeneficiario(String dataIniRiferimento, String dataFineRiferimento){
		logger.debug("SinaManBean.init()");
		String cf = null;
		cf = this.soggetto.getCsAAnagrafica().getCf();
	
		List<String> codiciPrestazioni = loadCodiciPrestazione(cf,dataIniRiferimento, dataFineRiferimento);
		this.getJsonCurrent().getDatiGenerali().setPrestazioniSel(codiciPrestazioni);
		
		valorizzaCodiceBeneficiario();
	}
	
	private void valorizzaCodiceBeneficiario(){
		if ("".equals(this.getJsonCurrent().getDatiGenerali().getCodiceAnonimoBeneficiario())
				|| this.getJsonCurrent().getDatiGenerali().getCodiceAnonimoBeneficiario() == null) {
			this.getJsonCurrent().getDatiGenerali().setCodiceAnonimoBeneficiario(this.createCodiceBeneficiario());
		}
	}
		
	public void initCalendar(SelectEvent event ){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateSel = (Date)event.getObject();
		Calendar c = Calendar.getInstance();//giorno attuale
		c.setTime(dateSel);
		c.add(Calendar.YEAR, -1); //Data di riferimento - 1 anno
	   			
		this.initPrestazioniBeneficiario(formatter.format(c.getTime()),formatter.format(dateSel));
		
		//SISO-981 Inizio
		this.initAffidoPAI(soggetto, c.getTime());
		//SISO-981 Fine
	}
	//SISO-777 inizializzazione del calendario in quanto le prestazioni devono essere sempre allineate alla data che appare nella scheda
	public void initCalendar(AjaxBehaviorEvent event){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateSel = (Date)((javax.faces.component.UIInput)event.getComponent()).getValue();
		Calendar c = Calendar.getInstance();//giorno attuale
		c.setTime(dateSel);
		
		//SISO-981 Inizio		
		this.initAffidoPAI(soggetto, c.getTime());
		//SISO-981 Fine

		c.add(Calendar.YEAR, -1); //Data di riferimento - 1 anno
	   	
		this.initPrestazioniBeneficiario(formatter.format(c.getTime()),formatter.format(dateSel));
	}
	
	private List<String> loadCodiciPrestazione(String cf, String dtIniRiferimento, String dtFineRiferimento){
		String tipo = "E";
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(cf);
		bdto.setObj2(dtIniRiferimento);
		bdto.setObj3(dtFineRiferimento);
		bdto.setObj4(tipo);
		
		List<String> codiciPrestazioni = sinbaService.findCodiciPrestazione(bdto);
		return codiciPrestazioni;
	}
	
	 //SISO-777  lista prestazioni codificate da visualizzare all'interno della scheda Sinba
	public List<ArTbPrestazioniInps> lstCodificaPrestazioniSel (){
		List<String> lstCodificaPrestazioni = new ArrayList<String>();
		List<ArTbPrestazioniInps> lstPrestazioni = new ArrayList<ArTbPrestazioniInps>();
		
		List<String> lstPrestSel = this.getJsonCurrent().getDatiGenerali().getPrestazioniSel();
				
		BaseDTO denomPrestazioneDto = new BaseDTO();
		fillEnte(denomPrestazioneDto);		
				
		for (String prestSelezionata : lstPrestSel){
			denomPrestazioneDto.setObj(prestSelezionata); 	
			
			ArTbPrestazioniInps arTbPrestazioniInps = this.confService.findArTbPrestazioniInpsByCodice(denomPrestazioneDto);
			
			/*lstCodificaPrestazioni.add(arTbPrestazioniInps.getCodice() + " - " 
			                           + (arTbPrestazioniInps.getDescrizione() != null ? arTbPrestazioniInps.getDescrizione() : "Nessuna descrizione"));*/
			lstPrestazioni.add(arTbPrestazioniInps);
		}
		
		
		//return lstCodificaPrestazioni;
		return lstPrestazioni;
	}
	
	/**
	 * Crea codice beneficiario nel caso in cui minoreCurrent sia null 
	 * @return
	 */
	public String createCodiceBeneficiarioFromCasoId(String belfiore){	
		if ( soggetto != null ){
			long casoId = soggetto.getCsACaso().getId();
			
			if (!StringUtils.isBlank(belfiore) && casoId != 0l) {
				return belfiore + casoId;
			}
		}
		
		return "";
	}

	public String createCodiceBeneficiario() {
		String belfiore = getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getCodCatastale();
		if (soggetto != null) {
			long casoId = soggetto.getCsACaso().getId();

			if (!StringUtils.isBlank(belfiore) && casoId != 0l) {
				return belfiore + casoId;
			}
		}
			
		return createCodiceBeneficiarioFromCasoId(belfiore);
	}

	public ValSinbaBean getJsonCurrent() {

		return controller.getJsonCurrent();
	}
	
	/*Inizializza scheda preesistente - in modifica*/
	@Override
	public void init(CsDSinba scheda) {
		try {
			Long idCaso = scheda.getCsDDiario().getCsACaso()!=null ? scheda.getCsDDiario().getCsACaso().getId() : null;
			controller.loadData(scheda);
			valorizzaDatiBase(idCaso, scheda.getCsDDiario().getSchedaId());
			
/*	Se lo faccio qui, ogni volta che inizializzo in visuazlizzazione una scheda, ne sovrascrivo le prestazioni, senza comunicarlo all'utente
 * la verifica successiva sarebbe inutile!!!
 * 
 * 	 		Date dtRiferimento = getJsonCurrent().getDatiGenerali().getDataRiferimentoValutazione();
			Calendar c = Calendar.getInstance();//giorno attuale
			c.setTime(dtRiferimento);
			c.add(Calendar.YEAR, -1); //Data di riferimento - 1 anno
	
			initPrestazioniBeneficiario(formatter.format(c.getTime()),formatter.format(dtRiferimento)); */
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	

	//SISO-981 Inizio
	private void initAffidoPAI(CsASoggettoLAZY soggetto, Date dataRiferimento){
	 
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			 
		try {
			
			dto.setObj("AFFIDAMENTO FAMILIARE");
			Long idProgAffido = confService.findIdProgettoPaiByDesc(dto);
			
			dto.setObj(soggetto.getCsACaso().getId());
			List<CsDPai> lPai = diarioService.findPaiByCaso(dto);
			
			//<Teoricamente può essere attivo un solo progetto affido per volta.
//			Data sinba ≥ data inizio pai
//			And data sinba ≤ nvl(data fine pai,'31-12-9999')
			// Per la nuova scheda si confronta di default la data corrente
			Date today = new Date();
			boolean found = false; 
		 	for(CsDPai pai : lPai){
		 		valUltimModifica(pai.getCsDDiario());
		 		if(pai.getCsTbTipoPai().getId() == (idProgAffido) && 
		 				(pai.getCsDDiario().getDtAttivazioneDa() != null && ( dataRiferimento == null ? today : dataRiferimento) .compareTo(pai.getCsDDiario().getDtAttivazioneDa()) >=0 )&&
		 				(pai.getCsDDiario().getDtAttivazioneA() == null ||  ( dataRiferimento == null ? today : dataRiferimento) .compareTo(pai.getCsDDiario().getDtAttivazioneA()) >=0 )&&
		 				
		 				(pai.getDataChiusuraPrevista() == null || 
		 					pai.getDataChiusuraPrevista().compareTo(getDtModifica())  <= 0)){
		 			dto = new BaseDTO();
		 			fillEnte(dto);
		 			dto.setObj(pai.getDiarioId());
		 			CsPaiAffidoDTO paiAffido =  paiService.findAffidoByDiarioPaiId(dto);
		 			if(paiAffido != null){
		 				valorizzaDatiAffido(pai,paiAffido, pai.getCsDDiario().getDtAttivazioneDa(),pai.getCsDDiario().getDtAttivazioneA(), false );
		 				found = true;
		 			}
		 		}
		 	}
		 	if(!found){
		 		//Non ha trovato nulla devo aggiornare i vecchi dati.
		 		valorizzaDatiAffido(null,null, null,null, true );
	 			
		 	}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	//SISO-981 Fine
	
	public void init(CsASoggettoLAZY soggetto){
		setSoggettoFascicolo(soggetto);
		valorizzaDatiBase(soggetto.getCsACaso().getId(), null);
		
		//SISO-981 Inizio
	     initAffidoPAI(soggetto, null);
		//SISO-981 Fine
	}
	
	/*Inizializza il Json compiando da altro*/
	@Override
	public void init(ISchedaValutazione bean){
		try{
			setCopia(false);
			if(this.getVersionLowerCase().equals(bean.getVersionLowerCase())){
				controller.load((ValSinbaBean)bean.getSelected());
			}else
				copyDataBetweenVersions(bean);
			
			valorizzaDatiBase(bean.getIdCaso(), bean.getIdSchedaUdc());	
			
		/*	Date dtRiferimento = getJsonCurrent().getDatiGenerali().getDataRiferimentoValutazione();
			Calendar c = Calendar.getInstance();//giorno attuale
			c.setTime(dtRiferimento);
			c.add(Calendar.YEAR, -1); //Data di riferimento - 1 anno
	
			initPrestazioniBeneficiario(formatter.format(c.getTime()),formatter.format(dtRiferimento));*/
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	/*Copia solo il contenuto del JSON, resettando i data e prestazioni - NUOVA DA COPIA*/
	@Override
	public void initCopia(ISchedaValutazione bean){
		try{
			if(this.getVersionLowerCase().equals(bean.getVersionLowerCase())){
				controller.load((ValSinbaBean)bean.getSelected());
			}else
				copyDataBetweenVersions(bean);
			
			getJsonCurrent().getDatiGenerali().setDataRiferimentoValutazione(null);
			valorizzaDatiBase(bean.getIdCaso(), bean.getIdSchedaUdc());	
			
			setCopia(true);
			/*Calendar c = Calendar.getInstance();//giorno attuale
			c.add(Calendar.YEAR, -1);
			
			initPrestazioniBeneficiario(formatter.format(c.getTime()),formatter.format(new Date()));*/
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public void copyDataBetweenVersions(ISchedaValutazione bean){
		try{
			controller.copyDataBetweenVersions(bean.getSelected());
		} catch (Exception e) {
			//addErrorFromProperties("caricamento.error");
			logger.error("Errore trasferimento dati ValSinbaMan - versioni diverse"+e.getMessage(),e);
		}
	}
	
	@Override
	public void init(CsDValutazione parent, CsDValutazione scheda) {
		try {
			controller.loadData(parent, scheda);
			CsDDiario diario = scheda.getCsDDiario();
			Long idCaso = diario.getCsACaso() != null ? diario.getCsACaso().getId() : null;
			valorizzaDatiBase(idCaso, diario.getSchedaId());
			//this.getJsonCurrent().setDescrizioneScheda(scheda.getDescrizioneScheda());
			this.getJsonCurrent().setDataValutazione(scheda.getCsDDiario().getDtAmministrativa());
			valUltimModifica(parent.getCsDDiario());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void initRowList(CsDSinba scheda) {
		try {
			controller.loadData(scheda);
			CsDDiario diario = scheda.getCsDDiario();
			Long idCaso = diario.getCsACaso() != null ? diario.getCsACaso().getId() : null;
			valorizzaDatiBase(idCaso, diario.getSchedaId());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	private void valorizzaDatiBase(Long idCaso, Long idSchedaUdc) {
		setIdCaso(idCaso);
		setIdSchedaUdc(idSchedaUdc);
		this.datiGen.valorizzaComponenteMan(this.getJsonCurrent().getDatiGenerali());
		this.datiFam.valorizzaComponenteMan(this.getJsonCurrent().getDatiFamiglia());
		this.datiDis.valorizzaComponenteMan(this.getJsonCurrent().getDatiDisabilita());
		this.datiSegn.valorizzaComponenteMan(this.getJsonCurrent().getDatiSegnalazioni());
		this.datiAff.valorizzaComponenteMan(this.getJsonCurrent().getDatiAffidamento());
		
		
		//valorizzaCodiceBeneficiario();
		Date dtRiferimento = getJsonCurrent().getDatiGenerali().getDataRiferimentoValutazione();
		if(dtRiferimento==null){
			dtRiferimento = new Date();
			getJsonCurrent().getDatiGenerali().setDataRiferimentoValutazione(dtRiferimento);
		}
		
		Calendar c = Calendar.getInstance();//giorno attuale
		c.setTime(dtRiferimento);
		c.add(Calendar.YEAR, -1); //Data di riferimento - 1 anno
		
		if(this.isNew()){
			initPrestazioniBeneficiario(formatter.format(c.getTime()),formatter.format(dtRiferimento));
		    this.getJsonCurrent().getDatiGenerali().setAnnoNascita(yearformat.format(soggetto.getCsAAnagrafica().getDataNascita()));
		}
	}
	
	//SISO-981 Inizio
	 
	private void valorizzaDatiAffido(CsDPai progettoPai,CsPaiAffidoDTO csPaiAffido, Date dataAperturaProgetto, Date dataChiusuraProgetto, boolean reset) {
		DatiAffidamentoBean datiAffidamentoBean = new DatiAffidamentoBean();
		DatiSegnalazioniBean datiSegnalazioniBean = new DatiSegnalazioniBean();
		
		
		if(!reset){
		
		String messaggioDaPai = "";
		if(csPaiAffido.getCodiceCollocamento()  != null){
			Integer tipoIntervento = dominiService.findSINBADominioByCodice(csPaiAffido.getCodiceCollocamento());
			if(tipoIntervento != -1)
				datiAffidamentoBean.setTipoIntervento(tipoIntervento);
		}
		if(csPaiAffido.getDataDecreto()  != null){
			datiSegnalazioniBean.setDataProvvedimento(csPaiAffido.getDataDecreto());
		}
		
		if(csPaiAffido.getCodiceNaturaAccoglienza()   != null){
			Integer formaIntervento = dominiService.findSINBADominioByCodice(csPaiAffido.getCodiceNaturaAccoglienza());
			if(formaIntervento != -1)
			
			datiAffidamentoBean.setFormaIntervento(formaIntervento);
		}
		 	
		if(csPaiAffido.getCodiceAutoritaProvvedimento()   != null){
			Integer codiceAutoritaProvvedimento = dominiService.findSINBADominioByCodice(csPaiAffido.getCodiceAutoritaProvvedimento());
			if(codiceAutoritaProvvedimento != -1)
				datiSegnalazioniBean.setAutoritaProvvedimento(codiceAutoritaProvvedimento);
			
		}
		if(csPaiAffido.getCodiceEntitaAffido()   != null){
			String carattereAffido = csPaiAffido.getCodiceEntitaAffido(); 
			Integer codiceEntitaAffido = null;
			if(carattereAffido != null && carattereAffido.contains("RESIDENZIALE")){
				codiceEntitaAffido = 1;
			}else
				codiceEntitaAffido = 2;
			datiAffidamentoBean.setCarattere(codiceEntitaAffido);
			
			Integer codiceDurataAffido = dominiService.findSINBADominioByCodice(csPaiAffido.getCodiceEntitaAffido());
			if(codiceDurataAffido != -1)
				datiAffidamentoBean.setDurata(codiceDurataAffido);
		}
//		if(csPaiAffido.getCodiceEsitoAffido()   != null){
//			Integer codiceEsitoAffido = dominiService.findSINBADominioByCodice(csPaiAffido.getCodiceEsitoAffido() );
//			if(codiceEsitoAffido != -1)
//				datiAffidamentoBean.setEsito(codiceEsitoAffido);
//				
//		}
		if (progettoPai.getMotivoChiusura() != null){
			String codiceEsitoAffido = leggiCodiceEsito(progettoPai.getMotivoChiusura());
			if (codiceEsitoAffido != null && !codiceEsitoAffido.isEmpty())
			 datiAffidamentoBean.setEsito(Integer.parseInt(codiceEsitoAffido));
		}
			
			
		if(csPaiAffido.getCodiceInserimentoResidenziale()   != null){
			Integer codiceCarattInsResidenziale = dominiService.findSINBADominioByCodice(csPaiAffido.getCodiceInserimentoResidenziale() );
			if(codiceCarattInsResidenziale != -1)
				datiAffidamentoBean.setCarattereInserimento(codiceCarattInsResidenziale); 
				
		}
		 
		if(csPaiAffido.getCodiceConvivenzaOrigineAffidataria()   != null){
			Integer codiceConvivenzaOrigineAffidataria = dominiService.findSINBADominioByCodice(csPaiAffido.getCodiceConvivenzaOrigineAffidataria() );
			if(codiceConvivenzaOrigineAffidataria != -1)
				datiAffidamentoBean.setTipoInserimento(codiceConvivenzaOrigineAffidataria);
				
		}
			datiAffidamentoBean.setDatiDaProgettoPAI(true);
		
			messaggioDaPai ="Dati riguardanti l'affidamento caricati dal progetto PAI di tipo AFFIDO aperto in data " + new SimpleDateFormat("dd-MM-yyyy").format(dataAperturaProgetto)+ (dataChiusuraProgetto != null ?  "e chiuso il " + new SimpleDateFormat("dd-MM-yyyy").format(dataChiusuraProgetto) : " e non ancora chiuso");
			datiAffidamentoBean.setMessaggioDaPai(messaggioDaPai);
		
			
		}
		this.getJsonCurrent().setDatiAffidamento(datiAffidamentoBean);
		
		this.getJsonCurrent().getDatiSegnalazioni().setAutoritaProvvedimento(datiSegnalazioniBean.getAutoritaProvvedimento());		
		this.getJsonCurrent().getDatiSegnalazioni().setDataProvvedimento(datiSegnalazioniBean.getDataProvvedimento());		

 
	}
	//SISO-981 Fine
	
	@Override
	public JsonBaseBean getSelected() {
		return getJsonCurrent();
	}
	
/*	public boolean getAttivaSalvataggio() {
		return this.attivaSalvataggio;
	}*/

	@Override
	public boolean elimina() {
		boolean ok = false;
		try {
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
	public boolean isCopia() {
		return copia;
	}

	public void setCopia(boolean copia) {
		this.copia = copia;
	}
	
	@Override
	public Long getIdSchedaUdc() {
		return controller.getUdcId();
	}

	public ValSinbaController getController() {
		return this.controller;
	}
	
	@Override
	public DatiGeneraliMan getDatiGen() {
		return datiGen;
	}

	@Override
	public DatiFamigliaMan getDatiFam() {
		return datiFam;
	}

	@Override
	public DatiDisabilitaMan getDatiDis() {
		return datiDis;
	}

	@Override
	public DatiSegnalazioniMan getDatiSegn() {
		return datiSegn;
	}

	public void test() {
		logger.debug("Save SimbaManBean invocato");
	}
	
	@Override
	public boolean save(Long visSecondoLivello){
		this.controller.setVisSecondoLivello(visSecondoLivello);
		return this.save();
	}

	@Override
	public boolean save() {
		boolean ok = false;
		try {
			if (validaData()) {
				
				controller.save(this.getClass().getName());
				ok = true;
			
				setDisabledSave(true);

				// ora salva
				addInfoFromProperties("salva.ok");
				RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			}
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
		return ok;
	}
	
	//SISO-777aggiornamento dei cod prestazioni quando si è in modifica
	@Override
	public boolean allineaCodiciPrestazione() {
		boolean ok = false;
		try {
			
			//if(!controller.getDataModelSinba().getDaInserire())
			this.getJsonCurrent().getDatiGenerali().setPrestazioniSel(codiciPrestazioniDb);
			ok = this.save();
			
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
		return ok;
	}
	
/*	public boolean preparaDuplica() {
		boolean ok = false;
		try {

			controller.getDataModelSinba().getCsDDiario().setId(0);
			controller.getDataModelSinba().getCsDDiario().getCsDClob().setId(0);
			controller.setCasoId(MinoreCurrent.getIdCaso());

			controller.save(this.getClass().getName());

			CsDSinbaDTO sDTO = new CsDSinbaDTO();
			fillEnte(sDTO);
			
			Package pack = this.getClass().getPackage();
			String packageName = pack.getName();
			sDTO.setDiarioId(controller.getDiarioId());
			sDTO.setDescrizioneScheda("SCHEDA MULTIDIMENSIONALE MINORE");
			sDTO.setVersioneScheda(packageName);
			// sto inserendo un sinba da zero... quindi CREO direttamente un
			// CsDSinbaDTO

			// sDTO.fromModel(sinbaModel);
			sinbaService.salvaSchedaSinba(sDTO);

			ok = true;

			setDisabledSave(true);

			// this.prescSpec.salvaDocumento(controller.getDataModel().getCsDDiario());

			// TODO: controller --> new
			resetSchedaSinba();
			// ora salva
			addInfoFromProperties("salva.ok");

		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
		return ok;
	}
*/
/*	public void resetSchedaSinba() {

		restore();
		controller = new ValSinbaController();
		controller.setUser(getUser());
		controller.setOperatoreSettore(getCurrentOpSettore());

	}*/

	public void onCloseDialog() {

		// TODO eventualmente resetta i campi

		setDisabledSave(false);

	}



	@Override
	public boolean validaData() {
		boolean validazioneOk = true;
		List<String> messagges;
		messagges = controller.validaData();
		if (messagges.size() > 0) {
			addWarning("Scheda Sinba - compilare tutti i campi obbligatori:", messagges);
			validazioneOk &= false;
		}

		RequestContext.getCurrentInstance().addCallbackParam("canClose", validazioneOk);

		return validazioneOk;
	}

	@Override
	public void restore() {
		controller.restore();
	}
	
	@Override
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto) {
		this.soggetto = soggetto;
		
		List<CsAComponente> listaComponenti = CsUiCompBaseBean.caricaParenti(soggetto.getAnagraficaId(), null);
		
		this.datiGen.init(soggetto.getAnagraficaId(),listaComponenti);
		this.datiFam.init(soggetto.getAnagraficaId(),listaComponenti);
		this.datiDis.init(soggetto.getAnagraficaId(),listaComponenti);
		this.datiSegn.init(soggetto.getAnagraficaId(),listaComponenti);
		this.datiAff.init(soggetto.getAnagraficaId(),listaComponenti);
		// setIdCaso(soggetto.getCsACaso().getId());
		loadCommonList();
	}

	public List<SelectItem> getLstParenti() {
		return lstParenti;
	}

	public void setLstParenti(List<SelectItem> lstParenti) {
		this.lstParenti = lstParenti;
	}

	public void setDatiGen(DatiGeneraliMan datiGen) {
		this.datiGen = datiGen;
	}

	public void setDatiFam(DatiFamigliaMan datiFam) {
		this.datiFam = datiFam;
	}

	public void setDatiDis(DatiDisabilitaMan datiDis) {
		this.datiDis = datiDis;
	}

	public void setDatiSegn(DatiSegnalazioniMan datiSegn) {
		this.datiSegn = datiSegn;
	}

	public void setDatiAff(DatiAffidamentoMan datiAff) {
		this.datiAff = datiAff;
	}

	public DatiAffidamentoMan getDatiAff() {
		return datiAff;
	}

	public void initCodiceBeneficiario() {
		if ("".equals(this.getJsonCurrent().getDatiGenerali().getCodiceAnonimoBeneficiario()) || 
			this.getJsonCurrent().getDatiGenerali().getCodiceAnonimoBeneficiario() == null) {
			this.getJsonCurrent().getDatiGenerali().setCodiceAnonimoBeneficiario(this.createCodiceBeneficiario());
		}

	}

	public boolean isDisabledSave() {
		return disabledSave;
	}

	public void setDisabledSave(boolean disabledSave) {
		this.disabledSave = disabledSave;
	}

	public List<CsCCategoriaSociale> getCategorieSociali() {
		return categorieSociali;
	}

	public void setCategorieSociali(List<CsCCategoriaSociale> categorieSociali) {
		this.categorieSociali = categorieSociali;
	}


	
	public Boolean isDataModificabile() {
		return this.isNew(); //(this.copiaMode || this.saveNewSinba);
	}

	@Override
	public void valorizzaRowList(ValSinbaRowBean row) {
		//Al momento non vengono prelevati dati dal JSON
		
	}

	@Override
	protected void loadCommonList() {
		setDisabledSave(false);
		BaseDTO bo = new BaseDTO();
		fillEnte(bo);
		bo.setObj(this.soggetto.getAnagraficaId());
		bo.setObj3(true);

		if (this.lstParenti == null) {
			this.lstParenti = new ArrayList<SelectItem>();
			List<CsAComponente> lstComponenti = CsUiCompBaseBean.caricaParenti(soggetto.getAnagraficaId(), null);
			for (CsAComponente item : lstComponenti) {
				if (item.getCsTbTipoRapportoCon() != null && item.getCsTbTipoRapportoCon().getParente())
					this.lstParenti.add(new SelectItem(item.getId(), item.getCsTbTipoRapportoCon().getDescrizione()));
			}
		}
	}

	@Override
	public SinbaDTO fillExportDTO() {
	
			SinbaDTO sinbaDTO = new SinbaDTO();
			
			DatiGeneraliBean generali = getJsonCurrent().getDatiGenerali();
			DatiFamigliaBean famiglia = getJsonCurrent().getDatiFamiglia();
			DatiAffidamentoBean affidamento = getJsonCurrent().getDatiAffidamento();
			DatiSegnalazioniBean segnalazioni = getJsonCurrent().getDatiSegnalazioni();
			DatiDisabilitaBean disabilita = getJsonCurrent().getDatiDisabilita();
			try {		
				// TODO:popolare il sinbaDTO
				//verificare se effettivamente recuperate tutte queste join...);
				sinbaDTO.setAnnoNascita(String.valueOf(generali.getAnnoNascita()));
				sinbaDTO.setAutoritaProvvedimentoGiudiziario(String.valueOf(segnalazioni.getAutoritaProvvedimento()));
				sinbaDTO.setCarattereInserimento(String.valueOf(affidamento.getCarattereInserimento()));
				sinbaDTO.setCarattereIntervento(String.valueOf(affidamento.getCarattere()));
				if (famiglia.getLstComponentiFamiglia().size() > 0)
				{
					for (ComponenteFamigliaBean c : famiglia.getLstComponentiFamiglia())
					{
						if (c.getTipoID() == 1) // Madre
						{
							sinbaDTO.setCittadinanzaMadre(c.getCittadinanzaID());
							sinbaDTO.setOccupazioneMadre(String.valueOf(c.getOccupazioneID()));
							sinbaDTO.setTitoloStudioMadre(String.valueOf(c.getTitoloStudioID()));
							sinbaDTO.setRegioneResidenzaMadre(String.valueOf(c.getRegioneID()));
						}
						if (c.getTipoID() == 2) // Padre
						{
							sinbaDTO.setCittadinanzaPadre(c.getCittadinanzaID());
							sinbaDTO.setOccupazionePadre(String.valueOf(c.getOccupazioneID()));
							sinbaDTO.setTitoloStudioPadre(String.valueOf(c.getTitoloStudioID()));
							sinbaDTO.setRegioneResidenzaPadre(String.valueOf(c.getRegioneID()));
						}
						sinbaDTO.getComponentiFamigliaSel().add(String.valueOf(c.getTipoID()));
					}
				}
				sinbaDTO.setCodiceAnonimoBeneficiario(generali.getCodiceAnonimoBeneficiario());
				if (generali.getPrestazioniSel().size() > 0)
				{
					for (String c : generali.getPrestazioniSel())
					{
						sinbaDTO.getPrestazioniSel().add(c);
					}
				}
				sinbaDTO.setCodNazioneResidenza(String.format("%03d", Integer.parseInt(generali.getNazioneResidenzaBeneficiario())));
				sinbaDTO.setCodNazioneResidenzaFam(String.format("%03d", Integer.parseInt(famiglia.getNazioneResidenza())));
				sinbaDTO.setCodRegioneResidenzaFam(String.valueOf(famiglia.getRegioneFam()));
				sinbaDTO.setCollaborazioneInterventi(String.valueOf(affidamento.getCollaborazioneInterventi()));
				sinbaDTO.setCondizioneLavoro(String.valueOf(generali.getCondizioneLavoro()));
				sinbaDTO.setCondizioneMinore(String.valueOf(famiglia.getCondizioneMinore()));
				DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
				DateFormat df2 = new SimpleDateFormat("MM/yyyy");
				if (segnalazioni.getDataProvvedimento() != null)
					sinbaDTO.setDataProvvedimentoGiudiziario(df2.format(segnalazioni.getDataProvvedimento()));
				if (segnalazioni.getDataSegnalazione() != null)
					sinbaDTO.setDataSegnalazione(df2.format(segnalazioni.getDataSegnalazione()));
				if (generali.getDataRiferimentoValutazione() != null)
					sinbaDTO.setDataValutazione(df1.format(generali.getDataRiferimentoValutazione()));
				sinbaDTO.setDisabile(String.valueOf(disabilita.getDisabile()));
				sinbaDTO.setDurataIntervento(String.valueOf(affidamento.getDurata()));
				sinbaDTO.setEsitoInserimentoStruttura(String.valueOf(affidamento.getEsitoInserimentoStruttura()));
				sinbaDTO.setEsitoIntervento(String.valueOf(affidamento.getEsito()));
				sinbaDTO.setFasciaEtaBeneficiario(String.valueOf(generali.getFasciaEtaBeneficiario()));
				sinbaDTO.setFasciaISEEBenefeciario(String.valueOf(generali.getFasciaISEEBeneficiario()));
				sinbaDTO.setFonteSegnalazione(String.valueOf(segnalazioni.getFonte()));
				sinbaDTO.setFormaInserimento(String.valueOf(affidamento.getFormaInserimento()));
				sinbaDTO.setFormaIntervento(String.valueOf(affidamento.getFormaIntervento()));
				sinbaDTO.setLuogoVita(String.valueOf(famiglia.getLuogoVita()));
				sinbaDTO.setMinoreStranieroAccompagnato(String.valueOf(famiglia.getMinoreStranieroAccompagnato()));
				sinbaDTO.setMotivazioneChiusuraCarico(String.valueOf(affidamento.getMotivazioneChiusuraCarico()));
				sinbaDTO.setNazioneResidenzaBeneficiario(String.valueOf(generali.getNazioneResidenzaBeneficiario()));
				sinbaDTO.setNumeroCompIsee(String.valueOf(generali.getNumeroComponentiISEE()));
				sinbaDTO.setPotestaTutela(String.valueOf(segnalazioni.getPotestaTutela()));
				sinbaDTO.setProvvedimentoGiudiziario(String.valueOf(segnalazioni.getProvvedimentoGiudiziario()));
				sinbaDTO.setScuolaFrequentata(String.valueOf(generali.getScuolaFrequentata()));
				sinbaDTO.setSegnalazioneAutoritaGiudiziaria(String.valueOf(segnalazioni.getAutoritaGiudiziaria()));
				sinbaDTO.setSituazioneChiusuraCarico(String.valueOf(affidamento.getSituazioneChiusuraCarico()));
				sinbaDTO.setTipoDisabilita(String.valueOf(disabilita.getTipoDisabilita()!=0 ? disabilita.getTipoDisabilita() : null));	
				sinbaDTO.setTipoIntervento(String.valueOf(affidamento.getTipoIntervento()));
				sinbaDTO.setTipoProvvedimento(String.valueOf(segnalazioni.getTipoProvvedimento()));
				sinbaDTO.setValutazioneFamigliaMinore(String.valueOf(segnalazioni.getValutazioneFamiglia()));
				sinbaDTO.setValutazioneMinore(String.valueOf(segnalazioni.getValutazioneMinore()));
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);;
			} 
			return sinbaDTO;

	}
	
	@Override
	public ReportPdfDTO fillReport(String reportPath,
			List<String> listaSubreport, HashMap<String, Object> map,
			CsDSinba bean) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Boolean codiciPrestazioneDaAggiornare(boolean compareSize){
    	Boolean daAggiornare=false;
    	String cfBeneficiario = "";
    	 
		Date dtFineRiferimento = this.getJsonCurrent().getDatiGenerali().getDataRiferimentoValutazione();
		Calendar dtIniRiferimento = new GregorianCalendar();
	    dtIniRiferimento = Calendar.getInstance();//giorno attuale
		dtIniRiferimento.setTime(dtFineRiferimento);
		dtIniRiferimento.add(Calendar.YEAR, -1); //Data di riferimento - 1 anno*/
		
	   cfBeneficiario = this.soggetto.getCsAAnagrafica().getCf();
	   codiciPrestazioniJson = this.getJsonCurrent().getDatiGenerali().getPrestazioniSel();
       codiciPrestazioniDb = leggiCodiciPrestazione(cfBeneficiario,formatter.format(dtIniRiferimento.getTime()), 
    		   													   formatter.format(dtFineRiferimento.getTime()), "E");
       
       
       if(compareSize){
	       //SISO-777
	       //se i cod prestazioni salvati su Json differiscono da quelli salvati su Db devono essere aggiornati(
	       daAggiornare = (codiciPrestazioniJson.size() != codiciPrestazioniDb.size()) || codiciPrestazioniJson.isEmpty() ;
       
       }else{
       
	       //se i cod prestazioni salvati su Json differiscono da quelli salvati su Db devono essere aggiornati
	       //Il confronto viene fatto sulle occorrenze e non sul loro numero
	       Collections.sort(codiciPrestazioniJson);
	       Collections.sort(codiciPrestazioniDb);
	       
	       daAggiornare = (!codiciPrestazioniJson.equals(codiciPrestazioniDb)) || codiciPrestazioniJson.isEmpty();
       }
    	return daAggiornare;
	}
	
}
