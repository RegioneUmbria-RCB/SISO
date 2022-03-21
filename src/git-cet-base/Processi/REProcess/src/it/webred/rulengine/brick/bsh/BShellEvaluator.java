package it.webred.rulengine.brick.bsh;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.db.model.RRuleParamOut;
import it.webred.rulengine.impl.ContextBase;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Il brick permette - data una espressione con sintassi BeanShell e una lista
 * di parametri in ingresso - di effettuare la valutazione dell'espressione. Un
 * classico esempio di espressione potrebbe essere: if (p1.equals(p2)) return 1;
 * else return 0; Oppure: int i=p1.intValue();int ii=p2.intValue();if (i!=ii)
 * return 1; else return 0; Il rispettivo parametro di configurazione sarebbe;
 * <complexParam> <param type='java.lang.Integer' name='p1'>10</param> <param
 * type='java.lang.Integer' name='p2'>10</param> </complexParam>
 * 
 * <p>
 * Si noti come all'inteno dello script BeanShell i parametri passati vengono
 * visti con il loro reale tipo java. Uno script BeanShell può essere dunque
 * considerato un frammento di codice java.
 * 
 * <p>
 * Un altro esempio: espressione risultato= p1 + p2 restituisce nel contesto la
 * somma di p1 e p2.
 * 
 * Il brick torna un RejectAck nel contesto nel caso che i parametri o
 * l'espressione non vengano forniti correttamente. Viene restituito un ErrorAck
 * nel caso che vada in errore.
 * <p>
 * <p>
 * E' possiile inoltre restituire oggetti java da uno script bsh. La cns è che
 * vengano importati nello script. Di default il brivk importa in ogni script
 * gli Ack del rulengine: ErrorAck, RejectAck, ApplicationAck, questi possono
 * essere utilizzati all'interno di ogni script, esempio:
 * <p>
 * int i=topolino.intValue();int ii=minni.intValue();if (i!=ii) return new
 * ErrorAck(\"Test errore\"); else return 0; Gli ack lanciati da uno script
 * vengono restituiti direttamente al rulengine come fossero ack di un command.
 * 
 * Lo script può restituire oggetti con la clausola return , se si omette return
 * lo scipt restituisce il risultato dell'ultima operazione.
 * 
 * Un possibile oggetto di ritorno è rappresentato dall'oggetto
 * it.webred.rulengine.brick.bsh.Result che unisce un CommandAck e un Object
 * result esempio:
 * <p>
 * int i=topolino.intValue();int ii=minni.intValue();if (i!=ii) return new
 * ErrorAck(\"Test errore\"); else return new Result(new
 * ApplicationAck(\"confronto di i e ii eseguito con successo\",0));
 * 
 * @author marcoric
 * @version $Revision: 1.3 $ $Date: 2010/09/28 12:12:27 $
 */
public class BShellEvaluator extends Command implements Rule
{
	private static final Logger log = Logger.getLogger(BShellEvaluator.class.getName());

	/**
	 * @param bc
	 */
	public BShellEvaluator(BeanCommand bc)
	{
		super(bc);
	}
	
	

	public BShellEvaluator(BeanCommand bc, Properties jrulecfg) {
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
		
		String espressione = null;
		//ScriptBeanShell script = (ScriptBeanShell) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
		ComplexParam script = (ComplexParam)ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
		//log.debug(script.getStringConfig());
		
		try {
			HashMap<String,ComplexParamP> shscript = script.getParams();
			Set set = shscript.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Entry entry = (Entry)it.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				espressione = o.toString();
			}
			
			//String espressione = script.getStringConfig();
			log.info("Script shell:\n"+espressione);		
			
		}catch(Exception e)	{
			RejectAck ra = new RejectAck("espressione non corretta");
			log.warn("RejectAck: "+ra.getMessage());
			return ra;
		}
		
		ComplexParam parametriEspressione = null;
		try {
			parametriEspressione = (ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));

 		} catch (Exception e)	{
			RejectAck ra = new RejectAck("parametriEspressione non corretti");
			log.warn("RejectAck: "+ra.getMessage());
			return ra;
		}

		if (espressione == null || espressione.trim().equalsIgnoreCase(""))	{
			RejectAck ra = new RejectAck("Espressione non fornita");
			log.warn("RejectAck: "+ra.getMessage());
			return ra;
		}
		
		espressione = "import it.webred.rulengine.brick.bean.ErrorAck;" + espressione;
		espressione = "import it.webred.rulengine.brick.bean.ApplicationAck;" + espressione;
		espressione = "import it.webred.rulengine.brick.bean.RejectAck;" + espressione;
		espressione = "import it.webred.rulengine.brick.bsh.Result;" + espressione;
		
		//log.debug("Script shell:\n"+espressione);
		
		Interpreter i = new Interpreter(); // Construct an interpreter
		
		// i parametri potrebbero non essere stati forniti, non sono infatti obbligatori
		if (parametriEspressione!=null) {
			try {
				HashMap<String, ComplexParamP> m = parametriEspressione.getParams();
				for (Map.Entry<String, ComplexParamP> item : m.entrySet())				{
					Object paramVal = item.getValue().getValore(); // marcoric testare TypeFactory.getType(item.getValue().getValore(), item.getValue().getType());
					log.debug("paramVal: "+paramVal.toString());
					i.set(item.getValue().getName(), paramVal);
				}
	
			} catch (EvalError e)	{
				ErrorAck ea = new ErrorAck("Parametri espressione [" + espressione + "] non correttamente valorizzati." + e.getMessage());
				log.warn("ErrorAck: "+ea.getMessage());
				return (ea);
			}catch (Exception e) {
				ErrorAck ea = new ErrorAck("Errore grave durante la valorizzazione di [" + espressione + "]" + e.getMessage());
				log.warn("ErrorAck: "+ea.getMessage());
				return (ea);
			}
		}

		try {
			Object ret = null;
			Object result = i.eval(espressione);
			CommandAck cmdAck = null;
			
			log.debug("Esito beanshell: "+result.toString());
			
			if (result instanceof ErrorAck)
				cmdAck = new ErrorAck(((ErrorAck) result).getMessage());
			else if (result instanceof RejectAck)
				cmdAck = new RejectAck(((RejectAck) result).getMessage());
			else if (result instanceof ApplicationAck)
				cmdAck = new ApplicationAck(((ApplicationAck) result).getMessage());
			else if (result instanceof Result)
			{
				cmdAck = ((Result) result).getAck();
				ret = ((Result) result).getResult();
			}
			else{
				ret = result;
			}

			List parametriOut = this.getParametersOut(_jrulecfg);
			ctx.put(((RRuleParamOut) parametriOut.get(0)).getDescr(), ret);

			if (cmdAck != null) {
				log.debug("CommandAck: "+cmdAck.getMessage());
				return cmdAck;
			}

		}catch (EvalError ee)	{
			log.error("Espressione " + espressione + " non valutata correttamente " + ee.getMessage());
			return new ErrorAck("Espressione " + espressione + " non valutata correttamente " + ee.getMessage());
		}

		return new ApplicationAck("Valutazione OK di [" + espressione + "]");

	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		BShellEvaluator p = new BShellEvaluator(new BeanCommand());
		Context c = new ContextBase();
		c.put("espressione", "int i=topolino.intValue();int ii=minni.intValue();if (i!=ii) return new ErrorAck(\"Test errore\"); else return new Result(new ApplicationAck(\"confronto di i e ii eseguito con successo\"),0);");
		try
		{

			c.put("parametriEspressione", TypeFactory.getType("<complexParam><param type='java.lang.Integer' name='topolino'>10</param><param type='java.lang.Integer' name='minni'>10</param></complexParam>", "it.webred.rulengine.type.ComplexParam"));
		}
		catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		c.put("ack", "E");
		try
		{
			p.run(c);
			System.out.println(c.get("result"));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			log.error(e);
		}

	}

}
