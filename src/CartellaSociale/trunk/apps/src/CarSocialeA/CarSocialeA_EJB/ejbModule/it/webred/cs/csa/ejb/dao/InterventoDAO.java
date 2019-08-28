package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.CodificaINPS;
import it.webred.cs.csa.ejb.dto.ErogazioneDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.InformativaDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.model.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

@Named
public class InterventoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public void saveIntervento(CsIIntervento nuovoIntervento) throws Exception {
		em.persist(nuovoIntervento);
	}

	public void saveFglIntervento(CsFlgIntervento nuovoFoglio) throws Exception {
		em.persist(nuovoFoglio);
	}

	public void updateIntervento(CsIIntervento nuovoIntervento) throws Exception {
		em.merge(nuovoIntervento);
	}

	public void updateFglIntervento(CsFlgIntervento nuovoFoglio) throws Exception {
		em.merge(nuovoFoglio);
	}

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
		Query q = em.createNamedQuery("CsRelSettCsocTipoInter.findTipiInterventoSettCatsoc");
		q.setParameter("idSettore", idSettore);
		q.setParameter("idCatsoc", idCatsoc);
		return (List<CsCTipoIntervento>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CsCTipoIntervento> findTipiInterventoCatSoc(Long idSoggetto, Date dataRif) {
		Query q = em.createNamedQuery("CsRelCatsocTipoInter.findTipiInterventoCatSoc");
		q.setParameter("idSoggetto", idSoggetto);
		q.setParameter("dtRif", dataRif);
		return (List<CsCTipoIntervento>) q.getResultList();
	}

	public CsIIntervento getInterventoById(Long id) {
		CsIIntervento intervento = em.find(CsIIntervento.class, id);
		return intervento;
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
	
	public CsFlgIntervento getFoglioInterventoById(Long id) {
		CsFlgIntervento fgl = em.find(CsFlgIntervento.class, id);
		loadRelatedEntities(fgl.getCsDDiario());
		return fgl;
	}

	private void loadRelatedEntities(CsDDiario diario) {
		if (diario != null) {
			diario.getCsDDiariFiglio().size();
			diario.getCsDDiariPadre().size();
		}
	}

	@SuppressWarnings("unchecked")
	public List<CsIIntervento> getListaInterventiByIdCaso(Long id) {
		List<CsIIntervento> lstI = new ArrayList<CsIIntervento>();
		logger.info("getListaInterventiByIdCaso " + id);

		Query q;
		try{
			q = em.createNamedQuery("CsFlgIntervento.getListaInterventiByIdCaso");
			q.setParameter("casoId", id);
			lstI = q.getResultList();
			logger.info("getListaInterventiByIdCaso id["+id+"]- result[" + lstI.size() + "]");
		}catch(Throwable e){
			logger.error(e);
			throw new CarSocialeServiceException(e);
		}
		
		return lstI;
	}

	public Integer countInterventiByIdCaso(Long id) {
		Query q = em.createNamedQuery("CsFlgIntervento.countInterventiByIdCaso");
		q.setParameter("casoId", id);
		Long count = (Long) q.getSingleResult();
		return count.intValue();
	}

	public CsRelSettCsocTipoInter findRelSettCsocTipoInterById(CsRelSettCsocTipoInterPK obj) {
		return em.find(CsRelSettCsocTipoInter.class, obj);

	}

	public void deleteFoglioAmministrativo(Long obj) {

		CsFlgIntervento fi = em.find(CsFlgIntervento.class, obj);

		Query q = em.createNamedQuery("CsFlgIntervento.deleteById");
		q.setParameter("idDiario", obj);
		q.executeUpdate();
	}

	public void deleteIntervento(Long obj) throws Exception {
		CsIIntervento i = this.getInterventoById(obj);
		em.remove(i);
		/*
		 * Query q = em.createNamedQuery("CsIIntervento.deleteById");
		 * q.setParameter("idIntervento", obj); q.executeUpdate();
		 */
	}

	public void saveRelRelazioneTipoint(CsRelRelazioneTipoint cs) {
		em.persist(cs);
	}

	public void deleteRelRelazioneTipointByIdRelazione(Long obj) {
		Query q = em.createNamedQuery("CsRelRelazioneTipoint.deleteByIdRelazione");
		q.setParameter("idRelazione", obj);
		q.executeUpdate();
	}

	public CsICentrod findCentroDiurnoById(Long id) {
		CsICentrod t = em.find(CsICentrod.class, id);
		return t;
	}

	@SuppressWarnings("unchecked")
	public List<VGerrarchiaServizi> findAllNodesTipoIntervento() {
		Query q = em.createNamedQuery("VGerrarchiaServizi.findAll");
		return (List<VGerrarchiaServizi>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<CsCTipoInterventoCustom> findTipiIntCustom(Long obj) {
		Query q = em.createNamedQuery("CsCTipoInterventoCustom.findAll");
		return (List<CsCTipoInterventoCustom>) q.getResultList();
	}

	public CsCTipoInterventoCustom saveNewCsCTipoInterventoCustom(List<CsCTipoInterventoCustom> intCustom, CsCTipoInterventoCustom selNewIntervento) {
		CsCTipoInterventoCustom selIntervento = null;
		for (CsCTipoInterventoCustom item : intCustom) {
			if (selNewIntervento.equals(item))
				selIntervento = item;
			item.setId(null);

			em.persist(item);

		}

		return selIntervento;
	}

	public void refreshTipoInterventoView() {
		try {
			Query q = em.createNativeQuery("call dbms_scheduler.run_job('MV_REFRESH_TIPO_INTERVENTO')");
			q.executeUpdate();
		} catch (Exception e) {
			System.out.println("ERRORE RTEFRESH " + e.getMessage());
		}

	}

	public CsCTipoInterventoCustom findTipiInterventoCustomById(Long tipoInterventoCustomId) {
		CsCTipoInterventoCustom t = em.find(CsCTipoInterventoCustom.class, tipoInterventoCustomId);
		return t;
	}

	public CsCCategoriaSociale findCatSocialeByDescrizione(String desc) {
		Query q = em.createNamedQuery("CsCCategoriaSociale.findByDescrizione");
		q.setParameter("desc", desc);
		List lst = q.getResultList();
		if (lst != null && lst.size() > 0)
			return (CsCCategoriaSociale) lst.get(0);
		return null;
	}

	public CsIPasti findPastiById(Long id) {
		CsIPasti t = em.find(CsIPasti.class, id);
		return t;
	}

	public CsIContrEco findContributoEconomicoById(Long id) {
		CsIContrEco t = em.find(CsIContrEco.class, id);
		return t;
	}

	public CsIVouchersad findVouherSadById(Long id) {
		CsIVouchersad t = em.find(CsIVouchersad.class, id);
		return t;
	}

	public CsIBuonoSoc findBuonoSocialeById(Long id) {
		CsIBuonoSoc t = em.find(CsIBuonoSoc.class, id);
		return t;
	}

	public CsIResiMinore findResiMinoreById(Long id) {
		CsIResiMinore t = em.find(CsIResiMinore.class, id);
		return t;
	}

	public CsIResiAdulti findResiAdultiById(Long id) {
		CsIResiAdulti t = em.find(CsIResiAdulti.class, id);
		return t;
	}

	public CsIAffidoFam findAffidoById(Long id) {
		CsIAffidoFam t = em.find(CsIAffidoFam.class, id);
		return t;
	}

	public CsIAdmAdh findAdmById(Long id) {
		CsIAdmAdh t = em.find(CsIAdmAdh.class, id);
		return t;
	}

	public CsISemiResiMin findSemiResMinoreById(Long id) {
		CsISemiResiMin t = em.find(CsISemiResiMin.class, id);
		return t;
	}

	public CsISchedaLavoro findSchedaLavoroById(Long id) {
		CsISchedaLavoro t = em.find(CsISchedaLavoro.class, id);
		return t;
	}

	@SuppressWarnings("unchecked")
	public List<VTipiInterventoUsati> getInterventiRecentiList() {
		Query q = em.createNamedQuery("VInterventiRecenti.findAll");
		return (List<VTipiInterventoUsati>) q.getResultList();
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
	
	public List<CsIRigaQuota> getRigheQuotaByQuotaId(Long idQuota){
		Query q = em.createNamedQuery("CsIRigaQuota.findIdsByQuota");
		q.setParameter("idQuota", idQuota);
		return q.getResultList();
	}
	
	public void deleteRigheQuota(List<CsIRigaQuota> lstRigheQuota){
		Query q = em.createNamedQuery("CsIRigaQuota.deleteById");
		q.setParameter("listaIds", lstRigheQuota);
		q.executeUpdate();
	}

	public CsIQuota updateCsIQuota(CsIQuota quota) {
		
/*		List<Long> idRigheNew = new ArrayList<Long>();
		for(CsIRigaQuota rq : quota.getCsIRigheQuota()){
			if(rq.getId()!=null && rq.getId()>0) idRigheNew.add(rq.getId());
		}
		
		//Recupero la lista di righe_quota per eliminare eventualmente i record non più presenti
		List<CsIRigaQuota> lstRigheRemove = new ArrayList<CsIRigaQuota>();
		List<CsIRigaQuota> lstRqOld = getRigheQuotaByQuotaId(quota.getId());
		
		for(CsIRigaQuota rqold : lstRqOld){
			if(!idRigheNew.contains(rqold.getId())){
			    CsIRigaQuota r = em.find(CsIRigaQuota.class, rqold.getId());
				lstRigheRemove.add(r);
			}
		}
		
		
		if(!lstRigheRemove.isEmpty())
			deleteRigheQuota(lstRigheRemove);*/
		return em.merge(quota);
	}


	//INIZIO MOD-RL
	public List<ArRelClassememoPresInps> findArRelClassememoPresInpbyTipoInterventoId( long tipoInterventoId) {

		Query q = em.createNamedQuery("ArRelClassememoPresInps.findByTipoInterventoId");  

		q.setParameter("tipoInterventoId", tipoInterventoId);
		List<ArRelClassememoPresInps> lst = q.getResultList();  
		
		logger.debug("findArRelClassememoPresInpbyTipoInterventoId["+tipoInterventoId+"] - RESULT["+lst.size()+"]");
		
		return  lst;
	}
	



	public CsFlgIntervento getPrimoFoglioAmministrativo(Long idIntervento) {
		try{
			Query q = em.createNamedQuery("CsFlgIntervento.getPrimoFoglioByIdIntervento");
			q.setParameter("interventoId", idIntervento);
			List<CsFlgIntervento> lst = q.getResultList();
			if(!lst.isEmpty()){
				CsFlgIntervento f =  lst.get(0);
				f.getCsDDiario().getCsOOperatoreSettore();
				return f;
			}
		}catch(Throwable e){
			logger.error(e);
			throw new CarSocialeServiceException(e);
		}
		return null;
	}
	
	//FINE MOD-RL


	//INIZIO SISO-522
	public List<ArFfProgetto> getProgetti(){			
		Query q = em.createNamedQuery("ArFfProgetto.findAll"); 
		return (List<ArFfProgetto>) q.getResultList();  
	}
	//FINE SISO-522

	//INIZIO SISO-575
	public List<ArFfProgetto> findProgettiByBelfioreOrganizzazione(String belfiore){			
		Query q = em.createNamedQuery("ArFfProgetto.findByBelfioreOrganizzazione"); 
		q.setParameter( "belfiore", belfiore );
		return (List<ArFfProgetto>) q.getResultList();  
	}
	//FINE SISO-575

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
						"AND C.ID ="+obj;
			
			q = em.createNativeQuery(sql);
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

	public CsIInterventoPr updateCsIInterventoPr(CsIInterventoPr progetto) {
		try{
			return em.merge(progetto);
		}catch(Throwable e){
			logger.error(e);
			throw new CarSocialeServiceException(e);
		}
	}
	
	//INIZIO SISO-790
		public List<ArFfProgettoAttivita> getSottocorsi(){			
			Query q = em.createNamedQuery("ArFfProgettoAttivita.findByAbilitato"); 
			return (List<ArFfProgettoAttivita>) q.getResultList();  
		}
		//FINE SISO 790
	
}
