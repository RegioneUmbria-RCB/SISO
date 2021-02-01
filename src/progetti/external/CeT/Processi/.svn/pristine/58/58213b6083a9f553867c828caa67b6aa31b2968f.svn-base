package it.webred.rulengine.brick.core;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.db.model.RRuleParamOut;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ArrayOfByte;
import it.webred.utils.RsUtils;

public class EsportaResultsetInFile extends Command implements Rule {
	private static final Logger log = Logger.getLogger(EsportaResultsetInFile.class.getName());
	
	public EsportaResultsetInFile(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	@SuppressWarnings("unchecked")
	@Override
	public CommandAck run(Context ctx) throws CommandException {
		try {
			log.debug("Esportazione resultset");

			List parametriIn = this.getParametersIn(_jrulecfg);
			ResultSet rs = (ResultSet) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
			String separator = (String) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
			

			byte[] b = RsUtils.getBytesFromRs(rs, separator);

			ArrayOfByte aob = new ArrayOfByte(b);

			List parametriOut = this.getParametersOut(_jrulecfg);
			ctx.put(((RRuleParamOut) parametriOut.get(0)).getDescr(), aob);

		}
		catch (Exception e)
		{
			log.error("Esportazione resultset non riuscita", e);
		}

		return new ApplicationAck("Esportazione resultset riscita");
	}

}
