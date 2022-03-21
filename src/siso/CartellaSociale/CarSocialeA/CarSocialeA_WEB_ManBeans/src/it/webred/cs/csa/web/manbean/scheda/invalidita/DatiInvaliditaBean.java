package it.webred.cs.csa.web.manbean.scheda.invalidita;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsADatiInvalidita;
import it.webred.cs.data.model.CsAInvCiv;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsTbIcd10;
import it.webred.cs.data.model.CsTbIcd9;
import it.webred.cs.data.model.CsTbSinaRisposta;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiValiditaList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@SessionScoped
public class DatiInvaliditaBean extends SchedaValiditaBaseBean implements IDatiValiditaList{
	
	private List<CsTbSinaRisposta> lstFonteDerivazioneValutaz;//SISO-1278
	private List<CsTbSinaRisposta> lstInvCiv;//SISO-1278
	private List<String> lstInvCivScelte = new ArrayList<String>();
	private List<String> lstInvCivLegge104 = new ArrayList<String>();
	private List<String> lstAccompagnamento = new ArrayList<String>();
	private String strInvMinore75;
	List<String> vecchioValoreSelezionato =  new ArrayList<String>();
	List<String> nuovoValoreSelezionato =  new ArrayList<String>();
	@Override
	public Object getTypeClass() {
		return new CsADatiInvalidita();
		
	}
	
	@Override
	public String getTypeComponent() {
		return "pnlDatiInvalidita";
	}
	
	@Override
	public void nuovo() {
		this.lstInvCivScelte.clear();
		loadDatiInvaliditaSina();
		DatiInvaliditaComp comp = new DatiInvaliditaComp();
		this.valorizzaComboComp(comp);
		comp.setlstFonteDerivazioneValutaz(lstFonteDerivazioneValutaz);
		comp.setlstInvCiv(lstInvCiv);
		comp.setDisabilitaCheckPercentuale(false);

		comp.setDataInizio(new Date());
		listaComponenti.add(0, comp);
		super.nuovo();
				
	}
	
	@Override
	public String getCodiceTab() {
		return "I";
	}
	
	//SISO-1278
	private void loadDatiInvaliditaSina() {
		BaseDTO dto = new BaseDTO();
		lstFonteDerivazioneValutaz = new ArrayList<CsTbSinaRisposta>();
		lstInvCiv = new ArrayList<CsTbSinaRisposta>();//SISO-1278
	
		
		fillEnte(dto);
		dto.setObj(DataModelCostanti.TipoSinaDomanda.FONTE_DERIVAZIONE_INVALIDITA);
		
		lstFonteDerivazioneValutaz = confService.getCsTbSinaRispostaByDomandaId(dto);
		
		dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(DataModelCostanti.TipoSinaDomanda.INVALIDITA_CIVILE);
		
		lstInvCiv = confService.getCsTbSinaRispostaByDomandaId(dto);
		
		for (CsTbSinaRisposta risposta : lstInvCiv ) {
			String risp = risposta.getTooltip().trim().toUpperCase().replaceAll("\\s", "");
			if (risp.contains("L.104")) {
				lstInvCivLegge104.add(String.valueOf(risposta.getId()));
			}
			if (risp.contains("ACCOMPAGNAMENTO")) {
				lstAccompagnamento.add(String.valueOf(risposta.getId()));
			}
			if (risp.contains("75")) {
				strInvMinore75 = String.valueOf(risposta.getId());
			}
		}
		
		
		
	}


	
	@Override
	public CsADatiInvalidita getCsFromComponente(Object obj) {
		
		DatiInvaliditaComp comp = (DatiInvaliditaComp) obj;
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);		
		
		CsADatiInvalidita cs = new CsADatiInvalidita();
		if(comp.getId() != null){
			//update
			dto.setObj(comp.getId());
			cs = schedaService.getDatiInvaliditaById(dto);
		} else {
			//nuovo
			CsASoggettoLAZY sogg = new CsASoggettoLAZY();
			sogg.setAnagraficaId(soggettoId);
			cs.setCsASoggetto(sogg);
		}
			
		cs.setInValutazione(comp.isInValutazione()?"1":"0");
		cs.setIndennitaFrequenza(comp.isIndennitaFrequenza()?"1":"0");
		
		cs.setAccompagnamento(comp.isAccompagnamento()?"1":"0");
		cs.setCertificazioneH(comp.isCertificazioneH()?"1":"0");
		cs.setDataCertificazione(comp.getDataCertificazione());
		cs.setDataInvalidita(comp.getDataInvalidita());
		cs.setDataScadenzaCertificazione(comp.getDataCertificazioneScadenza());
		cs.setDataScadenzaInvalidita(comp.getDataInvaliditaScadenza());
		if(comp.getIcd10D1Bean().getIdSceltaSalvata() != null)
			cs.setIcd10d1Id(new BigDecimal(comp.getIcd10D1Bean().getIdSceltaSalvata()));
		if(comp.getIcd9D1Bean().getIdSceltaSalvata() != null)
			cs.setIcd9d1Id(new BigDecimal(comp.getIcd9D1Bean().getIdSceltaSalvata()));
		if(comp.getIcd10D2Bean().getIdSceltaSalvata() != null)
			cs.setIcd10d2Id(new BigDecimal(comp.getIcd10D2Bean().getIdSceltaSalvata()));
		if(comp.getIcd9D2Bean().getIdSceltaSalvata() != null)
			cs.setIcd9d2Id(new BigDecimal(comp.getIcd9D2Bean().getIdSceltaSalvata()));
		cs.setInvaliditaCivile(comp.isInvalidita()?"1":"0");
		cs.setInvaliditaCivileInCorso(comp.isInvaliditaInCorso()?"1":"0");
		cs.setLegge104(comp.isLegge104()?"1":"0");
		cs.setNoteSanitarie(comp.getNote());
		cs.setPercInvalidita(comp.getInvaliditaPerc());
		
		cs.setDataInizioApp(comp.getDataInizio());
		if(comp.getDataInizio() == null)
			cs.setDataInizioApp(new Date());
		cs.setDataFineApp(comp.getDataFine());
		if(comp.getDataFine() == null)
			cs.setDataFineApp(DataModelCostanti.END_DATE);
		
		
	     cs.setFonteDerivazioneValutaz(comp.getIdFonteDerivazioneValutaz()); ///SISO-1278
	     //Qui elaboro la lista di long comp.getLstInvCivScelte()
	     
	     
	     cs.getCsAInvCivs().clear();
		 for (CsTbSinaRisposta inviCiv : comp.getlstInvCiv()) {
			 
		   for(String s : comp.getLstInvCivScelte()) {
				if (s.equals(String.valueOf(inviCiv.getId()))){		
					CsAInvCiv nuovaInvCiv = new CsAInvCiv();
					nuovaInvCiv.setSinaRispostaId(inviCiv.getId());
					nuovaInvCiv.setSinaRispostaDescrizione(inviCiv.getTooltip());
					nuovaInvCiv.setUserIns(getCurrentUsername());
					nuovaInvCiv.setDtIns(new Date());
					cs.addCsAInvCiv(nuovaInvCiv);
				}
			}
		 }
		  
		return cs;
		
	}
	
	@Override
	public DatiInvaliditaComp getComponenteFromCs(Object obj) {
		this.lstInvCivScelte.clear();
		 
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);		
		CsADatiInvalidita cs = (CsADatiInvalidita) obj;
		
		DatiInvaliditaComp comp = new DatiInvaliditaComp();
		comp.setInValutazione("1".equals(cs.getInValutazione()));
		comp.setIndennitaFrequenza("1".equals(cs.getIndennitaFrequenza()));
		comp.setAccompagnamento("1".equals(cs.getAccompagnamento()));
		comp.setCertificazioneH("1".equals(cs.getCertificazioneH()));
		comp.setDataCertificazione(cs.getDataCertificazione());
		comp.setDataCertificazioneScadenza(cs.getDataScadenzaCertificazione());
		comp.setDataInvalidita(cs.getDataInvalidita());
		comp.setDataInvaliditaScadenza(cs.getDataScadenzaInvalidita());
		if(cs.getIcd10d1Id() != null) {
			comp.getIcd10D1Bean().setIdSceltaSalvata(cs.getIcd10d1Id().toString());
			dto.setObj(cs.getIcd10d1Id().longValue());
			CsTbIcd10 icd10 = confService.getIcd10ById(dto);
			comp.getIcd10D1Bean().setDescrSceltaSalvata(icd10.getCodice() + " | " + icd10.getDescrizione());
		}
		if(cs.getIcd9d1Id() != null){
			comp.getIcd9D1Bean().setIdSceltaSalvata(cs.getIcd9d1Id().toString());
			dto.setObj(cs.getIcd9d1Id().longValue());
			CsTbIcd9 icd9 = confService.getIcd9ById(dto);
			comp.getIcd9D1Bean().setDescrSceltaSalvata(icd9.getCodice() + " | " + icd9.getDescrizione());
		}
		if(cs.getIcd10d2Id() != null) {
			comp.getIcd10D2Bean().setIdSceltaSalvata(cs.getIcd10d2Id().toString());
			dto.setObj(cs.getIcd10d2Id().longValue());
			CsTbIcd10 icd10 = confService.getIcd10ById(dto);
			comp.getIcd10D2Bean().setDescrSceltaSalvata(icd10.getCodice() + " | " + icd10.getDescrizione());
		}
		if(cs.getIcd9d2Id() != null){
			comp.getIcd9D2Bean().setIdSceltaSalvata(cs.getIcd9d2Id().toString());
			dto.setObj(cs.getIcd9d2Id().longValue());
			CsTbIcd9 icd9 = confService.getIcd9ById(dto);
			comp.getIcd9D2Bean().setDescrSceltaSalvata(icd9.getCodice() + " | " + icd9.getDescrizione());
		}
		comp.setInvalidita("1".equals(cs.getInvaliditaCivile()));
		comp.setInvaliditaInCorso("1".equals(cs.getInvaliditaCivileInCorso()));
		comp.setInvaliditaPerc(cs.getPercInvalidita());
		comp.setLegge104("1".equals(cs.getLegge104()));
		comp.setNote(cs.getNoteSanitarie());
		
		comp.setDataInizio(cs.getDataInizioApp());
		comp.setDataFine(cs.getDataFineApp());
		comp.setId(cs.getId());

		// SISO-1060
		comp.setDataInserimento(cs.getDtIns());
		comp.setDataModifica(cs.getDtMod());
		comp.setUsrInserimento(super.getCognomeNomeUtente(cs.getUserIns())); 
		comp.setUsrModifica(super.getCognomeNomeUtente(cs.getUsrMod()));

		comp.setlstFonteDerivazioneValutaz(lstFonteDerivazioneValutaz);
		comp.setIdFonteDerivazioneValutaz(cs.getFonteDerivazioneValutaz()); ///SISO-1278
		
		comp.setlstInvCiv(this.lstInvCiv);
		
		if (cs.getCsAInvCivs() != null) {
		
			for(CsAInvCiv invCivSalvata : cs.getCsAInvCivs() ) {
			
			   lstInvCivScelte.add(invCivSalvata.getSinaRispostaId().toString());
			  
			}
		}
		
        comp.setLstInvCivScelte(lstInvCivScelte);
		
        if (comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_INFERIORE_75) ||
        		comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_CON_ACCOMPAGNAMENTO) ||
        		comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_SENZA_ACCOMPAGNAMENTO) ) {
        	comp.setDisabilitaCheckPercentuale(true);
        }
        else if (!comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_INFERIORE_75) &&
        		!comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_CON_ACCOMPAGNAMENTO) &&
        		!comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_SENZA_ACCOMPAGNAMENTO) ){
        	comp.setDisabilitaCheckPercentuale(false);
        }
       
		return comp;
		
	}

	@Override
	protected boolean validaNumeroSituazioniAperte() {
		if(this.getNumeroSituazioniAperte()>1) {
			addWarningFromProperties("warn.numero.situazioniI.aperte");
			return false;
		}
		return true;
	}

	@Override
	public String getNomeTab() {
		return "Invalidità";
	}

	@Override
	protected void caricaValoriCombo() {
		loadDatiInvaliditaSina();
	}

	@Override
	protected void valorizzaComboComp(ValiditaCompBaseBean comp) {
		// TODO Auto-generated method stub
		
	}
	
	public void onListInvCivClicked(ValueChangeEvent event) {
		List<String> oldValue = (List<String>) event.getOldValue();
		List<String> newValue = (List<String>) event.getNewValue();
		
		if(oldValue!=null)
			vecchioValoreSelezionato = new ArrayList<String>(oldValue);
		else
			vecchioValoreSelezionato = new ArrayList<String>();
		
		if(newValue!=null)
			nuovoValoreSelezionato = new ArrayList<String>(newValue);
		else
			nuovoValoreSelezionato = new ArrayList<String>();
		
		if(vecchioValoreSelezionato.size()<nuovoValoreSelezionato.size()) {
		  nuovoValoreSelezionato.removeAll(vecchioValoreSelezionato);
		}
	}
	
	public void onSelItems(AjaxBehaviorEvent event) {
		DatiInvaliditaComp comp = (DatiInvaliditaComp)this.getListaComponenti().get(this.getCurrentIndex());
	
		List<String> lstInvCivScelteAppo = new ArrayList<String>(comp.getLstInvCivScelte());
		
		for (String idRisposta104 : lstInvCivLegge104) {
			if (comp.getLstInvCivScelte().contains(idRisposta104)) {
				
				if (comp.isLegge104()) {
				  comp.setLegge104(false);
				
				
					this.addWarning(
							"Selezionando \"Legge 104 con articolo specificato\" non è possibile selezionare \"Legge 104 (articolo non specificato)\r\n"
									+ "\"",
							"");
					return;
				}		
			}
		}
		if (nuovoValoreSelezionato.contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_CON_ACCOMPAGNAMENTO)) {
			lstInvCivScelteAppo.remove(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_SENZA_ACCOMPAGNAMENTO);
			
			comp.setDisabilitaCheckPercentuale(true);
			if (comp.getLstInvCivScelte().contains(strInvMinore75)) {
				lstInvCivScelteAppo.remove(strInvMinore75);
				comp.setInvaliditaPerc(BigDecimal.ZERO);
			}
		}

		if (nuovoValoreSelezionato.contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_SENZA_ACCOMPAGNAMENTO)) {
			lstInvCivScelteAppo.remove(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_CON_ACCOMPAGNAMENTO);
			comp.setDisabilitaCheckPercentuale(true);
			
			if (comp.getLstInvCivScelte().contains(strInvMinore75)) {
				lstInvCivScelteAppo.remove(strInvMinore75);
				comp.setInvaliditaPerc(BigDecimal.ZERO);
			}
		}
		if (nuovoValoreSelezionato.contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_INFERIORE_75)) {
			lstInvCivScelteAppo.remove(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_SENZA_ACCOMPAGNAMENTO);
			lstInvCivScelteAppo.remove(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_CON_ACCOMPAGNAMENTO);
			
			comp.setDisabilitaCheckPercentuale(true);
			
		}
		comp.getLstInvCivScelte().clear();
		comp.setLstInvCivScelte(lstInvCivScelteAppo);
		
		if (comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_INFERIORE_75) ||
        		comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_CON_ACCOMPAGNAMENTO) ||
        		comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_SENZA_ACCOMPAGNAMENTO) ) {
        	comp.setDisabilitaCheckPercentuale(true);
        }
        else if (!comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_INFERIORE_75) &&
        		!comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_CON_ACCOMPAGNAMENTO) &&
        		!comp.getLstInvCivScelte().contains(DataModelCostanti.TipoInvaliditaTotale.INVALIDITA_TOTALE_SENZA_ACCOMPAGNAMENTO) ){
        	comp.setDisabilitaCheckPercentuale(false);
        }
	}
	
	public void onChangeLegge104() {
		DatiInvaliditaComp comp = (DatiInvaliditaComp)this.getListaComponenti().get(this.getCurrentIndex());
		List<String> lstInvCivScelteAppo = new ArrayList<String>(comp.getLstInvCivScelte());
	
		if (comp.isLegge104()) {
			
			for(String strInvCiv : comp.getLstInvCivScelte()) {
				if(lstInvCivLegge104.contains(strInvCiv)) {
					lstInvCivScelteAppo.remove(strInvCiv);
					 this.addWarning("Selezionando \"Legge 104 (articolo non specificato)\" non è possibile selezionare altri riferimenti alla Legge 104", "");
					
					
				}
			}
		}
		
		 comp.getLstInvCivScelte().clear();
		 comp.setLstInvCivScelte(lstInvCivScelteAppo);
		
		
	}
	

}
