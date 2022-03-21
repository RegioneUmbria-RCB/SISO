package it.webred.rulengine.brick.loadDwh.load.cosap;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

import java.util.LinkedHashMap;

public abstract class CosapConcreteImportEnv<T extends EnvImport> extends ConcreteImportEnv<T> {

	public CosapConcreteImportEnv(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}
	
	protected String[] CHIAVE = {"CHIAVE"};

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		return getTabelleAndTipiRecordSpec();
	}

	public abstract LinkedHashMap<String, String> getTabelleAndTipiRecordSpec();

}


