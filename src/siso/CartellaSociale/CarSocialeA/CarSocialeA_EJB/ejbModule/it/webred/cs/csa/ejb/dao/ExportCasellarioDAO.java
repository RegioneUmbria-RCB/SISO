package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTOView;
import it.webred.cs.csa.ejb.queryBuilder.ErogazioniQueryBuilder;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsIPsExport;
import it.webred.cs.data.model.CsIPsExportMast;
import it.webred.cs.data.model.VErogExportHelp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class ExportCasellarioDAO extends CarSocialeBaseDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String dataInizioString;
	private String dataFineString;
	
	@SuppressWarnings("unchecked")
	public List<CsIPsExportMast> findFlussiInviatiInPeriodo(long idOperSettore, String dataInizio, String dataFine){		
		Query q = em.createNamedQuery("CsIPsExportMast.findPsExportMastInPeriodo");
		//q.setParameter("idOperSettore", idOperSettore);
	    q.setParameter("dataDA", dataInizio);
		q.setParameter("dataA", dataFine);
		return q.getResultList();
	}

	public List<EsportazioneDTOView> findErogazDaInviareInPeriodo(ErogazioniSearchCriteria bDto) throws CarSocialeServiceException{
		List<Object[]> result= new ArrayList<Object[]>();
		List<EsportazioneDTOView> listEsportazioni= new ArrayList<EsportazioneDTOView>(); //SISO-538 cambiato il tipo dato in EsportazioneDTOView
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
		try{
			String sql=qb.createQueryErogazioniDaEportare();				
			
			logger.debug("findErogazDaInviareInPeriodo SQL["+sql+"]");
			logger.debug("findErogazDaInviareInPeriodo PARAMS "
						+ "dataInizio["+bDto.getDataInizio()+"] "
						+ "dataFine["+bDto.getDataFine()+"] "
						+ "belfiore["+bDto.getOrganizzazioneBelfiore()+"]");
			
			Query q = em.createNativeQuery(sql);	
			q.setParameter("dataInizio", bDto.getDataInizio());
			q.setParameter("dataFine", bDto.getDataFine());	
//			q.setParameter("operatoreId", bDto.getOperatoreId());
			q.setParameter("belfioreOperatore", bDto.getOrganizzazioneBelfiore());
			result=(List<Object[]>)q.getResultList();

			listEsportazioni = createListEsportazioni(result);
			 		
		} catch(Throwable e){ 
			logger.error(e.getMessage(),e);
		}
		return listEsportazioni;
	}
	
	public List<EsportazioneDTOView> findErogazGiaInviateInPeriodo(ErogazioniSearchCriteria bDto) {
		List<Object[]> result= new ArrayList<Object[]>();
		List<EsportazioneDTOView> listEsportazioni= new ArrayList<EsportazioneDTOView>(); 
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
		try{
			String sql=qb.createQueryErogazioniEsportate();		
			logger.debug("findErogazGiaInviateInPeriodo SQL["+sql+"]");
			
			Query q = em.createNativeQuery(sql);	
			q.setParameter("dataInizio", bDto.getDataInizio());
			q.setParameter("dataFine", bDto.getDataFine());	
//			q.setParameter("operatoreId", bDto.getOperatoreId());
			q.setParameter("belfioreOperatore", bDto.getOrganizzazioneBelfiore());
			result=(List<Object[]>)q.getResultList();

			listEsportazioni = createListEsportazioni(result);
			 		
		} catch(Throwable e){ 
			logger.error(e.getMessage(),e);
		}
		return listEsportazioni;
	}
	

	public List<EsportazioneDTOView> createListEsportazioni(List<Object[]> queryResult){  //SISO-538 cambiato il tipo dato in EsportazioneDTOView
		List<Object[]> result=  queryResult;
		List<EsportazioneDTOView> listEsportazioni= new ArrayList<EsportazioneDTOView>();  //SISO-538 cambiato il tipo dato in EsportazioneDTOView
		HashMap<Long, CsCTipoIntervento> mappaTipiIntervento = new HashMap<Long, CsCTipoIntervento>();
		
		logger.debug("createListEsportazioni: elaborazione in corso di "+result.size()+" elementi");
		
		for(Object[] o : result){
			EsportazioneDTOView e= new EsportazioneDTOView();  //SISO-538
			e.setInterventoEsegId(((BigDecimal)o[0]).longValue());
			if(o[1]!=null)
			e.setBenefAnnoNascita(((BigDecimal)o[1]).intValue());
			e.setBenefLuogoNascita((String)o[2]);
			if(o[3]!=null)
			e.setBenefSesso(((BigDecimal)o[3]).intValue());
			if(o[4]!=null)
			e.setBenefCittadinanza(((BigDecimal)o[4]).intValue());

			e.setBenefRegione((String)o[5]);
			e.setBenefComune((String)o[6]);
			e.setBenefNazione((String)o[7]);				
			e.setNumProtDSU((String)o[8]);	
			if(o[9]!=null)
				e.setAnnoProtDSU(((BigDecimal)o[9]).intValue());
			e.setDataDSU(convertTimestampToDate(o[10]));
			e.setCodPrestazione((String)o[11]);
			e.setDenomPrestazione((String)o[12]);
			if (o[59] != null) {
				e.setPicPrestazione(((BigDecimal) o[59]).intValue() == 1 ? true : false);
			}
			e.setPrestazioneProtocEnte((String)o[13]);
//				e.setSogliaISEE((BigDecimal)o[15]);				
			
			BigDecimal d_spesa=(BigDecimal)o[17];
			BigDecimal d_compart_altre=(BigDecimal)o[18];
			BigDecimal d_compart_ssn=(BigDecimal)o[19];
			BigDecimal d_compart_utenti=(BigDecimal)o[20];
			BigDecimal d_perc_gestita_ente=(BigDecimal)o[21];
			BigDecimal d_valore_gestita_ente=(BigDecimal)o[22];
			
			BigDecimal m_spesa=(BigDecimal)o[28];
			BigDecimal m_compart_altre=(BigDecimal)o[29];
			BigDecimal m_compart_ssn=(BigDecimal)o[30];
			BigDecimal m_compart_utenti=(BigDecimal)o[31];
			BigDecimal m_perc_gestita_ente=(BigDecimal)o[32];
			BigDecimal m_valore_gestita_ente=(BigDecimal)o[33];
			String m_note_altre_compart=(String)o[34];
			
			String carattere = (String)o[35];
			
			String m_soggetto_cf=(String)o[36];
			String m_soggetto_cognome=(String)o[37];
			String m_soggetto_nome=(String)o[38];
			 

			//inizio SISO-538
			e.setSpesaDettaglio(d_spesa);
			e.setSpesaTestata(m_spesa);
			//fine SISO-538
			
			
			if(carattere.equalsIgnoreCase(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO)){
				//In questo caso se il dato è imputato direttamente in testata diventa problematico fare il calcolo: se assegno direttamente il valore di testata alla singola erogazione,
				//vwngono successivamente sommati n-volte i totali - quindi non si può fare
				e.setCompartAltre(d_compart_altre);
				e.setCompartSsn(d_compart_ssn);
				e.setCompartUtenti(d_compart_utenti);
				e.setPercGestitaEnte(d_perc_gestita_ente);
				e.setValoreGestitaEnte(d_valore_gestita_ente);
			}else{
				e.setCompartAltre(m_compart_altre!=null ? m_compart_altre : d_compart_altre);
				e.setCompartSsn(m_compart_ssn!=null ? m_compart_ssn : d_compart_ssn);
				e.setCompartUtenti(m_compart_utenti!=null ? m_compart_utenti : d_compart_utenti);
				e.setPercGestitaEnte(m_perc_gestita_ente!=null ? m_perc_gestita_ente : d_perc_gestita_ente);
				e.setValoreGestitaEnte(m_valore_gestita_ente!=null ? m_valore_gestita_ente : d_valore_gestita_ente);
			}
			
			
			if((String)o[27]!=null)
				e.setNoteAltreCompart((String)o[27]);
			else
				e.setNoteAltreCompart(m_note_altre_compart);
			
			if((String)o[14]!=null)
				e.setSoggettoCodiceFiscale((String)o[14]);
			else
				e.setSoggettoCodiceFiscale(m_soggetto_cf);
			
			if((String)o[15]!=null)
				e.setSoggettoCognome((String)o[15]);
			else
				e.setSoggettoCognome(m_soggetto_cognome);
			
			if((String)o[16]!=null)
				e.setSoggettoNome((String)o[16]);
			else
				e.setSoggettoNome(m_soggetto_nome);
				
			e.setDataEsecuzione( convertTimestampToDate(o[23]) );
			e.setEnteOperatoreErogante((String)o[24]);
			e.setNomeOperatoreErog((String)o[25]);
			e.setNote((String)o[26]);
			e.setCarattere(carattere);
			e.setInterventoEsegMastId(((BigDecimal)o[39]).longValue());
			e.setInterventoId((BigDecimal)o[40]); 
			e.setDataEsecuzioneA(convertTimestampToDate(o[41]));  //SISO-538
			
			//INIZIO SISO-657 - Nuovo tracciato casellario PSA - PS - SINA
			e.setPresenzaProvaMezzi( (BigDecimal)o[42]);  
			e.setPresaInCarico((BigDecimal)o[43]);  
			e.setCategoriaSocialeId((BigDecimal)o[44]);   
			//FINE SISO-657 - Nuovo tracciato casellario PSA - PS - SINA
			
			//SISO-962
			if(o[50]!=null)
			e.setBenefSecCittadinanza(((BigDecimal)o[50]).intValue());
			
			/* SISO-719 */
			e.setCategoriaSocialeDescrizione((String) o[45]);
			if (o[46] != null) {
				e.setIdFlusso((String) o[46]);
			}
			if (o[47] != null) {
				e.setDataEsportazione(convertTimestampToDate(o[47]));
			}
			if (o[48] != null) {
				e.setRevocabile(((BigDecimal) o[48]).intValue() == 1 ? true : false);
			}
			
			//** mod. SISO-886 **//
			if (o[49] != null) {
				e.setIsSinaCollegato(true);
				Boolean sinaValutaDopo = Boolean.getBoolean((String)o[49]);
				e.setIsSinaFlagValutaDopo(sinaValutaDopo!=null ? sinaValutaDopo : false );
			}
			
			Long tipoInterventoId = (o[51]!=null ? ((BigDecimal)o[51]).longValue() : null);
			e.setTipoInterventoId(tipoInterventoId);
			e.setTipoInterventoDesc(o[53]!=null ? (String)o[53] : null);
			
			BigDecimal tipoInterventoCustomId = (o[52]!=null ? (BigDecimal)o[52] : null);
			if(tipoInterventoCustomId!=null)
				e.setTipoInterventoCustom(getTipoIntervento(mappaTipiIntervento,tipoInterventoCustomId.longValue()));
			
			//SISO-806			
			BigDecimal unitaMisura = (o[54]!=null ? (BigDecimal)o[54] : null);
			if(unitaMisura!=null)
				e.setUnitaMisura(unitaMisura.longValueExact());
			
			BigDecimal valQuota = (o[55]!=null ? (BigDecimal)o[55] : null);
			if(valQuota!=null)
				e.setValQuota(valQuota);
			//FINE SISO-806
			
			e.setTipoBeneficiario((String)o[56]);
			e.setFrequenza((String)o[57]);
			e.setDataEvento(convertTimestampToDate(o[58]));
			
			listEsportazioni.add(e);
		}
		
		return listEsportazioni;
	}
	
	private CsCTipoIntervento getTipoIntervento(HashMap<Long, CsCTipoIntervento> mappaTipiIntervento, long id){
		CsCTipoIntervento tipoInt = mappaTipiIntervento.get(id);
		if(tipoInt==null){
			logger.debug("Recupero tipo intervento:"+id);
			 tipoInt = em.find(CsCTipoIntervento.class, id);
			 if(tipoInt!=null) mappaTipiIntervento.put(id, tipoInt);
		}
		return tipoInt;
	}
	
//inizio SISO-538
	private static Date convertTimestampToDate(Object object) {
		if (object!=null) {
			Calendar c = new GregorianCalendar();
			c.setTimeInMillis(((Timestamp)object).getTime());
			return c.getTime();			
		} else {
			return null;
		}

	}
//fine SISO-538

	public CsIPsExportMast saveCsIPsExportMast(CsIPsExportMast nuovoCsIPsExportMast) {
		try{
			return em.merge(nuovoCsIPsExportMast);
		}catch(Throwable t){
			t.printStackTrace();
			throw new CarSocialeServiceException(t);
		}
	}

	public long getProgressivoCsIPsExportMast(String enteTitolare, String flusso, Date dtInsStart, Date dtInsEnd) {
		 
		Query q = em.createNamedQuery("CsIPsExportMast.countProgressivoCsIPsExportMast");
  
		q.setParameter("enteTitolare", enteTitolare );
		q.setParameter("flusso",  flusso );
		 
		q.setParameter("dtInsStart", dtInsStart );
		q.setParameter("dtInsEnd", dtInsEnd  );
		 
		int count = ((Number)q.getSingleResult()).intValue();
		  
		return count;
	}

	//INIZIO SISO-538
	public List<VErogExportHelp> findVErogExportHelp(List<BigDecimal> ids) {
		List<VErogExportHelp> lst;
		
		if (ids==null || ids.size()==0) {
			lst = new ArrayList<VErogExportHelp>();
		} else {
			logger.debug("findVErogExportHelp SIZE["+ids.size()+"] IDS["+ids+"]");
			Query q = em.createNamedQuery("VErogExportHelp.findByIds");
			q.setParameter("ids", ids ); 

			lst = q.getResultList();			
		}

		return lst;
	}

	public List<EsportazioneDTOView> findEsportazioniDTOviewPerIdMaster(ErogazioniSearchCriteria bDto) throws CarSocialeServiceException{
		List<Object[]> result= new ArrayList<Object[]>();
		List<EsportazioneDTOView> listEsportazioni= new ArrayList<EsportazioneDTOView>();  
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
		try{
			String sql=qb.createQueryErogazioniDaEportarePerIdMaster();	 		
			logger.debug("findEsportazioniDTOviewPerIdMaster SQL["+sql+"]");
			logger.debug("findEsportazioniDTOviewPerIdMaster ID_MASTER["+bDto.getIdMaster()+"]");
			
			Query q = em.createNativeQuery(sql);
			q.setParameter("idMaster", bDto.getIdMaster() );	 
			result=(List<Object[]>)q.getResultList();

			listEsportazioni = createListEsportazioni(result);
			 		
		} catch(Throwable e){
			logger.error("Errore findEsportazioniDTOviewPerIdMaster "+e.getMessage(),e);
		}
		return listEsportazioni; 
	}
	


	public List<EsportazioneDTOView> findErogazioniMasterChiusuraInPeriodo(ErogazioniSearchCriteria bDto) throws CarSocialeServiceException{
		List<Object[]> result= new ArrayList<Object[]>();
		List<EsportazioneDTOView> listEsportazioni= new ArrayList<EsportazioneDTOView>();  
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
		try{
			String sql=qb.createQueryErogazioniMasterChiusuraInPeriodo();
			logger.debug("findErogazioniMasterChiusuraInPeriodo SQL["+sql+"]");
			
			Query q = em.createNativeQuery(sql);	
			q.setParameter("dataInizio", bDto.getDataInizio());
			q.setParameter("dataFine", bDto.getDataFine());	
//			q.setParameter("operatoreId", bDto.getOperatoreId());
			q.setParameter("belfioreOperatore", bDto.getOrganizzazioneBelfiore());
			result=(List<Object[]>)q.getResultList();

			listEsportazioni = createListEsportazioni(result);
			 		
		} catch(Throwable e){ 
			logger.error(e.getMessage(),e);
		}
		return listEsportazioni;
	}

	//FINE SISO-538



//INIZIO SISO-524 
	public Boolean verificaErogazioniEsportateByEsegIds( List<Long> ids) {
        Boolean esportate = false;
		try{
	        if (ids!=null && ids.size()>0) {
				Query q = em.createNamedQuery("CsIPsExport.countEsegExportedByIds");
				q.setParameter("csIInterventoEsegIds", ids ); 
	
				Long count = (Long)q.getSingleResult();
				esportate = count.longValue()>0;
			}
		} catch(Throwable e){
			logger.error(e.getMessage(),e);
		}
		return esportate;
	}
//FINE SISO-524 
	
	/*
	 * public List<CsIPsExport> findCsIPsExportByCsIInterventoMastId( Long id) {
	 * Query q = em.createNamedQuery("CsIPsExport.findByCsIInterventoMastId");
	 * q.setParameter("csIInterventoMastId", id ); return q.getResultList(); }
	 */

	// SISO-719
	public void updateCsIPsExportRevocaEsportazioneByInterventoEsegId(Long interventoEsegId) {
		/* L'operazionde di revoca deve estendersi in automatico ad altre erogazioni che condividono
		 * EXPORT_MASTER_ID e XML_ELEMENT_GROUPING con l'erogazione di cui si è chiesta esplicitamente
		 * la revoca. */
		String updateSql =
			  "UPDATE CS_I_PS_EXPORT "
			+ "SET FLAG_ESPORTATO = 0, DT_REVOCA_EXPORT = CURRENT_DATE "
			+ "WHERE (EXPORT_MASTER_ID, XML_ELEMENT_GROUPING) IN ("
			+ "  SELECT EXPORT_MASTER_ID, XML_ELEMENT_GROUPING "
			+ "  FROM CS_I_PS_EXPORT "
			+ "  WHERE INTERVENTO_ESEG_ID = :interventoEsegId "
			+ "    AND FLAG_ESPORTATO = 1)";
		
		Query q = em.createNativeQuery(updateSql);	
		q.setParameter("interventoEsegId", interventoEsegId);
		
		q.executeUpdate();
	}
	
	// SISO-780
	public void updateCsIPsExportRevocaEsportazioneByInterventoEsegMastId(Long esegMastId){
		Query q = em.createNamedQuery("CsIPsExport.revocByEsegMastId");
		q.setParameter("dataRevoca", new Date());
		q.setParameter("esegMastId", esegMastId);
		q.executeUpdate();
	}
	
	//SISO-780
	public List<EsportazioneDTOView> findErogazPeriodicheByMast(ErogazioniSearchCriteria bDto) throws CarSocialeServiceException{
		List<Object[]> result= new ArrayList<Object[]>();
		List<EsportazioneDTOView> listEsportazioni= new ArrayList<EsportazioneDTOView>();  
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
		try{
			String sql=qb.createQueryErogazioniPeriodiche();	 		
			logger.debug("findErogazDaInviarePeriodicheByMast SQL["+sql+"]");
			logger.debug("findErogazDaInviarePeriodicheByMast IDS_MASTER["+bDto.getLstMasterId()+"]");
			
			Query q = em.createNativeQuery(sql);
			q.setParameter("idsMaster", bDto.getLstMasterId() );
			q.setParameter("dataFine", bDto.getDataFine());	
			result=(List<Object[]>)q.getResultList();

			listEsportazioni = createListEsportazioni(result);
			 		
		} catch(Throwable e){
			logger.error(e.getMessage(),e);
		}
		return listEsportazioni; 
	}
	
			public List<CsIPsExport> findCsIPsExportByCsIInterventoMastIdExported( Long id) {
				Query q = em.createNamedQuery("CsIPsExport.findByCsIInterventoMastIdExported");
				q.setParameter("csIInterventoMastId", id ); 
				return q.getResultList();			
			}
			
			//SISO - 884
			public List<CsIPsExport> findCsIPsExportByIntEsegIdAndIntEsegMastId(Long intEsegId, Long intEsegMastId){
				Query q = em.createNamedQuery("CsIPsExport.findByIntEsegIdAndIntEsegMastId");
				q.setParameter("intEsegId", intEsegId );
				q.setParameter("intEsegMastId", intEsegMastId );
				return q.getResultList();
			}
			
			//SISO - 884
			public void deleteCsIPsExportByIntEsegIdAndIntEsegMastId(Long intEsegId, Long intEsegMastId){
				Query q = em.createNamedQuery("CsIPsExport.deleteByIntEsegIdAndIntEsegMastId");
				q.setParameter("intEsegId", intEsegId );
				q.setParameter("intEsegMastId", intEsegMastId );
				q.executeUpdate();
			}
			
			//SISO - 884
			public void deleteCsIPsExportMastById(Long id){
				Query q = em.createNamedQuery("CsIPsExportMast.deleteById");
				q.setParameter("id", id );
				q.executeUpdate();
			}
			
			//SISO - 884
			public List<CsIPsExportMast> findCsIPsExportMastByIds( List<Long> ids) {
				List<CsIPsExportMast> lst;

				if (ids==null || ids.size()==0) {
					lst = new ArrayList<CsIPsExportMast>();
				} else {
					Query q = em.createNamedQuery("CsIPsExportMast.findByIds");
			        q.setParameter("ids", ids ); 
	                lst = q.getResultList();			
				}

				return lst;
			}
		
}
