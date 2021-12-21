package it.webred.cs.jsf.manbean.por;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArConfigurazioneService;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArAttivitaDTO;
import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDatiPorSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiSiruSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.siru.ConfigurazioneFseDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruDominioDTO;
import it.webred.cs.csa.ejb.dto.siru.StampaFseDTO;
import it.webred.cs.csa.ejb.enumeratori.SiruEnum;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipiIngMercato;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.ArFfProgettoAttivita;
import it.webred.cs.data.model.CsExtraFseDatiLavoro;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbDurataRicLavoro;
import it.webred.cs.data.model.CsTbFormaGiuridica;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.jsf.interfaces.IDatiPor;
import it.webred.cs.jsf.manbean.comuneNazione.ComuneGenericMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.primefaces.context.RequestContext;

public abstract class DatiPorBaseMan extends CsUiCompBaseBean implements IDatiPor, Serializable {

	protected List<ArAttivitaDTO> sottoCorsi = new ArrayList<ArAttivitaDTO>();

	protected AccessTableDominiSiruSessionBeanRemote dominiSiruService = (AccessTableDominiSiruSessionBeanRemote) getCarSocialeEjb("AccessTableDominiSiruSessionBean");

	protected AccessTableDatiPorSessionBeanRemote datiPorService = (AccessTableDatiPorSessionBeanRemote) getCarSocialeEjb("AccessTableDatiPorSessionBean");

	protected Long idSottocorso;
	protected String descCondLavoro; //ING_MERCATO
	//private DatiPorDTO datiProgettoBean = new DatiPorDTO();

	protected List<SelectItem> lstProgetti;
	protected List<SelectItem> lstSottocorsi;
	
	protected Long idProgetto;
	protected String progetto;

	protected String vulnerabilita;
	protected String codiceForm;
	//private String codProgetto;

	protected boolean renderAttivita = true;

	protected Long idTipoIntervento;
	protected Long idTipoIntrCustom;
	protected Long idCatSociale;
	protected String belfiore;
	
	protected boolean occupato = false;

	protected boolean inattivo = false;
	protected boolean disoccupato = false;
	protected boolean ricercaPrimaOccupazione = false;
	protected boolean canShowComunicaVul = false;

	protected ComuneGenericMan comuneMan;
	protected List<SelectItem> lstDurataRicLavoro;
	protected List<SelectItem> lstGruppoVulnerabile;
	protected List<SelectItem> lstFormeGiuridiche;

	protected List<SelectItem> lstAteco;

	protected List<CsExtraFseDatiLavoro> lstDatiLavoro;
	protected CsExtraFseDatiLavoro csCDatiLavoro;

	protected List<String> validationErrorMessages;
	protected long idXStampa = 0l;
	protected boolean stampaPor = false;
	protected ConfigurazioneFseDTO mappaCampiFse;
	
	protected void initDatiProgetto(){
		
		this.comuneMan = new ComuneGenericMan("Sede aziendale");
		if (this.csCDatiLavoro.getAzComuneDes() != null)
			this.comuneMan.setComune(getComuneBean(this.csCDatiLavoro.getAzComuneCod(), this.csCDatiLavoro.getAzComuneDes(), this.csCDatiLavoro.getAzProv()));
		
		if (this.csCDatiLavoro.getProgetto() != null) {
			this.idProgetto = this.csCDatiLavoro.getProgetto().getId();
			this.onChangeProgetto();
		}
		if (this.csCDatiLavoro.getProgettoAttivita() != null) {
			this.idSottocorso = this.csCDatiLavoro.getProgettoAttivita().getId();
		}

	}

	public void impostaCondizioneLavorativa(BigDecimal id) {

		BaseDTO xo = new BaseDTO();
		fillEnte(xo);
		xo.setObj(id.toString());
		CsTbCondLavoro cl = confService.getCondLavoroById(xo);
		this.setDescCondLavoro(cl.getCsTbIngMercato().getTooltip());
		String ingMercatoId = cl.getCsTbIngMercato().getId();
		if (TipiIngMercato.INATTIVO.equalsIgnoreCase(ingMercatoId)||TipiIngMercato.STUDENTE.equalsIgnoreCase(ingMercatoId)) {
			this.inattivo = true;
			this.disoccupato = false;
			this.ricercaPrimaOccupazione = false;
			this.occupato = false;
		} else if (TipiIngMercato.DISOCCUPATO.equalsIgnoreCase(ingMercatoId)) {
			this.disoccupato = true;
			this.inattivo = false;
			this.ricercaPrimaOccupazione = false;
			this.occupato = false;
		} else if (TipiIngMercato.CERCA_PRIMA_OCCUPAZIONE.equalsIgnoreCase(ingMercatoId)) {
			this.ricercaPrimaOccupazione = true;
			this.inattivo = false;
			this.disoccupato = false;
			this.occupato = false;
		} else if (TipiIngMercato.OCCUPATO.equalsIgnoreCase(ingMercatoId)) {
			this.occupato = true;
			this.inattivo = false;
			this.disoccupato = false;
			this.ricercaPrimaOccupazione = false;
		}

		this.resetDatiOccupazione();
	}
	
	public void resetDatiOccupazione(){
			if(!this.isOccupato()){
				resetDatiInMercato02();
			}
			
			if(!this.isRicercaPrimaOccupazione() && !this.isDisoccupato())
				this.csCDatiLavoro.setDurataRicLavoroId(null);

			if(!this.isInattivo())
				this.csCDatiLavoro.setFlagAltroCorso(null);
	}
	
	private void resetDatiInMercato02(){
		if(this.csCDatiLavoro!=null){
			this.csCDatiLavoro.setDescTipoLavoro(null);
			this.csCDatiLavoro.setDescOrarioLavoro(null);
			this.csCDatiLavoro.setAzRagioneSociale(null);
			this.csCDatiLavoro.setAzPi(null);
			this.csCDatiLavoro.setAzCf(null);
			this.csCDatiLavoro.setAzVia(null);
			this.csCDatiLavoro.setAzComuneCod(null);
			this.csCDatiLavoro.setAzComuneDes(null);
			this.csCDatiLavoro.setAzProv(null);
			this.csCDatiLavoro.setAzFormaGiuridica(null);
			this.csCDatiLavoro.setDescDimAzienda(null);
			this.csCDatiLavoro.setAzCodAteco(null);
			
			this.comuneMan = new ComuneGenericMan("Sede aziendale");
		 
		}
	}

	public void changeCondizioneLavorativa(BigDecimal id) {
		impostaCondizioneLavorativa(id);
		refreshPage();
	}

	@Override
	public List<SelectItem> getLstProgetti() {

		if (lstProgetti == null) {
			lstProgetti = new ArrayList<SelectItem>();
			List<ArFfProgetto> listaProgetti = this.getListaProgetti();
			if (listaProgetti != null) {
				for (ArFfProgetto obj : listaProgetti){
					SelectItem si = new SelectItem(obj.getId(), obj.getDescrizione());
					si.setDisabled(obj.getAbilitato()==null || !obj.getAbilitato());
					lstProgetti.add(si);
				}
			}
		}
		return lstProgetti;
	}

	public void setLstProgetti(List<SelectItem> lstProgetti) {
		this.lstProgetti = lstProgetti;
	}

	public Long getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(Long idProgetto) {
		this.idProgetto = idProgetto;
		this.valorizzaProgetto();
	}

	private void valorizzaProgetto() {
		progetto = null;
		BaseDTO d = new BaseDTO();
		fillEnte(d);
		if (idProgetto != null) {
			d.setObj(idProgetto.longValue());
			ArFfProgetto cl = confService.getProgettiById(d);
			progetto = cl != null ? cl.getDescrizione() : null;
		}
		progetto = format(progetto);
	}

	private String format(String arg) {
		if (arg != null)
			return arg;
		else
			return "";
	}

	@Override
	public List<SelectItem> getLstSottocorsi() {
		lstSottocorsi = new ArrayList<SelectItem>();
		if (sottoCorsi != null) {
			for (ArAttivitaDTO obj : sottoCorsi) {
				SelectItem si = new SelectItem(obj.getId(), obj.getDescrizione());
				si.setDisabled(!obj.getAbilitato());
				lstSottocorsi.add(si);
			}
		}
		return lstSottocorsi;
	}

	public void setLstSottocorsi(List<SelectItem> lstSottocorsi) {
		this.lstSottocorsi = lstSottocorsi;
	}

	@Override
	public void onChangeProgetto() {
		logger.info("Change Progetto ");
		loadSottoCorsi();
		impostaProgetto();
		loadCodiceForm();
		loadMappaCampiFse();
		
		if(this.isModuloPorMarche() && this.mappaCampiFse.getSoggettoAttuatore().isAbilitato() && StringUtils.isBlank(this.csCDatiLavoro.getSoggettoAttuatore()))
		  this.csCDatiLavoro.setSoggettoAttuatore(this.getZonaSociale());
	}

	public void loadMappaCampiFse(){
		mappaCampiFse = null;
		if (this.idProgetto!=null) {  
			BaseDTO bdto = new BaseDTO();
			fillEnte(bdto);
			bdto.setObj(idProgetto);
			mappaCampiFse = confService.loadCampiFse(bdto);
		}
	}
	
	public void loadCodiceForm() {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);

		codiceForm = null;
		if (!StringUtils.isEmpty(progetto)) {

			dto.setObj(progetto);
			dto.setObj2(this.idTipoIntervento);
			dto.setObj3(this.idTipoIntrCustom);
			dto.setObj4(this.idCatSociale);
			codiceForm = confService.findCodFormProgetto(dto);
			logger.info("Progetto --> " + progetto + " " + codiceForm);
		}

	}

	public void loadSottoCorsi() {
		if (this.idProgetto != null && this.idProgetto > 0) {
			BaseDTO bdto = new BaseDTO();
			fillEnte(bdto);
			bdto.setObj(this.idProgetto.longValue());
			ArConfigurazioneService arConfService = (ArConfigurazioneService) getArgoEjb( "ArConfigurazioneServiceBean");
			sottoCorsi = arConfService.getListaAttivita(this.idProgetto.longValue());
		}
	}

	public List<SelectItem> getUffici() {
		List<SelectItem> lst = new ArrayList<SelectItem>();
		for (CsExtraFseDatiLavoro u : this.lstDatiLavoro) {

			if (u.getAzProv().equalsIgnoreCase(("PG"))) {
				lst.add(new SelectItem(u.getId(), u.getAzVia()));
				logger.info("Dato preso dal DB --> " + u.getAzProv());
			}
		}
		return lst;
	}

	public boolean isRenderProgetto() {
		return isVisualizzaModuloPorCs();
	}

	public String getCodiceForm() {
		return codiceForm;
	}

	public void setCodiceForm(String codiceForm) {
		this.codiceForm = codiceForm;
	}

	public boolean isRenderFSE() {
		return "FSE".equalsIgnoreCase(this.codiceForm);
	}

	public boolean isRenderFSEAttivita() {
		return renderAttivita;
	}

	public boolean isOccupato() {
		return occupato;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}

	public ComuneGenericMan getComuneMan() {
		return comuneMan;
	}
	
	public void setComuneMan(ComuneGenericMan comuneMan) {
		this.comuneMan = comuneMan;
	}

	public ComuneBean getComune(String id) {
		try {
			AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
			AmTabComuni comune = bean.getComuneByIstat(id);
			if (comune != null)
				return new ComuneBean(comune.getCodIstatComune(), comune.getDenominazione(), comune.getSiglaProv());

		} catch (NamingException e) {
			logger.error(e);
		}
		return null;
	}



	public List<SelectItem> getLstAteco() {
		if (lstAteco == null) {
			lstAteco = new ArrayList<SelectItem>();
			List<SiruDominioDTO> lsAteco = dominiSiruService.findAll(SiruEnum.SISO_ATECO.toString());
			lsAteco = dominiSiruService.findAll(SiruEnum.SISO_ATECO.toString());

			if (lsAteco != null) {
				for (SiruDominioDTO obj : lsAteco) {
					lstAteco.add(new SelectItem(obj.getCodiceSiru(), obj.getCodiceSiru() + " " + obj.getDescrizione()));
				}
			}
		}

		return lstAteco;
	}

	public List<SelectItem> getLstFormeGiuridiche() {
		if (lstFormeGiuridiche == null) {
			lstFormeGiuridiche = new ArrayList<SelectItem>();
			lstFormeGiuridiche.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbFormaGiuridica> lst = confService.getFormeGiuridiche(bo);
			if (lst != null) {
				for (CsTbFormaGiuridica p : lst) {
					SelectItem fa = new SelectItem(p.getId(), p.getDescrizione());
					fa.setDisabled(!p.getAbilitato());
					lstFormeGiuridiche.add(fa);
				}
			}
		}
		return lstFormeGiuridiche;
	}

	public List<SelectItem> getLstGruppoVulnerabile() {
		if (lstGruppoVulnerabile == null) {
			lstGruppoVulnerabile = new ArrayList<SelectItem>();
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<KeyValueDTO> lst = confService.getGruppiVulnerabili(bo);
			lstGruppoVulnerabile = convertiLista(lst);
		}
		return lstGruppoVulnerabile;
	}

	public List<SelectItem> getLstDurataRicLavoro() {
		if (lstDurataRicLavoro == null) {
			lstDurataRicLavoro = new ArrayList<SelectItem>();
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<KeyValueDTO> lst = confService.getDurataRicLavoro(cet);;
			this.lstDurataRicLavoro = convertiLista(lst);
		}
		return lstDurataRicLavoro;
	}
	
	public AccessTableDatiPorSessionBeanRemote getDatiPorService() {
		return datiPorService;
	}

	public void setDatiPorService(AccessTableDatiPorSessionBeanRemote datiPorService) {
		this.datiPorService = datiPorService;
	}

	public List<CsExtraFseDatiLavoro> getLstDatiLavoro() {
		return lstDatiLavoro;
	}

	public void setLstDatiLavoro(List<CsExtraFseDatiLavoro> lstDatiLavoro) {
		this.lstDatiLavoro = lstDatiLavoro;
	}

	public CsExtraFseDatiLavoro getCsCDatiLavoro() {
		return csCDatiLavoro;
	}

	public void setCsCDatiLavoro(CsExtraFseDatiLavoro csCDatiLavoro) {
		this.csCDatiLavoro = csCDatiLavoro;
	}

	public void addMessage(FacesMessage.Severity tipoMessaggio, String summary) {
		FacesMessage message = new FacesMessage(tipoMessaggio, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	private void refreshPage() {
		logger.info("page updated");

	}

	public void stampaModelloPOR() {
		RequestContext.getCurrentInstance().execute("PF('wVdlgStampaPor1').show()");
	}

	public boolean isStampaPor() {
		return stampaPor;
	}

	public void setStampaPor(boolean stampaPor) {
		this.stampaPor = stampaPor;
	}

	public boolean isInattivo() {
		return inattivo;
	}

	public void setInattivo(boolean inattivo) {
		this.inattivo = inattivo;
	}

	public boolean isDisoccupato() {
		return disoccupato;
	}

	public void setDisoccupato(boolean disoccupato) {
		this.disoccupato = disoccupato;
	}

	public boolean isRicercaPrimaOccupazione() {
		return ricercaPrimaOccupazione;
	}

	public void setRicercaPrimaOccupazione(boolean ricercaPrimaOccupazione) {
		this.ricercaPrimaOccupazione = ricercaPrimaOccupazione;
	}

	public Long getIdSottocorso() {
		return idSottocorso;
	}

	public void setIdSottocorso(Long idSottocorso) {
		this.idSottocorso = idSottocorso;
	}

	private void impostaProgetto() {
		//this.setCodProgetto(null);
		this.progetto = null;
		this.csCDatiLavoro.setProgetto(null);
		
		if(this.idProgetto!=null && this.idProgetto>0){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idProgetto);
			ArFfProgetto p = confService.getProgettiById(dto);
			this.progetto = p!=null ? p.getDescrizione() : null;
			this.csCDatiLavoro.setProgetto(p);
		}
	}

	private void impostaSottoProgetto() {
		this.csCDatiLavoro.setProgettoAttivita(null);
		if (this.idSottocorso != null && idSottocorso > 0) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idSottocorso);
			ArFfProgettoAttivita a = confService.getProgettoAttivitaById(dto);
			this.csCDatiLavoro.setProgettoAttivita(a);
		}
	}

	public List<String> aggiornaEntityXStampa() {
		this.aggiornaEntity();
		boolean ok = valida();
		return ok?null:validationErrorMessages;
	}
	
	public List<String> validaRecapiti(String telefono, String cellulare, String email){
		List<String> msg = new ArrayList<String>();
		if(this.isModuloPorMarche()){
			if(StringUtils.isBlank(telefono) && StringUtils.isBlank(cellulare) && StringUtils.isBlank(email))
				msg.add("Informazioni insufficienti: inserire almeno un recapito tra: telefono fisso, cellulare, email");
			else{
				if(!StringUtils.isBlank(telefono)){
				 Matcher mTel = DataModelCostanti.patternNumTelFisso.matcher(telefono);
				 if(!mTel.matches())
					 msg.add("Formato non corretto per il campo numero di telefono: " + telefono);
				}
				if(!StringUtils.isBlank(cellulare)){
				 Matcher mCel = DataModelCostanti.patternNumTelMobile.matcher(cellulare);
				 if(!mCel.matches())
					 msg.add("Formato non corretto per il campo numero di cellulare: "+cellulare);
				}
				if(!StringUtils.isBlank(email)){
					EmailValidator validator = EmailValidator.getInstance();
					boolean valido = validator.isValid(email);
					if(!valido)
						msg.add("Formato non corretto per il campo e-mail: "+email);
				}
			}
		}
		return msg;
	}

	public String getDescCondLavoro() {
		return descCondLavoro;
	}

	public void setDescCondLavoro(String descCondLavoro) {
		this.descCondLavoro = descCondLavoro;
	}

	public boolean isCanShowComunicaVul() {
		return this.canShowComunicaVul;
	}
	
	public void changeGruppoVulnerabile(CsTbGVulnerabile gr) {
		try {
			if(gr!=null && !StringUtils.isBlank(gr.getId())) {
				this.vulnerabilita = gr.getDescrizione();
				this.canShowComunicaVul = true;
			} else {
				if(this.csCDatiLavoro!=null) 
					this.csCDatiLavoro.setComunicaVul(false);
				this.canShowComunicaVul = false;
				this.vulnerabilita = null;
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}

	public void setCanShowComunicaVul(boolean canShowComunicaVul) {
		this.canShowComunicaVul = canShowComunicaVul;
	}

	public List<String> getErrorMessages() {
		return validationErrorMessages;
	}
	public String getProgetto() {
		return progetto;
	}

	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}

	public boolean valida() {
		logger.info("Valido i dati POR");
		this.aggiornaEntity();
		validationErrorMessages = new ArrayList<String>();
		boolean ret = true;
		if(isRenderProgetto() && (this.getIdProgetto() !=null && this.idProgetto >  0 )) {
			if(isRenderFSE()) {
				if(this.csCDatiLavoro.getProgetto() == null){
					String error = "Progetto";
					validationErrorMessages.add(error);
					ret = false;
				}
				if(this.csCDatiLavoro.getProgettoAttivita()==null){
					String error = "Attività del progetto";
					validationErrorMessages.add(error);
					ret = false;
				}
				if(this.occupato) {
					if(mappaCampiFse.getLavoroTipo().isValida() && StringUtils.isBlank(this.csCDatiLavoro.getDescTipoLavoro())) {
						String error = mappaCampiFse.getLavoroTipo().getLabel();
						validationErrorMessages.add(error);
						ret = false;
					}
					if(mappaCampiFse.getLavoroOrario().isValida() && StringUtils.isBlank(this.csCDatiLavoro.getDescOrarioLavoro())) {
						String error = mappaCampiFse.getLavoroOrario().getLabel();
						validationErrorMessages.add(error);
						ret = false;
					}
					if(mappaCampiFse.getAzRagioneSociale().isValida() && StringUtils.isBlank(this.csCDatiLavoro.getAzRagioneSociale())) {
						String error = mappaCampiFse.getAzRagioneSociale().getLabel();
						validationErrorMessages.add(error);
						ret = false;
					}
					if((mappaCampiFse.getAzPi().isValida() && StringUtils.isBlank(this.csCDatiLavoro.getAzPi())) && 
					   (mappaCampiFse.getAzCf().isValida() && StringUtils.isBlank(this.csCDatiLavoro.getAzCf()))) {
						String error = mappaCampiFse.getAzPi().getLabel()+" o "+mappaCampiFse.getAzCf().getLabel();
						validationErrorMessages.add(error);
						ret = false;
					}
			
					if(mappaCampiFse.getAzVia().isValida() && StringUtils.isBlank(this.csCDatiLavoro.getAzVia())) {
						String error = mappaCampiFse.getAzVia().getLabel();
						validationErrorMessages.add(error);
						ret = false;
					} 
		
					if(mappaCampiFse.getAzComune().isValida() && StringUtils.isBlank(this.csCDatiLavoro.getAzComuneDes())) {
						String error = mappaCampiFse.getAzComune().getLabel();
						validationErrorMessages.add(error);
						ret = false;
					} 
		
					if(mappaCampiFse.getAzCodAteco().isValida() && StringUtils.isBlank(this.csCDatiLavoro.getAzCodAteco())) {
						String error = mappaCampiFse.getAzCodAteco().getLabel();
						validationErrorMessages.add(error);
						ret = false;
					} 
		
					if(mappaCampiFse.getAzFormaGiuridica().isValida() && StringUtils.isBlank(this.csCDatiLavoro.getAzFormaGiuridica())) {
						String error = mappaCampiFse.getAzFormaGiuridica().getLabel();
						validationErrorMessages.add(error);
						ret = false;
					} 
		
					if(mappaCampiFse.getAzDimensione().isValida() && StringUtils.isBlank(this.csCDatiLavoro.getDescDimAzienda())) {
						String error = mappaCampiFse.getAzDimensione().getLabel();
						validationErrorMessages.add(error);
						ret = false;
					} 
				} else if(this.disoccupato) {
					if(mappaCampiFse.getDurataRicercaLavoro().isValida() && StringUtils.isBlank(csCDatiLavoro.getDurataRicLavoroId())) {
						String error = mappaCampiFse.getDurataRicercaLavoro().getLabel();
						validationErrorMessages.add(error);
						ret = false;
					} 
					
				} else if(this.inattivo) {
					
				} else if(this.ricercaPrimaOccupazione) {
					if(mappaCampiFse.getDurataRicercaLavoro().isValida() && StringUtils.isBlank(csCDatiLavoro.getDurataRicLavoroId())) {
						String error = mappaCampiFse.getDurataRicercaLavoro().getLabel();
						validationErrorMessages.add(error);
						ret = false;
					} 
				}
				
				BigDecimal annoTitolo = this.csCDatiLavoro.getAnnoConseguimentoTitoloStu();
				if(mappaCampiFse.getAnnoTitoloStudio().isValida() && (annoTitolo == null || annoTitolo.longValue()<1800)){
					String error = mappaCampiFse.getAnnoTitoloStudio().getLabel();
					validationErrorMessages.add(error);
					ret = false;
				}
				
				if(mappaCampiFse.getPagIban().isValida() && StringUtils.isBlank(this.csCDatiLavoro.getIban())){
					String error = mappaCampiFse.getPagIban().getLabel();
					validationErrorMessages.add(error);
					ret = false;
				}
				
				if(mappaCampiFse.getPagResDom().isValida() && this.csCDatiLavoro.getFlagResDom()==null){
					String error = mappaCampiFse.getPagResDom().getLabel();
					validationErrorMessages.add(error);
					ret = false;
				}
				
				if(mappaCampiFse.getInattivoAltroCorso().isValida() && this.csCDatiLavoro.getFlagAltroCorso()==null){
					String error = mappaCampiFse.getInattivoAltroCorso().getLabel();
					validationErrorMessages.add(error);
					ret = false;
				}
				
				if(this.csCDatiLavoro.getComunicaVul() == null){
					validationErrorMessages.add("L'utente intende comunicare la condizione di vulnerabilità");
					ret = false;
				}
				
				if(this.csCDatiLavoro.getDtSottoscrizione() == null){
					String error = "Data sottoscrizione";
					validationErrorMessages.add(error);
					ret = false;
				}
				
				if(mappaCampiFse.getDataSottoscrizione().isValida() && this.csCDatiLavoro.getDtSottoscrizione() == null){
					validationErrorMessages.add(mappaCampiFse.getDataSottoscrizione().getLabel());
					ret = false;
				}
				
				if(mappaCampiFse.getSoggettoAttuatore().isValida() && StringUtils.isBlank(csCDatiLavoro.getSoggettoAttuatore())) {
					validationErrorMessages.add(mappaCampiFse.getSoggettoAttuatore().getLabel());
					ret = false;
				}
				
			} else {
				String error = "Impostare un progetto FSE";
				validationErrorMessages.add(error);
				ret = false;
			}
			
		}
		logger.info("Fine validazione dati POR con esito "+ret);
		return ret;
	}
	
	public void showWarningDialog(){
		if(!validationErrorMessages.isEmpty()){
			String s  = "<ul>";
			for(String sm : validationErrorMessages) 
				s+= "<li>"+ sm.replace("'", "&#39;") +"</li>";
			s+="</ul>";	
			this.addWarningDialog("Valori FSE obbligatori", s);
		}
	}
	
	public void showWarning(){
		StringBuilder errorpor = new StringBuilder();
		int i = 0;
		for(String msg: validationErrorMessages) {
			errorpor.append(msg);
			if(i<validationErrorMessages.size()-1)
				errorpor.append(", ");
			i++;
		}
		this.addWarning("Valori FSE obbligatori", errorpor.toString());
	}
	
	public void aggiornaEntity() {
			impostaProgetto();
			impostaSottoProgetto();
			//loadCodiceForm();

			if (this.comuneMan.comune != null && this.mappaCampiFse.getAzComune().isAbilitato()) {
				this.csCDatiLavoro.setAzComuneCod(this.comuneMan.comune.getCodIstatComune());
				this.csCDatiLavoro.setAzComuneDes(this.comuneMan.comune.getDenominazione());
				this.csCDatiLavoro.setAzComuneCod(this.comuneMan.comune.getCodIstatComune());
				this.csCDatiLavoro.setAzProv(this.comuneMan.comune.getSiglaProv());
			} else {
				this.csCDatiLavoro.setAzComuneCod(null);
				this.csCDatiLavoro.setAzComuneDes(null);
				this.csCDatiLavoro.setAzComuneCod(null);
				this.csCDatiLavoro.setAzProv(null);
			}
	
			if(!isCanShowComunicaVul()) {
				this.csCDatiLavoro.setComunicaVul(Boolean.FALSE);
			}
	}

	public long getIdXStampa() {
		return idXStampa;
	}

	public void setIdXStampa(long idXStampa) {
		this.idXStampa = idXStampa;
	}

	public String getVulnerabilita() {
		return vulnerabilita;
	}

	public void setVulnerabilita(String vulnerabilita) {
		this.vulnerabilita = vulnerabilita;
	}

	public boolean isMarche() {
	    return this.isModuloPorMarche();
	}
	
	public void valorizzaStampa(StampaFseDTO datiProgettoBean) {
		ArFfProgetto progetto = this.getCsCDatiLavoro().getProgetto();
		if(progetto!=null){
			String progDesc = progetto.getDescrizione();
			datiProgettoBean.setFfProgettoDescrizione(progDesc.replaceFirst(DataModelCostanti.patternFSE, ""));
			datiProgettoBean.setCodProgetto(progetto.getCodiceMemo());
		}
		ArFfProgettoAttivita attivita = this.getCsCDatiLavoro().getProgettoAttivita();
		if(attivita!=null)
			datiProgettoBean.setCodAttivita(attivita.getCodice());
		
		datiProgettoBean.setComunicaVul(this.isComunicaVul());
		//datiProgettoBean.setDescrizioneCondLavoro(this.getDescCondLavoro());
		datiProgettoBean.setDurataRicercaLavoro(this.isRenderDurataRicLavoro());
		String idDurataRicLavoro = this.getCsCDatiLavoro().getDurataRicLavoroId();
		CsTbDurataRicLavoro ricLavoro = null;
		if(!StringUtils.isBlank(idDurataRicLavoro)){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(new Long(idDurataRicLavoro));
			ricLavoro = confService.findDurataRicLavoroById(dto);
		}
		datiProgettoBean.setLavoroDurataRicerca(ricLavoro!=null ? ricLavoro.getTooltip() : "");
		datiProgettoBean.setCondLavoro(this.getDescCondLavoro());
		
		datiProgettoBean.setSoggettoAttuatore(this.getCsCDatiLavoro().getSoggettoAttuatore());
		Date dtsottoscrizione = this.getCsCDatiLavoro().getDtSottoscrizione();
		if(dtsottoscrizione!=null)
			datiProgettoBean.setDtSottoscrizione(ddMMyyyy.format(dtsottoscrizione));
	}
	
	private List<ArFfProgetto> getListaProgetti() {
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		// dal ultimo iterstep del caso prendo organizzazione del titolare
		List<ArFfProgetto> listaProgetti = new ArrayList<ArFfProgetto>();
		if (!StringUtils.isBlank(this.belfiore)) {
			bdto.setObj(this.belfiore);
			bdto.setObj2(DataModelCostanti.TipoProgetto.FSE);
			listaProgetti = confService.findProgettiByBelfioreOrganizzazione(bdto);
		}
		return listaProgetti;
	}

	public List<ArAttivitaDTO> getSottoCorsi() {
		return sottoCorsi;
	}

	public void setSottoCorsi(List<ArAttivitaDTO> sottoCorsi) {
		this.sottoCorsi = sottoCorsi;
	}

	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	@Override
	public void elimina() {
		Long porId = this.csCDatiLavoro!=null ? this.csCDatiLavoro.getMaster().getId() : null;
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(porId);
		datiPorService.eliminaDatiPor(dto);		
	}
	
	public boolean isComunicaVul(){
		return this.getCsCDatiLavoro().getComunicaVul()!=null && this.getCsCDatiLavoro().getComunicaVul().booleanValue();
	}
	
	public String getDescFlagResDom() {
		String val = null;
		if(this.mappaCampiFse.getPagResDom().isAbilitato() && getCsCDatiLavoro().getFlagResDom()!=null)
			val = getCsCDatiLavoro().getFlagResDom()?"RES":"DOM";
		return val;
	}

	public ConfigurazioneFseDTO getMappaCampiFse() {
		return mappaCampiFse;
	}

	public void setMappaCampiFse(ConfigurazioneFseDTO mappaCampiFse) {
		this.mappaCampiFse = mappaCampiFse;
	}
	
	public boolean isRenderSezAzienda(){
		boolean render = false;
		if(this.isOccupato() && this.mappaCampiFse!=null)
			render = this.mappaCampiFse.isRenderSezAzienda();
		return render;
	}
	public boolean isRenderSezLavoro(){
		boolean render = false;
		if(this.isOccupato() && this.mappaCampiFse!=null)
			render = this.mappaCampiFse.isRenderSezLavoro();
		return render;
	}

	public boolean isRenderAltroCorso(){
		boolean render = false;
		if(this.isInattivo() && this.mappaCampiFse!=null)
			render = this.mappaCampiFse.getInattivoAltroCorso().isAbilitato();	
		return render;
	}
	
	public boolean isRenderDurataRicLavoro(){
		boolean render = false;
		if(this.mappaCampiFse!=null && (this.isDisoccupato() || this.isRicercaPrimaOccupazione()))
			render = this.mappaCampiFse.getDurataRicercaLavoro().isAbilitato();
		return render;
	}
}
