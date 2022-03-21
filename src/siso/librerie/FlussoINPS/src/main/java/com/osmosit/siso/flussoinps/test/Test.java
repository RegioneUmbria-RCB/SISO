package com.osmosit.siso.flussoinps.test;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.osmosit.siso.flussoinps.logic.Cost;
import com.osmosit.siso.flussoinps.logic.XMLFactory;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.AnagraficaBeneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiari;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Ente;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.PSAInvioPrestazioniInput;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.PrestazioniBeneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ResidenzaBeneficiario;

public class Test {

	public static void main(String[] args) throws JAXBException{
		XMLFactory xmlFactory= new XMLFactory(new File("C:\\EXPORT_FILE.xml"));
		
		String idFlusso="H501.PSA.INPS.20140423.115235";
		String denomEnte="Comune di Roma";
		String codEnte="H501";
		String cfOperatore="GHBLDE25A42H199X";
		String indirEnte="ROMA-ROMA-00123-via dei cerchi 29";
		 
		HashMap hmBen1= new HashMap<Object, Object>();		
		/*residenza*/
		hmBen1.put(Cost.BENEFICIARIO_CF,"AAABBB34M20H501R");
		hmBen1.put(Cost.RESIDENZA_REGIONE, "8");
		hmBen1.put(Cost.RESIDENZA_COMUNE, "H501");
		hmBen1.put(Cost.RESIDENZA_NAZIONE, "380");
		/*beneficiario*/
		hmBen1.put(Cost.ANAGRAFICA_NOME,"BABABA");
		hmBen1.put(Cost.ANAGRAFICA_COGNOME, "ABBAB");
		hmBen1.put(Cost.ANAGRAFICA_ANNONASCITA, "1925");
		hmBen1.put(Cost.ANAGRAFICA_LUOGONASCITA, "H501");
		hmBen1.put(Cost.ANAGRAFICA_SESSO, "2");
		hmBen1.put(Cost.ANAGRAFICA_CITTAD_ISO, "380");
		hmBen1.put(Cost.ANAGRAFICA_SEC_CITTAD_ISO, "608");		
		/*prestazioni*/
		List<HashMap> listPrestBen1= new ArrayList<HashMap>();
		
		HashMap prest1Ben1= new HashMap<Object, Object>();
		
		
		/* start xml gregorian date  */
		Date dob=null;
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		try {
			dob=df.parse( "2014-01-01" );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GregorianCalendar cal = new GregorianCalendar();

		cal.setTime(dob);
		XMLGregorianCalendar xmlDateInizio=null;
		try {
			xmlDateInizio = DatatypeFactory.newInstance().newXMLGregorianCalendar(
		            cal.get(java.util.GregorianCalendar.YEAR),
		            cal.get(java.util.GregorianCalendar.MONTH) + 1,
		            cal.get(java.util.GregorianCalendar.DAY_OF_MONTH),
		            cal.get(java.util.GregorianCalendar.HOUR_OF_DAY),
		            0,
		            0,
		            0, 0);
			xmlDateInizio.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println(xmlDateInizio);
		/* end xml gregorian date  */
		prest1Ben1.put(Cost.PRESTAZIONE_DATA_INIZIO, xmlDateInizio);
		prest1Ben1.put(Cost.PRESTAZIONE_DATA_FINE, "2014-12-31");
		prest1Ben1.put(Cost.PRESTAZIONE_PERIOD_EROG, "12");
		prest1Ben1.put(Cost.PRESTAZIONE_IMPORTO_MENS, "101.23");
		prest1Ben1.put(Cost.PRESTAZIONE_QUOTA_ENTE, "12.34");
		prest1Ben1.put(Cost.PRESTAZIONE_QUOTA_UTENTE, "20.56");
		prest1Ben1.put(Cost.PRESTAZIONE_QUOTA_SSN, "21.98");
		prest1Ben1.put(Cost.PRESTAZIONE_QUOTA_RICHIESTA, "151.64");
		prest1Ben1.put(Cost.PRESTAZIONE_SOGLIA_ISEE, "170.00");
				
		listPrestBen1.add(prest1Ben1);
		hmBen1.put("listaPrestazioni", listPrestBen1);
		
		HashMap hmBen2= new HashMap<Object, Object>();
		/*residenza*/
		hmBen2.put(Cost.BENEFICIARIO_CF,"CCCDDD34M20H501R");
		hmBen2.put(Cost.RESIDENZA_REGIONE, "8");
		hmBen2.put(Cost.RESIDENZA_COMUNE, "H501");
		hmBen2.put(Cost.RESIDENZA_NAZIONE, "380");
		/*beneficiario*/
		hmBen2.put(Cost.ANAGRAFICA_NOME,"DADADA");
		hmBen2.put(Cost.ANAGRAFICA_COGNOME, "CCCERC");
		hmBen2.put(Cost.ANAGRAFICA_ANNONASCITA, "1970");
		hmBen2.put(Cost.ANAGRAFICA_LUOGONASCITA, "H501");
		hmBen2.put(Cost.ANAGRAFICA_SESSO, "1");
		hmBen2.put(Cost.ANAGRAFICA_CITTAD_ISO, "380");
		hmBen2.put(Cost.ANAGRAFICA_SEC_CITTAD_ISO, "608");		
		/*prestazioni*/
		List<HashMap> listPrestBen2= new ArrayList<HashMap>();
		
		HashMap prest1Ben2= new HashMap<Object, Object>();
		prest1Ben2.put(Cost.PRESTAZIONE_CARATTERE, "2");
		prest1Ben2.put(Cost.PRESTAZIONE_NUMPROT_DSU, "INPS-ISEE-2015-01480722D-00");
		prest1Ben2.put(Cost.PRESTAZIONE_ANNO_PROT, "2105");
		prest1Ben2.put(Cost.PRESTAZIONE_DATA_DSU,"2015-09-23");
	    prest1Ben2.put(Cost.PRESTAZIONE_CODICE, "A3.04");
		prest1Ben2.put(Cost.PRESTAZIONE_DENOMINAZIONE, "Edilizia residenziale pubblica");
		prest1Ben2.put(Cost.PRESTAZIONE_PROTOC_ENTE, "2014-789");
		prest1Ben2.put(Cost.PRESTAZIONE_DATA_EROG, "2015-01-31");
		prest1Ben2.put(Cost.PRESTAZIONE_IMPORTO, "265.23");	
		prest1Ben2.put(Cost.PRESTAZIONE_QUOTA_ENTE, "12.34");
		prest1Ben2.put(Cost.PRESTAZIONE_QUOTA_UTENTE,"20.56");
		prest1Ben2.put(Cost.PRESTAZIONE_QUOTA_SSN, "21.98");			
		prest1Ben2.put(Cost.PRESTAZIONE_QUOTA_RICHIESTA, "151.64");
		prest1Ben2.put(Cost.PRESTAZIONE_SOGLIA_ISEE, "34308.60");				
		
		listPrestBen2.add(prest1Ben2);		
		hmBen2.put("listaPrestazioni", listPrestBen2);
		
		List<HashMap> listBeneficiariErog= new ArrayList<HashMap>();
		listBeneficiariErog.add(hmBen1);
		listBeneficiariErog.add(hmBen2);
		xmlFactory.createFlussoXML(idFlusso, denomEnte, codEnte, cfOperatore, indirEnte, listBeneficiariErog);						
			
			
		
		/* // senza services:
		ObjectFactory factory = new ObjectFactory();
		File file = new File("C:\\Users\\frida.valecchi\\Downloads\\BLASI\\EXPORT_FILE.xml");
	
		String idFlusso="H501.PSA.INPS.20140423.115235";
		
		//ente beneficiari
		Ente ente= factory.createEnte();
		ente.setDenominazioneEnte("Comune di Roma");
		ente.setCodiceEnte("H501");
		ente.setCodiceFiscaleOperatore("GHBLDE25A42H199X");
		ente.setIndirizzoEnte("ROMA-ROMA-00123-via dei cerchi 29");
		
		Beneficiari beneficiari=factory.createBeneficiari();
		List<Beneficiario> listBeneficiario= beneficiari.getBeneficiario();
	
		// BENEFICIARIO 1	PRESTAZIONE PERIODICA
		Beneficiario ben1= factory.createBeneficiario();
		ben1.setCodiceFiscale("AAABBB34M20H501R");
		AnagraficaBeneficiario ana1=factory.createAnagraficaBeneficiario();
		ana1.setNome("BABABA");
		ana1.setCognome("ABBAB");
		ana1.setAnnoNascita("1925");
		ana1.setLuogoNascita("H501");
		ana1.setSesso("2");
		ana1.setCittadinanzaISO("380");
		ana1.setSecondaCittadinanzaISO("608");
		
		ResidenzaBeneficiario resid1= factory.createResidenzaBeneficiario();
		resid1.setRegione("8");
		resid1.setComune("H501");
		resid1.setNazione("380");
		
		List<PrestazioniBeneficiario> listPrestazioniBen1=ben1.getPrestazione();
		PrestazioniBeneficiario prest1= factory.createPrestazioniBeneficiario();
		prest1.setCarattere("1");
		prest1.setNumeroProtocolloDSU("0004740224");
		prest1.setAnnoProtocollo("2013");
		prest1.setDataDSU("2013-09-30");
		prest1.setCodice("A3.04");
		prest1.setDenominazione("Edilizia residenziale pubblica");
		prest1.setProtocolloEnte("123456789");
		prest1.setDataEvento("2013-12-01");
		
		// start xml gregorian date  
		Date dob=null;
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		try {
			dob=df.parse( "2014-01-01" );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GregorianCalendar cal = new GregorianCalendar();

		cal.setTime(dob);
		XMLGregorianCalendar xmlDateInizio=null;
		try {
			xmlDateInizio = DatatypeFactory.newInstance().newXMLGregorianCalendar(
		            cal.get(java.util.GregorianCalendar.YEAR),
		            cal.get(java.util.GregorianCalendar.MONTH) + 1,
		            cal.get(java.util.GregorianCalendar.DAY_OF_MONTH),
		            cal.get(java.util.GregorianCalendar.HOUR_OF_DAY),
		            0,
		            0,
		            0, 0);
			xmlDateInizio.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println(xmlDateInizio);
		// end xml gregorian date  
		
		prest1.setDataInizio(xmlDateInizio);
		prest1.setDataFine("2014-12-31");
		prest1.setPeriodoErogazione("12");
		prest1.setImportoMensile("101.23");
		prest1.setQuotaEnte("12.34");
		prest1.setQuotaUtente("20.56");
		prest1.setQuotaSSN("21.98");
		prest1.setQuotaRichiesta("151.64");
		prest1.setSogliaISEE("170.00");		
		
		listPrestazioniBen1.add(prest1);
		ben1.setAnagrafica(ana1);
		ben1.setResidenza(resid1);
		listBeneficiario.add(ben1);
		
		// BENEFICIARIO 2	PRESTAZIONE OCCASIONALE
		Beneficiario ben2= factory.createBeneficiario();
		ben2.setCodiceFiscale("CCCDDD34M20H501R");
		AnagraficaBeneficiario ana2=factory.createAnagraficaBeneficiario();
		ana2.setNome("DADADA");
		ana2.setCognome("CCCERC");
		ana2.setAnnoNascita("1970");
		ana2.setLuogoNascita("H501");
		ana2.setSesso("1");
		ana2.setCittadinanzaISO("380");
		ana2.setSecondaCittadinanzaISO("608");
		
		ResidenzaBeneficiario resid2= factory.createResidenzaBeneficiario();
		resid2.setRegione("8");
		resid2.setComune("H501");
		resid2.setNazione("380");
		
		List<PrestazioniBeneficiario> listPrestazioniBen2=ben2.getPrestazione();
		PrestazioniBeneficiario prest2= factory.createPrestazioniBeneficiario();
		prest2.setCarattere("2");
		prest2.setNumeroProtocolloDSU("INPS-ISEE-2015-01480722D-00");
		prest2.setAnnoProtocollo("2015");
		prest2.setDataDSU("2015-09-23");
		prest2.setCodice("A3.04");
		prest2.setDenominazione("Edilizia residenziale pubblica");
		prest2.setProtocolloEnte("2014-789");
		prest2.setDataErogazione("2015-01-31");
						
		prest2.setImporto("265.23");
		prest2.setQuotaEnte("12.34");
		prest2.setQuotaUtente("20.56");
		prest2.setQuotaSSN("21.98");
		prest2.setQuotaRichiesta("151.64");
		prest2.setSogliaISEE("34308.60");		
		
		listPrestazioniBen2.add(prest2);
		ben2.setAnagrafica(ana2);
		ben2.setResidenza(resid2);
	
		listBeneficiario.add(ben2);
		

		
        PSAInvioPrestazioniInput psaPrestInput = factory.createPSAInvioPrestazioniInput();
        psaPrestInput.setIdentificazioneFlusso(idFlusso);
        psaPrestInput.setIdentificazioneEnte(ente);
        psaPrestInput.setBeneficiari(beneficiari);
       
        JAXBContext context = JAXBContext.newInstance(PSAInvioPrestazioniInput.class);


        JAXBElement<PSAInvioPrestazioniInput> rootElement = factory.createInps(psaPrestInput);

        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty("jaxb.formatted.output",Boolean.TRUE);

        marshaller.marshal(rootElement, System.out);
        marshaller.marshal(rootElement, file);
        */

	}

}
