package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CasoDAO;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.DiarioDAO;
import it.webred.cs.csa.ejb.dao.IterDAO;
import it.webred.cs.csa.ejb.dao.OperatoreDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DatiOperatoreDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OrganizzazioneDTO;
import it.webred.cs.csa.ejb.dto.PresaInCaricoDTO;
import it.webred.cs.csa.ejb.dto.StatoCartellaDTO;
import it.webred.cs.csa.ejb.dto.alert.AlertDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.Segnalibri;
import it.webred.cs.data.DataModelCostanti.TipiAlertCod;
import it.webred.cs.data.DataModelCostanti.TipoAttributo;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCfgAttr;
import it.webred.cs.data.model.CsCfgAttrOption;
import it.webred.cs.data.model.CsCfgItStato;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDiarioBASIC;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsItStepAttrValue;
import it.webred.cs.data.model.CsItStepLAZY;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;
import it.webred.utilities.CommonUtils;
import it.webred.utilities.DateTimeUtils;
import it.webred.utilities.SegnalibriMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableIterStepSessionBean extends CarSocialeBaseSessionBean implements AccessTableIterStepSessionBeanRemote {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IterDAO iterDao;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDao;
	
	@Autowired
	private CasoDAO casoDao;
	
	@Autowired
	private DiarioDAO diarioDao;
	
	@Autowired
	private OperatoreDAO operatoreDao;
	
	@Autowired
	private SoggettoDAO soggettiDao;

	@EJB
	public AccessTableAlertSessionBeanRemote alertSessionBean;

	public AccessTableIterStepSessionBean() {
	}
	
	@Override
	public  List<CsIterStepByCasoDTO> getIterStepsByCasoDTO(BaseDTO dto) throws Exception {
		
		List<CsIterStepByCasoDTO> ret = new ArrayList<CsIterStepByCasoDTO>();
		Long idCaso = (Long)dto.getObj();
		
		CsOOperatoreBASIC operatoreResponsabile = casoDao.findResponsabileBASIC(idCaso);

		List<CsItStepLAZY> iterSteps = iterDao.getIterStepLAZYListByCaso(idCaso);	
		for (Iterator iterator = iterSteps.iterator(); iterator.hasNext();) {
			CsItStepLAZY csItStep = (CsItStepLAZY) iterator.next();

			CsDDiarioBASIC diario = csItStep.getCsDDiario();
			if(diario==null){
				Long diarioId = csItStep.getCsDDiarioId();
				diario = diarioDao.findDiarioBASICById(diarioId);
				csItStep.setCsDDiario(diario);
			}
			
			CsIterStepByCasoDTO lastIsByCaso = new CsIterStepByCasoDTO(csItStep, operatoreResponsabile);
			ret.add(lastIsByCaso);
		}
		
		return ret;
		
		
	}
	
	@Override
 	public  CsIterStepByCasoDTO getLastIterStepByCasoDTO(BaseDTO dto) {
		Long idCaso = (Long)dto.getObj();
		logger.debug("INIT getLastIterStepByCasoDTO casoId["+idCaso+"]");
		try{
			CsOOperatoreBASIC operatoreResponsabile = casoDao.findResponsabileBASIC(idCaso);
			CsItStepLAZY csItStep = iterDao.getLastIterStepLAZYByCaso(idCaso);
			CsIterStepByCasoDTO lastIsByCaso =  new CsIterStepByCasoDTO(csItStep,operatoreResponsabile);
			logger.debug("END getLastIterStepByCasoDTO casoId["+idCaso+"]");	
			return lastIsByCaso;
		}catch(Exception e){
			logger.error("Errore recupero getLastIterStepByCasoDTO casoId["+idCaso+"]"+e.getMessage(), e);
		}
		return null;
	}
	
    //SISO-1297
	@Override
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public OrganizzazioneDTO getEnteLastIterStepByCaso(BaseDTO dto) throws Exception {
		
		OrganizzazioneDTO segnalante = null;
		
		Long idCaso = (Long)dto.getObj();
		
		CsItStepLAZY csItStep = iterDao.getLastIterStepLAZYByCaso(idCaso);
		if(csItStep!=null)
			segnalante = new OrganizzazioneDTO(csItStep.getCsOOrganizzazione1());
		return segnalante;
	}
	
	@Override
	public CsItStep getLastIterStepByCaso(BaseDTO dto) {
		return iterDao.getLastIterStepByCaso((Long)dto.getObj());
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

		CsOOperatore operatore = operatoreDao.findOperatoreByUsername(dto.getNomeOperatore());
		CsOOperatoreSettore currOpSett = operatoreDao.findOperatoreSettoreById(operatore.getId(), dto.getIdSettore(), new Date());
		CsOSettore settoreUtente = operatoreDao.findSettoreById(dto.getIdSettore());
		
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
		
		boolean bContainsOpTo = dto.getIdOpSettoreTo() != null && dto.getIdOpSettoreTo() > 0;
		CsOOperatoreSettore opSettoreTo=null;
		if (bContainsOpTo) {
			opSettoreTo = operatoreDao.findOperatoreSettoreById(dto.getIdOpSettoreTo());
			iterStep.setCsOOperatore2(opSettoreTo.getCsOOperatore());
			//emailSegnalatoA = operSett.getCsOOperatore().getCsOOperatoreAnagrafica().getEmail();
		}

		boolean bContainsSettTo = dto.getIdSettTo() != null && dto.getIdSettTo() > 0;
		CsOSettore settoreTo = null;
		if (bContainsSettTo) {
			settoreTo = operatoreDao.findSettoreById(dto.getIdSettTo());
			iterStep.setCsOSettore2(settoreTo);
			//emailSegnalatoA = settoreTo.getEmail();
		}

		boolean bContainsOrgTo = dto.getIdOrgTo() != null && dto.getIdOrgTo() > 0;
		CsOOrganizzazione enteTo = null;
		if (bContainsOrgTo) {
			enteTo = operatoreDao.getOrganizzazioneById(dto.getIdOrgTo());
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

				CsCfgAttr itStatoAttr = configurazioneDao.findStatoAttrById(idAttr);
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
			idResponsabile = aggiornaResponsabileCaso(bDto);
						
			//Aggiorno il responsabile del diario
			if(idResponsabile!=null)
				diario.setResponsabileCaso(idResponsabile);
		}

		return bAdded;
	}
	
	
	private Long aggiornaResponsabileCaso(BaseDTO dto) {
		Long idResponsabile = null;
		BaseDTO bDto = new BaseDTO();
		copiaCsTBaseObject(dto, bDto);
		
		CsItStep iterStep = (CsItStep) dto.getObj();
		Date dataInserimento = (Date) dto.getObj2();
		
		CsACasoOpeTipoOpe responsabileCorrente = casoDao.findCasoOpeResponsabile(iterStep.getCsACaso().getId());
		
		if(responsabileCorrente!=null)
			idResponsabile = responsabileCorrente.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore().getId();
		
		if (iterStep.getCsCfgItStato().getId().equals(DataModelCostanti.IterStatoInfo.PRESO_IN_CARICO)){
			if(responsabileCorrente != null){
				   //Confronto il responsabile precedente, con quello assegnato dall'ITSTEP: se non corrispondono storicizzo il vecchio
				   CsOOperatoreSettore opSettoreCorrente = responsabileCorrente.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore();
				   boolean stessoResponsabile = opSettoreCorrente.getCsOOperatore().getId().equals(iterStep.getCsOOperatore1().getId()) 
							                 && opSettoreCorrente.getCsOSettore().getId().equals(iterStep.getCsOSettore1().getId());
					if(!stessoResponsabile) {
					
						//storicizzo il responsabile corrente
						responsabileCorrente.setFlagResponsabile(false);
						responsabileCorrente.setDataFineApp(dataInserimento); //Inserito per far scadere il vecchio responsabile SISO-246
					
						bDto.setObj(responsabileCorrente);
						this.updateOperatoreCaso(bDto);
					}
				}
				
				//Ora procedo ad attivare il nuovo responsabile: se presente ed attivo, si AGGIORNA il FLAG - se assente o inattivo, si inserisce
				
				bDto.setObj(iterStep.getCsACaso().getId());
				List<CsACasoOpeTipoOpe> listaOperatoriCaso = casoDao.getListaOperatoreTipoOpByCasoId(iterStep.getCsACaso().getId());
				//se già esiste ed è attivo setto il flag responsabile
				boolean exist = false;
				int i = 0;
				while(!exist && i<listaOperatoriCaso.size()){
					CsACasoOpeTipoOpe casoOpTipoOp = listaOperatoriCaso.get(i);
					CsOOperatoreSettore opSettore = casoOpTipoOp.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore();
					if(opSettore.getCsOOperatore().getId().equals(iterStep.getCsOOperatore1().getId()) 
							&& opSettore.getCsOSettore().getId().equals(iterStep.getCsOSettore1().getId())
							&& casoOpTipoOp.getDataFineApp().after(dataInserimento)) {
						exist = true;
						
						casoOpTipoOp.setFlagResponsabile(true);
						casoOpTipoOp.setFlagGestioneFascicolo(null);
						casoOpTipoOp.setDataFineApp(DataModelCostanti.END_DATE);
						
						bDto.setObj(casoOpTipoOp);
						this.updateOperatoreCaso(bDto);
						idResponsabile = casoOpTipoOp.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore().getId();
					}
					
					i++;
				}
				
				if(!exist) {
					//creo nuovo responsabile
					Long operatoreId = iterStep.getCsOOperatore1().getId();
					Long settoreId = iterStep.getCsOSettore1().getId();
					CsOOperatoreTipoOperatore opTipoOp = casoDao.findOperatoreTipoOperatoreByOpSettore(operatoreId , settoreId );
					if(opTipoOp != null) {
						
						CsACasoOpeTipoOpe newResponsabile = new CsACasoOpeTipoOpe();
						
						newResponsabile.setCasoId(iterStep.getCsACaso().getId());
						newResponsabile.setDataFineApp(DataModelCostanti.END_DATE);
						newResponsabile.setOperatoreTipoOperatoreId(opTipoOp.getId());
						newResponsabile.setDataInizioApp(dataInserimento);
						newResponsabile.setDataInizioSys(dataInserimento);
						newResponsabile.setFlagResponsabile(true);
						bDto.setObj(newResponsabile);
						this.salvaOperatoreCaso(bDto);
						idResponsabile = opTipoOp.getCsOOperatoreSettore().getCsOOperatore().getId();
					}else
					  logger.warn("Impossibile inserire responsabile per il caso: nessun CsOOperatoreTipoOperatore definito per settore-"+iterStep.getCsOSettore1().getId()+" e operatore-"+iterStep.getCsOSettore1().getId());
						
				}
		}
		return idResponsabile;
		
	}
	
	@Override
	public void salvaOperatoreCaso(BaseDTO dto) {
		CsACasoOpeTipoOpe op = (CsACasoOpeTipoOpe)dto.getObj();
		op.setDtIns(new Date());
		op.setUserIns(dto.getUserId());
		casoDao.salvaOperatoreTipoOpCaso((CsACasoOpeTipoOpe)dto.getObj());
	}
	
	@Override
	public void updateOperatoreCaso(BaseDTO dto) {
		CsACasoOpeTipoOpe op = (CsACasoOpeTipoOpe)dto.getObj();
		op.setDtMod(new Date());
		op.setUsrMod(dto.getUserId());
		casoDao.updateOperatoreTipoOpCaso(op);
	}

	
	private void addAlert(IterDTO dto) throws Exception {
		
		CsACaso caso = dto.getCsACaso();
		AlertDTO newAlert = new AlertDTO();
		copiaCsTBaseObject(dto, newAlert);
		
		
		//INFO FROM
		CsOOperatore operatore = null;
		if (dto.getIdOperatore()!=null){
			operatore = operatoreDao.findOperatoreById(dto.getIdOperatore());
		}else if(dto.getNomeOperatore()!= null) 
			operatore = operatoreDao.findOperatoreByUsername(dto.getNomeOperatore());
		newAlert.setOperatoreFrom(operatore);
		
		if(dto.getIdSettore() != null) {
			CsOSettore settore = operatoreDao.findSettoreById(dto.getIdSettore());
			newAlert.setSettoreFrom(settore);
			newAlert.setOrganizzazioneFrom(settore.getCsOOrganizzazione());
		} else if (dto.getIdOrganizzazione() != null) {
			CsOOrganizzazione organizzazione = operatoreDao.getOrganizzazioneById(dto.getIdOrganizzazione());
			newAlert.setOrganizzazioneFrom(organizzazione);
		}
			
		newAlert.setCaso(caso);
		newAlert.setTipo(dto.getTipo());
		newAlert.setNotificaSettoreSegnalante(dto.isNotificaSettoreSegnalante());
	
		//INFO TO
		if(dto.getIdOpSettoreTo() != null && dto.getIdOpSettoreTo() > 0)
		{
			CsOOperatoreSettore opSettore2 = operatoreDao.findOperatoreSettoreById(dto.getIdOpSettoreTo());
			newAlert.setOpSettoreTo(opSettore2);
		}
		
		if(dto.getIdSettTo() != null && dto.getIdSettTo() > 0)
		{
			CsOSettore settoreTo = operatoreDao.findSettoreById(dto.getIdSettTo());
			newAlert.setSettoreTo(settoreTo);
		}
		
		if(dto.getIdOrgTo() != null && dto.getIdOrgTo() > 0)
		{
			CsOOrganizzazione ente = operatoreDao.getOrganizzazioneById(dto.getIdOrgTo());
			newAlert.setOrganizzazioneTo(ente);
		}
		
		newAlert.setDescrizione(dto.getDescrizione());
		newAlert.setUrl(dto.getUrl());
		newAlert.setTitolo(dto.getTitoloDescrizione());
		newAlert.setTipo(TipiAlertCod.ITER_STEP);
		
		alertSessionBean.addAlert(newAlert);
	}
	
	@Override
	public PresaInCaricoDTO getLastPICByCaso(BaseDTO dto){
		PresaInCaricoDTO pic = new PresaInCaricoDTO();
		
		Long idCaso = (Long)dto.getObj();
		logger.debug("INIT getLastPICByCaso casoId["+idCaso+"]");
		CsOOperatoreBASIC op = casoDao.findResponsabileBASIC(idCaso);
		CsItStepLAZY csItStep = iterDao.getLastIterStepLAZYByTipoCaso(idCaso, DataModelCostanti.IterStatoInfo.PRESO_IN_CARICO, false);
		
		if (op!=null){
			DatiOperatoreDTO responsabile = new DatiOperatoreDTO();
			responsabile.setId(op.getId());
			responsabile.setDenominazione(op.getDenominazione());
			responsabile.setUsername(op.getUsername());
			pic.setResponsabile(responsabile);
		}
			
		if(csItStep!=null){
			CsDDiarioBASIC diario = csItStep.getCsDDiario();
			if(diario==null){
				Long diarioId = csItStep.getCsDDiarioId();
				diario = diarioDao.findDiarioBASICById(diarioId);
			}
			Date dataAmministrativa = diario.getDtAmministrativa();
			pic.setDataAmministrativa(dataAmministrativa);
			
			CsOSettore settore = diario.getCsOOperatoreSettore().getCsOSettore();
			KeyValueDTO settoreR = new KeyValueDTO(settore.getId(), settore.getDescrizione());
			pic.setSettore(settoreR);
			
			CsOOrganizzazione organizzazione = settore.getCsOOrganizzazione();
			KeyValueDTO organizzazioneR = new KeyValueDTO(organizzazione.getId(), organizzazione.getNome());
			
			pic.setOrganizzazione(organizzazioneR);
		}
		return pic;
	}
	
	@Override
	public List<StatoCartellaDTO> getStatoCasoBySoggetto(BaseDTO dto1) throws Exception {
		List<StatoCartellaDTO> lst = new ArrayList<StatoCartellaDTO>();
		String cf = (String) dto1.getObj();
		logger.debug("INIT getStatoCasoBySoggetto cf["+cf+"]");
		List<CsASoggettoLAZY> soggetti = soggettiDao.getSoggettiByCF(cf);
	       if(soggetti!=null && soggetti.size()>0){
	    	   for(CsASoggettoLAZY soggetto : soggetti){
	    		   
	    		   StatoCartellaDTO  statoCartella = new StatoCartellaDTO();
	    		   statoCartella.setDenominazione(soggetto.getCsAAnagrafica().getDenominazione());
	    		   
	    		   BaseDTO itr = new BaseDTO();
	    		   copiaCsTBaseObject(dto1, itr);
	   			   itr.setObj(soggetto.getCsACaso().getId());
	   			   CsIterStepByCasoDTO stato = this.getLastIterStepByCasoDTO(itr);
	   			 
	   			   if(stato!=null){
	   				  statoCartella.setCodStato(stato.getIdStatoIter());
	   				  statoCartella.setNomeStato(stato.getNomeStatoIter()); 
	   				  statoCartella.setDataPassaggioStato(stato.getDataAmministrativa());
	   				  
	   				  String organizzazione = stato.getEnteSegnalante().isEnteComunale() ? "Comune di " : "";
	   				  organizzazione += stato.getEnteSegnalante().getNome();
	   				  statoCartella.setOrganizzazione(organizzazione);
	   				  
	   				  statoCartella.setSettore(stato.getSettoreSegnalante().getNome());
	   				  statoCartella.setuNameResponsabile(stato.getResposabileDenominazione());
	   				   
	 				   List<CsASoggettoCategoriaSoc> lstCatSoc = soggettiDao.getSoggCategorieAttualiBySoggetto(soggetto.getAnagraficaId());
	 				   if(lstCatSoc!=null){
	 					   for(CsASoggettoCategoriaSoc cs : lstCatSoc)
	 							   statoCartella.addAreaTarget(cs.getCsCCategoriaSociale().getDescrizione()+ (cs.getPrevalente()==1 ? "*" : ""));
	 				   }
	   			   }
	   			   lst.add(statoCartella);
	       }
	       }
	       logger.debug("END getStatoCasoBySoggetto cf["+cf+"]["+lst.size()+"]");
	       return lst;
	 }
}
