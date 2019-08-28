package it.webred.cs.jsf.bean;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIQuotaRipartiz;
import it.webred.cs.data.model.CsIRigaQuota;
import it.webred.cs.data.model.CsIValQuota;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.data.model.CsTbUnitaMisura;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class DatiTariffeInterventoBean extends CsUiCompBaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	protected AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getCarSocialeEjb ("AccessTableConfigurazioneSessionBean");
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getCarSocialeEjb ("AccessTableInterventoSessionBean");

	private Long selUnitaMisura;
	private CsIQuota csIQuota;
	private CsIRigaQuota csIRigaQuotaNew;
	private Map<Long, CsTbUnitaMisura> mappaOrdinataUnitaMisura;
	private List<CsTbUnitaMisura> lstCsTbUnitaMisuraAll; 			// in lettura devo far vedere tutte le unità di misura
	private List<CsTbUnitaMisura> lstCsTbUnitaMisuraForInsert;  	// in inserimento devo far vedere solo le unità di misura abilitate
	
	private boolean solaLettura = false;
	
	public enum TIPO_QUOTA {
        ASSEGNAZIONE("Assegnazione", "A"), 
        DIMINUZIONE("Diminuzione", "D");
        	   
        String descrizione;
        String codice;

        TIPO_QUOTA(String descrizione, String codice) {
            this.descrizione = descrizione;
            this.codice = codice;
        }

		public String getDescrizione() {return descrizione;}
		public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
		public String getCodice() {return codice;}
		public void setCodice(String codice) {this.codice = codice;}
    }
		
	public enum TIPO_CADENZA_QUOTA {
		ANNUALE("annuale", "A"),
		MENSILE("mensile", "M"),
		SETTIMANALE("settimanale", "S"),
		GIORNALIERA("giornaliera", "G"),
		OCCASIONALE("occasionale", "O"),
		UNATANTUM("una tantum", "U");
		
		String descrizione;
        String codice;
		
        private TIPO_CADENZA_QUOTA(String descrizione, String codice) {
			this.descrizione = descrizione;
			this.codice = codice;
		}

		public String getDescrizione() {return descrizione;}
		public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
		public String getCodice() {return codice;}
		public void setCodice(String codice) {this.codice = codice;}
	}
	
	public enum TIPO_FREQUENZA_SERVIZIO {
		UNATANTUM("Una tantum", "U"),
		OCCASIONALE("Irregolare", "O"),	//SISO-556
		PERIODICO("Regolare", "P");		//SISO-556
		
		String descrizione;
        String codice;
		
        private TIPO_FREQUENZA_SERVIZIO (String descrizione, String codice) {
			this.descrizione = descrizione;
			this.codice = codice;
		}

		public String getDescrizione() {return descrizione;}
		public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
		public String getCodice() {return codice;}
		public void setCodice(String codice) {this.codice = codice;}
	}
	
	public enum TIPO_FREQUENZA_SERVIZIO_CADENZA {
		ANNUALE("annuale", "A"),
		MENSILE("mensile", "M"),
		SETTIMANALE("settimanale", "S"),
		GIORNALIERA("giornaliera", "G");
		
		String descrizione;
        String codice;
		
        private TIPO_FREQUENZA_SERVIZIO_CADENZA (String descrizione, String codice) {
			this.descrizione = descrizione;
			this.codice = codice;
		}

		public String getDescrizione() {return descrizione;}
		public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
		public String getCodice() {return codice;}
		public void setCodice(String codice) {this.codice = codice;}
	}
	
	public enum TIPO_RENDICONTO {
		PERIODICO("Periodico", "P"),
		ADEVENTO("Ad evento", "E");
		
		String descrizione;
        String codice;
		
        private TIPO_RENDICONTO (String descrizione, String codice) {
			this.descrizione = descrizione;
			this.codice = codice;
		}

		public String getDescrizione() {return descrizione;}
		public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
		public String getCodice() {return codice;}
		public void setCodice(String codice) {this.codice = codice;}
	}
	
	public DatiTariffeInterventoBean(CsIQuota quota, boolean solalettura) {
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
		loadMappaOrdinataUnitaMisura();
		//frequenzaDelServizioChange();
		
	}


	public void aggiungiRigaQuota() {
		if (this.csIQuota.getCsIRigheQuota()==null)
			this.csIQuota.setCsIRigheQuota(new HashSet<CsIRigaQuota>());
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
				if (rigaQuota.getFlagAssegnazDiminuz().equals(TIPO_QUOTA.ASSEGNAZIONE.codice))
					result = result.add(rigaQuota.getCsIValQuota().getValQuota()!=null ? rigaQuota.getCsIValQuota().getValQuota() : BigDecimal.ZERO);
				else if (rigaQuota.getFlagAssegnazDiminuz().equals(TIPO_QUOTA.DIMINUZIONE.codice))
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
	
	
	public List<CsTbUnitaMisura> getLstCsTbUnitaMisuraAll() {
		if (lstCsTbUnitaMisuraAll==null || lstCsTbUnitaMisuraAll.isEmpty())
			this.loadMappaOrdinataUnitaMisura();
		
		return lstCsTbUnitaMisuraAll;
	}
	
	public List<CsTbUnitaMisura> getLstCsTbUnitaMisuraForInsert() {
		if (lstCsTbUnitaMisuraAll==null || lstCsTbUnitaMisuraAll.isEmpty())
			getLstCsTbUnitaMisuraAll();
			
		if (lstCsTbUnitaMisuraAll!=null && this.lstCsTbUnitaMisuraForInsert==null) {
			lstCsTbUnitaMisuraForInsert = new ArrayList<CsTbUnitaMisura>();
			for (CsTbUnitaMisura um : lstCsTbUnitaMisuraAll) {
				if (um.getAbilitato().equals("1")) 
					lstCsTbUnitaMisuraForInsert.add(um);
			}
		}
		
		return lstCsTbUnitaMisuraForInsert;
	}
	
	private void loadMappaOrdinataUnitaMisura() {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		lstCsTbUnitaMisuraAll = confService.getCsTbUnitaMisura(dto);
		
		mappaOrdinataUnitaMisura = new HashMap<Long,CsTbUnitaMisura>();
		for (CsTbUnitaMisura um : lstCsTbUnitaMisuraAll) {
			mappaOrdinataUnitaMisura.put(um.getId(), um);
		}
	}
	
	public void selectedUnitaMisuraChange(ErogazioneInterventoBean erog) {
		if(csIQuota.getCsTbUnitaMisura()!=null && csIQuota.getCsTbUnitaMisura().getValore().equals("€")) 
			csIQuota.setTariffa(BigDecimal.ONE);
		 else 
			csIQuota.setTariffa(BigDecimal.ZERO);  //SISO-556
		
		if(erog!=null)
			erog.onUpdateTariffa(csIQuota.getTariffa());
		
	}
	
	public void flagPeriodoRipartizChange() {
		try {
			if (!csIQuota.getCsIQuotaRipartiz().getFlagPeriodoRipartiz().equals(TIPO_FREQUENZA_SERVIZIO_CADENZA.GIORNALIERA.codice))
				csIQuota.getCsIQuotaRipartiz().setnGgSettimanali(null);
		} catch (NullPointerException e) { 
			//TODO ML: stabilire cosa fare
		}
	}
	
	public boolean flagPeriodoRipartizEnabled() {
		try {
			return csIQuota.getCsIQuotaRipartiz().getFlagPeriodoRipartiz().equals(TIPO_FREQUENZA_SERVIZIO_CADENZA.GIORNALIERA.codice);
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
				return TIPO_FREQUENZA_SERVIZIO.OCCASIONALE.getCodice();
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
			if (sUpper.equals(TIPO_FREQUENZA_SERVIZIO.OCCASIONALE.codice))
				csIQuota.getCsIQuotaRipartiz().setFlagIrregolare(true);
			else if (sUpper.equals(TIPO_FREQUENZA_SERVIZIO.PERIODICO.codice))
				csIQuota.getCsIQuotaRipartiz().setFlagRegolare(true);
			else if (sUpper.equals(TIPO_FREQUENZA_SERVIZIO.UNATANTUM.codice))
				csIQuota.getCsIQuotaRipartiz().setFlagUnatantum(true);
		}
	}
	
	public void frequenzaDelServizioChange() {
		if (csIQuota.getCsIQuotaRipartiz()==null)
			csIQuota.setCsIQuotaRipartiz(new CsIQuotaRipartiz());
		
		if (csIQuota.getCsIQuotaRipartiz().isFlagRegolare()) {
			csIQuota.getCsIQuotaRipartiz().setFlagRendiconto(TIPO_RENDICONTO.PERIODICO.codice);
		} else {
			csIQuota.getCsIQuotaRipartiz().setFlagPeriodoRipartiz(null);
			csIQuota.getCsIQuotaRipartiz().setnGgSettimanali(null);
			csIQuota.getCsIQuotaRipartiz().setValQuotaPeriodo(null);
			csIQuota.getCsIQuotaRipartiz().setFlagRendiconto(TIPO_RENDICONTO.ADEVENTO.codice);
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
	
	public void setLstCsTbUnitaMisuraAll(List<CsTbUnitaMisura> lstCsTbUnitaMisuraAll) {
		this.lstCsTbUnitaMisuraAll = lstCsTbUnitaMisuraAll;
	}


	
	public void setLstCsTbUnitaMisuraForInsert(
			List<CsTbUnitaMisura> lstCsTbUnitaMisuraForInsert) {
		this.lstCsTbUnitaMisuraForInsert = lstCsTbUnitaMisuraForInsert;
	}

	public boolean salva() {
		boolean bOk = true;	
	
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
		
		}else bOk = false;
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
		prova.setTooltip("Ad un protocollo di richiesta deve corrispondere una erogazione del beneficio in un'unica soluzione. L'erogazione viene resa immediatamente disponibile al casellario Inps. ");
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
					if (rigaQuota.getFlagAssegnazDiminuz().equals(TIPO_QUOTA.ASSEGNAZIONE.codice)){
						result = result.add( spesaRigaQuota(rigaQuota) );
					} else if (rigaQuota.getFlagAssegnazDiminuz().equals(TIPO_QUOTA.DIMINUZIONE.codice)) { //in realtà dopo la modifica SISO-536 la diminuzione non viene più gestita ma nel codice la metto lo stesso per completezza 
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
		return csIQuota.getCsIQuotaRipartiz().getFlagRendiconto().equals(DataModelCostanti.CsIQuotaRipartiz.FLAG_RENDICONTO_PERIODICO);
	}

}
