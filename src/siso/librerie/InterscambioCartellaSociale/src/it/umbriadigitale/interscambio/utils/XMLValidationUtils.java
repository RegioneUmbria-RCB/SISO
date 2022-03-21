package it.umbriadigitale.interscambio.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import it.umbriadigitale.interscambio.constants.XSDPaths;
import it.umbriadigitale.interscambio.exception.InterscambioValidationException;

/**
 * Classe di utilità per la validazione di documenti XML aderenti allo schema XSD Request per Interscambio Cartella Sociale
 * (<code>PRSS_IN001004ZZ_mod.xsd</code>)
 * 
 * @author Iacopo Sorce
 */
public class XMLValidationUtils implements XSDPaths {
	private static Validator requestValidator;
	
	static {
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(XMLValidationUtils.class.getClassLoader().getResource(HL7_REQUEST_SCHEMA));
			
			requestValidator = schema.newValidator();
		}
		// TODO come gestire le eccezioni?
		catch (Exception e) {
			e.printStackTrace();
			
			throw new RuntimeException("Inizializzazione del validatore XML di Interscambio fallita. Possibili cause: 1) schema XSD non trovato nel path dell'applicazione; 2) schema XSD non valido", e);
		}
	}
	
	/**
	 * Esegue la validazione dell'XML in <code>xmlInputStream</code> verso l'XSD di Interscambio Cartella Sociale (HL7). Se la validazione fallisce,
	 * lancia una <code>InterscambioValidationException</code>.
	 * 
	 * @param xmlInputStream	{@link java.io.InputStream InputStream} dell'XML da validare
	 * @throws InterscambioValidationException	se la validazione fallisce
	 */
	public static void validateRequest(InputStream xmlInputStream) throws InterscambioValidationException {
		try {
			requestValidator.validate(new StreamSource(xmlInputStream));
		}
		catch (SAXException | IOException e) {
			throw new InterscambioValidationException("Validazione XML di Interscambio fallita. Message: " + e.getMessage() + " - Type: " + e.getClass(), e);
		}
	}
}
