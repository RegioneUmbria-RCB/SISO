package it.webred.rulengine.brick.loadDwh.load.schedemartelli;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract class SchedeMartelliConcreteImportEnv<T extends EnvImport> extends ConcreteImportEnv<T> {

	public SchedeMartelliConcreteImportEnv(T ei) {
		super(ei);
	}
	
}
