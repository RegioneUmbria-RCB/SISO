package it.webred.rulengine.brick.loadDwh.load.ruolo.tarsu;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class RTarsuEnv extends EnvImport {

	public RTarsuEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("39", connectionName, ctx);
	}
	
	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	protected String campiTableA = getProperty("tableA.campi");
	
	protected final String SUPPLETIVO = "S";
	protected final String ACCONTO = "A";
	public String SQL_RUOLO = getProperty("sql.SQL_RUOLO");
	public String anno;
	
	public String tableRE = getProperty("tableA.name");
	
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}
	
	
	
}
