package it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.mav.v1;

import it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.mav.TSMavConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.mav.TSMavEnv;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Env<T extends TSMavEnv> extends TSMavConcreteImportEnv<T> {

	public Env(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedHashMap getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.tableFlusso , null);
		hm.put(envImport.tableDisposizione , null);
		hm.put(envImport.tablePagamento , null);
		hm.put(envImport.tablePromemoria , null);
		hm.put(envImport.tableSoggetto , null);
		
		return hm;
	}

	@Override
	public LinkedHashMap getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.tableFlusso , null);
		return tr;
	}

	@Override
	public ArrayList getTabelleFinaliDHW() {
		// TODO Auto-generated method stub
		return null;
	}

}

