package it.webred.rulengine.brick.loadDwh.load.planimetrieComma340.v1;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniStandardFilesEnv;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.planimetrieComma340.PlanimetrieComma340ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.planimetrieComma340.PlanimetrieComma340Env;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public class Env<T extends PlanimetrieComma340Env> extends  PlanimetrieComma340ConcreteImportEnv<T> {

	public Env(T envImport) {
		super(envImport);
	}

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.RE_PLANIMETRIE_COMMA340_A , null);

		return tr;
		
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_PLANIMETRIE_COMMA340_A , null);
		
		return hm;

	}
	

	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		String[] array = new String[] {"SIT_CAT_PLANIMETRIE_C340"};
		ArrayList<String> tabs = new ArrayList<String>(Arrays.asList(array));
		

		return tabs;
	}	
	































	


	

	


	
	
}
