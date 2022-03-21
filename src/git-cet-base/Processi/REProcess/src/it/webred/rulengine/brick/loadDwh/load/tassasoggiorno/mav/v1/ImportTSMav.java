package it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.mav.v1;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.mav.TSMavEnv;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

public class ImportTSMav<T extends  Env<?>> extends ConcreteImport<T> {

	public ImportTSMav() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.mav.v1.Env<TSMavEnv> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.mav.v1.Env<TSMavEnv>((TSMavEnv)ei);
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
