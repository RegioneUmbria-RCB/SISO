package it.webred.rulengine.brick.loadDwh.load.acqua.milano.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.acqua.milano.AcquaMilanoConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.acqua.milano.AcquaMilanoEnv;
import it.webred.rulengine.brick.loadDwh.load.acqua.milano.v1.EnvReAcqua;

public class Env<T extends AcquaMilanoEnv> extends AcquaMilanoConcreteImportEnv<T> {

	private String SQL_RE_ACQUA_A = getProperty("sql.RE_ACQUA_A");

	private EnvReAcqua envReAcqua=new EnvReAcqua(envImport.RE_ACQUA_A,"ACQUA_MILANO",null);
	
	public String getSQL_RE_ACQUA_A() {
		return SQL_RE_ACQUA_A;
	}
	
	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_ACQUA_A, null);
		
		return hm;
		
	}

	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_ACQUA_A, null);
		
		return hm;
	}

	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();

		tabs.add("SIT_ACQUA_UTENTE");
		tabs.add("SIT_ACQUA_UTENZE");
		tabs.add("SIT_ACQUA_CATASTO");
		
		return tabs;
		
	}

	public EnvReAcqua getEnvReAcqua() {
		return envReAcqua;
	}

}
