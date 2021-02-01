package it.webred.rulengine.brick.loadDwh.load.bonificiBancari.v1;

import java.util.ArrayList;



import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.bonificiBancari.BonBanConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.bonificiBancari.BonBanTipoRecordEnv;

public class Env<T extends BonBanTipoRecordEnv> extends  BonBanConcreteImportEnv<T> {

	
	private String SQL_RE_BONBAN_UNO = getProperty("sql.RE_BONBAN_UNO");

	private EnvSitBonBan envSitBonBan = new EnvSitBonBan(envImport.RE_BONBAN_UNO,  envImport.getTestata().getProvenienza(), new String[]{"NUMERO_BONIFICO", "CODICE_FISCALE_ORDINANTE", "DATA_DISPOSIZIONE"});


	public EnvSitBonBan getEnvSitBonBan() {
		return envSitBonBan;
	}


	public String getSQL_RE_BONBAN_UNO() {
		return SQL_RE_BONBAN_UNO;
	}


	public Env(T envImport) {
		super(envImport);
	}

	
	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecordSpec() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_BONBAN_UNO, "1");
		
		return hm;
		
	}
	
	
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		ArrayList<String> alChiavi = new ArrayList<String>();
		alChiavi.add("NUMERO_BONIFICO");
		alChiavi.add("CODICE_FISCALE_ORDINANTE");
		alChiavi.add("DATA_DISPOSIZIONE");
		hm.put(envImport.RE_BONBAN_UNO, alChiavi);
		
		return hm;
	}
	
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_BON_BAN");

		return tabs;
		
	}

	
}
