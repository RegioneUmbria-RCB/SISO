package it.webred.rulengine.brick.loadDwh.load.demografia.saia;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.DOMWriter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesXML;
import it.webred.rulengine.exception.RulEngineException;

public class DemogSaiaXMLFiles<T extends DemogSaiaXMLEnv> extends ImportFilesXML<T> {
	
	private Date dataExport;

	public DemogSaiaXMLFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine)
			throws RulEngineException {

		return null;
	}

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		if (dataExport!=null)
			return new Timestamp(dataExport.getTime());
		else
			return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	/*
	 * (non-Javadoc) Vado a scompattare la fornitura del GAS
	 * 
	 * @see
	 * it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles
	 * #preProcesing(java.sql.Connection)
	 */
	@Override
	public void preProcesing(Connection con) throws RulEngineException {
		// vado a scompattare la fornitura del GAS, se trovo zip

		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(env.createTable1_0);
		} catch (SQLException e1) {
			log.warn("Tabella esiste già : OK , BENE");
			/* TODO SVUOTARE TABELLA TEMP ??? */
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
			}
		}
		try {
			st = con.createStatement();
			st.execute(env.createTablePat1_0);
		} catch (SQLException e1) {
			log.warn("Tabella Patenti esiste già : OK , BENE");
			/* TODO SVUOTARE TABELLA TEMP ??? */
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
			}
		}
		try {
			st = con.createStatement();
			st.execute(env.createTableVei1_0);
		} catch (SQLException e1) {
			log.warn("Tabella Veicoli esiste già : OK , BENE");
			/* TODO SVUOTARE TABELLA TEMP ??? */
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
			}
		}
		try {
			st = con.createStatement();
			st.execute(env.RE_SIT_D_SAIA_IDX);
		} catch (SQLException e1) {
			log.warn("INDICE esiste già : OK , BENE");
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
			}
		}
		try {
			st = con.createStatement();
			st.execute(env.RE_SIT_D_SAIA_PATENTI_IDX);
		} catch (SQLException e1) {
			log.warn("INDICE Patenti esiste già : OK , BENE");
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
			}
		}
		try {
			st = con.createStatement();
			st.execute(env.RE_SIT_D_SAIA_VEICOLI_IDX);
		} catch (SQLException e1) {
			log.warn("INDICE Veicoli esiste già : OK , BENE");
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
			}
		}

	}

	@Override
	public void sortFiles(List<String> files) throws RulEngineException {
		Collections.sort(files);

	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		return "DEMOGRAFIA_SAIA";
	}

	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		return false;
	}

	@Override
	protected void preProcesingFile(String file) throws RulEngineException {
		dataExport = estraiDataFornitura(file,null,null);
	}
	

	@Override
	public String getNodeName() throws RulEngineException {
		return "/richiestaSAIA/richiesta/comunicazione";
	}

	@Override
	public String getSubNode() throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSubNodeOneValue() throws RulEngineException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getNodeToExclude() throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postElaborazione(String file, List<String> fileDaElaborare,
			String cartellaFiles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean elabPersonalizzata() throws RulEngineException {
		return true;
	}
	
	

	   
	@Override
	public void elabCurrentNode(org.dom4j.Element el, HashMap<String, String> hmCampi, 
			String tempTable, Timestamp dataExport, boolean lettoQualcosa, List<String> errori)
			throws RulEngineException {

		
		org.w3c.dom.Element element;
		try {
			element = this.convertDom4jElToW3CEl(el);
		} catch (DocumentException e1) {
			log.error("Errore di inserimento record", e1);
			throw new RulEngineException("Errore di inserimento record SAIA");
		}
		
		String key = "dataOraRichiesta";
		String value = element.getAttribute("dataOraRichiesta");
		String keyFormatted = underscoreFormat(key);
		hmCampi.put(keyFormatted, value);
		
		key = "protocolloAnagrafe";
		value = element.getAttribute("protocolloAnagrafe");
		keyFormatted = underscoreFormat(key);
		hmCampi.put(keyFormatted, value);

		elabGeneralita(element, hmCampi);
		elabGeneralitaINA(element, hmCampi);
		elabIscrizioneAPR(element, hmCampi);
		elabCancellazioneAPR(element, hmCampi);
		elabIndirizzo(element, hmCampi);
		elabIndirizzoEmigrazione(element, hmCampi);
		elabFamiglia(element, hmCampi);
		elabPermessoSoggiorno(element, hmCampi);
		elabConiuge(element, hmCampi);
		elabMatrimonio(element, hmCampi);
		elabCessazioneAnnullMatrimonio(element, hmCampi);
		elabDivorzio(element, hmCampi);
		elabVedovanza(element, hmCampi);
		elabCartaidentita(element, hmCampi);
		elabMorte(element, hmCampi);
		elabNuovoNomeCognome(element, hmCampi);
		elabNuovoSesso(element, hmCampi);
		elabNuovaCittadinanza(element, hmCampi);
		elabNuovoPermSoggiorno(element, hmCampi);
		elabNuovoPaterMater(element, hmCampi);
		elabDatiRettificati(element, hmCampi);
		/** possono essere + di una **/
		List<HashMap<String, String>> listaPatenti = elabPatenti(element, hmCampi);
		List<HashMap<String, String>> listaVeicoli = elabVeicoli(element, hmCampi);
		/***/
		
		try {
			insertInTmp(hmCampi, tempTable, dataExport, errori);
			for(HashMap<String, String> hmPatente: listaPatenti){
				insertInTmp(hmPatente, env.RE_SIT_D_SAIA_PATENTI_1_0, dataExport, errori);
			}
			for(HashMap<String, String> hmVeicolo: listaVeicoli){
				insertInTmp(hmVeicolo, env.RE_SIT_D_SAIA_VEICOLI_1_0, dataExport, errori);
			}
		} catch (Exception e) {
			log.error("Errore di inserimento record", e);
			throw new RulEngineException("Errore di inserimento record SAIA");

		} 
	}

	@Override
	protected Map<String, String> getNamespaces() {
		Map<String, String> m = new HashMap<String, String>();
		m.put( "ap5", "http://saia.ancitel.it/xmlmanager/ap5" );
		return m ;
		
	}
	

	private void elabGeneralita(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {

		addNode(element, hmCampi, "tipoComunicazione");
		addNode(element, hmCampi, "motivoComunicazione");
		addNode(element, hmCampi, "entiDestinatari");
		addNode(element, hmCampi, "codiceFiscale");
		addNode(element, hmCampi, "codiceFiscaleValidato", "flagValiditaCodiceFiscale");
		addNode(element, hmCampi, "matricolaAnagrafica");
		addNode(element, hmCampi, "cognome");
		addNode(element, hmCampi, "nome");
		addNode(element, hmCampi, "cognomePadre");
		addNode(element, hmCampi, "nomePadre");
		addNode(element, hmCampi, "codiceFiscalePadre");
		addNode(element, hmCampi, "cognomeMadre");
		addNode(element, hmCampi, "nomeMadre");
		addNode(element, hmCampi, "codiceFiscaleMadre");
		
		NodeList nodeList = element.getElementsByTagName("ap5:luogoNascita");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneNascita");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatNascita");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatNascita");
			addNode(attributeNode, hmCampi, "descrizioneLocalita", "descrizioneLocalitaNascita");
			addNode(attributeNode, hmCampi, "descrizioneStato", "descrizioneStatoNascita");
			addNode(attributeNode, hmCampi, "codiceStato", "codiceStatoNascita");
		}
		
		nodeList = element.getElementsByTagName("ap5:attoNascita");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneRegNascita");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatRegNasc");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatRegNasc");
			addNode(attributeNode, hmCampi, "atto");
			addNode(attributeNode, hmCampi, "ufficio", "ufficioAtto");
			addNode(attributeNode, hmCampi, "parte", "parteAtto");
			addNode(attributeNode, hmCampi, "serie", "serieAtto");
			addNode(attributeNode, hmCampi, "numero", "numeroAtto");
			addNode(attributeNode, hmCampi, "frazione", "frazioneAtto");
			addNode(attributeNode, hmCampi, "tipoRegistro", "tipoRegistroAtto");
			addNode(attributeNode, hmCampi, "numeroRegistro", "numeroRegistroAtto");
		}
		
		addNode(element, hmCampi, "dataNascita");
		nodeList = element.getElementsByTagName("ap5:cittadinanza");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "descrizioneStato", "descrizioneStatoCitt");
			addNode(attributeNode, hmCampi, "codiceStato", "codiceStatoCitt");
			addNode(attributeNode, hmCampi, "dataValidita", "dataValiditaCitt");
		}
		addNode(element, hmCampi, "sesso");
		addNode(element, hmCampi, "statoCivile");
		addNode(element, hmCampi, "flagSenzaFissaDimora");
		
	}
	
	private void elabGeneralitaINA(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {

		NodeList nodeList = element.getElementsByTagName("ap5:generalitaINA");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "progressivoUtente", "progUtenteIna");
			addNode(attributeNode, hmCampi, "idFilePopolamento", "idFilePopolamentoIna");
			addNode(attributeNode, hmCampi, "idPersonaPopolamento", "idPersonaPopolamentoIna");
			addNode(attributeNode, hmCampi, "residente", "residenteIna");
			addNode(attributeNode, hmCampi, "emigratoEstero", "emigratoEsteroIna");
			addNode(attributeNode, hmCampi, "deceduto", "decedutoIna");
			addNode(attributeNode, hmCampi, "cittadinanza", "cittadinanzaIna");
			addNode(attributeNode, hmCampi, "codiceIstatSezioneCensimento", "codIstatSezCensimentoIna");
		}
		
	}
	
	private void elabIscrizioneAPR(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		addNode(element, hmCampi, "motivoIscrizione");
		addNode(element, hmCampi, "descrizioneMotivoIscrizione");
		addNode(element, hmCampi, "dataIscrizioneAPR", "dataIscrizioneApr");
		addNode(element, hmCampi, "numeroPraticaIscrizione");
		addNode(element, hmCampi, "dataDefinizionePraticaIscrizione", "dataDefinizionePraticaIscr");
		NodeList nodeList = element.getElementsByTagName("ap5:luogoImmigrazione");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneImm");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatImm");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatImm");
			addNode(attributeNode, hmCampi, "descrizioneLocalita", "descrizioneLocalitaImm");
			addNode(attributeNode, hmCampi, "descrizioneStato", "descrizioneStatoImm");
			addNode(attributeNode, hmCampi, "codiceStato", "codiceStatoImm");
		}
		addNode(element, hmCampi, "flagResidenteDallaNascita");
		addNode(element, hmCampi, "numeroFamiglia");
		
	}
	
	private void elabCancellazioneAPR(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		addNode(element, hmCampi, "motivoCancellazione");
		addNode(element, hmCampi, "descrizioneMotivoCancellazione", "descrizioneMotivoCanc");
		addNode(element, hmCampi, "dataCancellazioneAPR", "dataCancellazioneApr");
		addNode(element, hmCampi, "numeroPraticaCancellazione");
		addNode(element, hmCampi, "dataDefinizionePraticaCancellazione", "dataDefinizionePraticaCanc");
		NodeList nodeList = element.getElementsByTagName("ap5:luogoEmigrazione");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneEmig");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatEmig");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatEmig");
			addNode(attributeNode, hmCampi, "descrizioneLocalita", "descrizioneLocalitaEmig");
			addNode(attributeNode, hmCampi, "descrizioneStato", "descrizioneStatoEmig");
			addNode(attributeNode, hmCampi, "codiceStato", "codiceStatoEmig");
		}
		
	}
	
	private void elabIndirizzo(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		NodeList nodeList = element.getElementsByTagName("ap5:indirizzo");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(element, hmCampi, "cap");
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneIndirizzo");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatIndirizzo");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatIndir");
			addNode(element, hmCampi, "frazione");
			addNode(element, hmCampi, "specie");
			addNode(element, hmCampi, "denominazione");
			addNode(element, hmCampi, "codiceVia");
			addNode(element, hmCampi, "numero");
			addNode(element, hmCampi, "lettera");
			addNode(element, hmCampi, "corte");
			addNode(element, hmCampi, "scala");
			addNode(element, hmCampi, "interno");
			addNode(element, hmCampi, "dataDecorrenzaIndirizzo");
		}
		
	}
	
	private void elabIndirizzoEmigrazione(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		NodeList nodeList = element.getElementsByTagName("ap5:indirizzoEmigrazione");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(element, hmCampi, "cap");
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneIndirizzo");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatIndirizzo");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatIndirizzo");
			addNode(element, hmCampi, "frazione");
			addNode(element, hmCampi, "specie");
			addNode(element, hmCampi, "denominazione");
			addNode(element, hmCampi, "codiceVia");
			addNode(element, hmCampi, "numero");
			addNode(element, hmCampi, "lettera");
			addNode(element, hmCampi, "corte");
			addNode(element, hmCampi, "scala");
			addNode(element, hmCampi, "interno");
			addNode(element, hmCampi, "dataDecorrenzaIndirizzo");
		}
		
	}
	
	private void elabFamiglia(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		addNode(element, hmCampi, "codiceFamiglia");
		addNode(element, hmCampi, "dataOrigineFamiglia");
		addNode(element, hmCampi, "dataDecorrenzaAppartenenzaFamiglia", "dataDecorrenzaAppartenenFam");
		addNode(element, hmCampi, "codiceRelazioneParentela");
		
	}
	
	private void elabPermessoSoggiorno(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		addNode(element, hmCampi, "numeroSoggiorno");
		addNode(element, hmCampi, "tipoSoggiorno");
		addNode(element, hmCampi, "dataRilascio", "dataRilascioSoggiorno");
		addNode(element, hmCampi, "dataScadenza", "dataScadenzaSoggiorno");
		NodeList nodeList = element.getElementsByTagName("ap5:questuraRilascioSoggiorno");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneRilSoggiorno");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatRilSogg");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatRilSogg");
		}
		
	}
	
	private void elabConiuge(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		NodeList nodeList = element.getElementsByTagName("ap5:coniuge");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "cognomeConiuge");
			addNode(attributeNode, hmCampi, "nomeConiuge");
			addNode(attributeNode, hmCampi, "codiceFiscaleConiuge");
			addNode(attributeNode, hmCampi, "matricolaAnagrafica", "matricolaAnagraficaConiuge");
			addNode(attributeNode, hmCampi, "dataNascita", "dataNascitaConiuge");
		}
		
	}
	
	private void elabMatrimonio(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		addNode(element, hmCampi, "dataMatrimonio");
		NodeList nodeList = element.getElementsByTagName("ap5:luogoMatrimonio");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneMatrimonio");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatMatrimonio");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatMatrim");
			addNode(attributeNode, hmCampi, "descrizioneLocalita", "descrizioneLocalitaMatrim");
			addNode(attributeNode, hmCampi, "descrizioneStato", "descrizioneStatoMatrimonio");
			addNode(attributeNode, hmCampi, "codiceStato", "codiceStatoMatrimonio");
		}
		
	}
	
	private void elabCessazioneAnnullMatrimonio(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		NodeList nodeList = element.getElementsByTagName("ap5:cessazioneAnnullamentoMatrimonio");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "sentenza", "sentenzaAnnulMatrim");
			addNode(attributeNode, hmCampi, "dataValidita", "dataValAnnulMatrim");
			addNode(attributeNode, hmCampi, "tribunale", "tribunaleAnnulMatrim");
		}
		
	}
	
	private void elabDivorzio(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		NodeList nodeList = element.getElementsByTagName("ap5:cessazioneAnnullamentoMatrimonio");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "data", "dataDivorzio");
			addNode(attributeNode, hmCampi, "numero", "numeroDivorzio");
			addNode(attributeNode, hmCampi, "tribunale", "tribunaleDivorzio");
			addNode(attributeNode, hmCampi, "dataValidita", "dataValiditaDivorzio");
		}
		
	}
	
	private void elabVedovanza(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		addNode(element, hmCampi, "dataMorteConiuge");
		NodeList nodeList = element.getElementsByTagName("ap5:luogoMorteConiuge");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneMorteC");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatMorteC");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatMorteC");
			addNode(attributeNode, hmCampi, "descrizioneLocalita", "descrizioneLocalitaMorteC");
			addNode(attributeNode, hmCampi, "descrizioneStato", "descrizioneStatoMorteC");
			addNode(attributeNode, hmCampi, "codiceStato", "codiceStatoMorteC");
		}
		
		nodeList = element.getElementsByTagName("ap5:attoMorteConiuge");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneAttoMorteC");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComIstatAttoMorteC");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvIstatAttoMorteC");
			addNode(attributeNode, hmCampi, "anno", "annoAttoMorteC");
			addNode(attributeNode, hmCampi, "ufficio", "ufficioAttoMorteC");
			addNode(attributeNode, hmCampi, "serie", "serieAttoMorteC");
			addNode(attributeNode, hmCampi, "parte", "parteAttoMorteC");
			addNode(attributeNode, hmCampi, "frazione", "frazioneAttoMorteC");
			addNode(attributeNode, hmCampi, "tipoRegistro", "tipoRegistroAttoMorteC");
			addNode(attributeNode, hmCampi, "numeroRegistro", "numeroRegistroAttoMorteC");
		}
		
	}
	
	private void elabCartaidentita(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		addNode(element, hmCampi, "numeroCI", "numeroCi");
		addNode(element, hmCampi, "dataRilascioCI", "dataRilascioCi");
		addNode(element, hmCampi, "dataScadenzaCI", "dataScadenzaCi");
		addNode(element, hmCampi, "cartaceaElettronica");
		NodeList nodeList = element.getElementsByTagName("ap5:comuneRilascio");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneRilCi");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatRilCi");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatRilCi");
		}
		
	}
	
	private List<HashMap<String, String>> elabPatenti(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {		
		List<HashMap<String, String>> listaHmCampi = new ArrayList<HashMap<String, String>>();
		
		NodeList nodeList = element.getElementsByTagName("ap5:patenti");
		for (int j = 0; j < nodeList.getLength(); j++) {

			HashMap<String, String> campi = new HashMap<String, String>();
			
			org.w3c.dom.Element subElem = (Element) nodeList.item(j);
			campi.put("data_Ora_Richiesta", hmCampi.get("data_Ora_Richiesta"));
			campi.put("protocollo_Anagrafe", hmCampi.get("protocollo_Anagrafe"));
			campi.put("tipo_Comunicazione", hmCampi.get("tipo_Comunicazione"));
			campi.put("motivo_Comunicazione", hmCampi.get("motivo_Comunicazione"));
			campi.put("codice_Fiscale", hmCampi.get("codice_Fiscale"));
			campi.put("matricola_Anagrafica", hmCampi.get("matricola_Anagrafica"));
			addNode(subElem, campi, "numeroPatente");
			addNode(subElem, campi, "carattereControllo");
			addNode(subElem, campi, "tipoPatente");
			addNode(subElem, campi, "dataRilascioPatente");
			addNode(subElem, campi, "enteRilascioPatente");
			addNode(subElem, campi, "provinciaRilascioPatente");
			listaHmCampi.add(campi);
		}
		
		return listaHmCampi;
	}
	
	private List<HashMap<String, String>> elabVeicoli(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		List<HashMap<String, String>> listaHmCampi = new ArrayList<HashMap<String, String>>();
		
		NodeList nodeList = element.getElementsByTagName("ap5:veicolo");
		for (int j = 0; j < nodeList.getLength(); j++) {

			HashMap<String, String> campi = new HashMap<String, String>();
		
			org.w3c.dom.Element subElem = (Element) nodeList.item(j);
			campi.put("data_Ora_Richiesta", hmCampi.get("data_Ora_Richiesta"));
			campi.put("protocollo_Anagrafe", hmCampi.get("protocollo_Anagrafe"));
			campi.put("tipo_Comunicazione", hmCampi.get("tipo_Comunicazione"));
			campi.put("motivo_Comunicazione", hmCampi.get("motivo_Comunicazione"));
			campi.put("codice_Fiscale", hmCampi.get("codice_Fiscale"));
			campi.put("matricola_Anagrafica", hmCampi.get("matricola_Anagrafica"));
			addNode(subElem, campi, "tipo");
			addNode(subElem, campi, "targa");
			listaHmCampi.add(campi);
		}
		
		return listaHmCampi;
	}
	
	private void elabMorte(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		addNode(element, hmCampi, "dataMorte");
		NodeList nodeList = element.getElementsByTagName("ap5:luogoMorte");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneMorte");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatMorte");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatMorte");
			addNode(attributeNode, hmCampi, "descrizioneLocalita", "descrizioneLocalitaMorte");
			addNode(attributeNode, hmCampi, "descrizioneStato", "descrizioneStatoMorte");
			addNode(attributeNode, hmCampi, "codiceStato", "codiceStatoMorte");
		}
		
		nodeList = element.getElementsByTagName("ap5:attoMorte");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneAttoMorte");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatAttoMorte");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvIstatAttoMorte");
			addNode(attributeNode, hmCampi, "anno", "annoAttoMorte");
			addNode(attributeNode, hmCampi, "ufficio", "ufficioAttoMorte");
			addNode(attributeNode, hmCampi, "serie", "serieAttoMorte");
			addNode(attributeNode, hmCampi, "parte", "parteAttoMorte");
			addNode(attributeNode, hmCampi, "frazione", "frazioneAttoMorte");
			addNode(attributeNode, hmCampi, "tipoRegistro", "tipoRegistroAttoMorte");
			addNode(attributeNode, hmCampi, "numeroRegistro", "numeroRegistroAttoMorte");
		}
		
	}
	
	private void elabNuovoNomeCognome(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		NodeList nodeList = element.getElementsByTagName("ap5:variazioneNomeCognome");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nuovoCognome");
			addNode(attributeNode, hmCampi, "nuovoNome");
			addNode(attributeNode, hmCampi, "dataVariazione");
		}
		
	}
	
	private void elabNuovoSesso(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		NodeList nodeList = element.getElementsByTagName("ap5:variazioneSesso");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nuovoSesso");
			addNode(attributeNode, hmCampi, "nuovoNome");
			addNode(attributeNode, hmCampi, "dataVariazione");
		}
		
	}
	
	private void elabNuovaCittadinanza(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		NodeList nodeList = element.getElementsByTagName("ap5:variazioneCittadinanza");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "descrizioneStato", "descrizioneStatoVarCitt");
			addNode(attributeNode, hmCampi, "codiceStato", "codiceStatoVarCitt");
			addNode(attributeNode, hmCampi, "dataVariazione");
		}
		
	}
	
	private void elabNuovoPermSoggiorno(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		NodeList nodeList = element.getElementsByTagName("ap5:variazionePermessoSoggiorno");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(element, hmCampi, "numeroSoggiorno", "numeroVarSoggiorno");
			addNode(element, hmCampi, "tipoSoggiorno", "tipoVarSoggiorno");
			addNode(element, hmCampi, "dataRilascio", "dataRilascioVarSoggiorno");
			addNode(element, hmCampi, "dataScadenza", "dataScadenzaVarSoggiorno");
			addNode(attributeNode, hmCampi, "nomeComune", "nomeComuneVarSoggiorno");
			addNode(attributeNode, hmCampi, "codiceComuneIstat", "codiceComuneIstatVarSogg");
			addNode(attributeNode, hmCampi, "siglaProvinciaIstat", "siglaProvinciaIstatVarSogg");
		}
		
	}
	
	private void elabNuovoPaterMater(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		NodeList nodeList = element.getElementsByTagName("ap5:variazionePaternitaMaternita");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "cognomeMadre", "cognomeVarMadre");
			addNode(attributeNode, hmCampi, "nomeMadre", "nomeVarMadre");
			addNode(attributeNode, hmCampi, "codiceFiscaleMadre", "codiceFiscaleVarMadre");
			addNode(attributeNode, hmCampi, "cognomePadre", "cognomeVarPadre");
			addNode(attributeNode, hmCampi, "nomePadre", "nomeVarPadre");
			addNode(attributeNode, hmCampi, "codiceFiscalePadre", "codiceFiscaleVarPadre");
			addNode(attributeNode, hmCampi, "dataVariazione");
		}
		
	}
	
	private void elabDatiRettificati(Element element, HashMap<String, String> hmCampi)
			throws RulEngineException {
		
		NodeList nodeList = element.getElementsByTagName("ap5:datiRettificati");
		if(nodeList != null && nodeList.getLength() > 0){
			Element attributeNode = (Element) nodeList.item(0);
			addNode(attributeNode, hmCampi, "nomeCampo", "nomeCampoRett");
			addNode(attributeNode, hmCampi, "vecchioValore", "vecchioValoreRett");
			addNode(attributeNode, hmCampi, "dataRettifica", "dataRett");
		}
		
	}
	
	private void addNode(Element element, HashMap<String, String> hmCampi, String nameNode){
		addNode(element, hmCampi, nameNode, null);
	}
	
	private void addNode(Element element, HashMap<String, String> hmCampi, String nameNode, String nameOnMap){
		
		NodeList nodeList = element.getElementsByTagName(nameNode.contains("ap5:")? nameNode: "ap5:" + nameNode);
		if(nodeList != null && nodeList.getLength() > 0){
			org.w3c.dom.Node attributeNode = nodeList.item(0);
			String key = attributeNode.getLocalName();
			if(nameOnMap != null)
				key = nameOnMap;
			String value = attributeNode != null? attributeNode.getTextContent().trim(): "";
			String keyFormatted = underscoreFormat(key.replace("ap5:", ""));
			//format xml characters
			if (value!=null) {
				value = value.replaceAll("&#x20;"," ");
				value = value.replaceAll("&apos;","'");
			}
			hmCampi.put(keyFormatted, value);
		}
		
	}

	private Date estraiDataFornitura(String file, String cartellaDati, String line)
			throws RulEngineException{
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String data = file.substring(0, 14);

			return sdf.parse(data);
		} catch (Exception e) {
			throw new RulEngineException("Impossibile estrarre la data fornitura dal file " + file,e);
		}
	}
		
	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException{
		
			env.saveFornitura(estraiDataFornitura(file,cartellaDati,line), file);

		
	}
	

	

}
