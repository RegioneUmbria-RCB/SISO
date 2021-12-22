package com.osmosit.siso.flussoinps.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import com.osmosit.siso.flussoinps.psa_invioprestazioni.AnagraficaBeneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiari;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Beneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.Ente;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ObjectFactory;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.PSAInvioPrestazioniInput;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.PrestazioniBeneficiario;
import com.osmosit.siso.flussoinps.psa_invioprestazioni.ResidenzaBeneficiario;
import com.osmosit.siso.flussoinps.services.IAnagraficaBeneficiarioService;
import com.osmosit.siso.flussoinps.services.IBeneficiariService;
import com.osmosit.siso.flussoinps.services.IBeneficiarioService;
import com.osmosit.siso.flussoinps.services.IEnteService;
import com.osmosit.siso.flussoinps.services.IObjectFactoryService;
import com.osmosit.siso.flussoinps.services.IPSAInvioPrestazioniService;
import com.osmosit.siso.flussoinps.services.IPrestazioniBeneficiarioService;
import com.osmosit.siso.flussoinps.services.IResidenzaBeneficiarioService;
import com.osmosit.siso.flussoinps.services.impl.AnagraficaBeneficiarioServiceImpl;
import com.osmosit.siso.flussoinps.services.impl.BeneficiariServiceImpl;
import com.osmosit.siso.flussoinps.services.impl.BeneficiarioServiceImpl;
import com.osmosit.siso.flussoinps.services.impl.EnteServiceImpl;
import com.osmosit.siso.flussoinps.services.impl.ObjectFactoryServiceImpl;
import com.osmosit.siso.flussoinps.services.impl.PSAInvioPrestazioniServiceImpl;
import com.osmosit.siso.flussoinps.services.impl.PrestazioniBeneficiarioServiceImpl;
import com.osmosit.siso.flussoinps.services.impl.ResidenzaBeneficiarioServiceImpl;


public class XMLFactory {
	private ObjectFactory objFactory;
	private IObjectFactoryService objectFactoryService;
	private IEnteService enteService;
	private IAnagraficaBeneficiarioService anagraficaBeneficiarioServ;
	private IResidenzaBeneficiarioService residenzaBeneficiarioServ;
	private IPrestazioniBeneficiarioService prestazioniBeneficiarioServ;
	private IBeneficiariService beneficiariServ;
	private IBeneficiarioService beneficiarioServ;
	private IPSAInvioPrestazioniService psaInvioPrestazioniService;
	/*oggetti da agganciare*/
	private Ente ente;
	private AnagraficaBeneficiario anagraficaBeneficiario;
	private ResidenzaBeneficiario residenzaBeneficiario;
	private Beneficiario beneficiario;
	private List<Beneficiario> listBeneficiari;
	private PSAInvioPrestazioniInput psaInvioPrestazioiInput;
	private PrestazioniBeneficiario prestazioneBeneficiario;
	private Beneficiari beneficiari;
	private File file;
	
	
	/*creare tutti i service*/
	public XMLFactory(File file){ 
		this.file=file;
		objectFactoryService= new ObjectFactoryServiceImpl();
		objFactory=objectFactoryService.getObjectFactory();
		
		enteService= new EnteServiceImpl(objFactory);
		anagraficaBeneficiarioServ= new AnagraficaBeneficiarioServiceImpl(objFactory);
		residenzaBeneficiarioServ=new ResidenzaBeneficiarioServiceImpl(objFactory);
		prestazioniBeneficiarioServ= new PrestazioniBeneficiarioServiceImpl(objFactory);
		beneficiariServ= new BeneficiariServiceImpl(objFactory);
		beneficiarioServ= new BeneficiarioServiceImpl(objFactory);
		psaInvioPrestazioniService= new PSAInvioPrestazioniServiceImpl(objFactory);
		
	}
	
	/*chiamare i set dati di tutti i service*/
	public void createFlussoXML(String idFlusso, String denomEnte, String codEnte, String cfOperatore, String indirEnte, List<HashMap> listBeneficiariErog){
		
		ente=enteService.createEnte(denomEnte, codEnte, cfOperatore, indirEnte);
		beneficiari=beneficiariServ.createBeneficiari();
		listBeneficiari=beneficiari.getBeneficiario();
		for (HashMap mapBeneficiario : listBeneficiariErog) {
			residenzaBeneficiario=residenzaBeneficiarioServ.createResidenza(
					(String)mapBeneficiario.get(Cost.RESIDENZA_REGIONE),(String)mapBeneficiario.get(Cost.RESIDENZA_COMUNE), (String)mapBeneficiario.get(Cost.RESIDENZA_NAZIONE));
			anagraficaBeneficiario=anagraficaBeneficiarioServ.createAnagrafica(
					(String)mapBeneficiario.get(Cost.ANAGRAFICA_NOME), (String)mapBeneficiario.get(Cost.ANAGRAFICA_COGNOME), (String)mapBeneficiario.get(Cost.ANAGRAFICA_ANNONASCITA), 
					(String)mapBeneficiario.get(Cost.ANAGRAFICA_LUOGONASCITA), (String)mapBeneficiario.get(Cost.ANAGRAFICA_SESSO), (String)mapBeneficiario.get(Cost.ANAGRAFICA_CITTAD_ISO), (String)mapBeneficiario.get(Cost.ANAGRAFICA_SEC_CITTAD_ISO));			
			beneficiario=beneficiarioServ.createBeneficiario((String)mapBeneficiario.get(Cost.BENEFICIARIO_CF), residenzaBeneficiario, anagraficaBeneficiario);
			
			List<HashMap> listPrestazioni=(List<HashMap>)mapBeneficiario.get("listaPrestazioni");
			
			//xml
			List<PrestazioniBeneficiario> listPrestazioniBen=beneficiario.getPrestazione();
			
			for (HashMap prestazione : listPrestazioni) {
				prestazioneBeneficiario=prestazioniBeneficiarioServ.createPrestazioneBeneficiario((String)prestazione.get(Cost.PRESTAZIONE_DATA_INIZIO), 
						(String)prestazione.get(Cost.PRESTAZIONE_DATA_FINE), (String)prestazione.get(Cost.PRESTAZIONE_PERIOD_EROG), (String)prestazione.get(Cost.PRESTAZIONE_IMPORTO_MENS), 
						(String)prestazione.get(Cost.PRESTAZIONE_QUOTA_ENTE), (String)prestazione.get(Cost.PRESTAZIONE_QUOTA_UTENTE), (String)prestazione.get(Cost.PRESTAZIONE_QUOTA_SSN), 
						(String)prestazione.get(Cost.PRESTAZIONE_QUOTA_RICHIESTA), (String)prestazione.get(Cost.PRESTAZIONE_SOGLIA_ISEE), (String)prestazione.get(Cost.PRESTAZIONE_CARATTERE),
						(String)prestazione.get(Cost.PRESTAZIONE_NUMPROT_DSU), (String)prestazione.get(Cost.PRESTAZIONE_ANNO_PROT), (String)prestazione.get(Cost.PRESTAZIONE_DATA_DSU), 
						(String)prestazione.get(Cost.PRESTAZIONE_CODICE), (String)prestazione.get(Cost.PRESTAZIONE_DENOMINAZIONE), (String)prestazione.get(Cost.PRESTAZIONE_PROTOC_ENTE), 
						(String)prestazione.get(Cost.PRESTAZIONE_DATA_EROG), (String)prestazione.get(Cost.PRESTAZIONE_IMPORTO),
						(String)prestazione.get(Cost.PRESTAZIONE_DATA_EVENTO)	 //INIZIO SISO-538	
						);
							
				listPrestazioniBen.add(prestazioneBeneficiario);
			}
						
			listBeneficiari.add(beneficiario);			
		}
		
	
		psaInvioPrestazioiInput=psaInvioPrestazioniService.cratePSAInvioPrestazioniInput(idFlusso, ente, beneficiari);		
  
        JAXBContext context;
		try {
			context = JAXBContext.newInstance(PSAInvioPrestazioniInput.class);
			
			 JAXBElement<PSAInvioPrestazioniInput> rootElement = objFactory.createInps(psaInvioPrestazioiInput);

		        Marshaller marshaller = context.createMarshaller();

		        marshaller.setProperty("jaxb.formatted.output",Boolean.TRUE);

		        marshaller.marshal(rootElement, System.out);
		        marshaller.marshal(rootElement, file);
		        
		} catch (JAXBException e) {		
			e.printStackTrace();
		}

       
		
	}

	
}
