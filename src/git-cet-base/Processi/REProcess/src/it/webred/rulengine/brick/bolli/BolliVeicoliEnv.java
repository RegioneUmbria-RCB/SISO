package it.webred.rulengine.brick.bolli;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class BolliVeicoliEnv extends EnvImport {

	public BolliVeicoliEnv(String connectionName, Context ctx) throws RulEngineException {
		super("46", connectionName, ctx);
	}
	
	public String folderNameFrom = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());

	@Override
	public String getPercorsoFiles() {
		return folderNameFrom;
	}
}
