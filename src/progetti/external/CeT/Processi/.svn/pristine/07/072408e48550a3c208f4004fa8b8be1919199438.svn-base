package it.webred.rulengine.brick.loadDwh.load.versamenti.dm.ici.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.versamenti.dm.ici.VersIciConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.versamenti.dm.ici.VersIciEnv;


public class Env<T extends VersIciEnv> extends VersIciConcreteImportEnv<T> {

	public Env(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedHashMap getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.tableAnagrafica , null);
		hm.put(envImport.tableContabile , null);
		hm.put(envImport.tableViolazione , null);
		return hm;
	}

	@Override
	public LinkedHashMap getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.tableContabile , null);
		return tr;
	}

	@Override
	public ArrayList getTabelleFinaliDHW() {
		// TODO Auto-generated method stub
		return null;
	}

}

