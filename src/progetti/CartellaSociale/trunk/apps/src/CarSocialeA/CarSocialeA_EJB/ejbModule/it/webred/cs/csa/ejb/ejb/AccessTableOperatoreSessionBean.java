package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.OperatoreDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.OperatoriSearchCriteria;
import it.webred.cs.csa.ejb.dto.configurazione.CsOOperatoreSettoreEstesa;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsOOperatore;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.ExcludeDefaultInterceptors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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
	@AuditSaltaValidazioneSessionID
	@AuditConsentiAccessoAnonimo
	public CsOOperatoreBASIC findOperatoreBASICByUsername(OperatoreDTO dto) throws Exception {
		CsOOperatoreBASIC operatore = operatoreDao.findOperatoreBASICByUserName(dto.getUsername());
		return operatore;
	}
	
	@Override
	public CsOOperatoreSettore findRespSettoreFirma(OperatoreDTO dto) throws Exception {
		List<CsOOperatoreSettore> respTemp = operatoreDao.findRespSettoreAttivo(dto.getIdSettore(), dto.getEnteId());
		boolean trovato = false;

		int i=0;
		while(!trovato && i<respTemp.size()){
			CsOOperatoreSettore os = respTemp.get(i);
			if(os.getFirma()){
					trovato=true;
					return os;
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
	public List<KeyValueDTO> findListaOperatoreSettoreBySettore(OperatoreDTO dto) throws Exception {
		return operatoreDao.findListaOperatoreSettoreBySettore(dto.getIdSettore());
	}
	
	@Override
	public List<KeyValueDTO> findListaOperatoreBySettore(OperatoreDTO dto) throws Exception {
		return operatoreDao.findListaOperatoreBySettore(dto.getIdSettore());
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
	public CsOZonaSoc findZonaSocAbilitata(CeTBaseObject dto) {
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

	@Override
	public List<KeyValueDTO> findOperatoreIdAnagraficaBySettore(OperatoreDTO dto) {
		List<KeyValueDTO> lst = operatoreDao.findOperatoreIdAnagraficaBySettore(dto.getIdSettore());
		Collections.sort(lst, new Comparator<KeyValueDTO>() {
			@Override
			public int compare(final KeyValueDTO object1, final KeyValueDTO object2) {
				return object1.getDescrizione().compareTo(object2.getDescrizione());
			}
		});
		return lst;
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public List<CsOOperatoreSettore> findOperatoreSettoreByCodStruttura(BaseDTO dto) throws Exception {
		String tipo2Liv = (String)dto.getObj();
		String codRouting = (String) dto.getObj2();
		return operatoreDao.findOperatoreSettoreByCodStruttura(tipo2Liv, codRouting);
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public CsOOperatoreSettore findOperatoreSettore2LivByIdOperatore(BaseDTO dto) throws Exception {
		String tipo2Liv = (String)dto.getObj();
		String codRouting = (String) dto.getObj2();
		Long idOperatore = (Long)dto.getObj3();
		return operatoreDao.findOperatoreSettore2LivByIdOperatore(tipo2Liv, idOperatore, codRouting, new Date());
    }

	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public CsOOperatoreSettore findOperatoreSettore2LivByUsername(BaseDTO dto) throws Exception {
		String tipo2Liv = (String)dto.getObj();
		String codRouting = (String) dto.getObj2();
		String username = (String)dto.getObj3();
		return operatoreDao.findOperatoreSettore2LivByUsername(tipo2Liv, username, codRouting, new Date());
    }
	
	@Override
	public void disabilitaOperatoreSettore(BaseDTO dto) throws Exception {
		List<CsOOperatoreSettoreEstesa> selOpSets = (List<CsOOperatoreSettoreEstesa>)dto.getObj();
		for (CsOOperatoreSettoreEstesa selOpSet : selOpSets) {
			CsOOperatoreSettore opSet = operatoreDao.findOperatoreSettoreById(selOpSet.getId());
			if (opSet != null) {
				opSet.setDataFineApp(ddMMyyyy.parse(ddMMyyyy.format(new Date())));
				opSet.setFirma(false);					
				operatoreDao.insertOrUpdateOperatoreSettore(opSet);
				
				//AGGIORNO CS_O_OPERATORE_TIPOOPERATORE
				CsOOperatoreTipoOperatore tipoOp = operatoreDao.getTipoByOperatoreSettore(opSet.getId(), opSet.getDataFineApp());
				if(tipoOp!=null){
            	   tipoOp.setDataFineApp(opSet.getDataFineApp());
            	   operatoreDao.insertOrUpdateTipoOperatore(tipoOp);
				}
               
				//Azzera flag email CS_O_OPSETTORE_ALERT_CONFIG
				operatoreDao.setEmailConfigAlertOpSettore(opSet.getId(), Boolean.FALSE);
			}
		}
	}
	
	@Override
	public void abilitaOperatoreSettore(BaseDTO dto) throws Exception {
		List<CsOOperatoreSettoreEstesa> selOpSets = (List<CsOOperatoreSettoreEstesa>)dto.getObj();
		for (CsOOperatoreSettoreEstesa selOpSet : selOpSets) {
			if(!selOpSet.isAttivo()){
				CsOOperatoreSettore opSet = operatoreDao.findOperatoreSettoreById(selOpSet.getId());
				if (opSet != null) {
					Date dataFineOld = opSet.getDataFineApp();
					Date oggi = new Date();
					if(!opSet.getDataFineApp().equals(oggi));
						opSet.setDataInizioApp(new Date());
						
					opSet.setDataFineApp(DataModelCostanti.END_DATE);
					operatoreDao.insertOrUpdateOperatoreSettore(opSet);
					
					Iterator<CsOOperatoreTipoOperatore> lstTipo = opSet.getTipoOperatore().iterator();
					while(lstTipo.hasNext()){
						CsOOperatoreTipoOperatore tipo = lstTipo.next();
						if(tipo.getDataFineApp().equals(dataFineOld)){
							tipo.setDataInizioApp(opSet.getDataInizioApp());
							tipo.setDataFineApp(opSet.getDataFineApp());
							 operatoreDao.insertOrUpdateTipoOperatore(tipo);
						}
					}
					
					//Attiva flag email CS_O_OPSETTORE_ALERT_CONFIG
					operatoreDao.setEmailConfigAlertOpSettore(opSet.getId(), Boolean.TRUE);
					
				}
			}
		}
	}

}
