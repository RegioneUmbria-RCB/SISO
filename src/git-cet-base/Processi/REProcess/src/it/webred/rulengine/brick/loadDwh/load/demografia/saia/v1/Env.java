package it.webred.rulengine.brick.loadDwh.load.demografia.saia.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.demografia.saia.DemogSaiaXMLConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.demografia.saia.DemogSaiaXMLEnv;

public class Env<T extends DemogSaiaXMLEnv> extends DemogSaiaXMLConcreteImportEnv<T> {

	private String SQL_RE_SIT_D_SAIA_1_0 = getProperty("sql.RE_SIT_D_SAIA_1_0");

	private EnvReSitDSaia envReSitDSaia = new EnvReSitDSaia(envImport.RE_SIT_D_SAIA_1_0,  "DEMOG_SAIA", null);

	public EnvReSitDSaia getEnvReSitDSaia() {
		return envReSitDSaia;
		
	}

	public String getSQL_RE_SIT_D_SAIA_1_0() {
		return SQL_RE_SIT_D_SAIA_1_0;
	}

	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_SIT_D_SAIA_1_0, null);
		
		return hm;
		
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_SIT_D_SAIA_1_0, null);
		
		return hm;
	}
	
	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		
		tabs.add("SIT_D_PERSONA");
		tabs.add("SIT_D_PERSONA_EXTRA");
		tabs.add("SIT_D_PERSONA_CIVICO");
		tabs.add("SIT_D_CIVICO");
		tabs.add("SIT_D_FAMIGLIA");
		tabs.add("SIT_D_PERS_FAM");
		tabs.add("SIT_D_UNIONE");
		tabs.add("SIT_STATO");
		tabs.add("SIT_PROVINCIA");
		tabs.add("SIT_COMUNE");
		
		return tabs;
		
	}
}
