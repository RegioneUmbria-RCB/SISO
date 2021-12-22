package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.ConfrontoSsCsDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.PaiDTOExt;
import it.webred.cs.csa.ejb.dto.fascicolo.scuola.ListaDatiScuolaDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.scuola.ScuolaDTO;
import it.webred.cs.csa.ejb.dto.pai.PaiSearchCriteria;
import it.webred.cs.csa.ejb.dto.pai.PaiSintesiDTO;
import it.webred.cs.csa.ejb.dto.relazione.RelazioneSintesiDTO;
import it.webred.cs.csa.ejb.queryBuilder.PaiQueryBuilder;
import it.webred.cs.data.DataModelCostanti.TipoDiario;
import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsDClob;
import it.webred.cs.data.model.CsDColloquio;
import it.webred.cs.data.model.CsDColloquioBASIC;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDiarioAna;
import it.webred.cs.data.model.CsDDiarioBASIC;
import it.webred.cs.data.model.CsDDiarioDoc;
import it.webred.cs.data.model.CsDDocIndividuale;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDRelSal;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsDScuola;
import it.webred.cs.data.model.CsDTriage;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.data.model.CsPaiMastSogg;
import it.webred.cs.data.model.CsRelDiarioDiariorif;
import it.webred.cs.data.model.CsRelDiarioDiariorifPK;
import it.webred.cs.data.model.CsRelRelazioneProbl;
import it.webred.cs.data.model.CsRelRelazioneTipoint;
import it.webred.cs.data.model.CsRelSettCatsocEsclusiva;
import it.webred.cs.data.model.CsTbMacroAttivita;
import it.webred.cs.data.model.CsTbMicroAttivita;
import it.webred.cs.data.model.CsTbScuola;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.data.model.view.CsVistaPai;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

@Named
public class DiarioDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public void loadPadreFiglioEntities(CsDDiario diario){
		if( diario != null ) {
			if(diario.getCsDDiariFiglio()!=null) diario.getCsDDiariFiglio().size();
			if(diario.getCsDDiariPadre()!=null)  diario.getCsDDiariPadre().size();
		}
	}
	
	public void loadDocEntities(CsDDiario diario) {
		if (diario != null) {
			diario.getCsDDiarioDocs().size();
		}
	}

	public List<PaiDTOExt> loadPaiEntities(CsDDiario diario) {
		List<PaiDTOExt> lstPai = new LinkedList<PaiDTOExt>();
		loadPadreFiglioEntities(diario);
		if (diario.getCsDDiariPadre() != null) {
			for (CsDDiario d : diario.getCsDDiariPadre()) {
				CsDPai pai = findPaiByDiarioId(d.getId());
				if (pai != null)
					lstPai.add(new PaiDTOExt(pai));
			}
		}
		return lstPai;
	}

	public CsDDiario newDiario(CsDDiario diario) {
		em.persist(diario);
		loadPadreFiglioEntities(diario);
		return diario;
	}

	public CsDDiario updateDiarioNR(CsDDiario diario) {
		em.merge(diario);
		return diario;
	}

	public CsDDiario updateDiario(CsDDiario diario) {
		diario = em.merge(diario);
		loadPadreFiglioEntities(diario);
		return diario;
	}

	public void saveDiarioChild(Object obj) {
		em.persist(obj);
	}

	@SuppressWarnings("unchecked")
	public List<CsDDiario> getDiarioByCaso(Long casoId) {
		Query q = em.createNamedQuery("CsDDiario.getDiarioByCasoId").setParameter("casoId", casoId);
		List<CsDDiario> lst = q.getResultList();
		for (CsDDiario d : lst)
			loadPadreFiglioEntities(d);

		return lst;
	}

	public List<CsDColloquioBASIC> getColloquios(Long idSoggetto) throws Exception {
		Query q = em.createNamedQuery("CsDColloquioBASIC.findBySoggetto");
		q.setParameter("idSoggetto", idSoggetto);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CsDColloquioBASIC> findAllColloquio() {
		Query q = em.createNamedQuery("CsDColloquioBASIC.findAllColloquio");
		return q.getResultList();
	}

	public void updateColloquio(CsDColloquio colloquio) {
		updateDiarioNR(colloquio.getCsDDiario());
		em.merge(colloquio);
	}

	public CsTbTipoDiario findTipoDiarioById(long idTipoDiario) {
		CsTbTipoDiario tipodiario = em.find(CsTbTipoDiario.class, idTipoDiario);
		return tipodiario;
	}

	public CsDRelazione findRelazioneById(Long idDiario) {
		return em.find(CsDRelazione.class, idDiario);
	}

	@SuppressWarnings("unchecked")
	public List<CsDRelazione> findRelazioniByCaso(Long idCaso) {
		Query q = em.createNamedQuery("CsDRelazione.findRelazioniByCaso");
		q.setParameter("idCaso", idCaso);
		return q.getResultList();
	}
	
	public List<RelazioneSintesiDTO> findRelazioniSintesiByCaso(Long idCaso) {
		List<RelazioneSintesiDTO> lstOut = new ArrayList<RelazioneSintesiDTO>();
		logger.debug("findRelazioniSintesiByCaso CASO_ID["+idCaso+"]");
		Query q = em.createNamedQuery("CsDRelazione.findRelazioniSintesiByCaso");
		q.setParameter("idCaso", idCaso);
		List<Object[]> lst = (List<Object[]>) q.getResultList();
		logger.debug("findRelazioniSintesiByCaso RES["+lst.size()+"]");
		for(Object[] o : lst){
			RelazioneSintesiDTO out = new RelazioneSintesiDTO();
			out.setDiarioId(((Long)o[0]));
			out.setDtAmministrativa((Date)o[1]);
	    	out.setSituazioneAmbientale((String)o[2]);
	    	out.setSituazioneParentale((String)o[3]);
	    	out.setSituazioneSanitaria((String)o[4]);
	    	out.setProposta((String)o[5]);
	    	out.setDescMacroAttivita((String)o[6]);
	    	out.setDescMicroAttivita((String)o[7]);
	    	out.setTipoFormMicroAttivita((String)o[8]);
	    	
	    	List<Long> paisId = findPadriCollegatiByFiglio(TipoDiario.PAI_ID, TipoDiario.RELAZIONE_ID, out.getDiarioId());
	    	out.setPaiCollegati(paisId);
	    	lstOut.add(out);
		}
		return lstOut;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsDRelazione> findRelazioniInScadenza() {
		try{
			Query q = em.createNamedQuery("CsDRelazione.findRelazioniInScadenza");
			return q.getResultList();
		}catch(Throwable t){
			logger.error(t.getMessage(), t);
		}
		return new ArrayList<CsDRelazione>();
	}
	
	@SuppressWarnings("unchecked")
	public List<CsDPai> findPaiByCaso(Long idCaso) {
		List<CsDPai> lst = new ArrayList<CsDPai>();
		try{
			if(idCaso!=null){
				Query q = em.createNamedQuery("CsDPai.findPaiByCaso");
				q.setParameter("idCaso", idCaso);
				lst = q.getResultList();
			}
		}catch(Exception e){
			logger.error("findPaiByCaso "+ e.getMessage(), e);
		}
		return lst;
	}
	
	public CsDDiario findDiarioById(Long obj) {
		CsDDiario diario = em.find(CsDDiario.class, obj);
		loadPadreFiglioEntities(diario);
		return diario;
	}

	public CsDDiarioBASIC findDiarioBASICById(Long obj) {
		CsDDiarioBASIC diario = em.find(CsDDiarioBASIC.class, obj);
		return diario;
	}

	public void deleteDiario(Long obj) {
		Query q = em.createNamedQuery("CsDDiario.deleteDiario");
		q.setParameter("idDiario", obj);
		q.executeUpdate();
	}

	public void deleteClob(Long obj) {
		Query q = em.createNamedQuery("CsDClob.delete");
		q.setParameter("idClob", obj);
		q.executeUpdate();
	}

	public void deleteValutazione(Long obj) {
		Query q = em.createNamedQuery("CsDValutazione.delete");
		q.setParameter("idDiario", obj);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<CsDValutazione> getSchedeValutazioneSoggetto(Long anagraficaId, int tipoDiarioId) {
		Query q = em.createNamedQuery("CsDValutazione.findValutazioneBySoggetto");
		q.setParameter("anagraficaId", anagraficaId);
		q.setParameter("tipoDiarioId", new Long(tipoDiarioId));
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CsDValutazione> getSchedeValutazioneUdcId(Long schedaId, List<Integer> tipoDiarioId) {
		
		List<Long> tipi = new ArrayList<Long>();
		for(Integer val : tipoDiarioId)
			tipi.add(val.longValue());
		
		Query q = em.createNamedQuery("CsDValutazione.findValutazioneByUdcId");
		q.setParameter("schedaId", schedaId);
		q.setParameter("tipoDiarioId", tipi);
		return q.getResultList();
	}

	

	@SuppressWarnings("unchecked")
	public List<CsDValutazione> findAllSchedeValutazioneTipo(int tipoDiarioId) {
		Query q = em.createNamedQuery("CsDValutazione.findAllSchedeValutazione");
		q.setParameter("tipoDiarioId", new Long(tipoDiarioId));
		return q.getResultList();
	}

	public CsDClob saveClob(CsDClob clob) {
		em.persist(clob);
		return clob;
	}

	public void updateClob(CsDClob clob) {
		em.merge(clob);
	}

	public void updateValutazione(CsDValutazione valutazione) {
		updateDiarioNR(valutazione.getCsDDiario());
		em.merge(valutazione);
		this.loadDocEntities(valutazione.getCsDDiario());
	}

	public void updateRelazione(CsDRelazione relazione) {
		this.updateDiarioNR(relazione.getCsDDiario());
		em.merge(relazione);
	}
	
	public void updateTriage(CsDRelazione relazione,CsDTriage triage) {
		this.updateDiarioNR(relazione.getCsDDiario());
		em.merge(relazione);
        em.merge(triage);
	}

	public void updateSal(CsDRelazione relazione,CsDRelSal sal) {
		this.updateDiarioNR(relazione.getCsDDiario());
		em.merge(relazione);
        em.merge(sal);
	}
	
	public void saveRelazione(CsDRelazione relazione) {
		em.persist(relazione);
	}

	public List<CsDDiarioDoc> findDiarioDocById(Long idDiario) {
		Query q = em.createNamedQuery("CsDDiarioDoc.findDiarioDocById");
		q.setParameter("idDiario", idDiario);
		return q.getResultList();
	}

	public void saveDiarioDoc(CsDDiarioDoc dd) {
		em.persist(dd);
	}

	public void deleteDiarioDoc(Long idDocumento) {
		Query q = em.createNamedQuery("CsDDiarioDoc.deleteDiarioDocByIdDocumento");
		q.setParameter("idDocumento", idDocumento);
		q.executeUpdate();
	}

	public void updateSchedaPai(CsDPai pai) {
		updateDiarioNR(pai.getCsDDiario());
		em.merge(pai);
	}

	public void saveSchedaPai(CsDPai pai) {
		em.persist(pai);
	}

	public void deleteSchedaPai(CsDPai pai) {
		Query q = em.createNamedQuery("CsDPai.deletePAIByIdDiario");
		q.setParameter("idDiario", pai.getDiarioId());
		q.executeUpdate();
	}

	public void deleteRelDiarioDiarioRif(Long idDiario) {
		Query q = em.createNativeQuery("delete from CS_REL_DIARIO_DIARIORIF where diario_id= :diarioId");
		q.setParameter("diarioId", idDiario);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<CsDDocIndividuale> findDocIndividualiByCaso(Long idCaso, Boolean visibilita) {
		String sql = "select i from CsDDocIndividuale i "+
					 "inner join fetch i.csDDiario d "+
					 "left join fetch i.csTbSottocartellaDoc s "+
					 "where d.csACaso.id=:idCaso ";
		if(visibilita!=null)
			sql+= "and nvl(i.privato,0) = :visibilita ";
		sql+="order by d.dtAmministrativa desc";
		logger.debug("findDocIndividualiByCaso SQL["+sql+"]");
		logger.debug("findDocIndividualiByCaso ID_CASO["+idCaso+"] VISIBILITA["+visibilita+"]");
		Query q = em.createQuery(sql);
		q.setParameter("idCaso", idCaso);
		if(visibilita!=null)
			q.setParameter("visibilita", visibilita);
		return q.getResultList();
	}

	public void updateDocIndividuale(CsDDocIndividuale docIndividuale) {
		updateDiarioNR(docIndividuale.getCsDDiario());
		em.merge(docIndividuale);
	}

	public void deleteDocIndividualeById(Long idDiario) {
		Query q = em.createNamedQuery("CsDDocIndividuale.deleteDocIndividualeByIdDiario");
		q.setParameter("idDiario", idDiario);
		q.executeUpdate();
	}

	public CsDDocIndividuale findDocIndividuale(Long id) {
		CsDDocIndividuale d = em.find(CsDDocIndividuale.class, id);
		this.loadDocEntities(d.getCsDDiario());
		return d;
	}

	public CsDValutazione findValutazioneById(Long id, boolean loadRelatedEntities) {
		Query q = em.createNamedQuery("CsDValutazione.findValutazioneById").setParameter("idValutazione", id);

		List<CsDValutazione> l = q.getResultList();
		if (l.size() > 0) {
			CsDValutazione v = (CsDValutazione) l.get(0);
			if (loadRelatedEntities)
				loadPadreFiglioEntities(v.getCsDDiario());
			return v;
		}
		return null;
	}

	public CsDValutazione findValutazioneChildByPadreId(Long valPadreId, int tipoDiarioFiglioId) {
		CsDValutazione padre = this.findValutazioneById(valPadreId, false);
		if (padre != null) {
			for (CsDDiario figlio : padre.getCsDDiario().getCsDDiariFiglio()) {
				if (figlio.getCsTbTipoDiario().getId() == tipoDiarioFiglioId) {
					CsDValutazione valFiglio = findValutazioneById(figlio.getId(), true);
					valFiglio.getCsDDiario().getCsDClob();
					return valFiglio;
				}
			}
		}
		return null;
	}
	
	public CsDClob findClobById(Long idClob) {
		Query q = em.createNamedQuery("CsDValutazione.findClobById").setParameter("idClob", idClob);

		List l = q.getResultList();
		if (l.size() > 0)
			return (CsDClob) l.get(0);

		return null;
	}

	public void saveDiarioRif(Long idDiarioPadre, Long idDiarioRifFiglio) {
		CsRelDiarioDiariorif csRif = new CsRelDiarioDiariorif();
		csRif.setId(new CsRelDiarioDiariorifPK());
		csRif.getId().setDiarioId(idDiarioPadre);
		csRif.getId().setDiarioIdRif(idDiarioRifFiglio);
		em.persist(csRif);
	}
	public int deleteDiarioRif(Long idDiarioPadre, Long idDiarioRifFiglio) {
		Query q = em.createNativeQuery("delete from CS_REL_DIARIO_DIARIORIF where DIARIO_ID=? and DIARIO_ID_RIF=?");
		q.setParameter(1, idDiarioPadre);
		q.setParameter(2, idDiarioRifFiglio);
		
		int deletedCount =q.executeUpdate();
		return deletedCount;
	}

	@SuppressWarnings("unchecked")
	public List<CsRelSettCatsocEsclusiva> findRelSettCatsocEsclusivaByTipoDiarioId(Long tipoDiarioId) {
		Query q = em.createNamedQuery("CsRelSettCatsocEsclusiva.findRelSettCatsocEsclusivaByTipoDiarioId");
		q.setParameter("tipoDiarioId", tipoDiarioId);
		return q.getResultList();
	}

	@SuppressWarnings({ "rawtypes" })
	public CsRelSettCatsocEsclusiva findRelSettCatsocEsclusivaByIds(Long tipoDiarioId, Long categoriaSocialeId, Long settoreId) {
		Query q = em.createNamedQuery("CsRelSettCatsocEsclusiva.findRelSettCatsocEsclusivaByIds");
		q.setParameter("tipoDiarioId", tipoDiarioId);
		q.setParameter("categoriaSocialeId", categoriaSocialeId);
		q.setParameter("settoreId", settoreId);
		List l = q.getResultList();
		if (l.size() > 0)
			return (CsRelSettCatsocEsclusiva) l.get(0);

		return null;
	}

	public void saveCsRelSettCatsocEsclusiva(CsRelSettCatsocEsclusiva rel) {
		em.persist(rel);
	}

	public void updateCsRelSettCatsocEsclusiva(CsRelSettCatsocEsclusiva rel) {
		em.merge(rel);
	}

	public void deleteCsRelSettCatsocEsclusiva(Long tipoDiarioId, Long categoriaSocialeId, Long settoreId) {
		Query q = em.createNamedQuery("CsRelSettCatsocEsclusiva.deleteRelSettCatsocEsclusivaByIds");
		q.setParameter("tipoDiarioId", tipoDiarioId);
		q.setParameter("categoriaSocialeId", categoriaSocialeId);
		q.setParameter("settoreId", settoreId);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<ListaDatiScuolaDTO> findScuoleByCaso(Long idCaso){
		
		List<ListaDatiScuolaDTO> scuole = new ArrayList<ListaDatiScuolaDTO>();
		try{
		Query q = em.createNamedQuery("CsDScuola.findScuolaByCaso");
		q.setParameter("idCaso", idCaso);
		List<Object[]> lst =  q.getResultList();
		for(Object[] o : lst){
			ListaDatiScuolaDTO s = new ListaDatiScuolaDTO();
			
			s.setDiarioId((Long)o[0]);
			s.setGrado((String)o[1]);
			s.setNote((String)o[2]);
			s.setNome((String)o[3]);
			s.setAnnoScolastico((String)o[4]);
			s.setTipoScuola((String)o[5]);
			s.setDataUltimaModifica((Date)o[6]);
			s.setOpModifica((String)o[7]);
			
			CsTbScuola tbScuola = o[8]!=null ? (CsTbScuola)o[8] : null;
			if(tbScuola!=null){
				ScuolaDTO scuola = new ScuolaDTO();
				BeanUtils.copyProperties(scuola, tbScuola);
				s.setScuola(scuola);
			}
			
			s.setSettSecondoLivello((Long)o[9]);
			s.setIdSettoreDiario((Long)o[10]);
			
			scuole.add(s);
		}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return scuole;
	}

	public void updateScuola(CsDScuola scuola) {
		updateDiario(scuola.getCsDDiario());
		em.merge(scuola);
	}

	public void deleteScuolaById(Long idDiario) {
		Query q = em.createNamedQuery("CsDScuola.deleteScuolaByIdDiario");
		q.setParameter("idDiario", idDiario);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<CsDIsee> findIseeByCaso(Long idCaso) {
		Query q = em.createNamedQuery("CsDIsee.findIseeByCaso");
		q.setParameter("idCaso", idCaso);
		return q.getResultList();
	}

	public void updateIsee(CsDIsee scuola) {
		updateDiarioNR(scuola.getCsDDiario());
		em.merge(scuola);
	}

	public void deleteIseeById(Long idDiario) {
		Query q = em.createNamedQuery("CsDIsee.deleteIseeByIdDiario");
		q.setParameter("idDiario", idDiario);
		q.executeUpdate();
	}

	public CsDColloquio findColloquioById(long id) {
		CsDColloquio colloquio = em.find(CsDColloquio.class, id);
		return colloquio;
	}

	public CsDPai findPaiByDiarioId(long id) {
		CsDPai pai = em.find(CsDPai.class, id);
		return pai;
	}

	public CsDIsee findIseeId(Long id) {
		CsDIsee isee = em.find(CsDIsee.class, id);
		return isee;
	}

	public CsFlgIntervento findFglInterventoById(Long id) {
		CsFlgIntervento fgl = em.find(CsFlgIntervento.class, id);
		return fgl;
	}

	public CsDValutazione findValutazioneById(Long id) {
		CsDValutazione val = em.find(CsDValutazione.class, id);
		loadDocEntities(val.getCsDDiario());
		int size = val.getCsDDiario().getCsDDiarioDocs().size();
		return val;
	}
	
	public CsCDiarioConchi getConchiById(Long id) {
		CsCDiarioConchi ret = em.find(CsCDiarioConchi.class, id);
		return ret;
	}
	
	public CsDRelazione findLastRelazioneProblAperte(Long idCaso)
	{
		TypedQuery<CsDRelazione> q = em.createNamedQuery("CsDRelazione.findLastRelazioneProblAperte", CsDRelazione.class);
		q.setParameter("idCaso", idCaso);
		List<?> results = q.getResultList();
		if(results==null || results.isEmpty())
		{
			return null;
		}
		
		CsDRelazione relazione = (CsDRelazione) results.get(0);	
		return relazione;
//		
//		TypedQuery<CsRelRelazioneProbl> qq = em.createNamedQuery("CsRelRelazioneProbl.findRelRelazioneProblAperte", CsRelRelazioneProbl.class);
//		q.setParameter("_diarioId", relazione.getDiarioId());
//		
//		List<CsRelRelazioneProbl> ret = qq.getResultList();	

	}

	public void saveCsRelRelazioneProbl(CsRelRelazioneProbl csRelRelazioneProbl) {
		em.persist(csRelRelazioneProbl);
		
	}
	public void deleteCsRelRelazioneProbl(CsRelRelazioneProbl csRelRelazioneProbl) {
		em.remove(csRelRelazioneProbl);
		
	}

	public void saveCsRelRelazioneTipoint(CsRelRelazioneTipoint csRelRelazioneTipoint) {
		em.persist(csRelRelazioneTipoint);		
	}
	public void deleteCsRelRelazioneTipoint(CsRelRelazioneTipoint csRelRelazioneTipoint) {
		em.remove(csRelRelazioneTipoint);
		
	}

	public List<CsDDiario> findDiarioBySchedaId(Long schedaId) {
		Query q = em.createNamedQuery("CsDDiario.getDiarioBySchedaId");
		q.setParameter("schedaId", schedaId);
		return q.getResultList();
		
	}

	public CsDValutazione saveSchedaValutazione(CsDValutazione schedaValutazione) {
		em.persist(schedaValutazione);
		return schedaValutazione;
	}

	//	public List<CsDDiario> findAllDiarioPadreByDiarioId(Long diarioId) {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}
	//
	//	public List<CsDDiario> findAllDiarioFiglioByDiarioId(long id) {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}


	//INIZIO SISO-438
	public CsDDiarioDoc mergeCsDDiarioDoc(CsDDiarioDoc csDDiarioDoc) { 
		try{
			return em.merge(csDDiarioDoc);
		}catch(Throwable e){
			throw new CarSocialeServiceException(e);
		}
	
	} 

	public void saveCsRelDiarioDiariorif(CsRelDiarioDiariorif csRif) {
		try{
			em.persist(csRif);
		}catch(Throwable e){
			throw new CarSocialeServiceException(e);
		} 
	}

	public void savecCsDDocIndividuale(CsDDocIndividuale csDDocIndividuale) {
		try{
			em.persist(csDDocIndividuale);
		}catch(Throwable e){
			throw new CarSocialeServiceException(e);
		} 
		
	}

	public CsLoadDocumento mergeCsLoadDocumento(CsLoadDocumento csLoadDocumento) {
		return   em.merge(csLoadDocumento);
	}

	public CsDDiario mergeCsDDiario(CsDDiario diarioDocIndividuale) {
		return   em.merge(diarioDocIndividuale);
	}

	public void persistCsDDiarioDoc(CsDDiarioDoc csDDiarioDoc) {
		em.persist(csDDiarioDoc);		
	}

	public  CsRelDiarioDiariorif findCsRelDiarioDiariorif(long idPadre, long diarioIdRif) {
		Query q = em.createQuery(" select rel from CsRelDiarioDiariorif rel "
				+ " where rel.id.diarioId=:idPadre and rel.id.diarioIdRif=:diarioIdRif ");
		q.setParameter("idPadre", idPadre);
		q.setParameter("diarioIdRif", diarioIdRif);

		List<CsRelDiarioDiariorif> l = q.getResultList();
		if (l.size() > 0) {
			CsRelDiarioDiariorif entity = (CsRelDiarioDiariorif) l.get(0); 
			return entity;
		}
		return null;
	}

//	public void deleteCsRelDiarioDiariorif(CsRelDiarioDiariorif rif) {
//		em.remove(rif);		
//	}
	
	@SuppressWarnings("unchecked")
	public List<CsDValutazione> getSchedeValutazioneByDiarioId(long diarioId) {
		Query q = em.createNamedQuery("CsDValutazione.findValutazioneByDiarioId");
		q.setParameter("diarioId", diarioId); 
		return q.getResultList();
	}

	public List<CsDDocIndividuale> findDocIndividualeByCfSchedaSegnalato(String cf, long organizzazioneid) {  
		List<CsDDocIndividuale> result = new ArrayList<CsDDocIndividuale>();
		
		String sql = " select doc.diario_id  from CS_D_DOC_INDIVIDUALE doc "
				+ " join CS_D_DIARIO diario on (doc.DIARIO_ID = diario.id ) "
				+ " join SS_SCHEDA scheda on (scheda.id = diario.SCHEDA_ID) "
				+ " join SS_SCHEDA_SEGNALATO segn on (scheda.SEGNALATO = segn.id )  "
				+ " join SS_ANAGRAFICA ana on (ana.id = segn.anagrafica) "
				+ " join ss_scheda_accesso acc on (scheda.accesso = acc.id) " 
				+ " where ana.cf =:cf  "
				+ "		and scheda.completa = 1 "
				+ "		and acc.REL_UPO_ORGANIZZAZIONE_ID =:organizzazioneid" ; 
				
		Query q = em.createNativeQuery(sql); 
		q.setParameter("cf", cf);
		q.setParameter("organizzazioneid", organizzazioneid);
		List lista = q.getResultList(); 
		
		if (lista.size()>0) {
			for (Object obj : lista) {
				CsDDocIndividuale doc = findDocIndividuale(((BigDecimal) obj).longValue());
				result.add(doc); 
			}
		}
		 
		return result;
	}
 
	
	//FINE SISO-438
	
	//SISO-763
	public List<CsDDiarioAna> findDiarioAnagraficaByDiarioId(Long diarioId){		
		Query query = em.createQuery("SELECT da FROM CsDDiarioAna da WHERE diarioId = ?1");
		query.setParameter(1, diarioId);
		
		return (List<CsDDiarioAna>) query.getResultList();
	}
	
	public CsDDiarioAna saveDiarioAnagrafica(CsDDiarioAna diarioAnagrafica) throws Exception {
		CsDDiarioAna toReturn = em.merge(diarioAnagrafica);
		em.flush();
		return toReturn;
	}
	
	public void deleteDiarioAnagrafica(Long diarioId) throws Exception {
		Query query = em.createQuery("DELETE FROM CsDDiarioAna da WHERE da.diarioId = ?1");
		query.setParameter(1, diarioId);
		query.executeUpdate();
	}
	
	public List<Long> findDiarioAnaCasoIdsByAnagraficaId(Long anagraficaId, int tipoDiario) throws Exception {
		String sql = "SELECT d.csACaso.id FROM CsDDiario d "
				+ "WHERE d.id in "
				+ "(SELECT da.diarioId FROM CsDDiarioAna da "
				+ "	 WHERE da.anagraficaId = :anagraficaId AND d.csTbTipoDiario.id = :tipoDiario)";
		Query query = em.createQuery(sql);
		query.setParameter("anagraficaId", anagraficaId);
		query.setParameter("tipoDiario", new Long(tipoDiario));
		return (List<Long>) query.getResultList();
	}
	
	public ConfrontoSsCsDTO estraiDatiSchedaSS(BigDecimal scheda){
		ConfrontoSsCsDTO dto = new ConfrontoSsCsDTO();
		HashMap<String, Object> mappa = null;
		String qq1 = "SELECT a.NOME AS NOME, "
				+ "a.COGNOME AS COGNOME, "
				+ "a.CF AS CF, "
				+ "a.CITTADINANZA AS CITTADINANZA, "
				+ "a.STATO_CIVILE AS STATO_CIVILE,"
				+ "s.SENZA_FISSA_DIMORA AS SENZA_FISSA_DIMORA, "
				+ "s1.VIA AS INDIRIZZO, "
				+ "TO_CHAR(s1.NUMERO) AS CIVICO, "
				+ "s1.COMUNE_DES AS COMUNE, "
				+ "s1.COMUNE_COD AS CODICE_COMUNE, "
				+ "s1.PROV_COD AS PROVINCIA, "
				+ "s1.STATO_COD AS CODICE_STATO, "
				+ "s2.IDENTIFICATIVO,"
				+ "s.lavoro as LAVORO, "
				+ "s.professione as PROFESSIONE"
				+ " FROM SS_ANAGRAFICA a, SS_SCHEDA_SEGNALATO s LEFT JOIN SS_INDIRIZZO s1 ON s.RESIDENZA = s1.ID, SS_SCHEDA s2 "
				+ " WHERE s.ANAGRAFICA = a.id AND s2.SEGNALATO = s.ID AND s2.ELIMINATA = 0  AND s2.id = :schedaId";
		Query q1 = em.createNativeQuery(qq1);
		q1.setParameter("schedaId", scheda.longValue());
		q1.setMaxResults(1);
		List<Object[]> ret = q1.getResultList();
		
		if(ret!=null && !ret.isEmpty()){
			Object[] o = ret.get(0);
			dto.setNome((String)o[0]);
			dto.setCognome((String)o[1]);
			dto.setCf((String)o[2]);
			dto.setCittadinanza((String)o[3]);
			dto.setStatoCivile((String)o[4]);
			Boolean sfd = o[5]!=null && "1".equalsIgnoreCase(o[5].toString()) ? true : false;
			dto.setSenzaFissaDimora(sfd);
			dto.setResidenzaVia((String)o[6]);
			dto.setResidenzaCivico((String)o[7]);
			dto.setResidenzaComune((String)o[9]);
			dto.setResidenzaProv((String)o[10]);
			dto.setResidenzaNazione((String)o[11]);
			dto.setIdentificativo((BigDecimal)o[12]);
			dto.setLavoro((String)o[13]);
			dto.setProfessione((String)o[14]);
	
		Long casoID = null;
			
			// per abitazione 15
		    Integer[] tipi = {TipoDiario.ABITAZIONE_ID , TipoDiario.STRANIERI_ID};
			List<CsDValutazione> rs = getSchedeValutazioneUdcId(scheda.longValue(), Arrays.asList(tipi));
			
			for(CsDValutazione cd : rs){
				
				if(casoID==null&&cd!=null&&cd.getCsDDiario()!=null&&cd.getCsDDiario().getCsACaso()!=null)
					cd.getCsDDiario().getCsACaso().getId();
				
				if(TipoDiario.ABITAZIONE_ID == cd.getCsDDiario().getCsTbTipoDiario().getId().intValue())
					dto.setAbitazione(cd);
				else if(TipoDiario.STRANIERI_ID == cd.getCsDDiario().getCsTbTipoDiario().getId().intValue())
					dto.setStranieri(cd);
			}
		}

		return dto;
	}
/*	public ConfrontoSsCsDTO estraiDatiSchedaCS(String codF, Date d){
		HashMap<String, Object> mappa = null;
		ConfrontoSsCsDTO dto = new ConfrontoSsCsDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date d = null;
		try {
			d = sdf.parse("31-12-9999");
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		
		this.get
		
		Query q = em.createNamedQuery("VSsSchedeUdcDiff.datiSchedaCS");
		
		q.setParameter("cf", codF);
		q.setParameter("dat", d);
		q.setMaxResults(1);
		
		List<Object[]> ret = q.getResultList();
		if(ret!=null && !ret.isEmpty()){
			Object[] o = ret.get(0);
			
			Long diarioAbitazione = (Long)(ret.get(0)[15]);
			Long diarioStranieri = (Long)(ret.get(0)[16]);
		    CsDValutazione abitazione = null;
			CsDValutazione stranieri = null;
		
			if(diarioAbitazione!=null) abitazione = this.findValutazioneById(diarioAbitazione);
			if(diarioStranieri!=null) stranieri  = this.findValutazioneById(diarioStranieri);
		
			dto.setNome((String)o[0]);
			dto.setCognome((String)o[1]);
			dto.setCf((String)o[2]);
			dto.setCittadinanza((String)o[3]);
			dto.setStatoCivile((String)o[4]);
			Boolean sfd = "Senza fissa dimora".equalsIgnoreCase((String)o[5]); 
			dto.setSenzaFissaDimora(sfd);
			dto.setResidenzaVia((String)o[6]);
			dto.setResidenzaCivico((String)o[7]);
			dto.setResidenzaComune((String)o[9]);
			dto.setResidenzaProv((String)o[10]);
			dto.setResidenzaNazione((String)o[11]);
			dto.setIdentificativo((BigDecimal)o[12]);
			dto.setLavoro((String)o[13]);
			dto.setProfessione((String)o[14]);
			dto.setCasoID((Long)o[15]);
			dto.setAbitazione(abitazione);
			dto.setStranieri(stranieri);
		}
		return dto;
	}*/

	public List<KeyValueDTO> findSintesiIseeByCaso(Long obj) {
		List<KeyValueDTO> lstIsee = new ArrayList<KeyValueDTO>();	
		try{
			Query q = em.createNamedQuery("CsDIsee.findSintesiByCaso");
			q.setParameter("idCaso", obj);
			List<Object[]> lst = q.getResultList();
			for(Object[] o : lst){
				Long diarioId = (Long)o[0];
				String annoRif = (String)o[1];
				String tipoIsee = (String)o[2];
				String label = annoRif + " - " + tipoIsee;
				lstIsee.add(new KeyValueDTO(diarioId, label));
			}
		}catch(Throwable e){
			logger.error("findSintesiIseeByCaso "+ e.getMessage(), e);
		}
		return lstIsee;
	
	}

	public CsDScuola getScuolaById(Long diarioId) {
		return em.find(CsDScuola.class, diarioId);
	}
	
	public List<CsDPai> findPaiAperti() {
		List<CsDPai> lst = new ArrayList<CsDPai>();
		try{
				Query q = em.createNamedQuery("CsDPai.findPaiAperti");
				lst = q.getResultList();
		}catch(Throwable e){
			logger.error("findPaiAperti "+ e.getMessage(), e);
		}
		return lst;
	}
		
	public List<CsVistaPai> findListaPaiEsterni(PaiSearchCriteria dto){
		List<CsVistaPai> lst = new ArrayList<CsVistaPai>();
		PaiQueryBuilder qb = new PaiQueryBuilder();
		String sql = qb.getSqlExternalPai(dto, Boolean.FALSE);
		logger.debug("findListaPaiEsterni SQL["+sql+"]");
		try {
			Query q = em.createNativeQuery(sql, CsVistaPai.class);
			q.setFirstResult(dto.getFirst());
			q.setMaxResults(dto.getPageSize());
			q.setParameter("settoreId", dto.getSettoreId());
			qb.setFilterParameters(q, dto);
			lst = q.getResultList();
		} catch (Exception e) {
			logger.error("findListaPaiEsterni " + e.getMessage(), e);
		}
		return lst;
	}
	
	public BigDecimal countListaPaiEsterni(PaiSearchCriteria dto){
		BigDecimal count = BigDecimal.ZERO;
		PaiQueryBuilder qb = new PaiQueryBuilder();
		String sql = qb.getSqlExternalPai(dto, Boolean.TRUE);
		logger.debug("countListaPaiEsterni SQL["+sql+"]");
		try {
			Query q = em.createNativeQuery(sql);
			q.setParameter("settoreId", dto.getSettoreId());
			qb.setFilterParameters(q, dto);
			count = (BigDecimal)q.getSingleResult();
		} catch (Exception e) {
			logger.error("countListaPaiEsterni " + e.getMessage(), e);
		}
		return count;
	}
	
	public List<CsVistaPai> findListaPaiFascicolo(PaiSearchCriteria dto){
		Long casoId = dto.getCasoId();
		String cf  = dto.getCodiceFiscale();
		PaiQueryBuilder qb = new PaiQueryBuilder();
		String sql = qb.getSqFascicoloPai(dto, Boolean.FALSE);
		logger.debug("findListaPaiFascicolo SQL[" + sql + "]");
		Query q = em.createNativeQuery(sql, CsVistaPai.class);
		q.setFirstResult(dto.getFirst());
		q.setMaxResults(dto.getPageSize());
		q.setParameter(PaiQueryBuilder.CASO_ID, casoId);
		q.setParameter(PaiQueryBuilder.COD_FISCALE, cf.toUpperCase());
		String params = qb.setFilterParameters(q, dto);
		logger.debug("findListaPaiFascicolo CF[" + cf + "] CASO_ID[" + casoId + "]"+params);
		return (List<CsVistaPai>) q.getResultList();
	}
	
	public BigDecimal countListaPaiFascicolo(PaiSearchCriteria dto){
		BigDecimal count = BigDecimal.ZERO;
		Long casoId = dto.getCasoId();
		String cf  = dto.getCodiceFiscale();
		PaiQueryBuilder qb = new PaiQueryBuilder();
		String sql = qb.getSqFascicoloPai(dto, Boolean.TRUE);
		logger.debug("countListaPaiFascicolo SQL[" + sql + "]");
		Query q = em.createNativeQuery(sql);
		q.setParameter(PaiQueryBuilder.CASO_ID, casoId);
		q.setParameter(PaiQueryBuilder.COD_FISCALE, cf.toUpperCase());
		String params = qb.setFilterParameters(q, dto);
		logger.debug("countListaPaiFascicolo CF[" + cf + "] CASO_ID[" + casoId + "]"+params);
		count = (BigDecimal)q.getSingleResult();
		return count;
	}
	
	public List<CsDRelazione> findRelazioniByIds(List<Long> ids){
		if(ids!=null && !ids.isEmpty()){
			Query q = em.createNamedQuery("CsDRelazione.findByIds");
			q.setParameter("listaId", ids);
			return (List<CsDRelazione>) q.getResultList();
		} return new ArrayList<CsDRelazione>();
	}
	
	public List<CsDRelazione> findRelazioniPaiEsterniByCF(String cf){
		List<CsDRelazione> lista = new ArrayList<CsDRelazione>();
		String sql = PaiQueryBuilder.SQL_RELAZIONI_PAI_ESTERNI_FROM_CF;
		logger.debug("findRelazioniPaiEsterniByCF SQL[" + sql + "]");
		logger.debug("findRelazioniPaiEsterniByCF CF[" + cf +"]");
		Query q = em.createNativeQuery(sql, CsDRelazione.class);
		q.setParameter("codiceFiscale", cf);
		lista = (List<CsDRelazione>) q.getResultList();
		logger.debug("findRelazioniPaiEsterniByCF RES[" + lista.size() +"]");
		return lista;
	}
	
	public void saveBeneficiariPai(List<CsPaiMastSogg> lstBeneficiari) {
		for (CsPaiMastSogg soggBeneficiario : lstBeneficiari) {
			if (soggBeneficiario.getId() != null) {
				em.merge(soggBeneficiario);
			} else {
				em.persist(soggBeneficiario);
			}
		}
	}
	
	public List<CsIIntervento> findInterventiPaiEsterniByCF(String cf){
		String sql = PaiQueryBuilder.SQL_INTERVENTI_PAI_ESTERNI_FROM_CF;
		logger.debug("LISTA INTERVENTI PAI ESTERNI PER CF[" + cf + "] SQL[" + sql + "]");
		Query q = em.createNativeQuery(sql, CsIIntervento.class);
		q.setParameter("cf", cf);
		return (List<CsIIntervento>) q.getResultList();
	}
	
	public List<CsPaiMastSogg> findSoggettiPaiSenzaCaso() {
		List<CsPaiMastSogg> lst = new ArrayList<CsPaiMastSogg>();
		try {
			Query q = em.createNamedQuery("CsPaiMastSogg.findSoggettiPaiSenzCaso");
			lst = (List<CsPaiMastSogg>) q.getResultList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
		return lst;
	}
	
	public void updateSoggettoPai(CsPaiMastSogg csPaiMastSogg) {
		em.merge(csPaiMastSogg);
	}

	public PaiSintesiDTO findSintesiPaiById(Long diarioId) {
		PaiSintesiDTO s = null;
		try {
			if(diarioId!=null && diarioId>0){
				Query q = em.createNamedQuery("CsDPai.findSintesiById");
				q.setParameter("diarioId", diarioId);
				Object[] res = (Object[])q.getSingleResult();
				if(res!=null){
					s = new PaiSintesiDTO();
					s.setTipo((String)res[0]);
					s.setDtAttivazione((Date)res[1]);
					s.setDtChiusura((Date)res[2]);
				}
			}
		}catch (NoResultException e) {
		}catch (Exception e) {
			logger.error("findSintesiPaiById " + e.getMessage(), e);
		}
		return s;
	}

	public CsPaiMastSogg findSoggettoPaiByDiarioId(Long diarioId) {
		CsPaiMastSogg  sogg = null;
		
		try {

			Query q = em.createNamedQuery("CsPaiMastSogg.findSoggByDiarioId");
			q.setParameter("diarioId", diarioId);
			sogg = (CsPaiMastSogg) q.getSingleResult();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
		return sogg;
	}

	public List<PaiSintesiDTO> findDatePai(PaiSearchCriteria psc) {
		List<PaiSintesiDTO> lst = new ArrayList<PaiSintesiDTO>();
		Long diarioId = psc.getDiarioId()!=null ? psc.getDiarioId() : 0L;
		String sql = PaiQueryBuilder.SQL_INTERVALLO_PAI;
		Query q = em.createNativeQuery(sql);
		logger.debug("findDatePai SQL["+sql+"]");
		logger.debug("findDatePai DIARIO_ID["+diarioId+"] COD_FISCALE["+ psc.getCodiceFiscale()+"] TIPO_PAI_ID["+psc.getTipoPaiId()+"]");
		q.setParameter("diarioId", diarioId);
		q.setParameter("codFiscale", psc.getCodiceFiscale());
		q.setParameter("tipoPaiId", psc.getTipoPaiId());
		
		List<Object[]> result = (List<Object[]>) q.getResultList();
		logger.debug("findDatePai RESULT["+result.size()+"]");
		for(Object[] o: result){
			PaiSintesiDTO i = new PaiSintesiDTO();
			i.setDiarioId(((BigDecimal)o[0]).longValue());
			i.setDtAttivazione((Date)o[1]);
			i.setDtChiusura((Date)o[2]);
			lst.add(i);
		}
		return lst;
	}
	
	public List<Long> findPadriCollegatiByFiglio(int tipoPadre, int tipoFiglio, Long figlioId){
		List<Long> lista = new ArrayList<Long>();
	/*	String sql = "select distinct pai.id from cs_d_diario rel left join CS_REL_DIARIO_DIARIORIF reld  on rel.id = RELD.DIARIO_ID_RIF "+
					 " left join cs_d_diario pai on pai.ID = RELD.DIARIO_ID AND pai.TIPO_DIARIO_ID = "+DataModelCostanti.TipoDiario.RELAZIONE_ID +
					 " where REL.TIPO_DIARIO_ID ="+DataModelCostanti.TipoDiario.PAI_ID +
					 " AND rel.id = :diarioId";*/
		String sql = "select distinct padre.id from cs_d_diario padre  "+
				" left join CS_REL_DIARIO_DIARIORIF rel on padre.id = rel.DIARIO_ID "+
				" left join cs_d_diario figlio on figlio.ID = REL.DIARIO_ID_RIF "+
				" where padre.TIPO_DIARIO_ID = :idTipoDiarioPadre "+
				" AND figlio.TIPO_DIARIO_ID = :idTipoDiarioFiglio "+
				" AND figlio.id = :diarioFiglioId";
		
		//logger.debug("findPaiCollegatiByRelazione SQL["+sql+"]");
		//logger.debug("findPaiCollegatiByRelazione RELAZIONE_DI["+relazioneId+"]");
		Query q = em.createNativeQuery(sql);
		q.setParameter("diarioFiglioId", figlioId);
		q.setParameter("idTipoDiarioPadre", tipoPadre);
		q.setParameter("idTipoDiarioFiglio", tipoFiglio);
		
		for(BigDecimal d : (List<BigDecimal>)q.getResultList())
			lista.add(d.longValue());
		return lista;
	}
}
