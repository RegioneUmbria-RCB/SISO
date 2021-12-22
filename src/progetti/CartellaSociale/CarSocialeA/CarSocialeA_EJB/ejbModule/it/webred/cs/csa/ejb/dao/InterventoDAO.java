package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.CodificaINPS;
import it.webred.cs.csa.ejb.dto.InformativaDTO;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.ArFfProgettoAttivita;
import it.webred.cs.data.model.ArRelClassememoPresInps;
import it.webred.cs.data.model.ArRelIntCustomIstat;
import it.webred.cs.data.model.ArRelIntCustomPresInps;
import it.webred.cs.data.model.ArTClasse;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIAdmAdh;
import it.webred.cs.data.model.CsIAffidoFam;
import it.webred.cs.data.model.CsIBuonoSoc;
import it.webred.cs.data.model.CsICentrod;
import it.webred.cs.data.model.CsIContrEco;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsIPasti;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIResiAdulti;
import it.webred.cs.data.model.CsIResiMinore;
import it.webred.cs.data.model.CsIRigaQuota;
import it.webred.cs.data.model.CsISchedaLavoro;
import it.webred.cs.data.model.CsISemiResiMin;
import it.webred.cs.data.model.CsIVouchersad;
import it.webred.cs.data.model.CsRelRelazioneTipoint;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsRelSettCsocTipoInterPK;
import it.webred.cs.data.model.CsTbProgettoAltro;
import it.webred.cs.data.model.VArCTariffa;
import it.webred.cs.data.model.VGerrarchiaServizi;
import it.webred.cs.data.model.VLineaFin;
import it.webred.cs.data.model.VServiziCustom;
import it.webred.cs.data.model.VTipiInterventoUsati;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

@Named
public class InterventoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	protected SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

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
		logger.debug("findTipiInterventoSettoreCatsoc idSettore["+idSettore+"] idCatSoc["+idCatsoc+"]");
		Query q = em.createNamedQuery("CsRelSettCsocTipoInter.findTipiInterventoSettCatsoc");
		q.setParameter("idSettore", idSettore);
		q.setParameter("idCatsoc", idCatsoc);
		List<CsCTipoIntervento> lst = (List<CsCTipoIntervento>) q.getResultList();
		logger.debug("findTipiInterventoSettoreCatsoc RESULT["+lst.size()+"]");
		return lst;
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
		logger.info("getListaInterventiByIdCaso id["+id+"]");

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
	
	public List<InterventoBaseDTO> getListaInfoInterventiByIdCaso(Long id){
		List<InterventoBaseDTO> lstInt = new ArrayList<InterventoBaseDTO>();
		Query q;
		try{
			String sql ="select distinct i.id, TIPOINT.DESCRIZIONE tipoIntervento, tipoCust.descrizione tipoInterventoCustom, "
					+ "i.inizio_dal, i.inizio_al, i.fine_dal, i.fine_al, s.tipo stato "
					+ "from cs_d_diario d, cs_flg_intervento fgl, cs_i_intervento i  "
					+ "left join cs_i_intervento_eseg_mast m on i.id=m.intervento_id "
                    + "left join cs_i_intervento_eseg e on e.intervento_eseg_mast_id=m.id "
                    + "left join CS_CFG_INT_ESEG_STATO s on (s.id=e.stato_id and s.tipo='E') "
                    + "left join CS_C_TIPO_INTERVENTO tipoInt on (tipoInt.id = I.SCTI_TIPO_INTERVENTO_ID) "
                    + "left join CS_C_TIPO_INTERVENTO_CUSTOM tipoCust on (tipoCust.id = I.TIPO_INTERVENTO_CUSTOM_ID) "
                    + "where d.caso_id = :casoId and d.id=fgl.diario_id and fgl.intervento_id=i.id "
                    + "order by i.id";
			
			q = em.createNativeQuery(sql);
			q.setParameter("casoId", id);
			
			List<Object[]> lst = q.getResultList();
			
			for(Object[] res : lst){
				int index = 0;
				BigDecimal idIntervento = (BigDecimal)res[index++];
				String tipoIntervento = (String)res[index++];
				String tipoInterventoCustom = (String)res[index++];
				Date inizioDal = (Date)res[index++];
				Date inizioAl = (Date)res[index++];
				Date fineDal = (Date)res[index++];
				Date fineAl = (Date)res[index++];
				Character stato = (Character)res[index++];
				
				boolean erogato = stato!=null ? DataModelCostanti.TipoStatoErogazione.EROGATIVO.equalsIgnoreCase(stato.toString()) : false;
			
				String dtInizioDal = inizioDal!=null ? ddMMyyyy.format(inizioDal) : "";
				String dtInizioAl = inizioAl!=null ? ddMMyyyy.format(inizioAl) : "";
				
				String inizio = dtInizioDal;
				if(!StringUtils.isBlank(dtInizioDal) && !StringUtils.isBlank(dtInizioAl))
				   inizio = "Dal "+ dtInizioDal +" al "+ dtInizioAl;
				
				String dtFineDal = fineDal!=null ? ddMMyyyy.format(fineDal) : "";
				String dtFineAl = fineAl!=null ? ddMMyyyy.format(fineAl) : "";
				
				String fine = dtFineDal;
				if(!StringUtils.isBlank(dtFineDal) && !StringUtils.isBlank(dtFineAl))
					fine = "Dal "+ dtFineDal +" al "+ dtFineAl;
				 
				InterventoBaseDTO intout = new InterventoBaseDTO();
				intout.setTipoIntervento(tipoIntervento);
				intout.setTipoInterventoCustom(tipoInterventoCustom);
				intout.setInizio(inizio);
				intout.setFine(fine);
				intout.setErogato(erogato);
				
				lstInt.add(intout);
			}
			
		}catch(Throwable e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		
		return lstInt;
	
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
		logger.debug("INIT findAllNodesTipoIntervento");
		Query q = em.createNamedQuery("VGerrarchiaServizi.findAll");
		List<VGerrarchiaServizi> lista = (List<VGerrarchiaServizi>) q.getResultList();
		logger.debug("END findAllNodesTipoIntervento");
		return lista;
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
	
	
	
	//SISO-1110 Fine
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	@SuppressWarnings("unchecked")
	public List<CsCTipoInterventoCustom> findTipiIntCustom() {
		Query q = em.createNamedQuery("CsCTipoInterventoCustom.findAll");
		return (List<CsCTipoInterventoCustom>) q.getResultList();
	}
	
	public CsCTipoInterventoCustom findTipoIntCustomById(Long id){
		return em.find(CsCTipoInterventoCustom.class, id);
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
		logger.debug("findArRelClassememoPresInpbyTipoInterventoId["+tipoInterventoId+"]");
		Query q = em.createNamedQuery("ArRelClassememoPresInps.findByTipoInterventoId");  

		q.setParameter("tipoInterventoId", tipoInterventoId);
		List<ArRelClassememoPresInps> lst = q.getResultList();  
		
		logger.debug("findArRelClassememoPresInpbyTipoInterventoId["+tipoInterventoId+"] - RESULT["+lst.size()+"]");
		
		return  lst;
	}
	
	//SISO-1110 Inizio
	public List<ArRelIntCustomPresInps> findArRelIntCustomPresInpbyTipoInterventoId( long tipoInterventoId) {

		Query q = em.createNamedQuery("ArRelClassememoPresInps.findByTipoInterventoId");  

		q.setParameter("tipoInterventoId", tipoInterventoId);
		List<ArRelIntCustomPresInps> lst = q.getResultList();  
		
		logger.debug("findArRelClassememoPresInpbyTipoInterventoId["+tipoInterventoId+"] - RESULT["+lst.size()+"]");
		
		return  lst;
	}
//DA CANCELLARE
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

	public CsIInterventoPr updateCsIInterventoPr(CsIInterventoPr progetto) {
		try{
			return em.merge(progetto);
		}catch(Throwable e){
			logger.error("salvaProgettoIntervento"+e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
	}
	
		//INIZIO SISO-1131
		public List<CsTbProgettoAltro> findProgettiAltro(){			
			Query q = em.createNamedQuery("CsTbProgettoAltro.findAll"); 
			return (List<CsTbProgettoAltro>) q.getResultList();  
		}
		
		public List<CsTbProgettoAltro> findProgettiAltroPerDesc(String descrizione){			
			Query q = em.createNamedQuery("CsTbProgettoAltro.findProgettoAltroByDescrizione"); 
			q.setParameter( "descrizione", descrizione );
			return (List<CsTbProgettoAltro>) q.getResultList();  
		}
		
		
	    public CsTbProgettoAltro updateCsTbProgettoAltro(CsTbProgettoAltro csTbProgettoAltro) {

		return em.merge(csTbProgettoAltro);
	  }
		//FINE SISO-1131
	//SISO-469
	    public List<VArCTariffa> findTariffe(String codRouting, Long unitaMisuraId){			
			Query q = em.createNamedQuery("VArCTariffa.findTariffaByUMeOrg"); 
			q.setParameter("codRouting", codRouting );
			q.setParameter("unitaMisuraId", unitaMisuraId );
			return (List<VArCTariffa>) q.getResultList();  
		}
}  
