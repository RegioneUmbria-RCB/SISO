package it.webred.rulengine.brick.loadDwh.load.rette.dettaglio.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.rette.BolletteConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.rette.BolletteEnv;
import it.webred.rulengine.brick.loadDwh.load.rette.dettaglio.BolletteDettConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.rette.dettaglio.BolletteDettEnv;

public class Env<T extends BolletteDettEnv> extends BolletteDettConcreteImportEnv<T> {

	private String SQL_RE_RTT_DETT_BOLLETTE_1_0 = getProperty("sql.RE_RTT_DETT_BOLLETTE_1_0");


	public EnvSitRttDettBollette getEnvSitRttDettBollette() {
		return new EnvSitRttDettBollette(envImport.RE_RTT_DETT_BOLLETTE_1_0,  "DETT_BOLLETTE", CHIAVE);
		
	}
	

	public String getSQL_RE_RTT_DETT_BOLLETTE_1_0() {
		return SQL_RE_RTT_DETT_BOLLETTE_1_0;
	}

	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_RTT_DETT_BOLLETTE_1_0, null);
		
		return hm;
		
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_RTT_DETT_BOLLETTE_1_0, new ArrayList<String>(Arrays.asList(CHIAVE)));
		
		
		return hm;
	}
	
	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_RTT_DETT_BOLLETTE");

		
		return tabs;
		
	}
}
