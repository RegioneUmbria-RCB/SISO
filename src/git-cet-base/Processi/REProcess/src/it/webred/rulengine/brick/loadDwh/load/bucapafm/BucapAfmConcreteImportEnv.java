package it.webred.rulengine.brick.loadDwh.load.bucapafm;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;



import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract class BucapAfmConcreteImportEnv<T extends EnvImport> extends ConcreteImportEnv<T> {

	public BucapAfmConcreteImportEnv(T ei) {
		super(ei);
	}
	
}
