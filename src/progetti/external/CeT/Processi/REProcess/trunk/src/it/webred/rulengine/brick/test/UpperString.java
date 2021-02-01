package it.webred.rulengine.brick.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;

import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;

import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.Variable;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.impl.TypeFactory;

public class UpperString extends Command implements Rule {

	private static final Logger log = Logger.getLogger(UpperString.class.getName());
	
	

	public UpperString(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	@Override
	public CommandAck run(Context ctxRE) throws CommandException {
		
		CommandAck cak = null;
		String paramNew = null;
		
		try {			
			//gestione param in
			//1
			String param = (String) ctxRE.get(_jrulecfg.getProperty("rengine.rule.param.in.1.descr"));
			//log.debug("[PARAM 1]valore input: "+param);

			//2
			ComplexParam param2 = (ComplexParam) ctxRE.get(_jrulecfg.getProperty("rengine.rule.param.in.2.descr"));
			//log.debug(param2.getStringConfig());
			
			HashMap<String,ComplexParamP> pars2 = param2.getParams();
			Set set2 = pars2.entrySet();
			Iterator it2 = set2.iterator();
			int i2 =1;
			while (it2.hasNext()) {
				Entry entry = (Entry)it2.next();
				ComplexParamP p = (ComplexParamP)entry.getValue();
				Object o = TypeFactory.getType(p.getValore(),p.getType());
				log.debug(o.toString());
				paramNew = o.toString().toUpperCase();
			}
						
			
			cak = new ApplicationAck("Stringa UPPERTA");

			//gestione param out per
			ctxRE.put(_jrulecfg.getProperty("rengine.rule.param.out.1.descr"), paramNew);
			
		}catch(Exception e) {
			log.debug("Eccezione: "+e.getMessage(),e);
			cak = new ErrorAck("Errore UPPERAGGIO");
		}
		
		return cak;
	}

}
