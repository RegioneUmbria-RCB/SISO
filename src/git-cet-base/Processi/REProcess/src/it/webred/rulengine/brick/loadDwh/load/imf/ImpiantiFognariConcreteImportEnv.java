package it.webred.rulengine.brick.loadDwh.load.imf;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;





import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract class ImpiantiFognariConcreteImportEnv<T extends EnvImport> extends ConcreteImportEnv<T> {

	public ImpiantiFognariConcreteImportEnv(T ei) {
		super(ei);
	}
	
}
