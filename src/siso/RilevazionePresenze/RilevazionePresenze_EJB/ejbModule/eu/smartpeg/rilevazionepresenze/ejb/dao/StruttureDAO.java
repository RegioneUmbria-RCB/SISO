package eu.smartpeg.rilevazionepresenze.ejb.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.BeanUtils;

import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPTIDTO;
import eu.smartpeg.rilevazionepresenze.data.model.Area;
import eu.smartpeg.rilevazionepresenze.data.model.Struttura;
import eu.smartpeg.rilevazionepresenze.data.model.TipoDocumento;
import eu.smartpeg.rilevazionepresenze.data.model.TipoStruttura;
import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiPTI;
import it.webred.amprofiler.model.AmAiRole;

@Named
public class StruttureDAO extends RilevazionePresenzeBaseDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<Struttura> getStrutture() {

		try {

			TypedQuery<Struttura> q = em.createNamedQuery("Struttura.findAll", Struttura.class);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore getStrutture " + e.getMessage(), e);
		}
		return null;
	}
    
	public List<Struttura> getStruttureByTipo(List<Long> lstIdTipoStruttura) {

		try {

			Query q = em.createNamedQuery("Struttura.findStrutturaByTipo");
			q.setParameter("idTipiSTruttura", lstIdTipoStruttura);
			@SuppressWarnings("unchecked")
			List<Struttura> lista = (List<Struttura>) q.getResultList();
			return lista ;

		} catch (Exception e) {
			logger.error("Errore getStruttureByTipo " + e.getMessage(), e);
		}
		return null;
	}
	
	
	public List<TipoStruttura> getTipoStruttura() {

		try {

			TypedQuery<TipoStruttura> q = em.createNamedQuery("TipoStruttura.findAll", TipoStruttura.class);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore getTipoStruttura " + e.getMessage(), e);
		}
		return null;
	}
	
	public List<TipoStruttura> getTipoStrutturaByTipoFunzione(Long idTipoFunzione) {
		List<TipoStruttura> listaTipoStruttura = null;

		if (idTipoFunzione != null) {
			try {
				TypedQuery<TipoStruttura> q = em.createNamedQuery("TipoStruttura.findAll", TipoStruttura.class);
				List<TipoStruttura> lista = q.getResultList();
				if (lista != null) {
					listaTipoStruttura = new ArrayList<TipoStruttura>();
					for (TipoStruttura tipoStrutt : lista) {
						String[] value_split = tipoStrutt.getTipoFunzione().split("|");
						if (Arrays.asList(value_split).contains(idTipoFunzione.toString())) {
							TipoStruttura dto = new TipoStruttura();
							dto.setId((Long) tipoStrutt.getId());
							dto.setDescrizione((String) tipoStrutt.getDescrizione());
							listaTipoStruttura.add(dto);
						}
					}

				}
			} catch (Exception e) {
				logger.error("Errore getTipoStruttura " + e.getMessage(), e);
			}
			
		}
		return listaTipoStruttura;

	}
    
	public List<Long> getIdTipoStrutturaByTipoFunzione(Long idTipoFunzione) {
		List<Long> listaIdTipoStruttura = null;

		if (idTipoFunzione != null) {
			try {
				TypedQuery<TipoStruttura> q = em.createNamedQuery("TipoStruttura.findAll", TipoStruttura.class);
				List<TipoStruttura> lista = q.getResultList();
				if (lista != null) {
					listaIdTipoStruttura = new ArrayList<Long>();
					for (TipoStruttura tipoStrutt : lista) {
						String[] value_split = tipoStrutt.getTipoFunzione().split("|");
						if (Arrays.asList(value_split).contains(idTipoFunzione.toString())) {
						
							listaIdTipoStruttura.add((Long) tipoStrutt.getId());
						}
					}

				}
			} catch (Exception e) {
				logger.error("Errore getIdTipoStrutturaByTipoFunzione " + e.getMessage(), e);
			}
		}
		return listaIdTipoStruttura;

	}
			
			
			
			
			
			
			
			
	
	
	public Struttura saveStruttura(Struttura struttura) {
		try {
			Struttura toReturn = new Struttura();
			toReturn = em.merge(struttura);
			em.flush();

			return toReturn;
		} catch (Exception e) {
			logger.error("Errore saveStruttura " + e.getMessage(), e);
		}
		return null;
	}

	public void eliminaStruttura(Struttura struttura) throws Exception {
		Query q = em.createNamedQuery("Struttura.deleteById");
		q.setParameter("id", struttura.getId());
		q.executeUpdate();
	}

	public void eliminaArea(Struttura struttura, Area area) throws Exception {
//		try {

			struttura.getAreas().remove(area);
			em.merge(struttura);
			em.flush();

//		} catch (Exception e) {
//			logger.error("Errore eliminaArea " + e.getMessage(), e);
//			throw e;
//		}
	}

	public List<Area> getAree() {
		try {

			TypedQuery<Area> q = em.createNamedQuery("Area.findAll", Area.class);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore getAree " + e.getMessage(), e);
		}
		return null;
	}
		
		
	public Struttura findStrutturaById(Long idStruttura) {

		try {

			TypedQuery<Struttura> q = em.createNamedQuery("Struttura.findStrutturaById", Struttura.class);
			q.setParameter("id", idStruttura);
			return q.getSingleResult();

		} catch (Exception e) {
			logger.error("Errore getStrutturaID " + e.getMessage(), e);
		}
		return null;
	}
	
	public Struttura findStrutturaByCodBelfFittizio(String codRouting) {
		Struttura s = null;
		try{
			Query query = em.createNamedQuery("Struttura.findStrutturaByCodRouting");
			query.setParameter("codRouting", codRouting);
			s = (Struttura) query.getSingleResult();
		}catch(NoResultException ne){
			logger.error("Nessuna struttura trovata con il codice routing["+codRouting+"]"+ne.getMessage(), ne);
		}
		return s;
	}
}