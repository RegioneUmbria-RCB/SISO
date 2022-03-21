package it.webred.rulengine.brick.loadDwh.load;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.brick.loadDwh.load.demografia.vie.DemogVieEnv;
import it.webred.rulengine.brick.loadDwh.load.demografia.vie.DemogVieFiles;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

public class ImportDemogVieRule extends Command implements Rule {
	
	private static Logger log = Logger.getLogger(ImportDemogVieRule.class.getName());
	
	public ImportDemogVieRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;
		
		try {
			DemogVieEnv env = new DemogVieEnv((String)ctx.get("connessione"), ctx);
			DemogVieFiles<DemogVieEnv> dvie = new DemogVieFiles<DemogVieEnv>(env);
			T2<String, List<RAbNormal>> msg = dvie.avviaImportazioneFiles();
			
			//retAck = new ApplicationAck(msg.firstObj);
			
			//se nn ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(dvie.RETURN_NO_FILE)) {
				retAck = new NotFoundAck(msg.firstObj);
			}
			else {
				retAck = new ApplicationAck(msg.firstObj);
			}
			
		}catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			retAck =  new ErrorAck(e);
		}
		
		return retAck;
	}

}
