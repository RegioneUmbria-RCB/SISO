package it.webred.rulengine.brick.core;


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
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ArrayOfByte;
import it.webred.utils.FtpUtils;

/**
 * Prendendo in input un array di byte si occupa di inviarlo in un server ftp
 * @author marcoric
 * @version $Revision: 1.3 $ $Date: 2010/09/28 12:12:26 $
 */
public class InviaFileInFtp extends Command implements Rule
{
	private static final Logger log = Logger.getLogger(InviaFileInFtp.class.getName());
	
	
	public InviaFileInFtp(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public CommandAck run(Context ctx)
		throws CommandException
	{
		List parametriIn = this.getParametersIn(_jrulecfg);
		String host = (String) ctx.get(((RRuleParamIn) parametriIn.get(0)).getDescr());
		Integer port = (Integer) ctx.get(((RRuleParamIn) parametriIn.get(1)).getDescr());
		String user = (String) ctx.get(((RRuleParamIn) parametriIn.get(2)).getDescr());
		String password = (String) ctx.get(((RRuleParamIn) parametriIn.get(3)).getDescr());
		String nomefile = (String) ctx.get(((RRuleParamIn) parametriIn.get(4)).getDescr());
		ArrayOfByte filebis = (ArrayOfByte) ctx.get(((RRuleParamIn) parametriIn.get(5)).getDescr());
		String dir = (String) ctx.get(((RRuleParamIn) parametriIn.get(6)).getDescr());
	
		log.debug("Invio file su ftp");
		log.debug("host:"+host);
		log.debug("nomefile:"+nomefile);
		
		byte[] file = filebis.getArray();
		
		
		try
		{
			FtpUtils.uploadFile( host,port,user,password,nomefile,file,dir);
			
			return new ApplicationAck("Invio ftp File "+ nomefile + "effettuato su host " + host);
		}
		catch (Exception e)
		{
			log.error("Errore invio file su ftp",e);
			return new ErrorAck(e);
		} 
		
		
	}

}
