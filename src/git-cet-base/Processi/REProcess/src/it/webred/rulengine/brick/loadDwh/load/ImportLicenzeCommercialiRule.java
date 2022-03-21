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
import it.webred.rulengine.brick.loadDwh.load.licenzecommerciali.LicenzeCommercialiTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.licenzecommerciali.LicenzeCommercialiStandardFiles;
import it.webred.rulengine.brick.loadDwh.load.licenzecommerciali.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ImportLicenzeCommercialiRule extends Command implements Rule {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportLicenzeCommercialiRule.class.getName());
	
	public ImportLicenzeCommercialiRule(BeanCommand bc) {		
		super(bc);
	}
	
	

	public ImportLicenzeCommercialiRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}



	public CommandAck run(Context ctx)	throws CommandException {	
		CommandAck retAck = null;
		
		try {

			LicenzeCommercialiTipoRecordEnv env = new LicenzeCommercialiTipoRecordEnv((String)ctx.get("connessione") , ctx);			
			LicenzeCommercialiStandardFiles<LicenzeCommercialiTipoRecordEnv<TestataStandardFileBean>> licenzeCommerciali = new LicenzeCommercialiStandardFiles<LicenzeCommercialiTipoRecordEnv<TestataStandardFileBean>>(env);
			
			log.info("INIZIO!");
			
			T2<String, List<RAbNormal>> msg = licenzeCommerciali.avviaImportazioneFiles();

			log.info("FINE!");
			
			
			//se nn ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(licenzeCommerciali.RETURN_NO_FILE)) {
				retAck = new NotFoundAck(msg.firstObj);
			}
			else {
				retAck = new ApplicationAck(msg.firstObj);
			}
			
			//ApplicationAck ack = new ApplicationAck(msg.firstObj);
			//ack.setAbn(msg.secondObj);
			///return ack;
			
		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			retAck = new ErrorAck(e);
		}
		
		return retAck;
	}
	
}
