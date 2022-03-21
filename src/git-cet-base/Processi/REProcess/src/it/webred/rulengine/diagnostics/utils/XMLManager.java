package it.webred.rulengine.diagnostics.utils;

import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;

import java.io.*;
import javax.xml.parsers.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * La classe gestisce le operazioni di lettura nel file xml delle diagnostiche.
 * La classe è gestita come singleton
 * @author Luca
 *
 */
public class XMLManager {
	
	private static Document doc;
	private static XMLManager singletonObject;
	private static String fileXml;	
	protected static org.apache.log4j.Logger log = Logger.getLogger(XMLManager.class.getName());
	
	private XMLManager(){
		doc = null;
		fileXml = "/diagnostiche.xml";		
	}
	
	public static XMLManager getSingletonObject() {
		if (singletonObject == null) {
			singletonObject = new XMLManager();
		}
		return singletonObject;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	
	// Parses an XML file and returns a DOM document.
    // If validating is true, the contents is validated against the DTD
    // specified in the file.
    private Document getDoc() throws SAXException, ParserConfigurationException, IOException {
        try {
        	
        	if (doc == null){ 
        		log.info("[GETDOC] - Carico il file diagnostiche.xml ");
                // Create a builder factory
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setValidating(false);

                // Create the builder and parse the file
                InputStream is = XMLManager.class.getResourceAsStream(fileXml);
                doc = factory.newDocumentBuilder().parse(is);
                log.info("[GETDOC] - File caricato ");
        	}
        	
            return doc;
        } catch (SAXException e) {
            throw e;
        } catch (ParserConfigurationException e) {
        	throw e;
        } catch (IOException e) {
        	throw e;
        }        
    }
    
    public void getDiagnosticConfig(int idCatalogoDia, DiagnosticConfigBean objDia ) throws Exception{    	
    	try {    
    		log.info("[GETDIAGNOSTICCONFIG] - START carico la configurazione per la diagnostica id:" +  idCatalogoDia);
			org.w3c.dom.Node root = getDoc().getFirstChild();
	        NodeList nodi = ((org.w3c.dom.Element)root).getElementsByTagName("diagnostica");

		    // Process the elements in the nodelist
		    for (int i=0; i<nodi.getLength(); i++) {
		    	org.w3c.dom.Node paramNode = nodi.item(i);
		    	
		    	NamedNodeMap attr = paramNode.getAttributes();
		    	if ((attr.getNamedItem("id") == null) ||
		    	    (Integer.parseInt(attr.getNamedItem("id").getNodeValue()) != idCatalogoDia ))
		    		continue;
		    	objDia.setIdCatalogoDia(idCatalogoDia);
		    			    			    	
		    	if (attr.getNamedItem("tableTestata") != null)
		    		objDia.setTableNameTestata(attr.getNamedItem("tableTestata").getNodeValue());
		    	
		    	if (attr.getNamedItem("tableDettaglio") != null)
		    		objDia.setTableNameDettaglio(attr.getNamedItem("tableDettaglio").getNodeValue());		    			    
		    	
		    	if (attr.getNamedItem("tableClass") != null)
		    		objDia.setTableClass(attr.getNamedItem("tableClass").getNodeValue());
		    	
		    	if (attr.getNamedItem("seqForDettaglio") != null)
		    		objDia.setSeqForDettaglio(attr.getNamedItem("seqForDettaglio").getNodeValue());
		    	
		    	if (attr.getNamedItem("standard") != null)
		    		objDia.setStandard(attr.getNamedItem("standard").getNodeValue());
		    		
		    	if (attr.getNamedItem("fieldTableDettaglioForFK") != null)
		    		objDia.setFieldTableDettaglioForFK(attr.getNamedItem("fieldTableDettaglioForFK").getNodeValue());
		    	
		    	if (attr.getNamedItem("tipoGest") != null)
		    		objDia.setNumTipoGestione(Integer.parseInt(attr.getNamedItem("tipoGest").getNodeValue()));
		    	
		    	if (attr.getNamedItem("tipoGestValue") != null)
		    		objDia.setNumTipoGestioneValue(attr.getNamedItem("tipoGestValue").getNodeValue());
		    	
		    			    			    
		    	//Esco dal for
		    	break;
		    }
		    log.debug("[GETDIAGNOSTICCONFIG] - Diagnostica : " + objDia.toString());
		    log.info("[GETDIAGNOSTICCONFIG] - END ");
		} catch (Exception e) {
			throw e;
		}		
    }

}
