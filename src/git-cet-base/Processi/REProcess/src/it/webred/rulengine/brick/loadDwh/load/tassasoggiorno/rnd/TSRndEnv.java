package it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.rnd;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class TSRndEnv extends EnvImport {

	public TSRndEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("32", connectionName, ctx);
	}

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public String tableFlusso = getProperty("tableFlusso.name");
	public String tableLiquidita = getProperty("tableLiquidita.name");
	public String tableMovimento = getProperty("tableMovimento.name");
	public String tableMovimentoInfo = getProperty("tableMovimentoInfo.name");
	public String tablePratica = getProperty("tablePratica.name");
	public String tableSaldo = getProperty("tableSaldo.name");
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}

