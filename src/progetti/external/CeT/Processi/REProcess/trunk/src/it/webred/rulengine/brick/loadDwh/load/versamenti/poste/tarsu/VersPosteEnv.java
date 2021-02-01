package it.webred.rulengine.brick.loadDwh.load.versamenti.poste.tarsu;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class VersPosteEnv extends EnvImport {

	public VersPosteEnv(String connectionName, Context ctx)
			throws RulEngineException {
		super("38", connectionName, ctx);
	}

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());

	public String tableRiga = getProperty("tableRiga.name");
	public String tableRiepilogo = getProperty("tableRiepilogo.name");
	public String tableVersamento = getProperty("tableVersamento.name");
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

}

