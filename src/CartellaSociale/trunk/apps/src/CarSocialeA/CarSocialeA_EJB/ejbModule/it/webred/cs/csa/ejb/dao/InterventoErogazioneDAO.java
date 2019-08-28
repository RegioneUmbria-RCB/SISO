package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.CompartecipazioneDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SpesaDTO;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCfgAttrOption;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEseg;
import it.webred.cs.data.model.CsCfgIntEsegAttUm;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsCfgIntEsegStatoInt;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIQuotaRipartiz;
import it.webred.cs.data.model.CsIRigaQuota;
import it.webred.cs.data.model.CsIValQuota;
import it.webred.utils.CollectionsUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Named
public class InterventoErogazioneDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CsCfgIntEseg findCfgIntEsegById(Long id) {
	   CsCfgIntEseg cfg = null;
       try{
			cfg = em.find(CsCfgIntEseg.class, id);
			if (cfg != null) {
				cfg.getCsCfgIntEsegStatoInt().size();
				for(CsCfgIntEsegStatoInt s : cfg.getCsCfgIntEsegStatoInt())
					s.getCsCfgIntEsegAttUms().size();
			}else
				logger.warn("findCfgIntEsegById - No Results per Id["+id+"]");
        }catch(Exception e){	
			throw new CarSocialeServiceException("getInterventoEsegByIntervento",e);
		}
		return cfg;
	}

	public CsCfgIntEseg findCfgIntEsegByTipoInterventoId(Long tipoInterventoId) {
		CsCfgIntEseg cfg = null;
		if(tipoInterventoId!=null){
			try{
				Query q = em.createNamedQuery("CsCfgIntEseg.findConfigIntErogByTipoIntervento");
				q.setParameter("tipoInterventoId", tipoInterventoId);
				List<CsCfgIntEseg> lst  = (List<CsCfgIntEseg>) q.getResultList();
				
				if(lst!=null && lst.size()>0){
					cfg = lst.get(0);
				    if(lst.size()>1)
				    	logger.warn("findCfgIntEsegByTipoInterventoId[id:"+tipoInterventoId+"] --> CsCfgIntEseg [MULTIPLI]");
				}else
					logger.warn("findCfgIntEsegByTipoInterventoId - No Results per Tipo Intervento[id:"+tipoInterventoId+"]");
				
				
				if (cfg != null) {
					logger.debug("findCfgIntEsegByTipoInterventoId[id:"+tipoInterventoId+"] --> CsCfgIntEseg [id:"+cfg.getId()+"]");
					for(CsCfgIntEsegStatoInt s : cfg.getCsCfgIntEsegStatoInt()){
						for(CsCfgIntEsegAttUm ums : s.getCsCfgIntEsegAttUms())
							ums.getCsCfgAttrUm().getCsCfgAttributo().getCsCfgAttrOptions().size();
					}
					
				}
				
		    }catch(Exception e){	
				throw new CarSocialeServiceException("findCfgIntEsegByTipoInterventoId",e);
			}
		}else
			logger.warn("findCfgIntEsegByTipoInterventoId - Tipo Intervento id NON DEFINITO");
		
		return cfg;
	}
	
	public List<CsCfgAttrOption> findCsCfgAttrOptions(Long csCfgAttributoId){
		try{
			Query q = em.createNamedQuery("CsCfgAttrOption.findByIdAtt");
			q.setParameter("idAttributo", csCfgAttributoId);
			return q.getResultList();
	    }catch(Exception e){	
			throw new CarSocialeServiceException("findCsCfgAttrOptions",e);
		}
	}

	public CsIInterventoEseg getErogazioniEseguiteHistory(Long intEseguitoId) {

		CsIInterventoEseg csIInterventoEseg = em.find(CsIInterventoEseg.class, intEseguitoId);
		csIInterventoEseg.getCsIInterventoEsegValores().size();

		return csIInterventoEseg;
	}

	@SuppressWarnings("unchecked")
	public List<CsCfgIntEsegStato> getListaIntEsegStatoByTipiStato(List<String> tipiStato, Long tipoInterventoId) {
		// Carico gli stati dell'intervento che siano del tipo specificato (se non è attivo il flag BYPASS_TIPO_STATO)
		Query q = em.createNamedQuery("CsCfgIntEsegStato.findIntEsegStatoByTipiStatoOrBypass");
		q.setParameter("tipiStato", tipiStato);
		q.setParameter("tipoInterventoId", tipoInterventoId);
		return (List<CsCfgIntEsegStato>) q.getResultList();
	}
	
	public CsIIntervento getInterventoById(Long interventoId) {

		CsIIntervento csIIntervento = em.find(CsIIntervento.class, interventoId);
		return csIIntervento;
	}

	public CsIInterventoEseg getInterventoEsegById(Long interventoId) {
		//TODO controllare perche non trova l'elemento con la funziona em.find
		CsIInterventoEseg csIInterventoEseg = em.find(CsIInterventoEseg.class, interventoId);
	
	
		if (csIInterventoEseg != null && csIInterventoEseg.getCsIInterventoEsegValores() != null)
			csIInterventoEseg.getCsIInterventoEsegValores().size();
		return csIInterventoEseg;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsIInterventoEseg> getInterventoEsegByIdMaster(Long masterId, boolean loadDettagli) {
		List<CsIInterventoEseg>listaInterventi;
		try{
			Query q = em.createNamedQuery("CsIInterventoEseg.findInterventoEsegByMasterId");
			q.setParameter("masterId", masterId);
			listaInterventi= (List<CsIInterventoEseg>) q.getResultList();
			
			if(loadDettagli){
				for(CsIInterventoEseg es : listaInterventi){
					for(CsIInterventoEsegValore val : es.getCsIInterventoEsegValores())
						val.getCsAttributoUnitaMisura().getCsCfgAttributo().getCsCfgAttrOptions().size();
				}
			}
			
		}catch(Exception e){
			logger.error("getInterventoEsegByIdMaster", e);
			throw new CarSocialeServiceException(e);
		}
		return listaInterventi;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsIInterventoEsegMastSogg> getBeneficiariErogazione(Long masterId) {
		if(masterId!=null){
			Query q = em.createNamedQuery("CsIInterventoEsegMastSogg.findBeneficiariByMasterId");
			q.setParameter("masterId", masterId);
			return (List<CsIInterventoEsegMastSogg>) q.getResultList();
		}
		return null;
	}

	
	public void rimuoviBeneficiari(Long masterId){
		try{
		Query q = em.createNativeQuery("DELETE FROM Cs_I_Intervento_Eseg_Mast_Sogg cs where INT_ESEG_MAST_ID="+masterId);
		q.executeUpdate();
		}catch(Throwable e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
	}

	public void rimuoviInterventiEseg(List<Long> ids){
		
		Query q = em.createNamedQuery("CsIInterventoEsegValore.deletebyIdsIntervento");
		q.setParameter("listaIds", ids);
		q.executeUpdate();
		
		q = em.createNamedQuery("CsIInterventoEseg.deleteByIds");
		q.setParameter("listaIds", ids);
		q.executeUpdate();
	}

	public void rimuoviInterventoEseguitoMast(Long interEsgMastId){	
		Query q = em.createNamedQuery("CsInterventoEsegMast.deleteIntesegMast");
		q.setParameter("interventoesegmastId", interEsgMastId);
		q.executeUpdate();
	}
	
	public void rimuoviQuota(Long id){
		//TODO forse può funzionare anche la cancellazione a cascata, da testare
		Query q = em.createNamedQuery("CsIQuota.findById");
		q.setParameter("id", id);
		CsIQuota csIQuota = (CsIQuota) q.getSingleResult(); 
		
		for (CsIRigaQuota csIRigaQuota : csIQuota.getCsIRigheQuota()) {
			
			Query deleteCsIRigaQuota = em.createNamedQuery("CsIRigaQuota.delete");
			deleteCsIRigaQuota.setParameter("id", csIRigaQuota.getId());
			deleteCsIRigaQuota.executeUpdate();
			
			CsIValQuota csIValQuota = csIRigaQuota.getCsIValQuota();
			if (csIValQuota!=null) { 
				Query deleteCsIValQuota = em.createNamedQuery("CsIValQuota.delete");
				deleteCsIValQuota.setParameter("id", csIValQuota.getId());
				deleteCsIValQuota.executeUpdate();
			}
		}

		CsIQuotaRipartiz csIQuotaRipartiz = csIQuota.getCsIQuotaRipartiz();
		if (csIQuotaRipartiz!=null) { 
			Query deleteCsIQuotaRipartiz = em.createNamedQuery("CsIQuotaRipartiz.delete");
			deleteCsIQuotaRipartiz.setParameter("id", csIQuotaRipartiz.getId());
			deleteCsIQuotaRipartiz.executeUpdate();
		}

		Query deleteCsIQuota = em.createNamedQuery("CsIQuota.delete");
		deleteCsIQuota.setParameter("id", csIQuota.getId());
		deleteCsIQuota.executeUpdate();
	}
	
	public CsIInterventoEseg aggiungiInterventoEseguito(CsIInterventoEseg csIInterventoEseg) {
		try{
			return em.merge(csIInterventoEseg);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public CsIInterventoEsegMast salvaInterventoEseguitoMast(CsIInterventoEsegMast intEsegMastNuovo) {		
			return em.merge(intEsegMastNuovo);		
	}

	public void eliminaInterventoEsegStorico(List<CsIInterventoEseg> intEsegIdDaEliminare) {
		List<Long> ids = new ArrayList<Long>();
		for (CsIInterventoEseg intEsegId : intEsegIdDaEliminare)
			ids.add(intEsegId.getId());
	
		if(!ids.isEmpty()) rimuoviInterventiEseg(ids);
	}
	
	//TODO: mk_osmosit
/*	private List<DatiAggregatiErogazioneDTO> groupListaErogInterventiBySettoreByIntervento(List<ErogazioneDTO> dtoList){		
		Map<Long, List<ErogazioneDTO>> mapList = new HashMap<Long, List<ErogazioneDTO>>();				
		for (ErogazioneDTO erogazioneDTO : dtoList) {
			List<ErogazioneDTO> list= new ArrayList<ErogazioneDTO>();
			if(erogazioneDTO.getIdIntervento()==null){
				list.add(erogazioneDTO);
				mapList.put(erogazioneDTO.getIdInterventoEseg(), list);
			}else
			{
				if(mapList.containsKey(erogazioneDTO.getIdIntervento())){
					list = mapList.get(erogazioneDTO.getIdIntervento());
					list.add(erogazioneDTO);
				}else{	
					list.add(erogazioneDTO);					
				}
				mapList.put(erogazioneDTO.getIdIntervento(), list);
			}
			
		}
		List<DatiAggregatiErogazioneDTO> list = new ArrayList<DatiAggregatiErogazioneDTO>();
		for (Map.Entry<Long, List<ErogazioneDTO>> entry : mapList.entrySet()) {
			DatiAggregatiErogazioneDTO dtoAggregato = new DatiAggregatiErogazioneDTO();			
			List<ErogazioneDTO> l= entry.getValue();
			BigDecimal totSpesa=new BigDecimal(0);
			BigDecimal totPercGestita=new BigDecimal(0);
		
			BigDecimal totaleValGest=new BigDecimal(0);
			BigDecimal totaleCompartUtenti=new BigDecimal(0);
			BigDecimal totaleCompartSsn=new BigDecimal(0);
			BigDecimal totaleCompartAltre=new BigDecimal(0);

			for (ErogazioneDTO dto : l) {
				totSpesa = totSpesa.add(dto.getDetailSpesa());
				totPercGestita = totPercGestita.add(dto.getDetailPercGest());
				totaleValGest = totaleValGest.add(dto.getDetailValGest());
				totaleCompartUtenti = totaleCompartUtenti.add(dto.getDetailCompartUtenti());
				totaleCompartSsn = totaleCompartSsn.add(dto.getDetailCompartSsn());
				totaleCompartAltre = totaleCompartAltre.add(dto.getDetailCompartAltre());		
				dtoAggregato.setMasterSpesa(dto.getMasterSpesa());
				dtoAggregato.setMasterPercGestitaEnte(dto.getMasterPercGestitaEnte());
				dtoAggregato.setMasterValGestitaEnte(dto.getMasterValGestitaEnte());
				dtoAggregato.setMasterCompartUtenti(dto.getMasterCompartUtenti());
				dtoAggregato.setMasterCompartSsn(dto.getMasterCompartSsn());
				dtoAggregato.setMasterCompartAltre(dto.getMasterCompartAltre());
				dtoAggregato.setMasterNoteAltre(dto.getMasterNoteAltre());
				dtoAggregato.setMasterFFOrigineId(dto.getMasterFFOrigineId());
				dtoAggregato.setMasterCodFin1(dto.getMasterCodFin1());				
				dtoAggregato.setTipoInterventoCustom(dto.getTipoInterventoCustom());								
			}	
			dtoAggregato.setTotaleSpesa(totSpesa);
			dtoAggregato.setTotalePercGest(totPercGestita);
			dtoAggregato.setTotaleValGest(totaleValGest);
			dtoAggregato.setTotaleCompartUtenti(totaleCompartUtenti);
			dtoAggregato.setTotaleCompartSsn(totaleCompartSsn);
			dtoAggregato.setTotaleCompartAltre(totaleCompartAltre);
			String spesa =  new String();
			String percGestita = new String();
			String valGestita = new String();
			String compartUtenti = new String();
			String compartSsn = new String();
			String compartAltre = new String();
			String euroSymbol = new String(" \u20ac ");
			if(dtoAggregato.getMasterSpesa() != null && dtoAggregato.getMasterSpesa().compareTo(new BigDecimal(0))>0)
				spesa = "Spesa (Euro) = "+dtoAggregato.getMasterSpesa()+euroSymbol;
			else {
				if(dtoAggregato.getTotaleSpesa()!=null && dtoAggregato.getTotaleSpesa().compareTo(new BigDecimal(0))>0){
					spesa = "Spesa (Euro) = "+dtoAggregato.getTotaleSpesa()+euroSymbol;
				}
			}
			if(dtoAggregato.getMasterPercGestitaEnte() != null && dtoAggregato.getMasterPercGestitaEnte().compareTo(new BigDecimal(0))>0)
				percGestita = "% Spesa gestita direttamente = "+dtoAggregato.getMasterPercGestitaEnte()+" % ";
			else{
				if(dtoAggregato.getTotalePercGest()!=null && dtoAggregato.getTotalePercGest().compareTo(new BigDecimal(0))>0){
					percGestita = "% Spesa gestita direttamente = "+dtoAggregato.getTotalePercGest()+" % ";
				}
			}
			if(dtoAggregato.getMasterValGestitaEnte() != null && dtoAggregato.getMasterValGestitaEnte().compareTo(new BigDecimal(0))>0)
				valGestita = "Valore spesa gestita direttamente = "+dtoAggregato.getMasterValGestitaEnte()+euroSymbol;
			else{
				if(dtoAggregato.getTotaleValGest()!=null && dtoAggregato.getTotaleValGest().compareTo(new BigDecimal(0))>0){
					valGestita = "Valore spesa gestita direttamente = "+dtoAggregato.getTotaleValGest()+euroSymbol;
				}
			}
			if(dtoAggregato.getMasterCompartUtenti() != null && dtoAggregato.getMasterCompartUtenti().compareTo(new BigDecimal(0))>0)
				compartUtenti = "Compartecipazione degli utenti = "+dtoAggregato.getMasterCompartUtenti()+euroSymbol;
			else{
				if(dtoAggregato.getTotaleCompartUtenti()!=null && dtoAggregato.getTotaleCompartUtenti().compareTo(new BigDecimal(0))>0){
					compartUtenti = "Compartecipazione degli utenti = "+dtoAggregato.getTotaleCompartUtenti()+euroSymbol;
				}
			}
			if(dtoAggregato.getMasterCompartSsn() != null && dtoAggregato.getMasterCompartSsn().compareTo(new BigDecimal(0))>0)
				compartSsn = "Compartecipazione del S.S.N. = "+dtoAggregato.getMasterCompartSsn()+euroSymbol;
			else{
				if(dtoAggregato.getTotaleCompartSsn()!=null && dtoAggregato.getTotaleCompartSsn().compareTo(new BigDecimal(0))>0){
					compartSsn = "Compartecipazione del S.S.N. = "+dtoAggregato.getTotaleCompartSsn()+euroSymbol;
				}
			}
			if(dtoAggregato.getMasterCompartAltre() != null && dtoAggregato.getMasterCompartAltre().compareTo(new BigDecimal(0))>0)
				compartAltre = "Compartecipazione altre = "+dtoAggregato.getMasterCompartAltre()+euroSymbol;
			else{
				if(dtoAggregato.getTotaleCompartAltre()!=null && dtoAggregato.getTotaleCompartAltre().compareTo(new BigDecimal(0))>0){
					compartAltre = "Compartecipazione altre = "+dtoAggregato.getTotaleCompartAltre()+euroSymbol;
				}
			}
			dtoAggregato.setTotaleErogato(spesa+percGestita+valGestita+compartUtenti+compartSsn+compartAltre);
			dtoAggregato.setErogazioni(l);
			list.add(dtoAggregato);
		}
		return list;
	}*/
	
	public int countListaErogInterventiBySettore(ErogazioniSearchCriteria bDto) throws CarSocialeServiceException {
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
		try{
			String sql = qb.createQueryListaErogazioni(true);
			if(sql!=null){
				logger.debug("COUNT LISTA EROGAZIONI SQL["+sql+"]");
				Query q = em.createNativeQuery(sql);
				
				q.setParameter("settoreId", bDto.getSettoreId());
				//if(!bDto.isSearchConRichiesta())
				//	q.setParameter("organizzazioneId", bDto.getOrganizzazioneId());
				return ((BigDecimal) q.getSingleResult()).intValue();
			}else return 0;
		}catch(Throwable e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
	}

/*	public List<DatiAggregatiErogazioneDTO> searchListaErogInterventiBySettoreOLD(ErogazioniSearchCriteria bDto) throws CarSocialeServiceException {
		List<ErogazioneDTO> lista = new ArrayList<ErogazioneDTO>();
		List<DatiAggregatiErogazioneDTO> listDatiAggregati = new ArrayList<DatiAggregatiErogazioneDTO>();
		try{
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
		
		String sql = qb.createQueryListaErogazioni(false);
		if(sql!=null){
			logger.debug("LISTA EROGAZIONI SQL["+sql+"]");
			Query q = em.createNativeQuery(sql);
			
			q.setParameter("settoreId", bDto.getSettoreId());
			if(!bDto.isSearchConRichiesta())
				q.setParameter("organizzazioneId", bDto.getOrganizzazioneId());
			
			if(bDto.getFirst()>=0)
				q.setFirstResult(bDto.getFirst());
			if(bDto.getPageSize()>=0)
		    	q.setMaxResults(bDto.getPageSize());
			
			List<Object[]> result = (List<Object[]>)q.getResultList();
		
			for(Object[] o : result){
				ErogazioneDTO e = new ErogazioneDTO();
				
				//
				 BigDecimal detailSpesa = (BigDecimal)o[16];
				 BigDecimal detailPercGest= (BigDecimal)o[17];
				 BigDecimal detailValGest= (BigDecimal)o[18];
				 BigDecimal detailCompartUtenti= (BigDecimal)o[19];
				 BigDecimal detailCompartSsn= (BigDecimal)o[20];
				 BigDecimal detailCompartAltre= (BigDecimal)o[21];
				 String detailNoteAltre =(String)o[22];
				 String masterCognome=(String)o[23];
				 String masterNome=(String)o[24];
				 String masterCf=(String)o[25];
				 String masterDescrIntervento=(String)o[26];
				 BigDecimal masterSpesa= (BigDecimal)o[27];
				 BigDecimal masterPercGestitaEnte= (BigDecimal)o[28];
				 BigDecimal masterValGestitaEnte= (BigDecimal)o[29]; 
				 BigDecimal masterCompartUtenti= (BigDecimal)o[30];
				 BigDecimal masterCompartSsn= (BigDecimal)o[31];
				 BigDecimal masterCompartAltre= (BigDecimal)o[32];
				 String masterNoteAltre=(String)o[33];
				 Long masterFFOrigineId= o[34]!=null ? ((BigDecimal)o[34]).longValue() : null;
				 String masterCodFin1=(String)o[35];				
				 String tipoInterventoCustom = (String)o[36];
				 Long tipoInterventoCustomId= o[37]!=null ? ((BigDecimal)o[37]).longValue() : null;
				 Long masterId = o[39]!=null ?  ((BigDecimal)o[39]).longValue() : null;
				 
				 if(detailSpesa != null)
					 e.setDetailSpesa(detailSpesa);
				 else
					 e.setDetailSpesa(new BigDecimal(0));
				 if(detailPercGest!=null)
					 e.setDetailPercGest(detailPercGest);
				 else
					 e.setDetailPercGest(new BigDecimal(0));
				 if(detailValGest!=null)
					 e.setDetailValGest(detailValGest);
				 else
					 e.setDetailValGest(new BigDecimal(0));
				 if(detailCompartUtenti!=null)
					 e.setDetailCompartUtenti(detailCompartUtenti);
				 else
					 e.setDetailCompartUtenti(new BigDecimal(0));
				 if(detailCompartSsn!=null)
					 e.setDetailCompartSsn(detailCompartSsn);
				 else
					 e.setDetailCompartSsn(new BigDecimal(0));
				 if(detailCompartAltre!=null)
					 e.setDetailCompartAltre(detailCompartAltre);
				 else
					 e.setDetailCompartAltre(new BigDecimal(0));
				
				 e.setDetailNoteAltre(detailNoteAltre);
				 e.setMasterCognome(masterCognome);
				 e.setMasterNome(masterNome);
				 e.setMasterCf(masterCf);
				 e.setMasterDescrIntervento(masterDescrIntervento);
				 e.setMasterSpesa(masterSpesa);
				 e.setMasterPercGestitaEnte(masterPercGestitaEnte);
				 e.setMasterValGestitaEnte(masterValGestitaEnte);
				 e.setMasterCompartUtenti(masterCompartUtenti);
				 e.setMasterCompartSsn(masterCompartSsn);
				 e.setMasterCompartAltre(masterCompartAltre);
				 e.setMasterNoteAltre(masterNoteAltre);
				 e.setMasterFFOrigineId(masterFFOrigineId);
				 e.setMasterCodFin1(masterCodFin1);
				 e.setTipoInterventoCustom(tipoInterventoCustom);				 
				//
				String tipoDato = o[14].toString();
				Long idIntervento = o[0]!=null ? ((BigDecimal)o[0]).longValue() : null;
				Long idErogazione = o[15]!=null ? ((BigDecimal)o[15]).longValue() : null;
				
				e.setIdIntervento(idIntervento);
				e.setIdInterventoEsegMaster(masterId);
				e.setIdInterventoEseg(idErogazione);
				
				e.setDataRichiestaIntervento((Date)o[1]);
				
				CsCTipoIntervento tipoInt = new CsCTipoIntervento();
				tipoInt.setId(o[2]!=null ? ((BigDecimal)o[2]).longValue() : null);
				tipoInt.setDescrizione((String)o[3]);
				e.setTipoIntervento(tipoInt);
				e.setStatoUltimoFlg((String)o[4]);
				e.setDataUltimoFlg((Date)o[5]);
				Long casoId = o[6]!=null ? ((BigDecimal)o[6]).longValue() : null;
				Long anagraficaId = o[7]!=null ? ((BigDecimal)o[7]).longValue() : null;
				
				CsASoggettoLAZY soggetto = null;
				if(casoId!=null){
					CsACaso caso = em. find(CsACaso.class, casoId);
					if(caso!=null)
						soggetto = caso.getCsASoggetto();
				}
				if(soggetto==null && anagraficaId!=null)
					soggetto = em.find(CsASoggettoLAZY.class, anagraficaId);
					
				e.setSoggetto(soggetto);
				e.setCognome((String)o[8]);
				e.setNome((String)o[9]);
				e.setCf((String)o[10]);
				
				e.setDataUltimaErogazione((Date)o[11]);
				e.setDiarioId(o[12]!=null ? ((BigDecimal)o[12]).longValue() : null);
				e.setStatoUltimaErogazione((String)o[13]);
				
				lista.add(e);
			}
			listDatiAggregati = groupListaErogInterventiBySettoreByIntervento(lista);

		}
		}catch(Throwable e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		return listDatiAggregati;
	}	*/
	
	public List<ErogazioneMasterDTO> searchListaErogInterventiBySettore(ErogazioniSearchCriteria bDto) throws CarSocialeServiceException {
		List<ErogazioneMasterDTO> lista = new ArrayList<ErogazioneMasterDTO>();
		try{
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
		
		String sql = qb.createQueryListaErogazioni(false);
		if(sql!=null){
			logger.debug("LISTA EROGAZIONI SQL["+sql+"]");
			Query q = em.createNativeQuery(sql);
			
			q.setParameter("settoreId", bDto.getSettoreId());
			
			//SISO-748
			if(bDto.isSearchByCaso()){
				q.setParameter("casoId", bDto.getCasoId());
			}
			
			//if(!bDto.isSearchConRichiesta())
			//	q.setParameter("organizzazioneId", bDto.getOrganizzazioneId());
			
			if(bDto.getFirst()>=0)
				q.setFirstResult(bDto.getFirst());
			if(bDto.getPageSize()>=0)
		    	q.setMaxResults(bDto.getPageSize());
			
			List<Object[]> result = (List<Object[]>)q.getResultList();
		
			for(Object[] o : result){
				ErogazioneMasterDTO e = new ErogazioneMasterDTO();
			
				e.setIdIntervento(o[0]!=null ? ((BigDecimal)o[0]).longValue() : null);
				e.setDataRichiestaIntervento((Date)o[1]);
				
				CsCTipoIntervento tipoInt = new CsCTipoIntervento();
				tipoInt.setId(o[2]!=null ? ((BigDecimal)o[2]).longValue() : null);
				tipoInt.setDescrizione((String)o[3]);
				e.setTipoIntervento(tipoInt);
				
				e.setStatoUltimoFlg((String)o[4]);
				e.setDataUltimoFlg((Date)o[5]);
				
				//Long casoId = o[6]!=null ? ((BigDecimal)o[6]).longValue() : null;
				//Long anagraficaId = o[7]!=null ? ((BigDecimal)o[7]).longValue() : null;
				
	/*			e.setCognome((String)o[8]);
				e.setNome((String)o[9]);
				e.setCf((String)o[10]);*/
				
				e.setDataUltimaErogazione((Date)o[9]);
				e.setDiarioId(o[10]!=null ? ((BigDecimal)o[10]).longValue() : null);
				e.setStatoUltimaErogazione((String)o[11]);
				
				String tipoDato = o[12].toString();
				Long idErogazione = o[13]!=null ? ((BigDecimal)o[13]).longValue() : null;
			
				 String masterDescrIntervento=(String)o[14];
				 BigDecimal masterSpesa= (BigDecimal)o[15];
				 BigDecimal masterPercGestitaEnte= (BigDecimal)o[16];
				 BigDecimal masterValGestitaEnte= (BigDecimal)o[17]; 
				 BigDecimal masterCompartUtenti= (BigDecimal)o[18];
				 BigDecimal masterCompartSsn= (BigDecimal)o[19];
				 BigDecimal masterCompartAltre= (BigDecimal)o[20];
				 String masterNoteAltre=(String)o[21];
				 String masterLineaFin= (String)o[22];
				 String masterCodFin1=(String)o[23];				
				 String tipoInterventoCustom = (String)o[24];
				 Long tipoInterventoCustomId= o[25]!=null ? ((BigDecimal)o[25]).longValue() : null;
				 Long masterId = o[26]!=null ?  ((BigDecimal)o[26]).longValue() : null;
				 Boolean spesaMod =   o[27]!=null && ((BigDecimal)o[27]).intValue()==0 ? false : true;
				 Boolean compartMod = o[28]!=null && ((BigDecimal)o[28]).intValue()==0 ? false : true;
				 String tipoBeneficiario = (String)o[29];
				
				 e.setTipoBeneficiario(tipoBeneficiario);
				 e.setDescrIntervento(masterDescrIntervento);
				
				 SpesaDTO mastSpesa = new SpesaDTO(masterSpesa,masterPercGestitaEnte,masterValGestitaEnte,spesaMod);
				 CompartecipazioneDTO mastCompartecipazione = 
						 new CompartecipazioneDTO(masterCompartUtenti,masterCompartSsn,masterCompartAltre,masterNoteAltre,compartMod);
				 e.setSpesa(mastSpesa);
				 e.setCompartecipazioni(mastCompartecipazione);
				 e.setLineaFinanziamento(masterLineaFin);
				 e.setCodFinanziamento(masterCodFin1);
				 e.setTipoInterventoCustom(tipoInterventoCustom);
				 e.setIdTipoInterventoCustom(tipoInterventoCustomId);
				
				e.setIdInterventoEsegMaster(masterId);
				e.setIdInterventoEseg(idErogazione);
				e.setIdCategoriaSociale((BigDecimal)o[31]);
				
				//SISO-748
				e.setDiarioPaiId(o[32]!=null ? ((BigDecimal)o[32]).longValue() : null);	
				
				lista.add(e);
			}
			
		}
		}catch(Throwable e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		return lista;
	}

	public List<BigDecimal> searchListaMasterIdBySoggetto(ErogazioniSearchCriteria bDto) {
		try{
			ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
			
			String sql = qb.createQueryListaSoggettiErogazioni();
			if(sql!=null){
				logger.debug("LISTA SOGGETTI EROGAZIONI SQL["+sql+"]");
				Query q = em.createNativeQuery(sql);
				
				return  (List<BigDecimal>)q.getResultList();
				
			}
		}catch(Throwable e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		return null;	
	}

	public CsIInterventoEsegMast getErogazioneMasterById(Long obj) {
		if(obj!=null)
			 return em.find(CsIInterventoEsegMast.class, obj);
		else return null;
	}
	
	public CsIInterventoEsegMast findErogazioneMasterByPaiId(Long obj){
		if(obj!=null){
			TypedQuery<CsIInterventoEsegMast> tq = em.createQuery("SELECT m FROM CsIInterventoEsegMast m where m.diarioPaiId= ?1", CsIInterventoEsegMast.class);
			tq = tq.setParameter(1, obj);
			return tq.getSingleResult();
		}
		else return null;
	}
	
	public List<CsIInterventoEsegMastSogg> findMastSoggSganciatiCaso(){
		List<CsIInterventoEsegMastSogg> lst = new ArrayList<CsIInterventoEsegMastSogg>();
		try{
			
			String sql = "select d.* from Cs_I_Intervento_Eseg_Mast_Sogg d "+
	                     "where d.caso_id is null and exists "+
	                     "(select 1 from cs_a_anagrafica a , cs_a_soggetto s where S.ANAGRAFICA_ID= a.id and upper(A.CF) = upper(D.CF))"+
	                     " order by d.cf ";
			Query q = em.createNativeQuery(sql, CsIInterventoEsegMastSogg.class);
			 lst = (List<CsIInterventoEsegMastSogg>)q.getResultList();
			 logger.debug("Trovate "+lst.size()+" erogazioni da collegare al caso (esistente)");
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		return lst;
	}

	public void updateSoggettoErogazione(CsIInterventoEsegMastSogg obj) {
		em.merge(obj);
	} 
	
	public int countTipoErogazioniByCF(String tipo, String cf){
		int count = 0;
		if(cf!=null){
			String sql = "select count(distinct se.INT_ESEG_MAST_ID) "
					+ "from cs_i_intervento_eseg_mast_Sogg se, cs_i_intervento_eseg e left join CS_CFG_INT_ESEG_STATO stato on stato.id=E.STATO_ID "
					+ "where SE.INT_ESEG_MAST_ID=E.INTERVENTO_ESEG_MAST_ID "
					+ "and stato.tipo='"+tipo+"' and se.cf='"+cf+"' ";
		
			try{
				Query q = em.createNativeQuery(sql);
				count = ((BigDecimal)q.getSingleResult()).intValue();
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				throw new CarSocialeServiceException(e);
			}
		}
		return count;
	}

	public List<ErogazioneBaseDTO> getListaInterventiErogatiByCF(String cf, Boolean erogatiSenzaIntervento) {
		List<ErogazioneBaseDTO> lst = new ArrayList<ErogazioneBaseDTO>();
		try{
			if(cf!=null){
				String sqlRaggr = "select M.TIPO_INTERVENTO_ID,o.id,TI.DESCRIZIONE, O.NOME, count(distinct m.id)  "+
				" from cs_i_intervento_eseg_mast m, cs_i_intervento_eseg e,Cs_Cfg_Int_Eseg_Stato s, cs_i_intervento_eseg_mast_Sogg se , cs_o_settore sett, cs_o_organizzazione o, cs_c_tipo_intervento ti "+
				" where  o.id = sett.ORGANIZZAZIONE_ID and sett.id = M.SETTORE_EROGANTE_ID "+
				" and ti.id=m.TIPO_INTERVENTO_ID and  m.id=E.INTERVENTO_ESEG_MAST_ID and E.STATO_ID=s.id and s.tipo='E' "+
				" and SE.INT_ESEG_MAST_ID=m.id and upper(SE.CF)='"+cf.toUpperCase()+"' ";
				
				if(erogatiSenzaIntervento!=null && erogatiSenzaIntervento)
					sqlRaggr+=" AND M.intervento_id is null ";
				
				sqlRaggr+=" group by M.TIPO_INTERVENTO_ID, O.ID, TI.DESCRIZIONE, O.NOME"+
				" order by  TI.DESCRIZIONE, o.nome";
		
				Query q1 = em.createNativeQuery(sqlRaggr);
		
				String sqlData = "select max(e.data_esecuzione)   "+
				"from cs_i_intervento_eseg_mast m, cs_i_intervento_eseg e,Cs_Cfg_Int_Eseg_Stato s, cs_i_intervento_eseg_mast_Sogg se, cs_o_settore sett  "+
				"where  m.id=E.INTERVENTO_ESEG_MAST_ID and E.STATO_ID=s.id and s.tipo='E'  and sett.id = M.SETTORE_EROGANTE_ID  "+
				"and SE.INT_ESEG_MAST_ID=m.id and upper(SE.CF)='"+cf.toUpperCase()+"' "+
				"and m.tipo_intervento_id= ? and sett.organizzazione_id = ? ";
				
				if(erogatiSenzaIntervento!=null && erogatiSenzaIntervento)
					sqlData+=" AND M.intervento_id is null ";
				
				Query q2 = em.createNativeQuery(sqlData);
				
				List<Object[]> lstRagg = q1.getResultList();
				for(Object[] r : lstRagg){
					
					ErogazioneBaseDTO e = new ErogazioneBaseDTO();
					e.setTipoIntervento((String)r[2]);
					e.setOrganizzazione((String)r[3]);
					e.setNumero((BigDecimal)r[4]);
					
					if(r[0]!=null && r[1]!=null){
						q2.setParameter(1, r[0]);
						q2.setParameter(2, r[1]);
						
						List<Date> lstDataErog = q2.getResultList();
						Date dataUltimaErogazione = null;
						if(!lstDataErog.isEmpty()) dataUltimaErogazione=lstDataErog.get(0);
						e.setDataUltimaErogazione(dataUltimaErogazione);
					}
					
					lst.add(e);
					
				}
			
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		
		return lst;
	}
	

	
	//inizio SISO-500  
	public CsIInterventoEsegMast getCsIInterventoEsegMastByByInterventoId(Long interventoId) {
		if(interventoId!=null){
			Query q = em.createNamedQuery("CsIInterventoEsegMast.findByInterventoId");
			q.setParameter("interventoId", interventoId);
			List<CsIInterventoEsegMast> elementList = q.getResultList();
			return CollectionsUtils.isEmpty(elementList ) ? null : elementList.get(0);
		}
		return null;
	}
	//fine SISO-500 

	public void eliminaCsIPsByEsegMaster(Long masterId) {
		Query delete = em.createNamedQuery("CsIPs.deleteByEsegMastId");
		delete.setParameter("masterId", masterId);
		delete.executeUpdate();
	}

}
