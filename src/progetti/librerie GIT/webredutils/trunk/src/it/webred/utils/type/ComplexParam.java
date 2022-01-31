package it.webred.utils.type;





import it.webred.utils.type.bean.ComplexParamP;
import it.webred.utils.type.def.BaseType;
import it.webred.utils.type.def.DeclarativeType;
import it.webred.utils.type.def.TypeFactory;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

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
 * @version $Revision: 1.1 $ $Date: 2009/02/06 14:51:25 $
 */
public class ComplexParam extends BaseType
{
	
	LinkedHashMap<String,ComplexParamP> params;

	// l'hashmap viene valorizzata all'interno della typefactory
	// e serve nel cosa in cui un complexparam contiene al suo interno un complexparam che ha la necessità di accedere a variabili
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
	public ComplexParam(String xmlComplexParam, HashMap<String, DeclarativeType> var) throws Exception
	{ 
		super(xmlComplexParam);

		this.vars = var;
		
		params = new LinkedHashMap<String,ComplexParamP>();

		ByteArrayInputStream xml = new ByteArrayInputStream(xmlComplexParam.getBytes());
		SAXReader reader = new SAXReader();
      	Document doc = reader.read(xml);
      	List paramList = doc.selectNodes("./complexParam/param");
        for (Iterator iter = paramList.iterator(); iter.hasNext(); ) {
            Node paramNode = (Node) iter.next();
            
            String valore = paramNode.getText();
            String type = paramNode.valueOf("@type");
            String name = paramNode.valueOf("@name");
            
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
	
	

	
	
	
	
}
