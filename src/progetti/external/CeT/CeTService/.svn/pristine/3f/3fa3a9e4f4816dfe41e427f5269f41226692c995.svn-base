package it.webred.ct.data.access.basic.licenzecommercio.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.licenzecommercio.LicenzeCommercioException;
import it.webred.ct.data.access.basic.licenzecommercio.dto.RicercaLicenzeCommercioDTO;
import it.webred.ct.data.access.basic.locazioni.LocazioniServiceException;
import it.webred.ct.data.model.licenzecommercio.SitLicenzeCommercio;
import it.webred.ct.data.model.licenzecommercio.SitLicenzeCommercioVie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class LicenzeCommercioJPAImpl extends CTServiceBaseDAO implements LicenzeCommercioDAO {

	private static final long serialVersionUID = 1L;

	@Override
	public SitLicenzeCommercio getLicenzaCommercioByID(RicercaLicenzeCommercioDTO rlc) {
		SitLicenzeCommercio lic = new SitLicenzeCommercio();
		logger.debug("LicenzeCommercioJPAImpl -  getLicenzaCommercioByID:  "+rlc.getId());
		try {
			lic = manager_diogene.find(SitLicenzeCommercio.class, rlc.getId() );
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LicenzeCommercioException(t);
		}
		return lic;
	}

	@Override
	public SitLicenzeCommercioVie getViaByIdExt(RicercaLicenzeCommercioDTO rlc) {
		SitLicenzeCommercioVie via = new SitLicenzeCommercioVie();
		logger.debug("LicenzeCommercioJPAImpl - getViaByIdExt:  "+rlc.getIdExtVia());
		try {
			Query q = manager_diogene.createNamedQuery("SitLicenzeCommercioVie.getViaByIdExt"); 
			q.setParameter("idExt",rlc.getIdExtVia());
			List<SitLicenzeCommercioVie> listaVie = (List<SitLicenzeCommercioVie>)q.getResultList();
			if (listaVie.size() >0)
				via = listaVie.get(0);
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new LicenzeCommercioException(t);
		}
		return via;
	}
	
	
	public SitLicenzeCommercioVie getViaByDescrizione(RicercaCivicoDTO rc){
		SitLicenzeCommercioVie via = null;
		
		Date dataRif = rc.getDataRif();
		if(dataRif == null)
			dataRif = new Date();
		
		logger.debug("RICERCA VIA Licenze Commercio" +
				"[Toponimo: "+rc.getToponimoVia()+"];" +
				"[Via: "     +rc.getDescrizioneVia()+"];" +
				"[Data: "    +dataRif+"]"); 
		try {
			Query q = null;
			q = manager_diogene.createNamedQuery("SitLicenzeCommercioVie.getViaByDescrizione");
			q.setParameter("toponimo", rc.getToponimoVia());
			q.setParameter("via", rc.getDescrizioneVia());
			q.setParameter("dtRif", dataRif);
			
			List<SitLicenzeCommercioVie> lista =(List<SitLicenzeCommercioVie>) q.getResultList();
			logger.debug("Result size["+lista.size()+"]");
			if(lista.size()> 0)
				via = lista.get(0);
		
		}catch (Throwable t) {
			logger.error("", t);
			throw new LocazioniServiceException(t);
		}
		
		return via;
	}
	
	public List<SitLicenzeCommercio> getLicCommercioCivicoAllaData(RicercaCivicoDTO rc){
		List<SitLicenzeCommercio> lista = new ArrayList<SitLicenzeCommercio>();
		
		SitLicenzeCommercioVie via = this.getViaByDescrizione(rc);
		if(via!=null){
			Date dataRif = rc.getDataRif();
			if(dataRif == null)
				dataRif = new Date();
			
			String idExtVia = via.getIdExt();
			logger.debug("RICERCA LICENZE DI COMMERCIO AL CIVICO " +
					"[idViaUnico: "+ idExtVia +"];" +
					"[Civico: "  +rc.getCivico()+"];" +
					"[Data: "    +dataRif+"]"); 
	
			try {
				Query q = null;
				q = manager_diogene.createNamedQuery("SitLicenzeCommercio.getLicenzeByIdExtViaCivico");
				q.setParameter("idExtVia", idExtVia);
				q.setParameter("civico", rc.getCivico());
				q.setParameter("dtRif", dataRif);
				
				lista =(List<SitLicenzeCommercio>) q.getResultList();
				logger.debug("Result size["+lista.size()+"]");
				
			}catch (Throwable t) {
				logger.error("", t);
				throw new LocazioniServiceException(t);
			}
		
		}
		
		return lista;
	}

	public List<SitLicenzeCommercio> getLicCommercioCivicoAllaDataByIdViaUnico(RicercaCivicoDTO rc){
		List<SitLicenzeCommercio> lista = new ArrayList<SitLicenzeCommercio>();
		Date dataRif = rc.getDataRif();
		if(dataRif == null)
			dataRif = new Date();
		
		String idVia = rc.getIdVia();
		logger.debug("RICERCA LICENZE DI COMMERCIO AL CIVICO " +
				"[idViaUnico: "+ idVia +"];" +
				"[Civico: "  + rc.getCivico()+"];" +
				"[Data: "    +dataRif+"]"); 
	
			try {
				Query q = null;
				q = manager_diogene.createNamedQuery("SitLicenzeCommercio.getLicenzeByIdViaUnicoCivico");
				q.setParameter("idViaUnico", idVia);
				q.setParameter("civico", rc.getCivico());
				q.setParameter("dtRif", dataRif);
				
				lista =(List<SitLicenzeCommercio>) q.getResultList();
				logger.debug("Result size["+lista.size()+"]");
				
			}catch (Throwable t) {
				logger.error("", t);
				throw new LocazioniServiceException(t);
			}
		return lista;
	}

}
