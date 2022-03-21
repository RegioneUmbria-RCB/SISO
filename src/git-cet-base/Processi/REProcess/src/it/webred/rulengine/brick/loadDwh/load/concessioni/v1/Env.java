package it.webred.rulengine.brick.loadDwh.load.concessioni.v1;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniStandardFilesEnv;
import it.webred.rulengine.brick.loadDwh.load.insertDwh.EnvInsertDwh;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

public class Env<T extends ConcessioniStandardFilesEnv<TestataStandardFileBean>> extends  ConcessioniConcreteImportEnv<T> {

	public Env(T envImport) {
		super(envImport);
	}

	private String SQL_RE_CONCESSIONI_A = getProperty("sql.RE_CONCESSIONI_A");
	private String SQL_RE_CONCESSIONI_B = getProperty("sql.RE_CONCESSIONI_B");
	private String SQL_RE_CONCESSIONI_C = getProperty("sql.RE_CONCESSIONI_C");
	private String SQL_RE_CONCESSIONI_D = getProperty("sql.RE_CONCESSIONI_D");
	private String SQL_RE_CONCESSIONI_E =  getProperty("sql.RE_CONCESSIONI_E");

	private EnvSitCConcDatiTec envSitCConcDatiTec = new EnvSitCConcDatiTec(envImport.RE_CONCESSIONI_C,envImport.getTestata().getProvenienza(), CHIAVE);
	private EnvSitCConcessioni envSitCConcessioni = new EnvSitCConcessioni(envImport.RE_CONCESSIONI_A,envImport.getTestata().getProvenienza(),CHIAVE);
	private EnvSitCConcessioniCatasto envSitCConcessioniCatasto = new EnvSitCConcessioniCatasto(envImport.RE_CONCESSIONI_D,envImport.getTestata().getProvenienza(),CHIAVE);
	private EnvSitCConcIndirizzi envSitCConcIndirizzi=new EnvSitCConcIndirizzi(envImport.RE_CONCESSIONI_E,envImport.getTestata().getProvenienza(),CHIAVE);
	private EnvSitCConcPersona envSitCConcPersona =  new EnvSitCConcPersona(envImport.RE_CONCESSIONI_B,envImport.getTestata().getProvenienza(),CHIAVE);
	private EnvSitCPersona envSitCPersona = new EnvSitCPersona(envImport.RE_CONCESSIONI_B,envImport.getTestata().getProvenienza(),CHIAVE );

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecordSpec() {
		LinkedHashMap<String, String>  hm = new LinkedHashMap<String, String>();
		hm.put(envImport.RE_CONCESSIONI_A, "A");
		hm.put(envImport.RE_CONCESSIONI_B, "B");
		hm.put(envImport.RE_CONCESSIONI_C, "C");
		hm.put(envImport.RE_CONCESSIONI_D, "D");
		hm.put(envImport.RE_CONCESSIONI_E, "E");
		
		return hm;
		
	}




	public String getSQL_RE_CONCESSIONI_A() {
		return SQL_RE_CONCESSIONI_A;
	}

	public String getSQL_RE_CONCESSIONI_B() {
		return SQL_RE_CONCESSIONI_B;
	}

	public String getSQL_RE_CONCESSIONI_C() {
		return SQL_RE_CONCESSIONI_C;
	}

	public String getSQL_RE_CONCESSIONI_D() {
		return SQL_RE_CONCESSIONI_D;
	}

	public String getSQL_RE_CONCESSIONI_E() {
		return SQL_RE_CONCESSIONI_E;
	}

	public EnvSitCConcDatiTec getEnvSitCConcDatiTec() {
		return envSitCConcDatiTec;
	}

	public EnvSitCConcessioni getEnvSitCConcessioni() {
		return envSitCConcessioni;
	}

	public EnvSitCConcessioniCatasto getEnvSitCConcessioniCatasto() {
		return envSitCConcessioniCatasto;
	}

	public EnvSitCConcIndirizzi getEnvSitCConcIndirizzi() {
		return envSitCConcIndirizzi;
	}

	public EnvSitCConcPersona getEnvSitCConcPersona() {
		return envSitCConcPersona;
	}

	public EnvSitCPersona getEnvSitCPersona() {
		return envSitCPersona;
	}




	@Override
	public LinkedHashMap<String, ArrayList<String>> getTabelleAndChiavi() {
		LinkedHashMap<String, ArrayList<String>>  hm = new LinkedHashMap<String, ArrayList<String>>();
		hm.put(envImport.RE_CONCESSIONI_A, new ArrayList<String>(Arrays.asList(CHIAVE)));
		hm.put(envImport.RE_CONCESSIONI_B, new ArrayList<String>(Arrays.asList(CHIAVE)));
		hm.put(envImport.RE_CONCESSIONI_C, new ArrayList<String>(Arrays.asList(CHIAVE)));
		hm.put(envImport.RE_CONCESSIONI_D, new ArrayList<String>(Arrays.asList(CHIAVE)));
		
		return hm;
	}





	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv#getTabelleFinaliDHW()
	 */
	@Override
	public ArrayList<String> getTabelleFinaliDHW() {
		ArrayList<String> tabs = new ArrayList<String>();

		tabs.add("SIT_C_CONCESSIONI");
		tabs.add("SIT_C_CONCESSIONI_CATASTO");
		tabs.add("SIT_C_CONC_DATI_TEC");
		tabs.add("SIT_C_CONC_INDIRIZZI");
		tabs.add("SIT_C_PERSONA");
		tabs.add("SIT_C_CONC_PERSONA");
		
		return tabs;
		
	}
	























	


	

	


	
	
}
