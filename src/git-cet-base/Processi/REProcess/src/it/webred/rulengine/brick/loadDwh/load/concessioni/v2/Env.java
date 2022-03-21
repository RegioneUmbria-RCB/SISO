package it.webred.rulengine.brick.loadDwh.load.concessioni.v2;

import java.util.LinkedHashMap;

import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniStandardFilesEnv;
import it.webred.rulengine.brick.loadDwh.load.concessioni.v1.EnvSitCConcDatiTec;
import it.webred.rulengine.brick.loadDwh.load.concessioni.v1.EnvSitCConcPersona;
import it.webred.rulengine.brick.loadDwh.load.concessioni.v1.EnvSitCPersona;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;

public class Env<T extends ConcessioniStandardFilesEnv<TestataStandardFileBean>> extends  it.webred.rulengine.brick.loadDwh.load.concessioni.v1.Env<T> {

	public Env(T envImport) {
		super(envImport);
	}


	private String SQL_RE_CONCESSIONI_A = getProperty("sql.RE_CONCESSIONI_A");
	private String SQL_RE_CONCESSIONI_B = getProperty("sql.RE_CONCESSIONI_B");
	private String SQL_RE_CONCESSIONI_C = getProperty("sql.RE_CONCESSIONI_C");
	private String SQL_RE_CONCESSIONI_D = getProperty("sql.RE_CONCESSIONI_D");
	private String SQL_RE_CONCESSIONI_E =  getProperty("sql.RE_CONCESSIONI_E");

	private EnvSitCConcDatiTec envSitCConcDatiTec = new EnvSitCConcDatiTec(envImport.RE_CONCESSIONI_C,envImport.getTestata().getProvenienza(),CHIAVE);
	private EnvSitCConcessioni envSitCConcessioni = new EnvSitCConcessioni(envImport.RE_CONCESSIONI_A,envImport.getTestata().getProvenienza(),CHIAVE);
	private EnvSitCConcessioniCatasto envSitCConcessioniCatasto = new EnvSitCConcessioniCatasto(envImport.RE_CONCESSIONI_D,envImport.getTestata().getProvenienza(),CHIAVE);
	private EnvSitCConcIndirizzi envSitCConcIndirizzi=new EnvSitCConcIndirizzi(envImport.RE_CONCESSIONI_E,envImport.getTestata().getProvenienza(),CHIAVE);
	private EnvSitCConcPersona envSitCConcPersona =  new EnvSitCConcPersona(envImport.RE_CONCESSIONI_B,envImport.getTestata().getProvenienza(),CHIAVE);
	private EnvSitCPersona envSitCPersona = new EnvSitCPersona(envImport.RE_CONCESSIONI_B,envImport.getTestata().getProvenienza(),CHIAVE);

	@Override
	public LinkedHashMap<String, String> getTabelleAndTipiRecordSpec() {
		return super.getTabelleAndTipiRecordSpec();
		
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


















	


	

	


	
	
}
