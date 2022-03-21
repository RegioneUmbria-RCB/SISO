package it.webred.rulengine.brick.loadDwh.load.multe.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.gas.v1.EnvSitUGas;
import it.webred.rulengine.brick.loadDwh.load.multe.MulteConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.multe.MulteEnv;

public class Env<T extends MulteEnv> extends MulteConcreteImportEnv<T> {

	private String SQL_RE_TRFF_MULTE_1_0 = getProperty("sql.RE_TRFF_MULTE_1_0");

	private EnvSitTrffMulte envSitTrffMulte = new EnvSitTrffMulte(envImport.RE_TRFF_MULTE_1_0,  "MULTE", null);

	public EnvSitTrffMulte getEnvSitTrffMulte() {
		return envSitTrffMulte;
		
	}

	public String getSQL_RE_TRFF_MULTE_1_0() {
		return SQL_RE_TRFF_MULTE_1_0;
	}

	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_TRFF_MULTE_1_0, null);
		
		return hm;
		
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_TRFF_MULTE_1_0, null);
		
		return hm;
	}
	
	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_TRFF_MULTE");

		
		return tabs;
		
	}
}
