package it.webred.ss.web.bean.wizard;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipiInterventoCustom;
import it.webred.cs.data.DataModelCostanti.TipoDiario;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.OrientamentoLavoro.IOrientamentoLavoro;
import it.webred.cs.json.OrientamentoLavoro.OrientamentoLavoroManBaseBean;
import it.webred.cs.json.intermediazione.IIntermediazioneAb;
import it.webred.cs.json.intermediazione.IntermediazioneManBaseBean;
import it.webred.cs.json.mediazioneculturale.IMediazioneCult;
import it.webred.cs.json.mediazioneculturale.MediazioneCultManBaseBean;
import it.webred.cs.json.orientamentoistruzione.IOrientamentoIstruzione;
import it.webred.cs.json.orientamentoistruzione.OrientamentoIstruzioneManBaseBean;
import it.webred.cs.json.serviziorichiestocustom.IServizioRichiestoCustom;
import it.webred.cs.json.serviziorichiestocustom.ServizioRichiestoCustomManBaseBean;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.util.PreselPuntoContatto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.UploadedFile;

/**
 * classe creata per SISO-438-Possibilità di allegare documenti in UdC
 * Mappa i dati del pannello dei servizi richiesti interventi custom ( nella pagina tab5_ServiziRichiesti.xhtml )
 */
public class ServiziRichiestiInterventiCustomBean extends SegretariatoSocBaseBean {
		
	private Long selectedTipoInterventoCustom;
	private SsScheda scheda;
	private SsSchedaSegnalato segnalato;
	
	private List<ISchedaValutazione> serviziRichiestiInterventiCustomSalvati;
	private List<ISchedaValutazione> serviziRichiestiInterventiCustomDaSalvare;
	private List<ISchedaValutazione> listaServizioRichiestoCustomAltreSchede;	//lista dei servizi custom per le altre schede dell'utente in questione
	
	private SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) getEjb("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
	
	// #ROMACAPITALE inizio
	private CsOSettore settoreSelezionato;
	
	public CsOSettore getSettoreSelezionato() {
		return settoreSelezionato;
	}
	public void setSettoreSelezionato(CsOSettore settoreSelezionato) {
		this.settoreSelezionato = settoreSelezionato;
	}
	// #ROMACAPITALE fine
	
	
	public ServiziRichiestiInterventiCustomBean() {
		serviziRichiestiInterventiCustomSalvati = new ArrayList<ISchedaValutazione>();
		serviziRichiestiInterventiCustomDaSalvare = new ArrayList<ISchedaValutazione>();
		
	
		//this.nuovaSchedaWizard = nuovaSchedaWizard;
	}
	

	public void loadManJsonServiziRichiesti(SsScheda scheda, SsSchedaSegnalato segnalato) throws Exception {
		
		this.scheda = scheda;
		this.segnalato = segnalato;
		if(scheda!=null && segnalato==null){
			BaseDTO  dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(scheda.getSegnalato());			
			this.segnalato = schedaService.readSegnalatoById(dto);
		}
		
		serviziRichiestiInterventiCustomSalvati.clear();
		serviziRichiestiInterventiCustomDaSalvare.clear();
		
		List<CsDValutazione> res = this.getSchedeJsonInterventiCustom(scheda.getId());
		
		for(CsDValutazione val : res){
			
			if(TipoDiario.INTERMEDIAZIONE_AB_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
				IIntermediazioneAb intermediazioneAbMan = (IIntermediazioneAb)IntermediazioneManBaseBean.initByModel(val);
				if (intermediazioneAbMan!=null) {
					serviziRichiestiInterventiCustomSalvati.add(intermediazioneAbMan);
					serviziRichiestiInterventiCustomDaSalvare.add(intermediazioneAbMan);
				}
			}
			if(TipoDiario.ORIENTAMENTO_LAVORO_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
				IOrientamentoLavoro orientamentoLavoroManBean = (IOrientamentoLavoro)OrientamentoLavoroManBaseBean.initByModel(val);
				if (orientamentoLavoroManBean!=null) {
					serviziRichiestiInterventiCustomSalvati.add(orientamentoLavoroManBean);
					serviziRichiestiInterventiCustomDaSalvare.add(orientamentoLavoroManBean);
				}
			}
			if(TipoDiario.MEDIAZIONE_CULT_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
				IMediazioneCult mediazioneCultMan = MediazioneCultManBaseBean.initByModel(val);
				if (mediazioneCultMan!=null) {
					serviziRichiestiInterventiCustomSalvati.add(mediazioneCultMan);
					serviziRichiestiInterventiCustomDaSalvare.add(mediazioneCultMan);
				} 
			}
			if(TipoDiario.ORIENTAMENTO_ISTRUZIONE_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
				IOrientamentoIstruzione orientamentoIstruzioneMan = (IOrientamentoIstruzione) OrientamentoIstruzioneManBaseBean.initByModel(val);
				if (orientamentoIstruzioneMan!=null) {
					serviziRichiestiInterventiCustomSalvati.add(orientamentoIstruzioneMan);
					serviziRichiestiInterventiCustomDaSalvare.add(orientamentoIstruzioneMan);
				}
			}
			if(TipoDiario.RICHIESTA_SERVIZIO_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
				IServizioRichiestoCustom iServizioRichiestoCustom = 
						(IServizioRichiestoCustom) ServizioRichiestoCustomManBaseBean.initByModel(val, isResidenteFuoriAmbito());
				serviziRichiestiInterventiCustomSalvati.add(iServizioRichiestoCustom);
				serviziRichiestiInterventiCustomDaSalvare.add(iServizioRichiestoCustom);
			}
		}
	}
	
	
	/**
	 * se non è presente, carico la lista delle schede di valutazione di tutte le altre schede COMPLETE e della stessa organizzazione sulla quale l'operatore sta lavorando,
	 *  il cui segnalato ha lo stesso codice fiscale di quello attuale
	 */
	public void loadListaServizioRichiestoCustomAltreSchede(SsScheda scheda, SsSchedaSegnalato segnalato) {
		this.scheda = scheda;
		this.segnalato = segnalato;
		try {
			if (scheda != null && segnalato!=null && segnalato.getAnagrafica()!=null) {
				String cf = segnalato.getAnagrafica().getCf();
				if (!StringUtils.isEmpty(cf)) {
					listaServizioRichiestoCustomAltreSchede = new ArrayList<ISchedaValutazione>();
				
					logger.debug("getListaServizioRichiestoCustomAltreSchede " + cf + " id scheda attuale " + scheda.getId());
					PreselPuntoContatto pContMan = (PreselPuntoContatto)SegretariatoSocBaseBean.getBeanReference("preselPuntoContatto");
					logger.debug("getListaServizioRichiestoCustomAltreSchede id organizzazione[" + pContMan.getPuntoContatto().getOrganizzazione().getId() + "]"); 
					

					SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
							"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

					it.webred.ss.ejb.dto.BaseDTO ssb = new it.webred.ss.ejb.dto.BaseDTO();
					CsUiCompBaseBean.fillEnte(ssb); 
					ssb.setObj(cf);
					//recupera tutte le schede per il CF passato
					List<SsScheda> listaSchede = schedaService.readSchedeByCF(ssb); 
					
					for (SsScheda ssScheda : listaSchede) {

						logger.debug("getListaServizioRichiestoCustomAltreSchede ciclo scheda id[" + ssScheda.getId() + "] OrganizzazioneId[" + (ssScheda.getAccesso()!=null ? ssScheda.getAccesso().getSsRelUffPcontOrg().getId().getOrganizzazioneId() : null) + "]");
						
						Long idUfficio = ssScheda.getAccesso()!=null ? ssScheda.getAccesso().getSsRelUffPcontOrg().getSsUfficio().getId() : null;
						
						//filtra le schede, devono essere diverse da quella attuale, complete e  della stessa organizzazione sulla quale l'operatore sta lavorando, filtrando per ufficio se ci sono restrizioni
						if (!ssScheda.getId().equals(scheda.getId())
								&& ssScheda.getCompleta()
								&& ssScheda.getAccesso().getSsRelUffPcontOrg().getId().getOrganizzazioneId().longValue() == pContMan.getPuntoContatto().getOrganizzazione().getId().longValue() 
								&& canAccessUfficio(idUfficio)) {
							
							List<CsDValutazione> res = this.getSchedeJsonInterventiCustom(ssScheda.getId()); //Lista ordinata per data amministrativa decrescente
							for(CsDValutazione val : res){
							
								if(TipoDiario.INTERMEDIAZIONE_AB_ID==val.getCsDDiario().getCsTbTipoDiario().getId().intValue()){
									IIntermediazioneAb intermediazioneAbMan = (IIntermediazioneAb)IntermediazioneManBaseBean.initByModel(val);
									if (intermediazioneAbMan!=null)
										listaServizioRichiestoCustomAltreSchede.add(intermediazioneAbMan);
								}
								if(TipoDiario.ORIENTAMENTO_LAVORO_ID==val.getCsDDiario().getCsTbTipoDiario().getId().intValue()){
									IOrientamentoLavoro orientamentoLavoroManBean = (IOrientamentoLavoro)OrientamentoLavoroManBaseBean.initByModel(val);
									if (orientamentoLavoroManBean!=null)
										listaServizioRichiestoCustomAltreSchede.add(orientamentoLavoroManBean);
								}
								if(TipoDiario.MEDIAZIONE_CULT_ID==val.getCsDDiario().getCsTbTipoDiario().getId().intValue()){
									IMediazioneCult mediazioneCultMan = MediazioneCultManBaseBean.initByModel(val);
									if (mediazioneCultMan!=null)
										listaServizioRichiestoCustomAltreSchede.add(mediazioneCultMan);
								}
								if(TipoDiario.ORIENTAMENTO_ISTRUZIONE_ID==val.getCsDDiario().getCsTbTipoDiario().getId().intValue()){
									IOrientamentoIstruzione orientamentoIstruzioneMan = (IOrientamentoIstruzione) OrientamentoIstruzioneManBaseBean.initByModel(val);
									if (orientamentoIstruzioneMan!=null)
										listaServizioRichiestoCustomAltreSchede.add(orientamentoIstruzioneMan);
								}
								if(TipoDiario.RICHIESTA_SERVIZIO_ID==val.getCsDDiario().getCsTbTipoDiario().getId().intValue()){
									IServizioRichiestoCustom iServizioRichiestoCustom = 
										(IServizioRichiestoCustom) ServizioRichiestoCustomManBaseBean.initByModel(val, isResidenteFuoriAmbito());
									listaServizioRichiestoCustomAltreSchede.add(iServizioRichiestoCustom);
								}
							}
							
						}
						 
					}					
				}

			
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SegretariatoSocBaseBean.addError("lettura.error");
		}
	}
	
	public List<ISchedaValutazione> getListaServizioRichiestoCustomAltreSchede() {	
		return listaServizioRichiestoCustomAltreSchede;
	}


	public void setListaServizioRichiestoCustomAltreSchede(
			List<ISchedaValutazione> listaServizioRichiestoCustomAltreSchede) {
		this.listaServizioRichiestoCustomAltreSchede = listaServizioRichiestoCustomAltreSchede;
	}


	public List<ISchedaValutazione> getServiziRichiestiInterventiCustomSalvati() {
		return serviziRichiestiInterventiCustomSalvati;
	}
	public void setServiziRichiestiInterventiCustomSalvati(
			List<ISchedaValutazione> serviziRichiestiInterventiCustomSalvati) {
		this.serviziRichiestiInterventiCustomSalvati = serviziRichiestiInterventiCustomSalvati;
	}
	
	
	
	public List<ISchedaValutazione> getServiziRichiestiInterventiCustomDaSalvare() {
		return serviziRichiestiInterventiCustomDaSalvare;
	}
	

	/**
	 * restutuisce solo quelli di tipo IServizioRichiestoCustom
	 */
	public List<IServizioRichiestoCustom> getServiziRichiestiCustomDaSalvare() {
		List<IServizioRichiestoCustom> result = new ArrayList<IServizioRichiestoCustom>();
		for (ISchedaValutazione iSchedaValutazione : serviziRichiestiInterventiCustomDaSalvare) { 
			if (iSchedaValutazione instanceof IServizioRichiestoCustom) {
				result.add((IServizioRichiestoCustom)iSchedaValutazione);
			}
		}

		return result;
	}
	
//SISO-659	
	public List<IServizioRichiestoCustom> getServiziRichiestiCustom(String tipo_contains, String and_tipo_contains)		
	{
		List<IServizioRichiestoCustom> ss = getServiziRichiestiCustomDaSalvare();
		List<IServizioRichiestoCustom> ret = new ArrayList<IServizioRichiestoCustom>(ss);		
		
		if(tipo_contains!=null && tipo_contains.length()>0) 
		{			
			ret.clear();
			for (IServizioRichiestoCustom s: ss) 
			{
				String tic = s.getTipoInterventoCustom().toLowerCase();
				if (tic.contains(tipo_contains.toLowerCase()) )
				{
					if(and_tipo_contains!=null && and_tipo_contains.length()>0) 
					{
						if (tic.contains(and_tipo_contains.toLowerCase()) )
						{
							ret.add(s);
						}
					}
					else
					{
						ret.add(s);
					}
				}
			}
		}
		return ret;		
	}
	
	public void setServiziRichiestiInterventiCustomDaSalvare(
			List<ISchedaValutazione> serviziRichiestiInterventiCustomDaSalvare) {
		this.serviziRichiestiInterventiCustomDaSalvare = serviziRichiestiInterventiCustomDaSalvare;
	} 


	
	
	public boolean servizioRendered(ISchedaValutazione schedaValutazione, String tipo){
		if (tipo.equals("IIntermediazioneAb") && schedaValutazione instanceof IIntermediazioneAb ) {
			return true;
		}
		if (tipo.equals("IOrientamentoLavoro") && schedaValutazione instanceof IOrientamentoLavoro ) {
			return true;
		}
		if (tipo.equals("IMediazioneCult") && schedaValutazione instanceof IMediazioneCult ) {
			return true;
		}
		if (tipo.equals("IOrientamentoIstruzione") && schedaValutazione instanceof IOrientamentoIstruzione ) {
			return true;
		}
		if (tipo.equals("IServizioRichiestoCustom") && schedaValutazione instanceof IServizioRichiestoCustom) {
			return true;
		}
		return false; 
	} 
	

	/**
	 * chiamato alla creazione del bean che gestisce tutto il wizard della scheda
	 */
/*	public void initManJsonServiziRichiesti(SsScheda scheda, String VER_MAX){
		this.intermediazioneAbMan = this.initManIntermediazioneAb(scheda, VER_MAX);
		this.orientamentoLavoroManBean = this.initOrientamentoLavoroManBean(scheda, VER_MAX);
		this.mediazioneCultMan = this.initManMediazioneCult(scheda, VER_MAX);
		this.orientamentoIstruzioneMan = this.initManOrientamentoIstruzione(scheda, VER_MAX);

		serviziRichiestiInterventiCustomDaSalvare.add(intermediazioneAbMan);
		serviziRichiestiInterventiCustomDaSalvare.add(orientamentoLavoroManBean);
		serviziRichiestiInterventiCustomDaSalvare.add(mediazioneCultMan);
		serviziRichiestiInterventiCustomDaSalvare.add(orientamentoIstruzioneMan);
		
	}*/
	
	public IIntermediazioneAb initManIntermediazioneAb(SsScheda scheda, String VER_MAX) {
		IIntermediazioneAb man = IntermediazioneManBaseBean.initByVersion(VER_MAX);
		if (scheda != null)
				man.setIdSchedaUdc(scheda.getId());
		return man;
	}
	
	
	private IOrientamentoLavoro initOrientamentoLavoroManBean(SsScheda scheda, String VER_MAX) {
		IOrientamentoLavoro man = (IOrientamentoLavoro) OrientamentoLavoroManBaseBean.initByVersion(VER_MAX);
		if (scheda != null)
			man.setIdSchedaUdc(scheda.getId());
		NuovaSchedaWizard nuovaSchedaWizard = (NuovaSchedaWizard)getBeanReference("nuovaSchedaWizard");
		if(nuovaSchedaWizard!=null)
			nuovaSchedaWizard.onChangeValorizzaCondLavoro(man); //Valorizzo la combo lavoro con il valore impostato nella scheda Segnalato
		return man;
	}
	
	public IMediazioneCult initManMediazioneCult(SsScheda scheda, String VER_MAX) {
		IMediazioneCult man = MediazioneCultManBaseBean.initByVersion(VER_MAX);
		if (scheda != null)
			man.setIdSchedaUdc(scheda.getId());
		return man;
	}
	
	public IOrientamentoIstruzione initManOrientamentoIstruzione(SsScheda scheda, String VER_MAX) {
		IOrientamentoIstruzione man = OrientamentoIstruzioneManBaseBean.initByVersion(VER_MAX);
		if (scheda != null)
			man.setIdSchedaUdc(scheda.getId());
		NuovaSchedaWizard nuovaSchedaWizard = (NuovaSchedaWizard)getBeanReference("nuovaSchedaWizard");
		if(nuovaSchedaWizard!=null)
			nuovaSchedaWizard.datiAggiuntiviOrientamentoIstruzione(man); // Valorizzo titolo di studio e conoscenza lingua ai valori impostati nei tab precedenti
		return man;
	}
	
	//INIZIO  SISO-438
	public IServizioRichiestoCustom initManServizioRichiestoCustom(SsScheda scheda, String VER_MAX, int selectedTipoInterventoCustomId, CsOSettore settoreErogante) {
		IServizioRichiestoCustom man = ServizioRichiestoCustomManBaseBean.initByVersion(VER_MAX, isResidenteFuoriAmbito());
		if (scheda != null){
			man.setIdSchedaUdc(scheda.getId());	
		}

		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		SegretariatoSocBaseBean.fillEnte(dto); 
		dto.setObj((long) selectedTipoInterventoCustomId);
		CsCTipoInterventoCustom csCTipoInterventoCustom = getConfigurationCsBean().findTipoInterventoCustomById(dto);
		if(csCTipoInterventoCustom!=null)
		{
			man.setTipoInterventoCustom( csCTipoInterventoCustom.getDescrizione()); 
			man.setTipoInterventoCustomId(selectedTipoInterventoCustomId);
		}
		
		// #ROMACAPITALE
		if(settoreErogante != null)
		{
			man.setSettoreEroganteDescrizione(settoreErogante.getNome()); 
			man.setSettoreEroganteId(settoreErogante.getId());
		}		
		
		return man;
	}
	//FINE SISO-438
	
	public void setIdSchedaUdc(Long schedaId) {
		for (ISchedaValutazione iSchedaValutazione : serviziRichiestiInterventiCustomDaSalvare) {
			iSchedaValutazione.setIdSchedaUdc(schedaId);
		} 
//		intermediazioneAbMan.setIdSchedaUdc(schedaId);
//		orientamentoLavoroManBean.setIdSchedaUdc(schedaId);
//		mediazioneCultMan.setIdSchedaUdc(schedaId);
//		orientamentoIstruzioneMan.setIdSchedaUdc(schedaId);
		
	}


	//INIZIO SISO-438
	private void eliminaDiariScheda(SsScheda scheda, long diarioId)
			throws Exception {
		try {
			if (scheda.getId() != null && scheda.getId() > 0) { 
				AccessTableDiarioSessionBeanRemote diarioService = getDiarioCsBean();
				it.webred.cs.csa.ejb.dto.BaseDTO bcs = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(bcs);
				bcs.setObj(diarioId);
				bcs.setObj2(null);
				diarioService.deleteSchedaJson(bcs); 
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	//FINE SISO-438
	
	private void eliminaDiariScheda(SsScheda scheda, int tipoDiario)
			throws Exception {
		try {
			if (scheda.getId() != null && scheda.getId() > 0) {
				AccessTableDiarioSessionBeanRemote diarioService = getDiarioCsBean();
				it.webred.cs.csa.ejb.dto.BaseDTO bcs = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(bcs);
				bcs.setObj(scheda.getId());
				bcs.setObj2(tipoDiario);
				diarioService.deleteSchedeValutazioneByUdcId(bcs);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	private IIntermediazioneAb loadSchedaJsonIAb(SsScheda scheda) throws Exception {
		IIntermediazioneAb man = new SegretariatoSocBaseBean().getSchedaJsonIntermediazioneAb(scheda.getId());
//		if (man  == null)
//			man = initManIntermediazioneAb(scheda, nuovaSchedaWizard.VER_MAX); 
		
		return man;
	}
	
	private IOrientamentoLavoro loadSchedaJsonOrientamentoLavoro(SsScheda scheda) throws Exception {
		IOrientamentoLavoro man = new SegretariatoSocBaseBean().getSchedaJsonOrientamentoLavoro(scheda.getId());
//		if (man == null)
//			man = initOrientamentoLavoroManBean(scheda, nuovaSchedaWizard.VER_MAX); 
		return man;
	}

	private IMediazioneCult loadSchedaJsonMediazioneCult(SsScheda scheda) throws Exception {
		IMediazioneCult man = new SegretariatoSocBaseBean().getSchedaJsonMediazioneCult(scheda.getId());
//		if (man == null)
//			man = initManMediazioneCult(scheda, nuovaSchedaWizard.VER_MAX);
		return man;
	}

	private IOrientamentoIstruzione loadSchedaJsonOrientamentoIstruzione(SsScheda scheda) throws Exception {
		IOrientamentoIstruzione man = new SegretariatoSocBaseBean().getSchedaJsonOrientamentoIstruzione(scheda.getId());
//		if (man == null)
//			man = initManOrientamentoIstruzione(scheda, nuovaSchedaWizard.VER_MAX);
		return man;
	}

//inizio SISO-438
	public List<IServizioRichiestoCustom> loadSchedaJsonServiziRichiestiCustom(Long schedaId ) {
		return  getSchedaJsonServiziRichiestiCustom(schedaId);
	}
	

	public List<IServizioRichiestoCustom> getSchedaJsonServiziRichiestiCustom(Long schedaId
			//, boolean loadToClone
			){
		List<IServizioRichiestoCustom> result = new ArrayList<IServizioRichiestoCustom>();
		try {
			List<CsDValutazione> listValutazione = getSchedeValutazione(schedaId, DataModelCostanti.TipoDiario.RICHIESTA_SERVIZIO_ID);
			for (CsDValutazione csDValutazione : listValutazione) {
				IServizioRichiestoCustom man = (IServizioRichiestoCustom) ServizioRichiestoCustomManBaseBean.initByModel(csDValutazione, isResidenteFuoriAmbito());
				result.add(man);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SegretariatoSocBaseBean.addError("lettura.error");
		}

		return result;
	}
	
	protected List<CsDValutazione> getSchedeValutazione(Long schedaId, int tipoDiario) throws NamingException {
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		new SegretariatoSocBaseBean().fillUserData(dto);
		dto.setObj(schedaId);
		dto.setObj2(tipoDiario);

		AccessTableDiarioSessionBeanRemote diarioService;
		diarioService = getDiarioCsBean();
		List<CsDValutazione> schede = diarioService.getSchedeValutazioneUdcId(dto);
		if (schede == null || schede.isEmpty()){
			schede = new ArrayList<CsDValutazione>();
		} 
		return schede;
	}
	
	
/*	private IServizioRichiestoCustom loadSchedaJsonServizioRichiestoCustom(SsScheda scheda, int selectedTipoInterventoCustomId, long diarioId) throws Exception {
		IServizioRichiestoCustom man = getSchedaJsonServizioRichiestoCustom(diarioId);
		if (man == null)
			man = initManServizioRichiestoCustom(scheda, VER_MAX, selectedTipoInterventoCustomId);
		return man;
	}*/
//FINE SISO-438
	
	public boolean salvaManJsonServiziRichiesti(boolean salvato, SsScheda scheda) throws Exception {
		int index = 0;
		for (ISchedaValutazione schedaValutazione : serviziRichiestiInterventiCustomDaSalvare) {
			salvato &= schedaValutazione.save();
		}
		
		List<ISchedaValutazione> serviziRichiestiInterventiCustomDaCancellare = getServiziRichiestiInterventiCustomDaCancellare();

		if (scheda.getId() != null && scheda.getId() > 0) {
			for (ISchedaValutazione iSchedaValutazioneDaCancellare : serviziRichiestiInterventiCustomDaCancellare) {
				if (iSchedaValutazioneDaCancellare instanceof IServizioRichiestoCustom ) {
						IServizioRichiestoCustom servizioRichiestoCustom = (IServizioRichiestoCustom) iSchedaValutazioneDaCancellare;
						servizioRichiestoCustom.eliminaDocumenti();
						eliminaDiariScheda(scheda, iSchedaValutazioneDaCancellare.getCurrentModel().getDiarioId() ); 
				} else if (iSchedaValutazioneDaCancellare instanceof IIntermediazioneAb ) {
					eliminaDiariScheda(scheda, DataModelCostanti.TipoDiario.INTERMEDIAZIONE_AB_ID);
				} else if (iSchedaValutazioneDaCancellare instanceof IOrientamentoLavoro ) {
					eliminaDiariScheda(scheda, DataModelCostanti.TipoDiario.ORIENTAMENTO_LAVORO_ID);
				} else if (iSchedaValutazioneDaCancellare instanceof IMediazioneCult ) {
					eliminaDiariScheda(scheda, DataModelCostanti.TipoDiario.MEDIAZIONE_CULT_ID);
				} else if (iSchedaValutazioneDaCancellare instanceof IOrientamentoIstruzione ) {
					eliminaDiariScheda(scheda, DataModelCostanti.TipoDiario.ORIENTAMENTO_ISTRUZIONE_ID);
				}
				//serviziRichiestiInterventiCustomSalvati.remove(iSchedaValutazioneDaCancellare);		
				
			}
		} else {
			logger.debug("salvaManJsonServiziRichiesti scheda id = " + scheda.getId());
		}
		
		serviziRichiestiInterventiCustomSalvati.clear();
		serviziRichiestiInterventiCustomSalvati.addAll(serviziRichiestiInterventiCustomDaSalvare);
		
		return salvato;
	}

	private List<ISchedaValutazione> getServiziRichiestiInterventiCustomDaCancellare() {
		List<ISchedaValutazione> result = new ArrayList<ISchedaValutazione>();
		
		for (ISchedaValutazione  schedaValutazioneSalvata: serviziRichiestiInterventiCustomSalvati) {
			if ( schedaValutazioneSalvata instanceof IServizioRichiestoCustom) {
				boolean presente = false;
				
				for (ISchedaValutazione  schedaValutazioneDaSalvare : serviziRichiestiInterventiCustomDaSalvare) {
					if (schedaValutazioneDaSalvare instanceof IServizioRichiestoCustom ) {
						IServizioRichiestoCustom iServizioRichiestoCustomSalvato = (IServizioRichiestoCustom) schedaValutazioneSalvata;
						IServizioRichiestoCustom iServizioRichiestoCustomDaSalvare = (IServizioRichiestoCustom) schedaValutazioneDaSalvare;
						if ( iServizioRichiestoCustomSalvato.getTipoInterventoCustomId() == iServizioRichiestoCustomDaSalvare.getTipoInterventoCustomId()) {
							presente = true;
						}
					}
				}
				if (!presente) {
					result.add(schedaValutazioneSalvata);
				}
				
			} else {
				if (!serviziRichiestiInterventiCustomDaSalvare.contains(schedaValutazioneSalvata)) {
					result.add(schedaValutazioneSalvata);
				}				
			}
			
//			else if (schedaValutazioneSalvata instanceof IIntermediazioneAb ) { 
//				
//				boolean presente = false; 
//				for (ISchedaValutazione  schedaValutazioneDaSalvare : serviziRichiestiInterventiCustomDaSalvare) {
//					if (schedaValutazioneDaSalvare instanceof IIntermediazioneAb ) { 
//							presente = true; 
//					}
//				}
//				if (!presente) {
//					result.add(schedaValutazioneSalvata);
//				}
//			} else if (schedaValutazioneSalvata instanceof IOrientamentoLavoro ) { 
//			} else if (schedaValutazioneSalvata instanceof IMediazioneCult ) { 
//			} else if (schedaValutazioneSalvata instanceof IOrientamentoIstruzione ) { 
//			} 
			 
		}
		
		return result;
	}


	/**
	 * metodo invocato al click del pulsante "Importa da precedente"
	 */
	public void nuoviDaSchedaJson(Long schedaPrecedenteId, Long schedaId) { 
		
		serviziRichiestiInterventiCustomDaSalvare = new ArrayList<ISchedaValutazione>();
		
		IIntermediazioneAb iab = this.nuovaDaSchedaJsonIntermediazioneAb(schedaPrecedenteId);
		IMediazioneCult mc = this .nuovaDaSchedaJsonMediazioneCulturale(schedaPrecedenteId);
		IOrientamentoIstruzione oi = this.nuovaDaSchedaJsonOrientamentoIstruzione(schedaPrecedenteId);
		IOrientamentoLavoro ol = this.nuovaDaSchedaJsonOrientamentoLavoro(schedaPrecedenteId);
 
		if (iab != null) {
			//this.setIntermediazioneAbMan(iab);
			//this.getIntermediazioneAbMan().setIdSchedaUdc(schedaId); 
			iab.setIdSchedaUdc(schedaId);
			serviziRichiestiInterventiCustomDaSalvare.add(iab);
		}
		if (mc != null) {
			//this.setMediazioneCultMan(mc);
			//this.getMediazioneCultMan() .setIdSchedaUdc(schedaId); 
			mc.setIdSchedaUdc(schedaId);
			serviziRichiestiInterventiCustomDaSalvare.add(mc);
		}
		if (oi != null) {
			//this.setOrientamentoIstruzioneMan(oi);
			//this.getOrientamentoIstruzioneMan().setIdSchedaUdc(schedaId); 
			oi.setIdSchedaUdc(schedaId);
			serviziRichiestiInterventiCustomDaSalvare.add(oi);
		}
		if (ol != null) {
			//this.setOrientamentoLavoroManBean(ol);
			//this.getOrientamentoLavoroManBean().setIdSchedaUdc(schedaId); 
			//logger.debug( this.getOrientamentoLavoroManBean().isNew() );
			ol.setIdSchedaUdc(schedaId);
			serviziRichiestiInterventiCustomDaSalvare.add(ol);
		}
		
		 List<IServizioRichiestoCustom> iServizioRichiestoCustomList = this.nuoviDaSchedaJsonServiziRichiestiCustom(schedaPrecedenteId);
		 
		 for (IServizioRichiestoCustom iServizioRichiestoCustom : iServizioRichiestoCustomList) {
			 iServizioRichiestoCustom.setIdSchedaUdc(schedaId);
			 serviziRichiestiInterventiCustomDaSalvare.add(iServizioRichiestoCustom);
		}
		
	}

 
	private List<IServizioRichiestoCustom> nuoviDaSchedaJsonServiziRichiestiCustom(Long schedaPrecedenteId) {
		List<IServizioRichiestoCustom> result = new ArrayList<IServizioRichiestoCustom>();
		 
		List<IServizioRichiestoCustom> listaIServizioRichiestoCustom = loadSchedaJsonServiziRichiestiCustom(schedaPrecedenteId
				//, false
				);
		
		for (IServizioRichiestoCustom iServizioRichiestoCustom : listaIServizioRichiestoCustom) {
			IServizioRichiestoCustom added = ServizioRichiestoCustomManBaseBean.init(iServizioRichiestoCustom, isResidenteFuoriAmbito());
			result.add(added);  
		}  
		
		return result;
	}


	protected IOrientamentoIstruzione nuovaDaSchedaJsonOrientamentoIstruzione(Long schedaId){
		IOrientamentoIstruzione man1 = getSchedaJsonOrientamentoIstruzione(schedaId);
		if(man1!=null) return OrientamentoIstruzioneManBaseBean.init(man1);
		else return null;
	}
	
	protected IMediazioneCult nuovaDaSchedaJsonMediazioneCulturale(Long schedaId){
		IMediazioneCult man1 = getSchedaJsonMediazioneCult(schedaId);
		if(man1!=null) return MediazioneCultManBaseBean.init(man1);
		else return null;
	}

	protected IIntermediazioneAb nuovaDaSchedaJsonIntermediazioneAb(Long schedaId){
		IIntermediazioneAb man1 = getSchedaJsonIntermediazioneAb(schedaId);
		if(man1!=null) return IntermediazioneManBaseBean.init(man1);
		else return null;
	}

	protected IOrientamentoLavoro nuovaDaSchedaJsonOrientamentoLavoro(Long schedaId){
		IOrientamentoLavoro man1 = getSchedaJsonOrientamentoLavoro(schedaId);
		if(man1!=null) return OrientamentoLavoroManBaseBean.init(man1);
		else return null;
	}

	
	public void elimina(ISchedaValutazione servizio){
		serviziRichiestiInterventiCustomDaSalvare.remove(servizio);
	}
	
	public void aggiungi(boolean schedaInviata){ 
		/**
		 *  per alcuni tipi di interventi custom ci sono form dedicate 
		 *  es. allo stato attuale dello sviluppo della SISO-438 le form dedicate per gli interventi custom sono:
		 *  "Orientamento / Inserimento Lavoro"
		 *  "Mediazione Culturale"
		 *  "Intermediazione Abitativa"
		 *  "Orientamento all'istruzione / formazione"
		 *  
		 *  gli altri interventi custom vengono gestiti con la form pnlServizioRichiestoCustom
		 */
		int selectedTipoInterventoCustomId = this.getSelectedTipoInterventoCustom().intValue();	
	   
		ISchedaValutazione nuovaISchedaValutazione = null;
		if(serviziRichiestiInterventiCustomDaSalvareContainsTipoInterventoCustomId(selectedTipoInterventoCustomId))
			this.addWarningMessage("Attenzione", "Il tipo di intervento è già presente nella lista");
		else{
			switch (selectedTipoInterventoCustomId) {
		
				case TipiInterventoCustom.UDC_ORIENTAMENTO_INSERIMENTO_LAVORO:
					 nuovaISchedaValutazione = initOrientamentoLavoroManBean(scheda, VER_MAX);
					 break; 
				case TipiInterventoCustom.UDC_MEDIAZIONE_CULTURALE:
					 nuovaISchedaValutazione = initManMediazioneCult(scheda, VER_MAX);
					 break;
				case TipiInterventoCustom.UDC_INTERMEDIAZIONE_ABITATIVA:
					 nuovaISchedaValutazione = initManIntermediazioneAb(scheda, VER_MAX);
					 break;
				case TipiInterventoCustom.UDC_ORIENTAMENTO_ALL_ISTRUZIONE_FORMAZIONE: 	
					 nuovaISchedaValutazione = initManOrientamentoIstruzione(scheda, VER_MAX);
					 break;
					
				// SISO-659				
				case TipiInterventoCustom.UDC_INVIO_A_ORGANIZZAZIONE_ESTERNA:
				case TipiInterventoCustom.UDC_INVIO_ALTRO_UFFICIO: {
						if ( (TipiInterventoCustom.UDC_INVIO_A_ORGANIZZAZIONE_ESTERNA==selectedTipoInterventoCustomId && serviziRichiestiInterventiCustomDaSalvareContainsTipoInterventoCustomId(TipiInterventoCustom.UDC_INVIO_ALTRO_UFFICIO))
								 || (TipiInterventoCustom.UDC_INVIO_ALTRO_UFFICIO==selectedTipoInterventoCustomId && serviziRichiestiInterventiCustomDaSalvareContainsTipoInterventoCustomId(TipiInterventoCustom.UDC_INVIO_A_ORGANIZZAZIONE_ESTERNA)) ) 
						{
							this.addWarningMessage("Attenzione", "Il servizio richiesto prevede l'invio della scheda ma esiste già una richiesta di invio");
						}
						else if (schedaInviata)
						{
							this.addWarningMessage("Attenzione", "Il servizio richiesto prevede l'invio della scheda ma la Scheda risulta già inviata.");
						}
						else 
						{
							nuovaISchedaValutazione = initManServizioRichiestoCustom(scheda, VER_MAX, selectedTipoInterventoCustomId, null);					
						}				
					}
					break;
				default:{
						// #ROMACAPITALE aggiunto il settoreErogante
						nuovaISchedaValutazione = initManServizioRichiestoCustom(scheda, VER_MAX, selectedTipoInterventoCustomId, settoreSelezionato);
						}
						break;
			} 
		
				if (nuovaISchedaValutazione!=null) {
					serviziRichiestiInterventiCustomDaSalvare.add(0, nuovaISchedaValutazione);			
				}
		}
		
		
	}
	
	
	private boolean serviziRichiestiInterventiCustomDaSalvareContainsTipoInterventoCustomId( int sel) {
		for (ISchedaValutazione iSchedaValutazione : serviziRichiestiInterventiCustomDaSalvare) {
			if (iSchedaValutazione instanceof IServizioRichiestoCustom) {
				if (sel == ((IServizioRichiestoCustom)iSchedaValutazione).getTipoInterventoCustomId()) {
					return true;
				}
			}
			else if(iSchedaValutazione instanceof IIntermediazioneAb && sel==TipiInterventoCustom.UDC_INTERMEDIAZIONE_ABITATIVA)
				return true; 
			else if(iSchedaValutazione instanceof IOrientamentoLavoro && sel==TipiInterventoCustom.UDC_ORIENTAMENTO_INSERIMENTO_LAVORO)
				return true; 
			else if(iSchedaValutazione instanceof IMediazioneCult && sel==TipiInterventoCustom.UDC_MEDIAZIONE_CULTURALE)
				return true; 
			else if(iSchedaValutazione instanceof IOrientamentoIstruzione && sel==TipiInterventoCustom.UDC_ORIENTAMENTO_ALL_ISTRUZIONE_FORMAZIONE)
				return true; 

		}
		return false;
	}
    
	public Long getSelectedTipoInterventoCustom() {
		return selectedTipoInterventoCustom;
	}

	public void setSelectedTipoInterventoCustom(Long selectedTipoInterventoCustom) {
		this.selectedTipoInterventoCustom = selectedTipoInterventoCustom;
	}

	private boolean isResidenteFuoriAmbito() {
		
		String codIstatComune = null;
		boolean senzaFissaDimora = false;
		if(segnalato!=null) {
			codIstatComune = segnalato.getResidenza()!=null ? segnalato.getResidenza().getComuneCod() : null;
			senzaFissaDimora = segnalato.getSenzaFissaDimora();
		}
		boolean residenzaFuoriAmbito = !senzaFissaDimora && isComuneFuoriAmbito(codIstatComune);
	
		return residenzaFuoriAmbito;
	}
	
	public void aggiornaResidenzaFuoriAmbito(SsScheda scheda, SsSchedaSegnalato segnalato){
		this.segnalato = segnalato;
		this.scheda = scheda;
		boolean residenzaFuoriAmbito = this.isResidenteFuoriAmbito();
		if(serviziRichiestiInterventiCustomDaSalvare!=null){
			for(ISchedaValutazione val : serviziRichiestiInterventiCustomDaSalvare){
				try{
					CsTbTipoDiario tipo = val.getCurrentModel()!=null ? val.getCurrentModel().getCsDDiario().getCsTbTipoDiario() : null;
					if(tipo!=null && tipo.getId()==DataModelCostanti.TipoDiario.RICHIESTA_SERVIZIO_ID){
						IServizioRichiestoCustom valc = ((IServizioRichiestoCustom)val);
						valc.setResidenzaFuoriAmbito(residenzaFuoriAmbito);
					}
				}catch(Exception e){
					logger.error(e.getMessage()+"SS_SCHEDA "+scheda.getId() , e);
				}
			}
		}
	}
	
	public boolean onChangeValorizzaTitStudio(BigDecimal idTitoloStudio, List<SelectItem> lstTitoliStudio) {
		boolean presente = false;
		for(ISchedaValutazione man: serviziRichiestiInterventiCustomDaSalvare ){
			if(man instanceof IOrientamentoIstruzione){
				((IOrientamentoIstruzione)man).preValorizzaDati(idTitoloStudio, lstTitoliStudio, null);
				presente = true;
			}
		}
		return presente;
	}
	
	public boolean preValorizzaLavoro(BigDecimal idLavoro) {
		boolean presente = false;
		for(ISchedaValutazione man: serviziRichiestiInterventiCustomDaSalvare ){
			if(man instanceof IOrientamentoLavoro){
				((IOrientamentoLavoro)man).preValorizzaLavoro(idLavoro);
				presente = true;
			}
		}
		return presente;
	}
    
}
