package it.webred.rulengine.brick.loadDwh.load.licenzecommerciali.v1;

import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniStandardFilesEnv;
import it.webred.rulengine.brick.loadDwh.load.licenzecommerciali.LicenzeCommercialiConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.licenzecommerciali.LicenzeCommercialiTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Env<T extends LicenzeCommercialiTipoRecordEnv<TestataStandardFileBean>> extends LicenzeCommercialiConcreteImportEnv<T> {

	private String SQL_RE_LICENZECOMMERCIALI_VIE = getProperty("sql.RE_LICENZECOMMERCIALI_VIE");
	private String SQL_RE_LICENZECOMMERCIALI_A = getProperty("sql.RE_LICENZECOMMERCIALI_A");
	private String SQL_RE_LICENZECOMMERCIALI_B = getProperty("sql.RE_LICENZECOMMERCIALI_B");

	private EnvSitLicenzeCommercioVie envSitLicenzeCommercioVie = new EnvSitLicenzeCommercioVie(envImport.RE_LICENZECOMMERCIALI_A, "LICCOM", new String[] {"CODICE_VIA"});
	private EnvSitLicenzeCommercio envSitLicenzeCommercio = new EnvSitLicenzeCommercio(envImport.RE_LICENZECOMMERCIALI_A, "LICCOM", new String[] {"CHIAVE"});
	private EnvSitLicenzeCommercioAnag envSitLicenzeCommercioAnag = new EnvSitLicenzeCommercioAnag(envImport.RE_LICENZECOMMERCIALI_B, "LICCOM", new String[] {"CHIAVE"});

	public String getSQL_RE_LICENZECOMMERCIALI_VIE() {
		return SQL_RE_LICENZECOMMERCIALI_VIE;
	}

	public void setSQL_RE_LICENZECOMMERCIALI_VIE(String sql_re_licenzecommerciali_vie) {
		SQL_RE_LICENZECOMMERCIALI_VIE = sql_re_licenzecommerciali_vie;
	}

	public String getSQL_RE_LICENZECOMMERCIALI_A() {
		return SQL_RE_LICENZECOMMERCIALI_A;
	}

	public void setSQL_RE_LICENZECOMMERCIALI_A(String sql_re_licenzecommerciali_a) {
		SQL_RE_LICENZECOMMERCIALI_A = sql_re_licenzecommerciali_a;
	}

	public String getSQL_RE_LICENZECOMMERCIALI_B() {
		return SQL_RE_LICENZECOMMERCIALI_B;
	}
	
	public void setSQL_RE_LICENZECOMMERCIALI_B(String sql_re_licenzecommerciali_b) {
		SQL_RE_LICENZECOMMERCIALI_B = sql_re_licenzecommerciali_b;
	}
	
	public EnvSitLicenzeCommercioVie getEnvSitLicenzeCommercioVie() {
		return envSitLicenzeCommercioVie;
	}

	public void setEnvSitLicenzeCommercioVie(
			EnvSitLicenzeCommercioVie envSitLicenzeCommercioVie) {
		this.envSitLicenzeCommercioVie = envSitLicenzeCommercioVie;
	}

	public EnvSitLicenzeCommercio getEnvSitLicenzeCommercio() {
		return envSitLicenzeCommercio;
	}

	public void setEnvSitLicenzeCommercio(
			EnvSitLicenzeCommercio envSitLicenzeCommercio) {
		this.envSitLicenzeCommercio = envSitLicenzeCommercio;
	}

	public EnvSitLicenzeCommercioAnag getEnvSitLicenzeCommercioAnag() {
		return envSitLicenzeCommercioAnag;
	}

	public void setEnvSitLicenzeCommercioAnag(
			EnvSitLicenzeCommercioAnag envSitLicenzeCommercioAnag) {
		this.envSitLicenzeCommercioAnag = envSitLicenzeCommercioAnag;
	}

	public Env(T envImport) {
		super(envImport);
	}

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecordSpec() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_LICENZECOMMERCIALI_A, "A");
		hm.put(envImport.RE_LICENZECOMMERCIALI_B, "B");
		return hm;		
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_LICENZECOMMERCIALI_A, new ArrayList<String>(Arrays.asList(CHIAVE)));
		hm.put(envImport.RE_LICENZECOMMERCIALI_B, new ArrayList<String>(Arrays.asList(CHIAVE)));
		return hm;
	}

	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		String[] array = new String[] {"SIT_LICENZE_COMMERCIO",
				"SIT_LICENZE_COMMERCIO_ANAG",
				"SIT_LICENZE_COMMERCIO_TIT",
				"SIT_LICENZE_COMMERCIO_VIE"};
		ArrayList<String> tabs = new ArrayList<String>(Arrays.asList(array));
		

		return tabs;
	}	
	
}

