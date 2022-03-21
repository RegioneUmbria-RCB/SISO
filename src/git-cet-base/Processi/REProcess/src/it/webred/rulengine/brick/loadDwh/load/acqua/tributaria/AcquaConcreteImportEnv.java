package it.webred.rulengine.brick.loadDwh.load.acqua.tributaria;

import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract  class AcquaConcreteImportEnv<T extends EnvImport> extends ConcreteImportEnv<T> {

	public AcquaConcreteImportEnv(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		return getTabelleAndTipiRecordSpec();
	}

	public abstract LinkedHashMap<String, String> getTabelleAndTipiRecordSpec();






}
