package it.webred.cs.jsf.bean;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti.TariffeErogazioni.*;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIQuotaRipartiz;
import it.webred.cs.data.model.CsIRigaQuota;
import it.webred.cs.data.model.CsIValQuota;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.data.model.CsTbUnitaMisura;
import it.webred.cs.data.model.VArCTariffa;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;

public class DatiTariffeInterventoBean extends CsUiCompBaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getCarSocialeEjb ("AccessTableInterventoSessionBean");

	private Long selUnitaMisura;
	private CsIQuota csIQuota;
	private CsIRigaQuota csIRigaQuotaNew;
	private Map<Long, CsTbUnitaMisura> mappaOrdinataUnitaMisura;
	private List<SelectItem> lstCsTbUnitaMisuraForInsert;  	// in inserimento devo far vedere solo le unità di misura abilitate
	
	//SISO-469
	private List<VArCTariffa> lstVArCTariffa = new ArrayList<VArCTariffa>(); 	
	private VArCTariffa selectedTariffa; 
	
	private boolean solaLettura = false; //SISO 469
	//fine SISO-1134
	private final String tooltipNumTotaleGiorni = "N. totale di accessi al servizio (numero di fruizioni) in programma o numero di contributi programmati, comunque sempre riferiti ad un protocollo di richiesta. Dato non rilevante ai fini della rendicontazione.";

	//SISO_806
	private Long oreErogazioneProgrammata;
	private Long minutiErogazioneProgrammata;
	
	//SISO-1207
	private Long idTipoInterventoIstat;
	private Long idTipoInterventoCustom;
	
	public DatiTariffeInterventoBean(CsIQuota quota, boolean solalettura, Long idTipoInterventoIstat, Long idTipoInterventoCustom , CsOSettore titolare) {
		//SISO-1207 
		this.idTipoInterventoIstat = idTipoInterventoIstat;
		this.idTipoInterventoCustom = idTipoInterventoCustom;
			
		inizializzaTariffe();
		
		if(quota!=null){
			this.csIQuota = quota;
			this.selUnitaMisura = this.csIQuota.getCsTbUnitaMisura()!=null ? this.csIQuota.getCsTbUnitaMisura().getId() : null;
		}else
			this.csIQuota = new CsIQuota();
		
		if(this.csIQuota.getCsTbUnitaMisura()==null)
			this.csIQuota.setCsTbUnitaMisura(new CsTbUnitaMisura());
		if(this.csIQuota.getCsIQuotaRipartiz()==null){
			this.csIQuota.setCsIQuotaRipartiz(new CsIQuotaRipartiz());
			this.csIQuota.getCsIQuotaRipartiz().setFlagUnatantum(true);
			this.csIQuota.getCsIQuotaRipartiz().setFlagRendiconto(TIPO_RENDICONTO.ADEVENTO.getCodice());
		}
		
		if(this.csIQuota.getCsIRigheQuota()==null)
			this.csIQuota.setCsIRigheQuota(new HashSet<CsIRigaQuota>());
		this.csIRigaQuotaNew = new CsIRigaQuota();
		this.csIRigaQuotaNew.setCsIValQuota(new CsIValQuota());
		this.csIRigaQuotaNew.setFlagAssegnazDiminuz(TIPO_QUOTA.ASSEGNAZIONE.getCodice());
		this.solaLettura = solalettura;
		loadListeUnitaMisura();
		loadListaTariffe(titolare);
		//frequenzaDelServizioChange();
		
	}
	public void addMessage(FacesMessage.Severity tipoMessaggio, String summary) {
		FacesMessage message = new FacesMessage(tipoMessaggio, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void aggiungiRigaQuota() {
		if (this.csIQuota.getCsIRigheQuota()==null)
			this.csIQuota.setCsIRigheQuota(new HashSet<CsIRigaQuota>());
		//SISO_806
		if(csIRigaQuotaNew.getCsIValQuota().getValQuota() != null) {
			
			if(csIRigaQuotaNew.getCsIValQuota().getValQuota().remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0 
					&& !this.csIQuota.isKm() 
					&& !this.csIQuota.isOreMinuti()
					&& !this.csIQuota.isValuta()) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Il valore inserito deve essere intero");
				return;
			  }
		}
		
		if(csIQuota.isOreMinuti()) {
		   Long ore = this.oreErogazioneProgrammata == null ? 0L : this.oreErogazioneProgrammata;
		   Long minuti = this.minutiErogazioneProgrammata == null ? 0L :  this.minutiErogazioneProgrammata;
		 
		   if(ore == 0L && minuti == 0L) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Inserire almeno un valore tra ore e minuti!");
				return;
			}
		   
		   if(minuti >= 60L) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Il campo Minuti non è corretto:inserire valore numerico intero compreso tra 0 e 59");
				return;
			}
		   
		   
		   double minutiInOre = ((double)minuti/60);
		   double minutiInOreRound = (int)(Math.round(minutiInOre * 100))/100.0;
		   double oreminutiDecimal = ore + minutiInOreRound;
			
		   BigDecimal bgOreMinuti = BigDecimal.valueOf(oreminutiDecimal);
			
		   this.csIRigaQuotaNew.getCsIValQuota().setValQuota(bgOreMinuti);
		}
			
		csIRigaQuotaNew.setCsIQuota(this.csIQuota);
		this.csIQuota.getCsIRigheQuota().add(csIRigaQuotaNew);
		csIRigaQuotaNew = new CsIRigaQuota();
		csIRigaQuotaNew.setCsIValQuota(new CsIValQuota()); 
		csIRigaQuotaNew.setFlagAssegnazDiminuz(TIPO_QUOTA.ASSEGNAZIONE.getCodice()); //SISO-536 
		
	}
	
	
	public void rimuoviRigaQuota(CsIRigaQuota r) {
		csIQuota.getCsIRigheQuota().remove(r);
	}
	
	
	public BigDecimal getTotaleQuote() {
		BigDecimal result = BigDecimal.ZERO;
		if (this.csIQuota.getCsIRigheQuota()!=null) {
			for (CsIRigaQuota rigaQuota : this.csIQuota.getCsIRigheQuota()) {
				if (rigaQuota.getFlagAssegnazDiminuz().equals(TIPO_QUOTA.ASSEGNAZIONE.getCodice()))
					result = result.add(rigaQuota.getCsIValQuota().getValQuota()!=null ? rigaQuota.getCsIValQuota().getValQuota() : BigDecimal.ZERO);
				else if (rigaQuota.getFlagAssegnazDiminuz().equals(TIPO_QUOTA.DIMINUZIONE.getCodice()))
					result = result.subtract(rigaQuota.getCsIValQuota().getValQuota()!=null ? rigaQuota.getCsIValQuota().getValQuota() : BigDecimal.ZERO);
				//TODO ML: valutare se lanciare una eccezione nel caso di tipo non previsto
			}
		}
		return result;
	}
	
	public BigDecimal getSpesaPrevista() {
		BigDecimal result = BigDecimal.ZERO;
		result = getTotaleQuote().multiply(csIQuota.getTariffa()!=null ? csIQuota.getTariffa() : BigDecimal.ZERO);
		return result;
	}
		
	public List<SelectItem> getLstCsTbUnitaMisuraForInsert() {
		return lstCsTbUnitaMisuraForInsert;
	}
	//SISO 1207
			 
	 private List<CsTbUnitaMisura> loadUnitaMisuraByTipiIntervento(Long idTipoInterventoIstat, Long idTipoInterventoCustom )  {
		List<CsTbUnitaMisura> lstUmRestricted = new ArrayList<CsTbUnitaMisura>();
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(idTipoInterventoIstat);
		dto.setObj2(idTipoInterventoCustom);
		try {
			 	lstUmRestricted = confService.getCsTbUnitaMisuraByInterventoIstatCustom(dto);
		} catch (Exception e) {
			logger.error(e);
		
		}
		return lstUmRestricted;
	}
		
	//SISO-1131

   public List<VArCTariffa> getLstVArCTariffa() {
		return lstVArCTariffa;
	}

	public void setLstVArCTariffa(List<VArCTariffa> lstVArCTariffa) {
		this.lstVArCTariffa = lstVArCTariffa;
	}
	
	public void loadListaTariffe(CsOSettore titolare){
		lstVArCTariffa = new ArrayList<VArCTariffa>();
		if(titolare!=null){
			BaseDTO bdto = new BaseDTO();
			fillEnte(bdto);
			bdto.setObj(titolare.getCsOOrganizzazione().getId());
			bdto.setObj2(selUnitaMisura);
			bdto.setObj3(idTipoInterventoCustom);
			lstVArCTariffa = confService.findTariffe(bdto);
		}
	}

	private void loadListeUnitaMisura() {
		List<CsTbUnitaMisura> lstCsTbUnitaMisuraAll; // in lettura devo far vedere tutte le unità di misura
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		lstCsTbUnitaMisuraAll = confService.getCsTbUnitaMisura(dto);
		
		mappaOrdinataUnitaMisura = new HashMap<Long,CsTbUnitaMisura>();
		for (CsTbUnitaMisura um : lstCsTbUnitaMisuraAll) {
			mappaOrdinataUnitaMisura.put(um.getId(), um);
		}
		
		//SISO-1207
		List<CsTbUnitaMisura> lstRestricted = loadUnitaMisuraByTipiIntervento(idTipoInterventoIstat, idTipoInterventoCustom);
		if (lstRestricted.isEmpty()) 
			lstRestricted.addAll(lstCsTbUnitaMisuraAll);
		
		lstCsTbUnitaMisuraForInsert = new ArrayList<SelectItem>();
		for (CsTbUnitaMisura um : lstRestricted) {
			SelectItem si = new SelectItem(um.getId(), um.getValore());
			if (um.getAbilitato()!=null && um.getAbilitato().booleanValue()) 
				lstCsTbUnitaMisuraForInsert.add(si);
		}
		
		if(this.selUnitaMisura==null && this.lstCsTbUnitaMisuraForInsert.size()==1)
			setSelUnitaMisura((Long)lstCsTbUnitaMisuraForInsert.get(0).getValue());
	}
	
	public void selectedUnitaMisuraChange(ErogazioneInterventoBean erog) {
		if(csIQuota.isValuta()) {
			csIQuota.setTariffa(BigDecimal.ONE);
			 csIQuota.setDescTariffa("");//SISO-469
		     }			
	    else {
			 csIQuota.setTariffa(BigDecimal.ZERO); //SISO-556
			 csIQuota.setDescTariffa("");//SISO-469
		 }
				
		if(erog!=null)
			erog.onUpdateTariffa(csIQuota.getTariffa(), csIQuota.isOreMinuti());
		
		loadListaTariffe(erog.getSettoreTitolare());
		
	}
	
	public void flagPeriodoRipartizChange() {
		try {
			if (!csIQuota.getCsIQuotaRipartiz().getFlagPeriodoRipartiz().equals(TIPO_FREQUENZA_SERVIZIO_CADENZA.GIORNALIERA.getCodice()))
				csIQuota.getCsIQuotaRipartiz().setnGgSettimanali(null);
		} catch (NullPointerException e) { 
			//TODO ML: stabilire cosa fare
		}
	}
	
	public boolean flagPeriodoRipartizEnabled() {
		try {
			return csIQuota.getCsIQuotaRipartiz().getFlagPeriodoRipartiz().equals(TIPO_FREQUENZA_SERVIZIO_CADENZA.GIORNALIERA.getCodice());
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	
	public String getFrequenzaAccessoAlServizio() {
		//TODO ML: aggiungo una CsIQuotaRipartiz: è giusto averla in fase di salvataggio? 
		if (csIQuota.getCsIQuotaRipartiz()==null) {
			csIQuota.setCsIQuotaRipartiz(new CsIQuotaRipartiz());
		}
		
		//TODO ML: valutare cosa fare in situazioni anomale
		//controllo che solo un flag sia ad 1
		if ((csIQuota.getCsIQuotaRipartiz().isFlagIrregolare() && csIQuota.getCsIQuotaRipartiz().isFlagRegolare())
				|| (csIQuota.getCsIQuotaRipartiz().isFlagIrregolare() && csIQuota.getCsIQuotaRipartiz().isFlagUnatantum())
				|| (csIQuota.getCsIQuotaRipartiz().isFlagRegolare() && csIQuota.getCsIQuotaRipartiz().isFlagUnatantum())) {
			//sono in errore, c'è più di un flag ad 1
			return "";
		} else if ((csIQuota.getCsIQuotaRipartiz().isFlagIrregolare() || csIQuota.getCsIQuotaRipartiz().isFlagRegolare() || csIQuota.getCsIQuotaRipartiz().isFlagUnatantum())==false) {
			//sono in errore, non c'è nessun flag ad 1
			return "";
		} else {
			if (csIQuota.getCsIQuotaRipartiz().isFlagIrregolare())
				return TIPO_FREQUENZA_SERVIZIO.IRREGOLARE.getCodice();
			else if (csIQuota.getCsIQuotaRipartiz().isFlagRegolare())
				return TIPO_FREQUENZA_SERVIZIO.PERIODICO.getCodice();
			else if (csIQuota.getCsIQuotaRipartiz().isFlagUnatantum())
				return TIPO_FREQUENZA_SERVIZIO.UNATANTUM.getCodice();
			else
				return "";
		}
	}
	
	public void setFrequenzaAccessoAlServizio(String s) {
		csIQuota.getCsIQuotaRipartiz().setFlagIrregolare(false);
		csIQuota.getCsIQuotaRipartiz().setFlagRegolare(false);
		csIQuota.getCsIQuotaRipartiz().setFlagUnatantum(false);
		if (s!=null && s!="") {
			String sUpper = s.toUpperCase();
			if (sUpper.equals(TIPO_FREQUENZA_SERVIZIO.IRREGOLARE.getCodice()))
				csIQuota.getCsIQuotaRipartiz().setFlagIrregolare(true);
			else if (sUpper.equals(TIPO_FREQUENZA_SERVIZIO.PERIODICO.getCodice()))
				csIQuota.getCsIQuotaRipartiz().setFlagRegolare(true);
			else if (sUpper.equals(TIPO_FREQUENZA_SERVIZIO.UNATANTUM.getCodice()))
				csIQuota.getCsIQuotaRipartiz().setFlagUnatantum(true);
		}
	}
	
	public void frequenzaDelServizioChange() {
		if (csIQuota.getCsIQuotaRipartiz()==null)
			csIQuota.setCsIQuotaRipartiz(new CsIQuotaRipartiz());
		
		if (csIQuota.getCsIQuotaRipartiz().isFlagRegolare()) {
			csIQuota.getCsIQuotaRipartiz().setFlagRendiconto(TIPO_RENDICONTO.PERIODICO.getCodice());
		} else {
			csIQuota.getCsIQuotaRipartiz().setFlagPeriodoRipartiz(null);
			csIQuota.getCsIQuotaRipartiz().setnGgSettimanali(null);
			csIQuota.getCsIQuotaRipartiz().setValQuotaPeriodo(null);
			csIQuota.getCsIQuotaRipartiz().setFlagRendiconto(TIPO_RENDICONTO.ADEVENTO.getCodice());
		}
	}
	
	public List<CsIRigaQuota> lstCsIRigheQuota() {
		List<CsIRigaQuota> result = new ArrayList<CsIRigaQuota>(csIQuota.getCsIRigheQuota());
		return result;
	}

	public TIPO_QUOTA[] getTipiQuota() {
		return TIPO_QUOTA.values();
	}
	
//INIZIO SISO-536 - viene abilitato solo il tipo quota ASSEGNAZIONE
	public TIPO_QUOTA getTipoQuotaAssegnazione() {
		return TIPO_QUOTA.ASSEGNAZIONE;
	}
//FINE SISO-536
	
	
	public TIPO_CADENZA_QUOTA[] getTipiCadenzaQuota() {
		return TIPO_CADENZA_QUOTA.values();
	}
	
	public TIPO_FREQUENZA_SERVIZIO[] getTipiFrequenzaServizio() {
		return TIPO_FREQUENZA_SERVIZIO.values();
	}

	public TIPO_FREQUENZA_SERVIZIO_CADENZA[] getTipiFrequenzaServizioCadenza() {
		return TIPO_FREQUENZA_SERVIZIO_CADENZA.values();
	}
	
	public TIPO_RENDICONTO[] getTipiRendicontazione() {
		return TIPO_RENDICONTO.values();
	}
	
	public CsIQuota getCsIQuota() {
		return csIQuota;
	}

	public void setCsIQuota(CsIQuota csIQuota) {
		this.csIQuota = csIQuota;
	}


	public CsIRigaQuota getCsIRigaQuotaNew() {
		return csIRigaQuotaNew;
	}

	public void setCsIRigaQuotaNew(CsIRigaQuota csIRigaQuotaNew) {
		this.csIRigaQuotaNew = csIRigaQuotaNew;
	}

	public boolean isDisableInputFrequenzaDelServizio() {
		return !isRendicontoPeriodico();
	}

	public void setLstCsTbUnitaMisuraForInsert(List<SelectItem> lstCsTbUnitaMisuraForInsert) {
		this.lstCsTbUnitaMisuraForInsert = lstCsTbUnitaMisuraForInsert;
	}

    //SISO-469
	public VArCTariffa getSelectedTariffa() {
		return selectedTariffa;
	}

	public void setSelectedTariffa(VArCTariffa selectedTariffa) {
		this.selectedTariffa = selectedTariffa;
	}
	//FINE SISO-469
	
	public boolean salva(boolean unitaMisuraRequired) {
		boolean bOk = true;	
	    
		if(this.getCsIQuota().getCsTbUnitaMisura()==null && unitaMisuraRequired){
			bOk = false;
			this.addWarning("Tariffe", "Unità di misura è un campo obbligatorio");
			return bOk;
		}
		
		if(this.getCsIQuota().getCsTbUnitaMisura()!=null){
			this.getCsIQuota().getCsIQuotaRipartiz().setCsIQuota(this.csIQuota);
			
			try{
				BaseDTO dto = new BaseDTO();
			    fillEnte(dto);
				dto.setObj(this.csIQuota);
				csIQuota = interventoService.salvaQuota(dto);
				
			}catch (Exception e) {
				logger.error("Errore salvataggio dati tariffe", e);
				this.addError("Errore salvataggio dati tariffe", "");
				bOk = false;
			}
		}
		return bOk;
		
	}

	public Long getSelUnitaMisura() {
		return selUnitaMisura;
	}


	public boolean isSolaLettura() {
		return solaLettura;
	}


	public void setSolaLettura(boolean solaLettura) {
		this.solaLettura = solaLettura;
	}


	public void setSelUnitaMisura(Long selUnitaMisura) {
		this.selUnitaMisura = selUnitaMisura;
		if(this.selUnitaMisura!=null)
			this.csIQuota.setCsTbUnitaMisura(this.mappaOrdinataUnitaMisura.get(selUnitaMisura));
		else
			this.csIQuota.setCsTbUnitaMisura(null);
	}
	
	//SISO-806
	public Long getOreErogazioneProgrammata() {
		return oreErogazioneProgrammata;
	}
	public void setOreErogazioneProgrammata(Long oreErogazioneProgrammata) {
		this.oreErogazioneProgrammata = oreErogazioneProgrammata;
	}
	public Long getMinutiErogazioneProgrammata() {
		return minutiErogazioneProgrammata;
	}
	public void setMinutiErogazioneProgrammata(Long minutiErogazioneProgrammata) {
		this.minutiErogazioneProgrammata = minutiErogazioneProgrammata;
	}
	
	//FINE SISO-806
	

	//SISO-469
	public void loadTariffa(){
		 this.csIQuota.setTariffa(this.selectedTariffa.getTariffa());
		 this.csIQuota.setDescTariffa(this.selectedTariffa.getDescTariffa());
    }
	
	 public void inizializzaTariffe(){
		 this.selectedTariffa = new VArCTariffa();
	 }
	public void onRowSelect(SelectEvent event) {
		this.selectedTariffa = (VArCTariffa) event.getObject();
		loadTariffa();
	}
	//FINE-SISO-469
		
	//inizio SISO-556 
	public List<CsTbSchedaMultidim> getRendicontoValueList(){
		List<CsTbSchedaMultidim> result = new ArrayList<CsTbSchedaMultidim>();
		
		CsTbSchedaMultidim prova = new CsTbSchedaMultidim();
		prova.setDescrizione("Periodico");
		prova.setTooltip("In fase di rendiconto verrà chiesto l'intervallo di date al quale si riferisce il valore del servizio erogato. Esempio: dal 1/1/2017 al 31/3/2017 Contributo di euro 400");
		result.add(prova);

		prova = new CsTbSchedaMultidim();
		prova.setDescrizione("Ad Evento");
		prova.setTooltip("Deve essere fornito il rendiconto di ogni singolo evento erogatorio. Esempio: erogate n. 8 ore il 15/1/2017. Erogato contributo di 100 euro il 2/2/2017");
		result.add(prova);
		return result;
	}
	
	public List<CsTbSchedaMultidim> getFrequenzaValueList(){
		List<CsTbSchedaMultidim> result = new ArrayList<CsTbSchedaMultidim>();
		
		CsTbSchedaMultidim prova = new CsTbSchedaMultidim();
		prova.setDescrizione("Una tantum");
		prova.setTooltip("Ad un protocollo di richiesta deve corrispondere una erogazione del beneficio in un'unica soluzione. L'erogazione viene resa immediatamente disponibile al SIUSS. ");
		result.add(prova);


		prova = new CsTbSchedaMultidim();
		prova.setDescrizione("Irregolare");
		prova.setTooltip("Da un protocollo di richiesta possono derivare più erogazioni, in date diverse anche senza una regolare periodicità");
		result.add(prova);
		
		
		prova = new CsTbSchedaMultidim();
		prova.setDescrizione("Regolare");
		prova.setTooltip("Da un protocollo scaturiscono più erogazioni di servizio, ognuna con una data inizio e fine coerente con la periodicità del rendiconto. Esempio: un beneficio periodico con rendiconto mensile richiederà di inserire in fase di erogazione la data di inizio e la data fine pari rispettivamente al primo e all'ultimo giorno del mese a cui sui riferisce il rendiconto");
		result.add(prova);
		return result;
	}
	
	//fine SISO-556 
	

	//inizio SISO-536 
	public BigDecimal spesaRigaQuota(CsIRigaQuota csIRigaQuota){
		try {
			if (csIRigaQuota.getCsIValQuota().getValQuota()!=null) { 
				return csIRigaQuota.getCsIQuota().getTariffa().
						multiply(
								csIRigaQuota.getCsIValQuota().getValQuota()
								); 
			} else {
				return BigDecimal.ZERO;
			}
		} catch (Exception e) {
			logger.error(e);
			return BigDecimal.ZERO;
		}

	}
	
	
	public BigDecimal getTotaleSpese() {
		try {
			BigDecimal result = BigDecimal.ZERO;
			if (this.csIQuota.getCsIRigheQuota()!=null) {
				for (CsIRigaQuota rigaQuota : this.csIQuota.getCsIRigheQuota()) {
					if (rigaQuota.getFlagAssegnazDiminuz().equals(TIPO_QUOTA.ASSEGNAZIONE.getCodice())){
						result = result.add( spesaRigaQuota(rigaQuota) );
					} else if (rigaQuota.getFlagAssegnazDiminuz().equals(TIPO_QUOTA.DIMINUZIONE.getCodice())) { //in realtà dopo la modifica SISO-536 la diminuzione non viene più gestita ma nel codice la metto lo stesso per completezza 
						result = result.subtract( spesaRigaQuota(rigaQuota)  );					
					}
				}
			}
			return result;
		} catch (Exception e) {
			logger.error(e);
			return BigDecimal.ZERO;
		}
		

	}
	
	//fine SISO-536 
	
	public boolean isRendicontoPeriodico(){
		return TIPO_RENDICONTO.PERIODICO.getCodice().equals(csIQuota.getCsIQuotaRipartiz().getFlagRendiconto());
	}

	public boolean isValuta(){
		return this.getCsIQuota()!=null && this.getCsIQuota().isValuta();
	}
	
	//SISO-1134
	public String getTooltipNumTotaleGiorni() {
		return tooltipNumTotaleGiorni;
	}
	public boolean valida() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
