package it.webred.rulengine.brick.loadDwh.load;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.bean.CNCTestata;
import it.webred.rulengine.brick.loadDwh.load.cnc.statoriscossione.FlussoStatoRiscTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.cnc.statoriscossione.FlussoStatoRiscTipoRecordFiles;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.ContextBase;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ImportFlussoStatoRisc extends Command implements Rule    {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportFlussoStatoRisc.class.getName());

		
	public ImportFlussoStatoRisc(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;
		
		try {

			FlussoStatoRiscTipoRecordEnv env = null;
			env = new FlussoStatoRiscTipoRecordEnv((String)ctx.get("connessione") , ctx);
			FlussoStatoRiscTipoRecordFiles<FlussoStatoRiscTipoRecordEnv<CNCTestata>> srisc = new FlussoStatoRiscTipoRecordFiles<FlussoStatoRiscTipoRecordEnv<CNCTestata>>(env);
			T2<String, List<RAbNormal>> msg = srisc.avviaImportazioneFiles();
			
			//ApplicationAck ack = new ApplicationAck(msg.firstObj);
			//ack.setAbn(msg.secondObj);
			//return ack;
			
			//se nn ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(srisc.RETURN_NO_FILE)) {
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
