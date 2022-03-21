package it.webred.rulengine.brick.loadDwh.load.acqua.AdE.v1;

import it.webred.rulengine.brick.loadDwh.load.acqua.AdE.AcquaTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

import org.apache.log4j.Logger;

public class ImportAcqua<T extends  Env<?>> extends ConcreteImport<T> {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportAcqua.class.getName());
	
	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {
		return new Env<AcquaTipoRecordEnv>((AcquaTipoRecordEnv) ei);
	}
	
	@Override
	public boolean normalizza(String belfiore) throws RulEngineException {

		return false;
	}

	@Override
	public GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni() {
		// TODO Auto-generated method stub
		return null;
	}
		
}
