package it.webred.rulengine.brick.elab.soggetti;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class VerificaInfoSoggettiSovvenzioniEnv extends EnvImport {

	public VerificaInfoSoggettiSovvenzioniEnv(String connectionName, Context ctx) throws RulEngineException {
		super("TODO_REGOLA", connectionName, ctx);
	}

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}
