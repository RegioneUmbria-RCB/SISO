package it.webred.rulengine.brick.loadDwh.load.demografia.anagrafe.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.demografia.anagrafe.DemogAnagrafeConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.demografia.anagrafe.DemogAnagrafeEnv;

public class Env<T extends DemogAnagrafeEnv> extends DemogAnagrafeConcreteImportEnv<T> {

	public Env(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.tableRE_A , null);
		
		return hm;
	}

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.tableRE_A , null);
		
		return tr;
	}


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
