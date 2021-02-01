package it.webred.rulengine.brick.loadDwh.load.enel.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;


import it.webred.rulengine.brick.loadDwh.load.enel.EnelConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.enel.EnelEnv;


public class Env<T extends EnelEnv> extends  EnelConcreteImportEnv<T> { 

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
		tabs.add("SIT_ENEL_UTENTE");
		tabs.add("SIT_ENEL_UTENZA");

		
		return tabs;
	}
	
	
}