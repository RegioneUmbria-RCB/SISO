package it.webred.rulengine.brick.loadDwh.load.bucap;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;


import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract class BucapConcreteImportEnv<T extends EnvImport> extends ConcreteImportEnv<T> {

	public BucapConcreteImportEnv(T ei) {
		super(ei);
	}
	
}
