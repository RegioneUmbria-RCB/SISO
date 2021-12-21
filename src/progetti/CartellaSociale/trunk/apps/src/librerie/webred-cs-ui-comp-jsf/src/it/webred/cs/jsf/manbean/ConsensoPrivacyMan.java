package it.webred.cs.jsf.manbean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import it.webred.cs.jsf.interfaces.IConsensoPrivacy;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.DatiPrivacyDTO;

@ManagedBean
@NoneScoped
public class ConsensoPrivacyMan extends CsUiCompBaseBean implements IConsensoPrivacy{ 
	
	private static final long serialVersionUID = 1L;
	
	private DatiPrivacyDTO sp;
	private boolean utenteAnonimo;
	private String cf;
	private Long organizzazioneId;
	private Long schedaUdcId;
	
	private boolean disabilitaSottoscrizione;
	
	private String privacy;
	private Date privacyDate;
	private boolean privacySottoscrivi;
	private String condividiCentriImpiego;
	private boolean beneficiarioRdC;
	
	private SsSchedaSessionBeanRemote ssSchedaSegrService = 
			(SsSchedaSessionBeanRemote) getEjb("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

	public ConsensoPrivacyMan(){}
	
	public ConsensoPrivacyMan(String cf, Long organizzazioneId, Boolean anonimo, Boolean rdc) {
		privacy = null;
		privacySottoscrivi = false;
		privacyDate = null;
		condividiCentriImpiego=null;
		this.beneficiarioRdC = rdc;
		this.cf = cf;
		this.organizzazioneId = organizzazioneId;
		this.disabilitaSottoscrizione = false;
		this.utenteAnonimo = anonimo;
		aggiornaDati();
	}
	
	
	private void aggiornaDati() {
		if(!StringUtils.isEmpty(cf) && organizzazioneId != null && organizzazioneId > 0) {
			if(!utenteAnonimo){
				try {
					DatiPrivacyDTO privacyDto = new DatiPrivacyDTO();
					fillEnte(privacyDto);
					privacyDto.setCf(cf);
					privacyDto.setOrganizzazioneId(organizzazioneId);
					sp = ssSchedaSegrService.findSchedaPrivacyByCfEnte(privacyDto);

					this.privacySottoscrivi = sp != null ? true : false;
					this.condividiCentriImpiego = (sp!=null && sp.getFlagCentriImpiego()!=null) ? (sp.getFlagCentriImpiego() ? "SI" : "NO") : null;
					
					this.aggiornaStato();
						
				} catch (Exception e) {
					logger.error(e);
					addError("lettura.error","Non è stato possibile verificare la sottoscrizione del modulo privacy per il soggetto");
				}
			}else{
				disabilitaSottoscrizione = true;
				logger.warn("Il soggetto è ANONIMO: impossibile verificare lo stato della scheda privacy!");
			}
		
		}else if(StringUtils.isEmpty(cf))
			logger.warn("Il soggetto non ha CF valorizzato: impossibile verificare lo stato della scheda privacy!");
	}
	
	public boolean valida() {
		boolean valida = true;
		if(this.privacySottoscrivi) {
			if(!utenteAnonimo) {
				if(privacyDate == null) {
					valida = false;
					this.addMessage("Errore sottoscrizione consenso", "inserire la data di sottoscrizione", FacesMessage.SEVERITY_WARN);
				}
			}else{
				valida = false;
				this.addMessage("Errore sottoscrizione consenso", "Modulo privacy non sottoscrivibile per utente ANONIMO", FacesMessage.SEVERITY_ERROR);
			}
		}
		return valida;
	}
	
	@Override
	public boolean salva() {
		boolean esito = true;
		try {
			if(sp == null && valida() && !this.utenteAnonimo && this.privacySottoscrivi) {
				sp = new DatiPrivacyDTO();
				fillEnte(sp);
				
				sp.setCf(cf);
				sp.setOrganizzazioneId(organizzazioneId);			
				sp.setSchedaId(schedaUdcId);
				sp.setDtSottoscrizione(privacyDate);
				sp.setBeneficiarioRdC(beneficiarioRdC);
				if(!StringUtils.isEmpty(condividiCentriImpiego))
					sp.setFlagCentriImpiego("SI".equalsIgnoreCase(condividiCentriImpiego));
				
				ssSchedaSegrService.saveConsensoPrivacy(sp);
				this.aggiornaDati();
				
				this.addMessage("Sottoscrizione consenso", "Salvataggio avvenuto con successo", FacesMessage.SEVERITY_INFO);
				RequestContext.getCurrentInstance().addCallbackParam("saved", true);
				//RequestContext.getCurrentInstance().update("pnlInfoPrivacy");
			}
		} catch (Exception e) {
			sp = null;
			this.addError("Sottoscrizione consenso", "Errore "+e.getMessage());
			logger.error(e.getMessage(), e);
			esito = false;
		}
			
		
		return esito;
	}
	
	private void aggiornaStato() {
		this.privacy = null;
		this.privacyDate = null;
		if(sp!=null && sp.getDtSottoscrizione()!=null) {
			privacyDate = sp.getDtSottoscrizione();
			String organizzazione = sp.getOrganizzazione() !=null ? " al "+this.getLabelOrganizzazione()+" di "+ sp.getOrganizzazione().getNome() : "";
			String sdata = new SimpleDateFormat("dd/MM/yyyy").format(privacyDate);
			privacy = "Consenso al trattamento dei dati personali rilasciato "+ organizzazione +" in data " + sdata;
			if(this.beneficiarioRdC) {
				if(sp.getFlagCentriImpiego()!=null)
					privacy += ", "+(sp.getFlagCentriImpiego() ? " AUTORIZZATA " : " NEGATA ")+"condivisione dei dati con i \"centri per l'impiego\" per la fruizione del Reddito di Cittadinanza.";
				else
					privacy += ", NON ESPRESSA condivisione dei dati con i \"centri per l'impiego\" per la fruizione del Reddito di Cittadinanza.";
			}
		}
	}
	
	@Override
	public boolean isUtenteAnonimo() {
		return utenteAnonimo;
	}


	public void setUtenteAnonimo(boolean utenteAnonimo) {
		this.utenteAnonimo = utenteAnonimo;
	}


	public String getCf() {
		return cf;
	}


	public void setCf(String cf) {
		this.cf = cf;
	}


	@Override
	public String getPrivacy() {
		return privacy;
	}


	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}


	@Override
	public Date getPrivacyDate() {
		return privacyDate;
	}


	@Override
	public void setPrivacyDate(Date privacyDate) {
		this.privacyDate = privacyDate;
	}


	@Override
	public boolean isPrivacySottoscrivi() {
		return privacySottoscrivi;
	}

	public void setPrivacySottoscrivi(boolean privacySottoscrivi) {
		this.privacySottoscrivi = privacySottoscrivi;
		if (this.utenteAnonimo && privacySottoscrivi) {
			this.utenteAnonimo = false;
		}
	}

	
	public void aggiornaCodiceFiscale(String codiceFiscale, Boolean anonimo) {
		this.cf = codiceFiscale;
		this.utenteAnonimo = anonimo;
		this.aggiornaDati();
		
	}


	public boolean isDisabilitaSottoscrizione() {
		return disabilitaSottoscrizione;
	}


	public void setDisabilitaSottoscrizione(boolean disabilitaSottoscrizione) {
		this.disabilitaSottoscrizione = disabilitaSottoscrizione;
	}



	public boolean isBeneficiarioRdC() {
		return beneficiarioRdC;
	}


	public void setBeneficiarioRdC(boolean beneficiarioRdC) {
		this.beneficiarioRdC = beneficiarioRdC;
	}


	public Long getSchedaUdcId() {
		return schedaUdcId;
	}


	public void setSchedaUdcId(Long schedaUdcId) {
		this.schedaUdcId = schedaUdcId;
	}


	public String getCondividiCentriImpiego() {
		return condividiCentriImpiego;
	}


	public void setCondividiCentriImpiego(String condividiCentriImpiego) {
		this.condividiCentriImpiego = condividiCentriImpiego;
	}
	
	
}
