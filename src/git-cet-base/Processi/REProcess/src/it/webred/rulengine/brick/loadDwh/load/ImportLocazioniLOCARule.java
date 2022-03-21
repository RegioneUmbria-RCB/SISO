package it.webred.rulengine.brick.loadDwh.load;

import java.util.List;
import java.util.Properties;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.brick.loadDwh.load.locazioni.loca.LocazioniEnv;
import it.webred.rulengine.brick.loadDwh.load.locazioni.loca.LocazioniFiles;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

import org.apache.log4j.Logger;



public class ImportLocazioniLOCARule extends Command implements Rule {
	private static Logger log = Logger.getLogger(ImportLocazioniLOCARule.class.getName());

	public ImportLocazioniLOCARule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;
		
		try {
			LocazioniEnv env = new LocazioniEnv((String)ctx.get("connessione"), ctx);
			LocazioniFiles<LocazioniEnv> loca = new LocazioniFiles<LocazioniEnv>(env);
			T2<String, List<RAbNormal>> msg = loca.avviaImportazioneFiles();
			
			//retAck = new ApplicationAck(msg.firstObj);
			
			//se nn ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(loca.RETURN_NO_FILE)) {
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
