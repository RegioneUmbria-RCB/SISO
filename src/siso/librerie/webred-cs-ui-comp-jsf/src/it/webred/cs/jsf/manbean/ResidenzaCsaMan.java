package it.webred.cs.jsf.manbean;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoIndirizzo;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsTbTipoIndirizzo;
import it.webred.cs.jsf.manbean.superc.ResidenzaMan;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.jsf.bean.ComuneBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

@ManagedBean
@NoneScoped
public class ResidenzaCsaMan extends ResidenzaMan implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<CsAIndirizzo> lstIndirizzi;
	protected List<CsAIndirizzo> lstIndirizziOld;
	
	private String tipoIndirizzo;
	private String tipoComune;
	private CsAIndirizzo indirizzoAnagrafe;
	private Date dataInizioAppComune;	
	private String citta;
	private String indirizzo;
	private String civicoNumero;
	private String civicoAltro;
	private String codiceFiscale;
	private String tipoRicercaSoggetto;
	private Date dataInizioApp;
	private Date dataAnnullamento = new Date();
	private List<SelectItem> listaTipoLuogoResidenza;
	private AmTabComuni comuneCorrente;
		
	private CsAIndirizzo indirizzoSelezionato;
	
	private String warningMessage;
	private String addressMessage;
	
	private ComuneNazioneResidenzaMan comuneNazioneResidenzaMan = new ComuneNazioneResidenzaMan();
	private IndirizzoMan indirizzoMan = new IndirizzoMan();
	
	public ResidenzaCsaMan(){
		super();
		indirizzoAnagrafe = null;
		String belfiore = getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getCodCatastale();
		if(!StringUtils.isBlank(belfiore)){
			comuneCorrente = luoghiService.getComuneItaByBelfiore(belfiore);
		}
		doLoadListaTipoLuogoResidenza();
		resetTipoComune();
	}

	public ComuneNazioneResidenzaMan getComuneNazioneResidenzaMan() {
		return comuneNazioneResidenzaMan;
	}

	public void setComuneNazioneResidenzaMan(ComuneNazioneResidenzaMan comuneNazioneResidenzaMan) {
		this.comuneNazioneResidenzaMan = comuneNazioneResidenzaMan;
	}

	public IndirizzoMan getIndirizzoMan() {
		return indirizzoMan;
	}

	public void setIndirizzoMan(IndirizzoMan indirizzoMan) {
		this.indirizzoMan = indirizzoMan;
	}
	
	public String getCodiceFiscale() {
	    return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getTipoIndirizzo() {
		return tipoIndirizzo;
	}

	public void setTipoIndirizzo(String tipoIndirizzo) {
		this.tipoIndirizzo = tipoIndirizzo;
	}

	public String getTipoComune() {
		return tipoComune;
	}

	public void setTipoComune(String tipoComune) {
		this.tipoComune = tipoComune;
	}
	
	public CsAIndirizzo getIndirizzoAnagrafe() {
		return indirizzoAnagrafe;
	}

	public void setIndirizzoAnagrafe(CsAIndirizzo indirizzoAnagrafe) {
		this.indirizzoAnagrafe = indirizzoAnagrafe;
	}
	
	public Date getDataInizioAppComune() {
		return dataInizioAppComune;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivicoNumero() {
		return civicoNumero;
	}

	public void setCivicoNumero(String civicoNumero) {
		this.civicoNumero = civicoNumero;
	}

	public String getCivicoAltro() {
		return civicoAltro;
	}

	public void setCivicoAltro(String civicoAltro) {
		this.civicoAltro = civicoAltro;
	}

	public void setDataInizioAppComune(Date dataInizioAppComune) {
		this.dataInizioAppComune = dataInizioAppComune;
	}

	public Date getDataInizioApp() {
		return dataInizioApp;
	}

	public void setDataInizioApp(Date dataInizioApp) {
		this.dataInizioApp = dataInizioApp;
	}
	
	public Date getDataAnnullamento() {
		return dataAnnullamento;
	}

	public void setDataAnnullamento(Date dataAnnullamento) {
		this.dataAnnullamento = dataAnnullamento;
	}

	@Override
	public List<CsAIndirizzo> getLstIndirizzi() {
		if (lstIndirizzi != null) {
			Collections.sort(lstIndirizzi, new indirizziComparator());
		}		
		return lstIndirizzi;
	}

	public void setLstIndirizzi(List<CsAIndirizzo> lstIndirizzi) {
		this.lstIndirizzi = lstIndirizzi;
	}
	
	public List<CsAIndirizzo> getLstIndirizziOld() {		
		return lstIndirizziOld;
	}

	public void setLstIndirizziOld(List<CsAIndirizzo> lstIndirizziOld) {
		this.lstIndirizziOld = lstIndirizziOld;
	}

	public CsAIndirizzo getIndirizzoSelezionato() {		
		return indirizzoSelezionato;
	}

	public void setIndirizzoSelezionato(CsAIndirizzo indirizzoSelezionato) {
		this.indirizzoSelezionato = indirizzoSelezionato;
	}

	@Override
	public ArrayList<CsAIndirizzo> getLstIndirizziForKey(String key) {
		ArrayList<CsAIndirizzo> lstIndirizziForKey = new ArrayList<CsAIndirizzo>();
		//TODO
		return lstIndirizziForKey;
	}
	
	public CsAIndirizzo getIndirizzoResidenzaAttivo() {
		List<CsAIndirizzo> lstIndirizzi = getLstIndirizzi();
		if (lstIndirizzi == null || lstIndirizzi.size() == 0) {
			return null;
		}
		CsAIndirizzo indirizzoAttivo = null;
		for (CsAIndirizzo indirizzo : lstIndirizzi) {
			if(indirizzo.getCsTbTipoIndirizzo().getId() == TipoIndirizzo.RESIDENZA_ID &&
					(indirizzo.getDataFineApp() == null || indirizzo.getDataFineApp().getTime() == getNullDateTime())) {
				indirizzoAttivo = indirizzo;
			}
		}
		return indirizzoAttivo;
	}
	
	//SISO-1127 Inizio
	public CsAIndirizzo getIndirizzoDomicilioAttivo() {
		List<CsAIndirizzo> lstIndirizzi = getLstIndirizzi();
		if (lstIndirizzi == null || lstIndirizzi.size() == 0) {
			return null;
		}
		CsAIndirizzo indirizzoAttivo = null;
		for (CsAIndirizzo indirizzo : lstIndirizzi) {
			if(indirizzo.getCsTbTipoIndirizzo().getId() == TipoIndirizzo.DOMICILIO_ID &&
					(indirizzo.getDataFineApp() == null || indirizzo.getDataFineApp().getTime() == getNullDateTime())) {
				indirizzoAttivo = indirizzo;
			}
		}
		return indirizzoAttivo;
	}
	//SISO-1127 Fine
	
	public String getIndirizzoResidenzaAttivoLabel() {
		String lbl="";
		CsAIndirizzo ind = this.getIndirizzoResidenzaAttivo();
		if(ind!=null)
			lbl = ind.getCsAAnaIndirizzo().getLabelIndirizzoCompleto();
		return lbl;
	}

	public String getWidgetVar() {
		return "residenzaCsaDialog";
	}
	
	private String getEnteLabel() {
		String enteLabel = TIPO_LUOGO.COMUNE.getDescrizione();
		String denominazione = null;
		if (comuneCorrente != null) {
			denominazione = comuneCorrente.getDenominazione();
		}
		if(!StringUtils.isBlank(denominazione))
			enteLabel = "COMUNE DI " + denominazione.toUpperCase();
		return enteLabel;
	}
	
	
	public List<SelectItem> getListaTipoLuogoResidenza(){
		return this.listaTipoLuogoResidenza;
	}
	
	private void doLoadListaTipoLuogoResidenza(){	
		listaTipoLuogoResidenza = new ArrayList<SelectItem>();
		if(comuneCorrente!=null)
			listaTipoLuogoResidenza.add(new SelectItem(TIPO_LUOGO.COMUNE.getCodice(), this.getEnteLabel()));
		listaTipoLuogoResidenza.add(new SelectItem(TIPO_LUOGO.SENZA_FISSA_DIMORA.getCodice(), TIPO_LUOGO.SENZA_FISSA_DIMORA.getDescrizione()));	
		listaTipoLuogoResidenza.add(new SelectItem(TIPO_LUOGO.ALTRO.getCodice(), TIPO_LUOGO.ALTRO.getDescrizione()));	
	}
	
	//SISO-1127 Inizio
	public String getEnteSiglaProv() {
		String siglaProv = comuneCorrente!=null && !StringUtils.isBlank(comuneCorrente.getSiglaProv()) ? comuneCorrente.getSiglaProv().toUpperCase() : "";
		return siglaProv;
	}
	//SISO-1127 Fine
	public void salvaIndirizzi() {
		
		boolean ok = true;
		
		//controlli
		boolean residenza = false;
		int countResidenza = 0;
		
		if(lstIndirizzi != null){
			for(CsAIndirizzo indirizzo: lstIndirizzi){
				if(indirizzo.getCsTbTipoIndirizzo().getId() == TipoIndirizzo.RESIDENZA_ID){
					residenza = true;
					if(indirizzo.getDataFineApp() == null || indirizzo.getDataFineApp().getTime() == nullDateTime)
						countResidenza++;
				}
			}
		}
		if(!residenza){
			ok = false;
			addError("Inserire almeno un indirizzo tipo RESIDENZA", null);
		}
		if(countResidenza > 1){
			ok = false;
			addError("Non può esistere più di un indirizzo di residenza ATTIVO: Annullare od Eliminare gli indirizzi non necessari", null);
		}
		
		//salvataggio
		if (ok) {
			lstIndirizziOld = resetListeIndirizzi(lstIndirizzi);
			String panelId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pnlToResetId");
			resetPanel(panelId);
			String lblClientId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pnlToUpdateId");
			RequestContext.getCurrentInstance().update(lblClientId);
	    }
		RequestContext.getCurrentInstance().addCallbackParam("saved", ok);
	}
	
	public class indirizziComparator implements Comparator<CsAIndirizzo> {
	    @Override
	    public int compare(CsAIndirizzo o1, CsAIndirizzo o2) {
	        Date dtIni1 = o1.getDataInizioApp();
	        if (dtIni1 == null) {
	        	try {
	        		dtIni1 = new Date(nullDateTime);
	        	} catch (Exception e) {}
	        }
	        Date dtIni2 = o2.getDataInizioApp();
	        if (dtIni2 == null) {
	        	try {
	        		dtIni2 = new Date(nullDateTime);
	        	} catch (Exception e) {}
	        }
	        Date dtFin1 = o1.getDataFineApp();
	        if (dtFin1 == null) {
	        	try {
	        		dtFin1 = new Date(nullDateTime);
	        	} catch (Exception e) {}
	        }
	        Date dtFin2 = o2.getDataFineApp();
	        if (dtFin2 == null) {
	        	try {
	        		dtFin2 = new Date(nullDateTime);
	        	} catch (Exception e) {}
	        }
	        if (dtFin2.compareTo(dtFin1) == 0) {
	        	return dtIni2.compareTo(dtIni1);
	        }
	        return dtFin2.compareTo(dtFin1);
	    }
	}
	
	public void annullaIndirizzo() {
		if (indirizzoSelezionato != null) {
			try {
				indirizzoSelezionato.setDataFineApp(dataAnnullamento);
			} catch (Exception e) {}
		}	
	}
	
	public void attivaIndirizzo() {
		if (indirizzoSelezionato != null) {
			try {
				indirizzoSelezionato.setDataFineApp(new Date(nullDateTime));
			} catch (Exception e) {}
		}	
	}
	
	public void eliminaIndirizzo() {
		if (lstIndirizzi != null && indirizzoSelezionato != null) {
			lstIndirizzi.remove(indirizzoSelezionato);
		}
	}

	public void trovaResidenza() throws SocioSanitarioException{
		String ti = getTipoIndirizzo();
		CsTbTipoIndirizzo tipo = getTipoIndirizzo(ti);
		boolean isResidenza = tipo!=null && tipo.getId() == TipoIndirizzo.RESIDENZA_ID;
		if (isResidenza && isCodFiscaleValido()){
			if(indirizzoAnagrafe==null) 
				indirizzoAnagrafe = ricercaIndirizzoAllProvenienza(codiceFiscale, tipoRicercaSoggetto);
		}else
			indirizzoAnagrafe = null;
		return;
	}
	
	public boolean isResidenzaAnagrafeRendered() {
		String tc = getTipoComune();
		String ti = getTipoIndirizzo();
		CsTbTipoIndirizzo tipo = getTipoIndirizzo(ti);
		boolean isResidenza = tipo!=null && tipo.getId() == TipoIndirizzo.RESIDENZA_ID;
		boolean rendered = isCodFiscaleValido() && isResidenza; //&& !StringUtils.isBlank(tc) && tc.equals(TIPO_LUOGO.COMUNE.getCodice());
		return rendered;
	}
	
	public boolean isCodFiscaleValido(){
		boolean valido = false;
		try{
			TempCodFiscManager tempMan = (TempCodFiscManager) getReferencedBean("tempCodFiscManager");
			valido =  !StringUtils.isBlank(codiceFiscale) && !tempMan.isTemporaneo(codiceFiscale);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return valido;
	}
	
	public boolean isSenzaFissaDimoraRendered(){
		String tc = getTipoComune();
		return tc != null && tc.equals(TIPO_LUOGO.SENZA_FISSA_DIMORA.getCodice());
	}
	
	public boolean isResidenzaComuneRendered() {
		String tc = getTipoComune();
		return tc != null && tc.equals(TIPO_LUOGO.COMUNE.getCodice());
	}
	
	public boolean isResidenzaNoComuneRendered() {
		String tc = getTipoComune();
		return tc != null && tc.equals(TIPO_LUOGO.ALTRO.getCodice());
	}
	
	public void reset(AjaxBehaviorEvent event) {
		lstIndirizzi = resetListeIndirizzi(lstIndirizziOld);
		String panelId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pnlToResetId");
		resetPanel(panelId);
		String lblClientId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pnlToUpdateId");
		RequestContext.getCurrentInstance().update(lblClientId);
	}
	
	protected void resetPanel(String clientId) {
		indirizzoAnagrafe = null;
		dataInizioAppComune = null;
		citta = null;
		indirizzo = null;
		civicoNumero = null;
		civicoAltro = null;
		dataInizioApp = null;
		indirizzoSelezionato = null;
		RequestContext.getCurrentInstance().reset(clientId);
		comuneNazioneResidenzaMan.setComuneValue();
		comuneNazioneResidenzaMan.getComuneMan().setComune(null);
		comuneNazioneResidenzaMan.getNazioneMan().setNazione(null);		
		tipoIndirizzo = null;
		resetTipoComune();
		
	}
	
	protected void resetTipoComune(){
		tipoComune = comuneCorrente!=null ? TIPO_LUOGO.COMUNE.getCodice() : TIPO_LUOGO.ALTRO.getCodice(); //default
	}
	
	protected void resetPanelIndirizzoComune() {
		citta = null;
		indirizzo = null;
		civicoNumero = null;
		civicoAltro = null;
		dataInizioApp = null;
		comuneNazioneResidenzaMan.setComuneValue();
		comuneNazioneResidenzaMan.getComuneMan().setComune(null);
		comuneNazioneResidenzaMan.getNazioneMan().setNazione(null);
	}
	
	protected void resetPanelIndirizzoNoComune() {
		indirizzoMan.setSelectedCivico(null);
		indirizzoMan.setSelectedIdVia(null);
		indirizzoMan.setSelectedIndirizzo(null);
		dataInizioAppComune = null;
		comuneNazioneResidenzaMan.setComuneValue();
		comuneNazioneResidenzaMan.getComuneMan().setComune(null);
		comuneNazioneResidenzaMan.getNazioneMan().setNazione(null);
	}	
	
	@Override
	public ArrayList<CsAIndirizzo> resetListeIndirizzi(List<?> lstIndirizziFrom) {
		if (lstIndirizziFrom == null) {
			return null;
		}
		ArrayList<CsAIndirizzo> lstIndirizziTo = new ArrayList<CsAIndirizzo>();
		for (Object indirizzo : lstIndirizziFrom) {
			CsAIndirizzo indirizzoFrom = (CsAIndirizzo)indirizzo;
			CsAIndirizzo indirizzoTo = new CsAIndirizzo();
			indirizzoTo.setCsAAnaIndirizzo(indirizzoFrom.getCsAAnaIndirizzo());
			indirizzoTo.setAnaIndirizzoId(indirizzoFrom.getAnaIndirizzoId());
			indirizzoTo.setCsASoggetto(indirizzoFrom.getCsASoggetto());
			indirizzoTo.setCsTbTipoIndirizzo(indirizzoFrom.getCsTbTipoIndirizzo());
			indirizzoTo.setDataInizioApp(indirizzoFrom.getDataInizioApp());
			indirizzoTo.setDataFineApp(indirizzoFrom.getDataFineApp());
			indirizzoTo.setUserIns(indirizzoFrom.getUserIns());
			indirizzoTo.setUsrMod(indirizzoFrom.getUsrMod());
			indirizzoTo.setDtIns(indirizzoFrom.getDtIns());
			indirizzoTo.setDtMod(indirizzoFrom.getDtMod());
			indirizzoTo.setDataInizioSys(indirizzoFrom.getDataInizioSys());

			lstIndirizziTo.add(indirizzoTo);
		}
		return lstIndirizziTo;
	}
	
	public void accettaIndirizzoAnagrafe(AjaxBehaviorEvent event) {
		indirizzoAnagrafe.setCsTbTipoIndirizzo(getTipoIndirizzoResidenza());
		if (lstIndirizzi == null) {
			lstIndirizzi = new ArrayList<CsAIndirizzo>();
		}
		lstIndirizzi.add(indirizzoAnagrafe);
	}
	
	public void aggiungiIndirizzo() {
		String enteCorrente = getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getCodCatastale();
		//validazione
		boolean err = false;
		String ti = getTipoIndirizzo();
		if (StringUtils.isBlank(ti)) {
			err = true;
			addError("Tipo Indirizzo è un campo obbligatorio", null);
		}
		if (TIPO_LUOGO.COMUNE.getCodice().equals(tipoComune)) {
			if(StringUtils.isBlank(indirizzoMan.getSelectedIndirizzo())){
				err = true;
				addError("Indirizzo è un campo obbligatorio", null);
			}
			if(StringUtils.isBlank(enteCorrente)){
				err= true;
				addError("Impossibile recuperare il Comune di Residenza: l'ente corrente non ha un codice catastale.", null);
			}
		} else if(TIPO_LUOGO.SENZA_FISSA_DIMORA.getCodice().equals(tipoComune)){
			//Niente da validare
		}else {
			
			if (getComuneNazioneResidenzaMan().isComuneRendered()) {
				if (getComuneNazioneResidenzaMan().getComuneResidenzaMan().getComune() == null) {
					err = true;
					addError("Comune di residenza è un campo obbligatorio", null);
				}
			} else {
				if (getComuneNazioneResidenzaMan().getNazioneResidenzaMan().getNazione() == null) {
					err = true;
					addError("Stato estero di residenza è un campo obbligatorio", null);
				}
			}
			String citta = getCitta();
			if (!getComuneNazioneResidenzaMan().isComuneRendered() && StringUtils.isBlank(citta)) {
				err = true;
				addError("Città è un campo obbligatorio", null);
			}
			String ind = getIndirizzo();
			if (StringUtils.isBlank(ind)) {
				err = true;
				addError("Indirizzo è un campo obbligatorio", null);
			}	
			
		}
				
		if (err) {
			return;
		}		
		
		CsAIndirizzo indirizzo = new CsAIndirizzo();
		CsAAnaIndirizzo anaIndirizzo = new CsAAnaIndirizzo();
		
		CsTbTipoIndirizzo tipoIndirizzo = getTipoIndirizzo(ti);
		indirizzo.setCsTbTipoIndirizzo(tipoIndirizzo);
		
		Date dtIniApp = null;
		
		if(TIPO_LUOGO.SENZA_FISSA_DIMORA.getCodice().equals(tipoComune)){
			anaIndirizzo.setIndirizzo(DataModelCostanti.SENZA_FISSA_DIMORA);
			dtIniApp = dataInizioApp == null ? getToday() : dataInizioApp;
		}else{
			if (TIPO_LUOGO.COMUNE.getCodice().equals(tipoComune)) {
				//nazione = NazioneResidenzaMan.getCurrNazione();
				anaIndirizzo.setIndirizzo(indirizzoMan.getSelectedIndirizzo());
				anaIndirizzo.setCivicoNumero(indirizzoMan.getSelectedCivico());
				anaIndirizzo.setCodiceVia(indirizzoMan.getSelectedIdVia());
				anaIndirizzo.setCivicoAltro(null);
				
				AmTabComuni comune = luoghiService.getComuneItaByBelfiore(enteCorrente);
				if (comune != null) {
						anaIndirizzo.setComDes(comune.getDenominazione());
						anaIndirizzo.setComCod(comune.getCodIstatComune());
						anaIndirizzo.setProv(comune.getSiglaProv());
				}
				dtIniApp = dataInizioAppComune == null ? getToday() : dataInizioAppComune;
			}else{
				if (comuneNazioneResidenzaMan.isComune()) {
					//comune italiano
					ComuneBean comune = comuneNazioneResidenzaMan.getComuneMan().getComune();
					//nazione = NazioneResidenzaMan.getCurrNazione();
					anaIndirizzo.setProv(comune == null ? null : comune.getSiglaProv());
					anaIndirizzo.setComCod(comune == null ? null : comune.getCodIstatComune());
					anaIndirizzo.setComDes(comune == null ? null : comune.getDenominazione());
				} else if (comuneNazioneResidenzaMan.isNazione()) {
					//stato estero
					AmTabNazioni nazione = comuneNazioneResidenzaMan.getNazioneMan().getNazione();
					//anaIndirizzo.setProv(nazione == null ? null : nazione.getSigla());
					anaIndirizzo.setComCod(null);
					anaIndirizzo.setComDes(getCitta());
					anaIndirizzo.setStatoCod(nazione == null ? null : nazione.getCodIstatNazione());
					anaIndirizzo.setStatoDes(nazione == null ? null : nazione.getNazione());
				}
				anaIndirizzo.setIndirizzo(getIndirizzo());
				anaIndirizzo.setCivicoNumero(getCivicoNumero());
				anaIndirizzo.setCivicoAltro(getCivicoAltro());
				dtIniApp = dataInizioApp == null ? getToday() : dataInizioApp;
			}
		}
		indirizzo.setDataInizioApp(dtIniApp);
		indirizzo.setCsAAnaIndirizzo(anaIndirizzo);
		
		if (lstIndirizzi == null) {
			lstIndirizzi = new ArrayList<CsAIndirizzo>();
		}
		
		lstIndirizzi.add(indirizzo);
		resetPanelIndirizzoNoComune();
		resetPanelIndirizzoComune();
	}
	


	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public String getAddressMessage() {
		return addressMessage;
	}

	public void setAddressMessage(String addressMessage) {
		this.addressMessage = addressMessage;
	}

	public AmTabComuni getComuneCorrente() {
		return comuneCorrente;
	}

	public void setComuneCorrente(AmTabComuni comuneCorrente) {
		this.comuneCorrente = comuneCorrente;
	}

	public void setListaTipoLuogoResidenza(List<SelectItem> listaTipoLuogoResidenza) {
		this.listaTipoLuogoResidenza = listaTipoLuogoResidenza;
	}
	
	public boolean isIndirizzoResidenzaTrovato(){
		return indirizzoAnagrafe != null;
	}
	
	public boolean isCodViaResidenzaTrovata(){
		return indirizzoAnagrafe!=null && !StringUtils.isBlank(indirizzoAnagrafe.getCsAAnaIndirizzo().getCodiceVia());
	}

	public String getTipoRicercaSoggetto() {
		return tipoRicercaSoggetto;
	}

	public void setTipoRicercaSoggetto(String tipoRicercaSoggetto) {
		this.tipoRicercaSoggetto = tipoRicercaSoggetto;
	}

}

