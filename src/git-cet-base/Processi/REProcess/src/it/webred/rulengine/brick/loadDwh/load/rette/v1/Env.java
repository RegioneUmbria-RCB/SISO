package it.webred.rulengine.brick.loadDwh.load.rette.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.rette.BolletteConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.rette.BolletteEnv;

public class Env<T extends BolletteEnv> extends BolletteConcreteImportEnv<T> {

	private String SQL_RE_RTT_BOLLETTE_1_0 = getProperty("sql.RE_RTT_BOLLETTE_1_0");

	private EnvSitRttBollette envSitRttBollette = new EnvSitRttBollette(envImport.RE_RTT_BOLLETTE_1_0,  "BOLLETTE", null);

	public EnvSitRttBollette getEnvSitRttBollette() {
		return envSitRttBollette;
		
	}

	public String getSQL_RE_RTT_BOLLETTE_1_0() {
		return SQL_RE_RTT_BOLLETTE_1_0;
	}

	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_RTT_BOLLETTE_1_0, null);
		
		return hm;
		
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_RTT_BOLLETTE_1_0, null);
		
		return hm;
	}
	
	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_RTT_BOLLETTE");

		
		return tabs;
		
	}
}
