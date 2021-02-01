package it.webred.rulengine.brick.loadDwh.load.docfa;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * TODO Scrivi descrizione
 * @author Petracca Marco
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:31:54 $
 */
public class LeggiTracciato
{
	private static final Logger	log	= Logger.getLogger(LeggiTracciato.class.getName());

	/**
	 * Legge un file di configurazione per un data source.
	 * <br>Il file viene passato in un InputStream   
	 * @param is InputStream del file di configurazione 
	 * @return Il tracciato popolato
	 * @throws Exception
	 * @throws IOException
	 */
	public static TracciatoXml caricaTracciatoXml(InputStream is) throws Exception, IOException
	{
		SAXBuilder builder = new SAXBuilder(false);
		Document doc = builder.build(is);
		return caricaTracciatoXml(doc);
	}

	
	
	
	/**
	 * Legge un file di configurazione per un data source.
	 * @param pathxml Percorso su disco del file di configurazione 
	 * @return Il tracciato popolato
	 * @throws Exception
	 * @throws IOException
	 */
	
	
	public static TracciatoXml caricaTracciatoXml(String pathxml) throws Exception, IOException
	{
		SAXBuilder builder = new SAXBuilder(false);
		Document doc = builder.build(pathxml);
		return caricaTracciatoXml(doc);
	}
	/**
	 * Legge un file di configurazione per un data source.
	 * @param doc  Il Document contenente il file di configurazione 
	 * @return Il tracciato popolato
	 * @throws Exception
	 * @throws IOException
	 */
	private static TracciatoXml caricaTracciatoXml(Document doc) throws Exception, IOException
	{

		Element root = doc.getRootElement();
		String nomeDS = root.getChildText("nomeDS");
		String versione = root.getChildText("versione");
		String caricatore = root.getChildText("caricatore");
		String driver = root.getChildText("driver");
		String gestoreDataEstrazione = root.getChildText("gestoreDataEstrazione");
		String connString = root.getChildText("stringaConnessione");
		String sourceTab = root.getChildText("sourceTab");
		String separatore = root.getChildText("separatore");

		if (separatore != null && separatore.equalsIgnoreCase("tab"))
			separatore = "\t";

		List listACTIONS = root.getChild("tracciato").getChildren("colonna");
		List listaColonne = new ArrayList();
		for (int i = 0; i < listACTIONS.size(); i++)
		{
			Element el = (Element) listACTIONS.get(i);
			String nomeColonna = el.getChild("nome").getText();

			String key = "false";
			if (el.getChild("key") != null)
				key = el.getChild("key").getText();

			// Recupero i tag <origine>
			List listTagOrigine = el.getChildren("origine");
			// Recupero i tag <destinazione>
			List listTagDest = el.getChildren("destinazione");

			for (int ii = 0; ii < listTagOrigine.size(); ii++)
			{

				// origine
				Element eleOrig = (Element) listTagOrigine.get(ii);
				String tipo = eleOrig.getChild("tipo").getText();
				Integer dimensioneOrig = 100;
				Integer startp = null;
				Integer stopp = null;
				Integer posizione = null;
				if (eleOrig.getChild("startp") != null && !eleOrig.getChild("startp").getText().equals(""))
					startp = new Integer(eleOrig.getChild("startp").getText());
				if (eleOrig.getChild("stopp") != null && !eleOrig.getChild("stopp").getText().equals(""))
					stopp = new Integer(eleOrig.getChild("stopp").getText());
				if (eleOrig.getChild("dimensioneOrig") != null && !eleOrig.getChild("dimensioneOrig").getText().equals(""))
					dimensioneOrig = new Integer(eleOrig.getChild("dimensioneOrig").getText());
				if (eleOrig.getChild("posizione") != null && !eleOrig.getChild("posizione").getText().equals(""))
					posizione = new Integer(eleOrig.getChild("posizione").getText());

				// destinazione
				Element eleDest = (Element) listTagDest.get(ii);
				String tipoDest = eleDest.getChild("tipoDest").getText();
				String formato = "";
				Integer dimensioneDest = 100;

				if (eleDest.getChild("formato") != null)
					formato = eleDest.getChild("formato").getText();
				if (eleDest.getChild("dimensioneDest") != null && !eleDest.getChild("dimensioneDest").getText().equals(""))
					dimensioneDest = new Integer(eleDest.getChild("dimensioneDest").getText());
				listaColonne.add(new Colonna(nomeColonna, key.equals("true") ? true : false, posizione, startp, stopp, tipo, dimensioneOrig, tipoDest, formato, dimensioneDest));
			}

			// log.debug("Nome colonna " + nome);
			// log.debug("Posizione " + posizione);
			// log.debug("Pimary key " + key);
			// log.debug("Tipo colonna " + tipo);
			// log.debug("Dimensione colonna " + (dimensione > 0 ? dimensione :
			// stopp - startp));
			// log.debug("Start position " + startp);
			// log.debug("Stop position " + stopp);

		}
		log.debug("xml configurazione data source letto");
		return new TracciatoXml("", nomeDS, versione, separatore, caricatore, driver, connString, sourceTab, listaColonne, gestoreDataEstrazione);

	}
}
