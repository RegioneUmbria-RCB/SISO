package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.CodificaINPS;
import it.webred.cs.csa.ejb.dto.InformativaDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.TriageItemDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrUtilsBean;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.csa.ejb.dto.siru.CampoFseDTO;
import it.webred.cs.csa.ejb.dto.siru.ConfigurazioneFseDTO;
import it.webred.cs.data.DataModelCostanti.CampiFse;
import it.webred.cs.data.model.*;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

@Named
public class ConfigurazioneDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("unchecked")
	public List<CsTbStatoCivile> getStatoCivile() {

		try {

			Query q = em.createNamedQuery("CsTbStatoCivile.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public CsTbStatoCivile getStatoCivileByIdExtCet(String codEnte, String staciv) {
		CsTbStatoCivile tb = null;
		try {
            if(!StringUtils.isBlank(codEnte) && !StringUtils.isBlank(staciv)){
				Query q = em.createNamedQuery("CsTbMapStacivGit2Cs.findByIdCet");
				q.setParameter("codOrig", staciv);
				q.setParameter("ente", codEnte);
				List<CsTbMapStacivGit2Cs> lista = q.getResultList();
				if (!lista.isEmpty()) {
				   if(lista.size()>1)
					   logger.warn("In mappa esiste più di un valore associato a ID_EXT["+staciv+"] COD_ENTE["+codEnte+"] di stato civile");
				   tb = lista.get(0).getCsTbStatoCivile();
				}else
					logger.warn("Nessun valore di CS_TB_STATO_CIVILE associato a ID_EXT["+staciv+"] COD_ENTE["+codEnte+"]: Configurare le corrispondende tra id degli stati civili (IDS_MAPPING_GIT2CS) ");
            }

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return tb;
	}

	@SuppressWarnings("unchecked")
	public CsTbStatoCivile getStatoCivileByDescrizione(String descrizione) {

		try {

			Query q = em.createNamedQuery("CsTbStatoCivile.findStatoByDescrizione");
			q.setParameter("descrizione", descrizione);
			List<CsTbStatoCivile> lista = q.getResultList();
			if (!lista.isEmpty())
				return lista.get(0);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public CsTbCondLavoro getCondLavoroByDescrizione(String descrizione) {

		try {

			Query q = em.createNamedQuery("CsTbCondLavoro.findByDescrizione");
			q.setParameter("descrizione", descrizione);
			List<CsTbCondLavoro> lista = q.getResultList();
			if (!lista.isEmpty())
				return lista.get(0);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public CsTbTitoloStudio getTitoloStudioByDescrizione(String descrizione) {

		try {

			Query q = em.createNamedQuery("CsTbTitoloStudio.findByDescrizione");
			q.setParameter("descrizione", descrizione);
			List<CsTbTitoloStudio> lista = q.getResultList();
			if (!lista.isEmpty())
				return lista.get(0);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	//SISO-1190
	public List<CsTbTitoloStudio> getTitoliStudio(boolean abilitatoOnly) {
		List<CsTbTitoloStudio> lista = new ArrayList<CsTbTitoloStudio>();
		try {
			String nomeQuery = abilitatoOnly ? "CsTbTitoloStudio.findAllAbil" : "CsTbTitoloStudio.findAll";
			Query q = em.createNamedQuery(nomeQuery);
			lista = q.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return lista;
	}

	public CsTbStatoCivile getStatoCivileByCodice(String obj) {
		if(obj!=null)
			return em.find(CsTbStatoCivile.class, obj);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbStatus> getStatus() {

		try {

			Query q = em.createNamedQuery("CsTbStatus.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbStatus getStatusById(Long id) {
		return em.find(CsTbStatus.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<CsTbSchedaMultidim> getParametriSM(String idTipo) {

		try {
			Query q = em.createNamedQuery("CsTbSchedaMultidim.findByTipo");
			q.setParameter("idTipo", idTipo);
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbSchedaMultidim getParametroSM(String idTipo, String codice) {
		try {
			Query q = em.createNamedQuery("CsTbSchedaMultidim.findByTipoCodice");
			q.setParameter("idTipo", idTipo);
			q.setParameter("codice", codice);
			List<CsTbSchedaMultidim> result = q.getResultList();
			if (result != null && !result.isEmpty())
				return result.get(0);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbPermesso getPermessoById(Long id) {
		return em.find(CsTbPermesso.class, id);
	}

	//SISO-792
	public CsTbAssenzaPermesso getAssenzaPermessoById(Long id) {
		return em.find(CsTbAssenzaPermesso.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<CsTbPermesso> getPermesso() {

		try {

			Query q = em.createNamedQuery("CsTbPermesso.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsTbAssenzaPermesso> getPermessoSenzaTitoloSoggiorno() {

		try {

			Query q = em.createNamedQuery("CsTbAssenzaPermesso.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsCCategoriaSociale getCategoriaSocialeById(Long id) {

		CsCCategoriaSociale cs = em.find(CsCCategoriaSociale.class, id);
		return cs;

	}

	@SuppressWarnings("unchecked")
	public List<CsCCategoriaSociale> getCategorieSociali() {

		try {

			Query q = em.createNamedQuery("CsCCategoriaSociale.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipologiaFamiliare> getTipologieFamiliari() {

		try {

			Query q = em.createNamedQuery("CsTbTipologiaFamiliare.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbTipologiaFamiliare getTipologiaFamiliareById(Long id) {
		return em.find(CsTbTipologiaFamiliare.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<CsTbResponsabilita> getResponsabilita() {

		try {

			Query q = em.createNamedQuery("CsTbResponsabilita.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbProblematica getProblematicaById(Long id) {

		CsTbProblematica cs = em.find(CsTbProblematica.class, id);
		return cs;

	}

	public CsTbTitoloStudio getTitoloStudioById(Long id) {

		CsTbTitoloStudio cs = em.find(CsTbTitoloStudio.class, id);
		return cs;

	}

	@SuppressWarnings("unchecked")
	public List<CsTbProfessione> getProfessioni() {

		try {

			Query q = em.createNamedQuery("CsTbProfessione.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbCondLavoro> getCondLavoro() {

		try {

			Query q = em.createNamedQuery("CsTbCondLavoro.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbSettoreImpiego> getSettoreImpiego() {

		try {

			Query q = em.createNamedQuery("CsTbSettoreImpiego.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTutela> getTutele() {

		try {

			Query q = em.createNamedQuery("CsTbTutela.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoIndirizzo> getTipoIndirizzo() {

		try {

			Query q = em.createNamedQuery("CsTbTipoIndirizzo.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoContributo> getTipoContributo() {

		try {

			Query q = em.createNamedQuery("CsTbTipoContributo.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}


	
	public CsTbIcd10 getIcd10ById(Long id) {

		CsTbIcd10 cs = em.find(CsTbIcd10.class, id);
		return cs;

	}

	@SuppressWarnings("unchecked")
	public List<String> getIcd10CodIniziali() {

		try {

			Query q = em.createNamedQuery("CsTbIcd10.findAllCodIniziali");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbIcd10> getIcd10ByCodIniziali(String codIniziale) {

		try {

			Query q = em.createNamedQuery("CsTbIcd10.findCodiciByCodIniziale").setParameter("codIniziale", codIniziale);
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbIcd9 getIcd9ById(Long id) {

		CsTbIcd9 cs = em.find(CsTbIcd9.class, id);
		return cs;

	}

	@SuppressWarnings("unchecked")
	public List<String> getIcd9CodIniziali() {

		try {

			Query q = em.createNamedQuery("CsTbIcd9.findAllCodIniziali");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbIcd9> getIcd9ByCodIniziali(String codIniziale) {

		try {

			Query q = em.createNamedQuery("CsTbIcd9.findCodiciByCodIniziale").setParameter("codIniziale", codIniziale);
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoRapportoCon> getTipoRapportoConParenti() {

		try {

			Query q = em.createNamedQuery("CsTbTipoRapportoCon.findAllAbilParenti");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoRapportoCon> getTipoRapportoConConoscenti() {

		try {

			Query q = em.createNamedQuery("CsTbTipoRapportoCon.findAllAbilConoscenti");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbPotesta> getPotesta() {

		try {

			Query q = em.createNamedQuery("CsTbPotesta.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbDisponibilita> getDisponibilita() {

		try {

			Query q = em.createNamedQuery("CsTbDisponibilita.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsTbDurataRicLavoro> getDurataRicLavoro() {

		try {

			Query q = em.createNamedQuery("CsTbDurataRicLavoro.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbDisponibilita getDisponibilitaById(Long id) {

		try {
			return em.find(CsTbDisponibilita.class, id);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoPai> getTipoPai() {

		try {

			Query q = em.createNamedQuery("CsTbTipoPai.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbMotivoChiusuraPai> getMotivoChiusuraPai() {

		try {

			Query q = em.createNamedQuery("CsTbMotivoChiusuraPai.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbContatto> getContatti() {

		try {

			Query q = em.createNamedQuery("CsTbContatto.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbMacroSegnal getMacroSegnalazioneById(Long id) {

		CsTbMacroSegnal cs = em.find(CsTbMacroSegnal.class, id);
		return cs;

	}

	@SuppressWarnings("unchecked")
	public List<CsTbMicroSegnal> getMicroSegnalazioni() {

		try {

			Query q = em.createNamedQuery("CsTbMicroSegnal.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbMicroSegnal getMicroSegnalazioneById(Long id) {

		CsTbMicroSegnal cs = em.find(CsTbMicroSegnal.class, id);
		return cs;

	}

	public CsTbMotivoSegnal getMotivoSegnalazioneById(Long id) {

		CsTbMotivoSegnal cs = em.find(CsTbMotivoSegnal.class, id);
		return cs;

	}

	public CsTbDisabGravita getDisabGravitaById(Long id) {
		return em.find(CsTbDisabGravita.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<CsTbDisabTipologia> getDisabTipologia() {

		try {

			Query q = em.createNamedQuery("CsTbDisabTipologia.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbDisabTipologia getDisabTipologiaById(Long id) {
		return em.find(CsTbDisabTipologia.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<CsTbServComunita> getServComunita() {

		try {

			Query q = em.createNamedQuery("CsTbServComunita.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbServLuogoStr> getServLuogoStr() {

		try {

			Query q = em.createNamedQuery("CsTbServLuogoStr.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbServResRetta> getServResRetta() {

		try {

			Query q = em.createNamedQuery("CsTbServResRetta.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbServSemiresRetta> getServSemiresRetta() {

		try {

			Query q = em.createNamedQuery("CsTbServSemiresRetta.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbBuono> getBuoni() {

		try {

			Query q = em.createNamedQuery("CsTbBuono.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbEsenzioneRiduzione> getEsenzioniRiduzioni() {

		try {

			Query q = em.createNamedQuery("CsTbEsenzioneRiduzione.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbMotivoChiusuraInt> getMotiviChiusuraIntervento() {

		try {

			Query q = em.createNamedQuery("CsTbMotivoChiusuraInt.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbMotivoChiusuraInt getMotivoChiusuraIntervento(Long id) {

		try {

			CsTbMotivoChiusuraInt tb = em.find(CsTbMotivoChiusuraInt.class, id.longValue());
			return tb;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoDiario> getTipiDiario() {

		try {

			Query q = em.createNamedQuery("CsTbTipoDiario.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoRetta> getTipiRetta() {

		try {

			Query q = em.createNamedQuery("CsTbTipoRetta.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoRientriFami> getTipiRientriFami() {

		try {

			Query q = em.createNamedQuery("CsTbTipoRientriFami.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoComunita> getTipiComunita() {

		try {

			Query q = em.createNamedQuery("CsTbTipoComunita.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public CsTbTipoComunita getTipoComunitaByDesc(String desc) {
		try {

			Query q = em.createNamedQuery("CsTbTipoComunita.findByDescrizione");
			q.setParameter("descrizione", desc);
			List<CsTbTipoComunita> lst = q.getResultList();
			if (lst.size() > 0)
				return lst.get(0);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbTipoRetta getTipoRetta(Long id) {
		return em.find(CsTbTipoRetta.class, id);
	}

	public CsTbTipoContributo getTipoContributo(Long id) {
		return em.find(CsTbTipoContributo.class, id);
	}

	public CsTbTipoRientriFami getTipoRientriFami(Long id) {
		return em.find(CsTbTipoRientriFami.class, id);
	}

	public List<CsTbTipoOperatore> getTipiOperatore() {
		try {
			Query q = em.createNamedQuery("CsTbTipoOperatore.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbInterventiUOL> getInterventiUOL() {

		try {

			Query q = em.createNamedQuery("CsTbInterventiUOL.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbInterventiUOL getInterventiUOLById(Long id) {

		CsTbInterventiUOL cs = em.find(CsTbInterventiUOL.class, id);
		return cs;

	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoCirc4> getTipiCirc4() {

		try {

			Query q = em.createNamedQuery("CsTbTipoCirc4.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbTipoCirc4 getTipoCirc4ById(Long id) {

		CsTbTipoCirc4 cs = em.find(CsTbTipoCirc4.class, id);
		return cs;

	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoProgetto> getTipiProgetto() {

		try {

			Query q = em.createNamedQuery("CsTbTipoProgetto.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbTipoProgetto getTipoProgettoById(Long id) {

		CsTbTipoProgetto cs = em.find(CsTbTipoProgetto.class, id);
		return cs;

	}

	@SuppressWarnings("unchecked")
	public CsCfgParametri getParametro(String sezione, String chiave) {

		try {

			Query q = em.createNamedQuery("CsCfgParametri.findParamBySezioneChiave");
			q.setParameter("sezione", sezione);
			q.setParameter("chiave", chiave);
			List<CsCfgParametri> lst = q.getResultList();
			if (lst.size() > 0)
				return lst.get(0);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public List<String> getComuniScuole() {

		try {

			Query q = em.createNamedQuery("CsTbScuola.findComuni");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public List<CsTbAnnoScolastico> getAnniScolastici() {
		try {
			Query q = em.createNamedQuery("CsTbAnnoScolastico.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbScuolaAnno> getScuoleByComuneTipo(String comune, Long tipoId) {

		try {

			Query q = em.createNamedQuery("CsTbScuola.findByComuneTipo");
			q.setParameter("comune", comune);
			q.setParameter("idTipo", tipoId);
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public List<CsTbScuolaAnno> getScuoleByComuneAnnoTipo(String comune, Long tipoId, Long anno) {
		try {

			Query q = em.createNamedQuery("CsTbScuola.findByComuneAnnoTipo");
			q.setParameter("anno", anno);
			q.setParameter("idTipo", tipoId);
			q.setParameter("comune", comune);
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbGVulnerabile> getGrVulnerabilita() {

		try {

			Query q = em.createNamedQuery("CsTbGVulnerabile.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbCondLavoro getCondLavoro(Long id) {
		try {

			return em.find(CsTbCondLavoro.class, id);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbProfessione getProfessione(Long id) {
		try {

			return em.find(CsTbProfessione.class, id);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbGVulnerabile getGrVulnerabile(String id) {
		try {

			return em.find(CsTbGVulnerabile.class, id);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}


	public CsTbCittadinanzaAcq getCittadinanzaAcq(Long id) {
		try {

			return em.find(CsTbCittadinanzaAcq.class, id);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbCittadinanzaAcq> getCittadinanzeAcquisite() {
		try {

			Query q = em.createNamedQuery("CsTbCittadinanzaAcq.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbSettoreImpiego getSettoreImpiegoById(Long id) {
		try {

			return em.find(CsTbSettoreImpiego.class, id);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public List<CsTbSottocartellaDoc> getTipoCartelle(){
		try{
			Query q = em.createNamedQuery("CsTbSottocartellaDoc.findAll");
			return q.getResultList();
			
		}catch(Throwable e){
			throw new CarSocialeServiceException(e);
		}
	}

	public CsTbSottocartellaDoc getTipoCartellaById(Long id) {
		try{
			return em.find(CsTbSottocartellaDoc.class, id);
		}catch(Throwable e){
			throw new CarSocialeServiceException(e);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CsTbUnitaMisura> getCsTbUnitaMisura() {
		List<CsTbUnitaMisura> lst = new ArrayList<CsTbUnitaMisura>();
		try {
			Query q = em.createNamedQuery("CsTbUnitaMisura.findAll");
			lst = q.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return lst;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbMacroAttivita> getMacroAttivita() {
		List<CsTbMacroAttivita> lst = new ArrayList<CsTbMacroAttivita>();
		try {

			Query q = em.createNamedQuery("CsTbMacroAttivita.findAllAbil");
			lst = q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return lst;
	}

	public CsTbMicroAttivita getMicroAttivitaById(Long id) 
	{
		CsTbMicroAttivita cs = em.find(CsTbMicroAttivita.class, id);
		return cs;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbMicroAttivita> getMicroAttivitaByIdMacroAttivita(Long idMacro) 
	{   List<CsTbMicroAttivita> lst = new ArrayList<CsTbMicroAttivita>();
		try {
			Query q = em.createNamedQuery("CsTbMicroAttivita.findAllAbilByIdMacro");
			q.setParameter("idMacro", idMacro);
			lst = q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return lst;
		
	}

	
	public List<CsTbProbl> getProbl() {
		List<CsTbProbl> lst = new ArrayList<CsTbProbl>();
		try {

			Query q = em.createNamedQuery("CsTbProbl.findAllAbil");
			lst = q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return lst;
	}

	public CsTbProbl getProblById(Long id) {
		CsTbProbl cs = em.find(CsTbProbl.class, id);
		return cs;
	}

	public CsTbTipoRapportoCon mappaRelazioneParentale(String obj,String belfiore) throws CarSocialeServiceException {
		CsTbTipoRapportoCon rapporto = null;
		
		try {
			if(obj!=null && belfiore!=null){
				Query q = em.createNamedQuery("CsTbMapTipoRapGit2Cs.findByRelazPar");
				CsTbMapGit2CsPK id = new CsTbMapGit2CsPK();
				id.setCodOrig(obj!=null ? obj.toUpperCase() : "");
				id.setEnte(belfiore);
				q.setParameter("id",id );
				List<CsTbMapTipoRapGit2Cs> lst = q.getResultList();
				if(!lst.isEmpty()){
					rapporto = lst.get(0).getCsTbTipoRapportoCon();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return rapporto;
	}

	public CsTbTipoRapportoCon getTipoRapportoConByCodice(Long obj) {
		if(obj!=null)
			return em.find(CsTbTipoRapportoCon.class, obj);
		else
			return null;
	}
	
	public List<CsTbTipoAlert> getTipiAlert(){
		Query q = em.createNamedQuery("CsTbTipoAlert.findAll");
		List<CsTbTipoAlert> lista = q.getResultList();
		return lista;
		
	}
	
	public List<CsTbFormaGiuridica> getFormeGiuridiche(){
		Query q = em.createNamedQuery("CsTbFormaGiuridica.findAll");
		List<CsTbFormaGiuridica> lista = q.getResultList();
		return lista;
	}

	public String findCodFormProgetto(String progetto, Long tipoIntervento, Long tipoInterventoCustom, Long idCatSoc) {
		Query q = em.createNamedQuery("CsCfgIntPrForm.find");
		q.setParameter("progetto", progetto);
		q.setParameter("tipoInterventoId", tipoIntervento);
		q.setParameter("tipoInterventoCustomId", tipoInterventoCustom);
		q.setParameter("catSocialeId", idCatSoc);
		List<String> lst = q.getResultList();
		if(!lst.isEmpty()) return ((String)lst.get(0));
		return null;
	}

	//SISO-1160
	public ArBiInviante findArBiInviante(String nomeInviante, Long idInviante) {
		Query q = em.createNamedQuery("ArBiInviante.findByInvianteIdAndNome");
		q.setParameter("idInviante", idInviante); 
		q.setParameter("nomeInviante", nomeInviante);
		 
		List<ArBiInviante> lst = q.getResultList();
		if(!lst.isEmpty()) return ((ArBiInviante)lst.get(0));
		return null;
	}
	
	//SISO-1160 Fine
	
	public CsTbFormaGiuridica getFormaGiuridicaById(String obj) {
		if(obj!=null)
			return em.find(CsTbFormaGiuridica.class, obj);
		else
			return null;
	}

	public CsTbIngMercato getIngMercatoById(String obj) {
		if(obj!=null)
			return em.find(CsTbIngMercato.class, obj);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsCTipoColloquio> findAllTipoColloquios() {
		Query q = em.createNamedQuery("CsCTipoColloquio.findAllTipoColloquios");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CsCDiarioDove> findAllDiarioDoves() {
		Query q = em.createNamedQuery("CsCDiarioDove.findAllDiarioDoves");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CsCDiarioConchi> findAllDiarioConchis() {
		Query q = em.createNamedQuery("CsCDiarioConchi.findAllDiarioConchis");
		return q.getResultList();
	}

	public CsCTipoColloquio getTipoColloquio(Long id) {
		CsCTipoColloquio tipo = null;
		if(id!=null) tipo= em.find(CsCTipoColloquio.class, id);
		return tipo;
	}
	
	public CsCDiarioConchi getDiarioConChi(Long id) {
		CsCDiarioConchi tipo = null;
		if(id!=null && id!=0) 
			tipo= em.find(CsCDiarioConchi.class, id);
		return tipo;
	}
	
	public List<CsCDiarioConchi> getDiarioConChi(List<Long> id) {
		List<CsCDiarioConchi> lst = new ArrayList<CsCDiarioConchi>();
		Query q = em.createNamedQuery("CsCDiarioConchi.findByIds");
		q.setParameter("listaIds", id);
		if(id!=null) lst = (List<CsCDiarioConchi>) q.getResultList();
		return lst;
	}
	
	public CsCDiarioDove getDiarioDove(Long id) {
		CsCDiarioDove tipo = null;
		if(id!=null) tipo= em.find(CsCDiarioDove.class, id);
		return tipo;
	}

	public CsCfgIntEsegStato findCsCfgIntEsegStato(Long id) {
		CsCfgIntEsegStato stato = null;
		if(id!=null) stato = em.find(CsCfgIntEsegStato.class, id);
		return stato;
	}

	public List<CsTbSinaDomanda> getListaDomandaSina() {
		Query q = em.createNamedQuery("CsTbSinaDomanda.findAll");
		List<CsTbSinaDomanda> lst =  q.getResultList();
		for(CsTbSinaDomanda dom : lst)
			dom.getCsTbSinaRispostas().size();
		return lst;
	}
	public List<CsTbTipoAbitazione> getListaTipoAbitazione(){
		Query q = em.createNamedQuery("CsTbTipoAbitazione.findAll");
		return q.getResultList();
	}
	public List<CsTbAbitTitoloGodimento> getListaTitoloGod(){
		Query q = em.createNamedQuery("CsTbAbitTitoloGodimento.findAll");
		return q.getResultList();
	}
	
	public List<CsTbMacroIntervento> readMacroDb(Long tipoInterventoId) {
		Query query = em.createNamedQuery("CsTbMacroIntervento.findByTipoIntervento");
		query.setParameter("tipoInterventoId", tipoInterventoId);
		List<CsTbMacroIntervento> csTbMacroIntervento = query.getResultList();
		return csTbMacroIntervento;
	}

	public List<CsTbMicroIntervento> readMicroDb(Long idMacro) {
		Query query = em.createNamedQuery("CsTbMicroIntervento.findAllByIdMacro");
		query.setParameter("idMacro", idMacro);
		List<CsTbMicroIntervento> csTbMacriIntervento = query.getResultList();
		return csTbMacriIntervento;
	}
	 
	public List<ArTbPrestazioniInps> findArTbPrestazioniInpsByCodice( String[] codici) {
		if(codici!=null && codici.length>0){
			try{
				Query q = em.createNamedQuery("ArTbPrestazioniInps.findByCodice");  
				q.setParameter("codice", Arrays.asList(codici));
				return  ( List<ArTbPrestazioniInps> ) q.getResultList();
			}catch(Throwable e){
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public List<CsTbAbitGestProprietario> getListaGestProprietario() {
		Query q = em.createNamedQuery("CsTbAbitGestProprietario.findAll");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ArTbPrestazioniInps> getPrestazioniInpsFromAreaId(long area){
		try{
			Query q = em.createNamedQuery("ArTbPrestazioniInps.findPrestazioniByAreaId");
			q.setParameter("area", new BigDecimal(area));
			return  ( List<ArTbPrestazioniInps> ) q.getResultList();
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	//SISO-1172
		@SuppressWarnings("unchecked")
		public List<CsTbMotivoChiusuraPai> getMotiviChiusuraPai(Long idTipoPai) {
			List<CsTbMotivoChiusuraPai> listaMotiviChiusura = null;
			if(idTipoPai != null){
				try{
					Query q = em.createNamedQuery("CsTbMotivoChiusuraPai.findAll");
					List<CsTbMotivoChiusuraPai> lista = q.getResultList();
					if(lista != null)
						listaMotiviChiusura = new ArrayList<CsTbMotivoChiusuraPai>();
					
					for(CsTbMotivoChiusuraPai o: lista){
						String[] value_split = o.getTipi_pai().split(";");
						if (Arrays.asList(value_split).contains(idTipoPai.toString()) ) {
							CsTbMotivoChiusuraPai dto = new CsTbMotivoChiusuraPai();
							dto.setId((Long) o.getId());
							dto.setDescrizione((String) o.getDescrizione());
							listaMotiviChiusura.add(dto);
						}
					}
					
			    }catch(Throwable e){
				logger.error(e.getMessage(), e);
			}
			
			}
			return listaMotiviChiusura;
		}
		//SISO-1172 FINE
		
		
		public CsTbMotivoChiusuraPai getMotivoChiusuraPaiById(Long id) 
		{
			CsTbMotivoChiusuraPai cs = em.find(CsTbMotivoChiusuraPai.class, id);
			return cs;
		}
			
	@SuppressWarnings("unchecked")
	public ArrayList<SelectItem> getCatalogoattivita()
	{
		try {
			
			ArrayList<SelectItem> catalogoAttivita = new ArrayList<SelectItem>();
			
			catalogoAttivita = new ArrayList<SelectItem>();
			for (CsTbMacroAttivita macro : getMacroAttivita()) {
				
				List<CsTbMicroAttivita> microl = getMicroAttivitaByIdMacroAttivita(macro.getId());
				ArrayList<SelectItem> iteml = new ArrayList<SelectItem>();
				if (microl != null)
					for (CsTbMicroAttivita micro : microl) {
						SelectItem option = new SelectItem(micro,micro.getDescrizione(), micro.getTooltip());
						iteml.add(option);
					}

				SelectItemGroup group = new SelectItemGroup(macro.getDescrizione(), macro.getTooltip(), false,
						iteml.toArray(new SelectItem[iteml.size()]));
				catalogoAttivita.add(group);
			}
			
			return catalogoAttivita;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SelectItem> getCatalogoProblematiche()
	{
		try {
			ArrayList<SelectItem> catalogoProblematiche = new ArrayList<SelectItem>();
			Map<String, Map<String, List<SelectItem>>> mapGroups = new HashMap<String, Map<String, List<SelectItem>>>();
			for (CsTbProbl problematica : getProbl()) {
				String flagMatImm = problematica.getFlagMatImm();
				Map<String, List<SelectItem>> mapGroups2 = mapGroups.get(flagMatImm);
				if (mapGroups2 == null) {
					mapGroups2 = new HashMap<String, List<SelectItem>>();
					mapGroups.put(flagMatImm, mapGroups2);
				}

				String tipo = problematica.getTipo();
				List<SelectItem> items = mapGroups2.get(tipo);
				if (items == null) {
					items = new ArrayList<SelectItem>();
					mapGroups2.put(tipo, items);
				}

				SelectItem option = new SelectItem(problematica.getId(),
						problematica.getDescrizione(),
						problematica.getTooltip());
				items.add(option);
			}
			
			
			for (String matImm : mapGroups.keySet()) {
				Map<String, List<SelectItem>> mapGroups2 = mapGroups.get(matImm);
				List<SelectItemGroup> tipoGroups = new ArrayList<SelectItemGroup>();
				for (String tipo : mapGroups2.keySet()) {
					List<SelectItem> probls = mapGroups2.get(tipo);
					SelectItemGroup tipoGroup = new SelectItemGroup(tipo, tipo, false,
							probls.toArray(new SelectItem[probls.size()]));
					tipoGroups.add(tipoGroup);
				}

				if ("M".equalsIgnoreCase(matImm)) {
					SelectItemGroup groupMat = new SelectItemGroup("Materiale","Problematiche di tipo materiale",false,tipoGroups.toArray(new SelectItem[tipoGroups.size()]));
					catalogoProblematiche.add(groupMat);
				} else if ("I".equalsIgnoreCase(matImm)) {
					SelectItemGroup groupImm = new SelectItemGroup("Immateriale","Problematiche di tipo immateriale",false, tipoGroups.toArray(new SelectItem[tipoGroups.size()]));
					catalogoProblematiche.add(groupImm);
				}

			}
			
			return catalogoProblematiche;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public  ArrayList<CsCDiarioConchi> getListaConChi(){
		
		try {
			ArrayList<CsCDiarioConchi> lstConChi = new ArrayList<CsCDiarioConchi>();
			CsCDiarioConchi altro = null;
			
			List<CsCDiarioConchi> diarioConchis = findAllDiarioConchis();
			for (CsCDiarioConchi cs : diarioConchis) 
			{
				//SelectItem item = new SelectItem(csCDiarioConchi, csCDiarioConchi.getDescrizione(), csCDiarioConchi.getTooltip());
				if("Altri".equalsIgnoreCase(cs.getDescrizione()))
					altro = cs;
				else
					lstConChi.add(cs);
				
			}
			if(altro!=null) lstConChi.add(altro);
			
			return lstConChi;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
		
		
		
	}
	

	
	@SuppressWarnings("unchecked")
	public ArrayList<SelectItem> getListaRichiestaIndagine()
	{
		try {
			
			ArrayList<SelectItem> listaRichiestaIndagine = new ArrayList<SelectItem>();
			SelectItem no = new SelectItem("No");
			SelectItem si1 = new SelectItem("Si, prima attività indagine");
			SelectItem si2 = new SelectItem("Si, aggiornamento indagine");

			listaRichiestaIndagine.add(no);
			listaRichiestaIndagine.add(si1);
			listaRichiestaIndagine.add(si2);
			return listaRichiestaIndagine;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		
		return null;
	}

	public HashMap<String, List<TriageItemDTO>> getTriageValueMap() {
		
		try {
			String morbilita = "MORBILITA";
			String alimentazione = "ALIMENTAZIONE";
			String alvoDiuresi = "ALVO_DIURESI";
			String mobilita = "MOBILITA";
			String igienePersonale = "IGIENE_PERSONALE";
			String statoMentale = "STATO_MENTALE";
			String conChiVive = "CON_CHI_VIVE";
			String assistenzaDiretta = "ASSISTENZA_DIRETTA";

			/**
			 * Mappa valori dei campi della tabella TRIAGE es: chiave
			 * "MORB_ASSENTE_O_LIEVE" = 1
			 */
			HashMap<String, List<TriageItemDTO>> valueMap = new HashMap<String, List<TriageItemDTO>>();

			// MORBILITA
					List<TriageItemDTO> morbList = new LinkedList<TriageItemDTO>();
					morbList.add(new TriageItemDTO(
							"MORB_ASSENTE_O_LIEVE_0",
							0,
							"ASSENTE O LIEVE",
							"Nessuna compromissione d'organo/sistema o la compromissione non interferisce con la normale attività"));
					morbList.add(new TriageItemDTO("MORB_MODERATO_1", 1, "MODERATO",
							"La compromissione d'organo/sistema interferisce con la normale attività"));
					morbList.add(new TriageItemDTO("MORB_GRAVE1_2", 2, "GRAVE",
							"La compromissione d'organo/sistema produce disabilità"));
					morbList.add(new TriageItemDTO("MORB_GRAVE2_2", 2, "GRAVE",
							"La compromissione d'organo/sistema mette a repentaglio la sopravvivenza"));
					valueMap.put(morbilita, morbList);
					// ALIMENTAZIONE
					List<TriageItemDTO> alimList = new LinkedList<TriageItemDTO>();
					alimList.add(new TriageItemDTO("ALIM_AUTONOMO_0", 0, "AUTONOMO", ""));
					alimList.add(new TriageItemDTO("ALIM_CON_AIUTO_0", 0, "CON AIUTO",
							"Supervisione"));
					alimList.add(new TriageItemDTO("ALIM_DIPENDENZA_SEVERA_1", 1,
							"DIPENDENZA SEVERA", "Imboccamento"));
					alimList.add(new TriageItemDTO("ALIM_ENTERALE_PARENTALE_2", 2,
							"ENTERALE-PARENTALE", ""));
					valueMap.put(alimentazione, alimList);
					// ALVO E DIURESI
					List<TriageItemDTO> alvdList = new LinkedList<TriageItemDTO>();
					alvdList.add(new TriageItemDTO("ALVO_D_CONTINENZA_0", 0, "CONTINENZA",
							""));
					alvdList.add(new TriageItemDTO("ALVO_D_INCONTINENZA_URINARIA_0", 0,
							"CONTINENZA PER ALVO INCONTINENZA URINARIA", ""));
					alvdList.add(new TriageItemDTO("ALVO_D_INCONTINENZA_STABILE1_1", 1,
							"INCONTINENZA STABILE", "Per alvo e diuresi (uso pannolone)"));
					alvdList.add(new TriageItemDTO("ALVO_D_INCONTINENZA_STABILE2_1", 1,
							"INCONTINENZA STABILE",
							"Per alvo e diuresi (CVP e/o evacuazione assistita)"));
					valueMap.put(alvoDiuresi, alvdList);
					// MOBILITA
					List<TriageItemDTO> mobList = new LinkedList<TriageItemDTO>();
					mobList.add(new TriageItemDTO("MOB_AUTONOMO_0", 0, "AUTONOMO", ""));
					mobList.add(new TriageItemDTO("MOB_CON_MIN_AIUTO_0", 0,
							"CON MINIMO AIUTO", "(qualche difficoltà)"));
					mobList.add(new TriageItemDTO("MOB_CON_AUSILI_1", 1, "CON AUSILI",
							"(uso del bastone, walker, carrozzina...)"));
					mobList.add(new TriageItemDTO("MOB_ALLETTATO_2", 2, "ALLETTATO", ""));
					valueMap.put(mobilita, mobList);
					// IGIENE PERSONALE
					List<TriageItemDTO> igiList = new LinkedList<TriageItemDTO>();
					igiList.add(new TriageItemDTO("IGIENE_P_AUTONOMO_0", 0, "AUTONOMO", ""));
					igiList.add(new TriageItemDTO("IGIENE_P_CON_MIN_AIUTO_0", 0,
							"CON MINIMO AIUTO", "(qualche difficoltà)"));
					igiList.add(new TriageItemDTO("IGIENE_P_CON_MOD_AIUTO_1", 1,
							"CON AIUTO MODERATO", ""));
					igiList.add(new TriageItemDTO("IGIENE_P_TOT_DIPENDENZA_2", 2,
							"TOTALE DIPENDENZA", ""));
					valueMap.put(igienePersonale, igiList);
					// STATO MENTALE
					List<TriageItemDTO> smentaleList = new LinkedList<TriageItemDTO>();
					smentaleList.add(new TriageItemDTO("STATOMENTALE_LIV1_0", 0,
							"Collaborante, capace di intendere e volere", ""));
					smentaleList.add(new TriageItemDTO("STATOMENTALE_LIV2_0", 0,
							"Collaborante, ma con difficoltà a capire le indicazioni", ""));
					smentaleList
							.add(new TriageItemDTO(
									"STATOMENTALE_LIV3_1",
									1,
									"Non collaborante e con difficoltà a capire le indicazioni",
									""));
					smentaleList
							.add(new TriageItemDTO(
									"STATOMENTALE_LIV4_1",
									1,
									"Non collaborante e gravemente incapace di intendere e volere / segni di disturbi comportamentali",
									""));
					valueMap.put(statoMentale, smentaleList);
					// CON CHI VIVE
					List<TriageItemDTO> cviveList = new LinkedList<TriageItemDTO>();
					cviveList.add(new TriageItemDTO("CONCHIVIVE_COPPIA_0", 0,
							"COPPIA, NUCLEO FAMILIARE, ASSISTENTE FAMILIARE", ""));
					cviveList.add(new TriageItemDTO("CONCHIVIVE_SOLO1_0", 0, "SOLO",
							"NON necessita di figure di riferimento;"));
					cviveList.add(new TriageItemDTO("CONCHIVIVE_SOLO2_1", 1,
							"SOLO o COPPIA",
							"Ma necessita di figure di riferimento (es.figli);"));
					cviveList.add(new TriageItemDTO("CONCHIVIVE_SOLO3_2", 2, "SOLO",
							"Nessuna rete di riferimento;"));
					valueMap.put(conChiVive, cviveList);
					// ASSISTENZA DIRETTA
					List<TriageItemDTO> assdList = new LinkedList<TriageItemDTO>();
					assdList.add(new TriageItemDTO(
							"ASSIST_ADEGUATA_0",
							0,
							"ADEGUATA",
							"Partecipano, familiari, assistenti familiari, servizi territoriali(SAD, pasti a domicilio,...), vicinato, associazioni"));
					assdList.add(new TriageItemDTO(
							"ASSIST_PARZ_ADEG_1",
							1,
							"PARZIAMENTE ADEGUATA",
							"Affidata solo ai familiari, o solo all'assistenza familiare o solo ai servizi territoriali"));
					assdList.add(new TriageItemDTO(
							"ASSIST_POCO_ADEG_2",
							2,
							"POCO ADEGUATA",
							"Affidata ad un soggetto che non assicura un'assistenza adeguata o sufficiente"));
					assdList.add(new TriageItemDTO("ASSIST_INADEGUATA_2", 2, "INADEGUATA",
							"Non è offerta alcun tipo di assistenza;"));
					valueMap.put(assistenzaDiretta, assdList);
			
			return valueMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
		
	}
	

	public List<CsTbTribStruttura> getStruttureTribunale() {
		try {

			Query q = em.createNamedQuery("CsTbTribStruttura.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public List<CsTbTipoIsee> getTipiIsee() {
		try {

			Query q = em.createNamedQuery("CsTbTipoIsee.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public List<CsTbSecondoLivello> getTipiListaSecondoLivello() {		
		try {

			Query q = em.createNamedQuery("CsTbSecondoLivello.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public String findCodiceSinbaMotivoChiusura(String descrizione){
		String codiceEsitoSinba="";
		try {

			Query q = em.createNamedQuery("CsTbMotivoChiusuraPai.findCodiceSinbaMotivoChiusura");
			q.setParameter("descrizione", descrizione);
			Object o = q.getSingleResult();
			if (o != null){
				codiceEsitoSinba = (String) o;
				return codiceEsitoSinba;
			}
		 	
			//return q.getSingleResult().toString();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
			
	}
		
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO>  findStruttura( ) {
		List<KeyValueDTO>listaStruttura = new ArrayList<KeyValueDTO>();
			try{
				Query q = em.createNamedQuery("VStrutturaArea.findStruttura"); 
				List<Object[]> lista = q.getResultList();
				for(Object[] o: lista){
					KeyValueDTO val = new KeyValueDTO();
					val.setCodice((Long)o[0]);
					val.setDescrizione((String) o[1]);
					//SelectItem struttureLst = new SelectItem((Long)o[0],(String) o[1]);
					listaStruttura.add(val);
				}
				return listaStruttura;
			}catch(Throwable e){
				logger.error(e.getMessage(), e);
			}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<VStrutturaArea>  findAllStruttura( ) {
		List<VStrutturaArea>listaStrutture = new ArrayList<VStrutturaArea>();
			try{
				Query q = em.createNamedQuery("VStrutturaArea.findAll"); 
				listaStrutture = q.getResultList();
				
				return listaStrutture;
			}catch(Throwable e){
				logger.error(e.getMessage(), e);
			}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO>  findArea(Long idStruttura) {
		ArrayList<KeyValueDTO>listaArea = new ArrayList<KeyValueDTO>();
		 listaArea = new ArrayList<KeyValueDTO>();
			try{
				Query q = em.createNamedQuery("VStrutturaArea.findArea");  
				q.setParameter("idStruttura", idStruttura);
				List<Object[]> lista = q.getResultList();
				for(Object[] o: lista){
				
					KeyValueDTO areaLst = new KeyValueDTO((Long)o[0],(String) o[1]);
					listaArea.add(areaLst);
				}
				return listaArea;
			}catch(Throwable e){
				logger.error(e.getMessage(), e);
			}
		return null;
	}
	
	public List<KeyValueDTO>  findAllArea( ) {
		List<KeyValueDTO>listaArea = new ArrayList<KeyValueDTO>();
			try{
				Query q = em.createNamedQuery("VStrutturaArea.findAllArea"); 
				List<Object[]> lista = q.getResultList();
				for(Object[] o: lista){
					KeyValueDTO struttureLst = new KeyValueDTO((Long)o[0],(String) o[1]);
					listaArea.add(struttureLst);
				}
				return listaArea;
			}catch(Throwable e){
				logger.error(e.getMessage(), e);
			}
		return null;
	}
	
	//recupero codiceDocumentoGed
	@SuppressWarnings("unchecked")
	public String findCodiceDocumentoGed(String codRoutingOrgId) {
		try {
			Query q = em.createNamedQuery("CsRelSottocartDocProt.findCodiceDocumentoGed");
//			q.setParameter("sottocartellaDocId", sottocartellaDocId);
			q.setParameter("codRoutingOrgId", codRoutingOrgId);
			
			if(q.getResultList() != null)
			{
				return (String)q.getSingleResult();
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
	}
	// #ROMACAPITALE fine

	public CsTbProgettoAltro getProgettoAltroById(Long id) {

		CsTbProgettoAltro pa = em.find(CsTbProgettoAltro.class, id);
		return pa;

	}
	public CsTbProgettoAltro getProgettoAltroByDescrizione(String descrizione) {
		
		try {

			Query q = em.createNamedQuery("CsTbProgettoAltro.findProgettoAltroByDescrizione");
			q.setParameter("descrizione", descrizione);
			List<Object> o = q.getResultList();
			if (o != null && o.size()>0){
				return (CsTbProgettoAltro)o.get(0);
			}
		} catch (NoResultException e) {
			logger.debug("CsTbProgettoAltro con descrizione ["+descrizione+"] non trovato");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	return null;
	}	
	
	//SISO-1207
	@SuppressWarnings("unchecked")
	public List<CsTbUnitaMisura> getUnitaMisuraByIdInterventi(String query) {
		try{
			logger.debug("getUnitaMisuraByIdInterventi SQL["+query+"]");
			Query q = em.createQuery(query);
			List<CsTbUnitaMisura> retList = q.getResultList();
			logger.debug("getUnitaMisuraByIdInterventi result["+retList.size()+"]");
			return retList;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	public CsTbTipoIsee getTipoIsee(Long id) {
		if(id!=null)
			return em.find(CsTbTipoIsee.class, id);
		else return null;
	}
	
	public void salva2Livello(String nome) {
		CsTbSecondoLivello tb = new CsTbSecondoLivello();
		tb.setDescrizione(nome);
		tb.setAbilitato(Boolean.TRUE);
		em.merge(tb);
	}

	@SuppressWarnings("unchecked")
	public List<CsTbSinaRisposta> getCsTbSinaRispostaByDomandaId(Long domandaId) {
			
		try {

			Query q = em.createNamedQuery("CsTbSinaRisposta.findRispostaByDomandaId");
			q.setParameter("idDomanda", domandaId);
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public List<CsTbSsProvenienza> getSsProvenienza() {
		try {

			Query q = em.createNamedQuery("CsTbSsProvenienza.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	//SISO-1275
	public CsPaiFaseChiusura getPaiFaseChiusuraById(CsPaiFaseChiusuraPK pk) {
		CsPaiFaseChiusura cs = em.find(CsPaiFaseChiusura.class, pk);
		return cs;
	}

	public CsTbTipoOperatore getTipoOperatore(Long id) {
		return em.find(CsTbTipoOperatore.class, id);
	}
	
	public List<KeyValueDTO> getTbItems(String tabella){
		List<KeyValueDTO> lstKv = new ArrayList<KeyValueDTO>();
		String sql = "SELECT DISTINCT t.ID, t.DESCRIZIONE, t.ABILITATO "
				   + "FROM "+tabella+" t "
				   + "ORDER BY t.descrizione ";
		Query q = em.createNativeQuery(sql);
		List<Object[]> lst = (List<Object[]>)q.getResultList();
		if (lst != null) {
			for (Object[] obj : lst) {
				KeyValueDTO kv = new KeyValueDTO(obj[0], (String)obj[1]);
				boolean abilitato = obj[2]!=null && obj[2].toString().equals("1") ? Boolean.TRUE : Boolean.FALSE;
				kv.setAbilitato(abilitato);
				lstKv.add(kv);
			}
		}		
    	return lstKv;
	}
	
   public Boolean getPaiFaseChiusuraByTipoPaiId(Long idTipoPai) {
		
		try {

			Query q = em.createNamedQuery("CsPaiFaseChiusura.findPaiFaseChiusuraByTipoPaiId");
			q.setParameter("idTipoPai", idTipoPai);
			List<Long> lstIs = q.getResultList();
			return lstIs.size()>0;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
   
	public ArFfProgetto getProgettoById(Long id) {
			ArFfProgetto prog = em.find(ArFfProgetto.class, id);
			return prog;
		}
	
	public ArFfProgettoAttivita getProgettoAttivitaById(Long id) {
			ArFfProgettoAttivita prog = em.find(ArFfProgettoAttivita.class, id);
			return prog;
		}
	
/*	public List<ArFfProgettoAttivita> getSottocorsi(){			
		Query q = em.createNamedQuery("ArFfProgettoAttivita.findByAbilitato"); 
		return (List<ArFfProgettoAttivita>) q.getResultList();  
	}*/
	
	public List<KeyValueDTO> findProgettiByBelfioreOrganizzazione(String belfiore, String tipoProgetto, Long idSelected, boolean showCodiceMemo){			
		List<KeyValueDTO> lst = new ArrayList<KeyValueDTO>();
		String sql = "select distinct p.id, p.codice_memo,p.descrizione, p.abilitato abip, po.abilitato abipo  "+
				"from ar_ff_progetto p "+
				"left join ar_ff_progetto_org po on (p.id = po.progetto_id) "+
				"left join ar_o_organizzazione o on (po.organizzazione_id = o.id) "+
				"left join cs_cfg_int_pr_form frm on (FRM.FF_PROGETTO_DESCRIZIONE = p.descrizione) "+
				"where o.belfiore = :belfiore ";
		if(!StringUtils.isBlank(tipoProgetto))
			sql+= "and frm.abilitato = 1 and frm.RIF_FORM_INTERVENTO_PR_DETT = :tipoProgetto ";
		sql+= "order by descrizione";
		
		try{		
			Query q = em.createNativeQuery(sql);
			if(!StringUtils.isBlank(tipoProgetto))
				q.setParameter("tipoProgetto", tipoProgetto);
			q.setParameter( "belfiore", belfiore );
			List<Object[]> lstObj = (List<Object[]>) q.getResultList();
			for(Object[] o : lstObj) {
				BigDecimal id = (BigDecimal)o[0];
				String codiceMemo = (String)o[1];
				String descrizione = (String)o[2];
				Character pAbilitato = (Character)o[3];
				BigDecimal poAbilitato = (BigDecimal)o[4];
				String d = descrizione + (showCodiceMemo ? " ["+codiceMemo+"]" : "");
				KeyValueDTO kv = new KeyValueDTO(id, d);
				boolean confAbilitato = poAbilitato!=null && poAbilitato.intValue()==1 && pAbilitato!=null && pAbilitato=='1';
				boolean abilitato = confAbilitato || (idSelected!=null && id.longValue()==idSelected.longValue());
				kv.setAbilitato(abilitato);
				lst.add(kv);
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return lst;
	}
	
	private HashMap<String, CampoFseDTO> loadCampiFseByIdProgetto(Long progettoId) {
		HashMap<String, CampoFseDTO> mappa = new HashMap<String, CampoFseDTO>();
		try{
			String sql = "select campo as codice, s.label, nvl(c.obbligatorio, s.obbligatorio) obbligatorio, nvl(c.abilitato, s.abilitato) abilitato "
					+ "from AR_FF_FSE_CAMPI_STANDARD s "
					+ "left join (select * from AR_FF_FSE_CAMPI_PROGETTO where progetto_id = :progettoId) c on s.campo = c.campo_id";
			logger.debug("loadCampiFseByIdProgetto idProgetto["+progettoId+"] SQL["+sql+"]");
			Query q = em.createNativeQuery(sql);
			q.setParameter("progettoId", progettoId);
			List<Object[]> lst = q.getResultList();
			for(Object[] o : lst){
				CampoFseDTO c = new CampoFseDTO();
				c.setLabel((String)o[1]);
				c.setObbligatorio(o[2]!=null ? BigDecimal.ONE.equals((BigDecimal)o[2]):false);
				c.setAbilitato(o[3]!=null ? BigDecimal.ONE.equals((BigDecimal)o[3]) :false);
				mappa.put((String)o[0], c);
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return mappa;
	}
	
	public ConfigurazioneFseDTO loadCampiFse(Long progettoId){
		HashMap<String, CampoFseDTO> mappa = null;
		ConfigurazioneFseDTO conf = null;
		
		if(progettoId!=null){
			mappa = loadCampiFseByIdProgetto(progettoId);
			
			conf = new ConfigurazioneFseDTO();
			conf.setAzRagioneSociale(setCampoFse(mappa, CampiFse.AZIENDA_RAGIONE_SOCIALE));
			conf.setAzCf(setCampoFse(mappa, CampiFse.AZIENDA_CF));
			conf.setAzPi(setCampoFse(mappa, CampiFse.AZIENDA_PI));
			conf.setAzVia(setCampoFse(mappa, CampiFse.AZIENDA_VIA));
			conf.setAzComune(setCampoFse(mappa, CampiFse.AZIENDA_COMUNE));
			conf.setAzCodAteco(setCampoFse(mappa, CampiFse.AZIENDA_COD_ATECO));
			conf.setAzFormaGiuridica(setCampoFse(mappa, CampiFse.AZIENDA_FORMA_GIURIDICA));
			conf.setAzDimensione(setCampoFse(mappa, CampiFse.AZIENDA_DIMENSIONE));
			
			conf.setLavoroOrario(setCampoFse(mappa, CampiFse.LAVORO_ORARIO));
			conf.setLavoroTipo(setCampoFse(mappa, CampiFse.LAVORO_TIPO));
			
			conf.setPagIban(setCampoFse(mappa, CampiFse.PAG_IBAN));
			conf.setPagResDom(setCampoFse(mappa, CampiFse.PAG_RES_DOM));
			
			conf.setAnnoTitoloStudio(setCampoFse(mappa, CampiFse.ANNO_TITOLO_STUDIO));
			conf.setInattivoAltroCorso(setCampoFse(mappa, CampiFse.INATTIVO_ALTRO_CORSO));
			conf.setDurataRicercaLavoro(setCampoFse(mappa, CampiFse.DURATA_RICERCA_LAVORO));
			
			conf.setDataSottoscrizione(setCampoFse(mappa, CampiFse.DATA_SOTTOSCRIZIONE));
			conf.setSoggettoAttuatore(setCampoFse(mappa, CampiFse.SOGGETTO_ATTUATORE));
		}
		return conf;
	}
	
	private CampoFseDTO setCampoFse(HashMap<String, CampoFseDTO> mappa, String chiave){
		if(mappa.get(chiave)!=null)
			return mappa.get(chiave);
		else{
			CampoFseDTO empty = new CampoFseDTO();
			empty.setAbilitato(false);
			empty.setObbligatorio(false);
			return empty;
		}
	}
	
	public List<TipoStruttura> getLstTipoStrutturaByTipoFunzione(Long idTipoFunzione) {
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
	
	public List<CsTbTipoMinore> getListaTipoMinore(){
		try {
		Query q = em.createNamedQuery("CsTbTipoMinore.findAll");
		return q.getResultList();
		    } 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public CsTbTipoMinore getTipoMinoreById(Long id) {
		return em.find(CsTbTipoMinore.class, id);
	}

	public boolean verificaUsoArProgettoAttivita(Long idOrg, Long idProgetto, Long idAttivita) {
		boolean exists = false;
		String sql = 
		"select distinct * from ( "+
		"select aro.id organizzazione_id, pr.progetto_id, pr.progetto_attivita_id "+ 
		"from cs_i_intervento_pr pr, cs_o_settore s, cs_o_organizzazione o, ar_o_organizzazione aro "+
		"where PR.SETTORE_TITOLARE_ID = s.id and s.organizzazione_id = o.id and o.cod_routing = aro.belfiore "+
		"union all "+
		"select aro.id organizzazione_id, pr.progetto_id, pr.progetto_attivita_id  "+
		"from CS_EXTRA_FSE_DATI_LAVORO pr, CS_EXTRA_FSE_MAST m, SS_SCHEDA s, SS_SCHEDA_ACCESSO sa, cs_o_organizzazione o, ar_o_organizzazione aro "+
		"where PR.EXTRA_FSE_MAST_ID = m.id and M.TIPO_EXTRA_FSE_ID = 2 and s.id = M.SCHEDA_ID and sa.id = s.accesso and SA.REL_UPO_ORGANIZZAZIONE_ID=o.id and o.cod_routing = aro.belfiore "+
		"union all "+
		"select aro.id organizzazione_id, pr.progetto_id, pr.progetto_attivita_id "+
		"from CS_EXTRA_FSE_DATI_LAVORO pr, CS_EXTRA_FSE_MAST m, CS_IT_STEP it, cs_o_settore s, cs_o_organizzazione o, ar_o_organizzazione aro "+
		"where PR.EXTRA_FSE_MAST_ID = m.id and M.TIPO_EXTRA_FSE_ID = 1  and IT.SETTORE_ID=s.id and s.organizzazione_id = o.id and o.cod_routing = aro.belfiore "+
		"AND (IT.ID IS NULL OR IT.ID = (SELECT MAX (it2.id) FROM CS_IT_STEP it2 WHERE IT2.CASO_ID = M.CASO_ID)) "+
		") where 1=1 ";
		
		logger.debug("verificaUsoArProgettoAttivita organizzazioneId["+idOrg+"] progettoId["+idProgetto+"] attivitaId["+idAttivita+"]");
		if(idOrg!=null && idOrg>0)
			sql+= " and organizzazione_Id = :organizzazioneId";
		if(idProgetto!=null)
			sql+= " and progetto_Id = :progettoId ";
		if(idAttivita!=null)
			sql+= " and progetto_attivita_id = :attivitaId ";
		
		Query q = em.createNativeQuery(sql);
		
		if(idOrg!=null && idOrg>0)
			q.setParameter("organizzazioneId", idOrg);
		if(idProgetto!=null)
			q.setParameter("progettoId", idProgetto);
		if(idAttivita!=null)
			q.setParameter("attivitaId", idAttivita);
		
		List<Object[]> lst = q.getResultList();
		exists = !lst.isEmpty();
		logger.debug("verificaUsoArProgettoAttivita organizzazioneId["+idOrg+"] progettoId["+idProgetto+"] attivitaId["+idAttivita+"] RES["+exists+"]");
		
		return exists;
	}

	public CsTbTipoPai findIdProgettoPaiByDesc(String descrizione) {
		try {	
			Query q = em.createNamedQuery("CsTbTipoPai.findByDesc");
			q.setParameter("descrizione", descrizione);
			return (CsTbTipoPai) q.getSingleResult();
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbTipoPai findCsTbTipoPaiById(Long id) {
		return em.find(CsTbTipoPai.class, id);
	}

	public List<Long> findTipoDocAuthDownload(long id) {
		List<Long> authDownload = new ArrayList<Long>();
		try {	
			Query q = em.createNamedQuery("CsOOpSettoreAuthDownload.findByOpSettore");
			q.setParameter("opSettoreId", id);
			authDownload = (List<Long>) q.getResultList();
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return authDownload;
	}

	public CsTbTipoIndirizzo findCsTbTipoIndirizzoById(Long id) {
		return em.find(CsTbTipoIndirizzo.class, id);
	}

	public CsTbDurataRicLavoro findDurataRicLavoroById(Long id) {
		return em.find(CsTbDurataRicLavoro.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<CsCComunita> findComunitaByDescTipo(String desc){
		
		try{
			
			Query q = em.createNamedQuery("CsCComunita.findByDescTipo");
			q.setParameter("descrizione", desc);
			
			return q.getResultList();
			
		} catch (Throwable e) {	
			logger.error("Errore findComunitaByDescTipo "+e.getMessage());
		}
		return null;
	}
	
	public CsTbTipoDiario findTipoDiarioById(long idTipoDiario) {
		CsTbTipoDiario tipodiario = em.find(CsTbTipoDiario.class, idTipoDiario);
		return tipodiario;
	}
	
	/*ITER*/
	public List<KeyValueDTO> getListaIterStati(){
		List<KeyValueDTO> lst = new ArrayList<KeyValueDTO>();
		Query q = em.createNamedQuery("CsCfgItStato.findListItems");
		List<Object[]> res = q.getResultList();
		for(Object[] o : res)
			lst.add(new KeyValueDTO(o[0], (String)o[1]));
		return lst;
	}
	
	public CsCfgAttr findStatoAttrById(long idStatoAttr) throws Exception{
		CsCfgAttr itStatoAttr = em.find(CsCfgAttr.class, idStatoAttr);
		return itStatoAttr;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsCfgItTransizione> findTransizionesByStatoRuolo(Long idStato, String opRuolo) throws Exception {
		Query q = em.createNamedQuery("CsCfgItTransizione.findTransizionesByStatoRuolo");
		q.setParameter("idStato", idStato).setParameter("nomeRuolo", opRuolo);
		List results = q.getResultList();
		return results;
	}
	
	public List<CsCfgItTransizione> findTransizionesByStatoDaA(Long idStatoDa, Long idStatoA) {
		Query q = em.createNamedQuery("CsCfgItTransizione.findTransizionesByStatoDaA");
		q.setParameter("idStatoDa", idStatoDa);
		q.setParameter("idStatoA", idStatoA);
		List results = q.getResultList();
		return results;
	}
	
	/*DATI INTERVENTO*/
	@SuppressWarnings("unchecked")
	public List<CsCTipoIntervento> findTipiIntervento(boolean soloAbilitati) {
		Query q;
		if(soloAbilitati)
		 q = em.createNamedQuery("CsCTipoIntervento.findAllAbil");
		else
		 q = em.createNamedQuery("CsCTipoIntervento.findAll");
		return (List<CsCTipoIntervento>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CsCTipoIntervento> findTipiInterventoSettoreCatsoc(Long idSettore, List<Long> idCatsoc) {
		logger.debug("findTipiInterventoSettoreCatsoc idSettore["+idSettore+"] idCatSoc["+idCatsoc+"]");
		Query q = em.createNamedQuery("CsRelSettCsocTipoInter.findTipiInterventoSettCatsoc");
		q.setParameter("idSettore", idSettore);
		q.setParameter("idCatsoc", idCatsoc);
		List<CsCTipoIntervento> lst = (List<CsCTipoIntervento>) q.getResultList();
		logger.debug("findTipiInterventoSettoreCatsoc RESULT["+lst.size()+"]");
		return lst;
	}

	@SuppressWarnings("unchecked")
	public List<VLineaFin> getLineeFinanziamentoByEnte(String belfiore){			
		Query q = em.createNamedQuery("VLineaFin.getLineeFinanziamentoByEnte");
		q.setParameter("belfiore", belfiore);
		return (List<VLineaFin>) q.getResultList();
	}
		
	public CsCTipoIntervento getTipoInterventoById(Long id) {
		return em.find(CsCTipoIntervento.class, id);
	}
	
	public CsCTipoInterventoCustom findTipoIntCustomById(Long id){
		return em.find(CsCTipoInterventoCustom.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<VGerrarchiaServizi> findAllNodesTipoIntervento() {
		logger.debug("INIT findAllNodesTipoIntervento");
		Query q = em.createNamedQuery("VGerrarchiaServizi.findAll");
		List<VGerrarchiaServizi> lista = (List<VGerrarchiaServizi>) q.getResultList();
		logger.debug("END findAllNodesTipoIntervento");
		return lista;
	}
	
	//SISO-1110 Fine
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	@SuppressWarnings("unchecked")
	public List<CsCTipoInterventoCustom> findTipiIntCustom() {
		Query q = em.createNamedQuery("CsCTipoInterventoCustom.findAll");
		return (List<CsCTipoInterventoCustom>) q.getResultList();
	}
	
	public CsCCategoriaSociale findCatSocialeByDescrizione(String desc) {
		Query q = em.createNamedQuery("CsCCategoriaSociale.findByDescrizione");
		q.setParameter("desc", desc);
		List lst = q.getResultList();
		if (lst != null && lst.size() > 0)
			return (CsCCategoriaSociale) lst.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getTipiInterventoRecentiList() {
		Query q = em.createNamedQuery("VInterventiRecenti.findTipiIntervento");
		List<Object[]> res = q.getResultList();
		List<KeyValueDTO> lstItem = new ArrayList<KeyValueDTO>();
		for(Object[] o : res)
			lstItem.add(new KeyValueDTO((Long)o[0], (String)o[1]));
		return lstItem;
	}
	
	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getTipiInterventoCustomRecentiList() {
		Query q = em.createNamedQuery("VInterventiRecenti.findTipiInterventoCustom");
		List<Object[]> res = q.getResultList();
		List<KeyValueDTO> lstItem = new ArrayList<KeyValueDTO>();
		for(Object[] o : res)
			lstItem.add(new KeyValueDTO((Long)o[0], (String)o[1]));
		return lstItem;
	}
	//SISO_1162

	@SuppressWarnings("unchecked")
	public List<KeyValueDTO> getTipiInterventoInpsList() {
		Query q = em.createNamedQuery("ArTbPrestazioniInps.findTipiInterventoInps");
		List<Object[]> res = q.getResultList();
		List<KeyValueDTO> lstItem = new ArrayList<KeyValueDTO>();
		for(Object[] o : res)
			lstItem.add(new KeyValueDTO((String)o[0], (String)o[1]));
		return lstItem;
	}
	

	@SuppressWarnings("unchecked")
	public List<VTipiInterventoUsati> getInterventiRecentiList() {
		Query q = em.createNamedQuery("VInterventiRecenti.findAll");
		return (List<VTipiInterventoUsati>) q.getResultList();
	}
	
	//INIZIO MOD-RL
	public List<ArRelClassememoPresInps> findArRelClassememoPresInpbyTipoInterventoId( long tipoInterventoId) {
		logger.debug("findArRelClassememoPresInpbyTipoInterventoId["+tipoInterventoId+"]");
		Query q = em.createNamedQuery("ArRelClassememoPresInps.findByTipoInterventoId");  

		q.setParameter("tipoInterventoId", tipoInterventoId);
		List<ArRelClassememoPresInps> lst = q.getResultList();  
		
		logger.debug("findArRelClassememoPresInpbyTipoInterventoId["+tipoInterventoId+"] - RESULT["+lst.size()+"]");
		
		return  lst;
	}
	
	@SuppressWarnings("unchecked")
	public List<ArRelIntCustomIstat> findArRelIntCustomIstatbyTipoInterventoId(Long tipoInterventoId) {
		try{
		Query q = em.createNamedQuery("ArRelIntCustomIstat.findIstatByTipoInterventoId");  

		q.setParameter("tipoInterventoId", tipoInterventoId);
		
//		List<ArRelIntCustomIstat> lst = q.getResultList();  
		
//		logger.debug("findArRelIntCustomIstatbyTipoInterventoId["+tipoInterventoId+"] - RESULT["+lst.size()+"]");
		
		
		return (List<ArRelIntCustomIstat>) q.getResultList();
		}
		catch(Throwable e){
			logger.error(e);
			throw new CarSocialeServiceException(e);
			
		}
	
	}
	
	//SISO-1110 Fine
	public CsCfgAttrUnitaMisura findAttrUnitaMisura(Long obj, Long obj2) {
		Query q = null;
		if(obj2!=null){
			 q = em.createNamedQuery("CsCfgAttrUnitaMisura.findByAttributoUM"); 
			 q.setParameter( "idUnitaMisura", obj2 );
		}else
			 q = em.createNamedQuery("CsCfgAttrUnitaMisura.findByAttributoNullUM");
		
		q.setParameter( "idAttributo", obj );
		
		List<CsCfgAttrUnitaMisura> res = q.getResultList();  
		if(res!=null && !res.isEmpty()) return res.get(0);
		else return null;
	}
	
	public InformativaDTO findInformativa(Long obj) {
		InformativaDTO i = null;
		Query q = null;
		if(obj!=null){
			String sql = "SELECT C.ID CLASSE_ID , C.CODICE_MEMO , C.DESCRIZIONE CLASSE_DESCRIZIONE , C.DESCRIZIONE2 CLASSE_DESCRIZIONE2 , I.CODICE, i.flag_psa, I.DENOMINAZIONE inps_denominazione , I.DESCRIZIONE inps_descrizione "+
						"FROM AR_REL_CLASSEMEMO_PRES_INPS R, AR_TB_PRESTAZIONI_INPS I, AR_T_CLASSE C "+
						"WHERE R.PRESTAZIONI_INPS_CODICE = I.CODICE "+
						"AND C.ID = R.T_CLASSE_ID "+
						"AND C.ID = :idClasse ";
			
			q = em.createNativeQuery(sql);
			q.setParameter("idClasse", obj);
			List<Object[]> lst = q.getResultList();
			if(lst!=null && !lst.isEmpty()){
				 i = new InformativaDTO();
				 Object[] lst0  = lst.get(0);
				 i.setIdClasse(((BigDecimal)lst0[0]).longValue());
				 i.setCodiceMemo((String)lst0[1]);
				 i.setDescClasse((String)lst0[2]);
				 i.setDescClasseISTAT((String)lst0[3]);
				 
				 List<CodificaINPS> lstInps = new ArrayList<CodificaINPS>();
				 for(Object[] o : lst){
					 CodificaINPS inps = new CodificaINPS();
					 inps.setCodice((String)o[4]);
					 inps.setPsa(((BigDecimal)o[5]).intValue()==1 ? true : false);
					 inps.setDenomINPS((String)o[6]);
					 inps.setDescINPS((String)o[7]);
					 
					 lstInps.add(inps);
				 }

				 i.setLstCodINPS(lstInps);
			}
		
		}
		return i;
	}

    public List<VArCTariffa> findTariffe(Long orgTitolareId, Long unitaMisuraId, Long interventoCustomId){		
    	List<VArCTariffa> lst = new ArrayList<VArCTariffa>();
    	if(orgTitolareId!=null && unitaMisuraId!=null && interventoCustomId!=null){
			Query q = em.createNamedQuery("VArCTariffa.findTariffaByUmOrgIntCustom"); 
			q.setParameter("orgTitolareId", orgTitolareId );
			q.setParameter("unitaMisuraId", unitaMisuraId );
			q.setParameter("intCustomId", interventoCustomId);
			lst = (List<VArCTariffa>) q.getResultList();  
    	}
    	return lst;
	}
 
	//SISO-1110 Inizio
	@SuppressWarnings("unchecked")
	public List<VServiziCustom> findAllServiziCustoByInterventoAndAreatId(Long interventoCustomId, Integer areatId) {
		Query q = em.createNamedQuery("VServiziCustom.findByInterventoCustomAndAreatId");
		q.setParameter("interventoCustomId", interventoCustomId);
		q.setParameter("areatId", areatId);
		return (List<VServiziCustom>) q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ArRelIntCustomIstat> findInterventoIstatByInterventoCustom(Long interventoCustomId) {
		Query q = em.createNamedQuery("ArRelIntCustomIstat.findByTipoInterventoId");
		q.setParameter("interventoCustomId", interventoCustomId);
		return (List<ArRelIntCustomIstat>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<VServiziCustom> findAreaTInterventoById( Long idIntervento) {
		List<VServiziCustom> listaV = null;
		if(idIntervento != null){
			try{
				Query q = em.createNamedQuery("VServiziCustom.findAreaTInterventoById");  
				q.setParameter("interventoCustomId", idIntervento);
				List<Object[]> lista = q.getResultList();
				if(lista != null)
					listaV = new ArrayList<VServiziCustom>();
				
				for(Object[] o: lista){
					VServiziCustom dto = new VServiziCustom();
					dto.setInterventoCustomId((Long) o[0]);
					dto.setAreatId((Integer) o[1]);
					dto.setAreatDescrizione((String) o[2]);
					dto.setCodiceMemoAreat((String) o[3]);
					
					listaV.add(dto);
				}
			}catch(Throwable e){
				logger.error(e.getMessage(), e);
			}
		}
		return listaV;
	}
	
	@SuppressWarnings("unchecked")
	public List<VServiziCustom> findDettaglioInterventobyAreaTId(List<String> areeId) {
		List<VServiziCustom> listaV = null;
		if(areeId != null && areeId.size() >0){
			try{
				Query q = em.createNamedQuery("VServiziCustom.findDettaglioInterventobyAreaTId");  
				q.setParameter("areeId", areeId);
				List<Object[]> lista = q.getResultList();
				if(lista != null)
					listaV = new ArrayList<VServiziCustom>();
				
				for(Object[] o: lista){
					VServiziCustom dto = new VServiziCustom();
					dto.setInterventoCustomId((Long) o[0]);
					dto.setInterventoDescrizione((String) o[1]);
										
					listaV.add(dto);
				}
			}catch(Throwable e){
				logger.error(e.getMessage(), e);
			}
		}
		return listaV;
	}
	@SuppressWarnings("unchecked")
	public List<VServiziCustom> findAreaTInterventoByIdeAreaTSoggetto(Long interventoCustomId, List<String> areeId) {
		List<VServiziCustom> listaV = null;
		if(interventoCustomId != null && areeId!=null ){
			try{
				Query q = em.createNamedQuery("VServiziCustom.findAreaTInterventoByIdeAreaTSoggetto");
				q.setParameter("interventoCustomId", interventoCustomId);
				q.setParameter("areeId", areeId);
				List<Object[]> lista = q.getResultList();
				if(lista != null)
					listaV = new ArrayList<VServiziCustom>();
				
				for(Object[] o: lista){
					VServiziCustom dto = new VServiziCustom();
					dto.setInterventoCustomId((Long) o[0]);
					dto.setAreatId((Integer) o[1]);
					dto.setAreatDescrizione((String) o[2]);
					dto.setCodiceMemoAreat((String) o[3]);
					
					listaV.add(dto);
				}
			}catch(Throwable e){
				logger.error(e.getMessage(), e);
			}
		}
		return listaV;
	}

	@SuppressWarnings("unchecked")
	public List<ArTClasse> findArTClasseByTipoInterventoId(String codiceMemo) {
		try{
		Query q = em.createNamedQuery("ArTClasse.findByCodiceMemo");  

		q.setParameter("codiceMemo", codiceMemo);
		
		List<ArTClasse> lst = q.getResultList();  
		
		logger.debug("findArTClasseByTipoInterventoId["+codiceMemo+"] - RESULT["+lst.size()+"]");
		
		
		return lst;
		}
		catch(Throwable e){
			logger.error(e);
			throw new CarSocialeServiceException(e);
			
		}
	
	}
	
	public List<CsCfgIntEsegStatoInt> findCfgIntEsegByTipoInterventoId(Long tipoInterventoId) {
		List<CsCfgIntEsegStatoInt> lst = null;
		if(tipoInterventoId!=null){
			try{
				//logger.debug("INIZIO findCfgIntEsegByTipoInterventoId [id:"+tipoInterventoId+"]");
				Query q = em.createNamedQuery("CsCfgIntEseg.findConfigIntErogByTipoIntervento");
				q.setParameter("tipoInterventoId", tipoInterventoId);
				lst  = (List<CsCfgIntEsegStatoInt>) q.getResultList();
				
				if(lst!=null && lst.size()>0){
					//cfg = lst.get(0);
				   /* if(lst.size()>1)
				    	logger.warn("findCfgIntEsegByTipoInterventoId[id:"+tipoInterventoId+"] CsCfgIntEseg [MULTIPLI -"+lst.size()+"]");*/
				}else
					logger.warn("findCfgIntEsegByTipoInterventoId - No Results per Tipo Intervento[id:"+tipoInterventoId+"]");
				
				
				if (lst != null) {
					//logger.debug("findCfgIntEsegByTipoInterventoId[id:"+tipoInterventoId+"] --> CsCfgIntEseg [id:"+cfg.getId()+"]");
					for(CsCfgIntEsegStatoInt s : lst){
						for(CsCfgIntEsegAttUm ums : s.getCsCfgIntEsegAttUms())
							ums.getCsCfgAttrUm().getCsCfgAttributo().getCsCfgAttrOptions().size();
					}
					
				}
				//logger.debug("FINE findCfgIntEsegByTipoInterventoId [id:"+tipoInterventoId+"] size["+lst.size()+"]");
				
		    }catch(Exception e){	
				throw new CarSocialeServiceException("findCfgIntEsegByTipoInterventoId",e);
			}
		}else
			logger.warn("findCfgIntEsegByTipoInterventoId - Tipo Intervento id NON DEFINITO");
		
		return lst;
	}
	
	public HashMap<Long, ErogStatoCfgDTO> findConfigIntEsegByTipoIntervento(Long tipoInterventoId){
		List<CsCfgIntEsegStatoInt> lst = findCfgIntEsegByTipoInterventoId(tipoInterventoId);
		HashMap<Long, ErogStatoCfgDTO> mappaEseg = new HashMap<Long, ErogStatoCfgDTO> ();
		if(lst!=null)
			mappaEseg = IntEsegAttrUtilsBean.getMappaStatoAttributi(lst);
		else
			logger.warn("Lista 'CsCfgIntEseg' vuota per il tipo intervento:"+tipoInterventoId);	
		return mappaEseg;
	}
	

	@SuppressWarnings("unchecked")
	public List<CsCfgIntEsegStato> getListaIntEsegStatoByTipiStato(List<String> tipiStato, Long tipoInterventoId) {
		List<CsCfgIntEsegStato> lst = new ArrayList<CsCfgIntEsegStato>();
		// Carico gli stati dell'intervento che siano del tipo specificato (se non è attivo il flag BYPASS_TIPO_STATO)
		try{
		//	logger.debug("getListaIntEsegStatoByTipiStato TIPI["+tipiStato+"] INTERVENTO["+tipoInterventoId+"]");
			Query q = em.createNamedQuery("CsCfgIntEsegStato.findIntEsegStatoByTipiStatoOrBypass");
			q.setParameter("tipiStato", tipiStato);
			q.setParameter("tipoInterventoId", tipoInterventoId);
			lst = (List<CsCfgIntEsegStato>) q.getResultList();
		//	logger.debug("getListaIntEsegStatoByTipiStato RESULT["+lst.size()+"]");
		}catch(Exception e){
			logger.error("getListaIntEsegStatoByTipiStato", e);
			throw new CarSocialeServiceException(e);
		}
		return lst;
	}

	public CsTbSinaRisposta findSinaRisposta(Long rispostaId) {
		return em.find(CsTbSinaRisposta.class, rispostaId);
	}
}
