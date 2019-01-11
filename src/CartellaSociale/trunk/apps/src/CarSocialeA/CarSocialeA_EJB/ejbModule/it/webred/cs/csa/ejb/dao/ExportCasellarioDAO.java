package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.csa.ejb.dto.EsportazioneDTOView;
import it.webred.cs.data.model.CsCfgIntEsegStato;
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
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
	
	
//	public List<EsportazioneDTO> findErogazDaInviareInPeriodo(ErogazioniSearchCriteria bDto) throws CarSocialeServiceException{
//		List<Object[]> result= new ArrayList<Object[]>();
//		List<EsportazioneDTO> listEsportazioni= new ArrayList<EsportazioneDTO>();
//		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
//		try{
//			String sql=qb.createQueryErogazioniDaEportare();				
//			Query q = em.createNativeQuery(sql);	
//			q.setParameter("dataInizio", bDto.getDataInizio());
//			q.setParameter("dataFine", bDto.getDataFine());	
//			q.setParameter("operatoreId", bDto.getOperatoreId());
//			result=(List<Object[]>)q.getResultList();
//			
//			for(Object[] o : result){
//				EsportazioneDTO e= new EsportazioneDTO();
//				e.setIdCsInterventoEseg(((BigDecimal)o[0]).longValue());
//				if(o[1]!=null)
//				e.setBenefAnnoNascita(((BigDecimal)o[1]).intValue());
//				e.setBenefLuogoNascita((String)o[2]);
//				if(o[3]!=null)
//				e.setBenefSesso(((BigDecimal)o[3]).intValue());
//				if(o[4]!=null)
//				e.setBenefCittadinanza(((BigDecimal)o[4]).intValue());
//				if(o[5]!=null)
//				e.setBenefSecCittadinanza(((BigDecimal)o[5]).intValue());
//				e.setBenefRegione((Long)o[6]);
//				e.setBenefComune((Long)o[7]);
//				e.setBenefNazione((Long)o[8]);
//				e.setNumProtDSU((String)o[9]);
//				if(o[10]!=null)
//				e.setAnnoProtDSU(((BigDecimal)o[10]).intValue());
//				e.setDataDSU((Date)o[11]);
//				e.setCodPrestazione((String)o[12]);
//				e.setDenomPrestazione((String)o[13]);				
//				e.setProtDomPrest((String)o[14]);
//				e.setSogliaISEE((BigDecimal)o[15]);				
//				
//				BigDecimal m_spesa=(BigDecimal)o[30];
//				BigDecimal m_compart_altre=(BigDecimal)o[31];
//				BigDecimal m_compart_ssn=(BigDecimal)o[32];
//				BigDecimal m_compart_utenti=(BigDecimal)o[33];
//				BigDecimal m_perc_gestita_ente=(BigDecimal)o[34];
//				BigDecimal m_valore_gestita_ente=(BigDecimal)o[35];
//				String m_note_altre_compart=(String)o[36];
//				String m_soggetto_cf=(String)o[38];
//				String m_soggetto_cognome=(String)o[39];
//				String m_soggetto_nome=(String)o[40];
//				
//				if(m_spesa!=null)
//					e.setSpesa(m_spesa);
//				else
//					e.setSpesa((BigDecimal)o[19]);
//				if(m_compart_altre!=null)
//					e.setCompartAltre(m_compart_altre);
//				else
//					e.setCompartAltre((BigDecimal)o[20]);
//				if(m_compart_ssn!=null)
//					e.setCompartSsn(m_compart_ssn);
//				else
//					e.setCompartSsn((BigDecimal)o[21]);
//				if(m_compart_utenti!=null)
//					e.setCompartUtenti(m_compart_utenti);
//				else
//					e.setCompartUtenti((BigDecimal)o[22]);
//				if(m_perc_gestita_ente!=null)
//					e.setPercGestitaEnte(m_perc_gestita_ente);
//				else
//					e.setPercGestitaEnte((BigDecimal)o[23]);
//				if(m_valore_gestita_ente!=null)
//					e.setValoreGestitaEnte(m_valore_gestita_ente);
//				else
//					e.setValoreGestitaEnte((BigDecimal)o[24]);
//				if(m_note_altre_compart!=null)
//					e.setNoteAltreCompart(m_note_altre_compart);
//				else
//					e.setNoteAltreCompart((String)o[29]);
//				if(m_soggetto_cf!=null)
//					e.setSoggettoCodiceFiscale(m_soggetto_cf);
//				else
//					e.setSoggettoCodiceFiscale((String)o[16]);
//				if(m_soggetto_cognome!=null)
//					e.setSoggettoCognome(m_soggetto_cognome);
//				else
//					e.setSoggettoCognome((String)o[17]);
//				if(m_soggetto_nome!=null)
//					e.setSoggettoNome(m_soggetto_nome);
//				else
//					e.setSoggettoNome((String)o[18]);
//					
//				e.setDataEsecuzione((Date)o[25]);
//				e.setEnteOperatoreErogante((String)o[26]);
//				e.setNomeOperatoreErog((String)o[27]);
//				e.setNote((String)o[28]);
//				e.setCarattere((String)o[37]);
//				listEsportazioni.add(e);
//			}			
//		}catch(Throwable e){
//			logger.error(e.getMessage(),e);
//			e.printStackTrace();
//		}
//		return listEsportazioni;
//	}
	
	

	public List<EsportazioneDTOView> findErogazDaInviareInPeriodo(ErogazioniSearchCriteria bDto) throws CarSocialeServiceException{
		List<Object[]> result= new ArrayList<Object[]>();
		List<EsportazioneDTOView> listEsportazioni= new ArrayList<EsportazioneDTOView>(); //SISO-538 cambiato il tipo dato in EsportazioneDTOView
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
		try{
			String sql=qb.createQueryErogazioniDaEportare();				
			
			logger.debug("findErogazDaInviareInPeriodo SQL["+sql+"]");
			
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
	

	public static List<EsportazioneDTOView> createListEsportazioni(List<Object[]> queryResult){  //SISO-538 cambiato il tipo dato in EsportazioneDTOView
		List<Object[]> result=  queryResult;
		List<EsportazioneDTOView> listEsportazioni= new ArrayList<EsportazioneDTOView>();  //SISO-538 cambiato il tipo dato in EsportazioneDTOView
			
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
			
//				if(o[5]!=null)
//				e.setBenefSecCittadinanza(((BigDecimal)o[5]).intValue());
			e.setBenefRegione((String)o[5]);
			e.setBenefComune((String)o[6]);
			e.setBenefNazione((String)o[7]);				
			e.setNumProtDSU((String)o[8]);	
			if(o[9]!=null)
			e.setAnnoProtDSU(((BigDecimal)o[9]).intValue());
			e.setDataDSU(convertTimestampToDate(o[10]));
			e.setCodPrestazione((String)o[11]);
			e.setDenomPrestazione((String)o[12]);
			e.setPrestazioneProtocEnte((String)o[13]);
//				e.setSogliaISEE((BigDecimal)o[15]);				
			
			BigDecimal m_spesa=(BigDecimal)o[28];
			BigDecimal m_compart_altre=(BigDecimal)o[29];
			BigDecimal m_compart_ssn=(BigDecimal)o[30];
			BigDecimal m_compart_utenti=(BigDecimal)o[31];
			BigDecimal m_perc_gestita_ente=(BigDecimal)o[32];
			BigDecimal m_valore_gestita_ente=(BigDecimal)o[33];
			String m_note_altre_compart=(String)o[34];
			String m_soggetto_cf=(String)o[36];
			String m_soggetto_cognome=(String)o[37];
			String m_soggetto_nome=(String)o[38];
			 

			//inizio SISO-538
			e.setSpesaDettaglio((BigDecimal)o[17]);
			e.setSpesaTestata(m_spesa);
			//fine SISO-538
			
//				if(m_compart_altre!=null)
//					e.setCompartAltre(m_compart_altre);
//				else
//					e.setCompartAltre((BigDecimal)o[18]); 
				e.setCompartAltre((BigDecimal)o[18]);
			
			
//				if(m_compart_ssn!=null)
//					e.setCompartSsn(m_compart_ssn);
//				else
//					e.setCompartSsn((BigDecimal)o[19]);
			e.setCompartSsn((BigDecimal)o[19]);
			
//				if(m_compart_utenti!=null)
//					e.setCompartUtenti(m_compart_utenti);
//				else
//					e.setCompartUtenti((BigDecimal)o[20]);
			e.setCompartUtenti((BigDecimal)o[20]);
			
			
			if((BigDecimal)o[21]!=null)
				e.setPercGestitaEnte((BigDecimal)o[21]);
			else
				e.setPercGestitaEnte(m_perc_gestita_ente);
			
			if((BigDecimal)o[22]!=null)
				e.setValoreGestitaEnte((BigDecimal)o[22]);
			else
				e.setValoreGestitaEnte(m_valore_gestita_ente);
			
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
			e.setCarattere((String)o[35]);
			e.setInterventoEsegMastId(((BigDecimal)o[39]).longValue());
			e.setInterventoId((BigDecimal)o[40]); 
			e.setDataEsecuzioneA(convertTimestampToDate(o[41]));  //SISO-538
			
			//INIZIO SISO-657 - Nuovo tracciato casellario PSA - PS - SINA
			e.setPresenzaProvaMezzi( (BigDecimal)o[42]);  
			e.setPresaInCarico((BigDecimal)o[43]);  
			e.setCategoriaSocialeId((BigDecimal)o[44]);   
			//FINE SISO-657 - Nuovo tracciato casellario PSA - PS - SINA
			
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
			
			
			listEsportazioni.add(e);
		}
		
		
		return listEsportazioni;
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

	public long getProgressivoCsIPsExportMast(String enteErogatore, String flusso, Date dtInsStart, Date dtInsEnd) {
		 
		Query q = em.createNamedQuery("CsIPsExportMast.countProgressivoCsIPsExportMast");
  
		q.setParameter("enteErogatore", enteErogatore );
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
			logger.debug("findVErogExportHelp IDS["+ids+"]");
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
			logger.error(e.getMessage(),e);
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
	public List<CsIPsExport> findCsIPsExportByCsIInterventoEsegIds( List<Long> ids) {
		List<CsIPsExport> lst;

		if (ids==null || ids.size()==0) {
			lst = new ArrayList<CsIPsExport>();
		} else {
			Query q = em.createNamedQuery("CsIPsExport.findByCsIInterventoEsegIds");
	  
			q.setParameter("csIInterventoEsegIds", ids ); 

			lst = q.getResultList();			
		}

		return lst;
	}
//FINE SISO-524 
	
	public List<CsIPsExport> findCsIPsExportByCsIInterventoMastId( Long id) {
		Query q = em.createNamedQuery("CsIPsExport.findByCsIInterventoMastId");
		q.setParameter("csIInterventoMastId", id ); 
		return q.getResultList();			
	}


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
	
}
