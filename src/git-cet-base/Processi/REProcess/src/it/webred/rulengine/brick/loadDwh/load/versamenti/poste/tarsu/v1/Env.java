package it.webred.rulengine.brick.loadDwh.load.versamenti.poste.tarsu.v1;



import it.webred.rulengine.brick.loadDwh.load.versamenti.poste.tarsu.VersPosteConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.versamenti.poste.tarsu.VersPosteEnv;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Env<T extends VersPosteEnv> extends VersPosteConcreteImportEnv<T> {

	public Env(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedHashMap getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.tableRiepilogo , null);
		hm.put(envImport.tableVersamento , null);
		return hm;
	}

	@Override
	public LinkedHashMap getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.tableVersamento , null);
		return tr;
	}

	@Override
	public ArrayList getTabelleFinaliDHW() {
		// TODO Auto-generated method stub
		return null;
	}

}

