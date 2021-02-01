package it.webred.rulengine.brick.loadDwh.load;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.loadDwh.load.pubblicita.PubblicitaStandardFiles;
import it.webred.rulengine.brick.loadDwh.load.pubblicita.PubblicitaTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ImportPubblicitaRule extends Command implements Rule {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportPubblicitaRule.class.getName());

	public ImportPubblicitaRule(BeanCommand bc) {		
		super(bc);
	}
	
	

	public ImportPubblicitaRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}



	public CommandAck run(Context ctx)	throws CommandException {		
		CommandAck retAck = null;
		
		try {
			PubblicitaTipoRecordEnv env = new PubblicitaTipoRecordEnv((String)ctx.get("connessione"), ctx);			
			PubblicitaStandardFiles<PubblicitaTipoRecordEnv<TestataStandardFileBean>> pub = new PubblicitaStandardFiles<PubblicitaTipoRecordEnv<TestataStandardFileBean>>(env);
			T2<String, List<RAbNormal>> msg = pub.avviaImportazioneFiles();
			
			//ApplicationAck ack = new ApplicationAck(msg.firstObj);
			//ack.setAbn(msg.secondObj);
			//return ack;
			
			//se nn ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(pub.RETURN_NO_FILE)) {
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


	
