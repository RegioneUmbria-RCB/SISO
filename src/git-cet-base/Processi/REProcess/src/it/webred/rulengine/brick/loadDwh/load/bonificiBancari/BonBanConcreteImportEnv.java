package it.webred.rulengine.brick.loadDwh.load.bonificiBancari;

import java.util.LinkedHashMap;



import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract  class BonBanConcreteImportEnv<T extends EnvImport> extends ConcreteImportEnv<T> {

	public BonBanConcreteImportEnv(T ei) {
		super(ei);
	}

	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		return getTabelleAndTipiRecordSpec();
	}

	public abstract LinkedHashMap<String, String> getTabelleAndTipiRecordSpec();






}
