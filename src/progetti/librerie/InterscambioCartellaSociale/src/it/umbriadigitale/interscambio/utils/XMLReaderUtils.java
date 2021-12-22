package it.umbriadigitale.interscambio.utils;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.hl7.v3.request.PRSSIN001004ZZ;

import it.umbriadigitale.interscambio.constants.XSDPaths;

/**
 * Classe di utilità per la lettura (unmarshalling) di elementi XML JAXB dello schema XSD Request per Interscambio Cartella Sociale
 * (<code>PRSS_IN001004ZZ_mod.xsd</code>)
 * 
 * @author Iacopo Sorce
 */
public class XMLReaderUtils implements XSDPaths {
	private static Unmarshaller unmarshaller;
	
	/* La creazione del JAXBContext tende a essere piuttosto lenta; poiché l'Unmarshaller di cui abbiamo bisogno avrà sempre le stesse caratteristiche,
	 * lo creiamo un'unica volta e lo utilizziamo all'occorrenza (è praticamente un Singleton). */
	static {
		try {
			JAXBContext context = JAXBContext.newInstance(XSD_REQUEST_CONTEXT_PATH);
			
			unmarshaller = context.createUnmarshaller();
		}
		catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	
	// TODO gestire eccezioni
	/**
	 * Esegue l'unmarshal di un documento XML di tipo request per Interscambio Cartella Sociale
	 */
	public static PRSSIN001004ZZ unmarshalRequestRoot(InputStream xmlInputStream) throws JAXBException, XMLStreamException, FactoryConfigurationError {
		return unmarshaller.unmarshal(XMLInputFactory.newInstance().createXMLStreamReader(xmlInputStream), PRSSIN001004ZZ.class).getValue();
	}
}
