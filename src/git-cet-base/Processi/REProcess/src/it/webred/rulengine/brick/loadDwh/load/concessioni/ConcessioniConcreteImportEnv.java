package it.webred.rulengine.brick.loadDwh.load.concessioni;

import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public abstract  class ConcessioniConcreteImportEnv<T extends EnvImport> extends ConcreteImportEnv<T> {

	public ConcessioniConcreteImportEnv(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}

	protected String[] CHIAVE ={ "CHIAVE"};
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		return getTabelleAndTipiRecordSpec();
	}

	public abstract LinkedHashMap<String, String> getTabelleAndTipiRecordSpec();






}
