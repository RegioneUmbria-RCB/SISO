package it.webred.rulengine.brick.loadDwh.load.cnc.statoriscossione;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;

public class FlussoStatoRiscEnv <T extends FlussoStatoRiscTipoRecordEnv> extends ConcreteImportEnv<T> {

	public FlussoStatoRiscEnv(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.tableFR00C , null);
		return hm;

	}

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		
		hm.put(envImport.tableFR00C, "00C");
		hm.put(envImport.tableFR0, "FR0");
		hm.put(envImport.tableFR3, "FR3");
		hm.put(envImport.tableFR4, "FR4");
		hm.put(envImport.tableFR5, "FR5");

		return hm;
	}
	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();

		tabs.add(envImport.tableFR00C);
		tabs.add(envImport.tableFR0);
		tabs.add(envImport.tableFR3);
		tabs.add(envImport.tableFR4);
		tabs.add(envImport.tableFR5);
		
		return tabs;
		
	}
	

}
