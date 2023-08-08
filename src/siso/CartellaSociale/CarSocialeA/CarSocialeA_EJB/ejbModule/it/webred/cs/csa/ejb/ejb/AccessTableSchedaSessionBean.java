package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.DatiPorDAO;
import it.webred.cs.csa.ejb.dao.DiarioDAO;
import it.webred.cs.csa.ejb.dao.IndirizzoDAO;
import it.webred.cs.csa.ejb.dao.ParentiDAO;
import it.webred.cs.csa.ejb.dao.SchedaDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.RisorsaFamDTO;
import it.webred.cs.csa.ejb.dto.cartella.DatiSocialiSintesiDTO;
import it.webred.cs.csa.ejb.dto.cartella.RisorsaCalcDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsADatiInvalidita;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsADisabilita;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.data.model.CsAInvCiv;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsATribunale;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsExtraFseDatiLavoro;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbSettoreImpiego;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableSchedaSessionBean extends CarSocialeBaseSessionBean implements AccessTableSchedaSessionBeanRemote {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SchedaDAO schedaDao;
	
	@Autowired
	private ParentiDAO parentiDAO;
	
	@Autowired 
	SoggettoDAO soggettoDao;
	
	@Autowired
	DatiPorDAO porDao;
	
	@Autowired
	ConfigurazioneDAO configurazioneDao;
	
	@Autowired
	private IndirizzoDAO indirizzoDao;
	
	@Autowired
	private DiarioDAO diarioDao;
	
	@EJB
	private AccessTableAlertSessionBeanRemote alertService;

	@Override
	public void saveCsA(BaseDTO dto) {
		Object obj = dto.getObj();
		if(obj instanceof CsADisabilita)
			saveDisabilita(dto);
		if(obj instanceof CsADatiSociali)
			saveDatiSociali(dto);
		if(obj instanceof CsADatiInvalidita)
			saveDatiInvalidita(dto);
		if(obj instanceof CsATribunale)
			saveTribunale(dto);
		if(obj instanceof CsAFamigliaGruppo)
			saveFamigliaGruppo(dto);
	}

	@Override
	public void updateCsA(BaseDTO dto) {
		Object obj = dto.getObj();
		if(obj instanceof CsADisabilita)
			updateDisabilita(dto);
		if(obj instanceof CsADatiSociali)
			updateDatiSociali(dto);
		if(obj instanceof CsADatiInvalidita)
			updateDatiInvalidita(dto);
		if(obj instanceof CsATribunale)
			updateTribunale(dto);
		if(obj instanceof CsAFamigliaGruppo)
			updateFamigliaGruppo(dto);
	}

	@Override
	public List<?> findCsBySoggettoId(BaseDTO dto) {
		Long anagraficaId = (Long)dto.getObj();
		Object obj = dto.getObj2();
		if(obj instanceof CsADisabilita)
			return schedaDao.findDisabilitaBySoggettoId(anagraficaId);
		if(obj instanceof CsADatiSociali)
			return schedaDao.findDatiSocialiBySoggettoId(anagraficaId);
		if(obj instanceof CsADatiInvalidita)
			return schedaDao.findDatiInvaliditaBySoggettoId(anagraficaId, null);
		if(obj instanceof CsATribunale)
			return schedaDao.findTribunaleBySoggettoId(anagraficaId);
		if(obj instanceof CsAFamigliaGruppo)
			return schedaDao.findFamigliaGruppoBySoggettoId(anagraficaId);

		return null;
	}

	@Override
	public void eliminaCsById(BaseDTO dto) {
		Object obj = dto.getObj2();
		if(obj instanceof CsADisabilita)
			eliminaDisabilita(dto);
		if(obj instanceof CsADatiSociali)
			eliminaDatiSociali(dto);
		if(obj instanceof CsADatiInvalidita)
			eliminaDatiInvalidita(dto);
		if(obj instanceof CsATribunale)
			eliminaTribunale(dto);
		if(obj instanceof CsAFamigliaGruppo)
			eliminaFamigliaGruppo(dto);
	}
	
	@Override
	public CsADisabilita getDisabilitaById(BaseDTO dto) {		
		return schedaDao.getDisabilitaById((Long) dto.getObj());
	}

	private void saveDisabilita(BaseDTO dto) {
		CsADisabilita cs = (CsADisabilita) dto.getObj();
		CsASoggettoLAZY soggetto = soggettoDao.getSoggettoById(cs.getCsASoggetto().getAnagraficaId());
		cs.setCsASoggetto(soggetto);
		cs.setDtIns(new Date());
		cs.setUserIns(dto.getUserId());
		cs.setDataInizioSys(new Date());
		schedaDao.saveDisabilita(cs);
	}

	private void updateDisabilita(BaseDTO dto) {
		CsADisabilita cs = (CsADisabilita) dto.getObj();
		cs.setUsrMod(dto.getUserId());
		cs.setDtMod(new Date());
		schedaDao.updateDisabilita(cs);
	}

	private void eliminaDisabilita(BaseDTO dto) {
		schedaDao.eliminaDisabilita((Long) dto.getObj());
	}

	@Override
	public CsADatiSociali getDatiSocialiById(BaseDTO dto) {
		return schedaDao.getDatiSocialiById((Long) dto.getObj());
	}

	private Boolean existsDatiSocialiAttiviBySoggettoCf(String cf) {		
		List<CsADatiSociali> lst =  schedaDao.findDatiSocialiBySoggettoCf(cf);
		boolean esiste = false;
		if(lst!=null && !lst.isEmpty()){
			for(CsADatiSociali ds : lst){
				if(!ds.getDataFineApp().before(new Date()))
					esiste = true;
			}
		}
		return esiste;
	}

	@Override
	public DatiSocialiSintesiDTO findDatiSocialiAttiviBySoggettoCf(BaseDTO in) {		
		CsADatiSociali datiSociali = schedaDao.findDatiSocialiAttiviBySoggettoCf((String) in.getObj());
		DatiSocialiSintesiDTO dto = null;
		if(datiSociali!=null){
			dto = new DatiSocialiSintesiDTO();
			
			CsASoggettoLAZY s = datiSociali.getCsASoggetto();
			CsAAnagrafica ana = s.getCsAAnagrafica();
			
			dto.setCel(ana.getCell());
			dto.setTel(ana.getTel());
			dto.setEmail(ana.getEmail());
			
			CsAAnaIndirizzo domicilio = indirizzoDao.getDomicilioBySoggetto(ana.getId());
			if(domicilio!=null){
				dto.setIndirizzoDomicilio(domicilio.getLabelIndirizzo());
				dto.setComuneDomicilioCod(domicilio.getComCod());
				dto.setComuneDomicilioDes(domicilio.getComDes());
				dto.setProvDomicilioCod(domicilio.getProv());
			}
			
			dto.setIdDatiSociali(datiSociali.getId());
			dto.setFamiliariConviventi(datiSociali.getFamiliariConviventi());
			
			if(datiSociali!=null && datiSociali.getCondLavorativaId()!=null){
				CsTbCondLavoro lavoro = configurazioneDao.getCondLavoro(datiSociali.getCondLavorativaId().longValue());
				dto.setCondLavoro(lavoro);
			}else{
				CsTbCondLavoro lavoro = configurazioneDao.getCondLavoroByDescrizione(DataModelCostanti.NON_RILEVATO);
				dto.setCondLavoroDefault(lavoro);
			}
			
			if(datiSociali!=null && datiSociali.getTitoloStudioId()!=null){
				CsTbTitoloStudio titoloStudio = configurazioneDao.getTitoloStudioById(datiSociali.getTitoloStudioId().longValue());
				dto.setTitoloStudio(titoloStudio);
			}else{
				CsTbTitoloStudio titoloStudio  = configurazioneDao.getTitoloStudioByDescrizione(DataModelCostanti.NON_RILEVATO);
				dto.setTitoloStudioDefault(titoloStudio);
			}
			
			if(datiSociali!=null && datiSociali.getSettImpiegoId()!=null){
				CsTbSettoreImpiego settImpiego = configurazioneDao.getSettoreImpiegoById(datiSociali.getSettImpiegoId().longValue());
				dto.setSettImpiego(settImpiego);
			}
		}
		
		return dto;
	}

	private void saveDatiSociali(BaseDTO dto) {
		CsADatiSociali cs = (CsADatiSociali) dto.getObj();
		CsASoggettoLAZY soggetto = soggettoDao.getSoggettoById(cs.getCsASoggetto().getAnagraficaId());
		cs.setCsASoggetto(soggetto);
		cs.setDtIns(new Date());
		cs.setUserIns(dto.getUserId());
		cs.setDataInizioSys(new Date());
		CsExtraFseDatiLavoro dl = porDao.fillDatiLavoroPor(cs.getDatiFse() , dto.getUserId());
		cs.setDatiFse(dl);
		schedaDao.saveDatiSociali(cs);
	}
	
	private void updateDatiSociali(BaseDTO dto) {
		CsADatiSociali cs = (CsADatiSociali) dto.getObj();
		cs.setUsrMod(dto.getUserId());
		cs.setDtMod(new Date());
		CsExtraFseDatiLavoro dl = porDao.fillDatiLavoroPor(cs.getDatiFse(), dto.getUserId());
		cs.setDatiFse(dl);
		schedaDao.updateDatiSociali(cs);
	}

	private void eliminaDatiSociali(BaseDTO dto) {
		Long id = (Long) dto.getObj();
		schedaDao.eliminaProtezioneGiuridica(id);
		schedaDao.eliminaDatiSociali(id);
	}

	@Override
	public CsADatiInvalidita getDatiInvaliditaById(BaseDTO dto) {		
		return schedaDao.getDatiInvaliditaById((Long) dto.getObj());
	}

	private void saveDatiInvalidita(BaseDTO dto) {
		CsADatiInvalidita cs = (CsADatiInvalidita) dto.getObj();
		CsASoggettoLAZY soggetto = soggettoDao.getSoggettoById(cs.getCsASoggetto().getAnagraficaId());
		cs.setCsASoggetto(soggetto);
		cs.setDtIns(new Date());
		cs.setUserIns(dto.getUserId());
		cs.setDataInizioSys(new Date());
		schedaDao.saveDatiInvalidita(cs);
	}

	private void updateDatiInvalidita(BaseDTO dto) {
		CsADatiInvalidita cs = (CsADatiInvalidita) dto.getObj();
		cs.setUsrMod(dto.getUserId());
		cs.setDtMod(new Date());
		schedaDao.updateDatiInvalidita(cs);
	}

	private void eliminaDatiInvalidita(BaseDTO dto) {
		schedaDao.eliminaDatiInvalidita((Long) dto.getObj());
	}

	@Override
	public CsATribunale getTribunaleById(BaseDTO dto) {		
		return schedaDao.getTribunaleById((Long) dto.getObj());
	}

	private void saveTribunale(BaseDTO dto) {
		CsATribunale cs = (CsATribunale) dto.getObj();
		CsASoggettoLAZY soggetto = soggettoDao.getSoggettoById(cs.getCsASoggetto().getAnagraficaId());
		cs.setCsASoggetto(soggetto);
		cs.setDtIns(new Date());
		cs.setUserIns(dto.getUserId());
		cs.setDataInizioSys(new Date());
		schedaDao.saveTribunale(cs);
	}

	private void updateTribunale(BaseDTO dto) {
		CsATribunale cs = (CsATribunale) dto.getObj();
		cs.setUsrMod(dto.getUserId());
		cs.setDtMod(new Date());
		schedaDao.updateTribunale(cs);
	}

	private void eliminaTribunale(BaseDTO dto) {
		schedaDao.eliminaTribunale((Long) dto.getObj());
	}

	@Override
	public CsAFamigliaGruppo getFamigliaGruppoById(BaseDTO dto) {		
		return schedaDao.getFamigliaGruppoById((Long) dto.getObj());
	}

	@Override
	public List<CsAComponente> findComponentiFamigliaAllaDataBySoggettoId(BaseDTO dto){
		return schedaDao.findComponentiFamigliaAllaDataBySoggettoId((Long)dto.getObj(), (Date)dto.getObj2());
	}

	private void saveFamigliaGruppo(BaseDTO dto) {
		CsAFamigliaGruppo cs = (CsAFamigliaGruppo) dto.getObj();
		Set<CsAComponente> listaComp = cs.getCsAComponentes();
		cs.setCsAComponentes(null);
		CsASoggettoLAZY soggetto = soggettoDao.getSoggettoById(cs.getCsASoggetto().getAnagraficaId());
		cs.setCsASoggetto(soggetto);
		cs.setDtIns(new Date());
		cs.setUserIns(dto.getUserId());
		cs.setDataInizioSys(new Date());
		
		if(listaComp != null) {
			Long idFamigliaGruppo = schedaDao.saveFamigliaGruppo(cs);
			cs.setId(idFamigliaGruppo);
			for(CsAComponente comp: listaComp) {
				
				comp.setDtIns(new Date());
				comp.setUserIns(dto.getUserId());
				comp.setCsAFamigliaGruppo(cs);

				schedaDao.saveComponente(comp);
			}
		}
	}

	private void updateFamigliaGruppo(BaseDTO dto) {
		try{
		CsAFamigliaGruppo cs = (CsAFamigliaGruppo) dto.getObj();
		Set<CsAComponente> listaComp = cs.getCsAComponentes();
		cs.setCsAComponentes(null);
		cs.setUsrMod(dto.getUserId());
		cs.setDtMod(new Date());
		
		//schedaDao.eliminaComponenteByFamigliaId(cs.getId());
		List<Long> listaId = new ArrayList<Long>();
		
		cs.setDtMod(new Date());
		cs.setUsrMod(dto.getUserId());
		schedaDao.updateFamigliaGruppo(cs);
		
		for(CsAComponente comp: listaComp) {
			comp.setCsAFamigliaGruppo(cs);
			if(comp.getId() == null) {
				//nuovo
				comp.setDtIns(new Date());
				comp.setUserIns(dto.getUserId());
			} else {
				//update
				comp.setDtMod(new Date());
				comp.setUsrMod(dto.getUserId());
			}
			
			schedaDao.saveComponente(comp);
			
			listaId.add(comp.getId());
		}
		// elimino i componenti non presenti nel nuovo salvataggio
		schedaDao.eliminaComponenteNotInByFamigliaGruppo(cs.getId(), listaId);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
	}
	
	private void eliminaFamigliaGruppo(BaseDTO dto) {
		schedaDao.eliminaComponenteByFamigliaId((Long) dto.getObj());
		schedaDao.eliminaFamigliaGruppo((Long) dto.getObj());
	}

	/**Restituisce i familiari di un soggetto individuati in tutte le 
	 * cartelle in cui è presente sia come titolare che come familiare 
	 * Specificando la data viene cercato solo nelle famiglie attive alla data di riferimento */
	@Override
	public List<RisorsaCalcDTO> findComponentiGiaFamigliariBySoggettoCf(BaseDTO dto) {
		String cfSoggetto = (String)dto.getObj();
		Date dataDa = dto.getObj2()!=null ? (Date) dto.getObj2() : null;
		Date dataA = dto.getObj3()!=null ? (Date) dto.getObj3() : null;
		List<RisorsaCalcDTO> lst = schedaDao.findComponentiGiaFamigliariBySoggettoCf(cfSoggetto, dataDa, dataA);
		
		//Verifico se per ciascun soggetto esiste una cartella sociale associata
		for (RisorsaCalcDTO a : lst) {
			if (!StringUtils.isBlank(a.getCf())) {
				CsASoggettoLAZY soggetto = soggettoDao.getSoggettoByCF(a.getCf());
				if (soggetto != null) {
					a.setAnagraficaId(soggetto.getAnagraficaId());
				}
			}
		}
		
		return lst;
	}

	@Override
	public Hashtable<String,RisorsaFamDTO> findRisorseFamiliariBySoggettoCf(BaseDTO dto) {
		Hashtable<String,RisorsaFamDTO> mappaSoggetti =new Hashtable<String, RisorsaFamDTO>();
		String cfSoggetto = (String)dto.getObj();
		List<RisorsaCalcDTO> lst = findComponentiGiaFamigliariBySoggettoCf(dto);
        
		for(RisorsaCalcDTO a : lst){
			if(!mappaSoggetti.containsKey(a.getCf()) && !cfSoggetto.equalsIgnoreCase(a.getCf())){
				RisorsaFamDTO r = new RisorsaFamDTO();
				r.setCognome(a.getCognome());
				r.setNome(a.getNome());
				r.setCf(a.getCf());

				CsASoggettoLAZY s = soggettoDao.getSoggettoByCF(a.getCf());
				r.setSoggettoCaso(s);
				if(s!=null)
					r.setHasDatiSociali(existsDatiSocialiAttiviBySoggettoCf(a.getCf()));
				else
					r.setHasDatiSociali(false);

				mappaSoggetti.put(a.getCf(), r);
			}

		}

		return mappaSoggetti;
	}

	@Override
	public CsAComponente findComponenteById(BaseDTO dto) {
		return parentiDAO.findComponenteById((Long) dto.getObj());
	}

	@Override
	public void verificaAllineamentoDatiInvalidita(BaseDTO dto) {
		Long diarioId = (Long)dto.getObj();
		CsDDiario diario = diarioDao.findDiarioById(diarioId);
		
		List<String> lstSinaParamInvalidita = (List<String>)dto.getObj2();
		
		List<Long> lstCsInvalidita = new ArrayList<Long>();
		List<CsADatiInvalidita> csDatiInvalidita = schedaDao.findDatiInvaliditaBySoggettoId(diario.getCsACaso().getCsASoggetto().getAnagraficaId(), diario.getDtAmministrativa());

		if (csDatiInvalidita != null) {
			for (CsADatiInvalidita datiCs : csDatiInvalidita) {
				if (datiCs.getCsAInvCivs() != null && datiCs.getCsAInvCivs().size() > 0) {
					for (CsAInvCiv invCivs : datiCs.getCsAInvCivs()) {
						lstCsInvalidita.add(invCivs.getSinaRispostaId());
					}
					break;
				}
			}
			if (lstSinaParamInvalidita != null ) {
				
				Collections.sort(lstSinaParamInvalidita);
				Collections.sort(lstCsInvalidita);
				if (!lstSinaParamInvalidita.equals(lstCsInvalidita)) {
					dto.setObj(diario.getCsACaso().getCsASoggetto());
					dto.setObj2(diario.getCsOOperatoreSettore());
					dto.setObj3(DataModelCostanti.TipiAlertCod.MULTIDIM);
					dto.setObj4("una nuova valutazione multidimensionale con dati invalidità differenti da quelli salvati in cartella sociale");
					dto.setObj5(Boolean.TRUE);
					alertService.addAlertNuovoInserimentoToResponsabileCaso(dto);
				}
			 
			}

		}
		
	}

}
