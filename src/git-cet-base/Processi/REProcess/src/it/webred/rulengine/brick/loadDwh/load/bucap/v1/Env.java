package it.webred.rulengine.brick.loadDwh.load.bucap.v1;


import java.util.ArrayList;

import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.bucap.BucapConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.bucap.BucapTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.bucap.bean.Testata;



public class Env<T extends BucapTipoRecordEnv<Testata>> extends BucapConcreteImportEnv<T> {
	
	private String SQL_RE_BUCAP_UNO = getProperty("sql.RE_BUCAP_UNO");

	//private EnvSitOmniadoc envSitOmniadoc = new EnvSitOmniadoc(envImport.RE_OMNIADOC_UNO,  envImport.getTestata().getProvenienza(), new String[]{"NR_PROT_CAPOFILA"});
	private EnvSitBucap envSitBucap = new EnvSitBucap(envImport.RE_BUCAP_UNO,  envImport.getTestata().getProvenienza(), new String[]{""});


	public EnvSitBucap getEnvSitBucapPraEdi() {
		return envSitBucap;
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
		tabs.add("SIT_BUCAP_PRA_EDI");
		
		return tabs;
	}
	
}

