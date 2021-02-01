package it.webred.ct.data.access.basic.f24.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.f24.F24ServiceException;
import it.webred.ct.data.access.basic.f24.dto.RicercaF24DTO;
import it.webred.ct.data.access.basic.f24.dto.TipoTributoDTO;
import it.webred.ct.data.model.f24.SitTCodTributo;
import it.webred.ct.data.model.f24.SitTCodTributoAnno;
import it.webred.ct.data.model.f24.SitTF24Annullamento;
import it.webred.ct.data.model.f24.SitTF24CodSogg;
import it.webred.ct.data.model.f24.SitTF24Versamenti;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class F24JPAImpl extends CTServiceBaseDAO implements F24DAO {

	private static final long serialVersionUID = 1L;

	@Override
	public SitTF24Versamenti getVersamentoById(String id) {

		SitTF24Versamenti v = null;

		try {

			logger.debug("getVersamentoById - id[" + id + "]");
			Query q = manager_diogene
					.createNamedQuery("SitTF24Versamenti.getVersamentoByID");
			q.setParameter("id", id);

			v = (SitTF24Versamenti) q.getSingleResult();

		} catch (Throwable t) {
			logger.error("getVersamentoById", t);
			throw new F24ServiceException(t);
		}

		return v;
	}
	
	@Override
	public SitTF24Annullamento getAnnullamentoById(String id) {

		SitTF24Annullamento v = null;

		try {

			logger.debug("getAnnullamentoById - id[" + id + "]");
			Query q = manager_diogene
					.createNamedQuery("SitTF24Annullamento.getAnnullamentoByID");
			q.setParameter("id", id);

			v = (SitTF24Annullamento) q.getSingleResult();

		} catch (Throwable t) {
			logger.error("getAnnullamentoById", t);
			throw new F24ServiceException(t);
		}

		return v;
	}

	@Override
	public String getDescTributoByCodice(String codice) {

		String desc = null;
		try {
			logger.debug("getDescTributoByCodice - codice[" + codice + "]");
			if(codice!=null){
				Query q = manager_diogene.createNamedQuery("SitTCodTributo.getDescTributoByCod");
				q.setParameter("codice", codice);
	
				SitTCodTributo v = (SitTCodTributo) q.getSingleResult();
				desc = v!=null ? v.getDescrizione() : null;
			}
		}catch(NoResultException nre){
			logger.error("getDescTributoByCodice: nessuna decodifica individuata", nre);
		}catch (Throwable t) {
			logger.error("getDescTributoByCodice", t);
			throw new F24ServiceException(t);
		}

		return desc;

	}

	@Override
	public String getDescSoggByCodice(String codice) {

		String desc = null;
		
		try {
			logger.debug("getDescSoggByCodice - codice[" + codice + "]");
			if(codice!=null){
				logger.debug("getDescSoggByCodice - codice[" + codice + "]");
				Query q = manager_diogene
						.createNamedQuery("SitTF24CodSogg.getTipoSoggByCodice");
				q.setParameter("codice", codice);
	
				SitTF24CodSogg v = (SitTF24CodSogg) q.getSingleResult();
				desc = v!=null ? v.getDescrizione() : null;
			}
		}catch(NoResultException nre){
			logger.error("getDescSoggByCodice: nessuna decodifica individuata", nre);
		}catch (Throwable t) {
			logger.error("getDescSoggByCodice", t);
			throw new F24ServiceException(t);
		}

		return desc;

	}
	
	
	@Override
	public List<TipoTributoDTO> getListaTipoTributo(boolean anno, boolean codice) {

		List<TipoTributoDTO> lst = new ArrayList<TipoTributoDTO>();
		Query q = null;
		try {

			logger.debug("getListaTipoTributo");
			if(anno){
			   q = manager_diogene.createNamedQuery("SitTCodTributoAnno.getLista");
			   
			   List<SitTCodTributoAnno> result = q.getResultList();
				for(SitTCodTributoAnno ca: result){
				
					TipoTributoDTO dto = new TipoTributoDTO();
					dto.setAnno(Long.toString(ca.getId().getAnno()));
					dto.setCodice(ca.getSitTCodTributo().getCodice());
					dto.setDescrizione(ca.getSitTCodTributo().getDescrizione());
					lst.add(dto);
				}
			   
			}else if(codice){
				q = manager_diogene.createNamedQuery("SitTCodTributo.getLista");
				
				 List<SitTCodTributo> result = q.getResultList();
					for(SitTCodTributo ca: result){
						TipoTributoDTO dto = new TipoTributoDTO();
						dto.setCodice(ca.getCodice());
						dto.setDescrizione(ca.getDescrizione());
						lst.add(dto);
					}
				
			}else{
				q= manager_diogene.createNamedQuery("SitTCodTributo.getListaDescrizioni");
				
				List<String> result = q.getResultList();
				for(String s: result){
					TipoTributoDTO dto = new TipoTributoDTO();
					dto.setDescrizione(s);
					lst.add(dto);
				}
			}

		}catch (Throwable t) {
			logger.error("getListaTipoTributo", t);
			throw new F24ServiceException(t);
		}

		return lst;

	}

	@Override
	public List<SitTF24Versamenti> getListaVersamentiByCF(String cf) {
			List<SitTF24Versamenti> lst = new ArrayList<SitTF24Versamenti>();
		
		try {

			logger.debug("getListaVersamentiByCF");
			Query q = manager_diogene.createNamedQuery("SitTF24Versamenti.getListaVersamentiByCF");
			q.setParameter("codfisc", cf.toUpperCase());
			
			lst = q.getResultList();
			

		}catch (Throwable t) {
			logger.error("getListaVersamentiByCF", t);
			throw new F24ServiceException(t);
		}

		return lst;
	}
	
	@Override
	public List<SitTF24Versamenti> getListaVersamentiByCFOrderByTipoAnno(String cf) {
			List<SitTF24Versamenti> lst = new ArrayList<SitTF24Versamenti>();
		
		try {

			logger.debug("getListaVersamentiByCF");
			Query q = manager_diogene.createNamedQuery("SitTF24Versamenti.getListaVersamentiByCFOrderByTipoAnno");
			q.setParameter("codfisc", cf.toUpperCase());
			
			lst = q.getResultList();
			

		}catch (Throwable t) {
			logger.error("getListaVersamentiByCF", t);
			throw new F24ServiceException(t);
		}

		return lst;
	}

	@Override
	public List<SitTF24Versamenti> getListaVersamentiByAnn(RicercaF24DTO search) {
		List<SitTF24Versamenti> lst = new ArrayList<SitTF24Versamenti>();
		
		try {

			logger.debug("getListaVersamentiByAnn[codEnte:"+search.getCodEnte()+"][codfisc:"+search.getCf()+"][dtRipartizione:"+search.getDtRipartizione()+"]" +
					"[dtRiscossione:"+ search.getDtRiscossione()+"][progRipartizione:"+search.getProgRipartizione()+"][dtBonifico:"+search.getDtBonifico()+"]" +
							"[codTributo:"+search.getCodTributo()+"][annoRif:"+ search.getAnnoRif()+"]");
			Query q = manager_diogene.createNamedQuery("SitTF24Versamenti.getListaVersamentiByAnn");
			q.setParameter("codEnte", search.getCodEnte());
			q.setParameter("codfisc", search.getCf());
			q.setParameter("dtRipartizione", search.getDtRipartizione());
			q.setParameter("dtRiscossione", search.getDtRiscossione());
			q.setParameter("progRipartizione", search.getProgRipartizione());
			q.setParameter("dtBonifico", search.getDtBonifico());
			q.setParameter("codTributo", search.getCodTributo());
			q.setParameter("annoRif", search.getAnnoRif());
			
			lst = q.getResultList();
			
			logger.debug("result getListaVersamentiByAnn["+lst.size()+"]");
			

		}catch (Throwable t) {
			logger.error("getListaVersamentiByAnn", t);
			throw new F24ServiceException(t);
		}
		return lst;
	}

	@Override
	public List<SitTF24Annullamento> getListaAnnullamentiByVer(RicercaF24DTO search) {
		
		List<SitTF24Annullamento> lst = new ArrayList<SitTF24Annullamento>();
		try {

			logger.debug("getListaAnnullamentiByVer[codEnte:"+search.getCodEnte()+"][codfisc:"+search.getCf()+"][dtRipartizione:"+search.getDtRipartizione()+"]" +
					"[dtRiscossione:"+ search.getDtRiscossione()+"][progRipartizione:"+search.getProgRipartizione()+"][dtBonifico:"+search.getDtBonifico()+"]" +
							"[codTributo:"+search.getCodTributo()+"][annoRif:"+ search.getAnnoRif()+"]");
			Query q = manager_diogene.createNamedQuery("SitTF24Annullamento.getListaAnnullamentiByVer");
			q.setParameter("codEnte", search.getCodEnte());
			q.setParameter("codfisc", search.getCf());
			q.setParameter("dtRipartizione", search.getDtRipartizione());
			q.setParameter("dtRiscossione", search.getDtRiscossione());
			q.setParameter("progRipartizione", search.getProgRipartizione());
			q.setParameter("dtBonifico", search.getDtBonifico());
			q.setParameter("codTributo", search.getCodTributo());
			q.setParameter("annoRif", search.getAnnoRif());
			
			lst = q.getResultList();
			
			logger.debug("result getListaAnnullamentiByVer["+lst.size()+"]");

		}catch (Throwable t) {
			logger.error("getListaAnnullamentiByVer", t);
			throw new F24ServiceException(t);
		}
		
		return lst;
	}

	@Override
	public List<SitTF24Annullamento> getListaAnnullamentiByCF(String cf) {
		List<SitTF24Annullamento> lst = new ArrayList<SitTF24Annullamento>();
		
		try {

			logger.debug("getListaSitTF24AnnullamentoByCF");
			Query q = manager_diogene.createNamedQuery("SitTF24Annullamento.getListaByCF");
			q.setParameter("codfisc", cf.toUpperCase());
			
			lst = q.getResultList();
			

		}catch (Throwable t) {
			logger.error("getListaSitTF24AnnullamentoByCF", t);
			throw new F24ServiceException(t);
		}

		return lst;
	}
	
	@Override
	public Date getDtAggVersamenti(RicercaF24DTO search) {
		Date dtAgg = null;
		
		try {
			logger.debug("getDtAggVersamenti");
			//per max data fornitura
			Query q = manager_diogene.createNamedQuery("SitTF24Testata.getMaxDtFornitura");
			dtAgg = (Date)q.getSingleResult();
			//per max data caricamento
			/*Query q = manager_diogene.createNamedQuery("SitTF24Versamenti.getMaxDtInsMod");
			List<Object[]> res = q.getResultList();
			for(Object[] o : res){
				Date dtIns = (Date)o[0];
				Date dtMod = (Date)o[1];
				if (dtAgg == null || (dtIns != null && dtIns.getTime() > dtAgg.getTime())) {
					dtAgg = dtIns;
				}
				if (dtAgg == null || (dtMod != null && dtMod.getTime() > dtAgg.getTime())) {
					dtAgg = dtMod;
				}
			}*/
		} catch (Throwable t) {
			logger.error("getDtAggVersamenti", t);
			throw new F24ServiceException(t);
		}
		
		return dtAgg;
	}
	

}
