package it.webred.rulengine.brick.loadDwh.load.successioni;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class SuccessioniEnv extends EnvImport {

	public SuccessioniEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("6", connectionName, ctx);
	}

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public String tableRE_A = getProperty("tableA.name");
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}
