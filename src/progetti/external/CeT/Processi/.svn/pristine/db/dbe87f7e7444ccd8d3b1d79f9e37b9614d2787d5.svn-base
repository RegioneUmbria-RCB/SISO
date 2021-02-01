package it.webred.rulengine.brick.loadDwh.load.redditi.analitici.v1;

import it.webred.rulengine.brick.loadDwh.load.redditi.analitici.RedditiAnConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.redditi.analitici.RedditiAnEnv;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Env<T extends RedditiAnEnv> extends RedditiAnConcreteImportEnv<T> {

	public Env(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedHashMap getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.tableRiga , null);
		hm.put(envImport.tableAttivita , null);
		hm.put(envImport.tableAnagrafici , null);
		hm.put(envImport.tableDescrizione , null);
		hm.put(envImport.tableDomicilio , null);
		hm.put(envImport.tableRedditi , null);
		hm.put(envImport.tableTrascodifica , null);
		
		return hm;
	}

	@Override
	public LinkedHashMap getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.tableRedditi , null);
		return tr;
	}

	@Override
	public ArrayList getTabelleFinaliDHW() {
		// TODO Auto-generated method stub
		return null;
	}

}

