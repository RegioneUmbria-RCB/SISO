package it.webred.cs.csa.ejb.dao;

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
import org.springframework.beans.factory.annotation.Autowired;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.CsRelRelazioneProblDTO;
import it.webred.cs.csa.ejb.dto.CsRelRelazioneProblDTO;
import it.webred.cs.csa.ejb.dto.DiarioAnagraficaDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.TriageItemDTO;
import it.webred.cs.data.model.ArBiInviante;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsCDiarioDove;
import it.webred.cs.data.model.CsCInterventoPrForm;
import it.webred.cs.data.model.CsCTipoColloquio;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsCfgParametri;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsRelRelazioneProbl;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.data.model.CsRelSottocartDocProt;
import it.webred.cs.data.model.CsTbAbitGestProprietario;
import it.webred.cs.data.model.CsTbAbitTitoloGodimento;
import it.webred.cs.data.model.CsTbAssenzaPermesso;
import it.webred.cs.data.model.CsTbBuono;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbCondLavoro;
import it.webred.cs.data.model.CsTbContatto;
import it.webred.cs.data.model.CsTbDisabEnte;
import it.webred.cs.data.model.CsTbDisabGravita;
import it.webred.cs.data.model.CsTbDisabTipologia;
import it.webred.cs.data.model.CsTbDisponibilita;
import it.webred.cs.data.model.CsTbEsenzioneRiduzione;
import it.webred.cs.data.model.CsTbFormaGiuridica;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.data.model.CsTbIcd10;
import it.webred.cs.data.model.CsTbIcd9;
import it.webred.cs.data.model.CsTbIngMercato;
import it.webred.cs.data.model.CsTbInterventiUOL;
import it.webred.cs.data.model.CsTbMacroAttivita;
import it.webred.cs.data.model.CsTbMacroIntervento;
import it.webred.cs.data.model.CsTbMacroSegnal;
import it.webred.cs.data.model.CsTbMapGit2CsPK;
import it.webred.cs.data.model.CsTbMapStacivGit2Cs;
import it.webred.cs.data.model.CsTbMapTipoRapGit2Cs;
import it.webred.cs.data.model.CsTbMicroAttivita;
import it.webred.cs.data.model.CsTbMicroIntervento;
import it.webred.cs.data.model.CsTbMicroSegnal;
import it.webred.cs.data.model.CsTbMotivoChiusuraInt;
import it.webred.cs.data.model.CsTbMotivoChiusuraPai;
import it.webred.cs.data.model.CsTbMotivoSegnal;
import it.webred.cs.data.model.CsTbPermesso;
import it.webred.cs.data.model.CsTbPotesta;
import it.webred.cs.data.model.CsTbProbl;
import it.webred.cs.data.model.CsTbProblematica;
import it.webred.cs.data.model.CsTbProfessione;
import it.webred.cs.data.model.CsTbProgettoAltro;
import it.webred.cs.data.model.CsTbResponsabilita;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.data.model.CsTbScuola;
import it.webred.cs.data.model.CsTbSecondoLivello;
import it.webred.cs.data.model.CsTbServComunita;
import it.webred.cs.data.model.CsTbServLuogoStr;
import it.webred.cs.data.model.CsTbServResRetta;
import it.webred.cs.data.model.CsTbServSemiresRetta;
import it.webred.cs.data.model.CsTbSettoreImpiego;
import it.webred.cs.data.model.CsTbSinaDomanda;
import it.webred.cs.data.model.CsTbSinaRisposta;
import it.webred.cs.data.model.CsTbSottocartellaDoc;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbStatus;
import it.webred.cs.data.model.CsTbStesuraRelazioniPer;
import it.webred.cs.data.model.CsTbTipoAbitazione;
import it.webred.cs.data.model.CsTbTipoAlert;
import it.webred.cs.data.model.CsTbTipoCirc4;
import it.webred.cs.data.model.CsTbTipoComunita;
import it.webred.cs.data.model.CsTbTipoContratto;
import it.webred.cs.data.model.CsTbTipoContributo;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.data.model.CsTbTipoIndirizzo;
import it.webred.cs.data.model.CsTbTipoIsee;
import it.webred.cs.data.model.CsTbTipoOperatore;
import it.webred.cs.data.model.CsTbTipoPai;
import it.webred.cs.data.model.CsTbTipoProgetto;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.data.model.CsTbTipoRetta;
import it.webred.cs.data.model.CsTbTipoRientriFami;
import it.webred.cs.data.model.CsTbTipoScuola;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.data.model.CsTbTitoloStudio;
import it.webred.cs.data.model.CsTbTribStruttura;
import it.webred.cs.data.model.CsTbTutela;
import it.webred.cs.data.model.CsTbUnitaMisura;

@Named
public class ConfigurazioneDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DiarioDAO diarioDao;
	@Autowired
	private DocumentoDAO documentoDao;
	@Autowired
	private CasoDAO casoDAO;
	
	@Autowired
	private SchedaDAO schedaDao;
	

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
	public List<CsTbTitoloStudio> getTitoloStudioAbilitato() {

		try {

			Query q = em.createNamedQuery("CsTbTitoloStudio.findAllAbilit");
			List<CsTbTitoloStudio> lista = q.getResultList();
			
				return lista;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
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

	@SuppressWarnings("unchecked")
	public List<CsTbProblematica> getProblematiche() {

		try {

			Query q = em.createNamedQuery("CsTbProblematica.findAllAbil");
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

	@SuppressWarnings("unchecked")
	public List<CsTbStesuraRelazioniPer> getStesuraRelazioniPer() {

		try {

			Query q = em.createNamedQuery("CsTbStesuraRelazioniPer.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTitoloStudio> getTitoliStudio() {

		try {

			Query q = em.createNamedQuery("CsTbTitoloStudio.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
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
	public List<CsTbTipoContratto> getTipoContratto() {

		try {

			Query q = em.createNamedQuery("CsTbTipoContratto.findAllAbil");
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

	@SuppressWarnings("unchecked")
	public List<CsOOrganizzazione> getOrganizzazioni() {

		try {

			Query q = em.createNamedQuery("CsOOrganizzazione.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsOOrganizzazione> getOrganizzazioniAccesso() {

		try {
			Query q = em.createNamedQuery("CsOOrganizzazione.findAllAbilAccesso");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsOOrganizzazione> getOrganizzazioniAll() {
		Query q = em.createNamedQuery("CsOOrganizzazione.findAll");
		return q.getResultList();
	}

	public void salvaOrganizzazione(CsOOrganizzazione org) {
		em.persist(org);
	}

	public void updateOrganizzazione(CsOOrganizzazione org) {
		em.merge(org);
	}

	public void eliminaOrganizzazione(Long id) {
		Query q = em.createNamedQuery("CsOOrganizzazione.eliminaById");
		q.setParameter("id", id);
		q.executeUpdate();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(long idOrganizzazione) {
		Query q = em.createNamedQuery("CsOSettore.findSettoreBASICByOrganizzazione").setParameter("idOrganizzazione", idOrganizzazione);
		List results = q.getResultList();
		return results;
	}

	public void salvaSettore(CsOSettore cs) {
		em.persist(cs);
	}

	public void updateSettore(Object cs) {
		em.merge(cs);
	}

	public void eliminaSettore(Long id) {
		Query q = em.createNamedQuery("CsRelSettoreCsocTipoInter.eliminaBySettoreId");
		q.setParameter("id", id);
		q.executeUpdate();
		
		Query q1 = em.createNamedQuery("CsRelSettoreCatsoc.eliminaBySettoreId");
		q1.setParameter("id", id);
		q1.executeUpdate();
		
		Query q2 = em.createNamedQuery("CsOSettore.eliminaById");
		q2.setParameter("id", id);
		q2.executeUpdate();
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

	@SuppressWarnings("unchecked")
	public List<CsTbMacroSegnal> getMacroSegnalazioni() {

		try {

			Query q = em.createNamedQuery("CsTbMacroSegnal.findAllAbil");
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

	@SuppressWarnings("unchecked")
	public List<CsTbMotivoSegnal> getMotivoSegnalazioni() {

		try {

			Query q = em.createNamedQuery("CsTbMotivoSegnal.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbMotivoSegnal getMotivoSegnalazioneById(Long id) {

		CsTbMotivoSegnal cs = em.find(CsTbMotivoSegnal.class, id);
		return cs;

	}

	@SuppressWarnings("unchecked")
	public List<CsTbDisabEnte> getDisabEnte() {

		try {

			Query q = em.createNamedQuery("CsTbDisabEnte.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbDisabGravita> getDisabGravita() {

		try {

			Query q = em.createNamedQuery("CsTbDisabGravita.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
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

	@SuppressWarnings("unchecked")
	public List<CsTbScuola> getScuole() {

		try {

			Query q = em.createNamedQuery("CsTbScuola.findAllAbil");
			return q.getResultList();

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

	@SuppressWarnings("unchecked")
	public List<CsTbScuola> getScuoleByComuneTipo(String comune, Long tipoId) {

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

	public List<CsTbScuola> getScuoleByComuneAnnoTipo(String anno, Long tipoId, String belfiore) {
		try {

			Query q = em.createNamedQuery("CsTbScuola.findByComuneAnnoTipo");
			q.setParameter("anno", anno);
			q.setParameter("idTipo", tipoId);
			q.setParameter("comune", belfiore);
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<CsTbTipoScuola> getTipoScuole() {

		try {

			Query q = em.createNamedQuery("CsTbTipoScuola.findAllAbil");
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

	@SuppressWarnings("unchecked")
	public List<CsOOrganizzazione> getOrganizzazioneByCodCatastale(String codice) {
		try {
			if (codice != null) {
				Query q = em.createNamedQuery("CsOOrganizzazione.findByCodCatastale");
				q.setParameter("codice", codice);
				return q.getResultList();
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public CsOOrganizzazione getOrganizzazioneByCodFittizio(String codice) {
		try {
			if (codice != null) {
				Query q = em.createNamedQuery("CsOOrganizzazione.findByCodFittizio");
				q.setParameter("codice", codice);
				List<CsOOrganizzazione> lst = q.getResultList();
				if(!lst.isEmpty()) return (CsOOrganizzazione)lst.get(0);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}



	/* SISO-663 SM */

	public CsOOrganizzazione getOrganizzazioneCapofila() {
		try {
			TypedQuery<CsOOrganizzazione> q = em.createNamedQuery("CsOOrganizzazione.findCapofila", CsOOrganizzazione.class);
			List<CsOOrganizzazione> lst = q.getResultList();
			if(lst.size()>0)
				return lst.get(0);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/* -=- */



	@SuppressWarnings("unchecked")
	public List<CsOSettore> getSettoreAll() {
		try {

			Query q = em.createNamedQuery("CsOSettore.findAll");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public CsOSettore getSettoreById(Long id) {
		try{
			return em.find(CsOSettore.class, id);
		}catch(Throwable e){
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

	public CsTbMacroAttivita getMacroAttivitaById(Long id) {

		CsTbMacroAttivita cs = em.find(CsTbMacroAttivita.class, id);
		return cs;

	}

	@SuppressWarnings("unchecked")
	public List<CsTbMicroAttivita> getMicroAttivita() {
		List<CsTbMicroAttivita> lst = new ArrayList<CsTbMicroAttivita>();
		try {

			Query q = em.createNamedQuery("CsTbMicroAttivita.findAllAbil");
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

	public CsOOrganizzazione getOrganizzazioneById(Long obj) {
		if(obj!=null)
			return em.find(CsOOrganizzazione.class, obj);
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
		Query q = em.createNamedQuery("CsCInterventoPrForm.find");
		q.setParameter("progetto", progetto);
		q.setParameter("tipoInterventoId", tipoIntervento);
		q.setParameter("tipoInterventoCustomId", tipoInterventoCustom);
		q.setParameter("catSocialeId", idCatSoc);
		List<CsCInterventoPrForm> lst = q.getResultList();
		if(!lst.isEmpty()) return ((CsCInterventoPrForm)lst.get(0)).getRifFormInterventoPrDett();
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
		if(id!=null) tipo= em.find(CsCDiarioConchi.class, id);
		return tipo;
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
		return q.getResultList();
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
	
	//SISO-812
	public List<Boolean> getFlagNascondiInformazioniFlagByCasoId(Long casoId) {
		Query q = em.createNamedQuery("CsACasoAccessoFascicolo.getFlagNascondiFascicoloByCasoId");
		q.setParameter("casoId", casoId);
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
						String[] value_split = o.getTipi_pai().split("|");
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
		
	//TODO TASK SISO 1044
	@SuppressWarnings("unchecked")
	public List<DiarioAnagraficaDTO> getUlterioriSoggettiInteressati(Long casoId)
	{
		try {
			HashMap<Long, DiarioAnagraficaDTO> idAna2diarioAna;
			idAna2diarioAna = new HashMap<Long, DiarioAnagraficaDTO>();
			List<CsAAnagrafica> anagrafiche;
			anagrafiche = new ArrayList<CsAAnagrafica>();

			
			CsACaso casoTrovato=casoDAO.findCasoById(casoId);
			
						
			
			
			anagrafiche= schedaDao.findComponentiGiaFamigliariBySoggettoCf(casoTrovato.getCsASoggetto().getCsAAnagrafica().getCf(),null);
						
			
			for (CsAAnagrafica fam : anagrafiche) {
				
				//solo i familiari con CF valorizzato
				if(fam.getCf() == null || fam.getCf().trim().isEmpty()){
					continue;
				}
							
				DiarioAnagraficaDTO da = new DiarioAnagraficaDTO();
				da.setAnagraficaId(fam.getId());
				da.setCf(fam.getCf());
				da.setCognome(fam.getCognome());
				da.setNome(fam.getNome());
				idAna2diarioAna.put(fam.getId(), da);
			}
					
			ArrayList<DiarioAnagraficaDTO> lstr = new ArrayList<DiarioAnagraficaDTO>(idAna2diarioAna.values());
			return lstr;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		
		return null;
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
	public List<SelectItem> getListaRiunioneCon(boolean riunioneCon) {
		
		
		try {
			
			List<SelectItem> RiunioneConList = new ArrayList<SelectItem>();
//			SelectItem nullSi = new SelectItem("NULL","- seleziona -");		
//			//nullSi.setNoSelectionOption(true);
//			RiunioneConList.add(nullSi);

			
			List<CsOSettore> lst = getSettoreAll();
			LinkedHashMap<String,List<SelectItem>> mappa = new LinkedHashMap<String,List<SelectItem>>();
			
			if (lst != null) {
				for (CsOSettore obj : lst) {
					//String belfioreOrg = obj.getCsOOrganizzazione().getBelfiore();
					//boolean comuneValido = belfioreOrg==null || belfioreOrg.equals(bo.getEnteId());	
					boolean comuneValido = true;
					boolean aggiungi = (riunioneCon && obj.getFlgRiunioneCon() != null);

					if (comuneValido && aggiungi) {
						String nomeOrg = obj.getCsOOrganizzazione().getNome();
						SelectItem si = new SelectItem(obj, obj.getNome());
						si.setDisabled(!obj.getAbilitato());
						List<SelectItem> gi = mappa.get(nomeOrg);
						if(gi==null)
							gi = new ArrayList<SelectItem>();

						gi.add(si);
						mappa.put(nomeOrg, gi);
					}				
				}
				
				Iterator<String> iter = mappa.keySet().iterator();
				while(iter.hasNext()){
					String s = (String) iter.next();
					mappa.get(s);
					SelectItemGroup g = new SelectItemGroup(s);
					List<SelectItem> lstIt = mappa.get(s);
					g.setSelectItems(lstIt.toArray(new SelectItem[lstIt.size()]));

					RiunioneConList.add(g);
				}
			}		

			return RiunioneConList;
			
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
	
	
	private List<CsRelRelazioneProblDTO> acquisizioneProblematichePrecedenti(Long casoId) {
		//analisi problematiche
		
		CsDRelazione relazioneConProblematichePrecedenti = diarioDao.findLastRelazioneProblAperte(casoId);
		if (relazioneConProblematichePrecedenti != null && relazioneConProblematichePrecedenti.getCsRelRelazioneProbl() != null) {
			List<CsRelRelazioneProblDTO> relProblToAdd = new ArrayList<CsRelRelazioneProblDTO>();
			for (CsRelRelazioneProbl relProbl : relazioneConProblematichePrecedenti.getCsRelRelazioneProbl()) {
				if (Boolean.FALSE.equals(relProbl.getFlagRisolta())) {
					// copia della problematica non risolta precedente
					CsRelRelazioneProblDTO relProblDTO=new CsRelRelazioneProblDTO();
					relProblDTO.setByModel(relProbl);
					relProblToAdd.add(relProblDTO);
				}
			}

			return relProblToAdd;

		}

		return null;
	}
	
	public List<CsRelRelazioneProblDTO> getProblematichePrecedenti(Long casoId){
		
		
		
		try {
			
			List<CsRelRelazioneProblDTO> relProblToAdd = acquisizioneProblematichePrecedenti(casoId);
			
//			List<CsRelRelazioneProbl> problematichePrecedentiList=new ArrayList<CsRelRelazioneProbl>();
//			
//			CsACaso casoTrovato=casoDAO.findCasoById(casoId);
//			List<RelazioneDTO> listaRelazioniDTO = new ArrayList<RelazioneDTO>();
//			if(casoId!=null){
//				List<CsDRelazione> lstr = diarioDao.findRelazioniByCaso(casoId);
//				for (CsDRelazione r : lstr) {
//					CsLoadDocumento doc = documentoDao.findLoadDocumentoByDiarioId(r.getDiarioId());
//					List<PaiDTOExt> lstPai = diarioDao.loadPaiEntities(r.getCsDDiario());
//					RelazioneDTO dto = new RelazioneDTO(r, doc, lstPai);
//					listaRelazioniDTO.add(dto);
//				}
//			}
//					
//			List<Long> casoIds = new ArrayList<Long>();
//			   Long idAnagrafica = casoTrovato.getCsASoggetto().getAnagraficaId();
//			   List<CsACaso> lCasi = diarioDao.findDiarioAnaCasoIdsByAnagraficaId(idAnagrafica,new Long(6)); //6 = ATTIVITA PROFESSIONALI
//			   if(lCasi!=null)
//			   {
//				   for (CsACaso csACaso : lCasi) {
//					   casoIds.add(csACaso.getId());
//				   }
//				  
//				for(Long l : casoIds){
//					Long idCaso =l;
//					List<RelazioneDTO> lst = new ArrayList<RelazioneDTO>();
//					if(idCaso!=null){
//						List<CsDRelazione> lstr = diarioDao.findRelazioniByCaso(idCaso);
//						for (CsDRelazione r : lstr) {
//							CsLoadDocumento doc = documentoDao.findLoadDocumentoByDiarioId(r.getDiarioId());
//							List<PaiDTOExt> lstPai = diarioDao.loadPaiEntities(r.getCsDDiario());
//							RelazioneDTO dto = new RelazioneDTO(r, doc, lstPai);
//							lst.add(dto);
//						}
//					}
//					
//					listaRelazioniDTO.addAll(lst);
//					
//					for (RelazioneDTO relazioneDTO : listaRelazioniDTO) {
//						problematichePrecedentiList.addAll(relazioneDTO.getRelazione().getCsRelRelazioneProbl());
//						
//					}
//					
//					
//					
//					
//					
//				}
//				   
//			   }
			  
			   return relProblToAdd;
			   
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return null;
		
		
		
		
	}
	 //TASK SISO 1044 --FINE

	public List<CsTbTribStruttura> getStruttureTribunale() {
		try {

			Query q = em.createNamedQuery("CsTbTribStruttura.findAllAbil");
			return q.getResultList();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	//SISO-155
	public List<CsTbTipoPai> findListaProgettiPai(){
		Query query = em.createQuery("SELECT p FROM CsTbTipoPai p WHERE p.progetto != null");
		
		return (List<CsTbTipoPai>)query.getResultList();
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
	

	// #ROMACAPITALE inizio
	@SuppressWarnings("unchecked")
	public List<Long> findIdSettoriByInterventoISTATandInterventoCustom(Long idInterventoISTAT, Long idInterventoCUSTOM) {
		try {
			Query q = em.createNamedQuery("CsRelIntSettore.findIdSettoriByInterventoISTATandInterventoCustom");
			q.setParameter("idInterventoISTAT", idInterventoISTAT);
			q.setParameter("idInterventoCUSTOM", idInterventoCUSTOM);
			return q.getResultList();
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findIdSettoriByInterventoCustom(Long idInterventoCUSTOM) {
		try {
			Query q = em.createNamedQuery("CsRelIntSettore.findIdSettoriByInterventoCustom");
			q.setParameter("idInterventoCUSTOM", idInterventoCUSTOM);
			return q.getResultList();
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsOSettore> findSettoriById(List<Long> listaIdSettori) {
		try {
			if(!listaIdSettori.isEmpty()){
				Query q = em.createNamedQuery("CsOSettore.findSettoriById");
				q.setParameter("listaIdSettori", listaIdSettori);
				return (List<CsOSettore>) q.getResultList();
			}
		} 
		catch (Exception e) {
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
	public String findCodiceDocumentoGed(Long sottocartellaDocId, String codRoutingOrgId) {
		try {
			Query q = em.createNamedQuery("CsRelSottocartDocProt.findCodiceDocumentoGed");
			q.setParameter("sottocartellaDocId", sottocartellaDocId);
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
			Object o = q.getSingleResult();
			if (o != null){
				return (CsTbProgettoAltro)o;
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
			Query q = em.createQuery(query);
			List<CsTbUnitaMisura> retList = q.getResultList();
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
}
