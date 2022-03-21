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
import it.webred.rulengine.brick.loadDwh.load.planimetrieComma340.PlanimetrieComma340Env;
import it.webred.rulengine.brick.loadDwh.load.planimetrieComma340.PlanimetrieComma340Files;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ImportPlanimetrieComma340Rule extends Command implements Rule {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportPlanimetrieComma340Rule.class.getName());

	public ImportPlanimetrieComma340Rule(BeanCommand bc) {		
		super(bc);
	}
	
	

	public ImportPlanimetrieComma340Rule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}



	public CommandAck run(Context ctx)	throws CommandException {
		
		CommandAck retAck = null;
		Connection conn = null;
		
		try {
			log.debug("Recupero parametro da contesto");
			//conn = ctx.getConnection((String)ctx.get("connessione"));
			
			PlanimetrieComma340Env env = new PlanimetrieComma340Env((String)ctx.get("connessione") , ctx);			
			PlanimetrieComma340Files<PlanimetrieComma340Env>  plan = new PlanimetrieComma340Files<PlanimetrieComma340Env>(env);
			T2<String, List<RAbNormal>> msg = plan.avviaImportazioneFiles();
			
			//se nn ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(plan.RETURN_NO_FILE)) {
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
