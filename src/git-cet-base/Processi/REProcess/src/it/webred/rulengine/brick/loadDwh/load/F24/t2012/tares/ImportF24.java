package it.webred.rulengine.brick.loadDwh.load.F24.t2012.tares;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

public class ImportF24<T extends  Env<?>> extends ConcreteImport<T> {

	public ImportF24() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.F24.t2012.tares.Env<F24Env> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.F24.t2012.tares.Env<F24Env>((F24Env)ei);
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
