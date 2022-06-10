package it.webred.rulengine.brick.loadDwh.load.sciajpe;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;




import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract class SciaJpeConcreteImportEnv<T extends EnvImport> extends ConcreteImportEnv<T> {

	public SciaJpeConcreteImportEnv(T ei) {
		super(ei);
	}
	
}
