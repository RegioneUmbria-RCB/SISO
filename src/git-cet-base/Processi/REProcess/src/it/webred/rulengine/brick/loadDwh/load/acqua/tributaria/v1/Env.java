package it.webred.rulengine.brick.loadDwh.load.acqua.tributaria.v1;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.acqua.tributaria.AcquaConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.acqua.tributaria.AcquaTipoRecordEnv;

public class Env<T extends AcquaTipoRecordEnv> extends  AcquaConcreteImportEnv<T>  {

	public Env(T envImport) {
		super(envImport);
	}


	private String SQL_RE_ACQUA_A = getProperty("sql.RE_ACQUA_A");

	private EnvReAcqua envReAcqua=new EnvReAcqua(envImport.RE_ACQUA_A,envImport.getTestata().getProvenienza(),null);
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecordSpec() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_ACQUA_A, "1");
		
		return hm;
		
	}


	public String getSQL_RE_ACQUA_A() {
		return SQL_RE_ACQUA_A;
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
