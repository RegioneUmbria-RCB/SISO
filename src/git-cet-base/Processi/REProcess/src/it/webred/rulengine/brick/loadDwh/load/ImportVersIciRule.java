package it.webred.rulengine.brick.loadDwh.load;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.loadDwh.load.versamenti.dm.ici.VersIciEnv;
import it.webred.rulengine.brick.loadDwh.load.versamenti.dm.ici.VersIciFiles;
import it.webred.rulengine.brick.loadDwh.load.versamenti.poste.tarsu.VersPosteEnv;
import it.webred.rulengine.brick.loadDwh.load.versamenti.poste.tarsu.VersPosteFiles;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ImportVersIciRule extends Command implements Rule {
	private static Logger log = Logger.getLogger(ImportVersIciRule.class.getName());

	public ImportVersIciRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;
		
		try {
			VersIciEnv env = new VersIciEnv((String)ctx.get("connessione"), ctx);
			VersIciFiles<VersIciEnv> vers = new VersIciFiles<VersIciEnv>(env);
			T2<String, List<RAbNormal>> msg = vers.avviaImportazioneFiles();
			
			//retAck = new ApplicationAck(msg.firstObj);
			
			//se nn ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(vers.RETURN_NO_FILE)) {
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

