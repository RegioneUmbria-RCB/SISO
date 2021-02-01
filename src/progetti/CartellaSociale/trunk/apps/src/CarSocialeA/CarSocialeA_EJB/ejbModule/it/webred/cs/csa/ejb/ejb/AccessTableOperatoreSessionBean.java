package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.OperatoreDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.OperatoriSearchCriteria;
import it.webred.cs.csa.ejb.dto.configurazione.CsOOperatoreSettoreEstesa;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOpsettoreAlertConfig;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsOZonaSoc;
import it.webred.cs.data.model.view.CsAmAnagraficaOperatore;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Andrea
 *
 */
@Stateless
public class AccessTableOperatoreSessionBean extends CarSocialeBaseSessionBean implements AccessTableOperatoreSessionBeanRemote {

	private static final long serialVersionUID = 1L;
	@Autowired
	private OperatoreDAO operatoreDao;
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public CsOOperatoreSettore findOperatoreSettoreById(OperatoreDTO dto) throws Exception {
		if(dto.getIdOperatoreSettore()!=null)
			return operatoreDao.findOperatoreSettoreById(dto.getIdOperatoreSettore());
		else
			return operatoreDao.findOperatoreSettoreById(dto.getIdOperatore(), dto.getIdSettore(), new Date());
	}
	
	@Override
	public List<CsOOperatoreSettore> findOperatoreSettoreByOperatore(OperatoreDTO dto) throws Exception {
		
		List<CsOOperatoreSettore> operSetts = operatoreDao.findOperatoreSettoreByOperatore(dto.getIdOperatore(), dto.getDate());
		return operSetts;
	}
	
	@Override
	public CsOSettoreBASIC findSettoreBASICById(OperatoreDTO dto)
			throws Exception {
		CsOSettoreBASIC settore = operatoreDao.findSettoreBASICById(dto.getIdSettore());
		return settore;
		
	}
	
	@Override
	public CsOSettore findSettoreById(OperatoreDTO dto) throws Exception {
		CsOSettore settore = operatoreDao.findSettoreById(dto.getIdSettore());
		return settore;
	}

	@Override
	public CsOOrganizzazione findOrganizzazioneById(OperatoreDTO dto) throws Exception {
		
		CsOOrganizzazione ente = operatoreDao.findOrganizzazioneById(dto.getIdOrganizzazione());
		return ente;
	}

	@Override	
	@AuditSaltaValidazioneSessionID
	@AuditConsentiAccessoAnonimo
	public CsOOperatore findOperatoreByUsername(OperatoreDTO dto) throws Exception {
		CsOOperatore operatore = operatoreDao.findOperatoreByUsername(dto.getUsername());
		return operatore;
	}
	
	
	@Override
	public CsOOperatoreBASIC findOperatoreBASICByUsername(OperatoreDTO dto) throws Exception {
		CsOOperatoreBASIC operatore = operatoreDao.findOperatoreBASICByUserName(dto.getUsername());
		return operatore;
	}
	
	
	
	@Override
	public Long findOperatoreIdByUsername(OperatoreDTO dto) throws Exception {
		CsOOperatoreBASIC operatore = operatoreDao.findOperatoreBASICByUserName(dto.getUsername());
		return operatore.getId() ;
	}

	
	@Override
	public CsOOperatoreSettore findRespSettoreFirma(OperatoreDTO dto) throws Exception {
		List<CsOOperatoreSettore> opSett = operatoreDao.findOperatoreSettoreBySettore(dto.getIdSettore());
		boolean trovato = false;
		String gruppo = "CSOCIALE_RESPO_SETTORE_" + dto.getEnteId();
		
		List<CsOOperatoreSettore> respTemp = new ArrayList<CsOOperatoreSettore>();
		
		int i=0;
		while(!trovato && i<opSett.size()){
			CsOOperatoreSettore os = opSett.get(i);
			boolean attivo = os.getDataFineApp()!=null && os.getDataFineApp().after(new Date());
			if(attivo && os.getAmGroup().contains(gruppo)){
				respTemp.add(os);
				if(os.getFirma()){
					trovato=true;
					return os;
				}
			}
			i++;
		}
		
		//Se non ce nessuno con la firma prendo l'unico del settore
		if(!trovato && respTemp.size()==1){
			CsOOperatoreSettore cs = respTemp.get(0);
			cs.setFirma(true); //Imposto pure il flag!
			operatoreDao.insertOrUpdateOperatoreSettore(cs); 
			return cs; 
		}
		
		return null;
	}
	
	
	@Override
	public List<CsOOperatoreSettore> findOperatoreSettoreBySettore(OperatoreDTO dto) throws Exception {
		List<CsOOperatoreSettore> opSett = operatoreDao.findOperatoreSettoreBySettore(dto.getIdSettore());
		return opSett;
	}
	
	@Override
	public List<CsOOperatoreAnagrafica> findAllOperatoreAnagrafica(CeTBaseObject cet) throws Exception {
		List<CsOOperatoreAnagrafica> opAna = operatoreDao.findAllOperatoreAnagrafica();
		return opAna;
	}

	@Override
	public CsOOperatore findOperatoreById(OperatoreDTO dto) throws Exception {
		CsOOperatore operatore = operatoreDao.findOperatoreById(dto.getIdOperatore()); 
		return operatore;
	}
	
	@Override
	public CsOOperatoreBASIC findOperatoreBASICById(OperatoreDTO dto) throws Exception {
		CsOOperatoreBASIC operatore = operatoreDao.findOperatoreBASICById(dto.getIdOperatore()); 
		return operatore;
	}
	
	
	@Override
	public List<CsOOperatore> getOperatoriAll(CeTBaseObject cet) throws Exception {
    	return operatoreDao.getOperatoriAll();
    }
	
	@Override
	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoId(BaseDTO dto) throws Exception {
    	return operatoreDao.getOperatoriByTipoId((Long) dto.getObj());
    }
	
	@Override
	public List<CsOOperatore> getOperatoriByTipoDescrizione(BaseDTO dto) throws Exception {
    	return operatoreDao.getOperatoriByTipoDescrizione((String) dto.getObj());
    }
	
	@Override
	public CsOOperatoreTipoOperatore getTipoByOperatoreSettore(OperatoreDTO dto) throws Exception {
    	return operatoreDao.getTipoByOperatoreSettore(dto.getIdOperatoreSettore(), dto.getDate());
    }
	
	@Override
	public List<CsOOperatore> getOperatoriByCatSocialeOrg(BaseDTO dto) throws Exception {
		Long idCatSoc = (Long) dto.getObj();
		List<CsOOperatore> lst = operatoreDao.getOperatoriByCatSocialeOrg(idCatSoc, dto.getEnteId());
		for(CsOOperatore o : lst)
			o.getCsOOperatoreSettores().size();
		return lst;
    }
	
	@Override
	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(OperatoreDTO dto) throws Exception {
		List<CsOSettoreBASIC> sett = operatoreDao.findSettoreBASICByOrganizzazione(dto.getIdOrganizzazione());
		return sett;
	}

	//SISO-812
	@Override
	public List<CsOSettoreBASIC> findSettoreBASICSecondoLivelloByOrganizzazione(OperatoreDTO dto) throws Exception {
		List<CsOSettoreBASIC> sett = operatoreDao.findSettoreBASICSecondoLivelloByOrganizzazione(dto.getIdOrganizzazione());
		return sett;
	}
	
	@Override
	public List<CsOSettoreBASIC> findSettoreBASICSenzaSecondoLivelloByOrganizzazione(OperatoreDTO dto) throws Exception {
		List<CsOSettoreBASIC> sett = operatoreDao.findSettoreBASICSenzaSecondoLivelloByOrganizzazione(dto.getIdOrganizzazione());
		return sett;
	}
	
	@Override
	public List<CsOOperatoreSettore> findOperatoreSettori(OperatoreDTO dto) throws Exception {
		List<CsOOperatoreSettore> opSett = operatoreDao.findOperatoreSettori();
		return opSett;
	}


	@Override
	public CsOOperatoreTipoOperatore getOperatoreTipoOpById(BaseDTO dto) throws Exception {
		return operatoreDao.findCsOOperatoreTipoOpById((Long)dto.getObj());
	}
	
	@Override
	public CsOOperatore salvaOperatore(BaseDTO dto) throws Exception {
		return operatoreDao.insertOrUpdateOperatore((CsOOperatore) dto.getObj());
	}
	
	@Override
	public CsOOperatoreTipoOperatore salvaTipoOperatore(BaseDTO dto) throws Exception {
		return operatoreDao.insertOrUpdateTipoOperatore((CsOOperatoreTipoOperatore)dto.getObj());
	}
	
	@Override
	public CsOOperatoreSettore salvaOperatoreSettore(BaseDTO dto) throws Exception {
		return operatoreDao.insertOrUpdateOperatoreSettore((CsOOperatoreSettore)dto.getObj());
	}
	
	@Override
	public void deleteTipoOperatore(BaseDTO dto) throws Exception {
		operatoreDao.deleteTipoOperatore((CsOOperatoreTipoOperatore)dto.getObj());
	}
	
	@Override
	public void deleteOperatoreSettore(BaseDTO dto) throws Exception {
		operatoreDao.deleteOperatoreSettore((CsOOperatoreSettore)dto.getObj());
	}
	
	@Override
	public CsOZonaSoc findZonaSocAbilitata(BaseDTO dto) {
		return operatoreDao.findZonaSocAbilitata();
	}

	@Override
	public void resetFirmaTuttiRespSettore(BaseDTO dto) {
		operatoreDao.resetFirmaTuttiRespSettore((String)dto.getObj(), (Long) dto.getObj2()); 
	}


	@Override
	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoIdSettore(BaseDTO dto) throws Exception {
		return operatoreDao.getOperatoriByTipoIdSettore((Long)dto.getObj(), (Long)dto.getObj2());
	}

	@Override
	public void insertOrUpdateAlertConfig(BaseDTO input) throws Exception {
		List<CsOOpsettoreAlertConfig> lst = (List<CsOOpsettoreAlertConfig>)input.getObj();
		
		operatoreDao.deleteConfigAlertOpSettore((Long)input.getObj2());
		
		for(CsOOpsettoreAlertConfig c : lst)
			operatoreDao.salvaConfigurazioneAlert(c);
	}



	@Override
	public List<CsOOperatoreTipoOperatore> findTipiOperatore(BaseDTO operatoreSettoreIdDto) throws Exception {
		Long operatoreSettoreId = (Long) operatoreSettoreIdDto.getObj();
		List<CsOOperatoreTipoOperatore> list = operatoreDao.findTipiOperatore(operatoreSettoreId);
		return list;
	}

	@Override
	public LinkedHashMap<String, String> getCodificaRuoli(BaseDTO dto) {
		String belfiore = (String)dto.getObj();
		return operatoreDao.getDecodificaRuoli(belfiore);
	}

	@Override
	public List<CsOOperatoreSettoreEstesa> findOperatoreSettoreEstesaByOperatore(OperatoreDTO opDto) throws Exception {
		List<CsOOperatoreSettoreEstesa> dataTableDati = null;
		List<CsOOperatoreSettore> beanLst = findOperatoreSettoreByOperatore(opDto);
		if (beanLst != null && !beanLst.isEmpty()) {
			dataTableDati = new ArrayList<CsOOperatoreSettoreEstesa>();
			for (CsOOperatoreSettore opSet : beanLst) {
				opDto.setIdOperatoreSettore(opSet.getId());
				opDto.setDate(opSet.getDataFineApp()); //Carico i tipi con la stessa data fine dell'opSettore altrimenti se un settore è inattivo non vedo più il tipo
				CsOOperatoreTipoOperatore opTipoOp = getTipoByOperatoreSettore(opDto);
				dataTableDati.add(copyFromCsOOperatoreSettore(opSet, opTipoOp));			
			}
		}
		return dataTableDati;
	}
	
	private CsOOperatoreSettoreEstesa copyFromCsOOperatoreSettore(CsOOperatoreSettore fromObj, CsOOperatoreTipoOperatore fromOpTipoOp) {
		if (fromObj == null) return null;
		CsOOperatoreSettoreEstesa obj = new CsOOperatoreSettoreEstesa();
		obj.setId(fromObj.getId());
		obj.setDataInizioApp(fromObj.getDataInizioApp());
		obj.setDataFineApp(fromObj.getDataFineApp());
		obj.setCsOOperatore(fromObj.getCsOOperatore());
		obj.setCsOSettore(fromObj.getCsOSettore());
		obj.setAmGroup(fromObj.getAmGroup());
		obj.setAppartiene(fromObj.getAppartiene());
		obj.setFirma(fromObj.getFirma());
		obj.setLblOrganizzazione(fromObj.getCsOSettore().getCsOOrganizzazione().getNome());
		obj.setLblSettore(fromObj.getCsOSettore().getNome());
		if(fromOpTipoOp != null)
			obj.setLblTipoOperatore(fromOpTipoOp.getCsTbTipoOperatore().getDescrizione());
		else obj.setLblTipoOperatore("-");
		
		obj.setAlertConfig(fromObj.getAlertConfig());
		
		List<String> lstRuoli = getListaRuoli(fromObj);
		obj.setListaRuoli(lstRuoli);
		
		return obj;
	}
	
	private List<String> getListaRuoli(CsOOperatoreSettore opSet){
		List<String> ruoli = new ArrayList<String>();
		
		String amGroup = opSet.getAmGroup();
		if (amGroup == null || amGroup.equals("") 
		|| amGroup.lastIndexOf("_") == -1 
		|| amGroup.lastIndexOf("_") != amGroup.length() - 5) {
			return  ruoli; // "-";
		}
		
		String[] arr = amGroup.split(",");
		if (arr.length > 0) {
			for (String gr : arr) {
				String prefisso = gr.substring(0,gr.length() - 4);
				String lblRuolo = operatoreDao.findLabelRuolo(prefisso);
				if(!StringUtils.isEmpty(lblRuolo) && !ruoli.contains(lblRuolo))
				   ruoli.add(lblRuolo);
			}
		}
		
		return ruoli;
	}

	@Override
	public List<CsAmAnagraficaOperatore> getUtentiAmPerCs(OperatoriSearchCriteria cet){
		return operatoreDao.searchUtentiAmPerCs(cet, false);
	}

	@Override
	public int countUtentiAmPerCs(OperatoriSearchCriteria criteria) {
		List<CsAmAnagraficaOperatore> lst =  operatoreDao.searchUtentiAmPerCs(criteria, true);
		return lst!=null ? lst.size() : 0;
	}
	
	

}
