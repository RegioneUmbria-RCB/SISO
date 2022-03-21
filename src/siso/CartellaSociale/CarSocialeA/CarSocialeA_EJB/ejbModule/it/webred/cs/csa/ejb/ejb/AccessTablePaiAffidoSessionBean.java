package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTablePaiAffidoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiPaiSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.PaiAffidoDAO;
import it.webred.cs.csa.ejb.dao.ParentiDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoFamigliaAffidatariaDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoFamigliaOrigineDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoSoggFamigliaDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoSoggettoDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoStatoDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.PaiAffidoDominiEnum;
import it.webred.cs.csa.ejb.dto.pai.affido.PaiAffidoStatoEnum;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.affido.CsPaiAffido;
import it.webred.cs.data.model.affido.CsPaiAffidoFamigliaAffidataria;
import it.webred.cs.data.model.affido.CsPaiAffidoFamigliaOrigine;
import it.webred.cs.data.model.affido.CsPaiAffidoSoggetto;
import it.webred.cs.data.model.affido.CsPaiAffidoStato;
import it.webred.ct.support.validation.ValidationStateless;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTablePaiAffidoSessionBean extends CarSocialeBaseSessionBean implements AccessTablePaiAffidoSessionBeanRemote {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PaiAffidoDAO affidoDAO;
	
	@Autowired
	private ParentiDAO parentiDAO;
	
	@EJB
	private AccessTableDominiPaiSessionBeanRemote dominiPaiService;
	

	@Override
	public CsPaiAffidoDTO saveAffido(BaseDTO dto) throws Exception {
		CsPaiAffidoDTO affido = (CsPaiAffidoDTO) dto.getObj();
		
		//gestione stato
		Integer codiceUltimoStato = (Integer) dto.getObj2();
		//SISO-993 gestione Data stato affido
		Date dataStatoAffido = (Date) dto.getObj3();
		//se lo stato è diverso -> deve essere aggiornato
		if(!codiceUltimoStato.equals(affido.getCodiceStatoAttuale())){
			
			for(CsPaiAffidoStatoDTO stato : affido.getStatiAffido()){
				if(stato.getDataA() == null){
					//chiudo ultimo stato
					stato.setDataA(new Date());
					break;
				}
			}
			
			CsPaiAffidoStatoDTO nuovoStato = new CsPaiAffidoStatoDTO();
			nuovoStato.setAffido(affido);
			nuovoStato.setCodice(codiceUltimoStato);
			nuovoStato.setDataDa(new Date());
			nuovoStato.setDataStatoAffido(dataStatoAffido);//SISO-993
			
			affido.getStatiAffido().add(nuovoStato);
		}
		else{
			for(CsPaiAffidoStatoDTO stato : affido.getStatiAffido()){
				if(stato.getDataA() == null){
					stato.setDataStatoAffido(dataStatoAffido);
					break;
				}
			}
			
			
		}
		
		CsPaiAffido res = affidoDAO.saveAffido(toEntity(affido));
		return toDTO(res);
	}
	
	@Override
	public CsPaiAffidoDTO findAffidoByDiarioPaiId(BaseDTO dto) throws Exception {
		CsPaiAffido res = affidoDAO.findAffidoByDiarioPaiId((Long)dto.getObj());
		CsPaiAffidoDTO toReturn = toDTO(res);
		
		//madre affidataria se esiste prendo da anagrafica altrimenti da temp affido
		if(toReturn.getFamigliaAffidataria().getCsAComponenteIdMadre() != null){
			CsPaiAffidoSoggFamigliaDTO madre = findSoggettoByComponente(toReturn.getFamigliaAffidataria().getCsAComponenteIdMadre());
			toReturn.getFamigliaAffidataria().setMadre(madre);
		}else{
			CsPaiAffidoSoggFamigliaDTO madre = new CsPaiAffidoSoggFamigliaDTO();
			madre.setCognome(res.getFamigliaAffidataria().getBaseCognomeMadre());
			madre.setNome(res.getFamigliaAffidataria().getBaseNomeMadre());
			madre.setCf(res.getFamigliaAffidataria().getBaseCfMadre());
			toReturn.getFamigliaAffidataria().setMadre(madre);
		}
		
		//padre affidatario se esiste prendo da anagrafica altrimenti da temp affido
		if(toReturn.getFamigliaAffidataria().getCsAComponenteIdPadre() != null){
			CsPaiAffidoSoggFamigliaDTO padre = findSoggettoByComponente(toReturn.getFamigliaAffidataria().getCsAComponenteIdPadre());
			toReturn.getFamigliaAffidataria().setPadre(padre);
		}else{
			CsPaiAffidoSoggFamigliaDTO padre = new CsPaiAffidoSoggFamigliaDTO();
			padre.setCognome(res.getFamigliaAffidataria().getBaseCognomePadre());
			padre.setNome(res.getFamigliaAffidataria().getBaseNomePadre());
			padre.setCf(res.getFamigliaAffidataria().getBaseCfPadre());
			toReturn.getFamigliaAffidataria().setPadre(padre);
		}
		
		//madre origine se esiste prendo da anagrafica
		if(toReturn.getFamigliaOrigine().getCsAComponenteIdMadre() != null){
			CsPaiAffidoSoggFamigliaDTO madre = findSoggettoByComponente(toReturn.getFamigliaOrigine().getCsAComponenteIdMadre());
			toReturn.getFamigliaOrigine().setMadre(madre);
		}
		
		//padre origine se esiste prendo da anagrafica
		if(toReturn.getFamigliaOrigine().getCsAComponenteIdPadre() != null){
			CsPaiAffidoSoggFamigliaDTO padre = findSoggettoByComponente(toReturn.getFamigliaOrigine().getCsAComponenteIdPadre());
			toReturn.getFamigliaOrigine().setPadre(padre);
		}
		
		if(!toReturn.getSoggettiAffido().isEmpty()){
			for(CsPaiAffidoSoggettoDTO pas : toReturn.getSoggettiAffido()){
				BaseDTO bdto = new BaseDTO();
				bdto.setObj(PaiAffidoDominiEnum.RUOLO_SOGGETTO.name());
				bdto.setObj2(pas.getCodiceRuolo());
				bdto.setEnteId(dto.getEnteId());
				bdto.setSessionId(dto.getSessionId());
				pas.setDescrizioneRuolo(dominiPaiService.findDescrizioneAffidoByDominio(bdto));
			}
		}
		
		return toReturn;
	}
	
//	private CsPaiAffidoSoggFamigliaDTO findSoggettoByComponente(Long idComponente) throws Exception {
//		CsAComponente comp = affidoDAO.findComponenteById(idComponente);
//		
//		if(comp == null){
//			return null;
//		}
//		
//		CsPaiAffidoSoggFamigliaDTO soggetto = new CsPaiAffidoSoggFamigliaDTO();
//		soggetto.setCognome(comp.getCsAAnagrafica().getCognome());
//		soggetto.setNome(comp.getCsAAnagrafica().getNome());
//		soggetto.setIndirizzo(comp.getIndirizzoRes());
//		soggetto.setParentela(comp.getCsTbTipoRapportoCon().getDescrizione());
//		soggetto.setCellulare(comp.getCsAAnagrafica().getCell());
//		soggetto.setSesso(comp.getCsAAnagrafica().getSesso());
//		soggetto.setCf(comp.getCsAAnagrafica().getCf());
//		
//		return soggetto;
//	}
	
	@Override
	public CsPaiAffidoSoggFamigliaDTO findSoggettoByComponente(Long idComponente) throws Exception {
		CsAComponente comp = parentiDAO.findComponenteById(idComponente);
		
		if(comp == null){
			return null;
		}
		
		CsPaiAffidoSoggFamigliaDTO soggetto = new CsPaiAffidoSoggFamigliaDTO();
		soggetto.setCognome(comp.getCsAAnagrafica().getCognome());
		soggetto.setNome(comp.getCsAAnagrafica().getNome());
		soggetto.setIndirizzo(comp.getIndirizzoRes());
		soggetto.setParentela(comp.getCsTbTipoRapportoCon().getDescrizione());
		soggetto.setCellulare(comp.getCsAAnagrafica().getCell());
		soggetto.setSesso(comp.getCsAAnagrafica().getSesso());
		soggetto.setCf(comp.getCsAAnagrafica().getCf());
		
		return soggetto;
	}
	
	
	/*CODEC DTO*/
	public static CsPaiAffidoDTO toDTO(CsPaiAffido source){
		
		if(source == null){
			return null;
		}
		
		CsPaiAffidoDTO target = new CsPaiAffidoDTO();
		String[] ignore = {"famigliaOrigine","famigliaAffidataria","statiAffido","soggettiAffido"};
		BeanUtils.copyProperties(source, target, ignore);
		
		if(!source.getStatiAffido().isEmpty()){
			target.setStatiAffido(new ArrayList<CsPaiAffidoStatoDTO>());
			
			Collections.sort(source.getStatiAffido(), new Comparator<CsPaiAffidoStato>() {

				@Override
				public int compare(CsPaiAffidoStato o1, CsPaiAffidoStato o2) {
					
					if(o1.getDataA() == null){
						return -1;
					}
					
					if(o2.getDataA() == null){
						return 1;
					}
					
					if(o1.getDataA().before(o2.getDataA())){
						return 1;
					}else if(o1.getDataA().after(o2.getDataA())){
						return -1;
					}else{
						return 0;
					}
				}
			});
			
			for (CsPaiAffidoStato stato : source.getStatiAffido()) {
				target.getStatiAffido().add(toDTO(stato,target));
			}
		}
		
		if(source.getFamigliaAffidataria() != null){
			target.setFamigliaAffidataria(toDTO(source.getFamigliaAffidataria()));
		}
		
		if(source.getFamigliaOrigine() != null){
			target.setFamigliaOrigine(toDTO(source.getFamigliaOrigine()));
		}
		
		if(source.getSoggettiAffido() != null){
			for(CsPaiAffidoSoggetto as : source.getSoggettiAffido()){
				target.getSoggettiAffido().add(toDTO(as));
			}
		}
		
		return target;
	}
	
	public static CsPaiAffidoStatoDTO toDTO(CsPaiAffidoStato source, CsPaiAffidoDTO affido){
		CsPaiAffidoStatoDTO target = new CsPaiAffidoStatoDTO();
		BeanUtils.copyProperties(source, target, new String[]{"affido"});
		target.setAffido(affido);
		target.setDescrizione(PaiAffidoStatoEnum.getDescrizioneFromValore(source.getCodice()));
		return target;
	}
	
	public static CsPaiAffidoFamigliaOrigineDTO toDTO(CsPaiAffidoFamigliaOrigine source){
		CsPaiAffidoFamigliaOrigineDTO target = new CsPaiAffidoFamigliaOrigineDTO();
		String[] ignore = {"madre","padre"};
		BeanUtils.copyProperties(source, target,ignore);
		return target;
	}
	
	public static CsPaiAffidoFamigliaAffidatariaDTO toDTO(CsPaiAffidoFamigliaAffidataria source){
		CsPaiAffidoFamigliaAffidatariaDTO target = new CsPaiAffidoFamigliaAffidatariaDTO();
		BeanUtils.copyProperties(source, target,new String[]{"soggetti"});
		return target;
	}
	
	public static CsPaiAffidoSoggettoDTO toDTO(CsPaiAffidoSoggetto source){
		CsPaiAffidoSoggettoDTO target = new CsPaiAffidoSoggettoDTO();
		BeanUtils.copyProperties(source, target);
		return target;
	}
	
	
	/*CODEC ENTITY*/
	public static CsPaiAffido toEntity(CsPaiAffidoDTO source){
		CsPaiAffido target = new CsPaiAffido();
		String[] ignore = {"famigliaOrigine","famigliaAffidataria","statiAffido","soggettiAffido"};
		BeanUtils.copyProperties(source, target, ignore);
		
		if(!source.getStatiAffido().isEmpty()){
			target.setStatiAffido(new ArrayList<CsPaiAffidoStato>());
			for (CsPaiAffidoStatoDTO stato : source.getStatiAffido()) {
				target.getStatiAffido().add(toEntity(stato, target));
			}
		}
		
		if(source.getFamigliaAffidataria() != null){
			target.setFamigliaAffidataria(toEntity(source.getFamigliaAffidataria()));
		}
		
		if(source.getFamigliaOrigine() != null){
			target.setFamigliaOrigine(toEntity(source.getFamigliaOrigine()));
		}
		
		target.setSoggettiAffido(new ArrayList<CsPaiAffidoSoggetto>());
		if(!source.getSoggettiAffido().isEmpty()){
			for (CsPaiAffidoSoggettoDTO sogg : source.getSoggettiAffido()) {
				CsPaiAffidoSoggetto sPai = toEntity(sogg);
				//sPai.setAffido(target);
				target.getSoggettiAffido().add(sPai);
			}
		}
		
		return target;
	}
	
	public static CsPaiAffidoStato toEntity(CsPaiAffidoStatoDTO source, CsPaiAffido affido){
		CsPaiAffidoStato target = new CsPaiAffidoStato();
		BeanUtils.copyProperties(source, target, new String[]{"affido"});
		target.setAffido(affido);
		return target;
	}
	
	public static CsPaiAffidoFamigliaOrigine toEntity(CsPaiAffidoFamigliaOrigineDTO source){
		CsPaiAffidoFamigliaOrigine target = new CsPaiAffidoFamigliaOrigine();
		BeanUtils.copyProperties(source, target,new String[]{"padre,madre"});
		return target;
	}
	
	public static CsPaiAffidoFamigliaAffidataria toEntity(CsPaiAffidoFamigliaAffidatariaDTO source){
		CsPaiAffidoFamigliaAffidataria target = new CsPaiAffidoFamigliaAffidataria();
		BeanUtils.copyProperties(source, target,new String[]{"padre,madre"});
		
		if(source.getCsAComponenteIdMadre() == null){
			target.setBaseCognomeMadre(source.getMadre().getCognome());
			target.setBaseNomeMadre(source.getMadre().getNome());
			target.setBaseCfMadre(source.getMadre().getCf());
		}
		
		//salvo padre
		if(source.getCsAComponenteIdPadre() == null){
			target.setBaseCognomePadre(source.getPadre().getCognome());
			target.setBaseNomePadre(source.getPadre().getNome());
			target.setBaseCfPadre(source.getPadre().getCf());
		}
		
		return target;
	}
	
	public static CsPaiAffidoSoggetto toEntity(CsPaiAffidoSoggettoDTO source){
		CsPaiAffidoSoggetto target = new CsPaiAffidoSoggetto();
		BeanUtils.copyProperties(source, target);
		return target;
	}

}
