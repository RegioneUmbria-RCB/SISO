package it.webred.rulengine.brick.loadDwh.load.curit;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract class CuritConcreteImportEnv<T extends EnvImport> extends ConcreteImportEnv<T> {

	public CuritConcreteImportEnv(T ei) {
		super(ei);
	}
	
}
