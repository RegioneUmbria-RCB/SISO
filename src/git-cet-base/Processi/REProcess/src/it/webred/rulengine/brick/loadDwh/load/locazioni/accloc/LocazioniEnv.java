package it.webred.rulengine.brick.loadDwh.load.locazioni.accloc;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;



public class LocazioniEnv extends EnvImport {

	public LocazioniEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("5", connectionName, ctx);
	}
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public String tableRE_A = getProperty("tableA.name");
	public String filePrefix = getProperty("file.prefix");
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles+filePrefix;
	}

}
