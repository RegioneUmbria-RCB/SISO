package it.umbriadigitale.soclav.managedbeans;

import java.util.Calendar;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Risposta_RDC_beneficiari;
import it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_Type;
import it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP_Type;
import it.umbriadigitale.soclav.model.Test;
import it.umbriadigitale.soclav.service.interfaccia.IAnagraficaService;
import it.umbriadigitale.soclav.service.interfaccia.ICentriImpiegoService;
import it.umbriadigitale.soclav.service.interfaccia.IGeneralService;
import it.umbriadigitale.soclav.util.HttpRestClient;
import it.umbriadigitale.soclav.util.RdcParameterKey;
import it.umbriadigitale.soclav.util.Token;
import it.umbriadigitale.soclav.wsclient.ClientRdcWS;


@ManagedBean(name = "testBean")
@ViewScoped
public class TestServiziBean extends BaseBean {

	public final Logger logger = Logger.getLogger(this.getClass());

	@ManagedProperty("#{testService}")
	protected IGeneralService testService;

	@ManagedProperty("#{anagraficaService}")
	protected IAnagraficaService anagraficaService;

	@ManagedProperty("#{centriImpiegoServiceImpl}")
	protected ICentriImpiegoService centriPerImpiegoService;

	
	private String selectWS;
	private String cf;
	private String codiceSap;
	private String textResult = null;
	private String codeSap;
	private String token;
	private String login;
	private String ente;
	private String protocolloDomanda;
	
	@PostConstruct
	public void init() {
		logger.debug("Inizializzazione Home Bean : versione " + webAppConfig.getAppConfig().getVersion());
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
		String tokenRead = req.getParameter("token");
		if(tokenRead != null)
			logger.debug("TOKE RICEVUTO DAL RACCORDO...  " + tokenRead );
		
	}
	
	public void opChanged(final AjaxBehaviorEvent event)  {

			this.textResult = "";
			this.cf = null;
			this.codeSap = null;
	}
	
	public Test creaTest() {

		logger.debug("Salvo un item di test");
		
		Test t = new Test();
		t.setNome("TEST."+Calendar.getInstance().getTimeInMillis());

		t = testService.save(t);
		
		logger.debug("Ho salvato l'item di test con id = "+t.getId());
		return t;
	}

	public void chiamaServizioWS() {

		logger.debug("Inizio chiamata al servizio REST");
		
	 
		
		logger.debug("Fine chiamata al servizio REST");
		
	}

	public void eseguiAccesso() {
		
		Token genToken = new Token();
		
		try {
			HashMap<String, String> claimList = new HashMap();
			claimList.put("login", login);
			claimList.put("ente", ente);
			
			String token = genToken.get(claimList);
			
			 FacesContext context = FacesContext.getCurrentInstance();
			    HttpServletRequest origRequest = (HttpServletRequest)context.getExternalContext().getRequest();
			String    contextPath = origRequest.getContextPath();
//				LogonMB logonMB = new LogonMB();
//		 		logonMB.setCurrentUser(login);
			        FacesContext.getCurrentInstance().getExternalContext()
			                .redirect(contextPath  + "/jsp/public/index.jsp?token=" + token);
			    } 
		
			catch (Exception e) {
			        e.printStackTrace();
			    }
			
	}
	public void scaricaDatiWS() {
		try {
		ClientRdcWS clientWS = new ClientRdcWS();
//		RispostaServizioRDCType result = null;
		Risposta_RDC_beneficiari result = null;
		Risposta_verificaEsistenzaSAP_Type esitoOp = null;
		Risposta_richiestaSAP_Type esitoSAP = null;
		switch(this.selectWS) {
			case "1":  //by codice fiscale
				//result = clientWS.estraiNucleoFamiliare("http://10.101.223.35:8280/PdDCentroServiziWebred/PD/SPCRegioneUmbriaTest/SPCNAZIONALE/SPCRDC/ricevi_RDC_by_codiceFiscale",this.username, this.password, this.cf, null);
				result = clientWS.estraiNucleoFamiliare(getGlobalParameter(RdcParameterKey.WS_RICERCA_CF_URL), 
															getGlobalParameter(RdcParameterKey.WS_RICERCA_USR), 
															getGlobalParameter(RdcParameterKey.WS_RICERCA_PWD), 
															cf, 
															null);
				textResult = " esito [" + result.getRisposta_RDC_beneficiari().getEsito().getCodEsito() + "] - messaggio [" + result.getRisposta_RDC_beneficiari().getEsito().getMessaggioErrore() + "] <br />";
				centriPerImpiegoService.aggiornaNucleoFamiliare(result.getRisposta_RDC_beneficiari(),false);
				break;
			case "2":  //by protocollo INPS
				//result =  clientWS.estraiNucleoFamiliare("http://10.101.223.35:8280/PdDCentroServiziWebred/PD/SPCRegioneUmbriaTest/SPCNAZIONALE/SPCRDC/ricevi_RDC_by_codProtocolloInps",this.username, this.password, null, this.getCodeSap());
				result =  clientWS.estraiNucleoFamiliare(getGlobalParameter(RdcParameterKey.WS_RICERCA_NUM_PROT_URL), 
															getGlobalParameter(RdcParameterKey.WS_RICERCA_USR) , 
															getGlobalParameter(RdcParameterKey.WS_RICERCA_PWD),  
															null, 
															this.getProtocolloDomanda());
				
				textResult = " esito [" + result.getRisposta_RDC_beneficiari().getEsito().getCodEsito() + "] - messaggio [" + result.getRisposta_RDC_beneficiari().getEsito().getMessaggioErrore() + "] <br />";
				centriPerImpiegoService.aggiornaNucleoFamiliare(result.getRisposta_RDC_beneficiari(),false);
				break;	
			case "3":  
				//esitoOp =    clientWS.verificaSAP("http://10.101.223.35:8280/PdDCentroServiziWebred/PD/SPCRegioneUmbriaTest/SPCNAZIONALE/SPCServiziSAP/verificaEsistenzaSAP","SAFruitore_ServiziSAP", "12345", this.getCf());
				esitoOp =    clientWS.verificaSAP(getGlobalParameter(RdcParameterKey.WS_SAP_VERIFICA_URL),
													getGlobalParameter(RdcParameterKey.WS_SAP_USR), 
													getGlobalParameter(RdcParameterKey.WS_SAP_PWD),
													this.getCf());
				textResult = " codice SAP [" + esitoOp.getCodiceSAP() + "]  <br />";
				
				break;	
			case "4":  
				//esitoSAP =    clientWS.estraiSAP("http://10.101.223.35:8280/PdDCentroServiziWebred/PD/SPCRegioneUmbriaTest/SPCNAZIONALE/SPCServiziSAP/richiestaSAP","SAFruitore_ServiziSAP", "12345", this.getCodeSap());
				esitoSAP =    clientWS.estraiSAP(getGlobalParameter(RdcParameterKey.WS_SAP_ESTRAZIONE_URL),
													getGlobalParameter(RdcParameterKey.WS_SAP_USR), 
													getGlobalParameter(RdcParameterKey.WS_SAP_PWD), 
													this.getCodeSap());
				textResult = " SAP IN OUTPUT [" + esitoSAP.getSAP() + "]  <br />";
				
				break;	
				
			case "5":  
				HttpRestClient httpClient1 = new HttpRestClient();
				httpClient1.testRest("aabbccddeeffgg", "localhost", 8080, "/Soclav_WEB/rest/AuthService/login");
				break;
		}
	
		}catch(Exception ex) {
			textResult = ex.getMessage();
			ex.printStackTrace();
		}
		
		
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public IAnagraficaService getAnagraficaService() {
		return anagraficaService;
	}


	public void setAnagraficaService(IAnagraficaService anagraficaService) {
		this.anagraficaService = anagraficaService;
	}


	public String getCf() {
		return cf;
	}


	public void setCf(String cf) {
		this.cf = cf;
	}


	public IGeneralService getTestService() {
		return testService;
	}


	public void setTestService(IGeneralService testService) {
		this.testService = testService;
	}


	public String getSelectWS() {
		return selectWS;
	}


	public void setSelectWS(String selectWS) {
		this.selectWS = selectWS;
	}


	public String getCodiceSap() {
		return codiceSap;
	}


	public void setCodiceSap(String codiceSap) {
		this.codiceSap = codiceSap;
	}


	public String getTextResult() {
		return textResult;
	}


	public void setTextResult(String textResult) {
		this.textResult = textResult;
	}


	public String getCodeSap() {
		return codeSap;
	}


	public void setCodeSap(String codeSap) {
		this.codeSap = codeSap;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public ICentriImpiegoService getCentriPerImpiegoService() {
		return centriPerImpiegoService;
	}

	public void setCentriPerImpiegoService(ICentriImpiegoService centriPerImpiegoService) {
		this.centriPerImpiegoService = centriPerImpiegoService;
	}

	public String getProtocolloDomanda() {
		return protocolloDomanda;
	}

	public void setProtocolloDomanda(String protocolloDomanda) {
		this.protocolloDomanda = protocolloDomanda;
	}
}
