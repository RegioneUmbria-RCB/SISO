package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.InterventoDAO;
import it.webred.cs.csa.ejb.dao.InterventoErogazioneDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.ArRelClassememoPresInps;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgAttrOption;
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
import it.webred.cs.data.model.CsIPasti;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIResiAdulti;
import it.webred.cs.data.model.CsIResiMinore;
import it.webred.cs.data.model.CsIRigaQuota;
import it.webred.cs.data.model.CsISchedaLavoro;
import it.webred.cs.data.model.CsISemiResiMin;
import it.webred.cs.data.model.CsIVouchersad;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelRelazioneTipoint;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsRelSettCsocTipoInterPK;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.data.model.VGerrarchiaServizi;
import it.webred.cs.data.model.VLineaFin;
import it.webred.cs.data.model.VTipiInterventoUsati;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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

	protected SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

	
	@Override
	public CsIIntervento salvaIntervento(BaseDTO dto) throws Exception {

		CsIIntervento inter = (CsIIntervento) dto.getObj();

		if (inter.getId() == null) {
			inter.setUserIns(dto.getUserId());
			inter.setDtIns(new Date());
			inter.setUsrMod(null);
			inter.setDtMod(null);

			if (inter.getCsIContrEco() != null && !inter.getCsIContrEco().isEmpty()) {
				inter.getCsIContrEco().iterator().next().setCsIIntervento(inter);
			}

			if (inter.getCsICentrod() != null && !inter.getCsICentrod().isEmpty()) {
				inter.getCsICentrod().iterator().next().setCsIIntervento(inter);
			}

			if (inter.getCsIPasti() != null && !inter.getCsIPasti().isEmpty()) {
				inter.getCsIPasti().iterator().next().setCsIIntervento(inter);
			}

			if (inter.getCsIBuonoSoc() != null && !inter.getCsIBuonoSoc().isEmpty()) {
				inter.getCsIBuonoSoc().iterator().next().setCsIIntervento(inter);
			}

			if (inter.getCsIResiMinore() != null && !inter.getCsIResiMinore().isEmpty()) {
				inter.getCsIResiMinore().iterator().next().setCsIIntervento(inter);
			}

			if (inter.getCsIVouchersad() != null && !inter.getCsIVouchersad().isEmpty()) {
				inter.getCsIVouchersad().iterator().next().setCsIIntervento(inter);
			}

			if (inter.getCsIResiAdulti() != null && !inter.getCsIResiAdulti().isEmpty()) {
				inter.getCsIResiAdulti().iterator().next().setCsIIntervento(inter);
			}

			if (inter.getCsIAdmAdh() != null && !inter.getCsIAdmAdh().isEmpty()) {
				inter.getCsIAdmAdh().iterator().next().setCsIIntervento(inter);
			}

			if (inter.getCsIAffidoFam() != null && !inter.getCsIAffidoFam().isEmpty()) {
				inter.getCsIAffidoFam().iterator().next().setCsIIntervento(inter);
			}

			if (inter.getCsISemiResiMin() != null && !inter.getCsISemiResiMin().isEmpty()) {
				inter.getCsISemiResiMin().iterator().next().setCsIIntervento(inter);
			}

			if (inter.getCsISchedaLavoro() != null && !inter.getCsISchedaLavoro().isEmpty()) {
				inter.getCsISchedaLavoro().iterator().next().setCsIIntervento(inter);
			}

			interventoDao.saveIntervento(inter);
		} else {
			inter.setUsrMod(dto.getUserId());
			inter.setDtMod(new Date());
			interventoDao.updateIntervento(inter);
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
	public List<CsCTipoIntervento> findAllTipiIntervento(BaseDTO dto) {
		return interventoDao.findAllTipiIntervento();
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
	public CsCTipoIntervento getTipoInterventoById(BaseDTO dto) throws Exception {
		return interventoDao.getTipoInterventoById(Long.parseLong((String) dto.getObj()));
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

	@Override
	public CsCfgIntEseg findConfigIntErogByTipoIntervento(BaseDTO bDto) {
		return interventoErogazioneDao.findCfgIntEsegByTipoInterventoId((Long) bDto.getObj());
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
	public List<CsIInterventoEseg> getInterventoEsegByIntervento(BaseDTO bDto) {

		return interventoErogazioneDao.getInterventoEsegByIntervento((Long) bDto.getObj());
	}
	
	@Override
	public List<CsIInterventoEseg> getInterventoEsegByMasterId(BaseDTO bDto) {
		return interventoErogazioneDao.getInterventoEsegByIdMaster((Long)bDto.getObj());
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
		List<CsIInterventoEseg> lstEsegs = interventoErogazioneDao.getInterventoEsegByIdMaster(masterId);
		BaseDTO e = new BaseDTO();
		e.setEnteId(dto.getEnteId());
		e.setUserId(dto.getUserId());
		e.setObj(lstEsegs);
		eliminaInterventoEsegStorico(e);
		
		CsIInterventoEsegMast master = interventoErogazioneDao.getErogazioneMasterById(masterId);
		
		interventoErogazioneDao.rimuoviInterventoEseguitoMast(masterId);

		if(master.getCsIQuota()!=null && master.getCsIIntervento()==null)
			interventoErogazioneDao.rimuoviQuota(master.getCsIQuota().getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void aggiungiInterventoEseguito(BaseDTO dto) {
		interventoErogazioneDao.aggiungiInterventoEseguito((List<CsIInterventoEseg>) dto.getObj());
	}

	@Override
	public void aggiungiInterventoEseguitoMast(BaseDTO dto) {
		interventoErogazioneDao.aggiungiInterventoEseguitoMast((CsIInterventoEsegMast) dto.getObj());
	}
	
	@Override
	public CsCTipoIntervento findTipiInterventoById(BaseDTO dto) {
		return interventoDao.findTipiInterventoById((Long) dto.getObj());
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
			
			quota.getCsIQuotaRipartiz().setUsrMod(user);
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
	public ArTbPrestazioniInps findArTbPrestazioniInpsByCodice(BaseDTO dto) { 
		return interventoDao.findArTbPrestazioniInpsByCodice((String)dto.getObj());
	} 
	//FINE MOD-RL
 
	

	@Override
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public List<CsIInterventoEsegMastSogg> findSoggettiErogazioneSenzaCaso(BaseDTO dto) {
		return interventoErogazioneDao.findMastSoggSenzaCaso();
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
}
