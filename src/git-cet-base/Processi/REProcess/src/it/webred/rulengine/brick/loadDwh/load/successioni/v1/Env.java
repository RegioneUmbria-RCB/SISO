package it.webred.rulengine.brick.loadDwh.load.successioni.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.successioni.SuccessioniConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.successioni.SuccessioniEnv;

public class Env<T extends SuccessioniEnv> extends SuccessioniConcreteImportEnv<T> {

	public Env(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedHashMap getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.tableRE_A , null);
		
		return hm;
	}

	@Override
	public LinkedHashMap getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.tableRE_A , null);
		return tr;
	}

	@Override
	public ArrayList getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SUCCESSIONI_A");
		tabs.add("SUCCESSIONI_B");
		tabs.add("SUCCESSIONI_C");
		tabs.add("SUCCESSIONI_D");

		
		return tabs;
	}

}
