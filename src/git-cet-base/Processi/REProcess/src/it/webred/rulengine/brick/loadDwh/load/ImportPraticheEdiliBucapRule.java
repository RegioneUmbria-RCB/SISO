package it.webred.rulengine.brick.loadDwh.load;

import it.webred.rulengine.Command;


import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.loadDwh.load.bucap.BucapTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.bucap.BucapTipoRecordFiles;
import it.webred.rulengine.brick.loadDwh.load.bucap.bean.Testata;


import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ImportPraticheEdiliBucapRule extends Command implements Rule    {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportPraticheEdiliBucapRule.class.getName());

	public ImportPraticheEdiliBucapRule(BeanCommand bc) {
		super(bc);
	}
	
	public ImportPraticheEdiliBucapRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@SuppressWarnings("unchecked")
	public CommandAck run(Context ctx) throws CommandException {
		
		CommandAck retAck = null;
		
		try {

			BucapTipoRecordEnv env = new BucapTipoRecordEnv((String)ctx.get("connessione") , ctx);

			BucapTipoRecordFiles<BucapTipoRecordEnv<Testata>> bucap = new BucapTipoRecordFiles<BucapTipoRecordEnv<Testata>>(env);
			T2<String, List<RAbNormal>> msg = bucap.avviaImportazioneFiles();
			
			//se non ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(bucap.RETURN_NO_FILE)) {
				retAck = new NotFoundAck(msg.firstObj);
			}
			else {
				retAck = new ApplicationAck(msg.firstObj);
			}
			
		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			retAck = new ErrorAck(e);
		}
		
		return retAck;
	}
	
}
