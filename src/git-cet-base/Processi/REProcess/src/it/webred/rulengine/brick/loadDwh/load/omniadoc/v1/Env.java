package it.webred.rulengine.brick.loadDwh.load.omniadoc.v1;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.omniadoc.OmniadocConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.omniadoc.OmniadocTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.omniadoc.bean.Testata;

public class Env<T extends OmniadocTipoRecordEnv<Testata>> extends OmniadocConcreteImportEnv<T> {
	
	private String SQL_RE_OMNIADOC_UNO = getProperty("sql.RE_OMNIADOC_UNO");

	//private EnvSitOmniadoc envSitOmniadoc = new EnvSitOmniadoc(envImport.RE_OMNIADOC_UNO,  envImport.getTestata().getProvenienza(), new String[]{"NR_PROT_CAPOFILA"});
	private EnvSitOmniadoc envSitOmniadoc = new EnvSitOmniadoc(envImport.RE_OMNIADOC_UNO,  envImport.getTestata().getProvenienza(), new String[]{""});


	public EnvSitOmniadoc getEnvSitOmniadocPraEdi() {
		return envSitOmniadoc;
	}


	public String getSQL_RE_OMNIADOC_UNO() {
		return SQL_RE_OMNIADOC_UNO;
	}


	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		ArrayList<String> alChiavi = new ArrayList<String>();
		//alChiavi.add("NR_PROT_CAPOFILA");
		hm.put(envImport.RE_OMNIADOC_UNO, alChiavi);
		
		return hm;
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.RE_OMNIADOC_UNO , null);
		
		return tr;
	}
	
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_OMNIADOC_PRA_EDI");
		
		return tabs;
	}
	
}

