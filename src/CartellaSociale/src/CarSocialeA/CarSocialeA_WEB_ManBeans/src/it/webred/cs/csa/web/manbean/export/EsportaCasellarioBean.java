package it.webred.cs.csa.web.manbean.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.osmosit.siso.flussoinps.logic.*;

import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIPsExport;
import it.webred.cs.data.model.CsIPsExportMast;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

@ManagedBean
@ViewScoped
public class EsportaCasellarioBean extends CsUiCompBaseBean{ 
	private CsOOperatoreSettore opSettore;
	private Boolean showDlgEsporta=true;
	
	private Boolean showPnlEsporta;
	private Boolean showPnlVisualizza;
	
	private Date dataDA;
	private Date dataA;
	private int numFlussi;
	private int numErogDaEsportare;
	File tempFile;
	String idFlusso;
	
	protected AccessTablePsExportSessionBeanRemote psExportService= (AccessTablePsExportSessionBeanRemote)getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTablePsExportSessionBean");
	
	private List<EsportazioneDTO> erogDaEsportareList;
	
	@PostConstruct
	public void init() {
		dataA=new Date();
		showPnlEsporta=false;
		showPnlVisualizza=false;
		opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore"); 
		
		CsOOrganizzazione csOOrganizzazione = opSettore.getCsOSettore().getCsOOrganizzazione(); 
		System.out.println( csOOrganizzazione.getBelfiore() );
		
	}
	
	public Boolean onBtnVerificaCasellarioClick(){
		findFlussiInviatiInPeriodo();				
		findErogDaEsportare();
		showPnlEsporta=true;
		return showPnlEsporta;
	}
	
	private int findErogDaEsportare(){		
		numErogDaEsportare=0;		
		ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
		CsUiCompBaseBean.fillEnte(bDto);
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		bDto.setDataInizio(formatter.format(dataDA));
		bDto.setDataFine(formatter.format(dataA));
//		bDto.setOperatoreId(opSettore.getId());
		bDto.setOrganizzazioneBelfiore( opSettore.getCsOSettore().getCsOOrganizzazione().getBelfiore());
		erogDaEsportareList = psExportService.findErogazDaInviareInPeriodo(bDto);		
		numErogDaEsportare=erogDaEsportareList.size();
		
		return numErogDaEsportare;
	}
	
	private int findFlussiInviatiInPeriodo(){
		numFlussi=0;		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(opSettore.getId());		
		List<CsIPsExportMast> flussiInviati= psExportService.findFlussiInviatiInPeriodo(dto, dataDA, dataA);
		numFlussi= flussiInviati.size();
		return numFlussi;
	}
	
	public Boolean onBtnVisualizzaErogazClick(){
		showPnlVisualizza=true;
		return showPnlVisualizza;
	}
	
	
	public Boolean onBtnResetClick(){
		showPnlEsporta=false;
		showPnlVisualizza=false;
		numFlussi=0;
		dataDA=null;
		return showPnlEsporta;
	}
	
	private HashMap<Object, Object> insertDatiBeneficiario(EsportazioneDTO erogDaEsportare){		
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
	
	private HashMap<Object, Object> insertDatiPrestazione(EsportazioneDTO erogDaEsportare){
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
		
		return mappaDatiPrest;
	}
	 
    public StreamedContent getFile() throws IOException {
    	onBtnEsportaCasellarioClick();
        InputStream stream = new FileInputStream(tempFile);
        StreamedContent file = new DefaultStreamedContent(stream, "text/xml", idFlusso+".xml");
        return file;
    }
	
	public void onBtnEsportaCasellarioClick() throws IOException{		
		if(numErogDaEsportare>0){
	    	tempFile =  File.createTempFile("EsportaCasellarioBean", "");
			XMLFactory xmlFactory= new XMLFactory(new File(tempFile.getAbsolutePath()));
			

			 
			CsOOperatoreSettore opSettore = getCurrentOpSettore();
			CsOOrganizzazione csOOrganizzazione = opSettore.getCsOSettore().getCsOOrganizzazione();  
			
			String codEnte = csOOrganizzazione.getBelfiore();
			String denomEnte = csOOrganizzazione.getNome();
			String cfOperatore = getCurrentOpSettore().getCsOOperatore().getCsOOperatoreAnagrafica().getCodiceFiscale(); //TODO MOD-RL per ora prendere dalla vista CS_O_OPERATORE_ANAGRAFICA , ma in realtà forse non serve andarlo a leggere in quanto (siamo in attesa di conferma da INPS) per operatore non si intende colui che ha svolto il servizio/intervento ma colui che ha creato e caricato l'xml.  IN SOSPESO
			String indirEnte=""; //TODO DA IGNORARE PER ORA
			Date actualDate = new Date();
			
			//INIZIO MOD-RL - salvo i dati nel db
			long numeroProgressivo = salvaDatiExport(codEnte, denomEnte, indirEnte, cfOperatore, actualDate);
			//FINE MOD-RL 
			idFlusso = creaIdFlusso(codEnte, actualDate, numeroProgressivo);   
		
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
		 					
	}
	
	public Boolean getShowDlgEsporta() {
		return showDlgEsporta;
	}
	public void setShowDlgEsporta(Boolean showDlgEsporta) {
		this.showDlgEsporta = showDlgEsporta;
	}

	public Date getDataDA() {
		return dataDA;
	}

	public void setDataDA(Date dataDA) {
		this.dataDA = dataDA;
	}

	public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public Boolean getShowPnlEsporta() {
		return showPnlEsporta;
	}

	public void setShowPnlEsporta(Boolean showPnlEsporta) {
		this.showPnlEsporta = showPnlEsporta;
	}

	public Boolean getShowPnlVisualizza() {
		return showPnlVisualizza;
	}

	public void setShowPnlVisualizza(Boolean showPnlVisualizza) {
		this.showPnlVisualizza = showPnlVisualizza;
	}

	public int getNumFlussi() {
		return numFlussi;
	}

	public void setNumFlussi(int numFlussi) {
		this.numFlussi = numFlussi;
	}

	public int getNumErogDaEsportare() {
		return numErogDaEsportare;
	}

	public void setNumErogDaEsportare(int numErogDaEsportare) {
		this.numErogDaEsportare = numErogDaEsportare;
	}

	public List<EsportazioneDTO> getErogDaEsportareList() {
		return erogDaEsportareList;
	}

	public void setErogDaEsportareList(List<EsportazioneDTO> erogDaEsportareList) {
		this.erogDaEsportareList = erogDaEsportareList;
	}
	
	
	

	//INIZIO MOD-RL  
	private String creaIdFlusso(String codEnte, Date actualDate, long numeroProgressivo) { 
		String EEEE = codEnte;
		String aaaammgg = new SimpleDateFormat("yyyyMMdd").format(actualDate);
		String nnnnnnn = String.format("%07d", numeroProgressivo);
		
		return EEEE + ".PSA.INPS." + aaaammgg + "." + nnnnnnn;
	}

/**
 * restituisce il progressivo dell'elaborazione
 * @param enteErogatore
 * @param denominazEnte
 * @param indirizzoEnte
 * @param cfOperatore
 * @param actualDate 
 * @throws CarSocialeServiceException
 */
	private long salvaDatiExport(String enteErogatore, String denominazEnte, String indirizzoEnte, String cfOperatore, Date actualDate) throws CarSocialeServiceException { 
		CsIPsExportMast csIPsExportMast = new CsIPsExportMast(); 

		csIPsExportMast.setEnteErogatore(enteErogatore);
		csIPsExportMast.setDenominazEnte(denominazEnte);
		csIPsExportMast.setIndirizzoEnte(indirizzoEnte);
		csIPsExportMast.setCfOperatore(cfOperatore);
		csIPsExportMast.setFlusso("PSA");
		csIPsExportMast.setDataInvio(actualDate);
		BaseDTO progressivoCsIPsExportMastPSA = new BaseDTO();  
		CsUiCompBaseBean.fillEnte(progressivoCsIPsExportMastPSA);
		progressivoCsIPsExportMastPSA.setObj(csIPsExportMast.getEnteErogatore()); 
		csIPsExportMast.setNumProgressivo(psExportService.getProgressivoCsIPsExportMastPSA(progressivoCsIPsExportMastPSA));  
		csIPsExportMast.setDtIns(actualDate);
		csIPsExportMast.setDtMod(actualDate);
		
		BaseDTO dto = new BaseDTO(); 
		CsUiCompBaseBean.fillEnte(dto);
		dto.setObj(csIPsExportMast);
		csIPsExportMast = psExportService.saveCsIPsExportMast(dto);
		
		Set<CsIPsExport> csIPsExportList = new HashSet<CsIPsExport>();
		csIPsExportMast.setCsIPsExportList(csIPsExportList);
		
		for (EsportazioneDTO esportazioneDTO : erogDaEsportareList) {
			CsIPsExport psExport = new CsIPsExport(); 
			
			CsIIntervento csIIntervento = new CsIIntervento();
			if (esportazioneDTO.getInterventoId()!=null) {
				csIIntervento.setId(esportazioneDTO.getInterventoId().longValue());   
				psExport.setCsIIntervento(csIIntervento);
			}
			CsIInterventoEseg csIInterventoEseg = new CsIInterventoEseg();
			csIInterventoEseg.setId(esportazioneDTO.getInterventoEsegId());  
			psExport.setCsIInterventoEseg(csIInterventoEseg); 
			CsIInterventoEsegMast csIInterventoEsegMast = new CsIInterventoEsegMast();
			csIInterventoEsegMast.setId(esportazioneDTO.getInterventoEsegMastId());   
			psExport.setCsIInterventoEsegMast(csIInterventoEsegMast); 
			psExport.setCsIPsExportMast(csIPsExportMast);
			psExport.setDtIns(actualDate);
			psExport.setDtMod(actualDate); 
			
			csIPsExportList.add(psExport);
		} 

		dto.setObj(csIPsExportMast);	
		psExportService.saveCsIPsExportMast(dto);
		
		
		return csIPsExportMast.getNumProgressivo();
 
	}
	

//	private void salvaDatiExportTest(String enteErogatore, String denominazEnte, String indirizzoEnte, String cfOperatore) throws CarSocialeServiceException { 
//
////		EntityManager em=null;
////		
////		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
////		
////		em = entityManagerFactory.createEntityManager();
////	
////		em.getTransaction().begin();
//		 
//		CsIPsExportMast csIPsExportMast = new CsIPsExportMast();
// 
//		Date actualDate = new Date();
//
//		csIPsExportMast.setEnteErogatore("enteErogatore");
//		csIPsExportMast.setDenominazEnte("denominazEnte");
//		csIPsExportMast.setIndirizzoEnte("indirizzoEnte");
//		csIPsExportMast.setCfOperatore("nexus1");
//		csIPsExportMast.setFlusso("PSA");
//		csIPsExportMast.setDataInvio(null);
//		
//		BaseDTO progressivoCsIPsExportMastPSA = new BaseDTO();  
//		CsUiCompBaseBean.fillEnte(progressivoCsIPsExportMastPSA);
//		progressivoCsIPsExportMastPSA.setObj(csIPsExportMast.getEnteErogatore()); 
//		csIPsExportMast.setNumProgressivo(psExportService.getProgressivoCsIPsExportMastPSA(progressivoCsIPsExportMastPSA));   
//		csIPsExportMast.setDtIns(actualDate);
//		csIPsExportMast.setDtMod(actualDate);
//		
//		BaseDTO dto = new BaseDTO(); 
//		CsUiCompBaseBean.fillEnte(dto);
//		dto.setObj(csIPsExportMast);
//		csIPsExportMast = psExportService.saveCsIPsExportMast(dto);
//		
//		Set<CsIPsExport> csIPsExportList = new HashSet<CsIPsExport>();
//		csIPsExportMast.setCsIPsExportList(csIPsExportList);
//		
////		for (EsportazioneDTO esportazioneDTO : erogDaEsportareList) {
//			CsIPsExport psExport = new CsIPsExport();
// 
//			psExport.setCsIIntervento(null); 
//			psExport.setCsIInterventoEseg(null); 
//			CsIInterventoEsegMast csIInterventoEsegMast = new CsIInterventoEsegMast();
//			csIInterventoEsegMast.setId(361870);  
//			psExport.setCsIInterventoEsegMast(csIInterventoEsegMast); 
//			psExport.setCsIPsExportMast(csIPsExportMast); ;
//			psExport.setDtIns(actualDate); ;
//			psExport.setDtMod(actualDate); ; 
//			
//			csIPsExportList.add(psExport);
////		}  
// 
//			dto.setObj(csIPsExportMast);	
//			psExportService.saveCsIPsExportMast(dto);
//	
//	}
	
	//FINE MOD-RL 
	
}
