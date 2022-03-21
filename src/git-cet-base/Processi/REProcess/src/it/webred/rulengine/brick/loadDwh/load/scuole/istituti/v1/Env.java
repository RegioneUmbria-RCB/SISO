package it.webred.rulengine.brick.loadDwh.load.scuole.istituti.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.gas.v1.EnvSitUGas;
import it.webred.rulengine.brick.loadDwh.load.scuole.istituti.SclIstitutiConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.scuole.istituti.SclIstitutiEnv;

public class Env<T extends SclIstitutiEnv> extends SclIstitutiConcreteImportEnv<T> {

	private String SQL_RE_SCL_ISTITUTI_1_0 = getProperty("sql.RE_SCL_ISTITUTI_1_0");

	private EnvSitSclIstituti envSitSclIstituti = new EnvSitSclIstituti(envImport.RE_SCL_ISTITUTI_1_0,  "SCL_ISTITUTI", null);

	public EnvSitSclIstituti getEnvSitSclIstituti() {
		return envSitSclIstituti;
		
	}

	public String getSQL_RE_SCL_ISTITUTI_1_0() {
		return SQL_RE_SCL_ISTITUTI_1_0;
	}

	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_SCL_ISTITUTI_1_0, null);
		
		return hm;
		
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_SCL_ISTITUTI_1_0, null);
		
		return hm;
	}
	
	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_SCL_ISTITUTI");

		
		return tabs;
		
	}
}
