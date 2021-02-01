package it.webred.rulengine.brick.loadDwh.load.docfa;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class DocfaEnv extends EnvImport {

	public DocfaEnv(String connectionName, Context ctx)	throws RulEngineException {
		super("9", connectionName, ctx);
	}
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public String pkListQuery = getProperty("user.pk.list.query");	
	
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}
