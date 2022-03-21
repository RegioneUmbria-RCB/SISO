package it.webred.rulengine.brick.loadDwh.load.redditi.analitici.v1;

import it.webred.rulengine.brick.loadDwh.load.redditi.analitici.RedditiAnEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;

public class ImportRedditiAn<T extends  Env<?>> extends ConcreteImport<T> {

	public ImportRedditiAn() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.redditi.analitici.v1.Env<RedditiAnEnv> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.redditi.analitici.v1.Env<RedditiAnEnv>((RedditiAnEnv)ei);
	}

	@Override
	public boolean normalizza(String belfiore) throws RulEngineException {
		log.debug("Sto normalizzando");
		return false;
	}

	@Override
	public GestoreCorrelazioneVariazioni getGestoreCorrelazioneVariazioni() {
		// TODO Auto-generated method stub
		return null;
	}

}
