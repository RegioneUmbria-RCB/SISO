package it.webred.rulengine.brick.loadDwh.load.acqua.spoleto;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class AcquaSpoletoEnv extends EnvImport {

public String RE_ACQUA_A = getProperty("tableA.name");
	
	protected String RE_ACQUA_A_IDX = getProperty("tableA.idx");
	
	protected String createTableA = getProperty("tableA.create_table");
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public AcquaSpoletoEnv(String connectionName, Context ctx) throws RulEngineException {
		super("30",connectionName, ctx);
	}
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}
