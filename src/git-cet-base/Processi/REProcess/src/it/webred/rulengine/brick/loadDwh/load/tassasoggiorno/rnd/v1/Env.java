package it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.rnd.v1;

import it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.rnd.TSRndConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.rnd.TSRndEnv;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Env<T extends TSRndEnv> extends TSRndConcreteImportEnv<T> {

	public Env(T ei) {
		super(ei);
		// TODO Auto-generated constructor stub
	}

	@Override
	public LinkedHashMap getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.tableFlusso , null);
		hm.put(envImport.tableLiquidita , null);
		hm.put(envImport.tableMovimento , null);
		hm.put(envImport.tableMovimentoInfo , null);
		hm.put(envImport.tablePratica , null);
		hm.put(envImport.tableSaldo , null);
		
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

