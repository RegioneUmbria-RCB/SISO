package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePersonaCiviciSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsTbTipoIndirizzo;
import it.webred.cs.jsf.manbean.superc.ResidenzaMan;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ejb.utility.ClientUtility;
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
import javax.naming.NamingException;

import org.primefaces.context.RequestContext;

@ManagedBean
@NoneScoped
public class ResidenzaCsaMan extends ResidenzaMan implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected List<CsAIndirizzo> lstIndirizzi;
	protected List<CsAIndirizzo> lstIndirizziOld;
	
	private String tipoIndirizzo;
	private String tipoComune;
	private String nomeComune;
	private CsAIndirizzo indirizzoAnagrafe;
	private Date dataInizioAppComune;	
	private String citta;
	private String indirizzo;
	private String civicoNumero;
	private String civicoAltro;
	private String codiceFiscale;
	private Date dataInizioApp;
	private Date dataAnnullamento = new Date();
		
	private CsAIndirizzo indirizzoSelezionato;
	
	private String warningMessage;
	
	private ComuneNazioneResidenzaMan comuneNazioneResidenzaMan = new ComuneNazioneResidenzaMan();
	
	private IndirizzoMan indirizzoMan = new IndirizzoMan();

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
		if (tipoComune == null) {
			tipoComune = getEnteValue(); //default
		}
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
			if(indirizzo.getCsTbTipoIndirizzo().getId() == DataModelCostanti.TipiIndirizzi.RESIDENZA_ID &&
					(indirizzo.getDataFineApp() == null || indirizzo.getDataFineApp().getTime() == getNullDateTime())) {
				indirizzoAttivo = indirizzo;
			}
		}
		return indirizzoAttivo;
	}

	public String getWidgetVar() {
		return "residenzaCsaDialog";
	}
	
	@Override
	public String getEnteLabel() {
		
		//setto il clientId per l'update del pannello Residenza Estero
		/*UIComponent comp = findComponent("pnlResidenzaNoComune");
		comuneNazioneResidenzaMan.setClientIdToUpdate(comp.getClientId());*/
		
		String denominazione = null;
		String belfiore = getUser().getCurrentEnte();
		try {
			AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
			AmTabComuni comune = bean.getComuneByBelfiore(belfiore);
			if (comune != null) {
				denominazione = comune.getDenominazione();
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		if (denominazione == null) {
			return "COMUNE " + belfiore;
		}
		nomeComune = denominazione.toUpperCase();
		
		return "COMUNE DI " + denominazione.toUpperCase();
	}
	
	public void salvaIndirizzi() {
		
		boolean ok = true;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		//controlli
		boolean residenza = false;
		int countResidenza = 0;
		
		if(lstIndirizzi != null){
			for(CsAIndirizzo indirizzo: lstIndirizzi){
				if(indirizzo.getCsTbTipoIndirizzo().getId() == DataModelCostanti.TipiIndirizzi.RESIDENZA_ID){
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

	public boolean isResidenzaAnagrafeRendered() {
		indirizzoAnagrafe = null;
		String tc = getTipoComune();
		String ti = getTipoIndirizzo();
		boolean isResidenza = false;
		for (CsTbTipoIndirizzo tipo : getBeanLstTipiIndirizzo()) {
			if (ti != null && !ti.equals("") && tipo.getId() == new Long(ti) && tipo.getDescrizione().trim().equalsIgnoreCase("Residenza")) {
				isResidenza = true;
				break;
			}
		}		
		boolean rendered = (codiceFiscale != null && !"".equals(codiceFiscale) && tc != null && tc.equals(getEnteValue()) && isResidenza);
		if (rendered) {
			indirizzoAnagrafe = getIndirizzoResidenzaCodFisc(getCodiceFiscale());
		}
		return rendered;
	}
	
	public boolean isResidenzaComuneRendered() {
		String tc = getTipoComune();
		return tc != null && tc.equals(getEnteValue());
	}
	
	public boolean isResidenzaNoComuneRendered() {
		String tc = getTipoComune();
		return tc != null && tc.equals(getAltroValue());
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
		comuneNazioneResidenzaMan.setValue(comuneNazioneResidenzaMan.getComuneValue());
		comuneNazioneResidenzaMan.getComuneMan().setComune(null);
		comuneNazioneResidenzaMan.getNazioneMan().setNazione(null);		
		tipoIndirizzo = null;
		tipoComune = getEnteValue();
	}
	
	protected void resetPanelIndirizzoComune() {
		citta = null;
		indirizzo = null;
		civicoNumero = null;
		civicoAltro = null;
		dataInizioApp = null;
		comuneNazioneResidenzaMan.setValue(comuneNazioneResidenzaMan.getComuneValue());
		comuneNazioneResidenzaMan.getComuneMan().setComune(null);
		comuneNazioneResidenzaMan.getNazioneMan().setNazione(null);
	}
	
	protected void resetPanelIndirizzoNoComune() {
		indirizzoMan.setSelectedCivico(null);
		indirizzoMan.setSelectedIdVia(null);
		indirizzoMan.setSelectedIndirizzo(null);
		dataInizioAppComune = null;
		comuneNazioneResidenzaMan.setValue(comuneNazioneResidenzaMan.getComuneValue());
		comuneNazioneResidenzaMan.getComuneMan().setComune(null);
		comuneNazioneResidenzaMan.getNazioneMan().setNazione(null);
	}	
	
	@Override
	public CsAIndirizzo getIndirizzoResidenzaCodFisc(String codFisc) {
		CsAIndirizzo indirizzoRes = null;
		try {
			AccessTablePersonaCiviciSessionBeanRemote bean = (AccessTablePersonaCiviciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTablePersonaCiviciSessionBean");
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(codFisc);
			indirizzoRes = bean.getIndirizzoResidenzaByCodFisc(dto);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return indirizzoRes;
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
		FacesContext context = FacesContext.getCurrentInstance();
		
		//validazione
		boolean err = false;
		String ti = getTipoIndirizzo();
		if (ti == null || ti.equalsIgnoreCase("")) {
			err = true;
			addError("Tipo Indirizzo è un campo obbligatorio", null);
		}
		if (tipoComune.equals(getEnteValue())) {
			if(indirizzoMan.getSelectedIndirizzo() == null){
				err = true;
				addError("Indirizzo è un campo obbligatorio", null);
			}
		} else {
			
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
			if (!getComuneNazioneResidenzaMan().isComuneRendered() && (citta == null || citta.equals(""))) {
				err = true;
				addError("Città è un campo obbligatorio", null);
			}
			String ind = getIndirizzo();
			if (ind == null || ind.equalsIgnoreCase("")) {
				err = true;
				addError("Indirizzo è un campo obbligatorio", null);
			}	
			
		}
				
		if (err) {
			return;
		}		
		
		CsAIndirizzo indirizzo = new CsAIndirizzo();
		CsAAnaIndirizzo anaIndirizzo = new CsAAnaIndirizzo();
		CsTbTipoIndirizzo tipoIndirizzo = null;
		
		for (CsTbTipoIndirizzo tipo : getBeanLstTipiIndirizzo()) {
			if (ti != null && !ti.equals("") && tipo.getId() == new Long(ti)) {
				tipoIndirizzo = tipo;
			}
		}
		indirizzo.setCsTbTipoIndirizzo(tipoIndirizzo);
		
		AmTabNazioni nazione = null;
		Date dtIniApp = null;
		if (tipoComune.equals(getEnteValue())) {
			nazione = NazioneResidenzaMan.getCurrNazione();
			anaIndirizzo.setIndirizzo(indirizzoMan.getSelectedIndirizzo());
			anaIndirizzo.setCivicoNumero(indirizzoMan.getSelectedCivico());
			anaIndirizzo.setCodiceVia(indirizzoMan.getSelectedIdVia());
			anaIndirizzo.setCivicoAltro(null);
			try {
				AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
				AmTabComuni comune = bean.getComuneByBelfiore(getUser().getCurrentEnte());
				if (comune != null) {
					anaIndirizzo.setComDes(comune.getDenominazione());
					anaIndirizzo.setComCod(comune.getCodIstatComune());
					anaIndirizzo.setProv(comune.getSiglaProv());
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
			dtIniApp = dataInizioAppComune == null ? getToday() : dataInizioAppComune;
		}else{
			if (comuneNazioneResidenzaMan.getValue().equalsIgnoreCase(comuneNazioneResidenzaMan.getComuneValue())) {
				//comune italiano
				ComuneBean comune = comuneNazioneResidenzaMan.getComuneMan().getComune();
				nazione = NazioneResidenzaMan.getCurrNazione();
				anaIndirizzo.setProv(comune == null ? null : comune.getSiglaProv());
				anaIndirizzo.setComCod(comune == null ? null : comune.getCodIstatComune());
				anaIndirizzo.setComDes(comune == null ? null : comune.getDenominazione());
			} else if (comuneNazioneResidenzaMan.getValue().equalsIgnoreCase(comuneNazioneResidenzaMan.getNazioneValue())) {
				//stato estero
				nazione = comuneNazioneResidenzaMan.getNazioneMan().getNazione();
				anaIndirizzo.setProv(nazione == null ? null : nazione.getSigla());
				anaIndirizzo.setComCod(null);
				anaIndirizzo.setComDes(getCitta());
			}
			anaIndirizzo.setIndirizzo(getIndirizzo());
			anaIndirizzo.setCivicoNumero(getCivicoNumero());
			anaIndirizzo.setCivicoAltro(getCivicoAltro());
			dtIniApp = dataInizioApp == null ? getToday() : dataInizioApp;
		}
		anaIndirizzo.setStatoCod(nazione == null ? null : nazione.getCodIstatNazione());
		anaIndirizzo.setStatoDes(nazione == null ? null : nazione.getNazione());
		
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

}

