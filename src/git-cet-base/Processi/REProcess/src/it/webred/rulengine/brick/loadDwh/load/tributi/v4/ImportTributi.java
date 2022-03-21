package it.webred.rulengine.brick.loadDwh.load.tributi.v4;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.tributi.TributiTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.tributi.v4.Env;

public class ImportTributi<T extends Env<?>> extends it.webred.rulengine.brick.loadDwh.load.tributi.v3.ImportTributi<T> {
	
	
	
	public ImportTributi() {
		super();
	}

	@Override
	public ConcreteImportEnv getEnvSpec(EnvImport ei) {
		return new Env<TributiTipoRecordEnv>((TributiTipoRecordEnv) ei);
	}
	
}
