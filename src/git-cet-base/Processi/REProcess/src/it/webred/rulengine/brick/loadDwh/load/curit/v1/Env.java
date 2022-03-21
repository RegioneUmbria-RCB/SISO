package it.webred.rulengine.brick.loadDwh.load.curit.v1;

import it.webred.rulengine.brick.loadDwh.load.curit.CuritConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.curit.CuritTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.curit.v1.EnvSitCurit;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Env<T extends CuritTipoRecordEnv> extends CuritConcreteImportEnv<T> {
	
	private String SQL_RE_CURIT_UNO = getProperty("sql.RE_CURIT_UNO");

	private EnvSitCurit envSitCurit = new EnvSitCurit(envImport.RE_CURIT_UNO,  envImport.getTestata().getProvenienza(), new String[]{"IDENTIFICATIVO_IMPIANTO"});


	public EnvSitCurit getEnvSitCurit() {
		return envSitCurit;
	}


	public String getSQL_RE_CURIT_UNO() {
		return SQL_RE_CURIT_UNO;
	}


	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		ArrayList<String> alChiavi = new ArrayList<String>();
		alChiavi.add("IDENTIFICATIVO_IMPIANTO");
		hm.put(envImport.RE_CURIT_UNO, alChiavi);
		
		return hm;
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.RE_CURIT_UNO , null);
		return tr;
	}
	
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_CURIT");
		return tabs;
	}
	
}

