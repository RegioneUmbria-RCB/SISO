package it.webred.ct.data.access.basic.redditi.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.ici.IciServiceException;
import it.webred.ct.data.access.basic.redditi.RedditiServiceException;
import it.webred.ct.data.access.basic.redditi.dto.KeySoggDTO;
import it.webred.ct.data.access.basic.redditi.dto.KeyTrascodificaDTO;
import it.webred.ct.data.model.ici.SitTIciSogg;
import it.webred.ct.data.model.redditi.RedDatiAnagrafici;
import it.webred.ct.data.model.redditi.RedDescrizione;
import it.webred.ct.data.model.redditi.RedDomicilioFiscale;
import it.webred.ct.data.model.redditi.RedRedditiDichiarati;
import it.webred.ct.data.model.redditi.RedTrascodifica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class RedditiJPAImpl extends CTServiceBaseDAO implements RedditiDAO {

	@Override
	public List<RedDatiAnagrafici> getListaSoggettiByCF(RicercaSoggettoDTO rs)	throws RedditiServiceException {
		 List<RedDatiAnagrafici> lista=new ArrayList<RedDatiAnagrafici>();
		 logger.debug("RedditiJPAImpl.getListaSoggByCF ->LISTA SOGGETTI REDDITI - CF["+rs.getCodFis()+"]");
			try { 
				Query q = manager_diogene.createNamedQuery("RedDatiAnagrafici.getListaSoggettiByCF");
				q.setParameter("codFis", rs.getCodFis());
				lista = q.getResultList();
				logger.debug("Result size ["+lista.size()+"]");
				
			}catch (NoResultException nre) {
				logger.warn("getListaSoggByCF - No Result " + nre.getMessage());
			} catch (Throwable t) {
				logger.error("", t);
				throw new RedditiServiceException(t);
			}
		 return lista;
	}
	@Override
	public List<RedDatiAnagrafici> getListaSoggettiPFByDatiAna(RicercaSoggettoDTO rs) throws RedditiServiceException {
		 List<RedDatiAnagrafici> lista=new ArrayList<RedDatiAnagrafici>();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		 String dtNasStr= sdf.format(rs.getDtNas());
		 logger.debug("RedditiJPAImpl.getListaSoggettiPFByDatiAna->LISTA SOGGETTI PF REDDITI - COGNOME["+rs.getCognome()+"]; NOME["+rs.getNome()+"]; DT-NAS["+rs.getDtNas()+"]; COM.NAS["+rs.getCodComNas()+"]");
			try { 
				Query q = manager_diogene.createNamedQuery("RedDatiAnagrafici.getListaSoggettiPFByDatiAna");
				q.setParameter("cognome", rs.getCognome());
				q.setParameter("nome", rs.getNome());
				q.setParameter("dtNas", dtNasStr);
				q.setParameter("codComNas", rs.getCodComNas());
				lista = q.getResultList();
				logger.debug("Result size ["+lista.size()+"]");
				
			}catch (NoResultException nre) {
				logger.warn("getListaSoggettiPFByDatiAna - No Result " + nre.getMessage());
			} catch (Throwable t) {
				logger.error("", t);
				throw new RedditiServiceException(t);
			}
		 return lista;
	}

	@Override
	public List<RedDatiAnagrafici> getListaSoggettiPGByDatiAna(RicercaSoggettoDTO rs) throws RedditiServiceException {
		List<RedDatiAnagrafici> lista=new ArrayList<RedDatiAnagrafici>();
		 logger.debug("RedditiJPAImpl.getListaSoggettiPGByDatiAna->LISTA SOGGETTI PG REDDITI - DENOMINAZIONE["+rs.getDenom()+"]");
			try { 
				Query q = manager_diogene.createNamedQuery("RedDatiAnagrafici.getListaSoggettiPGByDatiAna");
				q.setParameter("denom", rs.getDenom());
				lista = q.getResultList();
				logger.debug("Result size ["+lista.size()+"]");
				
			}catch (NoResultException nre) {
				logger.warn("getListaSoggettiPGByDatiAna - No Result " + nre.getMessage());
			} catch (Throwable t) {
				logger.error("", t);
				throw new RedditiServiceException(t);
			}
		 return lista;
	}

	@Override
	public RedDatiAnagrafici getSoggettoByKey(KeySoggDTO key) throws RedditiServiceException {
		RedDatiAnagrafici ele=null;
		logger.debug("RedditiJPAImpl.getSoggettoByKey. KEY: " + key.getCodFis() + "|" + key.getIdeTelematico());
		try{
			Query q = manager_diogene.createNamedQuery("RedDatiAnagrafici.getSoggettoByKey");
			q.setParameter("codFis", key.getCodFis());
			q.setParameter("ideTel", key.getIdeTelematico());
			ele = (RedDatiAnagrafici)q.getSingleResult();
		}catch (NoResultException nre) {
			logger.warn("RedditiJPAImpl.getSoggettoByKey- No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new RedditiServiceException(t);
		}
		return ele;
	}
	
	@Override
	public RedDomicilioFiscale getDomicilioByKey(KeySoggDTO key)	throws RedditiServiceException {
		RedDomicilioFiscale ele=null;
		logger.debug("RedditiJPAImpl.getDomicilioByKey. KEY: " + key.getCodFis() + "|" + key.getIdeTelematico());
		try{
			Query q = manager_diogene.createNamedQuery("RedDomicilioFiscale.getDomicilioByKey");
			q.setParameter("codFis", key.getCodFis());
			q.setParameter("ideTel", key.getIdeTelematico());
			ele = (RedDomicilioFiscale)q.getSingleResult();
		}catch (NoResultException nre) {
			logger.warn("RedditiJPAImpl.getDomicilioByKey- No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new RedditiServiceException(t);
		}
		return ele;
	}
	
	@Override
	public String getAnnoUltimiRedditiByCF(KeySoggDTO rs)	throws RedditiServiceException {
		
		logger.debug("RedditiJPAImpl.getAnnoUltimiRedditiDisponibiliByKey ->REDDITI PER SOGG- KEY: " + rs.getCodFis());
		try { 
			Query q = manager_diogene.createNamedQuery("RedRedditiDichiarati.getAnnoUltimiRedditiByCF");
			q.setParameter("codFis", rs.getCodFis());
			List lista = q.getResultList();
			logger.debug("Result size ["+lista.size()+"]");
			
			if(!lista.isEmpty())
				return (String) lista.get(0);
			
		}catch (NoResultException nre) {
			logger.warn("getRedditiByKey - No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new RedditiServiceException(t);
		}
		
		return null;
	}

	@Override
	public List<RedRedditiDichiarati> getRedditiByKey(KeySoggDTO rs)	throws RedditiServiceException {
		List<RedRedditiDichiarati> lista=null;
		 logger.debug("RedditiJPAImpl.getRedditiByKey ->REDDITI PER SOGG- KEY: " + rs.getCodFis() + "|" + rs.getIdeTelematico());
			try { 
				Query q = manager_diogene.createNamedQuery("RedRedditiDichiarati.getRedditiByKey");
				q.setParameter("codFis", rs.getCodFis());
				q.setParameter("ideTel", rs.getIdeTelematico());
				lista = q.getResultList();
				logger.debug("Result size ["+lista.size()+"]");
				
			}catch (NoResultException nre) {
				logger.warn("getRedditiByKey - No Result " + nre.getMessage());
			} catch (Throwable t) {
				logger.error("", t);
				throw new RedditiServiceException(t);
			}
		 return lista;
	}
	
	@Override
	public List<RedRedditiDichiarati> getRedditiByKeyAnno(KeySoggDTO rs)	throws RedditiServiceException {
		List<RedRedditiDichiarati> lista=null;
		 logger.debug("RedditiJPAImpl.getRedditiByKeyAnno ->REDDITI PER SOGG- KEY: " + rs.getCodFis() + "|" + rs.getIdeTelematico()+ "|" + rs.getAnno());
			try { 
				Query q = manager_diogene.createNamedQuery("RedRedditiDichiarati.getRedditiByKeyAnno");
				q.setParameter("codFis", rs.getCodFis());
				q.setParameter("ideTel", rs.getIdeTelematico());
				q.setParameter("anno", rs.getAnno());
				lista = q.getResultList();
				logger.debug("Result size ["+lista.size()+"]");
				
			}catch (NoResultException nre) {
				logger.warn("getRedditiByKeyAnno - No Result " + nre.getMessage());
			} catch (Throwable t) {
				logger.error("", t);
				throw new RedditiServiceException(t);
			}
		 return lista;
	}

	@Override
	public RedTrascodifica getTrascodificaByKey(KeyTrascodificaDTO key) throws RedditiServiceException {
		RedTrascodifica ele=null;
		logger.debug("RedditiJPAImpl.getTrascodificaByKey. KEY: " + key.getAnnoImposta() + "|" + key.getCodiceRiga() + "|" + key.getTipoModello());
		try{
			Query q = manager_diogene.createNamedQuery("RedTrascodifica.getTrascodificaByKey");
			q.setParameter("anno", key.getAnnoImposta());
			q.setParameter("codRig", key.getCodiceRiga());
			q.setParameter("tipMod", key.getTipoModello());
			ele = (RedTrascodifica)q.getSingleResult();
		}catch (NoResultException nre) {
			logger.warn("RedditiJPAImpl.getTrascodificaByKey- No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new RedditiServiceException(t);
		}
		return ele;
	}
	@Override
	public RedDescrizione getDescrizioneByKey(KeySoggDTO key)	throws RedditiServiceException {
		RedDescrizione ele=null;
		logger.debug("RedditiJPAImpl.getDescrizioneByKey. KEY: " + key.getCodFis() + "|" + key.getIdeTelematico() );
		try{
			Query q = manager_diogene.createNamedQuery("RedDescrizione.getDescrizioneByKey");
			q.setParameter("ideTel", key.getIdeTelematico());
			q.setParameter("codFis", key.getCodFis());
			ele = (RedDescrizione)q.getSingleResult();
		}catch (NoResultException nre) {
			logger.warn("RedditiJPAImpl.getDescrizioneByKey- No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new RedditiServiceException(t);
		}
		return ele;
	}
	

}
