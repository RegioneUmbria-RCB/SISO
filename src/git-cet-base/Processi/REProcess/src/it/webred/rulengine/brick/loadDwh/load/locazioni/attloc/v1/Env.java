package it.webred.rulengine.brick.loadDwh.load.locazioni.attloc.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.locazioni.attloc.LocazioniConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.locazioni.attloc.LocazioniEnv;

public class Env<T extends LocazioniEnv> extends LocazioniConcreteImportEnv<T> {

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
		// TODO Auto-generated method stub
		return null;
	}

}
