package it.webred.rulengine.brick.loadDwh.load.cnc.flusso750;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;

public class Flusso750Env <T extends Flusso750TipoRecordEnv> extends ConcreteImportEnv<T> {

	public Flusso750Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.tableR00C , null);
		return hm;

	}

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		
		hm.put(envImport.tableR00C, "00C");
		hm.put(envImport.tableR3A, "R3A");
		hm.put(envImport.tableR3Z, "R3Z");
		hm.put(envImport.tableR5A, "R5A");
		hm.put(envImport.tableR5B, "R5B");
		hm.put(envImport.tableR5Z, "R5Z");
		hm.put(envImport.tableR7A, "R7A");
		hm.put(envImport.tableR7B, "R7B");
		hm.put(envImport.tableR7C, "R7C");
		hm.put(envImport.tableR7D, "R7D");
		hm.put(envImport.tableR7E, "R7E");
		hm.put(envImport.tableR7F, "R7F");
		hm.put(envImport.tableR7G, "R7G");
		hm.put(envImport.tableR7H, "R7H");
		hm.put(envImport.tableR7K, "R7K");
		hm.put(envImport.tableR99C, "99C");
		
		return hm;
	}
	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add(envImport.tableR00C);
		tabs.add(envImport.tableR3A);
		tabs.add(envImport.tableR3Z);
		tabs.add(envImport.tableR5A);
		tabs.add(envImport.tableR5B);
		tabs.add(envImport.tableR5Z);
		tabs.add(envImport.tableR7A);
		tabs.add(envImport.tableR7B);
		tabs.add(envImport.tableR7C);
		tabs.add(envImport.tableR7D);
		tabs.add(envImport.tableR7E);
		tabs.add(envImport.tableR7F);
		tabs.add(envImport.tableR7G);
		tabs.add(envImport.tableR7H);
		tabs.add(envImport.tableR7K);
		tabs.add(envImport.tableR99C);
		
		
		return tabs;
		
	}

}
