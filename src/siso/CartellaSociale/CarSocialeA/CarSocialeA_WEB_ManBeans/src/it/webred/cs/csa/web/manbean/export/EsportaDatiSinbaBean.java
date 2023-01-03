
package it.webred.cs.csa.web.manbean.export;

import it.webred.cs.csa.ejb.client.AccessTableExportValutazioniSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.csa.ejb.dto.EsportazioneDTOView;
import it.webred.cs.csa.ejb.dto.InfoExportSinbaDTO;
import it.webred.cs.csa.ejb.dto.SinbaDTO;
import it.webred.cs.csa.ejb.dto.SinbaMinoriSearchResultDTO;
import it.webred.cs.csa.ejb.dto.SinbaSearchCriteria;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsDExportSinba;
import it.webred.cs.data.model.CsDSinba;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.valSinba.IValSinba;
import it.webred.cs.json.valSinba.ValSinbaManBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.osmosit.siso.flussoinps.logic.Cost;

@ManagedBean
@ViewScoped
public class EsportaDatiSinbaBean extends CsUiCompBaseBean {
	public static Logger logger = Logger.getLogger("carsociale.log");
	
	public IValSinba sinbaManBean;
	
	protected static final DecimalFormat df;
	
	static { // SISO-606
		df = new DecimalFormat("#0.00");
		DecimalFormatSymbols custom = new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(custom);
	}
	
	private CsOOperatoreSettore opSettore;
	private Boolean showDlgEsporta = true;

	private Boolean showPnlEsporta;
	private Boolean showPnlVisualizza;
	private Boolean inCopy;
	
	private Date dataDA;
	private Date dataA;
	private int numErogDaEsportare = 0;
	File tempFile;

	// SISO-777 filtri ricerca
	private String cf;
	private String nome;
	private String cognome;
	// private String prestazione;
	// private String areaTarget;
	// private String numeroProtocollo;
	private String statoEsportazione;
	private List<CsDSinba> SchedeSinbaDaEsportareList = new ArrayList<CsDSinba>();
	
	private InfoExportSinbaDTO schedaSinbaMinoreCurrent;
	private SinbaMinoriSearchResultDTO minoreExportCurrent;
	
	// cambiato con il sinba TASK SISO-777
	protected AccessTableExportValutazioniSinbaSessionBeanRemote sinbaExportService = 
			(AccessTableExportValutazioniSinbaSessionBeanRemote) getCarSocialeEjb("AccessTableExportValutazioniSinbaSessionBean");
	
	protected AccessTableSinbaSessionBeanRemote sinbaService = 
			(AccessTableSinbaSessionBeanRemote) getCarSocialeEjb("AccessTableSinbaSessionBean");
	
	protected AccessTableCatSocialeSessionBeanRemote catSocService = 
			(AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb("AccessTableCatSocialeSessionBean");

	private List<SinbaMinoriSearchResultDTO> minoriSinbaDaEsportareVisualizzazioneList; 
	
	private List<String> listaCategorieSociali; // SISO-738
	
	private Map<Long, Boolean> sinbaSelezionatiPerExport = new HashMap<Long, Boolean> ();
	
	@PostConstruct
	public void init() {
		dataA = new Date();
		showPnlEsporta = false;
		showPnlVisualizza = false;
		opSettore = getCurrentOpSettore();
		statoEsportazione = "a"; // SISO-738
		caricaCategorieSociali(); // SISO-738
	}

	// SISO-738
	private void caricaCategorieSociali() {
		listaCategorieSociali = new ArrayList<String>();
		CeTBaseObject b = new CeTBaseObject();
		fillEnte(b);
		List<CsCCategoriaSociale> categorieSocialiAll = catSocService.getCategorieSocialiAll(b);
		for (CsCCategoriaSociale ccs : categorieSocialiAll) 
			listaCategorieSociali.add(ccs.getDescrizione().toLowerCase());
	}

	public void onDownloadRefresh() {
		logger.debug("onDownloadRefresh - Ricarico le liste");
		onBtnVerificaDatiSinbaClick();
	}

	/**
	 * Al click su "Verifica", 
	 * di fatto il punto di partenza dell'interazione utente
	 * @return
	 */
	public Boolean onBtnVerificaDatiSinbaClick() {
		sinbaSelezionatiPerExport.clear();
		
		if( (cf==null || cf.isEmpty())&& (!((cognome!=null && !cognome.isEmpty()) || (nome!=null && !nome.isEmpty()))) )
		{
			if( dataA==null || dataDA==null )
			{
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Parametri di ricerca insufficienti", 
							"Per effettuare la ricerca inserire almeno un Codice Fiscale o in alternativa Data Inizio e Fine. "
						+ 	"Nome e Cognome sono facoltativi");
					FacesContext.getCurrentInstance().addMessage(null, message);
					return showPnlEsporta;
			}
		}
		
		findMinoriDaEsportare();
		
		showPnlEsporta = true;
		showPnlVisualizza = true; // SISO-738

		return showPnlEsporta;
	}

	private void findMinoriDaEsportare() {
		numErogDaEsportare = 0;

		SinbaSearchCriteria bDto = new SinbaSearchCriteria();
		fillEnte(bDto);
		
		/* Se la data non è obbligatoria può essere NULL !!!!*/
		if (dataDA != null && dataA != null ){
			bDto.setDataInizio(dataDA);
			bDto.setDataFine(dataA);			
		}
		
		// bDto.setOperatoreId(opSettore.getId());
		bDto.setOrganizzazioneBelfiore(opSettore.getCsOSettore().getCsOOrganizzazione().getCodRouting());
		bDto.setStatoEsportazione(statoEsportazione);
		bDto.setCodiceFiscale(cf);
		bDto.setNome(nome);
		bDto.setCognome(cognome);

		minoriSinbaDaEsportareVisualizzazioneList = sinbaExportService.findMinoriNelPeriodo(bDto);
       
		//carico diarioId ultimo sinba inserito per ogni minore
		Iterator<SinbaMinoriSearchResultDTO>iterator=minoriSinbaDaEsportareVisualizzazioneList.iterator();
		while(iterator.hasNext()){
			SinbaMinoriSearchResultDTO elem=iterator.next();
			
			if(elem.getLastInsertedSinbaId()==null)
				elem.checkLastSinbaInserted();
			
			if (elem.getLastInsertedExportedSinbaId()==null)
				elem.checkLastSinbaInsertedExported();
			
			//SISO-777 controllo se per i Sinba non ancora esportati ed esportabili se
			// i codici prestazione salvati sul Json e quelli erogati alla data sono allineati
		    //se non lo sono il Sinba è da Aggiornare
			if (elem.getCsDSinbas() != null){
			Iterator<InfoExportSinbaDTO> iteratorCDS = elem.getCsDSinbas().iterator();
			while(iteratorCDS.hasNext()){
				InfoExportSinbaDTO elemCDS =iteratorCDS.next();
				if(elemCDS.getCsDSinba().getDataExport()==null && elemCDS.getEsportabile() ){
					
					try {
						IValSinba valSinba = ValSinbaManBaseBean.initISchedaSinba(elemCDS.getCsDSinba());
						Boolean bo = valSinba.codiciPrestazioneDaAggiornare(true);
						elemCDS.setCodiciDaAggiornare(bo);
						
						//SISO-777 richeccko i SinbaDaesportare
						if (sinbaSelezionatiPerExport.size() >0){

							for (Map.Entry<Long, Boolean> coppia: sinbaSelezionatiPerExport.entrySet()){
								if(Objects.equals(elemCDS.getDiarioId(), coppia.getKey())){
									elemCDS.setToExport(coppia.getValue());

								}

							}

						}
					} catch (Exception e) {
						
						addError("Caricamento fallito", "la procedura di caricamento ha segnalato un errore: "+ e.getMessage());
						logger.error("Esportazione fallita", e);
						
					}
					
				}
				else{
					elemCDS.setCodiciDaAggiornare(false);
				}
			}	
			}
		}
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(getIds(minoriSinbaDaEsportareVisualizzazioneList));
	}
	
	public boolean checkDisabledModifica(Long idRow, Long lastInsertedId, Long lastInsertedExp,  Date dataExport ){
	//public boolean checkDisabled(){
		boolean res=false;
		
		if(lastInsertedId.intValue() > lastInsertedExp.intValue()){
			if((idRow.longValue() != lastInsertedId.intValue())) res=true;
			else res=false;
		}else res=true;
	
		return res;
	}
	public boolean checkDisabledCopia(Long idRow, Long lastInsertedId, Long lastInsertedExp, Date dataExport ){
		//public boolean checkDisabled(){
		boolean res=false;
		if(lastInsertedId.intValue() > lastInsertedExp.intValue()){
			if((idRow.longValue() != lastInsertedId.intValue())) res=true;
			else res=false;
		}
		else{
			if (idRow.longValue() != lastInsertedExp.intValue()) res=true;
			else res=false;
		}
		return res;
	}

/*	*//**
	 * Btn_Aggiungi:Action <br/>
	 * Recupera SinbaManBean e ne inizializza il contenuto chiamando init();
	 *//*
	public void callInitSinbaManBean(){
		
		sinbaManBean = ValSinbaManBaseBean.initISchedaSinba("", null);
		RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);
		//sinbaManBean.setSaveNewSinba(true);
		//sinbaManBean.init(formatter.format(c.getTime()), formatter.format(new Date()));
	}*/
	/**
	 * Btn_Aggiungi:Action <br/>
	 * Recupera SinbaManBean e ne inizializza il contenuto chiamando init();
	 */
	public void callInitSinbaManBean(SinbaMinoriSearchResultDTO minore){
		this.setInCopy(false);
		minoreExportCurrent = minore;
		schedaSinbaMinoreCurrent=null;
		sinbaManBean = ValSinbaManBaseBean.initISchedaSinba("", minore.getSoggettoBeneficiario());
		RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);
	}
	/**
	 * Btn_Modifica:Action 
	 * ISSUE-777
	 */
    public void setOnModifica(InfoExportSinbaDTO sinba) {
		//SISO-777: in modifica le prestazioni salvate nel Json devono essere sempre allineati
	    // con le prestazioni salvate nel DB
	  try{
		  this.setInCopy(false);
		  schedaSinbaMinoreCurrent=sinba;
		  aggiornaMinoreExportCurrent(); //??
		  sinbaManBean = ValSinbaManBaseBean.initISchedaSinba(schedaSinbaMinoreCurrent.getCsDSinba());
		  
		  if (sinbaManBean != null) {
    	    //confronto numericamente i codici prestazioni 
    		if(sinbaManBean.codiciPrestazioneDaAggiornare(true)){
            	//sinbaManBean.visualizzaPnlAggiornaPrestazioni();
    			RequestContext.getCurrentInstance().addCallbackParam("isShowDialogPrestazioni", true);
    		}else
    			RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);
    	}
	  }catch(Exception e){
		  logger.error(e.getMessage(), e);
	  }
	}

	public void updateDialog(){
		String ver = this.sinbaManBean.getVersionLowerCase();
		logger.debug("aggiorno la dialog prima di mostrarla "+ver);
		
	}
        
    public Boolean abilitaEsportazione(InfoExportSinbaDTO minoreCurrent){
    	Boolean aE = true;
    	
    	try {
			/* ora devo popolargli il jsonCurrent */
    		if (minoreCurrent.getCsDSinba().getDataExport() == null && minoreCurrent.getEsportabile()){
    			IValSinba sinbaManBean = ValSinbaManBaseBean.initISchedaSinba(minoreCurrent.getCsDSinba());
    			aE = (!sinbaManBean.codiciPrestazioneDaAggiornare(true));
    		}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
    	return aE;
    }

	public void aggiornaMinoreExportCurrent(){
		
		//if(this.minoreExportCurrent ==null){
	       //se provengo da fuori fascicolo è valorizzata minoriSinbaDaEsportareVisualizzazioneList
		   if(minoriSinbaDaEsportareVisualizzazioneList!=null){
			  
              for(SinbaMinoriSearchResultDTO minore:minoriSinbaDaEsportareVisualizzazioneList){
        	      List<InfoExportSinbaDTO> sinbas=minore.getCsDSinbas();
        	      if(sinbas!=null){
        	         for(InfoExportSinbaDTO sinba:sinbas){
        		         if(sinba.getDiarioId() == schedaSinbaMinoreCurrent.getDiarioId().longValue() && minore.getCsDSinbas()!= null){
        			        this.minoreExportCurrent = minore;
        			        break;
        		         }
        	         } 
                   }
                }
                                    
//               sinbaManBean.init();
              
              }
		    
		   //provengo dal fascicolo
		   else{
			   
				/* Recupero l'istanza specifica di FascicoloBean*/
				FascicoloBean fascicoloBean = (FascicoloBean)getBeanReference("fascicoloBean");
				List<CsDSinba> sinbas=fascicoloBean.getListaValSinbaBean().getLazyListaSchedeValSinbaModel().getCsDSinbas();
			
				//Non ci passo più da qui entrando dal fascicolo
				
/*			    if(sinbas!=null)
			    {
			    	for(CsDSinba sinba:sinbas)
       	            {
       		            if(sinba.getCsDDiario().getId() == schedaSinbaMinoreCurrent.getDiarioId().longValue())
       		            {
       		            	SinbaMinoriSearchResultDTO minore = new SinbaMinoriSearchResultDTO();
       		            	minore.setSoggettoBeneficiario(fascicoloBean.getListaValSinbaBean().getSoggetto());
       		            	minore.setCsDSinbas(sinbas);
       		            	minore.setcFbeneficiario(fascicoloBean.getListaValSinbaBean().getSoggetto().getCsAAnagrafica().getCf());
       		            	minore.setNome(fascicoloBean.getListaValSinbaBean().getSoggetto().getCsAAnagrafica().getNome());
       		            	minore.setCognome(fascicoloBean.getListaValSinbaBean().getSoggetto().getCsAAnagrafica().getCognome());
       		            	minore.setIdCaso(fascicoloBean.getListaValSinbaBean().getSoggetto().getCsACaso().getId());
       		            	minore.setIdAnagrafica(new BigDecimal(fascicoloBean.getListaValSinbaBean().getSoggetto().getAnagraficaId()));
       		            	this.minoreExportCurrent=minore;
       			            break;
       		            }
       	            } 
//			    	sinbaManBean.init();
			    }*/
		   }
		//  }
	}
	
	
	/*COPIA SCHEDA SINBA DA LISTA*/
	public void setOnCopy(InfoExportSinbaDTO sinba) {
		this.setInCopy(true);
		schedaSinbaMinoreCurrent=sinba;
		
		sinbaManBean = ValSinbaManBaseBean.initISchedaSinba("", schedaSinbaMinoreCurrent.getCsDSinba().getCsDDiario().getCsACaso().getCsASoggetto());
		try{
			IValSinba schedaO = ValSinbaManBaseBean.initISchedaSinba(sinba.getCsDSinba());
			sinbaManBean.initCopia(schedaO);
			
			aggiornaMinoreExportCurrent(); //Aggiorno il bean soggetto attuale
			//Resetto il bean attuale (è nuovo)
			this.schedaSinbaMinoreCurrent = null;
			RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}
	

	private List<Long> getIds(List<SinbaMinoriSearchResultDTO> listaEsportazioneSinbaDTOview) {
		Set<Long> ids = new HashSet<Long>();

		for (SinbaMinoriSearchResultDTO e : listaEsportazioneSinbaDTOview) {
			ids.add(e.getIdCaso());
		}

		return new ArrayList<Long>(ids);
	}
	


	public Boolean onBtnResetClick() {
		showPnlEsporta = false;
		showPnlVisualizza = false;
		dataDA = null;
		dataA=null;
		cf = null;
		cognome = null;
		nome = null;
		statoEsportazione = "a";
		
		return showPnlEsporta;
	}

	
	public Boolean onBtnEsportaClick(CsDSinba sinbaDaEsportare) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(sinbaDaEsportare);

		// psExportService.updateCsIPsExportRevocaEsportazione(dto);

		return false; // refresh e return
	}

	private HashMap<Object, Object> insertDatiBeneficiario(EsportazioneDTO erogDaEsportare) {
		HashMap<Object, Object> mappaDatiSogg = new HashMap<Object, Object>();
		if (erogDaEsportare.getSoggettoCodiceFiscale() != null)
			mappaDatiSogg.put(Cost.BENEFICIARIO_CF, erogDaEsportare.getSoggettoCodiceFiscale());
		if (erogDaEsportare.getBenefRegione() != null)
			mappaDatiSogg.put(Cost.RESIDENZA_REGIONE, erogDaEsportare.getBenefRegione());
		if (erogDaEsportare.getBenefComune() != null)
			mappaDatiSogg.put(Cost.RESIDENZA_COMUNE, erogDaEsportare.getBenefComune());
		if (erogDaEsportare.getBenefNazione() != null)
			mappaDatiSogg.put(Cost.RESIDENZA_NAZIONE, erogDaEsportare.getBenefNazione());
		if (erogDaEsportare.getSoggettoNome() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_NOME, erogDaEsportare.getSoggettoNome());
		if (erogDaEsportare.getSoggettoCognome() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_COGNOME, erogDaEsportare.getSoggettoCognome());
		if (erogDaEsportare.getBenefAnnoNascita() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_ANNONASCITA, Integer.toString(erogDaEsportare.getBenefAnnoNascita()));
		if (erogDaEsportare.getBenefLuogoNascita() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_LUOGONASCITA, erogDaEsportare.getBenefLuogoNascita());
		if (erogDaEsportare.getBenefSesso() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_SESSO, Integer.toString(erogDaEsportare.getBenefSesso()));
		if (erogDaEsportare.getBenefCittadinanza() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_CITTAD_ISO, Integer.toString(erogDaEsportare.getBenefCittadinanza()));
		// if(erogDaEsportare.getBenefSecCittadinanza()!=null)
		// mappaDatiSogg.put(Cost.ANAGRAFICA_SEC_CITTAD_ISO,
		// Integer.toString(erogDaEsportare.getBenefSecCittadinanza()));

		return mappaDatiSogg;
	}

	private HashMap<Object, Object> insertDatiPrestazione(EsportazioneDTO erogDaEsportare) {
		HashMap<Object, Object> mappaDatiPrest = new HashMap<Object, Object>();
		
		/* prestazione occasionale */

		if (erogDaEsportare.getNumProtDSU() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_NUMPROT_DSU, erogDaEsportare.getNumProtDSU());
		if (erogDaEsportare.getAnnoProtDSU() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_ANNO_PROT, Integer.toString(erogDaEsportare.getAnnoProtDSU()));
		if (erogDaEsportare.getDataDSU() != null) {
			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_DSU, ddMMyyyy.format(erogDaEsportare.getDataDSU()));
		}
		if (erogDaEsportare.getCodPrestazione() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_CODICE, erogDaEsportare.getCodPrestazione());
		if (erogDaEsportare.getDenomPrestazione() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_DENOMINAZIONE, erogDaEsportare.getDenomPrestazione());

		if (erogDaEsportare.getDataEsecuzione() != null) {
			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_EROG, ddMMyyyy.format(erogDaEsportare.getDataEsecuzione()));
		}
		if (erogDaEsportare.getSpesa() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_IMPORTO, df.format(erogDaEsportare.getSpesa()));

		/* dati comuni */
		if (erogDaEsportare.getCarattere() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_CARATTERE, erogDaEsportare.getCarattere());
		if (erogDaEsportare.getPercGestitaEnte() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_ENTE, df.format(erogDaEsportare.getPercGestitaEnte()));
		if (erogDaEsportare.getCompartUtenti() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_UTENTE, df.format(erogDaEsportare.getCompartUtenti()));
		if (erogDaEsportare.getCompartSsn() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_SSN, df.format(erogDaEsportare.getCompartSsn()));

		// if(erogDaEsportare.getSogliaISEE()!=null)
		// mappaDatiPrest.put(Cost.PRESTAZIONE_SOGLIA_ISEE,
		// decf.format(erogDaEsportare.getSogliaISEE()));

		return mappaDatiPrest;
	}

	

	public StreamedContent getFile() {
		InputStream stream = null;
		StreamedContent file=null;
		try {
			CsDExportSinba exportMast = onBtnEsportaDatiSinbaClick(SchemaVersion.SINBA_2018);
			if(exportMast!=null){
				stream = new FileInputStream(tempFile);
				file = new DefaultStreamedContent(stream, "text/xml", exportMast.getNomeFile() + ".xml");

				// Refresh caricamento dati
				onDownloadRefresh();
			}
		} catch (FileNotFoundException e) {
			addError("Esportazione fallita", "errore nel recupero del file xml");
			logger.error("Esportazione fallita", e);
		} catch (Exception e) {
			addError("Esportazione fallita","la procedura di esportazione ha segnalato un errore: "+ e.getMessage());
			logger.error("Esportazione fallita", e);
		}
		return file;
	}
	//SISO-777 registro i sinbaselezionati per essere esportati
	
	public void saveSinbaCheckedToExport(Long diarioId, Boolean checkedForExport){
		sinbaSelezionatiPerExport.put(diarioId, checkedForExport);
	}
	


	public CsDExportSinba onBtnEsportaDatiSinbaClick(SchemaVersion schemaVersion) throws Exception {
		CsDExportSinba exportMasterEntity = null; // fallback value
        
		List<SinbaDTO> sinbaDTOdaExp = new ArrayList<SinbaDTO>();
		SchedeSinbaDaEsportareList=new ArrayList<CsDSinba>();
		for (SinbaMinoriSearchResultDTO sinbaResultDTO : this.minoriSinbaDaEsportareVisualizzazioneList) {
			if (sinbaResultDTO.getCsDSinbas() != null) {
				
				for (InfoExportSinbaDTO info : sinbaResultDTO.getCsDSinbas()) {
					
					//SISO-777 richeccko i SinbaDaesportare
					if (sinbaSelezionatiPerExport.size() >0){

						for (Map.Entry<Long, Boolean> coppia: sinbaSelezionatiPerExport.entrySet()){
							if(Objects.equals(info.getCsDSinba().getDiarioId(), coppia.getKey())){
								info.setToExport(coppia.getValue());
							}
						}
					}
					
					if (info.getToExport() != null && info.getToExport()) {
						CsDSinba sinba = info.getCsDSinba();
						SchedeSinbaDaEsportareList.add(sinba);
						
						IValSinba currManBean = ValSinbaManBaseBean.initISchedaSinba(sinba);
						SinbaDTO sDTO = currManBean.fillExportDTO();
						sinbaDTOdaExp.add(sDTO);
					}
				}
			}
		}

		if (sinbaDTOdaExp.size() > 0) {

			/*
			 * Validazione esportazione: devono essere presenti i dati DSU:
			 * anno, data e protocollo o nessuno di essi
			 */
			for (SinbaDTO es : sinbaDTOdaExp) {
				if (false) {
					// TODO eventuali controlli sul dato
					throw new Exception("controllare i campi...");
				}
			}

			tempFile = File.createTempFile("EsportaDatiSinbaBean", "");

			CsOOperatoreSettore opSettore = getCurrentOpSettore();
			CsOOrganizzazione csOOrganizzazione = opSettore.getCsOSettore().getCsOOrganizzazione();

			String codEnte = csOOrganizzazione.getCodCatastale();
			String denomEnte = csOOrganizzazione.getNome();
			String cfOperatore = getCurrentOpSettore().getCsOOperatore().getCsOOperatoreAnagrafica().getCodiceFiscale();
			String indirEnte = csOOrganizzazione.getCodCatastale();
			Date actualDate = new Date();

			exportMasterEntity = preparaEntityExport(codEnte, denomEnte,
					indirEnte, cfOperatore, actualDate,
					this.SchedeSinbaDaEsportareList);

			EsportaDatiSinbaUtils.esportaDatiSinba(tempFile.getAbsolutePath(), // String XML_PATH
					sinbaDTOdaExp,
					exportMasterEntity.getNomeFile(), // String
					denomEnte, // String denomEnte,
					codEnte, // String codEnte,
					cfOperatore, // String cfOperatore,
					indirEnte, // String indirEnte
					schemaVersion // selettore della versione
					);

			// salvo l'XML generato nell'Entity Export Master
			// per sinba non necessario, basta storicizzare il nome del file
			String xml = FileUtils.readFileToString(tempFile);
			exportMasterEntity.setXml(xml);

			// dopo essere riuscito a generare l'XML, posso salvare le Entity
			BaseDTO dto = new BaseDTO();
			CsUiCompBaseBean.fillEnte(dto);
			dto.setObj(exportMasterEntity);
			CsDExportSinba persistExporSinba=sinbaExportService.saveCsDExportSinba(dto);
			
			//ora posso aggiornare tutti i sinba esportati con l'id del relativo XML creato e la data di creazione
			for (CsDSinba es : SchedeSinbaDaEsportareList) {
				BaseDTO sDTO=new BaseDTO();
				sDTO.setObj(es.getDiarioId());
				fillEnte(sDTO);
				
				CsDSinba esPersistente=sinbaService.getSchedaSinbaById(sDTO);
				esPersistente.setDataExport(new Date());
				esPersistente.setCsDExportSinba(persistExporSinba);
				sDTO.setObj(esPersistente);
				sinbaService.salvaSchedaSinba(sDTO);
			}
		} else {
			addWarning("Esportazione Scheda Sinba", "Selezionare almeno una scheda SINBA da esportare");
			logger.debug("onBtnEsportaDatiSinbaClick: niente da esportare");
		}

		return exportMasterEntity;
	}

	private CsDExportSinba preparaEntityExport(String enteErogatore,
			String denominazEnte, String indirizzoEnte, String cfOperatore,
			Date actualDate, List<CsDSinba> sinbaDaExp)
			throws CarSocialeServiceException {

		logger.debug("Inizio preparaEntityExport");

		// Entity dell'export della testata erogazione (andrà in
		// CS_I_PS_EXPORT_MAST)
		CsDExportSinba csDExportSinba = new CsDExportSinba();

		// TODO setta i campi del record di export

		csDExportSinba.setEnteErogatore(enteErogatore);
		csDExportSinba.setDenominazEnte(denominazEnte);
		csDExportSinba.setIndirizzoEnte(indirizzoEnte);
		csDExportSinba.setCfOperatore(cfOperatore);
		csDExportSinba.setFlusso("SINBA");	// costante
		csDExportSinba.setDataInvio(actualDate);
				
		// generazione e salvataggio del NumeroProgressivo
		BaseDTO progressivoCsIPsExportMastSINBA = new BaseDTO();  
		CsUiCompBaseBean.fillEnte(progressivoCsIPsExportMastSINBA);
		progressivoCsIPsExportMastSINBA.setObj(csDExportSinba.getEnteErogatore()); 
		csDExportSinba.setNumProgressivo(sinbaExportService.getProgressivoCsDExportSinbaPSA(progressivoCsIPsExportMastSINBA));
				
		// generazione e salvataggio dell'idFlusso (IdentificazioneFlusso.Nome
		// nell'XML da generare)
		String nomeFile = creaNomeFile(enteErogatore, actualDate, csDExportSinba.getNumProgressivo());
		csDExportSinba.setNomeFile(nomeFile);
		csDExportSinba.setIdentificazioneFlusso(nomeFile);

		csDExportSinba.setDtIns(actualDate);
		csDExportSinba.setDtMod(actualDate);

		// SISO-719 salvataggio posticipato
		// BaseDTO dto = new BaseDTO();
		// CsUiCompBaseBean.fillEnte(dto);
		// dto.setObj(csIPsExportMast);
		// csIPsExportMast = psExportService.saveCsIPsExportMast(dto);

		// generazione delle Entity dell'export delle singole erogazioni
		// (andranno in tabella CS_I_PS_EXPORT) della testata

		for (CsDSinba csDSinba : sinbaDaExp) {
			// TODO metto csDExportSinba come entity di export per il sinbaDTO.
			// Verifica se necessario in base al cascade
			csDSinba.setCsDExportSinba(csDExportSinba);
		}
		Set<CsDSinba> csDSinbaSet = new HashSet<CsDSinba>(sinbaDaExp);
		csDExportSinba.setCsDSinbas(csDSinbaSet);

		return csDExportSinba;
	}

	public Boolean getShowDlgEsporta() {
		return showDlgEsporta;
	}

	public void setShowDlgEsporta(Boolean showDlgEsporta) {
		this.showDlgEsporta = showDlgEsporta;
	}

	public Date getDataDA() {
		return dataDA;
	}

	public void setDataDA(Date dataDA) {
		this.dataDA = dataDA;
	}

	public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public Boolean getShowPnlEsporta() {
		return showPnlEsporta;
	}

	public void setShowPnlEsporta(Boolean showPnlEsporta) {
		this.showPnlEsporta = showPnlEsporta;
	}

	public Boolean getShowPnlVisualizza() {
		return showPnlVisualizza;
	}

	public void setShowPnlVisualizza(Boolean showPnlVisualizza) {
		this.showPnlVisualizza = showPnlVisualizza;
	}

	public int getNumErogDaEsportare() {
		return numErogDaEsportare;
	}

	public void setNumErogDaEsportare(int numErogDaEsportare) {
		this.numErogDaEsportare = numErogDaEsportare;
	}

	// INIZIO modifica SISO-538
	// public int getNumErogNonDisponibiliAllInvio() {
	// return erogDaEsportareVisualizzazioneList.size() - numErogDaEsportare;
	// }

	public List<SinbaMinoriSearchResultDTO> getMinoriSinbaDaEsportareVisualizzazioneList() {
		return minoriSinbaDaEsportareVisualizzazioneList;
	}

	public void setMinoriSinbaDaEsportareVisualizzazioneList(
			List<SinbaMinoriSearchResultDTO> minoriSinbaDaEsportareVisualizzazioneList) {
		this.minoriSinbaDaEsportareVisualizzazioneList = minoriSinbaDaEsportareVisualizzazioneList;
	}

	public boolean isBtnEsportaDisabled() {
		return numErogDaEsportare == 0;
	}

	private int calcolaNumErogDaEsportare(List<EsportazioneDTOView> erogDaEsportareVisualizzazioneList) {
		int numErogDaEsportare = 0;
		for (EsportazioneDTOView esportazioneDTOView : erogDaEsportareVisualizzazioneList) {
			if (esportazioneDTOView.isDaInviare()) {
				numErogDaEsportare++;
			} else {
				logger.debug("calcolaNumErogDaEsportare NON INVIARE" + esportazioneDTOView.getSoggettoCodiceFiscale());
			}
		}
		return numErogDaEsportare;
	}
	
	public void saveAndUpdate(){
		boolean bsaved = sinbaManBean.save();
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			onBtnVerificaDatiSinbaClick();
		}
	}
	
	public void allineaCodiciPrestazione(){
		boolean bsaved = sinbaManBean.allineaCodiciPrestazione();
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			onBtnVerificaDatiSinbaClick();
		}
	}
	
	private String creaNomeFile(String codEnte, Date actualDate, long numeroProgressivo) {
		String EEEE = codEnte;
		String aaaammgg = new SimpleDateFormat("yyyyMMdd").format(actualDate);
		String nnnnnnn = String.format("%07d", numeroProgressivo);
		// TODO capire come deve essere fatto il nome
		return EEEE + ".SINBA." + aaaammgg + "." + nnnnnnn; // + ".xml"
	}
	
	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf.trim().toLowerCase();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome.trim().toLowerCase();
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome.trim().toLowerCase();
	}

	public String getStatoEsportazione() {
		return statoEsportazione;
	}

	public void setStatoEsportazione(String statoEsportazione) {
		this.statoEsportazione = statoEsportazione.trim().toLowerCase();
	}

	
	public List<String> getListaCategorieSociali() {
		return listaCategorieSociali;
	}

	public void setListaCategorieSociali(List<String> listaCategorieSociali) {
		this.listaCategorieSociali = listaCategorieSociali;
	}

	public InfoExportSinbaDTO getSchedaSinbaMinoreCurrent() {
		return schedaSinbaMinoreCurrent;
	}

	public void setSchedaSinbaMinoreCurrent(InfoExportSinbaDTO schedaSinbaMinoreCurrent) {
		this.schedaSinbaMinoreCurrent = schedaSinbaMinoreCurrent;
	}

	public Map<Long, Boolean> getSinbaSelezionatiPerExport() {
		return sinbaSelezionatiPerExport;
	}

	public void setSinbaSelezionatiPerExport(
			Map<Long, Boolean> sinbaSelezionatiPerExport) {
		this.sinbaSelezionatiPerExport = sinbaSelezionatiPerExport;
	}

	public Boolean getEsisteStorico() {
		Boolean eStorico = false;
		if(this.minoreExportCurrent!=null){
		   List<InfoExportSinbaDTO> valPrecedenti = this.minoreExportCurrent.getCsDSinbas();
		   eStorico = valPrecedenti!=null && !valPrecedenti.isEmpty();
		}
	   return eStorico;
	}
	
	public IValSinba getSinbaManBean() {
		return sinbaManBean;
	}

	public void setSinbaManBean(IValSinba sinbaManBean) {
		this.sinbaManBean = sinbaManBean;
	}
	
	public void copiaDatiDaUltimaValutazione(){

		try {
			
			 CsDSinba ultimoSinba = null;
			 
			   if(this.minoreExportCurrent != null && minoreExportCurrent.getCsDSinbas()!=null)
			   {
				   //sinbaManBean = ValSinbaManBaseBean.initISchedaSinba("", this.minoreExportCurrent.getSoggettoBeneficiario());
				
					  for (InfoExportSinbaDTO sinba : minoreExportCurrent.getCsDSinbas())
					   {
						   if (sinba.getCsDSinba().getCsDDiario().getDtIns() == null )
							   continue;
						
					      if(ultimoSinba == null  || ultimoSinba.getCsDDiario().getDtIns().before(sinba.getCsDSinba().getCsDDiario().getDtIns()))
						     ultimoSinba = sinba.getCsDSinba();
				        }
				  }
							
				
				  if(ultimoSinba!=null){
					  IValSinba schedaO = ValSinbaManBaseBean.initISchedaSinba(ultimoSinba);
					  sinbaManBean.initCopia(schedaO);
				  }
			   
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public boolean getInCopy() {
		return inCopy;
	}

	public void setInCopy(boolean inCopy) {
		this.inCopy = inCopy;
	}
	
}
