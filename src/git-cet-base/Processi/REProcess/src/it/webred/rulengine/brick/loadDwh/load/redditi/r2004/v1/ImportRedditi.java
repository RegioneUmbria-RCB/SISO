package it.webred.rulengine.brick.loadDwh.load.redditi.r2004.v1;



import it.webred.rulengine.brick.loadDwh.load.redditi.r2004.RedditiEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

public class ImportRedditi<T extends  Env<?>> extends ConcreteImport<T> {

	public ImportRedditi() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.redditi.r2004.v1.Env<RedditiEnv> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.redditi.r2004.v1.Env<RedditiEnv>((RedditiEnv)ei);
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
