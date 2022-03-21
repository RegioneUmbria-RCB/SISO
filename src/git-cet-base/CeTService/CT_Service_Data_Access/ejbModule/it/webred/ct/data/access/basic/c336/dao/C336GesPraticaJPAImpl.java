package it.webred.ct.data.access.basic.c336.dao;
 
import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.c336.C336PraticaServiceException;
import it.webred.ct.data.access.basic.c336.dto.C336PraticaDTO;
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

import javax.persistence.Query;

public class C336GesPraticaJPAImpl extends CTServiceBaseDAO implements C336GesPraticaDAO{

	private static final long serialVersionUID = 1L;

	public C336Pratica nuovaPratica(C336PraticaDTO praticaDto) throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-nuovaPratica()" );
		C336Pratica pratica  = praticaDto.getPratica();
		try {
			manager_diogene.persist(pratica);
			//inserisce GesPratica
			Long idPratica = pratica.getIdPratica();
			praticaDto.getPratica().setIdPratica(idPratica);
			C336GesPratica gesPratica = new C336GesPratica ();
			C336GesPraticaPK pk = new C336GesPraticaPK(); 
			pk.setDtIniGes(new Date());
			pk.setIdPratica(idPratica);
			pk.setUserName(praticaDto.getPratica().getUserNameIni());
			gesPratica.setId(pk);
			iniziaGesPratica(gesPratica);
			logger.debug("PraticaJpaImpl-pratica creata-->id: )" + idPratica );
			return pratica;
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	public C336Pratica modificaPratica(C336PraticaDTO praticaDto) throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-modificaPratica()-->id: )" + praticaDto.getPratica().getIdPratica() );
		C336Pratica pratica  = praticaDto.getPratica();
		try {
			C336Pratica praticaUpd = manager_diogene.merge(pratica);
			return praticaUpd;
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	public C336Pratica getPraticaByPK(C336PraticaDTO praticaDto) 	throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-getPraticaByPK()-->idPra: " + praticaDto.getPratica().getIdPratica() );
		try {
			C336Pratica pra= manager_diogene.find(C336Pratica.class, praticaDto.getPratica().getIdPratica());
			return pra;
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}

	public void fineGesPratica(C336GesPratica gesPra) throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-fineGesPratica()-->idPra: " + gesPra.getId().getIdPratica() );
		try {
			gesPra.setDtFinGes(new Date());
			manager_diogene.merge(gesPra);	
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
		
	}

	public void iniziaGesPratica(C336GesPratica gesPratica) throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-iniziaGesPratica()-->idPra: " + gesPratica.getId().getIdPratica() );
		try {
			manager_diogene.persist(gesPratica);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
		
	}

	public C336GesPratica getGesAttualePratica(C336Pratica pratica)	throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-getGestioneAttualePratica()-->idPra: " +  pratica.getIdPratica());
		Query q = null;
		C336GesPratica result=null;
		try {
			q = manager_diogene.createNamedQuery("C336GesPratica.getGestioneAttualePratica");
			q.setParameter("idPratica", pratica.getIdPratica());
			result = (C336GesPratica) q.getSingleResult();
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
		return result;
	}
	public C336Pratica chiudiPratica(C336Pratica pratica)	throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-chiudiPratica()-->idPra: " +  pratica.getIdPratica());
		try {
			C336Pratica praticaUpd = manager_diogene.merge(pratica);
			return praticaUpd;
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	public void eliminaAllegato(Long idAllegato)	throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-eliminaAllegato()-->idAllegato: " +  idAllegato);
		try {
			C336Allegato all = manager_diogene.getReference(C336Allegato.class, idAllegato);
			manager_diogene.remove(all);		
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
		
	}
	public C336Allegato nuovoAllegato(C336Allegato allegato) throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-nuovoAllegato()-->idPratica: " +  allegato.getIdPratica());
		try {
			manager_diogene.persist(allegato);
			String nomeFile =  String.format("%010d", allegato.getIdAllegato());
			allegato.setNomeFile(nomeFile);
			return allegato;
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	public C336SkCarGenFabbricato modificaSkFabbricato(C336SkCarGenFabbricato skFabbr)			throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-modificaSkFabbricato()-->idPratica: " +  skFabbr.getIdPratica());
		try {
			return manager_diogene.merge(skFabbr);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	public void nuovaSkFabbricato(C336SkCarGenFabbricato skFabbr)	throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-nuovaSkFabbricato()-->idPratica: " +  skFabbr.getIdPratica());
		try {
			
			 manager_diogene.persist(skFabbr);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	public C336SkCarGenUiu modificaSkUiu(C336SkCarGenUiu skUiu)	throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-modificaSkUiu()-->idPratica: " +  skUiu.getIdPratica());
		try {
			return manager_diogene.merge(skUiu);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	public void nuovaSkUiu(C336SkCarGenUiu skUiu) throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-nuovaSkUiu()" );
		try {
			
			 manager_diogene.persist(skUiu);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	public C336TabValIncrClsA4A3 modificaTabValutIncrClasseA4A3(C336TabValIncrClsA4A3 griglia) throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-modificaTabValutIncrClasseA4A3()-->idPratica: " +  griglia.getIdPratica());
		try {
			return manager_diogene.merge(griglia);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	public C336TabValIncrClsA5A6 modificaTabValutIncrClasseA5A6(C336TabValIncrClsA5A6 griglia) throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-modificaTabValutIncrClasseA5A6()-->idPratica: " +  griglia.getIdPratica());
		try {
			return manager_diogene.merge(griglia);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	public void nuovaTabValutIncrClasseA4A3(C336TabValIncrClsA4A3 griglia)
			throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-nuovaTabValutIncrClasseA4A3()-->idPratica: " +  griglia.getIdPratica());
		try {
			
			 manager_diogene.persist(griglia);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
		
	}
	public void nuovaTabValutIncrClasseA5A6(C336TabValIncrClsA5A6 griglia)
			throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-nuovaTabValutIncrClasseA5A6()-->idPratica: " +  griglia.getIdPratica());
		try {
			
			 manager_diogene.persist(griglia);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
		
	}
	public C336GridAttribCatA2 modificaGridAttribCat2(
			C336GridAttribCatA2 griglia) throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-modificaGridAttribCat2()-->idPratica: " +  griglia.getIdPratica());
		try {
			return manager_diogene.merge(griglia);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	public void nuovaGridAttribCat2(C336GridAttribCatA2 griglia)
			throws C336PraticaServiceException {
		logger.debug("PraticaJpaImpl-nuovaGridAttribCat2()-->idPratica: " +  griglia.getIdPratica());
		try {
			
			 manager_diogene.persist(griglia);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new C336PraticaServiceException(t);
		}
	}
	
}
