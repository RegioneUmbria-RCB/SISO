package it.webred.cs.csa.ejb.ejb;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTablePaiSALSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.PaiSalDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.sal.CsPaiSALFaseDTO;
import it.webred.cs.csa.ejb.dto.pai.sal.CsPaiSALStoricoDTO;
import it.webred.cs.csa.ejb.dto.pai.sal.CsPaiSalDTO;
import it.webred.cs.csa.ejb.dto.pai.sal.PaiSALFaseEnum;
import it.webred.cs.data.model.sal.CsPaiSAL;
import it.webred.cs.data.model.sal.CsPaiSALFase;
import it.webred.cs.data.model.sal.CsPaiSalStorico;
import it.webred.ct.support.validation.ValidationStateless;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTablePaiSALSessionBean  extends CarSocialeBaseSessionBean implements AccessTablePaiSALSessionBeanRemote{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private PaiSalDAO salDAO;

	@Override
	public CsPaiSalDTO saveSAL(BaseDTO dto) throws Exception {
		CsPaiSalDTO sal = (CsPaiSalDTO) dto.getObj();
		
    	//gestione fase
		Integer codiceUltimaFase = (Integer) dto.getObj2();
	
		Date dataFaseSal = (Date) dto.getObj3();
		//se la fase è diversa -> deve essere aggiornata
		if(!codiceUltimaFase.equals(sal.getCodiceFaseAttuale())){
			
			for(CsPaiSALFaseDTO fase : sal.getFasiSAL()){
				if(fase.getDataA() == null){
					//chiudo ultimo stato
					fase.setDataA(new Date());
					break;
				}
			}
			
			CsPaiSALFaseDTO nuovaFase = new CsPaiSALFaseDTO();
			nuovaFase.setSal(sal);
			nuovaFase.setCodice(codiceUltimaFase);
			nuovaFase.setDataDa(new Date());
			nuovaFase.setDataFaseSal(dataFaseSal);
			
			sal.getFasiSAL().add(nuovaFase);
			
			}
		else{
			for(CsPaiSALFaseDTO fase : sal.getFasiSAL()){
				if(fase.getDataA() == null){
					fase.setDataFaseSal(dataFaseSal);
					break;
				}
			}
			
			
		}

		//recupero lo storico a presciindere dal fatto che ho cambiato fase o meno
		//qui devo salvare lo storcio con le info sul tutor e la mansione
		if(sal.getId()!=null) {//Se l'id è null sono il sal è nuovo e non ha storico
	    	CsPaiSAL  salPrecedente = salDAO.findById(sal.getId());
		
			if (sal.getTutorContesto() != null && !sal.getTutorContesto().trim().isEmpty() &&
					!sal.getTutorContesto().equals(salPrecedente.getTutorContesto())) {

				CsPaiSALStoricoDTO storicoDTO = new CsPaiSALStoricoDTO();
				storicoDTO.setSal(sal);
				storicoDTO.setTutorContesto(salPrecedente.getTutorContesto());
				storicoDTO.setMansione(null);
				storicoDTO.setDtIns(new Date());
				storicoDTO.setUserIns(dto.getUserId());

				sal.getStoricoSAL().add(storicoDTO);
			}

	
		if (sal.getMansione()!=null && !sal.getMansione().trim().isEmpty() && !sal.getMansione().equals(salPrecedente.getMansione()) ){

			CsPaiSALStoricoDTO storicoDTO = new CsPaiSALStoricoDTO();
			storicoDTO.setSal(sal);
			storicoDTO.setTutorContesto(null);
			storicoDTO.setMansione(salPrecedente.getMansione());
			storicoDTO.setDtIns(new Date());
			storicoDTO.setUserIns(dto.getUserId());
			
			sal.getStoricoSAL().add(storicoDTO);
	    	}
		
		}
		CsPaiSAL res = salDAO.saveSAL(toEntity(sal));
		return toDTO(res);
	}
	
	/*CODEC ENTITY*/
	public static CsPaiSAL toEntity(CsPaiSalDTO source) throws IllegalAccessException, InvocationTargetException{
		CsPaiSAL target = new CsPaiSAL();
		String[] ignore = {"fasiSAL","storicoSAL", "lstStoricoTutor", "lstStoricoMansioni"};
		BeanUtils.copyProperties(source, target, ignore);
		if (target.getId() != null && target.getId() == 0)
			target.setId(null);
	
		
		if(!source.getFasiSAL().isEmpty()){
			target.setFasiSAL(new ArrayList<CsPaiSALFase>());   
			for (CsPaiSALFaseDTO fase : source.getFasiSAL()) {
				target.getFasiSAL().add(toEntity(fase, target));
			}
		}
		
		target.setStoricoSal(new ArrayList<CsPaiSalStorico>());
		if(!source.getStoricoSAL().isEmpty()){
			for(CsPaiSALStoricoDTO storico : source.getStoricoSAL()) {
				target.getStoricoSal().add(toEntity(storico,target));
			}
		}
		
		
		return target;
	}
	public static CsPaiSALFase toEntity(CsPaiSALFaseDTO source, CsPaiSAL sal) throws IllegalAccessException, InvocationTargetException{
		CsPaiSALFase target = new CsPaiSALFase();
        String[] ignore = {"sal","paiSal"};
		BeanUtils.copyProperties(source, target, ignore);
	
		target.setSal(sal);
		return target;
	}
	
	public static CsPaiSalStorico toEntity(CsPaiSALStoricoDTO source, CsPaiSAL sal) throws IllegalAccessException, InvocationTargetException{
		CsPaiSalStorico target = new CsPaiSalStorico();
		String[] ignore = {"sal"};
		BeanUtils.copyProperties(source, target, ignore);
				
		target.setPaiSal(sal);
		
		return target;
	}
	

	/*CODEC DTO*/
	public static CsPaiSalDTO toDTO(CsPaiSAL source) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		
		if(source == null){
			return null;
		}
		
		CsPaiSalDTO target = new CsPaiSalDTO();
		String[] ignore = {"storicoSal", "fasiSAL"};
		BeanUtils.copyProperties(source, target, ignore);
		if(!source.getFasiSAL().isEmpty()){
//			target.setFasiSAL(new ArrayList<CsPaiSALFaseDTO>());
			
			Collections.sort(source.getFasiSAL(), new Comparator<CsPaiSALFase>() {

				@Override
				public int compare(CsPaiSALFase o1, CsPaiSALFase o2) {
					
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
			
			for (CsPaiSALFase fase : source.getFasiSAL()) {
				target.getFasiSAL().add(toDTO(fase,target));
			}
		}

		
		if(source.getStoricoSal() != null){
			for(CsPaiSalStorico as : source.getStoricoSal()){
				target.getStoricoSAL().add(toDTO(as));
			}
		}
		
		return target;
	}
	public static CsPaiSALFaseDTO toDTO(CsPaiSALFase source, CsPaiSalDTO sal) throws IllegalAccessException, InvocationTargetException{
		CsPaiSALFaseDTO target = new CsPaiSALFaseDTO();
		BeanUtils.copyProperties(source, target, new String[]{"paiSal", "sal"});
		target.setSal(sal);
		target.setDescrizione(PaiSALFaseEnum.getDescrizioneFromValore(source.getCodice()));
		return target;
	}
	
	public static CsPaiSALStoricoDTO toDTO(CsPaiSalStorico source) throws IllegalAccessException, InvocationTargetException{
		CsPaiSALStoricoDTO target = new CsPaiSALStoricoDTO();
		BeanUtils.copyProperties(source, target);
		return target;
	}

	@Override
	public CsPaiSalDTO findSalByDiarioPaiId(BaseDTO dto) throws Exception {
		CsPaiSAL res = salDAO.findSalByDiarioPaiId((Long)dto.getObj());
		CsPaiSalDTO toReturn = toDTO(res);
		
		if(!toReturn.getStoricoSAL().isEmpty()){
			for(CsPaiSALStoricoDTO pas : toReturn.getStoricoSAL()){
//				BaseDTO bdto = new BaseDTO();
//				bdto.setObj(PaiAffidoDominiEnum.RUOLO_SOGGETTO.name());
//				bdto.setObj2(pas.getCodiceRuolo());
//				bdto.setEnteId(dto.getEnteId());
//				bdto.setSessionId(dto.getSessionId());
//				pas.setDescrizioneRuolo(getDescrizioneDominio(bdto));
			}
		}
		
		return toReturn;
	}
	
}
