package it.webred.cs.jsf.manbean.superc;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.model.CsTbTipoIndirizzo;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.interfaces.IResidenza;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

public abstract class ResidenzaMan extends CsUiCompBaseBean implements IResidenza {
	
	
	protected static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
	private String enteValue = "E";
	private String altroValue = "A";
	private String senzaFissaDimoraValue = "SFD";
	
	protected static long nullDateTime;
	static {
		try {
			nullDateTime = SDF.parse("31/12/9999").getTime();
		} catch (Exception e) {}		
	}
	
	public long getNullDateTime() {
		return nullDateTime;
	}
	
	public Date getToday() {
		Date today = null;
		try {
			today = SDF.parse(SDF.format(new Date()));
		} catch (Exception e) {}
		return today;
	}

	public abstract List<?> getLstIndirizzi();

	public abstract List<?> getLstIndirizziForKey(String key);
	
	public abstract void salvaIndirizzi();
	
	public String getPanelHeader() {
		return "Indirizzi inseriti";
	}
	
	public String getTextIfEmpty() {
		return "Nessun indirizzo inserito";
	}
	
	public String getTextIfActiveEmpty() {
		return "Nessun indirizzo attivo";
	}
	
	public String getActiveText() {
		return "ATTIVO";
	}
	
	public String getButtonTooltip() {
		return "Clicca per accedere alla gestione degli indirizzi";
	}
	
	public String getDialogHeader() {
		return "Gestione indirizzi";
	}
	
	public String getEnteLabel() {
		return "COMUNE";
	}
	
	public boolean isIndirizziEmpty() {
		List<?> lstIndirizzi = getLstIndirizzi();
		return lstIndirizzi == null || lstIndirizzi.size() == 0;
	}
	
	public abstract String getWidgetVar();
	
	public List<CsTbTipoIndirizzo> getBeanLstTipiIndirizzo() {
		List<CsTbTipoIndirizzo> beanLstTipiIndirizzo = null;
		try {
			AccessTableConfigurazioneSessionBeanRemote tipoIndirizzoBean = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			beanLstTipiIndirizzo = tipoIndirizzoBean.getTipoIndirizzo(bo);
		} catch (NamingException e) {
			logger.error(e);
		}
		return beanLstTipiIndirizzo;
	}
	
	@SuppressWarnings("unused")
	public ArrayList<SelectItem> getLstTipiIndirizzo() {
		ArrayList<SelectItem> lstTipiIndirizzo = new ArrayList<SelectItem>();
		lstTipiIndirizzo.add(new SelectItem(null, "- seleziona -"));
		List<CsTbTipoIndirizzo> beanLstTipiIndirizzo = getBeanLstTipiIndirizzo();
		if (beanLstTipiIndirizzo != null) {
			for (CsTbTipoIndirizzo tipoIndirizzo : beanLstTipiIndirizzo) {
				lstTipiIndirizzo.add(new SelectItem(tipoIndirizzo.getId(), tipoIndirizzo.getDescrizione()));
			}
		}
		return lstTipiIndirizzo;
	}
	
	public CsTbTipoIndirizzo getTipoIndirizzoResidenza() {
		CsTbTipoIndirizzo tipoIndirizzoResidenza = null;
		List<CsTbTipoIndirizzo> beanLstTipiIndirizzo = getBeanLstTipiIndirizzo();
		if (beanLstTipiIndirizzo != null) {
			for (CsTbTipoIndirizzo tipoIndirizzo : beanLstTipiIndirizzo) {
				if (tipoIndirizzo.getDescrizione().equalsIgnoreCase("Residenza")) {
					tipoIndirizzoResidenza = tipoIndirizzo;
					break;
				}
			}
		}	
		return tipoIndirizzoResidenza;
	}
	
	public CsTbTipoIndirizzo getTipoIndirizzoDomicilio() {
		CsTbTipoIndirizzo tipoIndirizzoResidenza = null;
		List<CsTbTipoIndirizzo> beanLstTipiIndirizzo = getBeanLstTipiIndirizzo();
		if (beanLstTipiIndirizzo != null) {
			for (CsTbTipoIndirizzo tipoIndirizzo : beanLstTipiIndirizzo) {
				if (tipoIndirizzo.getDescrizione().equalsIgnoreCase("Domicilio")) {
					tipoIndirizzoResidenza = tipoIndirizzo;
					break;
				}
			}
		}	
		return tipoIndirizzoResidenza;
	}

		
	public abstract void annullaIndirizzo();
	
	public abstract void eliminaIndirizzo();

	public String getEnteValue() {
		return enteValue;
	}

	public void setEnteValue(String enteValue) {
		this.enteValue = enteValue;
	}

	public String getAltroValue() {
		return altroValue;
	}

	public void setAltroValue(String altroValue) {
		this.altroValue = altroValue;
	}
	
	public String getSenzaFissaDimoraValue() {
		return senzaFissaDimoraValue;
	}

	public void setSenzaFissaDimoraValue(String senzaFissaDimoraValue) {
		this.senzaFissaDimoraValue = senzaFissaDimoraValue;
	}

	public abstract void reset(AjaxBehaviorEvent event);
	
	protected abstract void resetPanel(String clientId);
	
	public abstract void accettaIndirizzoAnagrafe(AjaxBehaviorEvent event);
		
	public abstract void aggiungiIndirizzo();
	
	public abstract Serializable getIndirizzoResidenzaCodFisc(String codFisc);
	
	protected abstract List<?> resetListeIndirizzi(List<?> lstIndirizziFrom);

}
