package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDatiPorSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiSiruSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CasoDAO;
import it.webred.cs.csa.ejb.dao.InterventoDAO;
import it.webred.cs.csa.ejb.dao.InterventoErogazioneDAO;
import it.webred.cs.csa.ejb.dao.SinaDAO;
import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ComuneBean;
import it.webred.cs.csa.ejb.dto.ErogazionePrestazioneDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.InformativaDTO;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneDettaglioSintesiDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrUtilsBean;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.csa.ejb.dto.siru.ConfigurazioneFseDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruInputDTO;
import it.webred.cs.csa.ejb.dto.siru.SiruResultDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.ArRelClassememoPresInps;
import it.webred.cs.data.model.ArRelIntCustomIstat;
import it.webred.cs.data.model.ArRelIntCustomPresInps;
import it.webred.cs.data.model.ArTClasse;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgAttrOption;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEseg;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsCfgIntEsegStatoInt;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDRelazione;
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
import it.webred.cs.data.model.CsRelRelazioneTipoint;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsRelSettCsocTipoInterPK;
import it.webred.cs.data.model.CsTbProgettoAltro;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.data.model.VArCTariffa;
import it.webred.cs.data.model.VGerrarchiaServizi;
import it.webred.cs.data.model.VLineaFin;
import it.webred.cs.data.model.VServiziCustom;
import it.webred.cs.data.model.VTipiInterventoUsati;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
	@EJB
	public AccessTableOperatoreSessionBeanRemote operatoreSessionBean;
	@EJB
	public AccessTableDiarioSessionBeanRemote diarioSessionBean;
	@EJB
	public AccessTableConfigurazioneSessionBeanRemote confSessionBean;
	@EJB
	public AccessTableSoggettoSessionBeanRemote soggettoSessionBean;
	@EJB
	private AccessTableAlertSessionBeanRemote alertService;
	@EJB
	private AccessTableDominiSiruSessionBeanRemote dominiSiruService;
	@EJB
	private AccessTableIndirizzoSessionBeanRemote indirizziService;
	@EJB 
	private AccessTableDatiPorSessionBeanRemote datiPorService;
	
	//SISO - 884
	@EJB
	private AccessTablePsExportSessionBeanRemote psExportService;
		
	protected SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	
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

		BaseDTO bdto = new BaseDTO();
		copiaCsTBaseObject(dto, bdto);
		
		//Recupero il diario associato alla relazione da inserire
		CsDDiario relazione = null;
		if (dto.getIdRelazione() != null) {
			bdto.setObj(dto.getIdRelazione());
			relazione = diarioSessionBean.findDiarioById(bdto);
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

				bdto.setObj(inter.getCsDDiario());
				CsDDiario diario = diarioSessionBean.createDiario(bdto);
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
			bdto.setObj(inter.getCsDDiario());
			CsDDiario diario = diarioSessionBean.updateDiario(bdto);
			inter.setCsDDiario(diario);

			interventoDao.updateFglIntervento(inter);

		}
		return inter;
	}

	@Override
	public List<KeyValueDTO> findAllTipiIntervento(CeTBaseObject dto) {
		List<KeyValueDTO> lstOut = new ArrayList<KeyValueDTO>();
		List<CsCTipoIntervento> lst = interventoDao.findTipiIntervento(false);
		for(CsCTipoIntervento c : lst){
			KeyValueDTO kv = new KeyValueDTO(c.getId(), c.getDescrizione());
			kv.setAbilitato(c.getAbilitato()!=null ? c.getAbilitato().booleanValue() : false);
			lstOut.add(kv);
		}
		return lstOut;
	}
	
	@Override
	public List<KeyValueDTO> findTipiInterventoAbilitati(BaseDTO dto) {
		List<KeyValueDTO> lstOut = new ArrayList<KeyValueDTO>();
		List<CsCTipoIntervento> lst = interventoDao.findTipiIntervento(true);
		String tipo = (String)dto.getObj();
		for(CsCTipoIntervento c : lst){
			if(StringUtils.isBlank(tipo) || tipo.equalsIgnoreCase(c.getTipo())){
				KeyValueDTO kv = new KeyValueDTO(c.getId(), c.getDescrizione());
				kv.setAbilitato(true);
				lstOut.add(kv);
			}
		}
		return lstOut;
	}

	@Override
	public List<CsCTipoIntervento> findTipiInterventoCatSoc(BaseDTO dto) {
		return interventoDao.findTipiInterventoCatSoc((Long) dto.getObj(), new Date());
	}

	@Override
	public List<CsCTipoIntervento> findTipiInterventoSettoreCatSoc(InterventoDTO dto) {
		List<Long> lst = dto.getLstIdCatSoc();
		if ((lst == null || lst.isEmpty()) && dto.getIdCatsoc() != null) {
			lst = new ArrayList<Long>();
			lst.add(dto.getIdCatsoc());
		}
		return interventoDao.findTipiInterventoSettoreCatsoc(dto.getIdSettore(), lst);
	}

	@Override
	public CsIIntervento getInterventoById(BaseDTO dto) throws Exception {
		return interventoDao.getInterventoById((Long) dto.getObj());
	}

	@Override
	public CsCTipoIntervento getTipoInterventoById(BaseDTO dto){
		return interventoDao.getTipoInterventoById((Long)dto.getObj());
	}

	@Override
	public CsFlgIntervento getFoglioInterventoById(BaseDTO dto) throws Exception {
		return interventoDao.getFoglioInterventoById((Long) dto.getObj());
	}

	//SISO-812
	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<CsIIntervento> getListaInterventiByCaso(BaseDTO dto) throws Exception {
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
	public CsRelSettCsocTipoInter findRelSettCsocTipoInterById(BaseDTO bdto) {
		return interventoDao.findRelSettCsocTipoInterById((CsRelSettCsocTipoInterPK) bdto.getObj());
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

	@Override
	public CsCfgIntEseg findConfigurazioneInterventiEseguitiById(BaseDTO bDto) {
		return interventoErogazioneDao.findCfgIntEsegById((Long) bDto.getObj());
	}
	//SISO-972
	public List<CsIInterventoEsegMastSogg> getBeneficiariByCF (BaseDTO dto){
		return  interventoErogazioneDao.getBeneficiariErogazione((String)(dto.getObj()));
	}
	public List<CsIInterventoEsegMastSogg> getBeneficiariErogazione(BaseDTO dto) {
		//String cf, String attivita, String progetto
		return  interventoErogazioneDao.getBeneficiariErogazione((String)(dto.getObj()),(Long)(dto.getObj2()), (Long)(dto.getObj3()));
	}
	//SISO-972 Fine
/*	@Override
	public CsCfgIntEseg findConfigIntErogByTipoIntervento(BaseDTO bDto) {
		return interventoErogazioneDao.findCfgIntEsegByTipoInterventoId((Long) bDto.getObj());
	}*/
	
	public HashMap<Long, ErogStatoCfgDTO> findConfigIntEsegByTipoIntervento(BaseDTO bdto){
		Long tipoInterventoId = (Long) bdto.getObj();
		List<CsCfgIntEsegStatoInt> lst = interventoErogazioneDao.findCfgIntEsegByTipoInterventoId(tipoInterventoId);
		HashMap<Long, ErogStatoCfgDTO> mappaEseg = new HashMap<Long, ErogStatoCfgDTO> ();
		if(lst!=null)
			mappaEseg = IntEsegAttrUtilsBean.getMappaStatoAttributi(lst);
		else
			logger.warn("Lista 'CsCfgIntEseg' vuota per il tipo intervento:"+tipoInterventoId);	
		return mappaEseg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CsCfgIntEsegStato> getListaIntEsegStatoByTipiStato(BaseDTO bDto) {
		List<String> obj = (List<String>) bDto.getObj();
		return interventoErogazioneDao.getListaIntEsegStatoByTipiStato(obj, (Long) bDto.getObj2());
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
				sina.setCsIInterventoEsegMast(null);
				sinaDao.sinaSetNullMastId(sina.getId());
			}
			
		}
		
		
		if(master !=null){
			master.setDiarioPaiId(null);
			interventoErogazioneDao.interventoEsegMastSetNullDiarioPaiId(master.getId());
		}
	
	}
	
	@Override
	@AuditSaltaValidazioneSessionID
	public CsIInterventoEseg aggiungiInterventoEseguito(BaseDTO dto) {
		return interventoErogazioneDao.aggiungiInterventoEseguito((CsIInterventoEseg) dto.getObj());
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
				    
				    BaseDTO bdto=new BaseDTO();
					
					bdto.setObj(intEsegId);
					bdto.setObj2(intEsegMastId);
					bdto.setEnteId(dto.getEnteId());
					bdto.setSessionId(dto.getSessionId());
					
					List<CsIPsExport> exportList=psExportService.findCsIPsExportByCsIInterventoEseg(bdto);
					List<Long> idsExportMast=new ArrayList<Long>(); //id CSIPsExportMast relativi alle CsIPsExport cancellate
					
					for(CsIPsExport export : exportList){
						CsIPsExportMast exportMast= export.getCsIPsExportMast();
						if(!idsExportMast.contains(exportMast.getId())){
							idsExportMast.add(exportMast.getId());
						}
						
						BaseDTO cdto = new BaseDTO();
						if(exportMast!=null){
							//cancello csipsexport
							cdto.setObj(export.getCsIInterventoEseg().getId());
							cdto.setObj2(export.getCsIInterventoEsegMast().getId());
							cdto.setEnteId(dto.getEnteId());
							cdto.setSessionId(dto.getSessionId());
							psExportService.deleteCsIPsExportByIntEsegIdAndIntEsegMastId(cdto);
						}
					}
					//trovo tutte le mast delle export cancellate
					bdto.setObj(idsExportMast);
					List<CsIPsExportMast> lstMast= psExportService.findCsIPsExportMastByIds(bdto);
					for(CsIPsExportMast mast: lstMast){
						if(mast.getCsIPsExportList().isEmpty()){
							bdto.setObj(mast.getId());
							psExportService.deleteCsIPsExportMastById(bdto);
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
		
		BaseDTO dto = new BaseDTO();
		dto.setEnteId(bDto.getEnteId());
		dto.setUserId(bDto.getUserId());
		dto.setSessionId(bDto.getSessionId());
		
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
						dto.setObj(sb.getCf());
						CsASoggettoLAZY soggetto = this.soggettoSessionBean.getSoggettoByCF(dto);
						b.setCsASoggetto(soggetto);
					}
					beneficiari.add(b);
				}
			}else if(diarioId!=null){
				dto.setObj(diarioId);
				CsDDiario diario = null;
				
				try{ 
					diario = diarioSessionBean.findDiarioById(dto);
				}catch(Exception err){logger.error(err);}
				
				if(diario!=null){
					CsASoggettoLAZY soggetto = diario.getCsACaso().getCsASoggetto();
					dto.setObj(soggetto.getCsAAnagrafica().getId());
					CsAAnaIndirizzo residenza = indirizziService.getIndirizzoResidenza(dto);
					SoggettoErogazioneBean b = new SoggettoErogazioneBean(soggetto, residenza.getLabelIndirizzo(), this.getCasoComuneResidenza(residenza),  residenza.getStatoCod(), true);
					beneficiari.add(b);
				}
				
			}
			
			e.setBeneficiari(beneficiari);
		}
		
		return lstMaster;
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

	@Override
	public List<VGerrarchiaServizi> findAllNodesTipoIntervento(CeTBaseObject cet) {
		return interventoDao.findAllNodesTipoIntervento();
	}

	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	@Override
	public List<CsCTipoInterventoCustom> findTipiIntCustom(CeTBaseObject dto) {
		return interventoDao.findTipiIntCustom();
	}
	
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	@Override
	public List<it.webred.cs.csa.ejb.dto.rest.InterventoDTO> findTipiIntCustomConfigurazione(BaseDTO dto) {
		List<CsCTipoInterventoCustom> listTipoIntCustom = findTipiIntCustom(dto);
		List<it.webred.cs.csa.ejb.dto.rest.InterventoDTO> listInterventoDTO = new ArrayList<it.webred.cs.csa.ejb.dto.rest.InterventoDTO>();
		for(CsCTipoInterventoCustom c : listTipoIntCustom){
			it.webred.cs.csa.ejb.dto.rest.InterventoDTO interventoDTO = new it.webred.cs.csa.ejb.dto.rest.InterventoDTO();
			interventoDTO.setId(c.getId());
			interventoDTO.setDescrizione(c.getDescrizione());
			
			listInterventoDTO.add(interventoDTO);
		}
		return listInterventoDTO;
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
	public CsCTipoInterventoCustom findTipoInterventoCustomById(BaseDTO dto) {
		return interventoDao.findTipoIntCustomById((Long) dto.getObj());
	}

	@Override
	public CsCCategoriaSociale findCatSocialeByDescrizione(BaseDTO dto) {
		return interventoDao.findCatSocialeByDescrizione((String) dto.getObj());
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
	public List<VLineaFin> findAllOrigineFinanziamenti(BaseDTO dto) {		
		return interventoDao.getLineeFinanziamentoByEnte(dto.getEnteId());
	}

	@Override
	public List<VTipiInterventoUsati> findAllInterventiRecenti(BaseDTO dto) {
		return interventoDao.getInterventiRecentiList();
	}
	
	@Override
	public List<KeyValueDTO> findTipiInterventoRecenti(BaseDTO dto) {
		return interventoDao.getTipiInterventoRecentiList();
	}
	
	@Override
	public List<KeyValueDTO> findTipiInterventoCustomRecenti(BaseDTO dto) {
		return interventoDao.getTipiInterventoCustomRecentiList();
	}
	//SISO-1162
	@Override
	public List<KeyValueDTO> findTipiInterventoInps(BaseDTO dto) {
		return interventoDao.getTipiInterventoInpsList();
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
						BaseDTO bdto = new BaseDTO();
						bdto.setEnteId(dto.getEnteId());
						bdto.setSessionId(dto.getSessionId());
						bdto.setUserId(dto.getUserId());
						bdto.setObj(progetto.getId());
						//bdto.setObj2(codRoutingTitolare);
						mappaCampiFse = confSessionBean.loadCampiFse(bdto);
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
	

	//INIZIO MOD-RL
	@Override
	public List<ArRelClassememoPresInps> findArRelClassememoPresInpbyTipoInterventoId(BaseDTO dto) { 
		return interventoDao.findArRelClassememoPresInpbyTipoInterventoId((Long)dto.getObj());
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
	
	@Override
	public List<CsCfgAttrOption> findCsCfgAttrOptions(BaseDTO bdto){
		return interventoErogazioneDao.findCsCfgAttrOptions((Long)bdto.getObj());
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
    @AuditSaltaValidazioneSessionID
	public CsCfgAttrUnitaMisura findAttrUnitaMisura(BaseDTO dto) {
		return interventoDao.findAttrUnitaMisura((Long)dto.getObj(), (Long)dto.getObj2());
	}

	@Override
	public InformativaDTO findInformativa(BaseDTO dto) {
		return interventoDao.findInformativa((Long)dto.getObj());
	}

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
			pds.setGrpVulnerabilita(fse.getCsTbGrVulnerabile()!=null ? fse.getCsTbGrVulnerabile().getId() : null);
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
     
    //SISO-1110
     public List<VServiziCustom> findAreaTInterventoById(BaseDTO bdto){
    		return  interventoDao.findAreaTInterventoById((Long)bdto.getObj());  
     }
     public List<VServiziCustom> findAllServiziCustoByInterventoAndAreatId(BaseDTO bdto){
    	 return  interventoDao.findAllServiziCustoByInterventoAndAreatId((Long)bdto.getObj(), (Integer)bdto.getObj2()); 
    	 
     }
     public List<ArRelIntCustomPresInps> findArRelIntCustomPresInpbyTipoInterventoId (BaseDTO dto) { 
 		return interventoDao.findArRelIntCustomPresInpbyTipoInterventoId((Long)dto.getObj());
 	 } 
     @SuppressWarnings("unchecked")
	public List<VServiziCustom> findDettaglioInterventobyAreaTId(BaseDTO bdto){
    	 List<String> areeId = (List<String>) bdto.getObj();
    	 return  interventoDao.findDettaglioInterventobyAreaTId(areeId); 
    	 
     }
     //DA CANCELLARE
     public List<ArRelIntCustomIstat> findInterventoIstatByInterventoCustom (BaseDTO dto) { 
  		return interventoDao.findArRelIntCustomIstatbyTipoInterventoId((Long)dto.getObj());
  	 } 
     public List<ArTClasse> findInterventoIstatByCodice (BaseDTO dto) { 
  		return interventoDao.findArTClasseByTipoInterventoId((String)dto.getObj());
  	 } 
 	public List<VServiziCustom> findAreaTInterventoByIdeAreaTSoggetto(BaseDTO bdto){
    	 Long interventoCustomId = (Long)bdto.getObj();
     	 List<String> areeT = (List<String>) bdto.getObj2();
     	 return  interventoDao.findAreaTInterventoByIdeAreaTSoggetto(interventoCustomId,areeT); 
     	 
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
   
    //SISO-469
    @Override
    public List<VArCTariffa> findTariffe(BaseDTO dto){
    	 String codRouting = (String)dto.getObj();
    	 Long unitaMisuraId = (Long)dto.getObj2();
    	
    	return  interventoDao.findTariffe(codRouting, unitaMisuraId);
      }

	@Override
	public List<ErogazioneDettaglioSintesiDTO> getSintesiErogazioniByInterventoId(BaseDTO dto){
		Long masterId = (Long)dto.getObj();
		return interventoErogazioneDao.getSintesiErogazioniByInterventoId(masterId);
	}
}