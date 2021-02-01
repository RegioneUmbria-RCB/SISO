package it.webred.cs.csa.web.manbean.scheda.parenti;

import it.webred.cs.csa.ejb.client.AccessTableParentiGitSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ComponenteDTO;
import it.webred.cs.csa.ejb.dto.InfoRecapitiDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsAComponenteGit;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.data.model.CsAFamigliaGruppoGit;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiValiditaList;
import it.webred.cs.jsf.manbean.UserSearchBeanExt;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ParentiBean extends SchedaValiditaBaseBean implements IDatiValiditaList {

	private AccessTableParentiGitSessionBeanRemote parentiService = (AccessTableParentiGitSessionBeanRemote) getCarSocialeEjb("AccessTableParentiGitSessionBean");

	private List<ComponenteDTO> lstComponentiGit;

	private int idxSelected;

	public String beanP;

	private String nomeTab = "Risorse";

	private final Long AFFIDATARIO_NON_PARENTE = new Long(46);
	
	@Override
	public void initialize(Long sId) {

		super.initialize(sId);
		lstComponentiGit = null;
	}

	@Override
	public Object getTypeClass() {
		return new CsAFamigliaGruppo();
	}

	@Override
	public String getTypeComponent() {
		return "pnlParenti";
	}

	@Override
	public String getCodiceTab() {
		return "P";
	}

	@Override
	public void nuovo() {

		Integer idx = getFirstActiveComponentIndex();
		if (idx != null) {
			maxAttiviWarning(nomeTab + ": Nuovo elemento non disponibile");
			return;
		}

		ParentiComp comp = new ParentiComp();
		this.valorizzaComboComp(comp);
		comp.setDataInizio(new Date());
		listaComponenti.add(0, comp);
		super.nuovo();

	}

	public void attivaParente() {
		try {
			Integer idx = getFirstActiveComponentIndex();

			if (idx == null) {
				nuovo();
				idx = getFirstActiveComponentIndex();
			}

			logger.debug("Attiva Parente - Pannello Attivo n° " + idx);

			currentIndex = idx;
			ParentiComp pComp = (ParentiComp) listaComponenti.get(idx);
			pComp.getNuovoParenteBean().reset();

			pComp.setNuovo(true);
			pComp.setShowNewParente(true);
			pComp.setIdxSelected(0);

			CsAComponenteGit compGit = lstComponentiGit.get(idxSelected).getCompGit();

			// controllo se è già presente
			for (int i = 0; i < pComp.getLstParenti().size(); i++) {
				CsAComponente comp = pComp.getLstParenti().get(i);
				String compCF = comp.getCsAAnagrafica().getCf();
				String compCognome = comp.getCsAAnagrafica().getCognome();
				String compNome = comp.getCsAAnagrafica().getNome();
				if ((compCF != null && compCF.equals(compGit.getCf()))
						|| ((compCognome != null && compCognome.equals(compGit
								.getCognome())) && (compNome != null && compNome
								.equals(compGit.getNome())))) {
					pComp.setNuovo(false);
					pComp.setIdxSelected(i);
					pComp.loadModificaParente();
					break;
				}
			}

			// carico i dati sul popup di modifica parente
			NuovoParenteBean nuovoParente = pComp.getNuovoParenteBean();
			nuovoParente.setCodFiscale(compGit.getCf());
			nuovoParente.setCittadinanza(compGit.getCittadinanza());
			nuovoParente.setCognome(compGit.getCognome());
			if (compGit.getComResCod() != null) {
				ComuneBean comuneRes = new ComuneBean(compGit.getComResCod(),compGit.getComResDes(), compGit.getProvResCod());
				nuovoParente.getComuneNazioneResidenzaBean().getComuneMan().setComune(comuneRes);
			}
			if (compGit.getComuneNascitaCod() != null) {
				ComuneBean comuneNas = new ComuneBean(
						compGit.getComuneNascitaCod(),
						compGit.getComuneNascitaDes(),
						compGit.getProvNascitaCod());
				nuovoParente.getComuneNazioneNascitaBean().getComuneMan().setComune(comuneNas);
			}
			if (compGit.getStatoNascitaCod() != null) {
				AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(compGit.getStatoNascitaCod(), compGit.getStatoNascitaDes());
				nuovoParente.getComuneNazioneNascitaBean().setNazioneValue();
				nuovoParente.getComuneNazioneNascitaBean().getNazioneMan().setNazione(amTabNazioni);
			}
			nuovoParente.setDataDecesso(compGit.getDataDecesso());
			nuovoParente.setDataNascita(compGit.getDataNascita());
			nuovoParente.setIndirizzo(compGit.getIndirizzoRes());
			nuovoParente.setNome(compGit.getNome());
			nuovoParente.setNote(compGit.getNote());
			nuovoParente.setCivico(compGit.getNumCivRes());
			nuovoParente.setDatiSesso(new SessoBean(compGit.getSesso()));
			nuovoParente.setTelefono(compGit.getTel());
			nuovoParente.setCellulare(compGit.getCell());
			nuovoParente.setEmail(compGit.getEmail());
			
			//Verificare che l'intestatario scheda coincida con il titolare della cartella, 
			//in caso contrario non si può assegnare automaticamente la parentela
			if (compGit.getCsTbTipoRapportoCon() != null && compGit.getParentelaValida()) 
				nuovoParente.setIdParentela(compGit.getCsTbTipoRapportoCon().getId());

			
			//Verifico e assegno INFO RECAPITI
		    loadInfoRecapiti(nuovoParente);
			
			pComp.setNuovoParenteBean(nuovoParente);
			listaComponenti.set(idx, pComp);

			// quando viene attivato resetto il flag attivazione
			compGit.setFlgSegnalazione(false);
			BaseDTO bo = new BaseDTO();
			fillEnte(bo);
			bo.setObj(compGit);
			parentiService.updateComponenteGit(bo);

		} catch (Exception e) {
			logger.error(e);
			this.addError("caricamento.error", "");
		} finally {
			RequestContext.getCurrentInstance().addCallbackParam("canActivate",true);
		}
	}

	public CsAComponente recuperaAnagraficaParenteTrovatoWrapper(DatiUserSearchBean sel) {
		CsAComponente result = null;
		String id = sel.getId();
		if (id.trim().startsWith(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA)) 
			result = recuperaAnagraficaDaAnagrafeEsterna(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, id.replace(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, ""), (PersonaDettaglio)sel.getSoggetto());
		else if(id.trim().startsWith(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE))
			result = this.recuperaAnagraficaDaAnagrafeEsterna(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE, id.replace(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE, ""),(PersonaDettaglio)sel.getSoggetto());
		else if(id.trim().startsWith(DataModelCostanti.TipoRicercaSoggetto.SIGESS)){
			result = this.recuperaAnagraficaDaAnagrafeEsterna(DataModelCostanti.TipoRicercaSoggetto.SIGESS, id.replace(DataModelCostanti.TipoRicercaSoggetto.SIGESS, ""),(PersonaDettaglio)sel.getSoggetto());
		}else 
			result = recuperaAnagraficaDaAnagrafe(id);
		
		UserSearchBeanExt fbean = (UserSearchBeanExt) getReferencedBean("userSearchBeanExt");
		fbean.clearParameters();
		
		return result;
	}

	// /**********
	public CsAComponente recuperaAnagraficaDaAnagrafe(String id) {
		CsAComponente c = new CsAComponente();
		CsAAnagrafica anaBean = new CsAAnagrafica();
		try {
		
			// precarico anagrafica
			AnagrafeService anagrafeService = (AnagrafeService) getEjb("CT_Service", "CT_Service_Data_Access","AnagrafeServiceBean");
			RicercaSoggettoAnagrafeDTO ricercaDto = new RicercaSoggettoAnagrafeDTO();
			fillEnte(ricercaDto);
			ricercaDto.setIdVarSogg(id);
			SitDPersona p = anagrafeService.getPersonaById(ricercaDto);

			if (p != null) {

				if (p.getDataMor() != null && p.getDataMor().before(new Date())) {
					addWarning("Non è possibile utilizzare questo soggetto",
							   "Il soggetto selezionato è deceduto il "+ ddMMyyyy.format(p.getDataMor()));
					c = null;
					return c;
				}

				ComponenteFamigliaDTO compDto = new ComponenteFamigliaDTO();
				compDto.setPersona(p);
				fillEnte(compDto);
				compDto = anagrafeService.fillInfoAggiuntiveComponente(compDto);

				// anagrafica
				
				if(p.getIdExt()!=null)
					anaBean.setIdOrigWs(DataModelCostanti.TipoRicercaSoggetto.DEFAULT+"@"+p.getIdExt());
				anaBean.setCf(p.getCodfisc());
				anaBean.setCognome(p.getCognome());
				anaBean.setNome(p.getNome());
				anaBean.setDataNascita(p.getDataNascita());
				anaBean.setSesso(p.getSesso());
				
				// Cittadinanza
				anaBean.setCittadinanza(compDto.getCittadinanza());
				
				// nascita
				anaBean.setComuneNascitaCod(compDto.getCodComNas());
				anaBean.setComuneNascitaDes(compDto.getDesComNas());
				anaBean.setProvNascitaCod(compDto.getSiglaProvNas());
				anaBean.setStatoNascitaCod(compDto.getCodStatoNas());
				anaBean.setStatoNascitaDes(compDto.getDesStatoNas());
				
				// indirizzo res
				c.setComResCod(compDto.getCodComRes());
				c.setComResDes(compDto.getDesComRes());
				c.setProvResCod(compDto.getSiglaProvRes());
				c.setIndirizzoRes(compDto.getIndirizzoResidenza());
				c.setNumCivRes(compDto.getCivicoResidenza());

			}

			// FacesContext.getCurrentInstance().getExternalContext().redirect("scheda.faces");

		} catch (Exception e) {
			addError("Errore", "Errore durante il caricamento dell'anagrafica");
			logger.error("", e);
		}
		
		c.setCsAAnagrafica(anaBean);
		return c;
	}
	
	public CsAComponente recuperaAnagraficaDaAnagrafeEsterna(String tipoRicerca, String idSearch, PersonaDettaglio pIn) {
		CsAComponente c = new CsAComponente();
		CsAAnagrafica anaBean = new CsAAnagrafica();
		try {
			
			String id =         !StringUtils.isBlank(idSearch) && !idSearch.startsWith("@") ? idSearch : null;
    		String codFiscale = !StringUtils.isBlank(idSearch) &&  idSearch.startsWith("@") ? idSearch.replace("@","") : null;
			
			PersonaDettaglio p = pIn;
			if(!StringUtils.isBlank(id)) 
				p = getPersonaDaAnagEsterna(tipoRicerca, id);
			else if(!DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE.equals(tipoRicerca))
				p = CsUiCompBaseBean.getPersonaDaAnagEsterna(tipoRicerca, null, null, codFiscale);
			
			
			if (p != null) {
				
				if(p.isDefunto()){
					String msg = "Il soggetto selezionato è deceduto";
					msg+= p.getDataMorte()!=null ?  " il " + ddMMyyyy.format(p.getDataMorte()) : "";
					
					addWarning("Non è possibile utilizzare questo soggetto", msg);
					c = null;
					return c;
				}

				// anagrafica
				anaBean.setIdOrigWs(tipoRicerca+"@"+(id!=null ? id : ""));
				anaBean.setCf(p.getCodfisc());
				anaBean.setCognome(p.getCognome());
				anaBean.setNome(p.getNome());
				anaBean.setDataNascita(p.getDataNascita());
				SessoBean sb = new SessoBean(p.getSesso());
				anaBean.setSesso(p.getSesso());
				
				// Cittadinanza
				anaBean.setCittadinanza(p.getCittadinanza());
				
				// nascita
				AmTabComuni comuneNascita = p.getComuneNascita();
				AmTabNazioni nazioneNascita = p.getNazioneNascita();
				if (comuneNascita != null) {
					anaBean.setComuneNascitaCod(comuneNascita.getCodIstatComune());
					anaBean.setComuneNascitaDes(comuneNascita.getDenominazione());
					anaBean.setProvNascitaCod(comuneNascita.getSiglaProv());
				 }else if(nazioneNascita!=null){
					anaBean.setStatoNascitaCod(nazioneNascita.getCodIstatNazione());
					anaBean.setStatoNascitaDes(nazioneNascita.getNazione());
				 }
				
				
				//residenza
				c.setIndirizzoRes(p.getIndirizzoResidenza());
				c.setNumCivRes(p.getCivicoResidenza());
				AmTabComuni comRes = p.getComuneResidenza();
				if(comRes!=null){
					c.setComResCod(comRes.getCodIstatComune());
					c.setComResDes(comRes.getDenominazione());
					c.setProvResCod(comRes.getSiglaProv());
				}else if(p.getNazioneResidenza()!=null){
					c.setComAltroCod(p.getNazioneResidenza().getCodIstatNazione());
					c.setComAltroDes(p.getNazioneResidenza().getNazione());
				}

			}

			// FacesContext.getCurrentInstance().getExternalContext()
			// .redirect("scheda.faces");

		} catch (Exception e) {
			addError("Errore", "Errore durante il caricamento dell'anagrafica");
			logger.error("", e);
		}
		c.setCsAAnagrafica(anaBean);
		return c;
	}

	// *************
	public void creaParenteTrovatoAnagrafica(DatiUserSearchBean selSoggetto) {
		if (selSoggetto!=null)
		{
			try {
				Integer idx = getFirstActiveComponentIndex();

				if (idx == null) {
					nuovo();
					idx = getFirstActiveComponentIndex();
				}

				logger.debug("Attiva Parente - Pannello Attivo num. " + idx);

				currentIndex = idx;
				ParentiComp pComp = (ParentiComp) listaComponenti.get(idx);
				pComp.getNuovoParenteBean().reset();

				pComp.setNuovo(true);
				pComp.setIdxSelected(0);

				CsAComponente componenteRecuperato = recuperaAnagraficaParenteTrovatoWrapper(selSoggetto);
				CsAAnagrafica anagraficaRecuperata = componenteRecuperato!=null ? componenteRecuperato.getCsAAnagrafica() : null;
				
				// l'ho recuperato quindi lo resetto
				//this.userSearchNuovoParente.setSelSoggetto(null);

				// controllo se è già presente
				for (int i = 0; i < pComp.getLstParenti().size(); i++) {
					CsAComponente comp = pComp.getLstParenti().get(i);
					String compCF = comp.getCsAAnagrafica().getCf();
					String compCognome = comp.getCsAAnagrafica().getCognome();
					String compNome = comp.getCsAAnagrafica().getNome();
					if ((compCF != null && compCF.equals(anagraficaRecuperata.getCf()))
						   || ((compCognome != null && compCognome.equals(anagraficaRecuperata.getCognome())) 
						       && (compNome != null && compNome.equals(anagraficaRecuperata.getNome())))) {
						pComp.setNuovo(false);
						pComp.setIdxSelected(i);
						pComp.loadModificaParente();
						break;
					}
				}

				// carico i dati sul popup di modifica parente
				NuovoParenteBean nuovoParente = pComp.getNuovoParenteBean();
				nuovoParente.setIdOrigWs(anagraficaRecuperata.getIdOrigWs());
				nuovoParente.setCognome(anagraficaRecuperata.getCognome());
				nuovoParente.setNome(anagraficaRecuperata.getNome());
				nuovoParente.setCodFiscale(anagraficaRecuperata.getCf());
				nuovoParente.setDatiSesso(new SessoBean(anagraficaRecuperata.getSesso()));
				nuovoParente.setDataNascita(anagraficaRecuperata.getDataNascita());
				
				if (anagraficaRecuperata.getComuneNascitaCod() != null) {
					ComuneBean comuneNas = new ComuneBean(
							anagraficaRecuperata.getComuneNascitaCod(),
							anagraficaRecuperata.getComuneNascitaDes(),
							anagraficaRecuperata.getProvNascitaCod());
					nuovoParente.getComuneNazioneNascitaBean().getComuneMan().setComune(comuneNas);
					nuovoParente.getComuneNazioneNascitaBean().setComuneValue();
				}else
					nuovoParente.getComuneNazioneNascitaBean().setNazioneValue();
					
				if (anagraficaRecuperata.getStatoNascitaCod() != null) {
					AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(anagraficaRecuperata.getStatoNascitaCod(),anagraficaRecuperata.getStatoNascitaDes());
					nuovoParente.getComuneNazioneNascitaBean().getNazioneMan().setNazione(amTabNazioni);
				}
				nuovoParente.setDataDecesso(anagraficaRecuperata.getDataDecesso());
				
				nuovoParente.setTelefono(anagraficaRecuperata.getTel());
				nuovoParente.setCellulare(anagraficaRecuperata.getCell());
				nuovoParente.setEmail(anagraficaRecuperata.getEmail());
				
				nuovoParente.setCittadinanza(anagraficaRecuperata.getCittadinanza());
				nuovoParente.setNote(anagraficaRecuperata.getNote());
				
				loadIndirizzo(nuovoParente, componenteRecuperato);
				
				loadInfoRecapiti(nuovoParente);
				
				pComp.setNuovoParenteBean(nuovoParente);
				pComp.setShowNewParente(true);
				listaComponenti.set(idx, pComp);

			} catch (Exception e) {
				logger.error(e);
				this.addError("caricamento.error", "");
			} finally {
				RequestContext.getCurrentInstance().addCallbackParam("canActivate", true);
			}
		}
	}
	
	public void creaConoscenteTrovatoAnagrafica(DatiUserSearchBean selSoggetto) {
		if (selSoggetto!=null)
		{
			try {
				Integer idx = getFirstActiveComponentIndex();

				if (idx == null) {
					nuovo();
					idx = getFirstActiveComponentIndex();
				}

				logger.debug("Attiva Conoscente - Pannello Attivo num. " + idx);

				currentIndex = idx;
				ParentiComp pComp = (ParentiComp) listaComponenti.get(idx);
				pComp.getNuovoParenteBean().reset();

				pComp.setNuovo(true);
				pComp.setIdxSelected(0);

				CsAComponente componenteRecuperato = recuperaAnagraficaParenteTrovatoWrapper(selSoggetto);
				CsAAnagrafica anagraficaRecuperata = componenteRecuperato!=null ? componenteRecuperato.getCsAAnagrafica() : null;
				
				// l'ho recuperato quindi lo resetto
				//this.userSearchNuovoParente.setSelSoggetto(null);

				// controllo se è già presente
				List<CsAComponente> lista = pComp.getLstConoscenti();
				for (int i = 0; i < lista.size(); i++) {
					CsAComponente comp = lista.get(i);
					String compCF = comp.getCsAAnagrafica().getCf();
					String compCognome = comp.getCsAAnagrafica().getCognome();
					String compNome = comp.getCsAAnagrafica().getNome();
					if ((compCF != null && compCF.equals(anagraficaRecuperata.getCf()))
						   || ((compCognome != null && compCognome.equals(anagraficaRecuperata.getCognome())) 
						       && (compNome != null && compNome.equals(anagraficaRecuperata.getNome())))) {
						pComp.setNuovo(false);
						pComp.setIdxSelected(i);
						pComp.loadModificaConoscente();
						break;
					}
				}

				// carico i dati sul popup di modifica conoscente
				NuovoConoscenteBean nuovo = pComp.getNuovoConoscenteBean();
				nuovo.setIdOrigWs(anagraficaRecuperata.getIdOrigWs());
				nuovo.setCellulare(anagraficaRecuperata.getCell());
				nuovo.setCodFiscale(anagraficaRecuperata.getCf());
				nuovo.setCognome(anagraficaRecuperata.getCognome());

				if (anagraficaRecuperata.getComuneNascitaCod() != null) {
					ComuneBean comuneNas = new ComuneBean(
							anagraficaRecuperata.getComuneNascitaCod(),
							anagraficaRecuperata.getComuneNascitaDes(),
							anagraficaRecuperata.getProvNascitaCod());
					nuovo.getComuneNazioneNascitaBean().setComuneValue();
					nuovo.getComuneNazioneNascitaBean().getComuneMan().setComune(comuneNas);
				}else
					nuovo.getComuneNazioneNascitaBean().setNazioneValue();
				
				if (anagraficaRecuperata.getStatoNascitaCod() != null) {
					AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(anagraficaRecuperata.getStatoNascitaCod(),anagraficaRecuperata.getStatoNascitaDes());
					nuovo.getComuneNazioneNascitaBean().getNazioneMan().setNazione(amTabNazioni);
				}
				//nuovo.setDataDecesso(anagraficaRecuperata.getDataDecesso());
				nuovo.setDataNascita(anagraficaRecuperata.getDataNascita());
				nuovo.setEmail(anagraficaRecuperata.getEmail());
				nuovo.setNome(anagraficaRecuperata.getNome());
				nuovo.setNote(anagraficaRecuperata.getNote());
				nuovo.setDatiSesso(new SessoBean(anagraficaRecuperata.getSesso()));
				nuovo.setTelefono(anagraficaRecuperata.getTel());

				//Residenza
				loadIndirizzo(nuovo, componenteRecuperato);
				
				loadInfoRecapiti(nuovo);
				
				pComp.setNuovoConoscenteBean(nuovo);
				
				pComp.setShowNewConoscente(true);
				
				listaComponenti.set(idx, pComp);

			} catch (Exception e) {
				logger.error(e);
				this.addError("caricamento.error", "");
			} finally {
				
				RequestContext.getCurrentInstance().addCallbackParam("canActivate", true);
			}
		}
	}

	private Integer getFirstActiveComponentIndex() {

		if (listaComponenti != null) {
			for (int index = 0; index < listaComponenti.size(); index++) {
				ValiditaCompBaseBean comp = listaComponenti.get(index);
				if (comp.isAttivo()) {
					return index;
				}
			}
		}

		return null;
	}
	
	public ParentiComp getFirstActiveComponent() {

		if (listaComponenti != null) {
			for (int index = 0; index < listaComponenti.size(); index++) {
				ValiditaCompBaseBean comp = listaComponenti.get(index);
				if (comp.isAttivo()) {
					return (ParentiComp)comp;
				}
			}
		}

		return null;
	}

	public List<ComponenteDTO> getLstComponentiGit() {
		if (lstComponentiGit == null) {
			lstComponentiGit = new ArrayList<ComponenteDTO>();
			BaseDTO bo = new BaseDTO();
			fillEnte(bo);
			bo.setObj(soggettoId);
			CsAFamigliaGruppoGit famigliaGit = parentiService.getFamigliaGruppoGit(bo);
			if (famigliaGit != null) {

				Integer idx = getFirstActiveComponentIndex();
				ParentiComp pComp = null;
				if (idx == null) {
					nuovo();
					idx = getFirstActiveComponentIndex();
				}
				if (idx != null)
					pComp = (ParentiComp) listaComponenti.get(idx);

				for (CsAComponenteGit compGit : famigliaGit.getCsAComponenteGits()) {
					ComponenteDTO dto = new ComponenteDTO();
					dto.setCompGit(compGit);
					dto.setAttiva(true);
					dto.setAttiva(!this.isComponenteAttivato(compGit, pComp));
					dto.setStatoCartella(loadStatoCartella(compGit.getCf()));
					lstComponentiGit.add(dto);
				}
			}
		} else {
			Integer idx = getFirstActiveComponentIndex();
			if (idx != null) {
				ParentiComp pComp = (ParentiComp) listaComponenti.get(idx);

				for (ComponenteDTO dto : lstComponentiGit) {
					dto.setAttiva(true);
					// TODO:Verificare se il componente è già presente in
					// cartella
					dto.setAttiva(!this.isComponenteAttivato(dto.getCompGit(),
							pComp));
				}
			}
		}

		return lstComponentiGit;
	}

	public boolean isComponenteAttivato(CsAComponenteGit compGit,
			ParentiComp pComp) {
		boolean trovato = false;

		// controllo se è già presente
		for (int i = 0; i < pComp.getLstParenti().size(); i++) {
			CsAComponente comp = pComp.getLstParenti().get(i);
			String compCF = comp.getCsAAnagrafica().getCf();
			String compCognome = comp.getCsAAnagrafica().getCognome();
			String compNome = comp.getCsAAnagrafica().getNome();
			if ((compCF != null && compCF.equals(compGit.getCf()))
					|| ((compCognome != null && compCognome.equals(compGit
							.getCognome())) && (compNome != null && compNome
							.equals(compGit.getNome())))) {
				trovato = true;
				break;
			}
		}

		return trovato;

	}

	public void setLstComponentiGit(List<ComponenteDTO> lstComponentiGit) {
		this.lstComponentiGit = lstComponentiGit;
	}

	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

	@Override
	public Object getCsFromComponenteCopy(Object obj) {
		ParentiComp comp = (ParentiComp) obj;

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);

		CsAFamigliaGruppo cs = new CsAFamigliaGruppo();
		if (comp.getId() != null) {
			// update
			dto.setObj(comp.getId());
			cs = schedaService.getFamigliaGruppoById(dto);
		} else {
			// nuovo
			CsASoggettoLAZY sogg = new CsASoggettoLAZY();
			sogg.setAnagraficaId(soggettoId);
			cs.setCsASoggetto(sogg);
		}

		List<CsAComponente> lstTmp = new ArrayList<CsAComponente>();
		lstTmp.addAll(comp.getLstParenti());
		lstTmp.addAll(comp.getLstConoscenti());
		cs.setCsAComponentes(new ArrayList<CsAComponente>());
		for (CsAComponente c : lstTmp)
			cs.getCsAComponentes().add(duplica(c));

		cs.setHaParenti(comp.isHaParenti());
		cs.setViveSolo(comp.isViveSolo());
		cs.setParentiSconosciuti(comp.isParentiSconosciuti());
		cs.setInfoSconosciute(comp.getDisponibilitaDatiAnagraficiRisorse());
		
		cs.setDataInizioApp(comp.getDataInizio());
		if (comp.getDataInizio() == null)
			cs.setDataInizioApp(new Date());
		cs.setDataFineApp(comp.getDataFine());
		if (comp.getDataFine() == null)
			cs.setDataFineApp(DataModelCostanti.END_DATE);

		return cs;

	}

	private CsAComponente duplica(CsAComponente c) {
		CsAComponente cc = new CsAComponente();

		cc.setComAltroCod(c.getComAltroCod());
		cc.setComAltroDes(c.getComAltroDes());
		cc.setComResCod(c.getComResCod());
		cc.setComResDes(c.getComResDes());
		cc.setConvivente(c.getConvivente());

		cc.setCsTbContatto(c.getCsTbContatto());
		cc.setCsTbDisponibilita(c.getCsTbDisponibilita());
		cc.setCsTbPotesta(c.getCsTbPotesta());
		cc.setCsTbTipoRapportoCon(c.getCsTbTipoRapportoCon());
		cc.setIndirizzoAltro(c.getIndirizzoAltro());
		cc.setIndirizzoRes(c.getIndirizzoRes());
		cc.setNumCivAltro(c.getNumCivAltro());
		cc.setNumCivRes(c.getNumCivRes());
		cc.setProvAltroCod(c.getProvAltroCod());
		cc.setProvResCod(c.getProvResCod());
		cc.setCsAAnagrafica(duplica(c.getCsAAnagrafica()));
		cc.setUsrMod(null);
		cc.setDtMod(null);
		cc.setCsAFamigliaGruppo(null);
		cc.setCondLavorativaId(c.getCondLavorativaId());
		cc.setProfessioneId(c.getProfessioneId());
        cc.setAffidatario(c.getAffidatario()); //SISO-906
		return cc;
	}

	private CsAAnagrafica duplica(CsAAnagrafica a) {
		CsAAnagrafica ac = new CsAAnagrafica();
		ac.setIdOrigWs(a.getIdOrigWs());
		ac.setCell(a.getCell());
		ac.setCf(a.getCf());
		ac.setCittadinanza(a.getCittadinanza());
		ac.setCognome(a.getCognome());
		ac.setComuneNascitaCod(a.getComuneNascitaCod());
		ac.setComuneNascitaDes(a.getComuneNascitaDes());
		ac.setDataDecesso(a.getDataDecesso());
		ac.setDataNascita(a.getDataNascita());
		ac.setEmail(a.getEmail());
		ac.setNome(a.getNome());
		ac.setNote(a.getNote());
		ac.setProvNascitaCod(a.getProvNascitaCod());
		ac.setSesso(a.getSesso());
		ac.setStatoNascitaCod(a.getStatoNascitaCod());
		ac.setStatoNascitaDes(a.getStatoNascitaDes());
		ac.setTel(a.getTel());
		
		return ac;
	}

	@Override
	public Object getCsFromComponente(Object obj) {

		ParentiComp comp = (ParentiComp) obj;

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);

		CsAFamigliaGruppo cs = new CsAFamigliaGruppo(); //Recupero la famiglia da copiare
		if (comp.getId() != null) {
			// update
			dto.setObj(comp.getId());
			cs = schedaService.getFamigliaGruppoById(dto);
		} else {
			// nuovo
			CsASoggettoLAZY sogg = new CsASoggettoLAZY();
			sogg.setAnagraficaId(soggettoId);
			cs.setCsASoggetto(sogg);
		}

		cs.setCsAComponentes(new ArrayList<CsAComponente>());
		cs.getCsAComponentes().addAll(comp.getLstParenti());
		cs.getCsAComponentes().addAll(comp.getLstConoscenti());
		
		cs.setHaParenti(comp.isHaParenti());
		cs.setViveSolo(comp.isViveSolo());
		cs.setParentiSconosciuti(comp.isParentiSconosciuti());
		cs.setInfoSconosciute(comp.getDisponibilitaDatiAnagraficiRisorse());
		// TODO tipo

		cs.setDataInizioApp(comp.getDataInizio());
		if (comp.getDataInizio() == null)
			cs.setDataInizioApp(new Date());
		cs.setDataFineApp(comp.getDataFine());
		if (comp.getDataFine() == null)
			cs.setDataFineApp(DataModelCostanti.END_DATE);

		//SISO-1060
		comp.setDataInserimento(cs.getDtIns());
		comp.setDataModifica(cs.getDtMod());
		comp.setUsrInserimento(super.getCognomeNomeUtente(cs.getUserIns()));
		comp.setUsrModifica(super.getCognomeNomeUtente(cs.getUsrMod()));
		
		return cs;

	}

	@Override
	public ValiditaCompBaseBean getComponenteFromCs(Object obj) {

		CsAFamigliaGruppo cs = (CsAFamigliaGruppo) obj;
		ParentiComp comp = new ParentiComp();

		for (CsAComponente csComp : cs.getCsAComponentes()) {
			boolean isParente = csComp.getCsTbTipoRapportoCon() != null
					&& csComp.getCsTbTipoRapportoCon().getParente() != null
					&& csComp.getCsTbTipoRapportoCon().getParente();
			if (isParente) 
				comp.getLstParenti().add(csComp);
			else
				comp.getLstConoscenti().add(csComp);
		}
		// TODO tipo

		comp.setDataInizio(cs.getDataInizioApp());
		comp.setDataFine(cs.getDataFineApp());
		comp.setId(cs.getId());
		comp.setHaParenti(cs.getHaParenti()!=null ? cs.getHaParenti().toString() : null);
		comp.setParentiSconosciuti(cs.getParentiSconosciuti()!=null ? cs.getParentiSconosciuti().toString() : null);
		comp.setViveSolo(cs.getViveSolo()!=null ? cs.getViveSolo().toString() : null);
		comp.setDisponibilitaDatiAnagraficiRisorse(cs.getInfoSconosciute());
		//SISO-1060 
		comp.setDataInserimento(cs.getDtIns());
		comp.setDataModifica(cs.getDtMod());
		comp.setUsrInserimento(super.getCognomeNomeUtente(cs.getUserIns())  ); //
		comp.setUsrModifica(super.getCognomeNomeUtente(cs.getUsrMod()));
		
		return comp;
	}

	@Override
	public boolean validaCs(ValiditaCompBaseBean comp) {

		ParentiComp pComp = (ParentiComp) comp;
		boolean ok = true;

		String dtInizio = (pComp != null ? ddMMyyyy.format(pComp.getDataInizio()) : "");
		String dtFine = comp.getDataFine() != null ? ddMMyyyy.format(comp.getDataFine()) : "";
		dtFine = "31/12/9999".equals(dtFine) ? "ATTUALE" : dtFine;
		
		String head = "Risorse [" + dtInizio + "-" + dtFine +"]" + ":";
		List<String> msg = new ArrayList<String>();
		
		int countParenti = 0;
		int countConoscenti = 0;
		int countConviventi = 0;
		
		if(pComp.getDisponibilitaDatiAnagraficiRisorse() == 1 && (pComp.getLstParenti().size()>0 || pComp.getLstConoscenti().size()>0)){
			ok=false;
			msg.add("- Risulta selezionato il flag 'DATI ANAGRAFICI RISORSE: NON DISPONIBILI': non è possibile inserire parenti e/o conoscenti");
		}
		
		//Controllo sui PARENTI
		for (CsAComponente cs : pComp.getLstParenti()) {
			if (StringUtils.isBlank(cs.getCsAAnagrafica().getCognome())) {
				ok = false;
				msg.add("Lista: " + cs.getCsAAnagrafica().getNome()+ " - Cognome è un campo obbligatorio");
			}

			if (StringUtils.isBlank(cs.getCsAAnagrafica().getNome())) {
				ok = false;
				msg.add("Lista: " + cs.getCsAAnagrafica().getCognome()+ " - Nome è un campo obbligatorio");
			}

			if (StringUtils.isBlank(cs.getCsAAnagrafica().getSesso())) {
				ok = false;
				msg.add("Lista: " + cs.getCsAAnagrafica().getCognome()+ " " + cs.getCsAAnagrafica().getNome() + " - Sesso è un campo obbligatorio");
			}

			if (cs.getCsTbTipoRapportoCon() == null || cs.getCsTbTipoRapportoCon().getId() == null) {
				ok = false;
				msg.add("Lista: " + cs.getCsAAnagrafica().getCognome()+ " " + cs.getCsAAnagrafica().getNome() + " - Parentela è un campo obbligatorio");
			}else{
				//SISO-906--se seleziono dal menu a tendina Affidatario non parente il check Affidatario è obbligatorio
				if (cs.getCsTbTipoRapportoCon().getId().equals(AFFIDATARIO_NON_PARENTE)) {
					if (!cs.getAffidatario().booleanValue()) {
						ok = false;
						msg.add("Lista: " + cs.getCsAAnagrafica().getCognome() + " " + cs.getCsAAnagrafica().getNome()	+ " - Affidatario è un campo obbligatorio");
					}
				}
				if(cs.getCsTbTipoRapportoCon().getParente().booleanValue()) countParenti++;
				else countConoscenti++;
			}

			if (cs.getConvivente() == null) {
				ok = false;
				msg.add("Lista: " + cs.getCsAAnagrafica().getCognome()+ " " + cs.getCsAAnagrafica().getNome() + " - Convivente è un campo obbligatorio");
			}else{
				if(cs.getConvivente().booleanValue()) countConviventi++;
			}

			if (cs.getCsTbContatto() == null || cs.getCsTbContatto().getId() == null) {
				ok = false;
				msg.add("Lista: " + cs.getCsAAnagrafica().getCognome()+ " " + cs.getCsAAnagrafica().getNome() + " - Contatto è un campo obbligatorio");
			}

			if (cs.getCsTbPotesta() == null || cs.getCsTbPotesta().getId() == null) {
				ok = false;
				msg.add("Lista: " + cs.getCsAAnagrafica().getCognome()+ " " + cs.getCsAAnagrafica().getNome() + " - Potestà è un campo obbligatorio");
			}
		}
		
		//Controllo sui CONVIVENTI
		for (CsAComponente cs : pComp.getLstConoscenti()) {
			if (cs.getConvivente() == null) {
				ok = false;
				msg.add("Lista: " + cs.getCsAAnagrafica().getCognome()+ " " + cs.getCsAAnagrafica().getNome() + " - Convivente è un campo obbligatorio");
			}else{
				if(cs.getConvivente().booleanValue()) countConviventi++;
			}
		}
		
		//la validaione viene effettuata solo se è ho selezionato la disponibilita di informazioni (= 0)
		if(pComp.getDisponibilitaDatiAnagraficiRisorse() == 0){
			if (StringUtils.isBlank(pComp.getViveSolo())) {
				ok = false;
				msg.add("-'Vive Solo' è un campo obbligatorio");
			}else{
				if(pComp.isViveSolo().booleanValue() && countConviventi>0){
					//se vive da solo = si allora non è possibile inserire conviventi
					logger.debug("viveSolo["+pComp.getViveSolo()+"] --> se vive da solo = si allora non è possibile inserire conviventi");
					ok = false;
					msg.add("-Se l'utente vive solo non è possibile inserire conviventi");
					
				}else if(!pComp.isViveSolo().booleanValue() && countConviventi==0){
					//se vive da solo =no allora è obbligatorio inserire almeno un convivente 
					logger.debug("viveSolo["+pComp.getViveSolo()+"] --> se vive da solo = no allora è obbligatorio inserire almeno un convivente ");
					ok = false;
					msg.add("-Se l'utente non vive solo è obbligatorio inserire almeno un convivente");
				}
			}
			
			if (StringUtils.isBlank(pComp.getHaParenti())) {
				ok = false;
				msg.add("-'Ha parenti' è un campo obbligatorio");
			}
			
			if (StringUtils.isBlank(pComp.getParentiSconosciuti())) {
				ok = false;
				msg.add("-'Parenti Sconosciuti' è un campo obbligatorio");
			}
		
			if((pComp.isHaParenti()!=null && pComp.isHaParenti().booleanValue()) || (pComp.getParentiSconosciuti()!=null && !pComp.isParentiSconosciuti().booleanValue())){
				logger.debug("haParenti["+pComp.getHaParenti()+"], parentiSconosciuti["+pComp.getParentiSconosciuti()+"] --> se (ha parenti = si or p.sconosciuti=no) allora è obbligatorio inserire almeno un parente");
				if(countParenti==0){
					ok = false;
					msg.add("-Inserire almeno un parente");
				}		
			}
		
		
			if((!StringUtils.isBlank(pComp.getHaParenti()) && !pComp.isHaParenti().booleanValue()) || (!StringUtils.isBlank(pComp.getParentiSconosciuti()) && pComp.isParentiSconosciuti().booleanValue())){
				logger.debug("haParenti["+pComp.getHaParenti()+"], parentiSconosciuti["+pComp.getParentiSconosciuti()+"] --> se (ha parenti = no or parenti sconosciuti = si )allora non è possibile inserire parenti");
				if(countParenti>0){
					ok = false;
					msg.add("-Non è possibile inserire parenti: rimuovere quelli presenti o modificare i valori dei campi 'ha parenti' e/o 'parenti sconosciuti'");
				}
			}
		}
		
		addWarning(head, msg);
		
		RequestContext.getCurrentInstance().addCallbackParam("canClose", ok);
		return ok;
	}

	public String getBeanP() {
		return beanP;
	}

	public void setBeanP(String beanP) {
		this.beanP = beanP;
	}

	@Override
	public boolean validaComponenti() {
		boolean ok = true;

		int count = 0;
		for (ValiditaCompBaseBean comp : listaComponenti) {
			if (comp.isAttivo()) {
				count++;
			}
		}

		if (count > 1) {
			ok = false;
			maxAttiviWarning(nomeTab + ": Salvataggio non disponibile");
		}

		return ok;
	}

	private void maxAttiviWarning(String msg) {
		addWarning(msg,
				"E' possibile avere una sola situazione familiare attiva alla volta");
	}

	@Override
	protected boolean validaNumeroSituazioniAperte() {
		boolean valida = true;
		String gestioneParam = getGestioneTipoFamiglia();
		if(gestioneParam!=null && !gestioneParam.equalsIgnoreCase(DataModelCostanti.GestioneTipoFamiglia.SINTETICA)){
			ParentiComp p  = getFirstActiveComponent();
			if(p==null){
				this.addWarning(getNomeTab(), "Inserire una situazione valida");
				valida = false;
			}
		}
		
		if (this.getNumeroSituazioniAperte() > 1) {
			addWarningFromProperties("warn.numero.situazioniP.aperte");
			valida = false;
		}
		return valida;
	}
	
	public String loadStatoCartella(String cf){
		String s = super.loadStatoCartella(cf);
		if(s!=null && !s.isEmpty())
			return "<b>Cartella Sociale esistente</b>"+ s;
		else return s;
	}
	
	private void loadIndirizzo(AnagraficaNucleoBean p, CsAComponente c){
		if(c.getComResCod()!=null && !c.getComResCod().isEmpty()){
			p.setIndirizzo(c.getIndirizzoRes());
			p.setCivico(c.getNumCivRes());
			ComuneBean comuneRes = new ComuneBean(c.getComResCod(),c.getComResDes(), c.getProvResCod());
			p.getComuneNazioneResidenzaBean().setComuneValue();
			p.getComuneNazioneResidenzaBean().getComuneMan().setComune(comuneRes);
		}else{
			AmTabNazioni n = CsUiCompBaseBean.getNazioneByIstat(c.getComResCod(), c.getComResDes());
			p.getComuneNazioneResidenzaBean().getNazioneResidenzaBean().setNazione(n);
			p.getComuneNazioneResidenzaBean().setNazioneValue();
		}
	}
	
	private void loadInfoRecapiti(AnagraficaNucleoBean p){
		if(p.getCodFiscale()!=null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(p.getCodFiscale());
			InfoRecapitiDTO i = parentiService.findInfoRecapiti(dto);
			
			if(i!=null){
				p.setMsgInfoRecapiti("Il soggetto è "+(i.isCartellaSociale()? " intestatario di una cartella sociale." : "già presente in archivio.")+"<br/>Alcuni dati sono stati recuperati in automatico: verificare e confermare<br/><br/>");
				if(p.getCellulare()==null) p.setCellulare(i.getCellulare());
				if(p.getTelefono()==null)  p.setTelefono(i.getTelefono());
				if(p.getEmail()==null)     p.setEmail(i.getEmail());
				if(p.getIndirizzo()==null) p.setIndirizzo(i.getResIndirizzo());
				if(p.getCivico()==null)    p.setCivico(i.getResCivico());
				
				if (i.getResComune().getCodice() != null && 
						(p.getComuneNazioneResidenzaBean().getComuneMan().getComune()==null || p.getComuneNazioneResidenzaBean().getComuneMan().getComune().getCodIstatComune()==null)) {
					ComuneBean comuneRes = new ComuneBean((String)i.getResComune().getCodice(),i.getResComune().getDescrizione(), i.getResProv());
					p.getComuneNazioneResidenzaBean().setComuneValue();
					p.getComuneNazioneResidenzaBean().getComuneMan().setComune(comuneRes);
				}
				
				if(i.getResStato().getCodice()!=null && 
						(p.getComuneNazioneResidenzaBean().getNazioneMan().getNazione()==null || p.getComuneNazioneResidenzaBean().getNazioneMan().getNazione().getCodIstatNazione()==null)){
					p.getComuneNazioneResidenzaBean().setNazioneValue();
					AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat((String)i.getResStato().getCodice(), i.getResStato().getDescrizione());	
					p.getComuneNazioneResidenzaBean().getNazioneMan().setNazione(amTabNazioni);
				}
				
			}
		}
		
	}

	@Override
	public String getNomeTab() {
		return nomeTab;
	}

	@Override
	protected void caricaValoriCombo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void valorizzaComboComp(ValiditaCompBaseBean comp) {
		// TODO Auto-generated method stub
		
	}
	
}
