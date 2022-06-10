package it.webred.rulengine.brick.loadDwh.load.omniadoc;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract class OmniadocConcreteImportEnv<T extends EnvImport> extends ConcreteImportEnv<T> {

	public OmniadocConcreteImportEnv(T ei) {
		super(ei);
	}
	
}
