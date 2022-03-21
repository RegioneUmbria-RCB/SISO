package it.webred.rulengine.brick.loadDwh.load.demografia.vie.v1;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.demografia.vie.DemogVieConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.demografia.vie.DemogVieEnv;

public class Env<T extends DemogVieEnv> extends DemogVieConcreteImportEnv<T> {

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

		tabs.add("SIT_D_VIA");
		
		return tabs;

	}

}
