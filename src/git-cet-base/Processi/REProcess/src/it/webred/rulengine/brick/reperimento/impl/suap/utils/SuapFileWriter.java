package it.webred.rulengine.brick.reperimento.impl.suap.utils;

import it.webred.rulengine.brick.reperimento.impl.suap.ReperimentoSuapImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
//import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.ibm.icu.text.SimpleDateFormat;

public class SuapFileWriter {
	
	private static Logger log = Logger.getLogger(SuapFileWriter.class.getName());
	
	//chiavi
	public static final String KEY_ID = "id";
	public static final String KEY_RICEVUTA = "ricevuta";
	public static final String KEY_RIEPILOGO = "riepilogo";
	
	//carattere separatore
	public static final String SEPARATOR = "|";
	
	//campo vuoto
	public static final String CAMPO_VUOTO = "";	
	
	//prefisso nome file .txt
	public static final String PREF_NOME_FILE = "CONCESSIONI_SUAP";
	
	//provenienza
	public static final String PROVENIENZA = "SUAP";
	
	//tipi record
	public static final String TIPO_REC_TESTA = "0";
	public static final String TIPO_REC_CODA = "9";
	public static final String TIPO_REC_PRATICA = "A";
	public static final String TIPO_REC_DICHIARANTE = "B";
	public static final String TIPO_REC_DATI_TECNICI = "C";
	public static final String TIPO_REC_DATI_CATASTALI = "D";
	public static final String TIPO_REC_INDIRIZZI = "E";
	
	//troncamento campi stringa troppo lunghi
	public static final boolean DO_SUBSTRING = true;
	public static final int MAX_LENGTH_FROM = 10;
	
	
	public static void writeTempPropFile(String dirFiles, String path, String id) throws Exception {
		// creazione cartella file properties
		String propFolder = dirFiles + SuapFileReader.PROPERTIES_FOLDER + File.separator;
		if(!new File(propFolder).exists()) {
			new File(propFolder).mkdir();
		}

		log.info("SUAP - Lettura del file " + path);
		String pathTo = propFolder + id + SuapFileReader.SUFF_FILE_PROPERTIES;
		
		Properties props = new Properties();
		File fileTo = new File(pathTo);
		if (fileTo.exists()) {
			InputStream is = new FileInputStream(fileTo);
			props.load(is);
			is.close();
		} else {
			props.put(KEY_ID, id);
		}
		
		String rootKey = "";
		if (path.toLowerCase().replace("_tmp.xml", ".xml").endsWith(SuapFileReader.SUFF_FILE_RICEVUTA.toLowerCase())) {
			rootKey = KEY_RICEVUTA;
		} else if (path.toLowerCase().replace("_tmp.xml", ".xml").endsWith(SuapFileReader.SUFF_FILE_RIEPILOGO.toLowerCase())) {
			rootKey = KEY_RIEPILOGO;
		}
		
	    SAXBuilder builder = new SAXBuilder();
	    Document document = builder.build(new File(path));
	    Element root = document.getRootElement();
	    List<?> children = root.getChildren();
	    Iterator<?> iterator = children.iterator();
	    while(iterator.hasNext()){
	    	Element item = (Element)iterator.next();
	    	String key = rootKey + "." + item.getName();
	        writeNode(item, props, key);
	    }

	    OutputStream os = new FileOutputStream(fileTo);
		props.store(os, null);
		os.flush();
		os.close();
	}
	
	private static void writeNode(Element element, Properties props, String key) throws Exception {
		//attributi
		List<?> attrs = element.getAttributes();
	    Iterator<?> itAttrs = attrs.iterator();
	    while(itAttrs.hasNext()){
	    	Attribute attr = (Attribute)itAttrs.next();
	    	String attrKey = key + "." + attr.getName();
	    	putInProperties(props, attrKey, attr.getValue());
	    }
		
		//elementi figli
		List<?> children = element.getChildren();
	    Iterator<?> itChildren = children.iterator();
	    boolean hasChildren = itChildren.hasNext();
	    if (!hasChildren) {
	    	//valore
	    	putInProperties(props, key, element.getValue());
	    }
	    while(itChildren.hasNext()){
	    	Element item = (Element)itChildren.next();
	    	String elKey = key + "." + item.getName();
	        writeNode(item, props, elKey);
	    }
	}
	
	private static void putInProperties(Properties props, String key, String value) throws Exception {
		Iterator<?> it = props.keySet().iterator();
		int count = 0;
		while (it.hasNext()) {
			String myKey = (String)it.next();
			if (myKey.equalsIgnoreCase(key)) {
				String myValue = props.getProperty(myKey);
				props.put(myKey + ".1", myValue);
				props.put(key + ".2", value);
				props.remove(myKey);
				return;
			} else if (myKey.toLowerCase().startsWith(key.toLowerCase())) {
				String substr = myKey.substring(key.length() + 1);
				if (isNumeric(substr)) {
					int newCount = Integer.parseInt(substr);
					if (newCount > count) {
						count = newCount;
					}
				}
			}			
		}
		if (count > 0) {
			count++;
			props.put(key + "." + count, value);
			return;
		}		
		props.put(key, value);
	}
	
	public static void writeFile(String dirFiles) throws Exception {
		String enteFontePropPath = dirFiles + ReperimentoSuapImpl.ENTE_FILENAME;
		Properties enteFonteProps = new Properties();
		File fEnteFonteProps = new File(enteFontePropPath);
		InputStream isEnteFonteProps = new FileInputStream(fEnteFonteProps);
		enteFonteProps.load(isEnteFonteProps);
		isEnteFonteProps.close();
		
		String codEnte = enteFonteProps.getProperty(ReperimentoSuapImpl.CODENT);
		
		new File(enteFontePropPath).delete();
		
		Date now = new Date();
		String nomeFile = PREF_NOME_FILE +
						"_" +
						codEnte +
						"_" +
						new SimpleDateFormat("yyyyMMdd").format(now) +
						".txt";
		String path = dirFiles + nomeFile;
		
		String strNow = new SimpleDateFormat("dd/MM/yyyy").format(now);
		
		//cancellazione preliminare di eventuale file omonimo preesistente (altrimenti vi farebbe append)
		File oldF = new File(path);
		if (oldF.exists()) {
			oldF.delete();
		}
		PrintWriter out = new PrintWriter(new File(path), "UTF8");
		
		//record di testata
		writeLines(out, enteFonteProps, TIPO_REC_TESTA, strNow);
		
		String propFolder = dirFiles + SuapFileReader.PROPERTIES_FOLDER + File.separator;
		String[] fileList = it.webred.utils.FileUtils.cercaFileDaElaborare(propFolder);
		for (String fileName : fileList) {			
			String propPath = propFolder + fileName;
			Properties props = new Properties();
			File f = new File(propPath);
			InputStream is = new FileInputStream(f);
			props.load(is);
			is.close();
			writeLines(out, props, null, null);
		}
		
		//record di coda
		writeLines(out, enteFonteProps, TIPO_REC_CODA, strNow);
		
		out.close();
	}
	
	private static void writeLines(PrintWriter out, Properties props, String type, String date) throws Exception {	    
		StringBuffer lineBuf = new StringBuffer();
		
		if (type != null) {
			lineBuf.append(type);
			lineBuf.append(SEPARATOR);
			lineBuf.append(props.getProperty(ReperimentoSuapImpl.CODENT));
			lineBuf.append(SEPARATOR);
			lineBuf.append("COMUNE DI " + props.getProperty(ReperimentoSuapImpl.DESCRIZIONE));
			lineBuf.append(SEPARATOR);
			lineBuf.append(date);			
			if (type.equals(TIPO_REC_TESTA)) {
				lineBuf.append(SEPARATOR);
				lineBuf.append(props.getProperty(ReperimentoSuapImpl.FONTE));
				lineBuf.append(SEPARATOR);
				lineBuf.append(PROVENIENZA);				
			}
			String line = lineBuf.toString();
			out.println(line);
		} else {
			//ArrayList<String> indirizziCollegati = new ArrayList<String>();
			
			String id = "SUAP/" + getPropertyValue(props, "id", 0);
			String numConc = getPropertyValue(props, "id", 0);
			if (numConc.indexOf("_") > -1) {
				numConc = numConc.substring(0, numConc.indexOf("_"));
			}
			
			//estremi pratica - tipo record A
			lineBuf.append(TIPO_REC_PRATICA); //tipo record
			lineBuf.append(SEPARATOR);
			lineBuf.append(id); //chiave
			lineBuf.append(SEPARATOR);
			lineBuf.append(numConc); //concessione_numero
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //progressivo_numero
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //progressivo_anno
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //protocollo_data
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //protocollo_numero
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //tipo_intervento
			lineBuf.append(SEPARATOR);
			lineBuf.append(getPropertyValue(props, "riepilogo.intestazione.oggetto-comunicazione", 500)); //oggetto
			lineBuf.append(SEPARATOR);
			lineBuf.append(getPropertyValue(props, "riepilogo.intestazione.oggetto-comunicazione.tipo-procedimento", 300)); //procedimento
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //codice via
			lineBuf.append(SEPARATOR);
			String indirizzo = getPropertyValue(props, "riepilogo.intestazione.impianto-produttivo.indirizzo.denominazione-stradale", 250);
			lineBuf.append(indirizzo); //indirizzo
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //civico
			lineBuf.append(SEPARATOR);
			//addIndirizzoCollegato(indirizziCollegati, id, CAMPO_VUOTO, indirizzo, CAMPO_VUOTO);
			lineBuf.append(CAMPO_VUOTO); //barrato
			lineBuf.append(SEPARATOR);			
			lineBuf.append(CAMPO_VUOTO); //zona
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //data_rilascio
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //data_inizio_lavori
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //data_fine_lavori
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //data_proroga_lavori
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //posizione_codice
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //posizione_descrizione
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //posizione_data
			String line = lineBuf.toString();
			out.println(line);
			
			//dichiarante - tipo record B
			Properties propsImpresa = getPropertiesArray(props, "riepilogo.intestazione.impresa.codice-fiscale", true);
			if (propsImpresa != null && propsImpresa.size() > 0) {
				int count = propsImpresa.containsKey("count") ? 1 : 0;
				for (int i = 0; i <= count; i++) {
					if (i == 0 && count > 0) {
						continue;
					}
					String idxKey = (i == 0 ? "" : "." + i);
					
					lineBuf = new StringBuffer();

					lineBuf.append(TIPO_REC_DICHIARANTE); //tipo record
					lineBuf.append(SEPARATOR);
					lineBuf.append(getPropertyValue(props, "riepilogo.intestazione.impresa.codice-fiscale" + idxKey, 50)); //TODO chiave = C.F. o P.I., va bene? non c'è altra chiave...
					lineBuf.append(SEPARATOR);
					lineBuf.append(id); //chiave_relazione
					lineBuf.append(SEPARATOR);
					String codFisc = getPropertyValue(props, "riepilogo.intestazione.impresa.codice-fiscale" + idxKey, 16);
					if (isNumeric(codFisc)) {
						//partita iva
						lineBuf.append("A"); //tipo_soggetto
						lineBuf.append(SEPARATOR);
						lineBuf.append("G"); //tipo_persona
						lineBuf.append(SEPARATOR);
					} else {
						//codice fiscale
						lineBuf.append("P"); //tipo_soggetto
						lineBuf.append(SEPARATOR);
						lineBuf.append("F"); //tipo_persona
						lineBuf.append(SEPARATOR);
					}
					lineBuf.append(codFisc); //codice_fiscale
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //cognome
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //nome
					lineBuf.append(SEPARATOR);
					lineBuf.append(getPropertyValue(props, "riepilogo.intestazione.impresa.ragione-sociale" + idxKey, 200)); //denominazione_ragsoc
					lineBuf.append(SEPARATOR);
					lineBuf.append("Impresa"); //titolo
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //data_nascita
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //comune_nascita
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //provincia_nascita
					lineBuf.append(SEPARATOR);					
					String toponimo = getPropertyValue(props, "riepilogo.intestazione.impresa.indirizzo.toponimo" + idxKey, 100);
					indirizzo = getPropertyValue(props, "riepilogo.intestazione.impresa.indirizzo.denominazione-stradale" + idxKey, 100);
					String civico = getPropertyValue(props, "riepilogo.intestazione.impresa.indirizzo.numero-civico" + idxKey, 10);
					//addIndirizzoCollegato(indirizziCollegati, id, toponimo, indirizzo, civico);
					toponimo += " ";
					if (!indirizzo.toLowerCase().startsWith(toponimo.toLowerCase())) {
						indirizzo = toponimo + indirizzo;
					}
					//il civico non risulta essere gestito dall'elaborazione come campo a sé... quindi viene aggiunto all'indirizzo
					if (civico != null && !civico.equals("")) {
						indirizzo += ((indirizzo != null && !indirizzo.equals("") ? " " : "") + civico);
					}
					//
					if (indirizzo.length() > 100 && DO_SUBSTRING) {
						indirizzo = indirizzo.substring(0, 97) + "...";
					}
					lineBuf.append(indirizzo); //indirizzo_residenza
					lineBuf.append(SEPARATOR);
					lineBuf.append(civico); //civico_residenza
					lineBuf.append(SEPARATOR);
					lineBuf.append(getPropertyValue(props, "riepilogo.intestazione.impresa.indirizzo.cap" + idxKey, 20)); //cap_residenza
					lineBuf.append(SEPARATOR);
					lineBuf.append(getPropertyValue(props, "riepilogo.intestazione.impresa.indirizzo.comune" + idxKey, 100)); //comune_residenza
					lineBuf.append(SEPARATOR);
					lineBuf.append(getPropertyValue(props, "riepilogo.intestazione.impresa.indirizzo.provincia" + idxKey, 20)); //provincia_residenza
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //data_inizio_residenza
					lineBuf.append(SEPARATOR);					
					lineBuf.append(CAMPO_VUOTO); //tel
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //fax
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //email
					lineBuf.append(SEPARATOR);
					if (isNumeric(codFisc)) {
						lineBuf.append(codFisc); //piva
					} else {
						lineBuf.append(CAMPO_VUOTO); //piva
					}
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //indirizzo_studio
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //cap_studio
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //comune_studio
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //provincia_studio
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //albo
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //rag_soc_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //cf_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //pi_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //indirizzo_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //cap_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //comune_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //provincia_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //qualita
					
					line = lineBuf.toString();
					out.println(line);
				}
			}			
			
			Properties propsDichiarante = getPropertiesArray(props, "ricevuta.intestazione.dichiarante.codice-fiscale", true);
			if (propsDichiarante != null && propsDichiarante.size() > 0) {
				int count = propsDichiarante.containsKey("count") ? 1 : 0;
				for (int i = 0; i <= count; i++) {
					if (i == 0 && count > 0) {
						continue;
					}
					String idxKey = (i == 0 ? "" : "." + i);
					
					lineBuf = new StringBuffer();
					
					lineBuf.append(TIPO_REC_DICHIARANTE); //tipo record
					lineBuf.append(SEPARATOR);
					lineBuf.append(getPropertyValue(props, "ricevuta.intestazione.dichiarante.codice-fiscale" + idxKey, 50)); //TODO chiave = C.F. o P.I., va bene? non c'è altra chiave...
					lineBuf.append(SEPARATOR);
					lineBuf.append(id); //chiave_relazione
					lineBuf.append(SEPARATOR);
					String codFisc = getPropertyValue(props, "ricevuta.intestazione.dichiarante.codice-fiscale" + idxKey, 16);
					if (isNumeric(codFisc)) {
						//partita iva
						lineBuf.append("A"); //tipo_soggetto
						lineBuf.append(SEPARATOR);
						lineBuf.append("G"); //tipo_persona
						lineBuf.append(SEPARATOR);
					} else {
						//codice fiscale
						lineBuf.append("P"); //tipo_soggetto
						lineBuf.append(SEPARATOR);
						lineBuf.append("F"); //tipo_persona
						lineBuf.append(SEPARATOR);
					}
					lineBuf.append(codFisc); //codice_fiscale
					lineBuf.append(SEPARATOR);
					lineBuf.append(getPropertyValue(props, "ricevuta.intestazione.dichiarante.cognome" + idxKey, 200)); //cognome
					lineBuf.append(SEPARATOR);
					lineBuf.append(getPropertyValue(props, "ricevuta.intestazione.dichiarante.nome" + idxKey, 200)); //nome
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //denominazione_ragsoc
					lineBuf.append(SEPARATOR);
					String titolo = getPropertyValue(props, "ricevuta.intestazione.dichiarante.qualifica" + idxKey, 100);
					if (titolo.length() > 1) {
						titolo = titolo.substring(0, 1).toUpperCase() + titolo.substring(1).toLowerCase();
					}					
					lineBuf.append(titolo); //titolo
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //data_nascita
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //comune_nascita
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //provincia_nascita
					lineBuf.append(SEPARATOR);									
					lineBuf.append(CAMPO_VUOTO); //indirizzo_residenza
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //civico_residenza
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //cap_residenza
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //comune_residenza
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //provincia_residenza
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //data_inizio_residenza
					lineBuf.append(SEPARATOR);					
					lineBuf.append(getPropertyValue(props, "ricevuta.intestazione.dichiarante.telefono" + idxKey, 50)); //tel
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //fax
					lineBuf.append(SEPARATOR);
					String pec = getPropertyValue(props, "ricevuta.intestazione.dichiarante.pec" + idxKey, 100);
					if (pec == null || pec.trim().equals("")) {
						pec = getPropertyValue(props, "ricevuta.intestazione.domicilio-elettronico" + idxKey, 100);
					}
					lineBuf.append(pec); //email
					lineBuf.append(SEPARATOR);
					if (isNumeric(codFisc)) {
						lineBuf.append(codFisc); //piva
					} else {
						lineBuf.append(CAMPO_VUOTO); //piva
					}
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //indirizzo_studio
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //cap_studio
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //comune_studio
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //provincia_studio
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //albo
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //rag_soc_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //cf_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //pi_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //indirizzo_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //cap_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //comune_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //provincia_ditta
					lineBuf.append(SEPARATOR);
					lineBuf.append(CAMPO_VUOTO); //qualita
					
					line = lineBuf.toString();
					out.println(line);
				}
			}
			//TODO proprietario è anche lui dichiarante con qualifica = PROPRIETARIO?

			//dati tecnici - tipo record C
			//TODO verificare - negli xml non risultano dati
			
			//dati catastali - tipo record D
			lineBuf = new StringBuffer();
			
			lineBuf.append(TIPO_REC_DATI_CATASTALI); //tipo record
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //chiave
			lineBuf.append(SEPARATOR);
			lineBuf.append(id); //chiave_relazione
			lineBuf.append(SEPARATOR);
			lineBuf.append(getPropertyValue(props, "riepilogo.intestazione.impianto-produttivo.dati-catastali.foglio", 0)); //foglio
			lineBuf.append(SEPARATOR);
			lineBuf.append(getPropertyValue(props, "riepilogo.intestazione.impianto-produttivo.dati-catastali.mappale", 0)); //particella
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //subalterno
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //tipo
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //sezione
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //codice_fabbricato
			lineBuf.append(SEPARATOR);
			lineBuf.append(CAMPO_VUOTO); //chiave_relazione_e
			
			line = lineBuf.toString();
			out.println(line);
			
			//indirizzi collegati - tipo record E
			/*for (String indirizzoCollegato : indirizziCollegati) {
				out.println(indirizzoCollegato);
			}*/
			
		}		
	    
	}
	
	private static String getPropertyValue(Properties props, String key, int maxLength) throws Exception {
		if (key == null) {
			return CAMPO_VUOTO;
		}
		if (!props.containsKey(key)) {
			//provo con l'altra "rootKey"
			if (key.startsWith(KEY_RICEVUTA)) {
				key = KEY_RIEPILOGO + key.substring(KEY_RICEVUTA.length());
			} else if (key.startsWith(KEY_RIEPILOGO)) {
				key = KEY_RICEVUTA + key.substring(KEY_RIEPILOGO.length());
			}			
		}
		if (props.containsKey(key)) {
			String value = props.getProperty(key) == null ? CAMPO_VUOTO : props.getProperty(key).trim();
			if (DO_SUBSTRING) {
				if (value.length() > maxLength && maxLength >= MAX_LENGTH_FROM) {
					value = value.substring(0, maxLength - 3) + "...";
				}
			}
			return value == null ? CAMPO_VUOTO : value.replace("\n", " ");
		}
		return CAMPO_VUOTO;
	}
	
	private static Properties getPropertiesArray(Properties props, String key, boolean retry) throws Exception {
		Properties retVal = new Properties();
		if (props.containsKey(key)) {
			retVal.put(key, getPropertyValue(props, key, 0));
		} else {
			Iterator<?> it = props.keySet().iterator();
			int count = 0;
			while (it.hasNext()) {
				String myKey = (String)it.next();
				if (myKey.toLowerCase().startsWith(key.toLowerCase())) {
					String substr = myKey.substring(key.length() + 1);
					if (isNumeric(substr)) {
						int newCount = Integer.parseInt(substr);
						if (newCount > count) {
							count = newCount;
						}
						retVal.put(myKey, props.get(myKey));
					}
				}			
			}
			if (count > 0) {
				retVal.put("count", "" + count);
			}
		}
		if (retry && (retVal == null || retVal.size() == 0)) {
			//provo con l'altra "rootKey"
			if (key.startsWith(KEY_RICEVUTA)) {
				key = KEY_RIEPILOGO + key.substring(KEY_RICEVUTA.length());
			} else if (key.startsWith(KEY_RIEPILOGO)) {
				key = KEY_RICEVUTA + key.substring(KEY_RIEPILOGO.length());
			}
			retVal = getPropertiesArray(props, key, false);
		}
		return retVal;
	}
	
	private static boolean isNumeric(String str) throws Exception {
		boolean isNumeric = true;
		for (char car : str.toCharArray()) {
			if ("1234567890".indexOf("" + car) == -1) {
				isNumeric = false;
				break;
			}
		}
		return isNumeric;
	}
	
	/*private static void addIndirizzoCollegato(List<String> indirizziCollegati, String id, String prefisso, String indirizzo, String civico) throws Exception {
		if (indirizzo == null || indirizzo.equals(CAMPO_VUOTO)) {
			return;
		}
		
		//indirizzi collegati - tipo record E
		StringBuffer lineBuf = new StringBuffer();
		
		lineBuf.append(TIPO_REC_INDIRIZZI); //tipo record
		lineBuf.append(SEPARATOR);
		lineBuf.append(CAMPO_VUOTO); //chiave
		lineBuf.append(SEPARATOR);
		lineBuf.append(id); //chiave_relazione
		lineBuf.append(SEPARATOR);
		lineBuf.append(CAMPO_VUOTO); //filler
		lineBuf.append(SEPARATOR);
		lineBuf.append(prefisso); //prefisso_via
		lineBuf.append(SEPARATOR);
		lineBuf.append(indirizzo); //indirizzo
		lineBuf.append(SEPARATOR);
		lineBuf.append(CAMPO_VUOTO); //codice_via
		lineBuf.append(SEPARATOR);
		lineBuf.append(civico); //civico
		lineBuf.append(SEPARATOR);
		lineBuf.append(CAMPO_VUOTO); //civico2
		lineBuf.append(SEPARATOR);
		lineBuf.append(CAMPO_VUOTO); //civico3
		lineBuf.append(SEPARATOR);
		lineBuf.append(CAMPO_VUOTO); //descrizione		
		
		String line = lineBuf.toString();
		indirizziCollegati.add(line);
	}*/

}
