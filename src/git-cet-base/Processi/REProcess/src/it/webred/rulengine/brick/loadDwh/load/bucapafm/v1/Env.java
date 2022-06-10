package it.webred.rulengine.brick.loadDwh.load.bucapafm.v1;


import java.util.ArrayList;


import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.bucapafm.BucapAfmConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.bucapafm.BucapAfmTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.bucapafm.bean.Testata;



public class Env<T extends BucapAfmTipoRecordEnv<Testata>> extends BucapAfmConcreteImportEnv<T> {
	
	private String SQL_RE_BUCAP_UNO = getProperty("sql.RE_BUCAP_UNO");

	//private EnvSitOmniadoc envSitOmniadoc = new EnvSitOmniadoc(envImport.RE_OMNIADOC_UNO,  envImport.getTestata().getProvenienza(), new String[]{"NR_PROT_CAPOFILA"});
	private EnvSitBucapAfm envSitBucapAfm = new EnvSitBucapAfm(envImport.RE_BUCAP_UNO,  envImport.getTestata().getProvenienza(), new String[]{""});


	public EnvSitBucapAfm getEnvSitBucapAfm() {
		return envSitBucapAfm;
	}


	public String getSQL_RE_BUCAP_UNO() {
		return SQL_RE_BUCAP_UNO;
	}


	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		ArrayList<String> alChiavi = new ArrayList<String>();
		//alChiavi.add("NR_PROT_CAPOFILA");
		hm.put(envImport.RE_BUCAP_UNO, alChiavi);
		
		return hm;
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.RE_BUCAP_UNO , null);
		
		return tr;
	}
	
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_BUCAP_AFM");
		
		return tabs;
	}
	
}

