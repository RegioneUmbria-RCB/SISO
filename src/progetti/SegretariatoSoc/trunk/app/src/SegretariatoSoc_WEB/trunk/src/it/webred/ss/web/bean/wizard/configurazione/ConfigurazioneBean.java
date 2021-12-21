package it.webred.ss.web.bean.wizard.configurazione;

import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsPuntoContatto;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsRelUffPcontOrgPK;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.ejb.client.ConfigurazioneSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class ConfigurazioneBean extends SegretariatoSocBaseBean {

	private static final long serialVersionUID = 7376024607307005148L;

	private ConfigurazioneSessionBeanRemote confService = (ConfigurazioneSessionBeanRemote) getEjb("SegretariatoSoc",
			"SegretariatoSoc_EJB", "ConfigurazioneSessionBean");

	private List<SsOOrganizzazione> lstOrganizzazioni;
	private List<SsUfficio> lstUffici;
	private List<SsPuntoContatto> lstPuntiContatto;
	private List<SsRelUffPcontOrg> lstRelazioni;

	private SsUfficio nuovoUfficio;
	private SsPuntoContatto nuovoPContatto;
	private SsRelUffPcontOrg nuovaRelazione;

	private Long idOrganizzazione;
	private String idUfficio;
	private Long idPuntoContatto;

	private List<SsUfficio> selectedUffici;
	private List<SsPuntoContatto> selectedPuntiContatto;
	private List<SsRelUffPcontOrg> selectedRelazioni;
	
	public void aggiungiNuovoUfficio() {
		initUfficio();
	}

	public void modificaUfficioSel() {
		if(this.selectedUffici==null || this.selectedUffici.isEmpty() || this.selectedUffici.size()>1){
			this.addWarningMessage("Modifica", "Selezionare un "+(this.selectedUffici.size()>1 ? "solo" : "")+" ufficio");
			
			initUfficio();
		}else{
			this.nuovoUfficio = this.selectedUffici.get(0);
			RequestContext.getCurrentInstance().addCallbackParam("loadUfficioDialog", true);	
		}
	}

	public void aggiungiNuovoPuntoContatto() {
		this.nuovoPContatto = new SsPuntoContatto();
	}

	public void modificaPuntoContattoSel() {
		
		if(this.selectedPuntiContatto==null || this.selectedPuntiContatto.isEmpty() || this.selectedPuntiContatto.size()>1){
			this.addWarningMessage("Modifica", "Selezionare un "+(this.selectedPuntiContatto.size()>1 ? "solo" : "")+" punto di contatto");
			this.nuovoPContatto = new SsPuntoContatto();
		}else{
			this.nuovoPContatto = this.selectedPuntiContatto.get(0);
			RequestContext.getCurrentInstance().addCallbackParam("loadPuntoContattoDialog", true);	
		}
	}

	private final String TEMPLATE = "Comune di ORIG_DESC_ORGANIZZAZIONE - Progetto SISO \r\n"
			+ " Si segnala che in data ORIG_DATA_ACCESSO il soggetto: \r\n" + " \r\n" + " Cognome: ORIG_COGNOME\r\n"
			+ " Nome: ORIG_NOME\r\n" + " \r\n"
			+ " ha effettuato un colloquio, con conseguente creazione della seguente scheda di LABEL_UDC.\r\n" + " \r\n"
			+ " L'operatore di sportello, ORIG_COGNOME_OPERATORE ORIG_NOME_OPERATORE, ha ritenuto opportuno inviarlo alla Vostra organizzazione \r\n"
			+ " Il servizio inviante ha i seguenti recapiti: num. di telefono: ORIG_TELEFONO,  email ORIG_EMAIL\r\n"
			+ " \r\n" + " Messaggio inviato da un sistema automatico. Non rispondere a questa e-mail.\r\n" + " \r\n"
			+ " Zona Sociale n. ORIG_ZONA_SOC \r\n" + " Lo Staff";


	@PostConstruct
	private void init() {

		this.initUfficio();

		this.nuovoPContatto = new SsPuntoContatto();
		this.nuovaRelazione = new SsRelUffPcontOrg();

		this.caricaOrganizzazioni();
		this.caricaUffici();
		this.caricaPuntiContatto();
		this.caricaRelazioni();
	}

	public Long getIdOrganizzazione() {
		return idOrganizzazione;
	}

	public void setIdOrganizzazione(Long idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}

	public String getIdUfficio() {
		return idUfficio;
	}

	public void setIdUfficio(String idUfficio) {
		this.idUfficio = idUfficio;
	}

	public Long getIdPuntoContatto() {
		return idPuntoContatto;
	}

	public void setIdPuntoContatto(Long idPuntoContatto) {
		this.idPuntoContatto = idPuntoContatto;
	}

	private void initUfficio() {

		nuovoUfficio = new SsUfficio();
		nuovoUfficio.setTemplateMailInvio(TEMPLATE);
		nuovoUfficio.setVisColCsoc(true);
		nuovoUfficio.setVisColInterventi(true);

	}

	private void caricaOrganizzazioni() {

		try {

			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			lstOrganizzazioni = confService.getOrganizzazioniAccesso(cet);

		} catch (Exception e) {
			addError("caricamento.error");
			logger.error(e.getMessage(), e);
		}

	}

	private void caricaUffici() {
		try {

			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			lstUffici = confService.getUffici(cet);

		} catch (Exception e) {
			addError("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	private void caricaPuntiContatto() {
		try {

			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			lstPuntiContatto = confService.getPuntiContatto(cet);

		} catch (Exception e) {
			addError("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	private void caricaRelazioni() {
		try {

			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			lstRelazioni = confService.getRelazioni(cet);

		} catch (Exception e) {
			addError("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	public List<SelectItem> getPuntiContatto() {
		List<SelectItem> lst = new ArrayList<SelectItem>();
		for (SsPuntoContatto u : lstPuntiContatto) {
			if (u.getAbilitato())
				lst.add(new SelectItem(u.getId(), u.getNome()));
		}
		return lst;
	}

	public List<SelectItem> getUffici() {
		List<SelectItem> lst = new ArrayList<SelectItem>();
		for (SsUfficio u : this.lstUffici) {
			if (u.getAbilitato())
				lst.add(new SelectItem(u.getId(), u.getNome()));
		}
		return lst;
	}

	public List<SelectItem> getOrganizzazioni() {
		List<SelectItem> lst = new ArrayList<SelectItem>();
		for (SsOOrganizzazione u : this.lstOrganizzazioni) {
			if (u.getAbilitato())
				lst.add(new SelectItem(u.getId(), u.getNome()));
		}
		return lst;
	}

	public List<SsOOrganizzazione> getLstOrganizzazioni() {
		return lstOrganizzazioni;
	}

	public void setLstOrganizzazioni(List<SsOOrganizzazione> lstOrganizzazioni) {
		this.lstOrganizzazioni = lstOrganizzazioni;
	}

	public List<SsUfficio> getLstUffici() {
		return lstUffici;
	}

	public void setLstUffici(List<SsUfficio> lstUffici) {
		this.lstUffici = lstUffici;
	}

	// Ufficio

	public void salvaUfficio() {

		boolean salva = true;

		if (!StringUtils.isBlank(nuovoUfficio.getNome())) {
			try {
				
				for (SsUfficio u : this.lstUffici) {
					if (u.getNome().equalsIgnoreCase((nuovoUfficio.getNome())) && (!u.getId().equals(nuovoUfficio.getId()))) {
						addWarning("salva.warning", "Il nome dell'ufficio è già presente");
						salva = false;
						// break;
					}
				}
				if (salva) {
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(nuovoUfficio);
					confService.salvaUfficio(dto);

					RequestContext.getCurrentInstance().addCallbackParam("saved", true);	
					addInfo("salva.ok");
					
				}
			} catch (Exception e) {
				addError("salva.error");
				logger.error(e.getMessage(), e);
			}
		} else
			addWarning("salva.warning", "Inserire almeno il nome dell'ufficio");
	}

	public void aggiornaUffici(){
		selectedUffici=null;
		caricaUffici();
		initUfficio();
	}
	
	public void aggiornaPuntiContatto(){
		selectedPuntiContatto = null;
		caricaPuntiContatto();
		nuovoPContatto = new SsPuntoContatto();
	}
	
	public void attivaUffici() {

		if (selectedUffici != null && !selectedUffici.isEmpty()) {
			try {

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);

				for (SsUfficio u : selectedUffici) {
					dto.setObj(u);
					dto.setObj2(Boolean.TRUE);
					confService.gestisciAttivazioneUfficio(dto);
				}


				this.caricaUffici();
				this.caricaRelazioni();
				this.selectedUffici=null;
				addInfo("attiva.ok");

			} catch (Exception e) {
				addError("attiva.error");
				logger.error(e.getMessage(), e);
			}
		} else
			addWarning("salva.warning", "Seleziona almeno un Ufficio");

	}

	public void disattivaUffici() {
		if (selectedUffici != null) {
			try {

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);

				for (SsUfficio u : selectedUffici) {
					dto.setObj(u);
					dto.setObj2(Boolean.FALSE);
					confService.gestisciAttivazioneUfficio(dto);
				}

				this.caricaUffici();
				this.caricaRelazioni();
				this.selectedUffici = null;
				addInfo("disattiva.ok");

			} catch (Exception e) {
				addError("disattiva.error");
				logger.error(e.getMessage(), e);
			}
		} else
			addWarning("ss.configurazione.warning", "Seleziona almeno un Ufficio");

	}

	public void eliminaUffici() {

		List<SsUfficio> eliminati = new ArrayList<SsUfficio>();
		List<SsUfficio> relExists = new ArrayList<SsUfficio>();

		if (selectedUffici != null) {
			try {

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for (SsUfficio u : selectedUffici) {
				    int i = 0;
				    boolean esiste = false;
				    while(!esiste && i<lstRelazioni.size()){
				    	SsRelUffPcontOrg rel = lstRelazioni.get(i);
				    	if(rel.getId().getUfficioId().equals(u.getId())) esiste = true;
				    	i++;
				    }
					
					if (!esiste) {
						logger.debug("Elimino Ufficio " + u.getNome());
						dto.setObj(u);
						confService.eliminaUfficio(dto);
						eliminati.add(u);	
					}else
						relExists.add(u);
				}

				
				if(!relExists.isEmpty()){
					String s = "";
					for(SsUfficio u : relExists) s+= u.getNome()+", ";
					s = s.substring(0,s.length()-2);
					
					addWarning("salva.warning", "Non è possibile eliminare i seguenti uffici: "+s+". \nProcedere prima all'eliminazione delle relazioni organizzazione/ufficio/punto di contatto esistenti.");
				}
				
				if(!eliminati.isEmpty()){
					String s = "";
					for(SsUfficio u : eliminati) s+= u.getNome()+", ";
					s = s.substring(0,s.length()-2);
					
					addInfoMessage("Eliminazione uffici", "I seguenti uffici sono stati eliminati correttamente:"+s);	
				}
				
				
				this.aggiornaUffici();

			} catch (Exception e) {
				addError("elimina.error");
				logger.error(e.getMessage(), e);
			}
		} else
			addWarning("salva.warning", "Seleziona almeno un Ufficio");

	}

	// Punto Contatto

	public void salvaPuntoContatto() {

		boolean salva = true;

		if (!StringUtils.isBlank(nuovoPContatto.getNome())) {
			try {

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);

				for (SsPuntoContatto pc : this.lstPuntiContatto) {
					dto.setObj(pc);
					if (pc.getNome().equalsIgnoreCase((nuovoPContatto.getNome())) && !pc.getId().equals(nuovoPContatto.getId())) {
						addWarning("salva.warning", "Il nome del Punto di Contatto è già presente");

						salva = false;
						// break;
					}
				}

				if (salva) {
					dto.setObj(nuovoPContatto);
					confService.salvaPuntoContatto(dto);

					addInfo("salva.ok");
					RequestContext.getCurrentInstance().addCallbackParam("saved", true);
				}

			} catch (Exception e) {
				addError("salva.error");
				logger.error(e.getMessage(), e);
			}
		} else
			addWarning("salva.warning", "Inserire almeno il nome del Punto di Contatto");

	}
	

	public void attivaPuntiContatto() {

		if (selectedPuntiContatto != null && !selectedPuntiContatto.isEmpty()) {
			try {

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);

				for (SsPuntoContatto u : selectedPuntiContatto) {
					dto.setObj(u);
					dto.setObj2(Boolean.TRUE);
					confService.gestisciAttivazionePuntoContatto(dto);
				}

				this.aggiornaPuntiContatto();
				this.caricaRelazioni();
				addInfo("attiva.ok");

			} catch (Exception e) {
				addError("attiva.error");
				logger.error(e.getMessage(), e);
			}
		} else
			addWarning("salva.warning", "Seleziona almeno un Punto di Contatto");

	}

	public void disattivaPuntiContatto() {

		if (selectedPuntiContatto != null && !selectedPuntiContatto.isEmpty()) {
			try {

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);

				for (SsPuntoContatto u : selectedPuntiContatto) {
					dto.setObj(u);
					dto.setObj2(Boolean.FALSE);
					confService.gestisciAttivazionePuntoContatto(dto);
				}

				this.aggiornaPuntiContatto();
				this.caricaRelazioni();
				addInfo("disattiva.ok");

			} catch (Exception e) {
				addError("disattiva.error");
				logger.error(e.getMessage(), e);
			}
		} else
			addWarning("salva.warning", "Seleziona almeno un Punto di Contatto");

	}

	public void eliminaPuntiContatto() {
				
		List<SsPuntoContatto> eliminati = new ArrayList<SsPuntoContatto>();
		List<SsPuntoContatto> relExists = new ArrayList<SsPuntoContatto>();

		if (selectedPuntiContatto != null) {
			try {

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for (SsPuntoContatto u : selectedPuntiContatto) {
				    int i = 0;
				    boolean esiste = false;
				    while(!esiste && i<lstRelazioni.size()){
				    	SsRelUffPcontOrg rel = lstRelazioni.get(i);
				    	if(rel.getId().getPuntoContattoId().equals(u.getId())) esiste = true;
				    	i++;
				    }
					
					if (!esiste) {
						logger.debug("Elimino Punto di Contatto " + u.getNome());
						dto.setObj(u);
						confService.eliminaPuntoContatto(dto);
						eliminati.add(u);	
					}else
						relExists.add(u);
				}

				
				if(!relExists.isEmpty()){
					String s = "";
					for(SsPuntoContatto u : relExists) s+= u.getNome()+", ";
					s = s.substring(0,s.length()-2);
					
					addWarning("salva.warning", "Non è possibile eliminare i seguenti punti di contatto: "+s+". Procedere prima all'eliminazione delle relazioni organizzazione/ufficio/punto di contatto esistenti.");
				}
				
				if(!eliminati.isEmpty()){
					String s = "";
					for(SsPuntoContatto u : eliminati) s+= u.getNome()+", ";
					s = s.substring(0,s.length()-2);
					
					addInfoMessage("Eliminazione punti di contatto", "I seguenti punti di contatto sono stati eliminati correttamente:"+s);	
				}
				
				this.aggiornaPuntiContatto();
				
			} catch (Exception e) {
				addError("elimina.error");
				logger.error(e.getMessage(), e);
			}
		} else
			addWarning("salva.warning", "Seleziona almeno un Punto di Contatto");

	}

	// Relazioni

	public void aggiungiRelazione() {
        boolean valida = true;
        String message = "";
        
        if(!(this.idOrganizzazione != null && this.idOrganizzazione>0)){ 
        	message += "organizzazione, ";
        	valida = false;
        }
        if(StringUtils.isEmpty(idUfficio)){ 
        	message += "ufficio, ";
        	valida = false;
        }
        if(!(this.idPuntoContatto != null && this.idPuntoContatto>0)){ 
        	message += "punto di contatto, ";
        	valida = false;
        }
        
        
		if (!valida) 
			addWarning("salva.warning", "Selezionare: "+ message.substring(0, message.length()-2));
		else{
			try {

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
			
				SsRelUffPcontOrgPK key = new SsRelUffPcontOrgPK();
				key.setOrganizzazioneId(idOrganizzazione);
				key.setUfficioId(new Long(idUfficio));
				key.setPuntoContattoId(idPuntoContatto);
				dto.setObj(key);
				
				confService.attivaRelazione(dto);

				aggiornaRelazioni();

				addInfo("salva.ok");

			} catch (Exception e) {
				addError("salva.error");
				logger.error(e.getMessage(), e);
			}
		} 
	}

	private void aggiornaRelazioni(){
		this.caricaRelazioni();
		this.idOrganizzazione = null;
		this.idUfficio = null;
		this.idPuntoContatto = null;
		this.selectedRelazioni = null;
	}
	
	public void attivaRelazione() {

		if (selectedRelazioni != null && !selectedRelazioni.isEmpty()) {
			try {

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);

				for (SsRelUffPcontOrg u : selectedRelazioni) {
					dto.setObj(u.getId());
					confService.attivaRelazione(dto);
				}

				aggiornaRelazioni();

				addInfo("attiva.ok");

			} catch (Exception e) {
				addError("attiva.error");
				logger.error(e.getMessage(), e);
			}
		} else
			addWarningMessage("salva.warning", "Selezionare almeno una relazione");
	}

	public void disattivaRelazione() {

		if (selectedRelazioni != null && !selectedRelazioni.isEmpty()) {
			try {

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);

				for (SsRelUffPcontOrg u : selectedRelazioni) {
					dto.setObj(u.getId());
					confService.disattivaRelazione(dto);
				}

				aggiornaRelazioni();

				addInfo("disattiva.ok");

			} catch (Exception e) {
				addError("disattiva.error");
				logger.error(e.getMessage(), e);
			}
		} else
			addWarningMessage("salva.warning", "Selezionare almeno una relazione");
	}

	public void eliminaRelazione() {
		List<SsRelUffPcontOrg> noDelete = new ArrayList<SsRelUffPcontOrg>();
		
		if (selectedRelazioni != null && !selectedRelazioni.isEmpty()) {
			try {

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);

				for (SsRelUffPcontOrg r : selectedRelazioni) {
	
					List<SsRelUffPcontOrg> verificaRelazioni = confService.verificaRelazioni(r);
					boolean elimina = verificaRelazioni==null || verificaRelazioni.isEmpty();

					if (elimina) {
						logger.debug("Elimino relazione " + r.getId());
						dto.setObj(r);
						confService.eliminaRelazione(dto);
					}else{
						noDelete.add(r);
					}
				}
				
				int numNoDel= noDelete.size();
				if (numNoDel>0){
					addWarning("salva.warning",
							"Alcune relazioni selezionate ["+noDelete.size()+"] non sono state rimosse poichè collegate a schede UDC esistenti.");
				}
				int numDel = selectedRelazioni.size()-numNoDel;
				if(numDel>0){
					caricaRelazioni();
					addInfo("elimina.ok", "Sono state eliminate "+numDel+ " relazioni");
				}

			} catch (Exception e) {
				addError("elimina.error");
				logger.error(e.getMessage(), e);
			}
		} else
			addWarningMessage("salva.warning", "Selezionare almeno una relazione");
	}

	public SsUfficio getNuovoUfficio() {
		return nuovoUfficio;
	}

	public void setNuovoUfficio(SsUfficio nuovoUfficio) {
		this.nuovoUfficio = nuovoUfficio;
	}

	public SsPuntoContatto getNuovoPContatto() {
		return nuovoPContatto;
	}

	public void setNuovoPContatto(SsPuntoContatto nuovoPContatto) {
		this.nuovoPContatto = nuovoPContatto;
	}

	public List<SsUfficio> getSelectedUffici() {
		return selectedUffici;
	}

	public void setSelectedUffici(List<SsUfficio> selectedUffici) {
		this.selectedUffici = selectedUffici;
	}

	public List<SsPuntoContatto> getLstPuntiContatto() {
		return lstPuntiContatto;
	}

	public void setLstPuntiContatto(List<SsPuntoContatto> lstPuntiContatto) {
		this.lstPuntiContatto = lstPuntiContatto;
	}

	public List<SsRelUffPcontOrg> getLstRelazioni() {
		return lstRelazioni;
	}

	public void setLstRelazioni(List<SsRelUffPcontOrg> lstRelazioni) {
		this.lstRelazioni = lstRelazioni;
	}

	public SsRelUffPcontOrg getNuovaRelazione() {
		return nuovaRelazione;
	}

	public void setNuovaRelazione(SsRelUffPcontOrg nuovaRelazione) {
		this.nuovaRelazione = nuovaRelazione;
	}

	public List<SsPuntoContatto> getSelectedPuntiContatto() {
		return selectedPuntiContatto;
	}

	public void setSelectedPuntiContatto(List<SsPuntoContatto> selectedPuntiContatto) {
		this.selectedPuntiContatto = selectedPuntiContatto;
	}

	public List<SsRelUffPcontOrg> getSelectedRelazioni() {
		return selectedRelazioni;
	}

	public void setSelectedRelazioni(List<SsRelUffPcontOrg> selectedRelazioni) {
		this.selectedRelazioni = selectedRelazioni;
	}

}
