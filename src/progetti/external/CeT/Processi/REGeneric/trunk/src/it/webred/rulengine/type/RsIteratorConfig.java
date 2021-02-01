package it.webred.rulengine.type;

import it.webred.rulengine.Command;
import it.webred.rulengine.impl.BaseCommandFactory;

import it.webred.rulengine.impl.factory.RCommandFactory;
import it.webred.rulengine.type.bean.RsIteratorConfigCmds;
import it.webred.rulengine.type.bean.RsIteratorConfigP;
import it.webred.rulengine.type.def.BaseType;
import it.webred.rulengine.type.def.TypeFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

/**
 * Esempio di file di configurazione di RsIteratorConfig
 * <RsIterator connection="nome di una connessione (DEFAULT | DWH |ALTRO)">
 * <commands>
 * <key table="NOME TABELLA">
 * 		<column name="NOME COLONNA IN CHIAVE"/>   
 * 		<column name="NOME COLONNA IN CHIAVE"/>
 * </key>
 *  <command name="it.webred.rulengine.brick.nomeregola" livelloAnomalie="1|2 (1= basso 2 =alto)">
 *		  <param id="field" value="Nome campo del resultset" dest="nome parametro di input del copmmand" type="tipo del parametro"/>
 *		  <param id="const" value="valore della costante" dest="nome paramero di input del command" type="tipo java della costante/>
 *  </command>
 *  <command name="it.webred.rulengine.brick.nomeregola" livelloAnomalie="1|2">
 *		  <param id="const" dest="nome paramero di input del command" type="tipo java della costante>
 *				<![CDATA[ valore del parametro (alternativo all'attributo value) ]]> 
 *			</param>
 *  </command>
 *</commands>
 *</RsIterator>
 * @author marcoric
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:24 $
 */
public class RsIteratorConfig extends BaseType 
{
	private String connectionName;
	private List<RsIteratorConfigCmds> commands;
	private String tableName;
	private List<String> keyColumns = new ArrayList<String>();
	private static final Logger log = Logger.getLogger(RsIteratorConfig.class.getName());
	
	/**
	 * @return lISTA DI RsIteratorConfigCmds CONTENENTE I COMMANDS CHIAMATI CON I RELATIVI PARAMETRI PASSATI
	 */
	public List<RsIteratorConfigCmds> getCommands()
	{
		return commands;
	}


	/**
	 * @return List<String> Lista di nomi di colonne in chiave
	 */
	public List<String> getKeyColumns()
	{
		return keyColumns;
	}


	public RsIteratorConfig(String file_xml) throws Exception {
		super(file_xml);
		log.debug("Costruisco il tipo...");
		log.debug(file_xml);
		
		SAXBuilder builder = new SAXBuilder(false);
		StringReader sr = new StringReader(file_xml);
		Document doc = builder.build(new InputSource(sr));
		Element root = doc.getRootElement();

		connectionName = (String)root.getAttributeValue("connection");
		
		Element commandsXls = root.getChild("commands");
		
		
		Iterator commandXls = commandsXls.getChildren().iterator();
		commands = new ArrayList<RsIteratorConfigCmds>();
		
		/*
		 * commands da eseguire sul resultset
		 */
		while (commandXls.hasNext())
		{
			Element command = (Element) commandXls.next();
			RsIteratorConfigCmds beanRsI = new RsIteratorConfigCmds();
			
			//rule
			//beanRsI.setCmd((Command) CommandFactory.getCommand(command.getAttributeValue("name"), true));
			RCommandFactory rf = (RCommandFactory) BaseCommandFactory.getFactory("R");
			beanRsI.setCmd((Command) rf.getCommand(command.getAttributeValue("name"), true));

			Integer livelloAnomalie = 1;
			if (command.getAttributeValue("livelloAnomalie")!=null)
				livelloAnomalie=new Integer(command.getAttributeValue("livelloAnomalie"));
			if (livelloAnomalie==null)
				livelloAnomalie = 1;
			beanRsI.setLivelloAnomalie(livelloAnomalie);
			
			List<RsIteratorConfigP> cmdParams = new ArrayList<RsIteratorConfigP>();
			Iterator params = command.getChildren("param").iterator();
			while (params.hasNext())
			{
				Element param = (Element) params.next();
				RsIteratorConfigP bconfig = new RsIteratorConfigP();
				bconfig.setId(param.getAttributeValue("id"));
				if (param.getAttribute("value")!=null)
					bconfig.setValue(param.getAttributeValue("value"));
				else {
					CDATA valore = new CDATA(param.getText());
					bconfig.setValue(valore.getText());
				}
				bconfig.setDest(param.getAttributeValue("dest"));
				bconfig.setType(param.getAttributeValue("type"));
				cmdParams.add(bconfig);
			}
			beanRsI.setParam(cmdParams);
			commands.add(beanRsI);
		}

		/*
		 * chiavi del resultset iterato
		 */
		Element key = root.getChild("key");
		if(key!=null)
		{
			tableName = key.getAttributeValue("table");
			keyColumns = new ArrayList<String>();
			Iterator columns = key.getChildren().iterator();
			while (columns.hasNext())
			{
				Element column = (Element) columns.next();
				keyColumns.add(column.getAttributeValue("name"));
			}
		}		
		
	}


	/**
	 * @return nome della connessione sulla quale i command chiamati operano
	 */
	public String getConnectionName()
	{
		return connectionName;
	}

	public static void main(String[] args) {
		String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><RsIterator connection=\"DWH\"><key><column name=\"NOME COLONNA IN CHIAVE\"/>	<column name=\"NOME COLONNA IN CHIAVE\"/></key><commands>  <command name=\"it.webred.rulengine.brick.AssenzaDatiCf\">   <param id=\"field\" value=\"VALORE CAMPO DEL RESULTSET\" dest=\"NOME PARAMETRO SUL COMMAND CHIAMATO\" type=\"TIPO PARAMETRO DEL COMMAND CHIAMATO\"/>   <param id=\"const\" value=\"VALORE DELLA COSTANTE\" dest=\"NOME PARAMETRO DEL COMMAND CHIAMATO\" type=\"TIPO PARAMETRO DEL COMMAND CHIAMATO\"/>  </command></commands></RsIterator>";
		try
		{
			@SuppressWarnings("unused") Object type = TypeFactory.getType(xml,"it.webred.rulengine.type.RsIteratorConfig");
			if(type instanceof it.webred.rulengine.type.RsIteratorConfig)
				log.debug("FUNZIONA!!!!");
			else
				log.debug("NON FUNZIONA!!!!");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			log.error(e);
		}
	}


	public String getTableName()
	{
		return tableName;
	}
}
