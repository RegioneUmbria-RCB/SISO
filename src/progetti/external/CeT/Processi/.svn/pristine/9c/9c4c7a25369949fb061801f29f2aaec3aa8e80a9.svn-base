package it.webred.rulengine.brick.loadDwh.load;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ImportF24t2012Rule extends Command implements Rule {
	private static Logger log = Logger.getLogger(ImportF24t2012Rule.class.getName());

	public ImportF24t2012Rule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;
		
		try {
			it.webred.rulengine.brick.loadDwh.load.F24.t2012.v1.F24Env env = new it.webred.rulengine.brick.loadDwh.load.F24.t2012.v1.F24Env((String)ctx.get("connessione"), ctx);
			it.webred.rulengine.brick.loadDwh.load.F24.t2012.v1.F24Files<it.webred.rulengine.brick.loadDwh.load.F24.t2012.v1.F24Env> mod = new it.webred.rulengine.brick.loadDwh.load.F24.t2012.v1.F24Files<it.webred.rulengine.brick.loadDwh.load.F24.t2012.v1.F24Env>(env);
			T2<String, List<RAbNormal>> msg = mod.avviaImportazioneFiles();
			
			it.webred.rulengine.brick.loadDwh.load.F24.t2012.v2.F24Env imuEnv = new it.webred.rulengine.brick.loadDwh.load.F24.t2012.v2.F24Env((String)ctx.get("connessione"), ctx);
			it.webred.rulengine.brick.loadDwh.load.F24.t2012.v2.F24Files<it.webred.rulengine.brick.loadDwh.load.F24.t2012.v2.F24Env> modImu = new it.webred.rulengine.brick.loadDwh.load.F24.t2012.v2.F24Files<it.webred.rulengine.brick.loadDwh.load.F24.t2012.v2.F24Env>(imuEnv);
			T2<String, List<RAbNormal>> msgImu = modImu.avviaImportazioneFiles();
			
			it.webred.rulengine.brick.loadDwh.load.F24.t2012.tares.F24Env taresEnv = new it.webred.rulengine.brick.loadDwh.load.F24.t2012.tares.F24Env((String)ctx.get("connessione"), ctx);
			it.webred.rulengine.brick.loadDwh.load.F24.t2012.tares.F24Files<it.webred.rulengine.brick.loadDwh.load.F24.t2012.tares.F24Env> modTares = new it.webred.rulengine.brick.loadDwh.load.F24.t2012.tares.F24Files<it.webred.rulengine.brick.loadDwh.load.F24.t2012.tares.F24Env>(taresEnv);
			T2<String, List<RAbNormal>> msgTares = modTares.avviaImportazioneFiles();
			
			it.webred.rulengine.brick.loadDwh.load.F24.t2014.tasi.F24Env tasiEnv = new it.webred.rulengine.brick.loadDwh.load.F24.t2014.tasi.F24Env((String)ctx.get("connessione"), ctx);
			it.webred.rulengine.brick.loadDwh.load.F24.t2014.tasi.F24Files<it.webred.rulengine.brick.loadDwh.load.F24.t2014.tasi.F24Env> modTasi = new it.webred.rulengine.brick.loadDwh.load.F24.t2014.tasi.F24Files<it.webred.rulengine.brick.loadDwh.load.F24.t2014.tasi.F24Env>(tasiEnv);
			T2<String, List<RAbNormal>> msgTasi = modTasi.avviaImportazioneFiles();
			/*
			 * X Addizionale Comunale
			 */
			it.webred.rulengine.brick.loadDwh.load.F24.t2014.addCom.F24Env addComEnv = new it.webred.rulengine.brick.loadDwh.load.F24.t2014.addCom.F24Env((String)ctx.get("connessione"), ctx);
			it.webred.rulengine.brick.loadDwh.load.F24.t2014.addCom.F24Files<it.webred.rulengine.brick.loadDwh.load.F24.t2014.addCom.F24Env> modAddCom = new it.webred.rulengine.brick.loadDwh.load.F24.t2014.addCom.F24Files<it.webred.rulengine.brick.loadDwh.load.F24.t2014.addCom.F24Env>(addComEnv);
			T2<String, List<RAbNormal>> msgAddCom = modAddCom.avviaImportazioneFiles();
			
			//retAck = new ApplicationAck(msg.firstObj);
			
			//se nn ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(mod.RETURN_NO_FILE) && 
					msgImu.firstObj.equals(modImu.RETURN_NO_FILE) && 
					msgTares.firstObj.equals(modTares.RETURN_NO_FILE) &&
					msgTasi.firstObj.equals(modTasi.RETURN_NO_FILE) &&
					msgAddCom.firstObj.equals(modAddCom.RETURN_NO_FILE)
					) {
				retAck = new NotFoundAck(msg.firstObj + " " + msgImu.firstObj);
			}else {
				String msgAck = "";
				msgAck = !msg.firstObj.equals(mod.RETURN_NO_FILE) ? msg.firstObj  : msgAck;
				msgAck = !msgImu.firstObj.equals(modImu.RETURN_NO_FILE) ? msgImu.firstObj : msgAck;
				msgAck = !msgTares.firstObj.equals(modTares.RETURN_NO_FILE) ? msgTares.firstObj : msgAck;
				msgAck = !msgTasi.firstObj.equals(modTasi.RETURN_NO_FILE) ? msgTasi.firstObj : msgAck;
				msgAck = !msgAddCom.firstObj.equals(modAddCom.RETURN_NO_FILE) ? msgAddCom.firstObj : msgAck;
				
				retAck = new ApplicationAck(msgAck);
			}
			
		}catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			retAck =  new ErrorAck(e);
		}
		
		return retAck;
	}


}

