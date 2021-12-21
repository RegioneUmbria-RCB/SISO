package it.webred.cs.csa.ejb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.fse.FseSearchCriteria;
import it.webred.cs.csa.ejb.dto.fse.ListaFseDTO;
import it.webred.cs.csa.ejb.queryBuilder.FseQueryBuilder;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsExtraFseDatiLavoro;
import it.webred.cs.data.model.CsTbTipoExtraFse;
import it.webred.cs.data.model.view.CsVistaFse;


@Named
public class DatiPorDAO extends CarSocialeBaseDAO implements Serializable{

	private static final long serialVersionUID = -582934911140375972L;
	
	public CsExtraFseDatiLavoro findDatiPorUdcBySchedaId(Long schedaId){
		if(schedaId!=null){
			Query q = em.createNamedQuery("CsExtraFseDatiLavoro.findDatiPorUdcBySchedaId");
			q.setParameter("schedaId", schedaId);
			q.setParameter("tipo", DataModelCostanti.TipoPOR.SCHEDA_ACCESSO);
			List<CsExtraFseDatiLavoro> lst = q.getResultList();
			if(!lst.isEmpty()) return lst.get(0);
		}
		return null;
	}
	
	public CsExtraFseDatiLavoro getCsCDatiLavoroById(Long id) {
		CsExtraFseDatiLavoro c = em.find(CsExtraFseDatiLavoro.class, id);
		return c;

	}

	public CsExtraFseDatiLavoro saveDatiPor(CsExtraFseDatiLavoro ss){
		return em.merge(ss);
	}

	public void eliminaDatiPor(Long id) {
		try{
			Query q = em.createNamedQuery("CsExtraFseSiru.eliminaById");
			q.setParameter("id", id);
			q.executeUpdate();
			
			q = em.createNamedQuery("CsExtraFseDatiLavoro.eliminaByIdMast");
			q.setParameter("id", id);
			q.executeUpdate();
			
			q = em.createNamedQuery("CsExtraFseMast.eliminaById");
			q.setParameter("id", id);
			q.executeUpdate();
		
		
		}catch(Exception e){
			logger.error("eliminaDatiPor", e);
			throw new CarSocialeServiceException(e);
		}
	}

	public List<KeyValueDTO> getListaComuniResidenzaUsati() {
		List<KeyValueDTO> lista = new ArrayList<KeyValueDTO>();
		try{
			String sql = "select distinct v.residenzaComuneId, v.residenzaComuneDesc "
					   + "from CsVistaFse v "
					   + "order by residenzaComuneDesc";
			Query q = em.createQuery(sql);
			List<Object[]> res = q.getResultList();
			for(Object[] o : res){
				String istat = (String)o[0];
				String nome = (String)o[1];
				KeyValueDTO kv = new KeyValueDTO(istat, nome);
				lista.add(kv);
			}
		}catch(Exception e){
			logger.error("getListaComuniResidenzaUsati", e);
			throw new CarSocialeServiceException(e);
		}
		return lista;
	}

	public List<KeyValueDTO> findListaProgettiUsati() {
		List<KeyValueDTO> lista = new ArrayList<KeyValueDTO>();
		return lista;
	}

	public List<KeyValueDTO> findListaTipiFse() {
		List<KeyValueDTO> lista = new ArrayList<KeyValueDTO>();
		try{	
			Query q = em.createNamedQuery("CsTbTipoExtraFse.findAll");
			List<CsTbTipoExtraFse> lst = (List<CsTbTipoExtraFse>)q.getResultList();
			for(CsTbTipoExtraFse tipo : lst){
				KeyValueDTO kv = new KeyValueDTO(tipo.getId(), tipo.getDescrizione());
				lista.add(kv);
			}
		}catch(Exception e){
			logger.error("findListaTipiFse", e);
			throw new CarSocialeServiceException(e);
		}
		return lista;
	}

	public List<ListaFseDTO> getListaFse(FseSearchCriteria searchCriteria) {
		List<ListaFseDTO> lista = new ArrayList<ListaFseDTO>();
		try{
			FseQueryBuilder qb = new FseQueryBuilder();
		    String sql = qb.getSqlFse(searchCriteria, false);
		    logger.debug("getListaFse SQL["+sql+"]");
			Query q = em.createQuery(sql, CsVistaFse.class);
			
			q.setFirstResult(searchCriteria.getFirst());
			q.setMaxResults(searchCriteria.getPageSize());
			
			String params = qb.setFilterParameters(q, searchCriteria);
			logger.debug("getListaFse PARAMS "+params);
			List<CsVistaFse> res = (List<CsVistaFse>)q.getResultList();
			for(CsVistaFse o : res){
				ListaFseDTO fse = new ListaFseDTO();
				fse.setIdentificativo(o.getId());
				fse.setTipoFse(o.getTipoFse().getDescrizione());
				fse.setDataSottoscrizione(o.getDataSottoscrizione());
				fse.setProgettoDenominazione(o.getProgetto());
				fse.setProgettoCod(o.getProgettoAttivitaId());
				fse.setSoggettoAttuatore(o.getSoggettoAttuatore());
				
				fse.setCognome(o.getCognome());
				fse.setNome(o.getNome());
				fse.setCf(o.getCf());
				fse.setDataNascita(o.getDataNascita());
				fse.setNascitaNazioneIstat(o.getNazioneNascitaId());
				fse.setNascitaComuneIstat(o.getComuneNascitaId());
				fse.setNascitaComuneDesc(o.getComuneNascitaDes());
				fse.setCittadinanzaCod(o.getCittadinanzaCod());
				
				fse.setResidenzaNazioneIstat(o.getResidenzaNazioneId());
				fse.setResidenzaComuneIstat(o.getResidenzaComuneId());
				fse.setResidenzaComuneDesc(o.getResidenzaComuneDesc());
				fse.setResidenzaCap(o.getResidenzaCap());
				fse.setResidenzaIndirizzo(o.getResidenzaIndirizzo());
				
				fse.setDomicilioComuneIstat(o.getDomicilioComuneId());
				fse.setDomicilioComuneDesc(o.getDomicilioComuneDesc());
				fse.setDomicilioCap(o.getDomicilioCap());
				fse.setDomicilioIndirizzo(o.getDomicilioIndirizzo());
				
				fse.setTelefono(o.getTelefono());
				fse.setCellulare(o.getCellulare());
				fse.setEmail(o.getEmail());
				
				fse.setTitoloStudioCod(o.getTitoloStudio());
				fse.setCondOccupazioneCod(o.getCondMercatoIngresso());
				fse.setPeriodoDisoccupazione(o.getDurataRicerca());
				fse.setGrVulnerabile(o.getCodVulnerabile());
				
				lista.add(fse);
			}
			
		}catch(Exception e){
			logger.error("getListaFse", e);
			throw new CarSocialeServiceException(e);
		}
		
		return lista;
	}

	public Integer countListaFse(FseSearchCriteria searchCriteria) {
		Long count = 0l;
		try{
			FseQueryBuilder qb = new FseQueryBuilder();
		    String sql = qb.getSqlFse(searchCriteria, true);
		    logger.debug("countListaFse SQL["+sql+"]");
		    Query q = em.createQuery(sql);
			qb.setFilterParameters(q, searchCriteria);
			count = (Long) q.getSingleResult();
		}catch(Exception e){
			logger.error("countListaFse", e);
			throw new CarSocialeServiceException(e);
		}
		return count.intValue();
	}
	
	
}
