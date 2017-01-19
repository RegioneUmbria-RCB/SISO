package it.webred.cs.csa.web.manbean.export;

import it.webred.cs.csa.ejb.dto.EsportazioneDTO;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.osmosit.siso.flussoinps.logic.Cost;
import com.osmosit.siso.flussoinps.logic.XMLFactory;

public class EsportaCasellarioUtils {

	
//	public static List<EsportazioneDTO> createListEsportazioni(List<Object[]> queryResult){
//		List<Object[]> result=  queryResult;
//		List<EsportazioneDTO> listEsportazioni= new ArrayList<EsportazioneDTO>();  
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
//				
////				if(o[5]!=null)
////				e.setBenefSecCittadinanza(((BigDecimal)o[5]).intValue());
//				e.setBenefRegione((String)o[5]);
//				e.setBenefComune((String)o[6]);
//				e.setBenefNazione((String)o[7]);				
//				e.setNumProtDSU((String)o[8]);	
//				if(o[9]!=null)
//				e.setAnnoProtDSU(((BigDecimal)o[9]).intValue());
//				e.setDataDSU((Date)o[10]);
//				e.setCodPrestazione((String)o[11]);
//				e.setDenomPrestazione((String)o[12]);
//				e.setPrestazioneProtocEnte((String)o[13]);
////				e.setSogliaISEE((BigDecimal)o[15]);				
//				
//				BigDecimal m_spesa=(BigDecimal)o[28];
//				BigDecimal m_compart_altre=(BigDecimal)o[29];
//				BigDecimal m_compart_ssn=(BigDecimal)o[30];
//				BigDecimal m_compart_utenti=(BigDecimal)o[31];
//				BigDecimal m_perc_gestita_ente=(BigDecimal)o[32];
//				BigDecimal m_valore_gestita_ente=(BigDecimal)o[33];
//				String m_note_altre_compart=(String)o[34];
//				String m_soggetto_cf=(String)o[36];
//				String m_soggetto_cognome=(String)o[37];
//				String m_soggetto_nome=(String)o[38];
//				
//				if(m_spesa!=null)
//					e.setSpesa(m_spesa);
//				else
//					e.setSpesa((BigDecimal)o[17]);
//				if(m_compart_altre!=null)
//					e.setCompartAltre(m_compart_altre);
//				else
//					e.setCompartAltre((BigDecimal)o[18]);
//				if(m_compart_ssn!=null)
//					e.setCompartSsn(m_compart_ssn);
//				else
//					e.setCompartSsn((BigDecimal)o[19]);
//				if(m_compart_utenti!=null)
//					e.setCompartUtenti(m_compart_utenti);
//				else
//					e.setCompartUtenti((BigDecimal)o[20]);
//				if(m_perc_gestita_ente!=null)
//					e.setPercGestitaEnte(m_perc_gestita_ente);
//				else
//					e.setPercGestitaEnte((BigDecimal)o[21]);
//				if(m_valore_gestita_ente!=null)
//					e.setValoreGestitaEnte(m_valore_gestita_ente);
//				else
//					e.setValoreGestitaEnte((BigDecimal)o[22]);
//				if(m_note_altre_compart!=null)
//					e.setNoteAltreCompart(m_note_altre_compart);
//				else
//					e.setNoteAltreCompart((String)o[27]);
//				if(m_soggetto_cf!=null)
//					e.setSoggettoCodiceFiscale(m_soggetto_cf);
//				else
//					e.setSoggettoCodiceFiscale((String)o[14]);
//				if(m_soggetto_cognome!=null)
//					e.setSoggettoCognome(m_soggetto_cognome);
//				else
//					e.setSoggettoCognome((String)o[15]);
//				if(m_soggetto_nome!=null)
//					e.setSoggettoNome(m_soggetto_nome);
//				else
//					e.setSoggettoNome((String)o[16]);
//					
//				e.setDataEsecuzione((Date)o[23]);
//				e.setEnteOperatoreErogante((String)o[24]);
//				e.setNomeOperatoreErog((String)o[25]);
//				e.setNote((String)o[26]);
//				e.setCarattere((String)o[35]); 
//				listEsportazioni.add(e);
//			} 
//		return listEsportazioni;
//	}
	
	

	public static File esportaCasellario(String XML_PATH, List<EsportazioneDTO> erogDaEsportareList, 
																	String idFlusso,
																	String denomEnte,
																	String codEnte,
																	String cfOperatore,
																	String indirEnte
																	){
		int numErogDaEsportare = erogDaEsportareList.size();
		if(numErogDaEsportare>0){
			XMLFactory xmlFactory= new XMLFactory(new File(XML_PATH));
			
		
			//per ogni beneficiario,ci possono essere più erogazioni
			List<HashMap> listBeneficiariErog= new ArrayList<HashMap>();									
			String cfSoggettoPrec=erogDaEsportareList.get(0).getSoggettoCodiceFiscale();
			//TODO all'inizio inserisco la riga di esportazione, ovvero soggetto beneficiario1 e erogazione1
			HashMap hmBen= insertDatiBeneficiario(erogDaEsportareList.get(0));
			List<HashMap> listPrestBen= new ArrayList<HashMap>();
			HashMap prestBen= insertDatiPrestazione(erogDaEsportareList.get(0));
			
			if(numErogDaEsportare>1){
				for(int i=1; i<numErogDaEsportare; i++){
					if(erogDaEsportareList.get(i).getSoggettoCodiceFiscale().equalsIgnoreCase(
							erogDaEsportareList.get(i-1).getSoggettoCodiceFiscale())){
						//STESSO BENEFICIARIO
						listPrestBen.add(prestBen);
						
						prestBen= insertDatiPrestazione(erogDaEsportareList.get(i));
						
					}else{
						//NUOVO BENEFICIARIO
						
						listPrestBen.add(prestBen);
						hmBen.put("listaPrestazioni", listPrestBen);
						listBeneficiariErog.add(hmBen);	
						
						listPrestBen= new ArrayList<HashMap>();
						hmBen=insertDatiBeneficiario(erogDaEsportareList.get(i));
						prestBen= insertDatiPrestazione(erogDaEsportareList.get(i));
					}		
				}
			}
			
			/* all'ultimo devo aggiungere l'ultimo beneficiario a cui ho aggiunto l'ultima prestazione*/
			listPrestBen.add(prestBen);
			hmBen.put("listaPrestazioni", listPrestBen);
			listBeneficiariErog.add(hmBen);	
			
			xmlFactory.createFlussoXML(idFlusso, denomEnte, codEnte, cfOperatore, indirEnte, listBeneficiariErog);
		
		}
		return new File(XML_PATH);
	}
	
	private static HashMap<Object, Object> insertDatiBeneficiario(EsportazioneDTO erogDaEsportare){		
		HashMap<Object, Object> mappaDatiSogg= new HashMap<Object, Object>();
		if(erogDaEsportare.getSoggettoCodiceFiscale()!=null)
		mappaDatiSogg.put(Cost.BENEFICIARIO_CF,erogDaEsportare.getSoggettoCodiceFiscale());
		if(erogDaEsportare.getBenefRegione()!=null)
		mappaDatiSogg.put(Cost.RESIDENZA_REGIONE, erogDaEsportare.getBenefRegione());
		if(erogDaEsportare.getBenefComune()!=null)
		mappaDatiSogg.put(Cost.RESIDENZA_COMUNE, erogDaEsportare.getBenefComune());
		if(erogDaEsportare.getBenefNazione()!=null)
		mappaDatiSogg.put(Cost.RESIDENZA_NAZIONE, erogDaEsportare.getBenefNazione());
		if(erogDaEsportare.getSoggettoNome()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_NOME,erogDaEsportare.getSoggettoNome());
		if(erogDaEsportare.getSoggettoCognome()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_COGNOME, erogDaEsportare.getSoggettoCognome());
		if(erogDaEsportare.getBenefAnnoNascita()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_ANNONASCITA, Integer.toString(erogDaEsportare.getBenefAnnoNascita()));
		if(erogDaEsportare.getBenefLuogoNascita()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_LUOGONASCITA, erogDaEsportare.getBenefLuogoNascita());
		if(erogDaEsportare.getBenefSesso()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_SESSO,Integer.toString(erogDaEsportare.getBenefSesso()));
		if(erogDaEsportare.getBenefCittadinanza()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_CITTAD_ISO, Integer.toString(erogDaEsportare.getBenefCittadinanza()));
//		if(erogDaEsportare.getBenefSecCittadinanza()!=null)
//		mappaDatiSogg.put(Cost.ANAGRAFICA_SEC_CITTAD_ISO, Integer.toString(erogDaEsportare.getBenefSecCittadinanza()));		
		
		return mappaDatiSogg;
	}
	

	private static HashMap<Object, Object> insertDatiPrestazione(EsportazioneDTO erogDaEsportare){
		HashMap<Object, Object> mappaDatiPrest= new HashMap<Object, Object>();
		DateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat decf = new DecimalFormat();
		/* prestazione periodica */
		//TODO: mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_INIZIO, xmlDateInizio);
		// mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_FINE, erogDaEsportare.getPrestDataFine());
		// mappaDatiPrest.put(Cost.PRESTAZIONE_PERIOD_EROG, erogDaEsportare.getPrestPeriodoErogaz());
		// mappaDatiPrest.put(Cost.PRESTAZIONE_IMPORTO_MENS, erogDaEsportare.getPrestImportoMens());				
		
		/* prestazione occasionale */
		
		if(erogDaEsportare.getNumProtDSU()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_NUMPROT_DSU, erogDaEsportare.getNumProtDSU());
		if(erogDaEsportare.getAnnoProtDSU()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_ANNO_PROT, Integer.toString(erogDaEsportare.getAnnoProtDSU()));
		if(erogDaEsportare.getDataDSU()!=null){			
			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_DSU, datef.format(erogDaEsportare.getDataDSU()));
		}
		if(erogDaEsportare.getCodPrestazione()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_CODICE, erogDaEsportare.getCodPrestazione());
		if(erogDaEsportare.getDenomPrestazione()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_DENOMINAZIONE, erogDaEsportare.getDenomPrestazione());
			
		if(erogDaEsportare.getDataEsecuzione()!=null){		
			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_EROG, datef.format(erogDaEsportare.getDataEsecuzione()));
		}
		if(erogDaEsportare.getSpesa()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_IMPORTO, decf.format(erogDaEsportare.getSpesa()));				
		
		/* dati comuni */	
		if(erogDaEsportare.getCarattere()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_CARATTERE, erogDaEsportare.getCarattere());
		if(erogDaEsportare.getPercGestitaEnte()!=null){
			mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_ENTE, decf.format(erogDaEsportare.getPercGestitaEnte()));
		}
		if(erogDaEsportare.getCompartUtenti()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_UTENTE, decf.format(erogDaEsportare.getCompartUtenti()));
		if(erogDaEsportare.getCompartSsn()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_SSN, decf.format(erogDaEsportare.getCompartSsn()));
						
//		if(erogDaEsportare.getSogliaISEE()!=null)
//		mappaDatiPrest.put(Cost.PRESTAZIONE_SOGLIA_ISEE, decf.format(erogDaEsportare.getSogliaISEE()));	

		if(erogDaEsportare.getPrestazioneProtocEnte()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_PROTOC_ENTE, erogDaEsportare.getPrestazioneProtocEnte());
		
		return mappaDatiPrest;
	}
	
	


}
