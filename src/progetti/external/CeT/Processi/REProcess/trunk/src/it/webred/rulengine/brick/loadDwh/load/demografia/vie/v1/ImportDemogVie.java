package it.webred.rulengine.brick.loadDwh.load.demografia.vie.v1;

import it.webred.rulengine.brick.loadDwh.load.demografia.vie.DemogVieEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

public class ImportDemogVie<T extends  Env<?>> extends ConcreteImport<T> {

	public ImportDemogVie() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.demografia.vie.v1.Env<DemogVieEnv> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.demografia.vie.v1.Env<DemogVieEnv>((DemogVieEnv)ei);
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
