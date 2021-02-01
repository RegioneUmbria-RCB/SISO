package it.webred.rulengine.brick.loadDwh.load.versamenti.dm.ici.v1;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.brick.loadDwh.load.versamenti.dm.ici.VersIciEnv;
import it.webred.rulengine.exception.RulEngineException;

public class ImportVersIci<T extends  Env<?>> extends ConcreteImport<T> {

	public ImportVersIci() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.versamenti.dm.ici.v1.Env<VersIciEnv> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.versamenti.dm.ici.v1.Env<VersIciEnv>((VersIciEnv)ei);
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
