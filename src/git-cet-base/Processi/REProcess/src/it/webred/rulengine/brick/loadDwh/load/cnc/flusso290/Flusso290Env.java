package it.webred.rulengine.brick.loadDwh.load.cnc.flusso290;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;

public class Flusso290Env <T extends Flusso290TipoRecordEnv> extends ConcreteImportEnv<T> {

	public Flusso290Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.N0table , null);
		
	
		return hm;

	}

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		
		hm.put(envImport.N0table, "N0");
		hm.put(envImport.N1table, "N1");
		hm.put(envImport.N2table, "N2");
		hm.put(envImport.N3table, "N3");
		hm.put(envImport.N4table, "N4");
		hm.put(envImport.N5table, "N5");
		hm.put(envImport.N9table, "N9");
		
		return hm;
	}

	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add(envImport.N0table);
		tabs.add(envImport.N0table);
		tabs.add(envImport.N1table);
		tabs.add(envImport.N1table);
		tabs.add(envImport.N2table);
		tabs.add(envImport.N3table);
		tabs.add(envImport.N4table);
		tabs.add(envImport.N5table);
		
		return tabs;
		
	}
	

}
