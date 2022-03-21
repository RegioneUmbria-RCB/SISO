package it.webred.ct.proc.ario.bean;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

public class   BeanParser {
	private static final Logger log = Logger.getLogger(BeanParser.class.getName()); 

	public static  String getCivicoComposto(Civico civ)  {
		ByteArrayOutputStream cc = new ByteArrayOutputStream();

			
		try {
			if (civ.getCivLiv1().contains("&") || civ.getCivLiv1().contains("<")  
					|| civ.getCivLiv1().contains(">"))
				throw new Exception ("No posso costruire un civico che contenga caratteri strani");

			//xml civico_composto
			Element root = new Element("civicocomposto");
			Document doc = new Document(root);
			//tipo
			Element ind = new Element ("att");
			ind.setAttribute("tipo","numero");
			ind.setAttribute("valore", civ.getCivLiv1());
			root.addContent(ind);
			//xml format	
			XMLOutputter xop = new XMLOutputter();

			xop.output(doc, cc);
		} catch (Exception e) {
				log.warn("Errore generazione Civico Composto "+ civ.getCivLiv1(), e);
				return "<civicocomposto/>";
		}			

		return cc.toString();
			

	}

}
