package it.webred.rulengine.brick.loadDwh.load;

import it.webred.rulengine.Command;



import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.loadDwh.load.bucapafm.BucapAfmTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.bucapafm.BucapAfmTipoRecordFiles;
import it.webred.rulengine.brick.loadDwh.load.bucapafm.bean.Testata;


import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ImportPraticheEdiliBucapAfmRule extends Command implements Rule    {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportPraticheEdiliBucapAfmRule.class.getName());

	public ImportPraticheEdiliBucapAfmRule(BeanCommand bc) {
		super(bc);
	}
	
	public ImportPraticheEdiliBucapAfmRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@SuppressWarnings("unchecked")
	public CommandAck run(Context ctx) throws CommandException {
		
		CommandAck retAck = null;
		
		try {

			BucapAfmTipoRecordEnv env = new BucapAfmTipoRecordEnv((String)ctx.get("connessione") , ctx);

			BucapAfmTipoRecordFiles<BucapAfmTipoRecordEnv<Testata>> bucapAfm = new BucapAfmTipoRecordFiles<BucapAfmTipoRecordEnv<Testata>>(env);
			T2<String, List<RAbNormal>> msg = bucapAfm.avviaImportazioneFiles();
			
			//se non ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(bucapAfm.RETURN_NO_FILE)) {
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
