package it.webred.rulengine.brick.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.db.model.RRuleParamOut;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

/**
 * Accetta in input un Resulset e ne legge il primo valore se disponibile.
 * Può essere utile soprattutto dopo aver effettuato una select max o count.
 * In questo caso può sostituire l'RsIteratorAvanzato per la lettura del Resultset
 * @author marcoric
 * @version $Revision: 1.5 $ $Date: 2010/09/28 12:12:26 $
 */
public class LeggiSingoloValoreResultset extends Command implements Rule
{
	public LeggiSingoloValoreResultset(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}



	private static final Logger log = Logger.getLogger(LeggiSingoloValoreResultset.class.getName());

	

	@SuppressWarnings("unchecked")
	@Override
	public CommandAck run(Context ctx)
		throws CommandException
	{
		log.debug("Lettura singolo valore Resulset");

		List parametriIn = this.getParametersIn(_jrulecfg);
		ResultSet rs = (ResultSet) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());

		CommandAck cmdAck = null;
		Object ret = null;
		String noRecord="Risultato della selezione:";
		try
		{
			if (rs != null){
				if(rs.next())
					ret = rs.getObject(1);
				else
					noRecord="La selezione non ha prodotto nessun dato ";				
			}else{
				log.error("Resulset nullo - perché????");
			}
		}

				
		catch (SQLException e)
		{
			cmdAck = new ErrorAck(e);
			log.error("Errore lettura valore resultset",e);
		}
		
		List parametriOut = this.getParametersOut(_jrulecfg);
		ctx.put(((RRuleParamOut) parametriOut.get(0)).getDescr(), ret);
		
		if (ret!=null)
			cmdAck = new ApplicationAck(noRecord + ret.toString());
		else
			cmdAck = new ApplicationAck(noRecord);
		return cmdAck;
	}

}
