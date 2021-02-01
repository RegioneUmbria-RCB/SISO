package it.webred.rulengine.brick.core;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.db.model.RRuleParamOut;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.util.List;
import java.util.Properties;

/**
 * Regola per il controllo della lunghezza di una stringa che prende in input due 
 * parametri che sono, la stringa e un interno per la lunghezza da confrontare.
 * Una volata fatto il confronto ritorna true o false in un oggetto di tipo Boolean
 * 
 * @author sisani
 * @version $Revision: 1.3 $ $Date: 2010/09/28 12:12:26 $
 */
public class LunghezzaStringa extends Command implements Rule
{

	

	public LunghezzaStringa(BeanCommand bc, Properties jrulecfg) {
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

		try
		{
			List parametriIn = this.getParametersIn(_jrulecfg);
			List parametriOut = this.getParametersOut(_jrulecfg);
			String stringa = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			Integer len = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());

			boolean test = false;
			CommandAck aa = null;
			if(stringa == null || len == null)
			{
				RejectAck ra = new RejectAck("Non sono presenti i parametri per la regola LunghezzaStringa!");
				return (ra);
			}
			else if (stringa.length() == len.intValue()) {
				test = true;
				aa = new ApplicationAck("La lunghezza della Stringa è giusta! ("+ len.intValue()+")");
			} else {
				test = false;
				aa = new ApplicationAck("La lunghezza della Stringa non è "+ len.intValue());
			}
			
			ctx.put(((RRuleParamOut) parametriOut.get(0)).getDescr(), new Boolean(test));
			
			return aa;
		}
		catch (Exception e)
		{
			return new ErrorAck(e.getMessage());
		}
	}
}
