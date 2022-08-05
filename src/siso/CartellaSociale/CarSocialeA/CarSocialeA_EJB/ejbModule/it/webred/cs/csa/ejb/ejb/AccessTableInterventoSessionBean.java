package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDatiPorSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiSiruSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CasoDAO;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.DiarioDAO;
import it.webred.cs.csa.ejb.dao.ExportCasellarioDAO;
import it.webred.cs.csa.ejb.dao.IndirizzoDAO;
import it.webred.cs.csa.ejb.dao.InterventoDAO;
import it.webred.cs.csa.ejb.dao.InterventoErogazioneDAO;
import it.webred.cs.csa.ejb.dao.SinaDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ComuneBean;
import it.webred.cs.csa.ejb.dto.ErogazionePrestazioneDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.alert.AlertDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.CompartecipazioneDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneDettaglioDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneDettaglioSintesiDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.erogazioni.SpesaDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.csa.ejb.dto.siru.ConfigurazioneFseDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruInputDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruResultDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIAdmAdh;
import it.webred.cs.data.model.CsIAffidoFam;
import it.webred.cs.data.model.CsIBuonoSoc;
import it.webred.cs.data.model.CsICentrod;
import it.webred.cs.data.model.CsIContrEco;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsIInterventoPrFse;
import it.webred.cs.data.model.CsIInterventoPrFseSiru;
import it.webred.cs.data.model.CsIPasti;
import it.webred.cs.data.model.CsIPs;
import it.webred.cs.data.model.CsIPsExport;
import it.webred.cs.data.model.CsIPsExportMast;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIResiAdulti;
import it.webred.cs.data.model.CsIResiMinore;
import it.webred.cs.data.model.CsIRigaQuota;
import it.webred.cs.data.model.CsISchedaLavoro;
import it.webred.cs.data.model.CsISemiResiMin;
import it.webred.cs.data.model.CsIVouchersad;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelRelazioneTipoint;
import it.webred.cs.data.model.CsTbProgettoAltro;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.ExcludeDefaultInterceptors;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableInterventoSessionBean extends CarSocialeBaseSessionBean implements AccessTableInterventoSessionBeanRemote {

	private static final long serialVersionUID = 1L;

	@Autowired
	private InterventoDAO interventoDao; 
	
	@Autowired
	private InterventoErogazioneDAO interventoErogazioneDao;
	
	//SISO - 884
	@Autowired
	private SinaDAO sinaDao;
	
	@Autowired
	private CasoDAO casoDao;
	
	@Autowired
	private IndirizzoDAO indirizzoDao;
	
	@Autowired
	private SoggettoDAO soggettoDao;
	
	@Autowired
	private DiarioDAO diarioDao;
	
	@Autowired
	private ExportCasellarioDAO exportCasellarioDao;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDao;
	
	@EJB
	private AccessTableAlertSessionBeanRemote alertService;
	
	@EJB
	private AccessTableDominiSiruSessionBeanRemote dominiSiruService;
	
	@EJB 
	private AccessTableDatiPorSessionBeanRemote datiPorService;
	
	
	private void valorizzaDettaglioInterventoRif(CsIIntervento inter){
		if(inter.getCsIInterventoCustom()!=null){
		switch (inter.getCsIInterventoCustom().getId().intValue()) {
			case 1 : 			
					if (inter.getCsIVouchersad() != null && !inter.getCsIVouchersad().isEmpty())
						inter.getCsIVouchersad().iterator().next().setCsIIntervento(inter);
					break; 
			case 2 : 
					if (inter.getCsIContrEco() != null && !inter.getCsIContrEco().isEmpty())
						inter.getCsIContrEco().iterator().next().setCsIIntervento(inter);
					 break;
			case 3 : 
					if (inter.getCsICentrod() != null && !inter.getCsICentrod().isEmpty())
						inter.getCsICentrod().iterator().next().setCsIIntervento(inter);
					 break;
			case 4 : 
					if (inter.getCsIPasti() != null && !inter.getCsIPasti().isEmpty()) 
						inter.getCsIPasti().iterator().next().setCsIIntervento(inter);
					 break;
			case 5 : 
					if (inter.getCsIBuonoSoc() != null && !inter.getCsIBuonoSoc().isEmpty())
						inter.getCsIBuonoSoc().iterator().next().setCsIIntervento(inter);
					break;
			case 6 : 	
					if (inter.getCsIResiAdulti() != null && !inter.getCsIResiAdulti().isEmpty())
						inter.getCsIResiAdulti().iterator().next().setCsIIntervento(inter);
					 break;
			case 7 : 
					if (inter.getCsIResiMinore() != null && !inter.getCsIResiMinore().isEmpty()) 
						inter.getCsIResiMinore().iterator().next().setCsIIntervento(inter);
					 break;
			case 8 : 	
					if (inter.getCsIAffidoFam() != null && !inter.getCsIAffidoFam().isEmpty())
						inter.getCsIAffidoFam().iterator().next().setCsIIntervento(inter);
					 break;
			case 9 : 
					if (inter.getCsIAdmAdh() != null && !inter.getCsIAdmAdh().isEmpty()) 
						inter.getCsIAdmAdh().iterator().next().setCsIIntervento(inter);
					 break;
			case 10: 
					if (inter.getCsISemiResiMin() != null && !inter.getCsISemiResiMin().isEmpty())
						inter.getCsISemiResiMin().iterator().next().setCsIIntervento(inter);
					 break;
			case 11: 
					if (inter.getCsISchedaLavoro() != null && !inter.getCsISchedaLavoro().isEmpty()) 
						inter.getCsISchedaLavoro().iterator().next().setCsIIntervento(inter);
					 break;
			}
		}
	}
	
	@Override
	public CsIIntervento salvaIntervento(BaseDTO dto) throws Exception {
			
		CsIIntervento inter = (CsIIntervento) dto.getObj();

		valorizzaDettaglioInterventoRif(inter);
		
		if (inter.getId() == null) {
			inter.setUserIns(dto.getUserId());
			inter.setDtIns(new Date());
			inter.setUsrMod(null);
			inter.setDtMod(null);
			
			interventoDao.saveIntervento(inter);
		} else {
			inter.setUsrMod(dto.getUserId());
			inter.setDtMod(new Date());
			
			try{
			
				interventoDao.updateIntervento(inter);
			
			}catch(Exception t){
				logger.error(t);
				throw t;
			}
		}
		return inter;
	}

	@Override
	public CsFlgIntervento salvaFoglioAmministrativo(InterventoDTO dto) throws Exception {
		Date oggi = new Date();
		CsFlgIntervento inter = (CsFlgIntervento) dto.getFoglio();

		CsTbTipoDiario tipo = new CsTbTipoDiario();
		tipo.setId(new Long(DataModelCostanti.TipoDiario.FOGLIO_AMM_ID));
		
		//Recupero il diario associato alla relazione da inserire
		CsDDiario relazione = null;
		if (dto.getIdRelazione() != null) {
			relazione = diarioDao.findDiarioById(dto.getIdRelazione());
		}
		

		if (inter.getDiarioId() == null) {

			CsACaso caso = casoDao.findCasoById(dto.getCasoId());
			CsOOperatoreBASIC responsabile = casoDao.findResponsabileBASIC(dto.getCasoId());
			
			if (inter.getCsDDiario().getId() <= 0) {
				
				inter.getCsDDiario().setCsACaso(caso);
				inter.getCsDDiario().setDtIns(oggi);
				inter.getCsDDiario().setDtMod(null);
				inter.getCsDDiario().setUserIns(dto.getUserId());
				inter.getCsDDiario().setUsrMod(null);
				inter.getCsDDiario().setCsTbTipoDiario(tipo);
				inter.getCsDDiario().setResponsabileCaso(responsabile!=null ? responsabile.getId() : null);

				//Recupero il diario associato alla relazione da inserire
				if (relazione != null) {
					inter.getCsDDiario().addCsDDiariFiglio(relazione);
				}

				CsDDiario diario = diarioDao.newDiario(inter.getCsDDiario());
				inter.setDiarioId(diario.getId());
				inter.setCsDDiario(diario);
			}

			interventoDao.saveFglIntervento(inter);
			

			//Invio alert nuovo inserimento
			BaseDTO adto = new BaseDTO();
			this.copiaCsTBaseObject(dto, adto);
			adto.setObj(caso.getCsASoggetto());
			adto.setObj2(inter.getCsDDiario().getCsOOperatoreSettore());
			adto.setObj3(DataModelCostanti.TipiAlertCod.FGL_AMM);
			adto.setObj4("un nuovo intervento programmato");
			
			alertSessionBean.addAlertNuovoInserimentoToResponsabileCaso(adto);

		} else {

			List<CsDDiario> lstd = new LinkedList<CsDDiario>(inter.getCsDDiario().getCsDDiariFiglio());
			CsDDiario relazioneOld = null;
			if (lstd != null && lstd.size() > 0) {
				for (CsDDiario d : lstd) {
					//La rimuovo e riaggiungo
					if (d.getCsTbTipoDiario().getId() == DataModelCostanti.TipoDiario.RELAZIONE_ID) {
						relazioneOld = d;
					}
				}
				if (relazioneOld != null)
					inter.getCsDDiario().getCsDDiariFiglio().remove(relazioneOld);
			}

			//Recupero il diario associato alla relazione da inserire
			if (relazione != null) {
				inter.getCsDDiario().addCsDDiariFiglio(relazione);
			}

			inter.getCsDDiario().setDtMod(oggi);
			inter.getCsDDiario().setUsrMod(dto.getUserId());
			CsDDiario diario = diarioDao.updateDiario(inter.getCsDDiario());
			inter.setCsDDiario(diario);

			interventoDao.updateFglIntervento(inter);

		}
		return inter;
	}


	@Override
	public CsIIntervento getInterventoById(BaseDTO dto) throws Exception {
		return interventoDao.getInterventoById((Long) dto.getObj());
	}

	@Override
	public CsFlgIntervento getFoglioInterventoById(BaseDTO dto) throws Exception {
		return interventoDao.getFoglioInterventoById((Long) dto.getObj());
	}

	//SISO-812
	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<CsIIntervento> getListaInterventiByCaso(BaseDTO dto) {
		return interventoDao.getListaInterventiByIdCaso((Long) dto.getObj());
	}
	
	public List<InterventoBaseDTO> getListaInfoInterventiByCaso(BaseDTO dto){
		return interventoDao.getListaInfoInterventiByIdCaso((Long) dto.getObj());
	}
	
	@Override
	public Integer countInterventiByCaso(BaseDTO dto) throws Exception {
		return interventoDao.countInterventiByIdCaso((Long) dto.getObj());
	}

	@Override
	public void deleteFoglioAmministrativo(BaseDTO b) {
		interventoDao.deleteFoglioAmministrativo((Long) b.getObj());
	}

	@Override
	public void deleteIntervento(BaseDTO b) throws Exception {
		interventoDao.deleteIntervento((Long) b.getObj());
	}

	@Override
	public void saveRelRelazioneTipoint(BaseDTO b) {
		interventoDao.saveRelRelazioneTipoint((CsRelRelazioneTipoint) b.getObj());
	}

	@Override
	public void deleteRelRelazioneTipointByIdRelazione(BaseDTO b) {
		interventoDao.deleteRelRelazioneTipointByIdRelazione((Long) b.getObj());
	}

	//SISO-972
	public List<CsIInterventoEsegMastSogg> getBeneficiariByCF (BaseDTO dto){
		return  interventoErogazioneDao.getBeneficiariErogazione((String)(dto.getObj()));
	}
	public List<CsIInterventoEsegMastSogg> getBeneficiariErogazione(BaseDTO dto) {
		//String cf, String attivita, String progetto
		return  interventoErogazioneDao.getBeneficiariErogazione((String)(dto.getObj()),(Long)(dto.getObj2()), (Long)(dto.getObj3()));
	}

	@Override
	public List<CsIInterventoEseg> getInterventoEsegByMasterId(BaseDTO bDto) {
		boolean caricaDettagli = bDto.getObj2()!=null ? (Boolean) bDto.getObj2() : false;
		return interventoErogazioneDao.getInterventoEsegByIdMaster((Long)bDto.getObj(), caricaDettagli );
	}

	@Override
	public void rimuoviInterventoEseguitoMast(BaseDTO dto) throws Exception {
					
		Long masterId = (Long)dto.getObj();
			
		try{			
	
		//Rimuovo i soggetti di riferimento 
		interventoErogazioneDao.rimuoviBeneficiari(masterId);
		
		//Rimuovo interventi eseguiti
		List<CsIInterventoEseg> lstEsegs = interventoErogazioneDao.getInterventoEsegByIdMaster(masterId, true);
		BaseDTO e = new BaseDTO();
		e.setEnteId(dto.getEnteId());
		e.setUserId(dto.getUserId());
		e.setSessionId(dto.getSessionId()); //SISO - 884
		e.setObj(lstEsegs);
		eliminaInterventoEsegStorico(e);
	
		//Rimuovo CSIPS
		interventoErogazioneDao.eliminaCsIPsByEsegMaster(masterId);
		
		CsIInterventoEsegMast master = interventoErogazioneDao.getCsIInterventoEsegMastById(masterId);
		
		//controlla se esiste sina con intervento_eseg_mast_id uguale a masterId; se sì non cancello
		gestisciMaster(master);
		
		Long idProgetto = master.getCsIInterventoPr()!=null ? master.getCsIInterventoPr().getId() : null;
		Long idQuota = master.getCsIQuota()!=null ? master.getCsIQuota().getId() : null;
		
		interventoErogazioneDao.rimuoviInterventoEseguitoMast(masterId);

		//SISO-885: eliminare progetti (dopo il Mast)		
		if(idProgetto!=null)
			 interventoErogazioneDao.eliminaProgetto(idProgetto);
			           
		if(idQuota!=null)
			interventoErogazioneDao.rimuoviQuota(idQuota);
		
		}catch(Exception t){
			logger.error(t.getMessage(), t);
			throw t;
		}
		
	}
	//SISO - 884
	private void gestisciMaster(CsIInterventoEsegMast master){
		
		List<CsDSina> sinas=sinaDao.getSinaByIntEsegMastId(master.getId());
		
		if(sinas != null){
			for(CsDSina sina: sinas){
				sina.setIntEsegMastId(null);
				sinaDao.sinaSetNullMastId(sina.getId());
			}
		}
		
		if(master !=null){
			master.setDiarioPaiId(null);
			interventoErogazioneDao.interventoEsegMastSetNullDiarioPaiId(master.getId());
		}
	
	}

	@Override
	public CsIInterventoEsegMast salvaInterventoEseguitoMast(BaseDTO dto) {
		return interventoErogazioneDao.salvaInterventoEseguitoMast((CsIInterventoEsegMast) dto.getObj());
	}

	@Override
	public CsIContrEco findContributoEconomicoById(BaseDTO dto) {
		return interventoDao.findContributoEconomicoById((Long) dto.getObj());
	}

	@Override
	public CsIPasti findPastiById(BaseDTO dto) {
		return interventoDao.findPastiById((Long) dto.getObj());
	}

	@Override
	public CsICentrod findCentroDiurnoById(BaseDTO dto) {
		return interventoDao.findCentroDiurnoById((Long) dto.getObj());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void eliminaInterventoEsegStorico(BaseDTO dto) {
		eliminaExports(dto);
		interventoErogazioneDao.eliminaInterventoEsegStorico((List<CsIInterventoEseg>) dto.getObj());
	}

	//SISO-884
		public void eliminaExports(BaseDTO dto){
			List<CsIInterventoEseg> storicoDaControllare= (List<CsIInterventoEseg>) dto.getObj();
			
				for(CsIInterventoEseg interventoEseg : storicoDaControllare){
					Long intEsegId= interventoEseg.getId();
				    Long intEsegMastId=interventoEseg.getMasterId();
				    
					List<CsIPsExport> exportList = exportCasellarioDao.findCsIPsExportByIntEsegIdAndIntEsegMastId(intEsegId,intEsegMastId);

					List<Long> idsExportMast=new ArrayList<Long>(); //id CSIPsExportMast relativi alle CsIPsExport cancellate
					
					for(CsIPsExport export : exportList){
						CsIPsExportMast exportMast= export.getCsIPsExportMast();
						if(!idsExportMast.contains(exportMast.getId())){
							idsExportMast.add(exportMast.getId());
						}
						
						if(exportMast!=null){
							//cancello csipsexport
							Long expIntEsegId = export.getCsIInterventoEseg().getId();
							Long expIntEsegMastId = export.getCsIInterventoEsegMast().getId();
							exportCasellarioDao.deleteCsIPsExportByIntEsegIdAndIntEsegMastId(expIntEsegId, expIntEsegMastId);
						}
					}
					//trovo tutte le mast delle export cancellate
					List<CsIPsExportMast> lstMast= exportCasellarioDao.findCsIPsExportMastByIds(idsExportMast);
					
					for(CsIPsExportMast mast: lstMast){
						if(mast.getCsIPsExportList().isEmpty()){
							exportCasellarioDao.deleteCsIPsExportMastById(mast.getId());
						}
					}
				}
			
		}
	//SISO-883 prima si chiamava searchListaErogInterventiBySettore
	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<ErogazioneMasterDTO> searchListaErogInterventi(ErogazioniSearchCriteria bDto) {
		
		//recuperare la lista degli interventi con cognome, nome, cf
		if(!StringUtils.isBlank(bDto.getCodiceFiscale()) | !StringUtils.isBlank(bDto.getCognome()) | !StringUtils.isBlank(bDto.getNome()) | !StringUtils.isBlank(bDto.getDenominazione())){
			List<BigDecimal> lstMasterId = interventoErogazioneDao.searchListaMasterIdBySoggetto(bDto);
			bDto.setLstMasterId(lstMasterId);
			bDto.setSearchByBeneficiario(true);
		}
		
		List<ErogazioneMasterDTO> lstMaster = interventoErogazioneDao.searchListaErogInterventi(bDto);
		for(ErogazioneMasterDTO e : lstMaster){
			List<SoggettoErogazioneBean>beneficiari = new ArrayList<SoggettoErogazioneBean>();
			Long masterId= e.getIdInterventoEsegMaster();
			Long diarioId= e.getDiarioId();
				
			if(masterId!=null){
				List<CsIInterventoEsegMastSogg> lstSoggMast = interventoErogazioneDao.getBeneficiariErogazione(masterId);
				for(CsIInterventoEsegMastSogg sb : lstSoggMast){		
					SoggettoErogazioneBean b = new SoggettoErogazioneBean(sb);
				
					if(b.getCsASoggetto()==null && sb.getCf()!=null){
						CsASoggettoLAZY soggetto = soggettoDao.getSoggettoByCF(sb.getCf());
						b.setCsASoggetto(soggetto);
					}
					beneficiari.add(b);
				}
			}else if(diarioId!=null){
				CsDDiario diario = null;
				
				try{ 
					diario = diarioDao.findDiarioById(diarioId);
				}catch(Exception err){logger.error(err);}
				
				if(diario!=null){
					CsASoggettoLAZY soggetto = diario.getCsACaso().getCsASoggetto();
					CsAAnaIndirizzo residenza = indirizzoDao.getResidenzaBySoggetto(soggetto.getCsAAnagrafica().getId());
					SoggettoErogazioneBean b = new SoggettoErogazioneBean(soggetto, residenza.getLabelIndirizzo(), this.getCasoComuneResidenza(residenza),  residenza.getStatoCod(), true);
					beneficiari.add(b);
				}
				
			}
			
			e.setBeneficiari(beneficiari);
			
			/*Popolo i dati necessari alla lista*/
			Long idIntervento = e.getIdIntervento();
			Long idMaster = e.getIdInterventoEsegMaster();
			if(idIntervento!=null){
				if(idMaster==null){
					idMaster = interventoErogazioneDao.getCsIInterventoEsegMastIdByInterventoId(idIntervento);
				}
			}else{
				idMaster = e.getIdInterventoEsegMaster();
			}
			
			List<CsIInterventoEseg> lstEseg = interventoErogazioneDao.getInterventoEsegByIdMaster(idMaster, true);
			this.fillListaDtoErogazioni(e, lstEseg);
			
			CsIInterventoEseg ultimoCsIInterventoEseg = null;
			if (lstEseg != null && !lstEseg.isEmpty() )
				ultimoCsIInterventoEseg = lstEseg.get(0);
		
			Long idTipoIntervento = e.getTipoIntervento().getId();
			Long idTipoInterventoCustom = e.getIdTipoInterventoCustom();
			Long idCatSociale = e.getIdCategoriaSociale()!=null ? e.getIdCategoriaSociale().longValue() : null;
		
			if(ultimoCsIInterventoEseg!=null){
				CsIInterventoPr progetto = ultimoCsIInterventoEseg.getCsIInterventoEsegMast().getCsIInterventoPr();
				if(progetto!=null) {
					CsOSettore titolare = progetto.getSettoreTitolare();
					CsOSettore gestore = progetto.getSettoreGestore();
					e.setSettoreTitolare(titolare!=null ? titolare.getDescrizioneCompleta(): null);
					e.setServizioAmbito(progetto.getServizioAmbito()!=null ? progetto.getServizioAmbito() : false);
					e.setSettoreGestore(gestore!=null ? progetto.getSettoreGestore().getDescrizioneCompleta() : null);
					
					String descrizioneProgetto = progetto.getProgetto().getDescrizione();
					e.setDescrizioneProgetto(descrizioneProgetto);
					
					String	codiceForm = configurazioneDao.findCodFormProgetto(descrizioneProgetto, idTipoIntervento, idTipoInterventoCustom, idCatSociale);
					String modPor = getGlobalParameter(DataModelCostanti.AmParameterKey.POR_MODELLO_STAMPA);
					if(!StringUtils.isBlank(modPor)) 
						e.setStampaPOR(DataModelCostanti.TipoProgetto.FSE.equalsIgnoreCase(codiceForm));
				}
				CsOSettore erogante = ultimoCsIInterventoEseg.getCsIInterventoEsegMast().getSettoreErogante();
				e.setSettoreErogante(erogante!=null ? erogante.getDescrizioneCompleta() : null);
			}
			
			if(bDto.isLoadDettaglioErogazione() && lstEseg!=null && !lstEseg.isEmpty()){
				Long tipoInterventoCorrente = (idTipoInterventoCustom!=null ? idTipoInterventoCustom : idTipoIntervento);
				//Recupero configurazione attributi per tipo intervento e calcolo dettagli
				HashMap<Long, ErogStatoCfgDTO>  mappaStatiTipoIntervento = new HashMap<Long, ErogStatoCfgDTO>();
				mappaStatiTipoIntervento = configurazioneDao.findConfigIntEsegByTipoIntervento(tipoInterventoCorrente);
				e.setMappaStatiTipoIntervento(mappaStatiTipoIntervento);
			}
		}
		
		return lstMaster;
	}
	
	private void fillListaDtoErogazioni(ErogazioneMasterDTO e, List<CsIInterventoEseg> lstEseg){
		List<ErogazioneDettaglioDTO> lst = new LinkedList<ErogazioneDettaglioDTO>();
		BigDecimal sommaEntita = BigDecimal.ZERO;
		BigDecimal sommaEntitaOreMinuti = BigDecimal.ZERO;//SIO-806 per calcolare i ValQuota dell'unità di misura ore/minuti
		
		String sommaUnitaMisura = "";
		for(CsIInterventoEseg es : lstEseg){
			ErogazioneDettaglioDTO d = new ErogazioneDettaglioDTO();
			
			d.setSpesa(new SpesaDTO(es.getSpesa(),es.getPercGestitaEnte(),es.getValoreGestitaEnte(), false));
			d.setCompartecipazione(new CompartecipazioneDTO(es.getCompartUtenti(), es.getCompartSsn(), es.getCompartAltre(), es.getNoteAltreCompart(), false));
			d.setDataErogazione(es.getDataEsecuzione());
			d.setDataErogazioneA(es.getDataEsecuzioneA()); //SISO-556
			d.setStatoErogazione(es.getStato());
			d.setDescrizione(es.getNote());
			d.setIdInterventoEseg(es.getId());
			//SISO-859 Sommo tutti i valori di valQuota che non sono € per tutte le precedenti erogazioni
			if(es.getCsIValQuota()!=null){
				BigDecimal valQuota =  es.getCsIValQuota().getValQuota() != null ?   es.getCsIValQuota().getValQuota() : BigDecimal.ZERO ;
				if (!es.getCsIInterventoEsegMast().getCsIQuota().isValuta() && !es.getCsIInterventoEsegMast().getCsIQuota().isOreMinuti()){
					sommaEntita = sommaEntita.add(valQuota);
					if(sommaUnitaMisura.isEmpty())
						sommaUnitaMisura = es.getCsIInterventoEsegMast().getCsIQuota().getCsTbUnitaMisura().getValore();
					
					d.setEntita(valQuota.intValue() >0 ? valQuota.toString().concat("  ").concat(sommaUnitaMisura) : null);
				}else if(es.getCsIInterventoEsegMast().getCsIQuota().isOreMinuti()) {
					sommaUnitaMisura = es.getCsIInterventoEsegMast().getCsIQuota().getCsTbUnitaMisura().getValore();
					sommaEntitaOreMinuti = sommaEntitaOreMinuti.add(valQuota);
					
					int ore = valQuota.intValue();
					BigDecimal minutiValQuota = valQuota.remainder(BigDecimal.ONE);
					BigDecimal convMinutiValQuota = (minutiValQuota.multiply(new BigDecimal(60))).setScale(2, RoundingMode.HALF_UP);
					//
					int minuti = convMinutiValQuota.intValue();
					
					String strDate = StringUtils.leftPad(String.valueOf(ore), 2, "0")  + ":" + StringUtils.leftPad(String.valueOf(minuti),  2, "0");
						
					
					d.setEntita(valQuota.intValue() >0 ? strDate.concat("  ").concat(sommaUnitaMisura) : null);
				}
			}
			
			d.setValori(es.getCsIInterventoEsegValores());
			
			lst.add(d);
		}
		
		
		if(sommaEntita.compareTo(BigDecimal.ZERO) != 0  ) {
			sommaUnitaMisura = "Tot. " + sommaUnitaMisura.concat("  ").concat(sommaEntita.toString());
		}else if (sommaEntitaOreMinuti.compareTo(BigDecimal.ZERO) != 0  ) {
			//SISO_806 devo converitire la somma dei Valquota per OreMinuti
			int ore = sommaEntitaOreMinuti.intValue();
			
			BigDecimal minutiDaSomma = sommaEntitaOreMinuti.remainder(BigDecimal.ONE);
			BigDecimal convMinuti = (minutiDaSomma.multiply(new BigDecimal(60))).setScale(0, BigDecimal.ROUND_HALF_UP);
			int minuti = convMinuti.intValue();
			
			String oreMinutiString = String.valueOf(ore) + ":" +  String.valueOf(minuti)  ;
			
			sommaUnitaMisura = "Tot. " + sommaUnitaMisura.concat("  ").concat(oreMinutiString);
		}
		
		e.setSommaUnitaMisura(sommaUnitaMisura);
		e.setListaErogazioniDettaglio(lst);	
	}

	
	private String getCasoComuneResidenza(CsAAnaIndirizzo residenza){
		String comuneResidenza = null;
		if(residenza!=null){
			String comCod = residenza.getComCod();
		 	String comDes = residenza.getComDes();
		 	String comProv = residenza.getProv();
		 	ComuneBean comuneBean = new ComuneBean(comCod,comDes,comProv);

			try{
				ObjectMapper om = new ObjectMapper();
				comuneResidenza  = om.writeValueAsString(comuneBean);
			}
				catch(Exception ex ){}
		}
		return comuneResidenza;
	}

	@Override
	public int countListaErogInterventiBySettore(ErogazioniSearchCriteria bDto) {
		//recuperare la lista degli interventi con cognome, nome, cf
		if(!StringUtils.isBlank(bDto.getCodiceFiscale()) | !StringUtils.isBlank(bDto.getCognome()) | !StringUtils.isBlank(bDto.getNome()) | !StringUtils.isBlank(bDto.getDenominazione())){
			List<BigDecimal> lstMasterId = interventoErogazioneDao.searchListaMasterIdBySoggetto(bDto);
			bDto.setLstMasterId(lstMasterId);
			bDto.setSearchByBeneficiario(true);
		}
		
		return interventoErogazioneDao.countListaErogInterventiBySettore(bDto);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public CsCTipoInterventoCustom saveNewCsCTipoInterventoCustom(BaseDTO dto) {
		return interventoDao.saveNewCsCTipoInterventoCustom((List<CsCTipoInterventoCustom>) dto.getObj(), (CsCTipoInterventoCustom) dto.getObj2());
	}

	@Override
	public void refreshTipoInterventoView(BaseDTO dto) {
		interventoDao.refreshTipoInterventoView();
	}

	@Override
	public CsIVouchersad findVouherSadById(BaseDTO dto) {
		return interventoDao.findVouherSadById((Long) dto.getObj());
	}

	@Override
	public CsIBuonoSoc findBuonoSocialeById(BaseDTO dto) {
		return interventoDao.findBuonoSocialeById((Long) dto.getObj());
	}

	@Override
	public CsIResiMinore findResiMinoreById(BaseDTO dto) {
		return interventoDao.findResiMinoreById((Long) dto.getObj());
	}

	@Override
	public CsIResiAdulti findResiAdultiById(BaseDTO dto) {
		return interventoDao.findResiAdultiById((Long) dto.getObj());
	}

	@Override
	public CsIAffidoFam findAffidoById(BaseDTO dto) {
		return interventoDao.findAffidoById((Long) dto.getObj());
	}

	@Override
	public CsIAdmAdh findAdmById(BaseDTO dto) {
		return interventoDao.findAdmById((Long) dto.getObj());
	}

	@Override
	public CsISemiResiMin findSemiResMinoreById(BaseDTO dto) {
		return interventoDao.findSemiResMinoreById((Long) dto.getObj());
	}

	@Override
	public CsISchedaLavoro findSchedaLavoroById(BaseDTO dto) {
		return interventoDao.findSchedaLavoroById((Long) dto.getObj());
	}
	
	@Override
	public CsIQuota salvaQuota(BaseDTO dto) {
		CsIQuota quota = (CsIQuota) dto.getObj();
		
		if(quota.getCsTbUnitaMisura()!=null)
			quota.getCsIQuotaRipartiz().setCsIQuota(quota);
		
		String user = dto.getUserId();
		Date date = new Date();
			
		if (quota.getId() == null) {
			quota.setUserIns(user);
			quota.setDtIns(date);
			
			quota.getCsIQuotaRipartiz().setUserIns(user);
			quota.getCsIQuotaRipartiz().setDtIns(date);
			
			for(CsIRigaQuota rq : quota.getCsIRigheQuota()){
				rq.setUserIns(user);
				rq.setDtIns(date);
				
				rq.getCsIValQuota().setUserIns(user);
				rq.getCsIValQuota().setDtIns(date);
				
			}

		} else {
			
			quota.setUsrMod(dto.getUserId());
			quota.setDtMod(new Date());
			
			if(quota.getCsIQuotaRipartiz().getUserIns()==null)
				quota.getCsIQuotaRipartiz().setUserIns(user);
			else
				quota.getCsIQuotaRipartiz().setUsrMod(user);
			
			if(quota.getCsIQuotaRipartiz().getDtIns()==null)
				quota.getCsIQuotaRipartiz().setDtIns(date);
			else
				quota.getCsIQuotaRipartiz().setDtMod(date);
			
			
			for(CsIRigaQuota rq : quota.getCsIRigheQuota()){
				
				if(rq.getId()!=null && rq.getId()>0){
					rq.setUsrMod(user);
					rq.setDtMod(date);
				}else{
					rq.setUserIns(user);
					rq.setDtIns(date);
				}
				
				if(rq.getCsIValQuota().getId()>0){
					rq.getCsIValQuota().setUsrMod(user);
					rq.getCsIValQuota().setDtMod(date);
				}else{
					rq.getCsIValQuota().setUserIns(user);
					rq.getCsIValQuota().setDtIns(date);
				}
				
		}
			
		}
		return interventoDao.updateCsIQuota(quota);
	}
	
	@Override
	public CsIInterventoPr salvaProgettoIntervento(BaseDTO dto) {
		CsIInterventoPr progetto = (CsIInterventoPr) dto.getObj();
		
		
		String user = dto.getUserId();
		Date date = new Date();
			
		if (progetto.getId() == null) {
			progetto.setUserIns(user);
			progetto.setDtIns(date);
			
		} else {
			progetto.setUsrMod(dto.getUserId());
			progetto.setDtMod(new Date());
		}
		
		if(progetto.getCsIInterventoPrFse()!=null){
			
			//SISO-817
			if(progetto.getCsIInterventoPrFse().getUserIns()==null){
				progetto.getCsIInterventoPrFse().setUserIns(user);
				progetto.getCsIInterventoPrFse().setDtIns(date);
				progetto.getCsIInterventoPrFse().setCsIInterventoPr(progetto);
			}
			else{
				progetto.getCsIInterventoPrFse().setUsrMod(user);
				progetto.getCsIInterventoPrFse().setDtMod(date);
				progetto.getCsIInterventoPrFse().setCsIInterventoPr(progetto);
			}
			if(progetto.getCsIInterventoPrFse().getCsTbGrVulnerabile()!=null && progetto.getCsIInterventoPrFse().getCsTbGrVulnerabile().getId()==null)
				progetto.getCsIInterventoPrFse().setCsTbGrVulnerabile(null);
			
			if(progetto.getCsIInterventoPrFseSiru()!=null ){
				if(progetto.getId()==null)
					progetto.getCsIInterventoPrFseSiru().setCsIInterventoPr(progetto);
				else{
					progetto.getCsIInterventoPrFseSiru().setInterventoPrId(progetto.getId());
					progetto.getCsIInterventoPrFseSiru().setCsIInterventoPr(progetto);
					}
			}

		}
		else{
			progetto.setCsIInterventoPrFseSiru(null);
		}
		//SISO-972
		CsIInterventoPr csiInterventoPr = interventoDao.updateCsIInterventoPr(progetto);
		salvaFseAttivitaStorico( dto,  csiInterventoPr);
		
		//SISO-972 fine
		return csiInterventoPr;
	}
	//SISO-972 Inizio
	public void salvaFseAttivitaStorico(BaseDTO dto, CsIInterventoPr csiInterventoPr)
	{   
		Long progettoId = csiInterventoPr.getProgetto()!=null ? csiInterventoPr.getProgetto().getId() : null;
		Long attivitaId = csiInterventoPr.getProgettoAttivita()!=null ? csiInterventoPr.getProgettoAttivita().getId() : null;
		SoggettoErogazioneBean seb = (SoggettoErogazioneBean) dto.getObj2();
		dto.setObj(seb.getCodiceFiscale());
		
		List<CsIInterventoEsegMastSogg> lsti = interventoErogazioneDao.getBeneficiariErogazione(seb.getCodiceFiscale(), attivitaId, progettoId);
		if(lsti != null)
			for(CsIInterventoEsegMastSogg csInterventoMastSogg : lsti){
				
				Long masterId = csInterventoMastSogg.getId().getMasterId();
				
				CsIInterventoPr prCurrent = interventoErogazioneDao.getProgettoByMasterId(masterId);
				
				//Non riaggiorno quello corrente 
				if(prCurrent.getCsIInterventoPrFse() != null && !prCurrent.getId().equals(csiInterventoPr.getId())){
					
					ConfigurazioneFseDTO mappaCampiFse = null;
					String codRoutingTitolare = prCurrent.getSettoreTitolare().getCsOOrganizzazione().getCodRouting();
					ArFfProgetto progetto = prCurrent.getProgetto();
					if (progetto!=null && progetto.getId()>0) {  
						mappaCampiFse = configurazioneDao.loadCampiFse(progetto.getId());
					}
					 
					//csiInterventoPrFseUpdate.setInterventoPrId(prCurrent.getCsIInterventoPrFse().getInterventoPrId());
					prCurrent.getCsIInterventoPrFse().setAzCF(csiInterventoPr.getCsIInterventoPrFse().getAzCF());
					prCurrent.getCsIInterventoPrFse().setAzCodAteco(csiInterventoPr.getCsIInterventoPrFse().getAzCodAteco());
					prCurrent.getCsIInterventoPrFse().setAzComuneCod(csiInterventoPr.getCsIInterventoPrFse().getAzComuneCod());
					prCurrent.getCsIInterventoPrFse().setAzComuneCod(csiInterventoPr.getCsIInterventoPrFse().getAzComuneCod());

					prCurrent.getCsIInterventoPrFse().setAzComuneDes(csiInterventoPr.getCsIInterventoPrFse().getAzComuneDes());
					prCurrent.getCsIInterventoPrFse().setAzDescDimensioni(csiInterventoPr.getCsIInterventoPrFse().getAzDescDimensioni());
					prCurrent.getCsIInterventoPrFse().setAzFormaGiuridica(csiInterventoPr.getCsIInterventoPrFse().getAzFormaGiuridica());
					prCurrent.getCsIInterventoPrFse().setAzPIVA(csiInterventoPr.getCsIInterventoPrFse().getAzPIVA());
					prCurrent.getCsIInterventoPrFse().setAzProv(csiInterventoPr.getCsIInterventoPrFse().getAzProv());
					prCurrent.getCsIInterventoPrFse().setAzRagioneSociale(csiInterventoPr.getCsIInterventoPrFse().getAzRagioneSociale());
					prCurrent.getCsIInterventoPrFse().setAzVia(csiInterventoPr.getCsIInterventoPrFse().getAzVia());
					prCurrent.getCsIInterventoPrFse().setCellulare(csiInterventoPr.getCsIInterventoPrFse().getCellulare());
					//csiInterventoPrFseUpdate.setCittadinanza(csiInterventoPr.getCsIInterventoPrFse().getCittadinanza());
					
					prCurrent.getCsIInterventoPrFse().setDtMod(new Date()); 
					prCurrent.getCsIInterventoPrFse().setUsrMod(dto.getUserId()); 
					
					prCurrent.getCsIInterventoPrFse().setDtIns(csiInterventoPr.getCsIInterventoPrFse().getDtIns());
					prCurrent.getCsIInterventoPrFse().setUserIns(csiInterventoPr.getCsIInterventoPrFse().getUserIns());
					
					prCurrent.getCsIInterventoPrFse().setComuneDomicilio(csiInterventoPr.getCsIInterventoPrFse().getComuneDomicilio());
					prCurrent.getCsIInterventoPrFse().setComuneNascita(csiInterventoPr.getCsIInterventoPrFse().getComuneNascita());
					//csiInterventoPrFseUpdate.setComuneResidenza(csiInterventoPr.getCsIInterventoPrFse().getComuneResidenza());
					
					prCurrent.getCsIInterventoPrFse().setComunicaVul(csiInterventoPr.getCsIInterventoPrFse().getComunicaVul());
					prCurrent.getCsIInterventoPrFse().setCsTbGrVulnerabile(csiInterventoPr.getCsIInterventoPrFse().getCsTbGrVulnerabile());
					prCurrent.getCsIInterventoPrFse().setEmail(csiInterventoPr.getCsIInterventoPrFse().getEmail());
					prCurrent.getCsIInterventoPrFse().setFlagAltroCorso(csiInterventoPr.getCsIInterventoPrFse().getFlagAltroCorso());
					prCurrent.getCsIInterventoPrFse().setFlagResDom(csiInterventoPr.getCsIInterventoPrFse().getFlagResDom());
					prCurrent.getCsIInterventoPrFse().setIban(csiInterventoPr.getCsIInterventoPrFse().getIban());
					prCurrent.getCsIInterventoPrFse().setLavoroDescOrario(csiInterventoPr.getCsIInterventoPrFse().getLavoroDescOrario());
					prCurrent.getCsIInterventoPrFse().setLavoroDescTipo(csiInterventoPr.getCsIInterventoPrFse().getLavoroDescTipo());
					prCurrent.getCsIInterventoPrFse().setDurataRicLavoroId(csiInterventoPr.getCsIInterventoPrFse().getDurataRicLavoroId());
					prCurrent.getCsIInterventoPrFse().setTelefono(csiInterventoPr.getCsIInterventoPrFse().getTelefono());
					prCurrent.getCsIInterventoPrFse().setTitoloStudioAnno(csiInterventoPr.getCsIInterventoPrFse().getTitoloStudioAnno());
					prCurrent.getCsIInterventoPrFse().setViaDomicilio(csiInterventoPr.getCsIInterventoPrFse().getViaDomicilio());
					//csiInterventoPrFseUpdate.setViaResidenza(csiInterventoPr.getCsIInterventoPrFse().getViaResidenza());
					
					prCurrent.getCsIInterventoPrFse().setStatoNascitaCod(csiInterventoPr.getCsIInterventoPrFse().getStatoNascitaCod());
					prCurrent.getCsIInterventoPrFse().setStatoNascitaDes(csiInterventoPr.getCsIInterventoPrFse().getStatoNascitaDes());

					prCurrent.getCsIInterventoPrFse().setTitoloStudioAnno(csiInterventoPr.getCsIInterventoPrFse().getTitoloStudioAnno());
					
					//prCurrent.setCsIInterventoPrFse(csiInterventoPrFseUpdate);
					prCurrent.setUsrMod(dto.getUserId());
					prCurrent.setDtMod(new Date());
					
					prCurrent.getCsIInterventoPrFse().setCsIInterventoPr(prCurrent);
					
					//SIRU
					BaseDTO bdto = new BaseDTO();
					bdto.setObj(prCurrent);
					bdto.setObj2(seb);
					bdto.setObj3(mappaCampiFse);
					bdto.setEnteId(dto.getEnteId());
					bdto.setSessionId(dto.getSessionId());
					bdto.setUserId(dto.getUserId());
					
					CsIInterventoPrFseSiru csiInterventoPrSiru = validaSiru(bdto).getSiruInterventi();
					BeanUtils.copyProperties(csiInterventoPrSiru, prCurrent.getCsIInterventoPrFseSiru());
					
					prCurrent.getCsIInterventoPrFseSiru().setCsIInterventoPr(prCurrent);
					prCurrent.getCsIInterventoPrFseSiru().setInterventoPrId(prCurrent.getId());
				
					//FINE SIRU
					interventoDao.updateCsIInterventoPr(prCurrent);
			}
					
		}		
	}
	
	//SISO-972 Fine
	
	@Override
	public CsIInterventoPr getProgettoByMasterId(BaseDTO dto) {
		return interventoErogazioneDao.getProgettoByMasterId((Long)dto.getObj());
	}
	
	
	@Override
	public void salvaRifInterventoToPai(BaseDTO dto) throws Exception
	{
		Long idIntervento = (Long) dto.getObj();
		CsDPai csDPaiNuovoRif = (CsDPai) dto.getObj2();
		Long csDPaiRifDaSostituireId = (Long) dto.getObj3();
		CsIIntervento intervento= interventoDao.getInterventoById(idIntervento);

		if (csDPaiNuovoRif==null && //sostituzione
				intervento!=null && csDPaiRifDaSostituireId!=null
				&& !csDPaiRifDaSostituireId.equals(intervento.getDiarioPaiId())){

			// skip
		} else {
			intervento.setDiarioPaiId(csDPaiNuovoRif.getDiarioId());
			interventoDao.updateIntervento(intervento);
		}
	}
	

	@Override
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors  
	public List<CsIInterventoEsegMastSogg> findSoggettiErogazioneSenzaCaso(BaseDTO dto) {
		return interventoErogazioneDao.findMastSoggSganciatiCaso();
	}
	
	@Override
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors  
	public List<CsIInterventoEsegMastSogg> findSoggettiErogazioneConCaso(BaseDTO bDto) {
		return interventoErogazioneDao.findMastSoggAgganciatiCaso();
	}
     

	@Override
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public void updateSoggettoErogazione(BaseDTO dto) {
		interventoErogazioneDao.updateSoggettoErogazione((CsIInterventoEsegMastSogg)dto.getObj());
		
	}

	@Override
	public boolean esisteInterventoErogatoByCF(BaseDTO dto) {
		int count = interventoErogazioneDao.countTipoErogazioniByCF(DataModelCostanti.TipoStatoErogazione.EROGATIVO, (String)dto.getObj());
		return count>0;
	}
	
	@Override
	public List<ErogazioneBaseDTO> getListaInterventiErogatiByCF(BaseDTO dto) {
		return interventoErogazioneDao.getListaInterventiErogatiByCF((String)dto.getObj(), (Boolean) dto.getObj2());
	}

	//inizio SISO-500  
	@Override
	public CsIInterventoEsegMast getCsIInterventoEsegMastByInterventoId(BaseDTO bDto) { 
		return interventoErogazioneDao.getCsIInterventoEsegMastByInterventoId((Long) bDto.getObj());
	}
	//fine SISO-500 
	
	@Override
	public Long getCsIInterventoEsegMastIdByInterventoId(BaseDTO bDto) { 
		return interventoErogazioneDao.getCsIInterventoEsegMastIdByInterventoId((Long) bDto.getObj());
	}
	
	@Override
	public CsIPs getCsIPsByInterventoId(BaseDTO bDto) { 
		return interventoErogazioneDao.getCsIPsByInterventoId((Long) bDto.getObj());
	}
	//inizio SISO-822  
    @Override
     public CsIInterventoEsegMast getCsIInterventoEsegMastById(BaseDTO bDto) { 
		return interventoErogazioneDao.getCsIInterventoEsegMastById((Long) bDto.getObj());
	}
	//fine SISO-822 
	
	//INIZIO SISO-1131
		@Override
		public List<CsTbProgettoAltro> findProgettiAltro() {				
			return interventoDao.findProgettiAltro();
		}
		
		@Override
		public List<CsTbProgettoAltro> findProgettiAltroPerDesc(BaseDTO dto) {				
			return interventoDao.findProgettiAltroPerDesc((String) dto.getObj());
		}
		
		//FINE SISO-1131


	@Override
	public void gestisciAlertErogazioni(BaseDTO in) throws CarSocialeServiceException {
		try{
			CsIInterventoEsegMast master = (CsIInterventoEsegMast)in.getObj();
			
			//Invio notifica al responsabile del caso, se esiste, di avvenuta erogazione
			if((Boolean)in.getObj2()){
				BaseDTO bdto = new BaseDTO();
				bdto.setEnteId(in.getEnteId());
				bdto.setUserId(in.getUserId());
				bdto.setSessionId(in.getSessionId());
				
				//bdto.setObj(this.soggettoErogazione.getCodiceFiscale());
				CsIInterventoEsegMastSogg s = master.getBeneficiarioRiferimento();
				bdto.setObj(s.getCf());
				bdto.setObj2(master.getCsOOperatoreSettore());
				bdto.setObj3(DataModelCostanti.TipiAlertCod.EROGAZIONE);
				bdto.setObj4("una nuova erogazione");
				
				alertService.addAlertNuovoInserimentoToResponsabileCaso(bdto);	
			}
			
			//Invio notifica al settore titolare, se diverso dal settore gestore, di avvenuta erogazione
			List<CsIInterventoEseg> erogAlertTitolare = (List<CsIInterventoEseg>) in.getObj3();
			for(CsIInterventoEseg e : erogAlertTitolare){
				
				AlertDTO a = new AlertDTO();
				a.setEnteId(in.getEnteId());
				a.setUserId(in.getUserId());
				a.setSessionId(in.getSessionId());
				
				a.setNotificaSettoreSegnalante(false);
				a.setOperatoreFrom(e.getCsOOperatoreSettore().getCsOOperatore());
				a.setSettoreFrom(e.getCsOOperatoreSettore().getCsOSettore());
				a.setOrganizzazioneFrom(e.getCsOOperatoreSettore().getCsOSettore().getCsOOrganizzazione());
				
				a.setSettoreTo(master.getCsIInterventoPr().getSettoreTitolare());
				a.setTipo(DataModelCostanti.TipiAlertCod.EROGAZIONE);
				
				String beneficiari="";
				for(CsIInterventoEsegMastSogg s : master.getBeneficiari()){
					beneficiari+= s.getCognome()+" "+s.getNome()+"("+s.getCf()+") ";
					if(s.getRiferimento()) 
						a.setCaso(s.getCaso());
				}
				
				String operatore = a.getOperatoreFrom().getDenominazione();
				
				a.setTitolo("Notifica nuova erogazione: "+beneficiari);
				
				CsIInterventoPr pr = e.getCsIInterventoEsegMast().getCsIInterventoPr();
				
				
				String descrizione = "L'operatore "+operatore+" del settore "+
				a.getSettoreFrom().getNome() +"("+a.getOrganizzazioneFrom().getNome()+") ha aggiunto una nuova erogazione per i beneficiari:"+beneficiari+
				"- sett. erogante:"+e.getCsIInterventoEsegMast().getSettoreErogante().getNome()+" ("+e.getCsIInterventoEsegMast().getSettoreErogante().getCsOOrganizzazione().getNome()+")";
				
				if(pr!=null){
				descrizione += (pr.getSettoreTitolare()!=null ? ", settore titolare : "+pr.getSettoreTitolare().getNome()+" ("+pr.getSettoreTitolare().getCsOOrganizzazione().getNome()+")" : "")+
							   (pr.getSettoreGestore()!=null  ? ", settore gestore :"  +pr.getSettoreGestore().getNome() +" ("+pr.getSettoreGestore().getCsOOrganizzazione().getNome()+")"  : "");
				}
				
				a.setDescrizione(descrizione);
				
				alertService.addAlert(a);	
			}
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
		
	}

	//SISO-748
	@Override
	public void salvaRifErogazioneToPai(BaseDTO bdto) throws Exception 
	{
		Long idInterventoEsegMast = (Long) bdto.getObj();
		Long csDPaiRif = (Long) bdto.getObj2();
		CsIInterventoEsegMast interventoMast = interventoErogazioneDao.getCsIInterventoEsegMastById(idInterventoEsegMast);
		
		if(csDPaiRif==null) //sostituzione
		{
			if(interventoMast.getDiarioPaiId() != null){
				interventoMast.setDiarioPaiId(null);
				interventoErogazioneDao.salvaInterventoEseguitoMast(interventoMast);
			}
		}
		else //nuovo
		{
			BigDecimal toChange = new BigDecimal(csDPaiRif);
			
			if(!toChange.equals(interventoMast.getDiarioPaiId())){
				interventoMast.setDiarioPaiId(new BigDecimal(csDPaiRif.longValue()));
				interventoErogazioneDao.salvaInterventoEseguitoMast(interventoMast);
			}
		}
	}
	
	public SiruResultDTO validaSiru(BaseDTO dto){
		CsIInterventoPr progetto = (CsIInterventoPr) dto.getObj();
		SoggettoErogazioneBean seb =(SoggettoErogazioneBean) dto.getObj2();
		CsIInterventoPrFse fse = progetto.getCsIInterventoPrFse();
		
		SiruInputDTO pds = new SiruInputDTO();
		pds.setCittadinanza(seb.getCittadinanza());
		pds.setCodiceFiscale(seb.getCodiceFiscale());
		
		Object[] cfExt = estraiDaCF(seb.getCodiceFiscale());
		pds.setSesso(cfExt[0].toString());
		pds.setDataNascita((Date)cfExt[1]);
		
		pds.setFlagResDom(fse.getFlagResDom());
		
		if(!StringUtils.isBlank(fse.getComuneNascita())){
			pds.setComuneNascitaCod(estraiFromJson(fse.getComuneNascita(), "codIstatComune"));
		}else if(!StringUtils.isBlank(fse.getStatoNascitaCod())){
			pds.setStatoNascitaCod(fse.getStatoNascitaCod());
		}
		
		pds.setCodIstatComuneResidenza(estraiFromJson(seb.getJsonComuneResidenza(), "codIstatComune"));
		pds.setCodIstatComuneDomicilio(estraiFromJson(fse.getComuneDomicilio(), "codIstatComune"));
		
		if(progetto.getCsTbTitoloStudio() != null && progetto.getCsTbTitoloStudio().getId() != 0)
			pds.setIdTitoloStudio(String.valueOf(progetto.getCsTbTitoloStudio().getId()));
		pds.setCsTbIngMercato(progetto.getCsTbIngMercato());
		
		if(fse.getComunicaVul()) {
			pds.setGrpVulnerabilita(fse.getCsTbGrVulnerabile()!=null && ! "0".equals(fse.getCsTbGrVulnerabile().getId()) ? fse.getCsTbGrVulnerabile().getId() : null);
		}else{
			boolean regMarche = false;
			String modelloPor = this.getGlobalParameter(DataModelCostanti.AmParameterKey.POR_MODELLO_STAMPA);
			if (!StringUtils.isBlank(modelloPor))
				regMarche = modelloPor.contains("Marche");
			if(regMarche)
				pds.setGrpVulnerabilita(DataModelCostanti.GrVulnerabile.NON_COMUNICA_VULNERABILITA);
		}
		
		pds.setAzCodAteco(fse.getAzCodAteco());
		pds.setDescDimAzienda(fse.getAzDescDimensioni());
		pds.setAzFormaGiuridica(fse.getAzFormaGiuridica()!=null ? fse.getAzFormaGiuridica().getId() : null);
		pds.setDescOrarioLavoro(fse.getLavoroDescOrario());
		pds.setDescTipoLavoro(fse.getLavoroDescTipo());
		pds.setAzPi(fse.getAzPIVA());
		pds.setAzCf(fse.getAzCF());
		pds.setAzRagioneSociale(fse.getAzRagioneSociale());
		pds.setAzVia(fse.getAzVia());
		pds.setDurataRicLavoroId(fse.getDurataRicLavoroId());
		pds.setAzComuneCod(fse.getAzComuneCod());

		BaseDTO dtoVal = new BaseDTO();
		dtoVal.setEnteId(dto.getEnteId());
		dtoVal.setSessionId(dto.getSessionId());
		dtoVal.setUserId(dto.getUserId());
		dtoVal.setObj(pds);
		dtoVal.setObj2(dto.getObj3());
		SiruResultDTO val = datiPorService.validaSiru(dtoVal);
		return val;
		
	}
	
	private String estraiFromJson(String json, String attributo){
		ObjectMapper objectMapper = new ObjectMapper();
		if(!StringUtils.isBlank(json)) {
			JsonNode node;
			try {
				node = objectMapper.readValue(json, JsonNode.class);
				if(!node.isNull()){
					return node.get(attributo).asText();
				}
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
			
		}
		return null;
	}

	private Object[] estraiDaCF(String cf) {
		Object[] toReturn = new Object[3];
		Character sex = new Character('M');
		
		//luogo
		int sizeCf = cf.length();
		String luogo = cf.substring(sizeCf-5,sizeCf-1);

		// day
		String day = cf.substring(9, 11);
		int numDay = Integer.parseInt(day);
		if (numDay > 31) {
			sex = new Character('F');
			numDay -= 40;
		}
		// month
		int mm = 0;
		char m = cf.substring(8, 9).toLowerCase().charAt(0);
		switch (m) {
		case 'a':
			mm = 0;
			break;
		case 'b':
			mm = 1;
			break;
		case 'c':
			mm = 2;
			break;
		case 'd':
			mm = 3;
			break;
		case 'e':
			mm = 4;
			break;
		case 'h':
			mm = 5;
			break;
		case 'l':
			mm = 6;
			break;
		case 'm':
			mm = 7;
			break;
		case 'p':
			mm = 8;
			break;
		case 'r':
			mm = 9;
			break;
		case 's':
			mm = 10;
			break;
		case 't':
			mm = 11;
			break;
		}
		// year
		int theYear = 0;
		int thisYear = Integer.parseInt(String.valueOf(
				Calendar.getInstance().get(Calendar.YEAR)).substring(2, 4));
		String yy = cf.substring(6, 8);
		int y = Integer.parseInt(yy);
		if (y >= thisYear) {
			theYear = 1900 + y;
		} else {
			theYear = 2000 + y;
		}
		
		Calendar c = Calendar.getInstance();
		c.set(theYear, mm, numDay);
		Date birthDate =  c.getTime();
		toReturn[0] = sex;
		toReturn[1] = birthDate;
		toReturn[2] = luogo;
		
		return toReturn;
}

	//SISO - 886
	public List<CsIInterventoEsegMastSogg> getBeneficiari (BaseDTO dto){
		return  interventoErogazioneDao.getBeneficiariErogazione((Long)(dto.getObj()));
	}
	
	//SISO - 883
     public List<ErogazionePrestazioneDTO> recuperaListaErogazioneDuplicateByCf(BaseDTO bdto){
		return  interventoErogazioneDao.searchListaInterventiByCf((String)bdto.getObj());
	}
     

     
     //SISO-1110 Fine
//SISO-1131
    @Override
 	public CsTbProgettoAltro salvaProgettoAltro(BaseDTO dto) {
    	CsTbProgettoAltro progettoAltro = (CsTbProgettoAltro) dto.getObj();
 		
 		
 		String user = dto.getUserId();
 		Date date = new Date();
 		
 		progettoAltro.setUserIns(user);
 		progettoAltro.setDtIns(date);
 		

 		progettoAltro = interventoDao.updateCsTbProgettoAltro(progettoAltro);
 		
 		return progettoAltro;
 	}
     //FINE SISO-1131

	@Override
	public List<ErogazioneDettaglioSintesiDTO> getSintesiErogazioniByInterventoId(BaseDTO dto){
		Long masterId = (Long)dto.getObj();
		return interventoErogazioneDao.getSintesiErogazioniByInterventoId(masterId);
	}
	
	@Override
	public boolean verificaUsoArFonte(BaseDTO dto) {
		Long idOrg = (Long)dto.getObj();
		Long idFonte = (Long)dto.getObj2();
		
		return interventoErogazioneDao.verificaUsoArFonte(idOrg, idFonte);
	}

}