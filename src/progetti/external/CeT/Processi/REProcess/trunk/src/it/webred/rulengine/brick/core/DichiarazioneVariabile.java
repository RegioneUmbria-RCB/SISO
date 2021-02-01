package it.webred.rulengine.brick.core;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.Utils;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;

import it.webred.rulengine.db.model.RRuleParamOut;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.Variable;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.DeclarativeType;
import it.webred.rulengine.type.def.TypeFactory;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

/**
 * @author marcoric
 * @version $Revision: 1.5 $ $Date: 2010/09/28 12:12:26 $
 */
public class DichiarazioneVariabile extends Command implements Rule
{
	
	HashMap<String, DeclarativeType> dt = new HashMap<String, DeclarativeType>();
	

	private static final Logger log = Logger.getLogger(DichiarazioneVariabile.class.getName());
	
	public DichiarazioneVariabile(BeanCommand bc, Properties jrulecfg) {
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
		try {
			//List parametriIn = this.getParametersIn(_jrulecfg);
			//Object valoreVariabile = ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Object valoreVariabile = ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			
			
			//String nomeVariabile = (String)ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			String nomeVariabile = null;
			ComplexParam nomeVar = 
				(ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			
			HashMap<String,ComplexParamP> pars2 = nomeVar.getParams();
			Set set2 = pars2.entrySet();
			Iterator it2 = set2.iterator();
			while (it2.hasNext()) {
				Entry entry = (Entry)it2.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				nomeVariabile = o.toString();
			}
			
			log.debug("Nome variabile: "+nomeVariabile);
			
			
			
			Variable ret;
			if (ctx.getDeclarativeType(nomeVariabile)!=null && ctx.getDeclarativeType(nomeVariabile) instanceof Variable) {
				ret =  (Variable)ctx.getDeclarativeType(nomeVariabile);
				//log.debug("Variabile già nel contesto (DeclarativeType)");
			}
			else {
				//log.debug("Variabile non presente nel contesto (DeclarativeType)");
				String type = null;
				try
				{
					// prevengo il null
					if (valoreVariabile==null){
						//verifico se il valore e' preimpostato in re.properties
						String codComando = (String)ctx.getDeclarativeType("RULENGINE.COD_COMMAND").getValue();
						String varVal = Utils.getProperty(codComando+"."+nomeVariabile+".valore");
						String varType = Utils.getProperty(codComando+"."+nomeVariabile+".tipo");
						if (varVal != null && varType != null){
							valoreVariabile = varVal;
							type = varType;
						}
						else	
							type = "java.lang.Object";
					}	
					else
						type = valoreVariabile.getClass().getName();
				
					ret  = new Variable(nomeVariabile, type , valoreVariabile);
					//log.debug("Variabile: "+type+" - "+valoreVariabile);
				}
				catch (Exception e)
				{
					ErrorAck ba = new ErrorAck("Dichiarazione variabile " +nomeVariabile+" errata");
					return (ba);
				}
			}
			
			ctx.put(_jrulecfg.getProperty("rengine.rule.param.out.1.descr"), ret);
			return (new ApplicationAck("Variabile " + nomeVariabile + " settata :" + ret.getValue() ));
		
		}catch (Exception e)
		{
			log.error("Errore regola: "+e.getMessage(),e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
	}

}