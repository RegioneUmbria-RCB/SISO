package it.webred.cs.csa.ejb.ejb;

import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUser;
import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CasoDAO;
import it.webred.cs.csa.ejb.dao.DiarioDAO;
import it.webred.cs.csa.ejb.dao.IterDAO;
import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.StatoCartellaDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.Segnalibri;
import it.webred.cs.data.DataModelCostanti.TipiAlertCod;
import it.webred.cs.data.DataModelCostanti.TipoAttributo;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoCategoriaSocLAZY;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCfgAttr;
import it.webred.cs.data.model.CsCfgAttrOption;
import it.webred.cs.data.model.CsCfgItStato;
import it.webred.cs.data.model.CsCfgItTransizione;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDiarioBASIC;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsItStepAttrValue;
import it.webred.cs.data.model.CsItStepAttrValueBASIC;
import it.webred.cs.data.model.CsItStepLAZY;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;
import it.webred.utilities.CommonUtils;
import it.webred.utilities.DateTimeUtils;
import it.webred.utilities.SegnalibriMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless 
public class AccessTableIterStepSessionBean extends CarSocialeBaseSessionBean implements
		AccessTableIterStepSessionBeanRemote {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IterDAO iterDao;
	
	@Autowired
	private CasoDAO casoDao;
	
	@Autowired
	private DiarioDAO diarioDao;

	@EJB
	public AccessTableAlertSessionBeanRemote alertSessionBean;

	@EJB
	public AccessTableCasoSessionBeanRemote casoSessionBean;

	@EJB
	public AccessTableOperatoreSessionBeanRemote operatoreSessionBean;

	@EJB
	public AccessTableDiarioSessionBeanRemote diarioSessionBean;

	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/AnagraficaServiceBean")
	protected AnagraficaService anagraficaService;
	
	@EJB(mappedName = "java:global/AmProfiler/AmProfilerEjb/UserServiceBean")
	protected UserService userService;

	@EJB
	protected AccessTableSoggettoSessionBeanRemote soggettiService;

	public AccessTableIterStepSessionBean() {
	}

	
	public List<CsItStep> getIterStepsByCaso(IterDTO dto) throws Exception {
		return iterDao.getIterStepListByCaso(dto.getCsACaso().getId());
	}
	
	@Override
	public  List<CsIterStepByCasoDTO> getIterStepsByCasoDTO(BaseDTO dto) throws Exception {
		
		List<CsIterStepByCasoDTO> ret = new ArrayList<CsIterStepByCasoDTO>();
		Long idCaso = (Long)dto.getObj();
		
		List<CsItStepLAZY> iterSteps = iterDao.getIterStepLAZYListByCaso(idCaso);
		CsOOperatoreBASIC operatoreResponsabile = casoDao.findResponsabileBASIC(idCaso);

	
		for (Iterator iterator = iterSteps.iterator(); iterator.hasNext();) {
			CsItStepLAZY csItStep = (CsItStepLAZY) iterator.next();

			CsIterStepByCasoDTO lastIsByCaso = populateCsIterStepByCasoDTO(csItStep, operatoreResponsabile);
			ret.add(lastIsByCaso);
			
			
		}
		
		
		return ret;
		
		
	}
	
	 
	private CsIterStepByCasoDTO populateCsIterStepByCasoDTO(CsItStepLAZY csItStep, CsOOperatoreBASIC operatoreResponsabile)  throws Exception {

		CsIterStepByCasoDTO isByCaso =  new CsIterStepByCasoDTO(csItStep );

		if (operatoreResponsabile!=null)
			isByCaso.setResponsabileUsername(operatoreResponsabile.getUsername());
		
		// carico le liste
		if(csItStep!=null){
			List<CsItStepAttrValueBASIC> attrValues = csItStep.getCsItStepAttrValues();
			if(attrValues!=null){
				for (Iterator iterator2 = attrValues.iterator(); iterator2.hasNext();) {
					CsItStepAttrValueBASIC csItStepAttrValueBASIC = (CsItStepAttrValueBASIC) iterator2.next();
					csItStepAttrValueBASIC.getCsCfgAttr().getLabel();
				}
			}
		
			Long diarioId = isByCaso.getCsItStep().getCsDDiarioId();
			CsDDiarioBASIC diario = diarioDao.findDiarioBASICById(diarioId);
	
			Date dataAmministrativa = diario.getDtAmministrativa();
			isByCaso.setDataAmministrativa(dataAmministrativa);
			
		}else 
			logger.warn("Nessun iter step per il caso!");
		
		return isByCaso;
		
	}


	private HashMap<Long, CsOOperatoreBASIC > cacheOperatori = new HashMap<Long, CsOOperatoreBASIC>();
	private HashMap<Long, CsOOperatoreBASIC > cacheOperatori2 = new HashMap<Long, CsOOperatoreBASIC>();


	
	 
	@Override
 	public  CsIterStepByCasoDTO getLastIterStepByCasoDTO(BaseDTO dto) throws Exception {
		
		Long idCaso = (Long)dto.getObj();
		
		CsOOperatoreBASIC operatoreResponsabile = casoDao.findResponsabileBASIC(idCaso);
		CsItStepLAZY csItStep = iterDao.getLastIterStepLAZYByCaso(idCaso);
		CsIterStepByCasoDTO lastIsByCaso =  populateCsIterStepByCasoDTO(csItStep,operatoreResponsabile  );
				
		return lastIsByCaso;
	}
	
	    //SISO-1297
		@Override
	    @AuditConsentiAccessoAnonimo
	    @AuditSaltaValidazioneSessionID
		public  CsIterStepByCasoDTO getLastIterStepByCasoDTOAnonimo(BaseDTO dto) throws Exception {
			
			Long idCaso = (Long)dto.getObj();
			
		 
			CsItStepLAZY csItStep = iterDao.getLastIterStepLAZYByCaso(idCaso);
			 
			CsIterStepByCasoDTO lastIsByCaso = new CsIterStepByCasoDTO(csItStep);
					
			return lastIsByCaso;
		}
	@Override
	public CsItStep getLastIterStepByCaso(IterDTO dto) throws Exception {
		return iterDao.getLastIterStepByCaso(dto.getCsACaso().getId());
	}

	private SegnalibriMap getSegnalibriMap(CsItStep iterStep) {
		String soggetto_Cf = iterStep.getCsACaso().getCsASoggetto().getCsAAnagrafica().getCf();
		String soggetto_Nome = iterStep.getCsACaso().getCsASoggetto().getCsAAnagrafica().getNome();
		String soggetto_Cognome = iterStep.getCsACaso().getCsASoggetto().getCsAAnagrafica().getCognome();

		SegnalibriMap map = new SegnalibriMap();

		map.put(Segnalibri.CASO_DATA_CREAZIONE, DateTimeUtils.dateToString(iterStep.getCsACaso().getDtIns(), "dd/MM/yy"));
		map.put(Segnalibri.CASO_DATA_MODIFICA, DateTimeUtils.dateToString(iterStep.getCsACaso().getDtMod(), "dd/MM/yy"));
		map.put(Segnalibri.CASO_ID, iterStep.getCsACaso().getId().toString());
		map.put(Segnalibri.CASO_NOME, soggetto_Nome + "  " + soggetto_Cognome+ "  (" + soggetto_Cf + ")");
		map.put(Segnalibri.CASO_USERNAME_UTENTE_CREAZIONE, iterStep.getCsACaso().getUserIns());
		map.put(Segnalibri.CASO_USERNAME_UTENTE_MODIFICA, iterStep.getCsACaso().getUsrMod());
		map.put(Segnalibri.ITERSTEP_DATA_CREAZIONE, DateTimeUtils.dateToString(iterStep.getCsDDiario().getDtIns(), "dd/MM/yy"));
		map.put(Segnalibri.SETTORE_TO, iterStep.getCsOSettore2()!=null ? iterStep.getCsOSettore2().getNome() : null);

		if(iterStep.getCsOOperatore1()!=null){
			CsOOperatoreAnagrafica operatoreAna1 = iterStep.getCsOOperatore1().getCsOOperatoreAnagrafica();
			map.put(Segnalibri.ITERSTEP_USERNAME_SEGNALANTE, iterStep.getCsOOperatore1().getUsername());
			map.put(Segnalibri.ITERSTEP_NOME_SEGNALANTE, operatoreAna1.getNome());
			map.put(Segnalibri.ITERSTEP_COGNOME_SEGNALANTE,operatoreAna1.getCognome());
		}
		if (iterStep.getCsOOperatore2() != null) {
			CsOOperatoreAnagrafica operatoreAna2 = iterStep.getCsOOperatore2().getCsOOperatoreAnagrafica();
			map.put(Segnalibri.ITERSTEP_USERNAME_SEGNALATO, iterStep.getCsOOperatore2().getUsername());
			map.put(Segnalibri.ITERSTEP_NOME_SEGNALATO, operatoreAna2.getNome());
			map.put(Segnalibri.ITERSTEP_COGNOME_SEGNALATO,operatoreAna2.getCognome());
		}
		String sItStepAttrValues = "";
		if (iterStep.getCsItStepAttrValues() != null && iterStep.getCsItStepAttrValues().size() > 0) {
			sItStepAttrValues = "<strong>"
					+ iterStep.getCsCfgItStato().getSezioneAttributiLabel()
					+ "</strong>" + "<br/>";
			List<String> listValue = new LinkedList<String>();
			for (CsItStepAttrValue itVal : iterStep.getCsItStepAttrValues())
				listValue.add("<strong>" + itVal.getCsCfgAttr().getLabel()+ "</strong>" + " : " + itVal.getValore());

			sItStepAttrValues += CommonUtils.Join("<br/>", listValue.toArray());
		}
		map.put(Segnalibri.ITERSTEP_STATO_SEZIONE_ATTRIBUTI, sItStepAttrValues);

		return map;
	}

	@Override
	public boolean newIter(IterDTO dto) throws Exception {
		
		dto.setIdStato(DataModelCostanti.IterStatoInfo.DEFAULT_ITER_STEP_ID);
		dto.setAttrNewStato(null);
		dto.setNota("");
		return addIterStep(dto);

	}

	@Override
	public boolean addIterStep(IterDTO dto) throws Exception {
		BaseDTO bDto = new BaseDTO();
		copiaCsTBaseObject(dto, bDto);
		
		CsTbTipoDiario tipoDiario = new CsTbTipoDiario(); 
		tipoDiario.setId(new Long(DataModelCostanti.TipoDiario.CAMBIO_STATO_ID)); 
		
		CsItStep iterStep = new CsItStep();
		Date dataInserimento = dto.getDataInserimento();

		OperatoreDTO opDto = new OperatoreDTO();
		copiaCsTBaseObject(dto, opDto);
		opDto.setUsername(dto.getNomeOperatore());
		CsOOperatore operatore = operatoreSessionBean.findOperatoreByUsername(opDto);

		opDto.setIdOperatore(operatore.getId());
		opDto.setIdSettore(dto.getIdSettore());
		opDto.setDate(new Date());
		CsOOperatoreSettore currOpSett = operatoreSessionBean.findOperatoreSettoreById(opDto);
		CsOSettore settoreUtente = operatoreSessionBean.findSettoreById(opDto);
		
		CsACaso caso = dto.getCsACaso();
		
		CsDDiario diario = new CsDDiario();
		diario.setCsACaso(caso);
		diario.setCsTbTipoDiario(tipoDiario);
		diario.setCsOOperatoreSettore(currOpSett);
		diario.setUserIns(operatore.getUsername());
		diario.setDtIns(dataInserimento);
		diario.setDtAmministrativa(dataInserimento);
		
		diario = diarioDao.newDiario(diario);
		
		CsCfgItStato newStato = iterDao.findStatoById(dto.getIdStato());

		iterStep.setCsACaso(caso);
		iterStep.setCsDDiario(diario);

		iterStep.setCsOOperatore1(operatore);
		iterStep.setCsOSettore1(settoreUtente);
		iterStep.setCsOOrganizzazione1(currOpSett.getCsOSettore().getCsOOrganizzazione());

/*		String emailSegnalatoA = "";
		String emailSettoreSegnalante = "";
		if (dto.isNotificaSettoreSegnalante())
			emailSettoreSegnalante = currOpSett.getCsOSettore().getEmail();
*/
		opDto.setIdOperatoreSettore(dto.getIdOpSettoreTo());
		opDto.setIdSettore(dto.getIdSettTo());
		opDto.setIdOrganizzazione(dto.getIdOrgTo());
		
		boolean bContainsOpTo = dto.getIdOpSettoreTo() != null && dto.getIdOpSettoreTo() > 0;
		CsOOperatoreSettore opSettoreTo=null;
		if (bContainsOpTo) {
			opSettoreTo = operatoreSessionBean.findOperatoreSettoreById(opDto);
			iterStep.setCsOOperatore2(opSettoreTo.getCsOOperatore());
			//emailSegnalatoA = operSett.getCsOOperatore().getCsOOperatoreAnagrafica().getEmail();
		}

		boolean bContainsSettTo = dto.getIdSettTo() != null && dto.getIdSettTo() > 0;
		CsOSettore settoreTo = null;
		if (bContainsSettTo) {
			settoreTo = operatoreSessionBean.findSettoreById(opDto);
			iterStep.setCsOSettore2(settoreTo);
			//emailSegnalatoA = settoreTo.getEmail();
		}

		boolean bContainsOrgTo = dto.getIdOrgTo() != null && dto.getIdOrgTo() > 0;
		CsOOrganizzazione enteTo = null;
		if (bContainsOrgTo) {
			enteTo = operatoreSessionBean.findOrganizzazioneById(opDto);
			iterStep.setCsOOrganizzazione2(enteTo);
			//emailSegnalatoA = ente.getEmail();
			
		} else {
			if (bContainsSettTo && settoreTo!=null) {
				dto.setIdOrgTo(settoreTo.getCsOOrganizzazione().getId());
				iterStep.setCsOOrganizzazione2(settoreTo.getCsOOrganizzazione());
			}
		}

		iterStep.setCsCfgItStato(newStato);

		iterStep.setNota(dto.getNota());

		if (dto.getAttrNewStato() != null) {
			iterStep.setCsItStepAttrValues(new LinkedList<CsItStepAttrValue>());

			for (Long idAttr : dto.getAttrNewStato().keySet()) {

				Object val = dto.getAttrNewStato().get(idAttr);

				CsCfgAttr itStatoAttr = iterDao.findStatoAttrById(idAttr);
				CsItStepAttrValue itVal = new CsItStepAttrValue();

				itVal.setCsCfgAttr(itStatoAttr);
				itVal.setCsItStep( iterStep );

				if (val != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					if (TipoAttributo.ToEnum(itStatoAttr.getTipoAttr()).equals(TipoAttributo.Enum.LIST)) {
						for (CsCfgAttrOption itOption : itStatoAttr.getCsCfgAttrOptions()) {
							if (itOption.getId().equals(Long.parseLong(val.toString()))) {
								itVal.setValore(itOption.getValore());
								break;
							}
						}
					} else if(val instanceof Date) itVal.setValore(sdf.format(val));
					  else                         itVal.setValore(val.toString());
				}

				iterStep.getCsItStepAttrValues().add(itVal);
			}
		}

		// Inserisci nuovo IterStep
		boolean bAdded = iterDao.addIterStep(iterStep);

		if (bAdded) {
			
			if (!bContainsOpTo && !bContainsSettTo && !bContainsOrgTo) {
				logger.warn("Caso: "+caso.getId() + " - Cambio di stato " +iterStep.getCsCfgItStato().getNome()+ " senza destinatari. A chi segnalo?");
				///TODO: A chi segnalare quando non è uno stato di SEGNALAZIONE?!?
			}
			
			SegnalibriMap map = getSegnalibriMap(iterStep);
			String subject = iterStep.getCsCfgItStato().getOggettoEmail();
			String messageBody = iterStep.getCsCfgItStato().getCorpoEmail();
			subject = map.replaceTokens(subject);
			messageBody = map.replaceTokens(messageBody);
			dto.setDescrizione(messageBody);
			dto.setTitoloDescrizione(subject);
			addAlert(dto);
			
			if(!dto.getIdStato().equals(DataModelCostanti.IterStatoInfo.PRESO_IN_CARICO) && casoDao.findCasoById(caso.getId())!=null){
				caso.setDtMod(new Date());
				caso.setUsrMod(dto.getUserId());
				casoDao.updateCaso(caso);
			}
			
			// Salva responsabile in caso
			Long idResponsabile = null;
			bDto.setObj(iterStep);
			bDto.setObj2(dataInserimento);
			idResponsabile = casoSessionBean.aggiornaResponsabileCaso(bDto);
						
			//Aggiorno il responsabile del diario
			if(idResponsabile!=null)
				diario.setResponsabileCaso(idResponsabile);
		}

		return bAdded;
	}
	
	private void addAlert(IterDTO dto) throws Exception {
		
		//CsACaso caso = casoSessionBean.findCasoById(dto);
		CsACaso caso = dto.getCsACaso();
		AlertDTO newAlert = new AlertDTO();
		copiaCsTBaseObject(dto, newAlert);
		
		OperatoreDTO opDto = new OperatoreDTO();
		copiaCsTBaseObject(dto, opDto);
		opDto.setUsername(dto.getNomeOperatore());
		
		//INFO FROM
		CsOOperatore operatore = null;
		if (dto.getIdOperatore()!=null){
			opDto.setIdOperatore(dto.getIdOperatore());
			operatore = operatoreSessionBean.findOperatoreById(opDto);
		}else if(dto.getNomeOperatore()!= null) 
			operatore = operatoreSessionBean.findOperatoreByUsername(opDto);
		newAlert.setOperatoreFrom(operatore);
		
		if(dto.getIdSettore() != null) {
			opDto.setIdSettore(dto.getIdSettore());
			CsOSettore settore = operatoreSessionBean.findSettoreById(opDto);
			newAlert.setSettoreFrom(settore);
			newAlert.setOrganizzazioneFrom(settore.getCsOOrganizzazione());
		} else if (dto.getIdOrganizzazione() != null) {
			opDto.setIdOrganizzazione(dto.getIdOrganizzazione());
			CsOOrganizzazione organizzazione = operatoreSessionBean.findOrganizzazioneById(opDto);
			newAlert.setOrganizzazioneFrom(organizzazione);
		}
			
		newAlert.setCaso(caso);
		newAlert.setTipo(dto.getTipo());
		newAlert.setNotificaSettoreSegnalante(dto.isNotificaSettoreSegnalante());
	
		//INFO TO
		opDto.setIdOperatoreSettore(dto.getIdOpSettoreTo());
		opDto.setIdSettore(dto.getIdSettTo());
		opDto.setIdOrganizzazione(dto.getIdOrgTo());
		if(dto.getIdOpSettoreTo() != null && dto.getIdOpSettoreTo() > 0)
		{
			CsOOperatoreSettore opSettore2 = operatoreSessionBean.findOperatoreSettoreById(opDto);
			newAlert.setOpSettoreTo(opSettore2);
		}
		
		if(dto.getIdSettTo() != null && dto.getIdSettTo() > 0)
		{
			CsOSettore settoreTo = operatoreSessionBean.findSettoreById(opDto);
			newAlert.setSettoreTo(settoreTo);
		}
		
		if(dto.getIdOrgTo() != null && dto.getIdOrgTo() > 0)
		{
			CsOOrganizzazione ente = operatoreSessionBean.findOrganizzazioneById(opDto);
			newAlert.setOrganizzazioneTo(ente);
		}
		
		newAlert.setDescrizione(dto.getDescrizione());
		newAlert.setUrl(dto.getUrl());
		newAlert.setTitolo(dto.getTitoloDescrizione());
		newAlert.setTipo(TipiAlertCod.ITER_STEP);
		
		alertSessionBean.addAlert(newAlert);
	}
	
	@Override
	public CsCfgItStato findStatoById(IterDTO dto) throws Exception {
		return iterDao.findStatoById(dto.getIdStato());
	}

	@Override
	public List<CsCfgItTransizione> getTransizionesByStatoRuolo(IterDTO dto) throws Exception {
		return iterDao.findTransizionesByStatoRuolo(dto.getIdStato(), dto.getOpRuolo());
	}

	@Override
	public List<CsCfgItStato> getListaIterStati(CeTBaseObject cet){
		return iterDao.getListaIterStati();
	}

	@Override
	public List<StatoCartellaDTO> getStatoCasoBySoggetto(BaseDTO dto1) throws Exception {
		List<StatoCartellaDTO> lst = new ArrayList<StatoCartellaDTO>();
		
		List<CsASoggettoLAZY> soggetti = soggettiService.getSoggettiByCF(dto1);
	       if(soggetti!=null && soggetti.size()>0){
	    	   for(CsASoggettoLAZY soggetto : soggetti){
	    		   
	    		   StatoCartellaDTO  statoCartella = new StatoCartellaDTO();
	    		   statoCartella.setDenominazione(soggetto.getCsAAnagrafica().getCognome()+" "+soggetto.getCsAAnagrafica().getNome());
	    		   
	    		   BaseDTO itr = new BaseDTO();
	    		   copiaCsTBaseObject(dto1, itr);
	   			   itr.setObj(soggetto.getCsACaso().getId());
	   			   CsIterStepByCasoDTO stato = this.getLastIterStepByCasoDTO(itr);
	   			 
	   			   if(stato!=null){
	   				  statoCartella.setCodStato(stato.getCsItStep().getCsCfgItStato().getId());
	   				  statoCartella.setNomeStato(stato.getCsItStep().getCsCfgItStato().getNome()); 
	   				  statoCartella.setDataPassaggioStato(stato.getCsItStep().getCsDDiario().getDtAmministrativa());
	   				  
	   				  String organizzazione = stato.getCsItStep().getCsOOrganizzazione1().getCodCatastale()!=null ? "Comune di " : "";
	   				  organizzazione += stato.getCsItStep().getCsOOrganizzazione1().getNome();
	   				  statoCartella.setOrganizzazione(organizzazione);
	   				  
	   				  statoCartella.setSettore(stato.getCsItStep().getCsOSettore1().getNome());
	   				  
	   				   Long idResponsabile = stato.getCsItStep().getCsDDiario().getResponsabileCaso();
	   				   if(idResponsabile!=null){
		   					OperatoreDTO odto = new OperatoreDTO();
		   					odto.setEnteId(dto1.getEnteId());
		   					odto.setSessionId(dto1.getSessionId());
		   					odto.setIdOperatore(idResponsabile);
		   					try {
		   						CsOOperatore asResponsabile = operatoreSessionBean.findOperatoreById(odto);
		   						
		   						if(asResponsabile != null){
		   							AmAnagrafica responsabile = anagraficaService.findAnagraficaByUserName(asResponsabile.getUsername());
		   							statoCartella.setuNameResponsabile(responsabile.getCognome()+" "+responsabile.getNome());
		   						}
		   					} catch (Exception e) {
		   						logger.error(e);
		   					}
	   				   }
	   				   
	   				   dto1.setObj(soggetto.getAnagraficaId());
	 				   List<CsASoggettoCategoriaSocLAZY> lstCatSoc = soggettiService.getSoggettoCategorieAttualiBySoggetto(dto1);
	 				   String catSocs = "";
	 				   if(lstCatSoc!=null){
	 					   for(CsASoggettoCategoriaSocLAZY cs : lstCatSoc)
	 							   statoCartella.addAreaTarget(cs.getCsCCategoriaSociale().getDescrizione()+ (cs.getPrevalente()==1 ? "*" : ""));
	 				   }
	   			   }
	   			   lst.add(statoCartella);
	       }
	       }
	       return lst;
	 }
}
