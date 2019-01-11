package it.webred.ss.web.bean.wizard;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.primefaces.model.UploadedFile;

import it.webred.cet.permission.CeTUser;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsDValutazione;
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
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.SsSearchCriteria;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.lista.Scheda;
import it.webred.ss.web.bean.util.PreselPuntoContatto;

/**
 * classe creata per SISO-438-Possibilità di allegare documenti in UdC
 * Mappa i dati del pannello dei servizi richiesti interventi custom ( nella pagina tab5_ServiziRichiesti.xhtml )
 */
public class ServiziRichiestiInterventiCustomBean {
	public static Logger logger = Logger.getLogger("segretariatosoc.log");
	

	private static AccessTableDiarioSessionBeanRemote diarioService;
	public static final int 	TIPO_INTERVENTO_CUSTOM_ID_INTERMEDIAZIONE_ABITATIVA		 = 79;
	public static final int 	TIPO_INTERVENTO_CUSTOM_ID_ORIENTAMENTO_INSERIMENTO_LAVORO	 = 80;
	public static final int 	TIPO_INTERVENTO_CUSTOM_ID_MEDIAZIONE_CULTURALE		 	 = 81;
	public static final int 	TIPO_INTERVENTO_CUSTOM_ID_ORIENTAMENTO_ALL_ISTRUZIONE_FORMAZIONE = 82;
	public static final int 	TIPO_INTERVENTO_CUSTOM_ID_INVIO_ALTRO_ENTE		 	 = 176;
	public static final int 	TIPO_INTERVENTO_CUSTOM_ID_INVIO_ALTRO_UFFICIO	 	 = 177;
	
	
	private NuovaSchedaWizard nuovaSchedaWizard;
	private List<ISchedaValutazione> serviziRichiestiInterventiCustomSalvati;
	private List<ISchedaValutazione> serviziRichiestiInterventiCustomDaSalvare;
	private List<ISchedaValutazione> listaServizioRichiestoCustomAltreSchede;	//lista dei servizi custom per le altre schede dell'utente in questione
	
	public ServiziRichiestiInterventiCustomBean(NuovaSchedaWizard nuovaSchedaWizard, SsScheda scheda) {
		serviziRichiestiInterventiCustomSalvati = new ArrayList<ISchedaValutazione>();
		serviziRichiestiInterventiCustomDaSalvare = new ArrayList<ISchedaValutazione>();
		
		this.nuovaSchedaWizard = nuovaSchedaWizard;
	}
	

	public void loadManJsonServiziRichiesti(SsScheda scheda
			//, boolean loadToClone
			) throws Exception {
		serviziRichiestiInterventiCustomSalvati.clear();
		serviziRichiestiInterventiCustomDaSalvare.clear();
		
		intermediazioneAbMan = loadSchedaJsonIAb(scheda); 
		if (intermediazioneAbMan!=null) {
			serviziRichiestiInterventiCustomSalvati.add(intermediazioneAbMan);
			serviziRichiestiInterventiCustomDaSalvare.add(intermediazioneAbMan);
		}
		
		orientamentoLavoroManBean = loadSchedaJsonOrientamentoLavoro(scheda); 
		if (orientamentoLavoroManBean!=null) {
			serviziRichiestiInterventiCustomSalvati.add(orientamentoLavoroManBean);
			serviziRichiestiInterventiCustomDaSalvare.add(orientamentoLavoroManBean);
		}
		
		mediazioneCultMan = loadSchedaJsonMediazioneCult(scheda); 
		if (mediazioneCultMan!=null) {
			serviziRichiestiInterventiCustomSalvati.add(mediazioneCultMan);
			serviziRichiestiInterventiCustomDaSalvare.add(mediazioneCultMan);
		} 
		orientamentoIstruzioneMan = loadSchedaJsonOrientamentoIstruzione(scheda);
		if (orientamentoIstruzioneMan!=null) {
			serviziRichiestiInterventiCustomSalvati.add(orientamentoIstruzioneMan);
			serviziRichiestiInterventiCustomDaSalvare.add(orientamentoIstruzioneMan);
		}

		
		 List<IServizioRichiestoCustom> iServizioRichiestoCustomList = loadSchedaJsonServiziRichiestiCustom(scheda.getId()
				 //, loadToClone
				 );
		 
		 for (IServizioRichiestoCustom iServizioRichiestoCustom : iServizioRichiestoCustomList) {
			 serviziRichiestiInterventiCustomSalvati.add(iServizioRichiestoCustom);
			 serviziRichiestiInterventiCustomDaSalvare.add(iServizioRichiestoCustom);
		}
	}
	
	
	/**
	 * se non è presente, carico la lista delle schede di valutazione di tutte le altre schede COMPLETE e della stessa organizzazione sulla quale l'operatore sta lavorando,
	 *  il cui segnalato ha lo stesso codice fiscale di quello attuale
	 */
	public List<ISchedaValutazione> getListaServizioRichiestoCustomAltreSchede() {
		try {
			if (listaServizioRichiestoCustomAltreSchede==null) {
				listaServizioRichiestoCustomAltreSchede = new ArrayList<ISchedaValutazione>();
				if (nuovaSchedaWizard!=null && nuovaSchedaWizard.getScheda() != null) {
					String cf = nuovaSchedaWizard.getSegnalato().getAnagrafica().getCodiceFiscale();

					logger.debug("getListaServizioRichiestoCustomAltreSchede " + cf + " id scheda attuale " + nuovaSchedaWizard.getScheda().getId());
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
						
						//filtra le schede, devono essere diverse da quella attuale, complete e  della stessa organizzazione sulla quale l'operatore sta lavorando
						if (!ssScheda.getId().equals(nuovaSchedaWizard.getScheda().getId())
								&& ssScheda.getCompleta()
								&& ssScheda.getAccesso().getSsRelUffPcontOrg().getId().getOrganizzazioneId().longValue() == pContMan.getPuntoContatto().getOrganizzazione().getId().longValue() ) {
							List<IServizioRichiestoCustom> listaServizioRichiestoCustoms = getSchedaJsonServiziRichiestiCustom( ssScheda.getId());
							listaServizioRichiestoCustomAltreSchede.addAll(listaServizioRichiestoCustoms);
							
							IIntermediazioneAb intermediazioneAbMan = loadSchedaJsonIAb(ssScheda); 
							if (intermediazioneAbMan!=null) { 
								listaServizioRichiestoCustomAltreSchede.add(intermediazioneAbMan);
							}
							
							IOrientamentoLavoro orientamentoLavoroManBean = loadSchedaJsonOrientamentoLavoro(ssScheda); 
							if (orientamentoLavoroManBean!=null) {
								listaServizioRichiestoCustomAltreSchede.add(orientamentoLavoroManBean);
							}
							
							IMediazioneCult mediazioneCultMan = loadSchedaJsonMediazioneCult(ssScheda); 
							if (mediazioneCultMan!=null) {
								listaServizioRichiestoCustomAltreSchede.add(mediazioneCultMan);
							} 
							IOrientamentoIstruzione orientamentoIstruzioneMan = loadSchedaJsonOrientamentoIstruzione(ssScheda);
							if (orientamentoIstruzioneMan!=null) {
								listaServizioRichiestoCustomAltreSchede.add(orientamentoIstruzioneMan);
							}

							
						}
						 
					}					
				}

			
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SegretariatoSocBaseBean.addError("lettura.error");
		}

		
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
	
	private IIntermediazioneAb intermediazioneAbMan;
	private IOrientamentoLavoro orientamentoLavoroManBean;
	private IMediazioneCult mediazioneCultMan;
	private IOrientamentoIstruzione orientamentoIstruzioneMan;
	

	/**
	 * chiamato alla creazione del bean che gestisce tutto il wizard della scheda
	 */
	public void initManJsonServiziRichiesti(SsScheda scheda, String VER_MAX){
		this.intermediazioneAbMan = this.initManIntermediazioneAb(scheda, VER_MAX);
		this.orientamentoLavoroManBean = this.initOrientamentoLavoroManBean(scheda, VER_MAX);
		this.mediazioneCultMan = this.initManMediazioneCult(scheda, VER_MAX);
		this.orientamentoIstruzioneMan = this.initManOrientamentoIstruzione(scheda, VER_MAX);

		serviziRichiestiInterventiCustomDaSalvare.add(intermediazioneAbMan);
		serviziRichiestiInterventiCustomDaSalvare.add(orientamentoLavoroManBean);
		serviziRichiestiInterventiCustomDaSalvare.add(mediazioneCultMan);
		serviziRichiestiInterventiCustomDaSalvare.add(orientamentoIstruzioneMan);
		
	}
	
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
		nuovaSchedaWizard.datiAggiuntiviOrientamentoIstruzione(man); // Valorizzo titolo di studio e conoscenza lingua ai valori impostati nei tab precedenti
		return man;
	}
	
	//INIZIO  SISO-438
	public IServizioRichiestoCustom initManServizioRichiestoCustom(SsScheda scheda, String VER_MAX, int selectedTipoInterventoCustomId) {
		IServizioRichiestoCustom man = ServizioRichiestoCustomManBaseBean.initByVersion(VER_MAX);
		if (scheda != null){
			man.setIdSchedaUdc(scheda.getId());			
		}

		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		SegretariatoSocBaseBean.fillEnte(dto); 
		dto.setObj((long) selectedTipoInterventoCustomId);
		CsCTipoInterventoCustom csCTipoInterventoCustom = nuovaSchedaWizard.interventoService.findTipiInterventoCustomById(dto);
		if(csCTipoInterventoCustom!=null)
		{
			man.setTipoInterventoCustom( csCTipoInterventoCustom.getDescrizione()); 
			man.setTipoInterventoCustomId(selectedTipoInterventoCustomId);
		}
		
		return man;
	}
	//FINE SISO-438
	
	public IIntermediazioneAb getIntermediazioneAbMan() {
		return intermediazioneAbMan;
	}
	public void setIntermediazioneAbMan(IIntermediazioneAb intermediazioneAbMan) {
		this.intermediazioneAbMan = intermediazioneAbMan;
	}
	public IOrientamentoLavoro getOrientamentoLavoroManBean() {
		return orientamentoLavoroManBean;
	}
	public void setOrientamentoLavoroManBean(
			IOrientamentoLavoro orientamentoLavoroManBean) {
		this.orientamentoLavoroManBean = orientamentoLavoroManBean;
	}
	public IMediazioneCult getMediazioneCultMan() {
		return mediazioneCultMan;
	}
	public void setMediazioneCultMan(IMediazioneCult mediazioneCultMan) {
		this.mediazioneCultMan = mediazioneCultMan;
	}
	public IOrientamentoIstruzione getOrientamentoIstruzioneMan() {
		return orientamentoIstruzioneMan;
	}
	public void setOrientamentoIstruzioneMan(
			IOrientamentoIstruzione orientamentoIstruzioneMan) {
		this.orientamentoIstruzioneMan = orientamentoIstruzioneMan;
	}

	public void setIdSchedaUdc(Long schedaId) {
		for (ISchedaValutazione iSchedaValutazione : serviziRichiestiInterventiCustomDaSalvare) {
			iSchedaValutazione.setIdSchedaUdc(schedaId);
		} 
//		intermediazioneAbMan.setIdSchedaUdc(schedaId);
//		orientamentoLavoroManBean.setIdSchedaUdc(schedaId);
//		mediazioneCultMan.setIdSchedaUdc(schedaId);
//		orientamentoIstruzioneMan.setIdSchedaUdc(schedaId);
		
	}

	

	public boolean salvaSchedaIServizioRichiestoCustom(
								boolean salvato,  
								SsScheda scheda, IServizioRichiestoCustom iServizioRichiestoCustom
								) throws Exception {
		 
		salvato = iServizioRichiestoCustom.save();  
			
		return salvato;
		
	}	
	
	
	public boolean salvaSchedaIAb(
				boolean salvato,   
				SsScheda scheda
				) throws Exception {
 
		salvato &= this.intermediazioneAbMan.save(); 

		if (salvato && this.intermediazioneAbMan.isNew())
			intermediazioneAbMan = loadSchedaJsonIAb(scheda);

		return salvato;
		
	}
	
	public boolean salvaSchedaOrientamentoLavoro(
										boolean salvato,  
										SsScheda scheda)
										throws Exception { 
		 
		salvato = orientamentoLavoroManBean.save(); 

		if (salvato && this.orientamentoLavoroManBean.isNew())
			orientamentoLavoroManBean = loadSchedaJsonOrientamentoLavoro(scheda);

		return salvato;
		

	}

	public boolean salvaSchedaMediazioneCult(
										boolean salvato,  
 										SsScheda scheda) throws Exception {
//		boolean pannelloRichiesto = isSchedaMediazioneCultDaSalvare();
//		 
//		if (pannelloRichiesto)
//			salvato = this.mediazioneCultMan.save();
//		else {
//			if (scheda.getId() != null && scheda.getId() > 0) {
//				eliminaDiariScheda(scheda, DataModelCostanti.TipoDiario.MEDIAZIONE_CULT_ID);
//				mediazioneCultMan = initManMediazioneCult(scheda, nuovaSchedaWizard.VER_MAX);
//			}
//		}
//
//		if (salvato && this.mediazioneCultMan.isNew())
//			mediazioneCultMan = loadSchedaJsonMediazioneCult(scheda);
//
//		return salvato;
		
		salvato = this.mediazioneCultMan.save();
		
		if (salvato && this.mediazioneCultMan.isNew())
			mediazioneCultMan = loadSchedaJsonMediazioneCult(scheda);

		return salvato;
		
	}

	public boolean salvaSchedaOrientamentoIstruzione(
								boolean salvato,  
								SsScheda scheda
								) throws Exception {
//		boolean pannelloRichiesto = isSchedaOrientamentoIstruzioneDaSalvare();	
////		boolean salvato = true;
////		boolean pannelloRichiesto = true; // intervento.getInterventi().contains(idInterventoOrientamento)
//		if (pannelloRichiesto)
//			salvato = this.orientamentoIstruzioneMan.save();
//		else {
//			if (scheda.getId() != null && scheda.getId() > 0) {
//				eliminaDiariScheda(scheda, DataModelCostanti.TipoDiario.MEDIAZIONE_CULT_ID);
//				orientamentoIstruzioneMan = initManOrientamentoIstruzione(scheda, nuovaSchedaWizard.VER_MAX);
//			}
//		}
//
//		if (salvato && this.orientamentoIstruzioneMan.isNew())
//			orientamentoIstruzioneMan = loadSchedaJsonOrientamentoIstruzione(scheda);
//
//		return salvato;
		
//		boolean salvato = true;
//		boolean pannelloRichiesto = true; // intervento.getInterventi().contains(idInterventoOrientamento)
		
		salvato = this.orientamentoIstruzioneMan.save();
		
		if (salvato && this.orientamentoIstruzioneMan.isNew())
			orientamentoIstruzioneMan = loadSchedaJsonOrientamentoIstruzione(scheda);

		return salvato;
		
	}	

	//INIZIO SISO-438
	private void eliminaDiariScheda(SsScheda scheda, long diarioId)
			throws Exception {
		try {
			if (scheda.getId() != null && scheda.getId() > 0) { 
				AccessTableDiarioSessionBeanRemote diarioService = nuovaSchedaWizard.getDiarioCsBean();
				it.webred.cs.csa.ejb.dto.BaseDTO bcs = new it.webred.cs.csa.ejb.dto.BaseDTO();
				nuovaSchedaWizard.fillUserData(bcs);
				bcs.setObj(diarioId);
				bcs.setObj2(null);
//	diarioService.deleteSchedeValutazioneByUdcId(bcs);
//				 
//					dto.setObj(v.getDiarioId());
//					dto.setObj2(null);
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
				AccessTableDiarioSessionBeanRemote diarioService = nuovaSchedaWizard.getDiarioCsBean();
				it.webred.cs.csa.ejb.dto.BaseDTO bcs = new it.webred.cs.csa.ejb.dto.BaseDTO();
				nuovaSchedaWizard.fillUserData(bcs);
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
	public static List<IServizioRichiestoCustom> loadSchedaJsonServiziRichiestiCustom(Long schedaId ) {
		return  getSchedaJsonServiziRichiestiCustom(schedaId);
	}
	

	public static List<IServizioRichiestoCustom> getSchedaJsonServiziRichiestiCustom(Long schedaId
			//, boolean loadToClone
			){
		List<IServizioRichiestoCustom> result = new ArrayList<IServizioRichiestoCustom>();
		try {
			List<CsDValutazione> listValutazione = getSchedeValutazione(schedaId, DataModelCostanti.TipoDiario.RICHIESTA_SERVIZIO_ID);
			for (CsDValutazione csDValutazione : listValutazione) {
				IServizioRichiestoCustom man = (IServizioRichiestoCustom) ServizioRichiestoCustomManBaseBean.initByModel(csDValutazione
						//,   loadToClone
						);
				result.add(man);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SegretariatoSocBaseBean.addError("lettura.error");
		}

		return result;
	}
	
	protected static List<CsDValutazione> getSchedeValutazione(Long schedaId, int tipoDiario) throws NamingException {
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
	 
	
	public static AccessTableDiarioSessionBeanRemote getDiarioCsBean() throws NamingException {
		if ( diarioService== null) {
			diarioService = (AccessTableDiarioSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");			
		}
		return diarioService;
	}
	
	
	private IServizioRichiestoCustom loadSchedaJsonServizioRichiestoCustom(SsScheda scheda, int selectedTipoInterventoCustomId, long diarioId) throws Exception {
		IServizioRichiestoCustom man = nuovaSchedaWizard.getSchedaJsonServizioRichiestoCustom(diarioId);
		if (man == null)
			man = initManServizioRichiestoCustom(scheda, nuovaSchedaWizard.VER_MAX, selectedTipoInterventoCustomId);
		return man;
	}
//FINE SISO-438
	
	public boolean salvaManJsonServiziRichiesti(boolean salvato, SsScheda scheda) throws Exception {
		for (ISchedaValutazione schedaValutazione : serviziRichiestiInterventiCustomDaSalvare) {
			if (schedaValutazione instanceof IServizioRichiestoCustom) { 
				IServizioRichiestoCustom iServizioRichiestoCustom = (IServizioRichiestoCustom) schedaValutazione;
				salvato &= salvaSchedaIServizioRichiestoCustom(salvato, scheda, iServizioRichiestoCustom);
				
//				if (salvato && schedaValutazione.isNew()) {
//					iServizioRichiestoCustom = loadSchedaJsonServizioRichiestoCustom(scheda, 
//							iServizioRichiestoCustom.getTipoInterventoCustomId(),
//							iServizioRichiestoCustom.getDiarioId()
//							);
//				} 
			} else if (schedaValutazione instanceof IIntermediazioneAb ) { 
				salvato &= salvaSchedaIAb(salvato, scheda); 
			} else if (schedaValutazione instanceof IOrientamentoLavoro ) {  
				salvato &= salvaSchedaOrientamentoLavoro(salvato, scheda);
			} else if (schedaValutazione instanceof IMediazioneCult ) {
				salvato &= salvaSchedaMediazioneCult(salvato, scheda); 
			} else if (schedaValutazione instanceof IOrientamentoIstruzione ) {
				salvato &= salvaSchedaOrientamentoIstruzione(salvato, scheda); 
			}
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
			this.setIntermediazioneAbMan(iab);
			this.getIntermediazioneAbMan().setIdSchedaUdc(schedaId); 
			serviziRichiestiInterventiCustomDaSalvare.add(iab);
		}
		if (mc != null) {
			this.setMediazioneCultMan(mc);
			this.getMediazioneCultMan() .setIdSchedaUdc(schedaId); 
			serviziRichiestiInterventiCustomDaSalvare.add(mc);
		}
		if (oi != null) {
			this.setOrientamentoIstruzioneMan(oi);
			this.getOrientamentoIstruzioneMan().setIdSchedaUdc(schedaId); 
			serviziRichiestiInterventiCustomDaSalvare.add(oi);
		}
		if (ol != null) {
			this.setOrientamentoLavoroManBean(ol);
			this.getOrientamentoLavoroManBean().setIdSchedaUdc(schedaId); 
			

			logger.debug( this.getOrientamentoLavoroManBean().isNew() );
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
			IServizioRichiestoCustom added = ServizioRichiestoCustomManBaseBean.init(iServizioRichiestoCustom);
			result.add(added);  
		}  
		
		return result;
	}


	protected IOrientamentoIstruzione nuovaDaSchedaJsonOrientamentoIstruzione(Long schedaId){
		IOrientamentoIstruzione man1 = nuovaSchedaWizard.getSchedaJsonOrientamentoIstruzione(schedaId);
		if(man1!=null) return OrientamentoIstruzioneManBaseBean.init(man1);
		else return null;
	}
	
	protected IMediazioneCult nuovaDaSchedaJsonMediazioneCulturale(Long schedaId){
		IMediazioneCult man1 = nuovaSchedaWizard.getSchedaJsonMediazioneCult(schedaId);
		if(man1!=null) return MediazioneCultManBaseBean.init(man1);
		else return null;
	}

	protected IIntermediazioneAb nuovaDaSchedaJsonIntermediazioneAb(Long schedaId){
		IIntermediazioneAb man1 = nuovaSchedaWizard.getSchedaJsonIntermediazioneAb(schedaId);
		if(man1!=null) return IntermediazioneManBaseBean.init(man1);
		else return null;
	}

	protected IOrientamentoLavoro nuovaDaSchedaJsonOrientamentoLavoro(Long schedaId){
		IOrientamentoLavoro man1 = nuovaSchedaWizard.getSchedaJsonOrientamentoLavoro(schedaId);
		if(man1!=null) return OrientamentoLavoroManBaseBean.init(man1);
		else return null;
	}

	
	public void elimina(ISchedaValutazione servizio){
		serviziRichiestiInterventiCustomDaSalvare.remove(servizio);
	}
	
	public void aggiungi(){ 
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
		int selectedTipoInterventoCustomId = nuovaSchedaWizard.getSelectedTipoInterventoCustom().intValue();
	   
		ISchedaValutazione nuovaISchedaValutazione = null;
		switch (selectedTipoInterventoCustomId) {
			case TIPO_INTERVENTO_CUSTOM_ID_ORIENTAMENTO_INSERIMENTO_LAVORO: {
					if (serviziRichiestiInterventiCustomDaSalvare.contains(orientamentoLavoroManBean)) { 
						nuovaSchedaWizard.addWarningMessage("Attenzione", "Il tipo di intervento è già presente nella lista");
					} else {
						orientamentoLavoroManBean = initOrientamentoLavoroManBean(nuovaSchedaWizard.getScheda(), nuovaSchedaWizard.VER_MAX);
						nuovaISchedaValutazione = orientamentoLavoroManBean;
					}
				}
				break; 
			case TIPO_INTERVENTO_CUSTOM_ID_MEDIAZIONE_CULTURALE: {
					if (serviziRichiestiInterventiCustomDaSalvare.contains(mediazioneCultMan)) { 
						nuovaSchedaWizard.addWarningMessage("Attenzione", "Il tipo di intervento è già presente nella lista");
					} else {
						mediazioneCultMan = initManMediazioneCult(nuovaSchedaWizard.getScheda(), nuovaSchedaWizard.VER_MAX);
						nuovaISchedaValutazione = mediazioneCultMan;
					}
				}
				break;
			case TIPO_INTERVENTO_CUSTOM_ID_INTERMEDIAZIONE_ABITATIVA: 	{
					if (serviziRichiestiInterventiCustomDaSalvare.contains(intermediazioneAbMan)) { 
						nuovaSchedaWizard.addWarningMessage("Attenzione", "Il tipo di intervento è già presente nella lista");
					} else {
						intermediazioneAbMan = initManIntermediazioneAb(nuovaSchedaWizard.getScheda(), nuovaSchedaWizard.VER_MAX);
						nuovaISchedaValutazione = intermediazioneAbMan;
					}
				}
				break;
			case TIPO_INTERVENTO_CUSTOM_ID_ORIENTAMENTO_ALL_ISTRUZIONE_FORMAZIONE: { 
					if (serviziRichiestiInterventiCustomDaSalvare.contains(orientamentoIstruzioneMan)) { 
						nuovaSchedaWizard.addWarningMessage("Attenzione", "Il tipo di intervento è già presente nella lista");
					} else {
						orientamentoIstruzioneMan = initManOrientamentoIstruzione(nuovaSchedaWizard.getScheda(), nuovaSchedaWizard.VER_MAX);
						nuovaISchedaValutazione = orientamentoIstruzioneMan;
					}
				}
				break;
				
			// SISO-659				
			case TIPO_INTERVENTO_CUSTOM_ID_INVIO_ALTRO_ENTE:
			case TIPO_INTERVENTO_CUSTOM_ID_INVIO_ALTRO_UFFICIO: {
					if(serviziRichiestiInterventiCustomDaSalvareContainsTipoInterventoCustomId(selectedTipoInterventoCustomId)) 
					{
						nuovaSchedaWizard.addWarningMessage("Attenzione", "Il servizio richiesto che si vuole aggiungere è già presente nella lista");
					}
					else if ( (TIPO_INTERVENTO_CUSTOM_ID_INVIO_ALTRO_ENTE==selectedTipoInterventoCustomId && serviziRichiestiInterventiCustomDaSalvareContainsTipoInterventoCustomId(TIPO_INTERVENTO_CUSTOM_ID_INVIO_ALTRO_UFFICIO))
							 || (TIPO_INTERVENTO_CUSTOM_ID_INVIO_ALTRO_UFFICIO==selectedTipoInterventoCustomId && serviziRichiestiInterventiCustomDaSalvareContainsTipoInterventoCustomId(TIPO_INTERVENTO_CUSTOM_ID_INVIO_ALTRO_ENTE)) ) 
					{
						nuovaSchedaWizard.addWarningMessage("Attenzione", "Il servizio richiesto prevede l'invio della scheda ma esiste già un altra richiesta di invio presente");
					}
					else if (nuovaSchedaWizard.isSchedaInviata())
					{
						nuovaSchedaWizard.addWarningMessage("Attenzione", "Il servizio richiesto prevede l'invio della scheda ma la Scheda risulta già inviata.");
					}
					else 
					{
						nuovaISchedaValutazione = initManServizioRichiestoCustom(nuovaSchedaWizard.getScheda(), nuovaSchedaWizard.VER_MAX, selectedTipoInterventoCustomId);					
					}				
				}
				break;
			default:{
					if(serviziRichiestiInterventiCustomDaSalvareContainsTipoInterventoCustomId(selectedTipoInterventoCustomId)) {
						nuovaSchedaWizard.addWarningMessage("Attenzione", "Il servizio richiesto che si vuole aggiungere è già presente nella lista");
					} else {
						nuovaISchedaValutazione = initManServizioRichiestoCustom(nuovaSchedaWizard.getScheda(), nuovaSchedaWizard.VER_MAX, selectedTipoInterventoCustomId);					
					}
				}
				break;
		} 
		
		if (nuovaISchedaValutazione!=null) {
			serviziRichiestiInterventiCustomDaSalvare.add(0, nuovaISchedaValutazione);			
		}
	}
	
	
	private boolean serviziRichiestiInterventiCustomDaSalvareContainsTipoInterventoCustomId( int selectedTipoInterventoCustomId) {
		for (ISchedaValutazione iSchedaValutazione : serviziRichiestiInterventiCustomDaSalvare) {
			if (iSchedaValutazione instanceof IServizioRichiestoCustom) {
				if (selectedTipoInterventoCustomId == ((IServizioRichiestoCustom)iSchedaValutazione).getTipoInterventoCustomId()) {
					return true;
				}
			}
		}
		return false;
	}

	
	//------file upload
    private UploadedFile file;
	private ServizioRichiestoCustomManBaseBean servizioPopupFileUpload;
	
	public void setServizio(ServizioRichiestoCustomManBaseBean s){
		servizioPopupFileUpload = s;
	}
	
	public ServizioRichiestoCustomManBaseBean getServizioPopupFileUpload() {
		return servizioPopupFileUpload;
	}


	public void setServizioPopupFileUpload(
			ServizioRichiestoCustomManBaseBean servizioPopupFileUpload) {
		this.servizioPopupFileUpload = servizioPopupFileUpload;
	}


    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        if(file != null) {
        	logger.debug(file.getFileName());
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
             
            servizioPopupFileUpload.addFile(file);
        }
    }

}
