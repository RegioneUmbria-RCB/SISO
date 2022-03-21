package it.webred.rulengine.brick.loadDwh.load.gas.v1;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniStandardFilesEnv;
import it.webred.rulengine.brick.loadDwh.load.concessioni.v1.EnvSitCConcDatiTec;
import it.webred.rulengine.brick.loadDwh.load.gas.GasConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.gas.GasTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;

public class Env<T extends GasTipoRecordEnv> extends  GasConcreteImportEnv<T> {

	
	private String SQL_RE_GAS_UNO = getProperty("sql.RE_GAS_UNO");

	private EnvSitUGas envSitUGas = new EnvSitUGas(envImport.RE_GAS_UNO,  envImport.getTestata().getProvenienza(), null);


	public EnvSitUGas getEnvSitUGas() {
		return envSitUGas;
	}


	public String getSQL_RE_GAS_UNO() {
		return SQL_RE_GAS_UNO;
	}


	public Env(T envImport) {
		super(envImport);
	}

	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecordSpec() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_GAS_UNO, "1");
		
		return hm;
		
	}
	
	
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_GAS_UNO, null);
		
		return hm;
	}
	
	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_U_GAS");

		return tabs;
		
	}

	
}
