package it.webred.rulengine.brick.loadDwh.load.locazioni.conloc.v1;

import it.webred.rulengine.brick.loadDwh.load.locazioni.conloc.LocazioniEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.exception.RulEngineException;


public class ImportLocazioni<T extends  Env<?>> extends ConcreteImport<T> {

	

	public ImportLocazioni() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.locazioni.conloc.v1.Env<LocazioniEnv> getEnvSpec(EnvImport ei) {
		return new it.webred.rulengine.brick.loadDwh.load.locazioni.conloc.v1.Env<LocazioniEnv>((LocazioniEnv)ei);
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
