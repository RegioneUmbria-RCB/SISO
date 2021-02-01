package it.webred.rulengine.brick.loadDwh.load.cnc.flusso290;


import it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.bean.CNCTestata;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

import org.apache.log4j.Logger;

public class ImportFlusso290 <T extends  Flusso290Env<?>>   extends  ConcreteImport<T> {


	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportFlusso290.class.getName());

	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {		
		return new Flusso290Env<Flusso290TipoRecordEnv<CNCTestata>>((Flusso290TipoRecordEnv) ei);
	}

	@Override
	public boolean normalizza(String belfiore) throws RulEngineException {
		
		return true;
	}

	@Override
	public GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
