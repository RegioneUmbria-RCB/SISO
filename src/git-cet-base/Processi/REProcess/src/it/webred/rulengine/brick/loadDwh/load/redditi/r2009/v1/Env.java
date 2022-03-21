package it.webred.rulengine.brick.loadDwh.load.redditi.r2009.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.redditi.r2009.RedditiConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.redditi.r2009.RedditiEnv;

public class Env<T extends RedditiEnv> extends RedditiConcreteImportEnv<T> {

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
		// TODO Auto-generated method stub
		return null;
	}

}

