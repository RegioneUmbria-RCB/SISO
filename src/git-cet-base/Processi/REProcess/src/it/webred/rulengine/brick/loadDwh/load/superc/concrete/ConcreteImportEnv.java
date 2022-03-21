package it.webred.rulengine.brick.loadDwh.load.superc.concrete;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.GestoreCorrelazioneVariazioni;
import it.webred.rulengine.dwh.DwhUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class  ConcreteImportEnv<T  extends EnvImport> {

	protected T envImport ;
	public ConcreteImportEnv(T ei) {
		this.envImport =ei;
	}
	
	
	public abstract LinkedHashMap<String, String> getTabelleAndTipiRecord();

	public abstract ArrayList<String> getTabelleFinaliDHW();

	public abstract LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi();


	
	protected String getProperty(String propName) {
		String p =  DwhUtils.getProperty(this.getClass(), propName);
		return p;
	}

	

	
	public T getEnvImport() {
		return envImport;
	}
	

	

	


	
}
