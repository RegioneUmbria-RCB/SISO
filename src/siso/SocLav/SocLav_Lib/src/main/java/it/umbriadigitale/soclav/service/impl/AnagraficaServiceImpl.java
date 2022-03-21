package it.umbriadigitale.soclav.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.umbriadigitale.soclav.model.SlAnagrafica;
import it.umbriadigitale.soclav.repository.AnpalRepository;
import it.umbriadigitale.soclav.service.interfaccia.IAnagraficaService;

@Component("anagraficaService")
public class AnagraficaServiceImpl implements IAnagraficaService {

	@Autowired
	@Qualifier("anpalRepository")
	private AnpalRepository anpalRepository;

	
	@Override
	public SlAnagrafica save(SlAnagrafica t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SlAnagrafica find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	public EsitoOp scaricaAggiornaSAP(String URLWS, String numProtINPS,  String codiceFiscale) {
//		
//		ClientRdcWS clientRdc = new ClientRdcWS();
//	    RdCAnpalBeneficiario anpalBeneficiario =	 anpalRepository.findByCFAndNumProtINPS(codiceFiscale, numProtINPS);
//		
//	    EsitoOp esitoOp = clientRdc.estraiSAP(URLWS, codiceFiscale, anpalBeneficiario.getCodSap());
//		Date dataOp = new java.sql.Date(Calendar.getInstance().getTime().getTime());
//		
//	    RdCAnpalSAP anpalSap = null;
//	    anpalSap = anpalBeneficiario.getSap();
//	    
//	    if(anpalSap == null || anpalSap.getId() == null ) {
//	    	anpalSap = new RdCAnpalSAP();
//	    	 anpalSap.setDtIns(dataOp);
//	  	    		    
// 	    }
//	    else
//	    	 anpalSap.setDtMod(dataOp);
//		   	
//	    anpalSap.setSap(esitoOp.getContenutoSap());
//	    anpalSap.setId(anpalBeneficiario.getCodSap());
//	    
//	    anpalBeneficiario.setSap(anpalSap);
//	    
//		anpalRepository.save(anpalBeneficiario);
//		
//		 return esitoOp;
//	}
//	
//	@Override
//	public EsitoOp scaricaAggiornaNucleoFamiliare(String URLWS) {
//		// Si suppone di caricare 
//		// l'elenco delle anagrafi ricevute da ARPAL
//		ClientRdcWS clientRdc = new ClientRdcWS();
//		
//		EsitoOp esitoOp = new EsitoOp();
//		
//		String cf = null;
//		String numProtINPS = null;
//		List<RdCAnpalBeneficiario> listaBeneficiari = new ArrayList<RdCAnpalBeneficiario>();
//		
//		try {
//			RispostaServizioRDCType  rispostaServizio = clientRdc.estraiNucleoFamiliare(URLWS, cf, numProtINPS);
//			EsitoType esito = null;
//			
//			esito = rispostaServizio.getEsito();
//			
//			esitoOp.setCodEsito(esito.getCodEsito());
//			esitoOp.setMessaggioErrore(esito.getMessaggioErrore());
//			if(esito.equals("OK")) {
//				
//				Beneficiari b = rispostaServizio.getBeneficiari();
//				Date dataOp = new java.sql.Date(Calendar.getInstance().getTime().getTime());
//				b.getBeneficiario().forEach(
//						item->{
//							RdCAnpalBeneficiario beneficiarioAnpal = new RdCAnpalBeneficiario();
//
//							RdCBeneficiarioPK beneficiarioPK = new RdCBeneficiarioPK();
//							beneficiarioPK.setCf(item.getCodFiscale());
//							beneficiarioPK.setProtocolloINPSCod(item.getCodProtocolloInps());
//							beneficiarioAnpal.setId(beneficiarioPK);
//							
//							beneficiarioAnpal.setCfRichiedente(item.getCodFiscaleRichiedente());
//							beneficiarioAnpal.setCittadinanzaCod(item.getCodCittadinanza());
//							beneficiarioAnpal.setCognome(item.getDesCognome());
//							beneficiarioAnpal.setDataDecorrenzaBen(Convertitore.StringToDate(item.getDttRendicontazione()));
//							beneficiarioAnpal.setDataDomanda(Convertitore.StringToDate(item.getDttDomanda()) );
//							beneficiarioAnpal.setDataNascita(Convertitore.StringToDate(item.getDttNascita()));
//							beneficiarioAnpal.setDomicilioCap(item.getCodCapDomicilio());
//							beneficiarioAnpal.setDomicilioComuneCod(item.getCodComuneDomicilio());
//							beneficiarioAnpal.setDomicilioComuneDes(item.getDesComuneDomicilio());
//							beneficiarioAnpal.setDomicilioIndirizzo(item.getDesIndirizzoDomicilio());
//							beneficiarioAnpal.setDtIns(dataOp);
//							beneficiarioAnpal.setDtMod(dataOp);
//							beneficiarioAnpal.setEmail(item.getDesEmail());
//							beneficiarioAnpal.setNascitaLuogoCod(item.getCodComuneNascita());
//							beneficiarioAnpal.setNascitaLuogoDes(item.getDesComuneNascita());
//							beneficiarioAnpal.setNome(item.getDesNome());
//							
//							beneficiarioAnpal.setResidenzaCap(item.getCodCapResidenza());
//							beneficiarioAnpal.setResidenzaComuneCod(item.getCodComuneResidenza());
//							beneficiarioAnpal.setResidenzaComuneDes(item.getDesComuneResidenza());
//							//beneficiarioAnpal.setResidenzaCPICod();
//							beneficiarioAnpal.setResidenzaIndirizzo(item.getDesIndirizzoResidenza());
//							beneficiarioAnpal.setRuolo(item.getCodRuoloBeneficiario());
//							beneficiarioAnpal.setCodSap(item.getCodSap()); 
//						 
//							beneficiarioAnpal.setStatoCod(item.getCodCittadinanza());
//							beneficiarioAnpal.setTelefono(item.getDesTelefono());
//							listaBeneficiari.add(beneficiarioAnpal);
//						});
//				
//				for(RdCAnpalBeneficiario ben : listaBeneficiari) {
//					RdCAnpalBeneficiario rdcAnpal = anpalRepository.findByCFAndNumProtINPS(ben.getId().getCf(), ben.getId().getProtocolloINPSCod());
//				
//					if(rdcAnpal != null && rdcAnpal.getId() != null) {
//						//aggiornamento
//						rdcAnpal.setDtMod(dataOp);
//						rdcAnpal.setCfRichiedente(ben.getCfRichiedente());
//						rdcAnpal.setCittadinanzaCod(ben.getCittadinanzaCod());
//						rdcAnpal.setCodStatoDomandaInps(ben.getCodStatoDomandaInps());
//						rdcAnpal.setCognome(ben.getCognome());
//						rdcAnpal.setDataDecorrenzaBen(ben.getDataDecorrenzaBen());
//						rdcAnpal.setDataDomanda(ben.getDataDomanda());
//						rdcAnpal.setDataNascita(ben.getDataNascita());
//						rdcAnpal.setDomicilioCap(ben.getDomicilioCap());
//						rdcAnpal.setDomicilioComuneCod(ben.getDomicilioComuneCod());
//						rdcAnpal.setDomicilioComuneDes(ben.getDomicilioComuneDes());
//						rdcAnpal.setDomicilioIndirizzo(ben.getDomicilioIndirizzo());
//						rdcAnpal.setEmail(ben.getEmail());
//						rdcAnpal.setNascitaLuogoCod(ben.getNascitaLuogoCod());
//						rdcAnpal.setNascitaLuogoDes(ben.getNascitaLuogoDes());
//						rdcAnpal.setNome(ben.getNome());
//						rdcAnpal.setResidenzaCap(ben.getResidenzaCap());
//						rdcAnpal.setResidenzaComuneCod(ben.getResidenzaComuneCod());
//						rdcAnpal.setResidenzaComuneDes(ben.getResidenzaComuneDes());
//						rdcAnpal.setResidenzaCPICod(ben.getResidenzaCPICod());
//						rdcAnpal.setResidenzaIndirizzo(ben.getResidenzaIndirizzo());
//						rdcAnpal.setRuolo(ben.getRuolo());
//						rdcAnpal.setSap(ben.getSap());
//						rdcAnpal.setSesso(ben.getSesso());
//						rdcAnpal.setStatoCod(ben.getStatoCod());
//						rdcAnpal.setTelefono(ben.getTelefono());
//						rdcAnpal.setCodSap(ben.getCodSap());
//					}else {
//						
//						
//						rdcAnpal.setDtIns(dataOp);
//					
//					
//					}
//					anpalRepository.save(rdcAnpal);
//				}
//			 	
//			}
//		}
//		catch(Exception ex) {
//			esitoOp.setEx(ex);
//		}
//		
//		return esitoOp;
//	}
	
}
