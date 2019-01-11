package it.umbriadigitale.interscambio.utils;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import it.umbriadigitale.interscambio.constants.XSDPaths;

/**
 * Classe di utilità per la stampa di elementi XML JAXB dello schema XSD Request per Interscambio Cartella Sociale
 * (<code>PRSS_IN001004ZZ_mod.xsd</code>)
 * 
 * @author Iacopo Sorce
 */
public class XMLPrinterUtils implements XSDPaths {
	private static Marshaller marshaller;
	
	/* La creazione del JAXBContext tende a essere piuttosto lenta; poiché il Marshaller di cui abbiamo bisogno avrà sempre le stesse caratteristiche,
	 * lo creiamo un'unica volta e lo utilizziamo all'occorrenza (è praticamente un Singleton). */
	static {
		try {
			JAXBContext context = JAXBContext.newInstance(XSD_REQUEST_CONTEXT_PATH);
			
			marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);	// pretty print
		}
		catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Restituisce il pretty print dell'XML dell'elemento root <code>element</code>
	 * 
	 * @param element	Elemento JAXB di cui si vuole ottenere il pretty print; deve essere necessariamente un root element
	 * @return
	 * @throws JAXBException
	 */
	public static <T> String printXmlRootElement(T element) throws JAXBException {
		StringWriter sw = new StringWriter();
		
		marshaller.marshal(element, sw);
		
		return sw.toString();
	}
}
