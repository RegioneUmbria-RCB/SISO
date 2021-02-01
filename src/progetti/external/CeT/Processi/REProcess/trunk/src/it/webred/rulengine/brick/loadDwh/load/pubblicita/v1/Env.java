package it.webred.rulengine.brick.loadDwh.load.pubblicita.v1;

import it.webred.rulengine.brick.loadDwh.load.pubblicita.PubblicitaConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.pubblicita.PubblicitaTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Env<T extends PubblicitaTipoRecordEnv<TestataStandardFileBean>> extends PubblicitaConcreteImportEnv<T> {
	
	
	private String SQL_RE_PUBBLICITA_A = getProperty("sql.RE_PUBBLICITA_A");
	private String SQL_RE_PUBBLICITA_B = getProperty("sql.RE_PUBBLICITA_B");

	private EnvSitPubblicitaPratTestata envSitPubblicitaPratTestata = new EnvSitPubblicitaPratTestata(envImport.RE_PUBBLICITA_A,envImport.getTestata().getProvenienza(), new String[] {"TIPO_PRATICA","NUM_PRATICA","ANNO_PRATICA"});
	private EnvSitPubblicitaPratDettaglio envSitPubblicitaPratDettaglio = new EnvSitPubblicitaPratDettaglio(envImport.RE_PUBBLICITA_B, envImport.getTestata().getProvenienza(), new String[] {});

	
	public Env(T envImport) {
		super(envImport);
	}

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecordSpec() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_PUBBLICITA_A, "A");
		hm.put(envImport.RE_PUBBLICITA_B, "B");
		return hm;		
	}
	
	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_PUBBLICITA_A, new ArrayList<String>(Arrays.asList("TIPO_PRATICA","NUM_PRATICA","ANNO_PRATICA")));
		hm.put(envImport.RE_PUBBLICITA_B,  new ArrayList<String>());
		return hm;
	}
	
	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();
		tabs.add("SIT_PUB_PRAT_TESTATA");
		tabs.add("SIT_PUB_PRAT_DETTAGLIO");
		
		return tabs;
		
	}

	public String getSQL_RE_PUBBLICITA_A() {
		return SQL_RE_PUBBLICITA_A;
	}

	public void setSQL_RE_PUBBLICITA_A(String sQL_RE_PUBBLICITA_A) {
		SQL_RE_PUBBLICITA_A = sQL_RE_PUBBLICITA_A;
	}

	public String getSQL_RE_PUBBLICITA_B() {
		return SQL_RE_PUBBLICITA_B;
	}

	public void setSQL_RE_PUBBLICITA_B(String sQL_RE_PUBBLICITA_B) {
		SQL_RE_PUBBLICITA_B = sQL_RE_PUBBLICITA_B;
	}

	public EnvSitPubblicitaPratTestata getEnvSitPubblicitaPratTestata() {
		return envSitPubblicitaPratTestata;
	}

	public void setEnvSitPubblicitaPratTestata(
			EnvSitPubblicitaPratTestata envSitPubblicitaPratTestata) {
		this.envSitPubblicitaPratTestata = envSitPubblicitaPratTestata;
	}

	public EnvSitPubblicitaPratDettaglio getEnvSitPubblicitaPratDettaglio() {
		return envSitPubblicitaPratDettaglio;
	}

	public void setEnvSitPubblicitaPratDettaglio(
			EnvSitPubblicitaPratDettaglio envSitPubblicitaPratDettaglio) {
		this.envSitPubblicitaPratDettaglio = envSitPubblicitaPratDettaglio;
	}


	
}


