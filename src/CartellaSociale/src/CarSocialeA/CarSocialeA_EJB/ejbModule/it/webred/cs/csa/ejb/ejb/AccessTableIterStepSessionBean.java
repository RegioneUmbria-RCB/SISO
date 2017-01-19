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
import it.webred.cs.csa.ejb.dao.DiarioDAO;
import it.webred.cs.csa.ejb.dao.IterDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.StatoCartellaDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.Segnalibri;
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
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.sociosan.ejb.client.CTConfigClientSessionBeanRemote;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.mailing.MailUtils;
import it.webred.mailing.MailUtils.MailAddressList;
import it.webred.mailing.MailUtils.MailParamBean;
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

import javax.activation.FileDataSource;
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
	
	@EJB(mappedName = "java:global/SocioSanitario/SocioSanitario_EJB/CTConfigClientSessionBean")
	protected CTConfigClientSessionBeanRemote mailConf;

	
	@EJB
	protected AccessTableSoggettoSessionBeanRemote soggettiService;

	public AccessTableIterStepSessionBean() {
	}

	
	public List<CsItStep> getIterStepsByCaso(IterDTO dto) throws Exception {
		return iterDao.getIterStepListByCaso(dto.getIdCaso());
	}
	
	@Override
	public  List<CsIterStepByCasoDTO> getIterStepsByCasoDTO(IterDTO dto) throws Exception {
		
		List<CsIterStepByCasoDTO> ret = new ArrayList<CsIterStepByCasoDTO>();
		
		
		List<CsItStepLAZY> iterSteps = iterDao.getIterStepLAZYListByCaso(dto.getIdCaso());
		
		BaseDTO bdto = new BaseDTO();
		bdto.setEnteId(dto.getEnteId());
		bdto.setUserId(dto.getUserId());
		bdto.setSessionId(dto.getSessionId());
		bdto.setObj(dto.getIdCaso());
		
		CsOOperatoreBASIC operatoreResponsabile = casoSessionBean.findResponsabileBASIC(bdto);

		
		
		for (Iterator iterator = iterSteps.iterator(); iterator.hasNext();) {
			CsItStepLAZY csItStep = (CsItStepLAZY) iterator.next();

			CsIterStepByCasoDTO lastIsByCaso = this.populateCsIterStepByCasoDTO(dto, csItStep, operatoreResponsabile);
			
			
			ret.add(lastIsByCaso);
			
			
		}
		
		
		return ret;
		
		
	}
	
	
	private CsIterStepByCasoDTO populateCsIterStepByCasoDTO(IterDTO dto, CsItStepLAZY csItStep, CsOOperatoreBASIC operatoreResponsabile)  throws Exception {

		CsIterStepByCasoDTO isByCaso =  new CsIterStepByCasoDTO(csItStep );

		if (operatoreResponsabile!=null)
			isByCaso.setResponsabileUsername(operatoreResponsabile.getUsername());
		
		// carico le liste
		if(csItStep!=null){
			List<CsItStepAttrValueBASIC> attrValues = csItStep.getCsItStepAttrValues();
			if(attrValues!=null){
				for (Iterator iterator2 = attrValues.iterator(); iterator2.hasNext();) {
					CsItStepAttrValueBASIC csItStepAttrValueBASIC = (CsItStepAttrValueBASIC) iterator2
							.next();
					csItStepAttrValueBASIC.getCsCfgAttr().getLabel();
				}
			}
		
		
			Long diarioId = isByCaso.getCsItStep().getCsDDiarioId();
			
			BaseDTO bdto = new BaseDTO();
			bdto.setEnteId(dto.getEnteId());
			bdto.setUserId(dto.getUserId());
			bdto.setSessionId(dto.getSessionId());
			bdto.setObj(diarioId);
			CsDDiarioBASIC diario = diarioSessionBean.findDiarioBASICById(bdto);
	
			Date dataAmministrativa = diario.getDtAmministrativa();
			isByCaso.setDataAmministrativa(dataAmministrativa);
			
		}else 
			logger.warn("Nessun iter step per il caso!");
		
		return isByCaso;
		
	}


	private HashMap<Long, CsOOperatoreBASIC > cacheOperatori = new HashMap<Long, CsOOperatoreBASIC>();
	private HashMap<Long, CsOOperatoreBASIC > cacheOperatori2 = new HashMap<Long, CsOOperatoreBASIC>();


	
	@Override
	public  CsIterStepByCasoDTO getLastIterStepByCasoDTO(IterDTO dto) throws Exception {
		
		List<CsIterStepByCasoDTO> ret = new ArrayList<CsIterStepByCasoDTO>();
		
		BaseDTO bdto = new BaseDTO();
		bdto.setEnteId(dto.getEnteId());
		bdto.setUserId(dto.getUserId());
		bdto.setSessionId(dto.getSessionId());
		bdto.setObj(dto.getIdCaso());
		
		CsOOperatoreBASIC operatoreResponsabile = casoSessionBean.findResponsabileBASIC(bdto);

		
			CsItStepLAZY csItStep = iterDao.getLastIterStepLAZYByCaso(dto.getIdCaso());
		
			CsIterStepByCasoDTO lastIsByCaso =  populateCsIterStepByCasoDTO(dto,csItStep,operatoreResponsabile  );
			
	/*		
			// carico le liste
			List<CsItStepAttrValueBASIC> attrValues = csItStep.getCsItStepAttrValues();
			for (Iterator iterator2 = attrValues.iterator(); iterator2
					.hasNext();) {
				CsItStepAttrValueBASIC csItStepAttrValueBASIC = (CsItStepAttrValueBASIC) iterator2
						.next();
				csItStepAttrValueBASIC.getCsCfgAttr().getLabel();
			}
			
			CsIterStepByCasoDTO lastIsByCaso =  new CsIterStepByCasoDTO(csItStep);
			
			lastIsByCaso.setResponsabileUsername(operatoreResponsabile.getUsername());
			
			Long diarioId = lastIsByCaso.getCsItStep().getCsDDiarioId();
			
			bdto = new BaseDTO();
			bdto.setEnteId(dto.getEnteId());
			bdto.setUserId(dto.getUserId());
			bdto.setObj(diarioId);
			logger.debug("***  diarioSessionBean.findDiarioBASICById(bdto);");
			CsDDiarioBASIC diario = diarioSessionBean.findDiarioBASICById(bdto);
			logger.debug("*** FINE  diarioSessionBean.findDiarioBASICById(bdto);");
			Date dataAmministrativa = diario.getDtAmministrativa();
			lastIsByCaso.setDataAmministrativa(dataAmministrativa);
			
			logger.debug("*** reupero operatori iter caso");
			CsOOperatoreBASIC operatore = null;
			if (cacheOperatori.get(lastIsByCaso.getCsItStep().getCsOOperatore1Id())==null) {
				OperatoreDTO odto = new OperatoreDTO();
				odto.setEnteId(dto.getEnteId());
				odto.setUserId(dto.getUserId());
				odto.setIdOperatore(lastIsByCaso.getCsItStep().getCsOOperatore1Id().longValue());
				operatore = operatoreSessionBean.findOperatoreBASICById(odto);
				cacheOperatori.put(lastIsByCaso.getCsItStep().getCsOOperatore1Id(), operatore);
			} else {
				operatore = cacheOperatori.get(lastIsByCaso.getCsItStep().getCsOOperatore1Id());
			}
			
			CsOOperatoreBASIC operatore2 = null;
			if (lastIsByCaso.getCsItStep().getCsOOperatore2Id()!=null) {
				if (cacheOperatori2.get(lastIsByCaso.getCsItStep().getCsOOperatore2Id())==null) {
					OperatoreDTO odto = new OperatoreDTO();
					odto.setEnteId(dto.getEnteId());
					odto.setUserId(dto.getUserId());
					odto.setIdOperatore(lastIsByCaso.getCsItStep().getCsOOperatore2Id().longValue());
					operatore2 = operatoreSessionBean.findOperatoreBASICById(odto);
					cacheOperatori.put(lastIsByCaso.getCsItStep().getCsOOperatore2Id(), operatore2);
				} else {
					operatore2 = cacheOperatori.get(lastIsByCaso.getCsItStep().getCsOOperatore2Id());
				}
			}
			
			logger.debug("*** FINE reupero operatori iter caso");
		*/
			
		return lastIsByCaso;
	}
	

	@Override
	public CsItStep getLastIterStepByCaso(IterDTO dto) throws Exception {
		return iterDao.getLastIterStepByCaso(dto.getIdCaso());
	}

	private SegnalibriMap getSegnalibriMap(CsItStep iterStep) {
		String soggetto_Cf = iterStep.getCsACaso().getCsASoggetto()
				.getCsAAnagrafica().getCf();
		String soggetto_Nome = iterStep.getCsACaso().getCsASoggetto()
				.getCsAAnagrafica().getNome();
		String soggetto_Cognome = iterStep.getCsACaso().getCsASoggetto()
				.getCsAAnagrafica().getCognome();

		SegnalibriMap map = new SegnalibriMap();

		map.put(Segnalibri.CASO_DATA_CREAZIONE, DateTimeUtils.dateToString(
				iterStep.getCsACaso().getDtIns(), "dd/MM/yy"));
		map.put(Segnalibri.CASO_DATA_MODIFICA, DateTimeUtils.dateToString(
				iterStep.getCsACaso().getDtMod(), "dd/MM/yy"));
		map.put(Segnalibri.CASO_ID, iterStep.getCsACaso().getId().toString());
		map.put(Segnalibri.CASO_NOME, soggetto_Nome + "  " + soggetto_Cognome
				+ "  (" + soggetto_Cf + ")");
		map.put(Segnalibri.CASO_USERNAME_UTENTE_CREAZIONE, iterStep
				.getCsACaso().getUserIns());
		map.put(Segnalibri.CASO_USERNAME_UTENTE_MODIFICA, iterStep.getCsACaso()
				.getUsrMod());
		map.put(Segnalibri.ITERSTEP_DATA_CREAZIONE, DateTimeUtils.dateToString(
				iterStep.getCsDDiario().getDtIns(), "dd/MM/yy"));

		map.put(Segnalibri.ITERSTEP_USERNAME_SEGNALANTE, iterStep
				.getCsOOperatore1().getUsername());
		AmAnagrafica operatoreAna1 = anagraficaService
				.findAnagraficaByUserName(iterStep.getCsOOperatore1().getUsername());
		map.put(Segnalibri.ITERSTEP_NOME_SEGNALANTE, operatoreAna1.getNome());
		map.put(Segnalibri.ITERSTEP_COGNOME_SEGNALANTE,
				operatoreAna1.getCognome());

		if (iterStep.getCsOOperatore2() != null) {
			AmAnagrafica operatoreAna2 = anagraficaService
					.findAnagraficaByUserName(iterStep
							.getCsOOperatore2().getUsername());
			map.put(Segnalibri.ITERSTEP_USERNAME_SEGNALATO, iterStep
					.getCsOOperatore2().getUsername());
			map.put(Segnalibri.ITERSTEP_NOME_SEGNALATO, operatoreAna2.getNome());
			map.put(Segnalibri.ITERSTEP_COGNOME_SEGNALATO,
					operatoreAna2.getCognome());
		}
		String sItStepAttrValues = "";
		if (iterStep.getCsItStepAttrValues() != null
				&& iterStep.getCsItStepAttrValues().size() > 0) {
			sItStepAttrValues = "<strong>"
					+ iterStep.getCsCfgItStato().getSezioneAttributiLabel()
					+ "</strong>" + "<br/>";
			List<String> listValue = new LinkedList<String>();
			for (CsItStepAttrValue itVal : iterStep.getCsItStepAttrValues())
				listValue.add("<strong>" + itVal.getCsCfgAttr().getLabel()
						+ "</strong>" + " : " + itVal.getValore());

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
		bDto.setUserId(dto.getUserId());
		bDto.setEnteId(dto.getEnteId());
		bDto.setSessionId(dto.getSessionId());
		
		CsTbTipoDiario tipoDiario = new CsTbTipoDiario(); 
		tipoDiario.setId(new Long(DataModelCostanti.TipoDiario.CAMBIO_STATO_ID)); 
		
		CsItStep iterStep = new CsItStep();
		Date dataInserimento = dto.getDataInserimento();

		OperatoreDTO opDto = new OperatoreDTO();
		opDto.setUserId(dto.getUserId());
		opDto.setEnteId(dto.getEnteId());
		opDto.setUsername(dto.getNomeOperatore());
		opDto.setSessionId(dto.getSessionId());

		CsOOperatore operatore = operatoreSessionBean.findOperatoreByUsername(opDto);

		opDto.setIdOperatore(operatore.getId());
		opDto.setIdSettore(dto.getIdSettore());
		opDto.setDate(new Date());
		CsOOperatoreSettore currOpSett = operatoreSessionBean.findOperatoreSettoreById(opDto);
		CsOSettore settoreUtente = operatoreSessionBean.findSettoreById(opDto);
		
		CsACaso caso = casoSessionBean.findCasoById(dto);

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

		String emailSegnalatoA = "";
		String emailSettoreSegnalante = "";
		if (dto.isNotificaSettoreSegnalante())
			emailSettoreSegnalante = currOpSett.getCsOSettore().getEmail();

		opDto.setIdOperatore(dto.getIdOpTo());
		opDto.setIdSettore(dto.getIdSettTo());
		opDto.setIdOrganizzazione(dto.getIdOrgTo());
		
		boolean bContainsOpTo = dto.getIdOpTo() != null && dto.getIdOpTo() > 0;
		
		if (bContainsOpTo) {
			CsOOperatoreSettore operSett = operatoreSessionBean
					.findOperatoreSettoreById(opDto);
			iterStep.setCsOOperatore2(operSett.getCsOOperatore());
			AmUser amUser = userService.getUserByName(operSett.getCsOOperatore().getUsername());
			emailSegnalatoA = amUser.getEmail();
		}

		boolean bContainsSettTo = dto.getIdSettTo() != null && dto.getIdSettTo() > 0;
		
		if (bContainsSettTo) {
			CsOSettore settore = operatoreSessionBean.findSettoreById(opDto);
			iterStep.setCsOSettore2(settore);
			emailSegnalatoA = settore.getEmail();
		}

		boolean bContainsOrgTo = dto.getIdOrgTo() != null && dto.getIdOrgTo() > 0;
		
		if (bContainsOrgTo) {
			CsOOrganizzazione ente = operatoreSessionBean.findOrganizzazioneById(opDto);
			iterStep.setCsOOrganizzazione2(ente);
			emailSegnalatoA = ente.getEmail();
		} else {
			if (bContainsSettTo) {
				CsOSettore settore = operatoreSessionBean.findSettoreById(opDto);
				
				dto.setIdOrgTo(settore.getCsOOrganizzazione().getId());
				
				iterStep.setCsOOrganizzazione2(settore.getCsOOrganizzazione());
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
					if (DataModelCostanti.TipoAttributo.ToEnum(
							itStatoAttr.getTipoAttr()).equals(
							DataModelCostanti.TipoAttributo.Enum.LIST)) {
						for (CsCfgAttrOption itOption : itStatoAttr
								.getCsCfgAttrOptions()) {
							if (itOption.getId().equals(
									Long.parseLong(val.toString()))) {
								itVal.setValore(itOption.getValore());
								break;
							}
						}
					} else if(val instanceof Date) {
						itVal.setValore(sdf.format(val));
					} else
						itVal.setValore(val.toString());
				}

				iterStep.getCsItStepAttrValues().add(itVal);
			}
		}

		// Inserisci nuovo IterStep
		boolean bAdded = iterDao.addIterStep(iterStep);

		if (bAdded) {
			SegnalibriMap map = getSegnalibriMap(iterStep);

			if (bContainsOpTo || bContainsSettTo || bContainsOrgTo) {
				String descrizione = "Il caso " + map.get(":CASO_NOME")	+ " ha cambiato stato in " + newStato.getNome();
				String titoloDescrizione = "Il caso " + map.get(":CASO_NOME") + " ha cambiato stato";

				dto.setDescrizione(descrizione);
				dto.setTitoloDescrizione(titoloDescrizione);
				dto.setTipo("IT");
				dto.setLabelTipo("Iter Step");
				
				alertSessionBean.addAlert(dto);
			}
			

			if(!iterStep.getCsCfgItStato().getId().equals(DataModelCostanti.IterStatoInfo.PRESO_IN_CARICO))
				casoSessionBean.setDataModifica(dto);
			
			// Salva responsabile in caso
			Long idResponsabile = null;
			bDto.setObj(iterStep);
			bDto.setObj2(dataInserimento);
			idResponsabile = casoSessionBean.aggiornaResponsabileCaso(bDto);
						
			//Aggiorno il responsabile del diario
			if(idResponsabile!=null)
				diario.setResponsabileCaso(idResponsabile);
			
			// Now try to send email
			MailAddressList addressTO = new MailAddressList(emailSegnalatoA);
			MailAddressList addressCC = new MailAddressList();
			MailAddressList addressBCC = new MailAddressList(emailSettoreSegnalante);

			// Segnalibri
			String subject = iterStep.getCsCfgItStato().getOggettoEmail();
			String messageBody = iterStep.getCsCfgItStato().getCorpoEmail();

			subject = map.replaceTokens(subject);
			messageBody = map.replaceTokens(messageBody);

			MailParamBean params = mailConf.getSISOMailPatametres();
			
			
			MailUtils.sendEmail(params, addressTO, addressCC, addressBCC, subject,
					messageBody, (FileDataSource[]) null);

		}

		return bAdded;
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
	    		   
	    		   IterDTO itr = new IterDTO();
	   			   itr.setEnteId(dto1.getEnteId());
	   			   itr.setSessionId(dto1.getSessionId());
	   			   itr.setIdCaso(soggetto.getCsACaso().getId());
	   			   CsIterStepByCasoDTO stato = this.getLastIterStepByCasoDTO(itr);
	   			 
	   			   if(stato!=null){
	   				  statoCartella.setNomeStato(stato.getCsItStep().getCsCfgItStato().getNome()); 
	   				  statoCartella.setDataPassaggioStato(stato.getCsItStep().getCsDDiario().getDtAmministrativa());
	   				  
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
