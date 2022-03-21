package it.webred.ct.data.access.basic.rette.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.rette.RetteServiceException;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.data.model.rette.SitRttBollette;
import it.webred.ct.data.model.rette.SitRttDettBollette;
import it.webred.ct.data.model.rette.SitRttRateBollette;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class RetteJPAImpl extends CTServiceBaseDAO implements RetteDAO {

	@Override
	public List<SitRttBollette> getListaBollettePagateByCodFis(String codFiscale, String numBolletta)
			throws RetteServiceException {

		List<SitRttBollette> listaBollette = new ArrayList<SitRttBollette>();
		
		try {
			if(numBolletta != null && !"".equals(numBolletta)){
				Query q = manager_diogene.createNamedQuery("SitRttBollette.getBollettePagByNumBoll");
				q.setParameter("numBolletta", numBolletta.toUpperCase());
				listaBollette.addAll(q.getResultList());
				return listaBollette;
			}
			
			Query q2 = manager_diogene.createNamedQuery( "SitRttBollette.getBollettePagByCF");
			q2.setParameter("codFisc", codFiscale.toUpperCase());
			listaBollette.addAll(q2.getResultList());
			
			//anche bollette familiari
			Query q = manager_diogene.createNamedQuery("SitDPersona.getFamigliaByCF");
			q.setParameter("codFisc", codFiscale.toUpperCase());
			List<SitDPersona> listaFamiliari = q.getResultList();
			
			for(SitDPersona persona: listaFamiliari){
				if(!codFiscale.toUpperCase().equals(persona.getCodfisc().toUpperCase())){
					q2 = manager_diogene.createNamedQuery( "SitRttBollette.getBollettePagByCF");
					q2.setParameter("codFisc", persona.getCodfisc().toUpperCase());
					listaBollette.addAll(q2.getResultList());
				}
			}

		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}

		return listaBollette;
		
	}
	
	@Override
	public List<SitRttBollette> getListaBolletteNonPagateByCodFis(String codFiscale, String numBolletta)
			throws RetteServiceException {

		List<SitRttBollette> listaBollette = new ArrayList<SitRttBollette>();
		
		try {
			if(numBolletta != null && !"".equals(numBolletta)){
				Query q = manager_diogene.createNamedQuery("SitRttBollette.getBolletteNonPagByNumBoll");
				q.setParameter("numBolletta", numBolletta.toUpperCase());
				listaBollette.addAll(q.getResultList());
				return listaBollette;
			}
			
			Query q2 = manager_diogene.createNamedQuery( "SitRttBollette.getBolletteNonPagByCF");
			q2.setParameter("codFisc", codFiscale.toUpperCase());
			listaBollette.addAll(q2.getResultList());
			
			//anche bollette familiari
			Query q = manager_diogene.createNamedQuery("SitDPersona.getFamigliaByCF");
			q.setParameter("codFisc", codFiscale.toUpperCase());
			List<SitDPersona> listaFamiliari = q.getResultList();
			
			for(SitDPersona persona: listaFamiliari){
				if(!codFiscale.toUpperCase().equals(persona.getCodfisc().toUpperCase())){
					q2 = manager_diogene.createNamedQuery( "SitRttBollette.getBolletteNonPagByCF");
					q2.setParameter("codFisc", persona.getCodfisc().toUpperCase());
					listaBollette.addAll(q2.getResultList());
				}
			}

		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}

		return listaBollette;
		
	}
	
	@Override
	public SitRttBollette getBollettaByCodBoll(String codBolletta)
			throws RetteServiceException {

		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRttBollette.getBollettaByCodBoll").setParameter(
					"codBolletta", codBolletta);
			List<SitRttBollette> lista = q.getResultList();
			if(lista.size() > 0)
				return lista.get(0);

		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}
		return null;

	}
	
	@Override
	public List<SitRttDettBollette> getListaDettaglioBolletteByCodBoll(String codBolletta)
			throws RetteServiceException {

		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRttDettBollette.getBolletteDettByCodBoll").setParameter(
					"codBolletta", codBolletta);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}

	}
	
	@Override
	public List<SitRttRateBollette> getListaRateBolletteByCodBoll(String codBolletta)
			throws RetteServiceException {

		try {

			Query q = manager_diogene.createNamedQuery(
					"SitRttRateBollette.getBolletteRateByCodBoll").setParameter(
					"codBolletta", codBolletta);
			return q.getResultList();

		} catch (Throwable t) {
			throw new RetteServiceException(t);
		}

	}

}
