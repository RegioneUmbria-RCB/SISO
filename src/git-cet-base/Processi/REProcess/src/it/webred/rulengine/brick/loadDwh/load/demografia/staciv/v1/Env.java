package it.webred.rulengine.brick.loadDwh.load.demografia.staciv.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.demografia.staciv.DemogStatoCivileConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.demografia.staciv.DemogStatoCivileEnv;



public class Env<T extends DemogStatoCivileEnv> extends DemogStatoCivileConcreteImportEnv<T> {

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

	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();

		tabs.add("SIT_D_STACIV");
		
		return tabs;

	}

}
