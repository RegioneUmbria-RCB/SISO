package it.webred.rulengine.brick.loadDwh.load.cnc.statoriscossione;


import it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.bean.CNCTestata;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

import org.apache.log4j.Logger;

public class ImportStatoRisc <T extends  FlussoStatoRiscEnv<?>>   extends  ConcreteImport<T> {


	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportStatoRisc.class.getName());

	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {		
		return new FlussoStatoRiscEnv<FlussoStatoRiscTipoRecordEnv<CNCTestata>>((FlussoStatoRiscTipoRecordEnv) ei);
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
