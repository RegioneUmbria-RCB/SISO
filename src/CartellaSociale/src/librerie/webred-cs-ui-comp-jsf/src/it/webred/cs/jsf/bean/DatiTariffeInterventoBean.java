package it.webred.cs.jsf.bean;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIQuotaRipartiz;
import it.webred.cs.data.model.CsIRigaQuota;
import it.webred.cs.data.model.CsIValQuota;
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

	protected AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");

	private Long selUnitaMisura;
	private CsIQuota csIQuota;
	private CsIRigaQuota csIRigaQuotaNew;
	private Map<Long, CsTbUnitaMisura> mappaOrdinataUnitaMisura;
	private List<CsTbUnitaMisura> lstCsTbUnitaMisuraAll; 			// in lettura devo far vedere tutte le unità di misura
	private List<CsTbUnitaMisura> lstCsTbUnitaMisuraForInsert;  	// in inserimento devo far vedere solo le unità di misura abilitate
	private boolean disableInputFrequenzaDelServizio = true;
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
		UNATANTUM("una tantum", "U"),
		OCCASIONALE("occasionale", "O"),
		PERIODICO("periodico", "P");
		
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
			this.getCsIQuota().getCsIQuotaRipartiz().setFlagUnatantum(true);
			this.getCsIQuota().getCsIQuotaRipartiz().setFlagRendiconto(TIPO_RENDICONTO.ADEVENTO.getCodice());
		}
		if(this.csIQuota.getCsIRigheQuota()==null)
			this.csIQuota.setCsIRigheQuota(new HashSet<CsIRigaQuota>());
		this.csIRigaQuotaNew = new CsIRigaQuota();
		this.csIRigaQuotaNew.setCsIValQuota(new CsIValQuota());
		this.csIRigaQuotaNew.setFlagAssegnazDiminuz(TIPO_QUOTA.ASSEGNAZIONE.getCodice());
		this.solaLettura = solalettura;
		loadMappaOrdinataUnitaMisura();
		
	}


	public void aggiungiRigaQuota() {
		if (this.csIQuota.getCsIRigheQuota()==null)
			this.csIQuota.setCsIRigheQuota(new HashSet<CsIRigaQuota>());
		csIRigaQuotaNew.setCsIQuota(this.csIQuota);
		this.csIQuota.getCsIRigheQuota().add(csIRigaQuotaNew);
		csIRigaQuotaNew = new CsIRigaQuota();
		csIRigaQuotaNew.setCsIValQuota(new CsIValQuota());
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
				csIQuota.setTariffa(null);
		
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
		if ((csIQuota.getCsIQuotaRipartiz().isFlagOccasionale() && csIQuota.getCsIQuotaRipartiz().isFlagPeriodico())
				|| (csIQuota.getCsIQuotaRipartiz().isFlagOccasionale() && csIQuota.getCsIQuotaRipartiz().isFlagUnatantum())
				|| (csIQuota.getCsIQuotaRipartiz().isFlagPeriodico() && csIQuota.getCsIQuotaRipartiz().isFlagUnatantum())) {
			//sono in errore, c'è più di un flag ad 1
			return "";
		} else if ((csIQuota.getCsIQuotaRipartiz().isFlagOccasionale() || csIQuota.getCsIQuotaRipartiz().isFlagPeriodico() || csIQuota.getCsIQuotaRipartiz().isFlagUnatantum())==false) {
			//sono in errore, non c'è nessun flag ad 1
			return "";
		} else {
			if (csIQuota.getCsIQuotaRipartiz().isFlagOccasionale())
				return TIPO_FREQUENZA_SERVIZIO.OCCASIONALE.getCodice();
			else if (csIQuota.getCsIQuotaRipartiz().isFlagPeriodico())
				return TIPO_FREQUENZA_SERVIZIO.PERIODICO.getCodice();
			else if (csIQuota.getCsIQuotaRipartiz().isFlagUnatantum())
				return TIPO_FREQUENZA_SERVIZIO.UNATANTUM.getCodice();
			else
				return "";
		}
	}
	
	public void setFrequenzaAccessoAlServizio(String s) {
		csIQuota.getCsIQuotaRipartiz().setFlagOccasionale(false);
		csIQuota.getCsIQuotaRipartiz().setFlagPeriodico(false);
		csIQuota.getCsIQuotaRipartiz().setFlagUnatantum(false);
		if (s!=null && s!="") {
			String sUpper = s.toUpperCase();
			if (sUpper.equals(TIPO_FREQUENZA_SERVIZIO.OCCASIONALE.codice))
				csIQuota.getCsIQuotaRipartiz().setFlagOccasionale(true);
			else if (sUpper.equals(TIPO_FREQUENZA_SERVIZIO.PERIODICO.codice))
				csIQuota.getCsIQuotaRipartiz().setFlagPeriodico(true);
			else if (sUpper.equals(TIPO_FREQUENZA_SERVIZIO.UNATANTUM.codice))
				csIQuota.getCsIQuotaRipartiz().setFlagUnatantum(true);
		}
	}
	
	public void frequenzaDelServizioChange() {
		if (csIQuota.getCsIQuotaRipartiz()==null)
			csIQuota.setCsIQuotaRipartiz(new CsIQuotaRipartiz());
		
		if (csIQuota.getCsIQuotaRipartiz().isFlagPeriodico()) {
			disableInputFrequenzaDelServizio=false;
			csIQuota.getCsIQuotaRipartiz().setFlagRendiconto(TIPO_RENDICONTO.PERIODICO.codice);
		} else {
			csIQuota.getCsIQuotaRipartiz().setFlagPeriodoRipartiz(null);
			csIQuota.getCsIQuotaRipartiz().setnGgSettimanali(null);
			csIQuota.getCsIQuotaRipartiz().setValQuotaPeriodo(null);
			csIQuota.getCsIQuotaRipartiz().setFlagRendiconto(TIPO_RENDICONTO.ADEVENTO.codice);
			disableInputFrequenzaDelServizio=true;
		}
	}
	
	public List<CsIRigaQuota> lstCsIRigheQuota() {
		List<CsIRigaQuota> result = new ArrayList<CsIRigaQuota>(csIQuota.getCsIRigheQuota());
		return result;
	}

	public TIPO_QUOTA[] getTipiQuota() {
		return TIPO_QUOTA.values();
	}
	
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
		return disableInputFrequenzaDelServizio;
	}


	
	public void setDisableInputFrequenzaDelServizio(
			boolean disableInputFrequenzaDelServizio) {
		this.disableInputFrequenzaDelServizio = disableInputFrequenzaDelServizio;
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

}
