package it.webred.SisterClient;
 import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import it.webred.SisterClient.dto.ProvinciaDTO;
import it.webred.SisterClient.dto.VisuraDTO;
import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlFieldSet;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection;


public class VisuraCatastale {

	private static final Logger log = Logger.getLogger("diogene.log");
	
	@Test
	public VisuraDTO visuraCatastale(String cf, String userRich, String pwdRich) throws Exception {
		
	VisuraDTO visura = new VisuraDTO();
		
	HtmlAnchor uscita  =null;
	log.debug("VISURA NAZIONALE - INIZIO ISTANZA WEB CLIENT: " + new Date().getTime());
    final WebClient webClient = new WebClient();
    log.debug("VISURA NAZIONALE - FINE   ISTANZA WEB CLIENT: " + new Date().getTime());
    
    webClient.getOptions().setTimeout(60000);
    webClient.getOptions().setRedirectEnabled(true);
    webClient.getOptions().setJavaScriptEnabled(true);
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setUseInsecureSSL(true);
    
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    CookieManager cookieMan = webClient.getCookieManager();
    cookieMan.setCookiesEnabled(true);
    
    webClient.getOptions().setSSLClientProtocols(new String[]{"TLSv1.2","TLSv1.1","TLSv1"});
	SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
	SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("sister.agenziaentrate.gov.it", 443);
	webClient.getOptions().setSSLClientCipherSuites(sslsocket.getSupportedCipherSuites());
    
    log.debug("VISURA NAZIONALE - INIZIO CHIAMATA PAGINA LOGIN: " + new Date().getTime());
    final HtmlPage page = webClient.getPage("http://sister.agenziaentrate.gov.it/Main/index.jsp");
    log.debug("VISURA NAZIONALE - FINE   CHIAMATA PAGINA LOGIN: " + new Date().getTime());
    
    try {
		
		//Assert.assertEquals("SISTER - Home Page", page.getTitleText());

	    //WebAssert.assertLinkPresentWithText(page, "Adesione ai servizi");
	
	    final HtmlForm form = page.getFormByName("logonForm");

	    
	    

	    final HtmlSubmitInput button = form.getInputByValue("Accedi");
	    final HtmlTextInput textField = form.getInputByName("j_username");
	    
	    final HtmlPasswordInput textField2 = form.getInputByName("j_password");
	    
	   //  https://sister2.agenziaterritorio.it/Servizi/CloseSessions
	    
	    
	    

	    // Change the value of the text field
	    textField.setValueAttribute(userRich);
	    textField2.setValueAttribute(pwdRich);

	    // Now submit the form by clicking the button and get back the second page.
	    log.debug("VISURA NAZIONALE - INIZIO CHIAMATA PAGINA INIZIALE: " + new Date().getTime());
	    final HtmlPage page2 = button.click();
	    log.debug("VISURA NAZIONALE - FINE   CHIAMATA PAGINA INIZIALE: " + new Date().getTime());
	    
	    try{
		    if (page2.getAnchorByText("Esci") != null){	    
		    	uscita = page2.getAnchorByText("Esci");
		    }
	    }catch(Exception e){
	    	log.warn("SISTER: Test Anchor failed!",e);
	    }
	    	
	    boolean errUteConn = false;
	    //errUteConn = (uscita==null);
	    String bodyText = page2.getBody().getTextContent();
	    errUteConn = (uscita == null || bodyText.toLowerCase().indexOf("Utente gia' in sessione sulla stessa o altra postazione".toLowerCase()) > -1);
	    if (errUteConn)
	    	throw new Exception("Utente gia' connesso da altra postazione");
	    	    
	   // WebAssert.assertTextPresent(page2, "Benvenuto");
	   // WebAssert.assertLinkPresentWithText(page2, "Uscita");
	   // WebAssert.assertLinkPresentWithText(page2, "Consultazioni");
	    
	    log.debug("VISURA NAZIONALE - INIZIO CHIAMATA PAGINA INFORMATIVA: " + new Date().getTime());
	    HtmlPage pageInformativa =  webClient.getPage("https://sister.agenziaentrate.gov.it/Visure/Informativa.do?tipo=/T/TM/VCVC_");
	    log.debug("VISURA NAZIONALE - FINE   CHIAMATA PAGINA INFORMATIVA: " + new Date().getTime());
	    
	  /*  HtmlAnchor linkConsultazioni = page2.getAnchorByText("Consultazioni");
	    
	    HtmlPage pageConsultazioni = linkConsultazioni.click();
	    
	    HtmlAnchor linkVisureCatastali = pageConsultazioni.getAnchorByText("Visure catastali");

	    HtmlPage pageInformativa = linkVisureCatastali.click();
	    */ 
	    WebAssert.assertLinkPresentWithText(pageInformativa, "Conferma Lettura");
	    HtmlAnchor linkConfermaLettura = pageInformativa.getAnchorByText("Conferma Lettura");
	    log.debug("VISURA NAZIONALE - INIZIO CHIAMATA PAGINA SEL. UFFICIO PROVINCIALE: " + new Date().getTime());
	    HtmlPage pageSelezioneUfficioProvinciale = linkConfermaLettura.click();
	    log.debug("VISURA NAZIONALE - FINE   CHIAMATA PAGINA SEL. UFFICIO PROVINCIALE: " + new Date().getTime());
	 //   WebAssert.assertTextPresent(pageSelezioneUfficioProvinciale, "Selezione Ufficio provinciale");
	    HtmlSelect comboUfficioProvinciale = (HtmlSelect) pageSelezioneUfficioProvinciale.getElementByName("listacom");
	    
		HtmlOption option = comboUfficioProvinciale.getOptionByValue(" NAZIONALE-IT");
		comboUfficioProvinciale.setSelectedAttribute(option, true);
	    final HtmlForm formUfficioProvinciale = pageSelezioneUfficioProvinciale.getFormByName("DataRichiestaForm");
		HtmlSubmitInput buttonUfficioProvinciale = formUfficioProvinciale.getInputByValue("Applica");
		log.debug("VISURA NAZIONALE - INIZIO CHIAMATA PAGINA RICERCA IN AMBITO NAZIONALE: " + new Date().getTime());
		HtmlPage pageRicercaInAmbitoNazionale  = buttonUfficioProvinciale.click();
		log.debug("VISURA NAZIONALE - FINE   CHIAMATA PAGINA RICERCA IN AMBITO NAZIONALE: " + new Date().getTime());
		//WebAssert.assertTextPresent(pageRicercaInAmbitoNazionale, "Ricerca in ambito nazionale");

		
		 HtmlForm formPersonaFisica = pageRicercaInAmbitoNazionale.getFormByName("RicercaPFForm");
		 HtmlTextInput textInput_cod_fisc_pf = (HtmlTextInput) formPersonaFisica.getInputByName("cod_fisc_pf");
		 HtmlRadioButtonInput radioCodiceFiscale  = formPersonaFisica.getInputByValue("CF_PF");
		 radioCodiceFiscale.setChecked(true);
		 textInput_cod_fisc_pf.setText(cf);
		 
		 HtmlSelect motivoCombo = (HtmlSelect) pageRicercaInAmbitoNazionale.getElementByName("motivoLista");
		 HtmlOption motivoOption = motivoCombo.getOptionByValue("Altri fini istituzionali ");
		 motivoCombo.setSelectedAttribute(motivoOption, true);
		 
		 
		 final HtmlSubmitInput buttonRicercaPerCf = formPersonaFisica.getInputByName("ricerca");
		 log.debug("VISURA NAZIONALE - INIZIO CHIAMATA PAGINA RISULTATO RICERCA: " + new Date().getTime());
		 final HtmlPage pageRisultatoRicerca = buttonRicercaPerCf.click();
		 log.debug("VISURA NAZIONALE - FINE   CHIAMATA PAGINA RISULTATO RICERCA: " + new Date().getTime());
		 
		 
		 //WebAssert.assertTextPresent(pageRisultatoRicerca, "Elenco Omonimi");
		 HtmlForm formSceltaOmonimi = pageRisultatoRicerca.getFormByName("SceltaOmonimiForm");	 
		 final HtmlSubmitInput buttonVisura = formSceltaOmonimi.getInputByName("visura");
		 HtmlRadioButtonInput omonimoSel = (HtmlRadioButtonInput)formSceltaOmonimi.getInputByName("omonimoSelezionato") ;
		 omonimoSel.click();
		 log.debug("VISURA NAZIONALE - INIZIO CHIAMATA PAGINA VISURA: " + new Date().getTime());
		 final HtmlPage pageVisura = buttonVisura.click();
		 log.debug("VISURA NAZIONALE - FINE   CHIAMATA PAGINA VISURA: " + new Date().getTime());
		 
		/* HtmlForm tipoVisuraForm = pageTipoVisura.getFormByName("TipoVisuraForm");	 
		 final HtmlSubmitInput buttonInoltra = tipoVisuraForm.getInputByName("inoltra");
		 final HtmlPage pageVisura = buttonInoltra.click();
		 */
		 String testo = pageVisura.asText();
		 visura.setPage(testo);
		 
		 //CARICO I DATI DEL PANNELLO
		 log.debug("VISURA NAZIONALE - INIZIO CARICAMENTO DATI: " + new Date().getTime());
		 boolean fsTrovato = false;
		 DomNodeList<DomElement> lstFS = pageVisura.getElementsByTagName("fieldset");
		 int i=0;
		 while(i<lstFS.size() && !fsTrovato){
			 
			 HtmlFieldSet fs = (HtmlFieldSet) lstFS.get(i);
			 
			 DomNodeList<HtmlElement> lstLegend = fs.getElementsByTagName("legend");
			 if(lstLegend.size()>0 && lstLegend.get(0).asText().equals("Soggetto selezionato"))
				 fsTrovato = true;
				 
			 if(fsTrovato){
				 DomNodeList<DomNode> lstLab = fs.getChildNodes();
				 for(int j=0; j<lstLab.size(); j++){
					 
					 DomNode nodeInt = lstLab.get(j);
					 if(nodeInt.asText().trim().equals("Tipo richiesta:")){
						 j++;
						 visura.setTipoRichiesta(lstLab.get(j).asText().trim());
					 }
					 if(nodeInt.asText().trim().equals("Cognome:")){
						 j++;
						 visura.setCognome(lstLab.get(j).asText().trim());
					 }
					 if(nodeInt.asText().trim().equals("Nome:")){
						 j++;
						 visura.setNome(lstLab.get(j).asText().trim());
					 }	 
					 if(nodeInt.asText().trim().equals("Data di Nascita:")){
						 j++;
						 visura.setDataNasc(lstLab.get(j).asText().trim());
					 } 
					 if(nodeInt.asText().trim().equals("Comune di Nascita:")){
						 j++;
						 visura.setComuneNasc(lstLab.get(j).asText().trim());
					 } 
					 if(nodeInt.asText().trim().equals("Codice Fiscale:")){
						 j++;
						 visura.setCf(lstLab.get(j).asText().trim());
					 }
					 if(nodeInt.asText().trim().equals("Motivazione:")){
						 j++;
						 visura.setMotivazione(lstLab.get(j).asText().trim());
					 }
					 
				 }
			 }
			 
			 i++;
		 }
		 
		 //CARICO I DATI DELLA TABELLA PROVINCIE
		 HtmlForm formSceltaProvincia = pageVisura.getFormByName("SceltaVisuranazionaleForm");
		 HtmlTable table = (HtmlTable)formSceltaProvincia.getElementsByTagName("table").get(0);
		 List<HtmlTableBody> bodies = table.getBodies();
		 if(bodies.size()>0){
			 HtmlTableBody body = bodies.get(0);
			
			 List<ProvinciaDTO> lst = new ArrayList<ProvinciaDTO>();
			 for(HtmlTableRow row : body.getRows()){
				 ProvinciaDTO prov = new ProvinciaDTO();
				 
				 for(HtmlTableCell td : row.getCells()){
					 
					 String value = td.asText();
					 
					 if(td.getAttribute("headers").equals("provincia"))
							 prov.setProvincia(value);
					 if(td.getAttribute("headers").equals("fabbricati"))
						 prov.setNumFabbricati(value);
					 if(td.getAttribute("headers").equals("terreni"))
						 prov.setNumTerreni(value);
					 
				 }
				 
				 
				 lst.add(prov);
			 }
			 visura.setListaProv(lst);
		 }
			
		 String s = visura.getCognome()+"\n"+visura.getNome()+"\n"+visura.getDataNasc()+"\n";
 		s+= visura.getCf()+"\n"+visura.getComuneNasc()+"\n"+visura.getTipoRichiesta()+"\n";
 		for(ProvinciaDTO prov : visura.getListaProv())
 			s += prov.getProvincia()+","+prov.getNumFabbricati()+","+prov.getNumTerreni()+"\n";
 	
		 System.out.println(s);
		 
		 log.debug(s);
		 log.debug("VISURA NAZIONALE - FINE   CARICAMENTO DATI: " + new Date().getTime());
 		
		
		 
	}catch(Exception e){
		e.printStackTrace();
		log.error(e.getMessage(),e);
		throw e;
	}finally {
		uscita.click();
	    webClient.close();
	}
	
	 return visura;
	
	}
	
	
}
