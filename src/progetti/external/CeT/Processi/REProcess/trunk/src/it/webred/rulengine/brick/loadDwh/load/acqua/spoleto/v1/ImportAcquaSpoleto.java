package it.webred.rulengine.brick.loadDwh.load.acqua.spoleto.v1;

import it.webred.rulengine.brick.loadDwh.load.acqua.spoleto.AcquaSpoletoEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

public class ImportAcquaSpoleto<T extends  Env<?>> extends ConcreteImport<T> {

	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {
		return new Env<AcquaSpoletoEnv>((AcquaSpoletoEnv) ei);
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
