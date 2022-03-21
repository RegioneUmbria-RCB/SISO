package it.webred.rulengine.brick.loadDwh.load.redditi.analitici;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class RedditiAnEnv extends EnvImport {

	public RedditiAnEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("31", connectionName, ctx);
	}

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public String tableRiga = getProperty("tableRiga.name");
	public String tableAttivita = getProperty("tableAttivita.name");
	public String tableAnagrafici = getProperty("tableAnagrafici.name");
	public String tableDescrizione = getProperty("tableDescrizione.name");
	public String tableDomicilio = getProperty("tableDomicilio.name");
	public String tableRedditi = getProperty("tableRedditi.name");
	public String tableTrascodifica = getProperty("tableTrascodifica.name");
	public String tableRB = getProperty("tableRB.name");
	public String tableRA = getProperty("tableRA.name");
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}

