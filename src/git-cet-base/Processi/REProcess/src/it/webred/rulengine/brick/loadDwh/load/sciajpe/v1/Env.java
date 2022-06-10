package it.webred.rulengine.brick.loadDwh.load.sciajpe.v1;


import java.util.ArrayList;



import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.sciajpe.SciaJpeConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.sciajpe.SciaJpeTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.sciajpe.bean.Testata;



public class Env<T extends SciaJpeTipoRecordEnv<Testata>> extends SciaJpeConcreteImportEnv<T> {
	
	private String SQL_RE_SCIA_JPE_UNO = getProperty("sql.RE_SCIA_JPE_UNO");

	//private EnvSitOmniadoc envSitOmniadoc = new EnvSitOmniadoc(envImport.RE_OMNIADOC_UNO,  envImport.getTestata().getProvenienza(), new String[]{"NR_PROT_CAPOFILA"});
	private EnvSitSciaJpe envSitSciaJpe = new EnvSitSciaJpe(envImport.RE_SCIA_JPE_UNO,  envImport.getTestata().getProvenienza(), new String[]{""});


	public EnvSitSciaJpe getEnvSitSciaJpe() {
		return envSitSciaJpe;
	}


	public String getSQL_RE_SCIA_JPE_UNO() {
		return SQL_RE_SCIA_JPE_UNO;
	}


	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		ArrayList<String> alChiavi = new ArrayList<String>();
		//alChiavi.add("NR_PROT_CAPOFILA");
		hm.put(envImport.RE_SCIA_JPE_UNO, alChiavi);
		
		return hm;
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.RE_SCIA_JPE_UNO , null);
		
		return tr;
	}
	
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_SCIA_JPE");
		
		return tabs;
	}
	
}

