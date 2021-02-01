package it.webred.ct.data.access.basic.c336;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.c336.dao.C336GesPraticaDAO;
import it.webred.ct.data.access.basic.c336.dto.C336AllegatoDTO;
import it.webred.ct.data.access.basic.c336.dto.C336CommonDTO;
import it.webred.ct.data.access.basic.c336.dto.C336GridAttribCatA2DTO;
import it.webred.ct.data.access.basic.c336.dto.C336PraticaDTO;
import it.webred.ct.data.access.basic.c336.dto.C336SkGenFabbricatoDTO;
import it.webred.ct.data.access.basic.c336.dto.C336SkUiuDTO;
import it.webred.ct.data.access.basic.c336.dto.C336TabValIncrClsA4A3DTO;
import it.webred.ct.data.access.basic.c336.dto.C336TabValIncrClsA5A6DTO;
import it.webred.ct.data.model.c336.C336Allegato;
import it.webred.ct.data.model.c336.C336GesPratica;
import it.webred.ct.data.model.c336.C336GesPraticaPK;
import it.webred.ct.data.model.c336.C336GridAttribCatA2;
import it.webred.ct.data.model.c336.C336Pratica;
import it.webred.ct.data.model.c336.C336SkCarGenFabbricato;
import it.webred.ct.data.model.c336.C336SkCarGenUiu;
import it.webred.ct.data.model.c336.C336TabValIncrClsA4A3;
import it.webred.ct.data.model.c336.C336TabValIncrClsA5A6;

import java.util.Date;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class GestRichiestaServiceBean
 */
@Stateless
public class C336GesPraticaServiceBean extends CTServiceBaseBean implements C336GesPraticaService {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private C336GesPraticaDAO c336GesPraticaDAO;
	public C336Pratica nuovaPratica(C336PraticaDTO praticaDto) 	throws C336CommonServiceException {
		logger.debug("C336PraticaServiceBean - nuovaPratica()");
		try {
			return c336GesPraticaDAO.nuovaPratica(praticaDto); 
		}	
		catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
	}
	//Richiede: praticaDto.getPratica().getId()
	public void cambiaGestionePratica(C336PraticaDTO praticaDto) throws C336CommonServiceException {
		try {
			C336GesPratica gesPra = c336GesPraticaDAO.getGesAttualePratica(praticaDto.getPratica());
			c336GesPraticaDAO.fineGesPratica(gesPra);
			gesPra = new C336GesPratica();
			C336GesPraticaPK pk = new C336GesPraticaPK();
			pk.setIdPratica(praticaDto.getPratica().getIdPratica());
			pk.setDtIniGes(new Date());
			pk.setUserName(praticaDto.getUserId());
			gesPra.setId(pk);
			c336GesPraticaDAO.iniziaGesPratica(gesPra);
						
		}	
		catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
		
	}
	public C336Pratica modificaPratica(C336PraticaDTO praticaDto)		throws C336CommonServiceException {
		logger.debug("C336PraticaServiceBean - nuovaPratica()");
		try {
			return c336GesPraticaDAO.modificaPratica(praticaDto); 
		}	
		catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
	}
	public C336PraticaDTO chiudiIstrPratica(C336PraticaDTO praticaDto)		throws C336CommonServiceException {
		C336Pratica pra = praticaDto.getPratica();
		try {
			C336Pratica praUpd =c336GesPraticaDAO.chiudiPratica(pra);
			C336GesPratica ges = c336GesPraticaDAO.getGesAttualePratica(pra);
			ges.setDtFinGes(new Date());
			c336GesPraticaDAO.fineGesPratica(ges);
			praticaDto.setPratica(praUpd);
			
		}	
		catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
		return praticaDto;
	}
	public void eliminaAllegato(C336CommonDTO obj)	throws C336CommonServiceException {
		try {
			c336GesPraticaDAO.eliminaAllegato(obj.getIdAllegato());	
		}
		catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
		
	}
	public C336Allegato inserisciAllegato(C336AllegatoDTO allegatoDto)	throws C336CommonServiceException {
		try {
			return c336GesPraticaDAO.nuovoAllegato(allegatoDto.getAllegato());	}
		catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
	}
	public C336SkCarGenFabbricato modificaSkFabbricato(C336SkGenFabbricatoDTO skFabbrDto) throws C336CommonServiceException {
		try {
			return c336GesPraticaDAO.modificaSkFabbricato(skFabbrDto.getSchedaFabbr());
		}
		catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
	}
	public void nuovaSkFabbricato(C336SkGenFabbricatoDTO skFabbrDto)throws C336CommonServiceException {
		try {
			c336GesPraticaDAO.nuovaSkFabbricato(skFabbrDto.getSchedaFabbr());
		}
		catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
	}
	public C336SkCarGenUiu modificaSkUiu(C336SkUiuDTO skUiuDto) throws C336CommonServiceException {
		try {
			return c336GesPraticaDAO.modificaSkUiu(skUiuDto.getSchedaUI());
		}
		catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
	}
	public void nuovaSkUiu(C336SkUiuDTO skUiuDto) throws C336CommonServiceException {
		try {
			c336GesPraticaDAO.nuovaSkUiu(skUiuDto.getSchedaUI());
		}
		catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
		
	}
	
	public C336TabValIncrClsA4A3 modificaTabValutIncrClasseA4A3(C336TabValIncrClsA4A3DTO dto) throws C336CommonServiceException {
		try{	
			return c336GesPraticaDAO.modificaTabValutIncrClasseA4A3(dto.getGriglia());
		}catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
	}
	
	public C336TabValIncrClsA5A6 modificaTabValutIncrClasseA5A6(C336TabValIncrClsA5A6DTO dto) throws C336CommonServiceException {
		try{	
			return c336GesPraticaDAO.modificaTabValutIncrClasseA5A6(dto.getGriglia());
		}catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
	}
	
	public void nuovaTabValutIncrClasseA4A3(C336TabValIncrClsA4A3DTO dto) throws C336CommonServiceException{
		try{	
			 c336GesPraticaDAO.nuovaTabValutIncrClasseA4A3(dto.getGriglia());
		}catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
		
	}
	
	public void nuovaTabValutIncrClasseA5A6(C336TabValIncrClsA5A6DTO dto)throws C336CommonServiceException {
		try{	
			 c336GesPraticaDAO.nuovaTabValutIncrClasseA5A6(dto.getGriglia());
		}catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
	}
	
	public C336GridAttribCatA2 modificaGridAttribCat2(C336GridAttribCatA2DTO dto) {
		try{	
			 return c336GesPraticaDAO.modificaGridAttribCat2(dto.getGriglia());
		}catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
	}
	
	public void nuovaGridAttribCat2(C336GridAttribCatA2DTO dto) {
		try{	
			 c336GesPraticaDAO.nuovaGridAttribCat2(dto.getGriglia());
		}catch(C336PraticaServiceException cde) {
			logger.error(cde.getMessage(), cde);
			throw new C336CommonServiceException(cde);
		}
		
	}

}
