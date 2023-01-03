package it.webred.rulengine.brick.loadDwh.load.imf.v1;


import java.util.ArrayList;




import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.imf.ImpiantiFognariConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.imf.ImpiantiFognariTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.imf.bean.Testata;



public class Env<T extends ImpiantiFognariTipoRecordEnv<Testata>> extends ImpiantiFognariConcreteImportEnv<T> {
	
	private String SQL_RE_IMPIANTI_FOGNARI_UNO = getProperty("sql.RE_IMPIANTI_FOGNARI_UNO");

	//private EnvSitOmniadoc envSitOmniadoc = new EnvSitOmniadoc(envImport.RE_OMNIADOC_UNO,  envImport.getTestata().getProvenienza(), new String[]{"NR_PROT_CAPOFILA"});
	private EnvSitImpiantiFognari envSitImpiantiFognari = new EnvSitImpiantiFognari(envImport.RE_IMPIANTI_FOGNARI_UNO,  envImport.getTestata().getProvenienza(), new String[]{""});


	public EnvSitImpiantiFognari getEnvSitImpiantiFognari() {
		return envSitImpiantiFognari;
	}


	public String getSQL_RE_IMPIANTI_FOGNARI_UNO() {
		return SQL_RE_IMPIANTI_FOGNARI_UNO;
	}


	public Env(T envImport) {
		super(envImport);
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		ArrayList<String> alChiavi = new ArrayList<String>();
		//alChiavi.add("NR_PROT_CAPOFILA");
		hm.put(envImport.RE_IMPIANTI_FOGNARI_UNO, alChiavi);
		
		return hm;
	}
	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecord() {
		LinkedHashMap<String, String> tr = new LinkedHashMap<String, String>();
		tr.put(envImport.RE_IMPIANTI_FOGNARI_UNO , null);
		
		return tr;
	}
	
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_IMPIANTI_FOGNARI");
		
		return tabs;
	}
	
}

