package it.webred.rulengine.brick.loadDwh.load.demografia.milano.v1;

import it.webred.rulengine.brick.loadDwh.load.demografia.milano.DemogAnagrafeMilanoEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

public class ImportDemogAnagrafeMilano <T extends  Env<?>> extends ConcreteImport<T>  {

	public ImportDemogAnagrafeMilano() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.demografia.milano.v1.Env<DemogAnagrafeMilanoEnv> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.demografia.milano.v1.Env<DemogAnagrafeMilanoEnv>((DemogAnagrafeMilanoEnv)ei);
	}

	@Override
	public boolean normalizza(String belfiore) throws RulEngineException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni() {
		// TODO Auto-generated method stub
		return null;
	}

}
