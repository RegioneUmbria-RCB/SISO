package it.webred.rulengine.brick.loadDwh.load.concessioni.v2;

import it.webred.rulengine.brick.loadDwh.load.concessioni.ConcessioniStandardFilesEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;

import org.apache.log4j.Logger;

public class ImportConcessioni<T extends  Env<?>>  extends  it.webred.rulengine.brick.loadDwh.load.concessioni.v1.ImportConcessioni<T> {





	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportConcessioni.class.getName());
	



	@Override
	public ConcreteImportEnv<ConcessioniStandardFilesEnv<TestataStandardFileBean>> getEnvSpec(EnvImport ei) {
		return new Env<ConcessioniStandardFilesEnv<TestataStandardFileBean>>((ConcessioniStandardFilesEnv) ei);
	}























	
























	
	
}
