package it.webred.ct.data.access.basic.cosap.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.cosap.CosapQueryBuilder;
import it.webred.ct.data.access.basic.cosap.CosapServiceException;
import it.webred.ct.data.access.basic.cosap.dto.RicercaSoggettoCosapDTO;
import it.webred.ct.data.access.basic.cosap.dto.RicercaOggettoCosapDTO;
import it.webred.ct.data.access.basic.ici.IciServiceException;
import it.webred.ct.data.model.cosap.SitTCosapContrib;
import it.webred.ct.data.model.cosap.SitTCosapTassa;
import it.webred.ct.data.model.ici.SitTIciSogg;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class CosapJPAImpl extends CTServiceBaseDAO implements CosapDAO {

	@Override
	public List<SitTCosapContrib> searchSoggetto(RicercaSoggettoCosapDTO rs) throws CosapServiceException {
		List<SitTCosapContrib>  lista=null;
		try{
			String sql = (new CosapQueryBuilder(rs)).createQuery(false);
			logger.debug("searchSoggettoCosap. SQL: " + sql);
			Query q = manager_diogene.createQuery(sql);
			if (rs.getTipoRicerca() != null && rs.getTipoRicerca().equals("all")) {
				lista = (List<SitTCosapContrib> )q.getResultList();
				logger.debug("Result size ["+lista.size()+"]");
			}
			else {
				List<Object[]> res = q.getResultList();
				logger.debug("Result size ["+res.size()+"]");
				lista = new ArrayList<SitTCosapContrib>();
				if (res != null) {
					for (Object[] ele : res) {
						SitTCosapContrib sogg = new SitTCosapContrib();
						sogg.setTipoPersona(rs.getTipoSogg());
						if (rs.getTipoSogg().equals("F"))  {
							if (ele[0] != null)
								sogg.setCogDenom(ele[0].toString().trim());
							if (ele[1] != null)
								sogg.setNome(ele[1].toString().trim());
							if (ele[2] != null)
								sogg.setDtNascita((Date)ele[2]);
							if (ele[3] != null)
								sogg.setCodiceFiscale(ele[3].toString().trim());
							if (ele[4] != null)
								sogg.setCodBelfioreNascita(ele[4].toString().trim());
							if (ele[5] != null)
								sogg.setDescComuneNascita(ele[5].toString().trim());
						}else {
							if (ele[0] != null) 	
								sogg.setCogDenom(ele[0].toString().trim());
							if (ele[1] != null)
								sogg.setPartitaIva(ele[1].toString().trim());
						}
						lista.add(sogg);
					}
				}
				
			}
		}catch (NoResultException nre) {
			logger.warn("getListaSoggettiByOgg - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new CosapServiceException(t);
		}
		return lista;
	}

	@Override
	public List<SitTCosapTassa> getDatiOggettiByIdSogg(RicercaSoggettoCosapDTO rs) throws CosapServiceException {
		List<SitTCosapTassa> listaOgg = null;
		logger.debug("CosapJPAImpl.getDatiOggettiByIdSogg ->idSogg["+rs.getIdSoggCosap()+"]");
		
		try {
			Query q = manager_diogene.createNamedQuery("SitTCosapTassa.getOggettiByIdSogg");
			q.setParameter("id", rs.getIdSoggCosap());
			listaOgg = q.getResultList();
			logger.debug("Result size ["+listaOgg.size()+"]");
			
		}catch (NoResultException nre) {
			logger.warn("CosapJPAImpl.getDatiOggettiByIdSogg - No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CosapServiceException(t);
		}
		return listaOgg;
	}

	@Override
	public SitTCosapContrib getDatiSoggettoById(RicercaSoggettoCosapDTO rs)	throws CosapServiceException {
		SitTCosapContrib sogg =null;
		logger.debug("CosapJPAImpl.getDatiSoggettoById - Id sogg: " + rs.getIdSoggCosap());
		try{
			Query q = manager_diogene.createNamedQuery("SitTCosapContrib.getSoggettoById");
			q.setParameter("id", rs.getIdSoggCosap());
			sogg = (SitTCosapContrib)q.getSingleResult();
		}catch (NoResultException nre) {
			logger.warn("CosapJPAImpl.getDatiSoggettoById  - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new CosapServiceException(t);
		}
		return sogg ;
	}

	@Override
	public SitTCosapTassa getDatiOggettoById(RicercaOggettoCosapDTO rs) throws CosapServiceException {
		SitTCosapTassa tassa =new SitTCosapTassa();
		logger.debug("CosapJPAImpl.getDatiOggettoById - Id : " + rs.getId());
		try{
			Query q = manager_diogene.createNamedQuery("SitTCosapTassa.getOggettoById");
			q.setParameter("id", rs.getId());
			tassa = (SitTCosapTassa)q.getSingleResult();
		}catch (NoResultException nre) {
			logger.warn("CosapJPAImpl.getDatiOggettoById   - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new CosapServiceException(t);
		}
		return tassa ;
	}

	@Override
	public List<SitTCosapTassa> getDatiSintesiOggettiByListaID(RicercaOggettoCosapDTO rs) throws CosapServiceException {
		List<SitTCosapTassa>  lista=new ArrayList<SitTCosapTassa>();
		try{
			String sql = (new CosapQueryBuilder()).getSqlSintesiOggettiCosap(rs.getListaId());
			if (sql==null || sql.equals(""))
				return lista;
			logger.debug("getDatiSintesiOggettiByListaID. SQL: " + sql);
			Query q = manager_diogene.createNativeQuery(sql);
			List<Object[]> res = q.getResultList();
			logger.debug("Result size ["+res.size()+"]");
			if (res != null) {
				for (Object[] ele : res) {
					SitTCosapTassa ogg = new SitTCosapTassa();
					if (ele[0] != null)
						ogg.setNumeroDocumento((String)ele[0]);
					if (ele[1] != null)
						ogg.setAnnoDocumento((String)ele[1]);
					if (ele[2] != null)
						ogg.setCodiceImmobile(((String)ele[2]));
					if (ele[3] != null)
						ogg.setTipoOccupazione((String)ele[3]);
					/*
					if (ele[4] != null)
						ogg.setDtIniValidita((Date)ele[4]);
					if (ele[5] != null)
						ogg.setDtFineVal((Date)ele[5]);
					*/
					if (ele[4] != null) {
						long milliseconds = ((Timestamp) ele[4]).getTime() + ((Timestamp) ele[4]).getNanos()/1000000;
						ogg.setDtIniValidita(new java.util.Date(milliseconds));
					}
					if (ele[5] != null) {
						long milliseconds = ((Timestamp) ele[4]).getTime() + ((Timestamp) ele[4]).getNanos()/1000000;
						ogg.setDtIniValidita(new java.util.Date(milliseconds));
					}
					lista.add(ogg);
				}
			}
			
		}catch (NoResultException nre) {
			logger.warn("getListaSoggettiByOgg - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new CosapServiceException(t);
		}
		return lista;
		
	}

	
	public List<SitTCosapTassa> getDettaglioCosap(RicercaOggettoCosapDTO rs) throws CosapServiceException {
		
		List<SitTCosapTassa> listaOgg = null;
		
		DateFormat sf= new SimpleDateFormat("dd/MM/yyyy");
		logger.debug("CosapJPAImpl.getDettaglioCosap ->numeroDocumento["+rs.getNumeroDocumento()+"] annoDocumento["+ rs.getAnnoDocumento()+"] codiceImmobile["+ rs.getCodiceImmobile()+"] tipoOccupazione["+ rs.getTipoOccupazione()+"] dtIniValidita["+ (rs.getDtIniValidita()!= null && !rs.getDtIniValidita().equals("") ? sf.format(rs.getDtIniValidita()): "")+"] dtFineValidita["+ (rs.getDtFineValidita()!= null && !rs.getDtFineValidita().equals("") ? sf.format(rs.getDtFineValidita()): ""));
		
		try {
			Query q = manager_diogene.createNamedQuery("SitTCosapTassa.getOggettiDettaglio");
			q.setParameter("numeroDocumento", rs.getNumeroDocumento());
			q.setParameter("annoDocumento", rs.getAnnoDocumento());
			q.setParameter("codiceImmobile", rs.getCodiceImmobile());
			q.setParameter("tipoOccupazione", rs.getTipoOccupazione());
			
			listaOgg = q.getResultList();
			logger.debug("Result size ["+listaOgg.size()+"]");
			
		}catch (NoResultException nre) {
			logger.warn("CosapJPAImpl.getDatiOggettiByIdSogg - No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new CosapServiceException(t);
		}
		return listaOgg;
		
	}

	@Override
	public List<SitTCosapContrib> getListaSoggettiByIdExt(RicercaSoggettoCosapDTO rs) throws CosapServiceException {
		List<SitTCosapContrib> listaSogg=new ArrayList<SitTCosapContrib>();	
		logger.debug("getListaSoggettiByIdExt - IdExtSogg: " + rs.getIdExtSoggCosap());
		try{
			Query q = manager_diogene.createNamedQuery("SitTCosapContrib.getSoggettiByIdExt");
			q.setParameter("idExt", rs.getIdExtSoggCosap());
			List<Object[]> result = q.getResultList();
			for (Object[] ele : result) {
				SitTCosapContrib sogg = new SitTCosapContrib();
				if (ele[0] !=null)
					sogg.setCogDenom((String)ele[0]);
				if (ele[1] !=null)
					sogg.setNome((String)ele[1]);
				if (ele[2] !=null)
					sogg.setCodiceFiscale((String)ele[2]);
				if (ele[3] !=null)
					sogg.setPartitaIva((String)ele[3]);
				listaSogg.add(sogg);
				
					
			}
		}catch (NoResultException nre) {
			logger.warn("CosapJPAImpl.getDatiSoggettoByIdExt  - No Result " + nre.getMessage());
		}catch(Throwable t) {
			logger.error("", t);
			throw new CosapServiceException(t);
		}
		return listaSogg ;
	}
	

	

}
