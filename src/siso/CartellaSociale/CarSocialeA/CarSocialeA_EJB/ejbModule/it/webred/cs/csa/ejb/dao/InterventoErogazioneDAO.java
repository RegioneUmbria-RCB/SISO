package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.ErogazionePrestazioneDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.erogazioni.CompartecipazioneDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneDettaglioSintesiDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SpesaDTO;
import it.webred.cs.csa.ejb.queryBuilder.ErogazioniQueryBuilder;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsIPs;
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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

@Named
public class InterventoErogazioneDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CsIIntervento getInterventoById(Long interventoId) {

		CsIIntervento csIIntervento = em.find(CsIIntervento.class, interventoId);
		return csIIntervento;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsIInterventoEseg> getInterventoEsegByIdMaster(Long masterId, boolean loadDettagli) {
		List<CsIInterventoEseg>listaInterventi;
		try{
			logger.debug("getInterventoEsegByIdMaster idMaster["+ masterId+"] loadDettagli["+loadDettagli+"]");
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
		logger.debug("getInterventoEsegByIdMaster idMaster["+ masterId+"] loadDettagli["+loadDettagli+"] result["+listaInterventi.size()+"]");
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
	@SuppressWarnings("unchecked")
	public List<CsIInterventoEsegMastSogg> getBeneficiariErogazione(String cf) {
		if(cf!=null){
			Query q = em.createNamedQuery("CsIInterventoEsegMastSogg.findBeneficiariByCfSoggetto");
			q.setParameter("cf", cf);
			return (List<CsIInterventoEsegMastSogg>) q.getResultList();
		}
		return null;
	}
	 
	//SISO-972
	@SuppressWarnings("unchecked")
	public List<CsIInterventoEsegMastSogg> getBeneficiariErogazione(String cf, Long attivita, Long progetto) {
		if(!StringUtils.isBlank(cf)){
			logger.debug("getBeneficiariErogazione cf["+cf+"] progetto["+progetto+"] sottocorso["+attivita+"]");
			Query q = em.createNamedQuery("CsIInterventoEsegMastSogg.findBeneficiariByCfAttivitaProgetto");
			q.setParameter("cf", cf);
			q.setParameter("attivitaId", attivita);
			q.setParameter("progettoId", progetto);
			return (List<CsIInterventoEsegMastSogg>) q.getResultList();
		}
		return null;
	}
	//SISO-972 fine
	public void rimuoviBeneficiari(Long masterId){
		try{
		Query q = em.createNativeQuery("DELETE FROM Cs_I_Intervento_Eseg_Mast_Sogg cs where INT_ESEG_MAST_ID= :masterId ");
		q.setParameter("masterId", masterId);
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
		try{
			Query q = em.createNativeQuery("DELETE FROM Cs_I_Intervento_Eseg_Mast cs where ID= :masterId ");
			q.setParameter("masterId", interEsgMastId);
			q.executeUpdate();
			}catch(Throwable e){
				logger.error(e.getMessage(),e);
				throw new CarSocialeServiceException(e);
			}
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
		Date actual = new Date();
		if(intEsegMastNuovo.getCsIPs()!=null){
			if(intEsegMastNuovo.getCsIPs().getId()==null)
				intEsegMastNuovo.getCsIPs().setDtIns(actual);
			else
				intEsegMastNuovo.getCsIPs().setDtMod(actual); 
		}
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
				percGestita = "% quota a carico dell'ente = "+dtoAggregato.getMasterPercGestitaEnte()+" % ";
			else{
				if(dtoAggregato.getTotalePercGest()!=null && dtoAggregato.getTotalePercGest().compareTo(new BigDecimal(0))>0){
					percGestita = "% quota a carico dell'ente = "+dtoAggregato.getTotalePercGest()+" % ";
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
				String params = qb.setParameters(q);
				logger.debug("COUNT LISTA EROGAZIONI PARAMS "+params);
				//if(!bDto.isSearchConRichiesta())
				//	q.setParameter("organizzazioneId", bDto.getOrganizzazioneId());
				return ((BigDecimal) q.getSingleResult()).intValue();
			}else return 0;
		}catch(Throwable e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
	}
	
	public List<ErogazioneMasterDTO> searchListaErogInterventi(ErogazioniSearchCriteria bDto) throws CarSocialeServiceException {
		List<ErogazioneMasterDTO> lista = new ArrayList<ErogazioneMasterDTO>();
		try{
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
		String sql = qb.createQueryListaErogazioni(false);
		if(sql!=null){
			logger.debug("LISTA EROGAZIONI SQL["+sql+"]");
			Query q = em.createNativeQuery(sql);
			//q.setParameter("settoreId", bDto.getSettoreId());
			String params = qb.setParameters(q);
			logger.debug("LISTA EROGAZIONI PARAMS "+params);
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
				 e.setDescCategoriaSociale((String)o[32]);
				
				//SISO-748
				e.setDiarioPaiId(o[33]!=null ? ((BigDecimal)o[33]).longValue() : null);	
				
				//SISO-812
				e.setSettoreSecondoLivello(o[35]!=null ? ((BigDecimal)o[35]).longValue() : null);	
				
				//SISO-1162
				e.setCodicePrestazioneInps((String)o[36]);
				e.setDenominazionePrestazioneInps((String)o[37]);				
				//FINE SISO-1162
				
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
				String params = qb.setParamsListaSoggettiErogazioni(q);
				logger.debug("searchListaMasterIdBySoggetto PARAMS LISTA SOGGETTI EROGAZIONI " + params );
				return  (List<BigDecimal>)q.getResultList();
			}
		}catch(Throwable e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		return null;	
	}

	public CsIInterventoEsegMast getCsIInterventoEsegMastById(Long obj) {
		CsIInterventoEsegMast m = null;
		logger.debug("INIT getCsIInterventoEsegMastById ["+obj+"]");
		if(obj!=null)
			 m = em.find(CsIInterventoEsegMast.class, obj);
		logger.debug("END getCsIInterventoEsegMastById ["+obj+"] ["+m!=null+"]");
		return m;
	}
	
	public CsIInterventoPr getProgettoByMasterId(Long mastId) {
		if(mastId!=null){
			Query q = em.createNamedQuery("CsIInterventoPr.findProgettoByMasterId");
			q.setParameter("masterId", mastId);
			List<CsIInterventoPr> elementList = q.getResultList();
			return CollectionsUtils.isEmpty(elementList ) ? null : elementList.get(0);
		}
		return null;
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
	                     "(select 1 from cs_a_anagrafica a , cs_a_soggetto s "+
	                     "where S.ANAGRAFICA_ID= a.id and upper(A.CF) = upper(D.CF)) "+
	                     " order by d.cf ";
			Query q = em.createNativeQuery(sql, CsIInterventoEsegMastSogg.class);
			 lst = (List<CsIInterventoEsegMastSogg>)q.getResultList();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		return lst;
	}
	

	public List<CsIInterventoEsegMastSogg> findMastSoggAgganciatiCaso() {
		List<CsIInterventoEsegMastSogg> lst = new ArrayList<CsIInterventoEsegMastSogg>();
		try{
			
			String sql = "select d.* from Cs_I_Intervento_Eseg_Mast_Sogg d where d.caso_id is not null "+
	                     "order by d.cf ";
			Query q = em.createNativeQuery(sql, CsIInterventoEsegMastSogg.class);
			 lst = (List<CsIInterventoEsegMastSogg>)q.getResultList();
			 logger.debug("Trovate "+lst.size()+" erogazioni collegate al caso, da verificare");
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
					+ "and stato.tipo= :tipo and se.cf= :cf ";
		
			try{
				Query q = em.createNativeQuery(sql);
				q.setParameter("tipo", tipo);
				q.setParameter("cf", cf);
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
				" and SE.INT_ESEG_MAST_ID=m.id and upper(SE.CF)=upper(:cf) ";
				
				if(erogatiSenzaIntervento!=null && erogatiSenzaIntervento)
					sqlRaggr+=" AND M.intervento_id is null ";
				
				sqlRaggr+=" group by M.TIPO_INTERVENTO_ID, O.ID, TI.DESCRIZIONE, O.NOME"+
				" order by  TI.DESCRIZIONE, o.nome";
		
				Query q1 = em.createNativeQuery(sqlRaggr);
		
				String sqlData = "select max(e.data_esecuzione)   "+
				"from cs_i_intervento_eseg_mast m, cs_i_intervento_eseg e,Cs_Cfg_Int_Eseg_Stato s, cs_i_intervento_eseg_mast_Sogg se, cs_o_settore sett  "+
				"where  m.id=E.INTERVENTO_ESEG_MAST_ID and E.STATO_ID=s.id and s.tipo='E'  and sett.id = M.SETTORE_EROGANTE_ID  "+
				"and SE.INT_ESEG_MAST_ID=m.id and upper(SE.CF)=upper(:cf) "+
				"and m.tipo_intervento_id= :tipoInterventoId and sett.organizzazione_id = :organizzazioneId ";
				
				if(erogatiSenzaIntervento!=null && erogatiSenzaIntervento)
					sqlData+=" AND M.intervento_id is null ";
				
				q1.setParameter("cf", cf);
				List<Object[]> lstRagg = q1.getResultList();
				for(Object[] r : lstRagg){
					
					ErogazioneBaseDTO e = new ErogazioneBaseDTO();
					e.setTipoIntervento((String)r[2]);
					e.setOrganizzazione((String)r[3]);
					e.setNumero((BigDecimal)r[4]);
					
					BigDecimal tipoInterventoId = (BigDecimal)r[0];
					BigDecimal organizzazioneId = (BigDecimal)r[1];
					
					if(tipoInterventoId!=null && organizzazioneId!=null){
						Query q2 = em.createNativeQuery(sqlData);
						q2.setParameter("cf", cf);
						q2.setParameter("tipoInterventoId", tipoInterventoId);
						q2.setParameter("organizzazioneId", organizzazioneId);
						
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
	public CsIInterventoEsegMast getCsIInterventoEsegMastByInterventoId(Long interventoId) {
		if(interventoId!=null){
			Query q = em.createNamedQuery("CsIInterventoEsegMast.findByInterventoId");
			q.setParameter("interventoId", interventoId);
			List<CsIInterventoEsegMast> elementList = q.getResultList();
			return CollectionsUtils.isEmpty(elementList ) ? null : elementList.get(0);
		}
		return null;
	}
	//fine SISO-500 
	public Long getCsIInterventoEsegMastIdByInterventoId(Long interventoId) {
		if(interventoId!=null){
			Query q = em.createNamedQuery("CsIInterventoEsegMast.findIdByInterventoId");
			q.setParameter("interventoId", interventoId);
			List<Long> elementList = q.getResultList();
			return CollectionsUtils.isEmpty(elementList ) ? null : elementList.get(0);
		}
		return null;
	}
	public CsIPs getCsIPsByInterventoId(Long interventoId) {
		if(interventoId!=null){
			Query q = em.createNamedQuery("CsIPs.findByInterventoId");
			q.setParameter("interventoId", interventoId);
			List<CsIPs> elementList = q.getResultList();
			return CollectionsUtils.isEmpty(elementList ) ? null : elementList.get(0);
		}
		return null;
	}
	
	public void eliminaCsIPsByEsegMaster(Long masterId) {
		Query delete = em.createNamedQuery("CsIPs.deleteByEsegMastId");
		delete.setParameter("masterId", masterId);
		delete.executeUpdate();
	}
	//SISO - 884
	public void interventoEsegMastSetNullDiarioPaiId(Long id){
		Query update = em.createNamedQuery("CsIInterventoEsegMast.setNullDiarioPaiId");
		update.setParameter("id", id);
		update.executeUpdate();
	}

	//SISO - 883
	public List<ErogazionePrestazioneDTO> searchListaInterventiByCf(String cf) {
		
		List<ErogazionePrestazioneDTO> lstErogazioni= new ArrayList<ErogazionePrestazioneDTO>();
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(new ErogazioniSearchCriteria());
	      try{
			String sql = qb.createQueryControllaInterventiDuplicatiByCf();
			logger.debug("LISTA EROGAZIONE DUPLICATE SQL: ["+sql+"]");
			Query q = em.createNativeQuery(sql);
			q.setParameter("cf", cf);
	
			List<Object[]> lstObj  = (List<Object[]>) q.getResultList();
			
			if(lstObj!=null ){
				logger.debug("LISTA EROGAZIONE DUPLICATE per cod.fiscale: "+cf+" num.:"+lstObj.size());
				for(Object[] obj : lstObj){
					ErogazionePrestazioneDTO eiDto=new ErogazionePrestazioneDTO();
		            int i = 0;
					BigDecimal mastId=(BigDecimal)obj[i++];
					BigDecimal esegId = (BigDecimal)obj[i++];
					Date dataErog=(Date)obj[i++];
					Date dataErogA=(Date)obj[i++];
					String cF=(String)obj[i++];
					String codPrestazione=(String)obj[i++];
					Date dataDsu=(Date)obj[i++];
					BigDecimal annoProtDsu=(BigDecimal)obj[i++];
					String numProtDsu=(String)obj[i++];
					String prefixProtDsu=(String)obj[i++];
					String progProtDsu=(String)obj[i++];
					BigDecimal organizzazioneId = (BigDecimal)obj[i++];
					
					eiDto.setMastId(mastId!=null ? mastId : new BigDecimal(0));
					eiDto.setEsegId(esegId);
//					eiDto.setDataErog(dataErog!=null ? dataErog : new Date());
					eiDto.setDataErog(dataErog);
//					eiDto.setDataErogA(dataErogA!=null ? dataErogA : new Date());
					eiDto.setDataErogA(dataErogA);
		            eiDto.setCf(cF!=null ? cF : "");
		            eiDto.setCodPrestazione(codPrestazione!=null ? codPrestazione : "");
		            eiDto.setDataDSU(dataDsu!=null ? dataDsu : new Date());
		            eiDto.setAnnoProtDsu(annoProtDsu!=null ? annoProtDsu : new BigDecimal(0));
		            eiDto.setNumProtDsu(numProtDsu!=null ? numProtDsu : "");
		            eiDto.setPrefixProtDsu(prefixProtDsu!=null ? prefixProtDsu : "");
		            eiDto.setProgProtDsu(progProtDsu!=null ? progProtDsu : "");
		            eiDto.setIdEnteTitolare(organizzazioneId);
					
					 lstErogazioni.add(eiDto);
				}
			}
	  	}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new CarSocialeServiceException(e);
		}
		return lstErogazioni;
	}

	//SISO-885 rimozione PROGETTI (CS_I_INTERVENTO_PR,	CS_I_INTERVENTO_PR_FSE, CS_I_INTERVENTO_PR_FSE_SIRU
		public void eliminaProgetto(Long id){
					
			Query deletePrFse = em.createNamedQuery("CsIInterventoPrFse.eliminaProgettoFse");
			deletePrFse.setParameter("id", id);
			deletePrFse.executeUpdate();
			
			Query deletePrFseSiru = em.createNamedQuery("CsIInterventoPrFseSiru.eliminaProgettoFseSiru");
			deletePrFseSiru.setParameter("id", id);
			deletePrFseSiru.executeUpdate();
			
			Query deletePr = em.createNamedQuery("CsIInterventoPr.eliminaProgetto");
			deletePr.setParameter("id", id);
			deletePr.executeUpdate();

		}

		public List<ErogazioneDettaglioSintesiDTO> getSintesiErogazioniByInterventoId(Long masterId) {
			List<ErogazioneDettaglioSintesiDTO> out = new ArrayList<ErogazioneDettaglioSintesiDTO>();
			if(masterId!=null){
				Query q = em.createNamedQuery("CsIInterventoEseg.findSintesiInterventoEsegByInterventoId");
				q.setParameter("interventoId", masterId);
				List<Object[]> lst = q.getResultList();
				for(Object[] o: lst){
					int index = 0;
					Date dataEsecuzione = (Date) o[index++];
					Date dataEsecuzioneA = (Date) o[index++];
					String stato = (String) o[index++];
					
					ErogazioneDettaglioSintesiDTO s = new ErogazioneDettaglioSintesiDTO();
					s.setDataEsecuzione(dataEsecuzione);
					s.setDataEsecuzioneA(dataEsecuzioneA);
					s.setStato(stato);
					out.add(s);
				}		
			}
			return out;
		}

		public boolean verificaUsoArFonte(Long idOrg, Long idFonte) {
			boolean exists = false;
			String sql = 
					"select aro.id organizzazione_id, pr.ff_origine_id "+
			        " from cs_i_intervento_pr pr, cs_o_settore s, cs_o_organizzazione o, ar_o_organizzazione aro "+
			        " where PR.SETTORE_TITOLARE_ID = s.id and s.organizzazione_id = o.id and o.cod_routing = aro.belfiore"; 
			
			logger.debug("verificaUsoArFonte organizzazioneId["+idOrg+"] fonteId["+idFonte+"]");
			if(idOrg!=null && idOrg>0)
				sql+= " and organizzazione_Id = :organizzazioneId";
			if(idFonte!=null)
				sql+= " and ff_origine_Id = :fonteId ";
			
			Query q = em.createNativeQuery(sql);
			
			if(idOrg!=null && idOrg>0)
				q.setParameter("organizzazioneId", idOrg);
			if(idFonte!=null)
				q.setParameter("fonteId", idFonte);
			
			List<Object[]> lst = q.getResultList();
			exists = !lst.isEmpty();
			logger.debug("verificaUsoArFonte organizzazioneId["+idOrg+"] fonteId["+idFonte+"] RES["+exists+"]");
			
			return exists;
		}

	}

