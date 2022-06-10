package it.webred.rulengine.brick.loadDwh.load;

import it.webred.rulengine.Command;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.loadDwh.load.sciajpe.SciaJpeTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.sciajpe.SciaJpeTipoRecordFiles;
import it.webred.rulengine.brick.loadDwh.load.sciajpe.bean.Testata;

import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ImportPraticheEdiliSciaJpeRule extends Command implements Rule    {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportPraticheEdiliSciaJpeRule.class.getName());

	public ImportPraticheEdiliSciaJpeRule(BeanCommand bc) {
		super(bc);
	}
	
	public ImportPraticheEdiliSciaJpeRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@SuppressWarnings("unchecked")
	public CommandAck run(Context ctx) throws CommandException {
		
		CommandAck retAck = null;
		
		try {

			SciaJpeTipoRecordEnv env = new SciaJpeTipoRecordEnv((String)ctx.get("connessione") , ctx);

			SciaJpeTipoRecordFiles<SciaJpeTipoRecordEnv<Testata>> sciaJpe = new SciaJpeTipoRecordFiles<SciaJpeTipoRecordEnv<Testata>>(env);
			T2<String, List<RAbNormal>> msg = sciaJpe.avviaImportazioneFiles();
			
			//se non ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(sciaJpe.RETURN_NO_FILE)) {
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
