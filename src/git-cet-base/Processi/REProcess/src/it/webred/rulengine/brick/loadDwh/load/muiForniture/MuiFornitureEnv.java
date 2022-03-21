package it.webred.rulengine.brick.loadDwh.load.muiForniture;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class MuiFornitureEnv extends EnvImport {
	
	public MuiFornitureEnv(String connectionName, Context ctx) throws RulEngineException {
		super("7", connectionName, ctx);
	}
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}
