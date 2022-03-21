package it.webred.rulengine.brick.loadDwh.load.redditi.r2004;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class RedditiEnv extends EnvImport {

	public RedditiEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("11", connectionName, ctx);
	}

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public String tableRE_A = getProperty("tableA.name");
	public String anno = getProperty("file.year");
	public String prefix = getProperty("file.prefix");
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles+getFullPrefix();
	}
	
	
	//esempio nome file redditi: DICHSINT.A705.A2004MS.P0010.TXT	
	private String getFullPrefix() {
		String fullprefix = prefix;
		fullprefix += super.getCtx().getBelfiore();
		fullprefix += ".A";
		fullprefix += anno;
		return fullprefix;
	}
}
