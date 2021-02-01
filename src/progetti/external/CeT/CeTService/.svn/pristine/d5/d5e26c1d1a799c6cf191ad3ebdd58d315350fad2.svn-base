package it.webred.ct.data.access.basic.locazioni.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.locazioni.LocazioniQueryBuilder;
import it.webred.ct.data.access.basic.locazioni.LocazioniServiceException;
import it.webred.ct.data.access.basic.locazioni.dto.RicercaLocazioniDTO;
import it.webred.ct.data.model.locazioni.LocazioniA;
import it.webred.ct.data.model.locazioni.LocazioniB;
import it.webred.ct.data.model.locazioni.LocazioniI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class LocazioniJPAImpl extends CTServiceBaseDAO implements LocazioniDAO {
	
	private static final long serialVersionUID = -7466005628280424924L;

	public List<LocazioniA> getLocazioniByCF(RicercaLocazioniDTO rl) throws LocazioniServiceException {
		List<LocazioniA> lista=null;
		logger.debug("LOCAZIONI PER CF[CF "+rl.getCodFis()+"];" + "[DATA_RIF(OPZ): "+rl.getDtRif()+"]" );
		try {
			Query q = null;
			if (rl.getDtRif()==null)
				q = manager_diogene.createNamedQuery("Locazioni_A.getLocazioniByCF");
			else
				q =	manager_diogene.createNamedQuery("Locazioni_A.getLocazioniByCFAllaData");
			q.setParameter("codFis", rl.getCodFis());
			if (rl.getDtRif()!= null)
				q.setParameter("dtRif", rl.getDtRif());
			lista =(List<LocazioniA>) q.getResultList();
			if (lista != null && lista.size()>0){
				logger.warn("TROVATO. N.ELE: "+ lista.size());
			}else
				logger.warn("TROVATO. N.ELE: 0 ");

		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		return lista;
	}//-------------------------------------------------------------------------
	
	public List<LocazioniA> getLocazioniByInquilinoCF(RicercaLocazioniDTO rl) throws LocazioniServiceException {
		List<LocazioniA> lista=null;
		logger.debug("LOCAZIONI PER INQUILINO CF[CF "+rl.getCodFis()+"];" + "[DATA_RIF(OPZ): "+rl.getDtRif()+"]" );
		try {
			Query q = null;
			if (rl.getDtRif()==null)
				q = manager_diogene.createNamedQuery("Locazioni_A.getLocazioniByInquilinoCF");
			else
				q =	manager_diogene.createNamedQuery("Locazioni_A.getLocazioniByInquilinoCFAllaData");
			q.setParameter("codFis", rl.getCodFis());
			q.setParameter("tipoSogg", "A");
			if (rl.getDtRif()!= null)
				q.setParameter("dtRif", rl.getDtRif());
			lista =(List<LocazioniA>) q.getResultList();
			logger.warn("TROVATO. N.ELE: "+ lista.size());
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		return lista;
	}
	
	public LocazioniB getLocazioneSoggByKey(RicercaLocazioniDTO rl) throws LocazioniServiceException {
		LocazioniB loc=null;
		logger.debug("LOCAZIONI PER KEY[Ufficio "+rl.getKey().getUfficio()+"];[anno "+rl.getKey().getAnno()+"];[Serie "+rl.getKey().getSerie()+"];[Numero "+rl.getKey().getNumero()+"];[Prog_Sogg "+rl.getKey().getProgSoggetto()+"];" +"[DATA_RIF(OPZ): "+rl.getDtRif()+"]" );
		try {
			Query q = null;
			q = manager_diogene.createNamedQuery("Locazioni_B.getSoggByKey");
			q.setParameter("ufficio", rl.getKey().getUfficio());
			q.setParameter("anno", rl.getKey().getAnno());
			q.setParameter("serie", rl.getKey().getSerie());
			q.setParameter("numero", rl.getKey().getNumero());
			q.setParameter("progSogg", rl.getKey().getProgSoggetto());
			loc =(LocazioniB) q.getSingleResult();
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		return loc;
	}//-------------------------------------------------------------------------
	
	public List<LocazioniB> getLocazioneSoggByChiave(RicercaLocazioniDTO rl) throws LocazioniServiceException {
		List<LocazioniB> lstLocB=null;
		logger.debug("LOCAZIONI PER KEY[Ufficio "+rl.getKey().getUfficio()+"];[anno "+rl.getKey().getAnno()+"];[Serie "+rl.getKey().getSerie()+"];[Numero "+rl.getKey().getNumero()+"];" +"[DATA_RIF(OPZ): "+rl.getDtRif()+"]" );
		try {
			Query q = null;
			q = manager_diogene.createNamedQuery("Locazioni_B.getSoggByChiave");
			q.setParameter("ufficio", rl.getKey().getUfficio());
			q.setParameter("anno", rl.getKey().getAnno());
			q.setParameter("serie", rl.getKey().getSerie());
			q.setParameter("numero", rl.getKey().getNumero());
			//q.setParameter("progSogg", rl.getKey().getProgSoggetto());
			lstLocB = q.getResultList();
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		return lstLocB;
	}//-------------------------------------------------------------------------
	
	public LocazioniB getLocazioneSoggByKeyCodFisc(RicercaLocazioniDTO rl) throws LocazioniServiceException {
		LocazioniB loc=null;
		logger.debug("LOCAZIONI PER KEY[Ufficio "+rl.getKey().getUfficio()+"];[anno "+rl.getKey().getAnno()+"];[Serie "+rl.getKey().getSerie()+"];[Numero "+rl.getKey().getNumero()+"];[CodFisc "+rl.getCodFis()+"];" +"[DATA_RIF(OPZ): "+rl.getDtRif()+"]" );
		try {
			Query q = null;
			q = manager_diogene.createNamedQuery("Locazioni_B.getSoggByKeyCodFisc");
			q.setParameter("ufficio", rl.getKey().getUfficio());
			q.setParameter("anno", rl.getKey().getAnno());
			q.setParameter("serie", rl.getKey().getSerie());
			q.setParameter("numero", rl.getKey().getNumero());
			q.setParameter("codFisc", rl.getCodFis());
			List lista = q.getResultList();
			if(lista.size()> 0)
				loc = (LocazioniB) lista.get(0);
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		return loc;
	}
	
	public List<LocazioniA> getLocazioniOggByKey(RicercaLocazioniDTO rl) throws LocazioniServiceException {
		List<LocazioniA> lista=null;
		logger.debug("LOCAZIONI PER KEY[Ufficio "+rl.getKey().getUfficio()+"];[anno "+rl.getKey().getAnno()+"];[Serie "+rl.getKey().getSerie()+"];[Numero "+rl.getKey().getNumero()+"];" +"[DATA_RIF(OPZ): "+rl.getDtRif()+"]" );
		try {
			Query q = null;
			if (rl.getDtRif()==null)
				q = manager_diogene.createNamedQuery("Locazioni_A.getOggByKey");
			else
				q =	manager_diogene.createNamedQuery("Locazioni_A.getOggByKeyAllaData");
			q.setParameter("ufficio", rl.getKey().getUfficio());
			q.setParameter("anno", rl.getKey().getAnno());
			q.setParameter("serie", rl.getKey().getSerie());
			q.setParameter("numero", rl.getKey().getNumero());
			if (rl.getDtRif()!= null)
				q.setParameter("dtRif", rl.getDtRif());
			lista =(List<LocazioniA>) q.getResultList();
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		return lista;
	}

	@Override
	public List<LocazioniB> getInquiliniByOgg(RicercaLocazioniDTO rl) throws LocazioniServiceException {
		List<LocazioniB>  lista=null;
		logger.debug("LOCAZIONI PER KEY[Ufficio "+rl.getKey().getUfficio()+"];[anno "+rl.getKey().getAnno()+"];[Serie "+rl.getKey().getSerie()+"];[Numero "+rl.getKey().getNumero()+"];[Prog_Sogg "+rl.getKey().getProgSoggetto()+"];" +"[DATA_RIF(OPZ): "+rl.getDtRif()+"]" );
		try {
			Query q = null;
			q = manager_diogene.createNamedQuery("Locazioni_B.getInquiliniByOgg");
			q.setParameter("ufficio", rl.getKey().getUfficio());
			q.setParameter("anno", rl.getKey().getAnno());
			q.setParameter("serie", rl.getKey().getSerie());
			q.setParameter("numero", rl.getKey().getNumero());
			q.setParameter("tipoSogg", "A");
			lista =(List<LocazioniB>) q.getResultList();
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		return lista;
	}
	
	public List<LocazioniA> getLocazioniCivicoAllaData(RicercaCivicoDTO rc){
		List<LocazioniA> lista = new ArrayList<LocazioniA>();
		
		Date dataRif = rc.getDataRif();
		if(dataRif == null)
			dataRif = new Date();
		
		logger.debug ("LOCAZIONI AL CIVICO ALLA DATA" +
				"[IdViaUnico: "     +rc.getIdVia()+"];" +
				"[Civico: "  +rc.getCivico()+"];" +
				"[Data: "    +rc.getDataRif()+"]"); 
		try {
			String indirizzo = rc.getToponimoVia()+" "+rc.getDescrizioneVia()+" "+rc.getCivico();
			Query q = null;
			q = manager_diogene.createNamedQuery("LocazioniA.getLocazioniCivicoAllaData");
			q.setParameter("idViaUnico", rc.getDescrizioneVia());
			q.setParameter("civico", rc.getCivico());
			q.setParameter("dtRif", dataRif);
			
			lista =(List<LocazioniA>) q.getResultList();
			
			logger.debug ("Result Size[" + lista.size() +"]");
		
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		
		return lista;
	}

	@Override
	public List<LocazioniI> getImmobiliByKey(RicercaLocazioniDTO rl) throws LocazioniServiceException {
		List<LocazioniI>  lista=null;
		logger.debug("LOCAZIONI IMMOBILI PER KEY[Ufficio "+rl.getKey().getUfficio()+"];[anno "+rl.getKey().getAnno()+"];[Serie "+rl.getKey().getSerie()+"];[Numero "+rl.getKey().getNumero()+"];[Prog_Sogg "+rl.getKey().getProgSoggetto()+"];" +"[DATA_RIF(OPZ): "+rl.getDtRif()+"]" );
		try {
			Query q = null;
			q = manager_diogene.createNamedQuery("Locazioni_I.getImmobiliByKey");
			q.setParameter("ufficio", rl.getKey().getUfficio());
			q.setParameter("anno", rl.getKey().getAnno());
			q.setParameter("serie", rl.getKey().getSerie());
			q.setParameter("numero", rl.getKey().getNumero());
			
			lista =(List<LocazioniI>) q.getResultList();
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		return lista;
	}
	
	//GITOUT WS7
	@Override
	public List<LocazioniA> getLocazioniByCoord(RicercaLocazioniDTO rl) throws LocazioniServiceException {
		List<LocazioniA> lista=null;
		logger.debug("LOCAZIONI PER COORD[SEZ "+rl.getSezione()+"];" + "[FOGLIO: "+rl.getFoglio()+"]" + "[PARTICELLA: "+rl.getParticella()+"]" + "[SUBALTERNO: "+rl.getSubalterno()+"]" );
		try {
			Query q = null;
			q =	manager_diogene.createNamedQuery("Locazioni_A.getLocazioniByCoord");
			q.setParameter("sezUrbana", rl.getSezione());
			q.setParameter("foglio", rl.getFoglio());
			q.setParameter("particella", rl.getParticella());
			q.setParameter("subalterno", rl.getSubalterno());
			if (rl.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rl.getLimit());
			
			lista =(List<LocazioniA>) q.getResultList();
			if (lista != null && lista.size()>0)
				logger.warn("TROVATO. N.ELE: "+ lista.size());
			else
				logger.warn("TROVATO. N.ELE: 0");
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		return lista;
	}//-------------------------------------------------------------------------
	
	public List<Object[]> getLocazioniByCriteria(RicercaLocazioniDTO rl) {
		List<Object[]> lista=null;
		logger.debug("LOCAZIONI PER COORD[SEZ "+rl.getSezione()+"];" + "[FOGLIO: "+rl.getFoglio()+"]" + "[PARTICELLA: "+rl.getParticella()+"]" + "[SUBALTERNO: "+rl.getSubalterno()+"]" );
		try {
			String hql = new LocazioniQueryBuilder().createQueryLocazioniByCriteria(rl);
					
			Query q = manager_diogene.createQuery(hql);

			if (rl.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rl.getLimit());
			
			lista = q.getResultList();
			if (lista != null && lista.size()>0)
				logger.warn("TROVATO. N.ELE: "+ lista.size());
			else
				logger.warn("TROVATO. N.ELE: 0");
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		return lista;
	}//-------------------------------------------------------------------------
	
	public List<Object[]> getLocazioniByParams(RicercaLocazioniDTO rl) {
		List<Object[]> lista=null;
		logger.debug("LOCAZIONI PER CODICE FISCALE e COINVOLGIMENTO [CF "+rl.getCodFis()+"];" + "[COINV: "+rl.getTipoCoinvolgimento()+"]" );
		try {
			String hql = new LocazioniQueryBuilder().createQueryLocazioniByParams(rl);
					
			Query q = manager_diogene.createQuery(hql);

			if (rl.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rl.getLimit());
			
			lista = q.getResultList();
			if (lista != null && lista.size()>0)
				logger.warn("TROVATO. N.ELE: "+ lista.size());
			else
				logger.warn("TROVATO. N.ELE: 0");
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		return lista;
	}//-------------------------------------------------------------------------
	
}	
