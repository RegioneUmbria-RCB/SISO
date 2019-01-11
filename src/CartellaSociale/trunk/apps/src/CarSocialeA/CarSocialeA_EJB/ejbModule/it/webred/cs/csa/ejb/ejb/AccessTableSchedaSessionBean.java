package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.SchedaDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.RisorsaFamDTO;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsAContributi;
import it.webred.cs.data.model.CsADatiInvalidita;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsADisabilita;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.data.model.CsAServizi;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsATribunale;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableSchedaSessionBean extends CarSocialeBaseSessionBean implements AccessTableSchedaSessionBeanRemote  {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SchedaDAO schedaDao;
	
	@EJB
	public AccessTableSoggettoSessionBeanRemote soggettoSessionBean;
	
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
		if(obj instanceof CsAServizi)
			saveServizi(dto);
		if(obj instanceof CsAContributi)
			saveContributi(dto);
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
		if(obj instanceof CsAServizi)
			updateServizi(dto);
		if(obj instanceof CsAContributi)
			updateContributi(dto);
		if(obj instanceof CsAFamigliaGruppo)
			updateFamigliaGruppo(dto);
	}
	
	@Override
	public List<?> findCsBySoggettoId(BaseDTO dto) {
		
		Object obj = dto.getObj2();
		if(obj instanceof CsADisabilita)
			return findDisabilitaBySoggettoId(dto);
		if(obj instanceof CsADatiSociali)
			return findDatiSocialiBySoggettoId(dto);
		if(obj instanceof CsADatiInvalidita)
			return findDatiInvaliditaBySoggettoId(dto);
		if(obj instanceof CsATribunale)
			return findTribunaleBySoggettoId(dto);
		if(obj instanceof CsAServizi)
			return findServiziBySoggettoId(dto);
		if(obj instanceof CsAContributi)
			return findContributiBySoggettoId(dto);
		if(obj instanceof CsAFamigliaGruppo)
			return findFamigliaGruppoBySoggettoId(dto);
	
		
		return null;
	}
	
	private List<CsADatiSociali> findDatiSocialiBySoggettoId(BaseDTO dto) {
		return schedaDao.findDatiSocialiBySoggettoId((Long)dto.getObj());
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
		if(obj instanceof CsAServizi)
			eliminaServizi(dto);
		if(obj instanceof CsAContributi)
			eliminaContributi(dto);
		if(obj instanceof CsAFamigliaGruppo)
			eliminaFamigliaGruppo(dto);
	}
	
	@Override
	public CsADisabilita getDisabilitaById(BaseDTO dto) {		
		return schedaDao.getDisabilitaById((Long) dto.getObj());
	}
	
	private List<CsADisabilita> findDisabilitaBySoggettoId(BaseDTO dto) {		
		return schedaDao.findDisabilitaBySoggettoId((Long) dto.getObj());
	}

	private void saveDisabilita(BaseDTO dto) {
		CsADisabilita cs = (CsADisabilita) dto.getObj();
		dto.setObj(cs.getCsASoggetto().getAnagraficaId());
		CsASoggettoLAZY soggetto = soggettoSessionBean.getSoggettoById(dto);
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
	
	@Override
	public Boolean existsDatiSocialiAttiviBySoggettoCf(BaseDTO dto) {		
		List<CsADatiSociali> lst =  schedaDao.findDatiSocialiBySoggettoCf((String) dto.getObj());
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
	public CsADatiSociali findDatiSocialiAttiviBySoggettoCf(BaseDTO dto) {		
		List<CsADatiSociali> lst =  schedaDao.findDatiSocialiBySoggettoCf((String) dto.getObj());
		boolean esiste = false;
		if(lst!=null && !lst.isEmpty()){
			for(CsADatiSociali ds : lst){
				if(!ds.getDataFineApp().before(new Date()))
					return ds;
			}
		}
		return null;
	}
	
	

	private void saveDatiSociali(BaseDTO dto) {
		CsADatiSociali cs = (CsADatiSociali) dto.getObj();
		dto.setObj(cs.getCsASoggetto().getAnagraficaId());
		CsASoggettoLAZY soggetto = soggettoSessionBean.getSoggettoById(dto);
		cs.setCsASoggetto(soggetto);
		cs.setDtIns(new Date());
		cs.setUserIns(dto.getUserId());
		cs.setDataInizioSys(new Date());
		schedaDao.saveDatiSociali(cs);
	}
	
	private void updateDatiSociali(BaseDTO dto) {
		CsADatiSociali cs = (CsADatiSociali) dto.getObj();
		cs.setUsrMod(dto.getUserId());
		cs.setDtMod(new Date());
		schedaDao.updateDatiSociali(cs);
	}
	
	private void eliminaDatiSociali(BaseDTO dto) {
		schedaDao.eliminaDatiSociali((Long) dto.getObj());
	}
	
	@Override
	public CsADatiInvalidita getDatiInvaliditaById(BaseDTO dto) {		
		return schedaDao.getDatiInvaliditaById((Long) dto.getObj());
	}
	
	private List<CsADatiInvalidita> findDatiInvaliditaBySoggettoId(BaseDTO dto) {		
		return schedaDao.findDatiInvaliditaBySoggettoId((Long) dto.getObj());
	}

	private void saveDatiInvalidita(BaseDTO dto) {
		CsADatiInvalidita cs = (CsADatiInvalidita) dto.getObj();
		dto.setObj(cs.getCsASoggetto().getAnagraficaId());
		CsASoggettoLAZY soggetto = soggettoSessionBean.getSoggettoById(dto);
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
	
	private List<CsATribunale> findTribunaleBySoggettoId(BaseDTO dto) {		
		return schedaDao.findTribunaleBySoggettoId((Long) dto.getObj());
	}

	private void saveTribunale(BaseDTO dto) {
		CsATribunale cs = (CsATribunale) dto.getObj();
		dto.setObj(cs.getCsASoggetto().getAnagraficaId());
		CsASoggettoLAZY soggetto = soggettoSessionBean.getSoggettoById(dto);
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
	public CsAServizi getServiziById(BaseDTO dto) {		
		return schedaDao.getServiziById((Long) dto.getObj());
	}
	
	private List<CsAServizi> findServiziBySoggettoId(BaseDTO dto) {		
		return schedaDao.findServiziBySoggettoId((Long) dto.getObj());
	}

	private void saveServizi(BaseDTO dto) {
		CsAServizi cs = (CsAServizi) dto.getObj();
		dto.setObj(cs.getCsASoggetto().getAnagraficaId());
		CsASoggettoLAZY soggetto = soggettoSessionBean.getSoggettoById(dto);
		cs.setCsASoggetto(soggetto);
		cs.setDtIns(new Date());
		cs.setUserIns(dto.getUserId());
		//cs.setDataInizioSys(new Date());
		schedaDao.saveServizi(cs);
	}
	
	private void updateServizi(BaseDTO dto) {
		CsAServizi cs = (CsAServizi) dto.getObj();
		cs.setUsrMod(dto.getUserId());
		cs.setDtMod(new Date());
		schedaDao.updateServizi(cs);
	}
	
	private void eliminaServizi(BaseDTO dto) {
		schedaDao.eliminaServizi((Long) dto.getObj());
	}
	
	@Override
	public CsAContributi getContributiById(BaseDTO dto) {		
		return schedaDao.getContributiById((Long) dto.getObj());
	}
	
	private List<CsAContributi> findContributiBySoggettoId(BaseDTO dto) {		
		return schedaDao.findContributiBySoggettoId((Long) dto.getObj());
	}

	private void saveContributi(BaseDTO dto) {
		CsAContributi cs = (CsAContributi) dto.getObj();
		dto.setObj(cs.getCsASoggetto().getAnagraficaId());
		CsASoggettoLAZY soggetto = soggettoSessionBean.getSoggettoById(dto);
		cs.setCsASoggetto(soggetto);
		cs.setDtIns(new Date());
		cs.setUserIns(dto.getUserId());
		//cs.setDataInizioSys(new Date());
		schedaDao.saveContributi(cs);
	}
	
	private void updateContributi(BaseDTO dto) {
		CsAContributi cs = (CsAContributi) dto.getObj();
		cs.setUsrMod(dto.getUserId());
		cs.setDtMod(new Date());
		schedaDao.updateContributi(cs);
	}
	
	private void eliminaContributi(BaseDTO dto) {
		schedaDao.eliminaContributi((Long) dto.getObj());
	}
	
	@Override
	public CsAFamigliaGruppo getFamigliaGruppoById(BaseDTO dto) {		
		return schedaDao.getFamigliaGruppoById((Long) dto.getObj());
	}
	
	
	private List<CsAFamigliaGruppo> findFamigliaGruppoBySoggettoId(BaseDTO dto) {		
		return schedaDao.findFamigliaGruppoBySoggettoId((Long) dto.getObj());
	}
	
	@Override
	public CsAFamigliaGruppo findFamigliaAllaDataBySoggettoId(BaseDTO dto){
		return schedaDao.findFamigliaGruppoAllaDataBySoggettoId((Long)dto.getObj(), (Date)dto.getObj2(), ((Boolean) dto.getObj3())==null?false: ((Boolean) dto.getObj3()).booleanValue());
	}

	private void saveFamigliaGruppo(BaseDTO dto) {
		CsAFamigliaGruppo cs = (CsAFamigliaGruppo) dto.getObj();
		List<CsAComponente> listaComp = cs.getCsAComponentes();
		cs.setCsAComponentes(null);
		dto.setObj(cs.getCsASoggetto().getAnagraficaId());
		CsASoggettoLAZY soggetto = soggettoSessionBean.getSoggettoById(dto);
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
		CsAFamigliaGruppo cs = (CsAFamigliaGruppo) dto.getObj();
		List<CsAComponente> listaComp = cs.getCsAComponentes();
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
	}
	
	private void eliminaFamigliaGruppo(BaseDTO dto) {
		schedaDao.eliminaComponenteByFamigliaId((Long) dto.getObj());
		schedaDao.eliminaFamigliaGruppo((Long) dto.getObj());
	}
	
	@Override
	public List<CsAAnagrafica> findComponentiGiaFamigliariBySoggettoCf(BaseDTO dto) {
		Date dataRif = dto.getObj2()!=null ? (Date) dto.getObj2() : null;
		return schedaDao.findComponentiGiaFamigliariBySoggettoCf((String)dto.getObj(),dataRif);
	}
	
	@Override
	public Hashtable<String,RisorsaFamDTO> findRisorseFamiliariBySoggettoCf(BaseDTO dto) {
		Hashtable<String,RisorsaFamDTO> mappaSoggetti =new Hashtable<String, RisorsaFamDTO>();
		String cf = (String)dto.getObj();
		
		Date dataRif = dto.getObj2()!=null ? (Date) dto.getObj2() : null;
		List<CsAAnagrafica> lst = schedaDao.findComponentiGiaFamigliariBySoggettoCf(cf, dataRif);
        
		for(CsAAnagrafica a : lst){
			if(!mappaSoggetti.containsKey(a.getCf()) && !cf.equalsIgnoreCase(a.getCf())){
				RisorsaFamDTO r = new RisorsaFamDTO();
				r.setCognome(a.getCognome());
				r.setNome(a.getNome());
				r.setCf(a.getCf());
				
				dto.setObj(a.getCf());
				CsASoggettoLAZY s = soggettoSessionBean.getSoggettoByCF(dto);
				r.setSoggettoCaso(s);
				if(s!=null)
					r.setHasDatiSociali(existsDatiSocialiAttiviBySoggettoCf(dto));
				else
					r.setHasDatiSociali(false);
				
				mappaSoggetti.put(a.getCf(), r);
			}
			
		}
		
		return mappaSoggetti;
	}
	
	
}
