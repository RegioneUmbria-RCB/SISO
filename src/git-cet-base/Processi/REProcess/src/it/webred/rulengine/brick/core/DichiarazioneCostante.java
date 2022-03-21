package it.webred.rulengine.brick.core;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.db.model.RRuleParamOut;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.Constant;

import java.util.List;
import java.util.Properties;

/**
 * Regola per l'utilizzo della costante all'interno di una Catena.
 * Una dichiarazione di costante deve essere effettuata fornendo alla regola
 * il nome, tipo e valore stringa della costante.
 * Il tipo deve appartenere ai tipi di dato che possiedono un costruttore stringa.
 * Si veda a questo proposito TypeFactory
 * <p>
 * Esempio di complexparam passato al command.
 * L'xml deve avere sempre sue parametri uno con name=nome e l'altro con name=valore (l'atributo type del primo deve essere sempre java.lang.String)
 * Il valore del parametro può essere anche il valore di una variabile di contesto della chain.
 * Esempio di costante con nome pluto e valore 2:
 * <complexParam>
 *	<param type="java.lang.String" name="nome">pluto</param>
 *	<param type="java.lang.Integer" name="valore">2</param>
 * </complexParam>
 * Esempio di costante con nome pluto e valore di una variabile di contesto var1
 * <complexParam>
 *	<param type="java.lang.String" name="nome">pluto</param>
 *	<param name="valore">[[var1]]</param>
 * </complexParam>
 * @author sisani
 * @version $Revision: 1.3 $ $Date: 2010/09/28 12:12:26 $
 */
public class DichiarazioneCostante extends Command implements Rule
{


	public DichiarazioneCostante(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.webred.rulengine.Command#run(it.webred.rulengine.Context)
	 */
	@SuppressWarnings("unchecked")
	public CommandAck run(Context ctx)
	{

		List parametriIn = this.getParametersIn(_jrulecfg);
		ComplexParam definizioneCostante = (ComplexParam) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
		Constant ret;
		String name = null;
		try
		{
			 name = (String)definizioneCostante.getParam("nome");
			 String type = (String)definizioneCostante.getParam("valore").getClass().getName();
			 Object value = definizioneCostante.getParam("valore");
			
			ret  = new Constant(name,type,value);
			
		}
		catch (Exception e)
		{
			ErrorAck ba = new ErrorAck("Errore nella creazione della costante " + name);
			return (ba);
		}
		List parametriOut = this.getParametersOut(_jrulecfg);
		ctx.put(((RRuleParamOut) parametriOut.get(0)).getDescr(), ret);
		return (new ApplicationAck("COSTANTE SETTATA!!!"));
	}

}
