package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.CasoDAO;
import it.webred.cs.csa.ejb.dao.IterDAO;
import it.webred.cs.csa.ejb.dao.OperatoreDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DatiOperatoreDTO;
import it.webred.cs.csa.ejb.dto.listaCasi.UnitaOrganizzativaDTO;
import it.webred.cs.csa.ejb.dto.prospettoSintesi.CasiOperatoreBean;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoAccessoFascicolo;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsACasoOpeTipoOpe2;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.view.VSsSchedeUdcDiff;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;


@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableCasoSessionBean extends CarSocialeBaseSessionBean implements AccessTableCasoSessionBeanRemote  {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private CasoDAO casoDao;
	
	@Autowired
	private SoggettoDAO soggettoDao;
	
	@Autowired 
	private IterDAO iterDao;
	
	@Autowired
	private OperatoreDAO operatoreDao;
	
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
	public void updateCaso(BaseDTO dto) {
		casoDao.updateCaso((CsACaso) dto.getObj());
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
	public void eliminaOperatoreTipoOpByCasoId(BaseDTO dto) {
		casoDao.eliminaOperatoreTipoOpByCasoId((Long)dto.getObj());
		
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
	
	@Override
	public List<UnitaOrganizzativaDTO> getListaUnitaOrganizzativeByCasoId(BaseDTO dto) {
		return casoDao.getListaUnitaOrganizzative((Long)dto.getObj());
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
	public DatiOperatoreDTO findResponsabileCaso(BaseDTO dto) {
		DatiOperatoreDTO kv = null;
		CsOOperatoreBASIC o = casoDao.findResponsabileBASIC((Long) dto.getObj());
		if(o!=null){
			kv = new DatiOperatoreDTO();
			kv.setUsername(o.getUsername());
			kv.setId(o.getId());
			kv.setDenominazione(o.getDenominazione());
		}
		return kv;
	}
	
	@Override
	public Boolean existsTipoOperatore(BaseDTO dto) {
		CsOOperatoreTipoOperatore op = casoDao.findOperatoreTipoOperatoreByOpSettore((Long) dto.getObj(), (Long) dto.getObj2());
		return op!=null;
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
	public List<CasiOperatoreBean> loadCaricoLavoroByCatSocOrg(BaseDTO dto) throws Exception {
		List<CasiOperatoreBean> lstCasiOperatore = new ArrayList<CasiOperatoreBean>();
		Long idCatSoc = (Long) dto.getObj();
		List<CsOOperatore> listaOperatori = operatoreDao.getOperatoriByCatSocialeOrg(idCatSoc, dto.getEnteId());
	
		for (CsOOperatore op : listaOperatori) {
			boolean operatorCanBeAdded = false; // SISO-640
			
			Iterator<CsOOperatoreSettore> itSett = op.getCsOOperatoreSettores().iterator();
			List<String> orgs = new ArrayList<String>();
			List<Long> opSetIds = new ArrayList<Long>();
			while (itSett.hasNext()) {
				CsOOperatoreSettore s = (CsOOperatoreSettore) itSett.next();
				String org = s.getCsOSettore().getCsOOrganizzazione().getNome();
				
				opSetIds.add(s.getId());
				if (!orgs.contains(org))
					orgs.add(org);
			}
			
			// SISO-640					
			List<String> tipiOperatore = operatoreDao.findTipiOperatore(opSetIds);
			for(String descrizioneTipoOperatore : tipiOperatore) {
				operatorCanBeAdded |=    descrizioneTipoOperatore.equals("Assistente sociale") 
						                || descrizioneTipoOperatore.equals("COP")
						                || descrizioneTipoOperatore.equals("Educatore Professionale");
			}
			// -----------------------------------------------------------------------~ SISO-640
			
			String os = "";
			for (String s : orgs)
				os += s + ", ";
			os = os.substring(0, os.length() - 2);

			CasiOperatoreBean oComp = new CasiOperatoreBean();
			oComp.setOperatore(op.getDenominazione()); 
			oComp.setOrganizzazioni(os);
			if(operatorCanBeAdded){
			
				Integer numeroCasiEnte  = casoDao.countCasiByResponsabileCatSociale(op.getId(), idCatSoc, true, dto.getEnteId());

				if (numeroCasiEnte > 0) {
					oComp.setNumCasiEnte(numeroCasiEnte);
	
					Integer numeroCasiAltro = casoDao.countCasiByResponsabileCatSociale(op.getId(), idCatSoc, false, dto.getEnteId());
					oComp.setNumCasiAltro(numeroCasiAltro);
					lstCasiOperatore.add(oComp);
				}else {
					logger.info(String.format("operatore %d <%s> saltato: non ha casi in carico", op.getId(), op.getDenominazione()));
				}
			}else{
				logger.info(String.format("operatore %d <%s> saltato: non appartenente alla tipologia ['Assistente sociale','COP','Educatore Professionale']", op.getId(), op.getDenominazione()));
			}
		// -----------------------------------------------------------------------~ SISO-640
		}
		
		return lstCasiOperatore;
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


	@Override
	public boolean isOperatoreCaso(BaseDTO dto) {
		/**
		 * 	se il caso non ha responsabile e l'operatore è il creatore del caso
		 *  se l'operatore corrente (opSettoreId) è  presente nella lista degli operatori per quel caso
		*/
		boolean val = false;
		Long casoId = (Long)dto.getObj();
		Long opSettoreId = (Long)dto.getObj2();
		boolean isCasoOperatore = false;
		boolean respExist = false;
		CsACaso caso = casoDao.findCasoById(casoId);
		List<CsACasoOpeTipoOpe> listaCasoOpeTipoOpe = casoDao.getListaOperatoreTipoOpByCasoId(casoId);
		for (CsACasoOpeTipoOpe casoOpeTipoOpe : listaCasoOpeTipoOpe) {
			if (casoOpeTipoOpe.getDataFineApp().after(new Date())) {
				if (casoOpeTipoOpe.getFlagResponsabile() != null && casoOpeTipoOpe.getFlagResponsabile().booleanValue())
					respExist = true;
				if (casoOpeTipoOpe.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getId() == opSettoreId)
					isCasoOperatore = true;
			}
		}
		
		val = !respExist && caso.getUserIns().equals(dto.getUserId()) || isCasoOperatore;
		return val;
	}

}
