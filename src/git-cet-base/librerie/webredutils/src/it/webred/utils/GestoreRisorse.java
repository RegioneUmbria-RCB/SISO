package it.webred.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Registro di sistema E` un singleton che fornisce un solo metodo statico Object o=Registry.get( COMPONENTE ); si preoccupa di istanziare TUTTI i singleton di sistema, e ne fornisce il nome come
 * costante static di classe.
 *
 *
 */
public class GestoreRisorse //ex Registry
{
	private static final Logger log = Logger.getLogger(GestoreRisorse.class.getName());
	public static final String CONFIGURATION_FILE = FileSeeker.fileName("../conf/risorse.xml");
	public static final String COMPONENT = "componente";
	public static final String CLAZZ = "class";
	public static final String CONF = "file";
	private static GestoreRisorse reg = null;
	private TreeMap map = new TreeMap();
	private ArrayList l = new ArrayList();

	/**
	 * Crea una nuova istanza. Impedito dal private
	 */
	private GestoreRisorse()
	{
	}

	/**
	 * GestoreRisorse e `singleton di sistema. Nel caso di un application server e`singleton di application Nel caso di load balancing e`singleton di OGNI context, quindi ricordarsi che OGNI context ha la
	 * sua diversa istanza di GestoreRisorse, che andra`configurata nel modo opportuno.
	 *
	 * @return
	 */

	synchronized static public GestoreRisorse getInstance() throws Exception
	{
		if (reg == null)
		{
			reg = new GestoreRisorse();
			log.debug("GestoreRisorse init based on file:" + CONFIGURATION_FILE);
			Assertion.checkEmpty(CONFIGURATION_FILE, "configuration file");
			Assertion.fileExists(CONFIGURATION_FILE);
			log.debug("Caricamento file di configurazione " + CONFIGURATION_FILE);

			try
			{
				SAXBuilder builder = new SAXBuilder(false);
				Document doc = builder.build(CONFIGURATION_FILE);
				Element root = doc.getRootElement();

				List list = root.getChildren(COMPONENT);

				// carica i componenti sul registro
				for (int i = 0; i < list.size(); i++)
				{
					Element uc = (Element) list.get(i);
					String clazz = uc.getAttributeValue(CLAZZ);
					String file = uc.getAttributeValue(CONF);
					log.debug("Mounting component " + clazz + " has conf file ='" + file + "' ");

					Assertion.checkNull(clazz, "class");
					Assertion.checkEmpty(file, "file");

					// se file e`relativo allora lo compone con un fileseeker
					if (file.startsWith("."))
					{
						file = FileSeeker.fileName(file);
					}

					// NON CONTROLLO SE ESISTE IL COSTRUTTORE DEL COMPONENTE
					// HA LA DELEGA
					// prende il costruttore che ha una stringa come parametro
					Class c = Class.forName(clazz);
					Constructor cons = c.getConstructor(new Class[] { String.class });

					// lo costruisce
					Object o = cons.newInstance(new Object[] { file });

					// lo registra
					GestoreRisorse.register(clazz, o);
				}

				log.debug("Fatto");
			}
			catch (Exception e)
			{
				log.error(e);
				log.error("!!!!!!!!!!!!!!!!    QUESTO ERRORE E` GRAVE   !!!!!!!!!!!!!!!!!!! ");
				log.error("                NE SEGUE UNO STOP DEL SISTEMA..");
				log.error("!!!!!!!!!!!!!!!!    QUESTO ERRORE E` GRAVE   !!!!!!!!!!!!!!!!!!! ");
				throw e;
			}

			log.debug("GestoreRisorse initialization done.");
		}

		return reg;
	}

	/**
	 * Registra un oggetto
	 *
	 * @param key
	 *            nome del componente
	 * @param value
	 *            il componente
	 */
	static private void register(String key, Object value)
	{
		Assertion.checkNull(key, "key");
		Assertion.checkNull(value, "value");
		log.debug("Registro " + key + " [e` una " + value.getClass().getName() + "]");
		reg.map.put(key, value);
		reg.l.add(value);
	}

	/**
	 * Trova il componente passato come classe
	 *
	 * @param clazz
	 *            nome del componente
	 *
	 * @return il componente
	 */
	synchronized static public Object get(Class clazz) throws Exception
	{
		getInstance();
		Assertion.checkNull(clazz, "clazz");

		String name = clazz.getName();

		// voglio sapere chi chiede che cosa
		String proc = Thread.currentThread().getName();
		String chi = getChi();
		log.debug(proc + "." + chi + " ha richiesto un " + name);

		Object ret = reg.map.get(name);

		if (ret == null)
		{
			log.warn("componente " + name + " non presente sul registro !");
		}

		return ret;
	}

	private static String getChi()
	{
		try
		{
			// 1.4.X up
			StringBuffer ret = new StringBuffer();
			StackTraceElement se[] = new Throwable().getStackTrace();
			for (int i = se.length - 1; i > 1; i--)
			{
				String m = se[i].getClassName();
				String cn[] = m.split("\\.");
				m = cn[cn.length - 1];
				ret.append(m);
				ret.append('.');
				ret.append(se[i].getMethodName());
				ret.append('.');
			}
			return ret.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "BAD JVM";
		}
	}

}