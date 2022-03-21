package it.webred.rulengine.brick.loadDwh.load.cosap.v1;

import it.webred.rulengine.brick.loadDwh.load.cosap.CosapConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.cosap.CosapTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Env<T extends CosapTipoRecordEnv<TestataStandardFileBean>> extends CosapConcreteImportEnv<T> {

	private String SQL_RE_COSAP_A = getProperty("sql.RE_COSAP_A");
	private String SQL_RE_COSAP_B = getProperty("sql.RE_COSAP_B");

	private EnvSitTCosapContrib envSitTCosapContrib = new EnvSitTCosapContrib(envImport.RE_COSAP_A, "COSAP", new String[] {"COD_CONTRIBUENTE"});
	private EnvSitTCosapTassa envSitTCosapTassa = new EnvSitTCosapTassa(envImport.RE_COSAP_B, "COSAP", new String[] {});

	public String getSQL_RE_COSAP_A() {
		return SQL_RE_COSAP_A;
	}

	public void setSQL_RE_COSAP_A(String sql_re_cosap_a) {
		SQL_RE_COSAP_A = sql_re_cosap_a;
	}

	public String getSQL_RE_COSAP_B() {
		return SQL_RE_COSAP_B;
	}
	
	public void setSQL_RE_COSAP_B(String sql_re_cosap_b) {
		SQL_RE_COSAP_B = sql_re_cosap_b;
	}

	public EnvSitTCosapContrib getEnvSitTCosapContrib() {
		return envSitTCosapContrib;
	}

	public void setEnvSitTCosapContrib(EnvSitTCosapContrib envSitTCosapContrib) {
		this.envSitTCosapContrib = envSitTCosapContrib;
	}

	public EnvSitTCosapTassa getEnvSitTCosapTassa() {
		return envSitTCosapTassa;
	}

	public void setEnvSitTCosapTassa(EnvSitTCosapTassa envSitTCosapTassa) {
		this.envSitTCosapTassa = envSitTCosapTassa;
	}

	public Env(T envImport) {
		super(envImport);
	}

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecordSpec() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_COSAP_A, "A");
		hm.put(envImport.RE_COSAP_B, "B");
		return hm;		
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_COSAP_A, new ArrayList<String>(Arrays.asList("COD_CONTRIBUENTE")));
		hm.put(envImport.RE_COSAP_B, new ArrayList<String>());
		return hm;
	}
	
	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_T_COSAP_CONTRIB");
		tabs.add("SIT_T_COSAP_TASSA");
		
		return tabs;
		
	}	
	
}


