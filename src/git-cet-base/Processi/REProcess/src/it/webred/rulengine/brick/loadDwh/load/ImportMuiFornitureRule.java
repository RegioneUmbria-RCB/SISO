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
import it.webred.rulengine.brick.loadDwh.load.muiForniture.MuiFornitureEnv;
import it.webred.rulengine.brick.loadDwh.load.muiForniture.MuiFornitureFiles;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

public class ImportMuiFornitureRule extends Command implements Rule {
	
	private static Logger log = Logger.getLogger(ImportMuiFornitureRule.class.getName());
	
	public ImportMuiFornitureRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}
	
	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;
		
		try {
			MuiFornitureEnv env = new MuiFornitureEnv((String)ctx.get("connessione"), ctx);
			MuiFornitureFiles<MuiFornitureEnv> muif = new MuiFornitureFiles<MuiFornitureEnv>(env);
			T2<String, List<RAbNormal>> msg = muif.avviaImportazioneFiles();
			//se non ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(muif.RETURN_NO_FILE)) {
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
