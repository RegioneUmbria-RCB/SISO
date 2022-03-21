package it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.mav;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class TSMavEnv extends EnvImport {

	public TSMavEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("32", connectionName, ctx);
	}

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public String tableFlusso = getProperty("tableFlusso.name");
	public String tableDisposizione = getProperty("tableDisposizione.name");
	public String tablePagamento = getProperty("tablePagamento.name");
	public String tablePromemoria = getProperty("tablePromemoria.name");
	public String tableSoggetto = getProperty("tableSoggetto.name");
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}

