package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsIPsExportMast;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class ExportCasellarioDAO extends CarSocialeBaseDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String dataInizioString;
	private String dataFineString;
	
	@SuppressWarnings("unchecked")
	public List<CsIPsExportMast> findFlussiInviatiInPeriodo(long idOperSettore, Date dataInizio, Date dataFine){
//		Query q = em.createNamedQuery("CsIPsExportMast.findPsExportMastInPeriodo");
//		q.setParameter("dataDA", dataInizio);
//		q.setParameter("dataA", dataFine);		
//		return (List<CsIPsExportMast>) q.getResultList();
		
		Query q = em.createNamedQuery("CsIPsExportMast.findPsExportMastInPeriodo");
		//q.setParameter("idOperSettore", idOperSettore);
	    q.setParameter("dataDA", dataInizio);
		q.setParameter("dataA", dataFine);
		return q.getResultList();
		//return null;
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
	
	

	public List<EsportazioneDTO> findErogazDaInviareInPeriodo(ErogazioniSearchCriteria bDto) throws CarSocialeServiceException{
		List<Object[]> result= new ArrayList<Object[]>();
		List<EsportazioneDTO> listEsportazioni= new ArrayList<EsportazioneDTO>();
		ErogazioniQueryBuilder qb = new ErogazioniQueryBuilder(bDto);
		try{
			String sql=qb.createQueryErogazioniDaEportare();				
			Query q = em.createNativeQuery(sql);	
			q.setParameter("dataInizio", bDto.getDataInizio());
			q.setParameter("dataFine", bDto.getDataFine());	
//			q.setParameter("operatoreId", bDto.getOperatoreId());
			q.setParameter("belfioreOperatore", bDto.getOrganizzazioneBelfiore());
			result=(List<Object[]>)q.getResultList();

			listEsportazioni = createListEsportazioni(result);
			 		
		} catch(Throwable e){
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return listEsportazioni;
	}
	
	

	public static List<EsportazioneDTO> createListEsportazioni(List<Object[]> queryResult){
		List<Object[]> result=  queryResult;
		List<EsportazioneDTO> listEsportazioni= new ArrayList<EsportazioneDTO>();  
			
			for(Object[] o : result){
				EsportazioneDTO e= new EsportazioneDTO();
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
				e.setDataDSU((Date)o[10]);
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
				
				//MOD-RL modifica della logica di selezione tra master e detail: 
				//se i dati sono presenti nella detail prendo quelli, sennò li prendo dal master, tranne nel caso dei dati compart
				if(o[17]!=null)		 
					e.setSpesa((BigDecimal)o[17]);
				else
					e.setSpesa(m_spesa); 
				
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
					
				e.setDataEsecuzione((Date)o[23]);
				e.setEnteOperatoreErogante((String)o[24]);
				e.setNomeOperatoreErog((String)o[25]);
				e.setNote((String)o[26]);
				e.setCarattere((String)o[35]);
				e.setInterventoEsegMastId(((BigDecimal)o[39]).longValue());
				e.setInterventoId((BigDecimal)o[40]);
				
				listEsportazioni.add(e);
			} 
		return listEsportazioni;
	}
	

	public CsIPsExportMast saveCsIPsExportMast(CsIPsExportMast nuovoCsIPsExportMast) {
		return em.merge(nuovoCsIPsExportMast);
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
	

} 



