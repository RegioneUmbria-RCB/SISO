package it.webred.rulengine.type;



import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.BaseType;
import it.webred.rulengine.type.def.DeclarativeType;
import it.webred.rulengine.type.def.TypeFactory;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * Rappresenta un tipo di dato (parametro) complesso, formato da una lista di parametri
 * di numero arbitrario.
 * Viene utilizzato da brick che possono ricevere in input un numero variabile di parametri.
 * La forma dell'xml che lo rappresenta in configurazione è:
 * <complexParam>
 *	<param type="" name="param1"> valore </param>
 *	<param name="param2"> [[nomevariabile1]]
 *  </param>
 * </complexParam>
 * L'xml accetta al posto di "valore" un segnaposto rappresentante un tipo dichiarativo.
 * Nel caso che il parametro venga utilizzato in una catena, questo segnaposto viene sostituito con il valore di una variabile o costante.
 * L'attributo type viene ignorato e viene ereditato il tipo della variabile o costante assegnata. 
 * 
 * @author marcoric
 * @version $Revision: 1.5 $ $Date: 2009/10/09 07:21:20 $
 */
public class ComplexParam extends BaseType
{
	private static final Logger log = Logger.getLogger(ComplexParam.class.getName());
	
	public final static String ENCODING ="ISO-8859-1";
	LinkedHashMap<String,ComplexParamP> params;

	// l'hashmap viene valorizzata all'interno della typefactory
	// e serve nel cosa in cui un complexparam contiene al suo interno un complexparam 
	// che ha la necessità di accedere a variabili
	// per esempio:
	/* 
         <complexParam>
			<param type="java.lang.String" name="param1">
        		select 1 from dual where ? is not null
        	</param>
			<param type="it.webred.rulengine.type.ComplexParam" name="param2">
				<![CDATA[ 
            		<complexParam>
	      				<param type="java.lang.String" name="param3">
                   			[[COD_ENTE]]
              			</param>
            	</complexParam>
				]]>
        	</param>
        </complexParam>
	 */
	HashMap<String, DeclarativeType> vars = new LinkedHashMap<String, DeclarativeType>();
	

	/**
	*	@param xml
	*	Accetta un xml di configurazione
	 * @throws DocumentException 
	*   
	*/
	@SuppressWarnings("unchecked")
	public ComplexParam(String xmlComplexParam, HashMap<String, DeclarativeType> var) throws Exception
	{ 
		super(xmlComplexParam);

		this.vars = var;
		
		params = new LinkedHashMap<String,ComplexParamP>();

		ByteArrayInputStream xml = new ByteArrayInputStream(xmlComplexParam.getBytes());
		
		/*
		SAXReader reader = new SAXReader();
		reader.setEncoding(ComplexParam.ENCODING);
      	Document doc = reader.read(xml);
      	List paramList = doc.selectNodes("./complexParam/param");
      	*/
      	
		javax.xml.parsers.DocumentBuilder builder = null;
		javax.xml.parsers.DocumentBuilderFactory factory =
            javax.xml.parsers.DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		builder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = null;
        try {
        	doc = builder.parse(xml);
        } catch (Exception e) {
        	log.error("Eccezione nell'istanza del ComplexParam per l'xml: " + 
        			new String(Charset.forName("UTF-8").encode(xmlComplexParam).array()));
        	throw e;
        }
        org.w3c.dom.Node root = doc.getFirstChild();
        NodeList paramList = ((org.w3c.dom.Element)root).getElementsByTagName("param");
        
        //for (Iterator iter = paramList.iterator(); iter.hasNext(); ) {
        for (int j=0; j<paramList.getLength(); j++) {
        	
            //Node paramNode = (Node) iter.next();
        	org.w3c.dom.Node paramNode = paramList.item(j);
            
            //valore preso dall'xml
            //Object valore = (String)paramNode.getText(); //@
            Object valore = paramNode.getTextContent(); //@
            
            //controlo se esiste in hashmap var (accedendo con key or.apache...@21aeda)
            
            DeclarativeType ob = var.get(valore);
            if(ob != null) {
            	// setto valore con l'oggetto var.getParam con key	
            	valore = ob.getValue();
            }
            
            //String type = paramNode.valueOf("@type");
            //String name = paramNode.valueOf("@name");
            NamedNodeMap attr = paramNode.getAttributes();
            
            org.w3c.dom.Node typeNode = attr.getNamedItem("type");
            String type =null;
            if (typeNode != null)
            	type = typeNode.getNodeValue();

            org.w3c.dom.Node nameNode = attr.getNamedItem("name");
            String name =null;
            if (nameNode != null)
            	name = nameNode.getNodeValue();
            else
            	throw new RulEngineException("Attributo name su tag Complexparam obbligatorio");


            ComplexParamP p = new ComplexParamP();
    		p.setName(name);
    		p.setType(type);
    		p.setValore(valore);
    		
    		if (vars.isEmpty())
    			addParam(type,name,TypeFactory.getType(valore,type));
    		else if (type!=null && !type.equals(""))
    			addParam(type,name,TypeFactory.getType(valore,type,vars));
    		else // nel caso che valore sia una variabile ([[nomevariabile]]) il tipo non viene valorizzato e viene preso quello della variabile
    			// nel metodo constructWithDeclarativeType
    			addParam(type,name,valore);
            
        }      	
		

	}
	
	public void addParam(String type, String name, Object valore) 
	{
		ComplexParamP p = new ComplexParamP();
		p.setName(name);
		p.setType(type);
		p.setValore(valore);

		params.put(name,p);
		
	}
	
	
	public Object getParam(String name) throws Exception 
	{
		ComplexParamP p = (ComplexParamP)params.get(name);
		
		// torno il valore del tipo giusto
		return TypeFactory.getType(p.getValore(),p.getType());
	}



	public LinkedHashMap<String, ComplexParamP> getParams()
	{
		return params;
	}

	public HashMap<String, DeclarativeType> getVars() {
		return vars;
	}

	public void setVars(HashMap<String, DeclarativeType> vars) {
		this.vars = vars;
	}
	
	public static String cleanParam(String param) {
		if (param == null || param.trim().equals("")) {
			return param;
		}
		StringBuffer sb = new StringBuffer();
		for (char c : param.toCharArray()) {
			boolean remove = false;
			String xmlCivicoComplex = "<complexParam><param type='java.lang.String' name='test'>" + c + "</param></complexParam>";
			try {
				ComplexParam civicoComposto = (ComplexParam)TypeFactory.getType(xmlCivicoComplex, "it.webred.rulengine.type.ComplexParam");
			} catch (Exception e) {
				remove = true;
				log.debug("Il carattere " + c + " sarà rimosso in quanto non inseribile in ComplexParam");
			}
			if (!remove) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	

	
	
	
	
}
