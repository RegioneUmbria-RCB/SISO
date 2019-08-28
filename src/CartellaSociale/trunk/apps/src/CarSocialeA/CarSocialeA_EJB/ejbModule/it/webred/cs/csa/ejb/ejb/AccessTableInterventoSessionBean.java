package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiSiruSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.InterventoDAO;
import it.webred.cs.csa.ejb.dao.InterventoErogazioneDAO;
import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.InformativaDTO;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.SiruDominioDTO;
import it.webred.cs.csa.ejb.dto.SiruResultDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrUtilsBean;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.csa.ejb.enumeratori.SiruEnum;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.ArFfProgettoAttivita;
import it.webred.cs.data.model.ArRelClassememoPresInps;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgAttrOption;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEseg;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDPai;
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
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIResiAdulti;
import it.webred.cs.data.model.CsIResiMinore;
import it.webred.cs.data.model.CsIRigaQuota;
import it.webred.cs.data.model.CsISchedaLavoro;
import it.webred.cs.data.model.CsISemiResiMin;
import it.webred.cs.data.model.CsIVouchersad;
import it.webred.cs.data.model.CsRelRelazioneTipoint;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsRelSettCsocTipoInterPK;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.data.model.VGerrarchiaServizi;
import it.webred.cs.data.model.VLineaFin;
import it.webred.cs.data.model.VTipiInterventoUsati;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.ExcludeDefaultInterceptors;

import org.apache.commons.lang.StringUtils;
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
	@EJB
	public AccessTableOperatoreSessionBeanRemote operatoreSessionBean;
	@EJB
	public AccessTableDiarioSessionBeanRemote diarioSessionBean;
	@EJB
	public AccessTableCasoSessionBeanRemote casoSessionBean;
	@EJB
	public AccessTableConfigurazioneSessionBeanRemote confSessionBean;
	@EJB
	public AccessTableSoggettoSessionBeanRemote soggettoSessionBean;
	@EJB
	private AccessTableAlertSessionBeanRemote alertService;
	@EJB
	private AccessTableDominiSiruSessionBeanRemote dominiSiruService;
	

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
		bdto.setEnteId(dto.getEnteId());
		bdto.setUserId(dto.getUserId());
		bdto.setSessionId(dto.getSessionId());

		if (inter.getDiarioId() == null) {

			if (inter.getCsDDiario().getId() <= 0) {
				IterDTO iter = new IterDTO();

				iter.setIdCaso(dto.getCasoId());
				iter.setUserId(dto.getUserId());
				iter.setEnteId(dto.getEnteId());
				iter.setSessionId(dto.getSessionId());
				CsACaso caso = casoSessionBean.findCasoById(iter);

				inter.getCsDDiario().setCsACaso(caso);
				inter.getCsDDiario().setDtIns(oggi);
				inter.getCsDDiario().setDtMod(null);
				inter.getCsDDiario().setUserIns(dto.getUserId());
				inter.getCsDDiario().setUsrMod(null);
				inter.getCsDDiario().setCsTbTipoDiario(tipo);

				//Recupero il diario associato alla relazione da inserire
				if (dto.getIdRelazione() != null) {
					bdto.setObj(dto.getIdRelazione());
					CsDDiario relazione = diarioSessionBean.findDiarioById(bdto);
					inter.getCsDDiario().addCsDDiariFiglio(relazione);
				}

				bdto.setObj(inter.getCsDDiario());
				CsDDiario diario = diarioSessionBean.createDiario(bdto);
				inter.setDiarioId(diario.getId());
				inter.setCsDDiario(diario);
			}

			interventoDao.saveFglIntervento(inter);

		} else {

			List<CsDDiario> lstd = new LinkedList<CsDDiario>(inter.getCsDDiario().getCsDDiariFiglio());
			CsDDiario relazione = null;
			if (lstd != null && lstd.size() > 0) {
				for (CsDDiario d : lstd) {
					//La rimuovo e riaggiungo
					if (d.getCsTbTipoDiario().getId().equals(DataModelCostanti.TipoDiario.RELAZIONE_ID)) {
						relazione = d;
					}
				}
				if (relazione != null)
					inter.getCsDDiario().getCsDDiariFiglio().remove(relazione);
			}

			//Recupero il diario associato alla relazione da inserire
			if (dto.getIdRelazione() != null) {
				bdto.setObj(dto.getIdRelazione());
				CsDDiario nrelazione = diarioSessionBean.findDiarioById(bdto);
				inter.getCsDDiario().addCsDDiariFiglio(nrelazione);
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
	public List<CsCTipoIntervento> findAllTipiIntervento(CeTBaseObject dto) {
		return interventoDao.findTipiIntervento(false);
	}
	
	@Override
	public List<CsCTipoIntervento> findTipiInterventoAbilitati(CeTBaseObject dto) {
		return interventoDao.findTipiIntervento(true);
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

	@Override
	public List<CsIIntervento> getListaInterventiByCaso(BaseDTO dto) throws Exception {
		return interventoDao.getListaInterventiByIdCaso((Long) dto.getObj());
	}
	
	@Override
	public List<InterventoBaseDTO> getListaInfoInterventiByCaso(BaseDTO dto){
		List<InterventoBaseDTO> lstInt = new ArrayList<InterventoBaseDTO>();
		List<CsIIntervento> lst = interventoDao.getListaInterventiByIdCaso((Long) dto.getObj());
		for(CsIIntervento inter : lst){
			Collection<CsIInterventoEseg> esegs = inter.getCsIInterventoEsegMast()!= null ? inter.getCsIInterventoEsegMast().getCsIInterventoEsegs() : new ArrayList<CsIInterventoEseg>();
			boolean eseguito=false;
			int i=0;
			Iterator<CsIInterventoEseg> it = esegs.iterator();
			while(it.hasNext() && !eseguito){
				CsIInterventoEseg es = (CsIInterventoEseg)it.next();
				if(es.getStato().getTipo().equals(DataModelCostanti.TipoStatoErogazione.EROGATIVO))
					eseguito = true;
				i++;
			}
			
			CsCTipoIntervento tipoIntervento = inter.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento();
			CsCTipoInterventoCustom tipoInterventoC = inter.getCsIInterventoCustom();
			
			String inizio = inter.getInizioDal()!=null ? "Dal "+ ddMMyyyy.format(inter.getInizioDal()) : "";
			inizio+= inter.getInizioAl()!=null ? " al "+ ddMMyyyy.format(inter.getInizioAl()) : "";
			
			String fine = inter.getFineDal()!=null ? "Dal "+ ddMMyyyy.format(inter.getFineDal()) : "";
			fine+= inter.getFineAl()!=null ? " al "+ ddMMyyyy.format(inter.getFineAl()) : "";
			
			
			InterventoBaseDTO intout = new InterventoBaseDTO();
			intout.setTipoIntervento(tipoIntervento);
			intout.setTipoInterventoCustom(tipoInterventoC);
			intout.setInizio(inizio);
			intout.setFine(fine);
			intout.setErogato(eseguito);
			
			lstInt.add(intout);
		}
		return lstInt;
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

/*	@Override
	public CsCfgIntEseg findConfigIntErogByTipoIntervento(BaseDTO bDto) {
		return interventoErogazioneDao.findCfgIntEsegByTipoInterventoId((Long) bDto.getObj());
	}*/
	
	public HashMap<Long, ErogStatoCfgDTO> findConfigIntEsegByTipoIntervento(BaseDTO bdto){
		Long tipoInterventoId = (Long) bdto.getObj();
		CsCfgIntEseg csCfgIntEseg = interventoErogazioneDao.findCfgIntEsegByTipoInterventoId(tipoInterventoId);
		HashMap<Long, ErogStatoCfgDTO> mappaEseg = new HashMap<Long, ErogStatoCfgDTO> ();
		if(csCfgIntEseg!=null)
			mappaEseg = IntEsegAttrUtilsBean.getMappaStatoAttributi(csCfgIntEseg.getCsCfgIntEsegStatoInt());
		else
			logger.warn("Lista 'CsCfgIntEseg' vuota per il tipo intervento:"+tipoInterventoId);	
		return mappaEseg;
	}

	@Override
	public CsIInterventoEseg getErogazioniEseguiteHistory(BaseDTO bDto) {
		return interventoErogazioneDao.getErogazioniEseguiteHistory((Long) bDto.getObj());

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CsCfgIntEsegStato> getListaIntEsegStatoByTipiStato(BaseDTO bDto) {
		List<String> obj = (List<String>) bDto.getObj();
		return interventoErogazioneDao.getListaIntEsegStatoByTipiStato(obj, (Long) bDto.getObj2());
	}

	@Override
	public CsIInterventoEseg getInterventoEsegById(BaseDTO bDto) {
		return interventoErogazioneDao.getInterventoEsegById((Long) bDto.getObj());
	}
	
	@Override
	public List<CsIInterventoEseg> getInterventoEsegByMasterId(BaseDTO bDto) {
		boolean caricaDettagli = bDto.getObj2()!=null ? (Boolean) bDto.getObj2() : false;
		return interventoErogazioneDao.getInterventoEsegByIdMaster((Long)bDto.getObj(), caricaDettagli );
	}
	
	@Override
	public void rimuoviBeneficiariMaster(BaseDTO dto) {
		interventoErogazioneDao.rimuoviBeneficiari((Long)dto.getObj());
	}

	@Override
	public void rimuoviInterventoEseguitoMast(BaseDTO dto) {
		
		Long masterId = (Long)dto.getObj();
		
		//Rimuovo i soggetti di riferimento
		rimuoviBeneficiariMaster(dto);
		
		//Rimuovo interventi eseguiti
		List<CsIInterventoEseg> lstEsegs = interventoErogazioneDao.getInterventoEsegByIdMaster(masterId, true);
		BaseDTO e = new BaseDTO();
		e.setEnteId(dto.getEnteId());
		e.setUserId(dto.getUserId());
		e.setSessionId(dto.getSessionId()); //SISO - 884
		e.setObj(lstEsegs);
		eliminaInterventoEsegStorico(e);
	
		interventoErogazioneDao.eliminaCsIPsByEsegMaster(masterId);
		
		CsIInterventoEsegMast master = interventoErogazioneDao.getErogazioneMasterById(masterId);
		
		interventoErogazioneDao.rimuoviInterventoEseguitoMast(masterId);

		if(master.getCsIQuota()!=null && master.getCsIIntervento()==null)
			interventoErogazioneDao.rimuoviQuota(master.getCsIQuota().getId());
	}

	@SuppressWarnings("unchecked")
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
		interventoErogazioneDao.eliminaInterventoEsegStorico((List<CsIInterventoEseg>) dto.getObj());
	}

	@Override
	public List<ErogazioneMasterDTO> searchListaErogInterventiBySettore(ErogazioniSearchCriteria bDto) {
		
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
		
		List<ErogazioneMasterDTO> lstMaster = interventoErogazioneDao.searchListaErogInterventiBySettore(bDto);
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
					SoggettoErogazioneBean b = new SoggettoErogazioneBean(soggetto, true);
					beneficiari.add(b);
				}
				
			}
			
			e.setBeneficiari(beneficiari);
		}
		
		return lstMaster;
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
	public List<VGerrarchiaServizi> findAllNodesTipoIntervento(BaseDTO dto) {
		return interventoDao.findAllNodesTipoIntervento();
	}

	@Override
	public List<CsCTipoInterventoCustom> findTipiIntCustom(BaseDTO dto) {
		return interventoDao.findTipiIntCustom((Long) dto.getObj());
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
	public CsCTipoInterventoCustom findTipiInterventoCustomById(BaseDTO dto) {
		return interventoDao.findTipiInterventoCustomById((Long) dto.getObj());
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
	
	@Override
	public CsIQuota salvaQuota(BaseDTO dto) {
		CsIQuota quota = (CsIQuota) dto.getObj();
		
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
		SoggettoErogazioneBean seb = (SoggettoErogazioneBean) dto.getObj2();
		
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
			
			if(progetto.getCsIInterventoPrFseSiru()!=null)
				progetto.getCsIInterventoPrFseSiru().setCsIInterventoPr(progetto);
						
		}
		
		return interventoDao.updateCsIInterventoPr(progetto);
	}

	@Override
	public CsIInterventoEsegMast getErogazioneMasterById(BaseDTO dto) {
		return interventoErogazioneDao.getErogazioneMasterById((Long)dto.getObj());
	}
	
	@Override
	public void salvaRifInterventoToPai(BaseDTO dto) throws Exception
	{
		Long idIntervento = (Long) dto.getObj();
		CsDPai csDPaiNuovoRif = (CsDPai) dto.getObj2();
		Long csDPaiRifDaSostituireId = (Long) dto.getObj3();		
		CsIIntervento intervento= interventoDao.getInterventoById(idIntervento);
		
		if(csDPaiNuovoRif==null && //sostituzione 
				intervento.getCsDPai()!=null && csDPaiRifDaSostituireId!=null &&
				! intervento.getCsDPai().getDiarioId().equals(csDPaiRifDaSostituireId))
		{
			
			//skip
		}
		else
		{
			intervento.setCsDPai(csDPaiNuovoRif);
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
	public CsIInterventoEsegMast getCsIInterventoEsegMastByByInterventoId(BaseDTO bDto) { 
		return interventoErogazioneDao.getCsIInterventoEsegMastByByInterventoId((Long) bDto.getObj());
	}
	//fine SISO-500  

	@Override
	public CsFlgIntervento getPrimoFoglioAmministrativo(BaseDTO dto) {
		return interventoDao.getPrimoFoglioAmministrativo((Long) dto.getObj());
	}


	//INIZIO SISO-522 - modificato da SISO-575
	@Override
	public List<ArFfProgetto> findProgettiByBelfioreOrganizzazione(BaseDTO dto) {				
		return interventoDao.findProgettiByBelfioreOrganizzazione((String) dto.getObj());
	}
	//FINE SISO-522 - modificato da SISO-575

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
	public void gestisciAlertErogazioni(BaseDTO in) throws Exception {
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
			bdto.setObj2(master.getCsOOperatoreSettore().getCsOSettore().getCsOOrganizzazione().getId());
			bdto.setObj3(DataModelCostanti.TipiAlertCod.EROGAZIONE);
			alertService.addAlertResponsabileCaso(bdto);	
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
			
			String operatore = a.getOperatoreFrom().getCsOOperatoreAnagrafica().getCognome()+" "+a.getOperatoreFrom().getCsOOperatoreAnagrafica().getNome();
			
			a.setTitolo("Notifica nuova erogazione: "+beneficiari);
			a.setDescrizione("L'operatore "+operatore+" del settore "+a.getSettoreFrom().getNome()+" ha aggiunto una nuova erogazione per i beneficiari:"+beneficiari);
			
			alertService.addAlert(a);	
		}
		
	}

	//SISO-748
	@Override
	public void salvaRifErogazioneToPai(BaseDTO bdto) throws Exception 
	{
		Long idInterventoEsegMast = (Long) bdto.getObj();
		Long csDPaiRif = (Long) bdto.getObj2();
		CsIInterventoEsegMast interventoMast = interventoErogazioneDao.getErogazioneMasterById(idInterventoEsegMast);
		
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
	
	//INIZIO SISO - 790
	@Override
	public List<ArFfProgettoAttivita> findSottocorsi(BaseDTO dto) {				
		return interventoDao.getSottocorsi();
	}
	//FINE SISO-790

//	//SISO-817
	public  SiruResultDTO validaSiru(CsIInterventoPr progetto,SoggettoErogazioneBean seb){
		SiruResultDTO result = new SiruResultDTO();
		List<String> msg = new ArrayList<String>();
		CsIInterventoPrFse fse = progetto.getCsIInterventoPrFse();
		CsIInterventoPrFseSiru siru = new CsIInterventoPrFseSiru();
		//siru.setInterventoPrId(progetto.getId());
		//setup json handler
		ObjectMapper objectMapper = new ObjectMapper();
		//estrazione data di nascita, sesso, nazione/comune da CF
		Object[] cfExt = estraiDaFC(seb.getCodiceFiscale());
		siru.setSesso((Character)cfExt[0]);
		siru.setDataNascita((Date)cfExt[1]);
		String luogo = (String)cfExt[2];
		//titolo di studio
		if(progetto.getCsTbTitoloStudio() != null && progetto.getCsTbTitoloStudio().getId() != 0){ 
			SiruDominioDTO sd = dominiSiruService.findByDesc(SiruEnum.TITOLO_STUDIO.name(), String.valueOf(progetto.getCsTbTitoloStudio().getId()));
			if(sd==null) msg.add("Titolo di studio non presente nel SIRU");
			else siru.setTitoloStudio(sd.getCodice());
		}
		
		//condizione mercato ingresso
		if(progetto.getCsTbIngMercato() != null && progetto.getCsTbIngMercato().getId() != null &&
				!progetto.getCsTbIngMercato().getId().isEmpty()){ 
			SiruDominioDTO sd = dominiSiruService.findByDescStartsWith(SiruEnum.CONDIZIONE_MERCATO.name(), progetto.getCsTbIngMercato().getDescrizione());
			if(sd==null) msg.add("Condizione di ingresso nel mondo del lavoro non presente nel SIRU");
			else siru.setCondMercatoIngresso(sd.getCodice());
		}	
		
		//ateco
		if(fse.getAzCodAteco() != null && !fse.getAzCodAteco().isEmpty()){
			SiruDominioDTO sisoAteco = dominiSiruService.findById(SiruEnum.SISO_ATECO.name(), fse.getAzCodAteco());
			SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.ATECO.name(), sisoAteco.getCodice() + "_" + sisoAteco.getVersione());
			if(sd == null) msg.add("Ateco non presente nel SIRU");
			else siru.setCodAtecoAnno(sd.getCodice());
		}
		
		//dimensione azienda
		if(fse.getAzDescDimensioni() != null && !fse.getAzDescDimensioni().isEmpty()){
			SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.DIMENSIONE_AZIENDA.name(), fse.getAzDescDimensioni());
			if(sd == null) msg.add( "Dimensione azienda non presente nel SIRU");
			else siru.setDimensioneAziendaId(sd.getCodice());
		}
		
		//forma giuridica rna
		if(fse.getAzFormaGiuridica() != null && fse.getAzFormaGiuridica().getId()!= null ){
			SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.FORMA_GIURIDICA_RNA.name(), fse.getAzFormaGiuridica().getId());
			if(sd==null) msg.add( "Forma giuridica non presente nel SIRU");
			else siru.setFrmGiuridicaCod(sd.getCodice());
		}
		
		//gruppo vulnerabile
		if(fse.getCsTbGrVulnerabile() != null && fse.getCsTbGrVulnerabile().getId() != null){
			SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.GRUPPO_VUL_PART.name(), fse.getCsTbGrVulnerabile().getId());
			if(sd==null) msg.add("Gruppo vulnerabile non presente nel SIRU");
			else siru.setCodVulnerabilePa(sd.getCodice());
		}
		
		//tipo orario lavoro
		if(fse.getLavoroDescOrario() != null && !fse.getLavoroDescOrario().isEmpty()){
			SiruDominioDTO sd = dominiSiruService.findByDesc(SiruEnum.TIPO_ORARIO_LAVORO.name(), fse.getLavoroDescOrario());
			if(sd==null) msg.add("Orario di lavoro non presente nel SIRU");
			else siru.setTipoOrarioLavoroId(sd.getCodice());
		}
		
		//tipologia lavoro
		if(fse.getLavoroDescTipo() != null && !fse.getLavoroDescTipo().isEmpty()){ 
			SiruDominioDTO sd = dominiSiruService.findByDescStartsWith(SiruEnum.TIPOLOGIA_LAVORO.name(), fse.getLavoroDescTipo());
			if(sd==null) msg.add("Tipologia di lavoro non presente nel SIRU");
			else siru.setTipologiaLavoroId(sd.getCodice());
		}
		
		//durata ricerca
		if(fse.getLavoroDurataRicerca() != null && !fse.getLavoroDurataRicerca().isEmpty()){ 
			SiruDominioDTO sd = dominiSiruService.findByDesc(SiruEnum.DURATA_RICERCA.name(), fse.getLavoroDurataRicerca());
			if(sd==null) msg.add("Durata di ricerca non presente nel SIRU");
			else siru.setDurataRicerca(sd.getCodice());
		}
		
		//cittadinanza
		if(seb.getCittadinanza() != null && !seb.getCittadinanza().isEmpty()){
			SiruDominioDTO sd = dominiSiruService.findByDesc(SiruEnum.SISO_NAZIONE.name(), seb.getCittadinanza());
			//ITALIA da 1 a -> 0000
			if(sd != null && sd.getCodice().equals("1")){
				sd.setCodice("000");
			}
			sd = dominiSiruService.findById(SiruEnum.CITTADINANZA.name(), sd.getCodice());
			if(sd==null) msg.add("Nazione non presente nel SIRU");
			else siru.setCittadinanza(sd.getCodice());
		}
		
		//comune di nascita - stato di nascita
		SiruDominioDTO sdComune = dominiSiruService.findByDesc(SiruEnum.IT_COMUNE.name(), luogo);
		SiruDominioDTO sdNazione = dominiSiruService.findByDesc(SiruEnum.IT_NAZIONE.name(), luogo);
		
		if(sdComune != null){
			//ITALIANO
			siru.setComuneNascitaId(sdComune.getCodice());
		}
		if(sdNazione != null){
			//ESTERO
			siru.setNazioneNascitaId(sdNazione.getCodice());
		}
		
		if(sdComune== null && sdNazione==null) msg.add("Nazione o Comune del soggetto erogante non valorizzati");
		//comune azienda
		if(fse.getAzComuneCod() != null && !fse.getAzComuneCod().isEmpty()) msg.add("Comune azienda non presente nel SIRU");
		//if(fse.getAzComuneCod() != null){
		else{
			SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.LOCALIZZAZIONE_GEOG.name(), fse.getAzComuneCod());
			if(sd != null) siru.setComuneAziendaId(sd.getCodice());
		}
		
		//residenza
		if(fse.getFlagResDom() != null && !fse.getFlagResDom().isEmpty()){
		    SiruDominioDTO sd = dominiSiruService.findByDesc(SiruEnum.RESIDENZA.name(), fse.getFlagResDom());
			if(sd != null) siru.setFlagResidenza(new Integer(sd.getCodice()));
			else msg.add("Residenza non presente nel SIRU");
		}
		
		//comune residenza
		if(fse.getComuneResidenza() != null && !fse.getComuneResidenza().isEmpty()){
		    JsonNode node;
			try {
				node = objectMapper.readValue(fse.getComuneResidenza(), JsonNode.class);
				if(!node.isNull()){
					SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.LOCALIZZAZIONE_GEOG.name(), node.get("codIstatComune").asText());
					if(sd != null) siru.setComuneResId(sd.getCodice());	
				    else msg.add("Comune residenza non presente nel SIRU");
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
		
		//comune domicilio
		if(fse.getComuneDomicilio() != null && !fse.getComuneDomicilio().isEmpty()) {
		//if(fse.getComuneDomicilio() != null){
		//else{
			JsonNode node;
			try {
				node = objectMapper.readValue(fse.getComuneDomicilio(), JsonNode.class);
				if(!node.isNull()){
					SiruDominioDTO sd = dominiSiruService.findById(SiruEnum.LOCALIZZAZIONE_GEOG.name(), node.get("codIstatComune").asText());
					if(sd != null) siru.setComuneDomId(sd.getCodice());
					else msg.add("Comune domicilio non presente nel SIRU");
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
		result.setErrori(msg);
		result.setSiru(siru);
		
		return result;
	}
	private Object[] estraiDaFC(String fc) {
		Object[] toReturn = new Object[3];
		Character sex = new Character('M');
		
		//luogo
		int sizeCf = fc.length();
		String luogo = fc.substring(sizeCf-5,sizeCf-1);

		// day
		String day = fc.substring(9, 11);
		int numDay = Integer.parseInt(day);
		if (numDay > 31) {
			sex = new Character('F');
			numDay -= 40;
		}
		// month
		int mm = 0;
		char m = fc.substring(8, 9).toLowerCase().charAt(0);
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
		String yy = fc.substring(6, 8);
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

}