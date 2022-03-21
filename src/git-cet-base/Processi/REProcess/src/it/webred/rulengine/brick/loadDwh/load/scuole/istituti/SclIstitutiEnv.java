package it.webred.rulengine.brick.loadDwh.load.scuole.istituti;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.gas.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class SclIstitutiEnv extends EnvImport {

	public String RE_SCL_ISTITUTI_1_0 = getProperty("table1_0.name");
	public String RE_SCL_ISTITUTI_IDX = getProperty("table1_0.idx");
	
		
	protected String createTable1_0 = getProperty("table1_0.create_table");

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public SclIstitutiEnv(String connectionName, Context ctx) throws RulEngineException {
		super("25",connectionName, ctx);
	}
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}
