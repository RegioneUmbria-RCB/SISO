package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CasoDAO;
import it.webred.cs.csa.ejb.dao.IterDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoAccessoFascicolo;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsACasoOpeTipoOpe2;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.view.VSsSchedeUdcDiff;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;


@Stateless
public class AccessTableCasoSessionBean extends CarSocialeBaseSessionBean implements AccessTableCasoSessionBeanRemote  {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private CasoDAO casoDao;
	
	@Autowired
	private SoggettoDAO soggettoDao;
	
	@Autowired 
	private IterDAO iterDao;
	
	@EJB
	public AccessTableOperatoreSessionBeanRemote operatoreSessionBean;
	
	@Override
	public CsACaso findCasoById(BaseDTO dto) {	
		if(dto.getObj()!=null){
			CsACaso caso = casoDao.findCasoById((Long)dto.getObj());
			
			CsASoggettoLAZY soggetto = soggettoDao.getSoggettoByCaso(caso);
			caso.setCsASoggetto(soggetto);
			return caso;
		}
		return null;
	}

	
	@Override
	public CsACaso inizializzaNuovoCaso(CeTBaseObject dto) {
		
		CsACaso newCaso = new CsACaso();
		
		newCaso.setUserIns(dto.getUserId());
		newCaso.setDtIns(new Date());
		newCaso.setUsrMod(null);
		newCaso.setDtMod(null);
		
		casoDao.setIdentificativoCaso(newCaso);
		
		return newCaso;
	}
	
	@Override
	public void updateCaso(BaseDTO dto) {
		casoDao.updateCaso((CsACaso) dto.getObj());
	}

	@Override
	public List<CsACaso> findAll(CeTBaseObject dto) {
		return casoDao.findAll();
	}

	@Override
	public void salvaOperatoreCaso(BaseDTO dto) {
		CsACasoOpeTipoOpe op = (CsACasoOpeTipoOpe)dto.getObj();
		op.setDtIns(new Date());
		op.setUserIns(dto.getUserId());
		casoDao.salvaOperatoreTipoOpCaso((CsACasoOpeTipoOpe)dto.getObj());
	}
	
	//SISO-812
	@Override
	public void salvaAccessoFascicoloCaso(BaseDTO dto) {
		CsACasoAccessoFascicolo sl = (CsACasoAccessoFascicolo)dto.getObj();
		sl.setDtIns(new Date());
		sl.setUserIns(dto.getUserId());
		casoDao.salvaAccessoFascicolo((CsACasoAccessoFascicolo)dto.getObj());
		
	}
	
	@Override
	public void updateOperatoreCaso(BaseDTO dto) {
		CsACasoOpeTipoOpe op = (CsACasoOpeTipoOpe)dto.getObj();
		op.setDtMod(new Date());
		op.setUsrMod(dto.getUserId());
		casoDao.updateOperatoreTipoOpCaso(op);
	}

	@Override
	public void eliminaOperatoreTipoOpByCasoId(BaseDTO dto) {
		casoDao.eliminaOperatoreTipoOpByCasoId((Long)dto.getObj());
		
	}
	
	@Override
	public void eliminaOperatoreTipoOpById(BaseDTO dto) {
		casoDao.eliminaOperatoreTipoOpById((Long) dto.getObj());
		
	}
	
	//nuovo operatore tipo 2
	@Override
	public void salvaOperatoreCaso2(BaseDTO dto) {
		CsACasoOpeTipoOpe2 op = (CsACasoOpeTipoOpe2)dto.getObj();
		if(op.getId()!=null){
			op.setDtMod(new Date());
			op.setUsrMod(dto.getUserId());
		}else{
			op.setDtIns(new Date());
			op.setUserIns(dto.getUserId());
		}
		casoDao.salvaOperatoreTipoOp2Caso(op);
	}
	
	@Override
	public void eliminaOperatoreTipoOp2ByCasoId(BaseDTO dto) {
		casoDao.eliminaOperatoreTipoOp2ByCasoId((Long)dto.getObj());
		
	}
	//SISO-812
	@Override
	public void eliminaAccessoFascicoloByCasoId(BaseDTO dto) {
		casoDao.eliminaAccessoFascicoloByCasoId((Long)dto.getObj());
		}
	@Override
	public List<CsACasoOpeTipoOpe> getListaOperatoreTipoOpByCasoId(BaseDTO dto) {
		return casoDao.getListaOperatoreTipoOpByCasoId(dto.getObj());
	}
	
	@Override
	public List<CsACasoOpeTipoOpe2> getListaOperatoreTipoOp2ByCasoId(BaseDTO dto) {
		return casoDao.getListaOperatoreTipoOp2ByCasoId(dto.getObj());
	}
	
	//SISO-812
	@Override
	public List<CsACasoAccessoFascicolo> getListaAccessoFascicoloByCasoId(BaseDTO dto) {
		return casoDao.getListaAccessoFascicoloByCasoId(dto.getObj());
	}
	
	//SISO-812
	public boolean getFlagNascondiInformazioniAttualeByCasoSettoreOrganizzazione(BaseDTO dto){
		boolean nascondi=false;
		List<CsACasoAccessoFascicolo> lst = casoDao.findAccessoFascicoloAttuali((Long) dto.getObj(), (Long) dto.getObj2(), (Long)dto.getObj3());
		int i = 0;
		while(!nascondi && i<lst.size()) {
			CsACasoAccessoFascicolo c = lst.get(i);
			nascondi = c.getFlagNascondiInformazioni()!=null && c.getFlagNascondiInformazioni();
			i++;
		}
		return nascondi;
	}
	
    //SISO-812
	@Override
	public List<CsACasoAccessoFascicolo> findAccessoFascicoloByIdOrganizzazioneAndIdSettore(BaseDTO dto) {
		return casoDao.findAccessoFascicoloAttuali((Long)dto.getObj(), (Long)dto.getObj2());
	}

	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public CsACasoOpeTipoOpe findCasoOpeResponsabile(BaseDTO dto) {
		return casoDao.findCasoOpeResponsabile((Long) dto.getObj());
	}
	
/*	@Override
	public CsOOperatore findResponsabile(BaseDTO dto) {
		return casoDao.findResponsabile((Long) dto.getObj());
	}
	*/
	
	@Override
	public CsOOperatoreBASIC findResponsabileBASIC(BaseDTO dto) {
		return casoDao.findResponsabileBASIC((Long) dto.getObj());
	}
	
	
	@Override
	public CsOOperatoreTipoOperatore findOperatoreTipoOperatoreByOpSettore(BaseDTO dto) {
		return casoDao.findOperatoreTipoOperatoreByOpSettore((Long) dto.getObj(), (Long) dto.getObj2());
	}
	
	@Override
	public Boolean getFlagGestioneFascicolo(BaseDTO dto){
		List<CsACasoOpeTipoOpe> lst = casoDao.findCasoOpeTipoOpeByCasoOpSettore((Long) dto.getObj(), (Long) dto.getObj2());
		for(CsACasoOpeTipoOpe cope : lst) {
			if(cope.getDataFineApp().after(new Date())) return cope.getFlagGestioneFascicolo();
		}
		return null;
	}
	
	@Override
	public Integer countCasiByResponsabileCatSociale(BaseDTO dto) {
		return casoDao.countCasiByResponsabileCatSociale((Long) dto.getObj(), (Long) dto.getObj2(), (Boolean)dto.getObj3(), dto.getEnteId());
	}

	@Override
	public Long aggiornaResponsabileCaso(BaseDTO dto) {
		Long idResponsabile = null;
		BaseDTO bDto = new BaseDTO();
		copiaCsTBaseObject(dto, bDto);
		
		CsItStep iterStep = (CsItStep) dto.getObj();
		Date dataInserimento = (Date) dto.getObj2();
		
		bDto.setObj(iterStep.getCsACaso().getId());
		CsACasoOpeTipoOpe responsabileCorrente = this.findCasoOpeResponsabile(bDto);
		
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
				List<CsACasoOpeTipoOpe> listaOperatoriCaso = this.getListaOperatoreTipoOpByCasoId(bDto);
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
					bDto.setObj(iterStep.getCsOOperatore1().getId());
					bDto.setObj2(iterStep.getCsOSettore1().getId());
					CsOOperatoreTipoOperatore opTipoOp = this.findOperatoreTipoOperatoreByOpSettore(bDto);
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
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public CsOOperatoreSettore findCreatoreCaso(BaseDTO dto) {
		Long casoId = (Long)dto.getObj();
		
		CsItStep itStep = iterDao.getFirstIterStepByCaso(casoId);
		if(itStep!=null)
			return itStep.getCsDDiario().getCsOOperatoreSettore();
		return null;
	}


	@Override
	public List<CsACaso> findCasoByCognomeAndNome(BaseDTO dto) {
		return casoDao.findCasoByCognomeAndNome((String) dto.getObj(), (String)dto.getObj2());
	}


	@Override
	public Integer countDatiStorici(BaseDTO dto) {
		return casoDao.countDatiStorici((Integer)dto.getObj(), (Long)dto.getObj2());
	}

	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public List<VSsSchedeUdcDiff> controllaModificheSchedaCompletaUDC(CeTBaseObject dto) {
		return casoDao.controllaModificheSchedaCompletaUDC();
	}

}
