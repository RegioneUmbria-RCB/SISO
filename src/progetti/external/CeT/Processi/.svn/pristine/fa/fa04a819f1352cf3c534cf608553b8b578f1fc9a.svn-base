package it.webred.rulengine.brick.loadDwh.load.versamenti.dm.ici;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class VersIciEnv extends EnvImport {

	public VersIciEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("37", connectionName, ctx);
	}

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public String tableRiga = getProperty("tableRiga.name");
	public String tableContabile = getProperty("tableContabile.name");
	public String tableViolazione = getProperty("tableViolazione.name");
	public String tableAnagrafica = getProperty("tableAnagrafica.name");
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}

